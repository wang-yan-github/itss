<template>
	<div class="index-container">
		<el-form :inline="true" :model="queryForm" @submit.native.prevent>
			<div class="hiddenmoretop" v-if="isShow">
				<el-row :gutter="20">
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.id" placeholder="编号" clearable @keyup.enter.native="queryData"/>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-select v-model="queryForm.status" placeholder="状态" clearable>
								<el-option label="待响应" value="1"></el-option>
								<el-option label="处理中" value="2"></el-option>
								<el-option label="暂停" value="3"></el-option>
								<el-option label="处理完成" value="4"></el-option>
								<el-option label="待评价" value="5"></el-option>
								<el-option label="已关闭" value="6"></el-option>
								<el-option label="已撤销" value="7"></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.user_id_name" placeholder="用户" clearable @clear="queryForm.user_id=''" @focus="handleUser">
<!--								<el-button slot="append" icon="el-icon-search" @click="handleUser"></el-button>-->
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.create_user_name" placeholder="开单人" clearable @clear="queryForm.create_user=''" @focus="handleCreate">
<!--								<el-button slot="append" icon="el-icon-search" @click="handleCreate"></el-button>-->
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.handler_name" placeholder="处理人" clearable @clear="queryForm.handler=''" @focus="handleModel">
<!--								<el-button slot="append" icon="el-icon-search" @click="handleModel"></el-button>-->
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.response_user_name" placeholder="响应人" clearable @clear="queryForm.response_user=''" @focus="handleResponse">
<!--								<el-button slot="append" icon="el-icon-search" @click="handleResponse"></el-button>-->
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
              <el-select v-model="queryForm.source_id" placeholder="来源" class="w" clearable>
                <el-option v-for="(item,index) in sourceList" :key="index" :label="item.resource_name" :value="item.id"></el-option>
              </el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.service_groups_name" placeholder="服务群组" clearable @clear="queryForm.service_groups=''" @focus="handlegroup">
<!--                <el-button slot="append" icon="el-icon-search" @click="handlegroup"></el-button>-->
              </el-input>
            </el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="20">
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.department_name" placeholder="部门" clearable @clear="queryForm.department=''" @focus="handlebumen">
<!--								<el-button slot="append" icon="el-icon-search" @click="handlebumen"></el-button>-->
							</el-input>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.event_category_name" placeholder="工单类别" clearable @clear="queryForm.event_category_id=''" @focus="handleView">
<!--                <el-button slot="append" icon="el-icon-search" @click="handleView"></el-button>-->
              </el-input>
            </el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.priority_id" placeholder="优先级" class="w" clearable>
                <el-option v-for="(item,index) in firstList" :key="index" :label="item.first_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-date-picker v-model="queryForm.create_start_time" type="date" placeholder="开单时间(Start)"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-date-picker v-model="queryForm.create_end_time" type="date" placeholder="开单时间(End)"></el-date-picker>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
						<el-form-item>
							<el-input v-model.trim="queryForm.expenses" type="number" placeholder="费用" clearable />
						</el-form-item>
					</el-col>
				</el-row>
			</div>
			<vab-query-form>
				<vab-query-form-left-panel :span="8">
					<el-form-item>
						<el-checkbox v-model="checked" style="margin-left: 10px" @change="queryData">已结束</el-checkbox>
					</el-form-item>
				</vab-query-form-left-panel>
				<vab-query-form-right-panel :span="16">
					<el-form-item>
						<el-input v-model.trim="queryForm.keywords" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
					</el-form-item>
					<el-form-item>
						<el-button v-permission="event_assist_queryList" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-arrow-down" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>高级</el-button>
					</el-form-item>
				</vab-query-form-right-panel>
			</vab-query-form>
		</el-form>
		<el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
			highlight-current-row border @current-change="handleCurrentChange">
			<!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="handler_name" label="当前操作人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="priority_name" align="center" label="优先级" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="surplusMinutes" align="center" label="剩余时间" sortable>
        <template #default="{ row }">
          <span style="font-size: small">{{row.surplusMinutes}}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" width="100px;"></el-table-column>
			<el-table-column show-overflow-tooltip label="操作" width="100px;" align="center" fixed="right">
				<template #default="{ row }">
					<el-button v-permission="event_assist_beizhu" plain @click="handleRemake(row)" type="primary" size="mini">备注</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
		<user ref="user" @userData="getUser" @fetch-data="fetchData"></user>
		<rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
		<bumen ref="bumen" @deptData="getDept" @fetch-data="fetchData"></bumen>
		<remake ref="remake" @fetch-data="fetchData"></remake>
    <group ref="group" @serviceData="getGroup" @fetch-data="fetchData"></group>
    <children ref="children" @categoryData="getCategoryData" @fetch-data="fetchData"></children>
	</div>
</template>

<script>
  import {
    toXieChaList,
    exportEventExcel,
    getSourceMap,
    getRequestTypeMap,
    getEventImpactMap,
    doDelete
  } from '@/api/eventAllManage'
  import {getAllEventFirst, getAllTypeInfo, restore} from '@/api/event'
	import user from '@/components/UserSelectModel'
	import Bumen from '@/components/bumenTree'
  import Rolesview from '@/components/UserSelectModel'
	import Remake from '@/components/eventRemake'
  import Group from '@/components/serviceGroup'
  import Children from '@/components/gongdanclass'
  import permission from "@/directive/permission";

	export default {
		name: 'map1',
    directives: {permission},
		components: {
      user,
			Rolesview,
			Bumen,
			Remake,
      Group,
      Children
		},
		data() {
			return {
        selectType: '',
				value: '',
				value1: '',
				checked: false,
				isShow: false,
				list: null,
				listLoading: true,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
          service_groups: '',
          service_groups_name: '',
          user_id: '',
          create_user: '',
          handler: '',
          response_user: '',
          department: '',
          id: '',
          status: '',
          event_category_id: '',
          event_category_name: '',
          event_source_id: '',
          event_source_name: '',
          expenses: '',
          create_start_time: '',
          create_end_time: '',
				},
        sourceList: [],
        firstList: [],
			}
		},
		created() {
			this.fetchData();
      this.getSourceData();
		},
		methods: {
      // 工单类别查询
      handleView() {
        this.$refs['children'].showWin()
      },
      // 部门列表
      handlebumen() {
        this.$refs['bumen'].showWin()
      },
      // 服务群组查询
      handlegroup() {
        this.$refs['group'].showWin()
      },
      // 用户查询
      handleUser() {
        this.selectType = 'user_id';
        this.$refs['user'].showWin()
      },
      // 开单人查询
      handleCreate() {
        this.selectType = 'create_user';
        this.$refs['user'].showWin()
      },
      // 处理人查询
      handleModel() {
        this.selectType = 'handler';
        this.$refs['user'].showWin()
      },
      // 响应人查询
      handleResponse() {
        this.selectType = 'response';
        this.$refs['user'].showWin()
      },
      // 得到工单类别
      getCategoryData(row) {
        this.queryForm.event_category_name = row.name;
        this.queryForm.event_category_id = row.id
      },
      // 部门数据梳理
      getDept(row) {
        this.queryForm.department = row.id;
        this.queryForm.department_name = row.name;
      },
      // 服务群组回显
      getGroup(row) {
        this.queryForm.service_groups = row.id;
        this.queryForm.service_groups_name = row.name;
      },
      //用户选择，得到用户
      getUser(row) {
        switch (this.selectType) {
          case 'user_id':
            this.queryForm.user_id = row.id;
            this.queryForm.user_id_name = row.name;
            break
          case 'create_user':
            this.queryForm.create_user = row.id;
            this.queryForm.create_user_name = row.name;
            break
          case 'handler':
            this.queryForm.handler = row.id;
            this.queryForm.handler_name = row.name;
            break
          case 'response':
            this.queryForm.response_user = row.id;
            this.queryForm.response_user_name = row.name;
            break
          case  'response_user':
            this.queryForm.response_user = row.id;
            this.queryForm.response_user_name = row.name;
          default:
            break
        }
      },
      // 查看详情
      handlesee(row) {
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/thing/wholeview',
            query: { id: row.id },
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 备注操作
      handleRemake(row) {
        if (row.id != '' && row.id != null) {
          row.remark = "";
          this.$refs['remake'].showEdit(row)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },

			handleCurrentChange(val) {
				this.selectRows = val
			},

			handleRole() {
        this.selectType = 'user_id';
				this.$refs['user'].showWin()
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
        if(this.checked) {
          this.queryForm.status = '6'
        }else {
          this.queryForm.status = ''
        }
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			moreQuery() {
				this.isShow = !this.isShow
			},
      // 初始化查询
			async fetchData() {
				this.listLoading = true
				const {data, totalCount} = await toXieChaList(this.queryForm)
        this.list = data.list
        this.total = data.total
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
      // 来源列表查询
      async getSourceData() {
        var param = {};
        const {data} = await getSourceMap(param)
        this.sourceList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      // 优先级列表查询
      async getAllEventFirst() {
        const {data} = await getAllEventFirst()
        this.firstList = data;
      },
		},
	}
</script>

<style lang="scss" scoped>
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
</style>
