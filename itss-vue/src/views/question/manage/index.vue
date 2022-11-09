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
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.clearance_start_time"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="关单时间(Start)"
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
              ></el-date-picker>
            </el-form-item>
          </el-col>
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
            v-permission="question_manage_beizhu"
            icon="el-icon-edit-outline"
            type="primary"
            @click="handleRemarks"
          >
            备注
          </el-button>
          <!--问题创建人对处于‘鉴定拒绝’、‘待鉴定’状态的问题可进行修改操作；-->
          <!--问题管理员可对所有流程节点的问题进行修改操作；-->
          <el-button
            v-permission="question_manage_xiugai"
            type="success"
            icon="el-icon-edit"
            @click="handleEdit"
          >
            修改
          </el-button>

          <el-dropdown
            trigger="click"
            @command="handleCommandmore"
            style="margin-left: 10px"
          >
            <el-button plain>
              更多
              <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-permission="question_manage_daochu"
                icon="el-icon-upload2"
                command="a"
              >
                导出
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="question_manage_daoru"
                icon="el-icon-video-pause"
                command="b"
              >
                导入
              </el-dropdown-item>
              <!--问题创建人对处于‘鉴定拒绝’、‘待鉴定’状态的问题可进行撤销操作；-->
              <!--问题管理员可对所有流程节点的问题进行撤销操作；-->
              <el-dropdown-item
                v-permission="question_manage_chexiao"
                icon="el-icon-reading"
                command="c"
              >
                撤销
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="question_manage_shanchu"
                icon="el-icon-message"
                command="d"
              >
                删除
              </el-dropdown-item>
              <!--1.对于处于‘审核通过’、‘关闭’状态的问题，创建人可以执行生成知识操作；-->
              <!--问题管理员可对所有环节的问题执行生成知识操作；-->
              <el-dropdown-item
                v-permission="question_manage_zhishi"
                icon="el-icon-refresh-right"
                command="e"
              >
                生成知识
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="question_manage_dayin"
                icon="el-icon-s-operation"
                command="f"
              >
                打印
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="question_manage_guanlian"
                icon="el-icon-position"
                command="g"
              >
                修改关联
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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
              v-permission="question_manage_queryList"
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
        width="60"
        align="center"
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
        label="创建时间"
        width="160"
        align="center"
      >
        <!-- <template slot-scope="scope">
          <span>{{ parseDateFull(scope.row.create_time) }}</span>
        </template> -->
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="操作"
        width="200px"
        align="center"
        fixed="right"
      >
        <template #default="{ row }">
          <!--状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起-->
          <!--仅问题管理员拥有转办操作-->
          <el-button
            v-permission="question_manage_zhuanban"
            plain
            @click="handleZhuanban(row)"
            type="primary"
            size="mini"
            v-if="
              row.status == 1 ||
              row.status == 2 ||
              row.status == 3 ||
              row.status == 8
            "
          >
            转办
          </el-button>
          <!--1.对于处理结果‘审核通过’的问题，可通过关单功能进行关单操作；-->
          <el-button
            v-permission="question_manage_guandan"
            plain
            @click="handleClose(row)"
            type="primary"
            size="mini"
            v-if="row.status == 4"
          >
            关单
          </el-button>
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
  getQuestionPageList,
  deleteQuestion,
  onDbRecoveryUpd,
  getQuestionFirstList,
  getQuestionStatusList,
  exportQuestionManExcel,
} from '@/api/question'
// 备注
import Remake from './components/remake'
// 打印
import Prints from '@/components/prints'
// 撤销
import Cancle from './components/cancle'
// 问题类别
import QuestionCategory from '@/components/questionCategory'
// 导入
import UserImport from './components/UserImport'
import permission from '@/directive/permission'

export default {
  name: 'menuManagement1',
  directives: { permission },
  components: {
    Remake,
    Cancle,
    QuestionCategory,
    UserImport,
    Prints,
  },
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
    //查看详情
    handleView(row) {
      this.$router.push({
        path: '/faq/faqView',
        query: {
          id: row.id,
        },
      })
    },
    openhelp() {
      this.$router.push({
        path: '/help/index',
      })
    },
    handlesee(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/faq/faqView',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
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

    handleRemarks() {
      if (this.selectRows != '' && this.selectRows != null) {
        this.$refs['remake'].showEdit(this.selectRows)
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
    handleEdit() {
      if (this.selectRows != '' && this.selectRows != null) {
        const ids = this.selectRows.id
        this.$router.push({
          path: '/faq/faqEdit',
          query: { id: ids },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleexport() {
      // if (this.selectRows != '' && this.selectRows != null) {
      //   const ids = this.selectRows.id;
      //   this.$baseConfirm('确定要导出选中项吗', null, async () => {
      //     this.$baseMessage(msg, 'success');
      //     this.fetchData();
      //   })
      // } else {
      //   this.$baseMessage('未选中任何行', 'error');
      //   return false;
      // }
      exportQuestionManExcel().then((res) => {
        const url = URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', '问题管理.xls')
        document.body.appendChild(link)
        link.click()
      })
    },

    handleCategory() {
      this.$refs['questionCategory'].showWin()
    },

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
    //撤销
    handleCancle() {
      if (this.selectRows != '' && this.selectRows != null) {
        const ids = this.selectRows.id
        this.$refs['cancle'].showEdit(ids)
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleCommandmore(val) {
      if (
        (this.selectRows != '' && this.selectRows != null) ||
        val == 'a' ||
        val == 'b'
      ) {
        const ids = this.selectRows.id
        switch (val) {
          case 'a': //导出
            this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
              this.handleexport()
              this.$baseMessage('数据导出成功', 'success')
            })
            break
          case 'b': //导入
            this.$refs['UserImport'].showWin()
            break
          case 'c': //撤销
            this.handleCancle()
            break
          case 'd': //删除
            this.handleDelete()
            break
          case 'e': //生成知识
            this.$router.push({
              path: '/faq/createKnow',
              query: { bean: this.selectRows },
            })
            break
          case 'f': //打印
            this.$router.push({
              path: '/faq/faqPrint',
              query: {
                id: ids,
              },
            })

            // this.$refs['prints'].showWin(ids);
            break
          case 'g': //修改关联
            this.$router.push({
              path: '/faq/dealRelation',
              query: { id: ids },
            })
            break
          default:
            //这里是没有找到对应的值处理
            break
        }
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
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
      const { data } = await getQuestionPageList(this.queryForm)
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
