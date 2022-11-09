<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="600px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="满意度评分" prop="event_satisfaction">
            <el-rate v-model="form.event_satisfaction" show-text></el-rate>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="建议" prop="proposal">
            <el-input v-model.trim="form.proposal" placeholder='请输入' autocomplete="off" type="textarea"
                      :rows="6"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
    </div>

  </el-dialog>
</template>

<script>
  import {evaluateAdd} from '@/api/eventSelfService'

  // 确认评价
  export default {
    name: 'eventGrade',
    components: {},
    data() {
      return {
        id: '',
        form: {
          id: '',
          event_satisfaction: '',//满意度
          proposal: '',//建议
        },
        fileList: [],

        borderColor: '#dcdfe6',

        rules: {
          event_satisfaction: [{required: true, trigger: 'blur', message: '请输入'}],
          proposal: [{required: true, trigger: 'blur', message: '请输入'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {
      showWin(id) {
        this.title = '确认评价'
        this.dialogFormVisible = true
        this.id = id;
        this.form.id = id;
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const {msg} = await evaluateAdd(this.form)
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')
            this.close();
            this.closeall();
          } else {
            return false
          }
        })
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .upload-button {
      display: inline-block;
      margin-right: 10px;
    }

    .el-rate {
      margin-top: 6px;
    }
  }
</style>
