<template>
  <div class="index-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <template>
        <el-tabs v-model="activeName">
          <el-tab-pane :label="eventsName" name="first">
            <el-table
              v-loading="listLoading"
              :data="form.events"
              stripe
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelectEventRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
                <!--<template #default="{ row }">-->
                <!--<el-link @click="handlesee(row)">{{row.title}}</el-link>-->
                <!--</template>-->
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="user_id_name" sortable label="用户" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" >
                <template slot-scope="scope">
                  <span>{{ parseDateFull(scope.row.create_time) }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center" sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间" sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" width="180px;">
                <template #default="{ row }">
                  <el-rate v-model="row.event_satisfaction" disabled>
                  </el-rate>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteEvents">移除选择对象</el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleEventsAdd">添加关联的工单</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane :label="changesName" name="second">
            <el-table
              v-loading="listLoading"
              :data="form.changeInfos"
              stripe
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelecChangeRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="change_no" sortable  label="变更单号" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题"></el-table-column>
              <el-table-column show-overflow-tooltip prop="start_time"  label="计划开始时间" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="classify_name" align="center" sortable label="变更类别"></el-table-column>
              <el-table-column show-overflow-tooltip prop="type_name" align="center" sortable label="变更类型"></el-table-column>
              <el-table-column show-overflow-tooltip prop="approve_name" align="center" label="审批人"></el-table-column>
              <el-table-column show-overflow-tooltip prop="approve_time" align="center" label="审批时间"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" align="center" label="创建时间">
                <template slot-scope="scope">
                  <span>{{ parseDateFull(scope.row.create_time) }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态"></el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteChange">移除选择对象</el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleChangeAdd">添加关联变更</el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </template>

      <div class="bottom">
        <el-button type="primary" size="medium" :loading="loadingbut" @click="handleAdd">保存</el-button>
        <el-button size="medium" @click="closeall">取消</el-button>
      </div>
    </el-form>

    <!--事件-->
    <eventDialog ref="eventDialog" @listData="listEventLists"></eventDialog>
    <!--变更-->
    <changeDialog ref="changeDialog" @listData="listChanges"></changeDialog>
  </div>
</template>

<script>
  import {getQuestionById, questionModifyRelevance} from '@/api/question'
  // 引用事件弹窗页面
  import eventDialog from '@/components/eventDialog'
  // 引用变更弹窗页面
  import changeDialog from '@/components/changeDialog'

  export default {
    name: 'dealRelation',
    components: {eventDialog, changeDialog},
    data() {
      return {
        events_type:'1',
        texts:['差','一般','好','非常好','非常棒'],
        eventsName: '',
        changesName: '',
        activeName: 'first',
        loadingbut: false,//默认提交按钮可点击
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
        tableData: [],
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
          id: '',
          //来源id 来源通过设置-问题配置中的问题来源管理中获取
          source_id: '',
          //3	优先级	设置-服务级别协议中的问题优先级管理中获取
          priority_id: '',
          //4	问题类别id	问题类别通过设置-问题配置中的问题类别管理中获取
          category_id: '',
          //5	标题
          title: '',
          //6	描述
          description: '',
          //7	期望解决时间 yyyy-MM-dd
          hope_solve_time: '',
          //8	问题鉴定通知 0否 1是
          is_notice: '',
          //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
          status: '',
          //10 备注
          remark: '',
          //11 问题单号
          code: '',
          //14 鉴定人 用户表id
          appraisal_user: '',
          //15 鉴定时间
          appraisal_time: '',
          //16 鉴定意见
          appraisal_opinion: '',
          //17 处理人
          handle_user: '',
          //18 解决人 用户表id
          solve_user: '',
          //19 解决时间
          solve_time: '',
          //20 解决方案
          solve_content: '',
          //21 审核人 用户表id
          audit_user: '',
          //22 审核时间
          audit_time: '',
          //23 审核内容
          audit_content: '',
          //24 关单人 用户表id
          clearance_user: '',
          //25 关单时间
          clearance_time: '',
          //26 sla:(小时)
          sla_time: '',
          //27 关单时限
          time_limit: '',
          //28 解决分钟数
          resolution_minutes: '',
          //29 当前操作人 用户表id
          now_operator_user: '',
          //挂起原因
          suspend_reason: '',
          //审核人名称
          audit_user_name: '',
          //来源名称
          source_name: '',
          //关单人名称
          clearance_user_name: '',
          //解决人名称
          solve_user_name: '',
          //鉴定人名称
          appraisal_user_name: '',
          //处理人名称
          handle_user_name: '',
          //状态名称
          status_name: '',
          //创建人名称
          create_user_name: '',
          //类别名称
          category_name: '',
          //优先级名称
          priority_name: '',
          //当前操作人名称
          now_operator_user_name: '',
          //事件工单列表
          events: [],
          //变更列表
          changeInfos: [],
          //问题日志
          questionLogs: [],
          //关联审核人
          questionReviewers: [],
        },
        multipleSelection: [],
        rules: {
          // user_name: [
          //   { required: true, trigger: 'blur', message: '请输入用户名' },
          // ],
          // name: [{ required: true, trigger: 'blur', message: '请输入姓名' }],
          // pass: [{ required: true, trigger: 'blur', message: '请输入密码' }],
          // mailbox: [{ required: true, trigger: 'blur', message: '请输入邮箱' }],
          // permissions: [
          //   { required: true, trigger: 'blur', message: '请选择权限' },
          // ],
        },
      }
    },
    created() {
      this.getData();
    },
    beforeDestroy() {
    },
    mounted() {
      // this.list = this.states.map((item) => {
      //   return { value: `${item}`, label: `${item}` }
      // })
    },
    methods: {
      async getData() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getQuestionById(query.id);
        this.form = data;
        this.eventsName = "关联工单(" + this.form.events.length + ")";
        this.changesName = "关联变更(" + this.form.changeInfos.length + ")";
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      handleEventsAdd() {
        this.$refs['eventDialog'].showWin(this.events_type,this.form.events)
      },
      handleChangeAdd() {
        this.$refs['changeDialog'].showWin()
      },
      setSelectEventRows(val) {
        this.selectEventRows = val
      },
      setSelecChangeRows(val) {
        this.selectChangeRows = val
      },
      listEventLists(rows) {
        rows.forEach((item, i) => {
          let index = this.form.events.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.events.push(item)
          }
        })
        this.eventsName = "关联工单(" + this.form.events.length + ")";
      },
      listChanges(rows) {
        rows.forEach((item, i) => {
          let index = this.form.changeInfos.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.changeInfos.push(item)
          }
        })
        this.changesName = "关联变更(" + this.form.changeInfos.length + ")";
      },
      handleDeleteEvents() {
        if (this.selectEventRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectEventRows.forEach((item, i) => {
              this.form.events.some((x, i) => {
                if (x.id == item.id) {
                  this.form.events.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
              this.$refs.eventDialog.data.some((e, i) => {
                if (e.id == item.id) {
                  this.$refs.eventDialog.data.splice(i, 1)
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
            this.eventsName = "关联工单(" + this.form.events.length + ")";
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleDeleteChange() {
        if (this.selectChangeRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectChangeRows.forEach((item, i) => {
              this.form.changeInfos.some((x, i) => {
                if (x.id == item.id) {
                  this.form.changeInfos.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
            this.changesName = "关联变更(" + this.form.changeInfos.length + ")";
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleAdd() {
        this.$refs['form'].validate(async (valid) => {
          console.log(this.form);
          this.loadingbut = true;
          if (valid) {
            const {msg} = await questionModifyRelevance(this.form);
            this.$baseMessage(msg, 'success');
            this.$emit('fetch-data');

            this.closeall();
          } else {
            this.loadingbut = false;
            return false
          }
        })
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      //然后定义函数parseDateFull的实现
      parseDateFull(time) {
        // 判断是否为空
        if(time == null || time == '') {
          return ''
        }
        var x = new Date(time);
        var z = {
          y: x.getFullYear(),
          M: x.getMonth() + 1,
          d: x.getDate(),
          h: x.getHours(),
          m: x.getMinutes(),
          s: x.getSeconds(),
        };
        if (z.M < 10) {
          z.M = "0" + z.M;
        }
        if (z.d < 10) {
          z.d = "0" + z.d;
        }
        if (z.h < 10) {
          z.h = "0" + z.h;
        }
        if (z.m < 10) {
          z.m = "0" + z.m;
        }
        // return z.y + "-" + z.M + "-" + z.d + " " + z.h + ":" + z.m ;
        return z.y + "-" + z.M + "-" + z.d ;
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

    .el-upload {
      text-align: left;
      /*margin-top: 20px;*/
    }
  }
</style>
