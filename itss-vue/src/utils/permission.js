import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(value) {
  if (value) {
    const roles = store.getters['user/authority']
    const permissionRoles = value

    const hasPermission = roles.some(role => {
      return permissionRoles === role
    })
    return hasPermission
  } else {
    console.error(`need roles! Like v-permission="['admin','editor']"`)
    return false
  }
}
