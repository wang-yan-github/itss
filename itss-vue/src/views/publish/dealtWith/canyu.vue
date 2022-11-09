<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="4">
        <el-button  v-permission="publish_upcoming_me_out" icon="el-icon-upload2" @click="handleUpload">
          导出
        </el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="20">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态" clearable>
                <el-option label="已撤销" value="0"></el-option>
                <el-option label="待评审" value="1"></el-option>
                <el-option label="评审拒绝" value="2"></el-option>
                <el-option label="待测试" value="3"></el-option>
                <el-option label="待发布" value="4"></el-option>
                <el-option label="发布成功" value="5"></el-option>
                <el-option label="发布失败" value="6"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.type" placeholder="发布类型" clearable>
                <el-option label="一般发布" value="1"></el-option>
                <el-option label="紧急发布" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item class="textbox">
              <el-date-picker
                v-model="queryForm.create_time_start"
                type="date"
                placeholder="(Start)创建时间"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
            <el-form-item class="textbox">
              <el-date-picker
                v-model="queryForm.create_time_end"
                type="date"
                placeholder="(End)创建时间"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </div>
          <el-form-item>
            <el-input v-model.trim="queryForm.keyword" @keyup.enter.native="queryData" placeholder="关键字" clearable/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
            <el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">
              高级
            </el-button>
            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>
              高级
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column  label="序号" width="55" align="center">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="issue_no" label="发布单号" align="center" width="200px;" sortable>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="title" width="200px;" sortable label="标题" align="center">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="创建人" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="创建时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="type" label="发布类型" align="center">
        <template #default="scope">
          <div v-if="scope.row.type == '1'">一般发布</div>
          <div v-else-if="scope.row.type == '2'">紧急发布</div>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip  label="评审人" align="center">

        <template #default="scope">
          <span v-if="scope.row.type == '1'">{{scope.row.approver}}</span>
          <span v-else-if="scope.row.type == '2'"></span>
        </template>

      </el-table-column>
      <el-table-column show-overflow-tooltip  label="测试人" align="center">
        <template #default="scope">
          <span v-if="scope.row.type == '1'">{{scope.row.tester_name}}</span>
          <span v-else-if="scope.row.type == '2'"></span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="publisher_name" label="发布人" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="status" label="状态" align="center">
        <template #default="scope">
          <div v-if="scope.row.status == '0'">已撤销</div>
          <div v-else-if="scope.row.status == '1'">待评审</div>
          <div v-if="scope.row.status == '2'">评审拒绝</div>
          <div v-else-if="scope.row.status == '3'">待测试</div>
          <div v-if="scope.row.status == '4'">待发布</div>
          <div v-else-if="scope.row.status == '5'">发布成功</div>
          <div v-else-if="scope.row.status == '6'">发布失败</div>
        </template>
      </el-table-column>

    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
  </div>
</template>

<script>
  import {
    getList,
    doDelete
  } from '@/api/userManagement'
  import {exportPublishParticipateExcel, getPublishListByParticipate} from "@/api/publish";
  import permission from '@/directive/permission/index.js'
  export default {
    name: 'mypublish',
    directives: { permission },
    components: {  },
    data() {
      return {
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        loading: false,
        options: [],
        value:'',
        value1:'',
        value2:'',
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          status: '',
          type:'',
          create_time_start:'',
          create_time_end:'',
          keyword:''
        },
      }
    },
    created() {
      this.fetchData()
    },
    methods: {

      handleCurrentChange(val) {
        this.selectRows = val
      },

      handleUpload() {
        this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
          this.exportMyPublishExcel();
          this.$baseMessage('数据导出成功', 'success')
        })
      },
      async exportMyPublishExcel() {
        exportPublishParticipateExcel().then((res) => {
          const url = URL.createObjectURL(new Blob([res]))
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', '我参与的发布导出.xls')
          document.body.appendChild(link)
          link.click()
        })
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1
        console.log(this.queryForm)
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      remoteMethod(query) {
        if (query !== '') {
          this.loading = true
          setTimeout(() => {
            this.loading = false
            this.options = this.list.filter((item) => {
              return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
            })
          }, 200)
        } else {
          this.options = []
        }
      },
      async fetchData() {
        this.listLoading = true
        const {data} = await getPublishListByParticipate(this.queryForm)
        this.list = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
<style lang="scss" scoped>
  .index-container {
    .hiddenmore {
      display: inline;

      ::v-deep {
        .el-input {
          width: 130px;
        }
        .selbox .el-button{
          padding: 0;
          text-align: center;
        }
        .selbox .el-input{
          width: 100%;
        }
        .textbox .el-input__inner{
          padding-right: 0;
        }
      }
    }
  }
</style>
