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
            提示:必填项可被检索、首页置顶、查看范围、标题、正文、创建人、添加时间 
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
  import { exportAssestExcelTemplate,importAssetsList } from '@/api/assetsManage.js'
export default {
  name: 'userroles',
  data() {
    return {
      form: {
      },
      title: '',
      fileManageInfoList: {},
	  dialogFormVisible: false,
      
      
     }
  },
  created() {
    this.reFresh();
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  methods: {
    showWin() {
      this.title = '导入'
      this.dialogFormVisible = true
    },
    reFresh(){
      window.location.reload;
    },
    close() {
      location. reload()
      this.dialogFormVisible = false
    },
    async save() {

      const params = new FormData();
      const uploadedFiles = [];

      const f = this.fileManageInfoList;

      if (f.status == 'ready') {
        params.append('file', f.raw);
        console.log(f.raw)
      } else if (f.status == 'success') {
        uploadedFiles.push(f.url);
      }
      importAssetsList(params).then((res) => {
          if(res.code == 0 ){
            this.totalNum = res.data.all.length;
            this.successNum = res.data.success.length;
            this.errorNum = res.data.error.length;
            this.successList = res.data.all;
            this.errorList = res.data.error;
            this.$baseMessage('导入成功，请查看结果', 'success')
            this.$emit('fetch-data');
            this.close() ;
          }else {
            this.$baseMessage(res.msg, 'error')
          }
      })

    },
    async downloadTemple() {
      //const { data} = await downloadUser()
      exportAssestExcelTemplate().then((res) => {
        console.log(res);
        // 获取文件名
        let objectUrl = URL.createObjectURL(new Blob([res]));
        // 文件地址
        const link = document.createElement('a');
        link.download = "配置管理模板.xlsx";
        link.href = objectUrl;
        link.click();
      });
    },
    //属性 附件上传 选择文件
    onChange(file, fileList) {
      this.fileManageInfoList = file;
      console.log(this.fileManageInfoList);
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
