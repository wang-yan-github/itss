<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="600px"
    @close="close" append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="旧密码" prop="oldPass">
            <el-input
              v-model.trim="form.oldPass"
              autocomplete="off"
              type="password"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="新密码" prop="newPass">
            <el-input
              v-model.trim="form.newPass"
              autocomplete="off"
              type="password"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="确认密码" prop="confirmPass">
            <el-input
              v-model.trim="form.confirmPass"
              autocomplete="off"
              type="password"
            ></el-input>
          </el-form-item>
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
  import {updatePass} from '@/api/user'
  import { recordRoute } from '@/config'
  import { mapActions,mapGetters } from 'vuex'
  export default {
    name: 'VabAppMainEdit',
    components: {},
    computed: {
      ...mapGetters({
        avatar: 'user/avatar',
        username: 'user/username',
      }),
    },
    data() {
      return {
        isDisable: false,
        form: {
          oldPass: '',
          newPass: '',
          confirmPass: '',
          userId: '',
        },
        rules: {
          oldPass: [{ required: true, trigger: 'blur', message: '请输入密码' }],
          newPass: [
            { required: true, trigger: 'blur', message: '请输入新密码',min:6,message:'密码不能少于6位' },
          ],
          confirmPass: [{ required: true, trigger: 'blur', message: '请输入确认密码' ,min:6,message:'密码不能少于6位'}],

        },
        dialogFormVisible: false,
      }
    },
    methods: {
      ...mapActions("tabsBar", ["delAllVisitedRoutes"]),
      showEdit() {
        this.isDisable = false;
        this.title = '修改密码'
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      async save() {
        this.isDisable = true;
        console.log(this.form)
        const data = await updatePass(this.form)
        console.log(data);
        if (data.code == 0) {
          this.$baseMessage('修改成功', 'success');
          await this.$store.dispatch('user/logout')
          if (recordRoute) {
            this.delAllVisitedRoutes();//清除tabs
            const fullPath = this.$route.fullPath
            // this.$router.push(`/login?redirect=${fullPath}`)
            this.$router.push('/login')
            this.reload()
          } else {
            this.$router.push('/login')
            this.reload()
          }
        }
        // this.$refs['form'].validate(async (valid) => {
        //   if (valid) {
        //     try{
        //
        //       conosle.log(data)
        //       if (data.code == 0) {
        //         this.$baseMessage(data.msg, 'success');
        //         this.close();
        //       } else {
        //         this.$baseMessage(data.msg, 'error');
        //         this.isDisable = false;
        //       }
        //     }catch(e){
        //       this.isDisable = false;
        //     }
        //   } else {
        //     this.isDisable = false;
        //     return false
        //   }
        // })
      },
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

