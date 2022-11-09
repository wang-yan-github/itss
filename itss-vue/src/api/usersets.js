import request from '@/utils/request'

export function getusersetsList() {
  return request({
    url: '/usersets/getList',
    method: 'post',
  })
}
