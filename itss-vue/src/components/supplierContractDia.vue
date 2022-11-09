<!--合同供应商-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" @close="close">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-row :gutter="20">
            <el-col :span="5">
              <el-form-item>
                <el-input v-model.trim="queryForm.supplier_name" placeholder="供应商" clearable @keyup.enter.native="queryData"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-input autocomplete="off" placeholder="请选择部门" style="width: 80%;"
                          v-model.trim="queryForm.dept_name" @click.native="handlebumen"></el-input>
                <el-button icon="el-icon-search"
                           style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                           @click="handlebumen"></el-button>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-input v-model.trim="queryForm.contract_name" placeholder="合同名称" clearable @keyup.enter.native="queryData"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-select v-model="queryForm.status" placeholder="状态">
                   <el-option>请选择</el-option>
                  <el-option label="执行中" value="1"></el-option>
                  <el-option label="已结束" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-button icon="el-icon-search" type="primary" @click="queryData">
                  检索
                </el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="itemTable" :data="tableData" border highlight-current-row
              :element-loading-text="elementLoadingText" @selection-change="setSelectRows" height="250">
      <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip  label="序号" sortable align="center">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="supplier_name" align="center" sortable label="供应商">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="contract_name" align="center" sortable label="合同名称">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" align="center" label="部门" sortable>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" align="center" label="开始时间" sortable>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="end_time" align="center" label="结束时间" sortable>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态" sortable>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

    <bumen ref="bumen" @deptData="getDept" @fetch-data="{value2}"></bumen>

  </el-dialog>
</template>

<script>
  import {toList} from '@/api/contractshopmanagement'

  //引用部门弹窗页面
  import Bumen from '@/components/bumenTree'

  export default {
    name: 'supplierContractDia',
    components: {Bumen},
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
          supplier_name: '',
          dept_id: '',
          dept_name: '',
          contract_name: '',
          status: '',
        },
        tableData: [],
        tableData1: [],
        data: [],
        value2: '',
      }
    },
    created() {
      // this.fetchData();
      // setTimeout(() => {
      //   this.listLoading = false
      // }, 300)
    },
    methods: {
      handlebumen() {
        this.$refs['bumen'].showWin()
      },
      //部门选中 参数回显
      getDept(row) {
        this.queryForm.dept_id = row.id;
        this.queryForm.dept_name = row.name;
      },
      showWin(row) {
        this.title = '添加供应商合同';
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
        this.fetchData();
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val;
        this.fetchData();
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.data = [];
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
        console.log(that.data)
        that.$emit('listData', that.data);
        that.data = [];
        this.close()
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await toList(this.queryForm);
        for (var i = 0 ; i< data.list.length ; i++){
          if (data.list[i].status == 1){
            data.list[i].status_name = "执行中"
          }else {
            data.list[i].status_name = "已结束"
          }
        }
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
