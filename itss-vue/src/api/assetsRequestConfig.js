import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsRequestConfig/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsRequestConfig/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsRequestConfig/toDelete.do',
    method: 'post',
    data,
  })
}
