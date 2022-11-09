<template>
  <div class="index-container">


    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.create_start_time"
                type="date"
                placeholder="创建时间(Start)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.create_end_time"
                type="date"
                placeholder="创建时间(End)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="queryForm.title"
                placeholder="标题"
                clearable
                @keyup.enter.native="queryData"
              />
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select clearable placeholder="变更类别" v-model.trim="queryForm.change_classify" class="w">
                <el-option
                  v-for="item in this.tableDataClassify"
                  :key="item.id"
                  :label="item.type_name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select
                v-model="queryForm.change_type"
                placeholder="变更类型"
                clearable
              >
                <el-option
                  v-for="item in this.getDictChangeTypes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select
                v-model="queryForm.change_source"
                placeholder="变更来源"
                clearable
              >
                <el-option
                  v-for="item in this.changeSourceData"
                  :key="item.id"
                  :label="item.source_name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select
                v-model="queryForm.status"
                placeholder="状态"

              >
                <el-option
                  v-for="item in this.getDictChangeStatus"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="cpa_approve_user_name"
                placeholder="方案审核人"
                clearable
                @click.native="handleCpaApprove"
              >
                <!--<el-button-->
                  <!--slot="append"-->
                  <!--icon="el-icon-search"-->
                  <!--@click="handleCpaApprove"-->
                <!--&gt;</el-button>-->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-input
                v-model.trim="cn_approve_user_name"
                placeholder="审批人"
                clearable
                @click.native="handleCnApprove"
              >
                <!--<el-button-->
                  <!--slot="append"-->
                  <!--icon="el-icon-search"-->
                  <!--@click="handleCnApprove"-->
                <!--&gt;</el-button>-->
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.start_time"
                type="date"
                placeholder="计划开始时间(Start)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.end_time"
                type="date"
                placeholder="计划结束时间(End)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.implement_start_time"
                type="date"
                placeholder="实施开始时间(Start)"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-date-picker
                v-model="queryForm.implement_end_time"
                type="date"
                placeholder="实施结束时间(End)"
              ></el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>
      </div>
      <vab-query-form>

        <vab-query-form-left-panel :span="8">
          <!--<el-button icon="el-icon-plus" type="primary" @click="handleAdd">-->
          <!--添加-->
          <!--</el-button>-->
          <!--<el-dropdown trigger="hover" @command="handleCommand">-->
          <!--<el-button>-->
          <!--更多-->
          <!--<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
          <!--</el-button>-->
          <!--<el-dropdown-menu slot="dropdown">-->
          <!--<el-dropdown-item icon="el-icon-edit" command="a">-->
          <!--导出-->
          <!--</el-dropdown-item>-->
          <!--<el-dropdown-item icon="el-icon-check" command="b">-->
          <!--添加备注-->
          <!--</el-dropdown-item>-->
          <!--<el-dropdown-item icon="el-icon-warning-outline" command="c">-->
          <!--修改关联-->
          <!--</el-dropdown-item>-->
          <!--&lt;!&ndash;<el-dropdown-item icon="el-icon-delete" command="d">&ndash;&gt;-->
          <!--&lt;!&ndash;删除&ndash;&gt;-->
          <!--&lt;!&ndash;</el-dropdown-item>&ndash;&gt;-->
          <!--&lt;!&ndash;<el-dropdown-item icon="el-icon-upload2" command="e">&ndash;&gt;-->
          <!--&lt;!&ndash;导入&ndash;&gt;-->
          <!--&lt;!&ndash;</el-dropdown-item>&ndash;&gt;-->
          <!--</el-dropdown-menu>-->
          <!--</el-dropdown>-->

        </vab-query-form-left-panel>

        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input
              v-model.trim="queryForm.keyword"
              placeholder="关键字"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
            <el-button
              icon="el-icon-arrow-down"
              @click="moreQuery"
              v-if="isShow"
            >
              高级
            </el-button>
            <el-button icon="el-icon-arrow-up" @click="moreQuery" v-else>
              高级
            </el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>

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
        width="50px;"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="change_no"
        sortable
        label="变更单号"
        align="center"
      >
        <template #default="{ row }">
          <el-link @click="details(row)">{{row.change_no}}</el-link>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="title"
        sortable
        label="标题"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="start_time"
        width="200px;"
        label="计划开始时间"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="classify_name"
        align="center"
        sortable
        label="变更类别"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="type_name"

        align="center"
        sortable
        label="变更类型"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="approve_name"

        align="center"
        sortable
        label="审批人"
      ></el-table-column>
      <!--<el-table-column-->
      <!--show-overflow-tooltip-->
      <!--prop="approve_time"-->

      <!--align="center"-->
      <!--sortable-->
      <!--label="审批时间"-->
      <!--&gt;</el-table-column>-->
      <el-table-column
        show-overflow-tooltip
        prop="create_time"

        align="center"
        sortable
        label="创建时间"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="status_name"

        align="center"
        sortable
        label="状态"
      ></el-table-column>
      <!--<el-table-column label="操作" width="220" align="center">-->
      <!--&lt;!&ndash;12 状态 1.待审批 2.审批拒绝 3.方案制定中 4.方案待审核 5.方案审核拒绝 6.变更实施中 7.实施完成 8.实施失败 9.评审完成 10.已完成 11.挂起 12.已撤销&ndash;&gt;-->
      <!--<template #default="scope">-->
      <!--<el-button icon="el-icon-edit" type="success" @click="handleEdit(scope.row)" v-if="row.status==1 || row.status==2">-->
      <!--修改-->
      <!--</el-button>-->
      <!--<el-button icon="el-icon-edit" type="success" @click="handleCancel(scope.row)" v-if="row.status==1 || row.status==2 || row.status==5">-->
      <!--撤销-->
      <!--</el-button>-->
      <!--<el-button icon="el-icon-edit" type="success" @click="handleEdit(scope.row)" v-if="row.status==8">-->
      <!--重开-->
      <!--</el-button>-->
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
    <remark ref="remark" @fetch-data="fetchData"></remark>
    <dialogcpa ref="dialogcpa"
               @userData="listDialogCpa"
               @fetch-data="
        {
          input2
        }
      "
    ></dialogcpa>
    <dialogcn ref="dialogcn"
              @userData="listDialogCn"
              @fetch-data="
        {
          input2
        }
      "
    ></dialogcn>
  </div>


</template>

<script>
  import {toList, getAllChangeType, getDict, getChangeSource, toCancel, exportExcel, deleteChange} from '@/api/change'
  import Remark from './components/remake'

  import Dialogcpa from '@/components/UserSelectModel'
  import Dialogcn from '@/components/UserSelectModel'

  export default {
    name: 'changeCanyu',
    components: {Dialogcpa, Dialogcn, Remark},
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
        tableDataClassify: [],
        getDictChangeTypes: [],
        getDictChangeStatus: [],
        changeSourceData: [],
        dict: {dict_type: 'change_type'},
        dict_status: {dict_type: 'change_status'},
        cpa_approve_user_name: '',// 方案审核人名
        cn_approve_user_name: '',// 审批人名
        queryForm: {
          data_flag: '3',//我参与的
          pageNo: 1,
          pageSize: 10,
          implement_start_time: '',//4	实施开始时间
          implement_end_time: '',//5	实施结束时间
          create_start_time: '',// 创建开始时间
          create_end_time: '', // 创建结束时间
          start_time: '', // 计划开始时间
          end_time: '', // 计划结束时间
          status: '',
          change_classify: '',
          change_type: '',
          title: '',
          change_source: '',
          cn_approve_user: '',// 审批人
          cpa_approve_user: '',// 方案审核人
        },
      }
    },
    mounted() {
      this.fetchData()
      this.allChangeType();
      // 变更类型
      this.getDictChangeType();
      // 变更状态
      this.getDictChangeStatuss();
      // 变更来源
      this.getChangeSource();
    },
    methods: {
      handleCommand(command) {
        switch (command) {
          case 'a':
            this.handleExport();
            break;
          case 'b':
            this.handleEditRemark();
            break;
          case 'c':
            this.handleEditRele();
            break;
          case 'd'://删除
            this.handleDelete();
            break;
          default:
            //这里是没有找到对应的值处理
            break
        }
      },
      handleCpaApprove() { // 方案审核人
        this.$refs['dialogcpa'].showWin()
      },
      handleCnApprove() { // 审批人
        this.$refs['dialogcn'].showWin()
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd() {
        this.$router.push({
          path: '/biangeng/addmechange',
        })
      },
      handleDelete() {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.id;
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await deleteChange({id: ids});
            this.$baseMessage(msg, 'success');
            this.fetchData();
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false;
        }
      },
      details(row) {
        if (row.id != '' && row.id != null) {
          this.$router.push({
            path: '/biangeng/detalisExamine',
            query: {id: row.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      listDialogCpa(row) {
        this.queryForm.cpa_approve_user = row.id;
        this.cpa_approve_user_name = row.name;
      },
      listDialogCn(row) {
        this.queryForm.cn_approve_user = row.id;
        this.cn_approve_user_name = row.name;
      },
      async allChangeType() {

        const {data} = await getAllChangeType()
        this.tableDataClassify = data
        console.log(this.tableDataClassify)
        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      async getChangeSource() {
        const {data} = await getChangeSource()

        this.changeSourceData = data

        console.log(this.changeSourceData)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getDictChangeStatuss() {
        const {data} = await getDict(this.dict_status);
        this.getDictChangeStatus = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getDictChangeType() {
        console.log(1111);
        console.log(this.dict);

        const {data} = await getDict(this.dict)
        this.getDictChangeTypes = data
        console.log(this.getDictChangeTypes)
        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      async handleExport() {

        this.$baseConfirm('确定要导出所有记录吗？', null, async () => {

          exportExcel(this.queryForm).then((res) => {
            const url = URL.createObjectURL(new Blob([res]))
            const link = document.createElement('a')
            link.href = url
            link.setAttribute('download', '我的变更.xls')
            document.body.appendChild(link)
            link.click()
          })
        })


      },
      handleQuest() {

      },
      handleEdit(row) {

        this.$router.push({
          path: '/biangeng/editmechange',
          query: {id: row.id},
        })
      },
      handleEditRele() {//修改关联

        if (this.selectRows != '' && this.selectRows != null) {
          this.$router.push({
            path: '/biangeng/editchangeRelevance',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      handleEditRemark() {//添加备注

        if (this.selectRows != '' && this.selectRows != null) {
          this.$refs['remark'].showEdit(this.selectRows)
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }

      },
      async handleCancel(row) {

        var changeInfo = {id: row.id, status: '12'};
        const {msg} = await toCancel(changeInfo);
        this.$baseMessage(msg, 'success')
        this.queryData();
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
        const {data} = await toList(this.queryForm)
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
    padding: 20px !important;
    background: #fff !important;

    .hiddenmoretop {
      ::v-deep {
        .el-form-item {
          margin-bottom: 15px;
        }
        .el-date-editor.el-input {
          width: 100%;
        }
        .el-date-editor.el-input--prefix .el-input__inner{
          padding-right: 15px;
        }

      }
    }
  }
</style>
