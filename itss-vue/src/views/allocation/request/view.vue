<template>
  <div class="index-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="140px">
      <template>
        <el-tabs v-model="activeName">
          <el-tab-pane label="属性" name="first">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											基础信息
										</span>
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="请求编号">
                      {{this.form.req_NO}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="状态">
                      {{this.form.label}}
                    </el-form-item>
                  </el-col>

                </el-card>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											处理信息
										</span>
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="部门">
                      {{this.form.department_name}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="类型">
                      {{this.form.request_name}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="创建人">
                      {{this.form.create_name}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="创建时间" prop="data1">
                      {{this.form.create_time}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="需变更内容">
                      {{this.form.content}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="处理人" prop="status">
                      {{this.form.deal_user_name}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="处理结果" prop="result">
                      {{form.result}}
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											变更信息
										</span>
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="变更单号">
                      {{form.relevance_name}}
                    </el-form-item>
                  </el-col>


                </el-card>
              </el-col>


            </el-row>
          </el-tab-pane>


          <el-tab-pane :label="assetsName" name="second">
            <el-table ref="listTable" border stripe :data="tableDataSet"
                      highlight-current-row @selection-change="setSelectRowsSet">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>

              <el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型"></el-table-column>
              <el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="deptName" label="部门" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="receiverName" label="领用人" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" sortable
                               align="center">
                <template #default="scope">
                  <el-tag
                    size="mini"
                    icon="el-icon-edit"
                    v-if="scope.row.is_secrecy == '0'"
                    type="primary"
                  >
                    否
                  </el-tag>
                  <el-tag
                    size="mini"
                    icon="el-icon-delete"
                    class="red"
                    v-if="scope.row.is_secrecy == '1'"
                    type="success"
                  >
                    是
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column show-overflow-tooltip prop="assetsStatusName" label="资产状态" sortable
                               align="center">
                <template #default="scope">
                  <el-tag size="mini" icon="el-icon-delete" class="red" type="success">
                    {{ scope.row.assetsStatusName }}
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column show-overflow-tooltip prop="itStatusName" label="IT状态" sortable
                               align="center"></el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane :label="eventsName" name="three">
            <el-table ref="listTable" border stripe :data="tableDataEvent"
                      highlight-current-row @selection-change="setSelectRowsEvent">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
                <!--<template #default="{ row }">-->
                <!--<el-link @click="handlesee(row)">{{row.title}}</el-link>-->
                <!--</template>-->
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="user_id_name" sortable label="用户"
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"
                               sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center"
                               sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center"
                               sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="service_name" label="服务群组"
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center"
                               sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间"
                               sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态"
                               sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度"
                               sortable>
                <template #default="{ row }">
                  <el-rate v-model="row.event_satisfaction" disabled>
                  </el-rate>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </template>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="closeall()">关闭</el-button>
    </div>

  </div>

</template>

<script>
  import {getAssetsConfigReq} from '@/api/assetsconfigreq'

  export default {
    name: 'assetsconfigreqView',
    components: {},
    data() {
      return {
        eventsName: '关联的工单',
        assetsName: '关联的配置',
        elementLoadingText: '正在加载...',
        activeName: 'first',
        options: [],
        selectRowsSet: '',
        tableDataSet: [],
        selectRowsEvent: '',
        tableDataEvent: [],
        loading: false,
        auto_engineer_data: [],
        form: {
          id: '',
          result: '',
          deal_userId: '',
        },
        queryForm: {id: this.$route.query.id},
        rules: {
          result: [{
            required: true,
            trigger: 'blur',
            message: '请输入处理结果'
          },],
        },

      }
    },
    created() {
      this.fetchData();
    },
    methods: {
      setSelectRowsSet(val) {
        this.selectRowsSet = val
      },
      setSelectRowsEvent(val) {
        this.selectRowsEvent = val
      },
      openwinTable() {
        this.$refs['users'].showWin();
      },
      //处理人选择，数据渲染
      getUser(row) {
        this.form.deal_userId = row.id;
        this.form.deal_user_name = row.name;
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },

      async fetchData() {
        this.listLoading = true
        console.log(this.queryForm)
        const {data} = await getAssetsConfigReq(this.queryForm)
        this.form = data;
        this.eventsName = "关联的工单(" + data.events.length + ")";
        this.assetsName = "关联的配置(" + data.assetsManages.length + ")";
        //   this.tableDataQuest = data.questions;
        this.tableDataEvent = data.events;
        this.tableDataSet = data.assetsManages;
        console.log(data);
        // this.form.changeInfo=data.changeInfo;
        // this.form.changeProgrammeDraft=data.changeProgrammeDraft;
        // this.form.changeNodes=data.changeNodes;
        // this.form.changeProgrammeApprove=data.changeProgrammeApprove;
        // this.form.changeImplement=data.changeImplement;
        // this.form.changeImplementApprove=data.changeImplementApprove;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },


    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .tsitemd {
      label {
        width: 260 !important;
        text-align: left;
      }

      .el-form-item__content {
        margin-left: 0 !important;
      }
    }

    .upload-button {
      .el-upload {
        display: inline-block;
        margin-right: 10px;
      }
    }
  }

  .bghui {
    margin-bottom: 20px;
    background: #f5f5f5;
    padding: 15px;
    display: flex;
    flex-direction: row;
    border: 0px solid #ddd;
    width: 300px;
    align-items: center;
  }

  .bghui .r {
    display: inline-block;
    margin-left: 10px;
    line-height: 25px;
  }
</style>
