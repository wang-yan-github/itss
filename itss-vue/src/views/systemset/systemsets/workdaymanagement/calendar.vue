<template>
  <div class="index-container">
    <div class="box_card">
      <div class="box_header">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="日历">
            <el-date-picker
              v-model="value"
              type="month"
              popper-class="date-style"
              format="yyyy-MM"
              value-format="yyyy-MM"
              :clearable="false"
              :editable="false"
              @change='selectChange(value)'
              placeholder="选择日期">

            </el-date-picker>
          </el-form-item>
        </el-form>
      </div>
      <div class="box_content">
        <el-calendar v-model="value">
          <template slot="dateCell" slot-scope="{ date, data }" v-if="flag">
            <div class="date-content" @click="calItem(date,data.day)">
              <span class="text">{{ getDay(date) }}</span>

              <!--<span v-if="isWeek(date)" class="rest">休</span>-->
              <span v-if="dealMyDate(data.day,'0')" class="holiday">假</span>
              <span v-else-if="dealMyDate(data.day,'1')" class="rest">休</span>
              <span v-else-if="dealMyDate(data.day,'2')" class="work">班</span>
              <!--<span v-else="" class="work">班</span>-->
            </div>
          </template>
        </el-calendar>
      </div>
    </div>


    <el-dialog
      :title="editForm.day"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-form
        @submit.native.prevent
        :ref="editForm"
        :model="editForm"
      >
        <el-form-item label="日期性质：" prop="grade">
          <el-radio-group v-model="editForm.grade">
            <el-radio :label="0">节假日</el-radio>
            <el-radio :label="1">双休日</el-radio>
            <el-radio :label="2">工作日</el-radio>
          </el-radio-group>

        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="callOf('editForm')">取 消</el-button>
        <el-button
          type="primary"
          @click="
            dialogVisible = false
            add()
          "
        >
          确 定
        </el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>

  import {selectCalendar, setCalendar,} from '@/api/sysWorkingDays'

  export default {
    name: 'sysworkdaysCalendar',
    components: {},
    data() {
      return {
        formInline: "",
        flag: false,
        resMap: [],
        resDate: [],
        value: new Date(),
        dialogVisible: false,
        rcdata: [],
        editForm: {
          date: '',
          grade: '',
        },
        form: {
          value: '',
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      selectChange(val) {
        this.flag = false;
        this.value = val
        this.fetchData()

      },

      calItem(date, d) {
        this.editForm.date = d
        let res = this.resMap.find(item => {
          return item.date === d
        })
        // 判断res非空
        if (!res) {
          return;
        }
        this.rcdata = res.grade
        this.editForm.grade = this.rcdata
        this.dialogVisible = true
      },

      // 标注今天日期
      getDay(date) {
        return date.getDate()
      },

      dealMyDate(date, v) {
        // 从集合中查找某个日期
        let res = this.resMap.find(item => {
          return item.date === date
        })
        if (res == undefined) {
          return;
        }
        return res.grade == v ? true : false;
      },

      async add() {
        console.log(this.editForm)
        const {data} = await setCalendar(this.editForm)
        this.fetchData();
        // var a = {
        //   date: this.editForm.day,
        //   grade: this.editForm.grade,
        // }
        //
        // if (this.resDate.length>0){
        //   //find方法查询是否存在相同的数据
        //   let isAdd = this.resDate.find(item => {
        //     if (item.date == a.date){
        //       //存在相同的数据调用vue的$set方法替换value值
        //       this.$set(item, 'grade', a.grade)
        //       //数组中的元素在测试条件时返回 true 时, find()返回符合条件的元素
        //       return true
        //     }
        //     //如果不存在find方法会返回一个undefined
        //   })
        //   // 判断isAdd是否为undefined
        //   if (typeof(isAdd) == "undefined"){
        //     //当isAdd为undefined添加新的对象到数组中
        //     this.resDate.push(a);
        //   }
        // }

      },
      handleClose(done) {
        done()
      },
      callOf(formName) {
        this.dialogVisible = false
      },
      async fetchData() {
        this.form.value = this.value
        const {data} = await selectCalendar(this.form)
        this.resDate = data;
        //根据id, list 转map 去除前后空格 去重
        this.resMap = this.resDate.map(item => {
          return {
            date: item.date.trim(),
            grade: item.grade
          }
        })
        this.flag = true;
      },

    },
  }
</script>
<style lang="scss" scoped>
  .index-container {
    .demo-form-inline {
      margin-right: 24px;
    }

    .box_header {
      text-align: right;
    }

    .box_card .box_content {
      margin-top: -50px;
    }

    .select-box {
      display: flex;
      justify-content: flex-end;
    }

    ::v-deep {
      .el-calendar__button-group {
        display: none;
      }

      .el-calendar__title {
        font-size: 16px;
        margin-top: -8px;
        margin-bottom: 14px;
        margin-left: -10px;
      }

      .el-form-item--small.el-form-item {
        margin-bottom: 0;
      }

      .prev {
        cursor: not-allowed;
        pointer-events: none;

        .el-calendar-day {
          display: none;
        }
      }

      .next {
        cursor: not-allowed;
        pointer-events: none;

        .el-calendar-day {
          display: none;
        }
      }
    }

    ::v-deep .el-calendar-day {
      height: auto;
    }

    ::v-deep .el-calendar-table__row td::v-deep .el-calendar-table tr td:first-child, ::v-deep .el-calendar-table__row td.prev {
      border: none;
    }

    .date-content {
      height: 40px;
      text-align: center;
      line-height: 40px;
      font-size: 14px;
    }

    .date-content .rest {
      color: #fff;
      border-radius: 50%;
      background: rgb(250, 124, 77);
      width: 20px;
      height: 20px;
      line-height: 20px;
      display: inline-block;
      font-size: 12px;
      margin-left: 10px;
    }

    .date-content .work {
      color: #fff;
      border-radius: 50%;
      background: #409EFF;
      width: 20px;
      height: 20px;
      line-height: 20px;
      display: inline-block;
      font-size: 12px;
      margin-left: 10px;
    }

    .date-content .holiday {
      color: #fff;
      border-radius: 50%;
      background: #E6A23C;
      width: 20px;
      height: 20px;
      line-height: 20px;
      display: inline-block;
      font-size: 12px;
      margin-left: 10px;
    }

    .date-content .text {
      width: 20px;
      height: 20px;
      line-height: 20px;
      display: inline-block;
    }


  }
</style>
