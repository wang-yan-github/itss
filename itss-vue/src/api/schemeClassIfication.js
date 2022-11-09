import request from '@/utils/request'

export function tree() {
  return request({
    url: '/schemeClassification/tree',
    method: 'post'
  })
}

export function save(data) {
  return request({
    url: '/schemeClassification/save.do',
    method: 'post',
    data,
  })
}

export function edit(data) {
  return request({
    url: '/schemeClassification/update.do',
    method: 'post',
    data,
  })
}

export function del(data) {
  return request({
    url: '/schemeClassification/deleteSysCompany.do?id=' + data,
    method: 'post'
  })
}

export function view(data) {
  return request({
    url: '/schemeClassification/view?id=' + data,
    method: 'post'
  })
}


























