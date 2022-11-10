<template>
  <div class="index-container">
    <!-- <div class="help">
      <vab-icon :icon="['fas', 'question-circle']" @click="openhelp"></vab-icon>
    </div> -->
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.create_start_time" type="date" placeholder="创建时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.create_end_time" type="date" placeholder="创建时间(End)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.category_name" placeholder="问题类别" clearable @clear="queryForm.category_id=''" @click.native="handleCategory">
                <!--<el-button slot="append" icon="el-icon-search" @click="handleCategory"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.priority_id" placeholder="优先级">
                <el-option value="">请选择</el-option>
                <el-option v-for="item in this.firstList" :key="item.id" :label="item.first_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态">
                <el-option value="">请选择</el-option>
                <el-option v-for="item in this.statusList" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.solve_user_name"
                placeholder="解决人"
                clearable
                @clear="queryForm.solve_user=''"
                @focus="handleSolveUser"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.audit_user_name"
                placeholder="审核人"
                clearable
                @clear="queryForm.now_operator_user=''"
                @focus="handleAuditUser"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.clearance_start_time" type="date" placeholder="关单时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.clearance_end_time" type="date" placeholder="关单时间(End)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.clearance_user_name"
                placeholder="关单人"
                clearable
                @clear="queryForm.solve_user_id=''"
                @focus="handleCloseUser"
              ></el-input>
            </el-form-item> 
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button icon="el-icon-edit-outline" type="primary" @click="handleRemarks">备注</el-button>
          <el-button icon="el-icon-upload2" @click="handleUpload">导出</el-button>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input v-model.trim="queryForm.title" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-arrow-down" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>
    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
      <el-table-column show-overflow-tooltip sortable prop="code" label="问题单号" align="center" width="140px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center" header-align="center">
        <template #default="{ row }">
          <el-link @click="handleView(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="category_name" label="问题类别" align="center" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="hope_solve_time" label="期望解决时间" align="center" width="130px;">
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.hope_solve_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="priority_name" label="优先级" align="center" width="80px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态" width="80px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="now_operator_user_name" align="center" label="当前操作人" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="solve_time" align="center" label="解决时间" width="130px;">
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.solve_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="创建人" align="center" width="80px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="创建时间" align="center" width="130px;">
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
    <!-- 用户 -->
    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <!--问题类别-->
    <questionCategory ref="questionCategory" @questionCategoryData="questionCategorySet"></questionCategory>
    <!--问题备注-->
    <remake ref="remake" @fetch-data="fetchData"></remake>
  </div>
</template>

<script>
  import {
    getParticipatingPageList,
    deleteQuestion,
    onDbRecoveryUpd,
    getQuestionFirstList,
    getQuestionStatusList
  } from '@/api/question'
  // 用户
  import Users from '@/components/UserSelectModel'
  //引用 问题类别 弹窗 页面
  import QuestionCategory from '@/components/questionCategory'
  //引用 备注 弹窗 页面
  import Remake from '@/components/questionRemake'
  import {exporCydQuestionExcel} from "@/api/questionStatistics";

  export default {
    name: 'dealtCanyu1',
    components: {QuestionCategory,Remake,Users, },
    data() {
      return {
        value1: '',
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
          clearance_user_name: '',
          solve_user_id: '',
          now_operator_user: '',
          solve_user: ''
        },
      }
    },
    created() {
      this.fetchData();
      this.getFirstList();
      this.getStatusList();
    },
    methods: {
      handleUpload() {
        this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
          exporCydQuestionExcel().then((res) => {
            const url = URL.createObjectURL(new Blob([res]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', '参与的问题数据统计.xls')
            document.body.appendChild(link)
            link.click()
          })
          this.$baseMessage('数据导出成功', 'success')
        })
      },
      // 备注
      handleRemarks() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['remake'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      // 问题类别
      handleCategory() {
        this.$refs['questionCategory'].showWin()
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
        console.log(data)
        this.queryForm.category_id = data.id;
        this.queryForm.category_name = data.name;
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      //查看详情
      handleView(row) {
        this.$router.push({
          path: '/faq/faqView',
          query: {
            id: row.id
          }
        })
      },
      openhelp() {
        this.$router.push({
          path: '/help/index',
        })
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
        const {data} = await getParticipatingPageList(this.queryForm);
        this.list = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      //然后定义函数parseDateFull的实现
      parseDateFull(time) {
        // 判断是否为空
        if(time == null || time == '') {
          return ''
        }
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
      getUser(row) {
      console.log(this.selectType)
      switch (this.selectType) {
        case 'solve_user':
          this.queryForm.solve_user = row.id;
          this.queryForm.solve_user_name = row.name
          break
        case 'audit_user':
          this.queryForm.now_operator_user = row.id;
          this.queryForm.audit_user_name = row.name
          break
        case 'close_user':
          this.queryForm.solve_user_id = row.id;
          this.queryForm.clearance_user_name = row.name
          break
        default:
          break
      }
    },
    handleSolveUser() {
      this.selectType = 'solve_user'
      this.$refs['users'].showWin()
    },
    handleAuditUser() {
      this.selectType = 'audit_user'
      this.$refs['users'].showWin()
    },
    handleCloseUser() {
      this.selectType = 'close_user'
      this.$refs['users'].showWin()
    },
    },
  }
</script>

<style lang="scss" scoped>
  .hiddenmoretop {
    ::v-deep {
      .el-form-item {
        margin-bottom: 15px;
      }
      .el-date-editor.el-input {
        width: 100%;
      }
      .el-date-editor.el-input--prefix .el-input__inner{
        padding-right: 15px;
      }

    }
  }
</style>
