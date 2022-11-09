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
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="部门" prop="department_name">
                      <el-input autocomplete="off" v-model.trim="form.department_name" clearable placeholder="请选择"
                                style="width: 80%;"
                                @click.native="openwinOne"></el-input>
                      <el-button icon="el-icon-search"
                                 style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                                 @click="openwinOne"></el-button>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="类型" prop="status">
                      <el-select v-model.trim="form.type" class="w">
                        <el-option v-for="item in this.assetsRequestConfig" :key="item.id" :label="item.request_name"
                                   :value="item.id"></el-option>

                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="创建人">
                      {{this.form.create_name}}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="创建时间">
                      {{this.form.create_time}}
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
                    <el-form-item label="处理人">
                      {{ this.form.deal_user_name }}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="处理时间">
                      {{ this.form.deal_time }}
                    </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="需变更内容" prop="status">
                      <el-input v-model.trim="form.content" autocomplete="off" placeholder='请输入'
                                type="textarea" :rows="3"></el-input>
                    </el-form-item>
                  </el-col>

<!--                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                    <el-form-item label="处理结果">-->
<!--                      <el-input v-model.trim="form.result" autocomplete="off" placeholder='请输入'-->
<!--                                type="textarea" :rows="3"></el-input>-->
<!--                    </el-form-item>-->
<!--                  </el-col>-->
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
                    <el-form-item label="关联的变更" prop="relevance_NO">
                      <el-input v-model.trim="form.relevance_name" autocomplete="off" placeholder="请选择"
                                style="width: 80%;"
                                clearable @clear="form.relevance_NO=''"
                                @click.native="openwinChange"></el-input>
                      <el-button icon="el-icon-search"
                                 style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"
                                 @click="openwinChange"></el-button>
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
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteSet">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAddPz">
                添加
              </el-button>
            </div>
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
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteEvent">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAddGd">
                添加
              </el-button>
            </div>
          </el-tab-pane>

        </el-tabs>
      </template>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

    <dialogone ref="dialogone" @deptData="deptset" @fetch-data=""></dialogone>
    <dialogpz ref="dialogpz" @listData="listAssetsManages" @fetch-data=""></dialogpz>
    <dialoggd ref="dialoggd" @listData="listEventsManages" @fetch-data=""></dialoggd>
    <dialogchange ref="dialogchange" @listData="changeset" @fetch-data=""></dialogchange>

  </div>

</template>

<script>
  import {toList, getAllAssetsRequestConfig, getAssetsConfigReq, toEdit} from '@/api/assetsconfigreq'

  import dialogone from '@/components/bumenTree'
  import dialogpz from '@/components/assetsTypeDia'
  import dialoggd from '@/components/eventDialog'

  import dialogchange from '@/components/changeRadioWithFinish'

  export default {
    name: 'assetsConfigReqEdit',
    components: {
      dialogone,
      dialogpz,
      dialoggd,
      dialogchange
    },
    data() {
      return {
        eventsName: '关联的工单',
        assetsName: '关联的配置',
        activeName: 'first',
        selectRowsSet: '',
        tableDataSet: [],
        selectRowsEvent: '',
        tableDataEvent: [],
        assetsRequestConfig: [],
        options: [],
        form: {
          type: '',
          dw_id: '',
          content: '',
          department_name: '',
          result: '',
          relevance_NO: '',
          relevance_name: '',
          status: '1',
          assetsConfigReqProperties: [],
          assetsConfigReqOrders: [],
        },
        queryForm: {id: this.$route.query.id},
        rules: {
          department_name: [{
            required: true,
            trigger: 'submit',
            message: '请选择部门'
          },],
          type: [{
            required: true,
            trigger: 'blur',
            message: '请选择类型'
          }],
          content: [{
            required: true,
            trigger: 'blur',
            message: '请填写需变更内容'
          },],
        },

      }
    },
    created() {
      this.getAllAssetsRequestConfig();
      this.fetchData();
    },
    methods: {
      openwinOne() {
        this.$refs['dialogone'].showWin()
      },
      openwinChange() {
        this.$refs['dialogchange'].showWin()
      },
      handleAddPz() {
        this.$refs['dialogpz'].showWin(this.tableDataSet)
      },
      handleAddGd() {
        this.$refs['dialoggd'].showWin()
      },
      async getAllAssetsRequestConfig() {

        const {data} = await getAllAssetsRequestConfig()
        this.assetsRequestConfig = data
        console.log("------------------------- assetsRequestConfig -------------------------")
        console.log(this.assetsRequestConfig)
        setTimeout(() => {
          this.listLoading = false
        }, 300)

      },
      deptset(data) {
        this.form.dw_id = data.id;
        this.form.department_name = data.name;
      },
      changeset(data) {
        this.form.relevance_NO = data.id;
        this.form.relevance_name = data.change_no;
        console.log(data);
      },
      // 配置
      listAssetsManages(row) {
        row.forEach((item, i) => {
          let index = this.tableDataSet.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.tableDataSet.push(item)
          }
        })
        this.assetsName = "关联的配置(" + this.tableDataSet.length + ")";
      },
      handleDeleteEvent() {
        console.log(this.selectRowsEvent);
        if (this.selectRowsEvent.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsEvent.forEach((item, i) => {
              this.tableDataEvent.some((x, i) => {
                if (x.id == item.id) {
                  this.tableDataEvent.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.eventsName = "关联的工单(" + this.tableDataEvent.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //事件
      listEventsManages(row) {
        row.forEach((item, i) => {
          let index = this.tableDataEvent.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.tableDataEvent.push(item)
          }
        })
        this.eventsName = "关联的工单(" + this.tableDataEvent.length + ")";
      },
      handleDeleteSet() {
        console.log(this.selectRowsSet);
        if (this.selectRowsSet.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsSet.forEach((item, i) => {
              this.tableDataSet.some((x, i) => {
                if (x.id == item.id) {
                  this.tableDataSet.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.assetsName = "关联的配置(" + this.tableDataSet.length + ")";
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
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
      setSelectRowsSet(val) {
        this.selectRowsSet = val
      },
      setSelectRowsEvent(val) {
        this.selectRowsEvent = val
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
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      async saveok() {
        // this.form.assetsConfigReqProperties = [];
        // // 关联的事件
        // this.tableDataEvent.some((x, i) => {
        //   this.form.assetsConfigReqProperties.push({property_id: x.id, is_del: '0'});
        // })
        // this.form.assetsConfigReqOrders = [];
        // // 关联的配置
        // this.tableDataSet.some((x, i) => {
        //   this.form.assetsConfigReqOrders.push({order_id: x.id, is_del: '0'});
        // })

        this.form.events = this.tableDataEvent;
        this.form.assetsManages = this.tableDataSet;

        const {msg} = await toEdit(this.form)
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
        console.log("-------------- fetchData ----------------------");
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
