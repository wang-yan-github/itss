<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="600px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="名称" prop="resource_name">
        <el-input v-model.trim="form.resource_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <!--<el-form-item label="客户合同" prop="contractId">-->
      <!--<el-input-->
      <!--v-model.trim="form.contractName"-->
      <!--clearable-->
      <!--@click.native="openContract"-->
      <!--&gt;-->
      <!--<el-button-->
      <!--slot="append"-->
      <!--icon="el-icon-search"-->
      <!--@click="openContract"-->
      <!--&gt;</el-button>-->
      <!--</el-input>-->
      <!--</el-form-item>-->
      <el-form-item prop="kd">
        <template>
          <!-- `checked` 为 true 或 false -->
          <el-checkbox v-model="is_def_engineer" >工程师开单默认</el-checkbox>
          <el-checkbox v-model="is_def_wechat">微信自助报障默认</el-checkbox>
          <el-checkbox v-model="is_def_webpage">网页自助报障默认</el-checkbox>
          <!--<el-checkbox v-model="checked3">400热线开单默认</el-checkbox>-->
        </template>
      </el-form-item>
      <el-form-item label="描述" prop="">
        <el-input v-model.trim="form.description" autocomplete="off" type="textarea" :rows="10"></el-input>
      </el-form-item>

    </el-form>

    <!--<customerContractDia-->
    <!--ref="customerContractDia"-->
    <!--@listData="customerContractDataSet"-->
    <!--@fetch-data=""-->
    <!--&gt;</customerContractDia>-->

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    save, info, edit, getList,getAll
  } from '@/api/origin'

  import customerContractDia from '@/components/customerContractDia'

  export default {
    name: 'eventSourceedit',
    components: {customerContractDia},
    data() {
      return {
        allList: [],
        isDisable: false,
        is_def_engineer: false,
        is_def_wechat: false,
        is_def_webpage: false,
        form: {
          id: '',
          resource_name: '',
          sort: '',
          is_use: '',
          description: '',
          customer_contract_id: '',
          contractName: ''

        },
        rules: {
          resource_name: [{
            required: true,
            trigger: 'blur',
            message: '请输入名称'
          },],
          is_use: [{
            required: true,
            trigger: 'blur',
            message: '请选择状态'
          }],
          // kd: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请选择开单默认'
          // }],
          // contractId: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请选择客户合同'
          // }],

        },
        title: '',
        dialogFormVisible: false,
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          resource_name: '',
        }
      }
    },
    created() {


    },
    props: ['sendData'],
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
          this.is_def_engineer = false
          this.is_def_wechat = false
          this.is_def_webpage = false
        } else {
          console.log(row);
          this.title = '编辑'
          this.form = Object.assign({}, row)
          console.log("data")
          console.log(this.form)
          if (row.is_def_engineer == 1) {
            this.is_def_engineer = true;
          } else if (row.is_def_engineer == 0) {
            this.is_def_engineer = false;
          }

          if (row.is_def_webpage == 1) {
            this.is_def_webpage = true;
          } else if (row.is_def_webpage == 0) {
            this.is_def_webpage = false;
          }

          if (row.is_def_wechat == 1) {
            this.is_def_wechat = true;
          } else if (row.is_def_wechat == 0) {
            this.is_def_wechat = false;
          }

        }
        this.getAllList();
        this.dialogFormVisible = true
      },
      openContract() {
        this.$refs['customerContractDia'].showWin()
      },
      customerContractDataSet(row) {
        console.log("customerContractDataSet")
        console.log(row)
        this.form.customer_contract_id = row.id;
        this.form.contractName = row.contract_name;
        console.log(this.form.contractName)
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },

      async getAllList(){
        getAll({}).then((res) => {
          this.allList = res.data;
        })
      },

      save() {
        this.isDisable = true;
        var that = this;
        console.log(that.form)
        that.$refs['form'].validate(async (valid) => {
          if (valid) {

            if (that.form.id == null || that.form.id == undefined || that.form.id == "") {
              if (that.is_def_engineer == true) {
                that.form.is_def_engineer = "1"
              } else {
                that.form.is_def_engineer = "0"
              }

              if (that.is_def_wechat == true) {
                that.form.is_def_wechat = "1"
              } else {
                that.form.is_def_wechat = "0"
              }

              if (that.is_def_webpage == true) {
                that.form.is_def_webpage = "1"
              } else {
                that.form.is_def_webpage = "0"
              }

              var count = 0 ;
              var count1 = 0 ;
              var count2 = 0
              for(var i = 0 ; i < that.allList.length; i++) {
                if (that.allList[i].is_def_engineer == that.form.is_def_engineer && that.form.is_def_engineer == "1") {
                  count ++ ;
                }
                if (that.allList[i].is_def_wechat == that.form.is_def_wechat && that.form.is_def_wechat == "1"){
                  count1 ++ ;
                }
                if (that.allList[i].is_def_webpage == that.form.is_def_webpage && that.form.is_def_webpage == "1" ){
                  count2++ ;
                }
              }
              if (count != 0){
                that.$baseMessage("工程师开单默认已被选择", 'warning')
                this.isDisable = false;
                return ;
              }
              if (count1 != 0){
                that.$baseMessage("微信自助报障默认已被选择", 'warning')
                this.isDisable = false;
                return ;
              }
              if (count2 != 0){
                that.$baseMessage("网页自助报障默认已被选择", 'warning')
                this.isDisable = false;
                return ;
              }


              save(that.form).then((res) => {
                // that.$baseMessage(msg, 'success')
                that.$emit('fetch-data')
                that.close()
              });
            } else {
              if (that.is_def_engineer == true) {
                that.form.is_def_engineer = "1"
              } else {
                that.form.is_def_engineer = "0"
              }

              if (that.is_def_wechat == true) {
                that.form.is_def_wechat = "1"
              } else {
                that.form.is_def_wechat = "0"
              }

              if (that.is_def_webpage == true) {
                that.form.is_def_webpage = "1"
              } else {
                that.form.is_def_webpage = "0"
              }


              var count = 0 ;
              var count1 = 0 ;
              var count2 = 0
              for(var i = 0 ; i < that.allList.length; i++) {
                if (that.allList[i].is_def_engineer == that.form.is_def_engineer && that.form.is_def_engineer == "1" && that.allList[i].id != that.form.id) {
                  count ++ ;
                }
                if (that.allList[i].is_def_wechat == that.form.is_def_wechat && that.form.is_def_wechat == "1" && that.allList[i].id != that.form.id){
                  count1 ++ ;
                }
                if (that.allList[i].is_def_webpage == that.form.is_def_webpage && that.form.is_def_webpage == "1"  && that.allList[i].id != that.form.id){
                  count2++ ;
                }
              }
              if (count != 0){
                that.$baseMessage("工程师开单默认已被选择", 'warning')
                this.isDisable = false;
                return ;
              }
              if (count1 != 0){
                that.$baseMessage("微信自助报障默认已被选择", 'warning')
                this.isDisable = false;
                return ;
              }
              if (count2 != 0){
                that.$baseMessage("网页自助报障默认已被选择", 'warning')
                this.isDisable = false;
                return ;
              }

              edit(that.form).then((res) => {
                if (res.code == 0) {
                  // that.$baseMessage(data.msg, 'success');
                  that.$emit('fetch-data');
                  that.close();
                } else {
                  that.$baseMessage("失败", 'error');
                  that.isDisable = false;
                }

              });
            }

          } else {
            that.isDisable = false;
            return false
          }
        })
      },
      fetchData() {
        var that = this;
        getList(that.queryForm).then((res) => {
          that.list = res.data.list;
          that.total = res.data.total
        });
      }
    },
  }
</script>
