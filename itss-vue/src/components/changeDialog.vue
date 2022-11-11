<!--公共 变更 弹窗 多选-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1200px" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.create_start_time"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="创建时间(Start)"
              clearable
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.create_end_time"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="创建时间(End)"
              clearable
            ></el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-select clearable placeholder="变更类别" v-model.trim="queryForm.change_classify" class="w">
               <el-option>请选择</el-option>
              <el-option
                v-for="item in this.tableDataClassify"
                :key="item.id"
                :label="item.type_name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select
              v-model="queryForm.change_type"
              placeholder="变更类型"
              clearable
            >
             <el-option>请选择</el-option>
              <el-option
                v-for="item in this.getDictChangeTypes"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="cpa_approve_user_name"
              placeholder="方案审核人"
              clearable
              @focus="handleCpaApprove"
              @clear="queryForm.cpa_approve_user=''"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleCpaApprove"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="cn_approve_user_name"
              placeholder="审批人"
              clearable
              @focus="handleCnApprove"
              @clear="queryForm.cn_approve_user=''"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleCnApprove"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.start_time"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="计划开始时间(Start)"
              clearable
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.end_time"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="计划结束时间(End)"
              clearable
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.implement_start_time"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="实施开始时间(Start)"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.implement_end_time"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="实施结束时间(End)"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>
    <el-table v-loading="listLoading" ref="itemTable" :data="list" border highlight-current-row
              :element-loading-text="elementLoadingText" :row-key="getRowKeys" @selection-change="setSelectRows" height="500">
      <el-table-column show-overflow-tooltip type="selection" :reserve-selection="true" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="50px;" >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="change_no" sortable width="150px;" label="变更单号"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="title" sortable label="标题"></el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" width="130px;" label="计划开始时间"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="classify_name" align="center" sortable
                       label="变更类别"></el-table-column>
      <el-table-column show-overflow-tooltip prop="type_name" align="center" sortable label="变更类型"></el-table-column>
<!--      <el-table-column show-overflow-tooltip prop="approve_name" align="center" sortable label="审批人"></el-table-column>-->
      <el-table-column show-overflow-tooltip prop="implement_start_time" align="center"  label="实施开始时间"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" align="center" sortable label="创建时间">
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" align="center" sortable label="状态"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>

    <dialogcpa ref="dialogcpa" @userData="listDialogCpa"></dialogcpa>
    <dialogcn ref="dialogcn" @userData="listDialogCn"></dialogcn>

  </el-dialog>
</template>


<script>
  import {getChangeList, getAllChangeType, getDict, getChangeSource} from '@/api/eventAllManage'
  import Dialogcpa from '@/components/UserSelectModel'
  import Dialogcn from '@/components/UserSelectModel'

  export default {
    name: 'changeDialog',
    components: {Dialogcpa, Dialogcn},
    data() {
      return {
        value1: '',
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        getDictChangeStatus: [],
        tableDataClassify: [],
        getDictChangeTypes: [],
        dict: {dict_type: 'change_type'},
        dict_status:{dict_type:'change_status'},
        cpa_approve_user_name:'',// 方案审核人名
        cn_approve_user_name:'',// 审批人名
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          implement_start_time: '',//4	实施开始时间
          implement_end_time: '',//5	实施结束时间
          create_start_time: '',// 创建开始时间
          create_end_time: '', // 创建结束时间
          start_time: '', // 计划开始时间
          end_time: '', // 计划结束时间
          status: '10',
          change_classify: '',
          change_type: '',
          title: '',
          change_source: '',
          cn_approve_user: '',// 审批人
          cpa_approve_user: '',// 方案审核人
        },
        list: null,
        tableData: [],
        tableData1: [],
        tableData2: [],
        data: [],
      }
    },
    created() {

    },
    methods: {
      //变更类别
      async allChangeType() {
        const {data} = await getAllChangeType();
        this.tableDataClassify = data;
        // console.log(this.tableDataClassify);
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      // 变更状态
      async getDictChangeStatuss() {
        const {data} = await getDict(this.dict_status);
        this.getDictChangeStatus = data;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      //变更类型
      async getDictChangeType() {
        const {data} = await getDict(this.dict);
        this.getDictChangeTypes = data;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      handleCpaApprove() { // 方案审核人
        this.$refs['dialogcpa'].showWin()
      },
      handleCnApprove() { // 审批人
        this.$refs['dialogcn'].showWin()
      },
      listDialogCpa(row){
        this.queryForm.cpa_approve_user=row.id;
        this.cpa_approve_user_name=row.name;
      },
      listDialogCn(row){
        this.queryForm.cn_approve_user=row.id;
        this.cn_approve_user_name=row.name;
      },
      showWin(row) {
        this.title = '添加关联变更';

        if (row){
          this.tableData1 = row
          console.log(this.tableData1);
        }
        this.fetchData();

        //变更类别
        this.allChangeType();
        // 变更状态
        this.getDictChangeStatuss();
        // 变更类型
        this.getDictChangeType();

        this.dialogFormVisible = true;

      },
      setSelectRows(val) {
        this.tableData2 = val;
      },
      // 保存选中的数据id
      getRowKeys(row) {
        // id 是后台传递的每行信息唯一标识
        return row.id;
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData();
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData()
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val;
        this.fetchData();
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.tableData2 = [];
        this.$refs.itemTable.clearSelection();
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
        that.$emit('listData', this.tableData2);
        this.close()
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getChangeList(this.queryForm);
        this.list = data.list;
        this.total = data.total;
        console.log(this.list)

        this.$nextTick(() => {
          for (let i = 0; i < this.list.length; i++) {
            for (let j = 0; j <this.tableData1.length ; j++) {
              //两个数组做比对,选中的做勾选
              if(this.tableData1[j].id == this.list[i].id) {
                this.$refs.itemTable.toggleRowSelection(this.list[i],true);
              }
            }
          }

        });



        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      //然后定义函数parseDateFull的实现
      parseDateFull(time) {
        var x = new Date(time);
        var z = {
          y: x.getFullYear(),
          M: x.getMonth() + 1,
          d: x.getDate(),
          h: x.getHours(),
          m: x.getMinutes(),
          s: x.getSeconds(),
        };
        if (z.M < 10) {
          z.M = "0" + z.M;
        }
        if (z.d < 10) {
          z.d = "0" + z.d;
        }
        if (z.h < 10) {
          z.h = "0" + z.h;
        }
        if (z.m < 10) {
          z.m = "0" + z.m;
        }
        // return z.y + "-" + z.M + "-" + z.d + " " + z.h + ":" + z.m ;
        return z.y + "-" + z.M + "-" + z.d ;
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .left-panel {
      .el-input {
        width: 200px !important;
      }
    }
  }
</style>
