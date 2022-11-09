<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <!-- <el-card shadow="never"> -->
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="属性" name="first">
            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <div class="box_header">基本信息</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="状态:">
                        {{ form.status_name }}
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="问题单号:">
                        {{ form.code }}
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="创建人:">
                        {{ form.create_user_name }}
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="创建时间:">
                        {{ form.create_time }}
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="处理人:">
                        {{ form.handle_user_name }}
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="问题来源:">
                        <el-select v-model="form.source_id" class="w">
                          <el-option
                            v-for="item in this.sourceList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="问题类别:">

                        <el-input v-model.trim="form.category_name" placeholder="问题类别" clearable
                                  @click.native="handleCategory">
                          <!--<el-button slot="append" icon="el-icon-search" @click="handleCategory"></el-button>-->
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="优先级:">
                        <el-select v-model="form.priority_id" class="w">
                          <el-option
                            v-for="item in this.firstList"
                            :key="item.id"
                            :label="item.first_name"
                            :value="item.id">
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="期望解决时间:">
                        <el-date-picker
                          v-model="form.hope_solve_time"
                          type="date"

                        ></el-date-picker>
                        <!-- :picker-options="pickerOptions" -->
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题:" prop="username">
                        <el-input v-model="form.title"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="描述:">
                        <vab-quill
                          v-model="form.description"
                          :min-height="200"
                          :options="options"
                        ></vab-quill>
                        <p class="ts">
                          请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                        </p>
                      </el-form-item>
                    </el-col>


                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="附件:" style="width: 400px;">
                        <el-upload
                          class="upload-button"
                          action=""
                          :auto-upload="false"
                          :on-remove="handleRemove"
                          :before-remove="beforeRemove"
                          multiple
                          :limit="3"
                          :on-exceed="handleExceed"
                          :file-list="fileManageInfoList"
                          :on-change="onChange">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card" v-if="null!=form.appraisal_time&&undefined!=form.appraisal_time&&''!=form.appraisal_time">
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
              <div class="box_card" v-if="null!=form.solve_user_name&&undefined!=form.solve_user_name&&''!=form.solve_user_name">
                <div class="box_header">处理信息</div>
                <div class="box_content">
                  <el-row :gutter="20">
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
                        <vab-quill
                          v-model="form.solve_content"
                          :min-height="200"
                          :options="options"
                        ></vab-quill>
                        <p class="ts">
                          请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                        </p>
                      </el-form-item>
                    </el-col>


                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="附件:" style="width: 400px;">
                        <el-upload
                          class="upload-button"
                          action=""
                          :auto-upload="false"
                          :on-remove="handleRemoveHandle"
                          :before-remove="beforeRemoveHandle"
                          multiple
                          :limit="3"
                          :on-exceed="handleExceedHandle"
                          :file-list="fileManageHandleList"
                          :on-change="onChangeHandle">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card" v-if="form.questionReviewerCount>0">
                <div class="box_header">审核信息</div>
                <div class="box_content">
                  <el-row :gutter="20" v-for="item in form.questionReviewers" :key="item.id">
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
                        {{ item.content }}
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card" v-if="null!=form.clearance_user_name&&undefined!=form.clearance_user_name&&''!=form.clearance_user_name">
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
                          {{ scope.is_knowledge == 0 ? '否' : '是' }}
                        </template>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card" v-if="form.status==1||form.status==7">
								<div class="box_header">鉴定通知</div>
								<div class="box_content">
									<el-row :gutter="20">
										<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
											<el-form-item label="是否通知:">
												<el-checkbox v-model="checked">通知</el-checkbox>
											</el-form-item>
										</el-col>
									</el-row>
								</div>
							</div>
            </el-card>
          </el-tab-pane>
          <el-tab-pane :label="eventsName" name="second">
            <el-card shadow="never" style="border-top: 0">
            <el-table
              v-loading="listLoading"
              :data="form.events"
              stripe
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelectEventRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="60px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
                <!--<template #default="{ row }">-->
                <!--<el-link @click="handlesee(row)">{{row.title}}</el-link>-->
                <!--</template>-->
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" ></el-table-column>
              <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="user_id_name" label="用户"  align="center" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" width="130px;">
                <template slot-scope="scope">
                  <span>{{ parseDateFull(scope.row.create_time) }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间" width="130px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" width="190px;">
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
          </el-card>
          </el-tab-pane>
          <el-tab-pane :label="changesName" name="third">
            <el-card shadow="never" style="border-top: 0">
            <el-table
              v-loading="listLoading"
              :data="form.changeInfos"
              stripe
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelecChangeRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="60px;" ></el-table-column>
              <el-table-column show-overflow-tooltip prop="change_no" sortable width="150px;" label="变更单号"
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题"></el-table-column>
              <el-table-column show-overflow-tooltip prop="start_time" width="130px;" label="计划开始时间"
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="classify_name" align="center" sortable
                               label="变更类别"></el-table-column>
              <el-table-column show-overflow-tooltip prop="type_name" align="center" sortable label="变更类型"></el-table-column>
              <el-table-column show-overflow-tooltip prop="approve_name" align="center" sortable label="审批人"></el-table-column>
              <el-table-column show-overflow-tooltip prop="approve_time" align="center" sortable label="审批时间"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" align="center" sortable label="创建时间">
                <template slot-scope="scope">
                  <span>{{ parseDateFull(scope.row.create_time) }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="status_name" align="center" sortable label="状态"></el-table-column>
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
          </el-card>
          </el-tab-pane>
          <el-tab-pane :label="logsName" name="fourth">
            <el-card shadow="never" style="border-top: 0">
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
          </el-card>
          </el-tab-pane>

        </el-tabs>
      <!-- </el-card> -->
      <div class="bottom">
        <el-button type="primary" size="medium" :loading="loading" @click="saveok">
          {{ loadingText }}
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

    <!--问题类别-->
    <questionCategory ref="questionCategory" @questionCategoryData="questionCategorySet"></questionCategory>
    <!--事件-->
    <eventDialog ref="eventDialog" @listData="listEventLists"></eventDialog>
    <!--变更-->
    <changeDialog ref="changeDialog" @listData="listChanges"></changeDialog>
  </div>
</template>

<script>
  import {getQuestionById, getQuestionFirstList, getSourceList, toQuestionEdit} from '@/api/question'
  // 引用文本编辑器页面
  import vabQuill from '@/plugins/vabQuill'
  //引用 问题类别 弹窗 页面
  import QuestionCategory from '@/components/questionCategory'
  // 引用事件弹窗页面
  import eventDialog from '@/components/eventDialog'
  // 引用变更弹窗页面
  import changeDialog from '@/components/changeDialog'

  export default {
    name: 'faqEdit',
    components: {vabQuill, QuestionCategory, eventDialog, changeDialog},
    data() {
      return {
        pickerOptions: {
            disabledDate(time) {
              return time.getTime() < Date.now();
            },
         },
        texts:['差','一般','好','非常好','非常棒'],
        loading: false,
        loadingText: '保存',
        firstList: [],
        sourceList: [],
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
        fileManageInfoList: [],
        fileManageHandleList: [],
        options: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{header: [1, 2, 3, 4, 5, 6, false]}],
              [{size: ['small', false, 'large', 'huge']}],
              [{color: []}, {background: []}],
              ['blockquote', 'code-block'],
              [{list: 'ordered'}, {list: 'bullet'}],
              [{script: 'sub'}, {script: 'super'}],
              [{indent: '-1'}, {indent: '+1'}],
              [{align: []}],
              [{direction: 'rtl'}],
              [{font: []}],
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
          logs: [],
          //关联审核人
          questionReviewers: [],
          uploadedFiles: '',
          uploadedHandleFiles: '',
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
        username:
          'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {
      this.getData();
      this.getFirstList();
      this.getSourceList();
    },
    methods: {
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
      questionCategorySet(data) {
        console.log(data)
        this.form.category_id = data.id;
        this.form.category_name = data.name;
      },
      handleCategory() {
        this.$refs['questionCategory'].showWin()
      },
      handleEventsAdd() {
        this.$refs['eventDialog'].showWin(null,this.form.events)
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
        this.eventsName = "关联工单(" + this.form.events.length + ")";
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
        this.changesName = "关联变更(" + this.form.changeInfos.length + ")";
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
              this.$refs.eventDialog.data.some((e, i) => {
                if (e.id == item.id) {
                  this.$refs.eventDialog.data.splice(i, 1)
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
            this.eventsName = "关联工单(" + this.form.events.length + ")";
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
            this.changesName = "关联变更(" + this.form.changeInfos.length + ")";
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
        this.loading = true;
        this.loadingText = '保存中...';
        const params = new FormData();
        const uploadedFiles = [];
        const uploadedHandleFiles = [];
        for (let i = 0; i < this.fileManageInfoList.length; i++) {
          const f = this.fileManageInfoList[i];
          if (f.status == 'ready') {
            params.append('file', f.raw);
          } else if (f.status == 'success') {
            uploadedFiles.push(f.url);
          }
        }
        for (let i = 0; i < this.fileManageHandleList.length; i++) {
          const f = this.fileManageHandleList[i];
          if (f.status == 'ready') {
            params.append('fileHandle', f.raw);
          } else if (f.status == 'success') {
            uploadedHandleFiles.push(f.url);
          }
        }
        this.form.uploadedFiles = uploadedFiles.join(',');
        this.form.uploadedHandleFiles = uploadedHandleFiles.join(',');

        params.append('body', JSON.stringify(this.form));
        toQuestionEdit(params).then((res) => {
          if (res.code == 0) {
            this.$baseMessage('成功', 'success');
            this.closeall();
          }
        });
        this.loading = false
        // this.$refs['form'].validate(async (valid) => {
        //   if (valid) {
        //     const {
        //       msg
        //     } = await toQuestionEdit(this.form)
        //     this.$baseMessage(msg, 'success')
        //     this.$emit('fetch-data')
        //
        //     this.closeall()
        //   } else {
        //     return false
        //   }
        // })
      },
      handleRemove(file, fileList) {
        this.fileManageInfoList = fileList;
        console.log(file, fileList);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      //属性 附件上传 选择文件
      onChange(file, fileList) {
        this.fileManageInfoList = fileList;
      },
      handleRemoveHandle(file, fileList) {
        this.fileManageHandleList = fileList;
        console.log(file, fileList);
      },
      handleExceedHandle(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemoveHandle(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      //附件上传 选择文件
      onChangeHandle(file, fileList) {
        this.fileManageHandleList = fileList;
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
      .el-tabs__header {
      margin-bottom: 0 !important;
    }
    .el-tabs__item.is-active {
      background: #fff !important;
    }
      .el-tabs--border-card {
        box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
      }

      .el-card__body {
        height: calc(100vh - 340px) !important;
        overflow-y: auto;
        overflow-x: hidden;
        // .el-tabs__content {
        //   height: calc(100vh - 350px) !important;
        //   overflow-y: auto;
        //   overflow-x: hidden;
        // }
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
    }
  }
</style>
