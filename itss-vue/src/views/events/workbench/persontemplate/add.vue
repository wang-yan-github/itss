<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
						<span>
							<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
							模板信息
						</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="模板名称" prop="username">
                <el-input v-model.trim="form.template_name" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="公共模板" prop="isvip">
                <el-select v-model="form.is_public" placeholder="请选择" style="width: 100%;">
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <!--<el-form-item label="所属服务群组">-->
              <!--<template>-->
              <!--<el-select-->
              <!--v-model="value"-->
              <!--multiple-->
              <!--filterable-->
              <!--remote-->
              <!--reserve-keyword-->
              <!--placeholder="请输入关键词"-->
              <!--:remote-method="remoteMethod"-->
              <!--style="width: 100%;"-->
              <!--:loading="loading">-->
              <!---->
              <!--</el-select>-->
              <!--</template>-->
              <!--</el-form-item>-->
              <el-form-item label="所属服务群组" prop="isvip">
                <el-select v-model="form.event_service_id" placeholder="请选择" style="width: 100%;">
                  <el-option
                    v-for="item in serviceData"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="状态" prop="isvip">
                <el-select v-model="form.is_use" placeholder="请选择" style="width: 100%;">
                  <el-option label="启用" value="1"></el-option>
                  <el-option label="禁用" value="0"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="描述" prop="">
                <el-input v-model.trim="form.template_desc" autocomplete="off" type="textarea"
                          :rows="5"></el-input>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
						<span>
							<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
							用户信息
						</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="用户">
                <el-select v-model="form.user_id" filterable remote reserve-keyword
                           placeholder="请输入用户" :remote-method="remoteMethod" :loading="loading"
                           style="width: calc(100% - 60px)">
                  <template #prefix>
									<span style="padding-left: 2px">
										<i class="el-icon-search"></i>
									</span>
                  </template>
                  <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name"
                             :value="item.id"></el-option>
                </el-select>
                <el-button icon="el-icon-search"
                           style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 60px"
                           @click="openwinTable"></el-button>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
						<span>
							<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
							工单信息
						</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="工单标题">
                <el-input v-model.trim="form.title" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="工单描述">
                <quill-editor
                  ref="myQuillEditor1"
                  v-model="form.description"
                  :min-height="200"
                  :options="optionsText"
                ></quill-editor>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
						<span>
							<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
							分类信息
						</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>          
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="来源">
                  <el-select v-model="form.source" placeholder="请选择" style="width: 100%;">
                    <el-option v-for="item in eventSourceData" :key="item.id" :label="item.resource_name"
                               :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="请求类型">
                  <el-select v-model="form.request_type" placeholder="请选择" style="width: 100%;">

                    <el-option v-for="item in eventRequestData" :key="item.id" :label="item.request_type_name"
                               :value="item.id"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="工单类别">
                  <el-input @click.native="openwin" autocomplete="off" placeholder="请选择工单类别" style="width: calc(100% - 40px);"
                            v-model="form.bill_type_name"></el-input>
                  <el-button icon="el-icon-search"
                             style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 40px"
                             @click="openwin"></el-button>
                </el-form-item>
              </el-col>           
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="紧急度">
                  <el-select v-model="form.urgency_degree" placeholder="请选择" style="width: 100%;">
                    <el-option v-for="item in slaEventUrgentData" :key="item.id" :label="item.urgent_name"
                               :value="item.id"></el-option>

                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="影响度">
                  <el-select v-model="form.effect_degree" placeholder="请选择" style="width: 100%;">
                    <el-option v-for="item in upSlaEventImpactData" :key="item.id" :label="item.impact_name"
                               :value="item.id"></el-option>

                  </el-select>
                </el-form-item>
              </el-col>
              <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
              <!--<el-form-item label="优先级">-->
              <!--<el-button type="success" size="mini" plain>{{form.leave}}</el-button>-->
              <!--</el-form-item>-->
              <!--</el-col>-->
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="影响可用性">
                  <el-select v-model="form.is_effect" placeholder="请选择" style="width: 100%;">
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
						<span>
							<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
							处理信息
						</span>
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="服务群组">
                <el-select v-model="form.event_service_id" placeholder="请选择" style="width: 100%;">
                  <el-option
                    v-for="item in serviceData"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="处理人">
                <el-select v-model="form.handler" filterable remote reserve-keyword
                           placeholder="请输入姓名" :remote-method="remoteMethod" :loading="loading"
                           style="width: calc(100% - 40px)">
                  <template #prefix>
									<span style="padding-left: 2px">
										<i class="el-icon-search"></i>
									</span>
                  </template>
                  <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name"
                             :value="item.id"></el-option>
                </el-select>
                <el-button icon="el-icon-search"
                           style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 40px"
                           @click="openwinTable"></el-button>
              </el-form-item>
            </el-col>
            <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
            <!--<el-form-item label="开单人">-->
            <!--<el-select v-model="value" filterable remote reserve-keyword-->
            <!--placeholder="请输入姓名" :remote-method="remoteMethod" :loading="loading"-->
            <!--style="width: 80%">-->
            <!--<template #prefix>-->
            <!--<span style="padding-left: 2px">-->
            <!--<i class="el-icon-search"></i>-->
            <!--</span>-->
            <!--</template>-->
            <!--<el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name"-->
            <!--:value="item.id"></el-option>-->
            <!--</el-select>-->
            <!--<el-button icon="el-icon-search"-->
            <!--style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
            <!--@click="openwinTable"></el-button>-->
            <!--</el-form-item>-->
            <!--</el-col>-->
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="解决方式">
                <el-select v-model="form.solve_type" placeholder="请选择" style="width: 100%;">
                  <el-option v-for="item in upEventSolutionTypeData" :key="item.id" :label="item.solution_name"
                             :value="item.id"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="方案分类">
                <el-input @click.native="openwinTree" v-model="form.programme_type_name" autocomplete="off"
                          placeholder="请选择方案分类" style="width: calc(100% - 40px);"></el-input>
                <el-button icon="el-icon-search"
                           style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 40px"
                           @click="openwinTree"></el-button>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="解决方案">
                <quill-editor
                  ref="myQuillEditor2"
                  v-model="form.solution"
                  :min-height="200"
                  :options="optionsText2"
                ></quill-editor>
              </el-form-item>
            </el-col>

          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-alert title="配置参数:(标题、描述和解决方案) " :closable="false" type="success">
            日期（例如：2019-01-08）: (DATE)
            时间（例如：2019-01-08 15:36:22）: (DATETIME)
          </el-alert>
        </el-col>

      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <children ref="children" @categoryData="getBillType"
    ></children>
    <nameTable ref="nameTable" @fetch-data="
		      {
		        input2
		      }
		    "></nameTable>
    <codeTree ref="codeTree" @fetch-data="
		    getSchemeClassIfication
		  "></codeTree>
  </div>
</template>
<script>
  import vabQuill from '@/plugins/vabQuill'
  import {
    upList
  } from '@/api/selfService'
  import {
    upUserData
  } from '@/api/eventServiceManage'
  import {
    upRequestList,
    upSlaEventImpactList,
    slaEventUrgentList,
    upEventSourceList,
    upEventSolutionTypeList,
    save,
    eventTemplateInfo,
    edit
  } from '@/api/eventTemplate'
  import Children from '@/components/gongdanclass'
  import NameTable from './components/UserDiaTwo'
  import codeTree from './components/UserDiaThree'

  export default {
    name: 'persontemplateadd',
    components: {
      Children,
      vabQuill,
      NameTable,
      codeTree,
    },
    data() {
      return {
        upEventSolutionTypeData: [],
        input2: '',
        formArr: [{
          value1: ''
        }],
        formArr1: [{
          value1: ''
        }],
        tablelist: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        options: [],
        value: [],
        list: [],
        loading: false,
        states: [
          'Alabama', 'Alaska', 'Arizona', 'Arkansas', 'California', 'Colorado', 'Connecticut', 'Delaware', 'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois', 'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana', 'Maine', 'Maryland', 'Massachusetts', 'Michigan', 'Minnesota', 'Mississippi', 'Missouri', 'Montana', 'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey', 'New Mexico', 'New York', 'North Carolina', 'North Dakota', 'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania', 'Rhode Island', 'South Carolina', 'South Dakota', 'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia', 'Washington', 'West Virginia', 'Wisconsin', 'Wyoming',
        ],
        form: {
          username: '',
          truename: '',
          bumen: '',
          isvip: '',
          content: '',
          content2: '',
          leave: '',
          bill_type_name: '',
          bill_type: '',
          programme_type_name: "",
          templateNumber: "",
          template_name: "",
          is_public: "",
          event_service_id: "",
          is_use: "",
          template_desc: "",
          user_id: "",
          title: "",
          description: "",
          source: "",
          request_type: "",
          effect_degree: "",
          urgency_degree: "",
          is_effect: "",
          service_groups: "",
          handler: "",
          solve_type: "",
          programme_type: "",
          solution: ""
        },
        rules: {
          username: [{
            required: false,
            trigger: 'blur',
            message: '请输入名称'
          },],
          isvip: [{
            required: false,
            trigger: 'blur',
            message: '请选择状态'
          }],
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
        optionsText2: {
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
        auto_engineer_data: "",
        eventRequestData: [],
        eventSourceData: [],
        upSlaEventImpactData: [],
        slaEventUrgentData: [],
      }
    },
    created() {
      this.upServiceList();
      this.upUserData();
      this.upRequest();
      this.upEventSource();
      this.upSlaEventImpact();
      this.slaEventUrgent();
      this.upEventSolutionType();
      this.fetchData();
    },
    beforeDestroy() {
    },
    mounted() {
      this.list = this.states.map((item) => {
        return {
          value: `${item}`,
          label: `${item}`
        }
      })
      this.quillToTop()
    },
    methods: {
      quillToTop(){
        this.$refs.myQuillEditor1.quill.enable(false);
        this.$refs.myQuillEditor2.quill.enable(false);
        setTimeout(() => {
          this.$refs.myQuillEditor1.quill.enable(true);
          this.$refs.myQuillEditor2.quill.enable(true);
        },2000);
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
        this.selectRows = val
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      openwinTable() {
        this.$refs['nameTable'].showWin()
      },
      openwinTree() {
        this.$refs['codeTree'].showWin()
      },
      // handleAdd() {
      //   this.$refs['dialogone'].showWin()
      // },
      handleDelete() {
        if (this.selectRows != '') {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {
              msg
            } = await doDelete({
              ids
            })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      fetchData() {
        var that = this;
        if (null != that.$route.query.id || undefined != that.$route.query.id) {
          eventTemplateInfo(that.$route.query.id).then((res) => {
            console.log(res)
            that.form = res.data
            that.form.source = that.form.source?parseInt(that.form.source):'';
            that.form.request_type = that.form.request_type?parseInt(that.form.request_type):'';
            that.form.solve_type = that.form.solve_type?parseInt(that.form.solve_type):'';
            that.form.urgency_degree = that.form.urgency_degree?parseInt(that.form.urgency_degree):'';
            that.form.effect_degree = that.form.effect_degree?parseInt(that.form.effect_degree):'';
            that.form.event_service_id = that.form.event_service_id?parseInt(that.form.event_service_id):'';
            // that.form.service_groups = that.form.service_groups+"" ;
          });
        }
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
        var that = this;
        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            console.log(this.form)
            if (that.form.id == null || that.form.id == undefined || that.form.id == "") {
              save(that.form).then((res) => {
                if (res.code == 0) {
                  that.$baseMessage('操作成功', 'success')
                }
              });
            } else {
              edit(that.form).then((res) => {
                if (res.code == 0) {
                  that.$baseMessage('操作成功', 'success')
                }
              });
            }

            // this.$emit('fetch-data')
            that.fetchData()

            that.closeall()
          } else {
            return false
          }
        })
      },
      upServiceList() {
        var that = this;
        upList().then((res) => {
          that.serviceData = res.data;
        });
      },
      async upUserData() {
        var that = this;
        const resp = await upUserData()
        that.auto_engineer_data = resp.data;
      },
      upRequest() {
        var that = this;
        upRequestList().then((res) => {
          that.eventRequestData = res.data;
        });
      },
      upEventSource() {
        var that = this;
        upEventSourceList().then((res) => {
          that.eventSourceData = res.data;
        });
      },
      upSlaEventImpact() {
        var that = this;
        upSlaEventImpactList().then((res) => {
          that.upSlaEventImpactData = res.data;
        });
      },
      upEventSolutionType() {
        var that = this;
        upEventSolutionTypeList().then((res) => {
          that.upEventSolutionTypeData = res.data;
        });
      },

      slaEventUrgent() {
        var that = this;
        slaEventUrgentList().then((res) => {
          that.slaEventUrgentData = res.data;
        });
      },

      getBillType(data) {
        var that = this;
        that.form.bill_type_name = data.name;
        that.form.bill_type = data.id;
      },
      getSchemeClassIfication(data) {
        var that = this;
        that.form.programme_type_name = data.name;
        that.form.programme_type = data.id;
      }
    },
  }
</script>
<style lang="scss" scoped>
  .index-container {
    padding-bottom: 110px !important;
    .hiddenmore {
      display: inline;

      ::v-deep {
        .el-input {
          width: 130px;
        }
      }
    }
  }
  ::v-deep {
    .ql-container {
      min-height: 200px;
    }
  }

  .tips {
    color: #E6A23C;
  }

  .boxtable .btn-box {
    margin-top: 10px;
  }
</style>
