<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="130px">
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
                      <div class="ql-editor content-detail" v-html="this.form.changeInfo.change_reason"></div>
                    </el-form-item>
                    <el-form-item label="当前环境:">
                      <div class="ql-editor content-detail" v-html="this.form.changeInfo.current_environment"></div>
                    </el-form-item>
                    <el-form-item label="附件:" style="width: 400px;">
                      <div class="bghui" v-for="item in this.form.fileManageInfoList" :key="item.id">
                        <!-- <el-image
                          @click.native="downloadPic(item.id, item.file_name)"
                          style="width: 50px; height: 50px"
                          :src="item.base64"
                          :preview-src-list="srcList"
                        ></el-image> -->
                        <div class="r">
                          <p class="p1" style="margin: 0; color: #333" @click="downloadPic(item.id, item.file_name)">
                            {{item.file_name}}
                          </p>
                          <!-- <p class="p2" style="margin: 0; color: #999">
                          {{item.file_size}}
                          </p>  -->
                        </div>
                      </div>
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
                          <div class="ql-editor content-detail" v-html=" this.form.changeProgrammeDraft.risk_assessment "></div>
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
                          <div class="ql-editor content-detail" v-html=" this.form.changeProgrammeApprove.approve_remark "></div>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">实施信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="实施开始时间" prop="changeImplement.start_time">
                          <el-date-picker
                            v-model="form.changeImplement.start_time"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期"
                            class="w"
                          ></el-date-picker>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="实施结束时间" prop="changeImplement.end_time">
                          <el-date-picker
                            v-model="form.changeImplement.end_time"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            type="datetime"
                            placeholder="选择日期"
                            class="w"
                          ></el-date-picker>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="实施备注:" prop="changeImplement.implement_remark">
                          <quill-editor
                            ref="myQuillEditor1"
                            v-model="form.changeImplement.implement_remark"
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
                  <div class="box_header">实施完成通知</div>
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
        <el-button type="primary" size="medium" @click="save(1)">
          实施完成
        </el-button>
        <el-button type="warning" size="medium" @click="save(2)">
          实施失败
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
    <children
      ref="children"
      @fetch-data="
        {
          input2
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
    <dialogtwo
      ref="dialogtwo"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogtwo>
  </div>
</template>

<script>
  import {
    toList,
    toEdit,
    getAllChangeType,
    getChangeSource,
    getUserList,
    getChangeApproveUser,
    getChange,
    toImplement
  } from '@/api/change'
  import vabQuill from '@/plugins/vabQuill'
  import Children from '@/components/gongdanclass'
  import Dialogone from '@/components/fanganclass'
  import {download} from '@/api/file'

  export default {
    name: 'changeImplementFront',
    components: {vabQuill, Children, Dialogone},
    data() {
      return {
        logsName: '',
        eventsName: '',
        questionsName: '',
        elementLoadingText: '正在加载...',
        assetsName: '',
        stepData: [],
        timeStatus: 0,
        tableData: [],
        fileList: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],
        form: {
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
        queryForm: {id: this.$route.query.id},
        tableDataEvent: [],
        selectRowsEvent: '',

        tableDataSet: [],
        selectRowsSet: '',

        tableDataQuest: [],
        selectRowsQuest: '',
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

        rules: {
          'changeImplement.implement_remark': [
            {required: true, trigger: 'blur', message: '请输入实施备注'},
          ],
          'changeImplement.start_time': [
            {
              required: true,
              message: '请输入实施开始时间',
              trigger: 'blur',
            },
          ],
          'changeImplement.end_time': [
            {
              required: true,
              message: '请输入实施结束时间',
              trigger: 'blur',
            },
          ],
        },
        fileManageInfoList: [],//文件显示
        borderColor: '#dcdfe6',
        checked: false,
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        srcList: [],
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
      this.fetchData()
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
      handleExceed() {
        console.log(55);
      },
      beforeRemove() {
        console.log(444);
      },
      handlePreview() {
        console.log(444);
      },
      handleRemove() {
        console.log(4441);
      },
      downloadPic(id, name) {
        download(id).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', name)
          document.body.appendChild(link)
          link.click()
        })
      },
      handlegd() {
        this.$refs['children'].showWin()
      },
      handlefa() {
        this.$refs['dialogone'].showWin()
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
      save(status) {
        console.log(this.form);
        this.$refs['form'].validate(async (valid) => {

          if (valid) {
            console.log(this.form);
            this.form.changeImplement.status = status;
            this.form.changeNode.approve_status = status;

            const {data} = toImplement(this.form);
            this.$baseMessage('成功', 'success')
            this.$emit('fetch-data')
            this.close()
          }
        })
      },
      async fetchData() {
        this.listLoading = true
        console.log(this.queryForm)
        const {data} = await getChange(this.queryForm)
        this.form = data;
        this.form.fileManageInfoList.forEach((item) => {
          item.base64 = 'data:image/png;base64,' + item.base64;
          this.srcList.push(item.base64);
        });
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

      .el-upload {
        text-align: left;
      }

      .text-p {
        p {
          margin: 0;
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
