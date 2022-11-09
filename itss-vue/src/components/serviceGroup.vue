<!--选择服务群组-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.name" placeholder="请输入" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.is_use" placeholder="状态">
               <el-option>请选择</el-option>
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>
    <el-table v-loading="listLoading" :data="list" border highlight-current-row
              :element-loading-text="elementLoadingText" @current-change="setSelectRows" height="250">
      <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use" label="状态" :formatter="formatIsUse" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="description" label="描述"  align="center"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getServiceGroup} from '@/api/eventAllManage'

  export default {
    name: 'serviceGroup',
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
          name: '',
          is_use: '',
        },
        tableData: [],
        list: null,
        row: {},
      }
    },
    created() {
      this.fetchData();
    },
    methods: {
      showWin() {
        this.title = '服务群组列表';
        this.dialogFormVisible = true
      },
      setSelectRows(val) {
        this.selectRows = val;
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData();
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val;
        this.fetchData();
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        if (this.selectRows != '' && this.selectRows != null) {
          var that = this;
          that.$emit('serviceData', that.selectRows);
          this.close();
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false;
        }

      },
      formatIsUse(row, column, cellValue) {
        switch (cellValue) {
          case '1':
            return '使用';
          case '0':
            return '禁用';
          default:
            return '';
        }
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getServiceGroup(this.queryForm);
        this.list = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
