<!--选择用户 单选-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.user_name"
              placeholder="请输入用户名"
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
      v-loading="listLoading"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @current-change="setSelectRows"
    >
      <el-table-column
        show-overflow-tooltip
        type="index"
        label="序号"
        width="70px;"
        align="center"
      >
        <template slot-scope="scope">
          {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="user_name"
        label="用户名"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="name"
        label="姓名"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="departmentName"
        label="部门"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="telephone"
        label="电话"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="mailbox"
        label="邮箱"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        prop="status"
        label="状态"
      >
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status == '1'">活动</el-tag>
          <el-tag type="warning" v-else-if="scope.row.status == '2'">
            暂停
          </el-tag>
          <el-tag type="danger" v-else>已禁止</el-tag>
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
  </el-dialog>
</template>

<script>
import { getList, getGroupPage } from '@/api/userManagement'

export default {
  name: 'UserSelectModel',
  data() {
    return {
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
        username: '',
        groupType: '',
        ids: [],
      },
      tableData: [],
      list: null,
      row: {},
      group_id: '',
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    showWin(data) {
      console.log("data:", data)
      this.title = '用户列表'
      this.dialogFormVisible = true
      this.group_id = data.group_id
      this.queryForm.groupType = data.type
      this.queryForm.ids = data.ids
      this.fetchData()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    close() {
      // this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      if (this.selectRows != '' && this.selectRows != null) {
        var that = this
        // eslint-disable-next-line vue/custom-event-name-casing
        that.$emit('userData', that.selectRows)
        // eslint-disable-next-line vue/custom-event-name-casing
        that.$emit('listData', that.selectRows)
        this.close()
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    async fetchData() {
      this.listLoading = true
      // 判断group_id是否为空
      if (this.group_id != '' && this.group_id != undefined) {
        this.queryForm.eventServiceGroupId = this.group_id
        const res = await getGroupPage(this.queryForm)
        this.list = res.data.list
        this.total = res.data.total
      } else {
        const res = await getList(this.queryForm)
        this.list = res.data.list
        this.total = res.data.total
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
