import request from '@/utils/request'

export function getList(data) {
  return request({
    url: '/eventSolutionType/getPage.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function save(data) {
  return request({
    url: '/eventSolutionType/save.do',
    method: 'post',
    data,
  })
}


export function edit(data) {
  return request({
    url: '/eventSolutionType/update.do',
    method: 'post',
    data,
  })
}


export function del(data) {
  return request({
    url: '/eventSolutionType/deleteSysCompany.do?id='+data,
    method: 'post'
  })
}

export function getAllTypeInfo() {
  return request({
    url: '/eventSolutionType/getAllTypeInfo',
    method: 'post',
  })
}





















