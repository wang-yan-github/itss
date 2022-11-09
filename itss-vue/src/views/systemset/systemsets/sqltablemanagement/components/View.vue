<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="70%"
    @close="close"
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <!-- <el-form-item>
            <el-button>日报</el-button>
          </el-form-item>
          <el-form-item>
            <el-button>周报</el-button>
          </el-form-item>
          <el-form-item>
            <el-button>月报</el-button>
          </el-form-item>
          <el-form-item>
            <el-button>季报</el-button>
          </el-form-item> -->
          <el-form-item label="从">
            <el-date-picker
              v-model="start_time"
              @change="dateFormat(1)"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="到">
            <el-date-picker v-model="end_time" @change="dateFormat(2)"></el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-button icon="el-icon-view" type="primary" @click="queryData()">搜索</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="24">
        <!-- <el-button type="primary" icon="el-icon-tickets">上一月报</el-button>
        <el-button type="primary" icon="el-icon-tickets">下一月报</el-button> -->
        <el-button
          type="primary"
          icon="el-icon-position"
          @click="handleExport()" v-if="total!=0"
        >
          导出
        </el-button>
      </vab-query-form-right-panel>
    </vab-query-form>
    <el-card shadow="never">
      <div slot="header">
        <span class="font16 b">{{ headertit }}</span>
      </div>
      <el-table :data="listData" border style="width: 100%">
        <el-table-column
          v-for="(item, index) in listlin"
          :key="index"
          :prop="item"
          :label="item"
          :show-overflow-tooltip="true"
        ></el-table-column>
      </el-table>
      <el-pagination
        background
        :current-page="queryForm.pageNo"
        :page-size="queryForm.pageSize"
        :layout="layout"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      ></el-pagination>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {details, sysSqlExport} from '@/api/userManagement'

  export default {
    name: 'companyviews',
    data() {
      return {
        title: '',
        listLoading: true,
        dialogFormVisible: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        headertit: '',
        start_time: '',
        end_time: '',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          id: '',
          timeStart: '',
          timeEnd: '',
        },
        listlin: [],
        listData: [],
        elementLoadingText: '正在加载...',
        total: 0,
      }
    },
    created() {
    },
    methods: {
      showView(row) {
        this.title = '报表数据'
        this.dialogFormVisible = true
        this.queryForm.id = row.id
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.selectRows = val
        this.queryForm.pageNo = val
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      //获取数据信息
      async fetchData() {
        this.listLoading = true
        const {data} = await details(this.queryForm)
        if (data.page != 0) {
          this.listData = data.page.list
          this.listlin = data.ls
          this.total = data.page.total
          setTimeout(() => {
            this.listLoading = false
          }, 300)
        }
      },
      //导出数据
      handleExport() {
        this.listLoading = true
        sysSqlExport(this.queryForm.id).then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '数据报表.xls')
          document.body.appendChild(link)
          link.click()
          setTimeout(() => {
            this.listLoading = false
          }, 300)
        })
      },
      dateFormat(val) {
        if (val == 1) {
          if ("" != this.start_time && null != this.start_time) {
            var start_time = `${this.start_time.getFullYear()}-${this.start_time.getMonth() + 1 >= 10 ? (this.start_time.getMonth() + 1) : '0' + (this.start_time.getMonth() + 1)}-${this.start_time.getDate() >= 10 ? this.start_time.getDate() : '0' + this.start_time.getDate()}`;
            this.queryForm.timeStart = start_time;
            console.log(this.queryForm.timeStart)
          }
        } else {
          if ("" != this.end_time && null != this.end_time) {
            var end_time = `${this.end_time.getFullYear()}-${this.end_time.getMonth() + 1 >= 10 ? (this.end_time.getMonth() + 1) : '0' + (this.end_time.getMonth() + 1)}-${this.end_time.getDate() >= 10 ? this.end_time.getDate() : '0' + this.end_time.getDate()}`;
            this.queryForm.timeEnd = end_time;
          }
        }
      },
      close() {
        this.dialogFormVisible = false
        this.start_time = "";
        this.end_time = "";
        this.queryForm.timeStart = "";
        this.queryForm.timeEnd = "";
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .el-input {
      width: 150px;
    }
  }
</style>
