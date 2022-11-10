<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-card class="cardbox" shadow="never">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="名称" prop="name">
              <el-input v-model.trim="form.name" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="状态" prop="is_use">
              <el-select v-model="form.is_use" placeholder="请选择">
                <el-option label="禁用" value="0"></el-option>
                <el-option label="启用" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="描述" prop="description">
              <el-input v-model.trim="form.description" autocomplete="off" :rows="5" type="textarea"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="父级类别:" prop="parent_name">
              <el-input v-model.trim="form.parent_name" autocomplete="off" @click.native="openwin">
                <!--<el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="排序" prop="sort">
              <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="客户合同" prop="contractName">
              <el-input v-model.trim="form.contractName" clearable @click.native="openContract">
                <!--<el-button slot="append" icon="el-icon-search" @click="openContract"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
            <el-form-item label="鉴定人:" prop="appraisal_name">
              <el-input v-model.trim="form.appraisal_name" autocomplete="off" @click.native="openwin1">
                <!--<el-button slot="append" icon="el-icon-search" @click="openwin1"></el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label=" 审批人:" prop="list">
              <p class="ts">*排序号小的先审批</p>
              <el-table v-loading="listLoading" ref="listTable" border stripe :data="this.form.list"
                        :element-loading-text="elementLoadingText"
                        highlight-current-row @selection-change="setSelectContactRows3">
                <el-table-column show-overflow-tooltip type="selection"></el-table-column>
                <el-table-column show-overflow-tooltip type="index" label="序号" align="center"
                                 width="60px;"></el-table-column>
                <el-table-column show-overflow-tooltip prop="user_name" label="用户名" sortable
                                 align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="name" align="center" sortable label="姓名"></el-table-column>
                <el-table-column show-overflow-tooltip prop="status" align="center" label="状态">
                  <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.status == 1">启用</el-tag>
                    <el-tag type="danger" v-else>禁用</el-tag>
                  </template>
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="telephone" align="center" label="电话"></el-table-column>
                <el-table-column show-overflow-tooltip prop="user_type" align="center" label="类型"></el-table-column>

                <el-table-column show-overflow-tooltip prop="sort" align="center" label="排序">
                  <template v-slot="scope">
                    <el-input v-model.trim="scope.row.sort" autocomplete="off"></el-input>
                  </template>

                </el-table-column>
              </el-table>
              <div class="btn-box" style="margin-top:10px;">
                <el-button type="danger" icon="el-icon-delete" @click="handleDelete">移除选择对象</el-button>
                <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添加</el-button>
              </div>
            </el-form-item>

          </el-col>
        </el-row>

      </el-card>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <children ref="children" @deptData="deptset" @fetch-data=" { wenti }"></children>
    <dialogone ref="dialogone" @deptData="deptset2" @fetch-data=" { contactpeople }"></dialogone>
    <dialogtwo ref="dialogtwo" @deptData="deptset1" @fetch-data=" { takeman }"></dialogtwo>
    <customerContractDia ref="customerContractDia" @listData="customerContractDataSet"
                         @fetch-data=""></customerContractDia>
  </div>
</template>

<script>
  import {getList, doDelete, saveOrUpd} from '@/api/questionSet'
  import Children from './components/wenti'
  import Dialogone from './components/contactpeople'
  import Dialogtwo from './components/takeman'
  import customerContractDia from '@/components/customerContractDia'

  export default {
    name: 'faqtypeadd',
    components: {Children, Dialogone, Dialogtwo, customerContractDia},
    data() {
      return {
        isDisable: false,
        bumen: '',
        input2: '',
        tableData: [],
        formArr: [{value1: ''}],
        formArr1: [{value1: ''}],
        tablelist: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        elementLoadingText: '正在加载...',
        checked: false,
        checked1: false,
        options: [],
        value: [],
        list: [],
        loading: false,
        states: [
          'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming',
        ],
        form: {
          name: '',
          is_use: '',
          description: '',
          parent_id: '',
          parent_name: '',
          sort: '',
          sla_id: '',
          appraisal_user: '',
          appraisal_name: '',
          customer_contract_id: "",
          contractName: "",
          list: [],
        },
        rules: {
          name: [
            {required: true, trigger: 'blur', message: '请输入问题类别名称'},
          ],
          description: [{required: true, trigger: 'blur', message: '描述不能为空'}],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'}],
          contractName: [{required: true, trigger: 'submit', message: '不能为空'}],
          // parent_id: [{ required: true, trigger: 'blur', message: '父类级别不能为空' }],
          sort: [{required: true, trigger: 'blur', message: '排序不能为空'}],
          appraisal_name: [{required: true, trigger: 'submit', message: '鉴定人不能为空'}],
          // list: [{ required: true, trigger: 'blur', message: '审批人不能为空' }],
          // permissions: [
          //   { required: true, trigger: 'blur', message: '请选择权限' },
          // ],
        },
      }
    },
    created() {
      this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
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
      deptset(data) {
        this.form.parent_id = data.id;
        this.form.parent_name = data.name;
      },
      deptset1(data) {
        this.form.appraisal_user = data.id;
        this.form.appraisal_name = data.name;
      },
      deptset2(data) {
        // this.form.list = data;
        // this.form.list.forEach(function(item, index) {
        //   item.sort = index + 1;
        // });

        data.forEach((item, i) => {
          let index = this.form.list.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.list.push(item)
          }
        })
      },

      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      openwin() {
        this.$refs['children'].showWin()
      }
      ,
      openwin1() {
        this.$refs['dialogtwo'].showWin()
      },
      handleAdd() {
        console.log(this.form.list);
        this.$refs['dialogone'].showWin(this.form.list)
      },
      handleDelete() {
        if (this.selectContactRows3.length > 0) {
          // const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            // const { msg } = await doDelete({ ids })
            // this.$baseMessage(msg, 'success')
            // this.fetchData()
            this.selectContactRows3.forEach((item, i) => {
              this.form.list.some((x, i) => {
                if (x.id == item.id) {
                  this.form.list.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
              console.log( this.$refs.dialogone.data)
              this.$refs.dialogone.data.some((e, i) => {
                if (e.id == item.id) {
                  this.$refs.dialogone.data.splice(i, 1)
                  return true
                }
              })
            });
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await getList(this.queryForm)
        this.tablelist = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
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
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由

        this.$router.go(-1)
      },
      saveok() {

        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.isDisable = true;
            const {msg} = await saveOrUpd(this.form)
            if(msg == '名称已存在'){
              this.$baseMessage(msg, 'error')
              this.isDisable = false;
            }else {
              this.$baseMessage(msg, 'success')
              this.$emit('fetch-data')
              this.closeall()
            }

          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      setSelectContactRows3(val) {
        this.selectContactRows3 = val
      },
    },
  }
</script>
