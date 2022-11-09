<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1000px"
    @close="close"
    top="50px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="10">
        <el-form-item label="标题" prop="titles">
          <el-input v-model.trim="form.titles" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <vab-quill
            v-model="form.content"
            :min-height="200"
            :options="options"
          ></vab-quill>
          <!-- <vueUeditor></vueUeditor>  -->
        </el-form-item>

        <el-form-item label="额外抄送" prop="ewcs">
          <el-input v-model.trim="form.ewcs" autocomplete="off"></el-input>
          <p class="ts">
            抄送仅限于发送邮件时，多个请用半角分号分隔
          </p>

        </el-form-item>

        <el-form-item label="通知方式" prop="isac">
          <el-radio v-model="form.isac" label="1">发送</el-radio>
          <el-radio v-model="form.isac" label="2">不发送</el-radio>
        </el-form-item>
        <el-form-item label="">
          <p class="ts">
            注：合同到续约提醒时间后，发提醒给相关负责的人（可管理该合同的人）
          </p>
        </el-form-item>
      </el-row>
    </el-form>
    <el-alert title="配置参数" :closable="false" type="success">
      合同名称: (Name) 部门: (Organization) 金额: (Cost)
      开始时间: (StartDate) 结束时间: (EndDate) 续约提醒时间: (NoticeTime)
      币种: (Currency) 客户名称: (Customer) ID (ID)
    </el-alert>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import vabQuill from '@/plugins/vabQuill'
  // import vueUeditor from 'vue-ueditor'
  export default {
    name: 'companyviews',
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
        title: '通知配置',
        dialogFormVisible: false,
        form: {
          titles: '',
          ewcs: '',
          content: '',
          isac: '',
        },
        rules: {
          titles: [
            {
              required: true,
              message: '请输入标题',
              trigger: 'blur',
            },
          ],
          ewcs: [
            {
              required: true,
              message: '请输入额外抄送',
              trigger: 'change',
            },
          ],
          isac: [
            {
              required: true,
              message: '请选择通知方式',
              trigger: 'change',
            },
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
    methods: {
      showView() {
        this.title = '通知配置'
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
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

