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
          <i class="el-icon-postcard"></i>
          名称
        </template>
        {{ form.first_name }}
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
        {{ form.is_use }}
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
  export default {
    name: 'slaQuestionFirstview',
    data() {
      return {
        form: {
          first_name: '',
          sort: '',
          is_use: '',
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
        if (this.form.is_use == '0') {
          this.form.is_use = '禁用'
        } else if (this.form.is_use == '1') {
          this.form.is_use = '启用'
        }
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
