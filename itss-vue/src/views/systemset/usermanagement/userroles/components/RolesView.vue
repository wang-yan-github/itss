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
        {{ form.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          描述
        </template>
        {{ form.description }}
      </el-descriptions-item>

    </el-descriptions>
    <el-table
      v-loading="listLoading"
      :data="tableData"
      border
      :element-loading-text="elementLoadingText"
      height="400"
      row-key="id"
      style="margin-top: 20px"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column
        show-overflow-tooltip
        prop="name"
        width="250px"
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="path"
        label="功能路径"
        align="center"
      ></el-table-column>
    </el-table>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getSysAuthorityWithRole} from '@/api/userManagement'

  export default {
    name: 'userroles',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableData: [],
        form: {
          id: '',
          name: '',
          path: '',
          user_type: '',
          description: '',
          children: [],
        },
      }
    },
    created() {
      // setTimeout(() => {
      //   this.fetchData()
      //   this.listLoading = false
      // }, 300)
    },
    methods: {
      showView(row) {
        this.title = '权限查看'
        this.dialogFormVisible = true;
        if (row) {
          this.form = row
          this.fetchData()
        }
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
      fetchData() {
        // const roleId = this.$route.query.id
        const roleId = this.form.id;
        getSysAuthorityWithRole({"roleId": roleId}).then(res => {
          this.tableData = res.data
          this.listLoading = false
        })
        // const { data } = await getSysAuthorityWithRole({ roleId })
        // this.tableData = data
        // console.log(this.tableData)
      },
    },
  }
</script>
