<template>
  <div class="main-container pb80">
    <el-form ref="form" label-width="120px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <el-card shadow="never" style="border-top: 0">
                  <div class="box_card">
                    <div class="box_header">基础信息</div>
                    <div class="box_content">
                      <el-form-item label="标题:" class="">{{ form.title }}</el-form-item>
                      <el-form-item label="描述:" class=""><div class="ql-editor content-detail" v-html="form.description"/></el-form-item>
                      <el-form-item label="附件:" class="">
                        <div class="bghui">
                          <el-upload ref="uploadPicture" class="upload-demo" action="" :auto-upload="false" :on-preview="handlePreview"
                                     :multiple="true" disabled :file-list="fileManageInfoList"></el-upload>
                        </div>
                      </el-form-item>
                    </div>
                  </div>
                  <div class="box_card">
                    <div class="box_header">鉴定信息</div>
                    <div class="box_content">
                      <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="鉴定人:">{{ form.appraisal_user_name }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="鉴定时间:">{{ form.appraisal_time }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="鉴定意见:">
                            <div class="ql-editor content-detail" v-html="form.appraisal_opinion"/>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </div>
                  <div class="box_card">
                    <div class="box_header">处理信息</div>
                    <div class="box_content">
                      <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="处理人:">{{ form.handle_user_name }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="解决人:">{{ form.solve_user_name }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="解决时间:">{{ form.solve_time }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="解决方案:">
                            <div class="ql-editor content-detail" v-html="form.solve_content"/>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="附件:">
                            <div class="bghui">
                              <el-upload ref="uploadPicture" class="upload-demo" action="" :auto-upload="false" :on-preview="handlePreview"
                                         :multiple="true" disabled :file-list="fileManageHandleList"></el-upload>
                            </div>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </div>

                  <div class="box_card" v-for="(item, index) in this.form.questionReviewers" :key="index">
                    <div class="box_header">审核信息</div>
                    <div class="box_content">
                      <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="审核人:">{{ item.review_user_name }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="审核时间:">{{ item.update_time }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="审核意见:">
                            <div class="ql-editor content-detail" v-html="item.content"/>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </div>
                  <div class="box_card">
                    <div class="box_header">关单信息</div>
                    <div class="box_content">
                      <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="关单人:">{{ form.clearance_user_name }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="关单时间:">{{ form.clearance_time }}</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="已生成知识:">
                            {{ form.is_knowledge == null ? '否' : form.is_knowledge == 1 ? '是' : '否' }}
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </div>

                </el-card>
              </el-tab-pane>
              <el-tab-pane :label="eventsName" name="second">
                <el-table
                  v-loading="listLoading"
                  :data="form.events"
                  stripe
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelectEventRows">
                  <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="60px;"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
                    <!--<template #default="{ row }">-->
                    <!--<el-link @click="handlesee(row)">{{row.title}}</el-link>-->
                    <!--</template>-->
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" >
                    <template slot-scope="scope">
                      <span>{{ parseDateFull(scope.row.create_time) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" width="180px;">
                    <template #default="{ row }">
                      <el-rate v-model="row.event_satisfaction" disabled></el-rate>
                    </template>
                  </el-table-column>
                </el-table>

              </el-tab-pane>
              <el-tab-pane :label="changesName" name="third">
                <el-table
                  v-loading="listLoading"
                  :data="form.changeInfos"
                  stripe
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelecChangeRows">
                  <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="60px;"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="change_no" sortable  label="变更单号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" sortable label="标题"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="start_time"  label="计划开始时间" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="classify_name" align="center" label="变更类别"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="type_name" align="center" label="变更类型"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="approve_name" align="center" label="审批人"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="approve_time" align="center" label="审批时间"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" align="center" label="创建时间">
                    <template slot-scope="scope">
                      <span>{{ parseDateFull(scope.row.create_time) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态"></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane :label="logsName" name="fourth">
                <div class="box_card">
                  <div class="box_content">
                    <el-timeline>
                      <el-timeline-item v-for="(log, index) in form.logs" :key="index" :timestamp="log.operate_time" placement="top" size="large" color="#0bbd87">
                        <el-card>
                          <h4>{{log.title}}</h4>
                          <p>{{log.user_name}}</p>
                          <div class="ql-editor content-detail" v-html="log.content"/>
                        </el-card>
                      </el-timeline-item>
                    </el-timeline>
                  </div>
                </div>
              </el-tab-pane>

            </el-tabs>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
          <el-card shadow="never" class="hasiscoll">
            <div class="box_card">
              <div class="box_header">基础信息</div>
              <!-- <el-divider content-position="left" style="margin-bottom: 0">
                  <span class="font16 b">工单信息</span>
                </el-divider> -->
              <div class="box_content">
                <el-form-item label="编号:" class="mb0">
                  {{ form.id }}

                </el-form-item>
                <el-form-item label="问题单号:" class="mb0">{{ form.code }}</el-form-item>
                <el-form-item label="状态:" class="mb0">{{ statusType[form.status] }}</el-form-item>
                <el-form-item label="当前操作人:" class="mb0">{{ form.now_operator_user_name }}</el-form-item>
                <el-form-item label="创建人:" class="mb0">{{ form.create_user_name }}</el-form-item>
                <el-form-item label="创建时间:" class="mb0">{{ form.create_time }}</el-form-item>
                <el-form-item label="期望解决时间:" class="mb0">{{ form.hope_solve_time }}</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">分类信息</div>
              <div class="box_content">
                <el-form-item label="来源:" class="mb0">{{ form.source_name }}</el-form-item>
                <el-form-item label="优先级:" class="mb0">{{ form.priority_name }}</el-form-item>
                <el-form-item label="问题类别:" class="mb0"> {{ form.category_name }}</el-form-item>
<!--                <el-form-item label="sla:" class="mb0">{{ form.sla_time }}</el-form-item>-->
<!--                <el-form-item label="关单时限:" class="mb0">{{ form.time_limit }}</el-form-item>-->
                <el-form-item label="解决分钟数:" class="mb0">{{ (form.resolution_minutes==null || form.resolution_minutes==0) ? "0":form.resolution_minutes }} 分钟</el-form-item>

              </div>
            </div>
            <div class="box_card">
              <div class="box_header">时间信息</div>
              <div class="box_content step_content">
                <!-- 状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起-->
                <el-steps :active="active"  :style="{ width: activeList.length*90 + 'px'}">
                  <el-step :title="item.title" v-for="item in this.activeList" :key="item.id" :description="item.time"></el-step>
                </el-steps>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
          <el-button size="medium" @click="closeall">关闭</el-button>
  <!--        对于待本人处理的（作为创建人的）问题，可在详情页面根据问题业务节点分别执行：鉴定、处理、审核、关单操作；具体操作同业务功能设计。-->
          <el-dropdown trigger="click" @command="handleCommandmore" style="margin-left: 10px">
            <el-button plain size="medium">更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>

            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item icon="el-icon-help" v-if="this.form.appraisal_user == this.form.create_user && this.form.status === '1'" command="jianding">
                鉴定
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-receiving" v-if="this.form.audit_user == this.form.create_user && this.form.status === '3'" command="chuli">
                处理
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-potato-strips" v-if="this.form.audit_user == this.form.create_user && this.form.status === '2'" command="shenpi">
                审核
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-circle-close" v-if="this.form.audit_user == this.form.create_user && this.form.status === '4'" command="guandan">
                关单
              </el-dropdown-item>
              <el-dropdown-item icon="el-icon-reading" command="beizhu">备注</el-dropdown-item>
              <el-dropdown-item icon="el-icon-printer" command="dayin">打印</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>

      </div>
    </el-form>
    <!--备注 刷新-->
    <remake ref="remake" @fetch-data="refreshLog"></remake>
    <!-- 打印 -->
    <prints ref="prints" @fetch-data="refreshLog"></prints>

  </div>
</template>

<script>

import {getQuestionById, getQuestionFirstList, getSourceList, onDbRecoveryUpd} from "@/api/question";
import {download} from "@/api/file";
// 备注
import Remake from '@/components/questionRemake'
// 打印
import Prints from '@/components/prints'

  export default {
    name: 'faqView1',
    components: {Remake, Prints},
    data() {
      return {
        active: 0,
        activeList: [],
        texts:['差','一般','好','非常好','非常棒'],
        fileManageHandleList: [],
        fileManageInfoList: [],
        changesName: '',
        logsName: '',
        eventsName: '',
        fileList: [],
        selectEventRows: '',
        elementLoadingText: '正在加载...',
        listLoading: true,
        statusType: ['', '待鉴定', '待审核', '待解决', '审核通过', '关闭', '已撤销', '鉴定拒绝', '审核拒绝', '挂起'],
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        form: {
          id: '',
          //来源id 来源通过设置-问题配置中的问题来源管理中获取
          source_id: '',
          //3	优先级	设置-服务级别协议中的问题优先级管理中获取
          priority_id: '',
          //4	问题类别id	问题类别通过设置-问题配置中的问题类别管理中获取
          category_id: '',
          //5	标题
          title: '',
          //6	描述
          description: '',
          //7	期望解决时间 yyyy-MM-dd
          hope_solve_time: '',
          //8	问题鉴定通知 0否 1是
          is_notice: '',
          //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
          status: '',
          //10 备注
          remark: '',
          //11 问题单号
          code: '',
          //14 鉴定人 用户表id
          appraisal_user: '',
          //15 鉴定时间
          appraisal_time: '',
          //16 鉴定意见
          appraisal_opinion: '',
          //17 处理人
          handle_user: '',
          //18 解决人 用户表id
          solve_user: '',
          //19 解决时间
          solve_time: '',
          //20 解决方案
          solve_content: '',
          //21 审核人 用户表id
          audit_user: '',
          //22 审核时间
          audit_time: '',
          //23 审核内容
          audit_content: '',
          //24 关单人 用户表id
          clearance_user: '',
          //25 关单时间
          clearance_time: '',
          //26 sla:(小时)
          sla_time: '',
          //27 关单时限
          time_limit: '',
          //28 解决分钟数
          resolution_minutes: '',
          //29 当前操作人 用户表id
          now_operator_user: '',
          //挂起原因
          suspend_reason: '',
          //审核人名称
          audit_user_name: '',
          //来源名称
          source_name: '',
          //关单人名称
          clearance_user_name: '',
          //解决人名称
          solve_user_name: '',
          //鉴定人名称
          appraisal_user_name: '',
          //处理人名称
          handle_user_name: '',
          //状态名称
          status_name: '',
          //创建人名称
          create_user_name: '',
          //类别名称
          category_name: '',
          //优先级名称
          priority_name: '',
          //当前操作人名称
          now_operator_user_name: '',
          //事件工单列表
          events: [],
          //变更列表
          changeInfos: [],
          //问题日志
          questionLogs: [],
          logs: [],
          //关联审核人
          questionReviewers: [],
          uploadedFiles: '',
          uploadedHandleFiles: '',
        },

        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        srcList: [
          'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
          'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg',
        ],
        activeName: 'first',
        title: '',
        value: 3.7,
        input2: '',
        dialogFormVisible: false,
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {
      this.getData();
      this.getFirstList();
      this.getSourceList();
    },
    methods: {
      handleCommandmore(command) {
        // 取到路由带过来的参数
        const query = this.$route.query;
        switch (command) {
          case 'jianding'://鉴定
            this.$router.push({
              path: '/faq/dealAppraisal',
              query: {id: query.id},
            })
            break;
          case 'chuli'://处理
            this.$router.push({
              path: '/faq/dealChuli',
              query: {id: query.id},
            })
            break;
          case 'shenpi'://审核
            this.$router.push({
              path: '/faq/dealExamine',
              query: {id: query.id},
            })
            break;
          case 'guandan'://关单
            this.$router.push({
              path: '/faq/faqClose',
              query: {id: query.id},
            })
            break;
          case 'beizhu'://备注
            this.$refs['remake'].showEdit(this.form);
            break;
          case 'dayin'://打印
            this.$refs['prints'].showWin()
            break
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      handlePreview(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', file.name)
          document.body.appendChild(link)
          link.click()
        })
      },
      // 判断是否为空
      isEmpty(obj) {
        if (typeof obj == 'undefined' || obj == null || obj == '') {
          return true
        } else {
          return false
        }
      },
      async refreshLog() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getQuestionById(query.id);
        this.form = data;
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.changesName = "关联变更(" + data.changeInfos.length + ")";
        this.logsName = "日志(" + data.logs.length + ")";
      },
      async getData() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getQuestionById(query.id);
        this.form = data;
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.changesName = "关联变更(" + data.changeInfos.length + ")";
        this.logsName = "日志(" + data.logs.length + ")";

        //属性信息 附件
        if (data.fileManageInfoList) {
          data.fileManageInfoList.forEach((x) => {
            this.fileManageInfoList.push({name: x.file_name, url: x.id})
          })
        }
        //处理信息 附件
        if (data.fileManageHandleList) {
          data.fileManageHandleList.forEach((x) => {
            this.fileManageHandleList.push({name: x.file_name, url: x.id})
          })
        }
        console.log(data)
        // 时间步骤条设置
        this.activeList.push({title: "开单", time: this.parseDateTimeFull(data.create_time)});
        this.activeList.push({title: "鉴定", time: this.parseDateTimeFull(data.appraisal_time)});
        this.activeList.push({title: "解决", time: this.parseDateTimeFull(data.solve_time)});
        data.questionReviewers.forEach((item, i) => {
          this.activeList.push({title: item.review_user_name+"审核", time: this.parseDateTimeFull(item.update_time)});
        });
        this.activeList.push({title: "关单", time: this.parseDateTimeFull(data.clearance_time)});
        //步骤条 设置当前激活步骤
        let count = 0;
        if (!this.isEmpty(data.create_time)) {
          count++;
        }
        if (!this.isEmpty(data.appraisal_time)) {
          count++;
        }
        if (!this.isEmpty(data.solve_time)) {
          count++;
        }
        data.questionReviewers.forEach((item, i) => {
          if (!this.isEmpty(item.update_time)) {
            count++;
          }
        });
        if (!this.isEmpty(data.clearance_time)) {
          count++;
        }
        this.active = count;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getSourceList() {//问题来源列表
        const {data} = await getSourceList();
        this.sourceList = data;
      },
      async getFirstList() {//问题优先级列表
        const {data} = await getQuestionFirstList();
        this.firstList = data;
      },
      setSelectEventRows(val) {
        this.selectEventRows = val
      },
      setSelecChangeRows(val) {
        this.selectChangeRows = val
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const {
              msg
            } = await doEdit(this.form)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')

            this.closeall()
          } else {
            return false
          }
        })
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
      //然后定义函数parseDateFull的实现
      parseDateTimeFull(time) {
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
        return z.M + "-" + z.d + " " + z.h + ":" + z.m ;
      },
    },
  }
</script>
<style lang="scss" scoped>
  .main-container {
    ::v-deep {
      .el-divider--horizontal {
        margin-bottom: 0 !important;
      }

      .upload-button {
        display: inline-block;
        margin-right: 10px;
      }

      .el-tabs--border-card {
        box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
      }

      .el-card__body {
        height: calc(100vh - 290px) !important;

        .el-tabs__content {
          height: calc(100vh - 350px) !important;
          overflow-y: auto;
          overflow-x: hidden;
        }
      }

      .hasiscoll {
        .el-form-item__label {
          line-height: 22px;
        }

        .el-form-item__content {
          line-height: 22px;
        }

        .el-card__body {
          overflow-y: auto !important;
          overflow-x: hidden;
        }
      }

      .el-timeline {
        .el-card__body {
          height: auto !important;
        }
      }
      .step_content{
        overflow-x: auto;
      }

      .el-upload {
        text-align: left;
      }
    }
    .content-detail{
      padding-top: 7px;
      padding-left: 0;
    }
  }
</style>
