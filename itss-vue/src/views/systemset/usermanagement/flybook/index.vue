<template>
  <div class="index-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <el-form-item label="启用:" prop="checked" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="周期(分钟):" prop="isvip" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="首次执行时间:" prop="truename" class="tsitem">
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item
              label="最后执行时间:(空表示全量同步):"
              prop="truename"
              class="tsitem"
            >
              <el-input v-model="form.username"></el-input>
            </el-form-item>

            <el-form-item label="配置:" prop="truename" class="tsitem">
              <el-input
                v-model="form.username"
                type="textarea"
                :rows="10"
              ></el-input>
            </el-form-item>
            <p>配置说明：</p>
            <el-table
              v-loading="listLoading"
              ref="listTable"
              stripe
              :data="tableData"
              :element-loading-text="elementLoadingText"
              highlight-current-row
              border
              @current-change="handleCurrentChange"
            >
              <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
              <el-table-column
                show-overflow-tooltip
                prop="date"
                label="属性名称"
                align="center"
                width="200px;"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="address"
                label="说明"
                align="center"
              ></el-table-column>

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
        <el-button type="success" size="medium" @click="tongbu">立即同步</el-button>
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
        listLoading: true,
        selectRows: '',
        elementLoadingText: '正在加载...',
        input2: '',
        reverse: true,
        activities: [],
        formArr: [{value1: ''}],
        tablelist: null,
        checked: false,
        checked1: false,
        value: [],
        form: {
          username: 'http://ent1.topvee.cn/saml/metadata.do',
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
          path: '/set/usermanagementedit',
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
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 300)
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
      // 复制成功时的回调函数
      tongbu() {
        this.$message.success('同步成功！')
      },
      // 复制成功时的回调函数
      onCopy(e) {
        this.$message.success('内容已复制到剪切板！')
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error('抱歉，复制失败！')
      },
    },
  }
</script>
<style lang="scss" scoped>
  .upload-demo {
    ::v-deep {
      .el-upload {
        display: block;

        .el-upload-dragger {
          width: 100% !important;
        }
      }
    }
  }
</style>
