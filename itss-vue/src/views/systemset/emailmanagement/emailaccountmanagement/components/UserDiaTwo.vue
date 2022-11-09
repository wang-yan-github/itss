<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.username"
              placeholder="请输入用户名"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="title" placeholder="请选择">
              <el-option label="是" value="shanghai"></el-option>
              <el-option label="否" value="beijing"></el-option>
            </el-select>
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
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"
      height="250"
    >
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
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
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="state"
        label="状态"
        sortable
        align="center"
      ></el-table-column>

      <el-table-column show-overflow-tooltip prop="fuzeren" label="负责人" align="center">
      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="gongchengshi"
        label="工程师"
        align="center"
      ></el-table-column>
      <el-table-column prop="isac" show-overflow-tooltip label="处理人为空自动指定工程师" width="200" align="center">

      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="paixu"
        label="排序"
        align="center"
        sortable
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
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>

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
          username: '',
        },
        tableData: [{
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }, {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',

        }],
      }
    },
    created() {
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin() {
        this.title = '添加处理服务组'
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
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.close();
      },
    },
  }
</script>
