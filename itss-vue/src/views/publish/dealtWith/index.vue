<template>
	<div class="index-container">
		<vab-query-form>
			<vab-query-form-left-panel :span="4">
				<el-button icon="el-icon-news" @click="handleCanyu">参与的</el-button>
        <el-button  v-permission="publish_upcoming_out" icon="el-icon-upload2" @click="handleUpload">导出</el-button>
			</vab-query-form-left-panel>
			<vab-query-form-right-panel :span="20">
				<el-form :inline="true" :model="queryForm" @submit.native.prevent>
					<div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态" clearable>
                <el-option>请选择</el-option>
                <el-option label="已撤销" value="0"></el-option>
                <el-option label="待评审" value="1"></el-option>
                <el-option label="评审拒绝" value="2"></el-option>
                <el-option label="待测试" value="3"></el-option>
                <el-option label="待发布" value="4"></el-option>
                <el-option label="发布成功" value="5"></el-option>
                <el-option label="发布失败" value="6"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.type" placeholder="发布类型" clearable>
                <el-option>请选择</el-option>
                <el-option label="一般发布" value="1"></el-option>
                <el-option label="紧急发布" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.create_time_start" type="date" value-format="yyyy-MM-dd" placeholder="(Start)创建时间"></el-date-picker>
            </el-form-item>
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.create_time_end" type="date" value-format="yyyy-MM-dd" placeholder="(End)创建时间"></el-date-picker>
            </el-form-item>
					</div>
          <el-form-item>
            <el-input v-model.trim="queryForm.keyword"   @keyup.enter.native="queryData" placeholder="关键字" clearable/>
          </el-form-item>
					<el-form-item>
						<el-button v-permission="publish_upcoming_select" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button v-permission="publish_upcoming_select" icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button v-permission="publish_upcoming_select" icon="el-icon-d-arrow-right" @click="moreQuery" v-else>高级</el-button>
					</el-form-item>
				</el-form>
			</vab-query-form-right-panel>
		</vab-query-form>

		<el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
			highlight-current-row border @current-change="handleCurrentChange">
			<!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column  label="序号" width="70px;" align="center">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
<!--      <el-table-column show-overflow-tooltip  prop="issue_no"  sortable   width="140px;"  label="变更单号"   align="center"  ></el-table-column>-->
      <el-table-column show-overflow-tooltip prop="issue_no" label="发布单号" align="center" width="140px;" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="title" sortable label="标题" header-align="center" align="center">
        <template #default="{ row }">
          <el-link @click="handleView(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip  label="评审人" align="center">
        <template #default="scope">
          <span v-if="scope.row.type == '1'">{{scope.row.approver}}</span>
          <span v-else-if="scope.row.type == '2'"></span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="创建人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" align="center" label="创建时间" width="160px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="type" label="发布类型" align="center" width="100px;">
        <template #default="scope">
          <div v-if="scope.row.type == '1'">一般发布</div>
          <div v-else-if="scope.row.type == '2'">紧急发布</div>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip  label="测试人" align="center" width="100px;">
        <template #default="scope">
          <span v-if="scope.row.type == '1'">{{scope.row.tester_name}}</span>
          <span v-else-if="scope.row.type == '2'"></span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="publisher_name" label="发布人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status" label="状态" align="center" width="100px;">
        <template #default="scope">
          <div v-if="scope.row.status == '0'">已撤销</div>
          <div v-else-if="scope.row.status == '1'">待评审</div>
          <div v-if="scope.row.status == '2'">评审拒绝</div>
          <div v-else-if="scope.row.status == '3'">待测试</div>
          <div v-if="scope.row.status == '4'">待发布</div>
          <div v-else-if="scope.row.status == '5'">发布成功</div>
          <div v-else-if="scope.row.status == '6'">发布失败</div>
        </template>
      </el-table-column>
			<el-table-column show-overflow-tooltip prop="truename" label="操作" align="center" width="100px;">
				<template #default="scope">
            <el-button  v-permission="publish_upcoming_review" v-if="scope.row.status == '1'" plain @click="handleExamine1(scope.row)" type="primary" size="mini">评审</el-button>
          <el-button  v-permission="publish_upcoming_test" v-if="scope.row.status == '3'" plain @click="handleExamine2(scope.row)" type="primary" size="mini">测试</el-button>
          <el-button  v-permission="publish_upcoming_release" v-if="scope.row.status == '4'" plain @click="handleExamine3(scope.row)" type="primary" size="mini">发布</el-button>
				</template>
			</el-table-column>

		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
	</div>
</template>

<script>
import {getPublishBasicInfo,getDbPublishBasicInfo,exportDbPublishExcel} from '@/api/publish'
	import {
		getList,
		doDelete
	} from '@/api/userManagement'
import permission from '@/directive/permission/index.js'
	export default {
		name: 'publishDb2',
    directives: { permission },
		components: {  },
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
          status: '',
          type:'',
          create_time_start:'',
          create_time_end:'',
          keyword:''
        },
			}
		},
		created() {
			this.fetchData()
		},
		methods: {
      handleView(row) {
          this.$router.push({
              path: '/fabu/publishView',
              query: {id: row.id},
          })
      },
			handleCurrentChange(val) {
				this.selectRows = val
			},

			handleCanyu() {
				this.$router.push({
					path: '/fabu/publishCanyu',
				})
			},
      handleExamine1(row) {
			  console.log(row.id);
        const ids = row.id
        this.$router.push({
          path: '/fabu/publishExamine',
          query: {id: ids},
        })
      },
      handleExamine2(row) {
        const ids = row.id
        this.$router.push({
          path: '/fabu/publishTesting',
          query: {id: ids},
        })
      },
      handleExamine3(row) {
        const ids = row.id
        this.$router.push({
          path: '/fabu/publishRelease',
          query: {id: ids},
        })
      },
      handleTest(row) {
        this.$router.push({
          path: '/fabu/publishTesting',
          query: {row: row},
        })
      },
      handlePub(row) {
        this.$router.push({
          path: '/fabu/publishRelease',
          query: {row: row},
        })
      },
      // handleCommandmore(command) {
      //   switch (command) {
      //     case 'a':
      //     this.$router.push({
      //       path: '/fabu/publishTesting',
      //       query: {row: this.selectRows},
      //     })
      //       break
      //     case 'b':
      //     this.$router.push({
      //       path: '/fabu/publishRelease',
      //       query: {row: this.selectRows},
      //     })
      //       break
      //
      //     default:
      //       //这里是没有找到对应的值处理
      //       break
      //   }
      // },
      handleUpload() {
        this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
          this.exportMyDbPublishExcel();
          this.$baseMessage('数据导出成功', 'success')
        })
      },
      async exportMyDbPublishExcel() {
        exportDbPublishExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '发布代办导出.xls')
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
        const {data} = await getDbPublishBasicInfo(this.queryForm)
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
