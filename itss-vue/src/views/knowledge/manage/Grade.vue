<template>
	<div class="main-container pb80">
		<el-form ref="form" :model="form1" :rules="rules" label-width="80px">
			<el-card shadow="never" class="article">
				<div slot="header">
					<span>
						<vab-icon :icon="['fas', 'tags']"></vab-icon> 知识库系统 - IT服务管理
					</span>
				</div>
				<div class="article-name">
					<span @click="articleLink">{{form.title}}</span>
				</div>
				<div class="article-tag">
					<p>
						<i class="el-icon-time"></i>
						<span>{{form.create_time}}</span>
					</p>
					<p>
						<i class="el-icon-user"></i>
						<span>{{form.createUserName}}</span>
					</p>
					<!--<p>-->
						<!--<i class="el-icon-collection-tag"></i>-->
						<!--<span>基础知识-故障</span>-->
					<!--</p>-->
					<!--<p>-->
						<!--<i class="el-icon-view"></i>-->
						<!--<span>浏览 2</span>-->
					<!--</p>-->
					<!--<p v-show="articleClick">-->
						<!--<i class="el-icon-star-off"></i>-->
						<!--<span>未评分</span>-->
					<!--</p>-->
				</div>
				<div class="article-content" v-html="form.content">
				</div>
        <el-divider content-position="left">附件</el-divider>
        <div class="box_card">
          <div class="box_content">
            <el-form-item class="tsitem">
              <div class="bghui">
                <el-upload
                  ref="uploadPicture"
                  class="upload-demo"
                  action=""
                  :auto-upload="false"
                  :on-preview="handlePreview"
                  :multiple="true"
                  disabled
                  :file-list="fileManageHandleList"
                ></el-upload>
              </div>
            </el-form-item>
          </div>
        </div>
        <div v-show="articleClick">



				<!--<el-divider content-position="left">附件(1)</el-divider>-->
				<!--<div class="box_card">-->
					<!--<div class="box_content">-->
						<!--<el-form-item class="tsitem">-->
							<!--<div class="bghui">-->
								<!--<el-image style="width: 50px; height: 50px" :src="url" :preview-src-list="srcList">-->
								<!--</el-image>-->
								<!--<div class="r">-->
									<!--<p class="p1" style="margin: 0; color: #333">-->
										<!--3232333456891983223.jpg-->
									<!--</p>-->
									<!--<p class="p2" style="margin: 0; color: #999">-->
										<!--500kb 3-15 10:00 致敬-->
									<!--</p>-->
								<!--</div>-->
							<!--</div>-->
							<!--<div class="bghui">-->
								<!--<el-image style="width: 50px; height: 50px" :src="url" :preview-src-list="srcList">-->
								<!--</el-image>-->
								<!--<div class="r">-->
									<!--<p class="p1" style="margin: 0; color: #333">-->
										<!--3232333456891983223.jpg-->
									<!--</p>-->
									<!--<p class="p2" style="margin: 0; color: #999">-->
										<!--500kb 3-15 10:00 致敬-->
									<!--</p>-->
								<!--</div>-->
							<!--</div>-->
						<!--</el-form-item>-->
					<!--</div>-->
				<!--</div>-->
				<!--<div v-show="articleClick">-->
				<el-divider content-position="left">基础信息</el-divider>
				<div class="box_card">
					<div class="box_content">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="编号:" class="tsitem">
                  {{form.id}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="状态:" class="tsitem">
                  {{ form.knowledge_status == 1 ? '使用' : '禁用' }}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="知识类别:" class="tsitem">
                  {{form.categoryName}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="可被全员搜索:" class="tsitem">
                  {{ form.is_full_search == 1 ? '是' : '否' }}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="推荐知识:" class="tsitem">
                  {{form.is_recommend == 1 ? '推荐' : '不推荐'}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="工程师内部分享范围:" class="tsitem">
                  {{form.knowledge_range}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="标题:" class="tsitem">
                  {{form.title}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="关键字:" class="tsitem">
                  {{form.keyword}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="添加人:" class="tsitem">
                  {{form.createUserName}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="添加时间:" class="tsitem">
                  {{form.create_time}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="修改人:" class="tsitem">
                  {{form.updateUserName}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="上次更新时间:" class="tsitem">
                  {{form.update_time}}
								</el-form-item>
							</el-col>
						</el-row>
					</div>
				</div>
				<el-divider content-position="left">审批信息</el-divider>
				<div class="box_card">
					<div class="box_content">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="审核人:" class="tsitem">
                  {{form.approver_name}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="审核时间:" class="tsitem">
                  {{form.approval_time}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="审核状态:" class="tsitem">
                  {{ form.status == 1 ? '待审核' : form.status == 2 ? '发布' : "审核拒绝" }}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="审核意见:" class="tsitem">
                  {{form.approval_opinions}}
								</el-form-item>
							</el-col>
						</el-row>
					</div>
				</div>
				<el-divider content-position="left">评分信息</el-divider>
				<div class="box_card">
					<div class="box_content">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="评分人:" class="tsitem">
                  {{form.rater_name}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="评分时间:" class="tsitem">
                  {{form.scoring_time}}
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="贡献分:" class="tsitem">
                  {{form.score}}分
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="评语:" class="tsitem">
                  {{form.comment}}
								</el-form-item>
							</el-col>
						</el-row>
					</div>
				</div>
			</div>
			</el-card>
			<el-row :gutter="20">
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
									<el-input type="number"  v-model.trim="form1.score" placeholder='请输入(1-10)' autocomplete="off"></el-input>
									<p
										style="line-height: 1.4;margin-top: 0;margin-bottom: 0; color: #E6A23C;">
										(1-10分)</p>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-form-item label="评语">
									<el-input v-model.trim="form1.comment" placeholder='请输入' autocomplete="off" type="textarea"
										:rows="2"></el-input>
								</el-form-item>
							</el-col>

						</el-card>
					</el-col>

				</el-row>


		</el-form>
		<div class="bottom">
			<el-button type="primary" size="medium" @click="saveok">保存</el-button>
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
    getPoint,
	} from '@/api/knowledgeInfo'
	import vabQuill from '@/plugins/vabQuill'
	import dialogone from './components/UserDiaOne'
	import dialogthree from './components/UserDiaThree'
  import {download} from "@/api/file";
	export default {
		name: 'manageGrade',
		components: {
			dialogone,
			dialogthree,
			vabQuill,
		},
		data() {
			return {
        fileManageHandleList: [],
				articleClick: true,
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
          score: '',
          comment: '',
          knowledge_info_id: '',
				},
        form1: {
          score: '',
          comment: '',
          knowledge_info_id: '',
        },
				rules: {
					// username: [{
					// 	required: true,
					// 	trigger: 'blur',
					// 	message: '请输入名称'
					// }, ],
					score: [{
						required: true,
						trigger: 'blur',
						message: '请输入分数'
					}],
				},
				borderColor: '#dcdfe6',
				checked: false,
				url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
				srcList: [
					'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
					'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg',
				],
				title: '',
				value: 3.7,
				dialogFormVisible: false,
				username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
			}
		},
		created() {
      this.getOneById()
		},
		beforeDestroy() {},
		mounted() {

		},
		methods: {
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
      async getOneById() {
        const id =  this.$route.query.id
        const { data } = await getKnowledgeInfoById({ id })
        this.form  = data
        //处理信息 附件
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
				// 返回上一步路由
				this.$router.go(-1)
			},
			saveok() {
        if (this.form1.score == null || this.form1.score == undefined || this.form1.score == ""){
          this.$message.warning(`请输入分数`);
          return;
        }
				this.$refs['form'].validate(async (valid) => {
				  this.form1.knowledge_info_id = this.$route.query.id
					if (valid) {
						const {
							msg
						} = await getPoint(this.form1)
						this.$baseMessage(msg, 'success')
						// this.$emit('fetch-data')

						this.closeall()
					} else {
						return false
					}
				})
			},
			articleLink(){
				this.articleClick = false
			},
      // handlePreview(file) {
      //   download(file.url).then((res) => {
      //     const url = window.URL.createObjectURL(new Blob([res]))
      //     const link = document.createElement('a')
      //     link.href = url
      //     link.setAttribute('download', file.name)
      //     document.body.appendChild(link)
      //     link.click()
      //   })
      // },
			handleRemove(file, fileList) {
			        console.log(file, fileList);
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
	  .article .el-card__body{
	  	height: calc(100vh - 600px);
		overflow-y: auto;
	  }
	  }
	  .article-name{
	  	font-size: 26px;
	  	line-height: 42px;
	  	font-weight: bold;
	  	color: #409eff;
	  }
	  .article-name span{
	  	cursor: pointer;
	  }
	  .article-name span:hover{
	  	text-decoration: underline;
	  }
	  .article-tag{
	  	padding-bottom: 10px;
	  	border-bottom: 1px solid #666;
	  }
	  .article-tag p{
	  	display: inline-block;
	  	margin: 0 10px 0 0;
	  	line-height: 24px;
	  	font-size: 0.875rem;
	  	color: #84898f;
	  }
	  .article-tag p i{
	  	margin-right: 2px;
	  	vertical-align: middle;
	  }
	  .article-tag p span{
	  	vertical-align: middle;
	  }
	  .article-content{
	  	padding: 20px 0;
	  }
	  .bghui{
	  	float: left;
	  	margin: 0 10px 10px 0;
	  }


</style>
