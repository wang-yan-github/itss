<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
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
    </vab-query-form>

    <el-table
      ref="listTable"
      stripe
      :data="tableData"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
    >
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        sortable
        align="center"
        width="100"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        label="名称"
        sortable
      ></el-table-column>
<!--      <el-table-column-->
<!--        show-overflow-tooltip-->
<!--        type="index" label="序号"-->
<!--        sortable-->
<!--        align="center"-->
<!--        width="100"-->
<!--      ></el-table-column>-->
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        label="状态"
        width="100"
        align="center"
        :formatter="formatIsEnable"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="sort"
        label="排序"
        width="100"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="approves"
        label="审批人"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="description"
        label="描述"
        align="center"
      ></el-table-column>
    </el-table>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <rolesview ref="rolesview" @fetch-data="tableData"></rolesview>
  </div>
</template>

<script>
  import {categoryGetList, categoryDelete} from '@/api/knowledgeConfig'
  import Edit from './components/Edit'
  import Rolesview from './components/View'
  import {getList} from '@/api/assetsStatusManage'

  export default {
    name: 'bookclassmanagement',
    components: {Edit, Rolesview},
    data() {
      return {
        total: '',
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          username: '',
        },
        tableData: [],
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      moreQuery() {
        this.isShow = !this.isShow
      },
      handleCurrentChange(val) {
        this.selectRows = val
        this.fetchData()
      },
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['rolesview'].showView()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          console.log(this.selectRows);
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleRefresh() {
        this.$router.go(0)
        // this.$router.replace({
        //   path:"/set/bookclassmanagement"
        // })
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const id = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await categoryDelete({id})
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await categoryGetList(this.queryForm)
        this.tableData = data
        // this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      formatIsEnable(row, column) {
        if (row.is_use == null) {
          return "";
        }
        return row.is_use == '0' ? "禁用" : "使用";
      },
    },
  }
</script>
