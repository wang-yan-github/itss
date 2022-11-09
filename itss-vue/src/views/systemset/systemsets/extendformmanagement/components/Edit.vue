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

      <el-form-item label="父级表单" prop="parent_form_id">
        <el-input
          placeholder="请输入内容"
          v-model="form.parent_form_name"
          @click.native="openwin"
        >
          <!--<el-button-->
            <!--slot="append"-->
            <!--icon="el-icon-search"-->
            <!--@click="openwin"-->
          <!--&gt;</el-button>-->
        </el-input>
      </el-form-item>

      <el-form-item label="类型" prop="type">
        <el-select v-model.trim="form.type" class="w">
          <el-option label="工单" value="0"></el-option>
          <el-option label="问题" value="1"></el-option>
          <el-option label="变更" value="2"></el-option>
          <el-option label="发布" value="3"></el-option>
          <el-option label="配置项" value="4"></el-option>
          <el-option label="知识" value="5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="停用" value="0"></el-option>
          <el-option label="使用" value="1"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <children
      ref="children"
      @deptData="getParent"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
  </el-dialog>
</template>

<script>
  import {addForm} from '@/api/assetsExpandForm'
  import Children from './contactpeople'

  export default {
    name: 'companyEdit',
    components: {Children},
    data() {
      return {
        isDisable: false,
        input2: '',
        value1: '',
        value2: '',
        form: {
          id: '',
          name: '',
          type: '',
          parent_form_id: '0',
          parent_form_name: '',
          is_use: '',
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          type: [
            {required: true, trigger: 'blur', message: '请选择类型'},
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
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.type = row.type + "";
          this.form.is_use = row.is_use + "";
          this.form.parent_form_name = row.parent_form_name;
          this.form.parent_form_id = row.parent_form_id;
        }
        this.dialogFormVisible = true
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      close() {
        this.$refs['form'].resetFields()
        this.$emit('fetch-data');
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            console.log(this.form);
            const data = await addForm(this.form)
            if (data.code == 0) {
              this.$baseMessage(data.msg, 'success');

              this.close();
            } else {
              this.$baseMessage("失败", 'error');
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      getParent(row) {
        console.log(row);
        this.form.parent_form_id = row.id;
        this.form.parent_form_name = row.name;
      }
    },
  }
</script>

