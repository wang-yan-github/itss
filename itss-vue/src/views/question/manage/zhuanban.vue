<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <div v-model="activeName" class="box_card">
              <div class="box_header">基础信息</div>
              <div class="box_content">
                <el-form-item label="标题:" class="tsitem">
                  {{ form.title }}
                </el-form-item>
                <el-form-item label="描述:" class="tsitem">
                  <div class="ql-editor content-detail" v-html="form.description"/>
                </el-form-item>
                <el-form-item label="附件:" class="tsitem">
                  <div class="bghui">
                    <el-upload
                      ref="uploadPicture"
                      class="upload-demo"
                      action=""
                      :on-preview="handlePreview"
                      disabled
                      :file-list="fileManageInfoList"
                    ></el-upload>
                  </div>
                </el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">转办</div>
              <div class="box_content">
                <el-row :gutter="20">

                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="指定人:" prop="handle_user">
                      <el-select v-model="form.handle_user" filterable remote reserve-keyword
                                 placeholder="请输入姓名" :loading="loading"
                                 style="width: 80%">
                        <template #prefix>
                            <span style="padding-left: 2px">
                              <i class="el-icon-search"></i>
                            </span>
                        </template>
                        <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name"
                                   :value="item.id"></el-option>
                      </el-select>
                      <el-button icon="el-icon-search"
                                 style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                                 @click="openwinTable"></el-button>
                    </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="备注:">
                      <vab-quill
                        v-model="form.transfer_remark"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>

                    </el-form-item>
                  </el-col>

                </el-row>
              </div>
            </div>

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
                <el-form-item label="问题单号:" class="mb0">
                  {{ form.code }}
                </el-form-item>
                <el-form-item label="状态:" class="mb0"> {{ form.status_name }}</el-form-item>

                <el-form-item label="当前操作人:" class="mb0">{{ form.now_operator_user_name }}</el-form-item>
                <el-form-item label="创建人:" class="mb0">{{ form.create_user_name }}</el-form-item>
                <el-form-item label="创建时间:" class="mb0">{{ form.create_time }}</el-form-item>
                <el-form-item label="期望解决时间:" class="mb0"> {{ form.hope_solve_time }}</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">分类信息</div>
              <div class="box_content">
                <el-form-item label="来源:" class="mb0">{{ form.source_name }}</el-form-item>
                <el-form-item label="优先级:" class="mb0">{{ form.priority_name }}</el-form-item>
                <el-form-item label="问题类别:" class="mb0">
                  {{ form.category_name }}
                </el-form-item>
<!--                <el-form-item label="sla:" class="mb0">{{ form.sla_time }}</el-form-item>-->
<!--                <el-form-item label="关单时限:" class="mb0">{{ form.time_limit }}</el-form-item>-->
                <el-form-item label="解决分钟数:" class="mb0">{{ (form.resolution_minutes==null || form.resolution_minutes==0) ? "0":form.resolution_minutes }} 分钟</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">时间信息</div>
              <div class="box_content step_content">
                <!--<el-steps :active="1" align-center>-->
                <!--<el-step title="开单" :description="form.create_time"></el-step>-->
                <!--<el-step title="鉴定" :description="form.appraisal_time"></el-step>-->
                <!--<el-step title="解决" :description="form.solve_time"></el-step>-->
                <!--<el-step title="审核" v-for="item in form.questionReviewers" :key="item.id"-->
                <!--:description="item.update_time"></el-step>-->
                <!--<el-step title="关单" :description="form.clearance_time"></el-step>-->
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
        <el-button type="primary" size="medium" :loading="loadingbut" @click="saveok">
          保存
        </el-button>
        <el-button size="medium" @click="closeall">
          取消
        </el-button>
      </div>
    </el-form>

    <!--用户-->
    <users ref="users" @userData="getUser"></users>
  </div>
</template>

<script>
  import {getQuestionById, onDbTransferUpd, upUserData} from '@/api/question'
  import {download} from '@/api/file'
  // 引用文本编辑器页面
  import vabQuill from '@/plugins/vabQuill'
  //引用处理人弹窗页面
  import Users from '@/components/UserSelectModel'

  export default {
    name: 'manageZhuanban',
    components: {vabQuill, Users},
    data() {
      return {
        firstList: [],
        sourceList: [],
        loadingbut: false,//默认提交按钮可点击
        elementLoadingText: '正在加载...',
        selectEventRows: '',
        selectChangeRows: '',
        tableData: [],
        useroptions: [],
        list: [],
        listLoading: true,
        loading: false,
        auto_engineer_data: [],
        logsName: '',
        timeStatus: 0,
        stepData: [],
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
          //转办备注
          transfer_remark: '',
          //事件工单列表
          events: [],
          //变更列表
          changeInfos: [],
          //问题日志
          questionLogs: [],
          //关联审核人
          questionReviewers: [],
        },
        rules: {
          handle_user: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
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
      this.upUserData();
    },
    methods: {
      // 判断是否为空
      isEmpty(obj) {
        if (typeof obj == 'undefined' || obj == null || obj == '') {
          return true
        } else {
          return false
        }
      },
      async getData() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getQuestionById(query.id);
        this.form = data;
        // this.logsName = "日志(" + data.logs.length + ")";

        //时间信息
        this.stepData = [];
        this.stepData.push({title: "开单", description: data.create_time});
        this.stepData.push({title: "鉴定", description: data.appraisal_time});
        this.stepData.push({title: "解决", description: data.solve_time});
        data.questionReviewers.forEach((item, i) => {
          this.stepData.push({title:item.review_user_name+ "审核", description: item.update_time});
        });
        this.stepData.push({title: "关单", description: data.clearance_time});
        //步骤条 设置当前激活步骤
        let count = 0;
        if (!this.isEmpty(data.create_time)) {
          count++;
        }
        if (!this.isEmpty(data.appraisal_time)) {
          count++;
        }
        if (!this.isEmpty(data.solve_time)) {
          count++;
        }
        data.questionReviewers.forEach((item, i) => {
          if (!this.isEmpty(item.update_time)) {
            count++;
          }
        });
        if (!this.isEmpty(data.clearance_time)) {
          count++;
        }
        this.timeStatus = count;
        //属性信息 附件
        if (data.fileManageInfoList) {
          data.fileManageInfoList.forEach((x) => {
            this.fileManageInfoList.push({name: x.file_name, url: x.id})
          })
        }

        console.log(data)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async upUserData() {
        var that = this;
        const resp = await upUserData();
        that.auto_engineer_data = resp.data;
      },
      openwinTable() {
        this.$refs['users'].showWin();
      },
      //处理人选择，数据渲染
      getUser(row) {
        this.form.handle_user = row.id;
        this.form.handle_user_name = row.name;
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route);
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.$refs['form'].validate(async (valid) => {
          this.loadingbut = true;
          if (valid) {
            const {
              msg
            } = await onDbTransferUpd(this.form);
            this.$baseMessage(msg, 'success');
            // this.$emit('fetch-data')

            this.closeall()
          } else {
            this.loadingbut = false;
            return false
          }
        })
      },
      handlePreview(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]));
          const link = document.createElement('a');
          link.href = url;
          link.setAttribute('download', file.name);
          document.body.appendChild(link);
          link.click();
        })
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

      .el-tabs--border-card {
        box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
      }

      .el-card__body {
        height: calc(100vh - 290px) !important;

        .el-tabs__content {
          height: calc(100vh - 350px)!important;
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
      .step_content{
        overflow-x: auto;
      }
    }
    .content-detail{
      padding: 8px 15px 8px 0;
    }
  }
</style>
