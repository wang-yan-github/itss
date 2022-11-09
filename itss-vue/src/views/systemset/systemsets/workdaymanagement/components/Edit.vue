<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="900px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-divider content-position="left">
            <span class="font16 b">基础信息</span>
          </el-divider>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="名称" prop="work_name">
            <el-input
              v-model.trim="form.work_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="状态" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="停用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="默认" prop="acquiesce">
            <el-select v-model.trim="form.acquiesce" class="w">
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-divider content-position="left">
            <span class="font16 b">上下班时间</span>
          </el-divider>
        </el-col>
        <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--<el-form-item label="" prop="remakes">-->
        <!--<el-checkbox v-model="checked" @change="isshowClick(checked)">分别设置上下班时间</el-checkbox>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-row :gutter="20" v-if="isShow">
            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
              <!--<el-form-item  v-model="list.week_name">周一至周日</el-form-item>-->
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="上午上班时间" prop="start_time_AM">
                <el-time-select
                  v-model="form.start_time_AM"
                  :picker-options="{
                    start: '08:30',
                    step: '00:15',
                    end: '18:30',
                  }"
                  placeholder="选择时间"
                ></el-time-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="上午下班时间" prop="end_time_AM">
                <el-time-select
                  v-model="form.end_time_AM"
                  :picker-options="{
                    start: '08:30',
                    step: '00:15',
                    end: '18:30',
                  }"
                  placeholder="选择时间"

                ></el-time-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="下午上班时间" prop="start_time_PM">
                <el-time-select
                  v-model="form.start_time_PM"
                  :picker-options="{
                    start: '08:30',
                    step: '00:15',
                    end: '18:30',
                  }"
                  placeholder="选择时间"
                ></el-time-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="下午下班时间" prop="end_time_PM">
                <el-time-select
                  v-model="form.end_time_PM"
                  :picker-options="{
                    start: '08:30',
                    step: '00:15',
                    end: '18:30',
                  }"
                  placeholder="选择时间"
                ></el-time-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toAdd,getWorkTime} from '@/api/sysWorkingDays'

  export default {
    name: 'companyEdit',
    components: {},
    data() {
      return {
        isDisable: false,
        checked: false,
        value1: '',
        value2: '',
        isShow: true,


        form: {
          id: '',
          work_name: '',
          is_use: '',
          acquiesce: '',
          is_distinguish: '',
          distinguishes: [],
          start_time_AM: '',
          end_time_AM: '',
          start_time_PM: '',
          end_time_PM: '',
        },
        rules: {
          work_name: [{ required: true, trigger: 'blur', message: '请输入名称' }],
          is_use: [
            { required: true, trigger: 'blur', message: '请选择状态' },
          ],
          acquiesce: [{ required: true, trigger: 'blur', message: '请选择是否默认' }],
          start_time_AM:[{ required: true, trigger: 'blur', message: '请选择' }],
          end_time_AM:[{ required: true, trigger: 'blur', message: '请选择' }],
          start_time_PM:[{ required: true, trigger: 'blur', message: '请选择' }],
          end_time_PM:[{ required: true, trigger: 'blur', message: '请选择' }],

        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {

    },
    methods: {
      isshowClick(val) {
        if (val) {
          this.isShow = false;
        } else {
          this.isShow = true;
        }
      },
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          // this.form.start_time_AM = row.sysWorkingDaysDistinguish.start_time_AM;
          // this.form.end_time_AM = row.sysWorkingDaysDistinguish.end_time_AM;
          // this.form.start_time_PM = row.sysWorkingDaysDistinguish.start_time_PM;
          // this.form.end_time_PM = row.sysWorkingDaysDistinguish.end_time_PM;
          this.getWorkTime();
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.$emit('fetch-data');
        this.form = this.$options.data().form;
        // this.form = {};
        // this.form.start_time_AM = '';
        // this.form.end_time_AM = '';
        // this.form.start_time_PM = '';
        // this.form.end_time_PM = '';


        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {

            try{
              const data = await toAdd(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.$emit('fetch-data');
                this.close();
              } else {
                this.$baseMessage(data.msg, 'error');
                this.isDisable = false;
              }
            }catch(e){
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      async getWorkTime(){
        const data = await getWorkTime(this.form)
        console.log(data.data)
        this.form.start_time_AM = data.data.start_time_AM;
        this.form.end_time_AM = data.data.end_time_AM;
        this.form.start_time_PM = data.data.start_time_PM;
        this.form.end_time_PM = data.data.end_time_PM
      }


    },

  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .el-date-editor.el-input {
      width: 100% !important
    }
  }
</style>

