<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="6">
        <el-button v-permission="pzgl_overview_import" icon="el-icon-download" type="primary" @click="handleDownload">导入</el-button>
        <el-button v-permission="pzgl_overview_out" icon="el-icon-upload2" type="success" @click="handleUpload">导出</el-button>

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="18">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-select v-model="queryForm.assets_type_id" placeholder="配置项类型" clearable>
                <el-option v-for="item in this.assetsTypeList" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.child_asset" placeholder="配置子类" clearable>
                <el-option v-for="item in this.assetsChildrenList" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.it_status" placeholder="IT状态" clearable>
                <el-option v-for="item in this.assetsConfigStatusList" :key="item.id" :label="item.name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item style="width: 150px;" class="selbox">
              <el-input autocomplete="off" v-model="queryForm.assets_name" placeholder="资产名称" clearable @keyup.enter.native="queryData"></el-input>
            </el-form-item>
            <el-form-item style="width: 150px;" class="selbox">
              <el-input autocomplete="off" v-model="queryForm.receiver" placeholder="领用人" clearable @keyup.enter.native="queryData"></el-input>
            </el-form-item>
            <el-form-item style="width: 150px;" class="selbox">
              <el-input autocomplete="off" v-model="queryForm.person_liable" placeholder="负责人" clearable @keyup.enter.native="queryData"></el-input>
            </el-form-item>
            <el-form-item style="width: 150px;" class="selbox">
              <el-select v-model="queryForm.dept_name" filterable remote reserve-keyword placeholder="部门" :remote-method="remoteMethod" :loading="loading" style="width: 80%" clearable></el-select>
              <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwin"></el-button>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.purchase_company" placeholder="购置公司" clearable>
                <el-option v-for="item in this.assetsCompanyList" :key="item.id" :label="item.company_name" :value="item.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.use_scope_id" placeholder="使用范围" clearable>
                <el-option v-for="item in this.assetsUseScopeList" :key="item.id" :label="item.scope_name" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.purchase_date_start" type="date" placeholder="(Start)购置创建日期" clearable>
              </el-date-picker>
            </el-form-item>
            <el-form-item class="textbox">
              <el-date-picker v-model="queryForm.purchase_date_end" type="date" placeholder="(End)购置创建日期" clearable>
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-select placeholder="已打印标签" clearable>
                <el-option label="区域一" value="shanghai"></el-option>
                <el-option label="区域二" value="beijing"></el-option></el-select>
            </el-form-item>

          </div>
          <el-form-item>
            <el-input v-model.trim="queryForm.keyword" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button v-permission="pzgl_overview_select"  icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
<!--            <el-button icon="el-icon-d-arrow-left" @click="moreQuery" v-if="isShow">-->
<!--              高级-->
<!--            </el-button>-->
<!--            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>-->
<!--              高级-->
<!--            </el-button>-->
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>
    <el-card shadow="never" class="allcon">
      <div class="box_card" v-for="item in CIList" :key="item.id">
        <div class="box_header">{{item.group_name}}</div>
        <div class="box_content">
          <template v-for="assetsType in item.assetsTypeList">
            <div class="allocation" :key="assetsType.id" v-if="assetsType.group_id==item.id">
              <el-card shadow="hover">
                <el-image style="width: 100px; height: 100px" :src="assetsType.pic_path" fit="contain"></el-image>
                <h5 @click="tolist(assetsType.id)">{{ assetsType.name }}({{assetsType.num}})</h5>
                <div @click="allcationAdd(assetsType)">
                  <el-link v-permission="pzgl_overview_add">添加</el-link>
                </div>
              </el-card>
            </div>
          </template>
        </div>
      </div>
    </el-card>

    <children
      ref="children"
      @deptData="deptset"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <userimport ref="userimport"></userimport>
  </div>
</template>

<script>
import {getList, overviewList} from '@/api/assetsClGroup'
import { getAssetsTypeList } from '@/api/configtypemanagement'
import { getAssetsChildrenList } from '@/api/assetsChildren'
import { getAssetsConfigStatusList } from '@/api/assetsConfigStatus'
import { getAssetsCompanyList } from '@/api/assetsCompany'
import { getAssetsUseScopeList } from '@/api/assetsUseScope'
import { getAllAssetsManageList } from '@/api/assetsManage'
import permission from '@/directive/permission/index.js'

import Userimport from './components/UserImport'
import Children from '@/components/bumenTree'
export default {
  name: 'myknowledgeadd1',
  directives: { permission },
  components: {
    Userimport,
    Children,
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
      elementLoadingText: '正在加载...',
      queryForm: {
        assets_type_id: '',
        child_asset: '',
        it_status: '',
        assets_name: '',
        receiver: '',
        person_liable: '',
        dept_id: '',
        purchase_company: '',
        use_scope_id: '',
        purchase_date_start: '',
        purchase_date_end: '',
        dept_name: '',
      },
      CIList: '',
      assetsTypeList: '',
      assetsChildrenList: '',
      assetsConfigStatusList: '',
      assetsCompanyList: '',
      assetsUseScopeList: '',
      assetsManageList: [],
      assetsManageNum:'',
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
    }
  },
  created() {
    this.fetchCIData()
    // this.fetchAssetsTypeData()
    this.queryData()
  },
  methods: {
    tolist(id) {
      this.$router.push({
        path: '/pzgl/tolist',
        name: 'tolist',
        params: { assets_type_id: id },
      })
    },
    // 判断是否为空
    isEmpty(obj) {
      if (typeof obj == 'undefined' || obj == null || obj == '') {
        return true
      } else {
        return false
      }
    },
    allcationAdd(data) {
      if (this.isEmpty(data.add_address)) {
        this.$message.error('请先添加地址')
        return
      }
      this.$router.push({
        path: data.add_address,
        // name: data.code,
        query: { id: data.id },
      })
    },
    handleUpload() {
      this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
        this.$baseMessage('数据导出成功', 'success')
      })
    },
    handleDownload() {
      this.$refs['userimport'].showWin()
    },
    openwin() {
      this.$refs['children'].showWin()
    },
    deptset(data) {
      this.queryForm.dept_id = data.id
      this.queryForm.dept_name = data.name
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchAssetsManageData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchAssetsManageData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchCIData()
      // this.fetchAssetsManageData()
    },
    moreQuery() {
      this.isShow = !this.isShow
      this.fetchAssetsChildrenData()
      this.fetchAssetsConfigStatusData()
      this.fetchAssetsCompanyData()
      this.fetchAssetsUseScopeData()
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
    //概览图标列表展示
    async fetchCIData() {
      this.queryForm.pageSize = 1000000
      this.listLoading = true
      const { data } = await overviewList(this.queryForm)
      this.CIList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    // async fetchAssetsTypeData() {
    //   this.listLoading = true
    //   const { data } = await getAssetsTypeList({})
    //   this.assetsTypeList = data
    //   setTimeout(() => {
    //     this.listLoading = false
    //   }, 300)
    // },
    async fetchAssetsChildrenData() {
      this.listLoading = true
      const { data } = await getAssetsChildrenList({})
      this.assetsChildrenList = data.list
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async fetchAssetsConfigStatusData() {
      this.listLoading = true
      const { data } = await getAssetsConfigStatusList({})
      this.assetsConfigStatusList = data.list
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async fetchAssetsCompanyData() {
      this.listLoading = true
      const { data } = await getAssetsCompanyList({})
      this.assetsCompanyList = data.list
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async fetchAssetsUseScopeData() {
      this.listLoading = true
      const { data } = await getAssetsUseScopeList({})
      this.assetsUseScopeList = data.list
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    // 筛选指定字段
    filterId(id) {
      return this.assetsManageList.filter(function (item) {
        if (item.assets_type_id == id) {
          return item
        }
      })
    },
    async fetchAssetsManageData() {
      this.listLoading = true
      const { data } = await getAllAssetsManageList(this.queryForm)
      this.assetsManageList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
<style lang="scss" scoped>
.box_content{padding:20px 0px!important;}
.index-container {
  .hiddenmore {
    display: inline;

    ::v-deep {
      .el-input {
        width: 150px;
      }

      .selbox .el-button {
        padding: 0;
        text-align: center;
      }

      .selbox .el-input {
        width: 100%;
      }

      .textbox .el-input__inner {
        padding-right: 0;
      }
    }
  }

  ::v-deep {
    .allcon{border:0;padding:0;margin:0;}
    .allcon .el-card__body {
      padding-left:10px;
      padding-bottom: 0;      
      .allocation .el-card__body {
        padding-bottom: 20px;
      }
    }
  }

  .box_content {
    display: -webkit-flex;
    /* Safari */
    display: flex;
    flex-wrap: wrap;
  }

  .allocation {
    margin: 0 20px 30px 0;
    width: 160px;
    height: 160px;
    text-align: center;
  }

  .allocation h5 {
    margin: 0 0 6px;
  }
}
</style>
