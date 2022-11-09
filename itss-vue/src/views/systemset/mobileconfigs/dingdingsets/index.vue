<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="180px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">

          <el-card class="cardbox" shadow="never">


            <el-form-item label="钉钉参数配置:" prop="checked" class="tsitem">
              <el-input v-model="form.username" :rows="20" type="textarea"></el-input>
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
  export default {
    name: 'dingdingsets',
    components: {},
    data() {
      return {
        input2: '',
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
