<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-descriptions
      class="margin-top"
      title=""
      :column="1"
      border
    >

      <el-descriptions-item>
        <template slot="label">

          名称
        </template>
        {{form.name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          状态
        </template>
        {{form.is_enable == 0 ? '启用' : '禁用'}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          排序
        </template>
        {{form.sort}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          描述
        </template>
        {{form.code}}
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
        form: {},
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {

    },
    methods: {
      showView(row) {
        this.title = '查看详情'
        if (row){
          this.form = Object.assign({}, row)
        }
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
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 100px;
  }
</style>
