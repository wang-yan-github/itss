import request from '@/utils/request'

export function getAssetsConfigStatusList(data) {
  return request({
    url: '/assetsConfigStatus/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//增加 修改
export function toAdd(data) {
  return request({
    url: '/assetsConfigStatus/toAdd.do',
    method: 'post',
    data,
  })
}

//删除
export function delAssetsConfigStatus(data) {
  return request({
    url: '/assetsConfigStatus/delAssetsConfigStatus.do',
    method: 'post',
    params: data,
  })
}
//ID查询
export function getById(data) {
  return request({
    url: '/assetsConfigStatus/getById.do',
    method: 'post',
    params: data,
  })
}



export function toEdit(data) {
  return request({
    url: '/assetsConfigStatus/toEdit.do',
    method: 'post',
    data,
  })
}
