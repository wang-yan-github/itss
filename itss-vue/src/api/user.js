import request from '@/utils/request'
import {encryptedData} from '@/utils/encrypt'
import {loginRSA} from '@/config'

export async function login(data) {
  if (loginRSA) {
    data = await encryptedData(data)
  }
  return request({
    url: '/login',
    method: 'post',
    data,
  })
}

export function getUserInfo() {
  return request({
    url: '/sysuser/userInfo.do',
    method: 'post',
  })
}

export function logout() {
  return request({
    url: '/logout.do',
    method: 'post',
  })
}

export function register() {
  return request({
    url: '/register',
    method: 'post',
  })
}


export function getUserList(data) {
  return request({
    url: '/sysuser/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//获取当前登陆用户信息
export function getCurrentLoginUserInfo() {
  return request({
    url: '/sysuser/getCurrentLoginUserInfo.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
  })
}


export function updatePass(data) {
  return request({
    url: '/sysuser/updatePass',
    method: 'post',
    data,
  })
}
