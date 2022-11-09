import request from '@/utils/request'

//分页展示打印模板管理信息
export function toList(data) {
  return request({
    url: '/sysPrint/toList.do',
    method: 'post',
    data,
  })
}

//添加打印模板管理信息
export function toAdd(data) {
  return request({
    url: '/sysPrint/toAdd.do',
    method: 'post',
    data,
  })
}

//删除打印模板管理信息
export function deleteById(data) {
  return request({
    url: '/sysPrint/deleteById.do',
    method: 'post',
    params: data,
  })
}

//根据ID查询打印模板管理信息
export function getById(data) {
  return request({
    url: '/sysPrint/getById.do',
    method: 'post',
    params: data,
  })
}
