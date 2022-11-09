<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-descriptions class="margin-top" title="" :column="3" border>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          用户名
        </template>
        {{ form.user_name }}
      </el-descriptions-item>
      <el-descriptions-item >
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          姓名
        </template>
        {{ form.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          状态
        </template>
        <el-tag v-if="form.status == 1">活动</el-tag>
        <el-tag v-else-if="form.status == 2">暂停</el-tag>
        <el-tag v-else-if="form.status == 3">已禁止</el-tag>
        <el-tag v-else>暂无</el-tag>
      </el-descriptions-item>
      <!--<el-descriptions-item v-show="form.user_type">-->
      <!--<template slot="label">-->
      <!--<i class="el-icon-tickets"></i>-->
      <!--类型-->
      <!--</template>-->
      <!--<el-tag size="small">{{ form.user_type }}</el-tag>-->
      <!--</el-descriptions-item>-->
    </el-descriptions>
    <el-table

      :data="tableData"
      ref="itemTable"
      border
      :element-loading-text="elementLoadingText"

      @select="setSelectRows"
      height="400"
      style="margin-top: 20px"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        align="center"
      ></el-table-column>
<!--      <el-table-column-->
<!--        show-overflow-tooltip-->
<!--        label="序号11"-->
<!--        width="60px;"-->
<!--        align="center"-->
<!--      >-->
<!--        <template slot-scope="scope">-->
<!--          {{(queryForm.pageIndex-1) * queryForm.pageSize+scope.$index+1}}-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="60px;">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        sortable
        width="200px"
        label="名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="description"
        label="描述"
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
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    selectAllRoleByPage,
    addUserRole,
    getOneUser,
    getRoleIdByUser,
  } from '@/api/userManagement'

  export default {
    name: 'userroles',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        total1: 0,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          name: '',
        },
        tableData: [],
        tableData1: [],
        form: {
          id: '',
          user_name: '',
          name: '',
          status: '',
          user_type: '',
          sysRoles: [],
        },
        temp: [],
      }
    },
    created() {
      // setTimeout(() => {
      //   // this.getAllRole()
      //   this.listLoading = false
      // }, 300)
      // this.$nextTick(() => {
      //   for (let i = 0; i < this.fileUploadData.length; i++) {
      //     this.$refs.itemTable.toggleRowSelection(this.fileUploadData[i])
      //   }
      // })
    },
    methods: {
      showWin(row) {
        this.title = '设置角色'
        this.dialogFormVisible = true
        console.log(row);
        if (row) {
          this.form = row
        }
        this.getAllRole();
        this.getOneRole();
      },
      setSelectRows(val,row) {


        if (!val) {
          for (var i = 0; i < this.tableData1.length; i++) {
            if (this.tableData1[i].role_id == row.id) {
              this.tableData1.splice(i, 1)
            }
          }
        }else {

          var flag = true;
          for (var i = 0; i < this.tableData1.length; i++){
            if (row.id == this.tableData1[i].role_id){
               flag = false;
            }
          }

          if (flag) {
            var x = {};
            x.role_id = row.id
            this.tableData1.push(x)
          } else {
            for (var i = 0; i < this.tableData1.length; i++) {
              if (this.tableData1[i].role_id == row.id) {
                this.tableData1.splice(i, 1)
              }
            }
          }
        }
        console.log(this.tableData1);

      },
      queryData() {
        this.queryForm.pageNo = 1
        this.getAllRole()
      },
      handleSizeChange(val) {

        this.queryForm.pageSize = val
        this.getAllRole()
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val
        this.getAllRole()
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.$emit('fetch-data')
        this.tableData = [];
        this.tableData1 = [];
        this.dialogFormVisible = false
      },
      save() {

        // for (var i = 0 ; i < this.selectRows.length ; i ++){
        //   this.temp.push(this.selectRows[i]);
        // }
        // this.form.sysRoles = this.temp;
        var temp = [];
        for(var i = 0 ; i<this.tableData1.length ; i++){
          var x = {};
          x.id = this.tableData1[i].role_id;
          temp.push(x);
        }
        this.form.sysRoles = temp;
        const {data} = addUserRole(this.form)

        this.close()
      },

      async getAllRole() {
        // this.listLoading = true
        const {data} = await selectAllRoleByPage(this.queryForm)
        this.tableData = data.list
        this.total = data.total
        console.log(this.tableData);
        console.log(this.tableData1);
        this.$nextTick(() => {
          for (let i = 0; i < this.tableData.length; i++) {
            for (let j = 0; j <this.tableData1.length ; j++) {
              //两个数组做比对,选中的做勾选
              if(this.tableData1[j].role_id==this.tableData[i].id) {
                this.$refs.itemTable.toggleRowSelection(this.tableData[i],true);
              }
            }
          }

        });
        // setTimeout(() => {
        //   this.listLoading = false
        // }, 1000)




      },


      async getOneRole(){
        const {data} = await getRoleIdByUser(this.form)
        this.tableData1 = data;


      }


    },
  }
</script>
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 80px;
  }
</style>
