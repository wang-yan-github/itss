import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsOsLicence/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function getAssetsOsLicenceList(data) {
  return request({
    url: '/assetsOsLicence/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsOsLicence/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsOsLicence/toDelete.do',
    method: 'post',
    data,
  })
}

export function getAllOsLicence(data) {
  return request({
    url: '/assetsOsLicence/getAll.do',
    method: 'post',
    data,
  })
}
