<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-descriptions
      class="margin-top"
      title="盘点期详情信息"
      :column="1"
      :size="size"
      border
    >
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-s-order"></i>
          名称
        </template>
        {{ form.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-collection-tag"></i>
          状态
        </template>

        <div v-if="form.status == 1">未开始</div>
        <div v-if="form.status == 2">已开始</div>
        <div v-if="form.status == 3">已完成</div>
        <div v-if="form.status == 4">作废</div>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-time"></i>
          创建时间
        </template>
        {{ form.create_time }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          创建人
        </template>
        {{ form.createUser }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-time"></i>
          修改时间
        </template>
        {{ form.update_time }}

      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          修改人
        </template>
        {{ form.updateUser }}
      </el-descriptions-item>

    </el-descriptions>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'pandianqiView',
  data() {
    return {
      size: 'small',
      form: {
        name: '',
        status: '',
        create_time: '',
        create_user: '',
        createUser: '',
        update_time: '',
        update_user: '',
        updateUser: '',
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  methods: {
    showView(row) {
      this.form = Object.assign({},this.form, row)
      this.title = '查看'
      this.dialogFormVisible = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
  },
}
</script>
