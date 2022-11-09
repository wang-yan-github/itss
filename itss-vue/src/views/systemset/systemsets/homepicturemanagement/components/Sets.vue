<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="图片长度" prop="pic_length">
        <el-input
          v-model.trim="form.pic_length"
          autocomplete="off"
        ></el-input>
      </el-form-item>
      <el-form-item label="图片宽度" prop="pic_width">
        <el-input
          v-model.trim="form.pic_width"
          autocomplete="off"
        ></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>

  </el-dialog>
</template>

<script>
  import {showSetting, updateSetting} from '@/api/sysBackgroundPic'

  export default {
    name: 'companyEdit',
    components: {},
    data() {
      return {
        isDisable: false,
        value1: '',
        value2: '',
        form: {
          id: '',
          pic_length: '',
          pic_width: '',
        },
        rules: {
          // username: [{ required: true, trigger: 'blur', message: '请输入名称' }],
          // isac: [
          //   { required: true, trigger: 'blur', message: '请选择是否事件工程师' },
          // ],
          // remakes: [{ required: true, trigger: 'blur', message: '请输入描述' }],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '首页背景图配置项'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
        }
        this.dialogFormVisible = true
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        // this.dialogFormVisible = false
        // this.$router.push({path: '/set/homeppmanagement'})
        // window.parent.location.reload();
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await updateSetting(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.$emit('fetch-data');
                this.close();
              } else {
                this.$baseMessage("失败", 'error');
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
      async fetchData() {
        const {data} = await showSetting()
        this.form = data
      },

    },
  }
</script>

