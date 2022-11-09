<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="备注" prop="isac">
        <vab-quill
          v-model="form.proposal"
          :min-height="200"
          :options="options"
        ></vab-quill>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {evaluateAdd} from '@/api/eventSelfService'
  import vabQuill from '@/plugins/vabQuill'

  // 评价 未解决备注
  export default {
    name: 'eventSolvenot',
    components: {vabQuill},
    data() {
      return {
        form: {
          username: '',
          isac: false,
          remakes: '',
          proposal: '',
          id: ''
        },
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
        rules: {
          username: [{required: true, trigger: 'blur', message: '请输入名称'}],
          isac: [
            {required: true, trigger: 'blur', message: '请选择是否事件工程师'},
          ],
          remakes: [{required: true, trigger: 'blur', message: '请输入描述'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {
      showEdit(row) {
        this.title = '未解决备注'
        this.dialogFormVisible = true;
        this.form.id = row;
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            console.log(this.form)
            const {msg} = await evaluateAdd(this.form)
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')
            this.close();
            this.closeall();
          } else {
            return false
          }
        })
      },
    },
  }
</script>
