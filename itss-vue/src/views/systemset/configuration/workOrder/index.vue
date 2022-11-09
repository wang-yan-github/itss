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
        <el-button icon="el-icon-upload2" type="success">
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
        width="80"
        type="index" label="序号"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        label="名称"
        sortable
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="sort"
        label="排序"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        label="状态"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.is_default == 0 ? '禁用' : '启用' }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_send_charge_name"
        label="抄送负责人"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="contractName"
        width="400px;"
        sortable
        label="客户合同"
        align="center"
      ></el-table-column>
    </el-table>

  </div>
</template>

<script>
  import {tree, doDelete} from '@/api/selfService'

  export default {
    name: 'workOrder090',
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
      this.select();
    },
    methods: {
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$router.push({
          path: '/set/workAdd',
        })
      },
      handleEdit() {
        if (this.selectRows.id) {
          this.$router.push({
            path: '/set/workEdit', query: {id: this.selectRows.id}
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id;
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            doDelete(ids).then((res) => {
              if (res.code == 0) {
                this.select();
                this.$baseMessage('操作成功', 'success')
              }
            });
            this.select();
            this.$baseMessage(msg, 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      async select() {//触发子组件城市选择-选择城市的事件\
        // var that = this;
        // tree().then((res) => {
        //   that.tableData = res.data;
        // });
        const { data } = await tree({})
        this.tableData = data
      }
    },
  }
</script>
