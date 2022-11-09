<template>
  <div class="main-container pb80">
    <el-alert
      type="error"
      title="提示"
      :closable="false"
      style="margin-bottom: 20px"
    >
      注: 请正确配置已认证的微信服务号或企业号
    </el-alert>
    <el-form ref="form" :model="form" :rules="rules" label-width="180px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <el-form-item label="服务号自动登录匹配策略: ">
              需要用户进行手动绑定，绑定后系统会记录微信的openID，以后按照openID进行匹配。
            </el-form-item>

            <el-form-item label="微信开关:" prop="truename" class="tsitem">
              <el-radio v-model="input2" label="1">开</el-radio>
              <el-radio v-model="input2" label="2">关</el-radio>
            </el-form-item>
            <el-form-item label="公共号类型:" prop="truename" class="tsitem">
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="服务号" value="shanghai"></el-option>
                <el-option label="企业号" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="APPID:" prop="truename" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="AppSecret:" prop="truename" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="Server URL:" prop="truename" class="tsitem">
              <el-input v-model="form.username">
                <el-button slot="append" icon="el-icon-document-copy" v-clipboard:copy="form.username"
                           v-clipboard:success="onCopy" v-clipboard:error="onError">
                  复制到剪切板
                </el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="Token:" prop="truename" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="EncodingAESKey:" prop="truename" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="可信赖域:" prop="truename" class="tsitem">
              <el-input v-model="form.username">
                <el-button slot="append" icon="el-icon-document-copy" v-clipboard:copy="form.username"
                           v-clipboard:success="onCopy" v-clipboard:error="onError">
                  复制到剪切板
                </el-button>
              </el-input>
            </el-form-item>
            <el-form-item label="关注提示语:" prop="truename" class="tsitem">
              <el-input v-model="form.username" :rows="5" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="扫描二维码URL:" prop="truename" class="tsitem">
              <el-input v-model="form.username" :rows="3" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="自定义菜单:" prop="truename" class="tsitem">
              <el-input v-model="form.username" :rows="5" type="textarea"></el-input>
            </el-form-item>
            <el-form-item label="通过公众号发消息查询知识:" prop="truename" class="tsitem">
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="启用" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="将接收的用户消息转发给微信客服:" prop="truename" class="tsitem">
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="启用" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="扫码立即报障:" prop="truename" class="tsitem">
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="启用" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="报障指派至服务群组:" prop="truename" class="tsitem">
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="用户所属部门的服务群组" value="1"></el-option>
                <el-option label="资产所属部门的服务群组" value="2"></el-option>
                <el-option label="资产类别的服务群组" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="验证文件:" prop="truename" class="tsitem">
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
            <el-form-item label="消息模板ID:" prop="truename" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-alert
              type="success"
              title=""
              :closable="false"
              style="margin-bottom: 20px"
            >
              2022-03-16 08:25:08:
              获取微信access_token成功：54_nCEjxeD8p9ZTfX3s-1-stXmOjjxh5Is_kS9EdOSxHPVB0NnW-lL2cRni6zQ5BQ7SK1wNueMA1T5ZJeCnxfyPsdeMrKTLiGCIyFv1OqyB31FYx7G0xko3VoYsIWzV3-ViskrX5I9zzPVc39Q2OCDjAAAXLM
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
    name: 'weixinsets',
    components: {},
    data() {
      return {
        input2: '',
        reverse: true,
        activities: [],
        formArr: [],
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
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      // 复制成功时的回调函数
      onCopy(e) {
        this.$message.success('内容已复制到剪切板！')
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error('抱歉，复制失败！')
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
