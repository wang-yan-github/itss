import request from '@/utils/request'

export function getPeriodList(data) {
  return request({
    url: '/assetsInventory/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function savePeriod(data) {
  return request({
    url: '/assetsInventory/toAdd.do',
    method: 'post',
    data,
  })
}

export function info(data) {
  return request({
    url: '/eventSource/info?id='+data,
    method: 'post'
  })
}

export function edit(data) {
  return request({
    url: '/assetsInventory/toEdit.do',
    method: 'post',
    data,
  })
}

export function delPeriod(data) {
  return request({
    url: '/assetsInventory/toDelAssetsInventory.do',
    method: 'post',
    data
  })
}

export function getUserInfo(data) {
  return request({
    url: '/sysuser/getPage.do',
    method: 'post',
    data
  })
}

export function saveAssetsInventoryConfig(data) {
  return request({
    url: '/assetsInventoryConfig/toAdd.do',
    method: 'post',
    data,
  })
}

//删除
export function delBatch(data) {
  return request({
    url: '/assetsInventory/delBatch.do',
    method: 'post',
    data,
  })
}
















