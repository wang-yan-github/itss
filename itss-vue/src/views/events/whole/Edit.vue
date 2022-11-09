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
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="标题:" prop="title">
                          <el-input v-model="form.title"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="描述:" prop="description">
                          <quill-editor
                            ref="myQuillEditor1"
                            v-model="form.description"
                            :min-height="200"
                            :options="options"
                          ></quill-editor>
                          <p class="ts">
                            请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                          </p>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="附件:" style="width: 400px">
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
                            :on-change="onChange"
                          >
                            <el-button size="small" type="primary">
                              点击上传
                            </el-button>
                          </el-upload>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">分类信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="来源:">
                          <el-select v-model="form.source_id" class="w">
                            <el-option
                              v-for="(item, index) in sourceList"
                              :key="index"
                              :label="item.resource_name"
                              :value="item.id"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="请求类型:">
                          <el-select v-model="form.request_type" class="w">
                            <el-option
                              v-for="(item, index) in requestTypeMapList"
                              :key="index"
                              :label="item.request_type_name"
                              :value="item.id"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="工单类别:">
                          <el-input
                            v-model.trim="form.event_category_name"
                            placeholder=""
                            @click.native="handlegd"
                          >
                            <!--<el-button-->
                            <!--slot="append"-->
                            <!--icon="el-icon-search"-->
                            <!--@click="handlegd"-->
                            <!--&gt;</el-button>-->
                          </el-input>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="影响度:">
                          <el-select v-model="form.effect_degree" @change="getFirst" class="w">
                            <el-option
                              v-for="(item, index) in eventImpactList"
                              :key="index"
                              :label="item.impact_name"
                              :value="item.id"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="紧急度:">
                          <el-select v-model="form.urgency_degree_id" @change="getFirst" class="w">
                            <el-option
                              v-for="item in urgencyList"
                              :key="item.id"
                              :label="item.urgent_name"
                              :value="item.id"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="影响可用性:">
                          <el-select v-model="form.is_effect" class="w">
                            <el-option label="否" value="0"></el-option>
                            <el-option label="是" value="1"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="优先级:">
                          {{ form.priority_name }}
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="响应SLA/ 解决SLA:" class="tsitem">
                          {{ this.ResponseSLA }}
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">处理信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="开单人:" class="">
                          {{ form.create_user_name }}
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="开单时间:" class="">
                          {{ form.create_time }}
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="处理人:" class="">
                          {{ form.handler_name }}
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="上次更新时间:" class="">
                          {{ form.update_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="响应时间:" class="">
                          {{ form.responseTime }}
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="预计解决时间:">
                          <el-date-picker
                            v-model="form.ex_solve_time"
                            type="date"
                            class="w"
                          ></el-date-picker>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="解决方式:">
                          <el-select v-model="form.solve_type" clearable class="w">
                            <el-option
                              v-for="item in jjfsList"
                              :key="item.id"
                              :label="item.solution_name"
                              :value="item.id"
                            ></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="方案分类:">
                          <el-input
                            v-model.trim="form.programme_type_name"
                            placeholder=""
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
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="费用:">
                          <el-input v-model="form.expenses"></el-input>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="解决方案:">
                          <quill-editor
                            ref="myQuillEditor2"
                            v-model="form.solution"
                            :min-height="200"
                            :options="options"
                          ></quill-editor>
                          <p class="ts">
                            请详细填写问题原因和解决方案请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                            *
                          </p>
                        </el-form-item>
                      </el-col>
                       <el-col :xs="24" :sm="12" :md="6" :lg="12" :xl="6">
                        <el-form-item label="附件:" style="width: 400px">
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
                            :on-change="onChangeHandle"
                          >
                            <el-button size="small" type="primary">
                              点击上传
                            </el-button>
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
                          <div
                            class="ql-editor content-detail"
                            v-html="log.content"
                          />
                        </el-card>
                      </el-timeline-item>
                    </el-timeline>
                  </div>
                </div>
              </el-tab-pane>

              <el-tab-pane :label="oneUser" name="third">
                <el-table
                  :data="form.eventAssistUsers"
                  stripe
                  border
                  @selection-change="setSelectPeopleRows"
                  height="calc(100vh - 400px)"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="60px;"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="user_name"
                    label="用户名"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="name"
                    label="姓名"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="departmentName"
                    label="部门"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="telephone"
                    label="电话"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="mailbox"
                    label="邮箱"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="status"
                    label="状态"                   
                    align="center"
                    :formatter="formatStatus"
                  ></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDelete"
                  >
                    移除选择对象
                  </el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click="handlePeople"
                  >
                    添加协查人
                  </el-button>
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
                  height="calc(100vh - 400px)"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="60px;"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_number"
                    label="资产编号"    
                    width="160px;"              
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assetsTypeName"
                    label="配置项类型"
                    width="160px;"  
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="childAssetName"
                    label="配置子类" 
                    width="160px;"                    
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_name"
                    label="资产名称"
                    min-width="160px;"                    
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="deptName"
                    label="部门"
                    width="160px;"                    
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="receiverName"
                    label="领用人"  
                    width="120px;"                   
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="purchaseCompanyName"
                    label="购置公司"   
                    width="260px;"                  
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="is_secrecy"
                    label="保密"     
                    width="120px;"                
                    align="center"
                  >
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

                  <el-table-column
                    show-overflow-tooltip
                    prop="assetsStatusName"
                    label="资产状态"   
                    width="120px;"                 
                    align="center"
                  >
                    <template #default="scope">
                      <el-tag
                        size="mini"
                        icon="el-icon-delete"
                        class="red"
                        type="success"
                      >
                        {{ scope.row.assetsStatusName }}
                      </el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="itStatusName"
                    label="IT状态"  
                    width="120px;"                  
                    align="center"
                  ></el-table-column>
                </el-table>
                <div style="margin-top: 10px">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDeleteAssetsManage"
                  >
                    移除选择对象
                  </el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click="handleAssetsManageAdd"
                  >
                    添加关联的资产
                  </el-button>
                </div>
              </el-tab-pane>

              <el-tab-pane :label="threeUser" name="five">
                <el-table
                  v-loading="listLoading"
                  :data="form.eventList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelectEventRows"
                  height="calc(100vh - 400px)"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="60px;"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="title"
                    min-width="360px;"
                    label="标题"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="user_id_name"
                    width="120px;"
                    label="用户"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="category_name"
                    label="工单类别"
                    width="120px;"
                    align="center"                   
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_user_name"
                    label="开单人"
                    width="120px;"
                    align="center"                    
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_time"
                    label="开单时间"
                    width="180px;"
                    align="center"                   
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="service_name"
                    label="服务群组"
                    width="120px;"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="handler_name"
                    label="处理人"
                    width="120px;"
                    align="center"                    
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="ex_solve_time"
                    align="center"
                    label="解决时间"
                    width="180px;"
                  ></el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="statusName"
                    align="center"
                    label="状态"
                    width="120px;"
                  ></el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="event_satisfaction"
                    align="center"
                    label="用户满意度"
                    width="180px;"
                  >
                    <template #default="{ row }">
                      <el-rate
                        v-model="row.event_satisfaction"
                        disabled
                      ></el-rate>
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

              <el-tab-pane :label="fourUser" name="six">
                <el-table
                  v-loading="listLoading"
                  :data="form.questionList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelecQuestionRows"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="60px;"
                    align="center"
                  ></el-table-column>
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
                <div style="margin-top: 10px">
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    @click="handleDeleteQuestion"
                  >
                    移除选择对象
                  </el-button>
                  <el-button
                    type="primary"
                    icon="el-icon-plus"
                    @click="handleQuestionAdd"
                  >
                    添加关联的问题
                  </el-button>
                </div>
              </el-tab-pane>

              <el-tab-pane :label="fiveUser" name="seven">
                <el-table
                  v-loading="listLoading"
                  :data="form.changeList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  @selection-change="setSelecChangeRows"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    align="center"
                    width="60px;"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="change_no"
                    sortable
                    label="变更单号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="title"
                    sortable
                    label="标题"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="start_time"
                    label="计划开始时间"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="classify_name"
                    align="center"
                    sortable
                    label="变更类别"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="type_name"
                    align="center"
                    sortable
                    label="变更类型"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="approve_name"
                    align="center"
                    label="审批人"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="approve_time"
                    align="center"
                    label="审批时间"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_time"
                    align="center"
                    label="创建时间"
                  >
                    <template slot-scope="scope">
                      <span>{{ parseDateFull(scope.row.create_time) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="status_name"
                    align="center"
                    label="状态"
                  ></el-table-column>
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
                <el-form-item label="状态:" class="mb0">
                  {{ form.statusName }}
                </el-form-item>
                <el-form-item label="服务群组:" class="mb0">
                  {{ form.service_name }}
                </el-form-item>
                <el-form-item label="来源:" class="mb0">
                  {{ form.source_name }}
                </el-form-item>
                <el-form-item label="请求类型:" class="mb0">
                  {{ form.request_type_name }}
                </el-form-item>
                <el-form-item label="工单类别:" class="mb0">
                  {{ form.event_category_name }}
                </el-form-item>
                <el-form-item label="影响度:" class="mb0">
                  {{ form.effect_degree_name }}
                </el-form-item>
                <el-form-item label="紧急度:" class="mb0">
                  {{ form.urgency_degree_name }}
                </el-form-item>
                <el-form-item label="优先级:" class="mb0">
                  {{ form.priority_name }}
                </el-form-item>
                <!-- <el-form-item label="报障类别:" class="mb0">
                  职场1-办公设备
                </el-form-item> -->
                <el-form-item label="重开次数:" class="mb0">
                  {{ form.numberOfRestarts }}
                </el-form-item>
                <el-form-item label="影响可用性:" class="custemitem mb0">
                  {{ form.is_effect == 0 ? '否' : '是' }}
                </el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">用户信息</div>
              <div class="box_content">
                <el-form-item label="用户:" class="mb0">
                  {{ form.user_id_loginName }}
                </el-form-item>
                <el-form-item label="姓名:" class="mb0">
                  {{ form.user_id_name }}
                </el-form-item>
                <!--                <el-form-item label="公司:" class="mb0">-->
                <!--                  {{ form.service_name}}-->
                <!--                </el-form-item>-->
                <el-form-item label="部门:" class="mb0">
                  {{ form.deptName }}
                </el-form-item>
                <el-form-item label="职位:" class="mb0">
                  {{ form.position }}
                </el-form-item>
                <el-form-item label="位置:" class="mb0">
                  {{ form.location }}
                </el-form-item>
                <el-form-item label="电话:" class="mb0">
                  {{ form.phone }}
                </el-form-item>
                <el-form-item label="座机:" class="mb0">
                  {{ form.landline }}
                </el-form-item>
                <el-form-item label="邮箱:" class="mb0">
                  {{ form.email }}
                </el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">时间信息</div>
              <div class="box_content step_content">
                <!--<el-steps :active="1" align-center>-->
                <!--<el-step title="开单" description="03-15 10:29"></el-step>-->
                <!--<el-step title="响应" description="03-15 10:39"></el-step>-->
                <!--<el-step title="解决" description="03-15 10:40"></el-step>-->
                <!--<el-step title="关单" description="03-15 10:40"></el-step>-->
                <!--</el-steps>-->

                <el-steps :active="timeStatus" :style="{ width: stepData.length * 80 + 'px' }">
                  <el-step
                    :title="item.title"
                    v-for="item in stepData"
                    :key="item.id"
                    :description="item.description"
                  ></el-step>
                </el-steps>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button
          type="primary"
          size="medium"
          :loading="loading"
          @click="saveok"
        >
          {{ loadingText }}
        </el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <children ref="children" @categoryData="getCategoryData"></children>
    <dialogone
      ref="dialogone"
      @programmeTypeData="getProgrammeTypeData"
    ></dialogone>

    <XieChaRen ref="XieChaRen" @listData="getPeopleData"></XieChaRen>
    <assetsManagesDialog
      ref="assetsManagesDialog"
      @listData="listAssetsManages"
    ></assetsManagesDialog>
    <eventDialog ref="eventDialog" @listData="listEventLists"></eventDialog>
    <questionDialog
      ref="questionDialog"
      @listData="listQuestions"
    ></questionDialog>
    <changeDialog ref="changeDialog" @listData="listChanges"></changeDialog>
  </div>
</template>

<script>
import {
  getById,
  getSourceMap,
  getRequestTypeMap,
  getEventImpactMap,
  doEdit, categoryList,
} from '@/api/eventAllManage'
import {
  affectGetAllList,
  urgencyGetAllList,
  slaEventImpactUrgentGetFirst,
} from '@/api/affect'
import { getAllTypeInfo } from '@/api/eventSolutionType'

import vabQuill from '@/plugins/vabQuill'
import Children from '@/components/gongdanclass'
import Dialogone from '@/components/fanganclass'
import XieChaRen from '@/components/UsersDia'
import assetsManagesDialog from '@/components/assetsTypeDia'
import eventDialog from '@/components/eventDialog'
import questionDialog from '@/components/questDialog'
import changeDialog from '@/components/changeDialog'
import {eventCategoryinfo} from "@/api/event_category";

export default {
  name: 'wholeEdit',
  components: {
    vabQuill,
    Children,
    Dialogone,
    XieChaRen,
    assetsManagesDialog,
    eventDialog,
    questionDialog,
    changeDialog,
  },
  data() {
    return {
      title: '',
      stepData: [],
      timeStatus: 0,
      loading: false,
      loadingText: '保存',
      listLoading: true,
      selectPeopleRows: '',
      selectAssetsManageRows: '',
      selectEventRows: '',
      selectQuestionRows: '',
      selectChangeRows: '',
      elementLoadingText: '正在加载...',
      tableData: [],
      sourceList: [],
      requestTypeMapList: [],
      eventImpactList: [],
      urgencyList: [],
      options: {
        theme: 'snow',
        bounds: document.body,
        debug: 'warn',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ color: [] }, { background: [] }],
            ['blockquote', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ align: [] }],
            [{ direction: 'rtl' }],
            [{ font: [] }],
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
        effect_degree: '',
        effect_degree_name: '',
        urgency_degree_id: '',
        urgency_degree_name: '',
        is_effect: '',
        priority_name: '',
        create_user: '',
        create_user_name: '',
        create_time: '',
        handler: '',
        handler_name: '',
        update_time: '',
        responseTime: '',
        expenses: '',
        solve_type: '',
        programme_type: '',
        programme_type_name: '',
        solution: '',

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
        remark: '',

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
        title: [{ required: true, trigger: 'blur', message: '请输入用户名' }],
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
      value: 3.7,
      input2: '',
      dialogFormVisible: false,
      username:
        'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      fileManageInfoList: [],
      fileManageHandleList: [],
      jjfsList: [],
      oneUser: '协查人',
      towUser: '关联的配置项',
      threeUser: '关联的工单',
      fourUser: '关联的问题',
      fiveUser: '关联的变更',
      sixUser: '用户历史请求',
      sevenUser: '工单日志',
      //协查人范围
      eventAssistList: [],
      ResponseSLA: '',
      queryCategroyForm: {
        eventCategoryId: '',
        first_id: '',
        request_type_id: '',
      },
      first: '',
    }
  },
  created() {
    this.urgencyGetAllList()
    this.getData()
    this.getSourceData()
    this.getRequestTypeMap()
    this.getEventImpactMap()
    this.jjfsData()
  },
  mounted() {
    this.quillToTop()
  },
  computed: {
    changeData() {
      if (
        this.form.priority_id != null &&
        this.form.priority_id != undefined &&
        this.form.priority_id != '' &&
        this.form.event_category_id != null &&
        this.form.event_category_id != undefined &&
        this.form.event_category_id != '' &&
        this.form.request_type != null &&
        this.form.request_type != undefined &&
        this.form.request_type != ''
      ) {
        this.queryCategroyForm.first_id = this.form.priority_id //考虑优先级
        this.queryCategroyForm.eventCategoryId = this.form.event_category_id //工单类别
        this.queryCategroyForm.request_type_id = this.form.request_type //考虑请求类型
        categoryList(this.queryCategroyForm).then((res) => {
          if (res.code == 0) {
            if (res.data == 1) {
              this.form.event_category_name = '' //工单类别
              this.ResponseSLA = ''
            } else {
              this.ResponseSLA =
                res.data.response_time +
                '分钟 / ' +
                res.data.solve_time +
                '分钟'
            }
          }
        })
      }
    },
  },
  watch: {
    changeData(val) {
      console.log(val)
    },
  },
  methods: {
    getFirst() {
      if (
        this.form.effect_degree != null &&
        this.form.effect_degree != '' &&
        this.form.urgency_degree_id != null &&
        this.form.urgency_degree_id != ''
      ) {
        slaEventImpactUrgentGetFirst({
          impact_id: this.form.effect_degree,
          urgent_id: this.form.urgency_degree_id,
        }).then((res) => {
          if (res.code == 0) {
            if (res.data != null) {
              this.form.priority_id = res.data.first_id
              this.first = res.data.first_name
              this.categoryListClick()
            } else {
              this.first = ''
            }
          }
        })
      } else {
        this.first = ''
      }
    },
    //根据工单id、根据工单类别id查询\优先级、请求类型查询SLA的结果
    categoryListClick() {
      if (
        this.form.priority_id != null &&
        this.form.priority_id != undefined &&
        this.form.priority_id != '' &&
        this.form.event_category_id != null &&
        this.form.event_category_id != undefined &&
        this.form.event_category_id != '' &&
        this.form.request_type != null &&
        this.form.request_type != undefined &&
        this.form.request_type != ''
      ) {
        this.queryCategroyForm.first_id = this.form.priority_id //考虑优先级
        this.queryCategroyForm.eventCategoryId = this.form.event_category_id //工单类别
        this.queryCategroyForm.request_type_id = this.form.request_type //考虑请求类型
        categoryList(this.queryCategroyForm).then((res) => {
          if (res.code == 0) {
            if (res.data == 1) {
              this.form.event_category_name = '' //工单类别
              this.ResponseSLA = ''
            } else {
              this.ResponseSLA =
                res.data.response_time +
                '分钟 / ' +
                res.data.solve_time +
                '分钟'
            }
          }
        })
      }
    },
    quillToTop() {
      this.$refs.myQuillEditor1.quill.enable(false)
      this.$refs.myQuillEditor2.quill.enable(false)
      setTimeout(() => {
        this.$refs.myQuillEditor1.quill.enable(true)
        this.$refs.myQuillEditor2.quill.enable(true)
      }, 2000)
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
    async jjfsData() {
      const { data } = await getAllTypeInfo()
      this.jjfsList = data
    },
    async getData() {
      // 取到路由带过来的参数
      const query = this.$route.query
      const { data } = await getById({ id: query.id })
      this.form = data.data;
      //协查人
      this.oneUser = '协查人(' + this.form.eventAssistUsers.length + ')'
      this.towUser = '关联的配置项(' + this.form.assetsManages.length + ')'
      this.threeUser = '关联的工单(' + this.form.eventList.length + ')'
      this.fourUser = '关联的问题(' + this.form.questionList.length + ')'
      this.fiveUser = '关联的变更(' + this.form.changeList.length + ')'
      this.sevenUser = '工单日志(' + this.form.sysLogs.length + ')'

      //属性信息 附件
      if (data.data.files) {
        data.data.files.forEach((x) => {
          this.fileManageInfoList.push({ name: x.file_name, url: x.id })
        })
      }
      //处理信息 附件
      if (data.data.files1) {
        data.data.files1.forEach((x) => {
          this.fileManageHandleList.push({ name: x.file_name, url: x.id })
        })
      }

      //时间信息
      //变更类型1.待响应2.处理中  3暂停 4.处理完成5.待评价6.已关闭7.已撤销
      this.stepData = []
      this.stepData.push({
        title: '开单',
        description: this.parseDateTimeFull(this.form.create_time),
      })
      this.stepData.push({
        title: '待响应',
        description: this.parseDateTimeFull(this.form.responseTime),
      })
      this.stepData.push({
        title: '处理中',
        description: this.parseDateTimeFull(this.form.processTime),
      })
      this.stepData.push({
        title: '暂停',
        description: this.parseDateTimeFull(this.form.pauseTime),
      })
      this.stepData.push({
        title: '已处理',
        description: this.parseDateTimeFull(this.form.processTime),
      })
      this.stepData.push({
        title: '待评价',
        description: this.parseDateTimeFull(this.form.processTime),
      })
      this.stepData.push({
        title: '已关闭',
        description: this.parseDateTimeFull(this.form.closeTime),
      })
      this.stepData.push({
        title: '已撤销',
        description: this.parseDateTimeFull(this.form.revokeTime),
      })
      if (this.form.status == '1') {
        this.timeStatus = 2
      } else if (this.form.status == '2') {
        this.timeStatus = 3
      } else if (this.form.status == '3') {
        this.timeStatus = 4
      } else if (this.form.status == '4') {
        this.timeStatus = 5
      } else if (this.form.status == '5') {
        this.timeStatus = 6
      } else if (this.form.status == '6') {
        this.timeStatus = 7
      } else if (this.form.status == '7') {
        this.timeStatus = 8
      }

      // 协查人初始化
      // 根据工单类别确认协查人的选择范围
      eventCategoryinfo(this.form.event_category_id).then((res) => {
        // 得到协查人的id集合
        this.eventAssistList = res.data.sysUserList2;
        console.log("得到协查人的id集合:", this.eventAssistList);
      })

      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    showView() {
      this.title = '修改'
      this.dialogFormVisible = true
    },
    handlegd() {
      this.$refs['children'].showWin()
    },
    handlePeople() {
      if(this.eventAssistList.length < 1){
        this.$baseMessage('请先在工单类别中配置协查人', 'error')
        return false
      }
      var data = {};
      // 把tablelist2的数据对象保存到一个id集合
      var ids = [];
      if (this.eventAssistList.length > 0) {
        for (var i = 0; i < this.eventAssistList.length; i++) {
          ids.push(this.eventAssistList[i].id);
        }
      }else{
        this.$baseMessage('请先选择工程师', 'error')
        return
      }
      data.ids = ids;
      // this.$refs['dialogone3'].showWin(data)
      this.$refs['XieChaRen'].showWin(data)
    },
    handleAssetsManageAdd() {
      this.$refs['assetsManagesDialog'].showWin()
    },
    handleEventsAdd() {
      this.$refs['eventDialog'].showWin()
    },
    handleQuestionAdd() {
      this.$refs['questionDialog'].showWin()
    },
    handleChangeAdd() {
      this.$refs['changeDialog'].showWin()
    },
    handlefa() {
      this.$refs['dialogone'].showWin()
    },
    // 协查人多选
    setSelectPeopleRows(val) {
      this.selectPeopleRows = val
    },
    // 协查人数据回显
    getPeopleData(rows) {
      rows.forEach((item, i) => {
        let index = this.form.eventAssistUsers.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.eventAssistUsers.push(item)
        }
      })
      this.oneUser = '协查人(' + this.form.eventAssistUsers.length + ')'
    },
    handleRemove(file, fileList) {
      this.fileManageInfoList = fileList

      // console.log(file, fileList);
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    beforeRemove(file, fileList) {
      this.fileManageInfoList = fileList
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    //属性 附件上传 选择文件
    onChange(file, fileList) {
      this.fileManageInfoList = fileList
    },

    handleRemoveHandle(file, fileList) {
      this.fileManageHandleList = fileList
      // console.log(file, fileList);
    },
    handleExceedHandle(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    beforeRemoveHandle(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    //附件上传 选择文件
    onChangeHandle(file, fileList) {
      this.fileManageHandleList = fileList
    },
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    saveok() {
      this.loading = true
      this.loadingText = '保存中...'
      const params = new FormData()
      const uploadedFiles = []
      const uploadedHandleFiles = []
      for (let i = 0; i < this.fileManageInfoList.length; i++) {
        const f = this.fileManageInfoList[i]
        if (f.status == 'ready') {
          params.append('file', f.raw)
        } else if (f.status == 'success') {
          uploadedFiles.push(f.url)
        }
      }
      for (let i = 0; i < this.fileManageHandleList.length; i++) {
        const f = this.fileManageHandleList[i]
        if (f.status == 'ready') {
          params.append('fileHandle', f.raw)
        } else if (f.status == 'success') {
          uploadedHandleFiles.push(f.url)
        }
      }
      this.form.uploadedFiles = uploadedFiles.join(',')
      this.form.uploadedHandleFiles = uploadedHandleFiles.join(',')

      params.append('body', JSON.stringify(this.form))
      doEdit(params).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('成功', 'success')
          this.closeall()
        }
      })
      this.loading = false
    },
    setSelectAssetsManageRows(val) {
      this.selectAssetsManageRows = val
    },
    setSelectEventRows(val) {
      this.selectEventRows = val
    },
    setSelecQuestionRows(val) {
      this.selectQuestionRows = val
    },
    setSelecChangeRows(val) {
      this.selectChangeRows = val
    },
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
            this.oneUser = '协查人(' + this.form.eventAssistUsers.length + ')'
            // this.$baseMessage('删除成功', 'success')
          })
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
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
          this.towUser = '关联的配置项(' + this.form.assetsManages.length + ')'
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
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
          this.threeUser = '关联的工单(' + this.form.eventList.length + ')'
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
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
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
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
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    getCategoryData(row) {
      this.form.event_category_name = row.name
      this.form.event_category_id = row.id;
      console.log("工单类别的选择结果:",row)
      // 根据工单类别确认协查人的选择范围
      eventCategoryinfo(row.id).then((res) => {
        // 得到协查人的id集合
        this.eventAssistList = res.data.sysUserList2;
        console.log("得到协查人的id集合:", this.eventAssistList);
      })
    },
    getProgrammeTypeData(row) {
      this.form.programme_type_name = row.name
      this.form.programme_type = row.id
    },
    listAssetsManages(rows) {
      rows.forEach((item, i) => {
        let index = this.form.assetsManages.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.assetsManages.push(item)
        }
      })
      this.towUser = '关联的配置项(' + this.form.assetsManages.length + ')'
    },
    listEventLists(rows) {
      rows.forEach((item, i) => {
        let index = this.form.eventList.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.eventList.push(item)
        }
      })
      this.threeUser = '关联的工单(' + this.form.eventList.length + ')'
    },
    listQuestions(rows) {
      rows.forEach((item, i) => {
        let index = this.form.questionList.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.questionList.push(item)
        }
      })
    },
    listChanges(rows) {
      rows.forEach((item, i) => {
        let index = this.form.changeList.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.changeList.push(item)
        }
      })
    },
    async getSourceData() {
      var param = {}
      const { data } = await getSourceMap(param)
      this.sourceList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getRequestTypeMap() {
      var param = {}
      const { data } = await getRequestTypeMap(param)
      this.requestTypeMapList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getEventImpactMap() {
      var param = {}
      const { data } = await getEventImpactMap({'is_use': '1'})
      this.eventImpactList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    /*--获取事件紧急度--*/
    urgencyGetAllList() {
      urgencyGetAllList({}).then((res) => {
        if (res.code == 0) {
          this.urgencyList = res.data
        }
      })
    },
    //然后定义函数parseDateFull的实现
    parseDateTimeFull(time) {
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
      return z.M + '-' + z.d + ' ' + z.h + ':' + z.m
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
      .el-upload{
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
  }

  .content-detail {
    padding-top: 7px;
    padding-left: 0;
  }
}
</style>
