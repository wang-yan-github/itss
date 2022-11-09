<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="140px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>属性</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="名称" prop="name">
                <el-input v-model.trim="form.name" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="状态" prop="is_use">
                <el-select v-model="form.is_use" placeholder="请选择" style="width: 100%">
                  <el-option label="启用" :value="1"></el-option>
                  <el-option label="禁用" :value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="排序">
                <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="自动分派到服务群组" prop="group_id">
                <el-select v-model="form.group_id" placeholder="请选择" style="width: 100%">
                  <el-option v-for="item in serviceData" :key="item.id" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="自动分派到工程师" prop="engineer_id">
                <el-input v-model.trim="form.engineer_name" placeholder="处理人" clearable @click.native="handleRole1">
                  <!--<el-button slot="append" icon="el-icon-search" @click="handleRole1"></el-button>-->
                </el-input>
                <!--                <el-select-->
                <!--                  v-model="form.engineer_id"-->
                <!--                  filterable-->
                <!--                  remote-->
                <!--                  reserve-keyword-->
                <!--                  placeholder="请输入姓名"-->
                <!--                  :remote-method="remoteMethod"-->
                <!--                  :loading="loading"-->
                <!--                  style="width: 80%"-->
                <!--                >-->
                <!--                  <template #prefix><span style="padding-left: 2px"><i class="el-icon-search"></i></span></template>-->
                <!--                  <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name" :value="item.id"></el-option>-->
                <!--                </el-select>-->
                <!--                <el-button icon="el-icon-search" style=" border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%;" @click="openwinTable"></el-button>-->
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="上级类别" prop="parent_type_name">
                <el-input autocomplete="off" placeholder="请选择" style="width: 80%" @click.native="openwinOne"
                          v-model="form.parent_type_name"></el-input>
                <el-button icon="el-icon-search"
                           style=" border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%;"
                           @click="openwinOne"></el-button>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="公司" prop="company_id">
                <el-select v-model="form.company_id" placeholder="请选择" style="width: 100%">
                  <el-option v-for="item in companyData" :key="item.id" :label="item.name" :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="工单类别" prop="bill_type_name">
                <el-input autocomplete="off" placeholder="请选择" style="width: 80%" @click.native="openwinThree"
                          v-model="form.bill_type_name"></el-input>
                <el-button icon="el-icon-search"
                           style=" border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%;"
                           @click="openwinThree"></el-button>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="描述">
                <el-input v-model.trim="form.description" autocomplete="off" type="textarea" :rows="5"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="服务表单默认标题">
                <el-input v-model.trim="form.from_title" autocomplete="off" placeholder="请输入默认标题"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="服务表单默认描述">
                <vab-quill v-model="form.from_desc" :min-height="200" :options="optionsText"></vab-quill>
              </el-form-item>
            </el-col>
            <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
              <!--<el-form-item label="模块ID">-->
                <!--<el-input v-model.trim="form.ids" autocomplete="off" placeholder="多个用半角逗号(,)间隔"></el-input>-->
              <!--</el-form-item>-->
            <!--</el-col>-->
          </el-card>
        </el-col>
      </el-row>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存并继续</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

    <children ref="children" @fetch-data="getBillType2"></children>
    <dialogone ref="dialogone" @userData="getUser"></dialogone>
    <dialogtwo ref="dialogtwo" @fetch-data="getBillType"></dialogtwo>
    <dialogfour ref="dialogfour" @userData="deptset2" @fetch-data="fetchData"></dialogfour>
  </div>
</template>

<script>
  import {
    getList,
    doDelete,
    doEdit,
    add,
    upList,
    tree,
    upCompany,
    info,
    edit,
  } from '@/api/selfService'
  import {upUserData} from '@/api/eventServiceManage'
  import vabQuill from '@/plugins/vabQuill'
  import Children from '@/components/eventServiceCategoryDia'
  import Dialogone from '@/components/UserSelectModel'
  import Dialogtwo from '@/components/gongdanclass'
  import dialogfour from '@/components/UserSelectModel'

  export default {
    name: 'serviceAdd',
    components: {
      Children,
      Dialogone,
      Dialogtwo,
      vabQuill,
      dialogfour
    },
    data() {
      return {
        isDisable: false,
        activeName: 'first',
        input2: '',
        formArr: [
          {
            value1: '',
          },
        ],
        formArr1: [
          {
            value1: '',
          },
        ],
        tablelist: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        checked: false,
        options: [],
        value: [],
        list: [],
        loading: false,
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
          username: '',
          truename: '',
          bill_type_name: '',
          worker_order_id: '',
          parent_type_id: '',
          parent_type_name: '',
          bumen: '',
          isvip: '',
          from_desc: '',
          name: '',
          sort: '',
          is_use: '',
          group_id: '',
          engineer_id: '',
          engineer_name: '',
          company_id: '',
          description: '',
          from_title: '',
          ids: '',
        },
        rules: {
          name: [
            {
              required: true,
              trigger: 'blur',
              message: '请输入名称',
            },
          ],
          is_use: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择状态',
            },
          ],
          // group_id: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请选择服务群组'
          // }],
          // engineer_id: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请选择工程师'
          // }],
          // bill_type_name: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请选择工单类别'
          // }],
          // parent_type_name: [{
          //   required: false,
          //   trigger: 'blur',
          //   message: '请选择上级类别'
          // }],
          // company_id: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请选择公司'
          // }],
        },
        optionsText: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{header: [1, 2, 3, 4, 5, 6, false]}],
              [{size: ['small', false, 'large', 'huge']}],
              [{color: []}, {background: []}],
              ['blockquote', 'code-block'],
              [{list: 'ordered'}, {list: 'bullet'}],
              [{script: 'sub'}, {script: 'super'}],
              [{indent: '-1'}, {indent: '+1'}],
              [{align: []}],
              [{direction: 'rtl'}],
              [{font: []}],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
        serviceData: [],
        auto_engineer_data: [],
        companyData: [],
      }
    },
    created() {
      this.upServiceList()
      this.upUserData()
      this.upCompanyList()
      this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
      this.list = this.states.map((item) => {
        return {
          value: `${item}`,
          label: `${item}`,
        }
      })
    },
    methods: {
      deptset2(data) {
        this.form.engineer_id = data.id;
        this.form.engineer_name = data.name;
      },
      handleRole1() {
        var data = {};
        // 判断service_groups_id是否为空
        if (this.form.group_id == null || this.form.group_id == undefined || this.form.group_id == '') {
          this.$baseMessage("请选择服务组", 'error')
          return;
        }
        data.group_id = this.form.group_id;
        this.$refs['dialogfour'].showWin(data)
      },
      getUser(row) {
        this.form.engineer_id = row.id
      },
      openwinTable() {
        this.$refs['dialogone'].showWin()
      },
      openwinOne() {
        if (this.form.id == null || this.form.id == undefined || this.form.id == '') {
          this.$refs['children'].showWin(null,1)
        } else {
          this.$refs['children'].showWin(this.form.id,1)
        }
      },
      openwinThree() {
        this.$refs['dialogtwo'].showWin()
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
        var that = this
        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            try {
              if (
                that.form.id == null ||
                that.form.id == undefined ||
                that.form.id == ''
              ) {
                add(that.form).then((res) => {
                  if (res.code == 0) {
                    that.$baseMessage('操作成功', 'success')
                    that.closeall()
                  } else {
                    that.$baseMessage(res.msg, 'error');
                    that.isDisable = false;
                  }
                })
              } else {
                edit(that.form).then((res) => {
                  if (res.code == 0) {
                    that.$baseMessage('操作成功', 'success')
                    that.closeall()
                  } else {
                    that.$baseMessage(res.msg, 'error');
                    that.isDisable = false;
                  }
                })
              }
            } catch (e) {
              that.isDisable = false;
            }
          } else {
            that.isDisable = false;
            return false
          }
        })
      },
      upServiceList() {
        var that = this
        upList().then((res) => {
          that.serviceData = res.data
        })
      },
      upCompanyList() {
        var that = this
        upCompany().then((res) => {
          that.companyData = res.data
        })
      },
      changeUserData() {

      },
      async upUserData() {
        var that = this
        const resp = await upUserData()
        that.auto_engineer_data = resp.data
      },
      fetchData() {
        var that = this
        if (null != that.$route.query.id || undefined != that.$route.query.id) {
          info(that.$route.query.id).then((res) => {
            that.form = res.data
            // that.form.engineer_id = parseInt(res.data.engineer_id)
            // that.form.group_id = parseInt(res.data.group_id )
            //that.form.is_use = that.form.is_use + ''
          })
        }
      },
      getBillType(data) {
        var that = this
        that.form.bill_type_name = data.name
        that.form.worker_order_id = data.id
      },
      getBillType2(data) {
        var that = this
        that.form.parent_type_name = data.name
        that.form.parent_type_id = data.id
      },
    }
    ,
  }
</script>
<style lang="scss" scoped>
  .index-container {
    .hiddenmore {
      display: inline;

      ::v-deep {
        .el-input {
          width: 130px;
        }
      }
    }
  }

  .tips {
    color: #e6a23c;
  }

  .boxtable .btn-box {
    margin-top: 10px;
  }

  ::v-deep {
    .tsitemd {
      label {
        width: 260 !important;
        text-align: left;
      }

      .el-form-item__content {
        margin-left: 0 !important;
      }
    }
  }
</style>
