<template>
  <div class="index-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <template>
        <el-tabs v-model="activeName">

          <el-tab-pane :label="eventsName" name="second">
            <el-table ref="listTable" border stripe :data="tableDataEvent"
                      :element-loading-text="elementLoadingText" highlight-current-row
                      @selection-change="setSelectRowsEvent">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" width="60" align="center"></el-table-column>
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
              <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
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
              <el-button type="primary" icon="el-icon-plus" @click="handleAddEvent">
                添加关联的事件
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane :label="questionsName" name="three">
            <el-table ref="listTable" @selection-change="setSelectRowsQuest" border stripe :data="tableDataQuest"
                      :element-loading-text="elementLoadingText" highlight-current-row>
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column
                show-overflow-tooltip
                type="index" label="序号"
                width="60"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                sortable
                prop="code"
                label="问题单号"
                align="center"
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="title"
                sortable
                label="标题"
                align="center"
              >
                <!--<template #default="{ row }">-->
                <!--<el-link @click="handleView(row)">{{row.title}}</el-link>-->
                <!--</template>-->
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="create_user_name"
                label="创建人"
                align="center"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="create_time"
                label="创建时间"
                align="center"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="category_name"
                label="问题类别"
                align="center"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="hope_solve_time"
                label="期望解决时间"
                align="center"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="priority_name"
                label="优先级"
                align="center"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="status_name"
                align="center"
                label="状态"
                sortable
              ></el-table-column>

              <el-table-column
                show-overflow-tooltip
                prop="now_operator_user_name"
                align="center"
                label="当前操作人"
                sortable
              ></el-table-column>

              <el-table-column
                show-overflow-tooltip
                prop="solve_time"
                align="center"
                label="解决时间"
                sortable
              ></el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteQuest">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAddQuest">
                添加关联的问题
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane :label="assetsName" name="four">
            <el-table ref="listTable" border stripe :data="tableDataSet"
                      :element-loading-text="elementLoadingText" highlight-current-row
                      @selection-change="setSelectRowsSet">
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
              <el-button type="primary" icon="el-icon-plus" @click="handleAddDeploy">
                添加关联的配置
              </el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </template>

      <div class="bottom">
        <el-button type="primary" size="medium" @click="saveok">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>
    <dialogevent ref="dialogevent"
                 @listData="listEventsManages"
                 @fetch-data="
        {
          input2
        }
      "
    ></dialogevent>
    <dialogquest ref="dialogquest"
                 @listData="listQuestManages"
                 @fetch-data="
        {
          input2
        }
      "
    ></dialogquest>
    <dialogSet ref="dialogSet"
               @listData="listAssetsManages"
               @fetch-data="
        {
          input2
        }
      "
    ></dialogSet>
  </div>
</template>

<script>
  import {
    toList,
    toEdit,
    getAllChangeType,
    getChangeSource,
    getUserList,
    toEditRelevance,
    getChange
  } from '@/api/change'
  import vabQuill from '@/plugins/vabQuill'
  // 引用事件弹窗页面
  import Dialogevent from '@/components/eventDialog'
  // 引用问题弹窗页面
  import Dialogquest from '@/components/questDialog'
  // 引用关联的配置弹窗页面
  import DialogSet from '@/components/assetsTypeDia'

  export default {
    name: 'useredit',
    components: {vabQuill, Dialogevent, Dialogquest, DialogSet},
    data() {
      return {
        activeName: 'second',
        eventsName: '',
        questionsName: '',
        assetsName: '',
        optionss: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{header: [1, 2, 3, 4, 5, 6, false]}],
              [{size: ['small', false, 'large', 'huge']}],
              [{color: []}, {background: []}],
              ['blockquote', 'code-block'],
              [{list: 'ordered'}, {list: 'bullet'}],
              [{script: 'sub'}, {script: 'super'}],
              [{indent: '-1'}, {indent: '+1'}],
              [{align: []}],
              [{direction: 'rtl'}],
              [{font: []}],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
        bumen: '',
        input2: '',
        changeApproveUser: {id: ''},
        tableDataClassify: [],

        tableDataEvent: [],
        selectRowsEvent: '',

        tableDataSet: [],
        selectRowsSet: '',

        tableDataQuest: [],
        selectRowsQuest: '',

        tableDataApproveUser: [],

        programmeApproveUser: "",// 方案审核人
        implementApproveUser: "",// 实施评审人

        changeSourceData: [],
        userList: [],
        getDictChangeTypes: [],
        formArr: [{value1: ''}],
        formArr1: [{value1: ''}],
        tablelist: null,

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
        states: [
          'Alabama',
          'Alaska',
          'Arizona',
          'Arkansas',
          'California',
          'Colorado',
          'Connecticut',
          'Delaware',
          'Florida',
          'Georgia',
          'Hawaii',
          'Idaho',
          'Illinois',
          'Indiana',
          'Iowa',
          'Kansas',
          'Kentucky',
          'Louisiana',
          'Maine',
          'Maryland',
          'Massachusetts',
          'Michigan',
          'Minnesota',
          'Mississippi',
          'Missouri',
          'Montana',
          'Nebraska',
          'Nevada',
          'New Hampshire',
          'New Jersey',
          'New Mexico',
          'New York',
          'North Carolina',
          'North Dakota',
          'Ohio',
          'Oklahoma',
          'Oregon',
          'Pennsylvania',
          'Rhode Island',
          'South Carolina',
          'South Dakota',
          'Tennessee',
          'Texas',
          'Utah',
          'Vermont',
          'Virginia',
          'Washington',
          'West Virginia',
          'Wisconsin',
          'Wyoming',
        ],
        dict: {dict_type: 'change_type'},
        form: {
          changeInfo: {id: this.$route.query.id},
          changeAssets: [],
          changeEvents: [],
          changeQuestions: [],

        },
        multipleSelection: [],

        rules: {
          user_name: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
          name: [{required: true, trigger: 'blur', message: '请输入姓名'}],
          pass: [{required: true, trigger: 'blur', message: '请输入密码'}],
          mailbox: [{required: true, trigger: 'blur', message: '请输入邮箱'}],
          permissions: [
            {required: true, trigger: 'blur', message: '请选择权限'},
          ],
        },
        queryForm: {id: this.$route.query.id},
      }
    },
    created() {


      this.fetchData();
    },
    beforeDestroy() {
    },
    mounted() {
      this.list = this.states.map((item) => {
        return {value: `${item}`, label: `${item}`}
      })
    },
    methods: {
      handleAddEvent() {
        this.$refs['dialogevent'].showWin()
      },
      handleAddQuest() {
        this.$refs['dialogquest'].showWin()
      },
      handleAddDeploy() {
        this.$refs['dialogSet'].showWin()
      },


      setSelectRowsSet(val) {
        this.selectRowsSet = val
      },
      setSelectRowsEvent(val) {
        this.selectRowsEvent = val
      },
      setSelectRowsQuest(val) {
        this.selectRowsQuest = val
      },


      // 删除配置
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
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 删除问题
      handleDeleteQuest() {

        if (this.selectRowsQuest.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsQuest.forEach((item, i) => {
              this.tableDataQuest.some((x, i) => {
                if (x.id == item.id) {
                  this.tableDataQuest.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      // 删除事件
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
            const {msg} = await doDelete({ids})
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      async fetchData() {
        this.listLoading = true
        console.log(this.queryForm)
        const {data} = await getChange(this.queryForm)
        this.form = data;
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.questionsName = "关联的问题(" + data.questionVos.length + ")";
        this.assetsName = "关联的配置(" + data.assetsManages.length + ")";

        this.tableDataQuest = data.questionVos;
        this.tableDataEvent = data.events;
        console.log("关联的配置:")
        console.log(data.assetsManages)
        this.tableDataSet = data.assetsManages;

        this.form.changeEvents = data.changeEvents;
        this.form.changeQuestions = data.changeQuestions;
        this.form.changeAssets = data.changeAssets;


        console.log(data)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
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


      delGroup(data) {
        this.tablelist = data
        console.log(this.tablelist)
      },
      saveok() {

        this.$refs['form'].validate(async (valid) => {
          if (valid) {


            // 关联的事件
            this.form.changeEvents = [];
            this.tableDataEvent.some((x, i) => {
              this.form.changeEvents.push({event_id: x.id, is_del: '0'});
            })

            // 关联的配置
            this.form.changeAssets = [];
            this.tableDataSet.some((x, i) => {
              this.form.changeAssets.push({assets_id: x.id, is_del: '0'});
            })

            //关联的问题
            this.form.changeQuestions = [];
            this.tableDataQuest.some((x, i) => {
              this.form.changeQuestions.push({question_id: x.id, is_del: '0'});
            })

            const {msg} = await toEditRelevance(this.form)
            this.$baseMessage(msg, 'success')
            // this.$emit('fetch-data')
            this.$router.push({
              path: '/biangeng/pay',
            })

            this.closeall()
          } else {
            return false
          }
        })
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
      },
      // 问题
      listQuestManages(row) {
        row.forEach((item, i) => {
          let index = this.tableDataQuest.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.tableDataQuest.push(item)
          }
        })
      },
      // 问题
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
      },


      setSelectRows(val) {
        this.form.roleList = val
      }

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

    .el-upload {
      text-align: left;
      /*margin-top: 20px;*/
    }
  }
</style>
