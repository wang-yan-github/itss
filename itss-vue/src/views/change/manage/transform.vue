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
                      <div class="ql-editor content-detail" v-html="form.changeInfo.change_reason"/>
                      <!--<p>{{ this.form.changeInfo.change_reason }}</p>-->
                    </el-form-item>
                    <el-form-item label="当前环境:">
                      <div class="ql-editor content-detail" v-html="form.changeInfo.current_environment"/>
                      <!--{{ this.form.changeInfo.current_environment }}-->
                    </el-form-item>
                    <!--<el-form-item label="附件:">-->
                    <!--<div class="bghui">-->
                    <!--<el-image-->
                    <!--style="width: 50px; height: 50px"-->
                    <!--:src="url"-->
                    <!--:preview-src-list="srcList"-->
                    <!--&gt;</el-image>-->
                    <!--<div class="r">-->
                    <!--<p class="p1" style="margin: 0; color: #333">-->
                    <!--3232333456891983223.jpg-->
                    <!--</p>-->
                    <!--<p class="p2" style="margin: 0; color: #999">-->
                    <!--500kb 3-15 10:00 致敬-->
                    <!--</p>-->
                    <!--</div>-->
                    <!--</div>-->
                    <!--</el-form-item>-->
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">转办</div>
                  <div class="box_content">
                    <el-row :gutter="20">

                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="转办人:" prop="trans_form_user">
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

      </div>
    </el-form>

  </div>
</template>

<script>
  import {
    getUserList,
    getChange, toTransForm
  } from '@/api/change'
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'changeTransform',
    components: {vabQuill},
    data() {
      return {
        stepData: [],
        timeStatus: 0,
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
            {required: true, trigger: 'blur', message: '请输入转办人'},
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
        this.title = '查看详情';
        this.dialogFormVisible = true
      },
      close() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route);
        // 返回上一步路由
        this.$router.go(-1)
      },
      async getUserList() {
        const {data} = await getUserList();
        console.log("人员列表data：");
        console.log(data);
        this.userList = data;

        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            toTransForm(this.form).then((res) => {
              if (res.code == 0) {
                this.$baseMessage('转办成功', 'success');
                this.$store.dispatch('tabsBar/delRoute', this.$route);
                this.$router.push({
                  path: '/biangeng/biangengManagement',
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
        this.form = data;
        this.form.changeInfo.remark = "";
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
      .step_content{
        overflow-x: auto;
      }
    }
    .content-detail{
      padding: 8px 15px 8px 0;
    }
  }
</style>
