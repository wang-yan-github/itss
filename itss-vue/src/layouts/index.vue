<template>
  <div class="vue-itss-wrapper" :class="classObj">
    <div
      v-if="'horizontal' === layout"
      class="layout-container-horizontal"
      :class="{
        fixed: header === 'fixed',
        'no-tabs-bar': tabsBar === 'false' || tabsBar === false,
      }"
    >
      <div :class="header === 'fixed' ? 'fixed-header' : ''">
        <vab-top-bar />
        <div
          v-if="tabsBar === 'true' || tabsBar === true"
          :class="{ 'tag-view-show': tabsBar }"
        >
          <div class="vab-main">
            <vab-tabs-bar />
          </div>
        </div>
      </div>
      <div class="vab-main main-padding">
        <!-- <vab-ad /> -->
        <vab-app-main />
      </div>
    </div>
    <div
      v-else
      class="layout-container-vertical"
      :class="{
        fixed: header === 'fixed',
        'no-tabs-bar': tabsBar === 'false' || tabsBar === false,
      }"
    >
      <div
        v-if="device === 'mobile' && collapse === false"
        class="mask"
        @click="handleFoldSideBar"
      />
      <vab-side-bar />
      <div class="vab-main" :class="collapse ? 'is-collapse-main' : ''">
        <div :class="header === 'fixed' ? 'fixed-header' : ''">
          <vab-nav-bar />
          <vab-tabs-bar v-if="tabsBar === 'true' || tabsBar === true" />
        </div>
        <!-- <vab-ad /> -->
        <vab-app-main />
      </div>
    </div>
    <el-backtop />
  </div>
</template>

<script>
  import { mapActions, mapGetters } from 'vuex'
  import { tokenName } from '@/config'
  export default {
    name: 'Layout',
    data() {
      return { oldLayout: '' }
    },
    computed: {
      ...mapGetters({
        layout: 'settings/layout',
        tabsBar: 'settings/tabsBar',
        collapse: 'settings/collapse',
        header: 'settings/header',
        device: 'settings/device',
      }),
      classObj() {
        return {
          mobile: this.device === 'mobile',
        }
      },
    },
    beforeMount() {
      window.addEventListener('resize', this.handleResize)
    },
    beforeDestroy() {
      window.removeEventListener('resize', this.handleResize)
    },
    mounted() {
      this.oldLayout = this.layout
      const userAgent = navigator.userAgent
      // if (userAgent.includes('Juejin')) {
      //   this.$baseAlert(
      //     '本系统不支持在掘金内置浏览器演示。'
      //   )
      // }
      const isMobile = this.handleIsMobile()
      if (isMobile) {
        if (isMobile) {
          //横向布局时如果是手机端访问那么改成纵向版
          this.$store.dispatch('settings/changeLayout', 'vertical')
        } else {
          this.$store.dispatch('settings/changeLayout', this.oldLayout)
        }
          this.$store.dispatch('settings/toggleDevice', 'mobile')
        setTimeout(() => {
          this.$store.dispatch('settings/foldSideBar')
        }, 2000)
      } else {
          this.$store.dispatch('settings/openSideBar')
      }
      this.$nextTick(() => {
        window.addEventListener(
          'storage',
          (e) => {
            if (e.key === tokenName || e.key === null) window.location.reload()
            if (e.key === tokenName && e.value === null)
              window.location.reload()
          },
          false
        )
      })
    },
    methods: {
      ...mapActions({
        handleFoldSideBar: 'settings/foldSideBar',
      }),
      handleIsMobile() {
        return document.body.getBoundingClientRect().width - 1 < 992
      },
      handleResize() {
        if (!document.hidden) {
          const isMobile = this.handleIsMobile()
          if (isMobile) {
            //横向布局时如果是手机端访问那么改成纵向版
            this.$store.dispatch('settings/changeLayout', 'vertical')
          } else {
            this.$store.dispatch('settings/changeLayout', this.oldLayout)
          }

          this.$store.dispatch(
            'settings/toggleDevice',
            isMobile ? 'mobile' : 'desktop'
          )
        }
      },
    },
  }
</script>

<style lang="scss" scoped>
  @mixin fix-header {
    position: fixed;
    top: 0;
    right: 0;
    left: 0;
    z-index: $base-z-index - 2;
    width: 100%;
    overflow: hidden;
  }

  .vue-itss-wrapper {
    position: relative;
    width: 100%;
    height: 100%;

    .layout-container-horizontal {
      position: relative;

      &.fixed {
        padding-top: calc(#{$base-top-bar-height} + #{$base-tabs-bar-height});
      }

      &.fixed.no-tabs-bar {
        padding-top: $base-top-bar-height;
      }

      ::v-deep {
        .vab-main {
          width: 96%;
          margin: auto;
        }

        .fixed-header {
          @include fix-header;
        }

        .tag-view-show {
          background: $base-color-white;
          box-shadow: $base-box-shadow;
        }

        .nav-bar-container {
          .fold-unfold {
            display: none;
          }
        }

        .main-padding {
          .app-main-container {
            margin-top: $base-padding;
            background: $base-color-white;
          }
        }
      }
    }

    .layout-container-vertical {
      position: relative;

      .mask {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        z-index: $base-z-index - 1;
        width: 100%;
        height: 100vh;
        overflow: hidden;
        background: #000;
        opacity: 0.5;
      }

      &.fixed {
        padding-top: calc(#{$base-nav-bar-height} + #{$base-tabs-bar-height});
      }

      &.fixed.no-tabs-bar {
        padding-top: $base-nav-bar-height;
      }

      .vab-main {
        position: relative;
        min-height: 100%;
        margin-left: $base-left-menu-width;
        background: #f6f8f9;
        transition: $base-transition;

        ::v-deep {
          .fixed-header {
            @include fix-header;

            left: $base-left-menu-width;
            width: $base-right-content-width;
            box-shadow: $base-box-shadow;
            transition: $base-transition;
          }

          .nav-bar-container {
            position: relative;
            box-sizing: border-box;
          }

          .tabs-bar-container {
            box-sizing: border-box;
            .tabs-content{
              .el-tabs__header{
                .el-tabs__item{
                  height: 38px;
                  margin-right: -18px;
                  margin-top: 6px;
                  line-height: 38px;
                  border: 0px solid #dcdfe6;
                  border-radius: 0px;
                  padding:0 30px;
                }
                .el-tabs__item:hover{
                  background:url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANoAAAAkCAYAAADvhSSyAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3NpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQ4IDc5LjE2NDAzNiwgMjAxOS8wOC8xMy0wMTowNjo1NyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowNWUxOGE3My1iNTI4LTBlNDMtOTk3Zi0yNTE2ZGMzNzU3MzMiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6QkM5QjIxQzk1QTVCMTFFRDg1RkI4REYzQjZDN0U3OEMiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6QkM5QjIxQzg1QTVCMTFFRDg1RkI4REYzQjZDN0U3OEMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIDIxLjAgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6NGNjYzE4YTktY2JlNS1kNDQwLWFkOTEtZTUxMjY0YTI5OTVhIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjA1ZTE4YTczLWI1MjgtMGU0My05OTdmLTI1MTZkYzM3NTczMyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PnsDEAMAAAE5SURBVHja7N3hTcJAGIBhYYKO0A2UCegGsoFsIE7ACDqCGzCCOAG6gNE/rqHfJYeJSS20HPyQ50m+kP4gJJe8uSskZfT28XkxwFVME3MdU+Vr+K/eY15iXmMe83UvowGhLWKWOTA4R3cxD33eMO75AauYe5Fx5lIDm2OFlnaymTWGn9unRemjY50LtpPBb5N8/1ZkR5uJDP5so9jRcWo9odVlydB8fQ/tmpKh1dYTWlUlQwMOIDQQGggNEBoIDYQGCA2EBggNhAZCA4QGQgOhAUIDoQFCg9OoSoTmeSHQrS4RmoemQrebQ0NLpd5aR+g037WrjXecOz1nH/a7R3saEto2MsdG2P8+bdUntCbXObd20EvamDZt7aQ/udj+kWCaqV0MiljHPOfXdQrty5rAcfnBGk7gW4ABABlOKX4aGx7IAAAAAElFTkSuQmCC');
                  background-size: 100% 100%;
                  color:#515a6e!important;
                }
                .el-tabs__item.is-active{
                  color: #1890ff;
                  background: url('data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAANoAAAAkCAYAAADvhSSyAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA3NpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTQ4IDc5LjE2NDAzNiwgMjAxOS8wOC8xMy0wMTowNjo1NyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDowNWUxOGE3My1iNTI4LTBlNDMtOTk3Zi0yNTE2ZGMzNzU3MzMiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OThGOEY1REQ1QTU4MTFFRDhCMzQ4RDZFOEY4NEZGNEYiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OThGOEY1REM1QTU4MTFFRDhCMzQ4RDZFOEY4NEZGNEYiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIDIxLjAgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6MDVlMThhNzMtYjUyOC0wZTQzLTk5N2YtMjUxNmRjMzc1NzMzIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjA1ZTE4YTczLWI1MjgtMGU0My05OTdmLTI1MTZkYzM3NTczMyIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/PvtlTP0AAAE6SURBVHja7N3BTQJBFIBhsIItYTtQKmA7cDuQErACS9AS7IASxArUDjh69OAd3uoQD67L7jpw0O9LXggJp0n+zA4kw/T1fTsZ4SKmirmMKdJ7+Ks2Mc8xLzH36f0g0xGhLWNuUmDwH13H3B0ztFVMbZ3hY4eb9f3w2cCdTGTwdXxa5t7Rypgnj4vwzSztbll2tFpk8GMb2R4d59YTWp3nDM3X99CuyhlaaT2hVZEzNOAXhAZCA6EBQgOhgdAAoYHQAKGB0EBogNBAaCA0QGggNEBocBpFjtDcFwLdyhyhuTQVul0d+sChC1SbUl2cCt3eJp8XqW7G7GhNXLcig15ntIcxj477yDw2Qv9z2mpIaFWqc2HtYJA6HbUWbWe0/R8JNjO3i0EW65jH9LpuQttaEzguP1jDCewEGAAsCTGF0HfHGgAAAABJRU5ErkJggg==');
                  background-size: 100% 100%;
                }
              }
            }
          }

          .app-main-container {
            width: calc(100% - #{$base-padding} - #{$base-padding});
            margin: $base-padding auto;
            background: $base-color-white;
            border-radius: $base-border-radius;
          }
        }

        &.is-collapse-main {
          margin-left: $base-left-menu-width-min;

          ::v-deep {
            .fixed-header {
              left: $base-left-menu-width-min;
              width: calc(100% - 65px);
            }
          }
        }
      }
    }

    /* 手机端开始 */
    &.mobile {
      ::v-deep {
        .el-pager,
        .el-pagination__jump {
          display: none;
        }

        .layout-container-vertical {
          .el-scrollbar.side-bar-container.is-collapse {
            width: 0;
          }

          .vab-main {
            width: 100%;
            margin-left: 0;
          }
        }

        .vab-main {
          .fixed-header {
            left: 0 !important;
            width: 100% !important;
          }
        }
      }
    }

    /* 手机端结束 */
  }
</style>
