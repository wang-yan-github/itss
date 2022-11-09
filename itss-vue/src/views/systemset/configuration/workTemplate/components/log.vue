<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close">
    <div class="block">
      <el-timeline>
        <el-timeline-item v-for="(log, index) in sysLogs" :key="index" :timestamp="log.operate_time">
          <p v-if="log.user_name == '' || log.user_name == null">{{log.title}}</p>
          <p v-else>{{log.user_name}} - {{log.title}}</p>
        </el-timeline-item>
      </el-timeline>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getLogInfoById} from '@/api/eventSchedule'

  export default {
    name: 'eventScheduleLog',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        sysLogs: []
      }
    },
    created() {
      // this.getData();
    },
    methods: {
      showView(row) {
        this.title = '查看日志';
        this.dialogFormVisible = true;
        if (row) {
          this.getData(row);
        }

      },
      async getData(id) {
        const {data} = await getLogInfoById(id);
        console.log(data)
        this.sysLogs = data;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
    },
  }
</script>
