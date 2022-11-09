<template>
  <div class="main-container pb80">
    <el-form ref="form" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <div class="box_card">
                  <div class="box_header">工单信息</div>
                  <div class="box_content">
                    <el-form-item label="标题:" class="tsitem">
                      {{ form.title }}
                    </el-form-item>
                    <el-form-item label="描述:" class="tsitem">
                      <!--                      <el-image :src="url"></el-image>-->
                      <div
                        class="ql-editor content-detail"
                        v-html="form.description"
                      ></div>
                    </el-form-item>
                    <el-form-item label="附件:" class="tsitem">
                      <div
                        class="bghui"
                        v-for="item in this.fileManageHandleList"
                        :key="item.id"
                      >
                        <div class="r">
                          <p
                            class="p1"
                            style="margin: 0; color: #333"
                            @click="downloadPic(item.id, item.file_name)"
                          >
                            {{ item.file_name }}
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
                        <el-form-item label="开单人:" class="tsitem">
                          {{ form.create_user_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="开单时间:" class="tsitem">
                          {{ form.create_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="指派人:" class="tsitem">
                          {{ form.assigner_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="指派时间:" class="tsitem">
                          {{ form.assigner_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应人:" class="tsitem">
                          {{ form.handler_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应时间:" class="tsitem">
                          {{ form.update_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决工程师:" class="tsitem">
                          {{ form.solve_user_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决时间:" class="tsitem">
                          {{ form.ex_solve_time }}
                        </el-form-item>
                      </el-col>
                      <!--                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                      <!--                        <el-form-item label="关单人:" class="tsitem">-->
                      <!--                          {{ form.close_user_name }}-->
                      <!--                        </el-form-item>-->
                      <!--                      </el-col>-->
                      <!--                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                      <!--                        <el-form-item label="关单时间:" class="tsitem">-->
                      <!--                          {{ form.closeTime }}-->
                      <!--                        </el-form-item>-->
                      <!--                      </el-col>-->
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="手动暂停总时间:" class="tsitem">
                          {{ form.pauseTime }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="暂停总时间:" class="tsitem">
                          {{ form.pauseTime }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="上次暂停时间:" class="tsitem">
                          {{ form.pauseTime }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="上次更新时间:" class="tsitem">
                          {{ form.update_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="预计解决时间:" class="tsitem">
                          {{ form.ex_solve_time }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决方式:" class="tsitem">
                          {{ form.solve_type }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="方案分类:" class="tsitem">
                          {{ form.programme_type_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="费用:" class="tsitem">
                          {{ form.expenses }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="解决方案:" class="tsitem">
                          <div
                            class="ql-editor content-detail"
                            v-html="form.solution"
                          ></div>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="附件:" class="tsitem">
                          <div
                            class="bghui"
                            v-for="item in this.JJfileManageHandleList"
                            :key="item.id"
                          >
                            <div class="r">
                              <p
                                class="p1"
                                style="margin: 0; color: #333"
                                @click="downloadPic(item.id, item.file_name)"
                              >
                                {{ item.file_name }}
                              </p>
                            </div>
                          </div>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card" v-if="null != form.eventTimes.id">
                  <div class="box_header">SLA</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应SLA:" class="tsitem">
                          {{ form.eventTimes.response_times }}
                        </el-form-item>
                      </el-col>
                      <!-- <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="符合响应SLA:" class="tsitem">
                          {{ form.eventTimes.response_times }}
                        </el-form-item>
                      </el-col> -->
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应时限:" class="tsitem">
                          {{ form.eventTimes.response_exceed_times }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="处理总时间:" class="tsitem">
                          {{ form.eventTimes.handle_times }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="处理超时时间:" class="tsitem">
                          {{ form.eventTimes.handle_exceed_times }}
                        </el-form-item>
                      </el-col>
                      <!-- <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决SLA:" class="tsitem">
                           {{ form.times.handle_exceed_times }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="符合解决SLA:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col> -->
                      <!-- <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决时限:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决总时间:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决超时时间数:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col> -->
                    </el-row>
                  </div>
                </div>
                <!-- <div class="box_card">
                  <div class="box_header">处理时长</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                        <el-alert
                          title="服务群组"
                          type="info"
                          :closable="false"
                        >
                          <el-form-item
                            label="服务台"
                            class="tsitem"
                            style="margin-bottom: 0"
                          >
                            0.18小时
                          </el-form-item>
                        </el-alert>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                        <el-alert title="服务台" type="info" :closable="false">
                          <el-form-item
                            label="迟鹏"
                            class="tsitem"
                            style="margin-bottom: 0"
                          >
                            0.18小时
                          </el-form-item>
                        </el-alert>
                      </el-col>
                    </el-row>
                  </div>
                </div> -->
                <div class="box_card" v-if="null != form.event_satisfaction">
                  <div class="box_header">满意度</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="用户满意度:" class="tsitem">
                          <el-rate
                            v-model="form.event_satisfaction"
                            disabled
                            show-score
                            text-color="#ff9900"
                            score-template="{value}"
                          ></el-rate>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </el-tab-pane>

              <el-tab-pane :label="oneUser" name="third">
                <el-table :data="form.eventAssistUsers" stripe border>
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="name"
                    label="姓名"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="user_name"
                    label="用户名"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="telephone"
                    label="电话"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane :label="towUser" name="fourth">
                <el-table
                  v-loading="listLoading"
                  :data="form.assetsManages"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                  height="400"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_number"
                    label="资产编号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assetsTypeName"
                    label="配置项类型"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="childAssetName"
                    label="配置子类"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_name"
                    label="资产名称"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="deptName"
                    label="部门"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="receiver"
                    label="领用人"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="purchaseCompanyName"
                    label="购置公司"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="is_secrecy"
                    label="保密"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_status"
                    label="资产状态"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="it_status"
                    label="IT状态"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane :label="threeUser" name="five">
                <el-table
                  v-loading="listLoading"
                  :data="form.eventList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="title"
                    sortable
                    label="标题"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="user_id_name"
                    sortable
                    label="用户"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="category_name"
                    label="工单类别"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_user_name"
                    label="开单人"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_time"
                    label="开单时间"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="service_name"
                    label="服务群组"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="handler_name"
                    label="处理人"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="ex_solve_time"
                    align="center"
                    label="解决时间"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="status"
                    align="center"
                    label="状态"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="event_satisfaction"
                    align="center"
                    label="用户满意度"
                  >
                    <template #default="{ row }">
                      <el-rate
                        v-model="row.event_satisfaction"
                        disabled
                      ></el-rate>
                    </template>
                  </el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane :label="fourUser" name="six">
                <el-table
                  v-loading="listLoading"
                  :data="form.questionList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="code"
                    label="问题单号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="title"
                    label="标题"
                    width="280"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="create_user"
                    label="创建人"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="create_time"
                    label="创建时间"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="status"
                    label="状态"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane :label="fiveUser" name="seven">
                <el-table
                  v-loading="listLoading"
                  :data="form.changeList"
                  border
                  highlight-current-row
                  :element-loading-text="elementLoadingText"
                >
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="change_no"
                    label="变更单号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="title"
                    label="标题"
                    width="280"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="start_time"
                    label="计划开始时间"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="end_time"
                    label="计划结束时间"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="change_type"
                    label="变更类别"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="create_user"
                    label="创建人"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="status"
                    label="状态"
                    align="center"
                  ></el-table-column>
                </el-table>
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
                  <el-table-column
                    show-overflow-tooltip
                    type="index"
                    label="序号"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="create_user_name"
                    label="姓名"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="title"
                    label="标题"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="statusName"
                    label="状态"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="category_name"
                    label="类型"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="phone"
                    label="电话"
                    align="center"
                  ></el-table-column>
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
              <el-tab-pane :label="sevenUser" name="fifth">
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
                  {{ form.user_id }}
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
                  {{ form.user_id }}
                </el-form-item>
                <el-form-item label="姓名:" class="mb0">
                  {{ form.user_id_name }}
                </el-form-item>
                <el-form-item label="公司:" class="mb0">
                  {{ form.service_name }}
                </el-form-item>
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
                <!-- <el-steps :active="1" align-center>
                  <el-step title="开单" description="03-15 10:29"></el-step>
                  <el-step title="响应" description="03-15 10:39"></el-step>
                  <el-step title="解决" description="03-15 10:40"></el-step>
                  <el-step title="关单" description="03-15 10:40"></el-step>
                </el-steps> -->
                <el-steps
                  :active="timeStatus"
                  :style="{ width: this.stepData.length * 80 + 'px' }"
                >
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
        <el-button type="primary" size="medium" @click="handleSolve">
          已解决
        </el-button>
        <el-button type="danger" size="medium" @click="handleSolveNo">
          未解决
        </el-button>
      </div>
    </el-form>
    <grade ref="grade" @fetch-data="fetchData"></grade>
    <solvenot ref="solvenot" @fetch-data="fetchData"></solvenot>
    <pause ref="pause" @fetch-data="fetchData"></pause>
    <remake ref="remake" @fetch-data="fetchData"></remake>
    <sendemail ref="sendemail" @fetch-data="fetchData"></sendemail>
    <cancle ref="cancle" @fetch-data="fetchData"></cancle>
    <prints ref="prints" @fetch-data="fetchData"></prints>
  </div>
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
import {
  getById,
  getEventImpactMap,
  getRequestTypeMap,
  getSourceMap,
  userHistory,
} from '@/api/eventAllManage'
import Pause from '@/components/pause'
import Remake from '@/components/eventRemake'
import Sendemail from '@/components/sendemail'
import Cancle from '@/components/eventCancle'
import Prints from '@/components/prints'
import { download } from '@/api/file'
import Grade from './components/grade'
import Solvenot from './components/solvenot'
export default {
  name: 'thingEvaluate',
  components: {
    vabQuill,
    Remake,
    Pause,
    Sendemail,
    Cancle,
    Prints,
    Grade,
    Solvenot,
  },
  data() {
    return {
      oneUser: '',
      towUser: '',
      threeUser: '',
      fourUser: '',
      fiveUser: '',
      sixUser: '',
      stepData: [],
      timeStatus: 0,
      sevenUser: '',
      fileManageHandleList: [],
      JJfileManageHandleList: [],
      listLoading: true,
      sourceList: [],
      requestTypeMapList: [],
      eventImpactList: [],
      urgencyDegreeList: [],
      elementLoadingText: '正在加载...',
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄',
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄',
        },
      ],
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
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      queryUserForm: {
        pageIndex: 1,
        pageSize: 20,
        user_id: '',
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
        title: [{ required: true, trigger: 'blur', message: '请输入' }],
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
      activeName: 'first',
      title: '',
      value: 3.7,
      dialogFormVisible: false,
      username:
        'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
    }
  },
  created() {
    this.getData()
    this.getSourceData()
    this.getRequestTypeMap()
    this.getEventImpactMap()
    this.getUrgencyDegreeMap()
  },
  methods: {
    handleSolve() {
      const query = this.$route.query
      this.$refs['grade'].showWin(query.id)
    },
    handleSolveNo() {
      const query = this.$route.query
      this.$refs['solvenot'].showEdit(query.id)
    },
    handleSizeChange(val) {
      this.queryUserForm.pageSize = val
      this.userHistoryListClick()
    },
    handleCurrentChange(val) {
      this.queryUserForm.pageIndex = val
      this.userHistoryListClick()
    },
    handleCurrentChange2(val) {
      this.queryUserForm.pageIndex = val
      this.userHistoryListClick()
    },
    //用户历史请求
    userHistoryListClick() {
      if (
        this.form.user_id != null &&
        this.form.user_id != '' &&
        this.form.user_id != undefined
      ) {
        this.queryUserForm.user_id = this.form.user_id
        userHistory(this.queryUserForm).then((res) => {
          if (res.code == 0) {
            this.tableData = res.data.list
            this.total = res.data.total
            setTimeout(() => {
              this.listLoading = false
            }, 300)
            this.sixUser = '用户历史请求(' + this.total + ')'
          }
        })
      }
    },
    handlePreview(file) {},
    fetchData() {
      this.getData()
    },
    // --------------------------------------------------
    async getData() {
      // 取到路由带过来的参数
      const query = this.$route.query
      const { data } = await getById({ id: query.id })
      this.form = data.data
      //协查人
      this.oneUser = '协查人(' + this.form.eventAssistUsers.length + ')'
      this.towUser = '关联的配置项(' + this.form.assetsManages.length + ')'
      this.threeUser = '关联的工单(' + this.form.eventList.length + ')'
      this.fourUser = '关联的问题(' + this.form.questionList.length + ')'
      this.fiveUser = '关联的变更(' + this.form.changeList.length + ')'
      this.sevenUser = '工单日志(' + this.form.sysLogs.length + ')'
      //处理信息 附件
      if (this.form.files) {
        this.form.files.forEach((x) => {
          this.fileManageHandleList.push({ file_name: x.file_name, id: x.id })
        })
      }
      //处理信息 附件
      if (this.form.files1) {
        this.form.files1.forEach((x) => {
          this.JJfileManageHandleList.push({ file_name: x.file_name, id: x.id })
        })
      }
      this.userHistoryListClick(this.form.user_id)
      setTimeout(() => {
        this.listLoading = false
      }, 300)
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
    },
    downloadPic(id, name) {
      console.log(id)
      console.log(name)
      download(id).then((res) => {
        const url = window.URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', name)
        document.body.appendChild(link)
        link.click()
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
      const { data } = await getEventImpactMap(param)
      this.eventImpactList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getUrgencyDegreeMap() {
      var param = {}
      const { data } = await getEventImpactMap(param)
      this.urgencyDegreeList = data
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
    .el-divider--horizontal {
      margin-bottom: 0 !important;
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
  .content-detail {
    padding-top: 7px;
    padding-left: 0;
  }
}
</style>
