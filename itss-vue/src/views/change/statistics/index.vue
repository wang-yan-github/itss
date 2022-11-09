<template>
  <div class="main-container pb80">
    <template>
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="默认报表" name="first">
          <el-card shadow="never" style="border-top: 0">
            <el-form :inline="true" :model="queryForm" @submit.native.prevent>
              <div class="hiddenmoretop" v-if="isShow">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-date-picker v-model="value1" type="date" placeholder="创建时间(Start)"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-date-picker v-model="value1" type="date" placeholder="创建时间(End)"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select v-model="queryForm.username" placeholder="状态" multiple>
                        <el-option label="待响应" value="1"></el-option>
                        <el-option label="处理中" value="2"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-input v-model.trim="queryForm.username" placeholder="变更类别" clearable
                        @click.native="handleChange">
                        <!--<el-button slot="append" icon="el-icon-search" @click="handleChange"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>


                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select v-model="queryForm.username" placeholder="变更类型" multiple>
                        <el-option label="待响应" value="1"></el-option>
                        <el-option label="处理中" value="2"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-input v-model.trim="queryForm.username" placeholder="标题" clearable
                        @keyup.enter.native="queryData" />
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select v-model="queryForm.username" placeholder="变更来源" multiple>
                        <el-option label="待响应" value="1"></el-option>
                        <el-option label="处理中" value="2"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-date-picker v-model="value1" type="date" placeholder="计划开始时间(Start)"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-date-picker v-model="value1" type="date" placeholder="计划开始时间(End)"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-date-picker v-model="value1" type="date" placeholder="实施时间(Start)"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-date-picker v-model="value1" type="date" placeholder="实施时间(End)"></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-input v-model.trim="queryForm.username" placeholder="方案审核人" clearable
                        @click.native="handleRole">
                        <!--<el-button slot="append" icon="el-icon-search" @click="handleRole"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-input v-model.trim="queryForm.username" placeholder="审批人" clearable
                        @click.native="handleRole">
                        <!--<el-button slot="append" icon="el-icon-search" @click="handleRole"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>

                </el-row>

              </div>
              <vab-query-form>
                <vab-query-form-left-panel :span="4">
                  <!--<el-button icon="el-icon-upload2" type="primary" @click="handleUpload">-->
                  <!--导出-->
                  <!--</el-button>-->

                </vab-query-form-left-panel>
                <vab-query-form-right-panel :span="20">

                  <!--<el-form-item>-->
                  <!--<el-input v-model.trim="queryForm.username" placeholder="关键字" clearable @keyup.enter.native="queryData"/>-->
                  <!--</el-form-item>-->
                  <!--<el-form-item>-->
                  <!--<el-button icon="el-icon-search" type="primary" @click="queryData">-->
                  <!--查询-->
                  <!--</el-button>-->
                  <!--<el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">-->
                  <!--高级-->
                  <!--</el-button>-->
                  <!--<el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>-->
                  <!--高级-->
                  <!--</el-button>-->
                  <!--</el-form-item>-->

                </vab-query-form-right-panel>
              </vab-query-form>
            </el-form>
            <el-card shadow="never" class="card">
              <el-row>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <div class="box_card">
                    <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;变更来源统计</div>
                    <div class="box_content">
                      <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                        highlight-current-row border>
                        <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
                        <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;"
                          sortable>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="name" width="200px;" sortable label="变更来源"
                          align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="value" label="频次" align="center"></el-table-column>

                      </el-table>
                    </div>
                  </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <vab-chart ref="chartPie" theme="vab-echarts-theme" :options="piechart" />
                </el-col>
              </el-row>
            </el-card>
            <el-card shadow="never" class="card">
              <el-row>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <div class="box_card">
                    <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;变更类别数量统计</div>
                    <div class="box_content">
                      <el-table ref="listTable" stripe :data="list2" :element-loading-text="elementLoadingText"
                        highlight-current-row border>
                        <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
                        <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;"
                          sortable>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="name" width="200px;" sortable label=" 变更类别"
                          align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="value" label="频次" align="center"></el-table-column>

                      </el-table>
                    </div>
                  </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <vab-chart ref="chartPie2" theme="vab-echarts-theme" :options="piechart3" />
                </el-col>
              </el-row>
            </el-card>
            <el-card shadow="never" class="card">
              <el-row>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <div class="box_card">
                    <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;变更类型数量统计</div>
                    <div class="box_content">
                      <el-table ref="listTable" stripe :data="list3" :element-loading-text="elementLoadingText"
                        highlight-current-row border>
                        <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
                        <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;"
                          sortable>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="name" width="200px;" sortable label="变更类型"
                          align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="value" label="频次" align="center"></el-table-column>

                      </el-table>
                    </div>
                  </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <vab-chart ref="chartPie3" theme="vab-echarts-theme" :options="piechart3" />
                </el-col>
              </el-row>
            </el-card>

          </el-card>



        </el-tab-pane>
        <!--<el-tab-pane label="扩展报表" name="second">-->
        <!--<el-card shadow="never" style="border-top: 0">-->
        <!--<div class="box_card">-->
        <!--&lt;!&ndash; <div class="box_header"><i class="el-icon-s-data"></i>&nbsp;扩展统计</div> &ndash;&gt;-->
        <!--<div class="box_content">-->
        <!--<el-table v-loading="listLoading" ref="listTable" stripe :data="list"-->
        <!--:element-loading-text="elementLoadingText" highlight-current-row border>-->
        <!--&lt;!&ndash; <el-table-column show-overflow-tooltip type="selection"></el-table-column> &ndash;&gt;-->
        <!--<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;" sortable>-->
        <!--</el-table-column>-->
        <!--<el-table-column show-overflow-tooltip prop="username" width="200px;" sortable label="标题" align="center">-->
        <!--</el-table-column>-->
        <!--<el-table-column show-overflow-tooltip prop="truename" label="描述" align="center"></el-table-column>-->
        <!--<el-table-column show-overflow-tooltip prop="truename" label="操作" align="center">-->
        <!--<template slot-scope="scope">-->
        <!--<el-button type="success" icon="el-icon-view" plain size="mini" @click="handleView">查看</el-button>-->
        <!--<el-button type="success" icon="el-icon-upload2" plain size="mini" @click="handleUpload">导出-->
        <!--</el-button>-->
        <!--</template>-->
        <!--</el-table-column>-->
        <!--</el-table>-->
        <!--</div>-->
        <!--</div>-->
        <!--</el-card>-->
        <!--</el-tab-pane>-->
      </el-tabs>
    </template>

  </div>
</template>

<script>
import * as echarts from 'echarts'
import { sourcesOfStatistics, categoriesOfStatistical, typesOfStatistical } from "@/api/change.js";

import VabChart from '@/plugins/echarts'
import {
  getList,
  doDelete
} from '@/api/userManagement'
export default {
  name: 'biangengtongji',
  components: {
    VabChart,

  },
  data() {
    return {
      list: null,
      list2: null,
      list3: null,
      listLoading: true,
      loading: false,
      isShow: false,
      elementLoadingText: '正在加载...',
      activeName: 'first',
      queryForm: {

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
          data: ['类型11', '类型2', '类型3', '类型4', '类型5', '类型6', '类型7']
        },
        yAxis: {
          type: 'value'
        },
        series: [{
          data: [120, 200, 150, 80, 70, 110, 130],
          type: 'bar',
          showBackground: true,
          backgroundStyle: {
            color: 'rgba(180, 180, 180, 0.2)'
          }
        }]
      },
      piechart: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center'
        },
        series: [{
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
        }]
      },
      piechart2: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center'
        },
        series: [{
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
        }]
      },
      piechart3: {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          left: 'center'
        },
        series: [{
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
        }]
      },

    }
  },
  created() {
    this.getSourcesOfStatistics();
    this.getCategoriesOfStatistical();
    this.getTypesOfStatistical();

  },
  watch: {

  },
  mounted() {

  },
  methods: {
    openwinTwo() {
      // this.$refs['dialogtwo'].showWin()
    },
    handleChange() {
      // this.$refs['dialogChange'].showWin()
    },
    handleView() {
      this.$router.push({
        path: '/biangeng/statisticsBgView',
      })
    },
    handleUpload() {
      this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
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
    getSourcesOfStatistics() {
      var that = this;
      sourcesOfStatistics().then((res) => {
        if (res.code == 0) {
          that.list = res.data;
          that.piechart.series[0].data = that.list
        }
      })
    },
    getCategoriesOfStatistical() {
      var that = this;
      categoriesOfStatistical().then((res) => {
        if (res.code == 0) {
          that.list2 = res.data;
          that.piechart2.series[0].data = that.list2
        }
      })
    },
    getTypesOfStatistical() {
      var that = this;
      typesOfStatistical().then((res) => {
        if (res.code == 0) {
          that.list3 = res.data;
          that.piechart3.series[0].data = that.list3
        }
      })
    },

  },
}
</script>
<style lang="scss" scoped>
.box_content {
  padding: 20px 0px !important;
}

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
    border: 0
  }

  .el-tabs__header {
    margin-bottom: 0 !important;
  }

  .el-tabs__item.is-active {
    background: #fff !important;
  }

}
</style>
