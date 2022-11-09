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
        prop="name"
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="description"
        label="描述"
        sortable
        align="center"
      ></el-table-column>
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
    <children
      ref="children"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>

  </el-dialog>
</template>

<script>
  import {selectAllRoleByPage} from '@/api/userManagement'
  import Children from '@/components/bumenTree'

  export default {
    name: 'diaTwo',
    components: {Children},
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
        tableData: [],
      }
    },
    created() {
      setTimeout(() => {
        this.fetchData()
        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin() {
        this.title = '添加可见角色'
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
      openbumen() {
        this.$refs['children'].showWin()
      },
      opentakeman() {
        this.$refs['takeman'].showWin()
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
        var that = this;
        that.$emit('deptData', this.selectRows)
        this.close()
      },
      async fetchData() {
        console.log(this.queryForm);
        this.listLoading = true
        const {data} = await selectAllRoleByPage(this.queryForm)
        this.tableData = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
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
