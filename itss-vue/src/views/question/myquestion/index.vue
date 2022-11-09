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
                clearable
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.create_end_time"
                type="date"
                placeholder="创建时间(End)"
                value-format="yyyy-MM-dd"
                clearable
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
                @focus="handleCategory"
              >
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleCategory"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select
                v-model="queryForm.priority_id"
                placeholder="优先级"
                clearable
              >
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in firstList"
                  :key="item.id"
                  :label="item.first_name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
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
                @focus="handleSolveUser"
                clearable
              >
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleSolveUser"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.audit_user_name"
                placeholder="审核人"
                @focus="handleAuditUser"
                clearable
              >
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleAuditUser"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.clearance_start_time"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="关单时间(Start)"
                clearable
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.clearance_end_time"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="关单时间(End)"
                clearable
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.clearance_user_name"
                placeholder="关单人"
                @focus="handleCloseUser"
                clearable
              >
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleCloseUser"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button
            v-permission="question_myquestion_tianjia"
            type="primary"
            icon="el-icon-plus"
            @click="handleAdd"
          >
            添加
          </el-button>
          <el-button
            v-permission="question_myquestion_daochu"
            type="success"
            icon="el-icon-upload2"
            @click="handleExport"
          >
            导出
          </el-button>
          <el-button
            v-permission="question_myquestion_beizhu"
            icon="el-icon-edit-outline"
            type=""
            @click="handleRemarks"
          >
            备注
          </el-button>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input
              v-model.trim="queryForm.title"
              placeholder="关键字"
              @keyup.enter.native="queryData"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <el-button
              v-permission="question_myquestion_queryList"
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
      >
        <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.hope_solve_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="priority_name"
        label="优先级"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="status"
        align="center"
        label="状态"
        :formatter="formatStatus"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="now_operator_user_name"
        align="center"
        label="当前操作人"
      ></el-table-column>
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
        label="操作"
        align="right"
        width="164"
        header-align="center"
        fixed="right"
      >
        <template #default="{ row }">
          <!-- 问题查看 -->
          <el-button
            v-permission="question_myquestion_queryList"
            plain
            @click="handleView(row)"
            type="primary"
            size="mini"
          >
            查看
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
              <!--状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起-->

              <!--仅问题管理员拥有转办操作-->
              <el-dropdown-item
                v-if="
                  row.status == 1 ||
                  row.status == 2 ||
                  row.status == 3 ||
                  row.status == 8
                "
                v-permission="question_myquestion_zhuanban"
                icon="el-icon-refresh-right"
                :command="commandmoreValue('zhuanban', row)"
              >
                转办
              </el-dropdown-item>
              <!-- 可生成变更的状态：待处理、待审核、审核通过、审核拒绝,可提交人（需要分配权限）：申请人、处理人、审核人-->
              <el-dropdown-item
                v-if="
                  row.status == 2 ||
                  row.status == 3 ||
                  row.status == 4 ||
                  row.status == 8
                "
                icon="el-icon-edit"
                v-permission="question_myquestion_biangeng"
                :command="commandmoreValue('change', row)"
              >
                生成变更
              </el-dropdown-item>
              <!-- “待鉴定”和“鉴定拒绝”状态下的问题单可被修改，修改后状态为“待鉴定”。 -->
              <el-dropdown-item
                v-permission="question_myquestion_xiugai"
                v-if="row.status == '1' || row.status == '7'"
                icon="el-icon-reading"
                :command="commandmoreValue('edit', row)"
              >
                修改
              </el-dropdown-item>
              <!-- “待鉴定”和“鉴定拒绝”状态下的问题单可被撤销,撤消后状态变为“已撤销”，可在“我的问题”和“已结束问题”中查看 -->
              <el-dropdown-item
                v-permission="question_myquestion_chexiao"
                v-if="row.status == '1' || row.status == '7'"
                icon="el-icon-reading"
                :command="commandmoreValue('chexiao', row)"
              >
                撤销
              </el-dropdown-item>
              <!--1.对于处理结果‘审核通过’的问题，可通过关单功能进行关单操作；-->
              <el-dropdown-item
                v-if="row.status == 4"
                v-permission="question_myquestion_guandan"
                icon="el-icon-refresh-right"
                :command="commandmoreValue('close', row)"
              >
                关单
              </el-dropdown-item>
              <!-- 未被关联的问题单可删除。 未被关联：没有关联事件且没有生成过变更，即未被关联 -->
              <el-dropdown-item
                v-if="row.workorders.length == 0"
                icon="el-icon-refresh-right"
                v-permission="question_myquestion_delete"
                :command="commandmoreValue('delete', row)"
              >
                删除
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="question_myquestion_zhishi"
                icon="el-icon-reading"
                :command="commandmoreValue('knowadd', row)"
              >
                生成知识
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <!--          &lt;!&ndash; “待鉴定”和“鉴定拒绝”状态下的问题单可被修改，修改后状态为“待鉴定”。 &ndash;&gt;-->
          <!--          <el-button-->
          <!--            type="primary"-->
          <!--            size="small"-->
          <!--            @click="handleEdit(row)"-->
          <!--            v-if="row.status == '1' || row.status == '7'">-->
          <!--            修改-->
          <!--          </el-button>-->
          <!--          &lt;!&ndash; 未被关联的问题单可删除。 未被关联：没有关联事件且没有生成过变更，即未被关联 &ndash;&gt;-->
          <!--          <el-button plain @click="handleDelete(row)" type="primary" size="mini"-->
          <!--                     v-if="row.workorders.length == 0">-->
          <!--            删除-->
          <!--          </el-button>-->
          <!--          &lt;!&ndash;状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起&ndash;&gt;-->
          <!--          &lt;!&ndash;仅问题管理员拥有转办操作&ndash;&gt;-->
          <!--          <el-button plain @click="handleZhuanban(row)" type="primary" size="mini"-->
          <!--                     v-if="row.status == 1 || row.status == 2 || row.status == 3 || row.status == 8">-->
          <!--            转办-->
          <!--          </el-button>-->
          <!--          &lt;!&ndash;1.对于处理结果‘审核通过’的问题，可通过关单功能进行关单操作；&ndash;&gt;-->
          <!--          <el-button plain @click="handleClose(row)" type="primary" size="mini" v-if="row.status == 4">-->
          <!--            关单-->
          <!--          </el-button>-->
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
    <users ref="users" @fetch-data="fetchData"></users>
    <remake ref="remake" @fetch-data="fetchData"></remake>
    <!-- 用户 -->
    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <!--打印-->
    <prints ref="prints" @fetch-data="fetchData"></prints>
    <!--撤销-->
    <cancle ref="cancle" @fetch-data="fetchData"></cancle>
    <!--备注-->
    <remake ref="remake" @fetch-data="fetchData"></remake>
    <!--导入-->
    <UserImport ref="UserImport" @fetch-data="fetchData"></UserImport>
    <!--问题类别-->
    <questionCategory
      ref="questionCategory"
      @questionCategoryData="questionCategorySet"
    ></questionCategory>
  </div>
</template>

<script>
import {
  getMyQuestionPageList,
  deleteQuestion,
  onDbRecoveryUpd,
  getQuestionFirstList,
  getQuestionStatusList,
  toExportMyQuestions,
} from '@/api/question'
// 用户
import Users from '@/components/UserSelectModel'
// 备注
import Remake from '@/components/questionRemake'
// 打印
import Prints from '@/components/prints'
// 撤销
import Cancle from '../manage/components/cancle'
// 问题类别
import QuestionCategory from '@/components/questionCategory'
// 导入
import UserImport from './components/UserImport'
import { exportEventExcel } from '@/api/eventAllManage'
import permission from '@/directive/permission'
export default {
  name: 'userManagement__1',
  directives: { permission },
  components: {
    Remake,
    Cancle,
    QuestionCategory,
    UserImport,
    Prints,
    Users,
  },
  data() {
    return {
      question_myquestion_zhishi: 'question_myquestion_zhishi',
      question_myquestion_print: 'question_myquestion_print',
      question_myquestion_remark: 'question_myquestion_remark',
      question_myquestion_cancel: 'question_myquestion_cancel',
      question_myquestion_import: 'question_myquestion_import',
      question_myquestion_export: 'question_myquestion_export',
      question_myquestion_beizhu: 'question_myquestion_beizhu',
      question_myquestion_zhuanban: 'question_myquestion_zhuanban',
      question_myquestion_queryList: 'question_myquestion_queryList',
      question_myquestion_tianjia: 'question_myquestion_tianjia',
      question_myquestion_delete: 'question_myquestion_delete',
      question_myquestion_biangeng: 'question_myquestion_biangeng',
      question_myquestion_daochu: 'question_myquestion_daochu',
      question_myquestion_chexiao: 'question_myquestion_chexiao',
      question_myquestion_xiugai: 'question_myquestion_xiugai',
      statusType: [
        '',
        '待鉴定',
        '待审核',
        '待解决',
        '审核通过',
        '关闭',
        '已撤销',
        '鉴定拒绝',
        '审核拒绝',
        '挂起',
      ],
      selectType: '',
      firstList: [],
      statusList: [],
      value1: '',
      checked: false,
      isShow: false,
      list: null,
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
        now_operator_user: '',
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
    // 导出
    handleExport() {
      this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
        toExportMyQuestions().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '我的问题导出.xls')
          document.body.appendChild(link)
          link.click()
        })
      })
    },
    // 格式化状态
    formatStatus(row) {
      if (
        row.status == '' ||
        row.status == null ||
        row.status == undefined ||
        row.status == 0 ||
        row.status == '0'
      ) {
        return '未知'
      }
      return this.statusType[row.status]
    },
    handleView(row) {
      this.$router.push({
        path: '/faq/faqView',
        query: {
          id: row.id,
        },
      })
    },
    commandmoreValue(command, row) {
      return {
        row: row,
        command: command,
      }
    },
    handleCommandmore(val) {
      var command = val.command
      var row = val.row
      switch (command) {
        case 'edit':
          this.$router.push({
            path: '/faq/faqEdit',
            query: {
              id: row.id,
            },
          })
          break
        case 'delete':
          this.$confirm('确定删除该问题吗?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
            .then(async () => {
              const { data } = await deleteQuestion({ id: row.id })
              this.$message({
                type: 'success',
                message: '删除成功!',
              })
              this.fetchData()
            })
            .catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除',
              })
            })
          break
        case 'zhuanban':
          this.$router.push({
            path: '/faq/manageZhuanban',
            query: { id: row.id },
          })
          break
        case 'close':
          this.$router.push({
            path: '/faq/faqClose',
            query: { id: row.id },
          })
          break
        case 'closed':
          this.$baseMessage('暂无操作', 'success')
          break
        case 'chexiao':
          this.$refs['cancle'].showEdit(row.id)
          break
        case 'change':
          this.$router.push({
            path: '/faq/dealCreatchange',
            query: { question: row },
          })
          break
        case 'knowadd':
          this.$router.push({
            path: '/faq/createKnow',
            query: { bean: row },
          })
          break
        default:
          //这里是没有找到对应的值处理
          break
      }
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
    handleZhuanban(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/manageZhuanban',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleClose(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/faqClose',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },

    questionCategorySet(data) {
      this.queryForm.category_id = data.id
      this.queryForm.category_name = data.name
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handlesee(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/faqView',
          query: {
            id: row.id,
          },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },

    handleRemarks() {
      if (this.selectRows != '' && this.selectRows != null) {
        this.$refs['remake'].showEdit(this.selectRows)
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },

    handleAdd() {
      this.$router.push({
        path: '/faq/myQuestAdd',
      })
    },
    handleChange(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/thing/wholecreatchange',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
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
    getUser(row) {
      switch (this.selectType) {
        case 'solve_user':
          // this.queryForm.solve_user = row.id;
          this.queryForm.solve_user_name = row.name
          break
        case 'audit_user':
          this.queryForm.now_operator_user = row.id;
          this.queryForm.audit_user_name = row.name
          break
        case 'close_user':
          // this.queryForm.solve_user_id = row.id;
          this.queryForm.clearance_user_name = row.name
          break
        default:
          break
      }
    },

    handleexport() {
      if (this.selectRows != '' && this.selectRows != null) {
        const ids = this.selectRows.id
        this.$baseConfirm('确定要导出选中项吗', null, async () => {
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },

    handleCategory() {
      this.$refs['questionCategory'].showWin()
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
      console.log(this.queryForm)
      if(null==this.queryForm.audit_user_name||undefined==this.queryForm.audit_user_name||""==this.queryForm.audit_user_name){
        this.queryForm.now_operator_user='';
      }
      const { data } = await getMyQuestionPageList(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
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
