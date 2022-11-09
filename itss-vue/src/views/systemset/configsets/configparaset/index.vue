<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="资产编号生成格式配置" name="first">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-card class="cardbox" shadow="never" style="border-top: 0">
                <el-form-item
                  label="自动生成资产编号"
                  prop="is_auto_code"
                  class="tsitem"
                >
                  <el-radio v-model="form.is_auto_code " label="1">是</el-radio>
                  <el-radio v-model="form.is_auto_code" label="2">否</el-radio>
                </el-form-item>

                <el-form-item
                  label="资产编号方式: "
                  prop="is_code_mode"
                  class="tsitem"
                >
                  <el-radio v-model="form.is_code_mode" label="1">
                    大流水（按照资产添加顺序计数，不清零）
                  </el-radio>
                  <el-radio v-model="form.is_code_mode" label="2">
                    小流水（按照资产添加顺序计数，每年清零）
                  </el-radio>
                </el-form-item>

                <el-form-item
                  label="流水号位数:"
                  prop="erial_number"
                  class="tsitem"
                >
                  <el-input v-model="form.erial_number"></el-input>
                </el-form-item>
                <el-form-item label="时间格式:" prop="time_format" class="tsitem">
                  <el-input v-model="form.time_format"></el-input>
                </el-form-item>
                <el-form-item
                  label="配置项资产编号格式:"
                  prop="num_format"
                  class="tsitem"
                >
                  <el-input v-model="form.num_format"></el-input>
                </el-form-item>
                <el-alert type="success" title="配置参数" :closable="false">
                  时间格式: (DATE) 序号位数: (SERIES) 配置项类型: (CITYPE)
                  配置子类 (CHILDTYPE) 部门编码 (DeptCode) 城市编码 (CityCode)
                  使用范围编码 (ApplicationCode)
                </el-alert>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="通知模版" name="second">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-card class="cardbox" shadow="never" style="border-top: 0">
                <el-form-item label="标题:" prop="title">
                  <el-input v-model="form.title"></el-input>
                </el-form-item>
                <el-form-item label="内容:" prop="contant">
                  <vab-quill
                    v-model="form.contant"
                    :min-height="200"
                    :options="options"
                  ></vab-quill>
                </el-form-item>
                <el-form-item label="微信消息:" prop="wechat_message">
                  <el-input
                    v-model="form.wechat_message"
                    type="textarea"
                    :rows="3"
                  ></el-input>
                </el-form-item>
                <el-form-item label="" prop="truename">
                  <el-alert type="success" title="" :closable="false">
                    <p>
                      参数注：用户资产盘点列表链接开始：(as)；用户资产盘点列表链接结束：(/as)
                    </p>
                    <p>
                      注：配置管理->资产盘点管理->发送通知邮件:仅给当前查询结果中盘点期已开始且未确认的所有用户发送邮件。
                    </p>
                  </el-alert>
                </el-form-item>

                <el-form-item label="发件箱:" prop="send_email">
                  <el-select v-model.trim="form.send_email" class="w">
                    <el-option label="是" value="0"></el-option>
                    <el-option label="否" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-card>
            </el-col>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="资产打印配置" name="third">
          <el-row :gutter="20">
            <el-card class="cardbox" shadow="never" style="border-top: 0">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item
                  label="打印机名称:"
                  prop="print_name"
                  class="tsitem"
                >
                  <el-input v-model="form.print_name"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-alert
                  type="info"
                  title="纸张大小"
                  :closable="false"
                  style="margin-bottom: 20px"
                >
                  <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="宽:" prop="weight">
                      <el-input v-model="form.weight"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="高:" prop="high">
                      <el-input v-model="form.high"></el-input>
                    </el-form-item>
                  </el-col>
                </el-alert>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="打印浓度:" prop="concentration" class="tsitem">
                  <el-select v-model.trim="form.concentration" class="w">
                    <el-option label="50%" value="50%"></el-option>
                    <el-option label="75%" value="75%"></el-option>
                    <el-option label="100%" value="100%"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="感应器:" prop="sensor" class="tsitem">
                  <el-select v-model.trim="form.sensor" class="w">
                    <el-option label="感应器一" value="0"></el-option>
                    <el-option label="感应器二" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item
                  label="垂直距离感应器垂直间距高度（毫米):"
                  prop="print_high"
                  class="tsitem"
                >
                  <el-input v-model="form.print_high"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item
                  label="垂直距离感应器偏移距离（毫米）："
                  prop="deviation"
                  class="tsitem"
                >
                  <el-input v-model="form.deviation"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="打印份数：" prop="print_num" class="tsitem">
                  <el-input v-model="form.print_num"></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="打印内容:" prop="print_content" class="tsitem">
                  <el-input
                    v-model="form.print_content"
                    type="textarea"
                    :rows="10"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-alert type="success" title="说明：" :closable="false" style="margin-bottom:20px;">
                  <p>1.保证内容是合法的JSON格式</p>
                  <p>2.Content:打印内容</p>
                  <p>3.X:横轴坐标位置</p>
                  <p>4.Y:纵轴坐标位置</p>
                  <p>5.FontSize:字体大小</p>
                  <p>6.FontStyle:0:普通;1:斜体;2:加粗;3:斜体并加粗;</p>
                  <p>7.ItemType:Text:表示文本；QRcode:二维码</p>
                  <p>8.QrCodeWidth:二维码宽度</p>
                </el-alert>
              </el-col>
            </el-card>
          </el-row>
        </el-tab-pane>
        <el-tab-pane label="扩展表单" name="fourth">
          <el-row :gutter="20">
            <el-card class="cardbox" shadow="never" style="border-top: 0">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item
                  label="全局扩展表单："
                  prop="form_name"
                  class="tsitem"
                >
                  <el-input v-model="form.form_name" @click.native="openwin">
                    <!--<el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>-->
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-alert type="success" title="" :closable="false" style="margin-bottom:20px;">
                  <p>
                    说明：在这里选择所有配置项类型都有的扩展表单；配置项类型上可以设置自己的扩展表单；
                  </p>
                </el-alert>
              </el-col>
            </el-card>
          </el-row>
        </el-tab-pane>
      </el-tabs>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <dialogone
      ref="dialogone"
      @deptData="deptset"
      @fetch-data="
        {
          biaodan
        }
      "
    ></dialogone>
  </div>

</template>

<script>
  import {getOne, updateById} from '@/api/assetsConfigParams'
  import vabQuill from '@/plugins/vabQuill'
  import Dialogone from './components/biaodan'

  export default {
    name: 'configparaset',
    components: {vabQuill, Dialogone},
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
        activeName: 'first',
        input2: '4',
        radio1: '1',
        radio2: '1',
        reverse: true,
        activities: [],
        formArr: [{value1: ''}],
        checked: false,
        checked1: false,
        value: [],
        form: {
          id: '',
          is_auto_code: '',
          is_code_mode: '',
          erial_number: '',
          time_format: '',
          num_format: '',
          title: '',
          contant: '',
          wechat_message: '',
          send_email: '',
          print_name: '',
          weight: '',
          high: '',
          concentration: '',
          sensor: '',
          print_high: '',
          deviation: '',
          print_num: '',
          print_content: '',
          form_id: '',
          form_name: '',
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
      deptset(data) {
        this.form.form_id = data.id;
        this.form.form_name = data.name;
      },
      openwin() {
        this.$refs['dialogone'].showWin();
      },
      async fetchData() {
        const {data} = await getOne()
        console.log(data);
        if(data != null ){
          this.form = data
        }

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
            const {msg} = await updateById(this.form)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')
            this.fetchData()

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
        box-sizing: content-box;
        height: calc(100vh - 320px);
        overflow-y: auto;
        overflow-x: hidden;
      }
    }
  }
</style>
