<template>
	<div class="index-container">
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
    <div class="hiddenmoretop" v-if="isShow">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item>
            <el-select v-model="queryForm.inventory_id" placeholder="所属盘点期" clearable >
               <el-option>请选择</el-option>
              <el-option v-for="item in panDianQiArr" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item >
            <el-input autocomplete="off" placeholder="资产编号" v-model="queryForm.am_assets_number" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item>
            <el-select clearable v-model="queryForm.assets_type_id" placeholder="配置项类型" @change="assetsTypeChange">
               <el-option>请选择</el-option>
              <el-option v-for="item in assetsTypes" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item>
            <el-select clearable v-model="queryForm.child_asset" placeholder="配置子类">
               <el-option>请选择</el-option>
              <el-option v-for="item in assetsChildren" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item>
            <el-input autocomplete="off" placeholder="资产名称" v-model="queryForm.am_assets_name" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item class="selbox" prop="isvip" >
            <el-input autocomplete="off" placeholder="部门" clearable    v-model.trim="queryForm.sd_name" clearable @clear="queryForm.sd_id=''" @focus="openwinOne"></el-input>
<!--            <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwinOne"></el-button>-->
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item class="selbox">
            <el-input autocomplete="off" placeholder="领用人" clearable @clear="queryForm.su_id=''"    v-model.trim="queryForm.su_name" @focus="handleSolveUser('lyr')"></el-input>
<!--            <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="handleSolveUser('lyr')"></el-button>-->
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item class="selbox">
            <el-input autocomplete="off" placeholder="确认人" clearable @clear="queryForm.suc_id=''"  v-model.trim="queryForm.suc_name" @focus="handleSolveUser('qrr')"></el-input>
<!--            <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="handleSolveUser('qrr')"></el-button>-->
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item class="selbox">
            <el-input autocomplete="off" placeholder="处理人" clearable  @clear="queryForm.suh_id=''"  v-model.trim="queryForm.suh_name" @focus="handleSolveUser('clr')"></el-input>
<!--            <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="handleSolveUser('clr')"></el-button>-->
          </el-form-item>
        </el-col>
<!--        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">-->
<!--          <el-form-item>-->
<!--            <el-select v-model="queryForm.username" placeholder="通知发送状态" clearable>-->
<!--               <el-option>请选择</el-option>-->
<!--              <el-option label="否" value="0"></el-option>-->
<!--              <el-option label="是" value="1"></el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item>
            <el-select clearable v-model="queryForm.status" placeholder="盘点状态">
               <el-option>请选择</el-option>
              <el-option label="未确认" value="0"></el-option>
              <el-option label="确认无误" value="1"></el-option>
              <el-option label="确认有误" value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
          <el-form-item>
            <el-select clearable v-model="queryForm.handle_status" placeholder="处理状态">
               <el-option>请选择</el-option>
              <el-option label="未处理" value="0"></el-option>
              <el-option label="已处理" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </div>
		<vab-query-form>
			<vab-query-form-left-panel :span="12">
<!--				<el-button icon="el-icon-finished" type="primary" @click="handleBatchConfirm">-->
<!--					批量确认-->
<!--				</el-button>-->
				<el-button v-permission="pzgl_manage_inventory_deleted"  icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
        <el-button icon="el-icon-view" type="success" @click="handleView">查看</el-button>
        <el-button v-permission="pzgl_manage_inventory_manage_select" icon="el-icon-connection" type="success" @click="handleManage">盘点期管理</el-button>
        <el-button v-permission="pzgl_manage_inventory_out" icon="el-icon-upload2" type="primary" @click="handleUpload">导出</el-button>
        <el-button v-permission="pzgl_manage_inventory_email" icon="el-icon-message" type="primary" @click="handleEmail">发送通知邮件</el-button>
			</vab-query-form-left-panel>
			<vab-query-form-right-panel :span="12">
						<el-form-item>
							<el-input v-model.trim="queryForm.keywords" placeholder="关键字" @keyup.enter.native="queryData" clearable />
						</el-form-item>
						<el-form-item>
							<el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
              <el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">高级</el-button>
              <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>高级</el-button>
						</el-form-item>
				</vab-query-form-right-panel>
			</vab-query-form>
    </el-form>

		<el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
			highlight-current-row border   @current-change="setSelectRows">
			<el-table-column show-overflow-tooltip  label="序号" align="center" width="60px;">

<!--        <template slot-scope="scope">-->
<!--           {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}-->
<!--         -->
<!--        </template>-->
        <template  slot-scope="scope" >
          <el-link @click="handlesee(scope.row)">{{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}</el-link>
        </template>
      </el-table-column>
			<el-table-column show-overflow-tooltip prop="ai_name" align="center" label="所属盘点期"> </el-table-column>
			<el-table-column show-overflow-tooltip prop="am_id" label="配置项id" align="center" v-if="false"></el-table-column>
			<el-table-column show-overflow-tooltip prop="am_assets_number" label="资产编号" align="center" width="170px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="aty_name" label="配置项类型" align="center"></el-table-column>
			<el-table-column show-overflow-tooltip prop="ac_name" label="配置子类" align="center"></el-table-column>
			<el-table-column show-overflow-tooltip prop="am_assets_name" label="资产名称" align="center"></el-table-column>
			<el-table-column show-overflow-tooltip prop="sd_name" label="部门" align="center" width="100px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="su_name" label="领用人" align="center" width="90px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="ast_status_name" label="资产状态" align="center" width="80px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="am_it_status" label="IT状态" align="center" width="80px;">
        <template #default="{ row }">
          <el-tag type="success" v-if="row.am_it_status == 1">使用</el-tag>
          <el-tag type="warning" v-else>禁用</el-tag>
        </template>
      </el-table-column>
			<el-table-column show-overflow-tooltip prop="suc_name" label="确认人" align="center" width="90px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="suh_name" label="处理人" align="center" width="90px;"></el-table-column>
			<el-table-column show-overflow-tooltip prop="confirm_time" label="确认时间" align="center"></el-table-column>
			<el-table-column show-overflow-tooltip prop="handle_result" label="盘点结果" align="center"></el-table-column>
			<el-table-column show-overflow-tooltip prop="truename" width="100px;" label="操作" align="center">
				<template slot-scope="scope">
          <el-button  v-permission="pzgl_manage_inventory_sure"  type="primary" plain size="mini" v-if="!scope.row.suc_name" @click="handleConfirm(scope.row)">确认</el-button>
					<el-button  v-permission="pzgl_manage_inventory_deal" type="primary" plain size="mini" v-else-if="!scope.row.suh_name" @click="handleDeal(scope.row)">处理</el-button>

				</template>
			</el-table-column>
		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
			<dialogone ref="dialogone" @deptData="getDept" ></dialogone>
			<dialogtwo ref="dialogtwo" @userData="getUser"></dialogtwo>
			<userimport ref="userimport"></userimport>
	</div>
</template>

<script>
	import dialogone from '@/components/bumenTree'
	import dialogtwo from '@/components/UserSelectModel'
	// import dialogthree from '@/components/contractContactDia'
	import Userimport from './components/UserImport'
  import {getAssetsInventoryDetails,exportExcel,getAllPanDianQi,delBatch,confirmBatch} from '@/api/configtypemanagement'
  import { getACByAssetsTypeId } from '@/api/assetsChildren'
  import { getItStatus } from '@/api/assetsItStatus'
  import { getAllAssetsType } from '@/api/assetsType'
  import { getAllCompanies } from '@/api/assetsCompany'
  import { getAllUseScope } from '@/api/assetsUseScope'
  import permission from '@/directive/permission/index.js'
  export default {
		name: 'thingcapitalIndex11',
    directives: { permission },
		components: {
			dialogone,
			dialogtwo,
			// dialogthree,
			Userimport
		},
		data() {
			return {
        assetsTypes: [],
        assetsChildren: [],
        itStatus: [],
        companies: [],
        useScope: [],
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
				  id:'',
          ai_name:'',//所属盘点期
          inventory_id:'',//盘点期ID
          am_assets_number:'',
          assets_type_id: '', //配置类型
          child_asset: '', //配置子类
          am_assets_name: '', //资产名称
          it_status: '', //it状态
          sd_id: '', //部门
          sd_name: '',//
          su_id:'',
          su_name:'',//领用人
          suc_id:'',
          suc_name:'',//确认人
          suh_id:'',
          suh_name:'',//处理人
          status:'',//盘点状态
          handle_status:'',//处理状态 0 未处理，1已处理

					pageNo: 1,
					pageSize: 10,
					username: '',
				},
        panDianQiArr: [],
			}
		},
		created() {
			this.fetchData()
      this.getAssetsTypes()
      this.getAllItStatus()
      this.getCompanies()
      this.getusesCppe()
      this.getAllPanDianQi()
		},
		methods: {
      /* 查看详情 */
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$router.push({
            path: '/pzgl/capitaView', query: {id: ids}
          })
        } else if (this.selectRows.length > 1) {
          this.$baseMessage('请选择一条记录查看', 'error')
          return false
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handlesee(row) {
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/pzgl/capitaView', query: {id: row.id}
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      /*--获取配置项类型--*/
      getAssetsTypes() {
        getAllAssetsType().then((res) => {
          if (res.code == 0) {
            this.assetsTypes = res.data
          }
        })
      },

      /*--获取配置项子类--*/
      assetsTypeChange(data) {
        console.log(data)
        getACByAssetsTypeId({ assets_type_id: data }).then((res) => {
          if (res.code == 0) {
            this.assetsChildren = res.data
          }
        })
      },

      /*--获取it状态--*/
      getAllItStatus() {
        getItStatus().then((res) => {
          if (res.code == 0) {
            this.itStatus = res.data
          }
        })
      },

      /*--获取购置公司--*/
      getCompanies() {
        getAllCompanies().then((res) => {
          if (res.code == 0) {
            this.companies = res.data
          }
        })
      },

      /*--获取购置公司--*/
      getusesCppe() {
        getAllUseScope().then((res) => {
          if (res.code == 0) {
            this.useScope = res.data
          }
        })
      },
      //---------------获取下拉框选项end-----------------------------
			openwinOne() {
				this.$refs['dialogone'].showWin()
			},
      getDept(row) {
        this.queryForm.sd_id = row.id
        this.queryForm.sd_name = row.name
      },
			openwinTwo() {
				this.$refs['dialogtwo'].showWin()
			},
      handleSolveUser(type) {
        switch (type) {
          case 'lyr':
            this.selectType = 'lyr'
            this.$refs['dialogtwo'].showWin()
            break
          case 'qrr':
            this.selectType = 'qrr'
            this.$refs['dialogtwo'].showWin()
            break
          case 'clr':
            this.selectType = 'clr'
            this.$refs['dialogtwo'].showWin()
            break
        }
      },
      getUser(row) {
        switch (this.selectType) {
          case 'lyr':
            //领用人
            this.queryForm.su_id = row.id
            this.queryForm.su_name = row.name
            console.log("领用人" + this.queryForm.su_name)
            break
          case 'qrr':
            //确认人
            this.queryForm.suc_id = row.id
            this.queryForm.suc_name = row.name
            console.log("确认人" + this.queryForm.suc_name)
            break
          case 'clr':
            //处理人
            this.queryForm.suh_id = row.id
            this.queryForm.suh_name = row.name
            console.log("处理人" + this.queryForm.suh_name)
            break
        }

      },
      setSelectRows(val) {
        this.selectRows = val
      },
			handleConfirm(row) {

        this.$router.push({
          path: '/pzgl/capitaConfirm', query: { id:row.id}
        })
			},
			handleManage() {
				this.$router.push({
					path: '/pzgl/periodIndex',
				})
			},
			handleCheck(){
				this.$router.push({
					path: '/book/examineCheck',
				})
			},
			handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id;
					this.$baseConfirm('你确定要删除选中项吗', null, async () => {
						const { msg } = await delBatch(ids)
						this.$baseMessage(msg, 'success')

            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
            const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
            this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
            this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo

            this.fetchData()
					})
				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
			},
      handleBatchConfirm() {
        if (this.selectRows.length >= 1) {
          var idStr = '';
          this.selectRows.forEach((item, i) => {
            if(i ==0){
              idStr = item.id+""
            }else{
              idStr = idStr+","+item.id;
            }
          })
          console.log("=="+idStr)
          const ids =idStr;
          this.$baseConfirm('你确定要批量确认选中项吗', null, async () => {
            const { msg } = await confirmBatch(ids)
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
			handleEmail(){
				if (this.selectRows != '' && this.selectRows != null) {
					const ids = this.selectRows.id
					this.$baseConfirm('确定要给选择人员发送邮件吗？', null, async () => {
						this.$baseMessage('发送成功！', 'success')
						this.fetchData()
					})
				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
			},
			handleDeal(row){
        //router.push({ name: 'user', params: { userId: 123 }})

				this.$router.push({
					path: '/pzgl/capitaDeal' ,query: { id:row.id}
				})
			},
			handleUpload() {
        this.$baseConfirm('确定要导出该条记录？', null, async () => {
          this.exportPDExcel()
          this.$baseMessage('导出成功！', 'success')
          this.fetchData()
        })
			},
      async exportPDExcel() {
        exportExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '资产盘点管理信息导出.xls')
          document.body.appendChild(link)
          link.click()
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
        this.queryForm.aty_id = this.queryForm.assets_type_id
        this.queryForm.ac_id = this.queryForm.child_asset
				const {	data,totalCount} = await getAssetsInventoryDetails(this.queryForm)

				this.list = data.list
				this.total = data.total

				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
      async getAllPanDianQi() {
        const panDianQiArr = await getAllPanDianQi()
        this.panDianQiArr = panDianQiArr.data
      },
		},
	}
</script>
<style lang="scss" scoped>
	.index-container {
    .hiddenmoretop {
      ::v-deep {
        .el-form-item {
          margin-bottom: 15px;
        }
        .el-date-editor.el-input {
          width: 100%;
        }
        .el-date-editor.el-input--prefix .el-input__inner{
          padding-right: 15px;
        }

      }
    }
		.mr5{
			margin-right: 5px;
		}
	}
</style>
