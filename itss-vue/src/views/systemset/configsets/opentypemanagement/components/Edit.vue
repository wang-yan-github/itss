<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="名称" prop="username">
        <el-input v-model.trim="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="状态"
        prop="isac"
      >
        <el-select v-model.trim="form.isac" class="w">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item
        label="排序"
        prop="isac"
      >
        <el-input v-model.trim="form.username" autocomplete="off"></el-input>
      </el-form-item>


    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {doEdit} from '@/api/userManagement'

  export default {
    name: 'companyEdit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        loading: false,
        loadingText: '确 定',
        form: {
          username: '',
          isac: '',
          remakes: '',
        },
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
        this.loading = false
        this.loadingText = '确 定'
        if (!row) {
          this.title = '添加'
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
            this.loading = true
            this.loadingText = '提交中...'
            const {msg} = await doEdit(this.form)
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')
            this.close()
            this.loading = false
            this.loadingText = '确 定'
          } else {
            return false
          }
        })
      },
    },
  }
</script>

