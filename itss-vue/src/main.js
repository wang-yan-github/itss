import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import './plugins'
import '@/layouts/export'
//引用富文本编辑器
import VueQuillEditor from 'vue-quill-editor'
//引入富文本css
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import permission from '@/directive/permission/index.js'
//富文本编辑器添加实例
Vue.use(VueQuillEditor)

//打印
import Print from 'vue-print-nb'
Vue.use(Print)


/**
 * @description 生产环境默认都使用mock，如果正式用于生产环境时，记得去掉
 */

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('@/utils/static')
  mockXHR()
}

Vue.config.productionTip = false

new Vue({
  el: '#vue-itss',
  router,
  store,
  directives: { permission },
  render: (h) => h(App),
})
