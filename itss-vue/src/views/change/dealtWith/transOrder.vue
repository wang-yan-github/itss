<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <div class="box_card">
                  <div class="box_header">工单信息</div>
                  <div class="box_content">
                    <el-form-item label="标题:">
                      {{ this.form.changeInfo.title }}
                    </el-form-item>
                    <el-form-item label="变更原因:">
                      <div class="ql-editor content-detail" v-html="form.changeInfo.change_reason"/>
                      <!--<p>{{ this.form.changeInfo.change_reason }}</p>-->
                    </el-form-item>
                    <el-form-item label="当前环境:">
                      <div class="ql-editor content-detail" v-html="form.changeInfo.current_environment"/>
                      <!--{{ this.form.changeInfo.current_environment }}-->
                    </el-form-item>
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
                  </div>
                </div>

                <div class="box_card">
                  <div class="box_header">审批信息</div>
                  <div class="box_content">
                    <el-row :gutter="20" v-for="(item,index) in form.changeNodes" :key="index"
                            v-show="item.time_nodes=='2'">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="审批人:">
                          {{item.approve_user_name}}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="审批意见:">
                          <div class="ql-editor content-detail" v-html="item.approve_remark"></div>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">方案拟定</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="方案拟定人:">
                          {{ this.form.programme_user_name }}
                        </el-form-item>
                      </el-col>

                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="变更方案:">
                          <div class="ql-editor content-detail" v-html="this.form.changeProgrammeDraft.change_programme"></div>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="回退计划:">
                          <div class="ql-editor content-detail" v-html="this.form.changeProgrammeDraft.fallback_plan"></div>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="风险评估:">
                          <div class="ql-editor content-detail" v-html="this.form.changeProgrammeDraft.risk_assessment"></div>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="实施人:">
                          {{ form.implement_user_name }}
                        </el-form-item>
                      </el-col>


                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">方案评审</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="方案审核人:">
                          {{ form.cpa_approve_name }}

                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="方案审核时间:">
                          {{ this.form.changeProgrammeApprove.approve_time }}

                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="方案审核意见:">
                          <div class="ql-editor content-detail" v-html="this.form.changeProgrammeApprove.approve_remark "></div>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">实施信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="实施人:">
                          {{ form.implement_user_name }}

                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="实施时间:">
                          {{form.changeImplement.start_time }} - {{form.changeImplement.end_time }}

                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="实施备注:">
                          {{ form.changeImplement.implement_remark }}
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>

                <div class="box_card">
                  <div class="box_header">转单</div>
                  <div class="box_content">
                    <el-row :gutter="20">

                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="转单人:" prop="trans_form_user">
                          <el-select v-model.trim="form.trans_form_user" class="w">
                            <el-option
                              v-for="item in this.userList"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="备注:" prop="changeInfo.remark">
                          <quill-editor
                            ref="myQuillEditor1"
                            v-model="form.changeInfo.remark"
                            :min-height="200"
                            :options="options"
                          ></quill-editor>
                          <p class="ts">
                            请详细填写问题原因和解决方案请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                            *
                          </p>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>

                <div class="box_card">
                  <div class="box_header">变更转单通知</div>
                  <div class="box_content">
                    <el-row :gutter="20">

                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="是否通知:">
                          <el-checkbox v-model="checked">通知</el-checkbox>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>
              </el-tab-pane>

              <el-tab-pane :label="eventsName" name="second">
                <el-table
                  v-loading="listLoading"
                  :data="tableDataEvent"
                  stripe
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelectRowsEvent">
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" label="序号" align="center"></el-table-column>
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

              </el-tab-pane>
              <el-tab-pane :label="questionsName" name="three">
                <el-table ref="listTable" @selection-change="setSelectRowsQuest" border stripe :data="tableDataQuest"
                          :element-loading-text="elementLoadingText" highlight-current-row>
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index" label="序号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    sortable
                    prop="code"
                    label="问题单号"
                    align="center"
                  >
                  </el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="title"
                    sortable
                    label="标题"
                    align="center"
                  >
                    <!--<template #default="{ row }">-->
                    <!--<el-link @click="handleView(row)">{{row.title}}</el-link>-->
                    <!--</template>-->
                  </el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_user_name"
                    label="创建人"
                    align="center"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_time"
                    label="创建时间"
                    align="center"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="category_name"
                    label="问题类别"
                    align="center"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="hope_solve_time"
                    label="期望解决时间"
                    align="center"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="priority_name"
                    label="优先级"
                    align="center"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="status_name"
                    align="center"
                    label="状态"
                    sortable
                  ></el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="now_operator_user_name"
                    align="center"
                    label="当前操作人"
                    sortable
                  ></el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="solve_time"
                    align="center"
                    label="解决时间"
                    sortable
                  ></el-table-column>
                </el-table>

              </el-tab-pane>
              <el-tab-pane :label="assetsName" name="four">
                <el-table ref="listTable" border stripe :data="tableDataSet"
                          :element-loading-text="elementLoadingText" highlight-current-row
                          @selection-change="setSelectRowsSet">
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="deptName" label="部门" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="receiverName" label="领用人" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" sortable
                                   align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" sortable
                                   align="center">
                    <template #default="scope">
                      <el-tag
                        size="mini"
                        icon="el-icon-edit"
                        v-if="scope.row.is_secrecy == '0'"
                        type="primary"
                      >
                        否
                      </el-tag>
                      <el-tag
                        size="mini"
                        icon="el-icon-delete"
                        class="red"
                        v-if="scope.row.is_secrecy == '1'"
                        type="success"
                      >
                        是
                      </el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="assetsStatusName" label="资产状态" sortable
                                   align="center">
                    <template #default="scope">
                      <el-tag size="mini" icon="el-icon-delete" class="red" type="success">
                        {{ scope.row.assetsStatusName }}
                      </el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="itStatusName" label="IT状态" sortable
                                   align="center"></el-table-column>
                </el-table>

              </el-tab-pane>

              <el-tab-pane :label="logsName" name="fifth">
                <div class="box_card">
                  <div class="box_content">
                    <el-timeline>
                      <el-timeline-item
                        v-for="(log, index) in form.sysLogs"
                        :key="index"
                        :timestamp="log.operate_time"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
                        <el-card>
                          <h4>{{ log.title }}</h4>
                          <p>{{ log.user_name }}</p>
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
              <div class="box_header">变更信息</div>
              <!-- <el-divider content-position="left" style="margin-bottom: 0">
                  <span class="font16 b">工单信息</span>
                </el-divider> -->
              <div class="box_content">
                <el-form-item label="编号:" class="mb0">
                  {{this.form.changeInfo.id}}

                </el-form-item>
                <el-form-item label="变更单号:" class="mb0">
                  {{this.form.changeInfo.change_no}}
                </el-form-item>
                <el-form-item label="当前状态:" class="mb0">{{this.form.status_name}}</el-form-item>

                <el-form-item label="当前操作人:" class="mb0">{{this.form.handler_user_name}}</el-form-item>
                <el-form-item label="创建人:" class="mb0">{{this.form.create_user_uname}}</el-form-item>
                <el-form-item label="创建时间:" class="mb0">{{this.form.changeInfo.create_time}}</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">分类信息</div>
              <div class="box_content">
                <el-form-item label="变更类别:" class="mb0">
                  {{this.form.classify_name}}
                </el-form-item>
                <el-form-item label="变更类型:" class="mb0">
                  <template>
                    <label v-if="this.form.changeInfo.change_type==1">紧急变更</label>
                    <label v-if="this.form.changeInfo.change_type==2">一般变更</label>
                    <label v-if="this.form.changeInfo.change_type==3">重大变更</label>
                  </template>
                </el-form-item>
                <el-form-item label="变更来源:" class="mb0">{{this.form.source_name}}</el-form-item>
                <el-form-item label="计划开始时间:" class="mb0">{{this.form.changeInfo.start_time}}</el-form-item>
                <el-form-item label="计划完成时间:" class="mb0">{{this.form.changeInfo.end_time}}</el-form-item>

              </div>
            </div>
            <div class="box_card">
              <div class="box_header">变更进度</div>
              <div class="box_content step_content">
                <!--<el-steps :active="1" align-center>-->
                <!--<el-step title="开单" description="03-15 10:29"></el-step>-->
                <!--<el-step title="鉴定" description="03-15 10:39"></el-step>-->
                <!--<el-step title="解决" description="03-15 10:40"></el-step>-->
                <!--<el-step title="审核" description="03-15 10:40"></el-step>-->
                <!--<el-step title="关单" description="03-15 10:40"></el-step>-->
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
        <el-button type="primary" size="medium" @click="save()">
          保存
        </el-button>
        <el-button type="warning" size="medium" @click="close()">
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
    <dialogevent ref="dialogevent"
                 @listData="listEventsManages"
                 @fetch-data="
        {
          input2
        }
      "
    ></dialogevent>
    <dialogquest ref="dialogquest"
                 @listData="listQuestManages"
                 @fetch-data="
        {
          input2
        }
      "
    ></dialogquest>
    <dialogSet ref="dialogSet"
               @listData="listAssetsManages"
               @fetch-data="
        {
          input2
        }
      "
    ></dialogSet>
  </div>
</template>

<script>
  import {
    getUserList,
    getChange, toTransOrder
  } from '@/api/change'
  import vabQuill from '@/plugins/vabQuill'

  // 引用事件弹窗页面
  import Dialogevent from '@/components/eventDialog'
  // 引用问题弹窗页面
  import Dialogquest from '@/components/questDialog'
  // 引用关联的配置弹窗页面
  import DialogSet from '@/components/assetsTypeDia'

  export default {
    name: 'changeTransOrder',
    components: {vabQuill, Dialogevent, Dialogquest, DialogSet},
    data() {
      return {
        logsName: '',
        eventsName: '',
        questionsName: '',
        assetsName: '',
        stepData: [],
        timeStatus: 0,
        tableData: [],
        fileManageInfoList: [],
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
          username: 'http://ent1.topvee.cn/saml/metadata.do',
          truename: '管理员',
          password: '',
          email: '',
          permissions: [],
          bumen: '',
          isvip: '',
          content: '',
        },
        queryForm: {id: this.$route.query.id},
        userList: [],
        tableDataEvent: [],
        selectRowsEvent: '',

        tableDataSet: [],
        selectRowsSet: '',

        tableDataQuest: [],
        selectRowsQuest: '',
        rules: {
          trans_form_user: [
            {required: true, trigger: 'blur', message: '请选择转单人'},
          ],
          'changeInfo.remark': [
            {
              required: true,
              message: '请输入内容',
              trigger: 'blur',
            },
          ],
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
      this.fetchData();
      // 方案人
      this.getUserList();
    },
    mounted() {
      this.quillToTop()
    },
    methods: {
      quillToTop(){
        this.$refs.myQuillEditor1.quill.enable(false);
        setTimeout(() => {
          this.$refs.myQuillEditor1.quill.enable(true);
        },2000);
      },
      showView() {
        this.title = '查看详情'
        this.dialogFormVisible = true
      },
      handleAddEvent() {
        this.$refs['dialogevent'].showWin()
      },
      handleAddQuest() {
        this.$refs['dialogquest'].showWin()
      },
      handleAddDeploy() {
        this.$refs['dialogSet'].showWin()
      },
      setSelectRowsSet(val) {
        this.selectRowsSet = val
      },
      setSelectRowsEvent(val) {
        this.selectRowsEvent = val
      },
      setSelectRowsQuest(val) {
        this.selectRowsQuest = val
      },
      close() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      async getUserList() {
        const {data} = await getUserList()
        console.log(8554654);
        console.log(data);
        this.userList = data

        setTimeout(() => {
          this.listLoading = false
        }, 300)
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
      async save(status) {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            // 附件
            const params = new FormData();
            const uploadedFiles = [];
            for (let i = 0; i < this.fileManageInfoList.length; i++) {
              const f = this.fileManageInfoList[i];
              if (f.status == 'ready') {
                params.append('file', f.raw);
              } else if (f.status == 'success') {
                uploadedFiles.push(f.url);
              }
            }
            this.form.uploadedFiles = uploadedFiles.join(',');
            params.append('body', JSON.stringify(this.form));
            toTransOrder(params).then((res) => {
              if (res.code == 0) {
                this.$baseMessage('转单成功', 'success')
                this.$store.dispatch('tabsBar/delRoute', this.$route)
                this.$router.push({
                  path: '/biangeng/changeDeal',
                })
              }
            })
          }
        })
      },
      async fetchData() {
        this.listLoading = true
        console.log(this.queryForm)
        const {data} = await getChange(this.queryForm)
        //属性信息 附件
        if (data.fileManageInfoList) {
          data.fileManageInfoList.forEach((x) => {
            this.fileManageInfoList.push({name: x.file_name, url: x.id})
          })
        }

        this.form = data;
        this.form.changeInfo.remark = "";
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.questionsName = "关联的问题(" + data.questionVos.length + ")";
        this.assetsName = "关联的配置(" + data.assetsManages.length + ")";
        this.logsName = "日志(" + data.sysLogs.length + ")";
        this.tableDataQuest = data.questionVos;
        this.tableDataEvent = data.events;
        this.tableDataSet = data.assetsManages;

        //时间信息
        this.stepData = [];
        //变更类型 1.紧急变更 2.一般变更 3.重大变更|紧急变更影响时间轴，影响流程
        if (data.change_type == "1") {
          data.changeNodes.forEach((item, i) => {
            //紧急 1.开单 2.审批 3.实施 4.实施后评审 5.关单
            if (item.time_nodes == "1") {
              this.stepData.push({title: "开单", description: item.create_time});
            } else if (item.time_nodes == "2") {
              this.stepData.push({title: "审批", description: item.update_time});
            } else if (item.time_nodes == "5") {
              this.stepData.push({title: "实施", description: item.update_time});
            } else if (item.time_nodes == "6") {
              this.stepData.push({title: "实施后评审", description: item.update_time});
            } else if (item.time_nodes == "7") {
              this.stepData.push({title: "关单", description: item.update_time});
            }
          });
        } else {
          //一般、重大时间轴节点 1.开单 2.审批 3.制定方案 4.方案审核 5.实施 6.实施后评审 7.关单 出现多节点的情况下用sort排序
          data.changeNodes.forEach((item, i) => {
            if (item.time_nodes == "1") {
              this.stepData.push({title: "开单", description: item.create_time});
            } else if (item.time_nodes == "2") {
              this.stepData.push({title: "审批", description: item.update_time});
            } else if (item.time_nodes == "3") {
              this.stepData.push({title: "制定方案", description: item.update_time});
            } else if (item.time_nodes == "4") {
              this.stepData.push({title: "方案审核", description: item.update_time});
            } else if (item.time_nodes == "5") {
              this.stepData.push({title: "实施", description: item.update_time});
            } else if (item.time_nodes == "6") {
              this.stepData.push({title: "实施后评审", description: item.update_time});
            } else if (item.time_nodes == "7") {
              this.stepData.push({title: "关单", description: item.update_time});
            }
          });
        }
        //步骤条 设置当前激活步骤
        let count = 0;
        data.changeNodes.forEach((item, i) => {
          if (item.time_status == "0") {
            count++;
          }
        });
        this.timeStatus = count;

        console.log("时间进度条：" + count)
        console.log(this.stepData)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
<style lang="scss" scoped>
  .main-container {
    ::v-deep {
      .ql-container {
        min-height: 200px;
      }
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
          .bghui{
        .p1{
          cursor: pointer;
        }
      }
      .step_content{
        overflow-x: auto;
      }
    }
    .content-detail{
      padding-top: 7px;
      padding-left: 0;
    }
  }
</style>
