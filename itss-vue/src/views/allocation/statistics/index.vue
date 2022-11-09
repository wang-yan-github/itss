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
                      <el-select
                        clearable
                        v-model="queryForm.assets_type_id"
                        placeholder="配置项类型"
                        @change="assetsTypeChange"
                      >
                        <el-option
                          v-for="item in assetsTypes"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select
                        clearable
                        v-model="queryForm.child_asset"
                        placeholder="配置子类"
                      >
                        <el-option
                          v-for="item in assetsChildren"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select
                        clearable
                        v-model="queryForm.assets_status"
                        placeholder="资产状态"
                      >
                        <el-option
                          v-for="item in statusManage"
                          :key="item.id"
                          :label="item.status_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select
                        clearable
                        v-model="queryForm.it_status"
                        placeholder="IT状态"
                      >
                        <el-option
                          v-for="item in itStatus"
                          :key="item.id"
                          :label="item.status_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-form-item style="width: 150px" class="selbox">
                    <el-input
                      autocomplete="off"
                      placeholder="资产名称"
                      v-model="queryForm.assets_name"
                      @keyup.enter.native="queryData"
                    ></el-input>
                  </el-form-item>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item class="selbox">
                      <el-input
                        v-model.trim="queryForm.receiver_name"
                        placeholder="领用人"
                        clearable
                        @clear="clearQuery('lyr')"
                        @focus="handleSolveUser('receiver')"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="handleSolveUser('receiver')"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item class="selbox">
                      <el-input
                        v-model.trim="queryForm.person_liable_name"
                        placeholder="负责人"
                        clearable
                        @clear="clearQuery('fzr')"
                        @focus="handleSolveUser('liable')"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="handleSolveUser('liable')"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item class="selbox" prop="isvip">
                      <el-input
                        v-model.trim="queryForm.dept_name"
                        placeholder="部门"
                        clearable
                        @clear="clearQuery('bm')"
                        @focus="handlebumen"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="handlebumen"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select
                        clearable
                        v-model="queryForm.purchase_company"
                        placeholder="购置公司"
                      >
                        <el-option
                          v-for="item in companies"
                          :key="item.id"
                          :label="item.company_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select
                        clearable
                        v-model="queryForm.use_scope_id"
                        placeholder="使用范围"
                      >
                        <el-option
                          v-for="item in useScope"
                          :key="item.id"
                          :label="item.scope_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item class="textbox">
                      <el-date-picker
                        v-model="queryForm.start_time"
                        type="date"
                        placeholder="购置创建日期(Start)"
                        value-format="yyyy-MM-dd"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item class="textbox">
                      <el-date-picker
                        v-model="queryForm.end_time"
                        type="date"
                        placeholder="购置创建日期(End)"
                        value-format="yyyy-MM-dd"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
                    <el-form-item>
                      <el-select
                        clearable
                        v-model="queryForm.is_print"
                        placeholder="已打印标签"
                      >
                        <el-option label="是" value="1"></el-option>
                        <el-option label="否" value="0"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
              <vab-query-form>
                <vab-query-form-left-panel :span="4">
                  <!--<el-button-->
                    <!--icon="el-icon-upload2"-->
                    <!--type="primary"-->
                    <!--@click="onClickDownLoad"-->
                  <!--&gt;-->
                    <!--导出-->
                  <!--</el-button>-->
                </vab-query-form-left-panel>
                <vab-query-form-right-panel :span="20">
                  <!--<el-form-item>-->
                    <!--<el-input-->
                      <!--v-model.trim="queryForm.username"-->
                      <!--placeholder="关键字"-->
                      <!--clearable-->
                      <!--@keyup.enter.native="queryData"-->
                    <!--/>-->
                  <!--</el-form-item>-->
                  <!--<el-form-item>-->
                    <!--<el-button-->
                      <!--icon="el-icon-search"-->
                      <!--type="primary"-->
                      <!--@click="queryData"-->
                    <!--&gt;-->
                      <!--查询-->
                    <!--</el-button>-->
                    <!--<el-button-->
                      <!--icon="el-icon-d-arrow-left"-->
                      <!--@click="moreQuery"-->
                      <!--v-if="isShow"-->
                    <!--&gt;-->
                      <!--高级-->
                    <!--</el-button>-->
                    <!--<el-button-->
                      <!--icon="el-icon-d-arrow-right"-->
                      <!--@click="moreQuery"-->
                      <!--v-else-->
                    <!--&gt;-->
                      <!--高级-->
                    <!--</el-button>-->
                  <!--</el-form-item>-->
                </vab-query-form-right-panel>
              </vab-query-form>
            </el-form>
            <div id="pdfDom">
              <el-card shadow="never" class="card">
                <div class="box_card">
                  <div class="box_header">
                    <i class="el-icon-s-data"></i>
                    &nbsp;配置项类别统计
                  </div>
                  <div class="box_content">
                    <el-table
                      v-loading="listLoading"
                      ref="listTable"
                      stripe
                      :data="assetsTypeStatisticsData"
                      :element-loading-text="elementLoadingText"
                      highlight-current-row
                      border
                    >
                      <el-table-column
                        show-overflow-tooltip
                        prop="typeName"
                        label="类型"
                        align="center"
                        width="200px;"
                        sortable
                      ></el-table-column>
                      <el-table-column
                        show-overflow-tooltip
                        prop="count"
                        label="数量"
                        align="center"
                      >
                        <template #default="scope">
                          <el-progress
                            :format="format"
                            :percentage="scope.row.count"
                          ></el-progress>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                </div>
              </el-card>
              <el-card shadow="never" class="card">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <div class="box_card">
                    <div class="box_header">
                      <i class="el-icon-s-data"></i>
                      &nbsp;CI状态统计
                    </div>
                    <div class="box_content">
                      <el-table
                        v-loading="listLoading"
                        ref="listTable"
                        stripe
                        :data="clGroupStatisticsData"
                        :element-loading-text="elementLoadingText"
                        highlight-current-row
                        border
                      >
                        <el-table-column
                          show-overflow-tooltip
                          prop="name"
                          label="类型"
                          align="center"
                          width="200px;"
                          sortable
                        ></el-table-column>
                        <el-table-column
                          show-overflow-tooltip
                          prop="value"
                          label="数量"
                          align="center"
                        >
                          <template #default="scope">
                            <el-progress
                              :format="format"
                              :percentage="scope.row.value"
                            ></el-progress>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <vab-chart
                    ref="chartPie"
                    theme="vab-echarts-theme"
                    :options="piechart"
                  />
                </el-col>
              </el-card>
              <el-card shadow="never" class="card">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <div class="box_card">
                    <div class="box_header">
                      <i class="el-icon-s-data"></i>
                      &nbsp;部门统计
                    </div>
                    <div class="box_content">
                      <el-table
                        v-loading="listLoading"
                        ref="listTable"
                        stripe
                        :data="assetsDeptStatisticsData"
                        :element-loading-text="elementLoadingText"
                        highlight-current-row
                        border
                      >
                        <el-table-column
                          show-overflow-tooltip
                          prop="name"
                          label="类型"
                          align="center"
                          width="200px;"
                          sortable
                        ></el-table-column>
                        <el-table-column
                          show-overflow-tooltip
                          prop="value"
                          label="数量"
                          align="center"
                        >
                          <template #default="scope">
                            <el-progress
                              :format="format"
                              :percentage="scope.row.value"
                            ></el-progress>
                          </template>
                        </el-table-column>
                      </el-table>
                    </div>
                  </div>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <vab-chart
                    ref="chartPie3"
                    theme="vab-echarts-theme"
                    :options="piechart3"
                  />
                </el-col>
              </el-card>
            </div>
          </el-card>
        </el-tab-pane>

      </el-tabs>
    </template>
    <users ref="users" @userData="getUser"></users>
    <bumen ref="bumen" @deptData="getDept"></bumen>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import VabChart from '@/plugins/echarts'
import { getList, doDelete } from '@/api/userManagement'
import { sqlPageListData } from '@/api/userManagement'
import { getAllAssetsType } from '@/api/assetsType'
import { getACByAssetsTypeId } from '@/api/assetsChildren'
import { getItStatus } from '@/api/assetsItStatus'
import { getAllCompanies } from '@/api/assetsCompany'
import { getAllUseScope } from '@/api/assetsUseScope'
import { getAllStatus } from '@/api/assetsStatusManage'
import html2Canvas from 'html2canvas'
import jsPDF from 'jspdf'
import Users from '@/components/UserSelectModel'
import Bumen from '@/components/bumenTree'
import Views from '@/views/systemset/systemsets/sqltablemanagement/components/View'
import {
  assetsTypeStatistics,
  clGroupStatistics,
  assetsDeptStatistics,
} from '@/api/assetsStatistics'
import dialogone from '@/components/bumenTree'
import dialogtwo from '@/components/UserSelectModel'
export default {
  name: 'statistics',
  components: {
    Users,
    Bumen,
    Views,
    VabChart,
  },
  data() {
    return {
      list: [],
      queryForm: {
        assets_type_id: '',
        child_asset: '',
        assets_status: '',
        it_status: '',
        assets_name: '',
        dept_id: '',
        person_liable: '',
        person_liable_name: '',
        receiver: '',
        receiver_name: '',
        purchase_company: '',
        use_scope_id: '',
        start_time: '',
        end_time: '',
        is_print: '',
        dept_name: '',
      },
      assetsTypes: [],
      assetsChildren: [],
      itStatus: [],
      companies: [],
      useScope: [],
      statusManage: [],
      listLoading: true,
      loading: false,
      isShow: false,
      elementLoadingText: '正在加载...',
      activeName: 'first',
      assetsTypeStatisticsData: [],
      clGroupStatisticsData: [],
      assetsDeptStatisticsData: [],
      total: '',
      pageNo: 1,
      pageSize: 10,
      tableData: [
        {
          id: '10001',
          number: 60,
        },
        {
          id: '10001',
          number: 26,
        },
        {
          id: '10001',
          number: 14,
        },
      ],
      value1: '',
      value2: '',

      piechart: {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          left: 'center',
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
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      },
      piechart2: {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          left: 'center',
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: '类别1' },
              { value: 735, name: '类别2' },
              { value: 580, name: '类别3' },
              { value: 484, name: '类别4' },
              { value: 300, name: '类别5' },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      },
      piechart3: {
        tooltip: {
          trigger: 'item',
        },
        legend: {
          left: 'center',
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
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      },
    }
  },
  created() {
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  watch: {},
  mounted() {
    this.getAssetsTypeStatistics()
    this.getclGroupStatistics()
    this.getAssetsDeptStatistics()
    this.getAllAsseysType()
    this.getAllItStatus()
    this.getCompanies()
    this.getusesCppe()
    this.getStatus()
    this.fetchData()
  },
  methods: {
    format(percentage) {
      return percentage
    },
    getAssetsTypeStatistics() {
      assetsTypeStatistics(this.queryForm).then((res) => {
        this.assetsTypeStatisticsData = res.data
      })
    },
    getclGroupStatistics() {
      clGroupStatistics(this.queryForm).then((res) => {
        this.clGroupStatisticsData = res.data
        this.piechart.series[0].data = res.data
      })
    },
    getAssetsDeptStatistics() {
      assetsDeptStatistics(this.queryForm).then((res) => {
        this.assetsDeptStatisticsData = res.data
        this.piechart3.series[0].data = res.data
      })
    },
    getAllAsseysType() {
      getAllAssetsType().then((res) => {
        this.assetsTypes = res.data
      })
    },
    /*--获取配置项子类--*/
    assetsTypeChange(data) {
      console.log(data)
      this.queryForm.child_asset = null
      getACByAssetsTypeId({ assets_type_id: data }).then((res) => {
        if (res.code == 0) {
          this.assetsChildren = res.data
        }
      })
    },

    /*--获取it状态--*/
    getAllItStatus() {
      getItStatus().then((res) => {
        if (res.code == 0) {
          this.itStatus = res.data
        }
      })
    },

    /*--获取购置公司--*/
    getCompanies() {
      getAllCompanies().then((res) => {
        if (res.code == 0) {
          this.companies = res.data
        }
      })
    },

    /*--获取购置公司--*/
    getusesCppe() {
      getAllUseScope().then((res) => {
        if (res.code == 0) {
          this.useScope = res.data
        }
      })
    },
    /*--获取配置状态--*/
    getStatus() {
      getAllStatus().then((res) => {
        if (res.code == 0) {
          this.statusManage = res.data
        }
      })
    },
    /** 清空弹窗查询条件 */
    clearQuery(type) {
      switch (type) {
        case 'fzr':
          this.queryForm.person_liable = ''
          break
        case 'lyr':
          this.queryForm.receiver = ''
          break
        case 'bm':
          this.queryForm.dept_id = ''
          break
      }
    },

    handleSolveUser(type) {
      switch (type) {
        case 'receiver':
          this.selectType = 'receiver'
          this.$refs['users'].showWin()
          break
        case 'liable':
          this.selectType = 'liable'
          this.$refs['users'].showWin()
      }
    },
    handlebumen() {
      this.$refs['bumen'].showWin()
    },
    getUser(row) {
      switch (this.selectType) {
        case 'receiver':
          this.queryForm.receiver = row.id
          this.queryForm.receiver_name = row.name
          break
        case 'liable':
          this.queryForm.person_liable = row.id
          this.queryForm.person_liable_name = row.name
          break
      }
    },
    getDept(row) {
      this.queryForm.dept_id = row.id
      this.queryForm.dept_name = row.name
    },
    handleView(row) {
      if (row.id) {
        this.$refs['views'].showView(row)
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    //获取数据信息
    async fetchData() {
      this.listLoading = true
      const { data } = await sqlPageListData({
        module: 6,
        pageNo: this.pageNo,
        pageSize: this.pageSize,
      })
      var page = data.page
      this.list = page.list
      this.total = page.total
      // this.sqlModule = data.sqlModule
      // this.sqlIsUse = data.sqlIsUse
      // this.sqlTimechoose = data.sqlTimechoose
      // this.sqlScope = data.sqlScope
      // this.sqlDisplayMode = data.sqlDisplayMode
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.pageNo = val
      this.fetchData()
    },
    handleUpload() {
      this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
        this.$baseMessage('数据导出成功', 'success')
      })
    },

    queryData() {
      this.getAssetsTypeStatistics()
      this.getclGroupStatistics()
      this.getAssetsDeptStatistics()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },

    // 导出
    onClickDownLoad() {
      //htmlToPdf.getPdf('下载名称');

      html2Canvas(document.querySelector('#pdfDom'), {
        allowTaint: false,
        taintTest: false,
        logging: false,
        useCORS: true,
        dpi: window.devicePixelRatio * 4, //将分辨率提高到特定的DPI 提高四倍
        scale: 4, //按比例增加分辨率
      }).then((canvas) => {
        var pdf = new jsPDF('p', 'mm', 'a4') //A4纸，纵向
        var ctx = canvas.getContext('2d'),
          a4w = 190,
          a4h = 277, //A4大小，210mm x 297mm，四边各保留10mm的边距，显示区域190x277
          imgHeight = Math.floor((a4h * canvas.width) / a4w), //按A4显示比例换算一页图像的像素高度
          renderedHeight = 0

        while (renderedHeight < canvas.height) {
          var page = document.createElement('canvas')
          page.width = canvas.width
          page.height = Math.min(imgHeight, canvas.height - renderedHeight) //可能内容不足一页

          //用getImageData剪裁指定区域，并画到前面创建的canvas对象中
          page
            .getContext('2d')
            .putImageData(
              ctx.getImageData(
                0,
                renderedHeight,
                canvas.width,
                Math.min(imgHeight, canvas.height - renderedHeight)
              ),
              0,
              0
            )
          pdf.addImage(
            page.toDataURL('image/jpeg', 1.0),
            'JPEG',
            10,
            10,
            a4w,
            Math.min(a4h, (a4w * page.height) / page.width)
          ) //添加图像到页面，保留10mm边距

          renderedHeight += imgHeight
          if (renderedHeight < canvas.height) {
            pdf.addPage() //如果后面还有内容，添加一个空页
          }
          // delete page;
        }
        //保存文件
        pdf.save('事件统计.pdf')
      })
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
      .el-date-editor.el-input--prefix .el-input__inner{
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
  .card{border:0;
  .el-card__body{
     padding-left:10px;
  }
}
  .el-tabs__item.is-active {
    background: #fff !important;
  }
}
</style>
