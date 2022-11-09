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
          <el-form-item>
            <el-select v-model="queryForm.is_enable" clearable placeholder="状态">
              <el-option label="使用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.status_name" placeholder="名称" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" sortable label="名称" header-align="center"
                       align="center">
        <template #default="{ row }">
          <el-link @click="handleProcess(row)">{{row.status_name}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="is_enable" width="100px;" label="状态" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.is_enable == 1">使用</el-tag>
          <el-tag type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="is_scrap" width="100px;" align="center" label="报废">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.is_scrap == 1">是</el-tag>
          <el-tag type="danger" v-else>否</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="description" label="描述" header-align="center"
                       align="center"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total"
                   @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
  import {getList, doDelete} from '@/api/assetsStatusManage'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'assetsmanagement',
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
          is_enable: '',
          status_name: '',
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
            console.log({id})
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
      formatIsEnable(row, column) {
        if (row.is_enable == null) {
          return ''
        }
        return row.is_enable == '0' ? '禁用' : '启用'
      },
      formatIsScrap(row, column) {
        if (row.is_scrap == null) {
          return ''
        }
        return row.is_scrap == '0' ? '是' : '否'
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
