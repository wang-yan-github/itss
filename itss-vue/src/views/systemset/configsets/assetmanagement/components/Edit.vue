<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="id" prop="id" hidden>
        <el-input v-model.trim="form.id" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="名称" prop="status_name">
        <el-input v-model.trim="form.status_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_enable">
        <el-select v-model.trim="form.is_enable" class="w">
          <el-option label="禁用" value="0"></el-option>
          <el-option label="启用" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="报废" prop="is_scrap">
        <el-select v-model.trim="form.is_scrap" class="w">
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model.trim="form.description" type="textarea" :rows="10"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toSave} from '@/api/assetsStatusManage'

  export default {
    name: 'assetedit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        loading: false,
        loadingText: '保 存',
        form: {
          id: '',
          status_name: '',
          is_enable: '',
          is_scrap: '',
          description: '',
        },
        rules: {
          status_name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
          is_enable: [{required: true, trigger: 'blur', message: '请选择状态'}],
          is_scrap: [
            {required: true, trigger: 'blur', message: '请选择是否报废'},
          ],
          description: [
            {required: true, trigger: 'blur', message: '请输入描述'},
          ],
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
        this.loadingText = '保 存'
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.is_enable = row.is_enable + ''
          this.form.is_scrap = row.is_scrap + ''
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
              this.loading = false
              this.loadingText = '保 存'
            }
          } else {
            this.loading = false
            this.loadingText = '保 存'
            return false
          }
        })
      },
    },
  }
</script>

