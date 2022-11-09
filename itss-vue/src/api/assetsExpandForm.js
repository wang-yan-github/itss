import request from '@/utils/request'


export function getList(data) {
  return request({
    url: '/assetsExpandForm/toTree.do',
    method: 'post',
    // headers: {
    //   'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    // },
    data,
  })
}


export function addForm(data) {
  return request({
    url: '/assetsExpandForm/toAdd.do',
    method: 'post',
    data,
  })
}


export function toDelete(data) {
  return request({
    url: '/assetsExpandForm/toDelete.do',
    method: 'post',
    data,
  })
}
