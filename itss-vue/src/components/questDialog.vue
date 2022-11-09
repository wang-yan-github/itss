<!--添加关联的问题-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.create_start_time"
              type="date"
              placeholder="创建时间(Start)"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              v-model="queryForm.create_end_time"
              type="date"
              placeholder="创建时间(End)"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.category_name"
              placeholder="问题类别"
              clearable
              @clear="queryForm.category_id=''"
              @click.native="handleCategory"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="handleCategory"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-select
              v-model="queryForm.priority_id"
              placeholder="优先级"
              clearable
            >
             <el-option>请选择</el-option>
              <el-option
                v-for="item in this.firstList"
                :key="item.id"
                :label="item.first_name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select
              v-model="queryForm.status"
              placeholder="状态"
              clearable
            >
             <el-option>请选择</el-option>
              <el-option
                v-for="item in this.statusList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.solve_user_name"
              placeholder="解决人"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.audit_user_name"
              placeholder="审核人"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.clearance_user_name"
              placeholder="关单人"
              clearable
            >
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.title" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading" ref="itemTable" :data="tableDate" border highlight-current-row :element-loading-text="elementLoadingText"
      :row-key="getRowKeys" @selection-change="selectionLineChangeHandle" height="300">
      <el-table-column show-overflow-tooltip type="selection" :reserve-selection="true" align="center"></el-table-column>
      <el-table-column
        show-overflow-tooltip
         label="序号"
        align="center"
      >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>

      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        sortable
        prop="code"
        label="问题单号"
        align="center"
      >
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="title"
        sortable
        label="标题"
        align="center"
      >
        <!--<template #default="{ row }">-->
        <!--<el-link @click="handleView(row)">{{row.title}}</el-link>-->
        <!--</template>-->
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        label="创建人"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        label="创建时间"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="category_name"
        label="问题类别"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="hope_solve_time"
        label="期望解决时间"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="priority_name"
        label="优先级"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="status_name"
        align="center"
        label="状态"
        sortable
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="now_operator_user_name"
        align="center"
        label="当前操作人"
        sortable
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="solve_time"
        align="center"
        label="解决时间"
        sortable
      ></el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

    <!--问题类别-->
    <questionCategory ref="questionCategory" @questionCategoryData="questionCategorySet"></questionCategory>
  </el-dialog>

</template>

<script>
  import {
    getQuestionPageList,
    deleteQuestion,
    onDbRecoveryUpd,
    getQuestionFirstList,
    getQuestionStatusList
  } from '@/api/question'

  // 问题类别
  import QuestionCategory from '@/components/questionCategory'

  export default {
    name: 'questionDia',
    components: {
      QuestionCategory,
    },
    data() {
      return {
        input2: '',
        value1: '',
        title: '',
        dialogFormVisible: false,
        loading: false,
        checked: false,
        isShow: false,
        list: null,
        firstList: [],
        statusList: [],
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          keyword: '',
          title: '',
          create_start_time: '',
          create_end_time: '',
          category_id: '',
          category_name: '',
          priority_id: '',
          status: '',
          solve_user_name: '',
          audit_user_name: '',
          clearance_start_time: '',
          clearance_end_time: '',
          clearance_user_name: ''
        },
        data: [],
        tableData1: [],
        tableDate: [],
        tableData2: [],
      }
    },
    created() {


    },
    methods: {
      showWin(row) {
        this.title = '添加问题工单';
        if (row){
          this.tableData1 = row
        }
        setTimeout(() => {
          this.fetchData();
          this.getFirstList();
          this.getStatusList();
          this.listLoading = false
        }, 300)

        this.dialogFormVisible = true;
      },
      async getFirstList() {//问题优先级列表
        const {data} = await getQuestionFirstList();
        this.firstList = data;
      },
      async getStatusList() {//问题状态列表
        const {data} = await getQuestionStatusList({dict_type: "question_status"});
        this.statusList = data;
      },
      questionCategorySet(data) {
        this.queryForm.category_id = data.id;
        this.queryForm.category_name = data.name;
      },
      selectionLineChangeHandle (val) {
        this.tableData2 = val;
      },
      // 保存选中的数据id
      getRowKeys(row) {
        // id 是后台传递的每行信息唯一标识
        return row.id;
      },
      handleCategory() {
        this.$refs['questionCategory'].showWin();
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val;
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getQuestionPageList(this.queryForm);
        this.tableDate = data.list;
        this.total = data.total;

        this.$nextTick(() => {
          for (let i = 0; i < this.tableDate.length; i++) {
            for (let j = 0; j <this.tableData1.length ; j++) {
              //两个数组做比对,选中的做勾选
              if(this.tableData1[j].id==this.tableDate[i].id) {
                this.$refs.itemTable.toggleRowSelection(this.tableDate[i],true);
              }
            }
          }

        });
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      close() {

        this.tableData2 = [];
        this.$refs.itemTable.clearSelection();
        this.dialogFormVisible = false
      },
      save() {
        var that = this;

        that.$emit('listData', this.tableData2);
        this.close();
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
