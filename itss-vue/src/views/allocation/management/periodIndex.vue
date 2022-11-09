<template>
	<div class="index-container">
		<vab-query-form>
			<vab-query-form-left-panel :span="12">
				<el-button v-permission="pzgl_manage_inventory_manage_add" icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button v-permission="pzgl_manage_inventory_manage_edit" icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-view" type="info" @click="handleView">查看</el-button>
        <el-button v-permission="pzgl_manage_inventory_manage_deleted" icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
			</vab-query-form-left-panel>
			<vab-query-form-right-panel :span="12" class='period'>
					<el-form :inline="true" :model="queryForm" @submit.native.prevent>
            <el-form-item style="width: 180px;" class="selbox">
              <el-input v-model.trim="queryForm.name" placeholder="名称" clearable @keyup.enter.native="queryData"/>
            </el-form-item>
						<el-form-item>
							<el-select v-model.trim="queryForm.status" placeholder="状态" clearable>
								 <el-option>请选择</el-option>
							  <el-option label="未开始" value="1"></el-option>
                <el-option label="已开始" value="2"></el-option>
                <el-option label="已完成" value="3"></el-option>
                <el-option label="已作废" value="4"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item style="width: 180px;" class="selbox">
                <el-input v-model.trim="queryForm.create_user_name" placeholder="创建人" clearable @clear="queryForm.create_user=''" @focus="handleSolveUser('chuangjianren')">
                  <!--<el-button slot="append" icon="el-icon-search" @click="handleSolveUser('chuangjianren')"></el-button>-->
                </el-input>
						</el-form-item>
						<el-form-item>
							<el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
						</el-form-item>
					</el-form>
				</vab-query-form-right-panel>
			</vab-query-form>

		<el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
			highlight-current-row border @current-change="setSelectRows">
<!--			<el-table-column show-overflow-tooltip type="selection"></el-table-column>-->
			<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="60px;" > </el-table-column>
			<el-table-column show-overflow-tooltip prop="name" align="center" label="名称" header-align="center">
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{ row.name }}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="createUser" width="200px;" sortable label="创建人" align="center"> </el-table-column>
      <el-table-column show-overflow-tooltip prop="updateUser" width="200px;" sortable label="修改人" align="center"> </el-table-column>
      <el-table-column show-overflow-tooltip prop="status" label="状态" :formatter="formatter" align="center" width="100px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="truename" label="操作" align="center" width="150px;">
				<template slot-scope="scope">
					<el-button v-permission="pzgl_manage_inventory_manage_generate" type="primary" icon="el-icon-paperclip" class="mr5" plain size="mini" @click="handleAssets(scope.row)" >生成盘点资产</el-button>
				</template>
			</el-table-column>

		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
			<edit ref="edit" @fetch-data="fetchData"></edit>
			<rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
			<generate ref="generate" @fetch-data="fetchData"></generate>
      <users ref="users" @userData="getUser"></users>
	</div>
</template>

<script>
	// import dialogtwo from './components/UserDiaTwo'
	import edit from './components/edit'
	import Rolesview from './components/view'
	import Generate from './components/generate'
  //引用领用人、负责人弹窗页面
  import Users from '@/components/UserSelectModel'

  import {getPeriodList,delPeriod,delBatch } from '@/api/periodManage'
  import permission from '@/directive/permission/index.js'
	export default {
		name: 'periodIndex',
    directives: { permission },
		components: {
			// dialogtwo,
			edit,
			Rolesview,
			Generate,
      Users,
		},
		data() {
			return {
        selectType: '',
        input2: '',
			  falg:false,
        createUserMap:[],

				checked: false,
				isShow: false,
				list: null,
				listLoading: true,
				loading: false,
				options: [],
				value:'',
				value1:'',
				value2:'',
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
        selectRows: [],
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
          name: '',
          status: '',
          create_user: '',
          create_user_name: '',
				},
			}
		},
		created() {
			this.fetchData()
		},
		methods: {
      pageVisible2(val){
        this.dddddddd = val;
      },
      handleSolveUser(type) {
        switch (type) {
          case 'chuangjianren':
            this.selectType = 'chuangjianren';
            this.$refs['users'].showWin();
            break;
        }
      },
      getUser(row) {
        switch (this.selectType) {
          case 'chuangjianren'://领用人
            this.queryForm.create_user = row.id;
            this.queryForm.create_user_name = row.name;
            console.log(this.queryForm);
            break;
        }
      },
      formatter(row, column) {
        //1.未开始、2.已开始、3.已完成、4.已作废
        if(row.status == 1){
          return "未开始";
        }else if(row.status == 2){
          return "已开始";
        }else if(row.status == 3){
          return "已完成";
        }else if(row.status == 4){
          return "已作废";
        }else{
          return "未知状态";
        }

      },
			// openwinTwo() {
			// 	this.$refs['dialogtwo'].showWin()
			// },

			// handleCurrentChange(val) {
			// 	this.selectRows = val
			// },
      setSelectRows(val) {
        this.selectRows = val
      },
			handleAdd() {
			  this.$refs['edit'].showEdit()
			},
			handleAssets(data){
				 this.$refs['generate'].showEdit(data)
			},

			handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
			    this.$refs['edit'].showEdit(this.selectRows)
        } else {
			    this.$baseMessage('未选中任何行', 'error')
			    return false
			  }
			},

			handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
			  // if (this.selectRows != '' && this.selectRows != null) {
			    this.$refs['rolesview'].showView(this.selectRows)
        } else {
			    this.$baseMessage('未选中任何行', 'error')
			    return false
			  }
			},
      handlesee(row) {
        if (row.id != '' && row.id != null) {
          this.$refs['rolesview'].showView(row)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },

			handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id;
					this.$baseConfirm('你确定要删除选中项吗', "删除盘点期", async () => {
						const { msg	} = await delBatch(ids)
						this.$baseMessage(msg, 'success')
						this.fetchData()
					})
				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
			},

			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			moreQuery() {
				this.isShow = !this.isShow
			},
			remoteMethod(query) {
			  if (query !== '') {
			    this.loading = true
			    setTimeout(() => {
			      this.loading = false
			      this.options = this.list.filter((item) => {
			        return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
			      })
			    }, 200)
			  } else {
			    this.options = []
			  }
			},
			async fetchData() {
				this.listLoading = true
				const {data,totalCount} = await getPeriodList(this.queryForm)
				this.list = data.list
				this.total = data.total
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
		},
	}
</script>
<style lang="scss" scoped>
	.index-container {

			::v-deep {
				.period .el-input {
					width: 150px;
				}

				.selbox .el-button {
					padding: 0;
					text-align: center;
				}

				.selbox .el-input {
					width: 100%;
				}

				.textbox .el-input__inner {
					padding-right: 0;
				}
			}
		.mr5{
			margin-right: 5px;
		}
	}
</style>
