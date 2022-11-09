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
              <el-date-picker v-model="queryForm.create_start_time" type="date"  value-format="yyyy-MM-dd"  placeholder="创建时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.create_end_time" type="date"  value-format="yyyy-MM-dd"  placeholder="创建时间(End)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input v-model.trim="queryForm.title" placeholder="标题" clearable @keyup.enter.native="queryData"/>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select clearable placeholder="变更类别" v-model.trim="queryForm.change_classify" class="w">
                 <el-option>请选择</el-option>
                <el-option v-for="item in this.tableDataClassify" :key="item.id" :label="item.type_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.change_type" placeholder="变更类型" clearable>
                 <el-option>请选择</el-option>
                <el-option v-for="item in this.getDictChangeTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.change_source" placeholder="变更来源" clearable>
                 <el-option>请选择</el-option>
                <el-option v-for="item in this.changeSourceData" :key="item.id" :label="item.source_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态">
                 <el-option>请选择</el-option>
                <el-option v-for="item in this.getDictChangeStatus" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input v-model.trim="cpa_approve_user_name" placeholder="方案审核人" clearable @focus="handleCpaApprove">
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleCpaApprove"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input v-model.trim="cn_approve_user_name" placeholder="审批人" clearable @focus="handleCnApprove">
                <!-- <el-button slot="append" icon="el-icon-search" @click="handleCnApprove"></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.start_time" type="date"  value-format="yyyy-MM-dd"  placeholder="计划开始时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.end_time" type="date"  value-format="yyyy-MM-dd"  placeholder="计划结束时间(End)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.implement_start_time"  value-format="yyyy-MM-dd"  type="date" placeholder="实施开始时间(Start)"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.implement_end_time"   value-format="yyyy-MM-dd"  type="date" placeholder="实施结束时间(End)"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button v-permission="change_dealtWith_beizhu" icon="el-icon-edit-outline" type="primary" @click="handleRemarks">备注</el-button>
          <el-button v-permission="change_dealtWith_daochu" icon="el-icon-upload2" @click="handleexport">导出</el-button>
          <el-button v-permission="change_dealtWith_canyu" icon="el-icon-news" @click="handleCanyu">参与的</el-button>
          <!-- <el-button icon="el-icon-news" @click="handleRelation">
            修改关联
          </el-button> -->

        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input v-model.trim="queryForm.keyword" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button v-permission="change_dealtWith_queryList" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-arrow-down" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>
    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="change_no" sortable label="变更单号" align="center" width="140px;">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center" header-align="center">
        <template #default="{ row }">
          <el-link @click="details(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="classify_name" align="center" sortable label="变更类别"></el-table-column>
      <el-table-column show-overflow-tooltip prop="type_name" align="center" sortable label="变更类型"></el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" width="130px;" label="计划开始时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" align="center" width="100px;" label="创建人"></el-table-column>
      <el-table-column show-overflow-tooltip prop="handler_user_name" align="center" width="100px;" label="当前操作人"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" align="center" sortable label="创建时间" width="160px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" align="center" width="100px;" label="状态"></el-table-column>
      <el-table-column show-overflow-tooltip label="操作" width="200px;" align="right" header-align="center" fixed="right">
        <template #default="{ row }">
          <!--12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销-->
          <el-button plain @click="handleExamine(row)" v-permission="change_dealtWith_shenpi"
                     type="primary" size="mini" v-if="row.status==1">审批</el-button>
          <!--<el-button plain  type="primary" size="mini" class="is-disabled" disabled v-else>审批</el-button>-->
          <el-button plain @click="handleZdfa(row)" v-permission="change_dealtWith_zhidingfangan"
                     type="primary" size="mini" v-if="row.status==3 || row.status==5">制定方案</el-button>
          <el-button plain @click="handleFash(row)" v-permission="change_dealtWith_fanganshenhe"
                     type="primary" size="mini" v-if="row.status==4">方案审核</el-button>
          <el-button plain @click="handleSs(row)" v-permission="change_dealtWith_shishi"
                     type="primary" size="mini" v-if="row.status==6">实施</el-button>
          <el-button plain @click="handleSshps(row)" v-permission="change_dealtWith_shishihoupingshen"
                     type="primary" size="mini" v-if="row.status==7">实施后评审</el-button>
          <el-button plain @click="handleClose(row)" v-permission="change_dealtWith_guandan"
                     type="primary" size="mini" v-if="row.status==9">关单</el-button>
          <el-dropdown trigger="click" @command="handleCommandmore"  style="margin-left: 10px"
          v-permissions="['change_dealtWith_guandan','change_dealtWith_zhuandan','change_dealtWith_guaqi','change_dealtWith_huifu']" >
            <el-button plain size="mini">更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-permission="change_dealtWith_guandan"
                                icon="el-icon-warning-outline" command="k">修改关联</el-dropdown-item>
              <el-dropdown-item v-permission="change_dealtWith_zhuandan"
                                icon="el-icon-reading" command="c" v-if="row.status==3 || row.status==5 || row.status==6">转单</el-dropdown-item>
              <el-dropdown-item v-permission="change_dealtWith_guaqi"
                                icon="el-icon-s-operation" command="f" v-if="row.status==3 || row.status==5 || row.status==6">挂起</el-dropdown-item>
              <el-dropdown-item v-permission="change_dealtWith_huifu"
                                icon="el-icon-position" command="g" v-if="row.status==11">恢复</el-dropdown-item>
              <!--<el-dropdown-item icon="el-icon-connection" command="a">-->
              <!--制定方案-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item icon="el-icon-video-pause" command="b">-->
              <!--方案审核-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item icon="el-icon-position" command="h">-->
              <!--实施-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item icon="el-icon-message" command="d">-->
              <!--实施后评审-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item icon="el-icon-position" command="i">-->
              <!--关单-->
              <!--</el-dropdown-item>-->
              <!--<el-dropdown-item icon="el-icon-refresh-right" command="e">-->
              <!--导出-->
              <!--</el-dropdown-item>-->

            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
    <userimport ref="userimport"></userimport>

    <pause ref="pause" @fetch-data="fetchData"></pause>
    <bumen ref="bumen" @fetch-data="fetchData"></bumen>
    <remark ref="remark" @fetch-data="fetchData"></remark>
    <dialogcpa ref="dialogcpa" @userData="listDialogCpa" @fetch-data=" { input2 }"></dialogcpa>
    <dialogcn ref="dialogcn" @userData="listDialogCn" @fetch-data=" { input2 }"></dialogcn>
  </div>
</template>

<script>
import {
  getAllChangeType,
  toSuspend,
  getDict,
  getChangeSource,
  toNeedList,
  exportNeedExcel
} from '@/api/change'
  import Bumen from '@/components/bumenTree'
  import Pause from './components/stop'
  import Userimport from '@/components/exportfield'
  import Dialogcpa from '@/components/UserSelectModel'
  import Dialogcn from '@/components/UserSelectModel'
  import Remark from './components/remake'
  import permission from "@/directive/permission";

  export default {
    name: 'changeDeal',
    directives: {permission},
    components: {Dialogcpa, Dialogcn, Userimport, Bumen, Pause, Remark},
    data() {
      return {
        value1: '',
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        tableDataClassify: [],
        getDictChangeTypes: [],
        getDictChangeStatus: [],
        changeInfo: {id: '', status: ''},
        changeSourceData: [],
        dict: {dict_type: 'change_type'},
        dict_status: {dict_type: 'change_status'},
        cpa_approve_user_name: '',// 方案审核人名
        cn_approve_user_name: '',// 审批人名
        queryForm: {
          data_flag: '1',
          pageNo: 1,
          pageSize: 10,
          implement_start_time: '',//4	实施开始时间
          implement_end_time: '',//5	实施结束时间
          create_start_time: '',// 创建开始时间
          create_end_time: '', // 创建结束时间
          start_time: '', // 计划开始时间
          end_time: '', // 计划结束时间
          status: '',
          change_classify: '',
          change_type: '',
          title: '',
          change_source: '',
          cn_approve_user: '',// 审批人
          cpa_approve_user: '',// 方案审核人
        },
      }
    },
    created() {
      this.fetchData()
      this.allChangeType();
      // 变更类型
      this.getDictChangeType();
      // 变更状态
      this.getDictChangeStatuss();
      // 变更来源
      this.getChangeSource();
    },
    methods: {

      handleCurrentChange(val) {
        this.selectRows = val
      },
      async allChangeType() {

        const {data} = await getAllChangeType()
        this.tableDataClassify = data
        console.log(this.tableDataClassify)
        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      async getChangeSource() {
        const {data} = await getChangeSource()

        this.changeSourceData = data

        console.log(this.changeSourceData)
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
      listDialogCpa(row) {
        this.queryForm.cpa_approve_user = row.id;
        this.cpa_approve_user_name = row.name;
      },
      listDialogCn(row) {
        this.queryForm.cn_approve_user = row.id;
        this.cn_approve_user_name = row.name;
      },
      async getDictChangeStatuss() {
        const {data} = await getDict(this.dict_status);
        this.getDictChangeStatus = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getDictChangeType() {
        console.log(1111);
        console.log(this.dict);

        const {data} = await getDict(this.dict)
        this.getDictChangeTypes = data
        console.log(this.getDictChangeTypes)
        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      openhelp() {
        this.$router.push({
          path: '/help/index',
        })
      },
      handleExamine(row) {//审批
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/changeExamine',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleZdfa(row) {//制定方案
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/changeScheme',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleFash(row) {//方案审核
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/changeCheck',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleSs(row) {//实施
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/changeImplementFront',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleSshps(row) {//实施后评审
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/changeImplement',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleClose(row) {//关单
        if (row.id != '' && row.id != null) {
          this.$baseConfirm('确定关单吗', null, async () => {
            this.changeInfo.id = row.id;
            this.changeInfo.status = "10";
            const {msg} = await toSuspend(this.changeInfo);
            this.$baseMessage(msg, 'success');
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      details(row) {
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/detalisExamine',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleRemarks() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['remark'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },

      handleCanyu() {
        this.$router.push({
          path: '/biangeng/changeCanyu',
        })
      },

      handleRole() {
      },


      handleexport() {
         this.$baseConfirm('确定要导出所有记录吗？', null, async () => {
           exportNeedExcel(this.queryForm).then((res) => {
            const url = URL.createObjectURL(new Blob([res]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', '变更待办.xls')
            document.body.appendChild(link)
            link.click()
          })
        })
        // this.$refs['userimport'].showEdit()
      },
      handleUpload() {
        this.$refs['userUpload'].showEdit()
      },

      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({ids})
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleCommandmore(command) {
        switch (command) {
          case 'a'://制定方案
            this.$router.push({
              path: '/biangeng/changeScheme',
              query: {id: this.selectRows.id},
            })
            break
          case 'b'://方案审核
            this.$router.push({
              path: '/biangeng/changeCheck',
              query: {id: this.selectRows.id},
            })
            break
          case 'c':
            this.$router.push({
              path: '/biangeng/changeTransOrder',
              query: {id: this.selectRows.id},
            })
            break
          case 'd'://实施后评审
            this.$router.push({
              path: '/biangeng/changeImplement',
              query: {id: this.selectRows.id},
            })
            break
          case 'e'://导出
            this.$baseConfirm('确定要导出吗', null, async () => {

              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
            break
          case 'f'://挂起
            this.$refs['pause'].showEdit(this.selectRows)
            break
          case 'g'://恢复
            this.$baseConfirm('确定恢复变更吗', null, async () => {
              this.changeInfo.id = this.selectRows.id;
              this.changeInfo.status = this.selectRows.old_status;
              const {msg} = await toSuspend(this.changeInfo)
              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
            break
          case 'h'://实施
            this.$router.push({
              path: '/biangeng/changeImplementFront',
              query: {id: this.selectRows.id},
            })
            break
          case 'i'://关单
            this.$baseConfirm('确定关单吗', null, async () => {
              this.changeInfo.id = this.selectRows.id;
              this.changeInfo.status = "10";
              const {msg} = await toSuspend(this.changeInfo)
              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
            break
          case 'k'://修改关联
            if (this.selectRows != '' && this.selectRows != null) {
              this.$router.push({
                path: '/biangeng/editchangeRelevance',
                query: {id: this.selectRows.id},
              })
            } else {
              this.$baseMessage('未选中任何行', 'error')
              return false
            }
            break;
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      handleRelation() {
        this.$router.push({
          path: '/biangeng/changeRelation',
        })
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
        console.log(this.queryForm);
        const {data} = await toNeedList(this.queryForm)
        console.log(data);
        this.list = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
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
