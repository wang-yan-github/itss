<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" label-width="50px">
      <el-form-item label="URL" prop="username">
        <el-input v-model.trim="username" autocomplete="off"></el-input>
        <p class="ts">默认值：http://xx.com/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes
          scanQRCodeAdd</p>
        <p class="ts">参数说明：没有scanQRCodeAdd参数，是否能跳到自助报障页面，且报障类型为对应公司的</p>
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
    name: 'companyviews',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {

    },
    methods: {
      showView() {
        this.title = '二维码配置'
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
