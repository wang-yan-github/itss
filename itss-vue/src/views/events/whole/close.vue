<template>
  <div class="index-container">
    <!-- <div class="help">
      <vab-icon :icon="['fas', 'question-circle']" @click="openhelp"></vab-icon>
    </div> -->
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
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item >
              <el-input v-model.trim="queryForm.user_id_name" placeholder="用户" clearable @clear="queryForm.user_id = ''" @focus="handleUserId">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleUserId"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.create_user_name" placeholder="开单人" clearable @clear="queryForm.create_user = ''" @focus="handleCreateUser">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleCreateUser"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.solve_user_name" placeholder="解决工程师" clearable @clear="queryForm.solve_user_id = ''" @focus="handleSolveUser">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleSolveUser"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.handler_name" placeholder="处理人/关单人" clearable @clear="queryForm.handler = ''" @focus="handleHandlerUser1">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleHandlerUser1"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.response_user_name" placeholder="响应人" clearable @clear="queryForm.response_user = ''" @focus="handleResponseUser">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleResponseUser"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.service_groups_name" placeholder="服务群组" clearable @clear="queryForm.service_groups_id = ''" @focus="handlegroup">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handlegroup"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.department_name" placeholder="部门" clearable @clear="queryForm.department = ''" @focus="handlebumen">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handlebumen"></el-button>-->
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
              <el-input v-model.trim="queryForm.event_category_name" placeholder="工单类别" clearable @clear="queryForm.event_category_id = ''" @focus="handleView">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleView"></el-button>-->
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
              <el-input v-model.trim="queryForm.programme_type_name" placeholder="方案分类" clearable @clear="queryForm.programme_type = ''" @focus="handleProgramme">
                <!--                <el-button slot="append" icon="el-icon-search" @click="handleProgramme"></el-button>-->
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
          <!--          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">-->
          <!--            <el-form-item>-->
          <!--              <el-date-picker v-model="queryForm.closeStartTime" value-format="yyyy-MM-dd" type="date" placeholder="关单时间(Start)" clearable></el-date-picker>-->
          <!--            </el-form-item>-->
          <!--          </el-col>-->
          <!--          <el-col :xs="24" :sm="12" :md="3" :lg="4" :xl="3">-->
          <!--            <el-form-item>-->
          <!--              <el-date-picker v-model="queryForm.closeEndTime" value-format="yyyy-MM-dd" type="date" placeholder="关单时间(End)" clearable></el-date-picker>-->
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
              <el-input v-model.trim="queryForm.expenses_end" type="number"  placeholder="费用（到）" clearable/>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button
            icon="el-icon-close"
            type="primary"
            @click="handleCloseWork"
          >
            批量关单
          </el-button>

        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-date-picker v-model="queryForm.create_time_start" placeholder="开单时间(Start)" value-format="yyyy-MM-dd" type="date" clearable style="width:150px!important"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker v-model="queryForm.create_time_end" placeholder="开单时间(End)" value-format="yyyy-MM-dd" type="date" clearable style="width:150px!important"></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.title" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-arrow-down" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>
    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
      @selection-change="setSelectRows"
    >
      <el-table-column show-overflow-tooltip type="selection"></el-table-column>
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="title"  label="标题" align="center">
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="handler_name" label="当前操作人" align="center" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" >
        <template #default="{ row }">
          <el-rate v-model="row.event_satisfaction" disabled>
          </el-rate>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>

    <bumen ref="bumen" @deptData="getDept" @fetch-data="fetchData"></bumen>
    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <group ref="group" @serviceData="getGroup" @fetch-data="fetchData"></group>
    <children ref="children" @categoryData="getCategoryData" @fetch-data=""></children>
    <dialogone ref="dialogone" @programmeTypeData="getProgrammeTypeData" @fetch-data=""></dialogone>
  </div>
</template>

<script>
  import {getList, closingBatch, getSourceMap, getRequestTypeMap} from '@/api/eventAllManage'
  import {getAllEventFirst, getAllTypeInfo} from '@/api/event'

  import Bumen from '@/components/bumenTree'
  import Group from '@/components/serviceGroup'
  import Users from '@/components/UserSelectModel'
  import Children from '@/components/gongdanclass'
  import Dialogone from '@/components/fanganclass'

  // 批量关单
  export default {
    name: 'wholeClose',
    components: {Bumen, Group, Users, Children, Dialogone},
    data() {
      return {
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
          create_time_start: '',
          create_time_end: '',
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
        firstList: [],
        typeList: [],
      }
    },
    created() {
      this.fetchData();
      this.getSourceData();
      this.getRequestTypeMap();
      // this.getEventImpactMap();
      // this.getUrgencyDegreeMap();
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
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      openhelp() {
        this.$router.push({
          path: '/help/index',
        })
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
      handlegroup() {
        this.$refs['group'].showWin()
      },
      handlebumen() {
        this.$refs['bumen'].showWin()
      },
      handleView() {
        this.$refs['children'].showWin()
      },
      handleProgramme() {
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
          default:
            break
        }
      },
      getGroup(row) {
        this.queryForm.service_groups = row.id;
        this.queryForm.service_groups_id = row.id
        this.queryForm.service_groups_name = row.name;
      },
      getDept(row) {
        this.queryForm.department = row.id;
        this.queryForm.department_name = row.name;
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
      // async getEventImpactMap() {
      //   var param = {};
      //   const {data} = await getEventImpactMap(param)
      //   this.eventImpactList = data
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 300)
      // },
      // async getUrgencyDegreeMap() {
      //   var param = {};
      //   const {data} = await getEventImpactMap(param)
      //   this.urgencyDegreeList = data
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 300)
      // },
      async getAllEventFirst() {
        const {data} = await getAllEventFirst()
        this.firstList = data;
      },
      async getAllTypeInfo() {
        const {data} = await getAllTypeInfo()
        this.typeList = data;
      },
      getCategoryData(row) {
        this.queryForm.event_category_name = row.name;
        this.queryForm.event_category_id = row.id
      },
      getProgrammeTypeData(row) {
        this.queryForm.programme_type_name = row.name;
        this.queryForm.programme_type = row.id
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      handleCloseWork() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = new Array();
          this.selectRows.forEach((item, i) => {
            ids.push(item.id)
          })
          this.$baseConfirm('你确定要关闭选中项吗', null, async () => {
            const {msg} = await closingBatch({'ids': ids})
            this.$baseMessage(msg, 'success')
            this.fetchData();
            this.closeall();
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
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await getList(this.queryForm)
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
