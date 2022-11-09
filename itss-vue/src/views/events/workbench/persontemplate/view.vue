<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" label-width="120px">
      <el-card shadow="never">
        <div class="box_card">
          <div class="box_header">模板信息</div>
          <div class="box_content">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="模板编号:">
                  {{ form.templateNumber }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="模板名称:">
                  {{ form.template_name }}
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="所属服务群组:">
                  {{ form.event_service_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="状态:">
                  {{ form.is_use == 0 ? '禁用' : '启用' }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="公共模板:">
                  {{ form.is_public == 0 ? '否' : '是' }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="描述:">
                  {{ form.template_desc }}
                </el-form-item>
              </el-col>
<!--              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                <el-form-item label="使用次数:">-->
<!--                  TICTEMP20220318001-->
<!--                </el-form-item>-->
<!--              </el-col>-->
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="创建人:">
                  {{ form.create_user_name }}
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="创建时间:">
                  {{ form.create_time }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="修改人:">
                  {{ form.update_user_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="修改时间:">
                  {{ form.update_time }}
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>
        <div class="box_card">
          <div class="box_header">工单信息</div>
          <div class="box_content">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="用户:">
                  {{ form.user_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="来源:">
                  {{ form.source_name }}
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="请求类型:">
                  {{ form.request_type_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="工单类别:">
                  {{ form.bill_type_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="紧急度:">
                  {{ form.urgency_degree_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="影响度:">
                  {{ form.effect_degree_name }}
                </el-form-item>
              </el-col>
<!--              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                <el-form-item label="优先级:">-->
<!--                  使用-->
<!--                </el-form-item>-->
<!--              </el-col>-->
<!--              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">-->
<!--                <el-form-item label="服务群组:">-->
<!--                  否-->
<!--                </el-form-item>-->
<!--              </el-col>-->
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="处理人:">
                  {{ form.handler_name }}
                </el-form-item>
              </el-col>
               <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="标题:">
                  {{ form.title }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="工单描述:">
                  <div class="ql-editor content-detail" v-html="form.description"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="开单人:">
                  {{ form.create_user_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="解决方式:">
                  {{ form.solve_type_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="方案分类:">
                  {{ form.programme_type_name }}
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="解决方案:" >
                  <div class="ql-editor content-detail" v-html="form.solution"/>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
        </div>

      </el-card>

      <div class="bottom">
        <el-button size="medium" icon="el-icon-close" @click="closeall">
          关闭
        </el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
import {eventTemplateInfo} from "@/api/eventTemplate";
export default {
  name: 'persontemplateview',
  components: { vabQuill },
  data() {
    return {
      input2: '',
      form: {
        username: '',
        truename: '',
        bumen: '',
        isvip: '',
        content: '',
        content2: '',
        leave:'',
        bill_type_name:'',
        bill_type:'',
        programme_type_name:"",
        templateNumber:"",
        template_name:"",
        is_public:"",
        event_service_id:"",
        is_use:"",
        template_desc:"",
        user_id:"",
        title:"",
        description:"",
        source:"",
        request_type:"",
        effect_degree:"",
        urgency_degree:"",
        is_effect:"",
        service_groups:"",
        handler:"",
        solve_type:"",
        programme_type:"",
        solution:""
      },
    }
  },
  created() {
    this.fetchData();
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    fetchData() {
      var that = this ;
      if(null != that.$route.query.id || undefined != that.$route.query.id){
        eventTemplateInfo(that.$route.query.id).then((res) => {
          console.log(res)
          that.form = res.data
          that.form.source = parseInt(that.form.source) ;
          that.form.request_type = parseInt(that.form.request_type) ;
          that.form.solve_type = parseInt(that.form.solve_type) ;
          that.form.urgency_degree = parseInt(that.form.urgency_degree) ;
          that.form.effect_degree = parseInt(that.form.effect_degree) ;
          that.form.event_service_id = parseInt(that.form.event_service_id) ;
          // that.form.service_groups = that.form.service_groups+"" ;
        });
      }
    },
  },
}
</script>

<style lang="scss" scoped>
  .content-detail{
    padding: 8px 15px 8px 0;
  }
</style>
