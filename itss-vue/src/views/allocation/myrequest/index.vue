<template>
  <div class="index-container">
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.type" placeholder="类型" clearable>
                <el-option>请选择</el-option>
                <el-option v-for="item in this.assetsRequestConfig" :key="item.id" :label="item.request_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态" clearable>
                <el-option>请选择</el-option>
                <el-option v-for="item in this.dictQequestTypes" :key="item.value" :label="item.label" :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input autocomplete="off" v-model="queryForm.create_name" placeholder="创建人" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.start_time" type="date" value-format="yyyy-MM-dd" placeholder="(Start)创建时间"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.end_time" type="date" value-format="yyyy-MM-dd" placeholder="(End)创建时间"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input autocomplete="off" v-model="queryForm.deal_user_name" placeholder="处理人" clearable></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.deal_start_time" type="date" value-format="yyyy-MM-dd" placeholder="(Start)处理时间"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.deal_end_time" type="date" value-format="yyyy-MM-dd" placeholder="(End)处理时间"></el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>

      </div>

      <vab-query-form>
        <vab-query-form-left-panel :span="4">
          <el-button v-permission="pzgl_myrequire_add" icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        </vab-query-form-left-panel>

        <vab-query-form-right-panel :span="20">
          <el-form-item>
            <el-input v-model.trim="queryForm.keyword" @keyup.enter.native="queryData" placeholder="关键字" clearable />
          </el-form-item>
          <el-form-item>
            <el-button v-permission="pzgl_myrequire_select" icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
            <el-button v-permission="pzgl_myrequire_select" icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">高级</el-button>
            <el-button v-permission="pzgl_myrequire_select" icon="el-icon-d-arrow-right" @click="moreQuery" v-else>高级</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>

    </el-form>


    <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
              highlight-current-row border @current-change="handleCurrentChange">
      <el-table-column show-overflow-tooltip  label="序号" align="center" width="70px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="req_NO" width="140px;" align="center" sortable label="请求编号">
        <template #default="{ row }">
          <el-link @click="handleView(row)">{{row.req_NO}}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="request_name" label="请求类型" align="center" width="160px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="content" label="内容" header-align="center" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="department_name" label="部门" align="center" width="120px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_name" label="创建人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="创建时间" align="center" width="160px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="deal_user_name" label="处理人" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="deal_time" label="处理时间" align="center" width="160px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="label" label="状态" align="center" width="100px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="truename" label="操作" align="center" width="150px;">
        <template #default="{ row }">
          <el-button v-permission="pzgl_myrequire_edit" plain @click="handleEdit(row)" type="primary" size="mini" v-if="row.status == 1 || row.status == 4">修改</el-button>
          <el-button v-permission="pzgl_myrequire_back" plain @click="handleCancel(row)" type="danger" size="mini" v-if="row.status == 1 || row.status == 4">撤销</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>

    <dialogcancle ref="dialogcancle" @fetch-data="fetchData"></dialogcancle>

  </div>
</template>

<script>
  import {toList, getAllAssetsRequestConfig} from '@/api/assetsconfigreq'
  import {getDict} from '@/api/change'

  import dialogcancle from './components/cancle'
  import permission from '@/directive/permission/index.js'

  export default {
    name: 'myAssetsConfigReq',
    directives: { permission },
    components: {
      dialogcancle
    },
    data() {
      return {
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        loading: false,
        options: [],
        value: '',
        value1: '',
        value2: '',
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        assetsRequestConfig: [],
        dict: {dict_type: 'asset_request_status'},
        dictQequestTypes: [],
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          data_flag: '1',
          type: '',
          status: '',
          create_name: '',
          start_time: '',
          end_time: '',
          deal_start_time: '',
          deal_end_time: '',
          deal_user_name: ''
        },
        srcList: []
      }
    },
    created() {
      this.fetchData()
      this.getAllAssetsRequestConfig();
      // 请求类型
      this.getDictQequestType();
    },
    methods: {
      //查看详情
      handleView(row) {
        this.$router.push({
          path: '/pzgl/assetsConfigView',
          query: {
            id: row.id
          }
        })
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      async getDictQequestType() {

        const {data} = await getDict(this.dict);
        this.dictQequestTypes = data

        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      async getAllAssetsRequestConfig() {

        const {data} = await getAllAssetsRequestConfig()
        this.assetsRequestConfig = data
        console.log(this.assetsRequestConfig)
        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      handleAdd() {
        this.$router.push({
          path: '/pzgl/myRequestAdd',
        })
      },
      handleCancel(row) {
        if (row.id != '' && row.id != null) {
          this.$refs['dialogcancle'].showEdit(row.id)

        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },

      handleEdit(row) {
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/pzgl/myRequestEdit',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
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
        const {
          data,
          totalCount
        } = await toList(this.queryForm)
        console.log(data);
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

    .mr5 {
      margin-right: 5px;
    }
  }
</style>
