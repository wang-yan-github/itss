<template>
	<el-dialog :title="title" :visible.sync="dialogFormVisible" width="900px" append-to-body @close="close">
		<vab-query-form>
			<vab-query-form-left-panel :span="24">
				<el-form :inline="true" :model="queryForm" @submit.native.prevent>
					<el-form-item>
						<el-input v-model.trim="queryForm.user_name" placeholder="请输入用户名" clearable @keyup.enter.native="queryData"/>
					</el-form-item>
					<el-form-item>
						<el-button icon="el-icon-search" type="primary" @click="queryData">
							检索
						</el-button>
					</el-form-item>
				</el-form>
			</vab-query-form-left-panel>

		</vab-query-form>
		<el-table v-loading="listLoading" :data="tableData" border highlight-current-row
			:element-loading-text="elementLoadingText" @selection-change="setSelectRows" height="250" @row-click="btn">
      <el-table-column show-overflow-tooltip type="index" label="序号" width="70px;" align="center">
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip align="center" prop="user_name" label="用户名"></el-table-column>
      <el-table-column show-overflow-tooltip align="center" prop="name" label="姓名"></el-table-column>
      <el-table-column show-overflow-tooltip align="center" prop="departmentName" label="部门"></el-table-column>
      <el-table-column show-overflow-tooltip align="center" prop="telephone" label="电话"></el-table-column>
      <el-table-column show-overflow-tooltip align="center" prop="mailbox" label="邮箱"></el-table-column>
      <el-table-column show-overflow-tooltip align="center" prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status == '1'">活动</el-tag>
          <el-tag type="warning" v-else-if="scope.row.status == '2'">暂停</el-tag>
          <el-tag type="danger" v-else>已禁止</el-tag>
        </template>
      </el-table-column>
		</el-table>
		<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
			:total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

		<div slot="footer" class="dialog-footer">
			<el-button @click="close">取 消</el-button>
			<el-button type="primary" @click="save">确 定</el-button>
		</div>
	</el-dialog>
</template>

<script>

import {getUserList} from '@/api/user'
	export default {
		name: 'diaTwo',
		data() {
			return {
				title: '',
				dialogFormVisible: false,
				listLoading: true,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				loading: false,
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
				},
				tableData: [],
			}
		},
		created() {
		  this.fetchData();
			setTimeout(() => {
				this.listLoading = false
			}, 300)
		},
		methods: {
			showWin() {
				this.title = '选择人员'
				this.dialogFormVisible = true
			},
      btn(row,col,event){
        this.selectRows = row;
      },

			setSelectRows(val) {
			  this.selectRows = val;
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			handleSizeChange(val) {
			  console.log(val)
				this.queryForm.pageSize = val
        this.fetchData()
				// this.tableData
			},
			handleCurrentChange(val) {
			  console.log(val)
				this.queryForm.pageNo = val
				this.fetchData()
			},
			close() {
				// this.$refs['form'].resetFields()
				//this.form = this.$options.data().form
				this.dialogFormVisible = false
			},
			save() {
        this.selectRows.value = this.selectRows.id
        this.selectRows.label = this.selectRows.name

        this.$emit('userData',this.selectRows);
        this.$emit('pageVisible', this.selectRows.name);
				this.close();
			},
      fetchData() {
        var that  =this ;
        getUserList(that.queryForm).then((res) => {

          that.tableData = res.data.list ;
          that.total = res.data.total
        });
      },
		},
	}
</script>
