import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsSoftwareLicence/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsSoftwareLicence/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsSoftwareLicence/toDelete.do',
    method: 'post',
    data,
  })
}

export function getAllSoftWare(data) {
  return request({
    url: '/assetsSoftwareLicence/getAllSoftWare.do',
    method: 'post',
    data,
  })
}

export function getAssetsManageList() {
  return request({
    url: '/assetsSoftwareLicence/getAssetsManageList.do',
    method: 'post',
  })
}
