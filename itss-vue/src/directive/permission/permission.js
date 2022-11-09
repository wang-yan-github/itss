import store from '@/store'

function checkPermission(el, binding) {
  const { expression } = binding
  const roles = store.getters['user/authority']
  if (expression) {

    const permissionRoles = expression

    const hasPermission = roles.some(role => {
      return permissionRoles === role
    })

    if (!hasPermission) {
      el.parentNode && el.parentNode.removeChild(el)
    }

  } else {
    throw new Error(`need roles! Like v-permission="['admin','editor']"`)
  }
}

export default {
  inserted(el, binding) {
    checkPermission(el, binding)
  },
  update(el, binding) {
    checkPermission(el, binding)
  }
}
