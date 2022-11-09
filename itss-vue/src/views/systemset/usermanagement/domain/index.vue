<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="180px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <el-form-item label="" prop="checked" class="tsitem">
              <el-checkbox v-model="checked">启动Ldap同步功能</el-checkbox>
            </el-form-item>

            <el-form-item label="Windows域集成验证登录: " prop="isvip">
              请联系供应商进行配置。
            </el-form-item>

            <el-form-item
              label="使用SAML统一验证登录:"
              prop="truename"
              class="tsitem"
            >
              <el-select v-model="form.isvip" placeholder="请选择" class="w">
                <el-option label="是" value="shanghai"></el-option>
                <el-option label="否" value="beijing"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="truename" class="tsitem">
              <el-button icon="el-icon-setting" @click="openwin" type="primary">
                SAML验证登录设置
              </el-button>
            </el-form-item>
          </el-card>
          <el-card class="cardbox" shadow="never">
            <el-form-item
              label="进行动态验证的windows域:"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="域服务器连接:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="绑定识别名:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
              <p class="ts">(例如:Administrator@test.com)</p>
            </el-form-item>
            <el-form-item label="密码:" prop="checked" class="tsitem">
              <el-input v-model="form.username" show-password></el-input>
            </el-form-item>
            <el-form-item label="" prop="checked" class="tsitem">
              <el-button icon="el-icon-setting" type="danger">测试</el-button>
            </el-form-item>
          </el-card>
          <el-card class="cardbox" shadow="never">
            <el-form-item label="" prop="checked" class="tsitem">
              <el-checkbox v-model="checked">同步LDAP信息</el-checkbox>
            </el-form-item>

            <el-form-item label="同步项: " prop="isvip">
              <el-checkbox v-model="checked">中文名</el-checkbox>
              <el-checkbox v-model="checked">邮箱</el-checkbox>
              <el-checkbox v-model="checked">电话</el-checkbox>
              <el-checkbox v-model="checked">职位</el-checkbox>
              <el-checkbox v-model="checked">位置</el-checkbox>
            </el-form-item>

            <el-form-item label="" prop="isvip" class="tsitem">
              <el-checkbox v-model="checked">
                本地记录与AD记录同名时将本地记录类型修改为“域用户”类型
              </el-checkbox>
            </el-form-item>
          </el-card>
          <el-card class="cardbox" shadow="never">
            <table class="desctable" style="width: 50%" ref="addTable">
              <colgroup>
                <col/>
                <col class="w100"/>
              </colgroup>
              <thead>
              <th>OU</th>
              <th>角色管理</th>
              <th>
                <el-button
                  round
                  size="mini"
                  type="primary"
                  icon="el-icon-plus"
                  @click="addItem"
                ></el-button>
              </th>
              </thead>
              <tbody>
              <tr v-for="(item, index) in formArr" :key="index">
                <td>
                  <el-input
                    placeholder="请输入内容"
                    v-model="input2"
                  ></el-input>
                </td>
                <td>
                  <el-select
                    v-model="form.isvip"
                    placeholder="请选择"
                    class="w"
                  >
                    <el-option label="是" value="shanghai"></el-option>
                    <el-option label="否" value="beijing"></el-option>
                  </el-select>
                </td>
                <td align="center">
                  <el-button
                    round
                    size="mini"
                    type="danger"
                    icon="el-icon-delete"
                    @click="delItem(index)"
                  ></el-button>
                </td>
              </tr>
              </tbody>
            </table>
            <el-form-item label="用户搜索过滤器:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
              <p class="ts">
                示例:
                (&(objectClass=user)(objectCategory=person)(!(userAccountControl:1.2.840.113556.1.4.803:=2))(mail=*))
              </p>
            </el-form-item>
            <el-form-item label="部门搜索过滤器:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
              <p class="ts">示例: (objectClass=organizationalUnit)</p>
            </el-form-item>
            <p class="font16 b">AD字段对应关系:</p>
            <el-form-item label="中文名:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="职位:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="邮箱:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="电话:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="登录名:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="座机:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="位置:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="首次同步时间:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="同步周期(分钟):" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item
              label="页面大小(1-1000):"
              prop="checked"
              class="tsitem"
            >
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-alert title="最新日志:" type="success" :closable="false">
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
      </div>
    </el-form>
  </div>
</template>

<script>
  // import { getList } from '@/api/changeLog'
  export default {
    name: 'domainmana',
    components: {},
    data() {
      return {
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
