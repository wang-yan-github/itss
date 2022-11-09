<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="待审核" name="first">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-card class="cardbox" shadow="never" style="border-top: 0">
                <div slot="header" class="font16 b">
                  <span>通知发布人</span>
                </div>
                <el-form-item label="标题" prop="checked">
                  <el-input v-model="input2"></el-input>
                </el-form-item>

                <el-form-item
                  label="内容: "
                  prop="isvip"
                >
                  <vab-quill
                    v-model="form.content"
                    :min-height="200"
                    :options="options"
                  ></vab-quill>
                </el-form-item>

                <el-form-item
                  label="额外抄送:"
                  prop="truename"
                >
                  <el-input v-model="input2"></el-input>
                  <p class="ts">抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                </el-form-item>
                <el-form-item
                  label="微信消息:"
                  prop="truename"
                >
                  <el-input v-model="input2"></el-input>
                </el-form-item>
                <el-form-item
                  label="通知方式:"
                  prop="truename"
                >
                  <el-radio v-model="radio1" label="1">手动</el-radio>
                  <el-radio v-model="radio1" label="2">自动</el-radio>
                  <el-radio v-model="radio1" label="3">不发送</el-radio>
                  <p class="ts">知识添加后，发通知给该类别的全部审核人</p>
                </el-form-item>
                <el-alert type="success" title="配置参数" :closable="false">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="ID:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="知识类别:">
                        (KnowledgeType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="添加人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="添加时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="正文:">
                        (Description)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="修改人:">
                        (ModifiedPerson)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="上次更新时间:">
                        (LastModifiedDate)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="审核人:">
                        (Auditer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="审核时间:">
                        (AuditTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="审核意见:">
                        (Comments)
                      </el-form-item>
                    </el-col>

                  </el-row>

                </el-alert>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="审核通过" name="second">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-card class="cardbox" shadow="never" style="border-top: 0">
                <div slot="header" class="font16 b">
                  <span>通知发布人</span>
                </div>
                <el-form-item label="标题" prop="checked">
                  <el-input v-model="input2"></el-input>
                </el-form-item>

                <el-form-item
                  label="内容: "
                  prop="isvip"
                >
                  <vab-quill
                    v-model="form.content"
                    :min-height="200"
                    :options="options"
                  ></vab-quill>
                </el-form-item>

                <el-form-item
                  label="额外抄送:"
                  prop="truename"
                >
                  <el-input v-model="input2"></el-input>
                  <p class="ts">抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                </el-form-item>
                <el-form-item
                  label="微信消息:"
                  prop="truename"
                >
                  <el-input v-model="input2"></el-input>
                </el-form-item>
                <el-form-item
                  label="通知方式:"
                  prop="truename"
                >
                  <el-radio v-model="radio1" label="1">手动</el-radio>
                  <el-radio v-model="radio1" label="2">自动</el-radio>
                  <el-radio v-model="radio1" label="3">不发送</el-radio>
                  <p class="ts">知识添加后，发通知给该类别的全部审核人</p>
                </el-form-item>
                <el-alert type="success" title="配置参数" :closable="false">
                  ID: (ID) 状态: (Status) 知识类别: (KnowledgeType)
                  添加人: (Creator) 添加时间: (AddTime) 标题: (Title)
                  正文: (Description) 修改人: (ModifiedPerson) 上次更新时间: (LastModifiedDate)
                  查看URL: (As)(/As) 审核人: (Auditer) 审核时间: (AuditTime)
                  审核意见: (Comments)
                </el-alert>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="审核拒绝" name="third">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-card class="cardbox" shadow="never" style="border-top: 0">
                <div slot="header" class="font16 b">
                  <span>通知发布人</span>
                </div>
                <el-form-item label="标题" prop="checked">
                  <el-input v-model="input2"></el-input>
                </el-form-item>

                <el-form-item
                  label="内容: "
                  prop="isvip"
                >
                  <vab-quill
                    v-model="form.content"
                    :min-height="200"
                    :options="options"
                  ></vab-quill>
                </el-form-item>

                <el-form-item
                  label="额外抄送:"
                  prop="truename"
                >
                  <el-input v-model="input2"></el-input>
                  <p class="ts">抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                </el-form-item>
                <el-form-item
                  label="微信消息:"
                  prop="truename"
                >
                  <el-input v-model="input2"></el-input>
                </el-form-item>
                <el-form-item
                  label="通知方式:"
                  prop="truename"
                >
                  <el-radio v-model="radio1" label="1">手动</el-radio>
                  <el-radio v-model="radio1" label="2">自动</el-radio>
                  <el-radio v-model="radio1" label="3">不发送</el-radio>
                  <p class="ts">知识添加后，发通知给该类别的全部审核人</p>
                </el-form-item>
                <el-alert type="success" title="配置参数" :closable="false">
                  ID: (ID) 状态: (Status) 知识类别: (KnowledgeType)
                  添加人: (Creator) 添加时间: (AddTime) 标题: (Title)
                  正文: (Description) 修改人: (ModifiedPerson) 上次更新时间: (LastModifiedDate)
                  查看URL: (As)(/As) 审核人: (Auditer) 审核时间: (AuditTime)
                  审核意见: (Comments)
                </el-alert>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>

      </el-tabs>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
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
  // import { getList } from '@/api/changeLog'
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'booknoticeconfig',
    components: {vabQuill},
    data() {
      return {
        options: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{header: [1, 2, 3, 4, 5, 6, false]}],
              [{size: ['small', false, 'large', 'huge']}],
              [{color: []}, {background: []}],
              ['blockquote', 'code-block'],
              [{list: 'ordered'}, {list: 'bullet'}],
              [{script: 'sub'}, {script: 'super'}],
              [{indent: '-1'}, {indent: '+1'}],
              [{align: []}],
              [{direction: 'rtl'}],
              [{font: []}],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
        borderColor: '#dcdfe6',
        activeName: 'first',
        input2: '4',
        radio1: '1',
        radio2: '1',
        reverse: true,
        activities: [],
        formArr: [{value1: ''}],
        checked: false,
        checked1: false,
        value: [],
        form: {
          username: 'admin',
          truename: '管理员',
          password: '',
          email: '',
          permissions: [],
          bumen: '',
          isvip: '',
          content: '',
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
      async fetchData() {
        // const { data } = await getList()
        // data.map((item, index) => {
        //   if (index === data.length - 1) {
        //     item.color = '#0bbd87'
        //   }
        // })
        // this.activities = data
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const {msg} = await doEdit(this.form)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')
            this.fetchData()

            this.closeall()
          } else {
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
      // .el-alert__content p{margin:0;}
      .el-tabs__header {
        margin-bottom: 0 !important;
      }

      .el-tabs__item.is-active {
        background: #fff !important;
      }

      .el-card__body {
        min-height: 600px;
      }
    }
  }
</style>
