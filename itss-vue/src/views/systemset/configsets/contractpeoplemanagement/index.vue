<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
        <el-button icon="el-icon-view" @click="handleView">查看</el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-select v-model="queryForm.is_enable" placeholder="状态">
                <el-option label="禁用" value="0"></el-option>
                <el-option label="使用" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-input v-model.trim="queryForm.email" placeholder="email" clearable @keyup.enter.native="queryData"/>
            </el-form-item>
            <el-form-item>
              <el-input v-model.trim="queryForm.phone" placeholder="phone" clearable @keyup.enter.native="queryData"/>
            </el-form-item>
          </div>
          <el-form-item>
            <el-input v-model.trim="queryForm.contact_name" placeholder="名称" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="contact_name" sortable label="姓名" header-align="center"
                       align="center">
        <template #default="{ row }">
          <el-link @click="handleProcess(row)">{{row.contact_name}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="company_name" align="center" sortable
                       label="联系人公司"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_enable" width="120px;" label="状态" align="center">
        <template slot-scope="scope">
          <el-tag size="small" type="danger" v-if="scope.row.is_enable == 0">禁用</el-tag>
          <el-tag size="small" type="success" v-else>使用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" width="150px;" align="center"
                       label="我司对接部门"></el-table-column>
      <el-table-column show-overflow-tooltip prop="email" width="150px;" align="center" label="Email"></el-table-column>
      <el-table-column show-overflow-tooltip prop="phone" width="150px;" align="center" label="手机"></el-table-column>
      <el-table-column show-overflow-tooltip prop="remark" align="center" sortable label="备注"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total"
                   @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
  import {getList, doDelete} from '@/api/assetsContractContact'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'contractpeoplemanagement',
    components: {Edit, Views},
    data() {
      return {
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
          username: '',
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
      handleView() {
        if (this.selectRows.id) {
          this.$refs['views'].showView(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleProcess(row) {
        this.$refs['views'].showView(row)
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
            this.fetchData()
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
        this.queryForm.pageNo = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
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
