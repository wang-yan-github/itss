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
                    <el-form-item label="类型" prop="schedule_type">
                      <el-select v-model.trim="form.schedule_type" class="w">
                        <el-option label="每工作日" value="1"></el-option>
                        <el-option label="每天" value="2"></el-option>
                        <el-option label="每周" value="3"></el-option>
                        <el-option label="每月" value="4"></el-option>
                        <el-option label="一次性" value="5"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="执行时间" prop="execute_time">
                      <el-time-picker v-model="form.execute_time" placeholder="选择时间" value-format="HH:mm" format='HH:mm'
                                      style="width: 100%;">
                      </el-time-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="开始日期" prop="begin_time">
                      <el-date-picker v-model="form.begin_time" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"
                                      style="width: 100%;">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="form.schedule_type != 5">
                    <el-form-item label="结束日期">
                      <el-date-picker v-model="form.end_time" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"
                                      style="width: 100%;">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="form.schedule_type == 1">
                    <el-form-item label="每 ( 工作日 )">
                      <el-select
                        v-model.trim="form.interval_day"
                        multiple
                        collapse-tags
                        filterable
                        remote
                        reserve-keyword
                        clearable
                        placeholder="请选择">
                        <el-option
                          v-for="item in workDays"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                      <!--<el-input v-model.trim="form.interval_day" autocomplete="off"></el-input>-->
                      <p class="ts">
                        不填默认工作日为每周一到周五
                        <!--，多个按英文逗号分割，例如：2,3,4,5,6-->
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="form.schedule_type == 3">
                    <el-form-item label="每 ( 周 )">
                      <el-select
                        v-model.trim="form.week"
                        multiple
                        collapse-tags
                        filterable
                        remote
                        reserve-keyword
                        clearable
                        placeholder="请选择">
                        <el-option
                          v-for="item in weeks"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                      <!--<el-input v-model.trim="form.week" autocomplete="off"></el-input>-->
                      <p class="ts">
                        不填默认为每周一
                        <!--，注意1是星期一，多个按英文逗号分割，例如：1,2,3,4,5-->
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="form.schedule_type == 4">
                    <el-form-item label="每 ( 月 )">
                      <el-select
                        v-model.trim="form.month"
                        multiple
                        collapse-tags
                        filterable
                        remote
                        reserve-keyword
                        clearable
                        placeholder="请选择">
                        <el-option
                          v-for="item in months"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                      <!--<el-input v-model.trim="form.month" autocomplete="off"></el-input>-->
                      <p class="ts">
                        不填默认为每月
                        <!--，多个按英文逗号分割，例如：1,2,3,4,5-->
                      </p>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="form.schedule_type == 4">
                    <el-form-item label="日">
                      <el-select
                        v-model.trim="form.day"
                        multiple
                        collapse-tags
                        filterable
                        remote
                        reserve-keyword
                        clearable
                        placeholder="请选择">
                        <el-option
                          v-for="item in days"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value">
                        </el-option>
                      </el-select>
                      <!--<el-input v-model.trim="form.day" autocomplete="off"></el-input>-->
                      <p class="ts">
                        不填默认为每月1日
                        <!--，多个按英文逗号分割，例如：1,2,3,4,5-->
                      </p>
                    </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="备注" prop="">
                      <el-input v-model.trim="form.remark" autocomplete="off" type="textarea"
                                :rows="10"></el-input>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>

            </el-row>
          </el-tab-pane>
          <el-tab-pane :label="eventTemplateName" name="second">
            <el-table ref="listTable" border stripe :data="eventTemplateList"
                      :element-loading-text="elementLoadingText" highlight-current-row
                      @selection-change="setSelectContactRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column
                show-overflow-tooltip
                type="index" label="序号"
                width="60"
                sortable
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="templateNumber"
                width="200px;"
                sortable
                label="模板编号"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="template_name"
                label="模板名称"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="event_service_name"
                label="所属服务群组"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="create_user_name"
                label="创建人"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="create_time"
                label="创建时间"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="update_user_name"
                label="修改人"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="update_time"
                label="修改时间"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="is_use"
                label="状态"
                align="center"
              >
                <template slot-scope="scope">
                  {{ scope.row.is_use == 0 ? '禁用' : '启用' }}
                </template>
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="is_public"
                label="公共模板"
                align="center"
              >
                <template slot-scope="scope">
                  {{ scope.row.is_use == 0 ? '否' : '是' }}
                </template>
              </el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteContract">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd2">
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

    <dialogone ref="dialogone" @listData="listAssetsContract" @fetch-data=""></dialogone>

  </div>

</template>

<script>
  import Dialogone from '@/components/eventTemplatesDia'
  import {
    editEventSchedule, info
  } from '@/api/eventSchedule'

  export default {
    name: 'taskEdit',
    components: {
      Dialogone,
    },
    data() {
      return {
        isDisable: false,
        eventTemplateName: '工单模板',
        elementLoadingText: '正在加载...',
        activeName: 'first',
        checked: false,
        checked1: false,
        checked2: false,
        checked3: false,
        //工作日集合
        workDays: [],
        //周集合
        weeks: [],
        //月集合
        months: [],
        //日集合
        days: [],
        form: {
          status: '',
          remakes: '',
          schedule_type: "",
          execute_time: "",
          begin_time: "",
          end_time: "",
          interval_day: "",
          remark: "",
          week: "",
          month: "",
          eventTemplateList: []
        },
        eventTemplateList: [],
        selectContactRows: [],
        rules: {
          execute_time: [{
            required: true,
            trigger: 'blur',
            message: '请选择执行时间'
          },],
          schedule_type: [{
            required: true,
            trigger: 'blur',
            message: '请选择状态'
          }],
          begin_time: [{
            required: true,
            trigger: 'blur',
            message: '请选择开始日期'
          },],
        },
        title: '',
        value2: '',
        value3: '',
        value4: '',
      }
    },
    created() {
      this.onLoadData();
      this.fetchData();
    },
    methods: {
      onLoadData() {
        var weekList = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
        // var monthList = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];

        //工作日集合 workDays
        for (var i = 0; i < weekList.length - 2; i++) {
          this.workDays.push({
            value: i + 1 + "",
            label: weekList[i]
          });
        }

        //周集合 weeks
        for (var i = 0; i < weekList.length; i++) {
          this.weeks.push({
            value: i + 1 + "",
            label: weekList[i]
          });
        }

        //月集合 months
        for (var i = 0; i < 12; i++) {
          this.months.push({
            value: i + 1 + "",
            label: i + 1 + "月"
          });
        }
        //日集合 days
        for (var i = 0; i < 28; i++) {
          this.days.push({
            value: i + 1 + "",
            label: i + 1 + "日"
          });
        }
      },
      handleDeleteContract() {
        if (this.selectContactRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectContactRows.forEach((item, i) => {
              this.eventTemplateList.some((x, i) => {
                if (x.id == item.id) {
                  this.eventTemplateList.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false;
        }
      },
      setSelectContactRows(val) {
        this.selectContactRows = val
      },
      handleAdd2() {
        this.$refs['dialogone'].showWin()
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
      saveok() {
        this.isDisable = true;
        var that = this;
        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            //类型 1每工作日 2每天 3 每周 4每月 5一次性
            if (that.form.schedule_type == "1") {
              if (that.form.interval_day.length == 0) {
                that.form.interval_day = "";
              } else {
                that.form.interval_day = that.form.interval_day.join(",")
              }
              that.form.week = "";
              that.form.day = "";
              that.form.month = "";
            } else if (that.form.schedule_type == "3") {
              if (that.form.week.length == 0) {
                that.form.week = "";
              } else {
                that.form.week = that.form.week.join(",")
              }
              that.form.interval_day = "";
              that.form.day = "";
              that.form.month = "";
            } else if (that.form.schedule_type == "4") {
              if (that.form.day.length == 0) {
                that.form.day = "";
              } else {
                that.form.day = that.form.day.join(",")
              }
              if (that.form.month.length == 0) {
                that.form.month = "";
              } else {
                that.form.month = that.form.month.join(",")
              }
              that.form.interval_day = "";
              that.form.week = "";
            } else {
              that.form.interval_day = "";
              that.form.week = "";
              that.form.day = "";
              that.form.month = "";
            }
            that.form.eventTemplateList = that.eventTemplateList;
            editEventSchedule(that.form).then((res) => {
              if (res.code == 0) {
                that.$baseMessage("操作成功", 'success');
                that.closeall()
              } else {
                that.$baseMessage("失败", 'error');
                that.isDisable = false;
              }

            });
          } else {
            that.isDisable = false;
            return false
          }
        })

      },
      listAssetsContract(rows) {
        rows.forEach((item, i) => {
          let index = this.eventTemplateList.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.eventTemplateList.push(item)
          }
        })
        console.log(this.eventTemplateList);
      },
      fetchData() {//触发子组件城市选择-选择城市的事件\
        var that = this;
        info(that.$route.query.id).then((res) => {
          that.form = res.data;
          if (that.form.schedule_type == "1") {
            if (!that.form.interval_day) {
              that.form.interval_day = "";
            } else {
              that.form.interval_day = that.form.interval_day.split(",")
            }
          } else if (that.form.schedule_type == "3") {
            if (!that.form.week) {
              that.form.week = "";
            } else {
              that.form.week = that.form.week.split(",")
            }
          } else if (that.form.schedule_type == "4") {
            if (!that.form.day) {
              that.form.day = "";
            } else {
              that.form.day = that.form.day.split(",")
            }
            if (!that.form.month) {
              that.form.month = "";
            } else {
              that.form.month = that.form.month.split(",")
            }
          }
          console.log(that.form)
          that.eventTemplateList = res.data.eventTemplateList;
          this.eventTemplateName = "工单模板(" + this.eventTemplateList.length + ")";
        });
      },
    },
  }
</script>
