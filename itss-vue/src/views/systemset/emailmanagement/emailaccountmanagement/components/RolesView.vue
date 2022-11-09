<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="收件人" prop="username">
        <el-input
          v-model.trim="form.username"
          autocomplete="off"
        ></el-input>
        <p class="ts">注：填写测试邮箱可发送邮件，多个请用;分隔</p>
      </el-form-item>

    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: 'userroles',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        form: {
          username: '',
          isac: '',
          remakes: '',
        },
      }
    },
    created() {
    },
    methods: {
      showView() {
        this.title = '发送测试'
        this.dialogFormVisible = true
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.$baseMessage('模拟保存成功', 'success')
        this.$emit('fetch-data')
        this.close()
      },
    },
  }
</script>
