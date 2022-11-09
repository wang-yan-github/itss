<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div class="box_card">
              <div class="box_header">属性</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="名称" prop="type_name">
                      <el-input v-model.trim="form.type_name" autocomplete="off"></el-input>
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
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="排序" prop="sort">
                      <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="上级" prop="parent_name">
                      <el-input v-model.trim="form.parent_name" autocomplete="off" @click.native="openwin">
                        <!--<el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">一般变更</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="boxtable" style="margin-bottom:15px;">
                      <p>审批人:<span>*排序号小的先审批</span></p>
                      <el-table v-loading="listLoading" ref="listTable" border stripe
                                :data="this.form.changeCommonlyApproveList1"
                                :element-loading-text="elementLoadingText" highlight-current-row
                                @current-change="handleCurrentChange">
                        <el-table-column show-overflow-tooltip type="index" label="序号" width="70px;"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="name" align="center" sortable
                                         label="名称"></el-table-column>
                        <el-table-column show-overflow-tooltip label="状态" align="center">
                          <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
                            <el-tag type="danger" v-else>已禁止</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="nonFirstLineGroup" align="center" label="非一线组"
                                         sortable>
                          <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.nonFirstLineGroup == 1">是</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.nonFirstLineGroup == 2">否</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="principalName" align="center" label="负责人"
                                         sortable></el-table-column>
                        <el-table-column show-overflow-tooltip prop="engineerName" align="center" label="工程师"
                                         sortable></el-table-column>
                        <el-table-column show-overflow-tooltip prop="auto_engineer" align="center"
                                         label="处理人为空自动指定工程师"></el-table-column>
                        <el-table-column show-overflow-tooltip type="index" align="center" label="排序"
                                         width="80px;"></el-table-column>
                      </el-table>
                      <div class="btn-box">
                        <el-button type="danger" icon="el-icon-delete" @click="handleDelete('generally')">移除选择对象
                        </el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="handleAdd('generally')">添加</el-button>
                      </div>
                    </div>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="方案审核人" prop="commonly_audit_user_name">
                      <el-input v-model.trim="form.commonly_audit_user_name" autocomplete="off"
                                @click.native="openUser('generally_Program')">
                        <!--<el-button slot="append" icon="el-icon-search"-->
                                   <!--@click="openUser('generally_Program')"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="实施后评审人" prop="commonly_implement_user_name">
                      <el-input v-model.trim="form.commonly_implement_user_name" autocomplete="off"
                                @click.native="openUser('generally_impl')">
                        <!--<el-button slot="append" icon="el-icon-search" @click="openUser('generally_impl')"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">紧急变更</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="boxtable" style="margin-bottom:15px;">
                      <p>审批人:<span>*排序号小的先审批</span></p>
                      <el-table v-loading="listLoading" ref="listTable" border stripe
                                :data="this.form.changeUrgentApproveList1"
                                :element-loading-text="elementLoadingText" highlight-current-row
                                @current-change="handleCurrentChange">
                        <el-table-column show-overflow-tooltip type="index" label="序号" width="70px;"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="name" align="center" sortable
                                         label="名称"></el-table-column>
                        <el-table-column show-overflow-tooltip label="状态" align="center">
                          <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
                            <el-tag type="danger" v-else>已禁止</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="nonFirstLineGroup" align="center" label="非一线组"
                                         sortable>
                          <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.nonFirstLineGroup == 1">是</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.nonFirstLineGroup == 2">否</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="principalName" align="center" label="负责人"
                                         sortable></el-table-column>
                        <el-table-column show-overflow-tooltip prop="engineerName" align="center" label="工程师"
                                         sortable></el-table-column>
                        <el-table-column show-overflow-tooltip prop="auto_engineer" align="center"
                                         label="处理人为空自动指定工程师"></el-table-column>
                        <el-table-column show-overflow-tooltip type="index" align="center" label="排序"
                                         width="80px;"></el-table-column>
                      </el-table>
                      <div class="btn-box">
                        <el-button type="danger" icon="el-icon-delete" @click="handleDelete('urgent')">移除选择对象
                        </el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="handleAdd('urgent')">添加</el-button>
                      </div>
                    </div>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="实施后评审人" prop="urgent_implement_user_name">
                      <el-input v-model.trim="form.urgent_implement_user_name" autocomplete="off"
                                @click.native="openUser('urgent_impl')">
                        <!--<el-button slot="append" icon="el-icon-search" @click="openUser('urgent_impl')"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">重大变更</div>
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="boxtable" style="margin-bottom:15px;">
                      <p>审批人:<span>*排序号小的先审批</span></p>
                      <el-table v-loading="listLoading" ref="listTable" border stripe
                                :data="this.form.changeMajorApproveList1"
                                :element-loading-text="elementLoadingText" highlight-current-row
                                @current-change="handleCurrentChange">
                        <el-table-column show-overflow-tooltip type="index" label="序号" width="70px;"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="name" align="center" sortable
                                         label="名称"></el-table-column>
                        <el-table-column show-overflow-tooltip label="状态" align="center">
                          <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
                            <el-tag type="danger" v-else>已禁止</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="nonFirstLineGroup" align="center" label="非一线组"
                                         sortable>
                          <template slot-scope="scope">
                            <el-tag type="success" v-if="scope.row.nonFirstLineGroup == 1">是</el-tag>
                            <el-tag type="warning" v-else-if="scope.row.nonFirstLineGroup == 2">否</el-tag>
                          </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="principalName" align="center" label="负责人"
                                         sortable></el-table-column>
                        <el-table-column show-overflow-tooltip prop="engineerName" align="center" label="工程师"
                                         sortable></el-table-column>
                        <el-table-column show-overflow-tooltip prop="auto_engineer" align="center"
                                         label="处理人为空自动指定工程师"></el-table-column>
                        <el-table-column show-overflow-tooltip type="index" align="center" label="排序"
                                         width="80px;"></el-table-column>
                      </el-table>
                      <div class="btn-box">
                        <el-button type="danger" icon="el-icon-delete" @click="handleDelete('major')">移除选择对象</el-button>
                        <el-button type="primary" icon="el-icon-plus" @click="handleAdd('major')">添加</el-button>
                      </div>
                    </div>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="方案审核人" prop="major_audit_user_name">
                      <el-input v-model.trim="form.major_audit_user_name" autocomplete="off"
                                @click.native="openUser('major_Program')">
                        <!--<el-button slot="append" icon="el-icon-search" @click="openUser('major_Program')"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="实施后评审人" prop="major_implement_user_name">
                      <el-input v-model.trim="form.major_implement_user_name" autocomplete="off"
                                @click.native="openUser('major_impl')">
                        <!--<el-button slot="append" icon="el-icon-search" @click="openUser('major_impl')"></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                </el-row>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <children ref="children" @deptData="deptset"></children>
    <UserDialog ref="userDialog" @listData="setApprover"></UserDialog>
    <userSelectDialog ref="userSelectDialog" @userData="setUser"></userSelectDialog>
  </div>
</template>

<script>
  import {getList, doDelete, doEdit, getServerGroupByUser, getById} from '@/api/sourceSet'
  import UserDialog from '@/components/UsersDia'
  import userSelectDialog from '@/components/UserSelectModel'

  import Children from './components/changeshow'

  export default {
    name: 'changetypeadd',
    components: {Children, UserDialog, userSelectDialog},
    data() {
      return {
        isDisable: false,
        setUserType: '',
        setApproverType: '',
        bumen: '',
        input2: '',
        tableData: [],
        formArr: [{value1: ''}],
        formArr1: [{value1: ''}],
        tablelist: null,
        listLoading: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
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
          type_name: '',
          is_use: '',
          sort: '',
          parent_id: '',
          parent_name: '',
          changeCommonlyApproveList1: [],
          commonly_audit_user: '',
          commonly_audit_user_name: '',
          commonly_implement_user: '',
          commonly_implement_user_name: '',
          changeUrgentApproveList1: [],
          urgent_implement_user: '',
          urgent_implement_user_name: '',
          changeMajorApproveList1: [],
          major_audit_user: '',
          major_audit_user_name: '',
          major_implement_user: '',
          major_implement_user_name: '',
        },
        rules: {
          type_name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
          truename: [{required: true, trigger: 'blur', message: '请输入姓名'}],
          password: [{required: true, trigger: 'blur', message: '请输入密码'}],
          email: [{required: true, trigger: 'blur', message: '请输入邮箱'}],
          is_use: [
            {required: true, trigger: 'blur', message: '请选择状态'},
          ],
          commonly_audit_user_name:[{required: true, trigger: 'blur', message: '必填'}],
          commonly_implement_user_name:[{required: true, trigger: 'blur', message: '必填'}],
          urgent_implement_user_name:[{required: true, trigger: 'blur', message: '必填'}],
          major_audit_user_name:[{required: true, trigger: 'blur', message: '必填'}],
          major_implement_user_name:[{required: true, trigger: 'blur', message: '必填'}],
        },
      }
    },
    created() {
      this.getById()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      deptset(data) {
        this.form.parent_id = data.id;
        this.form.parent_name = data.name;
      },
      // 变更类别
      openwin() {
        const id = this.$route.query.id
        this.$refs['children'].showWin(id)
      },
      // 一般变更审批人添加
      handleAdd(type) {
        //判断type 是否为空
        if (type === '') {
          this.$message.error('请选择审批人类型')
          return
        }
        this.setApproverType = type;
        this.$refs['userDialog'].showWin()
      },
      // 回写审核人列表
      setApprover(data) {
        var userIds = [];
        // 判断是否为空,不为空循环添加到数组中
        if (data.length > 0) {
          data.forEach(item => {
            userIds.push(item.id)
          })
          getServerGroupByUser(userIds).then(res => {
            var list = res.data;
            switch (this.setApproverType) {
              case 'generally':
                this.form.changeCommonlyApproveList1 = list
                break
              case 'urgent':
                this.form.changeUrgentApproveList1 = list
                break
              case 'major':
                this.form.changeMajorApproveList1 = list
                break
            }
          })
        }
      },
      //删除审批人
      handleDelete(type) {
        if (this.selectRows != '') {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            switch (type) {
              case 'generally':
                this.form.changeCommonlyApproveList1.some((x, i) => {
                  if (x.id == ids) {
                    this.form.changeCommonlyApproveList1.splice(i, 1);
                    //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                    return true
                  }
                })
                break
              case 'urgent':
                this.form.changeUrgentApproveList1.some((x, i) => {
                  if (x.id == ids) {
                    this.form.changeUrgentApproveList1.splice(i, 1);
                    //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                    return true
                  }
                })
                break
              case 'major':
                this.form.changeMajorApproveList1.some((x, i) => {
                  if (x.id == ids) {
                    this.form.changeMajorApproveList1.splice(i, 1);
                    //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                    return true
                  }
                })
                break
            }
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 变更审批人添加
      openUser(type) {
        if (type == '') {
          this.$message.error('请选择审批人类型')
          return
        }
        this.setUserType = type;
        this.$refs['userSelectDialog'].showWin()
      },
      // 审批人回显
      setUser(data) {
        switch (this.setUserType) {
          case 'generally_Program':
            this.form.commonly_audit_user = data.id
            this.form.commonly_audit_user_name = data.name
            break
          case 'generally_impl':
            this.form.commonly_implement_user = data.id
            this.form.commonly_implement_user_name = data.name
            break
          case 'urgent_impl':
            this.form.urgent_implement_user = data.id
            this.form.urgent_implement_user_name = data.name
            break
          case 'major_Program':
            this.form.major_audit_user = data.id
            this.form.major_audit_user_name = data.name
            break
          case 'major_impl':
            this.form.major_implement_user = data.id
            this.form.major_implement_user_name = data.name
            break
        }
      },

      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
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
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            if(null==this.form.changeCommonlyApproveList1||undefined==this.form.changeCommonlyApproveList1||""==this.form.changeCommonlyApproveList1){
                  this.$baseMessage('请选择一般变更审批人', 'error');
                  this.isDisable = false;
              }else if(null==this.form.changeUrgentApproveList1||undefined==this.form.changeUrgentApproveList1||""==this.form.changeUrgentApproveList1){
                  this.$baseMessage('请选择紧急变更审批人', 'error');
                  this.isDisable = false;
              }else if(null==this.form.changeMajorApproveList1||undefined==this.form.changeMajorApproveList1||""==this.form.changeMajorApproveList1){
                  this.$baseMessage('请选择重大变更审批人', 'error');
                  this.isDisable = false;
              }else{
                  try{
                    const data = await doEdit(this.form)
                    if (data.code == 0) {
                      this.$baseMessage(data.msg, 'success');
                      this.fetchData()
                      this.closeall()
                    } else {
                      this.$baseMessage("失败", 'error');
                      this.isDisable = false;
                    }
                  }catch(e){
                    this.isDisable = false;
                  }
              }
          } else {
            this.isDisable = false;
            return false
          }
      })
      },
      // 初始化表单数据
      async getById() {
        this.listLoading = false
        const ids = this.$route.query.id
        //判断ids非空
        if (ids != '' && ids != undefined) {
          const {data} = await getById({ids})
          this.form = data
          console.log(this.form)
        }
      },
    },
  }
</script>
