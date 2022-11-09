<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1200px"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.username"
              placeholder="请输入用户名"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="value1" placeholder="-配置项类型-">
              <el-option label="是" value="shanghai"></el-option>
              <el-option label="否" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="value1" placeholder="-配置项子类-">
              <el-option label="是" value="shanghai"></el-option>
              <el-option label="否" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="value1" placeholder="-状态-">
              <el-option label="是" value="shanghai"></el-option>
              <el-option label="否" value="beijing"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="value1"
              placeholder="部门"
              clearable
              @click.native="openbumen"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="openbumen"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              v-model.trim="value1"
              placeholder="领用人"
              clearable
              @click.native="opentakeman"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="opentakeman"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>
    </vab-query-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"
      height="300"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        width="60"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assets_number"
        label="资产编号"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assetsTypeName"
        label="配置项类型"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="childAssetName"
        label="配置子类"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assets_name"
        label="资产名称"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="deptName"
        label="部门"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="receiver_name"
        label="领用人"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="purchaseCompanyName"
        label="购置公司"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_secrecy"
        label="保密"
        align="center"
        sortable
      >
        <template #default="scope">
          <el-tag
            size="mini"
            icon="el-icon-edit"
            v-if="scope.row.is_secrecy == '0'"
            type="primary"
          >
            否
          </el-tag>
          <el-tag
            size="mini"
            icon="el-icon-delete"
            class="red"
            v-if="scope.row.is_secrecy == '1'"
            type="success"
          >
            是
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assetsStatusName"
        label="资产状态"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="itStatusName"
        label="IT状态"
        align="center"
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

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
    <children
      ref="children"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <takeman
      ref="takeman"
      @data="
        {
          value1
        }
      "
    ></takeman>
  </el-dialog>
</template>

<script>
  import {getAssetsManageList} from "@/api/assetsCustomerContract";
  import Children from '@/components/bumenTree'
  import Takeman from './takeman'

  export default {
    name: 'diaTwo',
    components: {Children, Takeman},
    data() {
      return {
        value1: '',
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        list: null,
        tableData: [],
        data: [],
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      showWin() {
        this.title = '添加关联的配置项'
        this.dialogFormVisible = true
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      openbumen() {
        this.$refs['children'].showWin()
      },
      opentakeman() {
        this.$refs['takeman'].showWin()
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this;
        for (var i = 0; i < that.selectRows.length; i++) {
          var flag = that.data.every(function (val, index) {
            return val != that.selectRows[i];
          })
          if (flag) {
            that.data.push(that.selectRows[i]);
          }
        }
        that.$emit('listData', that.data)
        this.close()
      },
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await getAssetsManageList(this.queryForm)
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
  ::v-deep {
    .left-panel {
      .el-input {
        width: 200px !important;
      }
    }
  }
</style>
