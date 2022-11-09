<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
      <template>
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="开单" name="first">

            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <div class="box_header">工程师开单通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio" label="1">发送</el-radio>
                          <el-radio v-model="radio" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" placeholder="请输入">
                        <el-input v-model.trim="form.username" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor1" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio1" label="1">发送</el-radio>
                          <el-radio v-model="radio1" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" placeholder="请输入">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="抄送人">
                        <p class="filltext">协查人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 20px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 20px; color: #E6A23C;">
                        工程师开单后将发开单通知给用户（工单中保存的用户邮箱）</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">用户自助服务通知工程师</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio3" label="1">发送</el-radio>
                          <el-radio v-model="radio3" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" placeholder="请输入">
                        <el-input v-model.trim="form.username" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor2" v-model="form.content2" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio4" label="1">发送</el-radio>
                          <el-radio v-model="radio4" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" placeholder="请输入">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人" placeholder="请输入">
                        <template>
                          <el-radio v-model="radio5" label="1">服务群组工程师</el-radio>
                          <el-radio v-model="radio5" label="2">服务群组负责人</el-radio>
                          <el-radio v-model="radio5" label="3">服务群组工程师和负责人</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:
                      </p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        用户自助开单或接受邮件自动开单后根据上述收件人选择有如下规则： </p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        收件人选择服务群组工程师：如果指派了处理人，则发通知给处理人；处理人为空，则发通知给服务群组内所有工程师</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        收件人选择服务群组负责人：发通知给服务群组负责人</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 20px; color: #E6A23C;">
                        收件人选择服务群组工程师和负责人：如果指派了处理人，则发通知给处理人和负责人；处理人为空，则发通知给服务群组内所有工程师和负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <div class="box_card">
                <div class="box_header">用户自助服务通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" placeholder="请输入">
                        <el-input v-model.trim="form.username" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor3" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        用户自助开单或接受邮件自动开单后，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>
          </el-tab-pane>
          <el-tab-pane label="指派" name="second">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知工程师</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" placeholder="请输入">
                        <el-input v-model.trim="form.username" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor4" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人或服务群组内所有工程师</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工单被指派后，发通知给相应的人，规则如下：</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        如果指派到具体的人，则发通知给被指派的工程师</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        如果指派到服务群组，则发通知给组内所有工程师</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="响应" name="third">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" placeholder="请输入">
                        <el-input v-model.trim="form.username" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor5" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师响应工单后，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="转单" name="fourth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        转单后，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">通知工程师</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人或服务群组内工程师</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工单被转单后，发通知给相应的人，规则如下：</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        如果转单到具体的人，则发通知给被转单的工程师</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        如果转单到服务群组，则发通知给组内所有工程师</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="处理完成" name="fifth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知工程师</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio7" label="1">手动</el-radio>
                          <el-radio v-model="radio7" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">开单人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师处理完成工单后，工单自动转回开单人，发通知给开单人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="关单" name="sixth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio7" label="1">手动</el-radio>
                          <el-radio v-model="radio7" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工单关闭后，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="满意度调查" name="seventh">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" placeholder="请输入">
                        <el-input v-model.trim="form.username" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor6" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="满意度调查消息通知用户内容" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio7" label="1">手动</el-radio>
                          <el-radio v-model="radio7" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 20px; color: #E6A23C;">
                        单关闭后，发通知给用户</p>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-alert type="info" title="配置参数" :closable="false" class="setdata">
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          非常满意: (5)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          满意: (4)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          一般: (3)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          不满意: (2)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          非常不满意: (1)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          邮件满意度调查链接开始标签: (AccessUrlAs)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          邮件满意度调查链接结束标签: (/AccessUrlAs)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          微信满意度调查链接开始标签: (surveyAs)
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                          微信满意度调查链接结束标签: (/surveyAs)
                        </el-col>
                      </el-alert>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="暂停/恢复" name="eighth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">暂停工单通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师暂停工单后，发通知给用户</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">恢复工单</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师恢复工单后，发通知给用户</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>
          </el-tab-pane>
          <el-tab-pane label="撤销" name="ninth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">工程师撤销通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师撤销工单后，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">用户撤销通知工程师</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="通知方式">
                        <template>
                          <el-radio v-model="radio2" label="1">手动</el-radio>
                          <el-radio v-model="radio2" label="2">自动</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人或服务群组内所有工程师</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        用户撤销工单后，发通知给处理人，处理人为空则发给服务群组内所有工程师</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="SLA超时" name="tenth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">工程师撤销通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="还有" placeholder="请输入">
                        <el-input v-model.trim="form.remakes" autocomplete="off">
                        </el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
                      <p style="margin: 0 0 0 -10px;line-height: 32px;">
                        分钟超时提醒工程师（设置为-1即为不检查超时不发送提醒，不能为0）</p>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="抄送人">
                        <p class="filltext">服务群组负责人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        某工单还有x分钟超时（包括响应超时和解决超时），发通知给处理人，处理人为空则发通知给服务群组内所有工程师</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">SLA超时提醒</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="抄送人">
                        <p class="filltext">服务群组负责人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        某工单已超时（包括响应超时和解决超时），发通知给处理人，处理人为空则发通知给服务群组内所有工程师</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">OLA超时通知</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人或开单人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="抄送人">
                        <p class="filltext">服务群组负责人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        某工单已超时（服务群组配置的ola超时分钟数），发通知给处理人，处理人为空则发通知给服务群组内所有工程师 </p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="即将服务" name="eleventh">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">即将服务</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="下班时间" placeholder="请输入">
                        <el-time-picker v-model="value1" placeholder="任意时间点">
                        </el-time-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师关单后，发通知给剩余待处理工单中，按时间先后顺序排列第二的工单的用户，通知他即将为他服务。</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        若配置了下班时间，当预计下班前无法给下一个用户服务时，系统将不发邮件。</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="协查" name="twelfth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知协查人</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="协查通知内容" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">协查人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        添加工单或维护协查人信息后发通知给协查人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">SLA超时提醒</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="抄送人">
                        <p class="filltext">服务群组负责人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        某工单已超时（包括响应超时和解决超时），发通知给处理人，处理人为空则发通知给服务群组内所有工程师</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">OLA超时通知</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人或开单人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="抄送人">
                        <p class="filltext">服务群组负责人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        某工单已超时（服务群组配置的ola超时分钟数），发通知给处理人，处理人为空则发通知给服务群组内所有工程师 </p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        抄送服务群组负责人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="备注" name="thirteenth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知处理人</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="备注通知内容" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        非当前处理人添加备注时，发通知给处理人，没有处理人则不发送，如果勾选了“备注通知用户”，则发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor7" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="备注通知内容" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        添加备注勾选“通知用户”时，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="重开" name="fourteenth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">用户重开通知工程师</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio7" label="1">发送</el-radio>
                          <el-radio v-model="radio7" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">处理人或开单人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        用户重开工单后，发通知给处理人，处理人为空则发通知给开单人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="box_card">
                <div class="box_header">工程师重开通知用户</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信消息" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">用户</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工程师重开工单后，发通知给用户</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
          <el-tab-pane label="超时自动升级" name="fifteenth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_header">通知升级人</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="邮件通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="内容">
                        <quill-editor ref="myQuillEditor8" v-model="form.content" :min-height="200"
                                   :options="optionsText">
                        </quill-editor>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="额外抄送" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                        <p
                          style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
                          抄送仅限于发送邮件时，多个请用半角分号分隔</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="微信通知">
                        <template>
                          <el-radio v-model="radio6" label="1">发送</el-radio>
                          <el-radio v-model="radio6" label="2">不发送</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="超时升级通知内容" prop="">
                        <el-input v-model.trim="form.remakes" autocomplete="off" type="textarea"
                                  :rows="2"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="收件人">
                        <p class="filltext">超时升级人</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        注:</p>
                      <p
                        style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                        工单超时自动升级后通知超时升级人</p>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <el-alert type="success" title="配置参数" :closable="false" class="setdata">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    标题: (Title)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    描述: (ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    编号: (User)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    用户ID: (UserName)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    邮箱: (Email)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    电话: (Phone)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    座机: (Landline)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    部门: (Organization)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    是否是VIP: (VIP)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    职位: (Position)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    位置: (Address)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单人: (Creator)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    开单时间: (CreationTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单类别: (Type)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    服务群组: (ServiceGroup)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    处理人: (Agent)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派人: (AssginPerson)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    指派时间: (AssginTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    响应时间: (ResponseTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决工程师: (SolveEngineer)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决时间: (ResolvedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方式: (ResloveType)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案: (Solution)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    方案分类: (CloseCode)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关单时间: (ClosedTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    预计解决时间: (ExpectDealTime)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    状态: (Status)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注: (TicketNote)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    备注人: (TicketNoteUser)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    查看URL: (As)(/As)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    重开URL: (reopenAs)(/reopenAs)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    关联的资产ID: (CI_ID)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    费用: (Expense)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    工单信息附件: (InfoAttDownloadURL)
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                    解决方案附件: (DealAttDownloadURL)
                  </el-col>
                </el-row>
              </el-alert>
            </el-card>

          </el-tab-pane>
        </el-tabs>
      </template>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

  </div>
</template>

<script>
  import {
    getList,
    doDelete,
    doEdit
  } from '@/api/userManagement'
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'notice',
    components: {
      vabQuill,
    },
    data() {
      return {
        activeName: 'first',
        elementLoadingText: '正在加载...',
        options: [],
        value: [],
        list: [],
        loading: false,
        form: {
          username: 'admin',
          bumen: '',
          isvip: '',
          content: '',
          content2: '',
        },
        radio: '1',
        radio1: '1',
        radio2: '1',
        radio3: '1',
        radio4: '1',
        radio5: '1',
        radio6: '1',
        radio7: '1',
        value1: '',
        rules: {
          // username: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请输入名称'
          // },],
        },
        optionsText: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{
                header: [1, 2, 3, 4, 5, 6, false]
              }],
              [{
                size: ['small', false, 'large', 'huge']
              }],
              [{
                color: []
              }, {
                background: []
              }],
              ['blockquote', 'code-block'],
              [{
                list: 'ordered'
              }, {
                list: 'bullet'
              }],
              [{
                script: 'sub'
              }, {
                script: 'super'
              }],
              [{
                indent: '-1'
              }, {
                indent: '+1'
              }],
              [{
                align: []
              }],
              [{
                direction: 'rtl'
              }],
              [{
                font: []
              }],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
      }
    },
    created() {
      // this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
      this.quillToTop()
    },
    methods: {
      quillToTop(){
        this.$refs.myQuillEditor1.quill.enable(false);
        this.$refs.myQuillEditor2.quill.enable(false);
        this.$refs.myQuillEditor3.quill.enable(false);
        this.$refs.myQuillEditor4.quill.enable(false);
        this.$refs.myQuillEditor5.quill.enable(false);
        this.$refs.myQuillEditor6.quill.enable(false);
        this.$refs.myQuillEditor7.quill.enable(false);
        this.$refs.myQuillEditor8.quill.enable(false);

        setTimeout(() => {
          this.$refs.myQuillEditor1.quill.enable(true);
          this.$refs.myQuillEditor2.quill.enable(true);
          this.$refs.myQuillEditor3.quill.enable(true);
          this.$refs.myQuillEditor4.quill.enable(true);
          this.$refs.myQuillEditor5.quill.enable(true);
          this.$refs.myQuillEditor6.quill.enable(true);
          this.$refs.myQuillEditor7.quill.enable(true);
          this.$refs.myQuillEditor8.quill.enable(true);
        },2000);
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.$baseMessage("成功", 'success')
        this.closeall()

        // this.$refs['form'].validate(async (valid) => {
        // 	if (valid) {
        // 		const {
        // 			msg
        // 		} = await doEdit(this.form)
        // 		this.$baseMessage(msg, 'success')
        // 		// this.$emit('fetch-data')
        // 		// this.fetchData()
        //
        // 		this.closeall()
        // 	} else {
        // 		return false
        // 	}
        // })
      },
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
  ::v-deep {
    .ql-container {
      min-height: 200px;
    }
  }

  .tips {
    color: #E6A23C;
  }

  .boxtable .btn-box {
    margin-top: 10px;
  }

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

    .filltext {
      margin: 0;
    }

    .setdata .el-alert__content {
      width: 100%;
    }

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
  }
</style>
