<template>
  <div class="main-container pb80">
    <el-form ref="form" label-width="120px">
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
                    <el-form-item label="申请发布理由:" class="tsitem">
                      <div class="ql-editor content-detail" v-html="form.reason"></div>
                    </el-form-item>
                    <el-form-item label="申请发布内容:" class="tsitem">
                      <div class="ql-editor content-detail" v-html="form.content"></div>
                    </el-form-item>
                    <el-form-item label="发布方案:" class="tsitem">
                      <div class="ql-editor content-detail" v-html="form.publish_plan"></div>
                    </el-form-item>
                    <el-form-item label="附件:" class="tsitem">
                      <el-upload
                        class="upload-button"
                        action=""
                        :auto-upload="false"
                        multiple
                        :limit="3"
                        :on-preview="handlePreview"
                        :file-list="fileManageInfoList"
                      >
                        <!--<el-button size="small" type="primary">点击上传</el-button>-->
                      </el-upload>
                    </el-form-item>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">评审信息</div>
                  <div class="box_content">
                    <el-row :gutter="20" v-for="(item, index) in approvalInfos" :key="index">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="评审人:">
                          {{ item.approver_name }}
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="评审意见:" >
                          <div class="ql-editor content-detail" v-html="item.approval_opinions"></div>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">测试信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="测试人:">
                          <div class="ql-editor content-detail" v-html="form.tester_name"></div>
                        </el-form-item>
                      </el-col>

                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="测试时间:">
                          <div class="ql-editor content-detail" v-html="form.test_time"></div>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="测试结果:">
                          <div class="ql-editor content-detail" v-html="form.test_result"></div>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="测试方案:">
                          <div class="ql-editor content-detail" v-html="form.test_plan"></div>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">发布信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="发布备注:">
                          <quill-editor
                            v-model="form.remarks"
                            :min-height="200"
                            :options="options"
                          ></quill-editor>
                          <p class="ts">
                            请详细填写问题原因和解决方案请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                            *
                          </p>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="发布通知:">
                          <el-checkbox v-model="form.publish_notice">通知</el-checkbox>
                        </el-form-item>
                      </el-col>

                    </el-row>
                  </div>
                </div>


              </el-tab-pane>
              <el-tab-pane label="关联的变更" name="second">
                <el-table :data="form.changeVoList" stripe border>
                  <el-table-column
                    show-overflow-tooltip
                    prop="change_no"
                    label="变更单号"
                    sortable
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
                    prop="classify_name"
                    label="变更类别"
                    sortable
                    align="center"
                  ></el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="type_name"
                    label="变更类型"
                    align="center"
                  ></el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="status_name"
                    label="状态"
                    align="center"
                  ></el-table-column>
                </el-table>

              </el-tab-pane>
              <el-tab-pane :label="logName" name="fourth">
                <div class="box_card">
                  <div class="box_content">
                    <el-timeline>
                      <el-timeline-item
                        v-for="(log, index) in logList"
                        :key="index"
                        :timestamp="log.operate_time"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
                        <el-card>
                          <h4>{{log.title}}</h4>
                          <p>{{log.user_name}}</p>
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
              <div class="box_header">属性信息</div>
              <!-- <el-divider content-position="left" style="margin-bottom: 0">
                  <span class="font16 b">工单信息</span>
                </el-divider> -->
              <div class="box_content">
                <el-form-item label="编号:" class="mb0">
                  {{ form.id }}

                </el-form-item>
                <el-form-item label="发布单号:" class="mb0">
                  {{ form.issue_no }}
                </el-form-item>
                <el-form-item label="状态:" class="mb0">
                  <div v-if="form.status == '0'">已撤销</div>
                  <div v-else-if="form.status == '1'">待评审</div>
                  <div v-if="form.status == '2'">评审拒绝</div>
                  <div v-else-if="form.status == '3'">待测试</div>
                  <div v-if="form.status == '4'">待发布</div>
                  <div v-else-if="form.status == '5'">发布成功</div>
                  <div v-else-if="form.status == '6'">发布失败</div>
                </el-form-item>

                <el-form-item label="当前操作人:" class="mb0">{{ form.operator }}</el-form-item>
                <el-form-item label="发布类别:" class="mb0">{{ form.category_name }}</el-form-item>
                <el-form-item label="发布类型:" class="mb0">{{ form.type=='1'?'一般发布':'紧急发布' }}</el-form-item>
                <el-form-item label="创建人:" class="mb0">{{ form.create_user_name }}</el-form-item>
                <el-form-item label="创建时间:" class="mb0">{{ form.create_time }}</el-form-item>
              </div>
            </div>

            <div class="box_card">
              <div class="box_header">进度信息</div>
              <div class="box_content">
                <el-steps :active="(form.status  == 1) ? 1 : Number(form.status)-1 " align-center>
                  <el-step title="待评审" ></el-step>
                  <el-step title="待测试" ></el-step>
                  <el-step title="待发布" ></el-step>
                  <el-step title="发布成功" ></el-step>
                </el-steps>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="doPublish('1')">
          发布成功
        </el-button>
        <el-button type="warning" size="medium" @click="doPublish('2')">
          发布失败
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

  </div>
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
import Children from '@/components/gongdanclass'
import Dialogone from '@/components/fanganclass'
import {getPublishBasic, doPublish,getLogInfo,getApprovalByPublishId} from "@/api/publish";
import {download} from "@/api/file";
export default {
  name: 'publishRelease',
  components: { vabQuill, Children, Dialogone },
  data() {
    return {
      approvalInfos: [],
      tableData: [],
      fileManageInfoList: [],
      logList: [],
      logName: '',
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
        id: '',
        type: '',
        category: '',
        approver: '',
        publisher: '',
        title: '',
        reason: '',
        content: '',
        publish_plan: '',
        test_plan:'',
        tester_id:'',
        enclosure:'',
        add_notice:'',
        publisher_name:'',
        test_result:'',
        test_time:'',
        isPass:'',
        publish_notice:'',
        changeVoList:[],
        publishApprovalInfo:'',
        remarks:'',
      },
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
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
    this.getPublishBasic()
    this.getLogInfo();
    this.getApprovalInfo()
  },
  methods: {
    showView() {
      this.title = '查看详情'
      this.dialogFormVisible = true
    },
    handlegd() {
      this.$refs['children'].showWin()
    },
    handlefa() {
      this.$refs['dialogone'].showWin()
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
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
    async getPublishBasic() {
      this.listLoading = true
      // let row = this.$route.query.row
      let id = this.$route.query.id;
      let row = {};
      row.id = id;
      const {data} = await getPublishBasic(row)
      this.form=data
      //属性信息 附件
      if (data.fileManages) {
        data.fileManages.forEach((x) => {
          this.fileManageInfoList.push({name: x.file_name, url: x.id})
        })
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getLogInfo() {
      this.listLoading = true
      let id = this.$route.query.id;
      let row = {};
      row.id = id;
      const {data} = await getLogInfo(row)
      console.log(data);
      this.logList = data;
      console.log(this.logList)
      this.logName = "日志("+this.logList.length+")";
      console.log(this.logName);
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async doPublish(isPass) {
      this.form.isPass=isPass
      if(this.form.publish_notice){
        this.form.publish_notice='1'
      }else {
        this.form.publish_notice='0'
      }
      this.form.id =  this.$route.query.id;
      const params = new FormData();
      params.append('body', encodeURIComponent(JSON.stringify(this.form)));
      const {data} = await doPublish(params)
      this.$baseMessage(data, 'success')
      this.close();
      setTimeout(() => {
        this.listLoading = false
        // this.$router.push({
        //   path: '/fabu/publishDb'
        // })
      }, 300)
    },
    async getApprovalInfo() {
      this.listLoading = true
      // let row = this.$route.query.row
      let id = this.$route.query.id;
      let row = {};
      row.id = id;
      const {data} = await getApprovalByPublishId(row)
      this.approvalInfos=data
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
    .text-p{
      p{
        margin: 0;
      }
    }
  }
  .content-detail{
    padding-top: 7px;
    padding-left: 0;
  }
}
</style>
