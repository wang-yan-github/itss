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
              <el-date-picker v-model="queryForm.create_start_time" type="date" placeholder="创建时间(Start)" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.create_end_time" type="date" placeholder="创建时间(End)" value-format="yyyy-MM-dd"></el-date-picker>
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
              <el-date-picker v-model="queryForm.start_time" type="date" placeholder="计划开始时间(Start)"  value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.end_time" type="date" placeholder="计划结束时间(End)" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.implement_start_time" type="date" placeholder="实施开始时间(Start)" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker v-model="queryForm.implement_end_time" type="date" placeholder="实施结束时间(End)" value-format="yyyy-MM-dd"></el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button v-permission="change_manage_beizhu" icon="el-icon-edit-outline" type="primary" @click="handleRemarks">备注</el-button>
          <el-button v-permission="change_manage_daochu" icon="el-icon-upload2" @click="handleexport">导出</el-button>
          <!-- <el-button icon="el-icon-news" @click="handleCanyu">
            参与的
          </el-button> -->
          <!-- <el-button icon="el-icon-news" @click="handleRelation">
            修改关联
          </el-button> -->

        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input v-model.trim="queryForm.keyword" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button v-permission="change_manage_queryList" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button icon="el-icon-arrow-down" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>
    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="change_no" sortable width="140x;" label="变更单号" align="center">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="title" sortable label="标题" header-align="center" align="center">
        <template #default="{ row }">
          <el-link @click="details(row)">{{row.title}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="classify_name" align="center" label="变更类别"></el-table-column>
      <el-table-column show-overflow-tooltip prop="type_name" align="center" label="变更类型"></el-table-column>
      <el-table-column show-overflow-tooltip prop="start_time" width="120px;" label="计划开始时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="end_time" width="120px;" label="计划完成时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" align="center" label="创建人" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="approve_name" align="center" label="当前操作人" width="100px;"></el-table-column>
      <!--<el-table-column show-overflow-tooltip prop="programme_user_name" align="center" label="方案拟定人" width="100px;"></el-table-column>-->
      <el-table-column show-overflow-tooltip prop="create_time" align="center" label="创建时间" width="160px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip label="操作" width="165px;" align="right" header-align="center" fixed="right">
        <template #default="{ row }">
          <!--12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销-->
          <el-button plain @click="handleTransfer(row)" type="primary" size="mini" v-permission="change_dealtWith_zhuandan"
                     v-if="row.status == 1 || row.status == 3 || row.status == 4 || row.status == 6 || row.status == 7">
            转办
          </el-button>
          <el-dropdown trigger="click" @command="handleCommandmore" style="margin-left: 10px">
            <el-button plain size="mini">更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item v-permission="change_manage_xiugai"
                                icon="el-icon-edit" command="a">变更修改</el-dropdown-item>
              <el-dropdown-item v-permission="change_manage_chexiao"
                                icon="el-icon-edit" command="h" v-if="row.status != 12">撤销</el-dropdown-item>
              <!-- <el-dropdown-item icon="el-icon-connection" command="b">
                修改关联
              </el-dropdown-item> -->
              <el-dropdown-item v-permission="change_manage_chongkai"
                                icon="el-icon-video-pause" command="c" v-if="row.status==8">重开</el-dropdown-item>
              <el-dropdown-item v-permission="change_manage_delete"
                                icon="el-icon-reading" command="d">删除</el-dropdown-item>
              <el-dropdown-item v-permission="change_manage_daoru"
                                icon="el-icon-message" command="e">导入</el-dropdown-item>
              <el-dropdown-item v-permission="change_manage_dayin"
                                icon="el-icon-s-operation"  command="f">打印变更</el-dropdown-item>
              <!--<el-dropdown-item icon="el-icon-position" command="g">-->
              <!--修改附件-->
              <!--</el-dropdown-item>-->
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>

    <userimport ref="userimport"></userimport>

    <bumen ref="bumen" @fetch-data="fetchData"></bumen>
    <remark ref="remark" @fetch-data="fetchData"></remark>
    <dialogcpa ref="dialogcpa" @userData="listDialogCpa" @fetch-data=" { input2 }"></dialogcpa>
    <dialogcn ref="dialogcn" @userData="listDialogCn" @fetch-data=" { input2 }"></dialogcn>
    <el-dialog title="打印提示" :visible.sync="dialogVisible" width="500px" center>
      <span class="print-tips">确定打印此变更单吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" v-print="printObj" @click="printShow">确 定</el-button>
      </span>
    </el-dialog>
    <div v-show="false">
    <div id="printTest" class="print-table">
      <table>
        <tbody>
        <tr>
          <td colspan="4" class="title-tb">工单信息</td>
        </tr>
        <tr>
          <td class="sub-tb">标题</td>
          <td colspan="3" >{{formPrint.changeInfo.title}}</td>
        </tr>
        <tr>
          <td class="sub-tb">变更原因</td>
          <td colspan="3" v-html="formPrint.changeInfo.change_reason"></td>
        </tr>
        <tr>
          <td class="sub-tb">当前环境</td>
          <td colspan="3" v-html="formPrint.changeInfo.current_environment"></td>
        </tr>
        <tr>
          <td colspan="4" class="title-tb">审批信息</td>
        </tr>
        <template v-for="(item,index) in formPrint.changeNodes" >
          <tr v-if="item.time_nodes=='2'" :key="index">
            <td class="sub-tb">审批人</td>
            <td colspan="3"> {{item.approve_user_name}}</td>
          </tr>
          <tr  v-if="item.time_nodes=='2'" :key="index">
            <td class="sub-tb">审批意见</td>
            <td colspan="3"  v-html="item.approve_remark"></td>
          </tr>
        </template>
        <tr>
          <td colspan="4" class="title-tb">方案拟定</td>
        </tr>
        <tr>
          <td class="sub-tb">方案拟定人</td>
          <td>{{ formPrint.programme_user_name }}</td>
          <td class="sub-tb">实施人</td>
          <td>   {{ formPrint.implement_user_name }}</td>
        </tr>
        <tr>
          <td class="sub-tb">变更方案</td>
          <td colspan="3" v-html="formPrint.changeProgrammeDraft.change_programme"></td>
        </tr>
        <tr>
          <td class="sub-tb">回退计划</td>
          <td colspan="3" v-html="formPrint.changeProgrammeDraft.fallback_plan"></td>
        </tr>
        <tr>
          <td class="sub-tb">风险评估</td>
          <td colspan="3" v-html="formPrint.changeProgrammeDraft.risk_assessment"></td>
        </tr>
        <tr>
          <td colspan="4" class="title-tb">方案评审</td>
        </tr>
        <tr>
          <td class="sub-tb">方案审核人</td>
          <td> {{ formPrint.cpa_approve_name }}</td>
          <td class="sub-tb">方案审核时间</td>
          <td>{{ formPrint.changeProgrammeApprove.approve_time }}</td>
        </tr>
        <tr>
          <td class="sub-tb">方案审核意见</td>
          <td colspan="3" v-html=" formPrint.changeProgrammeApprove.approve_remark "></td>
        </tr>
        <tr>
          <td colspan="4" class="title-tb">实施信息</td>
        </tr>
        <tr>
          <td class="sub-tb">实施人</td>
          <td>{{ formPrint.implement_user_name }}</td>
          <td class="sub-tb">实施时间</td>
          <td> {{formPrint.changeImplement.start_time }} - {{formPrint.changeImplement.end_time }}</td>
        </tr>
        <tr>
          <td class="sub-tb">实施备注</td>
          <td colspan="3"  v-html=" formPrint.changeImplement.implement_remark"></td>
        </tr>
        <tr>
          <td class="sub-tb">实施后评审</td>
          <td colspan="3"  v-html="formPrint.changeImplementApprove.after_implement_remark"></td>
        </tr>
        </tbody>
      </table>
    </div>
    </div>
  </div>
</template>

<script>
import {
  getAllChangeType,
  toSuspend,
  getDict,
  getChangeSource,
  getPage,
  toCancel,
  deleteChange,
  getChange,
  exportManagerExcel
} from '@/api/change'
  import Bumen from '@/components/bumenTree'
  import Userimport from '@/components/exportfield'
  import Dialogcpa from '@/components/UserSelectModel'
  import Dialogcn from '@/components/UserSelectModel'
  import Remark from './components/remake'
  import permission from "@/directive/permission";

  export default {
    name: 'biangengManagement1',
    directives: {permission},
    components: {Dialogcpa, Dialogcn, Userimport, Bumen, Remark},
    data() {
      return {
        printObj: {
          id: 'printTest',
          popTitle:'&nbsp;',//页眉标题 默认浏览器标题 空字符串时显示undefined 使用html语言
          extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>',//头部文字 默认空
        },
        formPrint: {
          approve_remark: '',
          classify_name: '',
          programme_user_name: '',
          changeInfo: { // 基本信息
            title: '',
            start_time: '',
            end_time: '',
            change_classify: '',
            approve_user: '',
            change_type: '',
            change_source: '',
            change_reason: '',
            status: '1',
            current_environment: '',
            handler_user: '',
            is_del: '0'
          },
          changeProgrammeDraft: {//方案拟定
            programme_user: '',
            change_programme: '',
            fallback_plan: '',
            risk_assessment: '',
            implement_user: ''
          },
          changeProgrammeApprove: {approve_user: '', is_del: '0'},//方案评审
          changeImplement: {implement_user: '', is_del: '0'},// 方案实施
          changeImplementApprove: {after_implement_user: '', is_del: '0'},// 实施后评审人
          changeAssets: [],
          changeEvents: [],
          changeQuestions: [],
          changeNodes: [],
          changeNode: {
            change_id: '',
            approve_remark: '',
            approve_user: '',
            approve_status: ''
          },
        },
        test:'哈哈哈',
        dialogVisible: false,
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
          data_flag: '2',
          id: '',
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
      async getDictChangeStatuss() { // 变更状态
        const {data} = await getDict(this.dict_status);
        this.getDictChangeStatus = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getDictChangeType() {
        console.log("变更类型data:");
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

      handleRemarks() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['remark'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },

      handleexport() {
        this.$baseConfirm('确定要导出所有记录吗？', null, async () => {
          exportManagerExcel(this.queryForm).then((res) => {
            const url = URL.createObjectURL(new Blob([res]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', '变更管理.xls')
            document.body.appendChild(link)
            link.click()
          })
        })
        // this.$refs['userimport'].showEdit()
      },
      handleTransfer(row) {//转办
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/changeTransform',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleCommandmore(command) {

        switch (command) {
          case 'a'://a变更修改
            this.$router.push({
              path: '/biangeng/editmechange',
              query: {id: this.selectRows.id,htmlType:1},
            });
            break;
          case 'b'://b修改关联
            this.$router.push({
              path: '/biangeng/changeRelation',
              query: {id: this.selectRows.id},
            });
            break;
          case 'c'://c重开
            this.$router.push({
              path: '/biangeng/editmechange',
              query: {id: this.selectRows.id},
            });
            break;
          case 'd'://d删除
            if (this.selectRows != '' && this.selectRows != null) {
              const ids = this.selectRows.id;
              this.$baseConfirm('你确定要删除选中项吗', null, async () => {
                const {msg} = await deleteChange({id: ids});
                this.$baseMessage(msg, 'success');
                this.fetchData();
              })
            } else {
              this.$baseMessage('未选中任何行', 'error');
              return false;
            }
            break;
          case 'e'://e导入

            break;
          case 'f':// f 打印变更
            this.dialogVisible = true;
            const ids = this.selectRows.id;
            this.printClick(ids)
            break;
          case 'g':// g 修改附件

            break;
          case 'h':// h撤销
            if (this.selectRows != '' && this.selectRows != null) {
              const ids = this.selectRows.id;
              this.$baseConfirm('你确定要撤销选中项吗', null, async () => {
                var changeInfo = {id: ids, status: '12'};
                const {msg} = await toCancel(changeInfo);
                this.$baseMessage(msg, 'success')
                this.queryData();
              })
            } else {
              this.$baseMessage('未选中任何行', 'error');
              return false;
            }

            break;
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      handleRelation() {

      },
      printShow(){
        this.dialogVisible = false;
      },
      async fetchDataById(row) {
        // this.listLoading = true
        this.queryForm.id = row;
        const {data} = await getChange(this.queryForm)
        console.log("正在执行中——————————————————————————————————————————")
        console.log(this.formPrint.changeNodes)
        console.log(this.formPrint.changeNodes.length)
        this.formPrint = data;
      },
      printClick(id){
        this.fetchDataById(id)
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
        if(null== this.cpa_approve_user_name || "" == this.cpa_approve_user_name || undefined == this.cpa_approve_user_name){
          this.queryForm.cpa_approve_user = '';
        }
        if(null== this.cn_approve_user_name || "" == this.cn_approve_user_name || undefined == this.cn_approve_user_name){
          this.queryForm.cn_approve_user = '';
        }
        const {data} = await getPage(this.queryForm)
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
<style media="printTest">
  @media print {
    /*最外层打印节点*/
    #printTest {
      display:block;
      height: auto;
      overflow: hidden;
    }
    .print-table table{
      width: 100%;
      border-collapse: collapse;
    }
    table, td{
      border: 1px solid black;
    }
    .print-table table td{
      padding: 10px 6px;
      line-height: 18px;
      font-size: 14px;
    }
    .title-tb{
      text-align: center;
      font-weight: bold;
      background-color: #efefef;
    }
    .sub-tb{
      width: 100px;
      text-align: right;
    }

  }
</style>
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
  ::v-deep {
    .el-dialog__body,.el-dialog__footer{
      border-top: none !important;
    }
    .print-tips{
      display: inline-block;
      width: 100%;
      text-align: center;
      font-size: 16px;
    }
  }
</style>
