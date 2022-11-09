<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1100px"
    @close="close"
  >
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-view" @click="handleView">查看</el-button>

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.title" placeholder="标题" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="title" align="center" sortable label="标题"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" width="100px;" align="center" label="添加人"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" width="180px;" align="center" sortable label="添加时间"></el-table-column>
      <el-table-column show-overflow-tooltip label="状态" align="center" width="70px;">
        <template slot-scope="scope">{{ scope.row.is_use == 0 ? '停用' : '使用' }}</template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="可见范围" align="center">
        <template slot-scope="scope">{{ scope.row.scope == 0 ? '公开' : '指定人员 角色 部门' }}</template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="effective_date" width="110px;" label="有效期至" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip label="置顶" width="70px;" align="center">
        <template slot-scope="scope">{{ scope.row.is_top == 0 ? '是' : '否' }}</template>
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
  </div>
  </el-dialog>
</template>

<script>
import {toList, getById, toNoticeList} from '@/api/sysAnnouncement'
  // 查看公告
  import Views from '@/views/systemset/systemsets/noticemanagement/components/View'

  export default {
    name: 'assetmanagement',
    components: {Views},
    data() {
      return {
        title: '公告管理',
        dialogFormVisible: false,
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
          title: '',
        },
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      showWin() {
        this.title = '公告管理'
        this.dialogFormVisible = true
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      async handleView() {
        if (this.selectRows.id) {
          const id = this.selectRows.id
          const {data} = await getById({id})
          this.$refs['views'].showView(data)
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
        const {data, totalCount} = await toNoticeList(this.queryForm)
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
