import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eventSchedule/getPage.do',
    method: 'post',
    data,
  })
}

export function add(data) {
  return request({
    url: '/eventSchedule/save.do',
    method: 'post',
    data,
  })
}

export function editEventSchedule(data) {
  return request({
    url: '/eventSchedule/editEventSchedule',
    method: 'post',
    data,
  })
}


export function info(data) {
  return request({
    url: '/eventSchedule/info?id=' + data,
    method: 'post'
  })
}

export function del(data) {
  return request({
    url: '/eventSchedule/del?id=' + data,
    method: 'post'
  })
}

//开启定时任务
export function start(data) {
  return request({
    url: '/eventSchedule/start?id=' + data,
    method: 'post'
  })
}

//暂停定时任务
export function pause(data) {
  return request({
    url: '/eventSchedule/pause?id=' + data,
    method: 'post'
  })
}

//重启定时任务
export function restart(data) {
  return request({
    url: '/eventSchedule/restart?id=' + data,
    method: 'post'
  })
}

//查看日志
export function getLogInfoById(data) {
  return request({
    url: '/eventSchedule/getLogInfoById?id=' + data,
    method: 'post'
  })
}





































