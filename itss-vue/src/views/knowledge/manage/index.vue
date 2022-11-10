<template>
	<div class="index-container">
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input autocomplete="off" placeholder="部门"  v-model="queryForm.deptName"
                        clearable @clear="queryForm.dept_id= ''"
                        @focus="openwinOne"></el-input>
              <!--							<el-button icon="el-icon-search"-->
              <!--								style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
              <!--								@click="openwinOne"></el-button>-->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input autocomplete="off" placeholder="知识类别"
                        v-model="queryForm.knowledge_category_name"
                        clearable @clear="queryForm.knowledge_category_id= ''"
                        @focus="openwinThree"></el-input>
              <!--							<el-button icon="el-icon-search"-->
              <!--								style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
              <!--								@click="openwinThree"></el-button>-->
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker
                v-model="queryForm.startTime"
                type="date"
                clearable
                placeholder="(Start)添加时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker
                v-model="queryForm.endTime"
                type="date"
                clearable
                placeholder="(End)添加时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.status" clearable placeholder="审核状态">
                <el-option>请选择</el-option>
                <el-option label="待审核" value="1"></el-option>
                <el-option label="发布" value="2"></el-option>
                <el-option label="审核拒绝" value="3"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.knowledge_status" clearable placeholder="状态">
                <el-option>请选择</el-option>
                <el-option label="禁用" value="0"></el-option>
                <el-option label="使用" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.is_recommend" clearable placeholder="推荐知识">
                <el-option>请选择</el-option>
                <el-option label="不推荐" value="0"></el-option>
                <el-option label="推荐" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>

      </div>
		<vab-query-form>
			<vab-query-form-left-panel :span="6">
				<el-button  v-permission="book_manage_add" icon="el-icon-plus" type="primary" @click="handleAdd">
					添加
				</el-button>
				<el-button v-permission="book_manage_edit" icon="el-icon-edit" type="success" @click="handleEdit">
					修改
				</el-button>
				<!--<el-button icon="el-icon-refresh" @click="handleRefresh">-->
					<!--刷新索引-->
				<!--</el-button>-->
				<el-dropdown trigger="hover" @command="handleCommand">
				  <el-button>
				    更多
				    <i class="el-icon-arrow-down el-icon--right"></i>
				  </el-button>
				  <el-dropdown-menu slot="dropdown">
					<el-dropdown-item v-permission="book_manage_use" icon="el-icon-check" command="a">
					  使用
					</el-dropdown-item>
					<el-dropdown-item  v-permission="book_manage_unuse" icon="el-icon-warning-outline" command="b">
					  禁用
					</el-dropdown-item>
				    <el-dropdown-item v-permission="book_manage_delete" icon="el-icon-delete" command="c">
				      删除
				    </el-dropdown-item>
				    <el-dropdown-item v-permission="book_manage_out" icon="el-icon-upload2" command="d">
				      导出
				    </el-dropdown-item>
					<el-dropdown-item  v-permission="book_manage_import" icon="el-icon-download" command="e">
					  导入
					</el-dropdown-item>
				  </el-dropdown-menu>
				</el-dropdown>
			</vab-query-form-left-panel>
			<vab-query-form-right-panel :span="18">


					<el-form-item>
						<el-input v-model.trim="queryForm.title" placeholder="标题" clearable @keyup.enter.native="queryData"/>
					</el-form-item>
					<el-form-item>
						<el-button v-permission="book_manage_select" icon="el-icon-search" type="primary" @click="queryData">
							查询
						</el-button>
						<el-button v-permission="book_manage_select" icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">
							高级
						</el-button>
						<el-button v-permission="book_manage_select" icon="el-icon-d-arrow-right" @click="moreQuery" v-else>
							高级
						</el-button>
					</el-form-item>

			</vab-query-form-right-panel>
		</vab-query-form>
    </el-form>

		<el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
			highlight-current-row border @current-change="handleCurrentChange">
			<!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
			<el-table-column show-overflow-tooltip  label="序号" align="center" width="200px;" sortable>
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
			</el-table-column>
			<el-table-column show-overflow-tooltip prop="title" width="200px;" sortable label="标题" align="center">
        <template #default="{ row }">
          <el-link type="primary" @click="handleView(row.id)">{{row.title}}</el-link>
        </template>
			</el-table-column>
      <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="categoryName" label="知识类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="createUserName" label="添加人" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="添加时间" align="center"></el-table-column>
      <!--<el-table-column show-overflow-tooltip prop="approver_name" label="审核人"></el-table-column>-->
      <el-table-column
        show-overflow-tooltip
        label="审核状态"
        align="center"
      ><template slot-scope="scope">
        {{ scope.row.status == 1 ? '待审核' :  scope.row.status == 3 ? '审核拒绝' : '发布'}}
      </template></el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="可被全员搜索"
        align="center"
      ><template slot-scope="scope">
        {{ scope.row.is_full_search == 1 ? '是' : '否' }}
      </template></el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="状态"
        align="center"
      ><template slot-scope="scope">
        {{ scope.row.knowledge_status == 1 ? '启用' : '禁用' }}
      </template></el-table-column>
      <el-table-column show-overflow-tooltip prop="score" label="贡献分" align="center"></el-table-column>
			<el-table-column show-overflow-tooltip prop="truename"  label="操作" align="center">
				<template slot-scope="scope">
<!--          <span v-if="scope.row.status == 1">-->
<!--            <el-button v-permission="book_manage_verify"  type="success" icon="el-icon-paperclip" plain size="mini" @click="handleCheck(scope.row.id)">审核</el-button>-->
<!--          </span>-->
					<span v-if="scope.row.score == null && scope.row.status == 2">
            <el-button  v-permission="book_manage_point" type="success" icon="el-icon-paperclip" plain size="mini" @click="handleGrade(scope.row.id)">打分</el-button>
          </span>

				</template>
			</el-table-column>

		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
			<dialogone ref="dialogone" @deptData="deptset"  @fetch-data="
			    {
			      UserDiaOne
			    }
			  "></dialogone>
			  <dialogtwo ref="dialogtwo" @deptData="deptset2"  @fetch-data="
			      {
			        UserDiaTwo
			      }
			    "></dialogtwo>
				<dialogthree ref="dialogthree" @deptData="deptset3" @fetch-data="
				    {
				      UserDiaThree
				    }
				  "></dialogthree>
			  <userimport ref="userimport"></userimport>
	</div>
</template>

<script>
	import {
    manageKnowledge,
    deleteKnowledge,
    useKnowledge,
    unUseKnowledge,
    exporManageKnowledgeExcel,
	} from '@/api/knowledgeInfo'
	import dialogone from './components/UserDiaOne'
	import dialogtwo from './components/UserDiaTwo'
	import dialogthree from './components/UserDiaThree'
	import Userimport from './components/UserImport'
  import permission from '@/directive/permission/index.js'
	export default {
		name: 'myknowledgeadd',
    directives: { permission },
		components: { dialogone, dialogtwo, dialogthree,Userimport},
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
			handleCommand(command) {
			  switch (command) {

			    case 'a':
			      this.handleAction()
			      break
			    case 'b':
			      this.handleDisable()
			      break
			    case 'c':
			      this.handleDelete()
			      break
				  case 'd':
				    this.handleUpload()
				    break
			    case 'e':
			      this.$refs['userimport'].showWin()
			      break
			    default:
			      //这里是没有找到对应的值处理
			      break
			  }
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
			handleAdd() {

        this.$router.push({
          path: '/book/examineAdd',
          query: { value: 1 },
        });
				// this.$router.push({
				// 	path: '/book/manageAdd',
				// })
			},
			handleView(value) {
				this.$router.push({
					path: '/book/myKnowledgeView',
          query: { id: value },
				})
			},
			handleEdit() {
				if (this.selectRows != '' && this.selectRows != null) {
          if (this.selectRows.status == 1){
            const id = this.selectRows.id
            this.$router.push({
              path: '/book/examineEdit',
              query: { id: id ,value:1 },
            })
          }else {
            this.$baseMessage('只能对未审核的数据进行修改', 'error')
            return false
          }

				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}

			},
			handleRefresh(){
				this.$baseMessage('刷新索引成功！', 'success')
				this.fetchData()
			},
			handleUpload() {
				this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
          exporManageKnowledgeExcel().then((res) => {
            const url = URL.createObjectURL(new Blob([res]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', '知识管理导出.xls')
            document.body.appendChild(link)
            link.click()
          })
					this.$baseMessage('数据导出成功', 'success')
				})
			},
			handleCheck(value){
				// this.$router.push({
				// 	path: '/book/manageCheck',
        //   query: { id: value },
				// })
        this.$router.push({
          path: '/book/examineCheck',
          query: { id: value,value: 1 },
        })
			},
			handleGrade(value){
				this.$router.push({
					path: '/book/manageGrade',
          query: { id: value },
				})
			},
			handleDelete() {
				if (this.selectRows != '' && this.selectRows != null) {

          const id = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {
              msg
            } = await deleteKnowledge({
              id
            })
            this.$baseMessage(msg, 'success')

            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
            const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
            console.log("totalPage",totalPage);
            this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
            this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo
            console.log(this.queryForm.pageNo);
            this.fetchData()
          })


				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
			},
			handleAction() {
				if (this.selectRows != '' && this.selectRows != null) {
				  if (this.selectRows.status == 2){
            const id = this.selectRows.id
            this.$baseConfirm('确定要激活该条记录？', null, async () => {
              const { msg } = await useKnowledge({ id })
              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
          } else {
            this.$baseMessage('只能对发布的知识进行操作', 'error')
            return false
          }

				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
			},
			handleDisable() {
				if (this.selectRows != '' && this.selectRows != null) {
				  if (this.selectRows.status == 2){
            const id = this.selectRows.id
            this.$baseConfirm('确定要禁用该条记录？', null, async () => {
              const {
                msg
              } = await unUseKnowledge({
                id
              })

              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
          } else {
            this.$baseMessage('只能对发布的知识进行操作', 'error')
            return false
          }

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
				const { data, totalCount } = await manageKnowledge(this.queryForm)
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
	}
</style>
