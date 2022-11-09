<template>
	<div class="index-container pb80">
		<el-form ref="form" :model="form" :rules="rules" label-width="140px">
			<template>
				<el-tabs v-model="activeName">
					<el-tab-pane label="属性" name="first">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-card class="cardbox" shadow="hover">
									<div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											基础信息
										</span>
										<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
									</div>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="ID">
                    	34234
                    </el-form-item>
									</el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="资产编号">
                    	34234234234234
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="配置项类型">
                    	虚拟机
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                  	<el-form-item label="配置子类">
                  		2342
                  	</el-form-item>
                  </el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="资产名称" prop="status">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="部门" prop="isvip">
											<el-input autocomplete="off" placeholder="请选择" style="width: 80%;"
												@click.native="openwinOne"></el-input>
											<el-button icon="el-icon-search"
												style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
												@click="openwinOne"></el-button>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="负责人">
											<el-select v-model="value" filterable remote reserve-keyword
												placeholder="负责人" :remote-method="remoteMethod" :loading="loading"
												style="width: 80%">
												<template #prefix>
													<span style="padding-left: 2px">
														<i class="el-icon-search"></i>
													</span>
												</template>
												<el-option v-for="item in options" :key="item.value" :label="item.label"
													:value="item.value"></el-option>
											</el-select>
											<el-button icon="el-icon-search"
												style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
												@click="openwinTwo"></el-button>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="领用人">
											<el-select v-model="value" filterable remote reserve-keyword
												placeholder="领用人" :remote-method="remoteMethod" :loading="loading"
												style="width: 80%">
												<template #prefix>
													<span style="padding-left: 2px">
														<i class="el-icon-search"></i>
													</span>
												</template>
												<el-option v-for="item in options" :key="item.value" :label="item.label"
													:value="item.value"></el-option>
											</el-select>
											<el-button icon="el-icon-search"
												style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
												@click="openwinTwo"></el-button>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="资产状态" prop="status">
											<el-select v-model.trim="form.status" class="w">
												<el-option label="是" value="0"></el-option>
												<el-option label="否" value="1"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="保密">
											<el-select v-model.trim="form.status" class="w">
												<el-option label="是" value="0"></el-option>
												<el-option label="否" value="1"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="购置(创建)日期" prop="data1">
											<el-date-picker v-model="value3" type="date" placeholder="选择日期"
												style="width: 100%;">
											</el-date-picker>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="财务编号">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="保密编号">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="购置公司">
											<el-select v-model.trim="form.status" class="w">
												<el-option label="是" value="0"></el-option>
												<el-option label="否" value="1"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="使用范围">
											<el-select v-model.trim="form.status" class="w">
												<el-option label="是" value="0"></el-option>
												<el-option label="否" value="1"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="IT状态">
											<el-select v-model.trim="form.status" class="w">
												<el-option label="是" value="0"></el-option>
												<el-option label="否" value="1"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
								</el-card>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-card class="cardbox" shadow="hover">
									<div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											属性信息
										</span>
										<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
									</div>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="品牌">
											<el-select v-model.trim="form.status" class="w">
												<el-option label="是" value="0"></el-option>
												<el-option label="否" value="1"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="型号">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="序列号(SN号)">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="维保开始日期" prop="data1">
											<el-date-picker v-model="value3" type="date" placeholder="选择日期"
												style="width: 100%;">
											</el-date-picker>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="NBU">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
								</el-card>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-card class="cardbox" shadow="hover">
									<div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											拓展信息
										</span>
										<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
									</div>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="测试文本1001" prop="status">
											<el-input v-model.trim="form.username" placeholder='请输入' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                  	<el-form-item label="字段一">
                  		<el-select v-model.trim="form.status" class="w">
                  			<el-option label="是" value="0"></el-option>
                  			<el-option label="否" value="1"></el-option>
                  		</el-select>
                  	</el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                  	<el-form-item label="字段二">
                  		<el-select v-model.trim="form.status" class="w">
                  			<el-option label="是" value="0"></el-option>
                  			<el-option label="否" value="1"></el-option>
                  		</el-select>
                  	</el-form-item>
                  </el-col>

								</el-card>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
								<el-card class="cardbox" shadow="hover">
									<div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											其他信息
										</span>
										<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
									</div>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="照片">
                        <div class="bghui">
                            <el-image
                              style="width: 50px; height: 50px"
                              :src="url"
                              :preview-src-list="srcList"
                            ></el-image>
                            <div class="r">
                              <p class="p1" style="margin: 0; color: #333">
                                3232333456891983223.jpg
                              </p>
                              <p class="p2" style="margin: 0; color: #999">
                                500kb 3-15 10:00 致敬
                              </p>
                            </div>
                          </div>
                        <el-dropdown
                          split-button
                          type="primary"
                          @click="screenClick"
                          @command="screenCommand"
                        >
                          截屏
                          <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="a">
                              截屏时隐藏浏览器
                            </el-dropdown-item>
                          </el-dropdown-menu>
                        </el-dropdown>
                        <el-upload
                          class="upload-button"
                          action="https://jsonplaceholder.typicode.com/posts/"
                          :on-preview="handlePreview"
                          :on-remove="handleRemove"
                          :before-remove="beforeRemove"
                          multiple
                          :limit="3"
                          :on-exceed="handleExceed"
                          :file-list="fileList"
                          style="display: inline"
                        >
                          <el-button size="small" type="primary">
                            点击上传
                          </el-button>
                        </el-upload>
                        <p class="ts">附件大小上限:20.0MB</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="附件">
                        <div class="bghui">
                            <el-image
                              style="width: 50px; height: 50px"
                              :src="url"
                              :preview-src-list="srcList"
                            ></el-image>
                            <div class="r">
                              <p class="p1" style="margin: 0; color: #333">
                                3232333456891983223.jpg
                              </p>
                              <p class="p2" style="margin: 0; color: #999">
                                500kb 3-15 10:00 致敬
                              </p>
                            </div>
                          </div>
                        <el-dropdown
                          split-button
                          type="primary"
                          @click="screenClick"
                          @command="screenCommand"
                        >
                          截屏
                          <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="a">
                              截屏时隐藏浏览器
                            </el-dropdown-item>
                          </el-dropdown-menu>
                        </el-dropdown>
                        <el-upload
                          class="upload-button"
                          action="https://jsonplaceholder.typicode.com/posts/"
                          :on-preview="handlePreview"
                          :on-remove="handleRemove"
                          :before-remove="beforeRemove"
                          multiple
                          :limit="3"
                          :on-exceed="handleExceed"
                          :file-list="fileList"
                          style="display: inline"
                        >
                          <el-button size="small" type="primary">
                            点击上传
                          </el-button>
                        </el-upload>
                        <p class="ts">附件大小上限:20.0MB</p>
                      </el-form-item>
                    </el-col>


									<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
										<el-form-item label="用途">
											<el-input v-model.trim="form.remakes" autocomplete="off" placeholder='请输入'
												type="textarea" :rows="3"></el-input>
										</el-form-item>
										<el-form-item label="位置">
											<el-input v-model.trim="form.remakes" autocomplete="off" placeholder='请输入'
												type="textarea" :rows="3"></el-input>
										</el-form-item>
										<el-form-item label="备注">
											<el-input v-model.trim="form.remakes" autocomplete="off" placeholder='请输入'
												type="textarea" :rows="3"></el-input>
										</el-form-item>
									</el-col>
								</el-card>
							</el-col>

						</el-row>
					</el-tab-pane>
					<el-tab-pane label="联系人" name="second">
						<el-table ref="listTable" border stripe :data="tablelist"
							:element-loading-text="elementLoadingText" highlight-current-row>
							<el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
							<el-table-column show-overflow-tooltip prop="user_name" label="用户名" sortable align="center">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="name" align="center" sortable label="姓名">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="status" align="center" sortable label="状态">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="telephone" align="center" label="电话" sortable>
								>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="phone" align="center" label="类型" sortable>
							</el-table-column>
						</el-table>
						<div class="btn-box" style="margin-top: 10px;">
							<el-button type="danger" icon="el-icon-delete" @click="handleDelete">
								移除选择对象
							</el-button>
							<el-button type="primary" icon="el-icon-plus" @click="handleAdd2">
								添加
							</el-button>
						</div>
					</el-tab-pane>
					<el-tab-pane label="供应商合同" name="three">
						<el-table ref="listTable" border stripe :data="tablelist"
							:element-loading-text="elementLoadingText" highlight-current-row>
							<el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
							<el-table-column show-overflow-tooltip prop="user_name" label="用户名" sortable align="center">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="name" align="center" sortable label="姓名">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="status" align="center" sortable label="状态">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="telephone" align="center" label="电话" sortable>
								>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="phone" align="center" label="类型" sortable>
							</el-table-column>
						</el-table>
						<div class="btn-box" style="margin-top: 10px;">
							<el-button type="danger" icon="el-icon-delete" @click="handleDelete">
								移除选择对象
							</el-button>
							<el-button type="primary" icon="el-icon-plus" @click="handleAdd3">
								添加
							</el-button>
						</div>
					</el-tab-pane>
					<el-tab-pane label="关联的配置项" name="four">
						<el-table ref="listTable" border stripe :data="tablelist"
							:element-loading-text="elementLoadingText" highlight-current-row>
							<el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
							<el-table-column show-overflow-tooltip prop="user_name" label="用户名" sortable align="center">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="name" align="center" sortable label="姓名">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="status" align="center" sortable label="状态">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="telephone" align="center" label="电话" sortable>
								>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="phone" align="center" label="类型" sortable>
							</el-table-column>
						</el-table>
						<div class="btn-box" style="margin-top: 10px;">
							<el-button type="danger" icon="el-icon-delete" @click="handleDelete">
								移除选择对象
							</el-button>
							<el-button type="primary" icon="el-icon-plus" @click="handleAdd4">
								添加
							</el-button>
						</div>
					</el-tab-pane>
				</el-tabs>
			</template>
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
		<dialogtwo ref="dialogtwo" @fetch-data="
			      {
			        input2
			      }
			    "></dialogtwo>
		<dialogthree ref="dialogthree" @fetch-data="
				    {
				      input2
				    }
				  "></dialogthree>
		<dialogfour ref="dialogfour" @fetch-data="
				  		    {
				  		      input2
				  		    }
				  		  "></dialogfour>
		<dialogfive ref="dialogfive" @fetch-data="
						  		    {
						  		      input2
						  		    }
						  		  "></dialogfive>
	</div>

</template>

<script>
	import {
		doEdit
	} from '@/api/userManagement'
	import dialogone from '../components/UserDiaOne'
	import dialogtwo from '../components/UserDiaTwo'
	//合同联系人
    import dialogthree from '@/components/contractContactDia'
    //合同供应商
    import dialogfour from '@/components/supplierContractDia'
    //关联的配置项
    import dialogfive from '@/components/assetsTypeDia'
	export default {
		name: 'edit',
		components: {
			dialogone,
			dialogtwo,
			dialogthree,
			dialogfour,
			dialogfive,
		},
		data() {
			return {
				activeName: 'first',
				checked: false,
				checked1: false,
				checked2: false,
				checked3: false,
				options: [],
				form: {
					username: '',
					status: '',
					num: '',
					remakes: '',
				},
				rules: {
					username: [{
						required: true,
						trigger: 'blur',
						message: '请输入名称'
					}, ],
					status: [{
						required: true,
						trigger: 'blur',
						message: '请选择状态'
					}],
					data1: [{
						required: true,
						trigger: 'blur',
						message: '请选择开始日期'
					}, ],
				},
				title: '',
				value2: '',
				value3: '',
				value4: '',
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        srcList: [
          'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
          'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg',
        ],
			}
		},
		created() {},
		methods: {
			openwinOne() {
				this.$refs['dialogone'].showWin()
			},
			openwinTwo() {
				this.$refs['dialogtwo'].showWin()
			},
			openwinThree() {
				this.$refs['dialogthree'].showWin()
			},
			handleDelete() {
				if (this.selectRows != '') {
					const ids = this.selectRows.id
					this.$baseConfirm('你确定要删除选中项吗', null, async () => {
						const {
							msg
						} = await doDelete({
							ids
						})
						this.$baseMessage(msg, 'success')
						this.fetchData()
					})
				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
			},
			handleAdd2() {
				this.$refs['dialogthree'].showWin()
			},
			handleAdd3() {
				this.$refs['dialogfour'].showWin()
			},
			handleAdd4() {
				this.$refs['dialogfive'].showWin()
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

			},


		},
	}
</script>
<style lang="scss" scoped>
	::v-deep {
		.tsitemd {
			label {
				width: 260 !important;
				text-align: left;
			}

			.el-form-item__content {
				margin-left: 0 !important;
			}
		}

		.upload-button {
		  .el-upload {
		    display: inline-block;
            margin-right: 10px;
		  }
		}
    .upload-demo{
      .el-upload{
        text-align: left;
      }
    }
	}
  .bghui {
    margin-bottom: 20px;
     background: #f5f5f5;
     padding: 15px;
     display: flex;
     flex-direction: row;
     border: 0px solid #ddd;
     width: 300px;
     align-items: center;
   }
   .bghui .r {
     display: inline-block;
     margin-left: 10px;
     line-height: 25px;
   }
</style>
