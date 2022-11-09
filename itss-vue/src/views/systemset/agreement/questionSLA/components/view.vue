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
      :column="2"
      border
    >
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-postcard"></i>
          名称
        </template>
        {{ form.sla_question_name }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-collection-tag"></i>
          状态
        </template>
        {{ form.is_use=='0' ?'禁用':'启用' }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-reading"></i>
          描述
        </template>
        {{ form.remark }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-sort"></i>
          考虑优先级
        </template>
        {{ form.is_first=='0' ?'否':'是' }}
      </el-descriptions-item>

    </el-descriptions>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  export default {
    name: 'slaQuestionview',
    data() {
      return {
        form: {
          sla_question_name: '',
          is_first: '',
          is_use: '',
          remark: '',
        },
        title: '',
        dialogFormVisible: false,
        list: null,
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableH: 0
      }
    },
    created() {
      let h = document.documentElement.clientHeight || document.body.clientHeight;
      this.tableH = h - 240 + 'px';
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
<style>
  .my-label {
    width: 100px !important;
  }

  .margin-top {
    margin-bottom: 20px;
  }
</style>
