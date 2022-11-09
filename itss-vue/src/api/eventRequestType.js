import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eventRequestType/toList.do',
    method: 'post',
    data,
  })
}

export function getEventRequestTypeList(data) {
  return request({
    url: '/eventRequestType/getList.do',
    method: 'post',
    data,
  })
}

export function add(data) {
  return request({
    url: '/eventRequestType/toAdd.do',
    method: 'post',
    data,
  })
}

export function edit(data) {
  return request({
    url: '/eventRequestType/toEdit.do',
    method: 'post',
    data,
  })
}



export function del(data) {
  return request({
    url: '/eventRequestType/del?id='+data,
    method: 'post'
  })
}

//获取客户
export function getKehuType() {
  return request({
    url: '/eventRequestType/getKehuType',
    method: 'post',
  })
}






















