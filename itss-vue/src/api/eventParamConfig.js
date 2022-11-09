import request from '@/utils/request'

//新增
export function doAdd(data) {
  return request({
    url: '/eventParamConfig/toAdd.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//修改
export function doEdit(data) {
  return request({
    url: '/eventParamConfig/toEdit.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//根据ID查询
export function doView(data) {
  return request({
    url: '/eventParamConfig/getById.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    params: data,
  })
}

