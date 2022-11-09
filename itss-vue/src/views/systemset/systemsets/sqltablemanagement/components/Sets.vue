<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="统计表格最大行数" prop="username" class="tsitem">
        <el-input
          v-model.trim="form.username"
          autocomplete="off"
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
  import {doEdit} from '@/api/userManagement'

  export default {
    name: 'companyEdit',
    components: {},
    data() {
      return {
        disabled: false,
        value1: '',
        value2: '',
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
        this.isDisable = false;
        if (!row) {
          this.title = '配置'
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
              const data = await doEdit(this.form)
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

