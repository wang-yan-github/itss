<template>
	<div class="main-container pb80">
		<template>
		  <el-tabs v-model="activeName" type="card">
		    <el-tab-pane label="默认报表" name="first">
				<el-card shadow="never" style="border-top: 0">

				<div class="box_card">
					<div class="box_header"><i class="el-icon-s-data"></i>&nbsp;贡献分统计</div>
					<div class="box_content">
						<el-table  ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
							highlight-current-row border>
							<!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
							<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;" sortable>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="addPeople" width="200px;" sortable label="添加人" align="center">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="knowledgeOfTheNumber" label="知识数量" align="center"></el-table-column>
							<el-table-column show-overflow-tooltip prop="quantityReleased" label="已发布数量" align="center"></el-table-column>
							<el-table-column show-overflow-tooltip prop="publishContributionPoints" label="发布贡献分" align="center"></el-table-column>
							<el-table-column show-overflow-tooltip prop="releaseAverageScore" label="发布平均分" align="center"></el-table-column>

						</el-table>
					</div>
				</div>
				<div class="box_card">
					<div class="box_header"><i class="el-icon-s-data"></i>&nbsp;知识部门统计</div>
          <div class="box_content">
            <el-table  ref="listTable" stripe :data="list2" :element-loading-text="elementLoadingText"
                      highlight-current-row border>
              <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="addPeople" width="200px;" sortable label="部门" align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="knowledgeOfTheNumber" label="知识数量" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="quantityReleased" label="已发布数量" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="publishContributionPoints" label="发布贡献分" align="center"></el-table-column>

            </el-table>
          </div>
				</div>

				  </el-card>



			</el-tab-pane>

		  </el-tabs>
		</template>
	</div>
</template>

<script>
	import {
    contributionScoreStatistics,
    departmentOfKnowledge
	} from '@/api/publish.js'
	export default {
		name: 'statistics',
		data() {
			return {
				list: null,
				list2: null,
				listLoading: true,
				loading: false,
				elementLoadingText: '正在加载...',
				activeName:'first',
				queryForm:{

				},
				value1:'',
				value2:'',
				tableData: [],

			}
		},
		created() {
			this.getdata() ;
			this.getdata2() ;
		},
		watch: {

		},
		methods: {
      handleView(){
        this.$router.push({
          path: '/book/KnowStatistics',
        })
      },
			handleUpload() {
				this.$baseConfirm('确实要导出这些记录吗?', null, async () => {
					this.$baseMessage('数据导出成功', 'success')
				})
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			async fetchData() {
				this.listLoading = true
				const {
					data,
					totalCount
				} = await getList(this.queryForm)
				this.list = data
				this.total = totalCount
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
      getdata(){
        var that = this ;
        contributionScoreStatistics().then((res) => {
          if(res.code == 0){
            that.list = res.data ;
          }
        })
      },
      getdata2(){
        var that = this ;
        departmentOfKnowledge().then((res) => {
          if(res.code == 0){
            that.list2 = res.data ;
          }
        })
      }



		},
	}
</script>
<style lang="scss" scoped>
    .box_content{padding:20px 0px!important;}
	.main-container{

	}

	::v-deep {
		.el-tabs__header {
			margin-bottom: 0 !important;
		}

		.el-tabs__item.is-active {
			background: #fff !important;
		}

	}
</style>
