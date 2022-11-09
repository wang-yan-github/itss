import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsBrand/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsBrand/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsBrand/toDelete.do',
    method: 'post',
    data,
  })
}

export function getAllBrand(data) {
  return request({
    url: '/assetsBrand/getAll.do',
    method: 'post',
    data,
  })
}

export function getAssetsBrandList(data) {
  return request({
    url: '/assetsBrand/getList.do',
    method: 'post',
    data,
  })
}
