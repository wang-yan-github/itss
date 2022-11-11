<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-lock" type="success" @click="handleRole">设置权限</el-button>
        <el-dropdown trigger="hover" @command="handleCommand">
          <el-button>更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-user-solid" command="c">管理用户</el-dropdown-item>
            <el-dropdown-item icon="el-icon-s-check" command="a">查看权限列表</el-dropdown-item>
            <el-dropdown-item icon="el-icon-delete-solid" command="b">删除</el-dropdown-item>
            <el-dropdown-item icon="el-icon-upload" command="d">导出角色用户</el-dropdown-item>
            <el-dropdown-item icon="el-icon-upload" command="e">导出角色权限</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <!--          <div class="hiddenmore" v-if="isShow">-->
          <!--            <el-form-item>-->
          <!--              <el-select v-model="queryForm.is_incident_engineer" placeholder="技术角色">-->
          <!--                <el-option label="区域一" value="shanghai"></el-option>-->
          <!--                <el-option label="区域二" value="beijing"></el-option>-->
          <!--              </el-select>-->
          <!--            </el-form-item>-->
          <!--          </div>-->
          <el-form-item>
            <el-input v-model.trim="queryForm.name" placeholder="名称" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <!--            <el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">-->
            <!--              高级-->
            <!--            </el-button>-->
            <!--            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>-->
            <!--              高级-->
            <!--            </el-button>-->
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
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px;"
                       sortable>
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
                       </el-table-column>
      <el-table-column show-overflow-tooltip prop="name" width="200px;" sortable label="名称"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="description" label="描述" align="center"></el-table-column>
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
    <roles ref="roles" @fetch-data="fetchData"></roles>
    <rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
  </div>
</template>

<script>
  import {
    getPage,
    deleteSysRole,
    exporRoleWithUserExcel,
    exporRoleAuthorityExcel,
    exporUserExcel
  } from "@/api/userManagement";
  import Edit from './components/RolesEdit'
  import Roles from './components/RolesSet'
  import Rolesview from './components/RolesView'

  export default {
    name: 'rolesmanagement',
    components: {Edit, Rolesview, Roles},
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
          name: '',
          is_incident_engineer: '',
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
            if (this.selectRows.id) {
              this.$router.push({
                path: '/set/rolesusermana',
                query: {id: this.selectRows.id},
              })
            } else {
              this.$baseMessage('未选中任何行', 'error')
              return false
            }

            break
          case 'd':
            this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
              this.exporRoleWithUser()
              //this.$message('点击了导出')
            })
            break
          case 'e':
            this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
              this.exporRoleAuthority()
              //this.$message('点击了导出用户角色')
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
        this.$refs['edit'].showEdit()
      },
      handleRole() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['roles'].showWin(this.selectRows);
          // this.$router.push({
          //   path: this.$refs['roles'].showWin(),
          //   query: { id: this.selectRows.id },
          // })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['rolesview'].showView(this.selectRows)
          // this.$router.push({
          //   path:  this.$refs['rolesview'].showView(),
          //   query: { id: this.selectRows.id },
          // })
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
          console.log(ids);
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await deleteSysRole({ids})
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
        this.listLoading = true
        const {data} = await getPage(this.queryForm)
        this.list = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async exporRoleWithUser() {
        exporRoleWithUserExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '角色用户.xls')
          document.body.appendChild(link)
          link.click()
        })
      },

      async exporRoleAuthority() {
        exporRoleAuthorityExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '角色权限.xls')
          document.body.appendChild(link)
          link.click()
        })
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
