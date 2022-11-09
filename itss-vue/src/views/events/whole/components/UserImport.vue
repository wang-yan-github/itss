<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <div class="uploadbox">
        <div class="moban">
          <el-button size="medium" icon="el-icon-download" type="danger" @click="downloadTemple">
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
        :auto-upload="false"
        :on-change="onChange"
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
import { download, importExcel } from '@/api/eventAllManage'
export default {
  name: 'userroles',
  data() {
    return {
      fileManageInfoList: {},
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
    //属性 附件上传 选择文件
    onChange(file, fileList) {
      this.fileManageInfoList = file;
      console.log(this.fileManageInfoList);
    },
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
      const params = new FormData();
      const uploadedFiles = [];

      const f = this.fileManageInfoList;
      if (f.status == 'ready') {
        params.append('file', f.raw);
      } else if (f.status == 'success') {
        uploadedFiles.push(f.url);
      }

      debugger
      const { data } = importExcel(params)
      this.$baseMessage('导入成功', 'success')
      this.$emit('fetch-data')
    },
    async downloadTemple() {
      download().then((res) => {
        // 获取文件名
        let objectUrl = URL.createObjectURL(new Blob([res]));
        // 文件地址
        const link = document.createElement('a');
        link.download = "事件模板下载.xlsx";
        link.href = objectUrl;
        link.click();
      });
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
