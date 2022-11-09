<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <el-card shadow="never" style="border-top: 0">
                  <div class="box_card">
                    <div class="box_header">基础信息</div>
                    <div class="box_content">
                      <el-form-item label="标题:">
                        {{ form.title }}
                      </el-form-item>
                      <el-form-item label="描述:">
                        <div class="ql-editor content-detail" v-html="form.description"/>
                      </el-form-item>
                      <el-form-item label="附件:">
                        <div class="bghui">
                          <el-upload
                            ref="uploadPicture"
                            class="upload-demo"
                            action=""
                            :on-preview="handlePreview"
                            disabled
                            :file-list="fileManageInfoList"
                          ></el-upload>
                        </div>
                      </el-form-item>
                    </div>
                  </div>
                  <div class="box_card">
                    <div class="box_header">鉴定信息</div>
                    <div class="box_content">
                      <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="鉴定人:">
                            {{ form.appraisal_user_name }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="鉴定时间:">
                            {{ form.appraisal_time }}
                          </el-form-item>
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
                          <el-form-item label="处理人:">
                            <div class="ql-editor content-detail" v-html="form.appraisal_opinion"/>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="解决人:">
                            {{ form.solve_user_name }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="解决时间:">
                            {{ form.solve_time }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="解决方案:">
                            <div class="ql-editor content-detail" v-html="form.solve_content"/>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="附件:">
                            <div class="bghui">
                              <el-upload
                                ref="uploadPicture"
                                class="upload-demo"
                                action=""
                                :on-preview="handlePreview"
                                disabled
                                :file-list="fileManageHandleList"
                              ></el-upload>
                            </div>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </div>

                  <div class="box_card" v-for="item in form.questionReviewers" :key="item.id">
                    <div class="box_header">审核信息</div>
                    <div class="box_content">
                      <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="审核人:">
                            {{ item.review_user_name }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="审核时间:">
                            {{ item.update_time }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="审核意见:">
                            <span class="ql-editor content-detail" v-html="item.content"></span>
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
                          <el-form-item label="关单人:">
                            {{ form.clearance_user_name }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="关单时间:">
                            {{ form.clearance_time }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="已生成知识:">
                            <template slot-scope="scope">
                              {{ scope.is_knowledge == null ? '否' : (scope.is_knowledge == 0?'否':'是') }}
                            </template>
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </div>
                  </div>
                  <div class="box_card">
                    <div class="box_header">是否生成知识</div>
                    <div class="box_content">
                      <el-row :gutter="20">

                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="是否生成:">
                            <el-select v-model="form.is_knowledge" class="w">
                              <el-option label="否" value="0"></el-option>
                              <el-option label="是" value="1"></el-option>
                            </el-select>
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
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" label="序号" width="60" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
                    <!--<template #default="{ row }">-->
                    <!--<el-link @click="handlesee(row)">{{row.title}}</el-link>-->
                    <!--</template>-->
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="user_id_name" sortable label="用户"
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="service_name" label="服务群组"
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度"
                                   sortable>
                    <template #default="{ row }">
                      <el-rate v-model="row.event_satisfaction" disabled>
                      </el-rate>
                    </template>
                  </el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDeleteEvents"
                  >
                    移除选择对象
                  </el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click="handleEventsAdd"
                  >
                    添加关联的工单
                  </el-button>
                </div>
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
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" label="序号" width="60" align="center"
                                   sortable></el-table-column>
                  <el-table-column show-overflow-tooltip prop="change_no" sortable width="200px;" label="变更单号"
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" sortable label="标题"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="start_time" width="200px;" label="计划开始时间" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="classify_name" align="center" sortable
                                   label="变更类别"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="type_name" align="center" sortable
                                   label="变更类型"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="approve_name" align="center" sortable
                                   label="审批人"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="approve_time" align="center" sortable
                                   label="审批时间"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" align="center" sortable
                                   label="创建时间"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="status_name" align="center" sortable
                                   label="状态"></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDeleteChange"
                  >
                    移除选择对象
                  </el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click="handleChangeAdd"
                  >
                    添加关联变更
                  </el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="logsName" name="fourth">
                <div class="box_card">
                  <div class="box_content">
                    <el-timeline>

                      <el-timeline-item
                        v-for="(log, index) in form.logs"
                        :key="index"
                        :timestamp="log.operate_time"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
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
                <el-form-item label="问题单号:" class="mb0">
                  {{ form.code }}
                </el-form-item>
                <el-form-item label="状态:" class="mb0"> {{ form.status_name }}</el-form-item>

                <el-form-item label="当前操作人:" class="mb0">{{ form.now_operator_user_name }}</el-form-item>
                <el-form-item label="创建人:" class="mb0">{{ form.create_user_name }}</el-form-item>
                <el-form-item label="创建时间:" class="mb0">{{ form.create_time }}</el-form-item>
                <el-form-item label="期望解决时间:" class="mb0"> {{ form.hope_solve_time }}</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">分类信息</div>
              <div class="box_content">
                <el-form-item label="来源:" class="mb0">{{ form.source_name }}</el-form-item>
                <el-form-item label="优先级:" class="mb0">{{ form.priority_name }}</el-form-item>
                <el-form-item label="问题类别:" class="mb0">
                  {{ form.category_name }}
                </el-form-item>
<!--                <el-form-item label="sla:" class="mb0">{{ form.sla_time }}</el-form-item>-->
<!--                <el-form-item label="关单时限:" class="mb0">{{ form.time_limit }}</el-form-item>-->
                <el-form-item label="解决分钟数:" class="mb0">{{ (form.resolution_minutes==null || form.resolution_minutes==0) ? "0":form.resolution_minutes }} 分钟</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">时间信息</div>
              <div class="box_content step_content">
                <!--<el-steps :active="1" align-center>-->
                <!--<el-step title="开单" :description="form.create_time"></el-step>-->
                <!--<el-step title="鉴定" :description="form.appraisal_time"></el-step>-->
                <!--<el-step title="解决" :description="form.solve_time"></el-step>-->
                <!--<el-step title="审核" v-for="item in form.questionReviewers" :key="item.id"-->
                <!--:description="item.update_time"></el-step>-->
                <!--<el-step title="关单" :description="form.clearance_time"></el-step>-->
                <!--</el-steps>-->

                <el-steps :active="timeStatus" :style="{ width: stepData.length*80 + 'px'}">
                  <el-step :title="item.title" v-for="item in stepData" :key="item.id"
                           :description="item.description"></el-step>
                </el-steps>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" :loading="loadingbut" @click="saveok">
          保存并关单
        </el-button>
        <el-button size="medium" @click="closeall">
          取消
        </el-button>
        <!-- <el-dropdown
          trigger="click"
          @command="handleCommandmore"
          style="margin-left: 10px"
        >
          <el-button plain size="medium">
            更多
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-reading" command="b">
              备注
            </el-dropdown-item>
            <el-dropdown-item icon="el-icon-message" command="c">
              发邮件
            </el-dropdown-item>

            <el-dropdown-item icon="el-icon-printer" command="e">
              打印
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown> -->
      </div>
    </el-form>

    <!--事件-->
    <eventDialog ref="eventDialog" @listData="listEventLists"></eventDialog>
    <!--变更-->
    <changeDialog ref="changeDialog" @listData="listChanges"></changeDialog>
  </div>
</template>

<script>
  import {getQuestionById, onDbCloseUpd} from '@/api/question'
  import {download} from '@/api/file'
  // 引用事件弹窗页面
  import eventDialog from '@/components/eventDialog'
  // 引用变更弹窗页面
  import changeDialog from '@/components/changeDialog'

  export default {
    name: 'faqClose',
    components: {
      eventDialog, changeDialog
    },
    data() {
      return {
        firstList: [],
        sourceList: [],
        loadingbut: false,//默认提交按钮可点击
        elementLoadingText: '正在加载...',
        selectEventRows: '',
        selectChangeRows: '',
        tableData: [],
        useroptions: [],
        list: [],
        listLoading: true,
        auto_engineer_data: [],
        eventsName: '',
        changesName: '',
        logsName: '',
        timeStatus: 0,
        stepData: [],
        fileManageInfoList: [],
        fileManageHandleList: [],
        options: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{
                header: [1, 2, 3, 4, 5, 6, false]
              }],
              [{
                size: ['small', false, 'large', 'huge']
              }],
              [{
                color: []
              }, {
                background: []
              }],
              ['blockquote', 'code-block'],
              [{
                list: 'ordered'
              }, {
                list: 'bullet'
              }],
              [{
                script: 'sub'
              }, {
                script: 'super'
              }],
              [{
                indent: '-1'
              }, {
                indent: '+1'
              }],
              [{
                align: []
              }],
              [{
                direction: 'rtl'
              }],
              [{
                font: []
              }],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
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
          //关联审核人
          questionReviewers: [],
        },
        rules: {
          // username: [
          //   { required: true, trigger: 'blur', message: '请输入用户名' },
          // ],
          // content: [
          //   {
          //     required: true,
          //     message: '请输入内容',
          //     trigger: 'blur',
          //   },
          // ],
        },
        borderColor: '#dcdfe6',
        checked: false,
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
    },
    methods: {
      // 判断是否为空
      isEmpty(obj) {
        if (typeof obj == 'undefined' || obj == null || obj == '') {
          return true
        } else {
          return false
        }
      },
      async getData() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getQuestionById(query.id);
        this.form = data;
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.changesName = "关联变更(" + data.changeInfos.length + ")";
        this.logsName = "日志(" + data.logs.length + ")";
        this.form.is_knowledge = data.is_knowledge==null?"0": data.is_knowledge + "";

        //时间信息
        this.stepData = [];
        this.stepData.push({title: "开单", description: this.parseDateTimeFull(data.create_time)});
        this.stepData.push({title: "鉴定", description: this.parseDateTimeFull(data.appraisal_time)});
        this.stepData.push({title: "解决", description: this.parseDateTimeFull(data.solve_time)});
        data.questionReviewers.forEach((item, i) => {
          console.log(item);
          this.stepData.push({title: "审核", description: this.parseDateTimeFull(item.update_time)});
        });
        this.stepData.push({title: "关单", description: this.parseDateTimeFull(data.clearance_time)});
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
        this.timeStatus = count;
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
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      handleEventsAdd() {
        this.$refs['eventDialog'].showWin()
      },
      handleChangeAdd() {
        this.$refs['changeDialog'].showWin()
      },
      setSelectEventRows(val) {
        this.selectEventRows = val
      },
      setSelecChangeRows(val) {
        this.selectChangeRows = val
      },
      listEventLists(rows) {
        rows.forEach((item, i) => {
          let index = this.form.events.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.events.push(item)
          }
        })
      },
      listChanges(rows) {
        rows.forEach((item, i) => {
          let index = this.form.changeInfos.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.changeInfos.push(item)
          }
        })
      },
      handleDeleteEvents() {
        if (this.selectEventRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectEventRows.forEach((item, i) => {
              this.form.events.some((x, i) => {
                if (x.id == item.id) {
                  this.form.events.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleDeleteChange() {
        if (this.selectChangeRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectChangeRows.forEach((item, i) => {
              this.form.changeInfos.some((x, i) => {
                if (x.id == item.id) {
                  this.form.changeInfos.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route);
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.$refs['form'].validate(async (valid) => {
          this.loadingbut = true;
          if (valid) {
            const {
              msg
            } = await onDbCloseUpd(this.form);
            this.$baseMessage(msg, 'success');
            // this.$emit('fetch-data');
            //若选择生成知识，则打开生成知识页面
            this.$store.dispatch('tabsBar/delRoute', this.$route)
            if (this.form.is_knowledge == "1") {
              this.$router.push({
                path: '/faq/createKnow',
                query: {bean: this.form},
              });
            }else{

              this.closeall()
            }
          } else {
            this.loadingbut = false;
            return false
          }
        })
      },
      handlePreview(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]));
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', file.name);
          document.body.appendChild(link);
          link.click();
        })
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

      .el-upload {
        text-align: left;
      }
      .step_content{
        overflow-x: auto;
      }
    }
    .content-detail{
      padding: 8px 15px 0 0;
    }
  }
</style>
