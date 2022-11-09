<!--选择用户 多选-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" @close="close" append-to-body>
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
    <el-table v-loading="listLoading" :data="list" border highlight-current-row :element-loading-text="elementLoadingText" @selection-change="setSelectRows" >
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50px;">
          <template slot-scope="scope">
              {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
          </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" header-align="center" width="180px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status" label="状态" align="center">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.status == '1'">正常</el-tag>
          <el-tag size="small" type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getList} from '@/api/userManagement'

  export default {
    name: 'UsersDia',
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
          ids: [],
        },
        tableData: [],
        list: null,
        row: {},
        data: [],
      }
    },
    created() {

    },
    methods: {
      showWin(data) {
        this.title = '用户列表'
        this.dialogFormVisible = true;
        console.log("用户列表入参:", data)
        if(data){
          this.queryForm.ids = data.ids;
        }
        this.fetchData();
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
        // if (this.selectRows != '' && this.selectRows != null) {
          var that = this;
          // for (var i = 0; i < that.selectRows.length; i++) {
          //   var flag = that.data.every(function (val, index) {
          //     return val != that.selectRows[i];
          //   });
          //   if (flag) {
          //     that.data.push(that.selectRows[i]);
          //   }
          // }
        console.log(that.selectRows);
        that.$emit('listData', that.selectRows)
          this.close();
        // } else {
        //   this.$baseMessage('未选中任何行', 'error')
        //   return false
        // }

      },
      formatStatus(row, column, cellValue) {
        switch (cellValue) {
          case '1':
            return '活动'
          case '2':
            return '暂停'
          case '3':
            return '已禁用'
          default:
            return ''
        }
      },
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await getList(this.queryForm)
        this.list = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
