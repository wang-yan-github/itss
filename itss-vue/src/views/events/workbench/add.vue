<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <div style="position: absolute; right: 0px; top: 0; z-index: 2">
        <el-form-item>
          <el-select filterable v-model="form.template_id" @change="mouldListClick" placeholder="工单模板" class="w">
            <el-option v-for="item in eventTemplateList" :key="item.id" :label="item.template_name" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-tabs v-model="activeName" type="card" @tab-click="userHistoryListClick">
        <el-tab-pane label="属性" name="first">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">用户信息</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="公司:" prop="company_name">
                      <el-input
                        v-model.trim="form.company_name"
                        placeholder="公司"
                        clearable
                        @focus="handleCompany"
                      >
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="用户:" prop="user_name">
                      <el-input
                        v-model.trim="form.user_name"
                        placeholder="用户"
                        clearable
                        @click.native="handleRole"
                      >
                      </el-input>
                      <!--                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                      <!--                    <el-form-item label="用户:" prop="user_id_name">-->
                      <!--                      <el-input v-model.trim="form.user_id_name" placeholder="用户" clearable @click.native="handleRole"></el-input>-->
                    </el-form-item>
                  </el-col>
                  <!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                  <!--                    <el-form-item label="姓名:">-->
                  <!--                      <el-input-->
                  <!--                        v-model="form.user_name"-->
                  <!--                        v-bind:readonly="true"-->
                  <!--                      ></el-input>-->
                  <!--                    </el-form-item>-->
                  <!--                  </el-col>-->
                  <!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
                  <!--                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">-->
                  <!--                    <el-form-item label="姓名:">-->
                  <!--                      <el-input v-model="form.user_name" v-bind:readonly="true"></el-input>-->
                  <!--                    </el-form-item>-->
                  <!--                  </el-col>-->
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="部门:">
                      <el-input v-model.trim="form.deptName" clearable v-bind:readonly="true">
                        <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="职位:">
                      <el-input v-model="form.position" v-bind:readonly="true"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="位置:">
                      <el-input v-model="form.location" v-bind:readonly="true"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="电话:">
                      <el-input v-model="form.phone" v-bind:readonly="true"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="座机:">
                      <el-input v-model="form.landline" v-bind:readonly="true"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
                    <el-form-item label="邮箱:">
                      <el-input v-model="form.email" v-bind:readonly="true"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
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
                      <quill-editor v-model="form.description" :min-height="200" :options="options"></quill-editor>
                      <p class="ts">
                        请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="附件:" style="width: 400px">
                      <el-upload class="upload-button" action="" :auto-upload="false" :on-remove="handleRemove"
                                 :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed"
                                 :file-list="WorkFileList" :on-change="onChange">
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
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="来源:" prop="source_id">
                      <el-select v-model="form.source_id" placeholder="来源" class="w">
                        <el-option v-for="item in eventSourceList" :key="item.id" :label="item.resource_name"
                                   :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="工单类别:" prop="event_category_name">
                      <el-input v-model.trim="form.event_category_name" clearable @click.native="handlegd">
                        <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                        <!--@click="handlegd"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="请求类型:" prop="request_type">
                      <el-select v-model="form.request_type" placeholder="请选择" class="w">
                        <el-option v-for="item in eventRequestTypeList" :key="item.id" :label="item.request_type_name"
                                   :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="紧急度:" prop="urgency_degree_id">
                      <el-select v-model="form.urgency_degree_id" placeholder="请选择" @change="getFirst" class="w">
                        <el-option v-for="item in urgencyList" :key="item.id" :label="item.urgent_name"
                                   :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="影响度:" prop="effect_degree">
                      <el-select v-model="form.effect_degree" placeholder="请选择" @change="getFirst" class="w">
                        <el-option v-for="item in affectList" :key="item.id" :label="item.impact_name" :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="影响可用性:" prop="is_effect">
                      <el-select v-model="form.is_effect" placeholder="请选择" class="w">
                        <el-option label="是" value="1"></el-option>
                        <el-option label="否" value="0"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="优先级:">
                      {{ this.first }}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="响应SLA/ 解决SLA :" class="tsitem">
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
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="服务群组:">
                      <el-select v-model="form.service_groups_id" placeholder="服务群组" @change="serviceGroupsChange"
                                 class="w">
                        <el-option v-for="item in list" :key="item.id" :label="item.name" :value="item.id"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="处理人:">
                      <el-input v-model.trim="form.handler_name" placeholder="处理人" clearable
                                @click.native="handleRole1">
                        <!-- <el-button
                          slot="prepend"
                          icon="el-icon-location-information"
                          @click="handleRole1"
                        ></el-button> -->
                        <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                        <!--@click="handleRole1"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="预计解决时间:">
                      <el-date-picker v-model="form.ex_solve_time" type="date"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="解决方式:">
                      <el-select v-model="form.solve_type" placeholder="请示类型" class="w">
                        <el-option v-for="item in list1" :key="item.id" :label="item.solution_name" :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="方案分类:">
                      <el-input v-model.trim="form.programme_type_name" clearable @click.native="handlefa">
                        <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                        <!--@click="handlefa"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="12" :md="4" :lg="6" :xl="4">
                    <el-form-item label="费用:">
                      <el-input v-model="form.expenses" type="number"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="解决方案:">
                      <quill-editor v-model="form.solution" :min-height="200" :options="options"></quill-editor>
                      <p class="ts">
                        请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="附件:" style="width: 400px">
                      <el-upload class="upload-button" action="" :auto-upload="false" :on-remove="handleRemoveHandle"
                                 :before-remove="beforeRemoveHandle" multiple :limit="3" :on-exceed="handleExceedHandle"
                                 :file-list="fileListHandle" :on-change="onChangeHandle">
                        <el-button size="small" type="primary">
                          点击上传
                        </el-button>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane :label="oneUser" name="second">
          <el-card shadow="never" style="border-top: 0">
            <el-table v-loading="listLoading" :data="form.eventAssist1" border highlight-current-row
                      :element-loading-text="elementLoadingText" @selection-change="setSelectPeopleRows"
                      height="calc(100vh - 400px)">

              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" width="60" sortable align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="status" label="状态" sortable align="center"
                               :formatter="formatStatus"></el-table-column>
              <template slot="empty">
                <el-empty :image-size="100" description="暂无数据"></el-empty>
              </template>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="danger" icon="el-icon-delete" @click="handleDelete">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handlePeople">
                添加协查人
              </el-button>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane :label="towUser" name="third">
          <el-card shadow="never" style="border-top: 0">
            <el-table v-loading="listLoading" :data="form.eventAssets1" border highlight-current-row
                      :element-loading-text="elementLoadingText" @selection-change="setSelectAssetsManageRows"
                      height="calc(100vh - 400px)">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" width="60" sortable align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型"></el-table-column>
              <el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="deptName" label="部门" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="receiverName" label="领用人" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" align="center">
                <template #default="scope">
                  <el-tag size="mini" icon="el-icon-edit" v-if="scope.row.is_secrecy == '0'" type="primary">
                    否
                  </el-tag>
                  <el-tag size="mini" icon="el-icon-delete" class="red" v-if="scope.row.is_secrecy == '1'"
                          type="success">
                    是
                  </el-tag>
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
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteAssetsManage">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAssetsManageAdd">
                添加关联的资产
              </el-button>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane :label="threeUser" name="fourth">
          <el-card shadow="never" style="border-top: 0">
            <el-table v-loading="listLoading" :data="form.eventRelation1" border highlight-current-row
                      :element-loading-text="elementLoadingText" @selection-change="setSelectEventRows"
                      height="calc(100vh - 400px)">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" width="60" align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="title" label="标题" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间"></el-table-column>
              <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态"></el-table-column>

              <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度">
                <template #default="{ row }">
                  <el-rate v-model="row.event_satisfaction" disabled></el-rate>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteEvents">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleEventsAdd">
                添加关联的工单
              </el-button>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="用户历史请求" name="five">
          <el-card shadow="never" style="border-top: 0">
            <el-table v-loading="listLoading" :data="tableData" border highlight-current-row
                      :element-loading-text="elementLoadingText" @current-change="handleCurrentChange"
                      height="calc(100vh - 400px)">
              <el-table-column show-overflow-tooltip type="index" label="序号" width="60"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_user_name" label="姓名"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" label="标题"></el-table-column>
              <el-table-column show-overflow-tooltip prop="statusName" label="状态" sortable align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="category_name" label="类型" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="phone" label="电话"></el-table-column>
              <!-- <el-table-column
               show-overflow-tooltip
               prop="description"
               label="描述"
             >
             </el-table-column> -->
            </el-table>
            <el-pagination background :current-page="queryUserForm.pageIndex" :page-size="queryUserForm.pageSize"
                           :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2">
            </el-pagination>
          </el-card>
        </el-tab-pane>
      </el-tabs>

      <div class="bottom">
        <el-button type="primary" icon="el-icon-plus" size="medium" :disabled="kaidanLoading" @click="saveok">
          开单
        </el-button>
        <el-button type="warning" size="medium" icon="el-icon-close" @click="closeall">
          保存并关单
        </el-button>
        <el-checkbox v-model="radio1" label="不关闭页面" style="margin-left: 10px"></el-checkbox>
      </div>
    </el-form>

    <children ref="children" @deptData="deptset1"></children>
    <company-dia ref="company" @companyData="companySet"></company-dia>
    <dialogone ref="dialogone" @userData="deptset"></dialogone>
    <dialogtwo ref="dialogtwo" @categoryData="deptset4" @fetch-data="deptset4"></dialogtwo>
    <dialogthree ref="dialogthree" @programmeTypeData="deptset3"></dialogthree>
    <dialogfour ref="dialogfour" @userData="deptset2"></dialogfour>

    <XieChaRen ref="XieChaRen" @listData="getPeopleData"></XieChaRen>
    <assetsManagesDialog ref="assetsManagesDialog" @listData="listAssetsManages"></assetsManagesDialog>
    <eventDialog ref="eventDialog" @listData="listEventLists"></eventDialog>
  </div>
</template>

<script>
import {
  getAllManageInfo,
  userHistory,
  mouldList,
  categoryList,
} from '@/api/eventAllManage'
import { info } from '@/api/eventServiceManage'
import { getAllTypeInfo } from '@/api/eventSolutionType'
import { getAll } from '@/api/origin'
import { getEventRequestTypeList } from '@/api/eventRequestType'
import {
  affectGetAllList,
  urgencyGetAllList,
  slaEventImpactUrgentGetFirst,
} from '@/api/affect'
import { addEvent, closeSave, getAllEventTemplate } from '@/api/event'
import { eventCategoryinfo } from '@/api/event_category'
import { getCompanyUser } from '@/api/userManagement'

import vabQuill from '@/plugins/vabQuill'
import Children from '@/components/bumenTree'
import Dialogone from '@/components/UserSelectModel'
import Dialogfour from '@/components/UserSelectModel'
import Dialogtwo from '@/components/gongdanclass'
import Dialogthree from '@/components/fanganclass'
import assetsManagesDialog from '@/components/assetsTypeDia'
import eventDialog from '@/components/eventDialog'
import XieChaRen from '@/components/UsersDia'
import CompanyDia from '@/components/companySelect'

export default {
  name: 'gztadd',
  components: {
    vabQuill,
    Dialogone,
    eventDialog,
    Dialogtwo,
    Children,
    Dialogthree,
    Dialogfour,
    XieChaRen,
    assetsManagesDialog,
    CompanyDia,
  },
  data() {
    return {
      kaidanLoading: false,
      selectPeopleRows: '',
      selectAssetsManageRows: '',
      selectEventRows: '',
      oneUser: '协查人',
      towUser: '关联的配置项',
      threeUser: '关联的工单',
      eventTemplateList: [],
      WorkFileList: [], //工单附件
      fileListHandle: [], //处理附件
      list: [],
      list1: [],
      eventSourceList: [],
      eventRequestTypeList: [],
      affectList: [],
      urgencyList: [],
      first: '',
      ResponseSLA: '',
      responseTime: '',
      dealTime: '',
      queryForm: {
        pageIndex: 1,
        pageSize: 1000000,
        username: '',
      },
      queryUserForm: {
        pageIndex: 1,
        pageSize: 20,
        user_id: '',
      },
      queryCategroyForm: {
        eventCategoryId: '',
        first_id: '',
        request_type_id: '',
      },
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
      rules: {
        user_name: [
          {
            required: true,
            trigger: 'submit',
            message: '请选择',
          },
        ],
        title: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        description: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        source_id: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        request_type: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        event_category_name: [
          {
            required: true,
            trigger: 'submit',
            message: '请输入',
          },
        ],
        effect_degree: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        urgency_degree_id: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        is_effect: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        company_name: [
          {
            required: true,
            trigger: 'submit',
            message: '请输入',
          },
        ],
      },
      borderColor: '#dcdfe6',
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      loading: false,
      elementLoadingText: '正在加载...',
      tableData: [],
      activeName: 'first',
      input2: '4',
      radio1: '',
      radio2: '1',
      reverse: true,
      activities: [],
      formArr: [{ value1: '' }],
      checked: false,
      checked1: false,
      value: [],
      form: {
        id: '',
        template_id: '',
        user_id: '',
        user_id_name: '',
        user_name: '',
        department: '',
        deptName: '',
        position: '',
        location: '',
        phone: '',
        landline: '',
        email: '',
        title: '',
        description: '',
        source_id: '',
        source_name: '',
        request_type: '',
        request_type_name: '',
        event_category_id: '',
        event_category_name: '',
        effect_degree: '',
        effect_degree_name: '',
        urgency_degree_id: '',
        urgency_degree_name: '',
        is_effect: '',
        priority_id: '',
        priority_name: '',
        service_groups_id: '',
        service_name: '',
        handler: '',
        handler_name: '',
        ex_solve_time: '',
        solve_type: '',
        programme_type: '',
        programme_type_name: '',
        expenses: '',
        solution: '',
        eventAssist1: [],
        eventAssets1: [],
        eventRelation1: [],
        company_id: '',
        company_name: '',
      },
      //协查人范围
      eventAssistList: [],
    }
  },
  created() {
    setTimeout(() => {
      this.fetchData()
      this.fetchData1()
      this.getEventSource()
      this.getEventRequestTypeList()
      this.affectGetAllList()
      this.urgencyGetAllList()
      //工单模板
      this.getAllTemplate();
      this.listLoading = false
    }, 300)
  },
  beforeDestroy() {},
  mounted() {},
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
    // 公司弹窗
    handleCompany() {
      console.log("company弹窗")
      this.$refs['company'].showWin()
    },
    // 公司弹窗回调
    companySet(data) {
      console.log("公司弹窗回调:", data)
      this.form.company_name = data.name;
      this.form.company_id = data.id;
    },
    //根据服务群组得到处理人
    serviceGroupsChange() {
      if (
        this.form.service_groups_id != null &&
        this.form.service_groups_id != undefined &&
        this.form.service_groups_id != ''
      ) {
        info(this.form.service_groups_id).then((res) => {
          if (res.code == 0) {
            if (res.data.eventServiceManage.is_engineer_create == 1) {
              var userlist = res.data.sysUserList1
              if (userlist.length != 0) {
                var user = userlist[0]
                this.form.handler = user.id
                this.form.handler_name = user.name
              } else {
                this.form.handler = ''
                this.form.handler_name = ''
              }
            }
          }
        })
      }
    },
    //工单信息 附件上传 选择文件
    onChange(file, fileList) {
      this.WorkFileList = fileList
    },
    //处理信息 附件上传 选择文件
    onChangeHandle(file, fileList) {
      this.fileListHandle = fileList
    },
    deptset(data) {
      this.form.user_id = data.id
      this.form.user_id_name = data.user_name
      this.form.user_name = data.name
      this.form.department = data.department_id
      this.form.deptName = data.departmentName
      this.form.position = data.position
      this.form.location = data.location
      this.form.phone = data.telephone
      this.form.landline = data.landline
      this.form.email = data.mailbox
    },
    deptset1(data) {
      this.form.department = data.id
      this.form.deptName = data.name
    },
    deptset2(data) {
      this.form.handler = data.id
      this.form.handler_name = data.name
    },
    deptset3(data) {
      this.form.programme_type_name = data.name
      this.form.programme_type = data.id
    },
    deptset4(data) {
      this.form.event_category_name = data.name
      this.form.event_category_id = data.id;
      console.log("工单类别的选择结果:", data)
      // 根据工单类别确认协查人的选择范围
      eventCategoryinfo(data.id).then((res) => {
        // 得到协查人的id集合
        this.eventAssistList = res.data.sysUserList2;
        console.log("得到协查人的id集合:", this.eventAssistList);
      })
    },
    deptset5(data) {
      this.form.eventAssist1 = data
    },
    screenClick() {
      this.$baseMessage('截屏中', 'success')
    },
    screenCommand(command) {
      this.$message('click on item ' + command)
    },
    handleRemove(file, fileList) {
      this.WorkFileList = fileList
    },
    handleRemoveHandle(file, fileList) {
      this.fileListHandle = fileList
    },
    handlebumen() {
      this.$refs['children'].showWin()
    },
    // 用户查找
    handleRole() {
      if (this.form.company_id == null || this.form.company_id == '') {
        this.$baseMessage('请先选择公司', 'warning')
        return
      }
      // 根据公司id查询用户
      getCompanyUser({id:this.form.company_id}).then((res) => {
        console.log("根据公司id查询用户:", res)
        if (res.code == 0) {
          this.userList = res.data;
          let ids = [];
          this.userList.forEach((item) => {
            ids.push(item.id);
          });
          if(ids.length == 0){
            this.$baseMessage('该公司下没有用户', 'warning')
            return
          }
          let query = {
            ids: ids,
          };
          this.$refs['dialogone'].showWin(query);
        }
      })
    },
    handleRole1() {
      var data = {}
      // 判断service_groups_id是否为空
      if (
        this.form.service_groups_id == null ||
        this.form.service_groups_id == undefined ||
        this.form.service_groups_id == ''
      ) {
        this.$baseMessage('请选择服务组', 'error')
        return
      }
      data.group_id = this.form.service_groups_id
      data.type = 2
      this.$refs['dialogfour'].showWin(data)
    },
    handlegd() {
      this.$refs['dialogtwo'].showWin()
    },
    handlefa() {
      this.$refs['dialogthree'].showWin()
    },
    handlePreview(file) { },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length
        } 个文件`
      )
    },
    handleExceedHandle(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length
        } 个文件`
      )
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    beforeRemoveHandle(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    closeall() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.form.ex_solve_time = new Date()
          // this.form.closeTime = new Date()
          // if(this.kaidanLoading){
          //   const { msg } = await update(this.form)
          // }
          const { msg } = await closeSave(this.form)
          this.$baseMessage(msg, 'success')
          // this.$emit('fetch-data')

          // 调用全局挂载的方法
          this.$store.dispatch('tabsBar/delRoute', this.$route)
          // 返回上一步路由
          this.$router.go(-1)
        } else {
          return false
        }
      })
    },
    handleSizeChange(val) {
      this.queryUserForm.pageSize = val
      this.userHistoryListClick(1)
    },
    handleCurrentChange2(val) {
      this.queryUserForm.pageIndex = val
      this.userHistoryListClick(1)
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },

    saveok() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.kaidanLoading = true;
          // 定义任务flag
          var taskFlag = false
          // 判断服务群组如果不为空 则判断服务群组是否有值
          if (
            this.form.service_groups_id != null &&
            this.form.service_groups_id != undefined &&
            this.form.service_groups_id != ''
          ) {
            // 判断处理人是否为空
            if (
              this.form.handler == null ||
              this.form.handler == undefined ||
              this.form.handler == ''
            ) {
              taskFlag = true
            }
          }
          const params = new FormData()
          const uploadedFiles = []
          for (let i = 0; i < this.WorkFileList.length; i++) {
            const f = this.WorkFileList[i]
            if (f.status == 'ready') {
              params.append('file', f.raw)
            } else if (f.status == 'success') {
              uploadedFiles.push(f.url)
            }
          }
          for (let i = 0; i < this.fileListHandle.length; i++) {
            const f = this.fileListHandle[i]
            if (f.status == 'ready') {
              params.append('file1', f.raw)
            } else if (f.status == 'success') {
              uploadedFiles.push(f.url)
            }
          }
          this.form.uploadedFiles = uploadedFiles.join(',')

          // 按钮控制

          if (taskFlag) {
            info(this.form.service_groups_id).then((res) => {
              if (res.code == 0) {
                var auto_engineer = res.data.eventServiceManage.auto_engineer
                if (
                  auto_engineer != null &&
                  auto_engineer != undefined &&
                  auto_engineer != ''
                ) {
                  this.form.handler = auto_engineer
                }
              }
              params.append(
                'body',
                encodeURIComponent(JSON.stringify(this.form))
              )
              console.log(this.form)
              addEvent(params).then((res) => {
                console.log(res)
                this.form.id = res.data.id;
                this.$baseMessage(res.msg, 'success')
                this.$emit('fetch-data');
                if (this.radio1) {
                  console.log('不关闭页面')
                  this.kaidanLoading = true;
                } else {
                  // 调用全局挂载的方法
                  this.$store.dispatch('tabsBar/delRoute', this.$route)
                  // 返回上一步路由
                  this.$router.go(-1)
                  this.kaidanLoading = false;
                }
              })
            })
          } else {
            params.append('body', encodeURIComponent(JSON.stringify(this.form)))
            console.log(this.form)
            addEvent(params).then((res) => {
              console.log(res)
              this.form.id = res.data.id;
              this.$baseMessage(res.msg, 'success')
              this.$emit('fetch-data');
              if (this.radio1) {
                console.log('不关闭页面')
                this.kaidanLoading = true;
              } else {
                // 调用全局挂载的方法
                this.$store.dispatch('tabsBar/delRoute', this.$route)
                // 返回上一步路由
                this.$router.go(-1)
                this.kaidanLoading = false;
              }
            })
          }
        } else {
          return false
        }
      })
    },
    async fetchData() {
      const { data } = await getAllManageInfo({ "isAll": "1" })
      this.list = data
    },

    async fetchData1() {
      const { data } = await getAllTypeInfo()
      this.list1 = data
    },
    /*--工单模板--*/
    getAllTemplate() {
      getAllEventTemplate({}).then((res) => {
        if (res.code == 0) {
          this.eventTemplateList = res.data
        }
      })
    },
    /*--获取事件来源--*/
    getEventSource() {
      getAll({}).then((res) => {
        if (res.code == 0) {
          this.eventSourceList = res.data;
          // 循环eventSourceList
          for (var i = 0; i < this.eventSourceList.length; i++) {
            // 判断是否有值
            if (this.eventSourceList[i].is_def_engineer == '1') {
              this.form.source_id = this.eventSourceList[i].id;
            }
          }
        }
      })
    },
    /*--获取请求类型--*/
    getEventRequestTypeList() {
      getEventRequestTypeList({}).then((res) => {
        if (res.code == 0) {
          this.eventRequestTypeList = res.data
        }
      })
    },
    /*--获取影响度--*/
    affectGetAllList() {
      affectGetAllList({ 'is_use': '1' }).then((res) => {
        if (res.code == 0) {
          this.affectList = res.data
        }
      })
    },
    //点击模板，给与默认值
    mouldListClick(val) {
      mouldList(val).then((res) => {
        if (res.code == 0) {
          var data = res.data.eventTemplate
          //用户信息
          var users = res.data.sysUser
          this.form.user_id = users.id
          this.form.user_id_name = users.user_name
          this.form.user_name = users.name
          this.form.department = users.department_id
          this.form.deptName = users.departmentName
          this.form.position = users.position
          this.form.location = users.location
          this.form.phone = users.telephone
          this.form.landline = users.landline
          this.form.email = users.mailbox
          //根据userId查询相关信息
          this.form.title = data.title //标题
          this.form.description = data.description //描述
          //分类信息
          this.form.source_id = parseInt(data.source) //来源
          this.form.request_type = parseInt(data.request_type) //请求类型
          this.form.event_category_id = data.id //工单类别ID
          this.form.event_category_name = data.bill_type_name //工单类别
          this.form.effect_degree = parseInt(data.effect_degree) //影响度
          this.form.urgency_degree_id = parseInt(data.urgency_degree) //紧急度
          //获取优先级、响应sla/解决sla数据。
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
          this.form.is_effect = data.is_effect //影响可用性
          //处理信息
          this.form.service_groups_id = data.service_groups //服务群组
          var clrs = res.data.clrUser
          this.form.handler = data.handler //处理人id
          this.form.handler_name = clrs.name //处理人名称
          this.form.solve_type = parseInt(data.solve_type) //解决方式
          this.form.programme_type_name = data.programme_type_name //方案分类
          this.form.solution = data.solution //解决方案
        }
      })
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
    //用户历史请求
    userHistoryListClick(tab) {
      if (
        (tab == 1 || tab.index == 4) &&
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
          }
        })
      }
    },
    /*--获取事件紧急度--*/
    urgencyGetAllList() {
      urgencyGetAllList({}).then((res) => {
        if (res.code == 0) {
          this.urgencyList = res.data
        }
      })
    },
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
    // 工单设置多选
    setSelectEventRows(val) {
      this.selectEventRows = val
    },
    // 工单删除
    handleDeleteEvents() {
      if (this.selectEventRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectEventRows.forEach((item, i) => {
            this.form.eventRelation1.some((x, i) => {
              if (x.id == item.id) {
                this.form.eventRelation1.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.threeUser = '关联的工单(' + this.form.eventRelation1.length + ')'
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
        let index = this.form.eventRelation1.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.eventRelation1.push(item)
        }
      })
      this.threeUser = '关联的工单(' + this.form.eventRelation1.length + ')'
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
            this.form.eventAssets1.some((x, i) => {
              if (x.id == item.id) {
                this.form.eventAssets1.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.towUser = '关联的配置项(' + this.form.eventAssets1.length + ')'
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
        let index = this.form.eventAssets1.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.eventAssets1.push(item)
        }
      })
      this.towUser = '关联的配置项(' + this.form.eventAssets1.length + ')'
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
        let index = this.form.eventAssist1.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.eventAssist1.push(item)
        }
      })
      this.oneUser = '协查人(' + this.form.eventAssist1.length + ')'
    },
    //协查人添加
    handlePeople() {
      if (this.eventAssistList.length < 1) {
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
      } else {
        this.$baseMessage('请先选择工程师', 'error')
        return
      }
      data.ids = ids;
      // this.$refs['dialogone3'].showWin(data)
      this.$refs['XieChaRen'].showWin(data)
    },
    //协查人列表删除
    handleDelete() {
      if (this.selectPeopleRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectPeopleRows.forEach((item, i) => {
            this.form.eventAssist1.some((x, i) => {
              if (x.id == item.id) {
                this.form.eventAssist1.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.oneUser = '协查人(' + this.form.eventAssist1.length + ')'
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
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
      return z.y + '-' + z.M + '-' + z.d
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
    // .el-alert__content p{margin:0;}
    .el-tabs__header {
      margin-bottom: 0 !important;
    }

    .el-tabs__item.is-active {
      background: #fff !important;
    }

    .el-card__body {
      height: calc(100vh - 350px) !important;
      overflow-y: auto;
      overflow-x: hidden;
    }

    .upload-button {
      .el-upload {
        display: inline-block;
      }
    }
  }
}
</style>
