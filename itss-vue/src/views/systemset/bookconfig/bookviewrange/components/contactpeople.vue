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

      ref="itemTable"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @select="setSelectRows"
      height="250"
    >
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="序号"
        width="80px"
        sortable
        align="center"
      >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>

      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        width="150px"
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
  </el-dialog>
</template>

<script>
  import {selectAllRoleByPage} from '@/api/userManagement'

  export default {
    name: 'diaTwo',
    data() {
      return {
        list: null,
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
        data: [],
        tableData1: [],
      }
    },
    created() {
      // this.fetchData();
    },
    methods: {
      showWin(row) {
        this.title = '可查看范围'
        if(row){
          this.tableData1 = row
        }
        this.fetchData();
        this.dialogFormVisible = true
      },
      setSelectRows(val,row) {
        // this.selectRows = val
        if (!val) {
          for (var i = 0; i < this.tableData1.length; i++) {
            if (this.tableData1[i].id == row.id) {
              this.tableData1.splice(i, 1)
            }
          }
        }else {

          var flag = true;
          for (var i = 0; i < this.tableData1.length; i++){
            if (row.id == this.tableData1[i].id){
              flag = false;
            }
          }

          if (flag) {
            this.tableData1.push(row)
          } else {
            for (var i = 0; i < this.tableData1.length; i++) {
              if (this.tableData1[i].id == row.id) {
                this.tableData1.splice(i, 1)
              }
            }
          }
        }
        console.log(this.tableData1);
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
        this.list = [];
        this.tableData1 = [];
        this.dialogFormVisible = false
      },
      save() {
        var that = this;
        // for (var i = 0; i < that.selectRows.length; i++) {
        //   var flag = that.data.every(function (val, index) {
        //     return val != that.selectRows[i];
        //   })
        //   if (flag) {
        //     that.data.push(that.selectRows[i]);
        //   }
        // }
        console.log(that.tableData1)
        that.$emit('listData', that.tableData1)
        // that.data = [];
        this.close();
      },
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await selectAllRoleByPage(this.queryForm)
        console.log(data);
        this.list = data.list
        this.total = data.total

        if (this.tableData1 != null && this.tableData1 != [] && this.tableData1 != undefined){
          this.$nextTick(() => {
            for (let i = 0; i < this.list.length; i++) {
              for (let j = 0; j <this.tableData1.length ; j++) {
                //两个数组做比对,选中的做勾选
                if(this.tableData1[j].id == this.list[i].id) {
                  this.$refs.itemTable.toggleRowSelection(this.list[i],true);
                }
              }
            }

          });
        }



        this.listLoading = false
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 300)
      },
    },
  }
</script>
