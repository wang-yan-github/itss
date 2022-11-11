<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>基本信息</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="用户名" prop="user_name">
                <el-input
                  v-model.trim="form.user_name"
                  autocomplete="off"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="姓名" prop="name">
                <el-input
                  v-model.trim="form.name"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="部门" prop="departName">
                <el-select
                  v-model="form.departName"
                  filterable
                  remote
                  reserve-keyword
                  placeholder="请输入部门搜索"
                  :remote-method="remoteMethod"
                  :loading="loading"
                  style="width: 80%"
                >
                  <template #prefix>
                    <span style="padding-left: 2px">
                      <i class="el-icon-search"></i>
                    </span>
                  </template>
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
                <el-button
                  icon="el-icon-search"
                  style="border-left: 0; background-color: #f5f7fa; width: 20%"
                  @click.native="openwin"
                ></el-button>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="职位" prop="position">
                <el-input
                  v-model.trim="form.position"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="位置" prop="location">
                <el-input
                  v-model.trim="form.location"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="电话" prop="telephone">
                <el-input
                  v-model.trim="form.telephone"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="座机" prop="landline">
                <el-input
                  v-model.trim="form.landline"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="邮箱">
                <el-input
                  v-model.trim="form.mailbox"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="是否VIP" prop="is_vip">
                <el-select v-model="form.is_vip" placeholder="请选择">
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <!--<el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
            <!--<el-form-item label="类型" prop="user_type">-->
            <!--<el-input-->
            <!--v-model.trim="form.user_type"-->
            <!--autocomplete="off"-->
            <!--&gt;</el-input>-->
            <!--</el-form-item>-->
            <!--</el-col>-->
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="状态" prop="status">
                <el-select v-model="form.status" placeholder="请选择">
                  <el-option label="活动" value="1"></el-option>
                  <el-option label="暂停" value="2"></el-option>
                  <el-option label="已禁止" value="3"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="添加时间" prop="truename">
                <el-input
                  v-model.trim="form.create_time"
                  autocomplete="off"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="更新时间" prop="truename" :disabled="true">
                <el-input
                  v-model.trim="form.update_time"
                  autocomplete="off"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="OpenID" prop="truename" :disabled="true">
                <el-input
                  v-model.trim="form.id"
                  autocomplete="off"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>通知</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="是否接收邮件通知" class="tsitem">
                <el-select v-model="form.is_emial" placeholder="请选择">
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="是否接收微信通知" class="tsitem">
                <el-select v-model="form.is_wechat" placeholder="请选择">
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>其他</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="微信昵称" prop="wechat_nickname">
                <el-input
                  v-model.trim="form.wechat_nickname"
                  autocomplete="off"
                  :disabled="true"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="" prop="checked">
                <el-checkbox v-model="checked">解除微信绑定</el-checkbox>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="" prop="checked1">
                <el-checkbox v-model="form.updatePassword">
                  修改密码
                </el-checkbox>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="新密码" prop="checked1">
                <el-input
                  v-model.trim="form.newPassword"
                  show-password
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="重新输入" prop="checked1">
                <el-input
                  v-model.trim="form.ensurePassword"
                  show-password
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>事件</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <div class="boxtable">
              <p>
                处理服务群组:
                <span>*事件工程师不能为空</span>
              </p>
              <el-table
                v-loading="listLoading"
                ref="listTable"
                border
                stripe
                :data="form.groupList"
                :element-loading-text="elementLoadingText"
                highlight-current-row
                @current-change="handleCurrentChange"
              >
                <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
                <el-table-column
                  show-overflow-tooltip
                  type="index" label="序号"
                  width="60"
                  sortable
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="name"
                  align="center"
                  sortable
                  label="名称"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  label="状态"
                  align="center"
                >
                  <template slot-scope="scope">
                    {{ scope.row.is_use == 1 ? '启用' : '禁用' }}
                  </template>
                </el-table-column>
                <el-table-column show-overflow-tooltip label="非一线组" align="center">
                  <template slot-scope="scope">
                    {{ scope.row.nonFirstLineGroup == 1 ? '是' : '否' }}
                  </template>
                </el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="principalName"
                  align="center"
                  label="负责人"
                  sortable
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="engineerName"
                  align="center"
                  label="工程师"
                  sortable
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="auto_engineer_name"
                  align="center"
                  label="处理人为空自动指定工程师"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="sort"
                  align="center"
                  label="排序"
                  sortable
                ></el-table-column>
              </el-table>
              <div class="btn-box">
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  @click="handleDelete"
                >
                  移除选择对象
                </el-button>
                <el-button
                  type="primary"
                  icon="el-icon-plus"
                  @click="handleAdd"
                >
                  添加
                </el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>配置管理权限范围</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="权限范围">
                <el-select
                  v-model="selectedDepts"
                  multiple
                  filterable
                  style="width: 100%;"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in depts"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <!-- <div class="boxtable" style="background: #fff; border: 0">
              <table class="desctable" style="width: 400px" ref="addTable1">
                <colgroup>
                  <col />
                  <col class="w100" />
                </colgroup>
                <thead>
                  <th>部门</th>
                  <th>
                    <el-button
                      round
                      size="mini"
                      type="primary"
                      icon="el-icon-plus"
                      @click="addItemo"
                    ></el-button>
                  </th>
                </thead>
                <tbody>
                  <tr v-for="(item, index) in form.configList" :key="index">
                    <td>
                      <el-input
                        placeholder="请输入内容"
                        v-model.trim="item.name"
                        @click.native="openwin2"
                      >
                        <el-button
                          slot="append"
                          icon="el-icon-search"
                          @click="openwin2"
                        ></el-button>
                      </el-input>
                    </td>

                    <td align="center">
                      <el-button
                        round
                        size="mini"
                        type="danger"
                        icon="el-icon-delete"
                        @click="delItemo(index)"
                      ></el-button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div> -->

          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <children
      ref="children"
      @deptData="deptset"
      @fetch-data="
        {
          bumenInfo
        }
      "
    ></children>
    <children1
      ref="children1"
      @deptData1="deptset1"
      @fetch-data="
        {
          bumenInfo1
        }
      "
    ></children1>
    <children2
      ref="children2"
      @deptData2="deptset2"
      @fetch-data="
        {
          bumenInfo1
        }
      "
    ></children2>
    <serviceGroupDX
      ref="serviceGroupDX"
      @delGroup="delGroup"
      @fetch-data="
        {
          input2
        }
      "
    ></serviceGroupDX>
  </div>
</template>

<script>
  import {
    getList,
    doDelete,
    getOneUser,
    updateUser,
    getAllDept,
  } from '@/api/userManagement'
  import Children from './components/bumenInfo'
  import Children1 from './components/bumenInfo1'
  import Children2 from './components/bumenInfo2'
  import serviceGroupDX from '@/components/serviceGroupDX'

  export default {
    name: 'usermanagementedit',
    components: {Children, serviceGroupDX, Children1, Children2},
    data() {
      return {
        isDisable: false,
        input2: '',
        formArr: [{value1: ''}],
        formArr1: [{value1: ''}],
        tablelist: null,
        listLoading: true,
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
        depts: [],
        selectedDepts: [],
        groupList: [],
        tempGroup: {},
        states: [
          'Alabama',
          'Alaska',
          'Arizona',
          'Arkansas',
          'California',
          'Colorado',
          'Connecticut',
          'Delaware',
          'Florida',
          'Georgia',
          'Hawaii',
          'Idaho',
          'Illinois',
          'Indiana',
          'Iowa',
          'Kansas',
          'Kentucky',
          'Louisiana',
          'Maine',
          'Maryland',
          'Massachusetts',
          'Michigan',
          'Minnesota',
          'Mississippi',
          'Missouri',
          'Montana',
          'Nebraska',
          'Nevada',
          'New Hampshire',
          'New Jersey',
          'New Mexico',
          'New York',
          'North Carolina',
          'North Dakota',
          'Ohio',
          'Oklahoma',
          'Oregon',
          'Pennsylvania',
          'Rhode Island',
          'South Carolina',
          'South Dakota',
          'Tennessee',
          'Texas',
          'Utah',
          'Vermont',
          'Virginia',
          'Washington',
          'West Virginia',
          'Wisconsin',
          'Wyoming',
        ],
        form: {
          id: '',
          user_name: '',
          pass: '',
          name: '',
          department_id: '',
          departName: '',
          position: '',
          location: '',
          telephone: '',
          landline: '',
          mailbox: '',
          is_vip: '',
          status: '',
          is_emial: '',
          is_wechat: '',
          update_time: '',
          create_time: '',
          user_type: '',
          wechat_nickname: '',
          groupList: [],
          departmentList: [],
          configList: [],
          OpenID: '',
          updatePassword: false,
        },
        rules: {
          user_name: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
          name: [{required: true, trigger: 'blur', message: '请输入姓名'}],
          pass: [{required: true, trigger: 'blur', message: '请输入密码'}],
          departName: [{required: true, trigger: 'blur', message: '请选择部门'}],
          is_vip: [{required: true, trigger: 'blur', message: '请选择是否VIP'}],
          status: [{required: true, trigger: 'blur', message: '请选择状态'}],
          permissions: [
            {required: true, trigger: 'blur', message: '请选择权限'},
          ],
        },
      }
    },
    created() {
      this.fetchData()
      this.getAllDept()
      setTimeout(() => {
        this.getOneById()
      }, 300)
    },
    beforeDestroy() {
    },
    mounted() {
      this.list = this.states.map((item) => {
        return {value: `${item}`, label: `${item}`}
      })
    },
    methods: {
      getAllDept() {
        getAllDept().then((res) => {
          this.depts = res.data
        })
      },
      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index) {
        var that = this
        if (that.formArr.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr.splice(index, 1)
        }
      },
      addItemo() {
        var that = this
        that.formArr1.push({
          value1: '',
        })
      },
      delItemo(index) {
        var that = this
        if (that.formArr1.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr1.splice(index, 1)
        }
      },
      handleCurrentChange(val) {
        this.tempGroup = val;
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      openwin1() {
        this.$refs['children1'].showWin()
      },
      openwin2() {
        this.$refs['children2'].showWin()
      },
      handleAdd() {
        console.log(this.form.groupList)
        this.$refs['serviceGroupDX'].showWin(this.form.groupList)
      },
      delGroup(data) {
        this.form.groupList = data
      },
      handleDelete() {
        var index = -1;
        for (var i = 0 ; i < this.form.groupList.length ; i++){
          if(this.tempGroup.id == this.form.groupList[i].id){
            index = i;
          }
        }
        if (index != -1){
          this.form.groupList.splice(index,1);
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
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            if (this.form.updatePassword) {
              if (this.form.newPassword != this.form.ensurePassword) {
                this.$baseMessage('确认密码不一致', 'error')
                return false
              }
            }
            this.form.id = this.$route.query.id
            this.form.configIdList = this.selectedDepts
            try{
              const data = await updateUser(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                // this.$emit('fetch-data')
                this.fetchData()
                this.closeall()
              } else {
                this.$baseMessage(data.msg, 'error');
                this.isDisable = false;
              }
            }catch(e){
                this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      deptset(data) {
        this.form.department_id = data.id
        this.form.departName = data.name
      },
      //按部门统计权限范围三级页面拿数据
      deptset1(data) {
        this.form.departmentList = data
      },
      deptset2(data) {
        this.form.configList = data
      },
      async getOneById() {
        const ids = this.$route.query.id
        const {data} = await getOneUser({ids})
        this.form = data.data
        if (data.data.configList) {
          data.data.configList.forEach((element) => {
            this.selectedDepts.push(element.id)
          })
        }
        this.listLoading = false
      },
    },
  }
</script>
