<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.supplier_name" placeholder="供应商" clearable
                      @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.deptName" placeholder="部门" @clear="queryForm.dept_id=''" clearable
                      @focus="openbumen">
              <!--<el-button slot="append" icon="el-icon-search" @click="openbumen"></el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-select v-model.trim="queryForm.status" class="w" clearable placeholder="状态">
              <el-option label="执行中" value="1"></el-option>
              <el-option label="已结束" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.contract_name" placeholder="合同名称" clearable
                      @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-checkbox v-model="checked">待续费</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="supplier_name" sortable label="供应商名称" header-align="center"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="contract_name" sortable label="合同名称" header-align="center"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" width="120px;" label="部门" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="renew_time" align="center" label="续约提醒时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" align="center" label="开始时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="end_time" align="center" label="结束时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="amount" align="center" label="金额"></el-table-column>
      <el-table-column show-overflow-tooltip prop="currency_name" align="center" label="币种"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态">
        <template slot-scope="scope">
          <el-tag size="small" type="danger" v-if="scope.row.status_name == '已结束'">已结束</el-tag>
          <el-tag size="small" type="success" v-else>{{scope.row.status_name}}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageIndex" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange"
                   @current-change="handleCurrentChange2"></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
    <children ref="children" @deptData="deptset" @fetch-data=" { bumen }"></children>
  </div>
</template>

<script>
  import {toList, toAdd, doDelete} from '@/api/contractshopmanagement'
  import Children from '@/components/bumenTree'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'contractshopmanagement',
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
          pageIndex: 1,
          pageSize: 10,
          dept_id: '',
          deptName: '',
          contract_name: '',
          status: '',
          supplier_name: '',
        },
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
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
        if (this.selectRows.id) {
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const id = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({id})
            this.$baseMessage(msg, 'success')
            this.queryData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageIndex = val
        this.fetchData()
      },
      deptset(row) {
        this.queryForm.dept_id = row.id;
        this.queryForm.deptName = row.name;
      },
      queryData() {
        this.queryForm.pageIndex = 1
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      async fetchData() {
        this.listLoading = true
        console.log(this.queryForm);
        const {data} = await toList(this.queryForm)
        this.list = data.list
        this.total = data.total
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
