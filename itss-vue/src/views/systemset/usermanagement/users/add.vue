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
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="登录密码" prop="pass">
                <el-input
                  v-model.trim="form.pass"
                  autocomplete="off"
                  show-password
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
                  v-model.trim="form.departName"
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
                  style="
                    border-left: 0;
                    background-color: #f5f7fa;
                    height: 32px;
                    width: 20%;
                  "
                  @click="openwin"
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

            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <el-form-item label="状态" prop="status">
                <el-select v-model="form.status" placeholder="请选择">
                  <el-option label="活动" value="1"></el-option>
                  <el-option label="暂停" value="2"></el-option>
                  <el-option label="已禁止" value="3"></el-option>
                </el-select>
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
              <span>设置角色</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <div class="boxtable">
              <el-table
                ref="listTable1"
                :data="tableData"
                border
                stripe
                :element-loading-text="elementLoadingText"
                highlight-current-row
                @current-change="handleCurrentChange1"
              >
                <el-table-column
                  show-overflow-tooltip
                  type="index"
                  label="序号"
                  sortable
                  width="100px"
                  align="center"
                ></el-table-column>
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
            </div>
            <div class="btn-box">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteRole"
              >
                移除选择对象
              </el-button>

              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAddRole"
              >
                添加
              </el-button>
            </div>
          </el-card>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>配置管理权限范围</span>
            </div>

            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="权限范围">
                <el-select
                  v-model="selectedDepts"
                  multiple
                  filterable
                  style="width: 100%"
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
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button
          type="primary"
          size="medium"
          :loading="loading"
          @click="saveok"
          :disabled="this.isDisable"
        >
          保存
        </el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <serviceGroupDX
      ref="serviceGroupDX"
      @delGroup="delGroup"
      @fetch-data="
        {
          input2
        }
      "
    ></serviceGroupDX>
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
    <roles ref="roles" @listData="getRole"></roles>
  </div>
</template>

<script>
import {
  getList,
  doDelete,
  addUser,
  selectAllRole,
  getAllDept,
} from '@/api/userManagement'
import Children from './components/bumenInfo'
import Children1 from './components/bumenInfo1'
import Children2 from './components/bumenInfo2'
import serviceGroupDX from '@/components/serviceGroupDX'
import Roles from './components/Roles1'

export default {
  name: 'usermanagementadd',
  components: { Children, serviceGroupDX, Children1, Children2, Roles },
  data() {
    return {
      isDisable: false,
      bumen1: [],
      bumen: '',
      input2: '',
      tableData: [],
      formArr: [{ value1: '' }],
      formArr2: {},
      formArr1: [{ id: '', name: '' }],
      formArr3: [],
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
      handlerow: '',
      depts: [],
      selectedDepts: [],
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
        roleList: [],
        roleId: '',
        groupList: [],
        departmentIdList: [],
        configIdList: [],
        permissions: [],
      },
      multipleSelection: [],
      tempRole: {},
      tempGroup: {},
      rules: {
        user_name: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
        ],
        name: [{ required: true, trigger: 'blur', message: '请输入姓名' }],
        pass: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入密码',
            min: 6,
            message: '密码不能少于6位',
          },
        ],
        departName: [
          { required: true, trigger: 'blur', message: '请选择部门' },
        ],
        is_vip: [{ required: true, trigger: 'blur', message: '请选择是否VIP' }],
        status: [{ required: true, trigger: 'blur', message: '请选择状态' }],
        permissions: [
          { required: true, trigger: 'blur', message: '请选择权限' },
        ],
      },
    }
  },
  created() {
    //this.fetchData()
    // this.allRole()
    this.getAllDept()
  },
  beforeDestroy() {},
  mounted() {
    this.list = this.states.map((item) => {
      return { value: `${item}`, label: `${item}` }
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
        id: '',
        name: '',
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
      // this.form.groupList = val
      this.tempGroup = val
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
    openwin2(index) {
      this.handlerow = index
      this.$refs['children2'].showWin()
    },
    handleAdd() {
      this.$refs['serviceGroupDX'].showWin()
    },
    handleDelete() {
      var index = -1
      for (var i = 0; i < this.tablelist.length; i++) {
        if (this.tempGroup.id == this.tablelist[i].id) {
          index = i
        }
      }
      if (index != -1) {
        this.tablelist.splice(index, 1)
      }
      // if (this.selectRows != '') {
      //   const ids = this.selectRows.id
      //   this.$baseConfirm('你确定要删除选中项吗', null, async () => {
      //     const {msg} = await doDelete({ids})
      //     this.$baseMessage(msg, 'success')
      //     this.fetchData()
      //   })
      // } else {
      //   this.$baseMessage('未选中任何行', 'error')
      //   return false
      // }
    },
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getList(this.queryForm)
      this.tablelist = data
      console.log(data)
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
    deptset(data) {
      this.form.department_id = data.id
      this.form.departName = data.name
    },
    //按部门统计权限范围三级页面拿数据
    deptset1(data) {
      // debugger
      this.formArr = data
      this.formArr2 = data
      console.log(this.formArr2)
    },
    deptset2(data) {
      var that = this
      this.$set(that.formArr1, that.handlerow, data)
    },

    delGroup(data) {
      this.tablelist = data
      console.log(this.tablelist)
    },
    saveok() {
      this.isDisable = true
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true
          this.form.configIdList = this.selectedDepts
          this.form.departmentIdList = [this.formArr2.id]
          this.form.groupList = this.tablelist
          this.form.roleList = this.tableData
          const data = await addUser(this.form)
          this.loading = false
          if (data.code == 0) {
            this.$baseMessage(data.msg, 'success')
            this.fetchData()
            this.closeall()
          } else {
            this.$baseMessage('失败', 'error')
            this.isDisable = false
          }
        } else {
          this.isDisable = false
          return false
        }
      })
    },
    // async allRole() {
    //   const {data} = await selectAllRole()
    //   this.tableData = data
    //   console.log(this.tableData)
    //   setTimeout(() => {
    //     this.listLoading = false
    //   }, 300)
    // },

    handleCurrentChange1(val) {
      // this.form.roleId = val.id
      console.log(val)
      this.tempRole = val
    },
    handleAddRole() {
      console.log(this.tableData)
      this.$refs['roles'].showWin(this.tableData)
    },
    getRole(data) {
      this.tableData = []
      this.tableData = data
    },
    handleDeleteRole() {
      var index = -1
      for (var i = 0; i < this.tableData.length; i++) {
        if (this.tempRole.id == this.tableData[i].id) {
          index = i
        }
      }
      if (index != -1) {
        this.tableData.splice(index, 1)
      }
    },
  },
}
</script>
