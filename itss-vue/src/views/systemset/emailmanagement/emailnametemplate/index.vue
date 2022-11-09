<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <el-form-item label="启用:" prop="username">
              <el-select v-model="form.username" placeholder="请选择" class="w">
                <el-option label="是" value="shanghai"></el-option>
                <el-option label="否" value="beijing"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="邮件签名:" prop="content">
              <vab-quill
                v-model="form.content"
                :min-height="200"
                :options="options"
              ></vab-quill>
            </el-form-item>

            <el-alert title="配置参数" type="success" :closable="false">
              用户名: (Username) 部门: (Organization) 姓名: (ChineseName) 电话:
              (Phone) 邮箱: (Email) 职位: (Position) 座机: (Landline) 位置:
              (Location)
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
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'emailnametemplate',
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
          content: '',
        },
        rules: {
          username: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
          content: [
            {
              required: true,
              message: '请输入内容',
              trigger: 'blur',
            },
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
        this.$refs['form'].validate((valid) => {
          this.$refs.form.validateField('content', (errorMsg) => {
            this.borderColor = '#dcdfe6'
            if (errorMsg) {
              this.borderColor = '#F56C6C'
            }
          })
          if (valid) {
            this.$baseMessage('submit!', 'success')
            this.close()
          } else {
            return false
          }
        })
      },
    },
  }
</script>
