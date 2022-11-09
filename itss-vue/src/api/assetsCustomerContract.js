import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/assetsCustomerContract/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toSave(data) {
  return request({
    url: '/assetsCustomerContract/toSave.do',
    method: 'post',
    data,
  })
}

export function doDelete(data) {
  return request({
    url: '/assetsCustomerContract/toDelete.do',
    method: 'post',
    data,
  })
}

// 配置项信息查询
export function getAssetsManageList(data){
  return request({
    url: '/assetsManage/toList.do',
    method: 'post',
    data,
  })
}
