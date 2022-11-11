<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">添加</el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">修改</el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">删除</el-button>
        <el-button icon="el-icon-view" @click="handleView">查看</el-button>
        <el-button icon="el-icon-date" @click="handleCalendar">设置日历</el-button>

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>

          <el-form-item>
            <el-select v-model="queryForm.is_use" placeholder="状态">
              <el-option >请选择</el-option>
              <el-option label="停用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-input
              v-model.trim="queryForm.work_name"
              placeholder="名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>

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
      <el-table-column
        show-overflow-tooltip
        label="序号"
        align="center"
        width="80"
        sortable
      >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="work_name"
        sortable
        label="名称"
        align="center"
      ></el-table-column>


      <el-table-column
        show-overflow-tooltip
        label="状态"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.is_use == 1 ? '使用' : '停用' }}
        </template>
      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        label="默认"
        align="center"
      >
        <template #default="scope">
          <div v-if="scope.row.acquiesce != 1" style="color: red">是</div>
          <div v-if="scope.row.acquiesce == 1">否</div>
        </template>



      </el-table-column>


      <!--<el-table-column-->
      <!--show-overflow-tooltip  -->
      <!--width="280px;"-->
      <!--align="center"           -->
      <!--label="操作"-->
      <!--&gt;-->
      <!--<template #default="{row}">-->
      <!--<el-button type="primary" @click="handleRecord(row)" size="mini" plain>生成工作记录</el-button>-->

      <!--</template>-->
      <!--</el-table-column>-->
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
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
  import {toList, toDelete, getById} from '@/api/sysWorkingDays'
  import Edit from './components/Edit'
  import Views from './components/View'

  export default {
    name: 'sysworkdays',
    components: {Edit, Views},
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
          work_name: '',
        },
        form: {}
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
        this.$refs['edit'].showEdit()
      },
      handleView() {
        if (this.selectRows.id) {
          this.$refs['views'].showView(this.selectRows);
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      async getOneById(val) {
        const id = val
        const {data} = await getById({id})
        this.form = data
        console.log(this.form)
      },
      handleRecord(row) {
        if (row.id) {
          const ids = row.id
          this.$baseConfirm('你确定要生成此条工作记录吗', null, async () => {
            // const { msg } = await doDelete({ ids })
            this.$baseMessage("生成成功", 'success')
            //this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      handleCalendar() {
        this.$router.push({
          path: '/set/sysworkdaysCalendar',
        })
      },
      handleEdit() {
        if (this.selectRows.id) {
          console.log(this.selectRows)
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await toDelete({ids})
            console.log(msg);
            if (msg ==='成功') {
              this.$baseMessage(msg, 'success')
              // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
              const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
              this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
              this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo


              this.fetchData()
            }else {
              this.$baseMessage(msg, 'warning')
            }

          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
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
      async fetchData() {
        this.listLoading = true
        const {data, totalCount} = await toList(this.queryForm)
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
