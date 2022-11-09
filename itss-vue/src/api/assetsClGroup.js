import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsClGroup/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function overviewList(data) {
  return request({
    url: '/assetsClGroup/overviewList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsClGroup/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsClGroup/toDelete.do',
    method: 'post',
    data,
  })
}
