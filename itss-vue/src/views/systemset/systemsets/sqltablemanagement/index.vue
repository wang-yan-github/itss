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
        <!-- <el-button icon="el-icon-setting" @click="handleSets">配置项</el-button> -->
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.sql_name"
                placeholder="名称"
                clearable
                @keyup.enter.native="queryData"
              />
            </el-form-item>
            <el-form-item>
              <el-input
                v-model.trim="queryForm.sql_content"
                placeholder="SQL"
                clearable
                @keyup.enter.native="queryData"
              />
            </el-form-item>
            <el-form-item>
              <el-select v-model.trim="queryForm.is_use" placeholder="状态">
                <el-option value="">请选择</el-option>
                <el-option
                  v-for="item in sqlIsUse"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-select v-model.trim="queryForm.display_mode" placeholder="显示方式">
                <el-option value="">请选择</el-option>
                <el-option
                  v-for="item in sqlDisplayMode"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model.trim="queryForm.time_choose" placeholder="默认时间类型">
                <el-option value="">请选择</el-option>
                <el-option
                  v-for="item in sqlTimechoose"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model.trim="queryForm.scope" placeholder="可见范围">
                <el-option value="">请选择</el-option>
                <el-option
                  v-for="item in sqlScope"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select v-model.trim="queryForm.module" placeholder="所属模块">
                <el-option value="">请选择</el-option>
                <el-option
                  v-for="item in sqlModule"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </div>
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
        label="序号"
        align="center"
        width="200px;"
        sortable
      >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>

      </el-table-column>
      <el-table-column show-overflow-tooltip sortable label="名称" align="center">
        <template #default="{ row }">
          <el-link type="primary" @click="handleView(row)">
            {{ row.sql_name }}
            <i class="el-icon-view el-icon--right"></i>
          </el-link>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_use_name"
        width="100px;"
        label="状态"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="display_mode"
        width="200px;"
        align="center"
        sortable
        label="显示方式"
      ></el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="time_choose"
        width="200px;"
        label="默认时间类型"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="scope"
        width="150px;"
        label="可见范围"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="module"
        width="250px;"
        label="所属模块"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        width="200px;"
        label="操作"
        align="center"
      >
        <template #default="{ row }">
          <el-button
            type="danger"
            plain
            size="mini"
            @click="handleRecord(row)"
            v-if="row.is_use==1"
          >
            停用
          </el-button>
          <el-button
            type="primary"
            plain
            size="mini"
            @click="handleRecord(row)"
            v-else
          >
            使用
          </el-button>
          <!-- <el-button
            type="primary"
            plain
            size="mini"
            @click="handleExport(row)"
          >
            导出
          </el-button> -->
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
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
    <sets ref="sets" @fetch-data="fetchData"></sets>
  </div>
</template>

<script>
  import {sqlPageListData, deleteSysSql, stateUpd} from '@/api/userManagement'
  import Edit from './components/Edit'
  import Views from './components/View'
  import Sets from './components/Sets'

  export default {
    name: 'sqltablemanagement',
    components: {Edit, Views, Sets},
    data() {
      return {
        checked: false,
        isShow: false,
        list: [],
        listLoading: true,
        isqy: true,
        isqytext: '禁用',
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        sqlModule: '', //所属模块
        sqlIsUse: '', //报表状态
        sqlTimechoose: '', //时间类型
        sqlScope: '', //可见范围
        sqlDisplayMode: '', //显示方式
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          sql_name: '',
          sql_content: '',
          is_use: '',
          display_mode: '',
          time_choose: '',
          scope: '',
          module: '',
        },
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      //新增框
      handleAdd() {
        this.$refs['edit'].showEdit()
      },
      //配置项
      handleSets() {
        this.$refs['sets'].showEdit()
      },
      //详情框
      handleView(row) {
        if (row.id) {
          this.$refs['views'].showView(row)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleRecord(row) {
        if (row.id) {
          var queryForm = {
            id: row.id,
            is_use: row.is_use
          };
          stateUpd(queryForm).then((res) => {
            if (res.code == 0) {
              this.$baseMessage(res.msg, 'success')
              this.queryData()
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
          this.fetchData()
          // this.$baseMessage('操作成功', 'success')
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleExport(row) {
        if (row.id) {
          const ids = row.id
          this.$baseConfirm('你确定要导出此条记录吗', null, async () => {
            this.$baseMessage('导出成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleEdit() {
        if (this.selectRows.id) {
          this.$refs['edit'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleCurrentChange(val) {
        this.selectRows = val
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
      //获取数据信息
      async fetchData() {
        this.listLoading = true
        const {data} = await sqlPageListData(this.queryForm)
        var page = data.page;
        this.list = page.list
        this.total = page.total
        this.sqlModule = data.sqlModule
        this.sqlIsUse = data.sqlIsUse
        this.sqlTimechoose = data.sqlTimechoose
        this.sqlScope = data.sqlScope
        this.sqlDisplayMode = data.sqlDisplayMode
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      //删除功能
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            deleteSysSql(this.selectRows.id).then((res) => {
              if (res.code == 0) {
                if (res.data) {
                  this.$baseMessage(res.msg, 'success')
                  // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
                  const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
                  this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
                  this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo

                  this.fetchData()
                } else {
                  this.$baseMessage(res.msg, 'error')
                }
              }
            })
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
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
