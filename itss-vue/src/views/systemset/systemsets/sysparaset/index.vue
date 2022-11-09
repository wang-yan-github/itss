<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="首选项" name="first">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">系统名称和LOGO</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="系统名称" class="tsitem">
                      <el-input v-model="form.prefs_name"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="主页Logo" class="tsitem">
                      <el-upload
                        class="upload-demo"
                        drag
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :auto-upload="false"
                        :on-change="onChange1"
                        :on-preview="handlePreview1"
                        :on-remove="handleRemove1"
                        :before-remove="beforeRemove1"
                        :file-list="fileList1"
                      >
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">
                          将文件拖到此处，或
                          <em>点击上传</em>
                        </div>
                        <div class="el-upload__tip" slot="tip">
                          建议48x48以下,只能上传jpg/png文件，且不超过500kb
                        </div>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="登录页Logo" class="tsitem">
                      <el-upload
                        class="upload-demo"
                        drag
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :auto-upload="false"
                        :on-change="onChange2"
                        :on-preview="handlePreview2"
                        :on-remove="handleRemove2"
                        :before-remove="beforeRemove2"
                        :file-list="fileList2"
                      >
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">
                          将文件拖到此处，或
                          <em>点击上传</em>
                        </div>
                        <div class="el-upload__tip" slot="tip">
                          建议48x48以下,只能上传jpg/png文件，且不超过500kb
                        </div>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="Favicon" class="tsitem">
                      <el-upload
                        class="upload-demo"
                        drag
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :auto-upload="false"
                        :on-change="onChange3"
                        :on-preview="handlePreview3"
                        :on-remove="handleRemove3"
                        :before-remove="beforeRemove3"
                        :file-list="fileList3"
                      >
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">
                          将文件拖到此处，或
                          <em>点击上传</em>
                        </div>
                        <div class="el-upload__tip" slot="tip">
                          建议48x48以下,只能上传jpg/png文件，且不超过500kb
                        </div>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="登录页二维码" class="tsitem">
                      <el-upload
                        class="upload-demo"
                        drag
                        action="https://jsonplaceholder.typicode.com/posts/"
                        multiple
                        :auto-upload="false"
                        :on-change="onChange4"
                        :on-preview="handlePreview4"
                        :on-remove="handleRemove4"
                        :before-remove="beforeRemove4"
                        :file-list="fileList4"
                      >
                        <i class="el-icon-upload"></i>
                        <div class="el-upload__text">
                          将文件拖到此处，或
                          <em>点击上传</em>
                        </div>
                        <div class="el-upload__tip" slot="tip">
                          建议48x48以下,只能上传jpg/png文件，且不超过500kb
                        </div>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="登录页二维码说明:" class="tsitem">
                      <el-input v-model="form.code_explain"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="登录页二维码说明(En):" class="tsitem">
                      <el-input v-model="form.code_explain_en"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">系统根目录</div>
              <div class="box_content">
                <el-form-item label="系统根目录:" class="tsitem">
                  <el-input v-model="form.directory_root"></el-input>
                </el-form-item>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <!--<el-tab-pane label="通知邮箱配置" name="second">-->
        <!--<el-card class="cardbox" shadow="never" style="border-top: 0">-->
        <!--<div class="box_card">-->
        <!--<div class="box_header">通知邮件发件箱</div>-->
        <!--<div class="box_content">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="邮件开关:">-->
        <!--<el-radio v-model="form.is_use" label="1">是</el-radio>-->
        <!--<el-radio v-model="form.is_use" label="0">否</el-radio>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="发件箱:">-->
        <!--<el-input v-model="form.email"></el-input>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="">-->
        <!--<el-button plain type="primary">-->
        <!--没有可用发件箱？立即设置-->
        <!--</el-button>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--</el-row>-->
        <!--</div>-->
        <!--</div>-->

        <!--<div class="box_card">-->
        <!--<div class="box_header">发件测试</div>-->
        <!--<div class="box_content">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="测试邮箱:">-->
        <!--<el-input v-model="form.email_test"></el-input>-->
        <!--<p class="ts">-->
        <!--注：填写测试邮箱可发送邮件，多个请用;分隔-->
        <!--</p>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="">-->
        <!--<el-button type="primary" icon="el-icon-message">-->
        <!--发送测试邮件-->
        <!--</el-button>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--</el-row>-->
        <!--</div>-->
        <!--</div>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
        <el-tab-pane label="附件配置" name="third">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">附件大小</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="系统配置(M):" prop="system_config">
                      <el-input v-model="form.system_config"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="工单(M):" prop="work_order">
                      <el-input v-model="form.work_order"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="问题(M):" prop="question">
                      <el-input v-model="form.question"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="变更(M):" prop="alteration">
                      <el-input v-model="form.alteration"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="发布(M):" prop="publish">
                      <el-input v-model="form.publish"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="配置-附件(M):" prop="annex">
                      <el-input v-model="form.annex"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="配置-图片(M):" prop="pic">
                      <el-input v-model="form.pic"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="知识库(M):" prop="knowledge_base">
                      <el-input v-model="form.knowledge_base"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="帮助(M):" prop="help">
                      <el-input v-model="form.help"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="邮件(M):" prop="email_size">
                      <el-input v-model="form.email_size"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="即时通信(M):" prop="instant_messaging">
                      <el-input v-model="form.instant_messaging"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">附件上传文件夹</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="知识库:" prop="truename">
                      <el-input v-model="form.folder_knowledge_base"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="工单:" prop="truename">
                      <el-input v-model="form.folder_work_order"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="文档:" prop="truename">
                      <el-input v-model="form.folder_document"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="配置:" prop="truename">
                      <el-input v-model="form.folder_setting"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="问题:" prop="truename">
                      <el-input v-model="form.folder_question"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="变更:" prop="truename">
                      <el-input v-model="form.folder_change"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="即时通信:" prop="truename">
                      <el-input v-model="form.folder_instant_messaging"></el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane label="到期提醒" name="fourth">
          <el-row :gutter="20">
            <el-card class="cardbox" shadow="never" style="border-top: 0">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item
                  label="软件许可证到期前提醒天数:"
                  prop="truename"
                  class="tsitem"
                >
                  <el-input v-model="form.software_license"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item
                  label="客户合同到期提醒天数:"
                  prop="truename"
                  class="tsitem"
                >
                  <el-input v-model="form.customer_contract"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item
                  label="供应商合同到期提醒天数:"
                  prop="truename"
                  class="tsitem"
                >
                  <el-input v-model="form.supplier_contract"></el-input>
                </el-form-item>
              </el-col>
            </el-card>
          </el-row>
        </el-tab-pane>
        <!--<el-tab-pane label="系统接口" name="five">-->
        <!--<el-card shadow="never" style="border-top: 0">-->
        <!--<div class="box_card">-->
        <!--<div class="box_header">用户开单接口</div>-->
        <!--<div class="box_content">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="" class="tsitem">-->
        <!--<el-checkbox-->
        <!--v-model="form.is_auto_user"-->
        <!--true-label="1"-->
        <!--false-label="0"-->
        <!--label="自动创建用户"-->
        <!--&gt;</el-checkbox>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item-->
        <!--label="新创建用户默认服务群组:"-->
        <!--class="tsitem"-->
        <!--&gt;-->
        <!--<el-select v-model="form.default_group" class="w">-->
        <!--<el-option label="请选择"></el-option>-->
        <!--<el-option label="互联宝地维修地"></el-option>-->
        <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="开单接口地址:" class="tsitem">-->
        <!--<el-tag>[GET/POST]</el-tag>-->
        <!--{{form.billing_interface_address}}-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="参数列表:" class="tsitem">-->
        <!--<el-table-->
        <!--:data="tableData"-->
        <!--stripe-->
        <!--border-->
        <!--style="width: 100%"-->
        <!--&gt;-->
        <!--<el-table-column-->
        <!--prop="date"-->
        <!--label="参数名称"-->
        <!--width="180"-->
        <!--&gt;</el-table-column>-->
        <!--<el-table-column-->
        <!--prop="name"-->
        <!--label="是否必填"-->
        <!--width="180"-->
        <!--&gt;</el-table-column>-->
        <!--<el-table-column-->
        <!--prop="address"-->
        <!--label="说明"-->
        <!--&gt;</el-table-column>-->
        <!--</el-table>-->
        <!--<p class="ts">-->
        <!--说明：-->
        <!--系统通过“user”来查询用户，用户的个人信息（包括用户电话，邮箱，座机号码，部门单位和职位）为空时自动从匹配到的用户中获取。-->
        <!--</p>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="返回值:" class="tsitem">-->
        <!--<el-table-->
        <!--:data="tableData"-->
        <!--stripe-->
        <!--border-->
        <!--style="width: 100%"-->
        <!--&gt;-->
        <!--<el-table-column-->
        <!--prop="date"-->
        <!--label="字段名称"-->
        <!--width="180"-->
        <!--&gt;</el-table-column>-->

        <!--<el-table-column-->
        <!--prop="address"-->
        <!--label="说明"-->
        <!--&gt;</el-table-column>-->
        <!--</el-table>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--</el-row>-->
        <!--</div>-->
        <!--</div>-->
        <!--<div class="box_card">-->
        <!--<div class="box_header">自动跳转</div>-->
        <!--<div class="box_content">-->
        <!--<el-alert :closable="false" type="success">-->
        <!--{{form.illustrate}}-->
        <!--</el-alert>-->
        <!--</div>-->
        <!--</div>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
        <el-tab-pane label="人力地图" name="six">
          <el-card shadow="never" style="border-top: 0">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="地图默认显示级别:" class="tsitem">
                  <el-select v-model="form.map_show" class="w">
                    <el-option label="请选择"></el-option>
                    <el-option label="500" value="500"></el-option>
                    <el-option label="1000" value="1000"></el-option>
                    <el-option label="1500" value="1500"></el-option>
                    <el-option label="2000" value="2000"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
        </el-tab-pane>
        <!--<el-tab-pane label="在线技术支持（Beta）" name="senven">-->
        <!--<el-card shadow="never" style="border-top: 0">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="开启:" class="tsitem">-->
        <!--<el-select v-model="form.support_is_use" class="w">-->
        <!--<el-option label="停用"  value="0"></el-option>-->
        <!--<el-option label="使用" value="1"></el-option>-->
        <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="XT800专属聊天链接:" class="tsitem">-->
        <!--<el-input v-model="form.chat_link"></el-input>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="服务端安装程序:">-->
        <!--<el-upload-->
        <!--class="upload-button"-->
        <!--action="https://jsonplaceholder.typicode.com/posts/"-->
        <!--:on-preview="handlePreview"-->
        <!--:on-remove="handleRemove"-->
        <!--:before-remove="beforeRemove"-->
        <!--multiple-->
        <!--:limit="3"-->
        <!--:on-exceed="handleExceed"-->
        <!--:file-list="fileList"-->
        <!--&gt;-->
        <!--<el-button size="small" type="primary">点击上传</el-button>-->
        <!--<div slot="tip" class="el-upload__tip">-->
        <!--只能上传jpg/png文件，且不超过500kb-->
        <!--</div>-->
        <!--</el-upload>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="用户端安装程序:">-->
        <!--<el-upload-->
        <!--class="upload-button"-->
        <!--action="https://jsonplaceholder.typicode.com/posts/"-->
        <!--:on-preview="handlePreview"-->
        <!--:on-remove="handleRemove"-->
        <!--:before-remove="beforeRemove"-->
        <!--multiple-->
        <!--:limit="3"-->
        <!--:on-exceed="handleExceed"-->
        <!--:file-list="fileList"-->
        <!--&gt;-->
        <!--<el-button size="small" type="primary">点击上传</el-button>-->
        <!--<div slot="tip" class="el-upload__tip">-->
        <!--只能上传jpg/png文件，且不超过500kb-->
        <!--</div>-->
        <!--</el-upload>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--</el-row>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
        <!--<el-tab-pane label="呼叫中心电话条配置" name="eight">-->
        <!--<el-card shadow="never" style="border-top: 0">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="开启:" class="tsitem">-->
        <!--<el-select v-model="form.strip_is_use" class="w">-->
        <!--<el-option label="停用" value="0"></el-option>-->
        <!--<el-option label="使用" value="1"></el-option>-->
        <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="Proxy url:" class="tsitem">-->
        <!--<el-input v-model="form.proxy_url"></el-input>-->
        <!--&lt;!&ndash;<p class="ts"><el-link>参考这里</el-link></p>&ndash;&gt;-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!---->
        <!--</el-row>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
        <!--<el-tab-pane label="登陆验证码" name="night">-->
        <!--<el-card shadow="never" style="border-top: 0">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="验证码策略:" class="tsitem">-->
        <!--<el-select v-model="form.strategy" class="w">-->
        <!--<el-option label="是"></el-option>-->
        <!--<el-option label="否"></el-option>-->
        <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!---->
        <!---->
        <!--</el-row>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
        <!--<el-tab-pane label="即时通信" name="ten">-->
        <!--<el-card shadow="never" style="border-top: 0">-->
        <!--<el-row :gutter="20">-->
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="开启:" class="tsitem">-->
        <!--<el-select v-model="form.message_is_use" class="w">-->
        <!--<el-option label="停用" value="0"></el-option>-->
        <!--<el-option label="使用" value="1"></el-option>-->
        <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!---->
        <!---->
        <!--</el-row>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
      </el-tabs>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {getOneInfo, updateOne} from '@/api/sysParameter'
  import {download} from '@/api/file'

  export default {
    name: 'sysparaset',
    components: {},
    data() {
      return {
        fileList: [],
        fileList1: [],
        fileList2: [],
        fileList3: [],
        fileList4: [],
        tableData: [
          // {
          //   date: '2016-05-02',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1518 弄',
          // },
          // {
          //   date: '2016-05-04',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1517 弄',
          // },
          // {
          //   date: '2016-05-01',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1519 弄',
          // },
          // {
          //   date: '2016-05-03',
          //   name: '王小虎',
          //   address: '上海市普陀区金沙江路 1516 弄',
          // },
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
          prefs_name: '',
          principal_sheet_pic: '',
          land_logo_pic: '',
          favicon_pic: '',
          land_code_pic: '',
          code_explain: '',
          code_explain_en: '',
          directory_root: '',
          is_use: '',
          email: '',
          email_test: '',
          system_config: '',
          work_order: '',
          question: '',
          alteration: '',
          publish: '',
          annex: '',
          pic: '',
          knowledge_base: '',
          help: '',
          email_size: '',
          instant_messaging: '',
          folder_knowledge_base: '',
          folder_work_order: '',
          folder_document: '',
          folder_setting: '',
          folder_question: '',
          folder_change: '',
          folder_instant_messaging: '',
          software_license: '',
          customer_contract: '',
          supplier_contract: '',
          is_auto_user: '',
          default_group: '',
          billing_interface_address: '',
          parameter_tables: '',
          result: '',
          illustrate: '',
          map_show: '',
          chat_link: '',
          server: '',
          client: '',
          proxy_url: '',
          strategy: '',
        },
        rules: {
          // username: [
          //   { required: true, trigger: 'blur', message: '请输入用户名' },
          // ],
          // truename: [{ required: true, trigger: 'blur', message: '请输入姓名' }],
          // password: [{ required: true, trigger: 'blur', message: '请输入密码' }],
          // email: [{ required: true, trigger: 'blur', message: '请输入邮箱' }],
          // permissions: [
          //   { required: true, trigger: 'blur', message: '请选择权限' },
          // ],
        },
      }
    },
    created() {
      this.fetchData()
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

            if (this.fileList1.length > 1) {
              this.$baseMessage('只能上传一个主页LOGO', 'error')
              return false;
            }
            if (this.fileList2.length > 1) {
              this.$baseMessage('只能上传一个登录页LOGO', 'error')
              return false;
            }
            if (this.fileList3.length > 1) {
              this.$baseMessage('只能上传一个Favicon', 'error')
              return false;
            }
            if (this.fileList4.length > 1) {
              this.$baseMessage('只能上传一个登录页二维码', 'error')
              return false;
            }

            const params = new FormData();
            if (this.fileList1.length > 0) {
              const f = this.fileList1[0]
              if (f.status == 'ready') {
                params.append('file1', f.raw);
              }
            }

            if (this.fileList2.length > 0) {
              const a = this.fileList2[0]
              if (a.status == 'ready') {
                params.append('file2', a.raw);
              }
            }

            if (this.fileList3.length > 0) {
              const b = this.fileList3[0]
              if (b.status == 'ready') {
                params.append('file3', b.raw);
              }
            }


            if (this.fileList4.length > 0) {
              const c = this.fileList4[0]
              if (c.status == 'ready') {
                params.append('file4', c.raw);
              }
            }

            params.append('body', JSON.stringify(this.form))


            const {msg} = await updateOne(params)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')

            this.closeall()
          } else {
            return false
          }
        })
      },
      async fetchData() {
        const {data} = await getOneInfo()
        console.log(data);
        if (null != data){
          this.form = data;
          this.form.default_group = this.form.default_group + "";
          if (data.fileManageList1) {
            data.fileManageList1.forEach((x) => {
              this.fileList1.push({name: x.file_name, url: x.id})
            })
          }

          if (data.fileManageList2) {
            data.fileManageList2.forEach((x) => {
              this.fileList2.push({name: x.file_name, url: x.id})
            })
          }
          if (data.fileManageList3) {
            data.fileManageList3.forEach((x) => {
              this.fileList3.push({name: x.file_name, url: x.id})
            })
          }
          if (data.fileManageList4) {
            data.fileManageList4.forEach((x) => {
              this.fileList4.push({name: x.file_name, url: x.id})
            })
          }

        }

        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },

      //属性 附件上传 选择文件
      onChange1(file, fileList) {
        this.fileList1 = fileList;

      },
      handlePreview1(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', file.name)
          document.body.appendChild(link)
          link.click()
        })
      },
      handleRemove1(file, fileList) {
        this.fileList1 = fileList
      },
      beforeRemove1(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },


      //属性 附件上传 选择文件
      onChange2(file, fileList) {
        this.fileList2 = fileList;

      },
      handlePreview2(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', file.name)
          document.body.appendChild(link)
          link.click()
        })
      },
      handleRemove2(file, fileList) {
        this.fileList2 = fileList
      },
      beforeRemove2(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },


      //属性 附件上传 选择文件
      onChange3(file, fileList) {
        this.fileList3 = fileList;

      },
      handlePreview3(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', file.name)
          document.body.appendChild(link)
          link.click()
        })
      },
      handleRemove3(file, fileList) {
        this.fileList3 = fileList
      },
      beforeRemove3(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },


      //属性 附件上传 选择文件
      onChange4(file, fileList) {
        this.fileList4 = fileList;

      },
      handlePreview4(file) {
        download(file.url).then((res) => {
          const url = window.URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', file.name)
          document.body.appendChild(link)
          link.click()
        })
      },
      handleRemove4(file, fileList) {
        this.fileList4 = fileList
      },
      beforeRemove4(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
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
        height: calc(100vh - 320px) !important;
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
