<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="600px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" class="w">
           <el-option>请选择</el-option>
          <el-option label="未开始" value="1"></el-option>
          <el-option label="已开始" value="2"></el-option>
          <el-option label="已完成" value="3"></el-option>
          <el-option label="已作废" value="4"></el-option>
        </el-select>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { savePeriod,getPeriodList } from '@/api/periodManage'
  export default {
    name: 'periodIndexEdit',
    data() {
      return {
        isDisable: false,
        is_def_engineer: false,
        is_def_wechat: false,
        is_def_webpage: false,
        form: {
          name: '',
          status: '',
        },
        rules: {
          name: [{
            required: true,
            trigger: 'blur',
            message: '请输入名称'
          },],
          status: [{
            required: true,
            trigger: 'blur',
            message: '请选择状态'
          }],
        },
        title: '',
        status: '',
        dialogFormVisible: false,
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          resource_name: '',
        }
      }
    },
    created() {

    },
    props: ['sendData'],
    methods: {
      showEdit(row) {
        if (!row) {
          this.title = '添加'
          this.is_def_engineer = false
          this.is_def_wechat = false
          this.is_def_webpage = false
        } else {
          this.title = '编辑'
          row.status = row.status+"";//status是integer 要用到的时候string
          this.form = Object.assign({}, row)
          if (row.is_def_engineer == 1) {
            this.is_def_engineer = true;
          } else if (row.is_def_engineer == 0) {
            this.is_def_engineer = false;
          }

          if (row.is_def_webpage == 1) {
            this.is_def_webpage = true;
          } else if (row.is_def_webpage == 0) {
            this.is_def_webpage = false;
          }

          if (row.is_def_wechat == 1) {
            this.is_def_wechat = true;
          } else if (row.is_def_wechat == 0) {
            this.is_def_wechat = false;
          }

        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.isDisable = true;
            const { msg } = await savePeriod(this.form)
            this.$baseMessage(msg, 'success')
            this.isDisable = false;
            this.$emit('fetch-data')
            this.close()
          } else {
            return false
          }
        })
      },
      fetchData() {
        var that  =this ;
        getPeriodList(that.queryForm).then((res) => {
          that.list = res.data.list ;
          that.total = res.data.total
        });
      }
    },
  }
</script>
