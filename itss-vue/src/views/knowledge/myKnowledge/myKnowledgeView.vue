<template>
	<div class="main-container pb80">
		<el-form ref="form" label-width="80px">
			<el-card shadow="never">
				<div slot="header">
					<span>
						<vab-icon :icon="['fas', 'tags']"></vab-icon> 知识库系统 - IT服务管理
					</span>
				</div>
				<div class="article-name">
					<span >{{form.title}}</span>
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
				<div class="ql-editor content-detail" v-html="form.content">
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

			<div class="bottom">
				<el-button type="" size="medium" @click="closeAll">
					关闭
				</el-button>

			</div>
		</el-form>
	</div>
</template>

<script>
  import {getKnowledgeInfoById} from '@/api/knowledgeInfo'
  import {download} from "@/api/file";
	export default {
		name: 'myKnowlegeView',
		components: {},
		data() {
			return {
        fileManageHandleList: [],
				form: {
					username: 'http://ent1.topvee.cn/saml/metadata.do',
					truename: '管理员',
					password: '',
					email: '',
					permissions: [],
					bumen: '',
					isvip: '',
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
				articleClick:true,
			}
		},
		created() {
      this.getOneById()
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
        console.log(data);
        //处理信息 附件
        if (data.files) {
          data.files.forEach((x) => {
            this.fileManageHandleList.push({name: x.file_name, url: x.id})
          })
        }
      },
			closeAll() {
				// 调用全局挂载的方法
				this.$store.dispatch('tabsBar/delRoute', this.$route)
				// 返回上一步路由
				this.$router.go(-1)
			},
			articleLink(){
				this.articleClick = false
			}
		},
	}
</script>
<style lang="scss" scoped>
	.main-container {
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
		::v-deep {
			.el-divider--horizontal {
				margin-bottom: 0 !important;
			}
			.el-divider__text{
				font-size: 16px;
			}
			.el-tabs--border-card {
				box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
			}

			.el-card__body {
				height: calc(100vh - 290px) !important;

				.el-tabs__content {
					height: calc(100vh - 350px)x !important;
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
