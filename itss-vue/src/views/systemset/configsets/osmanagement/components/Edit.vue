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
      <el-form-item label="名称" prop="os_name">
        <el-input v-model.trim="form.os_name" autocomplete="off"></el-input>
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
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toSave} from '@/api/assetsOs'

  export default {
    name: 'companyEdit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        isDisable: false,
        form: {
          id: '',
          os_name: '',
          sort: '',
        },
        rules: {
          os_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
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
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await toSave(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.$emit('fetch-data');
                this.close();
              } else {
                this.$baseMessage("失败", 'error');
                this.isDisable = false;
              }
            }catch(e){
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
    },
  }
</script>

