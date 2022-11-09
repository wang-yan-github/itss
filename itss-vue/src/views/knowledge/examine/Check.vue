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
								<el-form-item label="部门" prop="departmentName">
									<el-input autocomplete="off" placeholder="请选择" v-model="form.departmentName" style="width: 80%;" @click.native="openwinOne"></el-input>
									<el-button icon="el-icon-search"
										style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
										@click="openwinOne"></el-button>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="知识类别" prop="categoryName">
									<el-input autocomplete="off" placeholder="请选择"   v-model="form.categoryName" style="width: 80%;" @click.native="openwinThree"></el-input>
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
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
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
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="添加人">
                  {{form.createUserName}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="添加时间">
                  {{form.create_time}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="修改人">
                  {{form.updateUserName}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="上次更新时间">
                  {{form.update_time}}
								</el-form-item>
							</el-col>

						</el-card>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
						<el-card class="cardbox" shadow="hover">
							<div slot="header">
								<span>
									审核信息
								</span>
								<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
							</div>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="审核意见">
									<el-input v-model.trim="form.approval_opinions" placeholder='请输入' autocomplete="off" type="textarea"
										:rows="2"></el-input>
								</el-form-item>
							</el-col>

						</el-card>
					</el-col>
					<!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
						<!--<el-card class="cardbox" shadow="hover">-->
							<!--<div slot="header">-->
								<!--<span>-->
									<!--评分信息-->
								<!--</span>-->
								<!--&lt;!&ndash; <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> &ndash;&gt;-->
							<!--</div>-->
							<!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
								<!--<el-form-item label="贡献分" prop="score">-->
									<!--<el-input v-model.trim="form.score" placeholder='请输入(1-10)' autocomplete="off"></el-input>-->
									<!--<p-->
										<!--style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">-->
										<!--(1-10分)</p>-->
								<!--</el-form-item>-->
							<!--</el-col>-->
							<!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
								<!--<el-form-item label="评语">-->
									<!--<el-input v-model.trim="form.comment" placeholder='请输入' autocomplete="off" type="textarea"-->
										<!--:rows="2"></el-input>-->
								<!--</el-form-item>-->
							<!--</el-col>-->

						<!--</el-card>-->
					<!--</el-col>-->

				</el-row>


		</el-form>
		<div class="bottom">
			<el-button type="primary" size="medium" :loading="fabuLoading" @click="saveok">{{ fabuLoadingText }}</el-button>
			<el-button type="primary" size="danger" :loading="jujueLoading" @click="savenook">{{ jujueLoadingText }}</el-button>
			<el-button size="medium" @click="closeall">取消</el-button>
		</div>
		<dialogone ref="dialogone" @fetch-data="
		    {
		      input2
		    }
		  "></dialogone>
			<dialogthree ref="dialogthree" @fetch-data="
			    {
			      input2
			    }
			  "></dialogthree>
	</div>
</template>

<script>
	import {
    getKnowledgeInfoById,
    toExamineKnowledge,
    toRefuseExamineKnowledge,
    getAllRange,
	} from '@/api/knowledgeInfo'
	import vabQuill from '@/plugins/vabQuill'
	import dialogone from './components/UserDiaOne'
	import dialogthree from './components/UserDiaThree'
	export default {
		name: 'examineCheck',
		components: {
			dialogone,
			dialogthree,
			vabQuill,
		},
		data() {
			return {
        titleName: '知识审核审核',
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
        fabuLoading: false,
        fabuLoadingText: '确认',
        jujueLoading: false,
        jujueLoadingText: '拒绝',
				form: {
          id: '',
          departmentName: '',
          categoryName: '',
          is_full_search: '',
          knowledge_range_id: '',
          is_recommend: '',
          sort_num: '',
          title: '',
          content: '',
          keyword: '',
          approval_opinions: '',
          score: '',
          comment: '',
				},
				rules: {
					// username: [{
					// 	required: true,
					// 	trigger: 'blur',
					// 	message: '请输入名称'
					// }, ],
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
      this.getAllRange();
		  this.getOneById();
      const value = this.$route.query.value
      if (value == 1){
        this.titleName = '知识管理审核'
      }
		},
		beforeDestroy() {},
		mounted() {

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
      async getOneById() {
        const id =  this.$route.query.id
        const { data } = await getKnowledgeInfoById({ id })
        this.form  = data
        //信息 附件
        if (data.files) {
          data.files.forEach((x) => {
            this.fileManageHandleList.push({name: x.file_name, url: x.id})
          })
        }
      },
			openwinOne() {
				this.$refs['dialogone'].showWin()
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
        this.$emit('fetch-data')
				// 返回上一步路由
				this.$router.go(-1)
			},
			saveok() {
        if(null != this.form.approval_opinions && undefined != this.form.approval_opinions && '' != this.form.approval_opinions){
          if(this.form.approval_opinions.length > 255){
            this.$baseMessage("审核意见超出长度", 'warning')
            return;
          }
        }

          this.$refs['form'].validate(async (valid) => {
            if (valid) {
              this.fabuLoading = true
              this.fabuLoadingText = '发布中...'
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
              params.append('body', encodeURIComponent(JSON.stringify(this.form)));

            	const { msg } = await toExamineKnowledge(params)
            	this.$baseMessage(msg, 'success')
            	this.$emit('fetch-data')

            	this.closeall()

              // if (this.titleName == '知识审核添加') {
              //   this.$router.push({
              //     path: '/book/knowledgeAudit',
              //   });
              // }else {
              //
              //   this.$router.push({
              //     path: '/book/knowledgeManage',
              //   });
              // }

              this.fabuLoading = false
            } else {
            	return false
            }
          })
			},
      savenook() {
        if(null != this.form.approval_opinions && undefined != this.form.approval_opinions && '' != this.form.approval_opinions){
          if(this.form.approval_opinions.length > 255){
            this.$baseMessage("审核意见超出长度", 'warning')
            return;
          }
        }


        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.jujueLoading = true
            this.jujueLoadingText = '拒绝中...'
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
            params.append('body', encodeURIComponent(JSON.stringify(this.form)));
            const { msg } = await toRefuseExamineKnowledge(params)
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')

            this.closeall()

            //
            // if (this.titleName = '知识审核审核') {
            //   this.$router.push({
            //     path: '/book/knowledgeAudit',
            //   });
            // }else {
            //
            //   this.$router.push({
            //     path: '/book/knowledgeManage',
            //   });
            // }

            this.jujueLoading = false
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
			handleRemove(file, fileList) {
			        console.log(file, fileList);
			      },
			      handlePreview(file) {
			        console.log(file);
			      },
			      handleExceed(files, fileList) {
			        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
			      },
			      beforeRemove(file, fileList) {
			        return this.$confirm(`确定移除 ${ file.name }？`);
			      }
		},

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
