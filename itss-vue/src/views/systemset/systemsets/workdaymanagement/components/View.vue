<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
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
        {{row.id}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          名称
        </template>
        {{row.work_name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          状态
        </template>
        {{row.is_use == 1 ? '使用' : '停用'}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          默认
        </template>
        {{row.acquiesce == 1 ? '否' : '是'}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          上午上班时间
        </template>
        {{row.start_time_AM}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          上午下班时间
        </template>
        {{row.end_time_AM}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          下午上班时间
        </template>
         {{row.start_time_PM}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          下午下班时间
        </template>
        {{row.end_time_PM}}
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
        row: {}
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
    width: 110px;
  }
</style>
