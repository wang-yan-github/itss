<template>
  <div class="index-container">
    <div class="help">
      <vab-icon :icon="['fas', 'question-circle']" @click="openhelp"></vab-icon>
    </div>
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.id" placeholder="编号" clearable @keyup.enter.native="queryData" class="w"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态" style="width: 100%" clearable>
                <el-option label="待响应" value="1"></el-option>
                <el-option label="处理中" value="2"></el-option>
<!--                <el-option label="暂停" value="3"></el-option>-->
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.user_id_name" placeholder="用户" clearable @clear="queryForm.user_id = ''" @focus="handleSolveUser('yonghu')">
<!--                <el-button slot="append" icon="el-icon-search" @click="handleSolveUser('yonghu')"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.create_user_name" placeholder="开单人" clearable @clear="queryForm.create_user = ''" @focus="handleSolveUser('kaiduanren')">
<!--                <el-button slot="append" icon="el-icon-search" @click="handleSolveUser('kaiduanren')"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.handler_name" placeholder="处理人" clearable @clear="queryForm.handler = ''" @focus="handleSolveUser('chuliren')">
<!--                <el-button slot="append" icon="el-icon-search" @click="handleSolveUser('chuliren')"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.source_id" placeholder="来源" class="w" clearable>
                <el-option v-for="(item, index) in sourceList" :key="index" :label="item.resource_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.service_groups_name" placeholder="服务群组" clearable @clear="queryForm.service_groups_id = ''" @focus="handlegroup">
<!--                <el-button slot="append" icon="el-icon-search" @click="handlegroup"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.deptName" placeholder="部门" clearable @clear="queryForm.department = ''" @focus="handlebumen">
<!--                <el-button slot="append" icon="el-icon-search" @click="handlebumen"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.category_name" placeholder="工单类别" clearable @clear="queryForm.event_category_id = ''" @focus="handleView">
<!--                <el-button slot="append" icon="el-icon-search" @click="handleView"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.priority_id" placeholder="优先级" class="w" clearable>
                <el-option v-for="(item, index) in firstList" :key="index" :label="item.first_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.create_time_start" type="date" value-format="yyyy-MM-dd" clearable placeholder="开单时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="12" :md="3" :lg="6" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.create_time_end" type="date" value-format="yyyy-MM-dd" clearable placeholder="开单时间(End)"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="14">
          <el-button v-permission="event_workbench_add" icon="el-icon-plus" type="primary" @click="handleAdd">开单</el-button>
          <el-button v-permission="event_workbench_canyude" icon="el-icon-news" @click="handleCanyu">参与的</el-button>
          <el-button v-permission="event_workbench_allzunei" icon="el-icon-files" @click="handleGroup">组内全部事件</el-button>
          <el-dropdown trigger="click" @command="handleCommand">
            <el-button>更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-permission="event_workbench_daochu" icon="el-icon-upload2" command="a">导出</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_persontemplate" icon="el-icon-monitor" command="b">个人模板管理</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          <el-checkbox v-model="checked" style="margin-left: 10px" @change="handlerWithMe">待我处理</el-checkbox>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="10">
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
              highlight-current-row border @current-change="handleCurrentChange" height="calc(100vh - 320px)">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="90px;" sortable>
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>

      <el-table-column show-overflow-tooltip label="标题" align="center" header-align="center">
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{ row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" width="120px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center" width="120px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" width="170px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="handler_name" label="当前操作人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="priority_name" align="center" label="优先级" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="surplusMinutes" align="center" label="剩余时间" width="170px;">
        <template #default="{ row }">
          <span style="font-size: small">{{ row.surplusMinutes }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="状态" align="center" width="100px;">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status == '1'">待响应</el-tag>
          <el-tag type="warning" v-else-if="scope.row.status == '2'">处理中</el-tag>
          <el-tag type="info" v-else-if="scope.row.status == '3'">暂停</el-tag>
          <el-tag type="primary" v-else-if="scope.row.status == '4'">处理完成</el-tag>
          <el-tag type="warning" v-else-if="scope.row.status == '5'">待评价</el-tag>
          <el-tag type="danger" v-else-if="scope.row.status == '6'">已关闭</el-tag>
          <el-tag type="danger" v-else-if="scope.row.status == '7'">已撤销</el-tag>
          <el-tag type="info" v-else>其他</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="操作" width="170px;" align="center" header-align="center" fixed="right">
        <template #default="{ row }">
          <el-button v-permission="event_workbench_chuli" plain @click="handlechuli(row)"
                     v-if="row.status == '2' && row.isHandler && row.service_groups_id"
                     type="primary" size="mini">处理</el-button>
          <el-button v-permission="event_workbench_add" plain @click="handleUpdate(row)"
                     v-if="row.status == '1' && row.isCreateUser "
                     type="primary" size="mini">编辑</el-button>
          <el-dropdown trigger="click" @command="handleCommandmore" style="margin-left: 10px">
            <el-button plain size="mini">更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-permission="event_workbench_zhuandan"
                                v-if="row.status != 1" icon="el-icon-connection" :command="commandmoreValue('a', row)">转单</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_zanting"
                                v-if="row.status == 2" icon="el-icon-video-pause" :command="commandmoreValue('b', row)">暂停</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_huifu"
                                v-if="row.status == 3" icon="el-icon-video-pause" :command="commandmoreValue('l', row)">恢复</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_beizhu"
                                icon="el-icon-reading" :command="commandmoreValue('c', row)">备注</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_fayoujian"
                                icon="el-icon-message" :command="commandmoreValue('d', row)">发邮件</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_chexiao"
                                icon="el-icon-refresh-right" :command="commandmoreValue('e', row)">撤销</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_dayin"
                                icon="el-icon-printer" :command="commandmoreValue('f', row)">打印</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_shengchengwenti"
                                v-if="row.status != 1" icon="el-icon-position" :command="commandmoreValue('g', row)">生成问题</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_shengchengbiangeng"
                                v-if="row.status != 1" icon="el-icon-s-operation" :command="commandmoreValue('h', row)">生成变更</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_createRequest"
                                v-if="row.status != 1" icon="el-icon-set-up" :command="commandmoreValue('i', row)">创建配置请求</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_xiangying"
                                v-if="row.status == 1 && row.service_groups_id" icon="el-icon-set-up" :command="commandmoreValue('j', row)">响应</el-dropdown-item>
              <el-dropdown-item v-permission="event_workbench_zhipai"
                                v-if="row.status == 1 && row.service_groups_id" icon="el-icon-set-up" :command="commandmoreValue('k', row)">指派</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total"
                   @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>

    <bumen ref="bumen" @fetch-data="fetchData" @deptData="getDept"></bumen>
    <pause ref="pause" @fetch-data="fetchData"></pause>
    <remake ref="remake" @fetch-data="fetchData"></remake>
    <sendemail ref="sendemail" @fetch-data="fetchData"></sendemail>
    <cancle ref="cancle" @fetch-data="fetchData"></cancle>
    <prints ref="prints" @fetch-data="fetchData"></prints>
    <group ref="group" @serviceData="getGroup" @fetch-data="fetchData"></group>
    <children ref="children" @categoryData="getCategoryData"></children>
    <users ref="users" @userData="getUser"></users>
  </div>
</template>

<script>
import {
  workBenchByPage,
  doDelete,
  responseEvent,
  restore,
  getAllEventFirst,
  getAllTypeInfo,
} from '@/api/event'
import {getCurrentLoginUserInfo} from '@/api/user'
import {getSourceMap, eventAllManageExcel} from '@/api/eventAllManage'
import permission from '@/directive/permission/index.js'
import Bumen from '@/components/bumenTree'
import Pause from '@/components/pause'
import Remake from '@/components/eventRemake'
import Sendemail from '@/components/sendemail'
import Cancle from '@/components/eventCancle'
import Prints from '@/components/prints'
//引用领用人、负责人弹窗页面
import Users from '@/components/UserSelectModel'
import Group from '@/components/serviceGroup'
import Children from '@/components/gongdanclass'

//工作台
export default {
  name: 'gongzuotai1',
  directives: {permission},
  components: {
    Bumen,
    Remake,
    Pause,
    Sendemail,
    Cancle,
    Prints,
    Users,
    Group,
    Children,
  },
  data() {
    return {
      event_workbench_zhuandan: 'event_workbench_zhuandan',
      event_workbench_chuli: 'event_workbench_chuli',
      event_workbench_zanting: 'event_workbench_zanting',
      event_workbench_huifu: 'event_workbench_huifu',
      event_workbench_beizhu: 'event_workbench_beizhu',
      event_workbench_fayoujian: 'event_workbench_fayoujian',
      event_workbench_chexiao: 'event_workbench_chexiao',
      event_workbench_dayin: 'event_workbench_dayin',
      event_workbench_shengchengwenti: 'event_workbench_shengchengwenti',
      event_workbench_shengchengbiangeng: 'event_workbench_shengchengbiangeng',
      event_workbench_createRequest: 'event_workbench_createRequest',
      event_workbench_xiangying: 'event_workbench_xiangying',
      event_workbench_zhipai: 'event_workbench_zhipai',
      event_workbench_add: 'event_workbench_add',
      event_workbench_canyude: 'event_workbench_canyude',
      event_workbench_allzunei: 'event_workbench_allzunei',
      event_workbench_daochu: 'event_workbench_daochu',
      event_workbench_persontemplate: 'event_workbench_persontemplate',

      firstList: [],
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      currentUser: {},
      selectType: '', //选择类型
      sourceList: [],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        id: '',
        status: '',
        user_id_name: '',
        user_id: '',
        create_user_name: '',
        create_user: '',
        handler_name: '', //处理人名称
        handler: '', //处理人id
        source_name: '',
        source_id: '',
        department: '',
        deptName: '',
        service_groups_id: '',
        service_groups_name: '',
        service_name: '',
        category_name: '',
        event_category_id: '',
        priority_id: '',
        priority_name: '',
        create_time_start: '',
        create_time_end: '',
        title: '',
        handlerWithMeFlag: '',
        statusList: ["1","2","3"],
      },
    }
  },
  created() {
    this.getCurrLoginUserInfo()
    this.fetchData()
    this.getSourceData()
    this.getAllEventFirst()
  },
  methods: {
    handleCommand(command) {
      switch (command) {
        case 'a':
          this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
            this.eventAllManageExcelClick()
          })
          break
        case 'b':
          this.$router.push({
            path: '/thing/persontemplatemanagement',
          })
          break
        default:
          //这里是没有找到对应的值处理
          break
      }
    },
    async eventAllManageExcelClick() {
      eventAllManageExcel().then((res) => {
        const url = URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', '工单导出.xls')
        document.body.appendChild(link)
        link.click()
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
      switch (command) {
        case 'a': //转单
          this.$router.push({
            path: '/thing/workingzd',
            query: {id: val.row},
          })
          break
        case 'b': //暂停
          this.$refs['pause'].showEdit(val.row)
          break
        case 'c': //备注
          this.$refs['remake'].showEdit(val.row)
          break
        case 'd': //发邮件
          this.$refs['sendemail'].showEdit(val.row)
          break
        case 'e': //撤销
          this.$refs['cancle'].showEdit(val.row)
          break
        case 'f': //打印
          this.$refs['prints'].showWin(val.row)
          break
        case 'g': //生成问题
          if (val.row.status == 2) {
            this.$router.push({
              path: '/thing/createQuestion',
              query: {event: val.row},
            })
          } else {
            this.$baseMessage('状态必须为处理中', 'error')
          }
          break
        case 'h': //生成变更
          if (val.row.status == 2) {
            console.log('生成变更')
            console.log(val.row)
            this.$router.push({
              path: '/thing/wholecreatchange',
              query: {event: val.row},
            })
          } else {
            this.$baseMessage('状态必须为处理中', 'error')
          }
          break
        case 'i': //创建配置请求
          this.$router.push({
            path: '/pzgl/myRequestAdd',
            query: {event: val.row},
          })
          break
        case 'j': //创建配置请求
                  // 弹出是否响应事件的提示
          this.$confirm('是否响应事件？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          })
            .then(() => {
              this.responseEvent(val.row.id)
            })
            .catch(() => {
              this.$message({
                type: 'info',
                message: '已取消响应',
              })
            })
          break
        case 'k': //指派
          this.$router.push({
            path: '/thing/workingzhipai',
            query: {id: val.row.id},
          })
          break
        case 'l': //恢复
          console.log(val.row);
          this.restore(val.row)
          break
        default:
          //这里是没有找到对应的值处理
          break
      }
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleAdd() {
      this.$router.push({
        path: '/thing/gztadd',
      })
    },
    handlerWithMe(val) {
      //勾选待我处理，处理人自动变成当前登陆user
      if (val) {
        //选中
        // this.queryForm.handler_name = this.currentUser.user_name//处理人名称
        this.queryForm.handler = this.currentUser.id//处理人id
        // this.queryForm.handlerWithMeFlag = "T"
        this.queryForm.status = '2'
      } else {
        // this.queryForm.handler_name = ""//处理人名称
        this.queryForm.handler = ""//处理人id
        // this.queryForm.handlerWithMeFlag = "F"
        this.queryForm.status = '' //状态
      }
      this.fetchData()
    },
    getDept(row) {
      this.queryForm.department = row.id
      this.queryForm.deptName = row.name
    },
    openhelp() {
      this.$router.push({
        path: '/thing/gztadd',
      })
    },
    handleCanyu() {
      this.$router.push({
        path: '/thing/workingcanyu',
      })
    },
    handleGroup() {
      this.$router.push({
        path: '/thing/groupthing',
      })
    },
    handleSolveUser(type) {
      switch (type) {
        case 'kaiduanren':
          this.selectType = 'kaiduanren'
          this.$refs['users'].showWin()
          break
        case 'yonghu':
          this.selectType = 'yonghu'
          this.$refs['users'].showWin()
          break
        case 'chuliren':
          this.selectType = 'chuliren'
          this.$refs['users'].showWin()
          break
      }
    },
    getUser(row) {
      switch (this.selectType) {
        case 'kaiduanren': //领用人
          this.queryForm.create_user = row.id
          this.queryForm.create_user_name = row.name
          break
        case 'yonghu': //用户
          this.queryForm.user_id = row.id
          this.queryForm.user_id_name = row.name
          break
        case 'chuliren': //用户
          this.queryForm.handler = row.id
          this.queryForm.handler_name = row.name
          break
      }
    },
    getCategoryData(row) {
      this.queryForm.category_name = row.name
      this.queryForm.event_category_id = row.id
    },
    handlebumen() {
      this.$refs['bumen'].showWin()
    },
    handleView() {
      this.$refs['children'].showWin()
    },
    handlegroup() {
      this.$refs['group'].showWin()
    },
    handleEdit(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/thing/wholeview',
          query: {id: row.id},
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handlesee(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/thing/wholeview',
          query: {id: row.id},
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleUpdate(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/thing/wholeEdit',
          query: {id: row.id},
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handlechuli(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/thing/workingchuli',
          query: {id: row.id},
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleDelete() {
      if (this.selectRows != '' && this.selectRows != null) {
        const ids = this.selectRows.id
        // this.$baseConfirm('你确定要删除选中项吗', null, async () => {
        //   const { msg } = await doDelete({ ids })
        //   this.$baseMessage(msg, 'success')
        //   this.fetchData()
        // })
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
    getGroup(row) {
      this.queryForm.service_groups_id = row.id
      this.queryForm.service_groups_name = row.name
    },
    async fetchData() {
      this.listLoading = true

      const {data, totalCount} = await workBenchByPage(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getCurrLoginUserInfo() {
      const {data} = await getCurrentLoginUserInfo()
      this.currentUser = data
    },
    //响应
    responseEvent(val) {
      var data = {}
      data.id = val
      responseEvent(data).then((res) => {
        this.$baseMessage(res.msg, 'success')
        this.fetchData()
      })
    },
    //恢复
    async restore(val) {
      const {data} = await restore(val)
      this.$baseMessage('成功', 'success')
      this.fetchData()
    },
    async getSourceData() {
      var param = {}
      const {data} = await getSourceMap(param)
      this.sourceList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getAllEventFirst() {
      const {data} = await getAllEventFirst()
      this.firstList = data
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
