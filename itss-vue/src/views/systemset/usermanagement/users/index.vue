<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-user" type="success" @click="handleRole">设置角色</el-button>
        <el-dropdown trigger="hover" @command="handleCommand">
          <el-button>更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-view" command="a">查看权限列表</el-dropdown-item>
            <el-dropdown-item icon="el-icon-delete" command="b">删除</el-dropdown-item>
            <el-dropdown-item icon="el-icon-upload2" command="c">导出</el-dropdown-item>
            <el-dropdown-item icon="el-icon-download" command="d">导入</el-dropdown-item>
            <el-dropdown-item icon="el-icon-user" command="e">导出用户角色</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <!-- <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          批量删除
        </el-button> -->
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-input v-model.trim="queryForm.name" placeholder="姓名" @keyup.enter.native="queryData" clearable/>
            </el-form-item>
            <el-form-item>
              <el-input v-model.trim="queryForm.mailbox" placeholder="邮箱" @keyup.enter.native="queryData" clearable/>
            </el-form-item>
            <el-form-item>
              <el-input v-model.trim="queryForm.departmentName" placeholder="部门" clearable @keyup.enter.native="queryData"/>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="请选择状态" clearable>
                 <el-option>请选择</el-option>
                <el-option label="活动" value="1"></el-option>
                <el-option label="暂停" value="2"></el-option>
                <el-option label="已禁止" value="3"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <el-form-item>
            <el-input v-model.trim="queryForm.user_name" placeholder="请输入用户名" @keyup.enter.native="queryData"
                      clearable/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>高级</el-button>
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
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;">
           <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="user_name" sortable label="用户名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" sortable label="姓名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="wechat_nickname" label="微信昵称" align="center"></el-table-column>
      <!--<el-table-column show-overflow-tooltip prop="user_type" label="类型" align="center" ></el-table-column>-->
      <el-table-column show-overflow-tooltip prop="status" align="center" label="状态" sortable>
        <template #default="{ row }">
          <el-tag v-if="row.status == 1">活动</el-tag>
          <el-tag v-else-if="row.status == 2">暂停</el-tag>
          <el-tag v-else-if="row.status == 3">已禁止</el-tag>
          <el-tag v-else>暂无</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="last_login_date" align="center" label="最后登陆时间"
                       sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" align="center" label="添加时间" sortable></el-table-column>
      <!-- <el-table-column show-overflow-tooltip label="权限">
        <template #default="{ row }">
          <el-tag v-for="(item, index) in row.permissions" :key="index">
            {{ item }}
          </el-tag>
        </template>
      </el-table-column> -->
      <!-- <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">
        <template #default="{ row }">
          <el-button type="text" @click="handleEdit(row)">编辑</el-button>
          <el-button type="text" @click="handleEdit(row)">设置角色</el-button>
          <el-button type="text" @click="handleDelete(row)">更多</el-button>
        </template>
      </el-table-column> -->
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
  import {getList, doDelete, exporUserExcel, exporUserRoleExcel, downloadUser} from "@/api/userManagement";
  import Roles from './components/Roles'
  import Rolesview from './components/RolesView'
  import Userimport from './components/UserImport'

  export default {
    name: 'UserManagement',
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
          user_name: '',
          name: '',
          mailbox: '',
          departmentName: '',
          user_type: '',
          status: '',
          is_technology: '',
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
              // this.$message('点击了导出')
              this.exporUserExcel()
            })
            break
          case 'd':
            this.$refs['userimport'].showWin()
            break
          case 'e':
            this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
              // this.$message('点击了导出用户角色')
              this.exporUserRoleExcel()
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
          path: '/set/usermanagementadd',
        })
      },
      handleRole() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['roles'].showWin(this.selectRows)
          // this.$router.push({
          //   path: this.$refs['roles'].showWin(this.selectRows),
          //   query: {id: this.selectRows.id},
          // })
          //this.$refs['roles'].showWin(this.selectRows)

        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
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
        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/set/usermanagementedit',
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

            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
            const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
            this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
            this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo


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
        console.log(this.queryForm);
        this.listLoading = true
        const {data} = await getList(this.queryForm)
        this.list = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },

      async exporUserExcel() {
        exporUserExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '用户导出.xls')
          document.body.appendChild(link)
          link.click()
        })
      },

      async exporUserRoleExcel() {
        exporUserRoleExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '用户角色导出.xls')
          document.body.appendChild(link)
          link.click()
        })
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
