import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsCompany/toList.do',
    method: 'post',
    // headers: {
    //   'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    // },
    data,
  })
}

export function getAssetsCompanyList(data) {
  return request({
    url: '/assetsCompany/toList.do',
    method: 'post',
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsCompany/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsCompany/toDelete.do',
    method: 'post',
    data,
  })
}

export function getAllCompanies(data) {
  return request({
    url: '/assetsCompany/getAll.do',
    method: 'post',
    data,
  })
}
