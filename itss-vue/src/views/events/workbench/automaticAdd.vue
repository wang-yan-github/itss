<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <!--<el-card shadow="never">-->
        <!--<el-tabs v-model="activeName" type="card">-->
          <!--<el-tab-pane label="属性" name="first">-->
            <el-card shadow="never">
              <div class="box_card">
                <div class="box_header">基本信息</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="服务类别" prop="parent_type_name">
                        <el-input autocomplete="off" placeholder="请选择" style="width: 80%;" @click.native="openwinOne"
                                  v-model="form.parent_type_name"></el-input>
                        <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwinOne"></el-button>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题:" prop="title">
                        <el-input v-model="form.title"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="描述:" prop="description">
                        <vab-quill v-model="form.description" :min-height="200" :options="optionsText"></vab-quill>
                        <p class="ts">请详细描述问题或现象，可ctrl+v粘贴图片或上传附件</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-show="configShow">
                      <el-form-item label="关联的配置项" prop="eventAssetName">
                        <el-input autocomplete="off" placeholder="请选择" style="width: 80%;" @click.native="handleAddpz" v-model="form.eventAssetName"></el-input>
                        <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="handleAddpz"></el-button>
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
                          :file-list="WorkFileList"
                          :on-change="onChange">
                          <el-button size="small" type="primary">点击上传</el-button>
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
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="请求类型:" prop="request_type">
                        <el-select v-model="form.request_type" placeholder="请选择">
                          <el-option v-for="item in eventRequestTypeList" :key="item.id" :label="item.request_type_name" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="紧急度:" prop="urgency_degree_id">
                        <el-select v-model="form.urgency_degree_id" placeholder="请选择" @change="getFirst">
                          <el-option v-for="item in urgencyList" :key="item.id" :label="item.urgent_name" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="影响度:" prop="effect_degree">
                        <el-select v-model="form.effect_degree" placeholder="请选择" @change="getFirst">
                          <el-option v-for="item in affectList" :key="item.id" :label="item.impact_name" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="优先级:">{{ this.first }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="响应SLA/ 解决SLA :" class="tsitem">{{ this.ResponseSLA }}</el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
            </el-card>
          <!--</el-tab-pane>-->
        <!--</el-tabs>-->
      <!--</el-card>-->
      <div class="bottom">
        <el-button type="primary" size="medium" :disabled="kaidanLoading" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>

    <children ref="children" @fetch-data="getBillType"></children>
    <assetsManagesDialog ref="assetsManagesDialog" @listData="listAssetsManages"></assetsManagesDialog>

  </div>
</template>
<script>
import {affectGetAllList, urgencyGetAllList, slaEventImpactUrgentGetFirst} from '@/api/affect'
import {getEventRequestTypeList , getKehuType} from '@/api/eventRequestType'
import {categoryList, saveWantToBill} from '@/api/eventAllManage'

import assetsManagesDialog from '@/components/assetsManageRadio'
import vabQuill from '@/plugins/vabQuill'
import Children from '@/components/eventServiceCategoryDia'

// 我要开单
export default {
  name: 'wykdadd',
  components: {vabQuill, Children, assetsManagesDialog},
  data() {
    return {
      kaidanLoading: false,
      activeName: 'first',
      options: [],
      inputval: '',
      first: '',
      configShow: false,
      ResponseSLA: '',
      form: {
        service_category: '',
        parent_type_name: '',
        handler: '',
        request_type: '',//考虑请求类型
        urgency_degree_id: '',
        priority_id: '',//考虑优先级
        event_category_id: '',//工单类别
        title: '',//标题
        description: '',//描述
        eventAssets1: [],
        eventAssetName: '',
        uploadedFiles: ''
      },
      WorkFileList: [],
      tableData: [],
      eventRequestTypeList: [],
      urgencyList: [],
      affectList: [],
      queryCategroyForm: {
        eventCategoryId: '',
        first_id: '',
        request_type_id: ''
      },

      optionsText: {
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
        parent_type_name: [
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
        // eventAssetName: [
        //   {
        //     required: true,
        //     trigger: 'submit',
        //     message: '请选择',
        //   },
        // ],
        request_type: [
          {
            required: true,
            trigger: 'blur',
            message: '请选择',
          },
        ],
        urgency_degree_id: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ],
        effect_degree: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入',
          },
        ]
      },
    }
  },
  created() {
    setTimeout(() => {
      this.getEventRequestTypeList();
      this.affectGetAllList();
      this.urgencyGetAllList();
      this.initKehuType();
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
    //工单信息 附件上传 选择文件
    onChange(file, fileList) {
      this.WorkFileList = fileList;
    },
    initKehuType() {
      var that = this;
      getKehuType({}).then((res) => {
        if (res.code == 0) {
          var eventCategory = res.data.eventCategory;
          var config = res.data.config;
          if(eventCategory && "1" === config.is_category){
            that.form.parent_type_name = eventCategory.name;
            that.form.service_category = eventCategory.id;//服务类别id
          }
          if("1" == config.is_show_config){
            that.configShow = true
          }
        }
      })
    },
    saveok: function () {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.kaidanLoading = true;
          const params = new FormData();
          const uploadedFiles = [];
          for (let i = 0; i < this.WorkFileList.length; i++) {
            const f = this.WorkFileList[i];
            if (f.status == 'ready') {
              params.append('file', f.raw);
            } else if (f.status == 'success') {
              uploadedFiles.push(f.url);
            }
          }
          this.form.uploadedFiles = uploadedFiles.join(',');
          params.append('body', JSON.stringify(this.form));
          const {msg} = await saveWantToBill(params)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.kaidanLoading = false;
          // 调用全局挂载的方法
          this.$store.dispatch('tabsBar/delRoute', this.$route)
          // 返回上一步路由
          this.$router.go(-1)
        } else {
          this.kaidanLoading = false;
          return false
        }
      })
    },
    closeall: function () {
      this.$router.push({
        path: '/thing/selfBill'
      })
      // // 调用全局挂载的方法
      // this.$store.dispatch('tabsBar/delRoute', this.$route)
      // // 返回上一步路由
      // this.$router.go(-1)
    },
    loadingList: function () {//数据加载

    },
    handlePreview: function () {

    },
    handleRemove(file, fileList) {
      this.WorkFileList = fileList;
    },
    beforeRemove: function () {

    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    getEventRequestTypeList() {//获取请求类型
      getEventRequestTypeList({}).then((res) => {
        if (res.code == 0) {
          this.eventRequestTypeList = res.data
        }
      })
    },
    urgencyGetAllList() {//获取事件紧急度
      urgencyGetAllList({}).then((res) => {
        if (res.code == 0) {
          this.urgencyList = res.data
        }
      })
    },
    affectGetAllList() {
      affectGetAllList({}).then((res) => {
        if (res.code == 0) {
          this.affectList = res.data
        }
      })
    },
    handleAddpz() {//关联配置项
      this.$refs['assetsManagesDialog'].showWin()
    },
    listAssetsManages(row) {
      var that = this;
      that.form.eventAssets1 = [];
      that.form.eventAssets1.push(row);
      that.form.eventAssetName = row.assets_number;
    },
    openwinOne() {//服务类别
      this.$refs['children'].showWin()
    },
    getBillType(data) {
      console.log("服务类别回显:", data)
      var that = this;
      that.form.service_groups_id = data.group_id;
      that.form.event_category_id = data.worker_order_id;//工单类别id
      that.form.parent_type_name = data.name;
      that.form.handler = data.engineer_id;
      that.form.service_category = data.id;//服务类别id
      if ((this.form.priority_id != null && this.form.priority_id != undefined && this.form.priority_id != '')
        && (this.form.request_type != null && this.form.request_type != undefined && this.form.request_type != '')) {
        this.categoryListClick();
      }
    },
    getFirst() {
      if (this.form.effect_degree != null && this.form.effect_degree != ''
        && this.form.urgency_degree_id != null && this.form.urgency_degree_id != '') {
        slaEventImpactUrgentGetFirst({
          impact_id: this.form.effect_degree,
          urgent_id: this.form.urgency_degree_id
        }).then((res) => {
          if (res.code == 0) {
            if (res.data != null) {
              this.form.priority_id = res.data.first_id;
              this.first = res.data.first_name;
              this.categoryListClick();
            } else {
              this.first = ''
            }
          }
        })
      } else {
        this.first = ''
      }
    },
    categoryListClick() {
      if ((this.form.priority_id != null && this.form.priority_id != undefined && this.form.priority_id != '') &&
        (this.form.event_category_id != null && this.form.event_category_id != undefined && this.form.event_category_id != '') &&
        (this.form.request_type != null && this.form.request_type != undefined && this.form.request_type != '')) {
        this.queryCategroyForm.first_id = this.form.priority_id;//考虑优先级
        this.queryCategroyForm.eventCategoryId = this.form.event_category_id;//工单类别
        this.queryCategroyForm.request_type_id = this.form.request_type;//考虑请求类型
        categoryList(this.queryCategroyForm).then((res) => {
          if (res.code == 0) {
            if (res.data == 1) {
              this.ResponseSLA = "暂无";
            } else {
              this.ResponseSLA = res.data.response_time + "分钟 / " + res.data.solve_time + "分钟";
            }
          }
        })
      }
    },
  }

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
      .el-tabs__content {
        height: calc(100vh - 350px);
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
  }
}
</style>
