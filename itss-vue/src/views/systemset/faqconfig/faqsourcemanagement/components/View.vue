<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close">
    <el-descriptions class="margin-top" title="" :column="3" border>
      <el-descriptions-item>
        <template slot="label">名称</template>
        kooriookami
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">状态</template>
        18100000000
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">报废</template>
        苏州市
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">描述</template>
        <el-tag size="small">学校</el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: 'assetviews',
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
        this.title = '查看详情'
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
