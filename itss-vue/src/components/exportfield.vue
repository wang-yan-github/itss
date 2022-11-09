<!--选择导出列 暂未开发/用到-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      label-width="80px"
      label-position="top"
    >
      <el-form-item label="">
        <el-checkbox v-model="form.isac" label="全选"></el-checkbox>
      </el-form-item>
      <div class="treeborder" style="height: 300px;">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
           <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="编号"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="服务群组"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox v-model="form.isac" label="解决工程师"></el-checkbox>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item>
              <el-checkbox
                v-model="form.isac"
                label="上次更新时间"
              ></el-checkbox>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/userManagement'
import vabQuill from '@/plugins/vabQuill'
export default {
  name: 'exportfield',
  components: { vabQuill },
  data() {
    return {
      form: {
        username: '',
        isac: false,
        remakes: '',
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
    showEdit(row) {
      if (!row) {
        this.title = '选择导出列'
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
  },
}
</script>

