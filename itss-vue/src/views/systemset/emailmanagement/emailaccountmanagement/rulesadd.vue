<template>
  <div class="main-container pb80">
    <el-alert type="error" title="提示" :closable="true" style="margin-bottom:20px;">
      可用变量:
      <el-tag type='warning' effect="plain">${findByUserByFirstReceiver}</el-tag>
      按第一个收件人查询用户
    </el-alert>
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-card class="cardbox" shadow="never">
        <div slot="header">
          <span class="font16 b">属性</span>
          <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
        </div>
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
            <el-form-item label="名称:" prop="username">
              <el-input
                v-model.trim="form.username"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
            <el-form-item label="状态">
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
              title="邮件到达时"
              style="margin-bottom: 20px"
            >
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="14" :lg="14" :xl="14">
                  <el-form-item label="匹配方式">
                    <el-select
                      v-model="form.isvip"
                      placeholder="请选择"
                      class="w"
                    >
                      <el-option label="匹配任意一个条件" value="0"></el-option>
                      <el-option label="匹配全部条件" value="1"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="如果收件人">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="正则表达式">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="如果发件人">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="正则表达式">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="如果标题">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="正则表达式">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item
                        label="如果收件时间从当日"
                        class="custemitem"
                      >
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="至 当日">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="如果">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          style="width: 60%; margin-right: 5%"
                        >
                          <el-option label="Auto_Submitted" value="Auto_Submitted"></el-option>
                          <el-option label="Body" value="Body"></el-option>
                          <el-option label="Cc" value="Cc"></el-option>
                          <el-option label="Delivered_To" value="Delivered_To"></el-option>
                          <el-option label="Envelope_To" value="Envelope_To"></el-option>
                          <el-option label="Errors_To" value="Errors_To"></el-option>
                          <el-option label="From" value="From"></el-option>
                          <el-option label="Importance" value="Importance"></el-option>
                          <el-option label="In_Reply_To" value="In_Reply_To"></el-option>
                          <el-option label="List_Archive" value="List_Archive"></el-option>
                          <el-option label="List_Id" value="List_Id"></el-option>
                        </el-select>
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          style="width: 35%"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="正则表达式">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1">
                      <el-form-item label="" class="tsitem">
                        <el-button
                          round
                          size="mini"
                          type="primary"
                          icon="el-icon-plus"
                          @click="addItem"
                        ></el-button>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col
                  :xs="24"
                  :sm="24"
                  :md="24"
                  :lg="24"
                  :xl="24"
                  v-for="(item, index) in formArr"
                  :key="index"
                >
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="如果">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          style="width: 60%; margin-right: 5%"
                        >
                          <el-option label="Auto_Submitted" value="Auto_Submitted"></el-option>
                          <el-option label="Body" value="Body"></el-option>
                          <el-option label="Cc" value="Cc"></el-option>
                          <el-option label="Delivered_To" value="Delivered_To"></el-option>
                          <el-option label="Envelope_To" value="Envelope_To"></el-option>
                          <el-option label="Errors_To" value="Errors_To"></el-option>
                          <el-option label="From" value="From"></el-option>
                          <el-option label="Importance" value="Importance"></el-option>
                          <el-option label="In_Reply_To" value="In_Reply_To"></el-option>
                          <el-option label="List_Archive" value="List_Archive"></el-option>
                          <el-option label="List_Id" value="List_Id"></el-option>

                        </el-select>

                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          style="width: 35%"
                        >
                          <el-option label="包含" value="0"></el-option>
                          <el-option label="不包含" value="1"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="正则表达式">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1">
                      <el-form-item label="" class="tsitem">
                        <el-button
                          round
                          size="mini"
                          type="danger"
                          icon="el-icon-delete"
                          @click="delItem(index)"
                        ></el-button>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-alert>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-alert
              :closable="false"
              type="info"
              title="满足以上条件时，则"
              style="margin-bottom: 20px"
            >
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="">
                    <el-checkbox v-model="checked">忽略该邮件</el-checkbox>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="设置">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="Auto_Submitted" value="Auto_Submitted"></el-option>
                          <el-option label="Body" value="Body"></el-option>
                          <el-option label="Cc" value="Cc"></el-option>
                          <el-option label="Delivered_To" value="Delivered_To"></el-option>
                          <el-option label="Envelope_To" value="Envelope_To"></el-option>
                          <el-option label="Errors_To" value="Errors_To"></el-option>
                          <el-option label="From" value="From"></el-option>
                          <el-option label="Importance" value="Importance"></el-option>
                          <el-option label="In_Reply_To" value="In_Reply_To"></el-option>
                          <el-option label="List_Archive" value="List_Archive"></el-option>
                          <el-option label="List_Id" value="List_Id"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="为">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1">
                      <el-form-item label="" class="tsitem">
                        <el-button
                          round
                          size="mini"
                          type="primary"
                          icon="el-icon-plus"
                          @click="addItemo"
                        ></el-button>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
                <el-col
                  :xs="24"
                  :sm="24"
                  :md="24"
                  :lg="24"
                  :xl="24"
                  v-for="(item, index) in formArr1"
                  :key="index"
                >
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="2" :lg="2" :xl="2">
                      <el-form-item label="">
                        <el-checkbox v-model="checked"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="设置">
                        <el-select
                          v-model="form.isvip"
                          placeholder="请选择"
                          class="w"
                        >
                          <el-option label="Auto_Submitted" value="Auto_Submitted"></el-option>
                          <el-option label="Body" value="Body"></el-option>
                          <el-option label="Cc" value="Cc"></el-option>
                          <el-option label="Delivered_To" value="Delivered_To"></el-option>
                          <el-option label="Envelope_To" value="Envelope_To"></el-option>
                          <el-option label="Errors_To" value="Errors_To"></el-option>
                          <el-option label="From" value="From"></el-option>
                          <el-option label="Importance" value="Importance"></el-option>
                          <el-option label="In_Reply_To" value="In_Reply_To"></el-option>
                          <el-option label="List_Archive" value="List_Archive"></el-option>
                          <el-option label="List_Id" value="List_Id"></el-option>

                        </el-select>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="5" :lg="5" :xl="5">
                      <el-form-item label="为">
                        <el-input
                          v-model.trim="form.username"
                          autocomplete="off"
                        ></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="1" :lg="1" :xl="1">
                      <el-form-item label="" class="tsitem">
                        <el-button
                          round
                          size="mini"
                          type="danger"
                          icon="el-icon-delete"
                          @click="delItemo(index)"
                        ></el-button>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-alert>
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
    name: 'emailrulesadd',
    components: {Children, Dialogone},
    data() {
      return {
        isDisable: false,
        bumen: '',
        input2: '',
        formArr: [],
        formArr1: [],
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
      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index) {
        var that = this
        that.formArr.splice(index, 1)
      },
      addItemo() {
        var that = this
        that.formArr1.push({
          value1: '',
        })
      },
      delItemo(index) {
        var that = this
        that.formArr1.splice(index, 1)

      },
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
