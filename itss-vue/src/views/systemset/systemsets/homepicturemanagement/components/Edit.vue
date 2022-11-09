<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="标题" prop="title">
            <el-input
              v-model.trim="form.title"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="描述" prop="des">
            <el-input
              v-model.trim="form.des"
              autocomplete="off"
              type="textarea"
              :rows="5"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="使用" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="停用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="排序" prop="pic_sort">
            <el-input v-model.trim="form.pic_sort"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="图片长度" prop="pic_length">
            <el-input v-model.trim="form.pic_length"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="图片宽度" prop="pic_width">
            <el-input v-model.trim="form.pic_width"></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="图片" prop="pic_id">
            <el-upload
              class="upload-demo"
              drag
              action="https://jsonplaceholder.typicode.com/posts/"
              multiple
              :auto-upload="false"
              :on-change="onChange"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :file-list="fileList"
              style="width: 100%"
            >
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将文件拖到此处，或
                <em>点击上传</em>
              </div>
              <div class="el-upload__tip" slot="tip">
                只能上传jpg/png文件，且不超过500kb
              </div>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toAdd} from '@/api/sysBackgroundPic'
  import {download} from '@/api/file'

  export default {
    name: 'companyEdit',
    components: {},
    data() {
      return {
        isDisable: false,
        fileList: [],
        value1: '',
        value2: '',
        form: {
          id: '',
          title: '',
          des: '',
          is_use: '',
          pic_sort: '',
          pic_length: '',
          pic_width: '',
          pic_id: '',
        },
        rules: {
          title: [{required: true, trigger: 'blur', message: '请输入标题'}],
          // isac: [
          //   { required: true, trigger: 'blur', message: '请选择是否事件工程师' },
          // ],
          des: [{required: true, trigger: 'blur', message: '请输入描述'}],
          is_use: [{required: true, trigger: 'blur', message: '使用为必选项'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          if (row.fileManageList) {
            row.fileManageList.forEach((x) => {
              this.fileList.push({name: x.file_name, url: x.id})
            })
          }
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.fileList = [];
        this.dialogFormVisible = false
        this.$router.push({path: '/set/homeppmanagement'})

      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {

            if (this.fileList.length > 1) {
              this.$baseMessage('只能上传一个文件', 'error')
              this.isDisable = false;
              return false;
            }
            // if (this.title != '添加'){
            //   this.form.id = this.$route.query.id
            // }

            const params = new FormData();
            if (this.fileList.length > 0) {
              const f = this.fileList[0]
              if (f.status == 'ready') {
                params.append('file', f.raw);
              }
            }

            params.append('body', JSON.stringify(this.form))

            const data = await toAdd(params)
            if (data.code == 0) {
              this.$baseMessage(data.msg, 'success');
              this.$emit('fetch-data');
              this.close();
            } else {
              this.$baseMessage("失败", 'error');
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      //属性 附件上传 选择文件
      onChange(file, fileList) {
        this.fileList = fileList;

      },
      handlePreview(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', file.name)
          document.body.appendChild(link)
          link.click()
        })
      },
      handleRemove(file, fileList) {
        this.fileList = fileList
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
    },
  }
</script>

