<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close">
    <el-descriptions class="margin-top" title="" :column="1" border>
      <el-descriptions-item>
        <template slot="label">名称</template>
        {{ row.group_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">英文名称</template>
        {{ row.en_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">排序</template>
        {{ row.sort }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">状态</template>
        <el-tag type="success" v-if="row.is_enable == 1">使用</el-tag>
        <el-tag type="danger" v-else>禁用</el-tag>
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
