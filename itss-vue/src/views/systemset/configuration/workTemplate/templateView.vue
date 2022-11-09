<!--<template>-->
<!--<div class="index-container pb80">-->
<!--<el-descriptions title="模板信息" :column='5' class='des-new' size='medium'>-->
<!--<el-descriptions-item label="模板编号">kooriookami</el-descriptions-item>-->
<!--<el-descriptions-item label="模板名称">呜呜呜呜呜呜</el-descriptions-item>-->
<!--<el-descriptions-item label="状态">使用</el-descriptions-item>-->
<!--<el-descriptions-item label="所属服务群组">2</el-descriptions-item>-->
<!--<el-descriptions-item label="公共模板">否</el-descriptions-item>-->
<!--<el-descriptions-item label="描述" :span='10'>摩奥</el-descriptions-item>-->
<!--<el-descriptions-item label="使用次数">0</el-descriptions-item>-->
<!--<el-descriptions-item label="创建人">王文文</el-descriptions-item>-->
<!--<el-descriptions-item label="创建时间">2021-11-27 09:54:54</el-descriptions-item>-->
<!--<el-descriptions-item label="修改人">王文文</el-descriptions-item>-->
<!--<el-descriptions-item label="修改时间">2021-11-27 09:54:54</el-descriptions-item>-->
<!--</el-descriptions>-->
<!--<el-descriptions title="工单信息" :column='5' class='des-new' size='medium'>-->
<!--<el-descriptions-item label="用户">王文</el-descriptions-item>-->
<!--<el-descriptions-item label="来源">现场</el-descriptions-item>-->
<!--<el-descriptions-item label="请求类型">事件</el-descriptions-item>-->
<!--<el-descriptions-item label="工单类别">2</el-descriptions-item>-->
<!--<el-descriptions-item label="紧急度">低</el-descriptions-item>-->
<!--<el-descriptions-item label="影响度">低</el-descriptions-item>-->
<!--<el-descriptions-item label="优先级">高</el-descriptions-item>-->
<!--<el-descriptions-item label="服务群组">鼎驰</el-descriptions-item>-->
<!--<el-descriptions-item label="处理人">王文文</el-descriptions-item>-->
<!--</el-descriptions>-->
<!--<el-descriptions :column='5' class='des-new' size='medium'>-->
<!--<el-descriptions-item label="工单标题" :span='10'>工单标题</el-descriptions-item>-->
<!--</el-descriptions>-->
<!--<el-descriptions :column='5' class='des-new' size='medium'>-->
<!--<el-descriptions-item label="工单描述" :span='10'>工单描述</el-descriptions-item>-->
<!--<el-descriptions-item label="开单人">王文文</el-descriptions-item>-->
<!--<el-descriptions-item label="解决方式">解决方式</el-descriptions-item>-->
<!--<el-descriptions-item label="方案分类">方案分类</el-descriptions-item>-->
<!--</el-descriptions>-->
<!--<el-descriptions :column='5' class='des-new' size='medium'>-->
<!--<el-descriptions-item label="解决方案" :span='10'>解决方案</el-descriptions-item>-->
<!--</el-descriptions>-->
<!--<div class="bottom">-->
<!--<el-button size="medium" @click="closeall">关闭</el-button>-->
<!--</div>-->
<!--</div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--name: 'templateview',-->
<!--data() {-->
<!--return {}-->
<!--},-->
<!--created() {-->
<!---->
<!--},-->
<!--methods: {-->
<!--closeall() {-->
<!--// 调用全局挂载的方法-->
<!--this.$store.dispatch('tabsBar/delRoute', this.$route)-->
<!--// 返回上一步路由-->
<!--this.$router.go(-1)-->
<!--},-->
<!--},-->
<!--}-->
<!--</script>-->
<!--<style lang="scss" scoped>-->
<!--.index-container {-->
<!--.hiddenmore {-->
<!--display: inline;-->

<!--::v-deep {-->
<!--.el-input {-->
<!--width: 130px;-->
<!--}-->
<!--}-->
<!--}-->
<!--}-->

<!--::v-deep {-->
<!--.des-new {-->
<!--.el-descriptions__title{-->
<!--font-size: 14px;-->
<!--font-weight: bold;-->
<!--border-left: 4px solid $base-color-default;-->
<!--padding-left: 10px;-->
<!--color: $base-color-default;-->
<!--}-->
<!--.el-descriptions-item__container{-->
<!--margin-bottom: 18px;-->
<!--}-->
<!---->
<!--}-->
<!--}-->
<!--</style>-->
