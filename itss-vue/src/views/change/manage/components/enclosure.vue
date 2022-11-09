<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="附件:">
        <el-dropdown split-button type="primary" @click="screenClick" @command="screenCommand">
          截屏
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="a">
              截屏时隐藏浏览器
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
        <el-upload class="upload-button" action="https://jsonplaceholder.typicode.com/posts/"
          :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3"
          :on-exceed="handleExceed" :file-list="fileList" style="display: inline">
          <el-button size="small" type="primary">
            点击上传
          </el-button>
        </el-upload>
        <p class="ts">附件大小上限:20.0MB</p>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    doEdit
  } from '@/api/userManagement'
  import vabQuill from '@/plugins/vabQuill'
  export default {
    name: 'assetedit',
    components: {
      vabQuill
    },
    data() {
      return {
        form: {
          username: '',
          isac: false,
          remakes: '',
        },
        fileList: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],
        options: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{
                header: [1, 2, 3, 4, 5, 6, false]
              }],
              [{
                size: ['small', false, 'large', 'huge']
              }],
              [{
                color: []
              }, {
                background: []
              }],
              ['blockquote', 'code-block'],
              [{
                list: 'ordered'
              }, {
                list: 'bullet'
              }],
              [{
                script: 'sub'
              }, {
                script: 'super'
              }],
              [{
                indent: '-1'
              }, {
                indent: '+1'
              }],
              [{
                align: []
              }],
              [{
                direction: 'rtl'
              }],
              [{
                font: []
              }],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
        borderColor: '#dcdfe6',
        rules: {
          username: [{
            required: true,
            trigger: 'blur',
            message: '请输入名称'
          }],
          isac: [{
            required: true,
            trigger: 'blur',
            message: '请选择是否事件工程师'
          }, ],
          remakes: [{
            required: true,
            trigger: 'blur',
            message: '请输入描述'
          }],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    methods: {
      showEdit(row) {
        this.title = '修改附件'
        this.dialogFormVisible = true
      },
      screenCommand(){

      },
      screenClick(){

      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const {
              msg
            } = await doEdit(this.form)
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

<style lang="scss" scoped>
  ::v-deep {
    .upload-button {
      .el-upload {
        display: inline-block;
        margin-right: 10px;
      }
    }
  }
</style>
