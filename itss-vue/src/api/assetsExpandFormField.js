import request from '@/utils/request'

export function getFieldsByFormId(data) {
    return request({
        url: '/assetsExpandFormField/getFormByType.do',
        method: 'post',
        data,
    })
}

export function getList(data) {
  return request({
    url: '/assetsExpandFormField/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toAdd(data) {
  return request({
    url: '/assetsExpandFormField/toAdd.do',
    method: 'post',
    data,
  })
}

export function toDel(data) {
  return request({
    url: '/assetsExpandFormField/delFormField.do',
    method: 'post',
    data,
  })
}
