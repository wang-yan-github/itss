<template>
  <el-dropdown @command="handleCommand">
    <span class="avatar-dropdown">
      <!--<el-avatar class="user-avatar" :src="avatar"></el-avatar>-->
      <!--<img class="user-avatar" :src="avatar" alt="" />-->
      <div class="user-name">
        {{ username }}
        <i class="el-icon-arrow-down el-icon--right"></i>
      </div>
    </span>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <el-dropdown-menu slot="dropdown">
      <!-- <el-dropdown-item command="github">个人资料</el-dropdown-item> -->
      <el-dropdown-item command="gitee">密码修改</el-dropdown-item>
      <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import Edit from './components/Edit'
import { mapActions, mapGetters } from 'vuex'
import { recordRoute } from '@/config'

export default {
  name: 'VabAvatar',
  components: { Edit },
  computed: {
    ...mapGetters({
      avatar: 'user/avatar',
      username: 'user/username',
    }),
  },
  methods: {
    ...mapActions('tabsBar', ['delAllVisitedRoutes']),
    handleCommand(command) {
      switch (command) {
        case 'logout':
          this.logout()
          break
        case 'personalCenter':
          this.personalCenter()
          break
        case 'github':
          // window.open('https://github.com/chuzhixin/vue-admin-beautiful')
          break
        case 'gitee':
          // window.open('https://gitee.com/chu1204505056/vue-admin-beautiful')
          this.$refs['edit'].showEdit()
          break
      }
    },
    personalCenter() {
      this.$router.push('/personalCenter/personalCenter')
    },
    logout() {
      this.$baseConfirm(
        '您确定要退出' + this.$baseTitle + '吗?',
        null,
        async () => {
          await this.$store.dispatch('user/logout')
          if (recordRoute) {
            this.delAllVisitedRoutes() //清除tabs
            this.$store.dispatch('tabsBar/delAllVisitedRoutes', this.$route)
            //const fullPath = this.$route.fullPath
            // this.$router.push(`/login?redirect=${fullPath}`)
            this.$router.push('/login')
            this.reload()
          } else {
            this.$router.push('/login')
            this.reload()
          }
        }
      )
    },
  },
}
</script>
<style lang="scss" scoped>
.avatar-dropdown {
  display: flex;
  align-content: center;
  align-items: center;
  justify-content: center;
  justify-items: center;
  height: 50px;
  padding: 0;

  .user-avatar {
    width: 40px;
    height: 40px;
    cursor: pointer;
    border-radius: 50%;
  }

  .user-name {
    position: relative;
    margin-left: 5px;
    margin-left: 5px;
    cursor: pointer;
  }
}
</style>
