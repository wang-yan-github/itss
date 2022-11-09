<template>
  <div class="nav-bar-container">
    <el-row :gutter="15">
      <el-col :xs="4" :sm="12" :md="12" :lg="12" :xl="12">
        <div class="left-panel">
          <i
            :class="collapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'"
            :title="collapse ? '展开' : '收起'"
            class="fold-unfold"
            @click="handleCollapse"
          ></i>
          <!-- <vab-breadcrumb class="hidden-xs-only" /> -->
        </div>
      </el-col>
      <el-col :xs="20" :sm="12" :md="12" :lg="12" :xl="12">
        <div class="right-panel">
          <div class="he-plugin-simple-div">
            <div id="he-plugin-simple"></div>
          </div>

          <!-- <vab-error-log /> -->
          <vab-full-screen-bar @refresh="refreshRoute" />
          <vab-theme-bar class="hidden-xs-only" />
          <vab-icon
            title="重载所有路由"
            :pulse="pulse"
            :icon="['fas', 'redo']"
            @click="refreshRoute"
          />
          <vab-avatar />
          <!--  <vab-icon
            title="退出系统"
            :icon="['fas', 'sign-out-alt']"
            @click="logout"
          />-->
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'

export default {
  name: 'VabNavBar',
  data() {
    return {
      pulse: false,
    }
  },
  created() {
    window.WIDGET = {
      "CONFIG": {
        "modules": "01234",
        "background": "5",
        "tmpColor": "606266",
        "tmpSize": "14",
        "cityColor": "606266",
        "citySize": "14",
        "aqiColor": "606266",
        "aqiSize": "14",
        "weatherIconSize": "22",
        "alertIconSize": "18",
        "padding": "6px 6px 6px 6px",
        "shadow": "1",
        "language": "zh",
        "fixed": "false",
        "vertical": "top",
        "horizontal": "left",
        "key": "0021ffe29b1847e8bb33764ad0ebe4ea"
      }
    }

  },
  mounted(){
    (function (d) {
      var c = d.createElement('link')
      c.rel = 'stylesheet'
      c.href = 'https://widget.qweather.net/simple/static/css/he-simple.css?v=1.4.0'
      // c.href = '../../../styles/he-simple.css'
      var s = d.createElement('script')
      s.src = 'https://widget.qweather.net/simple/static/js/he-simple.js?v=1.4.0'
      // s.src = '../../../utils/he-simple.js'
      var sn = d.getElementsByTagName('script')[0]
      sn.parentNode.insertBefore(c, sn)
      sn.parentNode.insertBefore(s, sn)
    })(document)
  },
  computed: {
    ...mapGetters({
      collapse: 'settings/collapse',
      visitedRoutes: 'tabsBar/visitedRoutes',
      device: 'settings/device',
      routes: 'routes/routes',
    }),
  },
  methods: {
    ...mapActions({
      changeCollapse: 'settings/changeCollapse',
    }),
    handleCollapse() {
      this.changeCollapse()
    },
    async refreshRoute() {
      this.$baseEventBus.$emit('reload-routerview')
      this.pulse = true
      setTimeout(() => {
        this.pulse = false
      }, 1000)
    },
  },
}
</script>

<style lang="scss" scoped>
  .he-plugin-simple-div{
    position: fixed;
    top: 14px;
    right: 156px;
    z-index: 9999;
    border-radius: 6px;
    background-color: #f2f8fd;
    display: flex;
  }
.nav-bar-container {
  position: relative;
  height: $base-nav-bar-height;
  padding-right: $base-padding;
  padding-left: $base-padding;
  overflow: hidden;
  user-select: none;
  background: $base-color-white;
  box-shadow: $base-box-shadow;

  .left-panel {
    display: flex;
    align-items: center;
    justify-items: center;
    height: $base-nav-bar-height;

    .fold-unfold {
      color: $base-color-gray;
      cursor: pointer;
    }

    ::v-deep {
      .breadcrumb-container {
        margin-left: 10px;
      }
    }
  }

  .right-panel {
    display: flex;
    align-content: center;
    align-items: center;
    justify-content: flex-end;
    height: $base-nav-bar-height;

    ::v-deep {
      svg {
        width: 1em;
        height: 1em;
        margin-right: 15px;
        font-size: $base-font-size-small;
        color: $base-color-gray;
        cursor: pointer;
        fill: $base-color-gray;
      }

      button {
        svg {
          margin-right: 0;
          color: $base-color-white;
          cursor: pointer;
          fill: $base-color-white;
        }
      }

      .el-badge {
        margin-right: 15px;
      }
    }
  }
}
</style>
