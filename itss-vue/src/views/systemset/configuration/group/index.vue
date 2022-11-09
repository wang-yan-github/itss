<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <!--<el-button icon="el-icon-document-copy" type="success" @click="handleView">-->
        <!--复制-->
        <!--</el-button>-->
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
        <el-button icon="el-icon-upload2" type="success" @click="handleUpload">导出</el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>

          <el-form-item>
            <el-select v-model="queryForm.is_use" placeholder="状态" clearable>
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.name" placeholder="名称" clearable @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">查询</el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
    >
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="id"-->
      <!--label="编码"-->
      <!--align="center"-->
      <!--width="200px;"-->
      <!--sortable-->
      <!--&gt;</el-table-column>-->
      <el-table-column show-overflow-tooltip prop="name" width="200px;" sortable label="名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip label="状态" align="center">
        <template slot-scope="scope">{{ scope.row.is_use == 1 ? '启用' : '禁用' }}</template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="非一线组" align="center">
        <template slot-scope="scope">{{ scope.row.nonFirstLineGroup == 1 ? '是' : '否' }}</template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="principalName" label="负责人" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="engineerName" label="工程师" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="auto_engineer_name" label="处理人为空自动指定工程师" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="sort" label="排序" align="center"></el-table-column>
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
  </div>
</template>

<script>
  import {getList, del} from '@/api/eventServiceManage'

  export default {
    name: 'groupTemp',
    data() {
      return {
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
          is_use: '',
          name: '',
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
      handleAdd() {
        this.$router.push({
          path: '/set/groupAdd',
        })
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$baseMessage('复制成功！', 'success')
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        console.log(this.selectRows);
        if (this.selectRows.id) {
          this.$router.push({path: '/set/groupEdit', query: {id: this.selectRows.id}})
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        var that = this;
        if (that.selectRows != '' && that.selectRows != null) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await del(that.selectRows.id);
            this.$baseMessage(msg, 'success');
            this.queryData()
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleUpload() {
        const ids = this.selectRows.id
        this.$baseConfirm('你确定要导出这些数据吗', null, async () => {
          this.$baseMessage('导出数据成功', 'success')
          this.fetchData()
        })
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

      async fetchData() {
        this.listLoading = true
        const {data} = await getList(this.queryForm)
        this.list = data.list
        this.total = data.total
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
      }
    }
  }
</style>
