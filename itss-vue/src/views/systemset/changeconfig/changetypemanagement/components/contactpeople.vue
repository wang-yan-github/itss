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
              v-model.trim="queryForm.username"
              placeholder="请输入用户名"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="value1" placeholder="请选择">
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
        prop="user_name"
        label="姓名"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="companyName"
        label="联系人公司"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="departmentName"
        label="部门"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="mailbox"
        label="EMAIL"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="address"
        label="联系地址"
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
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getList,} from '@/api/userManagement'

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
        selectRows: [],
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableData: [
          //   {
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // },{
          //   id:'10001',
          //   username:'王小虎',
          //   state:'使用',
          //   fuzeren: '王大虎',
          //   gongchengshi:'工程师',
          //   isac:'是',
          //   paixu: '1',

          // }
        ],
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
        this.title = '查询'
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
        var that = this;
        that.$emit('deptData', this.selectRows)
        this.dialogFormVisible = false
      },
      save() {
        this.close();
      },
      async fetchData() {
        console.log(this.queryForm);
        this.listLoading = true
        const {data} = await getList(this.queryForm)
        this.tableData = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
