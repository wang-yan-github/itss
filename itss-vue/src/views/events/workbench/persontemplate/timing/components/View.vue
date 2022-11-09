<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
   <el-timeline>
    <el-timeline-item timestamp="2018/4/12" placement="top">
      <el-card>
        <h4>更新 Github 模板</h4>
        <p>王小虎 提交于 2018/4/12 20:46</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2018/4/3" placement="top">
      <el-card>
        <h4>更新 Github 模板</h4>
        <p>王小虎 提交于 2018/4/3 20:46</p>
      </el-card>
    </el-timeline-item>
    <el-timeline-item timestamp="2018/4/2" placement="top">
      <el-card>
        <h4>更新 Github 模板</h4>
        <p>王小虎 提交于 2018/4/2 20:46</p>
      </el-card>
    </el-timeline-item>
  </el-timeline>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>     
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'companyviews',
  data() {
    return {
      title: '',
      dialogFormVisible: false, 
      username:'',     
    }
  },
  created() {
    
  },
  methods: {
    showView() {
      this.title = '查看任务执行详情'
      this.dialogFormVisible = true
    },    
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },
  },
}
</script>
