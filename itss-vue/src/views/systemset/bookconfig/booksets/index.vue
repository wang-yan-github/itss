<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="never">
            <div slot="header" class="font16 b">
              <span>搜索权重配置</span>
            </div>

            <el-form-item label="标题权重:" prop="title_weight">
              <el-input type="number" v-model="form.title_weight"></el-input>
            </el-form-item>

            <el-form-item label="描述权重:" prop="desc_weight">
              <el-input type="number" v-model="form.desc_weight"></el-input>
            </el-form-item>
            <el-form-item label="关键字权重:" prop="keyword_weight">
              <el-input type="number" v-model="form.keyword_weight"></el-input>
            </el-form-item>

            <el-alert title="" type="success" :closable="false">
              0表示不参与搜索,正整数表示权重，值越大权重越高
            </el-alert>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
  import {getConfigDetail, addOrUpdateDetail} from "@/api/knowledgeConfig";

  export default {
    name: 'booksets',
    components: {},
    data() {
      return {
        isDisable: false,
        input2: '',
        reverse: true,
        activities: [],
        formArr: [{value1: ''}],
        tablelist: null,
        checked: false,
        checked1: false,
        value: [],
        form: {
          id: '',
          title_weight: '',
          desc_weight: '',
          keyword_weight: '',
        },
        rules: {
          title_weight: [{required: true, trigger: 'blur', message: '请输入标题权重（正整数）'}],
          desc_weight: [{required: true, trigger: 'blur', message: '请输入描述权重（正整数）'}],
          keyword_weight: [{required: true, trigger: 'blur', message: '请输入关键字权重（正整数）'}],
        },
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
      openwin() {
        this.$router.push({
          path: '/set/usermanagementedit',
          query: {id: 123},
        })
      },
      async fetchData() {
        const {data} = await getConfigDetail()
        console.log(data);
        if (data != null){
          this.form = data;
        }

        // data.map((item, index) => {
        //   if (index === data.length - 1) {
        //     item.color = '#0bbd87'
        //   }
        // })
        // this.activities = data
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.isDisable = true;
        console.log(this.form)
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await addOrUpdateDetail(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success')
                this.fetchData()
                this.closeall()
              } else {
                this.$baseMessage("失败", 'error');
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
      // 复制成功时的回调函数
      onCopy(e) {
        this.$message.success('内容已复制到剪切板！')
      },
      // 复制失败时的回调函数
      onError(e) {
        this.$message.error('抱歉，复制失败！')
      },
    },
  }
</script>
