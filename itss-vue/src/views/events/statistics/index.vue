<template>
  <div class="main-container pb80">
    <template>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="默认报表" name="first">
          <el-card shadow="never" style="border-top: 0">
            <div id="pdfDom">
              <el-form :inline="true" :model="queryForm" @submit.native.prevent>
                <vab-query-form>
                  <vab-query-form-left-panel :span="4">
<!--                    <el-button icon="el-icon-upload2" type="primary" @click="onClickDownLoad">-->
<!--                      导出-->
<!--                    </el-button>-->

                  </vab-query-form-left-panel>
                  <vab-query-form-right-panel :span="20">
                    <el-form-item>
                      <el-date-picker v-model="value1" value-format="yyyy-MM-dd" type="daterange" range-separator="至"
                                      start-placeholder="开始日期" end-placeholder="结束日期">
                      </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                      <el-button icon="el-icon-search" type="primary" @click="queryData">
                        查询
                      </el-button>
                    </el-form-item>
                  </vab-query-form-right-panel>
                </vab-query-form>
              </el-form>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                </el-col>
                <!-- 工单数量统计 -->
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-show="showList[0].value">
                  <el-card class="cardbox" shadow="never">
                    <div class="box_card">
                      <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;工单数量统计</div>
                      <div class="box_content">
                        <el-row :gutter="20">
                          <el-col v-for="(item, index) in iconList1" :key="index" :xs="12" :sm="6"
                                  :md="4" :lg="4" :xl="4">
                            <el-card class="icon-panel1" shadow="never"
                                     :style="{ background: item.color }">
                              <div class="numbox">
                                {{ item.num }}
                              </div>
                              <p>{{ item.title }}</p>
                            </el-card>
                          </el-col>
                        </el-row>
                      </div>
                    </div>
                  </el-card>
                </el-col>
                <!-- 工单数量趋势统计 -->
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-show="showList[1].value">
                  <el-card shadow="never" class="card">
                    <div class="box_card">
                      <div class="box_header">
                        <i class="el-icon-s-data"></i>&nbsp;工单数量趋势统计
                        <div style="float: right;">
                          <el-button size="mini" @click="getEventByYear">年</el-button>
                          <el-button size="mini" @click="monthShow">月</el-button>
                        </div>

                      </div>
                      <div class="box_content">
                        <vab-chart ref="chartline" theme="vab-echarts-theme" :options="linechart"/>
                      </div>
                    </div>
                  </el-card>
                </el-col>
                <!-- 工单/状态统计 -->
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-show="showList[2].value">
                  <el-card shadow="never" class="card">
                    <div class="box_card">
                      <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;工单/状态统计</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                          <vab-chart ref="piechart" theme="vab-echarts-theme"
                                     :options="piechart"/>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                          <vab-chart ref="gauge" theme="vab-echarts-theme" :options="gauge"/>
                        </el-col>
                      </div>
                    </div>

                  </el-card>
                </el-col>
                <!-- 工单/状态统计 -->
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-show="showList[3].value">
                  <el-card shadow="never" class="card">
                    <div class="box_card">
                      <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;SLA统计</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                          <vab-chart ref="gauge" theme="vab-echarts-theme" :options="gauge2"/>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                          <vab-chart ref="gauge" theme="vab-echarts-theme" :options="gauge3"/>
                        </el-col>
                      </div>
                    </div>

                  </el-card>
                </el-col>
              </el-row>
            </div>

          </el-card>


        </el-tab-pane>
      </el-tabs>
    </template>
    <roles ref="roles" @fetch-data="fetchData"></roles>
    <rolesview ref="rolesview" @fetch-data="fetchData"></rolesview>
    <bumen ref="bumen" @fetch-data="fetchData"></bumen>
    <category ref="category" @fetch-data="fetchData"></category>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import VabChart from '@/plugins/echarts'
import {
  getList,
  doDelete
} from '@/api/userManagement'
import Roles from '@/components/UserSelectModel'
import Bumen from '@/components/bumenTree'
import Rolesview from '@/components/fanganclass'
import Category from './components/category'
import {statis, eventByYear, statisCate, statisFinish, getSysSql, eventByMonth} from '@/api/event'
import {eventStatistics, statistics} from '@/api/index'
import html2canvas from "html2canvas";
import html2Canvas from 'html2canvas'
import jsPDF from 'jspdf'

export default {
  name: 'form1',
  components: {
    VabChart,
    Roles,
    Rolesview,
    Bumen,
    Category,
  },
  data() {
    return {
      category: '',
      list: [],
      listLoading: true,
      loading: false,
      isShow: false,
      elementLoadingText: '正在加载...',
      activeName: 'first',
      queryForm: {},
      showList: [
        {
          "title": "工单数量统计",
          "value": true,
        },
        {
          "title": "工单数量趋势统计",
          "value": true,
        },
        {
          "title": "工单/状态统计",
          "value": true,
        },
        {
          "title": "SLA统计",
          "value": true,
        },
      ],
      value1: '',
      value2: '',
      //工单数量统计
      iconList1: [{
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
      linechart: {
        xAxis: {
          type: 'category',
          data: ['2018', '2019', '2020', '2021', '2022']
        },
        yAxis: {
          type: 'value'
        },
        series: []
      },
      piechart: {
        tooltip: {
          trigger: 'item'
        },
        series: [{
          type: 'pie',
          radius: '70%',
          data: [
            // {
            //   value: 1048,
            //   name: '类别1'
            // },
            // {
            //   value: 735,
            //   name: '类别2'
            // },
            // {
            //   value: 580,
            //   name: '类别3'
            // },
            // {
            //   value: 484,
            //   name: '类别4'
            // },
            // {
            //   value: 300,
            //   name: '类别5'
            // }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }]
      },
      gauge: {
        series: [{
          type: 'gauge',
          axisLine: {
            lineStyle: {
              width: 20,
              color: [
                [0.3, '#67e0e3'],
                [0.7, '#37a2da'],
                [1, '#fd666d']
              ]
            }
          },
          pointer: {
            itemStyle: {
              color: 'auto'
            }
          },
          axisTick: {
            distance: -30,
            length: 8,
            lineStyle: {
              color: '#fff',
              width: 2
            }
          },
          splitLine: {
            distance: -30,
            length: 30,
            lineStyle: {
              color: '#fff',
              width: 4
            }
          },
          axisLabel: {
            color: 'auto',
            distance: 0,
            fontSize: 14
          },
          detail: {
            valueAnimation: true,
            formatter: '{value} %',
            color: 'auto'
          },
          data: [
            //  {
            //  value: 70,
            //  name: '工单完成率'
            // }
          ]
        }]
      },
      gauge2: {
        series: [{
          type: 'gauge',
          axisLine: {
            lineStyle: {
              width: 20,
              color: [
                [0.3, '#67e0e3'],
                [0.7, '#37a2da'],
                [1, '#fd666d']
              ]
            }
          },
          pointer: {
            itemStyle: {
              color: 'auto'
            }
          },
          axisTick: {
            distance: -30,
            length: 8,
            lineStyle: {
              color: '#fff',
              width: 2
            }
          },
          splitLine: {
            distance: -30,
            length: 30,
            lineStyle: {
              color: '#fff',
              width: 4
            }
          },
          axisLabel: {
            color: 'auto',
            distance: 0,
            fontSize: 14
          },
          detail: {
            valueAnimation: true,
            formatter: '{value} %',
            color: 'auto'
          },
          data: [
            //   {
            // 	value: 70,
            // 	name: '响应SLA满足率'
            // }
          ]
        }]
      },
      gauge3: {
        series: [{
          type: 'gauge',
          axisLine: {
            lineStyle: {
              width: 20,
              color: [
                [0.3, '#67e0e3'],
                [0.7, '#37a2da'],
                [1, '#fd666d']
              ]
            }
          },
          pointer: {
            itemStyle: {
              color: 'auto'
            }
          },
          axisTick: {
            distance: -30,
            length: 8,
            lineStyle: {
              color: '#fff',
              width: 2
            }
          },
          splitLine: {
            distance: -30,
            length: 30,
            lineStyle: {
              color: '#fff',
              width: 4
            }
          },
          axisLabel: {
            color: 'auto',
            distance: 0,
            fontSize: 14
          },
          detail: {
            valueAnimation: true,
            formatter: '{value} %',
            color: 'auto'
          },
          data: [
            //   {
            // 	value: 70,
            // 	name: '处理SLA满足率'
            // }
          ]
        }]
      },

    }
  },
  created() {
    this.fetchData();
    this.monthShow();
    this.getStatic();
    this.statisCate();
    this.statisFinish();
    this.getSysSql();
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  watch: {},
  mounted() {

  },
  methods: {
    getShow(data) {
      console.log(data)
      this.showList[0].value = false;
      this.showList[1].value = false;
      this.showList[2].value = false;
      this.showList[3].value = false;
      for (var i = 0; i < data.length; i++) {
        if (data[i] == '工单数量统计') {
          this.showList[0].value = true;
        }
        if (data[i] == '工单数量趋势统计') {
          this.showList[1].value = true;
        }
        if (data[i] == '工单/状态统计') {
          this.showList[2].value = true;
        }
        if (data[i] == 'SLA统计') {
          this.showList[3].value = true;
        }

      }
    },
    handleRole() {
      this.$refs['roles'].showWin()
    },
    handlebumen() {
      this.$refs['bumen'].showWin()
    },
    handleClass() {
      this.$refs['rolesview'].showWin()
    },
    handleView() {
      this.$router.push({
        path: '/thing/statisticsViewEvent',
      })
    },
    handleUpload() {
      this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
        this.$baseMessage('数据导出成功', 'success')
      })
    },
    onClickDownLoad() {


      //htmlToPdf.getPdf('下载名称');


      html2Canvas(document.querySelector('#pdfDom'), {
        allowTaint: false,
        taintTest: false,
        logging: false,
        useCORS: true,
        dpi: window.devicePixelRatio * 4, //将分辨率提高到特定的DPI 提高四倍
        scale: 4 //按比例增加分辨率
      }).then(canvas => {
        var pdf = new jsPDF('p', 'mm', 'a4');    //A4纸，纵向
        var ctx = canvas.getContext('2d'),
          a4w = 190, a4h = 277,    //A4大小，210mm x 297mm，四边各保留10mm的边距，显示区域190x277
          imgHeight = Math.floor(a4h * canvas.width / a4w),    //按A4显示比例换算一页图像的像素高度
          renderedHeight = 0;

        while (renderedHeight < canvas.height) {
          var page = document.createElement("canvas");
          page.width = canvas.width;
          page.height = Math.min(imgHeight, canvas.height - renderedHeight);//可能内容不足一页

          //用getImageData剪裁指定区域，并画到前面创建的canvas对象中
          page.getContext('2d').putImageData(ctx.getImageData(0, renderedHeight, canvas.width, Math.min(imgHeight, canvas.height - renderedHeight)), 0, 0);
          pdf.addImage(page.toDataURL('image/jpeg', 1.0), 'JPEG', 10, 10, a4w, Math.min(a4h, a4w * page.height / page.width));    //添加图像到页面，保留10mm边距

          renderedHeight += imgHeight;
          if (renderedHeight < canvas.height) {
            pdf.addPage();//如果后面还有内容，添加一个空页
          }
          // delete page;
        }
        //保存文件
        pdf.save('事件统计.pdf');
      })

    },

    async queryData() {
      console.log(this.value1)
      var x = {};
      x.create_time_start = this.value1[0];
      x.create_time_relend = this.value1[1];
      const {data} = await statis(x)
      for (var i = 0; i < data.length; i++) {
        this.iconList1[i].num = data[i].num;
      }
      this.getEventByYear();
      this.monthShow();
      this.statisCate();
      this.statisFinish();
      this.value1.split(0, this.value1.length);

    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async getSysSql() {
      const {data} = await getSysSql()
      this.list = data;
    },
    async statisFinish() {
      var s = {};
      if (this.value1 != null) {
        s.create_time_start = this.value1[0];
        s.create_time_relend = this.value1[1];
      }
      const {data} = await statisFinish(s)
      var x = {};
      x.name = '工单完成率';
      x.value = data.finish;
      this.gauge.series[0].data.splice(0, this.gauge.series[0].data.length);
      this.gauge.series[0].data.push(x);
      var y = {};
      y.name = '响应SLA满足率';
      y.value = data.resSla;
      this.gauge2.series[0].data.splice(0, this.gauge2.series[0].data.length);
      this.gauge2.series[0].data.push(y);
      var z = {};
      z.name = '处理SLA满足率';
      z.value = data.dealSla;
      this.gauge3.series[0].data.splice(0, this.gauge3.series[0].data.length);
      this.gauge3.series[0].data.push(z);

    },
    async statisCate() {
      var z = {};
      if (this.value1 != null) {
        z.create_time_start = this.value1[0];
        z.create_time_relend = this.value1[1];
      }
      const {data} = await statisCate(z);

      this.piechart.series[0].data.splice(0, this.piechart.series[0].data.length);
      if (data.categoryName.length != 0) {
        for (var i = 0; i < data.categoryName.length; i++) {
          var x = {};
          x.name = data.categoryName[i];
          x.value = data.numList[i];
          this.piechart.series[0].data.push(x);
        }
      } else {
        var y = {};
        y.name = "暂无数据"
        y.value = 0;
        this.piechart.series[0].data.push(y);
      }


    },
    async monthShow() {
      var z = {};
      if (this.value1 != null) {
        z.create_time_start = this.value1[0];
        z.create_time_relend = this.value1[1];
      }
      const {data} = await eventByMonth(z);
      console.log(data);
      this.linechart.xAxis.data = data.monthList;
      // this.linechart.series.data = data.numList;
      var type = 'line';
      var data1 = data.numList;
      var x = {}
      x.type = type;
      x.data = data1;
      this.linechart.series.splice(0, this.linechart.series.length);
      this.linechart.series.push(x)
    },


    async getEventByYear() {
      var z = {};
      if (this.value1 != null) {
        z.create_time_start = this.value1[0];
        z.create_time_relend = this.value1[1];
      }
      const {data} = await eventByYear(z);
      this.linechart.xAxis.data = data.yearList;
      // this.linechart.series.data = data.numList;
      var type = 'line';
      var data1 = data.numList;
      var x = {}
      x.type = type;
      x.data = data1;
      this.linechart.series.splice(0, this.linechart.series.length);
      this.linechart.series.push(x)
    },
    async getStatic(){
      var x = {};
      const {data} = await statis(x)
      for (var i = 0 ; i < data.length ; i++){
        this.iconList1[i].num = data[i].num;
      }
    },
    async fetchData() {
      this.listLoading = true
      const {
        data,
        totalCount
      } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
<style lang="scss" scoped>
.box_content{padding:20px 0px!important;}
.hiddenmoretop {
  ::v-deep {
    .el-form-item {
      margin-bottom: 15px;
    }

    .el-date-editor.el-input {
      width: 100%;
    }

    .el-date-editor.el-input--prefix .el-input__inner {
      padding-right: 15px;
    }

  }
}

.echarts {
  width: 100%;
}

::v-deep {
  .el-tabs__header {
    margin-bottom: 0 !important;
  }

  .el-tabs__item.is-active {
    background: #fff !important;
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
</style>
