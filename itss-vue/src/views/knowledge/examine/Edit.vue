<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
								<span>
									基本信息
								</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="编号">
                {{form.id}}
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
              <el-form-item label="状态">
                {{ form.knowledge_status == 1 ? '使用' : '禁用' }}
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="部门" prop="departmentName" >
                <el-input autocomplete="off" placeholder="请选择" v-model="form.departmentName" style="width: 80%;" @click.native="openwinOne"></el-input>
                <el-button icon="el-icon-search"
                           style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                           @click="openwinOne"></el-button>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="知识类别" prop="categoryName">
                <el-input autocomplete="off" placeholder="请选择"  v-model="form.categoryName" style="width: 80%;" @click.native="openwinThree"></el-input>
                <el-button icon="el-icon-search"
                           style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                           @click="openwinThree"></el-button>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="可被全员搜索" prop="is_full_search">
                <el-select v-model="form.is_full_search" placeholder="请选择" style="width: 100%;">
                  <el-option label="是" value="1"></el-option>
                  <el-option label="否" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="工程师内部分享范围" prop="knowledge_range_id">
                <el-select v-model="form.knowledge_range_id" placeholder="请选择" style="width: 100%;">
                  <el-option v-for="(itema, index) in rangeInfo" :key="index" :label="itema.des" :value="itema.id"></el-option>

                  <!--<el-option label="是" value="1"></el-option>-->
                  <!--<el-option label="否" value="2"></el-option>-->
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="推荐知识" prop="is_recommend">
                <el-select v-model="form.is_recommend" placeholder="请选择" style="width: 100%;">
                  <el-option label="不推荐" value="0"></el-option>
                  <el-option label="推荐" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="排序号">
                <el-input v-model.trim="form.sort_num"  placeholder="请输入" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
              <!--<el-form-item label="可查看范围">-->
                <!--<p class="filltext">{{form.knowledge_range}}</p>-->
              <!--</el-form-item>-->
            <!--</el-col>-->
            <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
              <!--<el-form-item label="审批人">-->
                <!--<p class="filltext">{{form.approver_name}}</p>-->
              <!--</el-form-item>-->
            <!--</el-col>-->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="标题" prop="title">
                <el-input v-model.trim="form.title" placeholder="请输入" autocomplete="off"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="正文" prop="content">
                <vab-quill
                  v-model="form.content"
                  :min-height="200"
                  :options="optionsText"
                ></vab-quill>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="关键字">
                <el-input v-model.trim="form.keyword" placeholder="请输入" autocomplete="off" type="textarea"
                          :rows="5"></el-input>
                <p
                  style="line-height: 1.4;margin-top: 0;margin-bottom: 0px; color: #E6A23C;">
                  注:多个请使用英文模式下的逗号</p>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="附件" prop="isvip">
                <el-upload
                  class="upload-button"
                  action=""
                  :auto-upload="false"
                  :on-remove="handleRemoveHandle"
                  :before-remove="beforeRemoveHandle"
                  multiple
                  :limit="3"
                  :on-exceed="handleExceedHandle"
                  :file-list="fileManageHandleList"
                  :on-change="onChangeHandle">
                  <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
              </el-form-item>
            </el-col>

          </el-card>
        </el-col>


      </el-row>


    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" :loading="loading" @click="saveok">{{ loadingText }}</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>
    <dialogone ref="dialogone"    @deptData="deptset"  @fetch-data="
		    {
		      UserDiaOne
		    }
		  "></dialogone>
    <dialogtwo ref="dialogtwo" @fetch-data="
		      {
		        input2
		      }
		    "></dialogtwo>
    <dialogthree ref="dialogthree" @deptData="deptset3" @fetch-data="
			    {
			      UserDiaThree
			    }
			  "></dialogthree>
  </div>
</template>

<script>
import {updateKnowledgeInfo,getKnowledgeInfoById,getAllRange} from '@/api/knowledgeInfo'
import vabQuill from '@/plugins/vabQuill'
import dialogone from '@/components/bumenTree'
import dialogtwo from '../myKnowledge/components/UserDiaTwo'
import dialogthree from '../myKnowledge/components/UserDiaThree'
export default {
  name: 'examineEdit',
  components: {
    dialogone,
    dialogtwo,
    dialogthree,
    vabQuill,
  },
  data() {
    return {
      titleName: '知识审核修改',
      rangeInfo: [],
      fileManageHandleList: [],
      activeName: 'first',
      input2: '',
      formArr: [{
        value1: ''
      }],
      formArr1: [{
        value1: ''
      }],
      tablelist: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      checked: false,
      options: [],
      value: [],
      list: [],
      fileList:[],
      loading: false,
      loadingText: '保存',
      states: [
        'Alabama',
        'Alaska',
        'Arizona',
        'Arkansas',
        'California',
        'Colorado',
        'Connecticut',
        'Delaware',
        'Florida',
        'Georgia',
        'Hawaii',
        'Idaho',
        'Illinois',
        'Indiana',
        'Iowa',
        'Kansas',
        'Kentucky',
        'Louisiana',
        'Maine',
        'Maryland',
        'Massachusetts',
        'Michigan',
        'Minnesota',
        'Mississippi',
        'Missouri',
        'Montana',
        'Nebraska',
        'Nevada',
        'New Hampshire',
        'New Jersey',
        'New Mexico',
        'New York',
        'North Carolina',
        'North Dakota',
        'Ohio',
        'Oklahoma',
        'Oregon',
        'Pennsylvania',
        'Rhode Island',
        'South Carolina',
        'South Dakota',
        'Tennessee',
        'Texas',
        'Utah',
        'Vermont',
        'Virginia',
        'Washington',
        'West Virginia',
        'Wisconsin',
        'Wyoming',
      ],
      form: {
        id: '',
        dept_id: '',
        departmentName: '',
        knowledge_category_id: '',
        categoryName: '',
        is_full_search: '',
        knowledge_range_id: '',
        is_recommend: '',
        sort_num: '',
        title: '',
        content: '',
        keyword: '',
        username: 'admin',
        truename: '管理员',
        bumen: '',
        isvip: '',
      },
      rules: {
        title: [{
          required: true,
          trigger: 'blur',
          message: '请输入名称'
        }, ],
        // isvip: [{
        // 	required: true,
        // 	trigger: 'blur',
        // 	message: '请选择状态'
        // }],
      },
      optionsText: {
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
    }
  },
  created() {
    this.getOneById()
  },
  beforeDestroy() {},
  mounted() {
    this.getAllRange();
    const value = this.$route.query.value
    if (value == 1){
      this.titleName = '知识管理修改'
    }
    this.list = this.states.map((item) => {
      return {
        value: `${item}`,
        label: `${item}`
      }
    })
  },
  methods: {
    async getAllRange(){
      const { data } = await getAllRange();
      // for (var i = 0 ; i<data.length ;i++){
      //   data[i].id = data[i].id+"";
      // }
      this.rangeInfo = data

      console.log(this.rangeInfo)
    },
    deptset(data) {
      this.form.dept_id = data.id;
      this.form.departmentName = data.name;
    },
    deptset3(data) {
      this.form.knowledge_category_id = data.id;
      this.form.categoryName = data.name;
    },
    openwinOne() {
      this.$refs['dialogone'].showWin()
    },
    openwinTwo() {
      this.$refs['dialogtwo'].showWin()
    },
    openwinThree() {
      this.$refs['dialogthree'].showWin()
    },
    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.list.filter((item) => {
            return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    async getOneById() {
      const id = this.$route.query.id
      const {data} = await getKnowledgeInfoById({id})
      this.form = data
      //信息 附件
      if (data.files) {
        data.files.forEach((x) => {
          this.fileManageHandleList.push({name: x.file_name, url: x.id})
        })
      }
    },
    saveok() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true
          this.loadingText = '保存中...'
          const params = new FormData();
          const uploadedFiles = [];
          for (let i = 0; i < this.fileManageHandleList.length; i++) {
            const f = this.fileManageHandleList[i];
            if (f.status == 'ready') {
              params.append('file', f.raw);
            } else if (f.status == 'success') {
              uploadedFiles.push(f.url);
            }
          }
          this.form.uploadedFiles = uploadedFiles.join(',');
          params.append('body', JSON.stringify(this.form));
          console.log(params)
          const { msg } = await updateKnowledgeInfo(params)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')

          this.closeall()

          // if (this.titleName = '知识审核修改') {
          //   this.$router.push({
          //     path: '/book/knowledgeAudit',
          //   });
          // }else {
          //
          //   this.$router.push({
          //     path: '/book/knowledgeManage',
          //   });
          // }
          this.loading = false
        } else {
          return false
        }
      })
    },
    handleRemoveHandle(file, fileList) {
      this.fileManageHandleList = fileList;
      console.log(file, fileList);
    },
    handleExceedHandle(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemoveHandle(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    //附件上传 选择文件
    onChangeHandle(file, fileList) {
      this.fileManageHandleList = fileList;
    },
  }
}
</script>
<style lang="scss" scoped>
.index-container {
  .hiddenmore {
    display: inline;

    ::v-deep {
      .el-input {
        width: 130px;
      }
    }
  }
}

.tips {
  color: #E6A23C;
}

.boxtable .btn-box {
  margin-top: 10px;
}
.filltext{
  margin: 0;
}
::v-deep {.tsitemd {
  label {
    width: 260 !important;
    text-align: left;
  }
  .el-form-item__content {
    margin-left: 0 !important;
  }
}
  .el-upload{
    text-align: left;
    margin-top: 20px;
  }
}
</style>
