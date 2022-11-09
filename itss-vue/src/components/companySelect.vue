<!--选择公司 单选-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.name"
              placeholder="请输入公司名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>
    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row border @current-change="setSelectRows">
      <el-table-column type="index" label="序号" width="55" align="center">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="name" sortable label="名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="sort" width="200px;" align="center" label="排序号" sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use" label="状态" align="center">
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.is_use == '1'">启用</el-tag>
          <el-tag size="small" type="warning" v-else-if="scope.row.is_use == '0'">禁用</el-tag>
        </template>
      </el-table-column>
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
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {sysCompanyGetList, sysCompanyToEdit} from '@/api/userManagement'

export default {
  name: 'UserSelectModel',
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
        groupType: '',
        ids: [],
      },
      tableData: [],
      list: null,
      row: {},
      group_id: '',
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    showWin(data) {
      this.title = '公司列表'
      this.dialogFormVisible = true
      this.group_id = data.group_id
      this.queryForm.groupType = data.type
      this.queryForm.ids = data.ids
      this.fetchData()
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
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    close() {
      // this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      if (this.selectRows != '' && this.selectRows != null) {
        var that = this
        // eslint-disable-next-line vue/custom-event-name-casing
        that.$emit('companyData', that.selectRows)
        this.close()
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    async fetchData() {
      this.listLoading = true
      const {data} = await sysCompanyGetList(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
