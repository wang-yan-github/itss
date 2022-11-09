import request from '@/utils/request'

export function add(data) {
  return request({
    url: '/eventCategory/toAdd.do',
    method: 'post',
    data,
  })
}

export function eventCategoryEdit(data) {
  return request({
    url: '/eventCategory/edit',
    method: 'post',
    data,
  })
}



export function eventCategoryinfo(data) {
  return request({
    url: '/eventCategory/info?id='+data,
    method: 'post'
  })
}

export function eventCategoryTree(data) {
  return request({
    url: '/eventCategory/tree',
    method: 'post',
    data,
  })
}



















