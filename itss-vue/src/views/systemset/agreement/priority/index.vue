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
        <el-button icon="el-icon-view" type="info" @click="handleView">
          查看
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-select v-model="queryForm.is_use" placeholder="状态" clearable>
                <el-option label="启用" value="1"></el-option>
                <el-option label="禁用" value="0"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.first_name"
              placeholder="名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
            <el-button
              icon="el-icon-d-arrow-left"
              @click="moreQuery"
              v-if="isShow"
            >
              高级
            </el-button>
            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>
              高级
            </el-button>
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
      <el-table-column
        show-overflow-tooltip
        prop="first_name"
        width="200px;"
        sortable
        label="名称"
        align="center"
      >
        <template #default="scope">
          <div v-if="scope.row.vip == '1'">{{scope.row.first_name}}<span style="color:red">[VIP]</span></div>
          <div v-else>{{scope.row.first_name}}</div>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        label="状态"
        align="center"
      >
        <template #default="scope">
          <el-tag
            size="small"
            type="success"
            v-if="scope.row.is_use == '1'"
          >启用
          </el-tag
          >
          <el-tag
            size="small"
            type="warning"
            v-else-if="scope.row.is_use == '0'"
          >禁用
          </el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="remark"
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
  import {priorityGetList, priorityToEdit} from '@/api/affect'
  import Edit from './components/edit'
  import Rolesview from './components/view'

  export default {
    name: 'priority',
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
          first_name: '',
          is_use: '',
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
            const {msg} = await priorityToEdit({id: ids, is_del: '1'})
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
      moreQuery() {
        this.isShow = !this.isShow
      },
      async fetchData() {
        this.listLoading = true
        const {data} = await priorityGetList(this.queryForm)
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
