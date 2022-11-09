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
        <!--<el-button icon="el-icon-view" type="info" @click="handleView">-->
        <!--查看-->
        <!--</el-button>-->
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
              v-model.trim="queryForm.resource_name"
              placeholder="名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData" @keyup.enter.native="queryData">
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
        width="60px;"
        sortable
      >
         <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="resource_name"
        width="400px;"
        sortable
        label="名称"
        align="center"
      ></el-table-column>
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="contractName"-->
      <!--width="400px;"-->
      <!--sortable-->
      <!--label="客户合同"-->
      <!--align="center"-->
      <!--&gt;</el-table-column>-->
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
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
        prop="description"
        label="描述"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="sort"
        label="排序"
        align="center"
      ></el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
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
  import {getList, del} from '@/api/origin'
  import Edit from './components/edit'

  export default {
    name: 'origin',
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
          pageNo: 1,
          pageSize: 10,
          resource_name: '',
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
        if (this.selectRows != '' && this.selectRows != null) {
          // this.$refs['rolesview'].showView()
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
      handleDelete() {
        var that = this;
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.id
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            del(ids).then((res) => {
              if (res.code == 0) {
                that.$baseMessage('操作成功', 'success')
                that.fetchData()
              }
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
        this.queryForm.pageNo = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1
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
