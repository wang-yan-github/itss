<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="待评审通知" name="first">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知评审人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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
        <el-tab-pane label="评审拒绝通知" name="second">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知添加人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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
        <el-tab-pane label="待测试通知" name="third">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知测试人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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
        <el-tab-pane label="待发布通知" name="fourth">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知发布人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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
        <el-tab-pane label="发布完成通知" name="five">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知添加人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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
        <el-tab-pane label="发布失败通知" name="six">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知添加人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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
        <el-tab-pane label="撤消" name="senven">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">通知解决人</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:">
                      <el-input v-model="input2"></el-input>
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
                      <el-input v-model="input2"></el-input>
                      <p class="ts">
                        抄送仅限于发送邮件时，多个请用半角分号分隔
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="微信消息:">
                      <el-input v-model="input2"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通知方式:">
                      <el-radio v-model="input2" label="1">手动</el-radio>
                      <el-radio v-model="input2" label="2">自动</el-radio>
                      <el-radio v-model="input2" label="3">不发送</el-radio>
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

      </el-tabs>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'publishnotice',
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
          username: 'admin',
          truename: '管理员',
          password: '',
          email: '',
          permissions: [],
          bumen: '',
          isvip: '',
          content: '',
        },
        rules: {
          username: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
          truename: [{required: true, trigger: 'blur', message: '请输入姓名'}],
          password: [{required: true, trigger: 'blur', message: '请输入密码'}],
          email: [{required: true, trigger: 'blur', message: '请输入邮箱'}],
          permissions: [
            {required: true, trigger: 'blur', message: '请选择权限'},
          ],
        },
      }
    },
    created() {
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
            const {msg} = await doEdit(this.form)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')

            this.closeall()
          } else {
            return false
          }
        })
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
        /*height: 620px !important;*/
        height: calc(100vh - 330px);
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
