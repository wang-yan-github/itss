<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="900px" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.user_name" placeholder="请输入用户名" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">检索</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>

    </vab-query-form>
    <el-table v-loading="listLoading" ref="multipleTable" :data="tableData" border highlight-current-row
              :element-loading-text="elementLoadingText" @select="selectRow"
      @select-all="selectRowAll" height="250">
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;">
          <template slot-scope="scope">
          {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="companyName" label="联系人公司" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="mailbox" label="EMAIL" align="center"
                       width="200px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="address" label="联系地址" align="center"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total"
                   @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

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
        selectRows: '',
        data: [],
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableData: [],
        tableData1: [],
        events:[]
      }
    },
    created() {
      setTimeout(() => {

        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin(events) {
        this.title = '查询'
         if (undefined != events) {
          this.events = [] //清空之前保存的数组
          events.forEach((row) => {
            this.events.push(row.id)
          })
        }
        this.fetchData()
        this.dialogFormVisible = true
      },
      selectRow(val, row) {
         //如果已选列表为空则一定是取消选中
        if (!val) {
          for (var i = 0; i < this.events.length; i++) {
            if (this.events[i] == row.id) {
              this.events.splice(i, 1)
            }
          }
          for (var i = 0; i < this.data.length; i++) {
            if (this.data[i].id == row.id) {
              this.data.splice(i, 1)
            }
          }
        } else {
          //如果已选列表中存在选中当前行的id，则为选中操作
          var flag = val.some((e, i) => {
            if (e.id == row.id) {
              return true
            }
          })
          if (flag) {
            this.events.push(row.id)
            this.data.push(row)
          } else {
            for (var i = 0; i < this.events.length; i++) {
              if (this.events[i] == row.id) {
                this.events.splice(i, 1)
              }
            }
            for (var i = 0; i < this.data.length; i++) {
              if (this.data[i].id == row.id) {
                this.data.splice(i, 1)
              }
            }
          }
        }
      },
      selectRowAll(row){
        var that = this;
        row.forEach((a) => {
          that.events.push(a.id);
          that.data.push(a);
        })
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
        this.data = [];
        this.dialogFormVisible = false
      },
      save() {
        var that =this;
        this.$emit('deptData',  that.data);
        this.close();
      },
      async fetchData() {
        this.listLoading = true
        const {data} = await getList(this.queryForm)
        this.tableData = data.list
        this.total = data.total

        //默认回显数值
        if (this.events.length > 0) {
          this.$nextTick(function () {
            this.tableData.forEach((row) => {
              if (this.events.includes(row.id)) {
                this.$refs.multipleTable.toggleRowSelection(row, true)
              }
            })
          })
        }
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
