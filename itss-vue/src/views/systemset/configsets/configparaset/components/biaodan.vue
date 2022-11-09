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
              v-model.trim="queryForm.name"
              placeholder="请输入名称"
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
      :data="tableData"
      border
      highlight-current-row
      @current-change="handleCurrentChange1"
      :element-loading-text="elementLoadingText"
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
        prop="name"
        label="姓名"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="type"
        label="类型"
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
  </el-dialog>
</template>

<script>

  import {getAllFormList} from '@/api/assetsConfigParams'

  export default {
    name: 'diaTwo',
    data() {
      return {
        selectRadio: null,
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
          name: '',
        },
        tableData: [],
        currentRow: null,
      }
    },
    created() {
      setTimeout(() => {
        this.fetchData()
        this.listLoading = false
      }, 300)
    },
    methods: {
      handleCurrentChange1(val) {
        this.currentRow = val;
      },
      showWin() {
        this.title = '选择表单'
        this.dialogFormVisible = true
      },
      getTemplateRow(item) {
        // ... 拿到选中的表格数据
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
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this;
        that.$emit('deptData', this.currentRow)
        this.close();
      },

      async fetchData() {
        const {data} = await getAllFormList(this.queryForm)
        this.tableData = data.list
        this.total = data.total
      },
    },
  }
</script>
