/**
 * @description 登录、获取用户信息、退出登录、清除accessToken逻辑，不建议修改
 */

import Vue from 'vue'
import { getUserInfo, login, logout } from '@/api/user'
import { getAccessToken, removeAccessToken, setAccessToken, } from '@/utils/accessToken'
import { resetRouter } from '@/router'
import { title, tokenName } from '@/config'

const state = {
  accessToken: getAccessToken(),
  username: '',
  avatar: '',
  permissions: [],
  authority: {},
}
const getters = {
  accessToken: (state) => state.accessToken,
  username: (state) => state.username,
  avatar: (state) => state.avatar,
  permissions: (state) => state.permissions,
  authority: (state) => state.authority,
}
const mutations = {
  setAccessToken(state, accessToken) {
    state.accessToken = accessToken
    setAccessToken(accessToken)
  },
  setAuthority(state, authority) {
    state.authority = authority
  },
  setUsername(state, username) {
    state.username = username
  },
  setAvatar(state, avatar) {
    state.avatar = avatar
  },
  setPermissions(state, permissions) {
    state.permissions = permissions
  },
}
const actions = {
  setPermissions({ commit }, permissions) {
    commit('setPermissions', permissions)
  },
  async login({ commit }, userInfo) {
    let data = await login(userInfo)
    const accessToken = data.token.tokenValue
    const authority = data.authority.buttonStr;
    const permission = data.authority.menuStr;
    if (accessToken) {
      commit('setAccessToken', accessToken)
      commit('setAuthority', authority)
      //commit('setPermissions', permission)
      const hour = new Date().getHours()
      const thisTime =
        hour < 8
          ? '早上好'
          : hour <= 11
            ? '上午好'
            : hour <= 13
              ? '中午好'
              : hour < 18
                ? '下午好'
                : '晚上好'
      Vue.prototype.$baseNotify(`欢迎登录${title}`, `${thisTime}！`)
    } else {
      Vue.prototype.$baseMessage(
        `登录接口异常，未正确返回${tokenName}...`,
        'error'
      )
    }
  },
  async getUserInfo({ commit, state }) {
    const data = await getUserInfo()
    if (!data) {
      Vue.prototype.$baseMessage('验证失败，请重新登录...', 'error')
      return false
    }
    let { permissions, username, avatar, authorities } = data.data
    if (permissions && username && Array.isArray(permissions)) {
      commit('setPermissions', permissions)
      commit('setAuthority', authorities)
      commit('setUsername', username)
      commit('setAvatar', avatar)
      return permissions
    } else {
      Vue.prototype.$baseMessage('用户信息接口异常', 'error')
      return false
    }
  },
  async logout({ dispatch }) {
    // debugger
    await logout()
    await dispatch('resetAccessToken')
    await resetRouter()
  },
  resetAccessToken({ commit }) {
    commit('setPermissions', [])
    commit('setAccessToken', '')
    removeAccessToken()
  },
}
export default { state, getters, mutations, actions }
