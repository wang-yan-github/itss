import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eventTemplate/getPage.do',
    method: 'post',
    data,
  })
}

export function getMyList(data) {
  return request({
    url: '/eventTemplate/getMyPage.do',
    method: 'post',
    data,
  })
}

export function upRequestList() {
  return request({
    url: '/eventRequestType/upList',
    method: 'post'
  })
}

export function upEventSourceList() {
  return request({
    url: '/eventSource/upEventSourceList',
    method: 'post'
  })
}


export function slaEventUrgentList() {
  return request({
    url: '/slaEventUrgent/upSlaEventImpactUrgent',
    method: 'post'
  })
}

export function upSlaEventImpactList() {
  return request({
    url: '/slaEventImpact/upSlaEventImpact',
    method: 'post'
  })
}

export function upEventSolutionTypeList() {
  return request({
    url: '/eventSolutionType/upEventSolutionType',
    method: 'post'
  })
}

export function save(data) {
  return request({
    url: '/eventTemplate/save.do',
    method: 'post',
    data,
  })
}

export function eventTemplateInfo(data) {
  return request({
    url: '/eventTemplate/eventTemplateInfo?id=' + data,
    method: 'post'
  })
}

export function edit(data) {
  return request({
    url: '/eventTemplate/update.do',
    method: 'post',
    data,
  })
}


export function del(data) {
  return request({
    url: '/eventTemplate/del?id=' + data,
    method: 'post'
  })
}



































