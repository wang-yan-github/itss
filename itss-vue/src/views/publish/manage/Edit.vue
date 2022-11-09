<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="属性" name="first">
          <el-card shadow="never" style="border-top: 0">
            <div class="box_card">
              <div class="box_header">基本信息</div>
              <div class="box_content">
                <el-row :gutter="20">


                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发布类别:" prop="category">
                      <el-select v-model="form.category" class="w" @change="addUserInfo">
                        <el-option
                          v-for="item in publishCategoryList"
                          :key="item.id+''"
                          :label="item.name"
                          :value="item.id+''"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发布类型:" prop="type">
                      <el-select v-model="form.type" class="w">
                        <el-option label="一般发布" value="1"></el-option>
                        <el-option label="紧急发布" value="2"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="评审人:">{{ form.approver }}</el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发布人:">{{ form.publisher_name }}</el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标题:" prop="title">
                      <el-input v-model="form.title"></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="申请发布理由:" prop="reason">
                      <quill-editor
                        ref="myQuillEditor1"
                        v-model="form.reason"
                        :min-height="200"
                        :options="options"
                      ></quill-editor>
                      <p class="ts">
                        请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="发布内容:" prop="content">
                      <quill-editor
                        ref="myQuillEditor2"
                        v-model="form.content"
                        :min-height="200"
                        :options="options"
                      ></quill-editor>
                      <p class="ts">
                        请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="发布方案:" prop="publish_plan">
                      <quill-editor
                        ref="myQuillEditor3"
                        v-model="form.publish_plan"
                        :min-height="200"
                        :options="options"
                      ></quill-editor>
                      <p class="ts">
                        请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="测试方案:" prop="test_plan">
                      <quill-editor
                        ref="myQuillEditor4"
                        v-model="form.test_plan"
                        :min-height="200"
                        :options="options"
                      ></quill-editor>
                      <p class="ts">
                        请详细描述问题或现象，可ctrl+v粘贴图片或上传附件
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="测试人:">
                      <el-select v-model.trim="form.tester_id" class="w">
                        <el-option
                          v-for="item in this.userList"
                          :key="item.id+''"
                          :label="item.name"
                          :value="item.id+''">
                        </el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="附件:" style="width: 400px;">
                      <el-upload
                        class="upload-button"
                        action=""
                        :auto-upload="false"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        multiple
                        :limit="3"
                        :on-preview="handlePreview"
                        :on-exceed="handleExceed"
                        :file-list="fileManageInfoList"
                        :on-change="onChange">
                        <el-button size="small" type="primary">点击上传</el-button>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="发布添加通知:">
                      <el-checkbox v-model="form.add_notice">通知</el-checkbox>
                    </el-form-item>
                  </el-col>

                </el-row>
              </div>
            </div>

          </el-card>
        </el-tab-pane>
        <el-tab-pane label="关联的变更" name="second">
          <el-card shadow="never" style="border-top: 0">
            <el-table
              v-loading="listLoading"
              :data="tableData"
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelectRows"
              height="560"
            >
              <el-table-column
                show-overflow-tooltip
                type="selection"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="change_no"
                label="变更单号"
                sortable
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="title"
                label="标题"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="classify_name"
                label="变更类别"
                sortable
                align="center"
              ></el-table-column>

              <el-table-column
                show-overflow-tooltip
                prop="type_name"
                label="变更类型"
              ></el-table-column>

              <el-table-column
                show-overflow-tooltip
                prop="status_name"
                label="状态"
                align="center"
              ></el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete"
              >
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
                添加关联的变更
              </el-button>
            </div>
          </el-card>
        </el-tab-pane>
        <el-tab-pane :label="logName" name="third">
          <div class="box_card">
            <div class="box_content">
              <el-timeline>
                <el-timeline-item
                  v-for="(log, index) in logList"
                  :key="index"
                  :timestamp="log.operate_time"
                  placement="top"
                  size="large"
                  color="#0bbd87"
                >
                  <el-card>
                    <h4>{{log.title}}</h4>
                    <p>{{log.user_name}}</p>
                    <div class="ql-editor content-detail" v-html="log.content"/>
                  </el-card>
                </el-timeline-item>
              </el-timeline>
            </div>
          </div>
        </el-tab-pane>
      </el-tabs>

      <div class="bottom">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="medium"
          @click="saveok"
        >
          修改
        </el-button>
        <el-button
          type="warning"
          size="medium"
          icon="el-icon-close"
          @click="closeall"
        >
          取消
        </el-button>
      </div>
    </el-form>

    <changeDialog
      ref="changeDialog"
      @listData="listEventsManages"
      @fetch-data="
        {
          input2
        }
      "
    ></changeDialog>
  </div>
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
// 引用变更弹窗页面
import changeDialog from '@/components/changeDialog'
import {getPublishCategory, publishCategoryList,getPublishBasic,publishBasicInfoToEdit,getLogInfo} from "@/api/publish";
import {getUserList} from "@/api/change";
import {download} from "@/api/file";
export default {
  name: 'publishManageEdit',
  components: { vabQuill, changeDialog },
  data() {
    return {
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
      fileManageInfoList: [],
      publishCategoryList:[],
      logList: [],
      logName: '',
      userList:[],
      options: {
        theme: 'snow',
        bounds: document.body,
        debug: 'warn',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ color: [] }, { background: [] }],
            ['blockquote', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ align: [] }],
            [{ direction: 'rtl' }],
            [{ font: [] }],
            ['clean'],
            ['link', 'image'],
          ],
        },
        placeholder: '内容...',
        readOnly: false,
      },
      borderColor: '#dcdfe6',
      fileList: [],
      listLoading: true,
      loading: false,
      loadingText: '保存',
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      loading: false,
      elementLoadingText: '正在加载...',
      tableData: [],
      activeName: 'first',
      input2: '',
      radio1: '',
      radio2: '1',
      reverse: true,
      activities: [],
      formArr: [{ value1: '' }],
      checked: false,
      checked1: false,
      value: [],
      form: {
        type: '',
        status:'',
        category: '',
        approver: '',
        publisher: '',
        title: '',
        reason: '',
        content: '',
        publish_plan: '',
        test_plan:'',
        tester_id:'',
        enclosure:'',
        add_notice:'',
        publisher_name:'',
        changeVoList:[],
        uploadedFiles:[],
      },
      rules: {
        category: [
          { required: true, trigger: 'blur', message: '请选择发布类别' },
        ],
        type: [{ required: true, trigger: 'blur', message: '请选择发布类型' }],
        title: [{ required: true, trigger: 'blur', message: '请输入标题' }],
        reason: [{ required: true, trigger: 'blur', message: '请输入发布理由' }],
        content: [{ required: true, trigger: 'blur', message: '请输入发布内容' }],
        publish_plan: [{ required: true, trigger: 'blur', message: '请输入发布方案' }],
        test_plan: [{ required: true, trigger: 'blur', message: '请输入测试方案' }],
      },
    }
  },
  created() {
    this.getPublishBasic()
    this.fetchPublishCategoryLData()
    this.getUserList()
    this.getLogInfo();
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  beforeDestroy() {},
  mounted() {
    this.quillToTop();
  },
  methods: {
    quillToTop(){
      this.$refs.myQuillEditor1.quill.enable(false);
      this.$refs.myQuillEditor2.quill.enable(false);
      this.$refs.myQuillEditor3.quill.enable(false);
      this.$refs.myQuillEditor4.quill.enable(false);
      setTimeout(() => {
        this.$refs.myQuillEditor1.quill.enable(true);
        this.$refs.myQuillEditor2.quill.enable(true);
        this.$refs.myQuillEditor3.quill.enable(true);
        this.$refs.myQuillEditor4.quill.enable(true);
      },2000);
    },
    screenClick() {
      this.$baseMessage('截屏中', 'success')
    },
    screenCommand(command) {
      this.$message('click on item ' + command)
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handleAdd() {
      this.$refs['changeDialog'].showWin()
    },
    handlePreview(file) {
      download(file.url).then((res) => {
        const url = window.URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', file.name)
        document.body.appendChild(link)
        link.click()
      })
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleDelete() {
      if (this.selectRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectRows.forEach((item,i) => {
            this.tableData.some((x, i)=>{
              if(x.id==item.id){
                this.tableData.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleRemove(file, fileList) {
      this.fileManageInfoList = fileList;
      console.log(file, fileList);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    //属性 附件上传 选择文件
    onChange(file, fileList) {
      this.fileManageInfoList = fileList;
    },
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    addUserInfo(){
      this.getPublishCategory()
    },
    listEventsManages(row){
      row.forEach((item,i) => {
        let index = this.tableData.findIndex((ele) => {
          return ele.id === item.id;
        });
        if (index === -1) {
          this.tableData.push(item)
        }
      })
    },
    saveok() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true;
          this.loadingText = '保存中...';
          const params = new FormData();
          const uploadedFiles = [];
          for (let i = 0; i < this.fileManageInfoList.length; i++) {
            const f = this.fileManageInfoList[i];
            if (f.status == 'ready') {
              params.append('file', f.raw);
            } else if (f.status == 'success') {
              uploadedFiles.push(f.url);
            }
          }
          this.form.uploadedFiles = uploadedFiles.join(',');

          if(this.form.add_notice){
            this.form.add_notice='1'
          }else {
            this.form.add_notice='0'
          }
          this.form.changeVoList=this.tableData

          params.append('body', encodeURIComponent(JSON.stringify(this.form)));

          publishBasicInfoToEdit(params).then((res) => {
            this.$baseMessage("成功", 'success')
            this.$store.dispatch('tabsBar/delRoute', this.$route)
            this.$emit('fetch-data')
            // this.$router.push({
            //   path: '/fabu/publishManage'
            // })
            //
            // location. reload();
            this.closeall();
          });
          this.loading = false

          // const { msg } = await publishBasicInfoToEdit(this.form)
          // this.$baseMessage(msg, 'success')
          // this.$emit('fetch-data')

          // this.closeall()
        } else {
          return false
        }
      })
    },
    async getLogInfo() {
      this.listLoading = true
      let id = this.$route.query.id;
      let row = {};
      row.id = id;
      const {data} = await getLogInfo(row)
      console.log(data);
      this.logList = data;
      console.log(this.logList)
      this.logName = "日志("+this.logList.length+")";
      console.log(this.logName);
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getUserList(){
      const { data } = await getUserList()
      this.userList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getPublishCategory() {
      this.listLoading = true
      const {data} = await getPublishCategory({id:this.form.category})
      this.form.publisherName = data.publisherName
      this.form.publisher = data.publisher
      this.form.approver = data.approver
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async fetchPublishCategoryLData() {
      this.listLoading = true
      const {data} = await publishCategoryList({is_use:'1'})
      this.publishCategoryList = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    // 初始化数据
    async getPublishBasic() {
      this.listLoading = true
      let id = this.$route.query.id;
      let row = {};
      row.id = id;
      const {data} = await getPublishBasic(row)
      this.form=data
      this.tableData=data.changeVoList
      if(this.form.add_notice=="1"){
        this.form.add_notice=true
      }else {
        this.form.add_notice=false
      }
      //属性信息 附件
      if (data.fileManages) {
        data.fileManages.forEach((x) => {
          this.fileManageInfoList.push({name: x.file_name, url: x.id})
        })
      }
      console.log(this.data)
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>

<style lang="scss" scoped>
.main-container {
  ::v-deep {
    .ql-container {
      min-height: 200px;
    }
    // .el-alert__content p{margin:0;}
    .el-tabs__header {
      margin-bottom: 0 !important;
    }
    .el-tabs__item.is-active {
      background: #fff !important;
    }
    .el-card__body {
      height: calc(100vh - 290px) !important;
      overflow-y: auto;
      overflow-x: hidden;
    }
    .upload-button {
      .el-upload {
        display: inline-block;
      }
    }
    .el-divider--horizontal {
      margin-bottom: 0 !important;
    }
    .upload-button {
      display: inline-block;
    }
    .el-tabs--border-card {
      box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
    }
    .el-card__body {
      height: calc(100vh - 290px) !important;
      .el-tabs__content {
        height: calc(100vh - 350px) !important;
        overflow-y: auto;
        overflow-x: hidden;
      }
    }
    .hasiscoll {
      .el-form-item__label {
        line-height: 22px;
      }
      .el-form-item__content {
        line-height: 22px;
      }
      .el-card__body {
        overflow-y: auto !important;
        overflow-x: hidden;
      }
    }
    .el-timeline {
      .el-card__body {
        height: auto !important;
      }
    }
  }

}
</style>
