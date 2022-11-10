<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="600px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="名称" prop="solution_name">
        <el-input v-model.trim="form.solution_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设为默认值" prop="is_default">
        <el-select v-model.trim="form.is_default" class="w">
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="描述" prop="">
        <el-input v-model.trim="form.remark" autocomplete="off" type="textarea" :rows="10"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    doEdit, save, edit
  } from '@/api/eventSolutionType'

  export default {
    name: 'eventSolutionTypeedit',
    data() {
      return {
        isDisable: false,
        form: {
          username: '',
          status: '',
          num: '',
          isac: '',
          remakes: '',
          solution_name: "",
          is_use: "",
          is_default: "",
          remark: ""
        },
        rules: {
          solution_name: [{
            required: true,
            trigger: 'blur',
            message: '请输入名称'
          }],
          is_use: [{
            required: true,
            trigger: 'blur',
            message: '请选择状态'
          }],
          is_default: [{
            required: true,
            trigger: 'blur',
            message: '请选择是否默认值'
          }],

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
        var that = this;
        that.$refs['form'].validate(async (valid) => {
          if (valid) {

            if (that.form.id == null || that.form.id == undefined || that.form.id == "") {
              save(that.form).then((res) => {
                if (res.code == 0) {
                  that.$baseMessage("成功", 'success')
                  that.$emit('fetch-data')
                  that.close()
                } else {
                  that.$baseMessage(res.msg, 'error');
                  that.isDisable = false;
                }
              });

            } else {
              edit(that.form).then((res) => {
                if (res.code == 0) {
                  that.$baseMessage("成功", 'success')
                  that.$emit('fetch-data')
                  that.close()
                } else {
                  that.$baseMessage(res.msg, 'error');
                  that.isDisable = false;
                }
              });
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
