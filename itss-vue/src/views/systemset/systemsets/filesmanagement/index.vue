<template>
  <div class="index-container">
    <vab-query-form>

      <vab-query-form-right-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.start_time"
                type="date"
                placeholder="上传日期（Start）"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="queryForm.end_time"
                type="date"
                placeholder="上传日期（End）"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-input
                v-model.trim="queryForm.file_type"
                placeholder="拓展名"
                clearable
                @keyup.enter.native="queryData"
              />
            </el-form-item>
            <!--<el-form-item>-->
            <!--<el-select v-model="queryForm.username" placeholder="来源">-->
            <!--<el-option label="使用中" value="shanghai"></el-option>-->
            <!--<el-option label="未使用" value="beijing"></el-option>-->
            <!--</el-select>-->
            <!--</el-form-item>-->
            <!--<el-form-item>-->
            <!--<el-select v-model="queryForm.username" placeholder="文件夹">-->
            <!--<el-option label="使用中" value="shanghai"></el-option>-->
            <!--<el-option label="未使用" value="beijing"></el-option>-->
            <!--</el-select>-->
            <!--</el-form-item>-->
            <!--<el-form-item>-->
            <!--<el-input-->
            <!--v-model.trim="queryForm.username"-->
            <!--placeholder="ManageKey"-->
            <!--clearable-->
            <!--/>-->
            <!--</el-form-item>-->
            <!--<el-form-item>-->
            <!--<el-select v-model="queryForm.username" placeholder="全部" clearable>-->
            <!--<el-option label="使用中" value="shanghai"></el-option>-->
            <!--<el-option label="未使用" value="beijing"></el-option>-->
            <!--</el-select>-->
            <!--</el-form-item>-->
          </div>
          <el-form-item>
            <el-input
              v-model.trim="queryForm.file_name"
              placeholder="文件名"
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
        label="序号"
        align="center"
        width="200px;"
        sortable
      >
        <template slot-scope="scope">
          {{(queryForm.pageNo-1) * queryForm.pageSize+scope.$index+1}}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="file_name"
        sortable
        label="文件名"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="file_type"
        width="100px;"
        label="扩展名"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        width="200px;"
        align="center"
        sortable
        label="上传日期"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="file_size"
        width="120px;"
        align="center"
        sortable
        label="文件大小"
      ></el-table-column>
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="truename"-->
      <!--width="200px;"-->
      <!--align="center"-->
      <!--sortable-->
      <!--label="来源"-->
      <!--&gt;</el-table-column>-->
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="truename"-->
      <!--width="100px;"-->
      <!--align="center"-->
      <!--sortable-->
      <!--label="文件夹"-->
      <!--&gt;</el-table-column>-->
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        align="center"
        sortable
        width="100px;"
        label="上传人"
      ></el-table-column>
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="truename"      -->
      <!--align="center"-->
      <!--sortable        -->
      <!--label="ManageKey"-->
      <!--&gt;</el-table-column>-->
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="truename"      -->
      <!--align="center"-->
      <!--sortable-->
      <!--width="120px;" -->
      <!--label="关联单号"-->
      <!--&gt;-->
      <!--<template #default="{row}">-->
      <!---->
      <!--<el-button type="primary" plain size="mini" @click="handleView(row)">查看</el-button>-->
      <!--</template> -->
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

  </div>
</template>

<script>
  import {toList} from '@/api/file'

  export default {
    name: 'filesmanagement',
    components: {},
    data() {
      return {
        // value1:'',
        // value2:'',
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
          file_type: '',
          file_name: '',
          start_time: '',
          end_time: '',
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
      handleView(row) {
        if (row.id) {
          this.$router.push({
            path: '/set/filesviewmanagement',
            query: {id: row.id}
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
      // handleDelete() {
      //   if (this.selectRows != '' && this.selectRows != null) {
      //     const ids = this.selectRows.id
      //     this.$baseConfirm('你确定要删除选中项吗', null, async () => {
      //       const { msg } = await doDelete({ ids })
      //       this.$baseMessage(msg, 'success')
      //       this.fetchData()
      //     })
      //   } else {
      //     this.$baseMessage('未选中任何行', 'error')
      //     return false
      //   }
      // },
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
        console.log(this.queryForm);
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
          width: 195px;
        }
      }
    }
  }
</style>
