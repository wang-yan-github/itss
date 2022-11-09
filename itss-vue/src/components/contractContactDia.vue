<!--合同联系人-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="900px" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.contact_name" placeholder="姓名" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.email" placeholder="Email" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
<!--          <el-form-item>-->
<!--            <el-input v-model.trim="queryForm.phone" placeholder="Phone" clearable @keyup.enter.native="queryData"/>-->
<!--          </el-form-item>-->
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>

    </vab-query-form>
    <el-table v-loading="listLoading" ref="itemTable" :data="tableData" border highlight-current-row
              :element-loading-text="elementLoadingText" @selection-change="setSelectRows" height="250">
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="contact_name" align="center" sortable label="姓名">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="company_name" align="center" label="联系人公司" sortable>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" align="center" label="部门" sortable>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="email" align="center" label="Email" sortable>
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
  import {getList} from '@/api/assetsContractContact'

  export default {
    name: 'contractContactDia',
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
          contact_name: '',
          email: '',
          phone: '',
        },
        tableData: [],
        tableData1: [],
        data: [],
      }
    },
    created() {
      // this.fetchData();
      // setTimeout(() => {
      //   this.listLoading = false
      // }, 300)
    },
    methods: {
      showWin(row) {
        this.title = '添加合同联系人';
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
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData()
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
        for (var i = 0; i < that.selectRows.length; i++) {
          var flag = that.data.every(function (val, index) {
            return val != that.selectRows[i];
          });
          if (flag) {
            that.data.push(that.selectRows[i]);
          }
        }
        console.log("合同联系人 save")
        console.log(that.data)
        that.$emit('listData', that.data);
        that.data = [];
        this.close()
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getList(this.queryForm);
        this.tableData = data.list;
        this.total = data.total;

        if (this.tableData1 != null && this.tableData1 != [] && this.tableData1 != undefined){
          this.$nextTick(() => {
            for (let i = 0; i < this.tableData.length; i++) {
              for (let j = 0; j <this.tableData1.length ; j++) {
                //两个数组做比对,选中的做勾选
                if(this.tableData1[j].id == this.tableData[i].id) {
                  this.$refs.itemTable.toggleRowSelection(this.tableData[i],true);
                }
              }
            }

          });
        }


        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
