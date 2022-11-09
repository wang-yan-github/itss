<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="180px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <el-form-item label="注册状态: " class="tsitem">
              <el-link type="danger">未注册</el-link>
              <el-link type="success" style="margin-left:10px;">注册版</el-link>
            </el-form-item>
            <el-form-item label="系统版本:" class="tsitem">
              v3.6.2
            </el-form-item>
            <el-form-item label="注册码:" class="tsitem">
              <el-input v-model="form.username" type="textarea" :rows="10"></el-input>
            </el-form-item>
            <el-form-item label="机器码:" class="tsitem">
              cec6807e0d2203d5d28d2623d75c3bdc
            </el-form-item>


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
    name: 'sysreg',
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
