<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close" append-to-body>
    <el-table ref="multipleTable" v-loading="listLoading" :data="tableData" border highlight-current-row
              :element-loading-text="elementLoadingText" @selection-change="setSelectRows" height="250">
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="name" align="center" sortable label="名称">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="description" align="center" sortable label="描述">
      </el-table-column>
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
  import {selectAllRoleByPage} from '@/api/userManagement'

  export default {
    name: 'diaTwo',
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
          name: ''//角色名称
        },
        tableData: [],
        data: [],
        morgx: [],//默认选中
        type: ''//新增还是修改
      }
    },
    created() {

    },
    methods: {
      showWin(row, type) {
        this.title = '添加可见角色';
        this.dialogFormVisible = true;
        this.type = type;
        if (type == '2') {
          this.morgx = row;
        } else {
          this.morgx = [];
          this.data = [];
        }
        this.fetchData();
        setTimeout(() => {
          this.listLoading = false
        }, 300)
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
        this.dialogFormVisible = false;
      },
      save() {
        var that = this;
        for (var i = 0; i < that.selectRows.length; i++) {
          var flag = that.data.every(function (val, index) {
            return val != that.selectRows[i];
          });
          if (flag) {

            that.data.push(that.selectRows[i]);
          }
        }
        that.$emit('listRowData', that.data);
        this.close()
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await selectAllRoleByPage(this.queryForm);
        this.tableData = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
        if (this.type == '2') {
          //判断已经有的进行勾选（必须加上nextTick否则无效）
          this.$nextTick(() => {
            this.morgx.forEach((val, index) => {
              let obj = this.tableData.find(o => o.id === val.id);
              if (obj) this.$refs.multipleTable.toggleRowSelection(obj);
            })
          })
        }
      },
    },
  }
</script>
