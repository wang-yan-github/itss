import request from '@/utils/request'

export function getAssetsManageList(data) {
  return request({
    url: '/assetsManage/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function getAllAssetsManageList(data) {
  return request({
    url: '/assetsManage/getList.do',
    method: 'post',
    data,
  })
}

export function toAssetsManageAdd(data) {
  return request({
    url: '/assetsManage/toAdd.do',
    // headers: {
    //   'Content-Type': 'multipart/form-data'      //改这里就好了
    // },
    method: 'post',
    data,
  })
}

export function toAssetsManageEdit(data) {
  return request({
    url: '/assetsManage/toEdit.do',
    method: 'post',
    data,
  })
}

export function getAssetsManageById(data) {
  return request({
    url: '/assetsManage/getById.do',
    method: 'post',
    data,
  })
}

export function changePrint(data) {
  return request({
    url: '/assetsManage/changePrint.do',
    method: 'post',
    data,
  })
}

export function delAssets(data) {
  return request({
    url: '/assetsManage/delIds.do',
    method: 'post',
    data,
  })
}

export function getAssetsByType(data) {
  return request({
    url: '/assetsManage/getAssetsByType.do',
    method: 'post',
    data,
  })
}
export function historyPage(data) {
  return request({
    url: '/assetsManageHistory/toList.do',
    method: 'post',
    data,
  })
}

export function getSeats(data) {
  return request({
    url: '/assetsManage/getSeats.do',
    method: 'post',
    data,
  })
}

export function getTypeIdByName(data) {
  return request({
    url: '/assetsType/' + data + '/getByName.do',
    method: 'post',
    data,
  })
}


export function exportTemplate(data) {
  return request({
    url: '/assetsManage/exportTemplate.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
    responseType: 'blob'
  })

}
export function exportAssestExcelTemplate() {
  return request({
    url: '/assetsManage/exportAssetsExcelTemplate.do',
    method: 'post',
    responseType: 'blob'
  })
}


export function importAssetsList(data) {
  return request({
    url: '/assetsManage/importAssetsList',
    method: 'post',
    data,
  })
}
