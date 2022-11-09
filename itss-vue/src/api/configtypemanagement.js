import request from '@/utils/request'

export function getCIList() {
  return request({
    url: '/assetsClGroup/getAllList.do',
    method: 'post',
  })
}

export function getExtendFormList(data) {
  return request({
    url: '/sysExtendForm/toList.do',
    method: 'post',
    data,
  })
}
//分页展示配置项类型管理
export function assetsTypeByPage(data) {
  return request({
    url: '/assetsType/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}
export function getAssetsTypeList(data) {
  return request({
    url: '/assetsType/getList.do',
    method: 'post',
    data,
  })
}
//配置项类型添加 修改
export function addAssetsType(data) {
  return request({
    url: '/assetsType/toAdd.do',
    method: 'post',
    data,
  })
}
//配置项类型删除
export function delAssetsType(data) {
  return request({
    url: '/assetsType/delAssetsType.do',
    method: 'post',
    params: data,
  })
}
//配置项类型ID查询
export function getAssetsById(data) {
  return request({
    url: '/assetsType/getById.do',
    method: 'post',
    params: data,
  })
}


export function getAssetsType(data) {
  return request({
    url: '/assetsType/getAssetsType.do',
    method: 'post',
    data,
  })
}

export function getAssetsInventoryDetails(data) {
  return request({
    url: '/assetsInventoryDetails/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}
//查询资产处理数据
export function getInventoryDetails(data) {
  return request({
    url: '/assetsInventoryDetails/getInventoryDetails.do',
    method: 'post',
    data,
  })
}
export function doDetailsEdit(data) {
  return request({
    url: '/assetsInventoryDetails/toEdit.do',
    method: 'post',
    data,
  })
}
export function doDeleteInventoryDetails(data) {
  return request({
    url: '/assetsInventoryDetails/doDeleteInventoryDetails.do',
    method: 'post',
    data,
  })
}
//盘点处理
export function toDeal(data) {
  return request({
    url: '/assetsInventoryDetails/toDeal.do',
    method: 'post',
    data,
  })
}
//盘点确认
export function toConfirm(data) {
  return request({
    url: '/assetsInventoryDetails/toConfirm.do',
    method: 'post',
    data,
  })
}

//所属盘点期
export function getAllPanDianQi() {
  return request({
    url: '/assetsInventory/getAllPanDianQi.do',
    method: 'post',
  })
}

//删除
export function delBatch(data) {
  return request({
    url: '/assetsInventoryDetails/delBatch.do',
    method: 'post',
    data,
  })
}
//批量确认
export function confirmBatch(data) {
  return request({
    url: '/assetsInventoryDetails/confirmBatch.do',
    method: 'post',
    data,
  })
}
//资产盘点导出
export function exportExcel(data) {
  return request({
    url: '/assetsInventoryDetails/exportExcel.do',
    method: 'post',
    data,
    responseType: 'blob'
  })
}
