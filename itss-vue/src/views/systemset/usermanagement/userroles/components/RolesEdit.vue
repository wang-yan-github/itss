<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="是否事件工程师" prop="is_incident_engineer">
        <el-select v-model.trim="form.is_incident_engineer" class="w">
          <el-option label="否" value="0"></el-option>
          <el-option label="是" value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="描述" prop="description">
        <el-input
          v-model.trim="form.description"
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
  import {doEdit, updateRole} from '@/api/userManagement'

  export default {
    name: 'RolesEdit',
    data() {
      return {
        isDisable: false,
        form: {
          name: '',
          is_incident_engineer: '',
          description: '',
        },
        rules: {
          name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
          is_incident_engineer: [{required: true, trigger: 'blur', message: '请选择是否事件工程师'}],
          description: [
            {required: true, trigger: 'blur', message: '请输入描述'},
          ],
        },
        title: '',
        idx: '',
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
          this.form = row;
          this.form.is_incident_engineer = row.is_incident_engineer + '';
          this.form = Object.assign({}, this.form)
          this.idx = '2';
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
          console.log(valid)
          if (valid) {
            try{
              if (this.idx === '1') {
                const data = await doEdit(this.form)
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage("失败", 'error');
                  this.isDisable = false;
                }
              } else {
                const data = await updateRole(this.form)
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage("失败", 'error');
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
