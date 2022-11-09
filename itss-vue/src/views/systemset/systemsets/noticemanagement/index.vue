<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
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
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="title" align="center" sortable label="标题"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" width="100px;" label="添加人" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" width="250px;" align="center" sortable label="添加时间"></el-table-column>
      <el-table-column show-overflow-tooltip label="状态" align="center">
        <template slot-scope="scope">{{ scope.row.is_use == 0 ? '停用' : '使用' }}</template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="可见范围" align="center">
        <template slot-scope="scope">{{ scope.row.scope == 0 ? '公开' : '指定人员 角色 部门' }}</template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="effective_date" width="250px;" label="有效期至" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip label="置顶" align="center">
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
</template>

<script>
  import {toList, toDelete, getById} from '@/api/sysAnnouncement'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'noticemanagement',
    components: {Edit, Views},
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
          title: '',
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
      async handleView() {
        if (this.selectRows.id) {
          const id = this.selectRows.id
          const {data} = await getById({id})
          this.$refs['views'].showView(data)
          // this.$router.push({
          //   path:
          //   query: { id : data.id ,title: data.title ,content : data.content ,is_use : data.is_use ,scope : data.scope,is_top : data.is_top,effective_date : data.effective_date},
          // })

        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      handleRecord(row) {
        if (row.id) {
          const ids = row.id
          this.$baseConfirm('你确定要生成此条工作记录吗', null, async () => {
            // const { msg } = await doDelete({ ids })
            this.$baseMessage("生成成功", 'success')
            //this.fetchData()
          })
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
          const id = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await toDelete({id})
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
        const {data, totalCount} = await toList(this.queryForm)
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
