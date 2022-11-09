<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close">
    <el-descriptions class="margin-top" title="" :column="2" border>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label"><i class="el-icon-postcard"></i> 名 称</template>
        {{ form.sla_event_name }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label"><i class="el-icon-sort"></i>考虑优先级</template>
        {{ form.is_first == '0' ? '否' : '是' }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label"><i class="el-icon-collection-tag"></i>考虑请求类型</template>
        {{ form.is_request == '0' ? '否' : '是' }}
      </el-descriptions-item>
      <el-descriptions-item label-class-name="my-label">
        <template slot="label"><i class="el-icon-reading"></i> 描 述</template>
        {{ form.remark }}
      </el-descriptions-item>

    </el-descriptions>
    <el-table v-loading="listLoading" ref="listTable" :max-height="tableH" stripe :data="list"
              :element-loading-text="elementLoadingText"
              highlight-current-row border>
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <!--      <el-table-column show-overflow-tooltip prop="id" label="优先级" align="center" width="100px;" sortable></el-table-column>-->
      <!--      <el-table-column show-overflow-tooltip prop="username" width="200px;" sortable label="请求类型" align="center"></el-table-column>-->
      <!--      <el-table-column show-overflow-tooltip prop="truename" label="响应时间(分钟数)" align="center"></el-table-column>-->
      <!--      <el-table-column show-overflow-tooltip prop="truename" label="解决时间(分钟数)" align="center"></el-table-column>-->

      <el-table-column show-overflow-tooltip prop="first_name" label="优先级" align="center" width="100px;"
                       sortable></el-table-column>
      <el-table-column show-overflow-tooltip prop="request_type_name" width="200px;" sortable label="请求类型"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="response_time" label="响应时间(分钟数)" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="solve_time" label="解决时间(分钟数)" align="center"></el-table-column>
    </el-table>

    <!--    <el-table-->
    <!--      v-loading="listLoading"-->
    <!--      :data="tableData"-->
    <!--      border-->
    <!--      :element-loading-text="elementLoadingText"-->
    <!--      height="400"-->
    <!--      row-key="id"-->
    <!--      style="margin-top: 20px"-->
    <!--      default-expand-all-->
    <!--      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"-->
    <!--    >-->
    <!--      <el-table-column show-overflow-tooltip prop="name" width="250px" label="名称"></el-table-column>-->
    <!--      <el-table-column show-overflow-tooltip prop="path" label="功能路径"></el-table-column>-->
    <!--    </el-table>-->

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getFirstAndRequest, getSlaEvent} from "@/api/affect";

  export default {
    name: 'slaEventView',
    data() {
      return {
        form: {
          sla_event_name: '',
          is_first: '',
          is_request: '',
          remark: '',
        },
        title: '',
        dialogFormVisible: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        list: [{
          first_id: '',
          request_type_id: '',
          first_name: '',
          request_type_name: '',
          response_time: '',
          solve_time: '',
        }],
        listLoading: true,
        tableH: 0
      }
    },
    created() {
      // this.fetchData()

      let h = document.documentElement.clientHeight || document.body.clientHeight;
      this.tableH = h - 240 + 'px';
    },
    methods: {
      // async fetchData() {
      // 	this.listLoading = true
      // 	const {
      // 		data,
      // 		totalCount
      // 	} = await getList(this.queryForm)
      // 	this.list = data
      // 	this.total = totalCount
      // 	setTimeout(() => {
      // 		this.listLoading = false
      // 	}, 300)
      // },
      async showView(row) {
        this.title = '查看'
        this.form = Object.assign({}, row)
        console.log(row)
        this.getEditData(row)
        this.dialogFormVisible = true
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      //编辑传参查询data 事件sla data
      async getEditData(row) {
        const {data} = await getSlaEvent(row);
        this.list = data[0].slaEventFirstAndRequestVos;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
<style>
  .my-label {
    width: 110px !important;
  }

  .margin-top {
    margin-bottom: 20px;
  }
</style>
