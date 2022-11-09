import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsStatus/toList.do',
    method: 'post',
    // headers:{
    //   'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    // },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsStatus/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsStatus/toDelete.do',
    method: 'post',
    // 'content-type': 'application/json;charset=UTF-8',
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

