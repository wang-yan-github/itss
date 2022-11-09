<template>
	<el-dialog :title="title" :visible.sync="dialogFormVisible" width="870px" @close="close">
		<el-form ref="form" :model="form" :rules="rules" label-width="80px" label-position="top">
			<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选
			</el-checkbox>
			<div style="margin: 15px 0;"></div>
			<el-checkbox-group v-model="checkedCate" @change="handleCheckedCateChange">
				<el-checkbox v-for="item in categoryOption" :label="item" :key="item" border>{{item}}</el-checkbox>
			</el-checkbox-group>

		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="close">取 消</el-button>
			<el-button type="primary" @click="save">确 定</el-button>
		</div>
	</el-dialog>
</template>

<script>
	import {
		doEdit
	} from '@/api/userManagement'
	import vabQuill from '@/plugins/vabQuill'

	export default {
		name: 'assetedit',
		components: {
			vabQuill
		},
		data() {
			return {
				checkAll: false,
				checkedCate: ['工单数量统计', '工单数量趋势统计','工单/状态统计','SLA统计'],
				isIndeterminate: true,
				categoryOption: [
					'工单数量统计',
					'工单数量趋势统计',
					// '工单时间统计',
					'工单/状态统计',
					'SLA统计',
					// '工单/分类统计',
					// '工单来源统计',
					// '工单优先级统计',
					// '工单请求类型',
					// '工单解决方式',
					// '开单人开单数量统计',
					// '关单人关单数量统计',
					// '解决人解决数量统计',
					// '工程师参与的工单统计',
					// '未完成工单统计',
					// '服务群组关单数量统计',
					// '服务群组参与数量统计',
					// '工单/部门统计',
					// '工单状态统计(饼图)',
					// '工单状态统计(曲线图)',
					// '工单状态统计(按月对比)'
				],
				form: {

				},
        rules: {
          name: [{
            required: true,
            message: '请输入名称',
            trigger: 'blur'
          }]
        },

				title: '',
				dialogFormVisible: false,
			}
		},
		created() {},
		methods: {
      handleCheckedCateChange(val) {
        this.isIndeterminate = false;
        if (val.length === this.categoryOption.length) {
          this.checkAll = true;
        } else {
          this.checkAll = false;
        }
      },
			 handleCheckAllChange(val) {
				this.checkedCate = val ? this.categoryOption : [];
				this.isIndeterminate = false;
			  },
			  handleCheckedCitiesChange(value) {
				let checkedCount = value.length;
				this.checkAll = checkedCount === this.categoryOption.length;
				this.isIndeterminate = checkedCount > 0 && checkedCount < this.categoryOption.length;
			  },
			showEdit(row) {
				this.title = '统计项'
				this.dialogFormVisible = true
			},
			close() {
				this.$refs['form'].resetFields()
				this.form = this.$options.data().form
				this.dialogFormVisible = false
			},
			save() {
        var that = this;
        that.$emit('deptData', that.checkedCate)
        this.close()
			},
		},
	}
</script>
<style lang="scss" scoped>
	::v-deep {
		.el-checkbox.is-bordered.el-checkbox--small {
			margin-bottom: 14px;
			margin-right: 20px;
			width: 180px;
		}

		.el-checkbox.is-bordered+.el-checkbox.is-bordered {
			margin-left: 0;
		}
	}
</style>
