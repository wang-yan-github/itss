<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
        <el-button icon="el-icon-bell" @click="handleView">通知配置</el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.customer_name" placeholder="客户" clearable
                      @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.deptName" placeholder="部门" @clear="queryForm.dept_id=''" clearable
                      @focus="openbumen">
              <!--<el-button slot="append" icon="el-icon-search" @click="openbumen"></el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.status" placeholder="状态" clearable>
              <el-option label="执行中" value="0"></el-option>
              <el-option label="已结束" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.contract_name" placeholder="合同名称" clearable
                      @keyup.enter.native="queryData"/>
          </el-form-item>
          <!--          <el-form-item>-->
          <!--            <el-checkbox v-model="checked" @change="handlerWithMe">待续费</el-checkbox>-->
          <!--          </el-form-item>-->
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              :cell-style="isRed" highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="customer_name" sortable header-align="center" label="客户名称"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="contract_name" sortable label="合同名称" header-align="center"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" width="120px;" label="部门" sortable
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="renew_time" align="center" label="续约提醒时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" align="center" label="开始时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="end_time" align="center" label="结束时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="amount" align="center" label="金额"></el-table-column>
      <el-table-column show-overflow-tooltip prop="costing_threshold" align="center" label="成本阈值"></el-table-column>
      <el-table-column show-overflow-tooltip prop="amount_count" align="center" label="累计成本"></el-table-column>
      <el-table-column show-overflow-tooltip prop="currency" align="center" label="币种">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.currency == '0'">人民币</el-tag>
          <el-tag type="warning" v-else>美元</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="status" align="center" sortable label="状态">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status == '0'">执行中</el-tag>
          <el-tag type="danger" v-else>已结束</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="sla_event_name" align="center" sortable
                       label="事件SLA"></el-table-column>
      <el-table-column show-overflow-tooltip prop="sla_question_name" align="center" sortable
                       label="问题SLA"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total"
                   @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
    <children ref="children" @deptData="deptset"></children>
  </div>
</template>

<script>
  import {getList, doDelete} from '@/api/assetsCustomerContract'
  import Children from '@/components/bumenTree'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'contractcustommanagement',
    components: {Edit, Views, Children},
    data() {
      return {
        bumen: '',
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
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
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      isRed({row}) {
        let styleRes = {
            backgroundColor: "#DE6"
        }
        if (parseFloat(row.amount_count) > parseFloat(row.costing_threshold)) {
          console.log(row);
          return styleRes;
        }
      },
      handlerWithMe(val) {//勾选待我处理，处理人自动变成当前登陆user
        if (val) {//选中
          this.queryForm.status = "2";
        } else {
          this.queryForm.status = ""//状态
        }
        this.fetchData();
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      openbumen() {
        this.$refs['children'].showWin()
      },
      handleView() {
        this.$refs['views'].showView()
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const id = this.selectRows.id;
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({id});
            this.$baseMessage(msg, 'success');

            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
            const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
            this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
            this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val;
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      deptset(row) {
        this.queryForm.dept_id = row.id;
        this.queryForm.deptName = row.name;
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getList(this.queryForm);
        this.list = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
<style lang="scss" scoped>
  .index-container {
    .hiddenmore {
      display: inline;

      ::v-deep {
        .el-input {
          width: 130px;
        }
      }
    }
  }
</style>
