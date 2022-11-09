<template>
	<div class="index-container">
		<vab-query-form>
			<vab-query-form-left-panel :span="2">
				<el-button v-permission="book_inside_out" icon="el-icon-upload2" type="primary" @click="handleUpload">
					导出
				</el-button>
			</vab-query-form-left-panel>
			<vab-query-form-right-panel :span="22">
				<el-form :inline="true" :model="queryForm"  @submit.native.prevent>
					<div class="hiddenmore" v-if="isShow">
						<el-form-item style="width: 160px;" class="selbox">
							<el-input autocomplete="off" placeholder="部门"
                        v-model="queryForm.deptName"
                        clearable
                        @clear="queryForm.dept_id= ''"
                        @focus="openwinOne"></el-input>
<!--							<el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwinOne"></el-button>-->
						</el-form-item>
						<el-form-item style="width: 160px;" class="selbox">
							<el-input autocomplete="off" placeholder="知识类别"
                        v-model="queryForm.knowledge_category_name"
                        clearable
                        @clear="queryForm.knowledge_category_id= ''"
                        @focus="openwinThree"></el-input>
<!--							<el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwinThree"></el-button>-->
						</el-form-item>
						<el-form-item class="textbox">
							<el-date-picker v-model="queryForm.startTime" type="date" clearable placeholder="(Start)添加时间"></el-date-picker>
						</el-form-item>
						<el-form-item class="textbox">
							<el-date-picker v-model="queryForm.endTime" type="date" clearable placeholder="(End)添加时间"></el-date-picker>
						</el-form-item>
						<el-form-item>
							<el-select v-model="queryForm.status" clearable placeholder="审核状态">
                <el-option>请选择</el-option>
								<el-option label="待审核" value="1"></el-option>
								<el-option label="发布" value="2"></el-option>
                <el-option label="审核拒绝" value="3"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item>
							<el-select v-model="queryForm.knowledge_status" clearable placeholder="状态">
                <el-option>请选择</el-option>
								<el-option label="禁用" value="0"></el-option>
								<el-option label="使用" value="1"></el-option>
							</el-select>
						</el-form-item>
<!--						<el-form-item style="width: 160px;" class="selbox">-->
<!--							<el-select v-model="queryForm.approver_name" filterable remote reserve-keyword clearable-->
<!--								placeholder="审核人" :remote-method="remoteMethod" :loading="loading"-->
<!--								style="width: 80%">-->
<!--								<template #prefix>-->
<!--									<span style="padding-left: 2px"><i class="el-icon-search"></i></span>-->
<!--								</template>-->
<!--								<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>-->
<!--							</el-select>-->
<!--							<el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwinTwo"></el-button>-->
<!--						</el-form-item>-->
						<el-form-item>
							<el-select v-model="queryForm.is_recommend" clearable placeholder="推荐知识">
                <el-option>请选择</el-option>
								<el-option label="不推荐" value="0"></el-option>
								<el-option label="推荐" value="1"></el-option>
							</el-select>
						</el-form-item>

					</div>
					<el-form-item>
						<el-input v-model.trim="queryForm.title" placeholder="标题" clearable @keyup.enter.native="queryData"/>
					</el-form-item>
					<el-form-item>
						<el-button  v-permission="book_inside_select" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button  v-permission="book_inside_select" icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button   v-permission="book_inside_select" icon="el-icon-d-arrow-right" @click="moreQuery" v-else>高级</el-button>
					</el-form-item>
				</el-form>
			</vab-query-form-right-panel>
		</vab-query-form>

		<el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
			highlight-current-row border @current-change="handleCurrentChange">
			<!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
			<el-table-column show-overflow-tooltip  label="序号" align="center" width="70px;" >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="title" sortable label="标题" header-align="center" align="center">
        <template slot-scope="scope">
          <el-link type="primary" @click="handleView(scope.row.id)">{{scope.row.title}}</el-link>
        </template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="categoryName" label="知识类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="添加时间" align="center"></el-table-column>

		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
		<dialogone ref="dialogone"  @deptData="deptset"  @fetch-data=" { UserDiaOne }"></dialogone>
    <dialogtwo ref="dialogtwo" @deptData="deptset2" @fetch-data=" { UserDiaTwo }"></dialogtwo>
    <dialogthree ref="dialogthree" @deptData="deptset3" @fetch-data=" { UserDiaThree }"></dialogthree>
	</div>
</template>

<script>
	import {
    getInsideKnowledge,exporInsideKnowledgeExcel,
	} from '@/api/knowledgeInfo'
	import dialogone from './components/UserDiaOne'
	import dialogtwo from './components/UserDiaTwo'
	import dialogthree from './components/UserDiaThree'
  import permission from '@/directive/permission/index.js'
	export default {
		name: 'myknowledgeadd',
    directives: { permission },
		components: { dialogone, dialogtwo,dialogthree},
		data() {
			return {
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
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
          dept_id: '',
          deptName: '',
          knowledge_category_id: '',
          knowledge_category_name: '',
          startTime: '',
          endTime: '',
          status: '',
          knowledge_status: '',
          approver_id: '',
          approver_name: '',
          is_recommend: '',
          keyword: '',
          title: '',
				},
			}
		},
		created() {
			this.fetchData()
		},
		methods: {
      deptset(data){
        this.queryForm.dept_id = data.id;
        this.queryForm.deptName = data.name;
      },
      deptset2(data){
        this.queryForm.approver_id = data.id;
        this.queryForm.approver_name = data.user_name;
      },
      deptset3(data){
        this.queryForm.knowledge_category_id = data.id;
        this.queryForm.knowledge_category_name = data.name;
      },
			openwinOne() {
				this.$refs['dialogone'].showWin()
			},
			openwinTwo() {
				this.$refs['dialogtwo'].showWin()
			},
			openwinThree() {
				this.$refs['dialogthree'].showWin()
			},
			handleCurrentChange(val) {
				this.selectRows = val
			},
			handleUpload() {
				this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
          exporInsideKnowledgeExcel().then((res) => {
            const url = URL.createObjectURL(new Blob([res]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', '内部知识导出.xls')
            document.body.appendChild(link)
            link.click()
          })

					this.$baseMessage('数据导出成功', 'success')
				})
			},
			handleView(value) {
				this.$router.push({
					path: '/book/myKnowledgeView',
          query: { id: value },
				})
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
				const {
					data,
					totalCount
				} = await getInsideKnowledge(this.queryForm)
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
		.hiddenmore {
			display: inline;

			::v-deep {
				.el-input {
					width: 130px;
				}
				.selbox .el-button{
					padding: 0;
					text-align: center;
				}
				.selbox .el-input{
					width: 100%;
				}
				.textbox .el-input__inner{
					padding-right: 0;
				}
			}
		}
	}
</style>
