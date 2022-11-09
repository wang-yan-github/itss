<template>
  <div class="main-container pb80">
    <template>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="默认报表" name="first">
          <el-card shadow="never" style="border-top: 0">
            <el-form :inline="true" :model="queryForm" @submit.native.prevent>
              <vab-query-form>
                <vab-query-form-left-panel :span="4">
                  <!--                  <el-button icon="el-icon-upload2" type="primary" @click="handleUpload">-->
                  <!--                    导出-->
                  <!--                  </el-button>-->

                </vab-query-form-left-panel>
                <vab-query-form-left-panel :span="24">
                  <el-form-item label="时间范围" style="margin-bottom: 0">
                    <el-date-picker v-model="queryForm.create_time_start" type="date" placeholder="选择日期"
                      value-format="yyyy-MM-dd"></el-date-picker>
                  </el-form-item>
                  <el-form-item label="至" style="margin-bottom: 0">
                    <el-date-picker v-model="queryForm.create_time_end" type="date" placeholder="选择日期"
                      value-format="yyyy-MM-dd"></el-date-picker>
                  </el-form-item>
                  <el-form-item style="margin-bottom: 0">
                    <el-button type="primary" @click="queryData">查询 </el-button>
                  </el-form-item>

                </vab-query-form-left-panel>
              </vab-query-form>
            </el-form>
            <el-card shadow="never" class="card">
              <div class="box_card">
                <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;问题类别数量统计</div>
                <div class="box_content">
                  <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                    :element-loading-text="elementLoadingText" highlight-current-row border>
                    <el-table-column show-overflow-tooltip prop="name" width="200px;" sortable label="类型">
                    </el-table-column>
                    <el-table-column show-overflow-tooltip prop="num" label="数量"></el-table-column>

                  </el-table>
                  <vab-chart ref="chartline" theme="vab-echarts-theme" :options="linechart" />
                </div>
              </div>
            </el-card>
            <el-card shadow="never" class="card">
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <div class="box_card">
                  <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;是否生成知识统计</div>
                  <div class="box_content">
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="knowledge"
                      :element-loading-text="elementLoadingText" highlight-current-row border>
                      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;"
                        sortable>
                      </el-table-column>
                      <el-table-column show-overflow-tooltip prop="category_name" width="200px;" sortable label="类别">
                      </el-table-column>
                      <el-table-column show-overflow-tooltip prop="num" label="数量"></el-table-column>
                    </el-table>
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <vab-chart ref="chartPie" theme="vab-echarts-theme" :options="piechart" />
              </el-col>
            </el-card>
            <el-card shadow="never" class="card">
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <div class="box_card">
                  <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;问题SLA统计</div>
                  <div class="box_content">
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="isFitSla"
                      :element-loading-text="elementLoadingText" highlight-current-row border>
                      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;"
                        sortable>
                      </el-table-column>
                      <el-table-column show-overflow-tooltip prop="status_name" width="200px;" sortable label="是否符合SLA">
                      </el-table-column>
                      <el-table-column show-overflow-tooltip prop="num" label="数量"></el-table-column>

                    </el-table>
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <vab-chart ref="chartPie2" theme="vab-echarts-theme" :options="piechart2" />
              </el-col>
            </el-card>
            <el-card shadow="never" class="card">
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <div class="box_card">
                  <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;问题状态统计</div>
                  <div class="box_content">
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="statusList"
                      :element-loading-text="elementLoadingText" highlight-current-row border>
                      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->

                      <el-table-column show-overflow-tooltip prop="status_name" width="200px;" sortable label="状态">
                      </el-table-column>
                      <el-table-column show-overflow-tooltip prop="num" label="数量"></el-table-column>

                    </el-table>
                  </div>
                </div>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <vab-chart ref="chartPie3" theme="vab-echarts-theme" :options="piechart3" />
              </el-col>
            </el-card>
          </el-card>
        </el-tab-pane>
        <!--        <el-tab-pane label="扩展报表" name="second">-->
        <!--          <el-card shadow="never" style="border-top: 0">-->
        <!--            <div class="box_card">-->
        <!--              <div class="box_content">-->
        <!--                <el-table v-loading="listLoading" ref="listTable" stripe :data="list"-->
        <!--                          :element-loading-text="elementLoadingText"-->
        <!--                          highlight-current-row border>-->
        <!--                  <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;" sortable>-->
        <!--                  </el-table-column>-->
        <!--                  <el-table-column show-overflow-tooltip prop="username" width="200px;" sortable label="标题">-->
        <!--                  </el-table-column>-->
        <!--                  <el-table-column show-overflow-tooltip prop="truename" label="描述"></el-table-column>-->
        <!--                  <el-table-column show-overflow-tooltip prop="truename" label="操作" align="center">-->
        <!--                    <template slot-scope="scope">-->
        <!--                      <el-button type="success" icon="el-icon-view" plain size="mini" @click="handleView">查看</el-button>-->
        <!--                      <el-button type="success" icon="el-icon-upload2" plain size="mini" @click="handleUpload">导出-->
        <!--                      </el-button>-->
        <!--                    </template>-->
        <!--                  </el-table-column>-->
        <!--                </el-table>-->
        <!--              </div>-->
        <!--            </div>-->
        <!--          </el-card>-->
        <!--        </el-tab-pane>-->
      </el-tabs>
    </template>
    <dialogtwo ref="dialogtwo" @fetch-data="
      {
        input2
      }
    "></dialogtwo>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import VabChart from '@/plugins/echarts'

import dialogtwo from '@/components/UserSelectModel'
import { exporQuestionExcel, getQuestionStatistics } from "@/api/questionStatistics";

export default {
  name: 'faqtongji1',
  components: {
    dialogtwo,
    VabChart
  },
  data() {
    return {
      list: null,
      knowledge: null,
      isFitSla: null,
      statusList: null,
      listLoading: true,
      loading: false,
      isShow: false,
      elementLoadingText: '正在加载...',
      activeName: 'first',
      queryForm: {
        category_id: '',
        priority_id: '',
        solve_user: '',
        review_user: '',
        clearance_time_start: '',
        clearance_time_end: '',
        create_time_start: '',
        create_time_end: '',
        clearance_user: '',
        status: '',

      },
      value1: '',
      value2: '',
      linechart: {
        color: ['#66b1ff'],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        xAxis: {
          type: 'category',
          data: []
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: [],
            type: 'bar',
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(180, 180, 180, 0.2)'
            }
          }
        ]
      },
      piechart: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      piechart2: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      piechart3: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },

    }
  },
  created() {
    this.fetchData()
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  watch: {},
  mounted() {

  },
  methods: {
    openwinTwo() {
      this.$refs['dialogtwo'].showWin()
    },
    handleView() {
      this.$router.push({
        path: '/faq/statisticsView',
      })
    },
    handleUpload() {
      this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
        exporQuestionExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '知识统计.xls')
          document.body.appendChild(link)
          link.click()
        })
        this.$baseMessage('数据导出成功', 'success')
      })
    },

    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      this.listLoading = true
      const { data } = await getQuestionStatistics(this.queryForm)
      this.list = data.questionCategoryList
      this.linechart.xAxis.data = []
      this.linechart.series[0].data = []
      for (const index in data.questionCategoryList) {
        this.linechart.xAxis.data.push(data.questionCategoryList[index].name)
        this.linechart.series[0].data.push(data.questionCategoryList[index].num)
      }
      this.knowledge = data.knowledge
      this.piechart.series[0].data = []
      for (const index in data.knowledge) {
        this.piechart.series[0].data.push({ value: data.knowledge[index].num, name: data.knowledge[index].category_name })
      }
      this.isFitSla = data.isFitSla
      this.piechart2.series[0].data = []
      for (const index in data.isFitSla) {
        this.piechart2.series[0].data.push({ value: data.isFitSla[index].num, name: data.isFitSla[index].status_name })
      }
      this.statusList = data.statusList
      this.piechart3.series[0].data = []
      for (const index in data.statusList) {
        this.piechart3.series[0].data.push({ value: data.statusList[index].num, name: data.statusList[index].status_name })
      }
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

  .card {
    border: 0;

    .el-card__body {
      padding-left: 10px;
      border: 0;      

    }
  }

  .el-tabs__header {
    margin-bottom: 0 !important;
  }

  .el-tabs__item.is-active {
    background: #fff !important;
  }

}
</style>
