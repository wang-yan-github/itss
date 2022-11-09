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
        <!--<el-button icon="el-icon-document-copy" type="success" @click="handleView">-->
        <!--复制-->
        <!--</el-button>-->
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
        <el-button icon="el-icon-upload2" type="success" @click="handleUpload">
          导出
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
        prop="name"
        label="名称"
        sortable
        header-align="center"
      ></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use" align="center" label="状态">
        <template slot-scope="scope">
          {{ scope.row.is_use == 1 ? '启用' : '禁用' }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="sort"
        label="排序"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="company_name"
        label="公司"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="group_name"
        label="自动分派到服务群组"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="engineer_name"
        label="自动分派到工程师"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="description"
        label="描述"
        align="center"
      ></el-table-column>
    </el-table>
  </div>
</template>

<script>
  import {getList, del} from '@/api/selfService'

  export default {
    name: 'selfService1',
    components: {},
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
      this.select()
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$router.push({
          path: '/set/serviceAdd',
        })
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/set/serviceAdd',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['children'].showWin()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleUpload() {
        const ids = this.selectRows.id
        this.$baseConfirm('你确定要导出这些数据吗', null, async () => {
          this.$baseMessage('导出数据成功', 'success')
          this.fetchData()
        })
      },
      handleDelete() {
        var that = this
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.id
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            del(ids).then((res) => {
              if (res.code == 0) {
                that.select()
                that.$baseMessage('操作成功', 'success')
              }
            })
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      select() {
        //触发子组件城市选择-选择城市的事件\
        var that = this
        getList().then((res) => {
          that.tableData = res.data
        })
      },
    },
  }
</script>
