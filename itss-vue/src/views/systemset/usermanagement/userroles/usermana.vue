<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <div slot="header">
              <span style="color:#999;">
                名称：{{form.name}}　　描述：{{ form.description }}
              </span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>

            <el-table v-loading="listLoading" ref="listTable" border stripe
                      :data="form.userIdList" @selection-change="setSelectRows"
                      :element-loading-text="elementLoadingText"
                      highlight-current-row height="540">
              <el-table-column show-overflow-tooltip sortable type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center"
                               width="80px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="user_name" align="center" sortable
                               label="用户名"></el-table-column>
              <el-table-column show-overflow-tooltip prop="name" align="center" sortable label="姓名"></el-table-column>
              <el-table-column show-overflow-tooltip prop="telephone" align="center" sortable
                               label="电话"></el-table-column>
              <el-table-column show-overflow-tooltip prop="status" align="center" label="状态" sortable>
                <template #default="{ row }">
                  <el-tag v-if="row.status == 1">活动</el-tag>
                  <el-tag v-else-if="row.status == 2">暂停</el-tag>
                  <el-tag v-else-if="row.status == 3">已禁止</el-tag>
                  <el-tag v-else>暂无</el-tag>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top:10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDelete">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
                添加
              </el-button>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <userDialog ref="userDialog" @listData="deptset" @fetch-data=" { input2 }"></userDialog>
  </div>

</template>

<script>
  import {getList, addRoleAndUser, getRoleAndUser, getUserByRoleId} from "@/api/userManagement";
  import userDialog from '@/components/UsersDia'

  export default {
    name: 'rolesusermana',
    components: {userDialog},
    data() {
      return {
        isDisable: false,
        input2: '',
        tableData: [],
        formArr: [{value1: ''}],
        formArr1: [{value1: ''}],
        tablelist: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        checked: false,
        checked1: false,
        options: [],
        value: [],
        list: [],
        loading: false,
        states: [],
        form: {
          id: '',
          name: '',
          description: '',
          userIdList: [],
          userDeletes: [],
        },
      }
    },

    created() {
      this.getData()
    },
    beforeDestroy() {
    },
    mounted() {
      this.list = this.states.map((item) => {
        return {value: `${item}`, label: `${item}`}
      })
    },
    methods: {
      async getData() {
        const roleId = this.$route.query.id
        const {data} = await getUserByRoleId({roleId: roleId})
        this.form = data
        this.listLoading = false
      },
      deptset(rows) {
        rows.forEach((item, i) => {
          let index = this.form.userIdList.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.userIdList.push(item)
          }
        })
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleDelete() {
        if (this.selectRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRows.forEach((item, i) => {
              this.form.userIdList.some((x, i) => {
                if (x.id == item.id) {
                  this.form.userIdList.splice(i, 1)
                  console.log(x.id)
                  this.form.userDeletes.push(x.id)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleAdd() {
        this.$refs['userDialog'].showWin()
      },

      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await addRoleAndUser(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.closeall()
              } else {
                this.$baseMessage("失败", 'error');
                this.isDisable = false;
              }
            }catch(e){
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      async getUserWithRole() {
        const roleId = this.$route.query.id
        const {data} = await getRoleAndUser({roleId: roleId})
        this.tablelist = data
        this.listLoading = false
      },

    },
  }
</script>
