<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="中文名称" prop="group_name">
        <el-input v-model.trim="form.group_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="英文名称" prop="en_name">
        <el-input v-model.trim="form.en_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="状态"
        prop="is_enable"
      >
        <el-select v-model.trim="form.is_enable" class="w">
          <el-option label="禁用" value="0"></el-option>
          <el-option label="启用" value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item
        label="排序"
        prop="sort"
      >
        <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
      </el-form-item>


    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toSave} from '@/api/assetsClGroup'

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
          id: '',
          group_name: '',
          en_name: '',
          is_enable: '',
          sort: '',
        },
        rules: {
          group_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          en_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_enable: [{required: true, trigger: 'blur', message: '请选择状态'}],
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
          this.form.is_enable = row.is_enable + "";
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
              this.loadingText = '确 定'
            }
          } else {
            this.loading = false
            this.loadingText = '确 定'
            return false
          }
        })
      },
    },
  }
</script>

