<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="24">
        <el-button icon="el-icon-plus" type="primary" @click="handleAdd">
          添加
        </el-button>
        <el-button icon="el-icon-edit" type="success" @click="handleEdit">
          修改
        </el-button>
        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">
          删除
        </el-button>
        <el-button icon="el-icon-refresh-left" @click="handleRefresh">
          刷新
        </el-button>
      </vab-query-form-left-panel>
    </vab-query-form>

    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="tableData"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      row-key="id"
      @current-change="handleCurrentChange"
    >
      <el-table-column
        v-for="(item, index) in col"
        :key="`col_${index}`"
        :prop="dropCol[index].prop"
        :label="item.label"
        :align="item.align"
        :width="item.width"
      ></el-table-column>
      <pre style="text-align: left">
      {{ dropCol }}
    </pre
    >
      <hr/>
      <pre style="text-align: left">
      {{ tableData }}
    </pre
    >
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
  import {getList, doDelete} from '@/api/userManagement'

  import Sortable from 'sortablejs'

  export default {
    name: 'emailaccountmanagementrule',
    data() {
      return {
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        col: [
          {
            label: '收件规则',
            prop: 'name',
            align: 'left',
            width: 'auto',
          },
          {
            label: '状态',
            prop: 'date',
            align: 'center',
            width: '150px',
          },
        ],
        dropCol: [
          {
            label: '收件规则',
            prop: 'name',
            align: 'left',
            width: 'auto',
          },
          {
            label: '状态',
            prop: 'date',
            align: 'center',
            width: '150px',
          },
        ],
        tableData: [
          {
            id: '1',
            date: '使用',
            name: '开通邮箱',
          },
          {
            id: '2',
            date: '使用',
            name: '忽略退信',
          },
          {
            id: '3',
            date: '禁用',
            name: '开通邮箱2',
          },
          {
            id: '4',
            date: '禁用',
            name: '开通邮箱3',
          },
        ],
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
        this.$router.push({
          path: '/set/emailrulesadd',
        })
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
          this.$router.push({
            path: '/set/emailrulesedit',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({ids})
            this.$baseMessage(msg, 'success')
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
        const {data, totalCount} = await getList(this.queryForm)
        this.list = data
        this.total = totalCount
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
