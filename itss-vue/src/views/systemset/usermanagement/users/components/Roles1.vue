<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-table
      :data="tableData"
      ref="itemTable"
      border
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"

      height="400"
      style="margin-top: 20px"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        width="60px;"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        sortable
        width="200px"
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="description"
        label="描述"
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
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    selectAllRoleByPage,
    addUserRole,
    getOneUser,
    getRoleIdByUser,
  } from '@/api/userManagement'

  export default {
    name: 'userroles',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        total1: 0,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageIndex: 1,
          pageSize: 10,
          name: '',
        },
        tableData: [],
        tableData1: [],
        form: {
          id: '',
          user_name: '',
          name: '',
          status: '',
          user_type: '',
          sysRoles: [],
        },
      }
    },
    created() {
    },
    methods: {
      showWin(row) {
        this.title = '设置角色'
        this.dialogFormVisible = true
        if (row) {
          this.tableData1 = row
        }
        this.getAllRole();
        // this.getOneRole();
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.getAllRole()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.getAllRole()
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val
        this.getAllRole()
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.tableData = [];
        this.dialogFormVisible = false
      },
      save() {
        console.log(this.selectRows);
        var that = this;
        that.$emit('listData', that.selectRows)
        this.close()
      },

      async getAllRole() {
        // this.listLoading = true
        const {data} = await selectAllRoleByPage(this.queryForm)

        this.tableData = data.list
        this.total = data.total
        console.log(this.tableData);
        console.log(this.tableData1);
        this.$nextTick(() => {
          for (let i = 0; i < this.tableData.length; i++) {
            for (let j = 0; j <this.tableData1.length ; j++) {
              //两个数组做比对,选中的做勾选
              if(this.tableData1[j].id==this.tableData[i].id) {
                this.$refs.itemTable.toggleRowSelection(this.tableData[i],true);
              }
            }
          }

        });

        // this.listLoading = false

      },




    },
  }
</script>
