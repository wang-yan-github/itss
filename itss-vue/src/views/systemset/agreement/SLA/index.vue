<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-view" type="info" @click="handleView">查看</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.sla_event_name" placeholder="名称" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
    >
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column type="index" label="序号" width="55" align="center">
        <template slot-scope="scope">
          {{(queryForm.pageIndex-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <!--      <el-table-column
              show-overflow-tooltip
              prop="id"
              label="编码"
              align="center"
              width="200px;"
              sortable
            ></el-table-column>-->
      <el-table-column show-overflow-tooltip prop="sla_event_name" width="200px;" sortable label="名称"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_first" label="考虑优先级" align="center">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_first == '1'">是</el-tag>
          <el-tag size="small" type="warning" v-else-if="scope.row.is_first == '0'">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="is_request" label="考虑请求类型" align="center">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_request == '1'">是</el-tag>
          <el-tag size="small" type="warning" v-else-if="scope.row.is_request == '0'">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="remark" label="描述" align="center"></el-table-column>
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
    <rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
  </div>
</template>

<script>
  import {slaEventGetList, slaEventToEdit} from '@/api/affect'
  import Edit from './components/edit'
  import Rolesview from './components/view'

  export default {
    name: 'SLA',
    components: {Edit, Rolesview},
    data() {
      return {
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageIndex: 1,
          pageSize: 10,
          sla_event_name: '',
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
          this.$refs['rolesview'].showView(this.selectRows)
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
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await slaEventToEdit({id: ids, is_del: '1'})
            this.$baseMessage(msg, 'success')
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
        this.queryForm.pageIndex = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageIndex = 1
        this.fetchData()
      },
      getList() {
        if (this.idx == 1) {
          if (this.form.is_first == true) {
            this.fetchData();
          } else {
            this.list = [{first_name: '-全部-'}]
          }
        } else {
          if (this.form.is_first == true) {
            if (this.first == 0) {
              this.list = this.olist
            } else {
              this.fetchData();
            }
          } else {
            if (this.first == 1) {
              this.list = this.olist
            } else {
              this.list = [{first_name: '-全部-'}]
            }
          }
        }

      },
      async fetchData() {
        this.listLoading = true
        const {data} = await slaEventGetList(this.queryForm)
        this.list = data.list
        this.total = data.total
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
