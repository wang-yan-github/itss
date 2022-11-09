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
          用户名
        </template>
        kooriookami
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          姓名
        </template>
        李画
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          状态
        </template>
        <el-tag size="small">活动</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          类型
        </template>
        <el-tag size="small">飞书用户</el-tag>
      </el-descriptions-item>
    </el-descriptions>
    <el-table
      v-loading="listLoading"
      :data="tableData"
      border
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"
      height="400"
      style="margin-top: 20px"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        sortable
        width="100px"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="username"
        sortable
        width="200px"
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="state"
        label="描述"
        align="center"
      ></el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    ></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
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
        tableData: [
          {
            id: '10001',
            username: '系统管理员',
            state: '全部权限',
          },
          {
            id: '10001',
            username: '用户',
            state: '自助开单、查询本人工单、满意度调查',
          },
          {
            id: '10001',
            username: '工程师',
            state: '开单、响应、处理、转单、关单权限',
          },
          {
            id: '10001',
            username: '服务台',
            state: '开单、响应、指派、处理、转单、关单',
          },
          {
            id: '10001',
            username: '事件管理员',
            state: '事件管理、事件配置、服务级别管理全部权限',
          },
          {
            id: '10001',
            username: '知识工程师',
            state: '可创建、查看和搜索知识',
          },
          {
            id: '10001',
            username: '知识管理员',
            state: '知识管理全部权限',
          },
          {
            id: '10001',
            username: '问题工程师',
            state: '可创建、处理、解决问题',
          },
        ],
      }
    },
    created() {
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin() {
        this.title = '设置角色'
        this.dialogFormVisible = true
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
