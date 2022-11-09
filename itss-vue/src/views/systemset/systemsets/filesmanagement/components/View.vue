<template>
  <div class="main-container pb80">
    <el-form ref="form" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <div class="box_card">
                  <div class="box_header">工单信息</div>
                  <div class="box_content">
                    <el-form-item label="标题:" class="tsitem">
                      标题标题标题标题标题标题
                    </el-form-item>
                    <el-form-item label="描述:" class="tsitem">
                      <el-image :src="url"></el-image>
                      <p>对哦，对我我能用我能用，我现在正在用。</p>
                    </el-form-item>
                    <el-form-item label="附件:" class="tsitem">
                      <div class="bghui">
                        <el-image
                          style="width: 50px; height: 50px"
                          :src="url"
                          :preview-src-list="srcList"
                        ></el-image>
                        <div class="r">
                          <p class="p1" style="margin: 0; color: #333">
                            3232333456891983223.jpg
                          </p>
                          <p class="p2" style="margin: 0; color: #999">
                            500kb 3-15 10:00 致敬
                          </p>
                        </div>
                      </div>
                    </el-form-item>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">处理信息</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="开单人:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="开单时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="指派人:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="指派时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应人:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决工程师:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="关单人:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="关单时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="手动暂停总时间:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="暂停总时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="上次暂停时间:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="上次更新时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="预计解决时间:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决方式:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="方案分类:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="费用:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="解决方案:" class="tsitem">
                          <el-image :src="url"></el-image>
                          <p>对哦，对我我能用我能用，我现在正在用。</p>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="附件:" class="tsitem">
                          <div class="bghui">
                            <el-image
                              style="width: 50px; height: 50px"
                              :src="url"
                              :preview-src-list="srcList"
                            ></el-image>
                            <div class="r">
                              <p class="p1" style="margin: 0; color: #333">
                                3232333456891983223.jpg
                              </p>
                              <p class="p2" style="margin: 0; color: #999">
                                500kb 3-15 10:00 致敬
                              </p>
                            </div>
                          </div>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">SLA</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应SLA:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="符合响应SLA:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应时限:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应总时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="响应超时时间:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决SLA:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="符合解决SLA:" class="tsitem">
                          未指派
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决时限:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决总时间:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="解决超时时间数:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">处理时长</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                        <el-alert
                          title="服务群组"
                          type="info"
                          :closable="false"
                        >
                          <el-form-item
                            label="服务台"
                            class="tsitem"
                            style="margin-bottom: 0"
                          >
                            0.18小时
                          </el-form-item>
                        </el-alert>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                        <el-alert title="服务台" type="info" :closable="false">
                          <el-form-item
                            label="迟鹏"
                            class="tsitem"
                            style="margin-bottom: 0"
                          >
                            0.18小时
                          </el-form-item>
                        </el-alert>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">满意度</div>
                  <div class="box_content">
                    <el-row :gutter="20">
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="满意度评价状态:" class="tsitem">
                          致敬
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="满意度调查时间:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        <el-form-item label="用户满意度:" class="tsitem">
                          <el-rate
                            v-model="value"
                            disabled
                            show-score
                            text-color="#ff9900"
                            score-template="{value}"
                          ></el-rate>
                        </el-form-item>
                      </el-col>
                      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="用户建议:" class="tsitem">
                          2022-03-15 10:29
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <div class="box_card">
                  <div class="box_header">备注</div>
                  <div class="box_content">
                    <el-form-item label="">
                      <el-button type="primary">添加备注</el-button>
                    </el-form-item>
                    <el-form-item label="">
                      <el-checkbox
                        v-model="checked"
                        label="添加备注通知用户"
                      ></el-checkbox>
                    </el-form-item>
                    <el-form-item label="备注">
                      <vab-quill
                        v-model="form.content"
                        :min-height="200"
                        :options="options"
                      ></vab-quill>
                    </el-form-item>
                  </div>
                </div>
              </el-tab-pane>
              <el-tab-pane label="工单日志(5)" name="second">
                <div class="box_card">
                  <div class="box_content">
                    <el-timeline>
                      <el-timeline-item
                        timestamp="03-15 10:29:05"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
                        <el-card>
                          <h4>自助报障</h4>
                          <p>admin</p>
                          <p>
                            服务台--致敬自助报障，报障类型
                            办公设备，指派至服务群组 服务台
                          </p>
                        </el-card>
                      </el-timeline-item>
                      <el-timeline-item
                        timestamp="2018/4/3"
                        placement="top"
                        color="#0bbd87"
                        size="large"
                      >
                        <el-card>
                          <h4>用户修改</h4>
                          <p>admin</p>
                          <p>123</p>
                        </el-card>
                      </el-timeline-item>
                      <el-timeline-item
                        timestamp="2018/4/2"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
                        <el-card>
                          <h4>响应</h4>
                          <p>admin</p>
                          <p>服务台-迟鹏-0.17小时</p>
                        </el-card>
                      </el-timeline-item>
                      <el-timeline-item
                        timestamp="2018/4/2"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
                        <el-card>
                          <h4>关单</h4>
                          <p>admin</p>
                          <p>服务台-迟鹏-0.17小时</p>
                        </el-card>
                      </el-timeline-item>
                      <el-timeline-item
                        timestamp="2018/4/2"
                        placement="top"
                        size="large"
                        color="#0bbd87"
                      >
                        <el-card>
                          <h4>用户确认已解决</h4>
                          <p>admin</p>
                          <p>服务台-迟鹏-0.17小时</p>
                        </el-card>
                      </el-timeline-item>
                      <el-timeline-item timestamp="2018/4/2" placement="top">
                        <el-card>
                          <h4>备注</h4>
                          <p>admin</p>
                          <p>服务台-迟鹏-0.17小时</p>
                        </el-card>
                      </el-timeline-item>
                    </el-timeline>
                  </div>
                </div>
              </el-tab-pane>
              <el-tab-pane label="协查人" name="third">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="用户名"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="状态"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="电话"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="类型"
                    width="180"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联的配置项" name="fourth">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="ID"
                    width="80"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="资产编号"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="配置类型"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="配置子类"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="资产名称"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="部门"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="领用人"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="购置公司"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="保密"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="资产状态"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="IT状态"
                    sortable
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联的工单" name="five">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联的问题" name="six">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联变更" name="seven">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="用户历史请求" name="eight">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>
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
                  13506

                </el-form-item>
                <el-form-item label="状态:" class="mb0">待评价</el-form-item>
                <el-form-item label="服务群组:" class="mb0">
                  服务台(迟鹏,致敬,胡桥)
                </el-form-item>
                <el-form-item label="来源:" class="mb0">微信自助</el-form-item>
                <el-form-item label="请求类型:" class="mb0">事件</el-form-item>
                <el-form-item label="工单类别:" class="mb0">主板</el-form-item>
                <el-form-item label="影响度:" class="mb0">高</el-form-item>
                <el-form-item label="紧急度:" class="mb0">低</el-form-item>
                <el-form-item label="优先级:" class="mb0">中</el-form-item>
                <el-form-item label="报障类别:" class="mb0">
                  职场1-办公设备
                </el-form-item>
                <el-form-item label="重开次数:" class="mb0"></el-form-item>
                <el-form-item
                  label="影响可用性:"
                  class="custemitem mb0"
                ></el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">用户信息</div>
              <div class="box_content">
                <el-form-item label="用户:" class="mb0">13506</el-form-item>
                <el-form-item label="姓名:" class="mb0">待评价</el-form-item>
                <el-form-item label="公司:" class="mb0">
                  服务台(迟鹏,致敬,胡桥)
                </el-form-item>
                <el-form-item label="部门:" class="mb0">微信自助</el-form-item>
                <el-form-item label="职位:" class="mb0">事件</el-form-item>
                <el-form-item label="位置:" class="mb0">主板</el-form-item>
                <el-form-item label="电话:" class="mb0">高</el-form-item>
                <el-form-item label="座机:" class="mb0">低</el-form-item>
                <el-form-item label="邮箱:" class="mb0">中</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">时间信息</div>
              <div class="box_content step_content">
                <el-steps :active="1" align-center>
                  <el-step title="开单" description="03-15 10:29"></el-step>
                  <el-step title="响应" description="03-15 10:39"></el-step>
                  <el-step title="解决" description="03-15 10:40"></el-step>
                  <el-step title="关单" description="03-15 10:40"></el-step>
                </el-steps>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="handleAdd">
          重开
        </el-button>
        <el-dropdown
          trigger="hover"
          @command="handleCommand"
          style="margin-left: 10px"
        >
          <el-button size="medium">
            更多
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-document-remove" command="a">
              备注
            </el-dropdown-item>
            <el-dropdown-item icon="el-icon-message" command="b">
              发邮件
            </el-dropdown-item>
            <el-dropdown-item icon="el-icon-printer" command="c">
              打印
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-form>
  </div>
</template>

<script>
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'filesviewmanagement',
    components: {vabQuill},
    data() {
      return {
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
        rules: {
          username: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
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
        dialogFormVisible: false,
        username:
          'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {
    },
    methods: {
      showView() {
        this.title = '查看详情'
        this.dialogFormVisible = true
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.$baseMessage('模拟保存成功', 'success')
        this.$emit('fetch-data')
        this.close()
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

      .el-tabs--border-card {
        box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
      }

      .el-card__body {
        height: calc(100vh - 290px) !important;

        .el-tabs__content {
          height: calc(100vh - 340px) !important;
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

      .step_content {
        overflow-x: auto;
      }
    }
  }
</style>
