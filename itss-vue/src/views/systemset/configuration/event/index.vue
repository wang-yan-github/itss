<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="220px">
      <template>
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="基础信息" name="first">

            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <!-- <div class="box_header">工程师开单通知用户</div> -->
                <div class="box_content">
                  <el-row :gutter="20">
<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="启用“首问责任制”" class="">-->
<!--                        <el-checkbox true-label="1" false-label="0" v-model="form.is_first_question"></el-checkbox>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="工单超时自动升级" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.is_auto_update"></el-checkbox>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="开单时可选择所有服务群组" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.is_billing"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="指派时可选择所有服务群组" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.is_designate"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="转单时可转给所有服务群组" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.is_transfer_order"></el-checkbox>
                      </el-form-item>
                    </el-col>
<!--                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
<!--                      <el-form-item label="统计前N位开单人/解决人/关单人的工单数" placeholder="请输入" class="labelline">-->
<!--                        <el-input v-model.trim="form.num" autocomplete="off" oninput="value=value.replace(/[^\d]/g,'')">-->
<!--                        </el-input>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
                  </el-row>
                </div>
              </div>
            </el-card>
          </el-tab-pane>
<!--          <el-tab-pane label="自动暂停/恢复事件单" name="second">-->
<!--            <el-card shadow="never" style="border-top: 0">-->

<!--              <div class="box_card">-->
<!--                <div class="box_content">-->
<!--                  <el-row :gutter="20">-->

<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="根据工作日和上下班时间自动暂停并恢复事件单" class="labelline">-->
<!--                        <el-checkbox true-label="1" false-label="0" v-model="form.is_working_day_zthf"></el-checkbox>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->

<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="手动暂停的事件单自动恢复" class="">-->
<!--                        <el-checkbox true-label="1" false-label="0" v-model="form.is_pause_manually"></el-checkbox>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->

<!--                  </el-row>-->
<!--                </div>-->
<!--              </div>-->

<!--            </el-card>-->

<!--          </el-tab-pane>-->
          <el-tab-pane label="满意度评价设置" name="third">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="满意度评价时效性" class="">
                        <template>
                          <el-radio v-model="form.is_aging_switch" label="1">开</el-radio>
                          <el-radio v-model="form.is_aging_switch" label="0">关</el-radio>
                        </template>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="评价超时天数" placeholder="请输入" class="">
                        <el-input v-model.trim="form.timeout_days" autocomplete="off"
                                  oninput="value=value.replace(/[^\d]/g,'')">
                        </el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="超时默认自动评价" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.default_rating"></el-checkbox>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="自动评价为" placeholder="请输入" class="">
                        <el-rate v-model="form.automatic_evaluation"></el-rate>
                        <!--<el-input v-model.trim="form.automatic_evaluation" autocomplete="off" type="textarea"-->
                        <!--:rows="2"></el-input>-->
                      </el-form-item>
                    </el-col>

<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="满意度评价强提醒" class="">-->
<!--                        <el-checkbox true-label="1" false-label="0" v-model="form.is_strong_reminder"></el-checkbox>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->

                  </el-row>
                </div>
              </div>

            </el-card>
          </el-tab-pane>
          <el-tab-pane label="自助服务" name="fourth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_content">
                  <el-row :gutter="20">

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="记住报障类别" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.is_category"></el-checkbox>
                      </el-form-item>
                    </el-col>

                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="报障页显示配置项选择" class="">
                        <el-checkbox true-label="1" false-label="0" v-model="form.is_show_config"></el-checkbox>
                      </el-form-item>
                    </el-col>
<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="扫码报障标题模板" class="">-->
<!--                        <el-input v-model.trim="form.title_template" autocomplete="off" type="textarea"-->
<!--                                  :rows="2"></el-input>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="扫码报障描述模板" class="">-->
<!--                        <el-input v-model.trim="form.describe_template" autocomplete="off" type="textarea"-->
<!--                                  :rows="2"></el-input>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->

                  </el-row>
                </div>
              </div>
<!--              <el-alert type="success" title="配置参数" :closable="false" class="setdata">-->
<!--                <el-row :gutter="20">-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    资产编号: (EQID)-->
<!--                  </el-col>-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    资产名称: (EQNAME)-->
<!--                  </el-col>-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    部门: (ORGAN)-->
<!--                  </el-col>-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    领用人: (NAME)-->
<!--                  </el-col>-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    配置项类型: (TYPE)-->
<!--                  </el-col>-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    购置公司: (CITY)-->
<!--                  </el-col>-->
<!--                  <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                    用途: (USAGE)-->
<!--                  </el-col>-->

<!--                </el-row>-->
<!--              </el-alert>-->

            </el-card>
          </el-tab-pane>
<!--          <el-tab-pane label="用户确认" name="fifth">-->
<!--            <el-card shadow="never" style="border-top: 0">-->

<!--              <div class="box_card">-->
<!--                <div class="box_content">-->
<!--                  <el-row :gutter="20">-->

<!--                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                      <el-form-item label="启用“用户确认”" class="">-->
<!--                        <el-checkbox true-label="1" false-label="0" v-model="form.is_user_confirm"></el-checkbox>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->
<!--                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
<!--                      <el-form-item label="用户确认超时时间(天)" placeholder="请输入" class="">-->
<!--                        <el-input v-model.trim="form.user_time_out" autocomplete="off"-->
<!--                                  oninput="value=value.replace(/[^\d]/g,'')">-->
<!--                        </el-input>-->
<!--                      </el-form-item>-->
<!--                    </el-col>-->

<!--                  </el-row>-->
<!--                </div>-->
<!--              </div>-->

<!--            </el-card>-->
<!--          </el-tab-pane>-->
          <el-tab-pane label="重开时效性" name="sixth">
            <el-card shadow="never" style="border-top: 0">

              <div class="box_card">
                <div class="box_content">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="启用“重开时效性”" class="">
                      <el-checkbox true-label="1" false-label="0" v-model="form.is_reopen"></el-checkbox>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="重开超时时间(天) " placeholder="请输入" class="">
                      <el-input v-model.trim="form.reopen_day" autocomplete="off"
                                oninput="value=value.replace(/[^\d]/g,'')">
                      </el-input>
                    </el-form-item>
                  </el-col>
                </div>
              </div>

            </el-card>
          </el-tab-pane>
        </el-tabs>
      </template>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

  </div>
</template>

<script>
  import {
    doView,
    doAdd,
    doEdit
  } from '@/api/eventParamConfig'

  export default {
    name: 'event',

    data() {
      return {
        activeName: 'first',
        elementLoadingText: '正在加载...',
        options: [],
        value: [],
        list: [],
        listLoading: true,
        loading: false,
        form: {
          //2	是否启用“首问责任制	is_first_question	varchar(2)		是否启用“首问责任制”0否  1是
          is_first_question: '0',
          //3	工单超时自动升级	is_auto_update	varchar(2)		工单超时自动升级0否  1是
          is_auto_update: '0',
          //4	开单时可选择所有服务群组	is_billing	varchar(2)		开单时可选择所有服务群组0否  1是
          is_billing: '0',
          //5	指派时可选择所有服务群组	is_designate	varchar(2)		指派时可选择所有服务群组0否  1是
          is_designate: '0',
          //6	转单时可转给所有服务群组	is_transfer_order	varchar(2)		转单时可转给所有服务群组0否  1是
          is_transfer_order: '0',
          //7	统计前N位开单人	num	int		统计前N位开单人/解决人/关单人的工单数
          num: '',
          //8	是否自动恢复事件单	is_working_day_zthf	varchar(2)		根据工作日和上下班时间自动暂停并恢复事件单0否  1是
          is_working_day_zthf: '0',
          //9	手动暂停的事件单自动恢复	is_pause_manually	varchar(2)		手动暂停的事件单自动恢复0否  1是
          is_pause_manually: '0',
          //10	满意度评价时效性	is_aging_switch	varchar(2)		满意度评价时效性0否1是
          is_aging_switch: '0',
          //11	评价超时天数	timeout_days	int		评价超时天数
          timeout_days: '',
          //12	超时默认评价	default_rating	int		超时默认评价
          default_rating: '',
          //13	自动评价为	automatic_evaluation	varchar(2)
          automatic_evaluation: 0,
          //14	满意度评价强提醒	is_strong_reminder	varchar(2)		满意度评价强提醒0否1是
          is_strong_reminder: '0',
          // 记住报障类别 0否  1是
          is_category: '0',
          // 报障页显示配置项选择 0否  1是
          is_show_config: '0',
          // 扫码报障标题模板
          title_template: '',
          // 扫码报障描述模板
          describe_template: '',
          //15	是否启用“用户确认”	is_user_confirm	varchar(2)		启用“用户确认”0否1是
          is_user_confirm: '0',
          //16	用户确认超时时间(天):	user_time_out	int		用户确认超时时间(天):
          user_time_out: '',
          //17	启用“重开时效性”	is_reopen	varchar(2)		启用“重开时效性”0否1是
          is_reopen: '0',
          //18	重开超时时间	reopen_day	int		重开超时时间(天):
          reopen_day: '',
        },
        rules: {
          // username: [{
          //   required: true,
          //   trigger: 'blur',
          //   message: '请输入名称'
          // },],
        },
        radio: '1',
        checked: false,
        checked2: false,
        checked3: false,
        checked4: false,
        checked5: false,
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
      async fetchData() {
        this.listLoading = true;
        const {data} = await doView();
        console.log(data);
        if (data) {
          this.form = data;
          this.form.num = data.num?parseInt(data.num):'';
          console.log("this.form.timeout_days:",data.timeout_days);
          this.form.timeout_days = data.timeout_days?parseInt(data.timeout_days):(data.timeout_days == 0?0:'');
          this.form.user_time_out = data.user_time_out?parseInt(data.user_time_out):'';
          this.form.reopen_day = data.reopen_day?parseInt(data.reopen_day):'';
          this.form.automatic_evaluation = data.automatic_evaluation?parseInt(data.automatic_evaluation):'';
        }
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route);
        // 返回上一步路由
        this.$router.go(-1);
      },
      saveok() {
        console.log(this.form);
        // if (this.form.id) {
        //   console.log(111);
        // } else {
        //   console.log(2222);
        // }
        // return false;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            if(this.form.is_aging_switch == '1') {
              if (!this.form.timeout_days) {
                this.$baseMessage('请填写评价超时天数', 'error');
                return false;
              }
            }
            console.log(this.form.id);
            if (this.form.id) {
              const {msg} = await doEdit(this.form);
              this.$baseMessage(msg, 'success');
            } else {
              const {msg} = await doAdd(this.form);
              this.$baseMessage(msg, 'success');
            }

            // this.$emit('fetch-data')
            // this.fetchData()

            this.closeall();
          } else {
            return false;
          }
        })
      },
    },
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
    color: #E6A23C;
  }

  .boxtable .btn-box {
    margin-top: 10px;
  }

  ::v-deep {
    .d {
      label {
        width: 260 !important;
        text-align: left;
      }

      .el-form-item__content {
        margin-left: 0 !important;
      }
    }

    .filltext {
      margin: 0;
    }

    .setdata .el-alert__content {
      width: 100%;
    }

    .el-tabs__header {
      margin-bottom: 0 !important;
    }

    .el-tabs__item.is-active {
      background: #fff !important;
    }

    .el-card__body {
      /*height: 620px !important;*/
      height: calc(100vh - 330px);
      overflow-y: auto;
      overflow-x: hidden;
    }

    .labelline .el-form-item__label {
      line-height: 18px;
    }
  }
</style>
