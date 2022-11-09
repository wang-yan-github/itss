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
									<el-input autocomplete="off" placeholder="请选择"  v-model="form.departmentName"  style="width: 80%;" @click.native="openwinOne"></el-input>
									<el-button icon="el-icon-search"
										style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
										@click="openwinOne"></el-button>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="知识类别" prop="categoryName">
									<el-input autocomplete="off" placeholder="请选择" v-model="form.categoryName" style="width: 80%;" @click.native="openwinThree"></el-input>
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
                    <el-option label="是" value="shanghai"></el-option>
                    <el-option label="否" value="beijing"></el-option>
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
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="可查看范围">
									<p class="filltext">{{form.knowledge_range}}</p>
								</el-form-item>
							</el-col>
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
								<el-form-item label="附件" prop="isvip">
									<el-button type="primary" size="small" icon="el-icon-camera">截屏</el-button>
									<el-upload
									  class="upload-demo"
									  action="https://jsonplaceholder.typicode.com/posts/"
									  :on-preview="handlePreview"
									  :on-remove="handleRemove"
									  :before-remove="beforeRemove"
									  multiple
									  :limit="3"
									  :on-exceed="handleExceed"
									  :file-list="fileList">
									  <el-button size="small" icon="el-icon-upload2" type="primary">文件上传</el-button>
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
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
						<el-card class="cardbox" shadow="hover">
							<div slot="header">
								<span>
									评分信息
								</span>
								<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
							</div>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="贡献分" prop="score">
									<el-input v-model.trim="form.score" placeholder='请输入(1-10)' autocomplete="off"></el-input>
									<p
										style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
										(1-10分)</p>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="评语">
									<el-input v-model.trim="form.comment" placeholder='请输入' autocomplete="off" type="textarea"
										:rows="2"></el-input>
								</el-form-item>
							</el-col>

						</el-card>
					</el-col>

				</el-row>


		</el-form>
		<div class="bottom">
			<el-button type="primary" size="medium" @click="saveok">保存</el-button>
			<el-button type="primary" size="danger" @click="savenook">拒绝</el-button>
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
	} from '@/api/knowledgeInfo'
	import vabQuill from '@/plugins/vabQuill'
	import dialogone from './components/UserDiaOne'
	import dialogthree from './components/UserDiaThree'
	export default {
		name: 'manageCheck',
		components: {
			dialogone,
			dialogthree,
			vabQuill,
		},
		data() {
			return {
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
      this.getOneById()
		},
		beforeDestroy() {},
		mounted() {

		},
		methods: {
      async getOneById() {
        const id =  this.$route.query.id
        const { data } = await getKnowledgeInfoById({ id })
        this.form  = data
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
				// 返回上一步路由
				this.$router.go(-1)
			},
			saveok() {
				this.$refs['form'].validate(async (valid) => {
					if (valid) {
						const {
							msg
						} = await toExamineKnowledge(this.form)
						this.$baseMessage(msg, 'success')
						// this.$emit('fetch-data')

						this.closeall()
					} else {
						return false
					}
				})
			},
      savenook() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const {
              msg
            } = await toRefuseExamineKnowledge(this.form)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')

            this.closeall()
          } else {
            return false
          }
        })
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
