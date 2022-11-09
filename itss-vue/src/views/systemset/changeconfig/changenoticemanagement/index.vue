<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" label-width="80px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="待审批通知" name="first">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知审批人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form.notification_method" label="3">不发送</el-radio>
                      <p class="ts">变更添加、修改、审批后，发通知给下一任审批人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="审批拒绝通知" name="second">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知创建人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form2.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form2.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form2.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form2.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form2.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form2.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form2.notification_method" label="3">不发送</el-radio>
                      <p class="ts">测试后，发通知给发布人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="制定方案通知" name="third">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知方案拟定人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form3.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form3.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form3.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form3.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form3.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form3.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form3.notification_method" label="3">不发送</el-radio>
                      <p class="ts">变更单审批通过后，发邮件给方案拟定人，优先发送微信消息，微信消息发送失败后发送邮件。</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">通知转派的方案拟定人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form3.title_reassignment"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form3.content_reassignment"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form3.extra_cc_reassignment"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form3.wechat_message_reassignment"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form3.notification_method_reassignment" label="1">手动</el-radio>
                      <el-radio v-model="form3.notification_method_reassignment" label="2">自动</el-radio>
                      <el-radio v-model="form3.notification_method_reassignment" label="3">不发送</el-radio>
                      <p class="ts">变更被转单后，发通知给转派的方案拟定人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="方案待审核通知" name="fourth">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知方案审核人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form4.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form4.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form4.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form4.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form4.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form4.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form4.notification_method" label="3">不发送</el-radio>
                      <p class="ts">方案拟定完成，发通知给方案审核人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="方案审核拒绝通知" name="five">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知方案拟定人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form5.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form5.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form5.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form5.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form5.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form5.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form5.notification_method" label="3">不发送</el-radio>
                      <p class="ts">方案审核拒绝后，发通知给方案拟定人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="待实施通知" name="six">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知实施人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form6.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form6.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form6.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form6.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form6.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form6.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form6.notification_method" label="3">不发送</el-radio>
                      <p class="ts">方案审核通过后，发通知给实施人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="待评审通知" name="senven">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知实施后评审人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form7.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form7.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form7.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form7.extra_cc"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form7.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form7.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form7.notification_method" label="3">不发送</el-radio>
                      <p class="ts">实施完成后，发通知给方案审核人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="实施失败通知" name="eight">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知创建人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form8.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form8.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form8.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form8.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form8.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form8.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form8.notification_method" label="3">不发送</el-radio>
                      <p class="ts">实施完成后，发通知给方案审核人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="关闭通知" name="nine">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知创建人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form9.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form9.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form9.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form9.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form9.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form9.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form9.notification_method" label="3">不发送</el-radio>
                      <p class="ts">实施完成后，发通知给方案审核人</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="撤消" name="ten">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知解决人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="form10.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="内容:">
                      <vab-quill
                        v-model="form10.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="额外抄送:">
                      <el-input v-model="form10.extra_cc"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="form10.wechat_message"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="form10.notification_method" label="1">手动</el-radio>
                      <el-radio v-model="form10.notification_method" label="2">自动</el-radio>
                      <el-radio v-model="form10.notification_method" label="3">不发送</el-radio>
                      <p class="ts">问题超时后，发通知给问解决人，没有解决人发通知给添加人。</p>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">配置参数</div>
              <div class="box_content">
                <el-alert :closable="false" type="success">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布单号:">
                        (releaseID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="状态:">
                        (Status)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类型:">
                        (ReleaseType)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建人:">
                        (Creator)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="创建时间:">
                        (CreationTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="申请发布理由:" class="custemitem">
                        (Reason)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布内容:">
                        (Content)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	发布方案:">
                        (ReleaseProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试方案:">
                        (TestProgram)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审人:">
                        (Reviewer)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="评审时间:">
                        (ReviewTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="	评审意见:">
                        (ReviewComments)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试人:">
                        (Tester)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试结果:">
                        (TestResult)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="测试时间:">
                        (TestTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布人:">
                        (Releaser)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布时间:">
                        (ReleaseTime)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布备注:">
                        (ReleaseNote)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="查看URL:">
                        (As)(/As)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="编号:">
                        (ID)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="发布类别:">
                        (ReleaseCategory)
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                      <el-form-item label="标题:">
                        (Title)
                      </el-form-item>
                    </el-col>

                  </el-row>
                </el-alert>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
      </el-tabs>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {getOneByType, updateConfig} from '@/api/sourceSet'
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'changenotice',
    components: {vabQuill},
    data() {
      return {
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
        borderColor: '#dcdfe6',
        fileList: [],
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
        activeName: 'first',
        input2: '4',
        radio1: '',
        radio2: '1',
        reverse: true,
        activities: [],
        formArr: [{value1: ''}],
        checked: false,
        checked1: false,
        value: [],
        form: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '1',
        },
        form2: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '2',
        },
        form3: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '3',
          title_reassignment: '',
          content_reassignment: '',
          extra_cc_reassignment: '',
          wechat_message_reassignment: '',
          notification_method_reassignment: '',
        },
        form4: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '4',
        },
        form5: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '5',
        },
        form6: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '6',
        },
        form7: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '7',
        },
        form8: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '8',
        },
        form9: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '9',
        },
        form10: {
          id: '',
          title: '',
          content: '',
          extra_cc: '',
          wechat_message: '',
          notification_method: '',
          type: '10',
        },
        form11: {
          list: [],
        }


        // rules: {
        //   username: [
        //     { required: true, trigger: 'blur', message: '请输入用户名' },
        //   ],
        //   truename: [{ required: true, trigger: 'blur', message: '请输入姓名' }],
        //   password: [{ required: true, trigger: 'blur', message: '请输入密码' }],
        //   email: [{ required: true, trigger: 'blur', message: '请输入邮箱' }],
        //   permissions: [
        //     { required: true, trigger: 'blur', message: '请选择权限' },
        //   ],
        // },
      }
    },
    created() {
      setTimeout(() => {
        this.ShowInfo()
        this.ShowInfo2()
        this.ShowInfo3()
        this.ShowInfo4()
        this.ShowInfo5()
        this.ShowInfo6()
        this.ShowInfo7()
        this.ShowInfo8()
        this.ShowInfo9()
        this.ShowInfo10()
      }, 300)
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      handleRemove(file, fileList) {
        console.log(file, fileList)
      },
      handlePreview(file) {
        console.log(file)
      },
      handleExceed(files, fileList) {
        this.$message.warning(
          `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
            } 个文件`
        )
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`)
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.form11.list = [this.form, this.form2, this.form3, this.form4, this.form5, this.form6, this.form7, this.form8, this.form9, this.form10]
            console.log(this.form11)
            const {msg} = await updateConfig(this.form11)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')

            this.closeall()
          } else {
            return false
          }
        })
      },
      async ShowInfo() {
        const {data} = await getOneByType(this.form)
        this.form = data
        console.log(this.form)
      },
      async ShowInfo2() {
        const {data} = await getOneByType(this.form2)
        this.form2 = data
        console.log(this.form2)
      },
      async ShowInfo3() {
        const {data} = await getOneByType(this.form3)
        this.form3 = data
        console.log(this.form3)
      },
      async ShowInfo4() {
        const {data} = await getOneByType(this.form4)
        this.form4 = data
        console.log(this.form4)
      },
      async ShowInfo5() {
        const {data} = await getOneByType(this.form5)
        this.form5 = data
        console.log(this.form5)
      },
      async ShowInfo6() {
        const {data} = await getOneByType(this.form6)
        this.form6 = data
        console.log(this.form6)
      },
      async ShowInfo7() {
        const {data} = await getOneByType(this.form7)
        this.form7 = data
        console.log(this.form7)
      },
      async ShowInfo8() {
        const {data} = await getOneByType(this.form8)
        this.form8 = data
        console.log(this.form8)
      },
      async ShowInfo9() {
        const {data} = await getOneByType(this.form9)
        this.form9 = data
        console.log(this.form9)
      },
      async ShowInfo10() {
        const {data} = await getOneByType(this.form10)
        this.form10 = data
        console.log(this.form10)
      },
    },
  }
</script>
<style lang="scss" scoped>
  .main-container {
    ::v-deep {
      // .el-alert__content p{margin:0;}
      .el-tabs__header {
        margin-bottom: 0 !important;
      }

      .el-tabs__item.is-active {
        background: #fff !important;
      }

      .el-card__body {
        height: calc(100vh - 290px) !important;
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
