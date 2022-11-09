<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="10">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-view" @click="handleView">查看</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
        <el-button icon="el-icon-timer" type="warning" @click="handleUpload">定时任务</el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="14">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.template_name" placeholder="模板名称" clearable @keyup.enter.native="queryData" style="width:120px!important;" />
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.create_user" filterable remote reserve-keyword placeholder="创建人" :remote-method="remoteMethod" :loading="loading" clearable style="width:160px!important;">
              <template #prefix>
		  			    <span style="padding-left: 2px"><i class="el-icon-search"></i></span>
              </template>
              <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.is_use" placeholder="状态" clearable style="width:100px!important;">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.is_public" placeholder="公共模板" clearable style="width:120px!important;">
              <el-option label="是" value="1"></el-option>
              <el-option label="否" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row border @current-change="handleCurrentChange" height="calc(100vh - 300px)">
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
      <el-table-column show-overflow-tooltip prop="templateNumber" width="190px;" sortable label="模板编号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="template_name" label="模板名称" agign="center" align="center">
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{ row.template_name }}</el-link>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="event_service_name" label="所属服务群组" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_user_name" label="创建人" width="100px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="create_time" label="创建时间" width="160px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="update_user_name" label="修改人" width="100px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="update_time" label="修改时间" width="160px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="is_use" label="状态" width="100px;" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.is_use == 1">启用</el-tag>
          <el-tag type="danger" v-else>禁用</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="is_public" label="公共模板" width="100px;" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.is_public == 1">是</el-tag>
          <el-tag type="danger" v-else>否</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout" :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>

  </div>
</template>

<script>
  import {getMyList, del} from '@/api/eventTemplate'
  import {
    upUserData
  } from '@/api/eventServiceManage'

  export default {
    name: 'persontemplatemanagement1',
    components: {},
    data() {
      return {
        input2: '',
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
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
        value: '',
        loading: false,
        auto_engineer_data: ""
      }
    },
    created() {
      this.fetchData();
      this.upUserData();
    },
    methods: {
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
      handleUpload() {
        this.$router.push({
          path: '/set/scheduledTask',
        })
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$router.push({
          path: '/thing/persontemplateadd',
        })
      },
      // handleDingshi() {
      //   this.$router.push({
      //     path: '/thing/persontemplatetiming',
      //   })
      // },
      // openwin() {
      //   this.$refs['creator'].showWin()
      // },
      // openhelp() {
      //   this.$router.push({
      //     path: '/thing/persontemplateadd',
      //   })
      // },
      handlesee(row) {
        this.$router.push({
          path: '/thing/persontemplateview',
          query: {id: row.id,}
        })
      },
      handleView() {
        if (this.selectRows.id) {
          this.$router.push({
            path: '/thing/persontemplateview',
            query: {id: this.selectRows.id}
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        if (this.selectRows.id) {
          this.$router.push({
            path: '/thing/persontemplateedit', query: {id: this.selectRows.id}
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        var that = this;
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.id
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            del(ids).then((res) => {
              that.$baseMessage("操作成功", 'success')
              that.fetchData()
            });
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
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
      fetchData() {//触发子组件城市选择-选择城市的事件\
        var that = this;
        getMyList(that.queryForm).then((res) => {
          that.list = res.data.list;
          that.total = res.data.total
        });
      },
      async upUserData() {
        var that = this;
        const resp = await upUserData()
        that.auto_engineer_data = resp.data;
      },
    },
  }
</script>

