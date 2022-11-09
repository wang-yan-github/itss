<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <div class="uploadbox">
        <div class="moban">
          <el-button size="medium" icon="el-icon-download" type="danger">
            导入模版下载
          </el-button>
          <p>
            提示:多级部门、多个服务群组和多个角色时，用逗号(,)分隔；类型和状态取值请与添加页面一致。
          </p>
        </div>
    
        <el-upload
          class="upload-demo"
          drag
          action="https://jsonplaceholder.typicode.com/posts/"
          multiple
          style="width: 100%"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            将文件拖到此处，或
            <em>点击上传</em>
          </div>
          <div class="el-upload__tip" slot="tip">
            只能上传（excel）文件，且不超过500kb
          </div>
        </el-upload>
      </div>
    

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">导 入</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'userroles',
  data() {
    return {
      form: {
        
      },
      rules: {
       
      },
      title: '',
      dialogFormVisible: false,
      
    }
  },
  created() {
   
  },
  methods: {
    showWin() {
      this.title = '导入'
      this.dialogFormVisible = true
    },
   
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      // this.tableData
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
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
<style lang="scss" scoped>
.uploadbox {
  ::v-deep {
    .el-upload-dragger {
      width: 760px !important;
    }
  }
}
.el-card{
    ::v-deep{
        .el-input{width:100px;}
        .el-card__header{font-size:16px;}
    }
}
</style>
