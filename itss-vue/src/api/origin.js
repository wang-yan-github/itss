import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eventSource/getPage.do',
    method: 'post',
    data,
  })
}

export function getAll(data) {
  return request({
    url: '/eventSource/getList.do',
    method: 'post',
    data,
  })
}

export function save(data) {
  return request({
    url: '/eventSource/save.do',
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
    url: '/eventSource/update.do',
    method: 'post',
    data,
  })
}

export function del(data) {
  return request({
    url: '/eventSource/deleteSysCompany.do?id='+data,
    method: 'post'
  })
}




















