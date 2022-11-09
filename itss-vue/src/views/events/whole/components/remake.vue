<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="备注">
        <el-checkbox v-model="form.isac" label="通知用户"></el-checkbox>
      </el-form-item>
      <el-form-item label="" prop="remark">
        <vab-quill
          v-model="form.remark"
          :min-height="200"
          :options="options"
        ></vab-quill>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loadingbut" @click="save">保 存</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    updateRemark
  } from '@/api/event'
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'eventRemark',
    components: {vabQuill},
    data() {
      return {
        loadingbut: false,//默认提交按钮可点击
        form: {
          id: '',
          remark: '',
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
          remark: [{required: true, trigger: 'blur', message: '请输入备注'}],
          // isac: [
          //   {required: true, trigger: 'blur', message: '请选择是否事件工程师'},
          // ],
          // remakes: [{required: true, trigger: 'blur', message: '请输入描述'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {
      showEdit(row) {
        console.log("备注")
        console.log(row)
        this.title = '备注';
        this.form.id = row.id;
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        console.log("save")
        console.log(this.form)
        this.$refs['form'].validate(async (valid) => {
          this.loadingbut = true;
          if (valid) {
            const {msg} = await updateRemark(this.form)
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')
            this.close()
          } else {
            this.loadingbut = false;
            return false
          }
        })
      },
    },
  }
</script>

