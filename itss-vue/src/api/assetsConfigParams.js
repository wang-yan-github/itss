import request from '@/utils/request'


export function getOne() {
  return request({
    url: '/assetsConfigParams/getOne.do',
    method: 'post',
  })
}

export function updateById(data) {
  return request({
    url: '/assetsConfigParams/updateById.do',
    method: 'post',
    data,
  })
}


export function getAllFormList(data) {
  return request({
    url: '/assetsExpandForm/toList.do',
    method: 'post',
    data,
  })
}

