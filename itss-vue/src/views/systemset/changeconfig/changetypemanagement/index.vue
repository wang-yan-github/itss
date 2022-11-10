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
      <el-table-column show-overflow-tooltip prop="type_name" label="名称" sortable header-align="center" align="center">
        <template #default="{ row }">
          <el-link @click="handleView(row)">{{row.type_name}}</el-link>
        </template>
      </el-table-column>
      <!-- <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center"
                       width="100"></el-table-column> -->
      <el-table-column show-overflow-tooltip prop="sort" label="排序" width="100" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use_name" label="状态" width="100" align="center">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == '1'">使用</el-tag>
          <el-tag size="small" type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="commonly_name" label="一般变更(审批人-方案审核人-实施后评审人)"
                       header-align="center" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="urgent_name" label="紧急变更(审批人-实施后评审人)"
                       header-align="center" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="major_name" label="重大变更(审批人-方案审核人-实施后评审人)"
                       header-align="center" align="center"></el-table-column>
    </el-table>
    <refView ref="refView" @fetch-data="showWithTree"></refView>
  </div>
</template>

<script>
  import {deleteChangeType, getParentList} from '@/api/sourceSet'
  import refView from './components/View'

  export default {
    name: 'changetype1',
    components: {refView},
    data() {
      return {
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          username: '',
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
          path: '/set/changetypeadd',
        })
      },
      handleView(row) {
        var id = row.id;
        this.$refs['refView'].showView(id)
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/set/changetypeedit',
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
            const {msg} = await deleteChangeType({ids})
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
      },


    },
  }
</script>
