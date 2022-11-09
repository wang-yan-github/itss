import request from '@/utils/request'

export function workBenchByPage(data) {
  return request({
    url: '/event/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//开单
export function addEvent(data) {
  return request({
    url: '/event/addEvent',
    method: 'post',
    data,
  })
}

//保存并关单
export function closeSave(data) {
  return request({
    url: '/event/closeSave',
    method: 'post',
    data,
  })
}

//转单
export function transferOrder(data) {
  return request({
    url: '/event/transferOrder',
    method: 'post',
    data,
  })
}

//根据ID查询
export function getInfoById(data) {
  return request({
    url: '/event/getInfoById.do',
    method: 'post',
    params: data,
  })
}

//处理保存接口

export function process(data) {
  return request({
    url: '/event/process',
    method: 'post',
    data,
  })
}

//处理保存并关单
export function processClose(data) {
  return request({
    url: '/event/processClose',
    method: 'post',
    data,
  })
}

//事件撤单
export function revoke(data) {
  return request({
    url: '/event/revoke',
    method: 'post',
    data,
  })
}

//事件暂停
export function pause(data) {
  return request({
    url: '/event/pause.do',
    method: 'post',
    data,
  })
}

//事件暂停恢复
export function restore(data) {
  return request({
    url: '/event/restore.do',
    method: 'post',
    data,
  })
}

//响应接口
export function responseEvent(data) {
  return request({
    url: '/event/response',
    method: 'post',
    data,
  })
}

//指派操作
export function designate(data) {
  return request({
    url: '/event/designate',
    method: 'post',
    data,
  })
}


//事件备注
export function updateRemark(data) {
  return request({
    url: '/event/updateRemark',
    method: 'post',
    data,
  })
}

//获取所有事件模板
export function getAllEventTemplate() {
  return request({
    url: '/eventTemplate/getAllEventTemplate',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
  })
}

//获取所有事件紧急度
export function getAllEventFirst() {
  return request({
    url: '/slaEventFirst/getAllEventFirst.do',
    method: 'post',

  })
}

//获取所有事件解决方式
export function getAllTypeInfo() {
  return request({
    url: '/eventSolutionType/getAllTypeInfo',
    method: 'post',
  })
}


//事件统计
export function statis(data) {
  return request({
    url: '/event/statis.do',
    method: 'post',
    data,
  })
}


//事件统计按年
export function eventByYear(data) {
  return request({
    url: '/event/eventByYear.do',
    method: 'post',
    data,
  })
}


//事件统计按月
export function eventByMonth(data) {
  return request({
    url: '/event/eventByMonth.do',
    method: 'post',
    data,
  })
}

//事件统计按类别
export function statisCate(data) {
  return request({
    url: '/event/statisCate.do',
    method: 'post',
    data,
  })
}



//事件统计按类别
export function statisFinish(data) {
  return request({
    url: '/event/statisFinish.do',
    method: 'post',
    data,
  })
}


//扩展报表
export function getSysSql() {
  return request({
    url: '/event/getSysSql.do',
    method: 'post',
  })
}
export function isReopen(data) {
  return request({
    url: '/event/isReopen',
    method: 'post',
    params:data
  })
}

//处理页面 协同处理人 服务群组【处理工程师】
export function getSynergiaUsers(data) {
  return request({
    url: '/event/getSynergiaUsers.do',
    method: 'post',
    params:data
  })
}
