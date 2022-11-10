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
                    <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="名称" prop="name">
                      <el-input
                        v-model.trim="form.name"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="状态" prop="is_use">
                      <el-select
                        v-model="form.is_use"
                        placeholder="请选择"
                        style="width: 100%"
                      >
                        <el-option label="启用" value="1"></el-option>
                        <el-option label="禁用" value="0"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="排序">
                      <el-input
                        v-model.trim="form.sort"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="上级">
                      <el-input
                        autocomplete="off"
                        placeholder="请选择"
                        style="width: 80%"
                        v-model="form.parent_id"
                        @click.native="openwinSuperior"
                      ></el-input>
                      <!--<el-button icon="el-icon-search"-->
                      <!--style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
                      <!--@click="openwinSuperior"></el-button>-->
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="客户合同" prop="contractName">
                      <el-input
                        v-model.trim="form.contractName"
                        clearable
                        @click.native="openContract"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="openContract"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                    <!--<el-form-item label="SLA">-->
                    <!--<el-select v-model.trim="form.sla_id" class="w">-->
                    <!--<el-option label="是" value="0"></el-option>-->
                    <!--<el-option label="否" value="1"></el-option>-->
                    <!--</el-select>-->
                    <!--</el-form-item>-->
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="是否抄送负责人" prop="isac">
                      <el-select v-model.trim="form.is_send_charge" class="w">
                        <el-option label="是" value="1"></el-option>
                        <el-option label="否" value="0"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="描述" prop="">
                      <el-input
                        v-model.trim="form.remark"
                        autocomplete="off"
                        type="textarea"
                        :rows="5"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="负责人" name="fourth">
            <el-table
              ref="listTable"
              border
              stripe
              :data="fzrList"
              :element-loading-text="elementLoadingText"
              highlight-current-row
              @current-change="handleCurrentChange"
              @selection-change="setSelectContactRows"
            >
              <el-table-column
                show-overflow-tooltip
                type="selection"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                type="index" label="序号"
                width="60"
                sortable
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="user_name"
                label="用户名"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="name"
                label="姓名"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="departmentName"
                label="部门"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="telephone"
                label="电话"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="mailbox"
                label="邮箱"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="status"
                label="状态"
                sortable
                align="center"
                :formatter="formatStatus"
              ></el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteContract"
              >
                移除选择对象
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAddFzr"
              >
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="协查人" name="third">
            <el-table
              ref="listTable"
              border
              stripe
              :data="xcrList"
              :element-loading-text="elementLoadingText"
              highlight-current-row
              @current-change="handleCurrentChange"
              @selection-change="setSelectContactRows2"
            >
              <el-table-column
                show-overflow-tooltip
                type="selection"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                type="index" label="序号"
                width="60"
                sortable
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="user_name"
                label="用户名"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="name"
                label="姓名"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="departmentName"
                label="部门"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="telephone"
                label="电话"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="mailbox"
                label="邮箱"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="status"
                label="状态"
                sortable
                align="center"
                :formatter="formatStatus"
              ></el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteContract2"
              >
                移除选择对象
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAddXcr"
              >
                添加
              </el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </template>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

    <dialogone2 ref="dialogone2" @listData="updateXcr"></dialogone2>
    <dialogone3 ref="dialogone3" @listData="updateFzr"></dialogone3>
    <superior
      ref="superior"
      @categoryData="getParentId"
      @fetch-data="getParentId"
    ></superior>
    <customerContractDia
      ref="customerContractDia"
      @listData="customerContractDataSet"
    ></customerContractDia>
  </div>
</template>

<script>
  import {
    getList,
    dropDownData,
    upUserData,
    save,
    getUserList,
    edit,
  } from '@/api/eventServiceManage'
  import {add, eventCategoryinfo, eventCategoryEdit} from '@/api/event_category'

  import customerContractDia from '@/components/customerContractDia'
  import Dialogone2 from '@/components/UsersDia'
  import Dialogone3 from '@/components/UsersDia'
  import superior from '@/components/gongdanclass'

  export default {
    name: 'workEdit',
    components: {
      customerContractDia,
      Dialogone2,
      Dialogone3,
      superior,
    },
    data() {
      return {
        isDisable: false,
        activeName: 'first',
        input2: '',
        formArr: [
          {
            value1: '',
          },
        ],
        formArr1: [
          {
            value1: '',
          },
        ],
        fzrList: [],
        xcrList: [],
        selectContactRows: '',
        selectContactRows2: '',
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        checked: false,
        checked1: false,
        options: [],
        value: [],
        list: [],
        loading: false,

        form: {
          is_use: '',
          name: '',
          sort: '',
          parent_id: '',
          sla_id: '',
          customer_contract_id: '',
          contractName: '',
          is_send_charge: '',
        },
        rules: {
          name: [
            {
              required: true,
              trigger: 'blur',
              message: '请输入名称',
            },
          ],
          is_use: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择状态',
            },
          ],
          contractName: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择客户合同',
            },
          ],
        },
        workData: [],
        auto_engineer_data: '',
        eventServiceManage: '',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          is_use: '',
          name: '',
        },
        parentName: '',
      }
    },
    created() {
      this.fetchData()
      // this.dropDown();
      this.upUser()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index) {
        var that = this
        if (that.formArr.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr.splice(index, 1)
        }
      },
      addItemo() {
        var that = this
        that.formArr1.push({
          value1: '',
        })
      },
      delItemo(index) {
        var that = this
        if (that.formArr1.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          that.formArr1.splice(index, 1)
        }
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      setSelectContactRows(val) {
        this.selectContactRows = val
      },
      setSelectContactRows2(val) {
        this.selectContactRows2 = val
      },
      handleDeleteContract() {
        var that = this
        if (that.selectContactRows.length > 0) {
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            that.selectContactRows.forEach((item, i) => {
              that.fzrList.some((x, i) => {
                if (x.id == item.id) {
                  that.fzrList.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            that.$baseMessage('删除成功', 'success')
            // var data = that.fzrList;
            // var colorsArr = data.reduce(function (arr, data) {
            //   arr.push(data.id);
            //   return arr;
            // }, []);
            // that.$refs['dialogone3'].fcz(colorsArr);
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDeleteContract2() {
        var that = this
        if (that.selectContactRows2.length > 0) {
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            that.selectContactRows2.forEach((item, i) => {
              that.xcrList.some((x, i) => {
                if (x.id == item.id) {
                  that.xcrList.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            that.$baseMessage('删除成功', 'success')
            // var data = that.xcrList;
            // var colorsArr = data.reduce(function (arr, data) {
            //   arr.push(data.id);
            //   return arr;
            // }, []);
            // that.$refs['dialogone2'].fcz(colorsArr)
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      openContract() {
        this.$refs['customerContractDia'].showWin()
      },
      customerContractDataSet(row) {
        console.log('customerContractDataSet')
        console.log(row)
        this.form.customer_contract_id = row.id
        this.form.contractName = row.contract_name
        console.log(this.form.contractName)
      },
      openwinSuperior() {
        console.log(this.$route.query.id)
        this.$refs['superior'].showWin(this.$route.query.id)
      },
      handleAddXcr() {
        var temp = {};
        temp.list = this.xcrList;
        this.$refs['dialogone2'].showWin(temp)
      },
      handleAddFzr() {
        var temp = {};
        temp.list = this.fzrList
        this.$refs['dialogone3'].showWin(temp)
      },
      formatStatus(row, column, cellValue) {
        switch (cellValue) {
          case '1':
            return '活动'
          case '2':
            return '暂停'
          case '3':
            return '已禁用'
          default:
            return ''
        }
      },
      fetchData() {
        var that = this
        if (null != that.$route.query.id || undefined != that.$route.query.id) {
          eventCategoryinfo(that.$route.query.id).then((res) => {
            that.form = res.data.eventCategory
            that.form.parent_id = res.data.eventCategory.parent_id_name
            that.form.is_use = that.form.is_use + ''
            // that.form.sla_id = that.form.sla_id + "";
            that.form.customer_contract_id = that.form.customer_contract_id + ''
            that.form.is_send_charge = that.form.is_send_charge + ''
            that.fzrList = res.data.sysUserList1
            that.xcrList = res.data.sysUserList2
            console.log(that.form)
          })
        }
      },
      async dropDown() {
        var that = this
        const resp = await dropDownData()
        that.workData = resp.data
      },
      upUser() {
        var that = this

        upUserData().then((res) => {
          console.log(res)
          that.auto_engineer_data = res.data
        })
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
      saveok() {
        this.isDisable = true;
        var that = this
        that.$refs['form'].validate(async (valid) => {
          //定义一个数组
          // var cons = that.fzrList;
          // if (null != cons) {
          //   for (var i = 0; i < that.fzrList.length; i++) {
          //     var con = {};
          //     con["engineer_userid"] = that.fzrList[i].id;
          //     cons[i] = con;
          //   }
          // }
          //
          // var cons2 = that.xcrList;
          // if (null != cons2) {
          //   for (var i = 0; i < that.xcrList.length; i++) {
          //     var con = {};
          //     con["engineer_userid"] = that.xcrList[i].id;
          //     cons2[i] = con;
          //   }
          // }

          if (valid) {
            var data = {
              eventCategory: {
                id: that.form.id,
                name: that.form.name,
                is_use: that.form.is_use,
                sort: that.form.sort,
                parent_id: that.parentName,
                // "sla_id": that.form.sla_id,
                customer_contract_id: that.form.customer_contract_id,
                is_send_charge: that.form.is_send_charge,
                remark: that.form.remark,
              },
              type1: that.fzrList,
              type2: that.xcrList,
            }
            eventCategoryEdit(data).then((res) => {
              if (res.code == 0) {
                that.$baseMessage("成功", 'success');
                that.$store.dispatch('tabsBar/delRoute', this.$route)
                that.$router.push({
                  path: '/set/workOrder',
                })
              } else {
                that.$baseMessage(res.msg, 'error');
                that.isDisable = false;
              }

            })
            // const {
            //   msg
            // } = eventCategoryEdit(data)

            // this.closeall()
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      updateXcr(rows) {
        //协查人
        rows.forEach((item, i) => {
          let index = this.xcrList.findIndex((ele) => {
            return ele.id === item.id
          })
          if (index === -1) {
            this.xcrList.push(item)
          }
        })
      },
      updateFzr(rows) {
        //负责人
        rows.forEach((item, i) => {
          let index = this.fzrList.findIndex((ele) => {
            return ele.id === item.id
          })
          if (index === -1) {
            this.fzrList.push(item)
          }
        })
      },
      getParentId(data) {
        //触发子组件城市选择-选择城市的事件
        var that = this
        that.form.parent_id = data.name
        that.parentName = data.id
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

  .tips {
    color: #e6a23c;
  }

  .boxtable .btn-box {
    margin-top: 10px;
  }

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
  }
</style>
