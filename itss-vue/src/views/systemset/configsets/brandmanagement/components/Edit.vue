<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="id" prop="id" hidden>
        <el-input v-model.trim="form.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="brand_name">
        <el-input v-model.trim="form.brand_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="排序"
        prop="sort"
      >
        <el-input v-model.trim="form.sort"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toSave} from '@/api/assetsBrand'

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
        loadingText: '保存',
        form: {
          id: '',
          brand_name: '',
          sort: '',
        },
        rules: {
          brand_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
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
        this.loadingText = '保存'
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
        this.loading = true
        this.loadingText = '保存中...'
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const data = await toSave(this.form)
            if (data.code == 0) {
              this.$baseMessage(data.msg, 'success');
              this.$emit('fetch-data');
              this.close();
            } else {
              this.$baseMessage("失败", 'error');
              this.loading = false
              this.loadingText = '保存'
            }
          } else {
            this.loading = false
            this.loadingText = '保存'
            return false
          }
        })
      },
    },
  }
</script>

