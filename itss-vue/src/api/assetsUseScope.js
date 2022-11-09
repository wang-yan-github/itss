import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsUseScope/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function getAssetsUseScopeList(data) {
  return request({
    url: '/assetsUseScope/toList.do',
    method: 'post',
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsUseScope/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsUseScope/toDelete.do',
    method: 'post',
    data,
  })
}

export function getAllUseScope(data) {
  return request({
    url: '/assetsUseScope/getAll.do',
    method: 'post',
    data,
  })
}
