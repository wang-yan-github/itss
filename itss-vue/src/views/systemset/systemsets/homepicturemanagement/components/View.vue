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

          标题
        </template>
        {{form.title}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          是否使用
        </template>
        {{form.is_use == 1 ? '使用' : '停用'}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          长度
        </template>
        {{form.pic_length}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          宽度
        </template>
        {{form.pic_width}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          排序
        </template>
        {{form.pic_sort}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          描述
        </template>
        {{form.des}}
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
        form: {}
      }
    },
    created() {

    },
    methods: {
      showView(row) {
        this.title = '查看详情'
        this.dialogFormVisible = true
        this.form = Object.assign({}, row)

        console.log(this.form)
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
        this.$router.push({path: '/set/homeppmanagement'})
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
</style>
