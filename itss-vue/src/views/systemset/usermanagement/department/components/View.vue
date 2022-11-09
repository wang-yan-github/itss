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
          <i class="el-icon-tickets"></i>
          名称
        </template>
        {{ form.name }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-office-building"></i>
          公司
        </template>
        {{ form.companyName }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-notebook-2"></i>
          编码
        </template>
        {{ form.code }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-open"></i>
          状态
        </template>
        {{ form.is_use==0?'禁用':'启用'}}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label">
          <i class="el-icon-bangzhu"></i>
          快速报障支持群组
        </template>
        {{ form.groupName }}
      </el-descriptions-item>

    </el-descriptions>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    getSysDepartmentInfo,
  } from '@/api/userManagement'

  export default {
    name: 'departmentview',
    data() {
      return {
        form: {
          name: '',
          code: '',
          companyName: '',
          is_use: '',
          groupName: '',
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      async showView(row) {
        this.title = '部门信息查看'
        // this.form = Object.assign({}, row)
        this.getSysDepartmentInfo(row.id)
        this.dialogFormVisible = true
      },
      async getSysDepartmentInfo(id) {
        const {data} = await getSysDepartmentInfo({id: id});
        this.form = data.data;
        // this.form.is_use = data.data.is_use == '1' ? '1' : '0';
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val
        this.fetchData()
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
  ::v-deep .my-label {
    width: 140px;
  }
</style>
