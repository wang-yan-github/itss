import request from '@/utils/request'

//配置请求
export function toList(data) {
    return request({
      url: '/assetsConfigReq/toList.do',
      method: 'post',
      headers:{
        'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
      },
      data,
    })
  }

//配置请求
export function getAssetsConfigReq(data) {
  return request({
    url: '/assetsConfigReq/getAssetsConfigReq.do',
    method: 'post',
    data,
  })
}

//配置请求撤销
export function toCancel(data) {
  return request({
    url: '/assetsConfigReq/toCancel.do',
    method: 'post',
    data,
  })
}

//配置请求撤销
export function toResult(data) {
    return request({
      url: '/assetsConfigReq/toResult.do',
      method: 'post',
      data,
    })
  }

//配置编辑
export function toEdit(data) {
  return request({
    url: '/assetsConfigReq/toEdit.do',
    method: 'post',
    data,
  })
}

//配置添加
export function toAdd(data) {
  return request({
    url: '/assetsConfigReq/toAdd.do',
    method: 'post',
    data,
  })
}

// 配置请求类型全部数据
export function getAllAssetsRequestConfig(data) {
  return request({
    url: '/assetsRequestConfig/getAll.do',
    method: 'post',
    data,
  })
}
