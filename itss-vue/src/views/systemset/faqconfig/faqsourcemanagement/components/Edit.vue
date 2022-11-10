<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="使用" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="使用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="描述">
        <el-input v-model.trim="form.description" autocomplete="off" :rows="5" type="textarea"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {doEdit} from '@/api/questionSet'

  export default {
    name: 'assetedit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        isDisable: false,
        form: {
          name: '',
          is_use: '',
          description: '',
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_use: [
            {required: true, trigger: 'blur', message: '请选择是否使用'},
          ],
          description: [{required: true, trigger: 'blur', message: '请输入描述'}],
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
        this.$refs['form'].resetFields();
        this.form = this.$options.data().form;
        this.dialogFormVisible = false;
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await doEdit(this.form);
              console.log(data);
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.$emit('fetch-data');
                this.close();
              } else {
                this.$baseMessage(data.msg, 'error');
                this.isDisable = false;
              }
            }catch(e){
               this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false;
          }
        })
      },
    },
  }
</script>

