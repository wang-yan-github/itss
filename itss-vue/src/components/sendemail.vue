<!--发邮件 暂未开发/用到-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="发件人">
        <el-select v-model="form.remakes" class="w">
          <el-option label="123@126.com" value="0"></el-option>
          <el-option label="123@126.com" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收件人">
        <el-select v-model="form.remakes" class="w" multiple>
          <el-option label="123@126.com" value="0"></el-option>
          <el-option label="123@126.com" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="抄送">
        <el-select v-model="form.remakes" class="w" multiple filterable>
          <el-option label="123@126.com" value="0"></el-option>
          <el-option label="123@126.com" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="标题">
        <el-input v-model="form.remakes"></el-input>
      </el-form-item>
      <el-form-item label="内容">
        <vab-quill
          v-model="form.remakes"
          :min-height="200"
          :options="options"
        ></vab-quill>
      </el-form-item>
      <el-form-item label="附件">
        <el-upload
          class="upload-button"
          action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
          multiple
          :limit="3"
          :on-exceed="handleExceed"
          :file-list="fileList"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        <p class="ts">附件大小上限:40.0MB</p>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">发 送</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/userManagement'
import vabQuill from '@/plugins/vabQuill'
export default {
  name: 'sendemail',
  components: { vabQuill },
  data() {
    return {
      form: {
        username: '',
        isac: false,
        remakes: '',
      },
      fileList: [],
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
        username: [{ required: true, trigger: 'blur', message: '请输入名称' }],
        isac: [
          { required: true, trigger: 'blur', message: '请选择是否事件工程师' },
        ],
        remakes: [{ required: true, trigger: 'blur', message: '请输入描述' }],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    showEdit(row) {
      if (!row) {
        this.title = '发邮件'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
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
          const { msg } = await doEdit(this.form)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
  },
}
</script>
<style lang="scss" scoped>
  ::v-deep{
      .upload-button{display:inline-block;}
  }
</style>

