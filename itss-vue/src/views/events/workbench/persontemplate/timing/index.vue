<template>
  <div class="index-container">
    <div class="help">
      <vab-icon :icon="['fas', 'question-circle']" @click="openhelp"></vab-icon>
    </div>
    <vab-query-form>
      <vab-query-form-left-panel :span="6">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
          添加
        </el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">
          修改
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
        <el-button icon="el-icon-view" @click="handleView">
          查看任务执行详情
        </el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="18">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-select v-model="queryForm.username" placeholder="类型">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.username"
              placeholder="备注"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.username" placeholder="状态">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="2"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-input
              v-model.trim="queryForm.username"
              placeholder="创建人"
              clearable
              @click.native="openwin"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="openwin"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.username"
              placeholder="工单模板"
              clearable
              @click.native="openwin"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="openwin"-->
              <!--&gt;</el-button>-->
            </el-input>
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
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        align="center"
        width="200px;"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="username"
        sortable
        label="备注"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        width="200px;"
        label="状态"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        width="200px;"
        align="center"
        sortable
        label="创建时间"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        width="200px;"
        align="center"
        sortable
        label="上次执行时间"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        width="200px;"
        align="center"
        sortable
        label="EMAIL"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        width="200px;"
        align="center"
        sortable
        label="创建人"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        align="center"
        sortable
        label="工单模板"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        align="center"
        sortable
        label="操作"
      >
        <template #default="{row}">
          <el-button plain type="success" icon="el-icon-video-play" size="mini" @click="begin(row)">开始</el-button>
          <el-button plain type="primary" icon="el-icon-document" size="mini" @click="rizhi(row)">日志</el-button>
        </template>
      </el-table-column>
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
    <views ref="views" @fetch-data="fetchData"></views>
    <rizhi ref="rizhi" @fetch-data="fetchData"></rizhi>
  </div>
</template>

<script>
import { getList, doDelete } from '@/api/userManagement'
import Edit from './components/Edit'
import Views from './components/View'
import Rizhi from './components/rizhi'

export default {
  name: 'persontemplatetiming1',
  components: { Edit, Views, Rizhi },
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
    openwin() {
    },
    openhelp() {
      this.$router.push({
        path: '/thing/persontemplateadd',
      })
    },
    handleAdd() {
      this.$refs['edit'].showEdit()
    },
    handleView() {

        this.$refs['views'].showView()
    },
    begin(row) {
      if (row.id) {
        this.$baseMessage('开始执行', 'success')
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    rizhi(row) {
      if (row.id) {
        this.$refs['rizhi'].showView()
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
          const { msg } = await doDelete({ ids })
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
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  ::v-deep {
    .right-panel {
      .el-input {
        width: 160px;
      }
    }
  }
}
</style>
