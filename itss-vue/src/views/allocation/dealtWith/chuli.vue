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
                    <el-form-item label="处理人:">
                      <el-select v-model="form.deal_user_name" filterable remote reserve-keyword
                                 placeholder="请输入" :loading="loading" :remote-method="remoteMethod"
                                 style="width: 80%">
                        <template #prefix>
                            <span style="padding-left: 2px">
                              <i class="el-icon-search"></i>
                            </span>
                        </template>
                         <el-option>请选择</el-option>
                        <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name"
                                   :value="item.id"></el-option>
                      </el-select>
                      <el-button icon="el-icon-search"
                                 style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                                 @click="openwinTable"></el-button>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="处理结果" prop="result">
                      <el-input v-model.trim="form.result" autocomplete="off" placeholder='请输入'
                                type="textarea" :rows="3"></el-input>
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
      <el-button type="primary" size="medium" @click="saveok(1)">保存</el-button>
      <el-button type="success" size="medium" @click="saveok(2)">完成</el-button>
      <el-button type="warning" size="medium" @click="saveok(4)">拒绝</el-button>
    </div>

    <!--用户-->
    <users ref="users" @userData="getUser"></users>

  </div>

</template>

<script>
  import {toList, getAllAssetsRequestConfig, getAssetsConfigReq, toResult} from '@/api/assetsconfigreq'
  import {upUserData} from '@/api/question'

  //引用处理人弹窗页面
  import Users from '@/components/UserSelectModel'

  export default {
    name: 'dealtWithChuli',
    components: {
      Users,
    },
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
      this.upUserData();
    },
    methods: {
      async upUserData() {
        var that = this;
        const resp = await upUserData();
        console.log("upUserData")
        console.log(resp)
        that.auto_engineer_data = resp.data;
      },
      remoteMethod(query) {
        if (query !== '') {
          this.loading = true
          setTimeout(() => {
            this.loading = false
            this.options = this.list.filter((item) => {
              return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
            })
          }, 200)
        } else {
          this.options = []
        }
      },
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
      handleDelete() {
        if (this.selectRows != '') {
          const ids = this.selectRows.id
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {
              msg
            } = await doDelete({
              ids
            })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      async saveok(status) {
        this.form.status = status;
        if (this.form.result == null || this.form.result == undefined || this.form.result == ""){
          this.$baseMessage("请输入处理结果", 'error')
          return;
        }
        const {msg} = await toResult(this.form)

        this.$baseMessage(msg, 'success')

        this.closeall()

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
