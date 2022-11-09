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
        <!--<el-button icon="el-icon-view" type="success" @click="handleView">-->
        <!--查看任务执行详情-->
        <!--</el-button>-->
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="16">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <div class="hiddenmore" v-if="isShow">

            <el-form-item>
              <el-select v-model.trim="queryForm.schedule_type" placeholder="类型" clearable>
                <el-option label="每工作日" value="1"></el-option>
                <el-option label="每天" value="2"></el-option>
                <el-option label="每周" value="3"></el-option>
                <el-option label="每月" value="4"></el-option>
                <el-option label="一次性" value="5"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-input v-model.trim="queryForm.remark" placeholder="备注" clearable/>
            </el-form-item>
            <el-form-item>
              <el-select v-model="queryForm.status" placeholder="状态" clearable>
                <el-option label="运行" value="0"></el-option>
                <el-option label="停止" value="1"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-input
                v-model.trim="queryForm.create_user_name"
                placeholder="创建人"
                clearable
                @clear="queryForm.create_user=''"
                @click.native="handleCreateUser"
              >
                <!--<el-button-->
                  <!--slot="append"-->
                  <!--icon="el-icon-search"-->
                  <!--@click="handleCreateUser"-->
                <!--&gt;</el-button>-->
              </el-input>
            </el-form-item>
            <!--<el-form-item>-->
            <!--<el-input-->
            <!--v-model.trim="queryForm.event_id_name"-->
            <!--placeholder="工单模板"-->
            <!--clearable-->
            <!--@clear="queryForm.event_id=''"-->
            <!--@click.native="onEventTemplateData"-->
            <!--&gt;-->
            <!--<el-button-->
            <!--slot="append"-->
            <!--icon="el-icon-search"-->
            <!--@click="onEventTemplateData"-->
            <!--&gt;</el-button>-->
            <!--</el-input>-->
            <!--</el-form-item>-->
          </div>

          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
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
        prop="remark"
        width="500px;"
        sortable
        label="备注"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="status"
        label="状态"
        width="80px;"
        align="center"
      >
        <template slot-scope="scope">
          {{ scope.row.status == 0 ? '运行' : '停止' }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_time"
        label="创建时间"
        width="200px;"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="lastTime"
        label="上次执行时间"
        width="200px;"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="create_user_name"
        label="创建人"
        width="200px;"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="event_id_name"
        label="工单模板"
        width="200px;"
        align="center"
      ></el-table-column>
      <el-table-column show-overflow-tooltip label="操作" align="center">
        <template #default="{ row }">
          <el-button type="success" icon="el-icon-video-play" plain size="mini" @click="play(row)"
                     v-if="row.status == 1">开始
          </el-button>
          <el-button type="danger" icon="el-icon-video-pause" plain size="mini" @click="pause(row)"
                     v-if="row.status == 0">
            停止
          </el-button>
          <el-button type="warning" icon="el-icon-refresh" plain size="mini" @click="refresh(row)">重启</el-button>
          <el-button type="primary" icon="el-icon-document" plain size="mini" @click="handlelog(row)">日志</el-button>
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

    <log ref="log" @fetch-data=""></log>
    <users ref="users" @userData="getUser"></users>
    <eventTemplateDia ref="eventTemplateDia" @listData="getEventTemplateData"></eventTemplateDia>

  </div>
</template>

<script>
  import {getList, del, start, pause, restart} from '@/api/eventSchedule'

  import log from './components/log'
  import Users from '@/components/UserSelectModel'
  import eventTemplateDia from '@/components/eventTemplateDia'

  export default {
    name: 'scheduledTask',
    components: {
      log,
      Users,
      eventTemplateDia,
    },
    data() {
      return {
        input2: '',
        checked: false,
        isShow: true,
        list: null,
        options: [],
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          schedule_type: '',
          event_id: '',
          event_id_name: '',
          create_user: '',
          create_user_name: '',
          remark: '',
          status: '',
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
          path: '/set/taskAdd',
        })
      },
      handleEdit() {
        if (this.selectRows.id) {
          this.$router.push({
            path: '/set/taskEdit',
            query: {id: this.selectRows.id},
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleDelete() {
        if (this.selectRows.id) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await del(this.selectRows.id);
            this.$baseMessage(msg, 'success');
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handlelog(row) {
        this.$refs['log'].showView(row.id)
      },
      handleCreateUser() {
        this.$refs['users'].showWin()
      },
      onEventTemplateData() {
        this.$refs['eventTemplateDia'].showWin()
      },
      getUser(row) {
        this.queryForm.create_user = row.id;
        this.queryForm.create_user_name = row.name;
      },
      getEventTemplateData(row) {
        this.queryForm.event_id = row.id;
        this.queryForm.event_id_name = row.template_name;
      },
      play(row) {
        this.$baseConfirm('你确定要开启吗', null, async () => {
          const {msg} = await start(row.id);
          this.$baseMessage(msg, 'success');
          this.fetchData()
        });
        // this.$baseMessage('已开启！', 'success');
      },
      pause(row) {
        this.$baseConfirm('你确定要停止吗', null, async () => {
          const {msg} = await pause(row.id);
          this.$baseMessage(msg, 'success');
          this.fetchData()
        });
        // this.$baseMessage('已停止！', 'success');
      },
      refresh(row) {
        this.$baseConfirm('你确定要重启吗', null, async () => {
          const {msg} = await restart(row.id);
          this.$baseMessage(msg, 'success');
          this.fetchData()
        });
        // this.$baseMessage('已重启！', 'success');
      },
      handleUpload() {
        const ids = this.selectRows.id;
        this.$baseConfirm('你确定要导出这些数据吗', null, async () => {
          this.$baseMessage('导出数据成功', 'success');
          this.fetchData();
        })
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val;
        this.fetchData();
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val;
        this.fetchData();
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData();
      },

      async fetchData() {
        var that = this;
        getList(that.queryForm).then((res) => {
          that.list = res.data.list;
          that.total = res.data.total;
        });
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
