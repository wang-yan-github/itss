<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
          添加
        </el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">
          修改
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
        <el-button icon="el-icon-refresh-left" @click="handleRefresh">
          刷新
        </el-button>

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.name"
              placeholder="名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.isUse" placeholder="全部">
              <el-option>全部</el-option>
              <el-option label="禁用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>

            </el-select>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
    >
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column
        show-overflow-tooltip
        label="序号"
        align="center"
        width="200px;"
        sortable
      >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        width="200px;"
        sortable
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        width="200px;"
        label="状态"
        sortable
        align="center"
        :formatter="formatterIsUse"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="sort"
        width="200px;"
        align="center"
        sortable
        label="排序"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="des"
        align="center"
        sortable
        label="可查看范围/可查看部门/可查看服务群组"
      ></el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
  import {getKnowledgeRangeList, delKnowledgeRange} from '@/api/knowledgeConfig'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'bookviewrange',
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
          name: '',
          isUse: '',
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
          this.$refs['views'].showView()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

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
            const {msg} = await delKnowledgeRange({id})
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
      handleRefresh() {
        this.$router.go(0)
        // this.$router.replace({
        //   path:"/set/bookclassmanagement"
        // })
      },
      formatterIsUse(row) {
        if (null == row) {
          return "";
        }
        return row.is_use == 0 ? "禁用" : "使用";
      },
      async fetchData() {
        this.listLoading = true
        // if (this.queryForm.isUse == -1){
        //   this.queryForm.isUse = "";
        // }
        const {data, totalCount} = await getKnowledgeRangeList(this.queryForm)
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
