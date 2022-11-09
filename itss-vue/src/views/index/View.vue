<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
    @close="close"
    append-to-body
  >
    <el-descriptions
      class="margin-top"
      title=""
      :column="1"
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

          状态
        </template>
        {{form.is_use == 0 ? '停用' : '使用'}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          可见范围
        </template>
        {{form.scope == 0 ? '公开' : '指定人员 角色 部门'}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          是否置顶
        </template>
        {{form.is_top == 0 ? '是' : '否'}}
      </el-descriptions-item>


      <el-descriptions-item>
        <template slot="label">

          公告有效截止日期
        </template>
        {{form.effective_date}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          内容
        </template>
        <div v-html="form.content">

        </div>

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
        console.log(row);
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
