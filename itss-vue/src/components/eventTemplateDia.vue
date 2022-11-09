<!--选择事件工单模板 单选-->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.template_name"
              placeholder="模板名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.create_user" filterable remote reserve-keyword
                       placeholder="创建人" :remote-method="remoteMethod" :loading="loading" clearable>
              <template #prefix>
		  			<span style="padding-left: 2px">
		  				<i class="el-icon-search"></i>
		  			</span>
              </template>
              <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.is_use" placeholder="状态" clearable>
               <el-option>请选择</el-option>
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-select v-model="queryForm.is_public" placeholder="公共模板" clearable>
               <el-option>请选择</el-option>
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              检索
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-left-panel>

    </vab-query-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @current-change="handleCurrentChange"
      height="250"
    >
      <el-table-column
        show-overflow-tooltip
        type="index" label="序号"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="templateNumber"
        width="200px;"
        sortable
        label="模板编号"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="template_name"
        label="模板名称"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="event_service_name"
        label="所属服务群组"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        label="创建人"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        label="创建时间"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="update_user_name"
        label="修改人"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="update_time"
        label="修改时间"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use"
        label="状态"
      >
        <template slot-scope="scope">
          {{ scope.row.is_use == 0 ? '禁用' : '启用' }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_public"
        label="公共模板"
      >
        <template slot-scope="scope">
          {{ scope.row.is_use == 0 ? '否' : '是' }}
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getList, del} from '@/api/eventTemplate'
  import {
    upUserData
  } from '@/api/eventServiceManage'

  export default {
    name: 'eventTemplateDia',
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
          template_name: "",
          create_user: "",
          is_use: "",
          is_public: "",
        },
        tableData: [],
        list: null,
        row: {},
        auto_engineer_data: ""
      }
    },
    created() {
      this.fetchData();
      this.upUserData();
    },
    methods: {
      showWin() {
        this.title = '工单模板';
        this.dialogFormVisible = true
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData()
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val;
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData()
      },
      remoteMethod(query) {
        if (query !== '') {
          this.loading = true;
          setTimeout(() => {
            this.loading = false;
            this.options = this.list.filter((item) => {
              return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
            })
          }, 200)
        } else {
          this.options = []
        }
      },
      async upUserData() {
        var that = this;
        const resp = await upUserData();
        that.auto_engineer_data = resp.data;
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        if (this.selectRows != '' && this.selectRows != null) {
          var that = this;
          that.$emit('listData', that.selectRows);
          this.close();
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      async fetchData() {
        this.listLoading = true;
        const {data} = await getList(this.queryForm);
        this.list = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
