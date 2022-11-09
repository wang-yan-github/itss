<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
    append-to-body
  >
    <el-table
      v-loading="listLoading"
      :data="tableData"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"
      height="250"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        width="60"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="username"
        label="模板名称"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="state"
        label="创建人"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="state"
        label="创建时间"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="state"
        label="选择"
        sortable
        align="center"
      >
        <template #default="{ row }">
          <el-button plain type="primary" size="mini" @click="prints(row)">
            打印
          </el-button>
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
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'diaTwo',
  data() {
    return {
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
        username: '',
      },
      tableData: [
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
      ],
    }
  },
  created() {
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  methods: {
    showWin() {
      this.title = '打印'
      this.dialogFormVisible = true
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
      // this.tableData
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    prints(row) {
      this.$router.push({
        path: '/printpaper',
        query: { id: row.id },
      })
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    close() {
      // this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.close()
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
