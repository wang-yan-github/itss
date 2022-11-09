<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="备注">
        <el-checkbox label="通知用户"></el-checkbox>
      </el-form-item>
      <el-form-item label="描述" prop="changeInfo.remark">
        <vab-quill
          v-model="form.changeInfo.remark"
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
import { toRemark } from '@/api/change'
import vabQuill from '@/plugins/vabQuill'
export default {
  name: 'assetedit',
  components: { vabQuill },
  data() {
    return {
      form: {
        changeInfo:{id:'',remark:''}
        
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
        'changeInfo.remark': [{ required: true, trigger: 'blur', message: '请输入描述' }],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showEdit(row) {
       console.log(row);
      this.form.changeInfo.id=row.id;
      if(row.remark==null||row.remark==''){
        
       console.log(1111);
      }else{
         this.form.changeInfo.remark=row.remark;
      }

      
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
          const { msg } = await toRemark(this.form)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.close()
         }
       })
    },
  },
}
</script>

