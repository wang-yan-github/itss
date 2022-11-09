<template>
  <div class="index-container">
    <!-- <div class="help">
      <vab-icon :icon="['fas', 'question-circle']" @click="openhelp"></vab-icon>
    </div> -->
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item><el-input v-model.trim="queryForm.id" placeholder="编号" clearable @keyup.enter.native="queryData"/></el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
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
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.user_id_name" placeholder="用户" clearable @focus="handleUserId"  @clear="queryForm.user_id = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleUserId"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.create_user_name" placeholder="开单人" clearable @focus="handleCreateUser" @clear="queryForm.create_user = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleCreateUser"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.solve_user_name" placeholder="解决工程师" clearable @focus="handleSolveUser" @clear="queryForm.solve_user_id = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleSolveUser"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.handler_name" placeholder="处理人/关单人" clearable @focus="handleHandlerUser1" @clear="queryForm.handler = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleHandlerUser1"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.response_user_name" placeholder="响应人" clearable @focus="handleResponseUser" @clear="queryForm.response_user = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleResponseUser"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.service_groups_name" placeholder="服务群组" clearable @focus="handlegroup" @clear="queryForm.service_groups_id = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handlegroup"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>       
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.department_name" placeholder="部门" clearable @focus="handlebumen" @clear="queryForm.department = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handlebumen"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
            <el-select v-model="queryForm.source_id" placeholder="来源" class="w" clearable>
              <el-option v-for="(item,index) in sourceList" :key="index" :label="item.resource_name" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
            <el-select v-model="queryForm.request_type" placeholder="请求类型" class="w" clearable>
              <el-option v-for="(item,index) in requestTypeMapList" :key="index" :label="item.request_type_name" :value="item.id"></el-option>
            </el-select>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.event_category_name" placeholder="工单类别" clearable @focus="handleView" @clear="queryForm.event_category_id = ''">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleView"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.priority_id" placeholder="优先级" class="w" clearable>
                <el-option v-for="(item,index) in firstList" :key="index" :label="item.first_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.event_satisfaction" placeholder="用户满意度" class="w" clearable>
                <el-option label="1星" value="1"></el-option>
                <el-option label="2星" value="2"></el-option>
                <el-option label="3星" value="3"></el-option>
                <el-option label="4星" value="4"></el-option>
                <el-option label="5星" value="5"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.programme_type_name" placeholder="方案分类" clearable @focus="handleProgramme" @clear="queryForm.programme_type = ''" >
                <!--<el-button slot="append" icon="el-icon-search" @click="handleProgramme"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.meetResponseSLA" placeholder="符合响应SLA" class="w" clearable>
                <el-option label="是" value="1"></el-option>
                <el-option label="否" value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
<!--           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">-->
<!--            <el-form-item>-->
<!--              <el-date-picker v-model="queryForm.closeStartTime" type="date" placeholder="关单时间(Start)" clearable></el-date-picker>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">-->
<!--            <el-form-item>-->
<!--              <el-date-picker v-model="queryForm.closeEndTime" type="date" placeholder="关单时间(End)" clearable></el-date-picker>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.solve_type" placeholder="解决方式" clearable>
                <el-option v-for="(item,index) in typeList" :key="index" :label="item.solution_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.expenses_start" type="number" placeholder="费用（从）" clearable/>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.expenses_end" type="number" placeholder="费用（到）" clearable/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="6">
          <el-button v-permission="event_workbench_zunei_beizhu" icon="el-icon-edit-outline" type="primary" @click="handleAdd">备注</el-button>
          <el-button v-permission="event_workbench_zunei_zhishi" icon="el-icon-files" type="success" @click="handleKnow">生成知识</el-button>
          <span>&nbsp;</span>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="18">
          <el-form-item>
            <el-date-picker v-model="queryForm.create_time_start" placeholder="开单时间(Start)" value-format="yyyy-MM-dd" type="date" clearable style="width:150px!important;"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker v-model="queryForm.create_time_end" placeholder="开单时间(End)" value-format="yyyy-MM-dd" type="date" clearable style="width:150px!important;"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.title" placeholder="关键字" clearable @keyup.enter.native="queryData" style="width:150px!important;" />
          </el-form-item>
          <el-form-item>
            <el-button v-permission="event_workbench_zunei_query" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-arrow-down" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>
    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange" height="calc(100vh - 300px)">
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="title" label="标题" align="left" header-align="left" min-width="300px">
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center" width="200px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" width="100px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center" width="100px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" width="180px;">
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseDateFull(scope.row.create_time) }}</span>-->
<!--        </template>-->
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="handler_name" label="当前操作人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间" width="130px;">
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.ex_solve_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" width="190px;">
        <template #default="{ row }">
          <el-rate v-model="row.event_satisfaction" disabled>
          </el-rate>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
    <remake ref="remake" @fetch-data="fetchData"></remake>
    <bumen ref="bumen" @deptData="getDept" @fetch-data="fetchData"></bumen>
    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <group ref="group" @serviceData="getGroup" @fetch-data="fetchData"></group>
    <children ref="children" @categoryData="getCategoryData" @fetch-data=""></children>
    <dialogone ref="dialogone" @programmeTypeData="getProgrammeTypeData" @fetch-data=""></dialogone>
    <pause ref="pause" @fetch-data="fetchData"></pause>
  </div>
</template>

<script>
  import {toMyGroupList, exportEventExcel, getSourceMap, getRequestTypeMap, getEventImpactMap, doDelete} from '@/api/eventAllManage'
  import {getAllEventFirst, getAllTypeInfo, restore} from '@/api/event'

  import Dialogone from '@/components/fanganclass'
  import Children from '@/components/gongdanclass'
  import Users from '@/components/UserSelectModel'
  import Bumen from '@/components/bumenTree'
  import Group from '@/components/serviceGroup'
  import Pause from '@/components/pause'

  import remake from './components/remake'
  import permission from "@/directive/permission";


  export default {
    name: 'groupthing1',
    directives: {permission},
    components: {
      Dialogone,
      Children,
      Bumen,
      remake,
      Users,
      Pause,
      Group
    },
    data() {
      return {
        event_workbench_zunei_zhishi: 'event_workbench_zunei_zhishi',
        event_workbench_zunei_beizhu: 'event_workbench_zunei_beizhu',
        event_workbench_zunei_query: 'event_workbench_zunei_query',
        texts: ['差', '一般', '好', '非常好', '非常棒'],
        firstList: [],
        typeList: [],
        input2: '',
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
          title: '',
          billStartTime: '',
          billEndTime: '',
          //高级查询
          id: '',
          status: '',
          user_id: '',
          user_id_name: '',
          create_user: '',
          create_user_name: '',
          solve_user_id: '',
          solve_user_name: '',
          response_user: '',
          response_user_name: '',
          handler: '',
          handler_name: '',
          service_groups_id: '',
          service_groups_name: '',
          department: '',
          department_name: '',
          source_id: '',
          request_type: '',
          event_category_id: '',
          event_category_name: '',
          priority_id: '',
          programme_type: '',
          programme_type_name: '',
          meetResponseSLA: '',
          expenses: '',
          solve_type: '',
          expenses_start: '',
          expenses_end: '',
          numberOfRestarts: '',
          closeTime: '',
          closeStartTime: '',
          closeEndTime: '',
          event_satisfaction: '',
        },
        selectType: '',
        sourceList: [],
        requestTypeMapList: [],
        eventImpactList: [],
        urgencyDegreeList: [],
      }
    },
    created() {
      this.fetchData();
      this.getSourceData();
      this.getRequestTypeMap();
      this.getEventImpactMap();
      this.getUrgencyDegreeMap();
      this.getAllEventFirst();
      this.getAllTypeInfo();
    },
    methods: {
      formatterStatus(row) {
        switch (row.status) {
          case '1':
            return '待响应';
          case '2':
            return '处理中';
          case '3':
            return '暂停';
          case '4':
            return '处理完成';
          case '5':
            return '待评价';
          case '6':
            return '已关闭';
          case '7':
            return '已撤销';
          default:
            return '';
        }
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      openhelp() {
        this.$router.push({
          path: '/help/index',
        })
      },
      handleCommand(command) {
        switch (command) {
          case 'a':
            this.handleKnow()
            break
          case 'b':
            //批量关单
            this.handleClose()
            break
          case 'c':
            //导入
            this.handleexport()
            break
          case 'd':
            this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
              this.exportEventExcel()
            })
            break
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      async exportEventExcel() {
        exportEventExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '全部事件导出.xls')
          document.body.appendChild(link)
          link.click()
        })
      },
      commandmoreValue(command, row) {
        return {
          "row": row,
          "command": command
        }
      },
      handleCommandmore(val) {
        var command = val.command;
        switch (command) {
          // case 'a':
          //   //备注
          //   this.$refs['remake'].showEdit();
          //   break;
          case 'b':
            // 撤销
            // this.$refs['revoke'].showEdit(val.row);
            break;
          case 'c':
            // 转单
            this.$router.push({
              path: '/thing/workingzd',
              query: {id: val.row.id},
            });
            break;
          case 'd':
            // 指派
            this.$router.push({
              path: '/thing/workingzhipai',
              query: {id: val.row.id},
            });
            break;
          case 'e':
            //暂停
            this.$router.push({
              path: this.$refs['pause'].showEdit(),
              query: {id: val.row.id},
            });
            break;
          case 'f':
            //恢复
            this.restore(val.row);
            break;
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      //恢复
      async restore(val) {
        const {data} = await restore(val);
        this.$baseMessage('成功', 'success');
        this.fetchData();
      },
      handleAdd() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['remake'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      //重开页面
      handleReopen(row) {
        // this.$refs['reopen'].showEdit(row)
      },
      //批量关单页面
      handleClose() {
        this.$router.push({
          path: '/thing/wholeClose',
        })
      },
      // 生成知识
      handleKnow() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/thing/createKnow',
            query: {bean: this.selectRows},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handlesee(row) {
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/thing/wholeview',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handlegroup() {
        this.$refs['group'].showWin()
      },
      handlebumen() {
        this.$refs['bumen'].showWin()
      },
      handleexport() {
        // this.$refs['userimport'].showWin()
      },
      handleView() {
        this.$refs['children'].showWin()
      },
      handleProgramme() {
        this.$refs['dialogone'].showWin()
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/thing/wholeEdit',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({'id': ids})
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
      handleUserId() {
        this.selectType = 'user_id';
        this.$refs['users'].showWin()
      },
      handleCreateUser() {
        this.selectType = 'create_user';
        this.$refs['users'].showWin()
      },
      handleSolveUser() {
        this.selectType = 'solve_user';
        this.$refs['users'].showWin()
      },
      handleResponseUser() {
        this.selectType = 'response_user';
        this.$refs['users'].showWin()
      },
      handleHandlerUser1() {
        this.selectType = 'handler';
        this.$refs['users'].showWin()
      },
      handlefa() {
        this.$refs['dialogone'].showWin()
      },
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
          case 'solve_user':
            this.queryForm.solve_user_id = row.id;
            this.queryForm.solve_user_name = row.name;
            break
          case 'handler':
            this.queryForm.handler = row.id;
            this.queryForm.handler_name = row.name;
            break
          case  'response_user':
            this.queryForm.response_user = row.id;
            this.queryForm.response_user_name = row.name;
            break
          default:
            break
        }
      },
      getGroup(row) {
        this.queryForm.service_groups = row.id;
        this.queryForm.service_groups_id = row.id;
        this.queryForm.service_groups_name = row.name;
      },
      getDept(row) {
        this.queryForm.department = row.id;
        this.queryForm.department_name = row.name;
      },
      getCategoryData(row) {
        this.queryForm.event_category_name = row.name;
        this.queryForm.event_category_id = row.id
      },
      getProgrammeTypeData(row) {
        this.queryForm.programme_type_name = row.name;
        this.queryForm.programme_type = row.id
      },
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await toMyGroupList(this.queryForm)
        this.list = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getSourceData() {
        var param = {};
        const {data} = await getSourceMap(param)
        this.sourceList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getRequestTypeMap() {
        var param = {};
        const {data} = await getRequestTypeMap(param)
        this.requestTypeMapList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getEventImpactMap() {
        var param = {};
        const {data} = await getEventImpactMap(param)
        this.eventImpactList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getUrgencyDegreeMap() {
        var param = {};
        const {data} = await getEventImpactMap(param)
        this.urgencyDegreeList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getAllEventFirst() {
        const {data} = await getAllEventFirst()
        this.firstList = data;
      },
      async getAllTypeInfo() {
        const {data} = await getAllTypeInfo()
        this.typeList = data;
      },
      //然后定义函数parseDateFull的实现
      parseDateFull(time) {
        // 判断是否为空
        if (time == null || time == '') {
          return ''
        }
        var x = new Date(time);
        var z = {
          y: x.getFullYear(),
          M: x.getMonth() + 1,
          d: x.getDate(),
          h: x.getHours(),
          m: x.getMinutes(),
          s: x.getSeconds(),
        };
        if (z.M < 10) {
          z.M = "0" + z.M;
        }
        if (z.d < 10) {
          z.d = "0" + z.d;
        }
        if (z.h < 10) {
          z.h = "0" + z.h;
        }
        if (z.m < 10) {
          z.m = "0" + z.m;
        }
        // return z.y + "-" + z.M + "-" + z.d + " " + z.h + ":" + z.m ;
        return z.y + "-" + z.M + "-" + z.d;
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
