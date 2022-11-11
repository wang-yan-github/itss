<!--选择服务群组-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px;" @close="close" append-to-body>
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
    <el-table v-loading="listLoading"  ref="itemTable" :data="list" border highlight-current-row
              :element-loading-text="elementLoadingText" @selection-change="setSelectRows" height="250">
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip label="状态" align="center" width="70px;">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.is_use == 1">启用</el-tag>
          <el-tag type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="非一线组" align="center" width="80px;">
        <template slot-scope="scope">
          {{ scope.row.nonFirstLineGroup == 1 ? '是' : '否' }}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="principalName" label="负责人" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="engineerName" label="工程师" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="auto_engineer_name" label="处理人为空自动指定工程师" align="center" width="190px;">
<!--        <template slot-scope="scope">-->
<!--          <el-tag type="success" v-if="scope.row.auto_engineer == 1">是</el-tag>-->
<!--          <el-tag type="danger" v-else>否</el-tag>-->
<!--        </template>-->
      </el-table-column>
<!--      <el-table-column show-overflow-tooltip prop="sort" label="排序"></el-table-column>-->
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
        tableData1: [],
        list: null,
        row: {},
      }
    },
    created() {

    },
    methods: {
      showWin(row) {
        this.title = '服务群组列表';
        if (row) {
          this.tableData1 = row
        }
        this.fetchData();
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
        this.list = [];
        this.dialogFormVisible = false
      },
      save() {
        var that = this;
        that.$emit('delGroup', that.selectRows)
        this.close();
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

        this.$nextTick(() => {
          for (let i = 0; i < this.list.length; i++) {
            for (let j = 0; j <this.tableData1.length ; j++) {
              //两个数组做比对,选中的做勾选
              if(this.tableData1[j].id==this.list[i].id) {
                this.$refs.itemTable.toggleRowSelection(this.list[i],true);
              }
            }
          }

        });


        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
