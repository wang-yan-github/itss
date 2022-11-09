<template>
  <div class="index-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card shadow="never">
          <el-form inline size="small">
            <el-form-item style="margin-bottom: 0">
              <el-date-picker v-model="value1" value-format="yyyy-MM-dd" type="daterange" range-separator="至"
                              start-placeholder="开始日期" end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>

            <el-form-item style="margin-bottom: 0">
              <el-button type="primary" @click="fetchData">查询</el-button>
            </el-form-item>
            <!--            <el-form-item style="margin-bottom: 0">-->
            <!--              <el-button type="danger">设置</el-button>-->
            <!--            </el-form-item>-->
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="cardbox" shadow="never">
          <div slot="header">
            <span>工单数量统计</span>
            <div class="rbox">
              <!--              <vab-icon :icon="['fas', 'sync-alt']"></vab-icon>-->
            </div>
          </div>
          <el-col
            v-for="(item, index) in iconList1"
            :key="index"
            :xs="12"
            :sm="6"
            :md="4"
            :lg="4"
            :xl="4"
          >
            <el-card
              class="icon-panel1"
              shadow="never"
              :style="{ background: item.color }"
            >
              <div class="numbox">
                {{ item.num }}
              </div>
              <p>{{ item.title }}</p>
            </el-card>
          </el-col>
        </el-card>
      </el-col>

      <el-col
        v-for="(item, index) in iconList"
        :key="index"
        :xs="12"
        :sm="6"
        :md="4"
        :lg="6"
        :xl="4"
      >
        <router-link :to="item.link" target="_blank">
          <el-card class="icon-panel" shadow="never">
            <vab-icon
              :style="{ color: item.color }"
              :icon="['fas', item.icon]"
            ></vab-icon>
            <div class="box_num">
              <div class="numbox" :style="{ color: item.color }">
                {{ item.num }}
              </div>
              <p>{{ item.title }}</p>
            </div>
          </el-card>
        </router-link>
      </el-col>

      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
        <el-card class="card" shadow="never" closable="true">
          <div slot="header">
            <span>公告</span>
            <div class="rbox">
              <!--              <vab-icon :icon="['fas', 'times']"></vab-icon>-->
              <!--              <vab-icon :icon="['fas', 'sync-alt']"></vab-icon>-->
            </div>
          </div>
          <div class="list-box">
            <div v-for="(item) in noticeList" class="text item">
              <el-link @click='handleView(item.id)' class="classify-item" icon="el-icon-chat-line-square" type="primary"
                       v-text="item.title"></el-link>
            </div>
          </div>

        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span>操作历史</span>
            <div class="rbox">
              <!--              <vab-icon :icon="['fas', 'times']"></vab-icon>-->
              <!--              <vab-icon :icon="['fas', 'sync-alt']"></vab-icon>-->
            </div>
          </div>

          <el-table v-loading="listLoading" ref="listTable" stripe :data="logsList" border
                    :element-loading-text="elementLoadingText"
                    highlight-current-row @current-change="handleCurrentChange" height="368px">
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center"
                             width="60px;"></el-table-column>
            <el-table-column show-overflow-tooltip prop="user_name" label="姓名" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="title" label="标题" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="module" label="所属模块" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="operate_time" label="操作时间" align="center"></el-table-column>
          </el-table>
<!--          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"-->
<!--                         :total="total" @size-change="" @current-change=""></el-pagination>-->
        </el-card>
      </el-col>
    </el-row>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import {dependencies, devDependencies} from '../../../package.json'
import {statistics,getLogs,getNotices} from '@/api/index'
import {getById} from "@/api/sysAnnouncement";
import {statis} from "@/api/event";
import Views from './View'

export default {
  name: 'index',
  components: {
    Views
  },
  data() {
    return {
      tableData: [{
        a: '王小虎',
        b: '王小虎',
        c: '2016-05-02 12:00:30',
        d: 'PC端',
        e: '192.122.111',
        f: '问题待办'
      }
      ],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
      // listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      // elementLoadingText: '正在加载...',
      start_time: '',
      end_time: '',
      value1: '',
      value2: '',
      timer: 0,
      updateTime: process.env.VUE_APP_UPDATE_TIME,
      nodeEnv: process.env.NODE_ENV,
      dependencies: dependencies,
      devDependencies: devDependencies,
      config1: {
        startVal: 0,
        endVal: this.$baseLodash.random(20000, 60000),
        decimals: 0,
        prefix: '',
        suffix: '',
        separator: ',',
        duration: 8000,
      },
      config2: {
        startVal: 0,
        endVal: this.$baseLodash.random(1000, 20000),
        decimals: 0,
        prefix: '',
        suffix: '',
        separator: ',',
        duration: 8000,
      },
      config3: {
        startVal: 0,
        endVal: this.$baseLodash.random(1000, 20000),
        decimals: 0,
        prefix: '',
        suffix: '',
        separator: ',',
        duration: 8000,
      },

      //更新日志
      reverse: true,
      activities: [],
      noticeList: [],
      logsList: [],
      //其他信息
      userAgent: navigator.userAgent,
      //卡片图标
      iconList: [
        {
          icon: 'bars',
          num: '0',
          title: '待响应事件单',
          link: '/thing/table',
          color: '#ffc069',
        },
        {
          icon: 'bullseye',
          num: '0',
          title: '待处理事件单',
          link: '/thing/table',
          color: '#5cdbd3',
        },
        {
          icon: 'exclamation',
          num: '0',
          title: '已超时事件',
          link: '/thing/table',
          color: '#b37feb',
        },
        {
          icon: 'pause',
          num: '0',
          title: '暂停事件单',
          link: '/thing/table',
          color: '#69c0ff',
        },
        {
          icon: 'paperclip',
          num: '0',
          title: '待协查事件单',
          link: '/thing/map',
          color: '#ff85c0',
        },
        {
          icon: 'question',
          num: '0',
          title: '问题待办',
          link: 'faq/roleManagement',
          color: '#ffd666',
        },

        {
          icon: 'edit',
          num: '0',
          title: '变更待办',
          link: '/biangeng/changeDeal',
          color: '#ff9c6e',
        },
        {
          icon: 'exchange-alt',
          title: '发布待办',
          num: '0',
          link: '/fabu/publishDb',
          color: '#95de64',
        },
        {
          icon: 'book-open',
          num: '0',
          title: '待审核知识',
          link: '/book/knowledgeAudit',
          color: '#64b4de',
        },
        {
          icon: 'code',
          num: '0',
          title: '软件许可证到期',
          link: '/set/softwaremanagement',
          color: '#646dde',
        },
        {
          icon: 'list-ul',
          num: '0',
          title: '客户合同到期',
          link: '/set/contractcustommanagement',
          color: '#de64a6',
        },
        {
          icon: 'coffee',
          num: '0',
          title: '供应商合同到期',
          link: '/set/contractshopmanagement',
          color: '#de8164',
        },
      ],
      //工单数量统计
      iconList1: [
        {
          icon: 'video',
          num: '0',
          title: '工单个数',
          link: '',
          color: '#ffc069',
        },
        {
          icon: 'table',
          num: '0',
          title: '完成数',
          link: '/vab/table/comprehensiveTable',
          color: '#5cdbd3',
        },
        {
          icon: 'laptop-code',
          num: '0',
          title: '待响应',
          link: '',
          color: '#b37feb',
        },
        {
          icon: 'book',
          num: '0',
          title: '处理中',
          link: '',
          color: '#69c0ff',
        },
        {
          icon: 'bullhorn',
          num: '0',
          title: '暂停数',
          link: '',
          color: '#ff85c0',
        },
        {
          icon: 'gift',
          num: '0',
          title: '撤单数',
          link: '',
          color: '#ffd666',
        },

        {
          icon: 'balance-scale-left',
          num: '0',
          title: '重开数',
          link: '',
          color: '#ff9c6e',
        },
        {
          icon: 'coffee',
          title: '符合响应SLA',
          num: '0',
          link: '',
          color: '#95de64',
        },
        {
          icon: 'coffee',
          num: '0',
          title: '不符合响应SLA',
          link: '',
          color: '#64b4de',
        },
        {
          icon: 'coffee',
          num: '0',
          title: '符合处理SLA',
          link: '',
          color: '#646dde',
        },
        {
          icon: 'coffee',
          num: '0',
          title: '不符合处理SLA',
          link: '',
          color: '#de64a6',
        },
        {
          icon: 'coffee',
          num: '0',
          title: '费用',
          link: '',
          color: '#de8164',
        },
      ],
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  mounted() {
    let base = +new Date(2020, 1, 1)
    let oneDay = 24 * 3600 * 1000
    let date = []

    let data = [Math.random() * 1500]
    let now = new Date(base)

    const addData = (shift) => {
      now = [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/')
      date.push(now)
      data.push(this.$baseLodash.random(20000, 60000))

      if (shift) {
        date.shift()
        data.shift()
      }

      now = new Date(+new Date(now) + oneDay)
    }

    for (let i = 1; i < 6; i++) {
      addData()
    }
    addData(true)
    this.fwl.xAxis[0].data = date
    this.fwl.series[0].data = data
    this.timer = setInterval(() => {
      addData(true)
      this.fwl.xAxis[0].data = date
      this.fwl.series[0].data = data
    }, 3000)
  },
  methods: {
    handleClick(e) {
      this.$baseMessage(`点击了${e.name},这里可以写跳转`)
    },
    handleChangeTheme() {
      this.$baseEventBus.$emit('theme')
    },
    async handleView(id) {
      const {data} = await getById({id})
      this.$refs['views'].showView(data)
    },
    async fetchData() {
      await this.getEventData();
      await this.getNoticeData();
      await this.getLogsData();
      await this.getStatistics();
    },
    async getEventData() {
      const {data} = await statis({create_time_start: this.value1[0], create_time_relend: this.value1[1]})
      for (var i = 0; i < data.length; i++) {
        this.iconList1[i].num = data[i].num;
      }
      this.iconList[0].num = data[2].num
      this.iconList[1].num = data[3].num
      this.iconList[3].num = data[4].num
    },
    async getNoticeData() {
      var that = this;
      getNotices({start_time: this.value1[0], end_time: this.value1[1]}).then((res) => {
        console.log("公告", res);
        if (res.code == 0) {
          that.noticeList = res.data;
        }
      })
    },
    async getLogsData() {
      var that = this;
      getLogs({start_time: this.value1[0], end_time: this.value1[1]}).then((res) => {
        console.log("访问历史", res);
        if (res.code == 0) {
          that.logsList = res.data;
        }
      })
    },
    async getStatistics() {
      const {data} = await statistics({start_time: this.value1[0], end_time: this.value1[1]})
      this.iconList[2].num = data.overtime
      this.iconList[4].num = data.event_assist
      this.iconList[5].num = data.questions
      this.iconList[6].num = data.changes
      this.iconList[7].num = data.PublishBasics
      this.iconList[8].num = data.knowledges
      this.iconList[9].num = data.assetsSoftwareLicences
      this.iconList[10].num = data.assetsCustomerContracts
      this.iconList[11].num = data.assetsSupplierContracts
    },

  },
}
</script>
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;

  ::v-deep {
    .el-alert {
      padding: $base-padding;

      &--info.is-light {
        min-height: 82px;
        padding: $base-padding;
        margin-bottom: 15px;
        color: #909399;
        background-color: $base-color-white;
        border: 1px solid #ebeef5;
      }
    }

    .el-card__body {
      .echarts {
        width: 100%;
        height: 125px;
      }
    }
  }

  .card {
    min-height: 400px;

    ::v-deep {
      .el-card__header {
        div {
          display: flex;
          flex-direction: row;
          justify-content: space-between;

          svg {
            margin: 0 5px;
            color: #666;
            cursor: pointer;
          }
        }
      }

      .el-card__body {
        height:400px;
        .echarts {
          width: 100%;
          height: 305px;
        }
      }
    }
  }

  .cardbox {
    ::v-deep {
      .el-card__header {
        div {
          display: flex;
          flex-direction: row;
          justify-content: space-between;

          svg {
            margin: 0 5px;
            color: #666;
            cursor: pointer;
          }
        }
      }

      .el-card__body {
        .echarts {
          width: 100%;
          height: 305px;
        }
      }
    }
  }

  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }

  .table {
    width: 100%;
    color: #666;
    border-collapse: collapse;
    background-color: #fff;

    td {
      position: relative;
      min-height: 20px;
      padding: 9px 15px;
      font-size: 14px;
      line-height: 20px;
      border: 1px solid #e6e6e6;

      &:nth-child(odd) {
        width: 20%;
        text-align: right;
        background-color: #f7f7f7;
      }
    }
  }

  .icon-panel {
    height: auto;
    text-align: left;
    cursor: pointer;

    ::v-deep {
      .el-card__body {
        display: flex;
        padding: 10px;
      }
    }

    .box_num {
      display: block;
      margin-left: 10px;
      text-align: center;
      border-left: 1px solid #eee;
      padding-left: 20px;
    }

    .numbox {
      font-size: 40px;
      position: relative;
      font-weight: bold;
    }

    .numbox:after {
      display: inline;
      font-size: 12px;
      margin-left: 0px;
      color: #333;
      content: '个';
    }

    svg {
      padding-top: 20px;
      font-size: 36px;
      width: 40px;
    }

    p {
      margin-top: 10px;
    }
  }

  .icon-panel1 {
    height: auto;
    text-align: center;
    color: #fff;

    ::v-deep {
      .el-card__body {
        padding: 10px 5px;
      }
    }

    .numbox {
      font-size: 40px;
      position: relative;
      font-weight: bold;
    }

    svg {
      padding-top: 20px;
      font-size: 40px;
      width: 60px;
    }

    p {
      margin-top: 10px;
    }
  }

  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }

  .list-box {
    overflow-y: auto;
    min-height: 200px;
    height: calc(100vh - 640px);
  }

  .classify {
    overflow-y: auto;
    min-height: 200px;
    height: calc(100vh - 640px);
  }

  .classify h2 span {
    font-size: 1rem;
    font-weight: normal;
    color: #999;
  }

  .classify-item {
    line-height: 2;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
}
</style>
