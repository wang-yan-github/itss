<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close">
    <el-descriptions class="margin-top" title="" :column="1" border>
      <el-descriptions-item>
        <template slot="label">名称</template>
        {{ row.os_licence_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">部门</template>
        {{ row.deptName }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">key</template>
        {{ row.key_license }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">使用限制</template>
        {{ row.use_limit }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">开始时间</template>
        {{ row.start_time }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">结束时间</template>
        {{ row.end_time }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">软件</template>
        {{ row.software }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">有无期限</template>
        <el-tag size="small" type="success" v-if="row.is_limit == 0">有期限</el-tag>
        <el-tag size="small" type="warning" v-else>无期限</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">描述</template>
        {{ row.description }}
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
