<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @current-change="setSelectRows"
      height="250"
    >
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        width="60"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="option_type"
        label="操作类型"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="user_name"
        label="操作人"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="option_date"
        label="操作时间"
      ></el-table-column>
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
  </el-dialog>
</template>

<script>
import { historyPage } from '@/api/assetsManage'
export default {
  name: 'diaTwo',
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
        assets_id:''
      },
      tableData: [ ],
      list: null,
      row: {},
    }
  },
  created() {
    // this.fetchData();
  },
  methods: {
    showWin(id) {
      this.queryForm.assets_id = id;
      this.title = '历史记录'
      this.dialogFormVisible = true
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
     handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    close() {
      this.dialogFormVisible = false
    },
    async fetchData() {
      console.log(this.queryForm);
      this.listLoading = true
      const { data, totalCount } = await historyPage(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
