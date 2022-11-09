<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
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
      <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="名称" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use" label="状态" align="center">
        <template slot-scope="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == '0'">启用</el-tag>
          <el-tag size="small" type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="sort" label="排序" align="center"></el-table-column>
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="state"-->
      <!--label="状态"-->
      <!--&gt;</el-table-column>-->
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="state"-->
      <!--label="快速报障支持群组"-->
      <!--&gt;</el-table-column>-->
    </el-table>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
  import {tree, del} from '@/api/schemeClassIfication'
  import Edit from './components/Edit'

  export default {
    name: 'programme',
    components: {Edit},
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
      this.fetchData();
    },
    methods: {
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      handleEdit() {
        if (this.selectRows.id) {
          this.$refs['edit'].showEdit(this.selectRows.id)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            del(ids).then((res) => {
              this.$baseMessage("操作成功", 'success')
              this.select();
            });
          })
        } else {

          return false
        }
      },
      fetchData() {//触发子组件城市选择-选择城市的事件\
        var that = this;
        tree().then((res) => {
          that.tableData = res.data;
        });

      }
    },
  }
</script>
