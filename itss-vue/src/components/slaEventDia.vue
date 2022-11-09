<!--SLA事件-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" append-to-body width="900px" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.sla_event_name"
              placeholder="名称"
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
    <el-table v-loading="listLoading" :data="tableData" border highlight-current-row
              ref="singleTable" @current-change="setSelectRows"
              :element-loading-text="elementLoadingText" height="250">
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="id" label="编码" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="sla_event_name" align="center" sortable label="名称">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="is_first" align="center" label="考虑优先级" sortable>
        <template #default="scope">
          <el-tag
            size="small"
            type="success"
            v-if="scope.row.is_first == '1'"
          >是
          </el-tag
          >
          <el-tag
            size="small"
            type="warning"
            v-else-if="scope.row.is_first == '0'"
          >否
          </el-tag
          >
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="is_request" align="center" label="考虑请求类型" sortable>
        <template #default="scope">
          <el-tag
            size="small"
            type="success"
            v-if="scope.row.is_request == '1'"
          >是
          </el-tag
          >
          <el-tag
            size="small"
            type="warning"
            v-else-if="scope.row.is_request == '0'"
          >否
          </el-tag
          >
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="remark" align="center" label="描述" sortable>
      </el-table-column>

      <!--<el-table-column show-overflow-tooltip label="操作" align="center">-->
      <!--<template slot-scope="scope">-->
      <!--<el-button type="success" icon="el-icon-check" plain size="mini">选择</el-button>-->
      <!--</template>-->
      <!--</el-table-column>-->
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {slaEventGetList} from '@/api/affect'

  export default {
    name: 'slaEventDia',
    components: {},
    data() {
      return {
        bumen: '',
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
          sla_event_name: '',
        },
        tableData: [],
        data: [],
      }
    },
    created() {
      this.fetchData();
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin() {
        this.title = '添加事件SLA';
        this.dialogFormVisible = true
      },
      setSelectRows(val) {
        console.log(val)
        this.selectRows = val;
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val;
        this.fetchData()
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false;
      },
      save() {
        var that = this;
        // for (var i = 0; i < that.selectRows.length; i++) {
        //   var flag = that.data.every(function (val, index) {
        //     return val != that.selectRows[i];
        //   });
        //   if (flag) {
        //     that.data.push(that.selectRows[i]);
        //   }
        // }
        console.log(that.selectRows)
        that.$emit('listData', that.selectRows);
        this.close()
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await slaEventGetList(this.queryForm);
        this.tableData = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
