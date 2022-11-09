<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-descriptions
      class="margin-top"
      title=""
      :column="3"
      border
    >
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          名称
        </template>
        {{ form.impact_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-sort"></i>
          排序
        </template>
        {{ form.sort }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-collection-tag"></i>
          状态
        </template>
        {{ form.is_use=='0'?'禁用':'启用' }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-reading"></i>
          描述
        </template>
        {{ form.remark }}
      </el-descriptions-item>

    </el-descriptions>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getList} from "@/api/affect";

  export default {
    name: 'slaEventImpactView',
    data() {
      return {
        form: {
          impact_name: '',
          is_use: '',
          sort: '',
          default_value: '',
          remark: '',
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    methods: {
      async showView(row) {
        this.title = '查看'
        this.form = Object.assign({}, row)
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
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 100px;
  }
</style>
