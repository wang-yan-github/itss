<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="名称" prop="first_name">
        <el-input v-model.trim="form.first_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="num">
        <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="描述" prop="">
        <el-input
          v-model.trim="form.remark"
          autocomplete="off"
          type="textarea"
          :rows="10"
        ></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {slaQuestionToAdd, slaQuestionToEdit} from '@/api/affect'

  export default {
    name: 'slaQuestionFirstedit',
    data() {
      return {
        isDisable: false,
        form: {
          first_name: '',
          is_use: '',
          sort: '',
          remark: '',
        },
        rules: {
          first_name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'}],
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
          this.idx = '1';
        } else {
          this.title = '编辑'
          this.idx = '2';
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
              if (this.idx === '1') {
                const data = await slaQuestionToAdd(this.form)
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage(data.msg, 'error');
                  this.isDisable = false;
                }
              } else {
                const data = await slaQuestionToEdit(this.form)
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage(data.msg, 'error');
                  this.isDisable = false;
                }
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
