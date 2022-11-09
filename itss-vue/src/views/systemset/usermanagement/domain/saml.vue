<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <div slot="header" class="font16 b">
              <span>证书配置</span>
            </div>
            <el-form-item label="SP证书:" prop="checked">
              <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple
              >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  将文件拖到此处，或
                  <em>点击上传</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                  只能上传jpg/png文件，且不超过500kb
                </div>
              </el-upload>
            </el-form-item>

            <el-form-item label="证书别名:" prop="isvip">
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="证书密码:" prop="truename">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
          </el-card>
          <el-card class="cardbox" shadow="never">
            <div slot="header" class="font16 b">
              <span>提供给SAML</span>
            </div>
            <el-form-item
              label="SP ID(标识符，实体ID):"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username">
                <el-button slot="append" icon="el-icon-document-copy" v-clipboard:copy="form.username"
                           v-clipboard:success="onCopy" v-clipboard:error="onError">
                  复制到剪切板
                </el-button>
              </el-input>
            </el-form-item>

            <el-form-item
              label="Assertion consumer service（回复URL,断言使用者服务URL）:"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username">
                <el-button slot="append" icon="el-icon-document-copy" v-clipboard:copy="form.username"
                           v-clipboard:success="onCopy" v-clipboard:error="onError">
                  复制到剪切板
                </el-button>
              </el-input>
            </el-form-item>

            <el-form-item
              label="Single logout service(SLS,登录URL):"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username">
                <el-button slot="append" icon="el-icon-document-copy" v-clipboard:copy="form.username"
                           v-clipboard:success="onCopy" v-clipboard:error="onError">
                  复制到剪切板
                </el-button>
              </el-input>
            </el-form-item>
          </el-card>
          <el-card class="cardbox" shadow="never">
            <div slot="header" class="font16 b">
              <span>由SAML提供</span>
            </div>
            <el-form-item label="IDP EntityID:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="IDP SSO Url:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item
              label="IDP Single logout url:"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item
              label="Username attribute:"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item
              label="IdP X509Cert（SAML证书指纹）:"
              prop="checked"
              class="tsitem"
            >
              <el-upload
                class="upload-demo"
                drag
                action="https://jsonplaceholder.typicode.com/posts/"
                multiple
              >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">
                  将文件拖到此处，或
                  <em>点击上传</em>
                </div>
                <div class="el-upload__tip" slot="tip">
                  只能上传jpg/png文件，且不超过500kb
                </div>
              </el-upload>
            </el-form-item>
            <el-alert title="最新日志:" type="success" :closable="false">
              <el-timeline :reverse="reverse">
                <el-timeline-item
                  v-for="(activity, index) in activities"
                  :key="index"
                  :timestamp="activity.timestamp"
                  :color="activity.color"
                >
                  {{ activity.content }}
                </el-timeline-item>
              </el-timeline>
            </el-alert>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  // import { getList } from '@/api/changeLog'
  export default {
    name: 'domainmana',
    components: {},
    data() {
      return {
        input2: '',
        reverse: true,
        activities: [],
        formArr: [{value1: ''}],
        tablelist: null,
        checked: false,
        checked1: false,
        value: [],
        form: {
          username: 'http://ent1.topvee.cn/saml/metadata.do',
          truename: '管理员',
          password: '',
          email: '',
          permissions: [],
          bumen: '',
          isvip: '',
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
        if (that.formArr.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr.splice(index, 1)
        }
      },
      openwin() {
        this.$router.push({
          path: '/set/usermanagementedit',
          query: {id: 123},
        })
      },
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
      // 复制成功时的回调函数
      onCopy(e) {
        this.$message.success('内容已复制到剪切板！')
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error('抱歉，复制失败！')
      },
    },
  }
</script>
