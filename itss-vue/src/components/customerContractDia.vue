<!--客户合同-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" append-to-body width="1000px" @close="close">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.customer_name" placeholder="客户" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.deptName" placeholder="部门" clearable @clear="queryForm.dept_id=''" @click.native="openbumen">
              <!--<el-button slot="append" icon="el-icon-search" @click="openbumen"></el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.status" placeholder="状态" clearable>
               <el-option>请选择</el-option>
              <el-option label="执行中" value="0"></el-option>
              <el-option label="已结束" value="1"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-input v-model.trim="queryForm.contract_name" placeholder="合同名称" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">检索</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>

    </vab-query-form>
    <el-table v-loading="listLoading" :data="tableData" border highlight-current-row ref="singleTable" @current-change="setSelectRows"
              :element-loading-text="elementLoadingText" height="250">
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="55px;"></el-table-column>
<!--      <el-table-column show-overflow-tooltip prop="id" label="编码" hidden="true" align="center"></el-table-column>-->
      <el-table-column show-overflow-tooltip prop="customer_name" align="center" label="客户名称"></el-table-column>
      <el-table-column show-overflow-tooltip prop="contract_name" align="center" label="合同名称"></el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" align="center" label="部门"></el-table-column>
      <el-table-column show-overflow-tooltip prop="renew_time" align="center" label="续约提醒时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" align="center" label="开始时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="end_time" align="center" label="结束时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="amount" align="center" label="金额"></el-table-column>
      <el-table-column show-overflow-tooltip prop="currency" align="center" label="币种" width="80px;">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status==0">人民币</el-tag>
          <el-tag type="warning" v-else>美元</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="status" align="center" label="状态" width="80px;">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status==0">执行中</el-tag>
          <el-tag type="warning" v-else>已结束</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
    <children ref="children" @deptData="deptset" @fetch-data=" { bumen }"></children>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getList} from '@/api/assetsCustomerContract'
  import Children from '@/components/bumenTree'

  export default {
    name: 'customerContractDia',
    components: {Children},
    data() {
      return {
        bumen: '',
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
          customer_name: '',
          dept_id: '',
          deptName: '',
          status: '',
          contract_name: '',
        },
        tableData: [],
        data: [],
      }
    },
    created() {
      this.fetchData();
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin() {
        this.title = '添加客户合同';
        this.dialogFormVisible = true
      },
      setSelectRows(val) {
        console.log(val)
        this.selectRows = val;
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val;
        this.fetchData()
      },
      openbumen() {
        this.$refs['children'].showWin()
      },
      deptset(row) {
        this.queryForm.dept_id = row.id;
        this.queryForm.deptName = row.name;
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false;
      },
      save() {
        var that = this;
        // for (var i = 0; i < that.selectRows.length; i++) {
        //   var flag = that.data.every(function (val, index) {
        //     return val != that.selectRows[i];
        //   });
        //   if (flag) {
        //     that.data.push(that.selectRows[i]);
        //   }
        // }
        console.log(that.selectRows)
        that.$emit('listData', that.selectRows);
        this.close()
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getList(this.queryForm);
        this.tableData = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
