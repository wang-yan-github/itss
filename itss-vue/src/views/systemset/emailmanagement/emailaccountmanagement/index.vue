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
        <el-button icon="el-icon-set-up" type="success" @click="handleRole">
          收件规则
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
        <el-button icon="el-icon-position" @click="handleView">
          发送测试
        </el-button>
        <el-button icon="el-icon-message" @click="handleGet">
          接收测试
        </el-button>
        <el-button icon="el-icon-refresh-left" @click="handleRefresh">
          刷新
        </el-button>
      </vab-query-form-left-panel>

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
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        width="80"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="username"
        sortable
        label="邮箱"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="truename"
        sortable
        label="用户名"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="bumen"
        label="接收手机"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="phone"
        label="发送手机"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="email"
        label="启用"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="weixin"
        label="上次接收时间"
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
    <roles ref="roles" @fetch-data="fetchData"></roles>
    <rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
    <userimport ref="userimport"></userimport>
  </div>
</template>

<script>
  import {getList, doDelete} from '@/api/userManagement'
  import Roles from './components/Roles'
  import Rolesview from './components/RolesView'
  import Userimport from './components/UserImport'

  export default {
    name: 'emailaccountmanagement',
    components: {Roles, Rolesview, Userimport},
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
      handleCommand(command) {
        switch (command) {
          case 'a':
            this.handleView()
            break
          case 'b':
            this.handleDelete()
            break
          case 'c':
            this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
              this.$message('点击了导出')
            })
            break
          case 'd':
            this.$refs['userimport'].showWin()
            break
          case 'e':
            this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
              this.$message('点击了导出用户角色')
            })
            break
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$router.push({
          path: '/set/emailaccountmanagementadd',
        })
      },
      handleRole() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/set/emailaccountmanagementrule',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleRefresh() {
        this.$router.go(0)
        // this.$router.replace({
        //   path:"/set/bookclassmanagement"
        // })
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['rolesview'].showView()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleGet() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$baseMessage('测试成功', 'success')
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/set/emailaccountmanagementedit',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({ids})
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
        const {data, totalCount} = await getList(this.queryForm)
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
    padding: 20px !important;
    background: #fff !important;
  }
</style>
