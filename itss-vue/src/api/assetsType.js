import request from '@/utils/request'

export function getAllAssetsType(data) {
    return request({
      url: '/assetsType/getAll.do',
      method: 'post',
      data,
    })
  }