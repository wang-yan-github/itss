<!--关联的配置项-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1024px;" @close="close" append-to-body>
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-row :gutter="20">
            <el-col :span="5">
              <el-form-item>
                <el-input v-model.trim="queryForm.keyword" placeholder="关键字" clearable @keyup.enter.native="queryData"/>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-select v-model="queryForm.assets_type_id" placeholder="配置项类型" @change="assetsTypeChange" clearable>
                   <el-option>请选择</el-option>
                  <el-option v-for="item in assetsTypes" :key="item.id" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-select v-model="queryForm.child_asset" placeholder="配置子类" clearable>
                   <el-option>请选择</el-option>
                  <el-option v-for="item in assetsChildren" :key="item.id" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-select v-model="queryForm.it_status" placeholder="IT状态" clearable>
                   <el-option>请选择</el-option>
                  <el-option v-for="item in itStatus" :key="item.id" :label="item.status_name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-input autocomplete="off" placeholder="请选择部门" v-model.trim="queryForm.dept_name" @clear="queryForm.dept_id=''" @focus="handlebumen" clearable></el-input>
                <!--<el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="handlebumen"></el-button>-->
              </el-form-item>
            </el-col>
            <el-col :span="5">
              <el-form-item>
                <el-input v-model.trim="queryForm.receiver_name" placeholder="领用人" clearable @clear="queryForm.receiver=''" @focus="handleSolveUser('receiver')">
                  <!--<el-button slot="append" icon="el-icon-search" @click="handleSolveUser('receiver')"></el-button>-->
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item>
                <el-button icon="el-icon-search" type="primary" @click="queryData">检索</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </vab-query-form-left-panel>

    </vab-query-form>
    <el-table v-loading="listLoading" ref="itemTable" :data="tableData" border highlight-current-row
              :element-loading-text="elementLoadingText" @selection-change="setSelectRows" :row-key="getRowKeys">
      <el-table-column show-overflow-tooltip type="selection" align="center" :reserve-selection="true"></el-table-column>
      <el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" width="160px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型" width="100px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" width="90px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="deptName" label="部门" width="90px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="receiverName" label="领用人" width="70px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" width="100px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" width="70px;" align="center">
        <template #default="scope">
          <el-tag size="mini" icon="el-icon-edit" v-if="scope.row.is_secrecy == '0'" type="primary">否</el-tag>
          <el-tag size="mini" icon="el-icon-delete" class="red" v-if="scope.row.is_secrecy == '1'" type="success">是</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="assetsStatusName" label="资产状态" align="center" width="80px;">
        <template #default="scope">
          <el-tag size="mini" icon="el-icon-delete" class="red" type="success">{{scope.row.assetsStatusName ==null ? '无' : scope.row.assetsStatusName}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="itStatusName" label="IT状态" align="center" width="70px;">
        <template #default="scope">
          <el-tag size="mini" type="success" v-if="scope.row.it_status==1">{{scope.row.itStatusName ==null ? '无' : scope.row.itStatusName}}</el-tag>
          <el-tag size="mini" type="warning" v-else>{{scope.row.itStatusName ==null ? '无' : scope.row.itStatusName}}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <bumen ref="bumen" @deptData="getDept" @fetch-data="fetchData"></bumen>
  </el-dialog>

</template>


<script>
  import {getAssetsManageList} from '@/api/assetsManage'
  //获取配置项子类
  import {getACByAssetsTypeId} from '@/api/assetsChildren'
  //获取it状态
  import {getItStatus} from '@/api/assetsItStatus'
  //获取配置项
  import {getAllAssetsType} from '@/api/assetsType'

  import Users from '@/components/UserSelectModel'
  import Bumen from '@/components/bumenTree'

  export default {
    name: 'assetsTypeDia',
    components: {Users, Bumen},
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: [],
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          assets_type_id: '', //配置类型
          child_asset: '', //配置子类
          it_status: '', //it状态
          assets_name: '', //资产名称
          receiver: '', //领用人
          person_liable: '', //负责人
          dept_id: '', //部门
          dept_name: '',
          receiver_name: '',
          person_liable_name: '',
          keyword: '',
        },
        tableData: [],
        tableData1: [],
        tableData2: [],
        data: [],
        assetsTypes: [],
        assetsChildren: [],
        itStatus: [],
      }
    },
    created() {
      this.queryForm.assets_type_id = this.$route.params.assets_type_id; //undefined
      // this.fetchData();
      this.getAssetsTypes();
      this.getAllItStatus();
      // this.getCompanies();
      // this.getusesCppe();
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      //---------------获取下拉框选项begin---------------------------

      /*--获取配置项类型--*/
      getAssetsTypes() {
        getAllAssetsType().then((res) => {
          if (res.code == 0) {
            this.assetsTypes = res.data
          }
        })
      },

      /*--获取配置项子类--*/
      assetsTypeChange(data) {
        getACByAssetsTypeId({assets_type_id: data}).then((res) => {
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
      //---------------获取下拉框选项end-----------------------------
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
      handlebumen() {
        this.$refs['bumen'].showWin()
      },
      getDept(row) {
        this.queryForm.dept_id = row.id
        this.queryForm.dept_name = row.name
      },
      openwinOne() {
        this.$refs['dialogone'].showWin()
      },
      openwinTwo() {
        this.$refs['dialogtwo'].showWin()
      },
      showWin(row) {
        console.log(row)
        this.title = '添加关联的配置项'
        if (row) {
          this.tableData1 = row
        }
        this.fetchData();
        this.dialogFormVisible = true



      },
      setSelectRows (val) {
        this.tableData2 = val;
      },
      // 保存选中的数据id
      getRowKeys(row) {
        // id 是后台传递的每行信息唯一标识
        return row.id;
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.tableData2 = [];
        this.$refs.itemTable.clearSelection();

        this.dialogFormVisible = false
      },
      save() {
        var that = this;
        // for (var i = 0; i < that.selectRows.length; i++) {
        //   var flag = that.data.every(function (val, index) {
        //     return val != that.selectRows[i];
        //   });
        //   if (flag) {
        //     that.data.push(that.selectRows[i]);
        //   }
        // }
        that.$emit('listData', this.tableData2);

        this.close()
      },
      async fetchData() {
        this.listLoading = true
        const res = await getAssetsManageList(this.queryForm)
        this.tableData = res.data.list
        this.total = res.data.total

        if (this.tableData1 != null && this.tableData1 != [] && this.tableData1 != undefined){
          this.$nextTick(() => {
            for (let i = 0; i < this.tableData.length; i++) {
              for (let j = 0; j <this.tableData1.length ; j++) {
                //两个数组做比对,选中的做勾选
                if(this.tableData1[j].id == this.tableData[i].id) {
                  this.$refs.itemTable.toggleRowSelection(this.tableData[i],true);
                }
              }
            }

          });
        }

        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
