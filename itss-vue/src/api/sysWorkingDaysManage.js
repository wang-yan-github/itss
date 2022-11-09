import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/sysWorkingDays/toList.do',
    method: 'post',
    data,
  })
}

export function getById(data) {
  return request({
    url: '/sysWorkingDays/getById.do',
    method: 'post',
    data,
  })
}

export function toAdd(data) {
  return request({
    url: '/sysWorkingDays/toAdd.do',
    method: 'post',
    headers:{
      'Content-Type':'application/json;charset=UTF-8' //改这里就好了
    },
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/sysWorkingDays/toDelete.do',
    method: 'post',
    data,
  })
}

export function doEdit(data) {
  return request({
    url: '/userManagement/doEdit',
    method: 'post',
    data,
  })
}
