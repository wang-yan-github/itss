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


      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>


    <el-table
      ref="listTable"
      stripe
      :data="list"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
    >
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="80px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        label="名称"
        sortable

      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="type"
        label="类型"
        align="center"
      >
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.type == 0">工单</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 1">问题</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 2">变更</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 3">发布</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 4">配置项</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 5">知识</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        label="状态"
        align="center"
      >
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == 1">
            使用
          </el-tag>
          <el-tag
            size="small"
            type="warning"
            v-else-if="scope.row.is_use == 0"
          >
            停用
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="truename"
        width="200px;"
        align="center"
        sortable
        label="操作"
      >
        <template #default="{row}">
          <el-button type="primary" plain size="mini" @click="handleRecord(row)">字段管理</el-button>
        </template>
      </el-table-column>
    </el-table>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
  </div>
</template>

<script>
  import {getList, toDelete} from '@/api/assetsExpandForm'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'extendformmanagement',
    components: {Edit, Views},
    data() {
      return {
        checked: false,
        isShow: false,
        list: [],
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
          this.$refs['views'].showView()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      handleRecord(row) {
        if (row.id) {
          this.$router.push({
            path: 'extendformsubmanagement',
            query: {id: row.id}
          })
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
          const ids = this.selectRows.id
          var x = {};
          x.id = ids;
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await toDelete(x)
            console.log(msg);
            this.$baseMessage(msg, 'success');

            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
            const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
            this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
            this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo
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
        const {data} = await getList(this.queryForm)
        console.log(data)
        this.list = data
        // this.total = data.total
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
