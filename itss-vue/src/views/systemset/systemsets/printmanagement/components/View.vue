<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="600px"
    @close="close"
  >
    <el-descriptions
      class="margin-top"
      title=""
      :column="2"
      border
    >

      <el-descriptions-item>
        <template slot="label">

          编号
        </template>
        {{form.id}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          模板名称
        </template>
        {{form.print_name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          状态
        </template>
        {{form.is_use == 0 ? '停用' : '使用'}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          模块
        </template>
        {{form.module}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          序号
        </template>
        {{form.order_num}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          内容
        </template>
        <div class="ql-editor content-detail" v-html="form.content"></div>

      </el-descriptions-item>

    </el-descriptions>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
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
        form: {},
      }
    },
    created() {

    },
    methods: {
      showView(row) {
        this.title = '查看详情'
        this.dialogFormVisible = true
        this.form = Object.assign({}, row)
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
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 80px;
  }
  .content-detail{
    padding-top: 7px;
    padding-left: 0;
  }
</style>
