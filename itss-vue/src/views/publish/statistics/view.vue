<template>
	<div class="index-container">
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
		<vab-query-form>
      <vab-query-form-left-panel :span="18">

          <el-form-item>
          	<el-button>日报</el-button>
          </el-form-item>
          <el-form-item>
          	<el-button>周报</el-button>
          </el-form-item>
          <el-form-item>
          	<el-button>月报</el-button>
          </el-form-item>
          <el-form-item>
          	<el-button>季报</el-button>
          </el-form-item>
          <el-form-item>
          	<el-button>年报</el-button>
          </el-form-item>
          <el-form-item>
          	<el-button>固定时间</el-button>
          </el-form-item>
      		<el-form-item>
      			<el-date-picker
      			      v-model="value1"
      			      type="daterange"
      			      range-separator="至"
      			      start-placeholder="开始日期"
      			      end-placeholder="结束日期">
      			    </el-date-picker>
      		</el-form-item>
      		<el-form-item>
      			<el-button icon="el-icon-search" type="primary" @click="queryData">
      				查询
      			</el-button>

      		</el-form-item>

      </vab-query-form-left-panel>
			<vab-query-form-right-panel :span="6">
				<el-button icon="el-icon-back" type="primary" @click="">
					上一周报
				</el-button>
        <el-button icon="el-icon-right" type="primary" @click="">
        	下一周报
        </el-button>
        <el-button icon="el-icon-download" type="primary" @click="">
        	导出
        </el-button>

			</vab-query-form-right-panel>

		</vab-query-form>
    </el-form>
    <div class="box_card">

      <div class="box_content">
        <h1>最近10条问题单</h1>
        <p>2022-03-03至2022-03-05</p>
        <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
          highlight-current-row border @current-change="handleCurrentChange">
          <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
          <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="200px;" sortable>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="username" width="200px;" sortable label="标题" align="center">
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="truename" label="描述" align="center"></el-table-column>

        </el-table>
      </div>
    </div>

			<dialogthree ref="dialogthree" @fetch-data="
			    {
			      input2
			    }
			  "></dialogthree>
			  <userimport ref="userimport"></userimport>
	</div>
</template>

<script>
	import {
		getList,
		doDelete
	} from '@/api/userManagement'

	export default {
		name: 'myknowledgeadd',
		components: {},
		data() {
			return {
				checked: false,
				isShow: false,
				list: null,
				listLoading: true,
				loading: false,
				options: [],
				value:'',
				value1:'',
				value2:'',
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
				},
			}
		},
		created() {
			this.fetchData()
		},
		methods: {


			handleCurrentChange(val) {
				this.selectRows = val
			},

			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			moreQuery() {
				this.isShow = !this.isShow
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
				.selbox .el-button{
					padding: 0;
					text-align: center;
				}
				.selbox .el-input{
					width: 100%;
				}
				.textbox .el-input__inner{
					padding-right: 0;
				}
			}
		}
	}
  .box_content{
    text-align: center;
    h1{
      margin-bottom: 10px;
    }
    p{
      margin-top: 0;
      margin-bottom: 20px;
    }
  }
</style>
