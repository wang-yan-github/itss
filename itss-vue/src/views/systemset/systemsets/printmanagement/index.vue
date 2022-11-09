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
        <el-button icon="el-icon-view" @click="handleView">
          查看
        </el-button>

      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-input
                placeholder="创建人"
                v-model="queryForm.create_user_name"
                @focus="openwin"
                clearable
              >
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.is_use" placeholder="状态">
                <el-option>全部</el-option>
                <el-option label="停用" value="0"></el-option>
                <el-option label="使用" value="1"></el-option>
              </el-select>
            </el-form-item>

<!--            <el-form-item>-->
<!--              <el-input-->
<!--                v-model.trim="queryForm.content"-->
<!--                placeholder="模板内容"-->
<!--                clearable-->
<!--                @keyup.enter.native="queryData"-->
<!--              />-->
<!--            </el-form-item>-->
          </div>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.print_name"
              placeholder="模板名称"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
            <el-button
              icon="el-icon-d-arrow-left"
              @click="moreQuery"
              v-if="isShow"
            >
              高级
            </el-button>
            <el-button icon="el-icon-d-arrow-right" @click="moreQuery" v-else>
              高级
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
        type="index" label="序号"
        align="center"
        width="200px;"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="print_name"
        sortable
        label="模板名称"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        width="200px;"
        label="创建人"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        width="200px;"
        align="center"
        sortable
        label="创建时间"
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
        prop="module"
        width="200px;"
        align="center"
        sortable
        label="模块"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="order_num"
        width="200px;"
        align="center"
        sortable
        label="排序"
      ></el-table-column>
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
    <views ref="views" @fetch-data="fetchData"></views>
    <sets ref="sets" @userData="deptset" @fetch-data="fetchData"></sets>
  </div>
</template>

<script>
  import {toList, deleteById, getById} from '@/api/sysPrint'
  import Edit from './components/Edit'
  import Views from './components/View'
  import Sets from '@/components/UserSelectModel'

  export default {
    name: 'printmanagement',
    components: {Edit, Views, Sets},
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
          pageIndex: 1,
          pageSize: 10,
          is_use: '',
          content: '',
          print_name: '',
          create_user: '',
          create_user_name: ''
        },
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      deptset(data) {
        console.log(data);
        this.queryForm.create_user = data.id;
        this.queryForm.create_user_name = data.name;
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      openwin() {
        this.$refs['sets'].showWin()
      },
      async handleView() {
        if (this.selectRows.id) {
          const id = this.selectRows.id
          const {data} = await getById({id})
          this.$refs['views'].showView(data);
          // this.$router.push({
          //   path:  this.$refs['views'].showView(),
          //   query: { id : data.id ,print_name: data.print_name ,is_use : data.is_use ,module : data.module ,order_num : data.order_num,content : data.content},
          // })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      handleEdit() {
        if (this.selectRows.id) {

          this.$refs['edit'].showEdit(this.selectRows)
          // this.$router.push({
          //   path:  this.$refs['edit'].showEdit(this.selectRows),
          //   query: { id: this.selectRows.id },
          // })

        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const id = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await deleteById({id})
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
        this.queryForm.pageIndex = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageIndex = 1
        if(''==this.queryForm.create_user_name||null==this.queryForm.create_user_name||undefined==this.queryForm.create_user_name){
          this.queryForm.create_user='';
        }

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
          width: 200px;
        }
      }
    }
  }
</style>
