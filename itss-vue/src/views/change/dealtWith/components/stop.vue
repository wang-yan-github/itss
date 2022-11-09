<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="changeInfo" :rules="rules" label-width="80px">
      
      <el-form-item label="挂起" prop="remark">
        <vab-quill
          v-model="changeInfo.remark"
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
import { toSuspend } from '@/api/change'
import vabQuill from '@/plugins/vabQuill'
export default {
  name: 'assetedit',
  components: { vabQuill },
  data() {
    return {
      form: {
        username: '',
        isac: false,
        remakes: '',
      },
      changeInfo:{
        id:'',
        status:'',
        remark:'',
        old_status:''
      },
      options: {
        theme: 'snow',
        bounds: document.body,
        debug: 'warn',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ color: [] }, { background: [] }],
            ['blockquote', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ align: [] }],
            [{ direction: 'rtl' }],
            [{ font: [] }],
            ['clean'],
            ['link', 'image'],
          ],
        },
        placeholder: '内容...',
        readOnly: false,
      },
      borderColor: '#dcdfe6',
      rules: {
        remark: [{ required: true, trigger: 'blur', message: '请输入描述' }],
       
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showEdit(row) {
      console.log(row);
      this.changeInfo.id=row.id;
      this.changeInfo.old_status=row.status;
      this.title = '挂起'
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.changeInfo.status="11";
          const { msg } = await toSuspend(this.changeInfo)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
