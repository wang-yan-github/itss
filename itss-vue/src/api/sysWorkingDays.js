import request from '@/utils/request'

//分页展示工作日信息
export function toList(data) {
  return request({
    url: '/sysWorkingDays/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//获取所有默认状态的工作日信息
export function getAllList() {
  return request({
    url: '/sysWorkingDays/getAllList.do',
    method: 'post',
  })
}

//工作日信息添加
export function toAdd(data) {
  return request({
    url: '/sysWorkingDays/toAdd.do',
    method: 'post',
    data,
  })
}
//根据ID查询工作日信息
export function getById(data) {
  return request({
    url: '/sysWorkingDays/getById.do',
    method: 'post',
    params: data,
  })
}
//删除工作日信息
export function toDelete(data) {
  return request({
    url: '/sysWorkingDays/toDelete.do',
    method: 'post',
    params: data,
  })
}

//日历展示
export function selectCalendar(data) {
  return request({
    url: '/sysWorkingDays/selectCalendar',
    method: 'post',
    data,
  })
}
//日历修改
export function setCalendar(data) {
  return request({
    url: '/sysWorkingDays/setCalendar',
    method: 'post',
    data,
  })
}

//获取工作时间
export function getWorkTime(data) {
  return request({
    url: '/sysWorkingDays/getWorkTime.do',
    method: 'post',
    data,
  })
}
