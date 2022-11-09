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
        <el-button icon="el-icon-view" type="info" @click="handleView">
          查看
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
        <!-- <el-button icon="el-icon-connection">异步模式</el-button> -->
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
        width="400"
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="companyName"
        label="公司"
        align="center"
        width="400"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="code"
        label="编码"
        align="center"
        width="200"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        label="状态"
        align="center"
        width="200"
      >
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == '1'">
            启用
          </el-tag>
          <el-tag
            size="small"
            type="warning"
            v-else-if="scope.row.is_use == '0'"
          >
            禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="manageName"
        label="快速报障支持群组"
        align="center"
      ></el-table-column>
    </el-table>
    <edit ref="edit" @fetch-data="showWithTree"></edit>
    <rolesview ref="rolesview" @fetch-data="showWithTree"></rolesview>
  </div>
</template>

<script>
  import {sysDepartmentGetList, sysDepartmentToDel} from '@/api/userManagement'
  import Edit from './components/Edit'
  import Rolesview from './components/View'

  export default {
    name: 'departmentmana',
    components: {Edit, Rolesview},
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
        this.$refs['edit'].showEdit()
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['rolesview'].showView(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //删除
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await sysDepartmentToDel({id: ids, is_del: '1'})
            this.showWithTree()
            this.$baseMessage(msg, 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //初始化部门树
      async showWithTree() {
        const {data} = await sysDepartmentGetList(this.queryForm)
        this.tableData = data
      },
    },
  }
</script>
