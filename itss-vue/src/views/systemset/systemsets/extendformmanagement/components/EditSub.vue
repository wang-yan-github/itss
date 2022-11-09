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

      <el-form-item label="类型" prop="type">
        <el-select v-model.trim="form.type" class="w">
          <el-option label="文本" value="0"></el-option>
          <el-option label="长文本" value="1"></el-option>
          <el-option label="整数" value="2"></el-option>
          <el-option label="小数" value="3"></el-option>
          <el-option label="日期" value="4"></el-option>
          <el-option label="单选" value="5"></el-option>
          <el-option label="复选" value="6"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="必填" prop="is_required">
        <el-select v-model.trim="form.is_required" class="w">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="启用查询" prop="is_search">
        <el-select v-model.trim="form.is_search" class="w">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="整行显示" prop="is_whole_line">
        <el-select v-model.trim="form.is_whole_line" class="w">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="序号" prop="serial">
        <el-input v-model.trim="form.serial" autocomplete="off"></el-input>
      </el-form-item>
      <!--<el-form-item label="日期格式" prop="remakes">-->
      <!--<el-select v-model.trim="form.isac" class="w">-->
      <!--<el-option label="是" value="0"></el-option>-->
      <!--<el-option label="否" value="1"></el-option>-->
      <!--</el-select>-->
      <!--</el-form-item>-->
      <el-form-item label="状态" prop="is_enable">
        <el-select v-model.trim="form.is_enable" class="w">
          <el-option label="使用" value="0"></el-option>
          <el-option label="禁用" value="1"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toAdd} from '@/api/assetsExpandFormField'

  export default {
    name: 'companyEdit',
    data() {
      return {
        isDisable: false,
        value1: '',
        value2: '',
        val: '',
        form: {
          id: '',
          name: '',
          type: '',
          is_required: '',
          is_search: '',
          is_whole_line: '',
          serial: '',
          is_enable: '',
          form_id: '',
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          type: [
            {required: true, trigger: 'blur', message: '请选择类型'},
          ],
          is_required: [{required: true, trigger: 'blur', message: '是否必填'}],
          is_enable: [{required: true, trigger: 'blur', message: '请选择状态'}],
          is_search: [{required: true, trigger: 'blur', message: '是否查询'}],
          is_whole_line: [{required: true, trigger: 'blur', message: '是否整行显示'}],
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
        if (!row.name) {
          this.title = '添加'
          this.form = Object.assign({}, row)
          this.form.form_id = row.form_id;
          this.val = row.form_id;
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.type = row.type + "";
          this.form.is_required = row.is_required + "";
          this.form.is_search = row.is_search + "";
          this.form.is_whole_line = row.is_whole_line + "";
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
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            console.log(this.form);
            try{
              const data = await toAdd(this.form)
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

