<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
      </vab-query-form-left-panel>
    </vab-query-form>

    <el-table ref="listTable" stripe :data="tableData" row-key="id" default-expand-all
              :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
              :element-loading-text="elementLoadingText" highlight-current-row border
              @current-change="handleCurrentChange">
      <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center"
                       width="100"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="名称" sortable header-align="center"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use_name" label="状态" width="100" align="center">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == '1'">使用</el-tag>
          <el-tag size="small" type="warning" v-else-if="scope.row.is_use == '0'">禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="sort" label="排序" width="100" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="contractName" label="客户合同" width="200"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="description" label="描述" header-align="center"
                       align="center"></el-table-column>
    </el-table>
    <edit ref="edit" @fetch-data="tableData"></edit>
    <rolesview ref="rolesview" @fetch-data="tableData"></rolesview>
  </div>
</template>

<script>
  import {deleteCategory, getParentList,} from '@/api/questionSet'
  import Edit from './components/Edit'
  import Rolesview from './components/View'

  export default {
    name: 'faqtype1',
    components: {Edit, Rolesview},
    data() {
      return {
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          name: '',
        },
        tableData: [],
      }
    },
    created() {
      setTimeout(() => {
        this.showWithTree()
        this.listLoading = false
      }, 300)
    },
    methods: {
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$router.push({
          path: '/set/faqtypeadd',
        })
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
          this.$router.push({
            path: '/set/faqtypeedit',
            query: {id: this.selectRows.id},
          })
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
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await deleteCategory({ids})
            this.$baseMessage(msg, 'success')
            this.showWithTree()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },


      async showWithTree() {
        const {data} = await getParentList(this.queryForm)
        this.tableData = data
        console.log(this.tableData)
      },
    },
  }
</script>
