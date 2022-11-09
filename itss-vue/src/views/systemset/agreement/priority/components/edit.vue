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
      <el-form-item label="排序">
        <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="VIP">
        <el-select v-model.trim="form.vip" class="w">
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="描述">
        <el-input
          v-model.trim="form.remark"
          autocomplete="off"
          type="textarea"
          :rows="10"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <p style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">注:</p>
        <p style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
          1、请确认添加的“优先级”已在【影响度紧急度关系管理】中维护了相关的数据，以确保该优先级的正常使用。</p>
        <p style="line-height: 1.4;margin-top: 0; color: #E6A23C;">
          2、若系统中的SLA会因“优先级”的影响而不同，请确认添加的“优先级”已在【SLA管理】中维护了相关的SLA信息，以确保该优先级的事件单可正确地设置SLA。</p>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {priorityToAdd, priorityToEdit, urgencyToAdd, urgencyToEdit} from "@/api/affect";

  export default {
    name: 'slaEventFirstedit',
    data() {
      return {
        isDisable: false,
        form: {
          first_name: '',
          is_use: '',
          sort: '',
          vip: '',
          remark: '',
        },
        rules: {
          first_name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'}],
          // vip: [{required: true, trigger: 'blur', message: '请选择是否VIP'}],
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
                  const data = await priorityToAdd(this.form)
                  if (data.code == 0) {
                    this.$baseMessage(data.msg, 'success');
                    this.$emit('fetch-data');
                    this.close();
                  } else {
                    this.$baseMessage(data.msg, 'error');
                    this.isDisable = false;
                  }
                } else {
                  const data = await priorityToEdit(this.form)
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
