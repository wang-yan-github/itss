<template>
  <div class="main-container pb80">
    <el-form ref="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
              	<el-row :gutter="20">

              		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">属性信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="品牌:" class="tsitem">
                        		345
                        	</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="型号:" class="tsitem">
                        		2345
                        	</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="序列号(SN号):" class="tsitem">
                        		2345
                        	</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="维保开始日期:" class="tsitem">
                        		3245
                        	</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="NBU:" class="tsitem">
                        		2345
                        	</el-form-item>
                        </el-col>
                      </div>
                    </div>

              		</el-col>
              		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">拓展信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="测试文本1001:" class="tsitem">
                        		3245
                        	</el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                        	<el-form-item label="添加的:" class="tsitem">
                        		3245
                        	</el-form-item>
                        </el-col>
                      </div>
                    </div>
              		</el-col>
              		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">其他信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <el-form-item label="照片:" class="tsitem">
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


                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        	<el-form-item label="用途:" class="tsitem">
                        		452345
                        	</el-form-item>
                        	<el-form-item label="位置:" class="tsitem">
                        		234532
                        	</el-form-item>
                        	<el-form-item label="备注:" class="tsitem">
                        		234523
                        	</el-form-item>
                        </el-col>
                      </div>
                    </div>
              		</el-col>

              	</el-row>
              </el-tab-pane>

              <el-tab-pane label="联系人" name="second">
                <el-table :data="tableData" stripe border >
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
              <el-tab-pane label="供应商合同" name="third">
                <el-table :data="tableData" stripe border >
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
                <el-table :data="tableData" stripe border >
                  <el-table-column
                    prop="date"
                    label="ID"
                    width="80"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="资产编号"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="配置类型"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="配置子类"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="资产名称"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="部门"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="领用人"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="购置公司"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="保密"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="资产状态"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="IT状态"
                    sortable
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="工单" name="five">
                <el-table :data="tableData" stripe border >
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联的变更" name="six">
                <el-table :data="tableData" stripe border >
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
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
              <div class="box_content">
                <el-form-item label="资产编号:" class="mb0">
                  13506

                </el-form-item>
                  <el-form-item label="ID:" class="mb0">
                  	34234
                  </el-form-item>
                  <el-form-item label="配置项类型:" class="mb0">
                  	虚拟机
                  </el-form-item>
                	<el-form-item label="配置子类:" class="mb0">
                		2342
                	</el-form-item>
                	<el-form-item label="资产名称:" class="mb0">
                		575
                	</el-form-item>
                	<el-form-item label="部门:" class="mb0">
                		3453
                	</el-form-item>
                	<el-form-item label="负责人:" class="mb0">
                		25245
                    </el-form-item>
                	<el-form-item label="领用人:" class="mb0">
                		2352
                	</el-form-item>
                	<el-form-item label="资产状态:" class="mb0">
                		42524
                	</el-form-item>
                	<el-form-item label="保密:" class="mb0">
                		3452345
                	</el-form-item>
                	<el-form-item label="购置(创建)日期:" class="custemitem mb0">
                		252525
                	</el-form-item>
                	<el-form-item label="财务编号:" class="mb0">
                		235235
                	</el-form-item>
                	<el-form-item label="保密编号:" class="mb0">
                		2525
                	</el-form-item>
                	<el-form-item label="购置公司:" class="mb0">
                		345234
                	</el-form-item>
                	<el-form-item label="使用范围:" class="mb0">
                		3452345
                	</el-form-item>
                	<el-form-item label="IT状态:" class="mb0">
                		3452345
                	</el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">二维码</div>
              <div class="box_content">
                <el-form-item label="" class="mb0">
                	<el-image :src="src" style="width: 140px; height: 140px"></el-image>
                </el-form-item>

              </div>
            </div>

          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
      	<el-button size="medium" @click="closeall">关闭</el-button>
      </div>
    </el-form>

  </div>
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
export default {
  name: 'companyviews',
  components: { vabQuill },
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
      dialogFormVisible: false,
      username:
        'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
    }
  },
  created() {},
  methods: {

    closeall() {
    	// 调用全局挂载的方法
    	this.$store.dispatch('tabsBar/delRoute', this.$route)
    	// 返回上一步路由
    	this.$router.go(-1)
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
  }
}
</style>
