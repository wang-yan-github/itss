import router from '@/router'
import store from '@/store'
import VabProgress from 'nprogress'
import 'nprogress/nprogress.css'
import getPageTitle from '@/utils/pageTitle'
import { authentication, loginInterception, progressBar, recordRoute, routesWhiteList, } from '@/config'

VabProgress.configure({
  easing: 'ease',
  speed: 500,
  trickleSpeed: 200,
  showSpinner: false,
})
router.beforeResolve(async (to, from, next) => {
  if (progressBar) VabProgress.start()
  let hasToken = store.getters['user/accessToken']

  if (!loginInterception) hasToken = true

  if (hasToken) {
    if (to.path === '/login') {
      next({ path: '/' })
      if (progressBar) VabProgress.done()
    } else {
      const hasPermissions =
        store.getters['user/permissions'] &&
        store.getters['user/permissions'].length > 0
      if (hasPermissions) {
        next()
      } else {
        try {
          let permissions
          if (!loginInterception) {
            await store.dispatch('user/setPermissions', ['admin'])
            permissions = ['admin']
          } else {
            permissions = await store.dispatch('user/getUserInfo')
          }

          let accessRoutes = []
          if (authentication === 'intelligence') {
            accessRoutes = await store.dispatch('routes/setRoutes', permissions)
            if (!accessRoutes.some(role => role.path === "/")) {

              for (var i = 0; i < accessRoutes.length; i++) {
                var element = accessRoutes[i]
                if (!element.isButton) {
                  if (element.children) {
                    element.children[0].meta.affix = true
                    break;
                  }

                }
              }
              router.addRoutes(accessRoutes)
              next(element.path + "/" + element.children[0].path)
            } else {
              router.addRoutes(accessRoutes)
              next({ ...to, replace: true })
            }
          } else if (authentication === 'all') {
            accessRoutes = await store.dispatch('routes/setAllRoutes')
          }

        } catch {
          await store.dispatch('user/resetAccessToken')
          if (progressBar) VabProgress.done()
        }
      }
    }
  } else {
    if (routesWhiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      if (recordRoute) {
        next(`/login?redirect=${to.path}`)
      } else {
        next('/login')
      }

      if (progressBar) VabProgress.done()
    }
  }
  //document.title = getPageTitle(to.meta.title)
})
router.afterEach(() => {
  if (progressBar) VabProgress.done()
})
