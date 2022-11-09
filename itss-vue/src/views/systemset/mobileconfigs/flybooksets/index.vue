<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="180px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <el-form-item label="飞书参数配置:" class="tsitem">
              <el-input
                v-model="form.username"
                :rows="15"
                type="textarea"
              ></el-input>
            </el-form-item>
            <p>配置说明:</p>
            <el-table :data="tableData" border style="width: 100%">
              <el-table-column label="属性名称" width="180" align="center">
                <template #default="{ row }">
                  <el-tag>{{row.name}}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="content" label="说明" align="center"></el-table-column>
            </el-table>
            <el-form-item label="消息模板:" prop="checked" class="tsitem">
              <el-input
                v-model="form.username"
                :rows="15"
                type="textarea"
              ></el-input>
            </el-form-item>
            <p>飞书应用配置:</p>
            <el-table :data="tableData1" border style="width: 100%" :show-header="false">
              <el-table-column label="名称" width="380" align="center">
                <template #default="{ row }">
                  {{row.name}}
                </template>
              </el-table-column>
              <el-table-column prop="content" label="说明"></el-table-column>
            </el-table>
            <el-alert title="最新日志:" type="success" :closable="false" style="margin-top:20px;">
              <el-timeline :reverse="reverse">
                <el-timeline-item
                  v-for="(activity, index) in activities"
                  :key="index"
                  :timestamp="activity.timestamp"
                  :color="activity.color"
                >
                  {{ activity.content }}
                </el-timeline-item>
              </el-timeline>
            </el-alert>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
        <el-button size="medium" @click="closeall">立即同步知识库</el-button>
        <el-button size="medium" @click="closeall">立即同点工单</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  // import { getList } from '@/api/changeLog'
  export default {
    name: 'flybooksets',
    components: {},
    data() {
      return {
        tableData: [
          {
            content: '是否启用飞书,true:启用;false:不启用',
            name: 'enable',
          },
          {
            content: '凭证与基础信息-应用凭证-App Id',
            name: 'appId',
          },
          {
            content: '凭证与基础信息-应用凭证-App Secret',
            name: 'appSecret',
          },
          {
            content: '事件订阅-Encrypt Key',
            name: 'encryptKey',
          },
        ],
        tableData1: [
          {
            content: 'https://applink.feishu.cn/client/web_app/open?appId=<appId>&path=admin%2Ffeishu%2FuserAddTicket.do',
            name: '飞书自助报障页面跳转地址(兼容移动端和桌面端):',
          },
          {
            content: '	桌面端主页：http://ent1.topvee.cn:80/admin/index.do；移动端主页：http://ent1.topvee.cn:80/admin/ticket/ticketSystem.do',
            name: '应用功能-网页:',
          },
          {
            content: '消息卡片请求网址：http://ent1.topvee.cn:80/admin/feishu/event.do',
            name: '应用功能-机器人:',
          },
          {
            content: '配置应用快捷入口（移动端和桌面端）：http://applink.feishu.cn/client/web_app/open?appId=<appId>&path=admin%2Fticket%2FuserAdd.do',
            name: '应用功能-扩展:',
          },
        ],
        input2: '',
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
      this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index) {
        var that = this
        if (that.formArr.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr.splice(index, 1)
        }
      },
      openwin() {
        this.$router.push({
          path: '/set/samlmana',
          query: {id: 123},
        })
      },
      async fetchData() {
        // const { data } = await getList()
        // data.map((item, index) => {
        //   if (index === data.length - 1) {
        //     item.color = '#0bbd87'
        //   }
        // })
        // this.activities = data
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
