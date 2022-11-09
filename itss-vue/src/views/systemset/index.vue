<template>
  <div class="index-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'user-cog']"></vab-icon>
              用户
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>
          <div class="box">
            <div class="li" v-if="checkPermission('set_user')">
              <router-link to="/set/usermanagement">用户管理</router-link>
            </div>
            <div class="li">
              <router-link to="/set/rolesmanagement">角色管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_dept')">
              <router-link to="/set/departmentmana">部门管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_company')">
              <router-link to="/set/companymana">公司管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_map')">
              <router-link to="/set/peoplemap">人力地图</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'server']"></vab-icon>
              服务级别协议
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('set_event_affect')">
              <router-link to="/set/affect">事件影响度管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_event_urgent')">
              <router-link to="/set/urgency">事件紧急度管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_event_priority')">
              <router-link to="/set/priority">事件优先级管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_event_affect_urgent')">
              <router-link to="/set/relation">
                事件影响度紧急度关系管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('set_event_sla')">
              <router-link to="/set/SLA">事件SLA管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_question_priority')">
              <router-link to="/set/question">问题优先级管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_question_sla')">
              <router-link to="/set/questionSLA">问题SLA管理</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'clipboard']"></vab-icon>
              事件配置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('set_servergroup')">
              <router-link to="/set/group">服务群组管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_selfservertype')">
              <router-link to="/set/selfService">自助服务类别管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_source')">
              <router-link to="/set/origin">来源管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_applytype')">
              <router-link to="/set/request">请求类型管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_workordertype')">
              <router-link to="/set/workOrder">工单类别管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_solution')">
              <router-link to="/set/solution">解决方式管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_program')">
              <router-link to="/set/programme">方案分类管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_workordertemplate')">
              <router-link to="/set/workTemplate">工单模板管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_workordernotice')">
              <router-link to="/set/notice">工单通知配置</router-link>
            </div>
            <div class="li" v-if="checkPermission('set_event_config')">
              <router-link to="/set/event">事件参数设置</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'bug']"></vab-icon>
              问题配置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('question_source')">
              <router-link to="/set/faqsource">问题来源管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('question_type')">
              <router-link to="/set/faqtype">问题类别管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('question_notice')">
              <router-link to="/set/faqnotice">问题通知配置</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'pen']"></vab-icon>
              变更配置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>
          <div class="box">
            <div class="li" v-if="checkPermission('change_source')">
              <router-link to="/set/changesource">变更来源管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('change_type')">
              <router-link to="/set/changetype">变更类别管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('change_notice')">
              <router-link to="/set/changenotice">变更通知配置</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'location-arrow']"></vab-icon>
              发布配置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('publish_type')">
              <router-link to="/set/publishtype">发布类别管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('publish_notice')">
              <router-link to="/set/publishnotice">发布通知配置</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'cogs']"></vab-icon>
              配置设置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('assets_status')">
              <router-link to="/set/assetsmanagement">资产状态管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('purchase_company')">
              <router-link to="/set/purchasemanagement">
                购置公司管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('usage_scope')">
              <router-link to="/set/usescopemanagement">
                使用范围管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_brand')">
              <router-link to="/set/brandmanagement">品牌管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_os')">
              <router-link to="/set/osmanagement">操作系统管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_oslicense')">
              <router-link to="/set/oslicencemanagement">
                操作系统许可证管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_softwarelicense')">
              <router-link to="/set/softwaremanagement">
                软件许可证管理
              </router-link>
            </div>
            <!--<div class="li">-->
            <!--<router-link to="/set/opentypemanagement">-->
            <!--启用类型管理-->
            <!--</router-link>-->
            <!--</div>-->
            <div class="li" v-if="checkPermission('assets_backup')">
              <router-link to="/set/backuptypemanagement">
                备份方式管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('contract_contact')">
              <router-link to="/set/contractpeoplemanagement">
                合同联系人管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('client_contract')">
              <router-link to="/set/contractcustommanagement">
                客户合同管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('supply_contract')">
              <router-link to="/set/contractshopmanagement">
                供应商合同管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_apply')">
              <router-link to="/set/setstypemanagement">
                配置请求类型管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_ci')">
              <router-link to="/set/cigroupmanagement">CI分组管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_type')">
              <router-link to="/set/configtypemanagement">
                配置项类型管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('assets_config')">
              <router-link to="/set/configparaset">
                配置管理参数设置
              </router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'book']"></vab-icon>
              知识库配置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <!--<div class="li">-->
            <!--<router-link to="/set/booknoticeconfig">-->
            <!--知识通知配置-->
            <!--</router-link>-->
            <!--</div>-->
            <div class="li" v-if="checkPermission('knowledge_type')">
              <router-link to="/set/bookclassmanagement">
                知识类别管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('konwledge_scope')">
              <router-link to="/set/bookviewrange">
                知识查看范围管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('knowledge_config')">
              <router-link to="/set/booksets">知识库配置</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'mail-bulk']"></vab-icon>
              邮件管理
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('email_account')">
              <router-link to="/set/emailaccountmanagement">
                邮件账户管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('email_sign')">
              <router-link to="/set/emailnametemplate">
                邮件签名模板
              </router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'mobile']"></vab-icon>
              移动端配置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('weixin_config')">
              <router-link to="/set/weixinsets">微信参数设置</router-link>
            </div>
            <div class="li" v-if="checkPermission('dingding_config')">
              <router-link to="/set/dingdingsets">钉钉配置管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('feishu_config')">
              <router-link to="/set/flybooksets">飞书配置管理</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
        <el-card class="cardbox" shadow="hover">
          <div slot="header">
            <span>
              <vab-icon :icon="['fas', 'tags']"></vab-icon>
              系统设置
            </span>
            <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
          </div>

          <div class="box">
            <div class="li" v-if="checkPermission('workday_manage')">
              <router-link to="/set/sysworkdays">工作日管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('announcement_manage')">
              <router-link to="/set/noticemanagement">公告管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('index_background')">
              <router-link to="/set/homeppmanagement">
                首页背景图管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('print_templete')">
              <router-link to="/set/printmanagement">打印模板管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('sql_report')">
              <router-link to="/set/sqltablemanagement">
                SQL报表管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('expand_form')">
              <router-link to="/set/extendformmanagement">
                扩展表单管理
              </router-link>
            </div>
            <div class="li" v-if="checkPermission('file_manage')">
              <router-link to="/set/filesmanagement">文件管理</router-link>
            </div>
            <div class="li" v-if="checkPermission('system_register')">
              <router-link to="/set/sysreg">系统注册</router-link>
            </div>
            <div class="li" v-if="checkPermission('system_config')">
              <router-link to="/set/sysparaset">系统参数设置</router-link>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {getusersetsList} from '@/api/usersets'
  import permission from '@/directive/permission/index.js'
  import checkPermission from '@/utils/permission' // 权限判断函数
  export default {
    name: 'userIndex',
    directives: {permission},
    components: {},
    data() {
      return {
        noticeList: [],
      }
    },
    created() {
      this.fetchData()
    },
    beforeDestroy() {
      clearInterval(this.timer)
    },
    mounted() {
    },
    methods: {
      async fetchData() {
        // const res = await getusersetsList()
        // this.noticeList = res.data
      },
      checkPermission,
    },
  }
</script>
<style lang="scss" scoped>
  .index-container {
    padding: 0 !important;
    margin: 0 !important;
    background: #f5f7f8 !important;

    ::v-deep {
      .el-card__header {
        font-size: 20px;
      }

      .el-card__body {
        // display: grid;
        // box-sizing: border-box;
        // grid-template-columns: repeat(3, 31.33%);
        // grid-gap: 2%;

        min-height: 220px;

        .li {
          margin-bottom: 10px;
          margin-right: 10px;
          height: auto;

          a {
            padding: 0 10px;
            box-sizing: border-box;
            display: block;
            width: 100%;
            height: 100%;
            background-color: #e8f4ff;
            border: 1px solid #d1e9ff;
            text-align: center;
            line-height: 24px;
            font-size: 12px;
            border-radius: 3px;
          }

          a:hover {
            opacity: 0.7;
          }

          p {
            margin: 0;
          }
        }
      }
    }

    .box {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
    }

    .cardbox {
      ::v-deep {
        .el-card__header {
          div {
            display: flex;
            flex-direction: row;
            justify-content: space-between;

            svg {
              margin: 0 5px;
              color: #666;
              cursor: pointer;
            }
          }
        }
      }
    }

    .bottom {
      padding-top: 20px;
      margin-top: 5px;
      color: #595959;
      text-align: left;
      border-top: 1px solid $base-border-color;
    }

    .bottom-btn {
      button {
        margin: 5px 10px 15px 0;
      }
    }
  }
</style>
