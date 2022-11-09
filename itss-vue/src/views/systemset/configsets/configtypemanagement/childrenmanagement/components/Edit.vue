<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_enable">
        <el-select v-model.trim="form.is_enable" class="w">
          <el-option label="启用" value="0"></el-option>
          <el-option label="禁止" value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="编码" prop="code">
        <el-input v-model.trim="form.code" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="排序" prop="sort">
        <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toAdd} from '@/api/assetsChildren'

  export default {
    name: 'companyEdit',
    data() {
      return {
        isDisable: false,
        form: {
          id: '',
          name: '',
          is_enable: '',
          code: '',
          sort: '',
          assets_type_id: '',
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_enable: [
            {required: true, trigger: 'blur', message: '请选择状态'},
          ],
          code: [{required: true, trigger: 'blur', message: '请输入编码'}],
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
              const assets_type_id = this.$route.query.assets_type_id
              this.form.assets_type_id = assets_type_id
              this.form.id = this.$route.query.id
              const data = await toAdd(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.$emit('fetch-data')
                this.$router.push({path: "/set/childrenmanagement", query: {id: this.form.assets_type_id}})
                this.close()
                this.$router.go(0)
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

