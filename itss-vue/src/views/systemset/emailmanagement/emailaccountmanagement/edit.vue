<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-card class="cardbox" shadow="never">
        <div slot="header">
          <span class="font16 b">属性</span>
          <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
        </div>
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="邮箱:" prop="username">
              <el-input
                v-model.trim="form.username"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="用户名:" prop="password">
              <el-input
                v-model.trim="form.username"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="密码:" prop="truename">
              <el-input
                v-model.trim="form.password"
                autocomplete="off"
                show-password
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="启用">
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="是" value="shanghai"></el-option>
                <el-option label="否" value="beijing"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-alert
              :closable="false"
              type="info"
              title="接收邮件"
              style="margin-bottom: 20px"
            >
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                  <el-form-item label="启用接收">
                    <el-select
                      v-model="form.isrecive"
                      placeholder="请选择"
                      class="w"
                      @change="
                        (val) => {
                          changeStatus(val, index)
                        }
                      "
                    >
                      <el-option label="使用" value="1"></el-option>
                      <el-option label="禁用" value="0"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <div style="display: inline" v-if="isShow">
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="接收类型">
                      <el-select
                        v-model="form.isvip"
                        placeholder="请选择"
                        class="w"
                      >
                        <el-option label="是" value="shanghai"></el-option>
                        <el-option label="否" value="beijing"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="接收主机">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="接收端口">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="IMAP文件夹" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="接收周期(分钟)" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="自动归档到文件夹" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="上次接收时间" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="分配到服务群组" class="custemitem">
                      <el-select
                        v-model="form.isvip"
                        placeholder="请选择"
                        class="w"
                      >
                        <el-option label="是" value="shanghai"></el-option>
                        <el-option label="否" value="beijing"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="分配到工程师" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                        @click.native="handleAdd"
                      >
                        <el-button
                          icon="el-icon-search"
                          slot="append"
                          @click="handleAdd"
                        ></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="默认工单用户" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                        @click.native="handleAdd"
                      >
                        <el-button
                          icon="el-icon-search"
                          slot="append"
                          @click="handleAdd"
                        ></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="默认开单人" class="custemitem">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                        @click.native="handleAdd"
                      >
                        <el-button
                          icon="el-icon-search"
                          slot="append"
                          @click="handleAdd"
                        ></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                </div>
              </el-row>
            </el-alert>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-alert
              :closable="false"
              type="info"
              title="发送邮件"
              style="margin-bottom: 20px"
            >
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                  <el-form-item label="启用发送">
                    <el-select
                      v-model="form.isvip"
                      placeholder="请选择"
                      class="w"
                      @change="
                        (val) => {
                          changeStatusfa(val, index)
                        }
                      "
                    >
                      <el-option label="使用" value="1"></el-option>
                      <el-option label="禁用" value="0"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <div style="display: inline" v-if="isShowfa">

                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发送类型">
                      <el-select
                        v-model="form.isvip"
                        placeholder="请选择"
                        class="w"
                      >
                        <el-option label="是" value="shanghai"></el-option>
                        <el-option label="否" value="beijing"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发送主机">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发送端口">
                      <el-input
                        v-model.trim="form.username"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="">
                      <el-checkbox>启用SMTP匿名发送</el-checkbox>
                    </el-form-item>
                  </el-col>
                </div>
              </el-row>
            </el-alert>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="备注：" prop="username">
              <el-input
                v-model.trim="form.username"
                autocomplete="off"
                type="textarea"
                :rows="10"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <children
      ref="children"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
  </div>
</template>

<script>
  import {getList, doDelete, doEdit} from '@/api/userManagement'
  import Children from '@/components/bumenTree'
  import Dialogone from './components/UserDiaTwo'

  export default {
    name: 'emailaccountmanagementedit',
    components: {Children, Dialogone},
    data() {
      return {
        isDisable: false,
        bumen: '',
        input2: '',
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
        isShow: false,
        isShowfa: false,
        form: {
          username: 'admin',
          truename: '管理员',
          password: '',
          email: '',
          permissions: [],
          bumen: '',
          isvip: '',
          isrecive: '',
        },
        rules: {
          username: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
          truename: [{required: true, trigger: 'blur', message: '请输入姓名'}],
          password: [{required: true, trigger: 'blur', message: '请输入密码'}],
          email: [{required: true, trigger: 'blur', message: '请输入邮箱'}],
          permissions: [
            {required: true, trigger: 'blur', message: '请选择权限'},
          ],
        },
      }
    },
    created() {
      this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      handleAdd() {
        this.$refs['dialogone'].showWin()
      },
      handleDelete() {
        if (this.selectRows != '') {
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
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await getList(this.queryForm)
        this.tablelist = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      changeStatus(val, index) {
        if (val == '1') {
          this.isShow = true
        } else {
          this.isShow = false
        }
      },
      changeStatusfa(val, index) {
        if (val == '1') {
          this.isShowfa = true
        } else {
          this.isShowfa = false
        }
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
              const data = await doEdit(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.fetchData()
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
    },
  }
</script>
<style lang="scss" scoped>
  .main-container {
    ::v-deep {
      .el-alert {
        .el-alert__content {
          width: 100% !important;
        }
      }
    }
  }
</style>
