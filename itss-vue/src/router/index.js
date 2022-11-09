import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layouts'
import EmptyLayout from '@/layouts/EmptyLayout'
import { publicPath, routerMode } from '@/config'

Vue.use(VueRouter)
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true,
  },
  {
    path: '/register',
    component: () => import('@/views/register/index'),
    hidden: true,
  },
  {
    path: '/401',
    name: '401',
    component: () => import('@/views/401'),
    hidden: true,
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404'),
    hidden: true,
  },
  {
    path: '/printpaper',
    name: 'printpaper',
    component: () => import('@/components/printpaper'),
    hidden: true,
  },

]

export const asyncRoutes =
  [
    {
      path: '/',
      component: Layout,
      redirect: 'index',
      hidden: true,
      meta: { title: '我的主页', permissions: 'index' },
      children: [
        {
          isButton: true,
          path: 'index',
          name: 'index',
          component: () => import('@/views/index/index'),
          meta: {
            isButton: true,
            title: '我的主页',
            affix: true,
          },
        },
      ],
    },

    {
      path: '/thing',
      component: Layout,
      redirect: 'noRedirect',
      alwaysShow: true,
      meta: { title: '事件', icon: 'eraser', permissions: 'event' },
      children: [
        {
          path: 'gongzuotai',
          name: 'gongzuotai',
          component: () => import('@/views/events/workbench/index'),
          meta: {
            title: '工作台',
            permissions: 'event_workbench',
          },
        },
        {
          path: 'table',
          component: () => import('@/views/events/whole/index'),
          name: 'table',
          meta: {
            title: '全部事件管理',
            permissions: 'event_manage',
          },
        },
        {
          path: 'map',
          component: () => import('@/views/events/assist/index'),
          name: 'map',
          meta: {
            title: '事件协查',
            permissions: 'event_investigation',
          },
        },

        {
          path: 'webSocket',
          name: 'webSocket',
          component: () => import('@/views/events/self/index'),
          meta: { title: '自助服务', permissions: 'event_selfhelp' },
        },
        {
          path: 'form',
          name: 'form',
          component: () => import('@/views/events/statistics/index'),
          meta: { title: '事件统计', permissions: 'event_statistics' },
        },
      ],
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'statisticsViewEvent',
          name: 'statisticsViewEvent',
          component: () => import('@/views/events/statistics/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'wholeClose',
          name: 'wholeClose',
          component: () => import('@/views/events/whole/close'),
          meta: {
            isButton: true,
            title: '批量关单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'wholeEdit',
          name: 'wholeEdit',
          component: () => import('@/views/events/whole/Edit'),
          meta: {
            isButton: true,
            title: '事件修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'gztadd',
          name: 'gztadd',
          component: () => import('@/views/events/workbench/add'),
          meta: {
            isButton: true,
            title: '开单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'wykdadd',
          name: 'wykdadd',
          component: () => import('@/views/events/workbench/automaticAdd'),
          meta: {
            isButton: true,
            title: '我要开单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'selfBill',
          name: 'selfBill',
          component: () => import('@/views/events/self/selfbill'),
          meta: {
            isButton: true,
            title: '我的工单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'selfEdit',
          name: 'selfEdit',
          component: () => import('@/views/events/self/selfEdit'),
          meta: {
            isButton: true,
            title: '修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'workingcanyu',
          name: 'workingcanyu',
          component: () => import('@/views/events/workbench/canyu'),
          meta: {
            isButton: true,
            title: '参与的',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'wholeview',
          name: 'wholeview',
          component: () => import('@/views/events/whole/View'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'workingchuli',
          name: 'workingchuli',
          component: () => import('@/views/events/workbench/chuli'),
          meta: {
            isButton: true,
            title: '处理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'workingzhipai',
          name: 'workingzhipai',
          component: () => import('@/views/events/workbench/zhipai'),
          meta: {
            isButton: true,
            title: '指派',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'groupthing',
          name: 'groupthing',
          component: () => import('@/views/events/workbench/groupthing'),
          meta: {
            isButton: true,
            title: '组内的全部事件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'persontemplatemanagement',
          name: 'persontemplatemanagement',
          component: () => import('@/views/events/workbench/persontemplate/index'),
          meta: {
            isButton: true,
            title: '个人模板管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'persontemplateadd',
          name: 'persontemplateadd',
          component: () => import('@/views/events/workbench/persontemplate/add'),
          meta: {
            isButton: true,
            title: '个人模板管理添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'persontemplateedit',
          name: 'persontemplateadd',
          component: () => import('@/views/events/workbench/persontemplate/add'),
          meta: {
            isButton: true,
            title: '个人模板管理修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'persontemplateview',
          name: 'persontemplateview',
          component: () => import('@/views/events/workbench/persontemplate/view'),
          meta: {
            isButton: true,
            title: '个人模板管理查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'persontemplatetiming',
          name: 'persontemplatetiming',
          component: () => import('@/views/events/workbench/persontemplate/timing/index'),
          meta: {
            isButton: true,
            title: '定时任务',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'workingzd',
          name: 'workingzd',
          component: () => import('@/views/events/workbench/zhuandan'),
          meta: {
            isButton: true,
            title: '转单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'wholecreatchange',
          name: 'wholecreatchange',
          component: () => import('@/views/change/mechange/add'),
          meta: {
            isButton: true,
            title: '生成变更',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: '/noRedirect',
      children: [
        {
          isButton: true,
          path: 'createQuestion',
          name: 'createQuestion',
          component: () => import('@/views/question/myquestion/add'),
          meta: {
            isButton: true,
            title: '生成问题',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'createKnow',
          name: 'createKnow',
          component: () => import('@/views/knowledge/myKnowledge/myKnowledgeAdd'),
          meta: {
            isButton: true,
            title: '生成知识',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'thingcapitalIndex',
          name: 'thingcapitalIndex',
          component: () => import('@/views/allocation/management/capitalIndex'),
          // component: () => import('@/views/events/self/capitalIndex'),
          meta: {
            isButton: true,
            title: '资产盘点',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'thingcapitaView',
          name: 'thingcapitaView',
          component: () => import('@/views/allocation/management/capitaView'),
          meta: {
            isButton: true,
            title: '资产盘点查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'thingcapitaConfirm',
          name: 'thingcapitaConfirm',
          component: () => import('@/views/allocation/management/capitaConfirm'),
          meta: {
            isButton: true,
            title: '资产盘点确认',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/thing',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'thingEvaluate',
          name: 'thingEvaluate',
          component: () => import('@/views/events/self/evaluate'),
          meta: {
            isButton: true,
            title: '确认评价',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      name: 'Faq',
      meta: { title: '问题', icon: 'bug', permissions: 'faq' },
      children: [
        {
          path: 'userManagement',
          name: 'userManagement',
          component: () => import('@/views/question/myquestion/index'),
          meta: { title: '我的问题', permissions: 'faq_my' },
        },
        {
          path: 'roleManagement',
          name: 'roleManagement',
          component: () =>
            import('@/views/question/dealtWith/index'),
          meta: { title: '问题待办', permissions: 'faq_upcoming' },
        },
        {
          path: 'menuManagement',
          name: 'menuManagement',
          component: () =>
            import('@/views/question/manage/index'),
          meta: { title: '问题管理', permissions: 'faq_manage' },
        },
        {
          path: 'faqtongji',
          name: 'faqtongji',
          component: () =>
            import('@/views/question/statistics/index'),
          meta: { title: '问题统计', permissions: 'faq_statistics' },
        },
      ],
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'myQuestAdd',
          name: 'myQuestAdd',
          component: () => import('@/views/question/myquestion/add'),
          meta: {
            isButton: true,
            title: '添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealtCanyu',
          name: 'dealtCanyu',
          component: () => import('@/views/question/dealtWith/canyu'),
          meta: {
            isButton: true,
            title: '我参与的问题',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'statisticsView',
          name: 'statisticsView',
          component: () => import('@/views/question/statistics/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealRelation',
          name: 'dealRelation',
          component: () => import('@/views/question/dealtWith/relation'),
          meta: {
            isButton: true,
            title: '修改关联',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealChuli',
          name: 'dealChuli',
          component: () => import('@/views/question/dealtWith/chuli'),
          meta: {
            isButton: true,
            title: '处理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealZhuandan',
          name: 'dealZhuandan',
          component: () => import('@/views/question/dealtWith/zhuandan'),
          meta: {
            isButton: true,
            title: '转单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealExamine',
          name: 'dealExamine',
          component: () => import('@/views/question/dealtWith/examine'),
          meta: {
            isButton: true,
            title: '审核',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealAppraisal',
          name: 'dealAppraisal',
          component: () => import('@/views/question/dealtWith/appraisal'),
          meta: {
            isButton: true,
            title: '鉴定',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'faqEdit',
          name: 'faqEdit',
          component: () => import('@/views/question/manage/Edit'),
          meta: {
            isButton: true,
            title: '修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'createKnow',
          name: 'createKnow',
          component: () => import('@/views/knowledge/myKnowledge/myKnowledgeAdd'),
          meta: {
            isButton: true,
            title: '生成知识',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'manageZhuanban',
          name: 'manageZhuanban',
          component: () => import('@/views/question/manage/zhuanban'),
          meta: {
            isButton: true,
            title: '转办',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'faqClose',
          name: 'faqClose',
          component: () => import('@/views/question/manage/close'),
          meta: {
            isButton: true,
            title: '关单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'faqView',
          name: 'faqView',
          component: () => import('@/views/question/manage/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'faqPrint',
          name: 'faqPrint',
          component: () => import('@/views/question/manage/print'),
          meta: {
            isButton: true,
            title: '打印',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/faq',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealCreatchange',
          name: 'dealCreatchange',
          component: () => import('@/views/change/mechange/add'),
          meta: {
            isButton: true,
            title: '生成变更',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      name: 'Biangeng',
      meta: {
        title: '变更',
        icon: 'exchange-alt',
        permissions: 'change',
      },

      children: [
        {
          path: 'pay',
          name: 'pay',
          component: () => import('@/views/change/mechange/index'),
          meta: {
            title: '我的变更', permissions: 'change_my'
            // noKeepAlive: true,
          },
        },
        {
          path: 'changeDeal',
          name: 'changeDeal',
          component: () => import('@/views/change/dealtWith/index'),
          meta: {
            title: '变更待办', permissions: 'change_upcoming'
          },
        },
        {
          path: 'biangengManagement',
          name: 'biangengManagement',
          component: () => import('@/views/change/manage/index'),
          meta: {
            title: '变更管理', permissions: 'change_manage'
          },
        },
        {
          path: 'biangengtongji',
          name: 'biangengtongji',
          component: () => import('@/views/change/statistics/index'),
          meta: {
            title: '变更统计', permissions: 'change_statistics'
          },
        },
      ],
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'statisticsBgView',
          name: 'statisticsBgView',
          component: () => import('@/views/change/statistics/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'addmechange',
          name: 'addmechange',
          component: () => import('@/views/change/mechange/add'),
          meta: {
            isButton: true,
            title: '我的变更添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'editmechange',
          name: 'editmechange',
          component: () => import('@/views/change/mechange/edit'),
          meta: {
            isButton: true,
            title: '我的变更修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'editmechangeFile',
          name: 'editmechangeFile',
          component: () => import('@/views/change/mechange/editFile'),
          meta: {
            isButton: true,
            title: '我的变更修改附件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'editchangeRelevance',
          name: 'editchangeRelevance',
          component: () => import('@/views/change/mechange/editRelevance'),
          meta: {
            isButton: true,
            title: '修改变更关联',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeCanyu',
          name: 'changeCanyu',
          component: () => import('@/views/change/dealtWith/canyu'),
          meta: {
            isButton: true,
            title: '我参与的变更',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeScheme',
          name: 'changeScheme',
          component: () => import('@/views/change/dealtWith/scheme'),
          meta: {
            isButton: true,
            title: '制定方案',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeExamine',
          name: 'changeExamine',
          component: () => import('@/views/change/dealtWith/examine'),
          meta: {
            isButton: true,
            title: '变更审批',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'detalisExamine',
          name: 'detalisExamine',
          component: () => import('@/views/change/dealtWith/details'),
          meta: {
            isButton: true,
            title: '详情',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeCheck',
          name: 'changeCheck',
          component: () => import('@/views/change/dealtWith/check'),
          meta: {
            isButton: true,
            title: '审核方案',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeTransform',
          name: 'changeTransform',
          component: () => import('@/views/change/manage/transform'),
          meta: {
            isButton: true,
            title: '转办',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeTransOrder',
          name: 'changeTransOrder',
          component: () => import('@/views/change/dealtWith/transOrder'),
          meta: {
            isButton: true,
            title: '转单',
            affix: false,
          },
        },
      ],
      hidden: true,
    },

    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeImplementFront',
          name: 'changeImplementFront',
          component: () => import('@/views/change/dealtWith/implementFront'),
          meta: {
            isButton: true,
            title: '实施',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'changeImplement',
          name: 'changeImplement',
          component: () => import('@/views/change/dealtWith/implement'),
          meta: {
            isButton: true,
            title: '实施后评审',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/biangeng',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'createPublish',
          name: 'createPublish',
          component: () => import('@/views/publish/myPublish/Add'),
          meta: {
            isButton: true,
            title: '生成发布',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      name: 'Fabu',
      meta: { title: '发布', icon: 'plus', permissions: 'publish' },
      children: [
        {
          path: 'myPublish',
          name: 'myPublish',
          component: () => import('@/views/publish/myPublish/index'),
          meta: { title: '我的发布', permissions: 'publish_my' },
        },
        {
          path: 'publishDb',
          name: 'publishDb',
          component: () => import('@/views/publish/dealtWith/index'),
          meta: { title: '发布待办', permissions: 'publish_upcoming' },
        },
        {
          path: 'publishManage',
          name: 'publishManage',
          component: () => import('@/views/publish/manage/index'),
          meta: { title: '发布管理', permissions: 'publish_manage' },
        },
        {
          path: 'publishReport',
          name: 'publishReport',
          component: () => import('@/views/publish/statistics/index'),
          meta: { title: '发布统计', permissions: 'publish_statistics' },
        },
      ],
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishAdd',
          name: 'publishAdd',
          component: () => import('@/views/publish/myPublish/Add'),
          meta: {
            isButton: true,
            title: '我的发布添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishEdit',
          name: 'publishEdit',
          component: () => import('@/views/publish/myPublish/Edit'),
          meta: {
            isButton: true,
            title: '我的发布修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishManageEdit',
          name: 'publishManageEdit',
          component: () => import('@/views/publish/manage/Edit'),
          meta: {
            isButton: true,
            title: '发布管理修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishView',
          name: 'publishView',
          component: () => import('@/views/publish/manage/View'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishCanyu',
          name: 'publishCanyu',
          component: () => import('@/views/publish/dealtWith/canyu'),
          meta: {
            isButton: true,
            title: '参与的',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishExamine',
          name: 'publishExamine',
          component: () => import('@/views/publish/dealtWith/examine'),
          meta: {
            isButton: true,
            title: '评审',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishTesting',
          name: 'publishTesting',
          component: () => import('@/views/publish/dealtWith/testing'),
          meta: {
            isButton: true,
            title: '测试',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/fabu',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'publishRelease',
          name: 'publishRelease',
          component: () => import('@/views/publish/dealtWith/release'),
          meta: {
            isButton: true,
            title: '发布',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      name: 'pzgl',
      meta: { title: '配置管理', icon: 'users-cog', permissions: 'pzgl' },
      children: [
        {
          path: 'assetsConfigInfo',
          name: 'assetsConfigInfo',
          component: () => import('@/views/allocation/overview/index'),
          meta: { title: '概览', permissions: 'pzgl_overview' },
        },
        {
          path: 'assetsConfigManage',
          name: 'assetsConfigManage',
          component: () => import('@/views/allocation/management/index'),
          meta: { title: '配置项管理', permissions: 'pzgl_manage' },
        },
        {
          path: 'assetsConfigReq',
          name: 'assetsConfigReq',
          component: () => import('@/views/allocation/request/index'),
          meta: { title: '配置请求管理', permissions: 'pzgl_require' },
        },
        {
          path: 'assetsConfigReport',
          name: 'assetsConfigReport',
          component: () => import('@/views/allocation/statistics/index'),
          meta: { title: '配置项统计', permissions: 'pzgl_statistics' },
        },
        {
          path: 'MyAssetsConfigReq',
          name: 'MyAssetsConfigReq',
          component: () => import('@/views/allocation/myrequest/index'),
          meta: { title: '我的配置请求', permissions: 'pzgl_myrequire' },
        },
        {
          path: 'assetsConfigDb',
          name: 'assetsConfigDb',
          component: () => import('@/views/allocation/dealtWith/index'),
          meta: { title: '配置请求待办', permissions: 'pzgl_upcoming' },
        },
      ],
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'staViewAllocation',
          name: 'staViewAllocation',
          component: () => import('@/views/allocation/statistics/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'myRequestAdd',
          name: 'myRequestAdd',
          component: () => import('@/views/allocation/myrequest/Add'),
          meta: {
            isButton: true,
            title: '添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'myRequestEdit',
          name: 'myRequestEdit',
          component: () => import('@/views/allocation/myrequest/Edit'),
          meta: {
            isButton: true,
            title: '修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },

    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'assetsConfigView',
          name: 'assetsConfigView',
          component: () => import('@/views/allocation/request/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'dealtWithChuli',
          name: 'dealtWithChuli',
          component: () => import('@/views/allocation/dealtWith/chuli'),
          meta: {
            isButton: true,
            title: '处理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'tolist',
          name: 'tolist',
          component: () => import('@/views/allocation/management/index'),
          meta: {
            isButton: true,
            title: '配置项列表',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'manageSelAdd',
          name: 'manageSelAdd',
          component: () => import('@/views/allocation/management/Add'),
          meta: {
            isButton: true,
            title: '配置项添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'enclosureAdd',
          name: 'enclosureAdd',
          component: () => import('@/views/allocation/management/enclosure/add'),
          meta: {
            isButton: true,
            title: '添加机位',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'enclosureView',
          name: 'enclosureView',
          component: () => import('@/views/allocation/management/enclosure/view'),
          meta: {
            isButton: true,
            title: '查看机位',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'enclosureEdit',
          name: 'enclosureEdit',
          component: () => import('@/views/allocation/management/enclosure/edit'),
          meta: {
            isButton: true,
            title: '修改机位',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'rackAdd',
          name: 'rackAdd',
          component: () => import('@/views/allocation/management/rack/add'),
          meta: {
            isButton: true,
            title: '添加机柜',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'rackView',
          name: 'rackView',
          component: () => import('@/views/allocation/management/rack/view'),
          meta: {
            isButton: true,
            title: '查看机柜',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'rackEdit',
          name: 'rackEdit',
          component: () => import('@/views/allocation/management/rack/edit'),
          meta: {
            isButton: true,
            title: '修改机柜',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'nasAdd',
          name: 'nasAdd',
          component: () => import('@/views/allocation/management/nas/add'),
          meta: {
            isButton: true,
            title: '添加NAS',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'nasView',
          name: 'nasView',
          component: () => import('@/views/allocation/management/nas/view'),
          meta: {
            isButton: true,
            title: '查看NAS',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'nasEdit',
          name: 'nasEdit',
          component: () => import('@/views/allocation/management/nas/edit'),
          meta: {
            isButton: true,
            title: '修改NAS',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'telAdd',
          name: 'telAdd',
          component: () => import('@/views/allocation/management/telephone/telAdd'),
          meta: {
            isButton: true,
            title: '添加手机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'telEdit',
          name: 'telEdit',
          component: () => import('@/views/allocation/management/telephone/telEdit'),
          meta: {
            isButton: true,
            title: '修改手机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'telView',
          name: 'telView',
          component: () => import('@/views/allocation/management/telephone/view'),
          meta: {
            isButton: true,
            title: '查看手机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'pcAdd',
          name: 'pcAdd',
          component: () => import('@/views/allocation/management/pc/pcAdd'),
          meta: {
            isButton: true,
            title: '添加PC',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'pcEdit',
          name: 'pcEdit',
          component: () => import('@/views/allocation/management/pc/pcEdit'),
          meta: {
            isButton: true,
            title: '修改PC',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'pcView',
          name: 'pcView',
          component: () => import('@/views/allocation/management/pc/view'),
          meta: {
            isButton: true,
            title: '查看PC',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'serverAdd',
          name: 'serverAdd',
          component: () => import('@/views/allocation/management/server/serverAdd'),
          meta: {
            isButton: true,
            title: '添加服务器',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'serverEdit',
          name: 'serverEdit',
          component: () => import('@/views/allocation/management/server/serverEdit'),
          meta: {
            isButton: true,
            title: '修改服务器',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'serverView',
          name: 'serverView',
          component: () => import('@/views/allocation/management/server/view'),
          meta: {
            isButton: true,
            title: '查看服务器',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 网络设备管理=添加
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'networkmanageAdd',
          name: 'networkmanageAdd',
          component: () => import('@/views/allocation/management/networkmanage/Add'),
          meta: {
            isButton: true,
            title: '添加网络设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 网络设备管理=查看
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'networkmanageView',
          name: 'networkmanageView',
          component: () => import('@/views/allocation/management/networkmanage/View'),
          meta: {
            isButton: true,
            title: '查看网络设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 网络设备管理=修改
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'networkmanageEdit',
          name: 'networkmanageEdit',
          component: () => import('@/views/allocation/management/networkmanage/Edit'),
          meta: {
            isButton: true,
            title: '修改网络设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'SuperManagerAdd',
          name: 'SuperManagerAdd',
          component: () => import('@/views/allocation/management/superManager/SuperManagerAdd'),
          meta: {
            isButton: true,
            title: '添加超管理器',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'SuperManagerEdit',
          name: 'SuperManagerEdit',
          component: () => import('@/views/allocation/management/superManager/SuperManagerEdit'),
          meta: {
            isButton: true,
            title: '修改超管理器',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'SuperManagerView',
          name: 'SuperManagerView',
          component: () => import('@/views/allocation/management/superManager/SuperManagerView'),
          meta: {
            isButton: true,
            title: '查看超管理器',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'powerAdd',
          name: 'powerAdd',
          component: () => import('@/views/allocation/management/power/powerAdd'),
          meta: {
            isButton: true,
            title: '添加电源',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'powerEdit',
          name: 'powerEdit',
          component: () => import('@/views/allocation/management/power/powerEdit'),
          meta: {
            isButton: true,
            title: '修改电源',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'powerView',
          name: 'powerView',
          component: () => import('@/views/allocation/management/power/powerView'),
          meta: {
            isButton: true,
            title: '查看电源',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'tapeAdd',
          name: 'tapeAdd',
          component: () => import('@/views/allocation/management/tape/tapeAdd'),
          meta: {
            isButton: true,
            title: '添加磁带库',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'tapeEdit',
          name: 'tapeEdit',
          component: () => import('@/views/allocation/management/tape/tapeEdit'),
          meta: {
            isButton: true,
            title: '修改磁带库',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'tapeView',
          name: 'tapeView',
          component: () => import('@/views/allocation/management/tape/tapeView'),
          meta: {
            isButton: true,
            title: '查看磁带库',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 其他管理=添加
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'otherAdd',
          name: 'otherAdd',
          component: () => import('@/views/allocation/management/other/Add'),
          meta: {
            isButton: true,
            title: '添加其他',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    //其他管理=修改
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'otherEdit',
          name: 'otherEdit',
          component: () => import('@/views/allocation/management/other/Edit'),
          meta: {
            isButton: true,
            title: '修改其他',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    //其他管理=查看
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'otherView',
          name: 'otherView',
          component: () => import('@/views/allocation/management/other/View'),
          meta: {
            isButton: true,
            title: '查看其他',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    //pad管理
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'padAdd',
          name: 'padAdd',
          component: () => import('@/views/allocation/management/padmana/padAdd'),
          meta: {
            isButton: true,
            title: '添加平板电脑',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'padEdit',
          name: 'padEdit',
          component: () => import('@/views/allocation/management/padmana/padEdit'),
          meta: {
            isButton: true,
            title: '修改平板电脑',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'padView',
          name: 'padView',
          component: () => import('@/views/allocation/management/padmana/padView'),
          meta: {
            isButton: true,
            title: '查看平板电脑',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 虚拟机管理
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'VMAdd',
          name: 'VMAdd',
          component: () => import('@/views/allocation/management/VM/VMAdd'),
          meta: {
            isButton: true,
            title: '添加虚拟机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'VMEdit',
          name: 'VMEdit',
          component: () => import('@/views/allocation/management/VM/VMEdit'),
          meta: {
            isButton: true,
            title: '修改虚拟机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'VMView',
          name: 'VMView',
          component: () => import('@/views/allocation/management/VM/VMView'),
          meta: {
            isButton: true,
            title: '查看虚拟机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 外部设备管理
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'peripheralAdd',
          name: 'peripheralAdd',
          component: () => import('@/views/allocation/management/peripheral/peripheralAdd'),
          meta: {
            isButton: true,
            title: '添加外部设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'peripheralEdit',
          name: 'peripheralEdit',
          component: () => import('@/views/allocation/management/peripheral/peripheralEdit'),
          meta: {
            isButton: true,
            title: '修改外部设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'peripheralView',
          name: 'peripheralView',
          component: () => import('@/views/allocation/management/peripheral/peripheralView'),
          meta: {
            isButton: true,
            title: '查看外部设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    // 打印机管理
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'printerAdd',
          name: 'printerAdd',
          component: () => import('@/views/allocation/management/printer/printerAdd'),
          meta: {
            isButton: true,
            title: '添加打印机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'printerEdit',
          name: 'printerEdit',
          component: () => import('@/views/allocation/management/printer/printerEdit'),
          meta: {
            isButton: true,
            title: '修改打印机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'printerView',
          name: 'printerView',
          component: () => import('@/views/allocation/management/printer/printerView'),
          meta: {
            isButton: true,
            title: '查看打印机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    //farm处理机管理
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'farmAdd',
          name: 'farmAdd',
          component: () => import('@/views/allocation/management/farm/farmAdd'),
          meta: {
            isButton: true,
            title: '添加farm处理机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'farmView',
          name: 'farmView',
          component: () => import('@/views/allocation/management/farm/farmView'),
          meta: {
            isButton: true,
            title: '查看farm处理机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'farmEdit',
          name: 'farmEdit',
          component: () => import('@/views/allocation/management/farm/farmEdit'),
          meta: {
            isButton: true,
            title: '修改farm处理机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    //交换机管理
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'switchAdd',
          name: 'switchAdd',
          component: () => import('@/views/allocation/management/switch/switchAdd'),
          meta: {
            isButton: true,
            title: '添加交换机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'switchEdit',
          name: 'switchEdit',
          component: () => import('@/views/allocation/management/switch/switchEdit'),
          meta: {
            isButton: true,
            title: '修改交换机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'switchView',
          name: 'switchView',
          component: () => import('@/views/allocation/management/switch/switchView'),
          meta: {
            isButton: true,
            title: '查看交换机',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'operationFlowAdd',
          name: 'operationFlowAdd',
          component: () => import('@/views/allocation/management/operationFlow/operation'),
          meta: {
            isButton: true,
            title: '添加业务流程',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'operationFlowEdit',
          name: 'operationFlowEdit',
          component: () => import('@/views/allocation/management/operationFlow/edit'),
          meta: {
            isButton: true,
            title: '修改业务流程',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'operationFlowView',
          name: 'operationFlowView',
          component: () => import('@/views/allocation/management/operationFlow/view'),
          meta: {
            isButton: true,
            title: '查看业务流程',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'databaseModeAdd',
          name: 'databaseModeAdd',
          component: () => import('@/views/allocation/management/databaseMode/add'),
          meta: {
            isButton: true,
            title: '添加数据库模式',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'databaseModeEdit',
          name: 'databaseModeEdit',
          component: () => import('@/views/allocation/management/databaseMode/edit'),
          meta: {
            isButton: true,
            title: '修改数据库模式',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'databaseModeView',
          name: 'databaseModeView',
          component: () => import('@/views/allocation/management/databaseMode/view'),
          meta: {
            isButton: true,
            title: '查看数据库模式',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'networkAdd',
          name: 'networkAdd',
          component: () => import('@/views/allocation/management/networkApplication/add'),
          meta: {
            isButton: true,
            title: '添加网络应用',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'networkEdit',
          name: 'networkEdit',
          component: () => import('@/views/allocation/management/networkApplication/edit'),
          meta: {
            isButton: true,
            title: '修改网络应用',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'networkView',
          name: 'networkView',
          component: () => import('@/views/allocation/management/networkApplication/view'),
          meta: {
            isButton: true,
            title: '查看网络应用',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'softwareAdd',
          name: 'softwareAdd',
          component: () => import('@/views/allocation/management/software/softwareAdd'),
          meta: {
            isButton: true,
            title: '添加软件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'softwareEdit',
          name: 'softwareEdit',
          component: () => import('@/views/allocation/management/software/softwareEdit'),
          meta: {
            isButton: true,
            title: '修改软件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'softwareView',
          name: 'softwareView',
          component: () => import('@/views/allocation/management/software/softwareView'),
          meta: {
            isButton: true,
            title: '查看软件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'applyAdd',
          name: 'applyAdd',
          component: () => import('@/views/allocation/management/apply/applyAdd'),
          meta: {
            isButton: true,
            title: '添加应用',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'applyEdit',
          name: 'applyEdit',
          component: () => import('@/views/allocation/management/apply/applyEdit'),
          meta: {
            isButton: true,
            title: '修改应用',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'applyView',
          name: 'applyView',
          component: () => import('@/views/allocation/management/apply/applyView'),
          meta: {
            isButton: true,
            title: '查看应用',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'pduAdd',
          name: 'pduAdd',
          component: () => import('@/views/allocation/management/pdu/pduAdd'),
          meta: {
            isButton: true,
            title: '添加PDU',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'pduEdit',
          name: 'pduEdit',
          component: () => import('@/views/allocation/management/pdu/pduEdit'),
          meta: {
            isButton: true,
            title: '修改PDU',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'pduView',
          name: 'pduView',
          component: () => import('@/views/allocation/management/pdu/pduView'),
          meta: {
            isButton: true,
            title: '查看PDU',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'mobilePhoneAdd',
          name: 'mobilePhoneAdd',
          component: () => import('@/views/allocation/management/mobilePhone/mobilePhoneAdd'),
          meta: {
            isButton: true,
            title: '添加电话',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'mobilePhoneEdit',
          name: 'mobilePhoneEdit',
          component: () => import('@/views/allocation/management/mobilePhone/mobilePhoneEdit'),
          meta: {
            isButton: true,
            title: '修改电话',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'mobilePhoneView',
          name: 'mobilePhoneView',
          component: () => import('@/views/allocation/management/mobilePhone/mobilePhoneView'),
          meta: {
            isButton: true,
            title: '查看电话',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'storageDeviceAdd',
          name: 'storageDeviceAdd',
          component: () => import('@/views/allocation/management/storageDevice/storageDeviceAdd'),
          meta: {
            isButton: true,
            title: '添加存储设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'storageDeviceEdit',
          name: 'storageDeviceEdit',
          component: () => import('@/views/allocation/management/storageDevice/storageDeviceEdit'),
          meta: {
            isButton: true,
            title: '修改存储设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'storageDeviceView',
          name: 'storageDeviceView',
          component: () => import('@/views/allocation/management/storageDevice/storageDeviceView'),
          meta: {
            isButton: true,
            title: '查看存储设备',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'customManageAdd',
          name: 'customManageAdd',
          component: () => import('@/views/allocation/management/custom/customManageAdd'),
          meta: {
            isButton: true,
            title: '添加自定义',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'customManageEdit',
          name: 'customManageEdit',
          component: () => import('@/views/allocation/management/custom/customManageEdit'),
          meta: {
            isButton: true,
            title: '修改自定义',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'customManageView',
          name: 'customManageView',
          component: () => import('@/views/allocation/management/custom/customManageView'),
          meta: {
            isButton: true,
            title: '查看自定义',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'fileManageAdd',
          name: 'fileManageAdd',
          component: () => import('@/views/allocation/management/file/fileManageAdd'),
          meta: {
            isButton: true,
            title: '添加文件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'fileManageEdit',
          name: 'fileManageEdit',
          component: () => import('@/views/allocation/management/file/fileManageEdit'),
          meta: {
            isButton: true,
            title: '修改文件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'fileManageView',
          name: 'fileManageView',
          component: () => import('@/views/allocation/management/file/fileManageView'),
          meta: {
            isButton: true,
            title: '查看文件',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'capitalIndex',
          name: 'capitalIndex',
          component: () => import('@/views/allocation/management/capitalIndex'),
          meta: {
            isButton: true,
            title: '资产盘点管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'capitaDeal',
          name: 'capitaDeal',
          component: () => import('@/views/allocation/management/capitaDeal'),
          meta: {
            isButton: true,
            title: '资产盘点处理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },

    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'capitaView',
          name: 'capitaView',
          component: () => import('@/views/allocation/management/capitaView'),
          meta: {
            isButton: true,
            title: '查看资产盘点',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'periodIndex',
          name: 'periodIndex',
          component: () => import('@/views/allocation/management/periodIndex'),
          meta: {
            isButton: true,
            title: '盘点期管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/pzgl',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'capitaConfirm',
          name: 'capitaConfirm',
          component: () => import('@/views/allocation/management/capitaConfirm'),
          meta: {
            isButton: true,
            title: '资产盘点确认',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      name: 'Book',
      meta: { title: '知识库', icon: 'book', permissions: 'book' },
      children: [
        {
          path: 'myKnowledge',
          name: 'myKnowledge',
          component: () => import('@/views/knowledge/myKnowledge/index'),
          meta: { title: '我的知识', permissions: 'book_my' },
        },
        {
          path: 'knowledgeAudit',
          name: 'knowledgeAudit',
          component: () => import('@/views/knowledge/examine/index'),
          meta: { title: '知识审核', permissions: 'book_audit' },
        },
        {
          path: 'internalKnowledge',
          name: 'internalKnowledge',
          component: () => import('@/views/knowledge/inside/index'),
          meta: { title: '内部知识', permissions: 'book_inner' },
        },
        {
          path: 'knowledgeManage',
          name: 'knowledgeManage',
          component: () => import('@/views/knowledge/manage/index'),
          meta: { title: '知识管理', permissions: 'book_manage' },
        },
        {
          path: 'knowledgeSearch',
          name: 'knowledgeSearch',
          component: () => import('@/views/knowledge/search/index'),
          meta: { title: '知识搜索', permissions: 'book_search' },
        },
        {
          path: 'knowledgeReport',
          name: 'knowledgeReport',
          component: () => import('@/views/knowledge/statistics/index'),
          meta: { title: '知识统计', permissions: 'book_stastics' },
        },
      ],
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'KnowStatistics',
          name: 'KnowStatistics',
          component: () => import('@/views/knowledge/statistics/view'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'myKnowledgeAdd',
          name: 'createKnow',
          component: () => import('@/views/knowledge/myKnowledge/myKnowledgeAdd'),
          meta: {
            isButton: true,
            title: '我的知识添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'myKnowledgeEdit',
          name: 'myKnowledgeEdit',
          component: () => import('@/views/knowledge/myKnowledge/myKnowledgeEdit'),
          meta: {
            isButton: true,
            title: '我的知识修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'myKnowledgeView',
          name: 'myKnowledgeView',
          component: () => import('@/views/knowledge/myKnowledge/myKnowledgeView'),
          meta: {
            isButton: true,
            title: '文章查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'examineAdd',
          name: 'examineAdd',
          component: () => import('@/views/knowledge/examine/Add'),
          meta: {
            isButton: true,
            title: '知识管理添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'examineCheck',
          name: 'examineCheck',
          component: () => import('@/views/knowledge/examine/Check'),
          meta: {
            isButton: true,
            title: '知识审核审核',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'examineEdit',
          name: 'examineEdit',
          component: () => import('@/views/knowledge/examine/Edit'),
          meta: {
            isButton: true,
            title: '知识管理修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },

    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'manageGrade',
          name: 'manageGrade',
          component: () => import('@/views/knowledge/manage/Grade'),
          meta: {
            isButton: true,
            title: '知识管理打分',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'manageAdd',
          name: 'manageAdd',
          component: () => import('@/views/knowledge/manage/Add'),
          meta: {
            isButton: true,
            title: '知识管理添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'manageCheck',
          name: 'manageCheck',
          component: () => import('@/views/knowledge/manage/Check'),
          meta: {
            isButton: true,
            title: '知识管理审核',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/book',
      component: Layout,
      redirect: 'noRedirect',
      children: [
        {
          isButton: true,
          path: 'manageEdit',
          name: 'manageEdit',
          component: () => import('@/views/knowledge/manage/Edit'),
          meta: {
            isButton: true,
            title: '知识管理修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '/set',
      component: Layout,
      redirect: 'set',
      meta: { title: '设置', permissions: 'set' },
      children: [
        {
          path: 'set',
          name: 'set',
          component: () => import('@/views/systemset/index'),
          meta: {
            title: '设置',
            icon: 'cog',
            affix: false,
            permissions: 'set_config'
          },
        }

      ],
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'usermanagement',
          name: 'usermanagement',
          component: () => import('@/views/systemset/usermanagement/users/index'),
          meta: {
            isButton: true,
            title: '用户管理',
            affix: false,
            permissions: 'set_user'
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'usermanagementedit',
          name: 'usermanagementedit',
          component: () => import('@/views/systemset/usermanagement/users/edit'),
          meta: {
            isButton: true,
            title: '用户修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'usermanagementadd',
          name: 'usermanagementadd',
          component: () => import('@/views/systemset/usermanagement/users/add'),
          meta: {
            isButton: true,
            title: '用户添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'rolesmanagement',
          name: 'rolesmanagement',
          component: () => import('@/views/systemset/usermanagement/userroles/index'),
          meta: {
            isButton: true,
            title: '角色管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'rolesusermana',
          name: 'rolesusermana',
          component: () => import('@/views/systemset/usermanagement/userroles/usermana'),
          meta: {
            isButton: true,
            title: '管理用户',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'departmentmana',
          name: 'departmentmana',
          component: () => import('@/views/systemset/usermanagement/department/index'),
          meta: {
            isButton: true,
            title: '部门管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'companymana',
          name: 'companymana',
          component: () => import('@/views/systemset/usermanagement/company/index'),
          meta: {
            isButton: true,
            title: '公司管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'peoplemap',
          name: 'peoplemap',
          component: () => import('@/views/systemset/usermanagement/peoplemap/index'),
          meta: {
            isButton: true,
            title: '人力地图',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'domainmana',
          name: 'domainmana',
          component: () => import('@/views/systemset/usermanagement/domain/index'),
          meta: {
            isButton: true,
            title: '域用户管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'samlmana',
          name: 'samlmana',
          component: () => import('@/views/systemset/usermanagement/domain/saml'),
          meta: {
            isButton: true,
            title: 'SAML验证登录',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'flybookmana',
          name: 'flybookmana',
          component: () => import('@/views/systemset/usermanagement/flybook/index'),
          meta: {
            isButton: true,
            title: '飞书同步管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'assetsmanagement',
          name: 'assetsmanagement',
          component: () => import('@/views/systemset/configsets/assetmanagement/index'),
          meta: {
            isButton: true,
            title: '资产状态管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'purchasemanagement',
          name: 'purchasemanagement',
          component: () => import('@/views/systemset/configsets/purchasemanagement/index'),
          meta: {
            isButton: true,
            title: '购置公司管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'usescopemanagement',
          name: 'usescopemanagement',
          component: () => import('@/views/systemset/configsets/usescopemanagement/index'),
          meta: {
            isButton: true,
            title: '使用范围管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'brandmanagement',
          name: 'brandmanagement',
          component: () => import('@/views/systemset/configsets/brandmanagement/index'),
          meta: {
            isButton: true,
            title: '品牌管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'osmanagement',
          name: 'osmanagement',
          component: () => import('@/views/systemset/configsets/osmanagement/index'),
          meta: {
            isButton: true,
            title: '操作系统管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'oslicencemanagement',
          name: 'oslicencemanagement',
          component: () => import('@/views/systemset/configsets/oslicencemanagement/index'),
          meta: {
            isButton: true,
            title: '操作系统许可证管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'softwaremanagement',
          name: 'softwaremanagement',
          component: () => import('@/views/systemset/configsets/softwaremanagement/index'),
          meta: {
            isButton: true,
            title: '软件许可证管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'opentypemanagement',
          name: 'opentypemanagement',
          component: () => import('@/views/systemset/configsets/opentypemanagement/index'),
          meta: {
            isButton: true,
            title: '启用类型管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'backuptypemanagement',
          name: 'backuptypemanagement',
          component: () => import('@/views/systemset/configsets/backuptypemanagement/index'),
          meta: {
            isButton: true,
            title: '备份方式管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'contractpeoplemanagement',
          name: 'contractpeoplemanagement',
          component: () => import('@/views/systemset/configsets/contractpeoplemanagement/index'),
          meta: {
            isButton: true,
            title: '合同联系人管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'contractcustommanagement',
          name: 'contractcustommanagement',
          component: () => import('@/views/systemset/configsets/contractcustommanagement/index'),
          meta: {
            isButton: true,
            title: '客户合同管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'contractshopmanagement',
          name: 'contractshopmanagement',
          component: () => import('@/views/systemset/configsets/contractshopmanagement/index'),
          meta: {
            isButton: true,
            title: '供应商合同管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'setstypemanagement',
          name: 'setstypemanagement',
          component: () => import('@/views/systemset/configsets/setstypemanagement/index'),
          meta: {
            isButton: true,
            title: '配置请求类型管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'cigroupmanagement',
          name: 'cigroupmanagement',
          component: () => import('@/views/systemset/configsets/cigroupmanagement/index'),
          meta: {
            isButton: true,
            title: 'CI分组管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'configtypemanagement',
          name: 'configtypemanagement',
          component: () => import('@/views/systemset/configsets/configtypemanagement/index'),
          meta: {
            isButton: true,
            title: '配置项类型管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'childrenmanagement',
          name: 'childrenmanagement',
          component: () => import('@/views/systemset/configsets/configtypemanagement/childrenmanagement/index'),
          meta: {
            isButton: true,
            title: '配置子类管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'statemanagement',
          name: 'statemanagement',
          component: () => import('@/views/systemset/configsets/configtypemanagement/statemanagement/index'),
          meta: {
            isButton: true,
            title: '配置项状态管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'configparaset',
          name: 'configparaset',
          component: () => import('@/views/systemset/configsets/configparaset/index'),
          meta: {
            isButton: true,
            title: '配置管理参数设置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'booknoticeconfig',
          name: 'booknoticeconfig',
          component: () => import('@/views/systemset/bookconfig/booknoticeconfig/index'),
          meta: {
            isButton: true,
            title: '知识通知配置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'bookclassmanagement',
          name: 'bookclassmanagement',
          component: () => import('@/views/systemset/bookconfig/bookclassmanagement/index'),
          meta: {
            isButton: true,
            title: '知识类别管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'bookviewrange',
          name: 'bookviewrange',
          component: () => import('@/views/systemset/bookconfig/bookviewrange/index'),
          meta: {
            isButton: true,
            title: '知识查看范围管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'booksets',
          name: 'booksets',
          component: () => import('@/views/systemset/bookconfig/booksets/index'),
          meta: {
            isButton: true,
            title: '知识库配置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailaccountmanagement',
          name: 'emailaccountmanagement',
          component: () => import('@/views/systemset/emailmanagement/emailaccountmanagement/index'),
          meta: {
            isButton: true,
            title: '邮件账户管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailaccountmanagementadd',
          name: 'emailaccountmanagementadd',
          component: () => import('@/views/systemset/emailmanagement/emailaccountmanagement/add'),
          meta: {
            isButton: true,
            title: '添加邮件账户',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailaccountmanagementedit',
          name: 'emailaccountmanagementedit',
          component: () => import('@/views/systemset/emailmanagement/emailaccountmanagement/edit'),
          meta: {
            isButton: true,
            title: '修改邮件账户',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailaccountmanagementrule',
          name: 'emailaccountmanagementrule',
          component: () => import('@/views/systemset/emailmanagement/emailaccountmanagement/rulesindex'),
          meta: {
            isButton: true,
            title: '邮件收件规则',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailrulesadd',
          name: 'emailrulesadd',
          component: () => import('@/views/systemset/emailmanagement/emailaccountmanagement/rulesadd'),
          meta: {
            isButton: true,
            title: '邮件收件规则添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailrulesedit',
          name: 'emailrulesedit',
          component: () => import('@/views/systemset/emailmanagement/emailaccountmanagement/rulesedit'),
          meta: {
            isButton: true,
            title: '邮件收件规则修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'emailnametemplate',
          name: 'emailnametemplate',
          component: () => import('@/views/systemset/emailmanagement/emailnametemplate/index'),
          meta: {
            isButton: true,
            title: '邮件签名模板',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'weixinsets',
          name: 'weixinsets',
          component: () => import('@/views/systemset/mobileconfigs/weixinsets/index'),
          meta: {
            isButton: true,
            title: '微信参数设置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'dingdingsets',
          name: 'dingdingsets',
          component: () => import('@/views/systemset/mobileconfigs/dingdingsets/index'),
          meta: {
            isButton: true,
            title: '钉钉配置管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'flybooksets',
          name: 'flybooksets',
          component: () => import('@/views/systemset/mobileconfigs/flybooksets/index'),
          meta: {
            isButton: true,
            title: '飞书配置管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'sysworkdays',
          name: 'sysworkdays',
          component: () => import('@/views/systemset/systemsets/workdaymanagement/index'),
          meta: {
            isButton: true,
            title: '工作日管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'sysworkdaysCalendar',
          name: 'sysworkdaysCalendar',
          component: () => import('@/views/systemset/systemsets/workdaymanagement/calendar'),
          meta: {
            isButton: true,
            title: '设置日历',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'noticemanagement',
          name: 'noticemanagement',
          component: () => import('@/views/systemset/systemsets/noticemanagement/index'),
          meta: {
            isButton: true,
            title: '公告管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'homeppmanagement',
          name: 'homeppmanagement',
          component: () => import('@/views/systemset/systemsets/homepicturemanagement/index'),
          meta: {
            isButton: true,
            title: '首页背景图管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'printmanagement',
          name: 'printmanagement',
          component: () => import('@/views/systemset/systemsets/printmanagement/index'),
          meta: {
            isButton: true,
            title: '打印模板管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'sqltablemanagement',
          name: 'sqltablemanagement',
          component: () => import('@/views/systemset/systemsets/sqltablemanagement/index'),
          meta: {
            isButton: true,
            title: 'SQL报表管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'extendformmanagement',
          name: 'extendformmanagement',
          component: () => import('@/views/systemset/systemsets/extendformmanagement/index'),
          meta: {
            isButton: true,
            title: '扩展表单管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'extendformsubmanagement',
          name: 'extendformsubmanagement',
          component: () => import('@/views/systemset/systemsets/extendformmanagement/subindex'),
          meta: {
            isButton: true,
            title: '字段管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'filesmanagement',
          name: 'filesmanagement',
          component: () => import('@/views/systemset/systemsets/filesmanagement/index'),
          meta: {
            isButton: true,
            title: '文件管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'filesviewmanagement',
          name: 'filesviewmanagement',
          component: () => import('@/views/systemset/systemsets/filesmanagement/components/View'),
          meta: {
            isButton: true,
            title: '查看',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'sysreg',
          name: 'sysreg',
          component: () => import('@/views/systemset/systemsets/sysreg/index'),
          meta: {
            isButton: true,
            title: '系统注册',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'sysparaset',
          name: 'sysparaset',
          component: () => import('@/views/systemset/systemsets/sysparaset/index'),
          meta: {
            isButton: true,
            title: '系统参数配置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'publishtype',
          name: 'publishtype',
          component: () => import('@/views/systemset/publishconfig/publishtypemanagement/index'),
          meta: {
            isButton: true,
            title: '发布类别管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'publishnotice',
          name: 'publishnotice',
          component: () => import('@/views/systemset/publishconfig/publishnoticemanagement/index'),
          meta: {
            isButton: true,
            title: '发布通知管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'changetype',
          name: 'changetype',
          component: () => import('@/views/systemset/changeconfig/changetypemanagement/index'),
          meta: {
            isButton: true,
            title: '变更类别管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'changetypeadd',
          name: 'changetypeadd',
          component: () => import('@/views/systemset/changeconfig/changetypemanagement/add'),
          meta: {
            isButton: true,
            title: '变更类别添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'changetypeedit',
          name: 'changetypeadd',
          component: () => import('@/views/systemset/changeconfig/changetypemanagement/add'),
          meta: {
            isButton: true,
            title: '变更类别修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'changenotice',
          name: 'changenotice',
          component: () => import('@/views/systemset/changeconfig/changenoticemanagement/index'),
          meta: {
            isButton: true,
            title: '变更通知配置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'changesource',
          name: 'changesource',
          component: () => import('@/views/systemset/changeconfig/changesourcemanagement/index'),
          meta: {
            isButton: true,
            title: '变更来源管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'faqsource',
          name: 'faqsource',
          component: () => import('@/views/systemset/faqconfig/faqsourcemanagement/index'),
          meta: {
            isButton: true,
            title: '问题来源管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'faqtype',
          name: 'faqtype',
          component: () => import('@/views/systemset/faqconfig/faqtypemanagement/index'),
          meta: {
            isButton: true,
            title: '问题类别管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'faqtypeadd',
          name: 'faqtypeadd',
          component: () => import('@/views/systemset/faqconfig/faqtypemanagement/add'),
          meta: {
            isButton: true,
            title: '问题类别添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'faqtypeedit',
          name: 'faqtypeedit',
          component: () => import('@/views/systemset/faqconfig/faqtypemanagement/edit'),
          meta: {
            isButton: true,
            title: '问题类别修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'faqnotice',
          name: 'faqnotice',
          component: () => import('@/views/systemset/faqconfig/faqnoticemanagement/index'),
          meta: {
            isButton: true,
            title: '问题通知管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'affect',
          name: 'affect',
          component: () => import('@/views/systemset/agreement/affect/index'),
          meta: {
            isButton: true,
            title: '事件影响度管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'urgency',
          name: 'urgency',
          component: () => import('@/views/systemset/agreement/urgency/index'),
          meta: {
            isButton: true,
            title: '事件紧急度管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'priority',
          name: 'priority',
          component: () => import('@/views/systemset/agreement/priority/index'),
          meta: {
            isButton: true,
            title: '事件优先级管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'relation',
          name: 'relation',
          component: () => import('@/views/systemset/agreement/relation/index'),
          meta: {
            isButton: true,
            title: '事件影响度紧急度关系管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'SLA',
          name: 'SLA',
          component: () => import('@/views/systemset/agreement/SLA/index'),
          meta: {
            isButton: true,
            title: '事件SLA管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'question',
          name: 'question',
          component: () => import('@/views/systemset/agreement/question/index'),
          meta: {
            isButton: true,
            title: '问题优先级管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'questionSLA',
          name: 'questionSLA',
          component: () => import('@/views/systemset/agreement/questionSLA/index'),
          meta: {
            isButton: true,
            title: '问题SLA管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'group',
          name: 'group',
          component: () => import('@/views/systemset/configuration/group/index'),
          meta: {
            isButton: true,
            title: '服务群组管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'groupAdd',
          name: 'groupAdd',
          component: () => import('@/views/systemset/configuration/group/groupAdd'),
          meta: {
            isButton: true,
            title: '服务群组添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'groupEdit',
          name: 'groupEdit',
          component: () => import('@/views/systemset/configuration/group/groupEdit'),
          meta: {
            isButton: true,
            title: '服务群组修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'selfService',
          name: 'selfService',
          component: () => import('@/views/systemset/configuration/selfService/index'),
          meta: {
            isButton: true,
            title: '自助服务类别管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'serviceAdd',
          name: 'serviceAdd',
          component: () => import('@/views/systemset/configuration/selfService/serviceAdd'),
          meta: {
            isButton: true,
            title: '自助服务类别添加/修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'origin',
          name: 'origin',
          component: () => import('@/views/systemset/configuration/origin/index'),
          meta: {
            isButton: true,
            title: '来源管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'request',
          name: 'request',
          component: () => import('@/views/systemset/configuration/request/index'),
          meta: {
            isButton: true,
            title: '请求类型管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'workOrder',
          name: 'workOrder',
          component: () => import('@/views/systemset/configuration/workOrder/index'),
          meta: {
            isButton: true,
            title: '工单类别管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'workAdd',
          name: 'workAdd',
          component: () => import('@/views/systemset/configuration/workOrder/Add'),
          meta: {
            isButton: true,
            title: '工单类别添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'workEdit',
          name: 'workEdit',
          component: () => import('@/views/systemset/configuration/workOrder/Edit'),
          meta: {
            isButton: true,
            title: '工单类别修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'solution',
          name: 'solution',
          component: () => import('@/views/systemset/configuration/solution/index'),
          meta: {
            isButton: true,
            title: '解决方式管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'programme',
          name: 'programme',
          component: () => import('@/views/systemset/configuration/programme/index'),
          meta: {
            isButton: true,
            title: '方案分类管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'workTemplate',
          name: 'workTemplate',
          component: () => import('@/views/systemset/configuration/workTemplate/index'),
          meta: {
            isButton: true,
            title: '工单模板管理',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'templateAdd',
          name: 'templateAdd',
          component: () => import('@/views/systemset/configuration/workTemplate/templateAdd'),
          meta: {
            isButton: true,
            title: '工单模板添加/修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'templateView',
          name: 'templateView',
          component: () => import('@/views/systemset/configuration/workTemplate/templateView'),
          meta: {
            isButton: true,
            title: '查看工单模板',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'scheduledTask',
          name: 'scheduledTask',
          component: () => import('@/views/systemset/configuration/workTemplate/scheduledTask'),
          meta: {
            isButton: true,
            title: '定时任务',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'taskAdd',
          name: 'taskAdd',
          component: () => import('@/views/systemset/configuration/workTemplate/taskAdd'),
          meta: {
            isButton: true,
            title: '定时任务添加',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'taskEdit',
          name: 'taskEdit',
          component: () => import('@/views/systemset/configuration/workTemplate/taskEdit'),
          meta: {
            isButton: true,
            title: '定时任务修改',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'notice',
          name: 'notice',
          component: () => import('@/views/systemset/configuration/notice/index'),
          meta: {
            isButton: true,
            title: '工单通知配置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      isButton: true,
      path: '/set',
      component: Layout,
      redirect: '/set',
      children: [
        {
          isButton: true,
          path: 'event',
          name: 'event',
          component: () => import('@/views/systemset/configuration/event/index'),
          meta: {
            isButton: true,
            title: '事件参数设置',
            affix: false,
          },
        },
      ],
      hidden: true,
    },
    {
      path: '*',
      redirect: '/404',
      hidden: true,
    },

  ]

const router = new VueRouter({
  base: publicPath,
  mode: routerMode,
  scrollBehavior: () => ({
    y: 0,
  }),
  routes: constantRoutes,
})
//注释的地方是允许路由重复点击，如果你觉得框架路由跳转规范太过严格可选择放开
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject)
  return originalPush.call(this, location).catch((err) => err)
}

export function resetRouter() {
  router.matcher = new VueRouter({
    base: publicPath,
    mode: routerMode,
    scrollBehavior: () => ({
      y: 0,
    }),
    routes: constantRoutes,
  }).matcher
}

export default router
