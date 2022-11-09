<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close">
    <el-descriptions class="margin-top" title="" :column="2" border>
      <el-descriptions-item>
        <template slot="label">姓名</template>
        {{ row.contact_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">联系人公司</template>
        {{ row.company_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">我司对接部门</template>
        {{ row.deptName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">状态</template>
        <el-tag size="small" type="danger" v-if="row.is_enable == 0">禁用</el-tag>
        <el-tag size="small" type="success" v-else>使用</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">phone</template>
        {{ row.phone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">座机</template>
        {{ row.telephone }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">是否接收通知</template>
        <el-tag size="small" type="success" v-if="row.is_receive_notice == 0">接受通知</el-tag>
        <el-tag size="small" type="danger" v-else>不接受通知</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">联系地址</template>
        {{ row.contact_address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">备注</template>
        {{ row.remark }}
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
        row: {},
        title: '',
        dialogFormVisible: false,
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {

    },
    methods: {
      showView(row) {
        this.title = '查看详情'
        this.dialogFormVisible = true
        this.row = row;
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
