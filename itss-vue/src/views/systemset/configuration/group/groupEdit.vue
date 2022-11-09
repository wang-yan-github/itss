<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
      <template>
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="属性" name="first">
            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <!-- <div class="box_header">工程师开单通知用户</div> -->
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-card class="cardbox" shadow="hover">
                        <div slot="header">
                          <span><vab-icon :icon="['fas', 'paperclip']"></vab-icon>基础信息</span>
                          <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                        </div>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="4">
                          <el-form-item label="名称" prop="name">
                            <el-input v-model.trim="form.name" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="4">
                          <el-form-item label="状态" prop="is_use">
                            <el-select v-model="form.is_use" placeholder="请选择" style="width: 100%;">
                              <el-option label="启用" value="1"></el-option>
                              <el-option label="禁用" value="0"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="4">
                          <el-form-item label="排序">
                            <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="4">
                          <el-form-item label="OLA超时分钟数">
                            <el-input v-model.trim="form.ola_time_out" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="4">
                          <el-form-item label="非一线组">
                            <el-select v-model="form.nonFirstLineGroup" placeholder="请选择" style="width: 100%;">
                              <el-option label="是" value="1"></el-option>
                              <el-option label="否" value="0"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="描述" prop="">
                            <el-input v-model.trim="form.description" autocomplete="off" type="textarea"
                                      :rows="5"></el-input>
                          </el-form-item>
                        </el-col>
                      </el-card>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-card class="cardbox" shadow="hover">
                        <div slot="header">
                          <span>
                            <vab-icon :icon="['fas', 'paperclip']"></vab-icon>
                            根据工作日自动暂停和恢复事件单
                          </span>
                          <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                        </div>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="根据工作日自动暂停和恢复事件单" prop="isvip" class="tsitem">
                            <el-select v-model="form.is_working_day" placeholder="请选择" style="width: 100%;">
                              <el-option label="否" value="0"></el-option>
                              <el-option label="是" value="1"></el-option>
                            </el-select>
                          </el-form-item>
                          <p class="tips">注:设置为“否”时，不自动暂停工单</p>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="手动暂停的事件单自动恢复" prop="isvip_" class="tsitem">
                            <el-select v-model="form.is_pause_manually" placeholder="请选择" style="width: 100%;">
                              <el-option label="否" value="0"></el-option>
                              <el-option label="是" value="1"></el-option>
                            </el-select>
                          </el-form-item>
                          <p class="tips">注:设置为“否”时，不自动恢复</p>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="工作日" prop="isvip" class="tsitem">
                            <el-select v-model="form.work_name" placeholder="请选择" clearable style="width: 100%;" @change="changeVal">
                              <el-option v-for="item in workData" :key="item.id" :label="item.work_name"
                                         :value="item.id"></el-option>
                            </el-select>
                          </el-form-item>
                          <p class="tips">注:如果不设置“工作日”或设置的“工作日”被禁用时，则默认使用系统设置的“默认工作日”</p>
                        </el-col>
                      </el-card>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-card class="cardbox" shadow="hover">
                        <div slot="header">
                          <span>
                            <vab-icon :icon="['fas', 'paperclip']"></vab-icon>
                            系统自动指派
                          </span>
                          <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                        </div>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="处理人为空自动指定工程师(开单/指派/转单)" prop="bumen" class="tsitem">
                            <el-select v-model="form.auto_engineer" filterable remote reserve-keyword clearable
                                       placeholder="请输入姓名" :remote-method="remoteMethod" :loading="loading"
                                       style="width: 100%">
                              <template #prefix>
                                <span style="padding-left: 2px">
                                  <i class="el-icon-search"></i>
                                </span>
                              </template>
                              <el-option v-for="item in this.tablelist2" :key="item.id" :label="item.name"
                                         :value="item.id"></el-option>
                            </el-select>
                            <!--<el-button icon="el-icon-search"-->
                            <!--style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
                            <!--@click="openwinTable"></el-button>-->
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="用户自助开单自动指派工程师" class="tsitem">
                            <el-select v-model="form.is_user_create" placeholder="请选择" style="width: 100%;">
                              <el-option label="是" value="1"></el-option>
                              <el-option label="否" value="0"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="工程师开单自动指派工程师" class="tsitem">
                            <el-select v-model="form.is_engineer_create" placeholder="请选择" style="width: 100%;">
                              <el-option label="是" value="1"></el-option>
                              <el-option label="否" value="0"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="指派自动指派工程师" class="tsitem">
                            <el-select v-model="form.is_assign_automatic" placeholder="请选择" style="width: 100%;">
                              <el-option label="是" value="1"></el-option>
                              <el-option label="否" value="0"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="转单自动指派工程师" class="tsitem">
                            <el-select v-model="form.is_automatic_transfer" placeholder="请选择" style="width: 100%;">
                              <el-option label="是" value="1"></el-option>
                              <el-option label="否" value="0"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="自动指派方式" class="tsitem">
                            <el-select v-model="form.assign_type" placeholder="请选择" clearable style="width: 100%;">
                              <el-option label="权重" value="0"></el-option>
                              <el-option label="轮询" value="1"></el-option>
                            </el-select>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="待处理工单数权重%" prop="username" class="tsitem">
                            <el-input v-model.trim="form.to_be_processed" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="预计完成时间数权重%" prop="truename" class="tsitem">
                            <el-input v-model.trim="form.expected_to_complete" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>

                      </el-card>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-card class="cardbox" shadow="hover">
                        <div slot="header">
                          <span><vab-icon :icon="['fas', 'paperclip']"></vab-icon>超时升级</span>
                          <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                        </div>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="响应SLA超时X分钟后升级" prop="username" class="tsitem">
                            <el-input v-model.trim="form.sla_upgrade" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="解决SLA超时Y分钟后升级" prop="truename" class="tsitem">
                            <el-input v-model.trim="form.sla_solve" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="响应前，OLA超时A分钟后升级" prop="truename" class="tsitem">
                            <el-input v-model.trim="form.ola_font_upgrade" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="响应后，OLA超时B分钟后升级" prop="truename" class="tsitem">
                            <el-input v-model.trim="form.ola_after_upgrade" autocomplete="off"></el-input>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="超时升级人" prop="bumen" class="tsitemd">
                            <el-select v-model="form.timeout_user_id" filterable remote reserve-keyword clearable
                                       placeholder="请输入姓名" :remote-method="remoteMethod" :loading="loading"
                                       style="width: 80%">
                              <template #prefix>
                                <span style="padding-left: 2px"><i class="el-icon-search"></i></span>
                              </template>
                              <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name" :value="item.id"></el-option>
                            </el-select>
                            <!--<el-button icon="el-icon-search"-->
                            <!--style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
                            <!--@click="openwinTable"></el-button>-->
                          </el-form-item>
                        </el-col>

                      </el-card>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">注:</p>
                      <p style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                        1、请确认添加的“优先级”已在【影响度紧急度关系管理】中维护了相关的数据，以确保该优先级的正常使用。</p>
                      <p style="line-height: 1.4;margin-top: 0; color: #E6A23C;">
                        2、若系统中的SLA会因“优先级”的影响而不同，请确认添加的“优先级”已在【SLA管理】中维护了相关的SLA信息，以确保该优先级的事件单可正确地设置SLA。</p>
                    </el-col>

                  </el-row>
                </div>
              </div>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="处理工程师" name="second">
            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <!-- <div class="box_header">工程师开单通知用户</div> -->
                <div class="box_content">
                  <el-table ref="listTable" border stripe :data="tablelist2"
                            :element-loading-text="elementLoadingText" highlight-current-row
                            @current-change="handleCurrentChange" @selection-change="setSelectContactRows2">
                    <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip type="index" label="序号" width="60" sortable align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="status" label="状态" sortable align="center" :formatter="formatStatus"></el-table-column>
                  </el-table>
                  <div class="btn-box" style="margin-top: 10px;">
                    <el-button type="danger" icon="el-icon-delete" @click="handleDeleteContract2">移除选择对象</el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="handleAdd2">添加</el-button>
                  </div>
                </div>
              </div>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="开单、指派、转单工程师" name="third">
            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <!-- <div class="box_header">工程师开单通知用户</div> -->
                <div class="box_content">
                  <el-table ref="listTable" border stripe :data="tablelist"
                            :element-loading-text="elementLoadingText" highlight-current-row
                            @current-change="handleCurrentChange" @selection-change="setSelectContactRows">
                    <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip type="index" label="序号" width="60" sortable align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="status" label="状态" sortable align="center" :formatter="formatStatus"></el-table-column>
                  </el-table>
                  <div class="btn-box" style="margin-top: 10px;">
                    <el-button type="danger" icon="el-icon-delete" @click="handleDeleteContract">移除选择对象</el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加</el-button>
                  </div>
                </div>
              </div>
            </el-card>
          </el-tab-pane>

          <el-tab-pane label="负责人" name="fourth">
            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <!-- <div class="box_header">工程师开单通知用户</div> -->
                <div class="box_content">
                  <el-table ref="listTable" border stripe :data="tablelist3"
                            :element-loading-text="elementLoadingText" highlight-current-row
                            @current-change="handleCurrentChange" @selection-change="setSelectContactRows3">
                    <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip type="index" label="序号" width="60" sortable align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="user_name" label="用户名" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="name" label="姓名" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="departmentName" label="部门" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="telephone" label="电话" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
                    <el-table-column show-overflow-tooltip prop="status" label="状态" sortable align="center" :formatter="formatStatus"></el-table-column>
                  </el-table>
                  <div class="btn-box" style="margin-top: 10px;">
                    <el-button type="danger" icon="el-icon-delete" @click="handleDeleteContract3">移除选择对象</el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="handleAdd3">添加</el-button>
                  </div>
                </div>
              </div>
            </el-card>
          </el-tab-pane>

        </el-tabs>
      </template>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

    <dialogone ref="dialogone" @listData="updateCity"></dialogone>
    <dialogone2 ref="dialogone2" @listData="updateCity2"></dialogone2>
    <dialogone3 ref="dialogone3" @listData="updateCity3"></dialogone3>
  </div>
</template>

<script>
import {
  getList, dropDownData, upUserData, save, getUserList, info, edit
} from '@/api/eventServiceManage'

import Dialogone from '@/components/UserSelectModel'
import Dialogone2 from '@/components/UserByGroup'
import Dialogone3 from '@/components/UsersDia'

export default {
  name: 'groupEdit',
  components: {
    Dialogone,
    Dialogone2,
    Dialogone3,
  },
  data() {
    return {
      isDisable: false,
      activeName: 'first',
      input2: '',
      formArr: [{
        value1: ''
      }],
      formArr1: [{
        value1: ''
      }],
      tablelist: [],
      tablelist2: [],
      tablelist3: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      selectRows2: '',
      selectRows3: '',
      elementLoadingText: '正在加载...',
      checked: false,
      checked1: false,
      options: [],
      value: [],
      list: [],
      selectContactRows: '',
      loading: false,
      states: [
        'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming',
      ],
      form: {
        username: '',
        truename: '',
        password: '',
        email: '',
        permissions: [],
        bumen: '',
        is_use: '',
        name: '',
        sort: '',
        ola_time_out: '',
        nonFirstLineGroup: '',
        description: '',
        is_user_create: "",
        is_automatic_transfer: "",
        assign_type: "",
        is_engineer_create: "",
        is_assign_automatic: "",
        to_be_processed: "",
        expected_to_complete: "",
        sla_upgrade: "",
        sla_solve: "",
        ola_font_upgrade: "",
        ola_after_upgrade: "",
        timeout_user_id: "",
        is_working_day: "",
        is_pause_manually: "",
        work_id: "",
        work_name: "",
        auto_engineer: "",
        auto_engineer_name: "",
        id: '',
      },
      rules: {
        username: [{
          required: false,
          trigger: 'blur',
          message: '请输入名称'
        },],
        name: [{
          required: true,
          trigger: 'blur',
          message: '请输入名称'
        },],
        is_use: [{
          required: true,
          trigger: 'blur',
          message: '请选择状态'
        }],
      },
      workData: [],
      auto_engineer_data: "",
      eventServiceManage: "",
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        is_use: '',
        name: '',
      },
    }
  },
  created() {
    this.dropDown();
    this.fetchData()
    this.upUserData();
  },
  beforeDestroy() {
  },
  mounted() {
    this.list = this.states.map((item) => {
      return {
        value: `${item}`,
        label: `${item}`
      }
    })
  },
  methods: {
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
    delItemo(index) {
      var that = this
      if (that.formArr1.length == 1) {
        this.$baseMessage('至少保留一条', 'error')
      } else {
        that.formArr1.splice(index, 1)
      }
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      // this.tableData
    },
    openwinTable() {
    },
    //开单、指派、转单工程师
    handleAdd() {
      var data = {};
      // 把tablelist2的数据对象保存到一个id集合
      var ids = [];
      if (this.tablelist2.length > 0) {
        for (var i = 0; i < this.tablelist2.length; i++) {
          ids.push(this.tablelist2[i].id);
        }
      }else{
        this.$baseMessage('请先选择工程师', 'error')
        return
      }
      data.ids = ids;
      this.$refs['dialogone'].showWin(data)
    },
    handleAdd2() {
      console.log(this.form.id)
      console.log(this.tablelist2)
      var x = {};
      x.id = this.form.id;
      x.userList = this.tablelist2
      this.$refs['dialogone2'].showWin(x)
    },
    handleAdd3() {
      var data = {};
      // 把tablelist2的数据对象保存到一个id集合
      var ids = [];
      if (this.tablelist2.length > 0) {
        for (var i = 0; i < this.tablelist2.length; i++) {
          ids.push(this.tablelist2[i].id);
        }
      }else{
        this.$baseMessage('请先选择工程师', 'error')
        return
      }
      data.ids = ids;
      this.$refs['dialogone3'].showWin(data)
    },
    handleDeleteContract() {
      var that = this;
      if (that.selectContactRows.length > 0) {
        that.$baseConfirm('你确定要删除选中项吗', null, async () => {
          that.selectContactRows.forEach((item, i) => {
            that.tablelist.some((x, i) => {
              if (x.id == item.id) {
                that.tablelist.splice(i, 1);
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          });
          that.$baseMessage('删除成功', 'success');
          // var data = that.tablelist;
          // var colorsArr = data.reduce(function (arr, data) {
          //   arr.push(data.id);
          //   return arr;
          // }, []);
          // that.$refs['dialogone'].fcz(colorsArr)
        })
      } else {
        that.$baseMessage('未选中任何行', 'error');
        return false;
      }
    },
    handleDeleteContract2() {
      if (this.selectContactRows2.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectContactRows2.forEach((item, i) => {
            this.tablelist2.some((x, i) => {
              if (x.id == item.id) {
                this.tablelist2.splice(i, 1);
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          });
          this.$baseMessage('删除成功', 'success');
          // var data = this.tablelist;
          // var colorsArr = data.reduce(function (arr, data) {
          //   arr.push(data.id);
          //   return arr;
          // }, []);
          // this.$refs['dialogone'].fcz(colorsArr)
        })
      } else {
        this.$baseMessage('未选中任何行', 'error');
        return false;
      }
    },
    handleDeleteContract3() {
      if (this.selectContactRows3.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectContactRows3.forEach((item, i) => {
            this.tablelist3.some((x, i) => {
              if (x.id == item.id) {
                this.tablelist3.splice(i, 1);
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          });
          this.$baseMessage('删除成功', 'success');
          // var data = this.tablelist;
          // var colorsArr = data.reduce(function (arr, data) {
          //   arr.push(data.id);
          //   return arr;
          // }, []);
          // this.$refs['dialogone'].fcz(colorsArr)
        })
      } else {
        this.$baseMessage('未选中任何行', 'error');
        return false;
      }
    },
    fetchData() {
      var that = this;
      if (null != that.$route.query.id || undefined != that.$route.query.id) {
        info(that.$route.query.id).then((res) => {
          that.form = res.data.eventServiceManage;
          console.log(that.form);
          if (res.data.eventServiceManage.auto_engineer){
            that.form.auto_engineer = parseInt(res.data.eventServiceManage.auto_engineer);
          }
          that.tablelist = res.data.sysUserList1;
          that.tablelist2 = res.data.sysUserList2;
          that.tablelist3 = res.data.sysUserList3;
          console.log(that.form);
        });
      }
    },
    async dropDown() {
      var that = this;
      const resp = await dropDownData()
      that.workData = resp.data;
    },
    async upUserData() {
      var that = this;
      const resp = await upUserData()
      that.auto_engineer_data = resp.data;
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
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    saveok() {
      this.isDisable = true;
      var that = this;
      console.log(that.form);
      console.log(that.form);
      that.$refs['form'].validate(async (valid) => {

        if (valid) {
          var data = {
            "eventServiceManage": that.form,
            "type1": that.tablelist,
            "type2": that.tablelist2,
            "type3": that.tablelist3,
          }
          // 新增
          if (that.form.id == null || that.form.id == undefined || that.form.id == "") {
            save(data).then((res) => {
              if (res.code == 0) {
                that.$baseMessage("成功", 'success')
                that.$store.dispatch('tabsBar/delRoute', this.$route)
                that.$router.push({
                  path: '/set/group',
                })
              } else {
                that.$baseMessage(res.msg, 'error');
                that.isDisable = false;
              }
            });
          } else {
            edit(data).then((res) => {
              if (res.code == 0) {
                that.$baseMessage("成功", 'success')
                that.$store.dispatch('tabsBar/delRoute', this.$route)
                that.$router.push({
                  path: '/set/group',
                })
              } else {
                that.$baseMessage(res.msg, 'error');
                that.isDisable = false;
              }
            });
          }
          // this.closeall()
        } else {
          that.isDisable = false;
          return false
        }
      })
    },
    updateCity(rows) {//开单、指派转单工程师
      console.log("开单、指派转单工程师回填: ",rows)
      this.tablelist = [];
      this.tablelist.push(rows)
      // rows.forEach((item, i) => {
      //   let index = this.tablelist.findIndex((ele) => {
      //     return ele.id === item.id;
      //   });
      //   if (index === -1) {
      //     this.tablelist.push(item)
      //   }
      // })
    },
    updateCity2(rows) {//处理工程师
      rows.forEach((item, i) => {
        let index = this.tablelist2.findIndex((ele) => {
          return ele.id === item.id;
        });
        if (index === -1) {
          this.tablelist2.push(item)
        }
      })
    },
    updateCity3(rows) {//负责人
      rows.forEach((item, i) => {
        let index = this.tablelist3.findIndex((ele) => {
          return ele.id === item.id;
        });
        if (index === -1) {
          this.tablelist3.push(item)
        }
      })
    },
    setSelectContactRows(val) {
      this.selectContactRows = val
    },
    setSelectContactRows2(val) {
      this.selectContactRows2 = val
    },
    setSelectContactRows3(val) {
      this.selectContactRows3 = val
    },
    changeVal(val){
      this.form.work_id = val
    }
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  .hiddenmore {
    display: inline;

    ::v-deep {
      .el-input {
        width: 130px;
      }
    }
  }
}

.tips {
  color: #E6A23C;
}

.boxtable .btn-box {
  margin-top: 10px;
}

::v-deep {
  .el-tabs__header {
    margin-bottom: 0 !important;
  }

  .el-tabs__item.is-active {
    background: #fff !important;
  }

  .el-card__body {
    /*height: 620px !important;*/
    //height: calc(100vh - 330px);
    overflow-y: auto;
    overflow-x: hidden;
  }
  .tsitemd {
    label {
      width: 260 !important;
      text-align: left;
    }

    .el-form-item__content {
      margin-left: 0 !important;
    }
  }
}
</style>
