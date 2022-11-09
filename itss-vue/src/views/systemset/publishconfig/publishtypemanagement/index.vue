<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
      </vab-query-form-left-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
              :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <el-table-column label="序号" width="70px;" align="center">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="名称" header-align="center" align="center" sortable>
        <template #default="{ row }">
          <el-link @click="handleView(row)">{{row.name}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="approver" label="评审人" align="center"
                       header-align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="publisherName" label="发布人" align="center"
                       width="120px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use" label="状态" align="center" width="120px;">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == '1'">启用</el-tag>
          <el-tag size="small" type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange"
                   @current-change="handleCurrentChange2"></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
  </div>
</template>

<script>
  import {publishCategoryList, publishCategoryToEdit} from '@/api/publish'
  import Edit from './components/Edit'
  import Rolesview from './components/View'
  import {priorityGetList} from "@/api/affect";

  export default {
    name: 'publishtype',
    components: {Edit, Rolesview},
    data() {
      return {
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          username: '',
          pageNo: 1,
          pageSize: 10,
        },
        tableData: [],
        total: 0,
        layout: 'total, sizes, prev, pager, next, jumper',
      }
    },
    created() {
      this.fetchData()
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      handleView(row) {
        this.$refs['rolesview'].showView(row)
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['edit'].showEdit(this.selectRows)
          // this.fetchData()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleRefresh() {
        this.$router.go(0)
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await publishCategoryToEdit({id: ids, is_del: '1'})
            this.$baseMessage(msg, 'success')
            this.fetchData();
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
      async fetchData() {
        this.listLoading = true
        const {data} = await publishCategoryList(this.queryForm)
        this.tableData = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
