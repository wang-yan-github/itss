<template>
  <div class="main-container pb80">
    <el-form ref="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <div class="box_card">
                  <div class="box_header">工单信息</div>
                  <div class="box_content">
                    <el-form-item label="标题:">
                      {{ form.title }}
                    </el-form-item>
                    <el-form-item label="描述:">
                      <!--                      <el-image :src="url"></el-image>-->
                      <div class="ql-editor content-detail" v-html="form.description"></div>
                    </el-form-item>
                    <el-form-item label="附件:">
                      <div class="bghui" v-for="item in this.fileManageHandleList" :key="item.id">
                        <div class="r">
                          <p class="p1" style="margin: 0; color: #333" @click="downloadPic(item.id, item.file_name)">
                            {{item.file_name}}
                          </p>
                        </div>
                      </div>
                    </el-form-item>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">处理信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="开单人:" class="">
                          {{ form.create_user_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="开单时间:" class="">
                          {{ form.create_time }}
                        </el-form-item>
                      </el-col>

                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应人:" class="">
                          {{ form.handler_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应时间:" class="">
                          {{ form.update_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="预计解决时间:" class="custemitem">
                          <el-date-picker
                            v-model="form.ex_solve_time"
                            type="date"
                          ></el-date-picker>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决方式:" class="">
                          <el-select v-model="form.solve_type" placeholder="请示类型">
                            <el-option
                              v-for="item in list1"
                              :key="item.id"
                              :label="item.solution_name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="方案分类:" class="">
                          <el-input
                            v-model.trim="form.programme_type_name"
                            clearable
                            @click.native="handlefa"
                          >
                            <!--<el-button-->
                              <!--slot="append"-->
                              <!--icon="el-icon-search"-->
                              <!--@click="handlefa"-->
                            <!--&gt;</el-button>-->
                          </el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="12" :lg="6" :xl="6">
                        <el-form-item label="费用:" class="">
                          <el-input v-model="form.expenses"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                        <el-form-item label="协同处理人:" class="">
                          <el-select v-model="form.synergiaUsers" multiple filterable collapse-tags placeholder="请选择">
                            <el-option
                              v-for="item in synergiaUsersList"
                              :key="item.id"
                              :label="item.user_name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="解决方案:" class="">
                          <!--                          <el-image :src="url"></el-image>-->
                          <vab-quill
                            v-model="form.solution"
                            :min-height="200"
                            :options="options"
                          ></vab-quill>
                          <p class="ts">
                            请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                          </p>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
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
                            :file-list="clFileList"
                            :on-change="onChangeHandle">
                            <el-button size="small" type="primary">点击上传</el-button>
                          </el-upload>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>

              </el-tab-pane>
              <el-tab-pane :label="sevenUser" name="second">
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

              <el-tab-pane :label="oneUser" name="third">
                <el-table :data="form.eventAssistUsers" stripe border @selection-change="setSelectPeopleRows">
                  <el-table-column show-overflow-tooltip type="selection" width="40px;" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" label="序号" width="70px;" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="status" label="状态" align="center" :formatter="formatStatus"></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button type="danger" icon="el-icon-delete" @click="handleDelete">移除选择对象</el-button>
                  <el-button type="primary" icon="el-icon-plus" @click="handlePeople">添加协查人</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="towUser" name="fourth">
                <el-table
                  v-loading="listLoading"
                  :data="form.assetsManages"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelectAssetsManageRows"
                  height="400"
                >
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" width="70px;" label="序号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="deptName" label="部门" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="receiverName" label="领用人" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" align="center">
                    <template #default="scope">
                      <el-tag size="mini" icon="el-icon-edit" v-if="scope.row.is_secrecy == '0'" type="primary">否</el-tag>
                      <el-tag size="mini" icon="el-icon-delete" class="red" v-if="scope.row.is_secrecy == '1'" type="success">是</el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="assetsStatusName" label="资产状态" align="center">
                    <template #default="scope">
                      <el-tag size="mini" icon="el-icon-delete" class="red" type="success">
                        {{ scope.row.assetsStatusName }}
                      </el-tag>
                    </template>
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="itStatusName" label="IT状态" align="center"></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button type="danger" icon="el-icon-delete" @click="handleDeleteAssetsManage">移除选择对象</el-button>
                  <el-button type="primary" icon="el-icon-plus" @click="handleAssetsManageAdd">添加关联的资产</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="threeUser" name="five">
                <el-table
                  v-loading="listLoading"
                  :data="form.eventList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelectEventRows">
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" width="70px;" label="序号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" label="标题" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" ></el-table-column>

                  <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" sortable>
                    <template #default="{ row }">
                      <el-rate v-model="row.event_satisfaction" disabled>
                      </el-rate>
                    </template>
                  </el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button type="danger" icon="el-icon-delete" @click="handleDeleteEvents">移除选择对象</el-button>
                  <el-button type="primary" icon="el-icon-plus" @click="handleEventsAdd">添加关联的工单</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="fourUser" name="six">
                <el-table
                  v-loading="listLoading"
                  :data="form.questionList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelecQuestionRows">
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" width="70px;" label="序号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="code" label="问题单号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" label="标题" align="center">
                    <!--<template #default="{ row }">-->
                    <!--<el-link @click="handleView(row)">{{row.title}}</el-link>-->
                    <!--</template>-->
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_user_name" label="创建人" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" label="创建时间" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="category_name" label="问题类别" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="hope_solve_time" label="期望解决时间" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="priority_name" label="优先级" align="center" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="now_operator_user_name" align="center" label="当前操作人" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="solve_time" align="center" label="解决时间" ></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button type="danger" icon="el-icon-delete" @click="handleDeleteQuestion">移除选择对象</el-button>
                  <el-button type="primary" icon="el-icon-plus" @click="handleQuestionAdd">添加关联的问题</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="fiveUser" name="seven">
                <el-table
                  v-loading="listLoading"
                  :data="form.changeList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelecChangeRows">
                  <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;" ></el-table-column>
                  <el-table-column show-overflow-tooltip prop="change_no" label="变更单号" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" label="标题" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="start_time" label="计划开始时间" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="classify_name" align="center" label="变更类别"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="type_name" align="center" label="变更类型"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="approve_name" align="center" label="审批人"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="approve_time" align="center" label="审批时间"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_time" align="center" label="创建时间">
                    <template slot-scope="scope">
                      <span>{{ parseDateFull(scope.row.create_time) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="status_name" align="center"
                                   label="状态"></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button type="danger" icon="el-icon-delete" @click="handleDeleteChange">移除选择对象</el-button>
                  <el-button type="primary" icon="el-icon-plus" @click="handleChangeAdd">添加关联变更</el-button>
                </div>
              </el-tab-pane>
              <el-tab-pane :label="sixUser" name="eight">
                <el-table
                  v-loading="listLoading"
                  :data="tableData"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @current-change="handleCurrentChange"
                  height="560"
                >
                  <el-table-column show-overflow-tooltip  label="序号" width="50px;" align="center">
                    <template slot-scope="scope">
                      {{(queryUserForm.pageIndex-1) * queryUserForm.pageSize+scope.$index+1}}
                    </template>
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="create_user_name" label="姓名" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="title" label="标题" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="statusName" label="状态" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="category_name" label="类型" align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="phone" label="电话" align="center"></el-table-column>
                </el-table>
                <el-pagination
                  background
                  :current-page="queryUserForm.pageIndex"
                  :page-size="queryUserForm.pageSize"
                  :layout="layout"
                  :total="total"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange2"
                ></el-pagination>
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
                <el-form-item label="状态:" class="mb0">{{ form.statusName }}</el-form-item>
                <el-form-item label="服务群组:" class="mb0">
                  {{ form.service_name }}
                </el-form-item>
                <el-form-item label="来源:" class="mb0">{{ form.source_name }}</el-form-item>
                <el-form-item label="请求类型:" class="mb0">{{ form.request_type_name }}</el-form-item>
                <el-form-item label="工单类别:" class="mb0">{{ form.event_category_name }}</el-form-item>
                <el-form-item label="影响度:" class="mb0">{{ form.effect_degree_name }}</el-form-item>
                <el-form-item label="紧急度:" class="mb0">{{ form.urgency_degree_name }}</el-form-item>
                <el-form-item label="优先级:" class="mb0">{{ form.priority_name }}</el-form-item>
<!--                <el-form-item label="报障类别:" class="mb0">-->
<!--                  职场1-办公设备-->
<!--                </el-form-item>-->
                <el-form-item label="重开次数:" class="mb0">{{ form.numberOfRestarts }}</el-form-item>
                <el-form-item
                  label="影响可用性:"
                  class="custemitem mb0"
                >
                  {{ form.is_effect == 0? '否' : '是' }}
                </el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">用户信息</div>
              <div class="box_content">
                <el-form-item label="用户:" class="mb0">{{ form.user_id_loginName }}</el-form-item>
                <el-form-item label="姓名:" class="mb0">{{ form.user_id_name }}</el-form-item>
<!--                <el-form-item label="公司:" class="mb0">-->
<!--                  鼎驰科技有限公司-->
<!--                </el-form-item>-->
                <el-form-item label="部门:" class="mb0">{{ form.deptName }}</el-form-item>
                <el-form-item label="职位:" class="mb0">{{ form.position }}</el-form-item>
                <el-form-item label="位置:" class="mb0">{{ form.location }}</el-form-item>
                <el-form-item label="电话:" class="mb0">{{ form.phone }}</el-form-item>
                <el-form-item label="座机:" class="mb0">{{ form.landline }}</el-form-item>
                <el-form-item label="邮箱:" class="mb0">{{ form.email }}</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">时间信息</div>
              <div class="box_content step_content">
                <el-steps :active="3" align-center>
                  <el-step title="开单" :description="form.create_time"></el-step>
                  <el-step title="响应" :description="form.responseTime"></el-step>
                  <el-step title="解决" description=""></el-step>
                  <el-step title="关单" description=""></el-step>
                </el-steps>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="save">
          保存
        </el-button>
        <el-button type="warning" size="medium" @click="saveClose">
          保存并关单
        </el-button>

      </div>
    </el-form>

    <dialogthree ref="dialogthree" @programmeTypeData="deptset3" @fetch-data=" { input2 }"></dialogthree>
    <XieChaRen ref="XieChaRen" @listData="getPeopleData" @fetch-data=" { input2 }"></XieChaRen>
    <assetsManagesDialog ref="assetsManagesDialog" @listData="listAssetsManages"
                         @fetch-data=" { input2 }"></assetsManagesDialog>
    <eventDialog ref="eventDialog" @listData="listEventLists" @fetch-data=" { input2 }"></eventDialog>
    <questionDialog ref="questionDialog" @listData="listQuestions" @fetch-data=" { input2 }"></questionDialog>
    <changeDialog ref="changeDialog" @listData="listChanges" @fetch-data=" { input2 }"></changeDialog>
  </div>
</template>

<script>
  import vabQuill from '@/plugins/vabQuill'
  import {doEdit, getById, getEventImpactMap, getRequestTypeMap, getSourceMap, userHistory} from "@/api/eventAllManage";
  import {getSynergiaUsers, process, processClose} from '@/api/event'
  import {getAllTypeInfo} from '@/api/eventSolutionType'
  import {download} from "@/api/file";

  import Dialogthree from '@/components/fanganclass'
  import XieChaRen from "@/components/UsersDia";
  import assetsManagesDialog from "@/components/assetsTypeDia";
  import eventDialog from "@/components/eventDialog";
  import questionDialog from "@/components/questDialog";
  import changeDialog from '@/components/changeDialog';

  export default {
    name: 'workingchuli',
    components: {
      vabQuill,
      Dialogthree,
      XieChaRen,
      assetsManagesDialog,
      eventDialog,
      questionDialog,
      changeDialog,
    },
    data() {
      return {
        selectPeopleRows: '',
        selectAssetsManageRows: '',
        selectEventRows: '',
        selectQuestionRows: '',
        selectChangeRows: '',
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        queryUserForm: {
          pageIndex: 1,
          pageSize: 20,
          user_id: '',
        },
        oneUser: '协查人',
        towUser: '关联的配置项',
        threeUser: '关联的工单',
        fourUser: '关联的问题',
        fiveUser: '关联的变更',
        sixUser: '用户历史请求',
        sevenUser: '工单日志',
        fileManageHandleList: [],
        list1: [],
        synergiaUsersList: [],
        input2: '',
        listLoading: true,
        sourceList: [],
        requestTypeMapList: [],
        eventImpactList: [],
        urgencyDegreeList: [],
        elementLoadingText: '正在加载...',
        tableData: [],
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
          title: '',
          description: '',
          source_name: '',
          source_id: '',
          request_type: '',
          request_type_name: '',
          event_category_id: '',
          event_category_name: '',
          ex_solve_time: '',
          close_user: '',
          close_user_name: '',
          closeTime: '',
          pauseTime: '',
          effect_degree: '',
          effect_degree_name: '',
          urgency_degree_id: '',
          urgency_degree_name: '',
          is_effect: '',
          priority_name: '',
          create_user: '',
          create_user_name: '',
          assigner_name: '',
          assigner_time: '',
          create_time: '',
          handler: '',
          handler_name: '',
          update_time: '',
          responseTime: '',
          solve_user_name: '',
          expenses: '',
          solve_type: '',
          synergiaUsers: '',
          programme_type: '',
          programme_type_name: '',
          solution: '',
          meetResponseSLA: '',

          id: '',
          numberOfRestarts: '',
          status: '',
          statusName: '',
          department: '',
          deptName: '',
          position: '',
          location: '',
          phone: '',
          landline: '',
          email: '',
          service_name: '',

          user_id: '',
          user_id_name: '',
          eventAssistUsers: [],
          assetsManages: [],
          eventList: [],
          questionList: [],
          changeList: [],

          permissions: [],
          bumen: '',
          isvip: '',
          content: '',
        },
        rules: {
          title: [
            {required: true, trigger: 'blur', message: '请输入'},
          ],
          content: [
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
        fileList: [],
        activeName: 'first',
        title: '',
        value: 3.7,
        dialogFormVisible: false,
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
        clFileList: [],
      }
    },
    created() {
      this.getData();
      this.getSourceData();
      this.getRequestTypeMap();
      this.getEventImpactMap();
      this.getUrgencyDegreeMap();
      this.fetchData1();
      this.getSynergiaUsersData();
    },
    methods: {
      // 处理附件信息
      handleRemoveHandle(file, fileList) {
        this.clFileList = fileList;
        // console.log(file, fileList);
      },
      beforeRemoveHandle(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      handleExceedHandle(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      //附件上传 选择文件
      onChangeHandle(file, fileList) {
        //获取上传文件大小
        let imgSize = Number(file.size / 1024 / 1024);

        if (imgSize > 3) {
          this.$msgbox({
            title: "",
            message: "文件大小不能超过3MB，请重新上传。",
            type: "warning",
          });
          return;
        }else{
          this.clFileList = fileList;
        }
        // this.clFileList = fileList;
      },
      //下载附件
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
      // 变更多选
      setSelecChangeRows(val) {
        this.selectChangeRows = val
      },
      // 变更列表删除
      handleDeleteChange() {
        if (this.selectChangeRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectChangeRows.forEach((item, i) => {
              this.form.changeList.some((x, i) => {
                if (x.id == item.id) {
                  this.form.changeList.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.fiveUser = "关联的变更(" + this.form.changeList.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 变更数据回显
      listChanges(rows) {
        rows.forEach((item, i) => {
          let index = this.form.changeList.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.changeList.push(item)
          }
        })
        this.fiveUser = "关联的变更(" + this.form.changeList.length + ")";
      },
      // 变更关联新增
      handleChangeAdd() {
        this.$refs['changeDialog'].showWin()
      },
      // 问题设置多选
      setSelecQuestionRows(val) {
        this.selectQuestionRows = val
      },
      // 问题删除
      handleDeleteQuestion() {
        if (this.selectQuestionRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectQuestionRows.forEach((item, i) => {
              this.form.questionList.some((x, i) => {
                if (x.id == item.id) {
                  this.form.questionList.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            //fourUser
            this.fourUser = "关联的问题(" + this.form.questionList.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //问题列表回显
      listQuestions(rows) {
        rows.forEach((item, i) => {
          let index = this.form.questionList.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.questionList.push(item)
          }
        })
        this.fourUser = "关联的问题(" + this.form.questionList.length + ")";
      },
      // 问题关联新增
      handleQuestionAdd() {
        this.$refs['questionDialog'].showWin(this.form.questionList)
      },
      // 工单设置多选
      setSelectEventRows(val) {
        this.selectEventRows = val
      },
      // 工单删除
      handleDeleteEvents() {
        if (this.selectEventRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectEventRows.forEach((item, i) => {
              this.form.eventList.some((x, i) => {
                if (x.id == item.id) {
                  this.form.eventList.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.threeUser = "关联的工单(" + this.form.eventList.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 工单回显
      listEventLists(rows) {
        rows.forEach((item, i) => {
          let index = this.form.eventList.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.eventList.push(item)
          }
        })
        this.threeUser = "关联的工单(" + this.form.eventList.length + ")";
      },
      // 工单新增
      handleEventsAdd() {
        this.$refs['eventDialog'].showWin()
      },
      // 关联项设置多选
      setSelectAssetsManageRows(val) {
        this.selectAssetsManageRows = val
      },
      // 关联项删除
      handleDeleteAssetsManage() {
        if (this.selectAssetsManageRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectAssetsManageRows.forEach((item, i) => {
              this.form.assetsManages.some((x, i) => {
                if (x.id == item.id) {
                  this.form.assetsManages.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.towUser = "关联的配置项(" + this.form.assetsManages.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 关联项回写
      listAssetsManages(rows) {
        rows.forEach((item, i) => {
          let index = this.form.assetsManages.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.assetsManages.push(item)
          }
        })
        this.towUser = "关联的配置项(" + this.form.assetsManages.length + ")";
      },
      // 关联项添加跳转
      handleAssetsManageAdd() {
        this.$refs['assetsManagesDialog'].showWin()
      },
      // 协查人多选
      setSelectPeopleRows(val) {
        this.selectPeopleRows = val
      },
      // 协查人数据回显
      getPeopleData(rows) {
        rows.forEach((item, i) => {
          let index = this.form.eventAssistUsers.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.eventAssistUsers.push(item)
          }
        })
        this.oneUser = "协查人(" + this.form.eventAssistUsers.length + ")";
      },
      //协查人添加
      handlePeople() {
        this.$refs['XieChaRen'].showWin()
      },
      //协查人列表删除
      handleDelete() {
        if (this.selectPeopleRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectPeopleRows.forEach((item, i) => {
              this.form.eventAssistUsers.some((x, i) => {
                if (x.id == item.id) {
                  this.form.eventAssistUsers.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.oneUser = "协查人(" + this.form.eventAssistUsers.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //用户历史请求
      userHistoryListClick() {
        if (this.form.user_id != null && this.form.user_id != '' && this.form.user_id != undefined) {
          this.queryUserForm.user_id = this.form.user_id;
          userHistory(this.queryUserForm).then((res) => {
            if (res.code == 0) {
              this.tableData = res.data.list;
              this.total = res.data.total
              setTimeout(() => {
                this.listLoading = false
              }, 300)
              this.sixUser = "用户历史请求(" + this.total + ")";
            }
          })

        }
      },
      handleSizeChange(val) {
        this.queryUserForm.pageSize = val
        this.userHistoryListClick();
      },
      handleCurrentChange(val) {
        this.queryUserForm.pageIndex = val
        this.userHistoryListClick();
      },
      handleCurrentChange2(val) {
        this.queryUserForm.pageIndex = val
        this.userHistoryListClick();
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
      handlefa() {
        this.$refs['dialogthree'].showWin()
      },
      async fetchData1() {
        const {data} = await getAllTypeInfo();
        this.list1 = data;
      },
      async getSynergiaUsersData() {
        const query = this.$route.query;
        const {data} = await getSynergiaUsers({id: query.id});
        this.synergiaUsersList = data;
      },
      handleAdd() {
        this.dialogFormVisible = true;
      },
      fetchData() {
        this.getData();
      },
      handleRemove(file, fileList) {
        console.log(file, fileList);
      },
      beforeRemove(file, fileList) {
        // console.log(file, fileList);
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },

      // --------------------------------------------------
      async getData() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getById({id: query.id})
        this.form = data.data

        console.log(typeof(data.data.synergiaUsers))
        debugger
        if(data.data.synergiaUsers && data.data.synergiaUsers != '[]'){
          data.data.synergiaUsers = data.data.synergiaUsers.replace('[','')
          data.data.synergiaUsers = data.data.synergiaUsers.replace(']','')
          const arr = data.data.synergiaUsers.split(',')
          const list = []
          arr.map(item => {
            list.push(parseInt(item))
          })
          this.form.synergiaUsers = list
        }else {
          this.form.synergiaUsers = []
        }

        // this.form.service_groups_id = data.data.service_groups_id + "";
        //协查人
        this.oneUser = "协查人(" + this.form.eventAssistUsers.length + ")";
        this.towUser = "关联的配置项(" + this.form.assetsManages.length + ")";
        this.threeUser = "关联的工单(" + this.form.eventList.length + ")";
        this.fourUser = "关联的问题(" + this.form.questionList.length + ")";
        this.fiveUser = "关联的变更(" + this.form.changeList.length + ")";
        this.sixUser = "用户历史请求(" + this.form.eventAssistUsers.length + ")";
        this.sevenUser = "工单日志(" + this.form.sysLogs.length + ")";
        //处理信息 附件
        if (this.form.files) {
          this.form.files.forEach((x) => {
            this.fileManageHandleList.push({file_name: x.file_name, id: x.id})
          })
        }

        //处理信息 附件
        if (this.form.files1) {
          this.form.files1.forEach((x) => {
            this.clFileList.push({name: x.file_name, url: x.id})
          })
        }
        this.userHistoryListClick();
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getSourceData() {
        var param = {};
        const {data} = await getSourceMap(param)
        this.sourceList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getRequestTypeMap() {
        var param = {};
        const {data} = await getRequestTypeMap(param)
        this.requestTypeMapList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getEventImpactMap() {
        var param = {};
        const {data} = await getEventImpactMap(param)
        this.eventImpactList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getUrgencyDegreeMap() {
        var param = {};
        const {data} = await getEventImpactMap(param)
        this.urgencyDegreeList = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      deptset3(data) {
        this.form.programme_type_name = data.name;
        this.form.programme_type = data.id;
      },
      async save() {
        this.loading = true;
        this.loadingText = '保存中...';
        const params = new FormData();
        const uploadedHandleFiles = [];
        for (let i = 0; i < this.clFileList.length; i++) {
          const f = this.clFileList[i];
          if (f.status == 'ready') {
            params.append('fileHandle', f.raw);
          } else if (f.status == 'success') {
            uploadedHandleFiles.push(f.url);
          }
        }
        this.form.uploadedHandleFiles = uploadedHandleFiles.join(',');

        params.append('body', JSON.stringify(this.form));
        process(params).then((res) => {
          if (res.code == 0) {
            this.$baseMessage('成功', 'success');
            // 调用全局挂载的方法
            this.$store.dispatch('tabsBar/delRoute', this.$route)
            // 返回上一步路由
            this.$router.go(-1)
          }
        });
        this.loading = false
        //
        // const { msg } = await process(this.form)
        // this.$baseMessage(msg, 'success')
        // // this.$emit('fetch-data')
        //
        // // 调用全局挂载的方法
        // this.$store.dispatch('tabsBar/delRoute', this.$route)
        // // 返回上一步路由
        // this.$router.go(-1)
      },
      async saveClose() {
        // this.form.ex_solve_time = new Date();
        // this.form.closeTime = new Date();
        const {msg} = await processClose(this.form)
        this.$baseMessage(msg, 'success')
        // this.$emit('fetch-data')

        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)


      },

      formatStatus(row, column, cellValue) {
        switch (cellValue) {
          case '1':
            return '活动'
          case '2':
            return '暂停'
          case '3':
            return '已禁用'
          default:
            return ''
        }
      },
      //然后定义函数parseDateFull的实现
      parseDateFull(time) {
        // 判断是否为空
        if (time == null || time == '') {
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
        return z.y + "-" + z.M + "-" + z.d;
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
        .el-upload {
          display: inline-block;
        }
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

      .step_content {
        overflow-x: auto;
      }

      .text-p {
        p {
          margin: 0;
        }
      }

      .bghui {
        .p1 {
          cursor: pointer;
        }
      }
    }
    .content-detail{
      padding-top: 7px;
      padding-left: 0;
    }
  }
</style>
