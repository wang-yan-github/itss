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
              <el-date-picker
                v-model="queryForm.create_start_time"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="创建时间(Start)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.create_end_time"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="创建时间(End)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.category_name"
                placeholder="问题类别"
                clearable
                @clear="queryForm.category_id = ''"
                @click.native="handleCategory"
              >
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleCategory"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.priority_id" placeholder="优先级">
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in this.firstList"
                  :key="item.id"
                  :label="item.first_name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态">
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in this.statusList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.solve_user_name"
                placeholder="解决人"
                clearable
                @keyup.enter.native="queryData"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.audit_user_name"
                placeholder="审核人"
                clearable
                @keyup.enter.native="queryData"
              ></el-input>
            </el-form-item>
          </el-col>
          <!-- <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.clearance_start_time" type="date" placeholder="关单时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.clearance_end_time" type="date" placeholder="关单时间(End)"></el-date-picker>
            </el-form-item>
          </el-col> -->
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.clearance_user_name"
                placeholder="关单人"
                clearable
                @keyup.enter.native="queryData"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button
            v-permission="question_dealtWith_beizhu"
            icon="el-icon-edit-outline"
            type="primary"
            @click="handleRemarks"
          >
            备注
          </el-button>
          <el-button
            v-permission="question_dealtWith_daochu"
            icon="el-icon-upload2"
            @click="handleUpload"
          >
            导出
          </el-button>
          <el-button
            v-permission="question_dealtWith_canyude"
            icon="el-icon-news"
            @click="handleCanyu"
          >
            参与的
          </el-button>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input
              v-model.trim="queryForm.title"
              placeholder="关键字"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              v-permission="question_dealtWith_queryList"
              icon="el-icon-search"
              type="primary"
              @click="queryData"
            >
              查询
            </el-button>
            <el-button
              icon="el-icon-arrow-down"
              @click="moreQuery"
              v-if="isShow"
            >
              高级
            </el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>
              高级
            </el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>
    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
    >
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column
        show-overflow-tooltip
        type="index"
        label="序号"
        align="center"
        width="80px"
      >
        <template slot-scope="scope">
          {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        sortable
        prop="code"
        label="问题单号"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="title"
        sortable
        label="标题"
        align="center"
        header-align="center"
      >
        <template #default="{ row }">
          <el-link @click="handleView(row)">{{ row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="category_name"
        label="问题类别"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="hope_solve_time"
        label="期望解决时间"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="priority_name"
        label="优先级"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="status_name"
        align="center"
        label="状态"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="now_operator_user_name"
        align="center"
        label="当前操作人"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        label="创建人"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        width="160"
        label="创建时间"
        align="center"
      >
        <!-- <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.create_time) }}</span>
        </template> -->
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="solve_time"
        align="center"
        label="解决时间"
      >
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.solve_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="sla_surplus_time"
        align="center"
        label="剩余时间"
      >
      <template #default="{ row }">
          <span style="font-size: small">{{ row.sla_surplus_time }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="操作"
        align="right"
        width="164px"
        header-align="center"
        fixed="right"
      >
        <template #default="{ row }">
          <!--1.对于待鉴定的问题，点击‘鉴定’进入问题鉴定页面；-->
          <el-button
            v-permission="question_dealtWith_jianding"
            plain
            @click="handleAppraisal(row)"
            type="primary"
            size="mini"
            v-if="row.status == 1"
          >
            鉴定
          </el-button>
          <!--1.对于待解决、审核拒绝状态的问题，点击‘处理’进入问题处理页面；-->
          <el-button
            v-permission="question_dealtWith_chuli"
            plain
            @click="handleRelation(row)"
            type="primary"
            size="mini"
            v-if="row.status == 3 || row.status == 8"
          >
            处理
          </el-button>
          <!--1.对于待审核的问题，点击‘审核’进入问题处理结果审核页面；-->
          <el-button
            v-permission="question_dealtWith_shenhe"
            plain
            @click="handleExamine(row)"
            type="primary"
            size="mini"
            v-if="row.status == 2"
          >
            审核
          </el-button>
          <el-dropdown
            trigger="click"
            @command="handleCommandmore"
            style="margin-left: 10px"
          >
            <el-button plain size="mini">
              更多
              <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <!--1.对于处于‘审核拒绝’、‘待解决’状态的问题，处理工程师可以执行转单操作；-->
              <el-dropdown-item
                v-permission="question_dealtWith_zhuandan"
                v-if="row.status == 3 || row.status == 8"
                icon="el-icon-message"
                :command="composeValue(row, 'd')"
              >
                转单
              </el-dropdown-item>
              <!--对于处于‘审核拒绝’、‘待解决’状态的问题，处理工程师可以执行挂起操作；-->
              <el-dropdown-item
                v-permission="question_dealtWith_guaqi"
                v-if="row.status == 3 || row.status == 8"
                icon="el-icon-refresh-right"
                :command="composeValue(row, 'e')"
              >
                挂起
              </el-dropdown-item>
              <!--1.对于已挂起的问题工单，处理工程师和问题管理员可以执行恢复操作，问题恢复到挂起前的状态；-->
              <el-dropdown-item
                v-permission="question_dealtWith_huifu"
                v-if="row.status == 9"
                icon="el-icon-position"
                :command="composeValue(row, 'g')"
              >
                恢复
              </el-dropdown-item>
              <!--处理工程师可对处于‘待解决’状态的问题执行生成变更操作；-->
              <!--问题创建人可对处于‘待解决’、‘待审核’、‘审核通过’状态的问题执行生成变更操作；-->

              <!--状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起-->
              <el-dropdown-item
                v-permission="question_dealtWith_guanlian"
                icon="el-icon-connection"
                :command="composeValue(row, 'a')"
              >
                修改关联
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="question_dealtWith_biangeng"
                icon="el-icon-s-operation"
                :command="composeValue(row, 'f')"
              >
                生成变更
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
      @current-change="handleCurrentChange2"
    ></el-pagination>

    <!--备注-->
    <remake ref="remake" @fetch-data="fetchData"></remake>
    <!--停止-->
    <stop ref="stop" @fetch-data="fetchData"></stop>
    <!--问题类别-->
    <questionCategory
      ref="questionCategory"
      @questionCategoryData="questionCategorySet"
    ></questionCategory>
  </div>
</template>

<script>
import {
  getDbPageList,
  deleteQuestion,
  onDbRecoveryUpd,
  getQuestionFirstList,
  getQuestionStatusList,
} from '@/api/question'
//引用 问题类别 弹窗 页面
import QuestionCategory from '@/components/questionCategory'
//引用 停止 弹窗 页面
import Stop from './components/stop'
//引用 备注 弹窗 页面
import Remake from '@/components/questionRemake'
import { exporDBQuestionExcel } from '@/api/questionStatistics'
import permission from '@/directive/permission'

export default {
  name: 'roleManagement1',
  directives: { permission },
  components: { Remake, Stop, QuestionCategory },
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
      },
    }
  },
  created() {
    this.fetchData()
    this.getFirstList()
    this.getStatusList()
  },
  methods: {
    handleUpload() {
      this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
        exporDBQuestionExcel(this.queryForm).then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '问题待办数据统计.xls')
          document.body.appendChild(link)
          link.click()
        })
        this.$baseMessage('数据导出成功', 'success')
      })
    },
    async getFirstList() {
      //问题优先级列表
      const { data } = await getQuestionFirstList()
      this.firstList = data
    },
    async getStatusList() {
      //问题状态列表
      const { data } = await getQuestionStatusList({
        dict_type: 'question_status',
      })
      this.statusList = data
    },
    questionCategorySet(data) {
      this.queryForm.category_id = data.id
      this.queryForm.category_name = data.name
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openhelp() {
      this.$router.push({
        path: '/help/index',
      })
    },
    //处理
    handleRelation(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/dealChuli',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    //审核
    handleExamine(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/dealExamine',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    //鉴定
    handleAppraisal(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/dealAppraisal',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    // 备注
    handleRemarks() {
      if (this.selectRows != '' && this.selectRows != null) {
        this.$refs['remake'].showEdit(this.selectRows)
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    // 参与的
    handleCanyu() {
      this.$router.push({
        path: '/faq/dealtCanyu',
      })
    },
    // 问题类别
    handleCategory() {
      this.$refs['questionCategory'].showWin()
    },
    // 删除
    handleDelete() {
      if (this.selectRows != '' && this.selectRows != null) {
        const ids = this.selectRows.id
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          const { msg } = await deleteQuestion({ id: ids })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    composeValue(bean, command) {
      return {
        bean: bean,
        command: command,
      }
    },
    handleCommandmore(val) {
      switch (val.command) {
        case 'a': //修改关联
          this.$router.push({
            path: '/faq/dealRelation',
            query: { id: val.bean.id },
          })
          break
        // case 'b'://审核
        //   this.$router.push({
        //     path: '/faq/dealExamine',
        //     query: {id: val.id},
        //   });
        //   break;
        // case 'c'://鉴定
        //   this.$router.push({
        //     path: '/faq/dealAppraisal',
        //     query: {id: val.id},
        //   });
        //   break;
        case 'd': //转单
          this.$router.push({
            path: '/faq/dealZhuandan',
            query: { id: val.bean.id },
          })
          break
        case 'e': //挂起
          this.$refs['stop'].showEdit(val.bean.id)
          break
        case 'f': //生成变更
          console.log(val)
          this.$router.push({
            path: '/faq/dealCreatchange',
            query: { question: val.bean },
          })
          break
        case 'g': //恢复
          onDbRecoveryUpd({ id: val.bean.id }).then((res) => {
            if (res.code == 0) {
              this.$baseMessage('成功', 'success')
              this.fetchData()
            }
          })
          break
        default:
          //这里是没有找到对应的值处理
          break
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      this.listLoading = true
      const { data } = await getDbPageList(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    //查看详情
    handleView(row) {
      this.$router.push({
        path: '/faq/faqView',
        query: {
          id: row.id,
        },
      })
    },
    //然后定义函数parseDateFull的实现
    parseDateFull(time) {
      // 判断是否为空
      if (time == null || time == '') {
        return ''
      }
      var x = new Date(time)
      var z = {
        y: x.getFullYear(),
        M: x.getMonth() + 1,
        d: x.getDate(),
        h: x.getHours(),
        m: x.getMinutes(),
        s: x.getSeconds(),
      }
      if (z.M < 10) {
        z.M = '0' + z.M
      }
      if (z.d < 10) {
        z.d = '0' + z.d
      }
      if (z.h < 10) {
        z.h = '0' + z.h
      }
      if (z.m < 10) {
        z.m = '0' + z.m
      }
      // return z.y + "-" + z.M + "-" + z.d + " " + z.h + ":" + z.m ;
      return z.y + '-' + z.M + '-' + z.d
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
    .el-date-editor.el-input--prefix .el-input__inner {
      padding-right: 15px;
    }
  }
}
</style>
