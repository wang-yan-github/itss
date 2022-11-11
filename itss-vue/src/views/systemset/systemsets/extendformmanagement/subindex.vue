<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="8">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
          添加
        </el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">
          修改
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.name"
              placeholder="名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.type" placeholder="类型">
              <el-option  value="">请选择</el-option>
              <el-option label="文本" value="0"></el-option>
              <el-option label="长文本" value="1"></el-option>
              <el-option label="整数" value="2"></el-option>
              <el-option label="小数" value="3"></el-option>
              <el-option label="日期" value="4"></el-option>
              <el-option label="单选" value="5"></el-option>
              <el-option label="复选" value="6"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-select v-model="queryForm.is_required" placeholder="必填">
              <el-option  value="">请选择</el-option>
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.is_enable" placeholder="状态">
              <el-option  value="">请选择</el-option>
              <el-option label="使用" value="0"></el-option>
              <el-option label="禁用" value="1"></el-option>
            </el-select>
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
      :data="tableData"
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
        width="200px;"
        sortable
      >
        <template slot-scope="scope">
          {{(queryForm.pageIndex-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="name"
        sortable
        label="名称"
        align="center"
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="type"
        sortable
        align="center"
        label="类型"
      >
        <template #default="scope">
          <el-tag size="small" type="success" v-if="scope.row.type == 0">文本</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 1">长文本</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 2">整数</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 3">小数</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 4">日期</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 5">单选</el-tag>
          <el-tag size="small" type="success" v-else-if="scope.row.type == 6">复选</el-tag>
        </template>

      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        label="启用查询"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.is_search == 0 ? '启用' : '停用' }}
        </template>
      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        label="必填"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.is_required == 0 ? '是' : '否' }}
        </template>
      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        label="状态"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.is_enable == 0 ? '使用' : '禁用' }}
        </template>
      </el-table-column>


    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageIndex"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
  import {getList, toDel} from '@/api/assetsExpandFormField'
  import Edit from './components/EditSub'
  import Sortable from 'sortablejs'

  export default {
    name: 'extendformsubmanagement',
    components: {Edit},
    data() {
      return {
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        tableData: [],
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        value1: '',
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageIndex: 1,
          pageSize: 10,
          name: '',
          form_id: '',
          type: '',
          is_required: '',
          is_enable: '',
        },
      }
    },
    created() {
      this.fetchData();
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    mounted() {
      // 阻止默认行为
      document.body.ondrop = function (event) {
        event.preventDefault()
        event.stopPropagation()
      }
      this.rowDrop()
      this.columnDrop()
    },
    methods: {
      //行拖拽
      rowDrop() {
        const tbody = document.querySelector('.el-table__body-wrapper tbody')
        const _this = this
        Sortable.create(tbody, {
          onEnd({newIndex, oldIndex}) {
            const currRow = _this.tableData.splice(oldIndex, 1)[0]
            _this.tableData.splice(newIndex, 0, currRow)
          },
        })
      },
      //列拖拽
      columnDrop() {
        const wrapperTr = document.querySelector('.el-table__header-wrapper tr')
        this.sortable = Sortable.create(wrapperTr, {
          animation: 180,
          delay: 0,
          onEnd: (evt) => {
            const oldItem = this.dropCol[evt.oldIndex]
            this.dropCol.splice(evt.oldIndex, 1)
            this.dropCol.splice(evt.newIndex, 0, oldItem)
          },
        })
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleRefresh() {
        this.$router.go(0)
        // this.$router.replace({
        //   path:"/set/bookclassmanagement"
        // })
      },
      handleAdd() {
        var x = {};
        x.form_id = this.queryForm.form_id
        this.$refs['edit'].showEdit(x)
      },
      handleRole() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['roles'].showWin()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleView() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['rolesview'].showView()
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          var x = {};
          x.id = ids
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await toDel(x)
            this.$baseMessage(msg, 'success')
            // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
            const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
            this.queryForm.pageIndex = this.queryForm.pageIndex > totalPage ? totalPage : this.queryForm.pageIndex
            this.queryForm.pageIndex = this.queryForm.pageIndex < 1 ? 1 : this.queryForm.pageIndex

            this.fetchData()
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
        this.queryForm.pageIndex = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageIndex = 1
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      async fetchData() {
        this.listLoading = true
        this.queryForm.form_id = this.$route.query.id;
        const {data, totalCount} = await getList(this.queryForm)
        console.log(data);
        this.tableData = data.list;
        this.total = data.total;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
