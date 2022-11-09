<template>
  <div class="index-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <template>
        <el-tabs v-model="activeName">
          <el-tab-pane label="属性" name="first">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
						        <span>
						          基本信息
						        </span>
                    <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                  </div>
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="4" :xl="4">-->
                    <!--<el-form-item label="编号" prop="id">-->
                      <!--<template>-->
                        <!--{{ form.changeInfo.id }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="4" :xl="4">-->
                    <!--<el-form-item label="变更单号" prop="change_no">-->
                      <!--<template>-->
                        <!--{{ form.changeInfo.change_no }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="4" :xl="4">-->
                    <!--<el-form-item label="状态" prop="status_name">-->
                      <!--<template>-->
                        <!--{{ form.status_name }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="4" :xl="4">-->
                    <!--<el-form-item label="创建人" prop="create_user_name">-->
                      <!--<template>-->
                        <!--{{ form.create_user_name }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="4" :xl="4">-->
                    <!--<el-form-item label="创建时间" prop="create_time">-->
                      <!--<template>-->
                        <!--{{ form.changeInfo.create_time }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="标题" prop="changeInfo.title">-->
                      <!--<el-input-->
                        <!--v-model.trim="form.changeInfo.title"-->
                        <!--autocomplete="off"-->
                      <!--&gt;</el-input>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="计划开始时间" prop="changeInfo.start_time">-->
                      <!--<el-date-picker-->
                        <!--v-model="form.changeInfo.start_time"-->
                        <!--type="datetime"-->
                        <!--value-format="yyyy-MM-dd HH:mm:ss"-->
                        <!--placeholder="选择日期"-->
                        <!--class="w"-->
                      <!--&gt;</el-date-picker>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="计划结束时间" prop="changeInfo.end_time">-->
                      <!--<el-date-picker-->
                        <!--v-model="form.changeInfo.end_time"-->
                        <!--value-format="yyyy-MM-dd HH:mm:ss"-->
                        <!--type="datetime"-->
                        <!--placeholder="选择日期"-->
                        <!--class="w"-->
                      <!--&gt;</el-date-picker>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="变更类别" prop="changeInfo.change_classify">-->
                      <!--<el-select v-model="form.changeInfo.change_classify" class="w" @change="getChangeApproveUser">-->
                        <!--<el-option-->
                          <!--v-for="item in this.tableDataClassify"-->
                          <!--:key="item.id+''"-->
                          <!--:label="item.type_name"-->
                          <!--:value="item.id+''">-->
                        <!--</el-option>-->
                      <!--</el-select>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="变更类型" prop="changeInfo.change_type">-->
                      <!--<el-select v-model.trim="form.changeInfo.change_type" class="w" @change="getChangeApproveUser">-->
                        <!--<el-option-->
                          <!--v-for="item in this.getDictChangeTypes"-->
                          <!--:key="item.value"-->
                          <!--:label="item.label"-->
                          <!--:value="item.value">-->
                        <!--</el-option>-->
                      <!--</el-select>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="变更来源" prop="changeInfo.change_source">-->
                      <!--<el-select v-model.trim="form.changeInfo.change_source" class="w">-->
                        <!--<el-option-->
                          <!--v-for="item in this.changeSourceData"-->
                          <!--:key="item.id+''"-->
                          <!--:label="item.source_name"-->
                          <!--:value="item.id+''">-->
                        <!--</el-option>-->
                      <!--</el-select>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->

                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="审批人" prop="tableDataApproveUser">-->
                      <!--<template>-->
                        <!--<el-tag-->
                          <!--size="small"-->
                          <!--v-for="(item, index) in this.tableDataApproveUser"-->
                          <!--:key="index"-->
                          <!--class="mar5 mb5"-->
                        <!--&gt;{{ item.approve_name }}-->
                        <!--</el-tag-->
                        <!--&gt;-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->

                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="方案审核人" prop="programmeApproveUser">-->
                      <!--<template>-->
                        <!--{{ this.programmeApproveUser }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="实施后评审人" prop="implementApproveUser">-->
                      <!--<template>-->
                        <!--{{ this.implementApproveUser }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->

                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="变更原因:" prop="changeInfo.change_reason">-->
                      <!--<vab-quill-->
                        <!--v-model="form.changeInfo.change_reason"-->
                        <!--:min-height="200"-->
                        <!--:options="optionss"-->
                      <!--&gt;</vab-quill>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="当前环境:" prop="changeInfo.current_environment">-->
                      <!--<vab-quill-->
                        <!--v-model="form.changeInfo.current_environment"-->
                        <!--:min-height="200"-->
                        <!--:options="optionss"-->
                      <!--&gt;</vab-quill>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
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

                </el-card>
              </el-col>
              <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                <!--<el-card class="cardbox" shadow="hover">-->
                  <!--<div slot="header">-->
						        <!--<span>-->

						          <!--方案拟定-->
						        <!--</span>-->
                    <!--&lt;!&ndash; <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> &ndash;&gt;-->
                  <!--</div>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="方案拟定人" prop="changeProgrammeDraft.programme_user">-->
                      <!--<el-select v-model.trim="form.changeProgrammeDraft.programme_user" class="w">-->
                        <!--<el-option-->
                          <!--v-for="item in this.userList"-->
                          <!--:key="item.id"-->
                          <!--:label="item.name"-->
                          <!--:value="item.id">-->
                        <!--</el-option>-->
                      <!--</el-select>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="变更方案:" prop="changeProgrammeDraft.change_programme">-->
                      <!--<vab-quill-->
                        <!--v-model="form.changeProgrammeDraft.change_programme"-->
                        <!--:min-height="200"-->
                        <!--:options="optionss"-->
                      <!--&gt;</vab-quill>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="回退计划:" prop="changeProgrammeDraft.fallback_plan">-->
                      <!--<vab-quill-->
                        <!--v-model="form.changeProgrammeDraft.fallback_plan"-->
                        <!--:min-height="200"-->
                        <!--:options="optionss"-->
                      <!--&gt;</vab-quill>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="风险评估:" prop="changeProgrammeDraft.risk_assessment">-->
                      <!--<vab-quill-->
                        <!--v-model="form.changeProgrammeDraft.risk_assessment"-->
                        <!--:min-height="200"-->
                        <!--:options="optionss"-->
                      <!--&gt;</vab-quill>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->


                <!--</el-card>-->
              <!--</el-col>-->
              <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                <!--<el-card class="cardbox" shadow="hover">-->
                  <!--<div slot="header">-->
						        <!--<span>-->

						          <!--方案评审-->
						        <!--</span>-->
                    <!--&lt;!&ndash; <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> &ndash;&gt;-->
                  <!--</div>-->
                  <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
                    <!--<el-form-item label="方案审核人">-->
                      <!--<template>-->
                        <!--{{ form.cpa_approve_name }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
                    <!--<el-form-item label="方案审核时间">-->
                      <!--<template>-->
                        <!--{{ form.changeProgrammeApprove.approve_time }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                    <!--<el-form-item label="方案审核意见">-->
                      <!--<div class="text-p" v-html="form.changeProgrammeApprove.approve_remark "></div>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->
                <!--</el-card>-->
              <!--</el-col>-->

              <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                <!--<el-card class="cardbox" shadow="hover">-->
                  <!--<div slot="header">-->
						        <!--<span>-->

						          <!--实施信息-->
						        <!--</span>-->
                    <!--&lt;!&ndash; <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> &ndash;&gt;-->
                  <!--</div>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                    <!--<el-form-item label="方案实施人" prop="changeImplement.implement_user">-->
                      <!--<el-select v-model.trim="form.changeImplement.implement_user" class="w">-->
                        <!--<el-option-->
                          <!--v-for="item in this.userList"-->
                          <!--:key="item.id"-->
                          <!--:label="item.name"-->
                          <!--:value="item.id">-->
                        <!--</el-option>-->
                      <!--</el-select>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
                    <!--<el-form-item label="实施时间">-->
                      <!--<template>-->
                        <!--{{ form.changeImplement.start_time }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->

                <!--</el-card>-->
              <!--</el-col>-->

              <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                <!--<el-card class="cardbox" shadow="hover">-->
                  <!--<div slot="header">-->
						        <!--<span>-->

						          <!--实施后评审-->
						        <!--</span>-->
                    <!--&lt;!&ndash; <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> &ndash;&gt;-->
                  <!--</div>-->
                  <!--<el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                    <!--<el-form-item label="实施后评审人" prop="implementApproveUser">-->
                      <!--<template>-->
                        <!--{{ this.implementApproveUser }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                    <!--<el-form-item label="实施后评审时间">-->
                      <!--<template>-->
                        <!--{{ form.changeImplementApprove.after_implement_time }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                    <!--<el-form-item label="实施后评审意见">-->
                      <!--<template>-->
                        <!--{{ form.changeImplementApprove.after_implement_remark }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                    <!--<el-form-item label="关单人">-->
                      <!--<template>-->
                        <!--{{ form.close_user_name }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                    <!--<el-form-item label="关单时间">-->
                      <!--<template>-->
                        <!--{{ form.changeClose == null ? "" : form.changeClose.close_time }}-->
                      <!--</template>-->
                    <!--</el-form-item>-->

                  <!--</el-col>-->
                <!--</el-card>-->
              <!--</el-col>-->
            </el-row>
          </el-tab-pane>
          <!--<el-tab-pane :label="eventsName" name="second">-->
            <!--<el-table-->
              <!--v-loading="listLoading"-->
              <!--:data="tableDataEvent"-->
              <!--stripe-->
              <!--border-->
              <!--highlight-current-row-->
              <!--:element-loading-text="elementLoadingText"-->
              <!--@selection-change="setSelectRowsEvent">-->
              <!--<el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip type="index" label="序号" align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">-->
                <!--&lt;!&ndash;<template #default="{ row }">&ndash;&gt;-->
                <!--&lt;!&ndash;<el-link @click="handlesee(row)">{{row.title}}</el-link>&ndash;&gt;-->
                <!--&lt;!&ndash;</template>&ndash;&gt;-->
              <!--</el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="user_id_name" sortable label="用户"-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"-->
                               <!--sortable></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center"-->
                               <!--sortable></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center"-->
                               <!--sortable></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center"-->
                               <!--sortable></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间"-->
                               <!--sortable></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态"-->
                               <!--sortable></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度"-->
                               <!--sortable></el-table-column>-->
            <!--</el-table>-->
            <!--<div style="margin-top: 10px">-->
              <!--<el-button-->
                <!--type="danger"-->
                <!--icon="el-icon-delete"-->
                <!--@click="handleDeleteEvent"-->
              <!--&gt;-->
                <!--移除选择对象-->
              <!--</el-button>-->
              <!--<el-button-->
                <!--type="primary"-->
                <!--icon="el-icon-plus"-->
                <!--@click="handleAddEvent"-->
              <!--&gt;-->
                <!--添加关联的工单-->
              <!--</el-button>-->
            <!--</div>-->
          <!--</el-tab-pane>-->
          <!--<el-tab-pane :label="questionsName" name="three">-->
            <!--<el-table ref="listTable" @selection-change="setSelectRowsQuest" border stripe :data="tableDataQuest"-->
                      <!--:element-loading-text="elementLoadingText" highlight-current-row>-->
              <!--<el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="id"-->
                <!--label="编号"-->
                <!--align="center"-->
              <!--&gt;</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--sortable-->
                <!--prop="code"-->
                <!--label="问题单号"-->
                <!--align="center"-->
              <!--&gt;-->
              <!--</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="title"-->
                <!--sortable-->
                <!--label="标题"-->
                <!--align="center"-->
              <!--&gt;-->
                <!--&lt;!&ndash;<template #default="{ row }">&ndash;&gt;-->
                <!--&lt;!&ndash;<el-link @click="handleView(row)">{{row.title}}</el-link>&ndash;&gt;-->
                <!--&lt;!&ndash;</template>&ndash;&gt;-->
              <!--</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="create_user_name"-->
                <!--label="创建人"-->
                <!--align="center"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="create_time"-->
                <!--label="创建时间"-->
                <!--align="center"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="category_name"-->
                <!--label="问题类别"-->
                <!--align="center"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="hope_solve_time"-->
                <!--label="期望解决时间"-->
                <!--align="center"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="priority_name"-->
                <!--label="优先级"-->
                <!--align="center"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->
              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="status_name"-->
                <!--align="center"-->
                <!--label="状态"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->

              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="now_operator_user_name"-->
                <!--align="center"-->
                <!--label="当前操作人"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->

              <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="solve_time"-->
                <!--align="center"-->
                <!--label="解决时间"-->
                <!--sortable-->
              <!--&gt;</el-table-column>-->
            <!--</el-table>-->
            <!--<div class="btn-box" style="margin-top: 10px;">-->
              <!--<el-button type="danger" icon="el-icon-delete" @click="handleDeleteQuest">-->
                <!--移除选择对象-->
              <!--</el-button>-->
              <!--<el-button type="primary" icon="el-icon-plus" @click="handleAddQuest">-->
                <!--添加关联的问题-->
              <!--</el-button>-->
            <!--</div>-->
          <!--</el-tab-pane>-->
          <!--<el-tab-pane :label="assetsName" name="four">-->
            <!--<el-table ref="listTable" border stripe :data="tableDataSet"-->
                      <!--:element-loading-text="elementLoadingText" highlight-current-row-->
                      <!--@selection-change="setSelectRowsSet">-->
              <!--<el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" sortable-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" sortable-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" sortable-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="deptName" label="部门" sortable-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="receiverName" label="领用人" sortable-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" sortable-->
                               <!--align="center"></el-table-column>-->
              <!--<el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" sortable-->
                               <!--align="center">-->
                <!--<template #default="scope">-->
                  <!--<el-tag-->
                    <!--size="mini"-->
                    <!--icon="el-icon-edit"-->
                    <!--v-if="scope.row.is_secrecy == '0'"-->
                    <!--type="primary"-->
                  <!--&gt;-->
                    <!--否-->
                  <!--</el-tag>-->
                  <!--<el-tag-->
                    <!--size="mini"-->
                    <!--icon="el-icon-delete"-->
                    <!--class="red"-->
                    <!--v-if="scope.row.is_secrecy == '1'"-->
                    <!--type="success"-->
                  <!--&gt;-->
                    <!--是-->
                  <!--</el-tag>-->
                <!--</template>-->
              <!--</el-table-column>-->

              <!--<el-table-column show-overflow-tooltip prop="assetsStatusName" label="资产状态" sortable-->
                               <!--align="center">-->
                <!--<template #default="scope">-->
                  <!--<el-tag size="mini" icon="el-icon-delete" class="red" type="success">-->
                    <!--{{ scope.row.assetsStatusName }}-->
                  <!--</el-tag>-->
                <!--</template>-->
              <!--</el-table-column>-->

              <!--<el-table-column show-overflow-tooltip prop="itStatusName" label="IT状态" sortable-->
                               <!--align="center"></el-table-column>-->
            <!--</el-table>-->
            <!--<div class="btn-box" style="margin-top: 10px;">-->
              <!--<el-button type="danger" icon="el-icon-delete" @click="handleDeleteSet">-->
                <!--移除选择对象-->
              <!--</el-button>-->
              <!--<el-button type="primary" icon="el-icon-plus" @click="handleAddDeploy">-->
                <!--添加关联的配置-->
              <!--</el-button>-->
            <!--</div>-->
          <!--</el-tab-pane>-->
          <!--<el-tab-pane :label="logsName" name="fifth">-->
            <!--<div class="box_card">-->
              <!--<div class="box_content">-->
                <!--<el-timeline>-->
                  <!--<el-timeline-item-->
                    <!--v-for="(log, index) in form.sysLogs"-->
                    <!--:key="index"-->
                    <!--:timestamp="log.operate_time"-->
                    <!--placement="top"-->
                    <!--size="large"-->
                    <!--color="#0bbd87"-->
                  <!--&gt;-->
                    <!--<el-card>-->
                      <!--<h4>{{ log.title }}</h4>-->
                      <!--<p>{{ log.user_name }}</p>-->
                      <!--<div class="ql-editor content-detail" v-html="log.content"/>-->
                    <!--</el-card>-->
                  <!--</el-timeline-item>-->
                <!--</el-timeline>-->
              <!--</div>-->
            <!--</div>-->
          <!--</el-tab-pane>-->
        </el-tabs>
      </template>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
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
    toList,
    toEdit,
    getAllChangeType,
    getDict,
    getChangeSource,
    getUserList,
    getChangeApproveUser,
    getChange
  } from '@/api/change'
  import vabQuill from '@/plugins/vabQuill'
  // 引用事件弹窗页面
  import Dialogevent from '@/components/eventDialog'
  // 引用问题弹窗页面
  import Dialogquest from '@/components/questDialog'
  // 引用关联的配置弹窗页面
  import DialogSet from '@/components/assetsTypeDia'

  export default {
    name: 'editmechangeFile',
    components: {vabQuill, Dialogevent, Dialogquest, DialogSet},
    data() {
      return {
        activeName: 'first',
        eventsName: '',
        questionsName: '',
        assetsName: '',
        logsName: '',
        optionss: {
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
        bumen: '',
        input2: '',
        changeApproveUser: {id: ''},
        tableDataClassify: [],

        tableDataEvent: [],
        selectRowsEvent: '',

        tableDataSet: [],
        selectRowsSet: '',

        tableDataQuest: [],
        selectRowsQuest: '',

        tableDataApproveUser: [],

        programmeApproveUser: "",// 方案审核人
        implementApproveUser: "",// 实施评审人

        changeSourceData: [],
        userList: [],
        getDictChangeTypes: [],
        formArr: [{value1: ''}],
        formArr1: [{value1: ''}],
        tablelist: null,

        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        checked: false,
        checked1: false,
        options: [],
        value: [],
        list: [],
        loading: false,
        states: [
          'Alabama',
          'Alaska',
          'Arizona',
          'Arkansas',
          'California',
          'Colorado',
          'Connecticut',
          'Delaware',
          'Florida',
          'Georgia',
          'Hawaii',
          'Idaho',
          'Illinois',
          'Indiana',
          'Iowa',
          'Kansas',
          'Kentucky',
          'Louisiana',
          'Maine',
          'Maryland',
          'Massachusetts',
          'Michigan',
          'Minnesota',
          'Mississippi',
          'Missouri',
          'Montana',
          'Nebraska',
          'Nevada',
          'New Hampshire',
          'New Jersey',
          'New Mexico',
          'New York',
          'North Carolina',
          'North Dakota',
          'Ohio',
          'Oklahoma',
          'Oregon',
          'Pennsylvania',
          'Rhode Island',
          'South Carolina',
          'South Dakota',
          'Tennessee',
          'Texas',
          'Utah',
          'Vermont',
          'Virginia',
          'Washington',
          'West Virginia',
          'Wisconsin',
          'Wyoming',
        ],
        dict: {dict_type: 'change_type'},
        form: {
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
          uploadedFiles: '',
          fileManageInfoList: [],
        },
        multipleSelection: [],
        changeApproveUserData: {},
        fileManageInfoList: [],
        rules: {
          'changeInfo.title': [
            {required: true, trigger: 'blur', message: '请输入标题'},
          ],
          'changeInfo.start_time': [{required: true, trigger: 'blur', message: '请输入计划开始时间'}],
          'changeInfo.end_time': [{required: true, trigger: 'blur', message: '请输入计划结束时间'}],
          'changeInfo.change_classify': [{required: true, trigger: 'blur', message: '请选择变更类别'}],
          'changeInfo.change_type': [{required: true, trigger: 'blur', message: '请选择变更来源'}],
          'changeInfo.change_source': [
            {required: true, trigger: 'blur', message: '请选择变更来源'},
          ],
          'changeInfo.change_reason': [
            {required: true, trigger: 'blur', message: '请输入变更原因'},
          ],
          'changeInfo.current_environment': [
            {required: true, trigger: 'blur', message: '请输入当前环境'},
          ],
          'changeProgrammeDraft.programme_user': [
            {required: true, trigger: 'blur', message: '请选择方案拟定人'},
          ],
          'changeProgrammeDraft.change_programme': [
            {required: true, trigger: 'blur', message: '请输入变更方案'},
          ],
          'changeProgrammeDraft.fallback_plan': [
            {required: true, trigger: 'blur', message: '请输入回退计划'},
          ],
          'changeProgrammeDraft.risk_assessment': [
            {required: true, trigger: 'blur', message: '请输入风险评估'},
          ],
          'changeImplement.implement_user': [
            {required: true, trigger: 'blur', message: '请选择实施人'},
          ],
        },
        queryForm: {id: this.$route.query.id},
      }
    },
    created() {

      // 变更类别
      this.allChangeType()
      // 变更类型
      this.getDictChangeType();
      // 变更来源
      this.getChangeSource();
      // 方案人
      this.getUserList();
      this.fetchData();
    },
    beforeDestroy() {
    },
    mounted() {
      this.list = this.states.map((item) => {
        return {value: `${item}`, label: `${item}`}
      })
    },
    methods: {
      handleAddEvent() {
        this.$refs['dialogevent'].showWin()
      },
      handleAddQuest() {
        this.$refs['dialogquest'].showWin()
      },
      handleAddDeploy() {
        this.$refs['dialogSet'].showWin()
      },
      handleRemove(file, fileList) {
        this.fileManageInfoList = fileList;

        console.log(file, fileList);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        this.fileManageInfoList = fileList;
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      //属性 附件上传 选择文件
      onChange(file, fileList) {
        this.fileManageInfoList = fileList;
      },

      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index) {
        var that = this
        if (that.formArr.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr.splice(index, 1)
        }
      },
      addItemo() {
        var that = this
        that.formArr1.push({
          value1: '',
        })
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
      delItemo(index) {
        var that = this
        if (that.formArr1.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr1.splice(index, 1)
        }
      },
      handleCurrentChange(val) {
        this.form.groupList = val
      },
      // 删除配置
      handleDeleteSet() {
        console.log(this.selectRowsSet);
        if (this.selectRowsSet.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsSet.forEach((item, i) => {
              this.tableDataSet.some((x, i) => {
                if (x.id == item.id) {
                  this.tableDataSet.splice(i, 1)
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
      // 删除问题
      handleDeleteQuest() {

        if (this.selectRowsQuest.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsQuest.forEach((item, i) => {
              this.tableDataQuest.some((x, i) => {
                if (x.id == item.id) {
                  this.tableDataQuest.splice(i, 1)
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
      // 删除事件
      handleDeleteEvent() {
        console.log(this.selectRowsEvent);
        if (this.selectRowsEvent.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsEvent.forEach((item, i) => {
              this.tableDataEvent.some((x, i) => {
                if (x.id == item.id) {
                  this.tableDataEvent.splice(i, 1)
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
      handleDelete() {
        if (this.selectRows != '') {
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
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.questionsName = "关联的问题(" + data.questionVos.length + ")";
        this.assetsName = "关联的配置(" + data.assetsManages.length + ")";
        this.logsName = "日志(" + data.sysLogs.length + ")";
        this.tableDataQuest = data.questionVos;
        console.log(this.tableDataQuest)
        this.tableDataEvent = data.events;
        this.tableDataSet = data.assetsManages;


        console.log(this.form);
        // this.form.changeInfo=data.changeInfo;
        // this.form.changeProgrammeDraft=data.changeProgrammeDraft;
        // this.form.changeNodes=data.changeNodes;
        // this.form.changeProgrammeApprove=data.changeProgrammeApprove;
        // this.form.changeImplement=data.changeImplement;
        // this.form.changeImplementApprove=data.changeImplementApprove;


        this.getChangeApproveUser();

        console.log(data)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },

      remoteMethod(query) {
        if (query !== '') {
          this.loading = true
          setTimeout(() => {
            this.loading = false
            this.options = this.list.filter((item) => {
              return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
            })
          }, 200)
        } else {
          this.options = []
        }
      },
      deptset(data) {

        this.form.changeInfo.change_classify = data.change_classify;
        this.form.classify_name = data.classify_name;
      },
      delGroup(data) {
        this.tablelist = data;
        console.log(this.tablelist)
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route);
        // 返回上一步路由
        this.$router.go(-1);
      },
      saveok() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            // 关联的事件
            this.form.changeEvents = [];
            this.tableDataEvent.some((x, i) => {
              this.form.changeEvents.push({event_id: x.id, is_del: '0'});
            });

            // 关联的配置
            this.form.changeAssets = [];
            this.tableDataSet.some((x, i) => {
              this.form.changeAssets.push({assets_id: x.id, is_del: '0'});
            });

            //关联的问题
            this.form.changeQuestions = [];
            this.tableDataQuest.some((x, i) => {
              this.form.changeQuestions.push({question_id: x.id, is_del: '0'});
            });
            // 重构节点 先默认是本人修改


            this.form.changeNodes = [];
            var sorts = 1;
            var n = 7;
            if (this.form.changeInfo.change_type == '1') {
              n = 5;
            }
            // 构建节点 审批流程按节点大小排序，同节点按sort排序
            for (var i = 1; i <= n; i++) {

              if (i == 1) {
                this.form.changeNodes.push({time_nodes: i, sort: sorts, time_status: '0', is_del: '0'});
              } else if (i == 2) {
                for (var j = 0; j < this.tableDataApproveUser.length; j++) {
                  if (j == 0) {// 当前审批人赋值
                    this.form.changeInfo.approve_user = this.tableDataApproveUser[j].approve_user;
                    // 下一位处理人
                    this.form.changeInfo.handler_user = this.tableDataApproveUser[j].approve_user;
                  }

                  this.form.changeNodes.push({
                    time_nodes: i,
                    sort: sorts,
                    time_status: "1",
                    approve_user: this.tableDataApproveUser[j].approve_user,
                    is_del: "0"
                  });
                  sorts++;
                }
              } else {
                this.form.changeNodes.push({time_nodes: i, sort: sorts, time_status: "1", is_del: "0"});
              }
              sorts++;
            }
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
            console.log("-------------------------------------")
            console.log(uploadedFiles)
            this.form.uploadedFiles = uploadedFiles.join(',');
            params.append('body', JSON.stringify(this.form));


            toEdit(params).then((res) => {
              if (res.code == 0) {
                this.$baseMessage('修改成功', 'success')
                this.closeall();
                // this.$store.dispatch('tabsBar/delRoute', this.$route)
                // this.$router.push({
                //   path: '/biangeng/pay',
                // })
              }
            })
          } else {
            return false
          }
        })
      },
      //事件
      listEventsManages(row) {
        // debugger
        row.forEach((item, i) => {
          let index = this.tableDataEvent.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.tableDataEvent.push(item)
          }
        })
      },
      // 问题
      listQuestManages(row) {
        row.forEach((item, i) => {
          let index = this.tableDataQuest.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.tableDataQuest.push(item)
          }
        })
      },
      // 问题
      // 配置
      listAssetsManages(row) {
        row.forEach((item, i) => {
          let index = this.tableDataSet.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.tableDataSet.push(item)
          }
        })
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
      async getChangeSource() {
        const {data} = await getChangeSource()

        this.changeSourceData = data

        console.log(this.changeSourceData)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async allChangeType() {

        const {data} = await getAllChangeType()
        this.tableDataClassify = data
        console.log(this.tableDataClassify)
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
      async getChangeApproveUser() {
        console.log(this.form.changeInfo.change_classify);
        console.log(this.form.changeInfo.change_type);
        if (this.form.changeInfo.change_classify && this.form.changeInfo.change_type) {
          this.changeApproveUser.id = this.form.changeInfo.change_classify;
          const {data} = await getChangeApproveUser(this.changeApproveUser)

          console.log(data);
          this.changeApproveUserData = data;

          if (this.form.changeInfo.change_type == 1) {//紧急
            this.tableDataApproveUser = data.changeUrgentApproveList;
            this.programmeApproveUser = "";
            this.implementApproveUser = data.changeType.urgent_implement_user_name;
            // 方案审核人
            this.form.changeProgrammeApprove.approve_user = '';
            // 实施后评审人
            this.form.changeImplementApprove.after_implement_user = data.changeType.urgent_implement_user;
          } else if (this.form.changeInfo.change_type == 2) {// 一般
            this.tableDataApproveUser = data.changeCommonlyApproveList;
            this.programmeApproveUser = data.changeType.commonly_audit_user_name;
            this.implementApproveUser = data.changeType.commonly_implement_user_name;
            // 方案审核人
            this.form.changeProgrammeApprove.approve_user = data.changeType.commonly_audit_user;
            // 实施后评审人
            this.form.changeImplementApprove.after_implement_user = data.changeType.commonly_implement_user;
          } else if (this.form.changeInfo.change_type == 3) {// 重大
            this.tableDataApproveUser = data.changeMajorApproveList;
            this.programmeApproveUser = data.changeType.major_audit_user_name;
            this.implementApproveUser = data.changeType.major_implement_user_name;
            // 方案审核人
            this.form.changeProgrammeApprove.approve_user = data.changeType.urgent_implement_user;
            // 实施后评审人
            this.form.changeImplementApprove.after_implement_user = data.changeType.major_implement_user;
          }
          this.tableDataChangeType = data.changeType;
          console.log(this.tableDataApproveUser);
        }


      },

      setSelectRows(val) {
        this.form.roleList = val
      }

    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .tsitemd {
      label {
        width: 260 !important;
        text-align: left;
      }

      .el-form-item__content {
        margin-left: 0 !important;
      }
    }

    .el-upload {
      text-align: left;
      /*margin-top: 20px;*/
    }
  }
</style>
