<!--公共 事件弹窗-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1200px"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <!-- <el-form-item>
            <el-input v-model.trim="queryForm.id" placeholder="编号" clearable @keyup.enter.native="queryData"/>
          </el-form-item> -->
          <el-form-item>
            <el-input
              v-model.trim="queryForm.user_id_name"
              placeholder="用户"
              @clear="queryForm.user_id = ''"
              clearable
              @focus="handleUserId"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleUserId"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.create_user_name"
              placeholder="开单人"
              clearable
              @clear="queryForm.create_user = ''"
              @focus="handleCreateUser"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleCreateUser"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.service_name"
              placeholder="服务群组"
              clearable
              @focus="handleService"
              @clear="queryForm.service_groups_id = ''"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleService"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.department_name"
              placeholder="部门"
              clearable
              @clear="queryForm.department = ''"
              @focus="handleDept"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleDept"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.event_category_name"
              placeholder="工单类别"
              clearable
              @clear="queryForm.event_category_id = ''"
              @focus="handleCategory"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleCategory"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.create_time_start"
              placeholder="开单时间(Start)"
              value-format="yyyy-MM-dd"
              type="date"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.create_time_end"
              placeholder="开单时间(End)"
              value-format="yyyy-MM-dd"
              type="date"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.keyword"
              placeholder="关键字"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>
    <el-table
      ref="multipleTable"
      v-loading="listLoading"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      :row-key="getRowKeys"
      @selection-change="selectionLineChangeHandle"
      height="500"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        :reserve-selection="true"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        type="index"
        label="序号"
        align="center"
        width="50px;"
      >
        <template slot-scope="scope">
          {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="title"
        sortable
        label="标题"
        align="center"
      >
        <!--<template #default="{ row }">-->
        <!--<el-link @click="handlesee(row)">{{row.title}}</el-link>-->
        <!--</template>-->
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="user_id_name"
        label="用户"
        align="center"
        width="70px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="category_name"
        label="工单类别"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        label="开单人"
        align="center"
        width="70px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        label="开单时间"
        align="center"
        width="100px;"
      >
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="service_name"
        label="服务群组"
        align="center"
        width="120px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="handler_name"
        label="处理人"
        align="center"
        width="70px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="ex_solve_time"
        align="center"
        label="解决时间"
        width="100px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="statusName"
        align="center"
        label="状态"
        width="70px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="event_satisfaction"
        align="center"
        label="用户满意度"
        width="180px;"
      >
        <template #default="{ row }">
          <el-rate v-model="row.event_satisfaction" disabled></el-rate>
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
      @current-change="handleCurrentChange"
    ></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>

    <!--定义 部门 窗口 数据回显方法-->
    <bumen ref="bumen" @deptData="getDept"></bumen>
    <!--定义 用户 窗口 数据回显方法-->
    <users ref="users" @userData="getUser"></users>
    <!--定义 工单类别 窗口 数据回显方法-->
    <category ref="category" @categoryData="getCategoryData"></category>
    <!--定义 服务群组 窗口 数据回显方法-->
    <serviceGroup
      ref="serviceGroup"
      @serviceData="getServiceData"
    ></serviceGroup>
  </el-dialog>
</template>

<script>
import { getList } from '@/api/eventAllManage'
//引用 公共 工单类型 弹窗
import Category from '@/components/gongdanclass'
//引用 公共 用户 弹窗
import Users from '@/components/UserSelectModel'
//引用 公共 部门 弹窗
import Bumen from '@/components/bumenTree'
//引用 服务群组 弹窗
import serviceGroup from '@/components/serviceGroup'

export default {
  name: 'eventDialog',
  components: { Category, Bumen, Users, serviceGroup },
  data() {
    return {
      texts: ['差', '一般', '好', '非常好', '非常棒'],
      input2: '',
      value1: '',
      title: '',
      dialogFormVisible: false,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      loading: false,
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        keyword: '',
        title: '',
        create_time_start: '',
        create_time_end: '',
        //高级查询
        id: '',
        status: '',
        statusName: '',
        user_id: '',
        user_id_name: '',
        create_user: '',
        create_user_name: '',
        solve_user_id: '',
        solve_user_name: '',
        handler: '',
        handler_name: '',
        service_groups_id: '',
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
        service_name: '',
        service_id: '',
        service_groups_type: '',
      },
      selectType: '',
      sourceList: [],
      requestTypeMapList: [],
      eventImpactList: [],
      urgencyDegreeList: [],
      list: [],
      tableData: [],
      data: [],
      events: [], //默认选中数据
      dataonLineListSelections: []
    }
  },
  created() {
    // this.fetchData();//不进行初始化，点击按钮时在进行获取数据
  },
  methods: {
    // 列表选择,val选中的表格行数据
    selectionLineChangeHandle (val) {
      this.dataonLineListSelections = val;
    },
    // 保存选中的数据id
    getRowKeys(row) {
      // id 是后台传递的每行信息唯一标识
      return row.id;
    },
    //打开 部门 弹窗
    handleDept() {
      this.$refs['bumen'].showWin()
    },
    //打开 工单类别 弹窗
    handleCategory() {
      this.$refs['category'].showWin()
    },
    //打开 用户 弹窗
    handleUserId() {
      this.selectType = 'user_id'
      this.$refs['users'].showWin()
    },
    //打开 开单人 弹窗
    handleCreateUser() {
      this.selectType = 'create_user'
      this.$refs['users'].showWin()
    },
    //打开服务群组弹窗
    handleService() {
      this.$refs['serviceGroup'].showWin()
    },
    //用户、开单人 数据回显
    getUser(row) {
      switch (this.selectType) {
        case 'user_id':
          this.queryForm.user_id = row.id
          this.queryForm.user_id_name = row.name
          break
        case 'create_user':
          this.queryForm.create_user = row.id
          this.queryForm.create_user_name = row.name
          break
        case 'solve_user':
          this.queryForm.solve_user_id = row.id
          this.queryForm.solve_user_name = row.name
          break
        case 'handler':
          this.queryForm.handler = row.id
          this.queryForm.handler_name = row.name
          break
        default:
          break
      }
    },
    //部门 数据回显
    getDept(row) {
      this.queryForm.department = row.id
      this.queryForm.department_name = row.name
    },
    //工单类别 数据回显
    getCategoryData(row) {
      this.queryForm.event_category_name = row.name
      this.queryForm.event_category_id = row.id
    },
    //服务群组 数据回显
    getServiceData(rows) {
      this.queryForm.service_name = rows.name
      this.queryForm.service_groups_id = rows.id //服务群组id
    },

    showWin(event, events, status) {
      //点击弹框按钮获取相关数据
      this.title = '添加关联工单'
      if (undefined != events) {
        this.events = [] //清空之前保存的数组
        events.forEach((row) => {
          this.events.push(row.id)
        })
      }
      if (null != event && undefined != event && '' != event) {
        this.queryForm.service_groups_type = event
      }
      this.dialogFormVisible = true
      this.fetchData(status)
    },

    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
      // this.tableData
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    close() {
      // this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.data = [];
      // 清空选择的值
      this.$refs.multipleTable.clearSelection()
      this.dialogFormVisible = false
    },
    save() {
      var that = this

      that.$emit('listData', this.dataonLineListSelections)
      this.close()
    },
    async fetchData(status) {
      this.listLoading = true
      if (status) {
        this.queryForm.status = status
      }

      const { data } = await getList(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
      //默认回显数值
      if (this.events.length > 0) {
        this.$nextTick(function () {
          this.list.forEach((row) => {
            if (this.events.includes(row.id)) {
              this.$refs.multipleTable.toggleRowSelection(row, true)
            }
          })
        })
      }
    },
    //然后定义函数parseDateFull的实现
    parseDateFull(time) {
      // 判断是否为空
      if (time == null || time == '') {
        return ''
      }
      var x = new Date(time)
      var z = {
        y: x.getFullYear(),
        M: x.getMonth() + 1,
        d: x.getDate(),
        h: x.getHours(),
        m: x.getMinutes(),
        s: x.getSeconds(),
      }
      if (z.M < 10) {
        z.M = '0' + z.M
      }
      if (z.d < 10) {
        z.d = '0' + z.d
      }
      if (z.h < 10) {
        z.h = '0' + z.h
      }
      if (z.m < 10) {
        z.m = '0' + z.m
      }
      // return z.y + "-" + z.M + "-" + z.d + " " + z.h + ":" + z.m ;
      return z.y + '-' + z.M + '-' + z.d
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .left-panel {
    .el-input {
      width: 200px !important;
    }
  }
}
</style>
