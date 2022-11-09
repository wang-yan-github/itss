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
          <el-form-item>
            <el-select v-model="queryForm.is_use" placeholder="状态" clearable>
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.solution_name"
              placeholder="名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
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
        type="index" label="序号"
        align="center"
        width="200px;"
        sortable
      >
         <template slot-scope="scope">
          {{(queryForm.pageIndex-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="solution_name"
        width="200px;"
        sortable
        label="名称"
        align="center"
      >
        <template #default="scope">
          <div v-if="scope.row.is_default == '1'">{{scope.row.solution_name}}<span style="color:red">[默认]</span>
          </div>
          <div v-else>{{scope.row.solution_name}}</div>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        sortable
        width="200px;"
        label="状态"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.is_use == 0 ? '禁用' : '启用' }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="remark"
        sortable
        label="描述"
        align="center"
      ></el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageIndex"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
  import {getList, del} from '@/api/eventSolutionType'
  import Edit from './components/edit'

  export default {
    name: 'solution',
    components: {Edit},
    data() {
      return {
        checked: false,
        list: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageIndex: 1,
          pageSize: 10,
          username: '',
          solution_name: "",
          is_use: ""
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
      handleEdit() {
        if (this.selectRows.id) {
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        var that = this;
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.id
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            del(ids).then((res) => {
              that.$baseMessage("操作成功", 'success')
              that.fetchData()
            });
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageIndex = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageIndex = 1
        this.fetchData()
      },
      fetchData() {
        var that = this;
        getList(that.queryForm).then((res) => {
          that.list = res.data.list;
          that.total = res.data.total
        });
      }
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
