<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-descriptions class="margin-top" title="" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{ form.user_name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          姓名
        </template>
        {{ form.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          状态
        </template>
        <el-tag v-if="form.status == 1">活动</el-tag>
        <el-tag v-else-if="form.status == 2">暂停</el-tag>
        <el-tag v-else-if="form.status == 3">已禁止</el-tag>
        <el-tag v-else>暂无</el-tag>
      </el-descriptions-item>
      <!--<el-descriptions-item>-->
      <!--<template slot="label">-->
      <!--<i class="el-icon-tickets"></i>-->
      <!--类型-->
      <!--</template>-->
      <!--<el-tag size="small" v-show="form.user_type">-->
      <!--{{ form.user_type }}-->
      <!--</el-tag>-->
      <!--</el-descriptions-item>-->
    </el-descriptions>
    <el-table
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
  import {getSysAuthority} from '@/api/userManagement'

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
          user_name: '',
          name: '',
          status: '',
          user_type: '',
          sysRoles: [],
        },
      }
    },
    created() {
      // setTimeout(() => {
      //   // this.getAllAuthor()
      //   this.listLoading = false
      // }, 300)
    },
    methods: {
      showView(row) {
        this.title = '权限查看'
        this.dialogFormVisible = true
        if (row) {
          this.form = row
        }
        this.getAllAuthor()
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

      async getAllAuthor() {
        const ids = this.form.id
        const {data} = await getSysAuthority({ids})
        this.tableData = data
        console.log(this.tableData)
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 300)
      },
    },
  }
</script>
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 80px;
  }
</style>
