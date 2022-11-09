import request from '@/utils/request'


export function toList(data) {
    return request({
      url: '/assetsSupplierContract/toList.do',
      method: 'post',
      headers:{
        'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
      },
      data,
    })
}

export function toSave(data) {
    return request({
      url: '/assetsSupplierContract/toSave.do',
      method: 'post',
      data,
    })
  }


  export function doDelete(data) {
    return request({
      url: '/assetsSupplierContract/toDelete.do',
      method: 'post',
      data,
    })
  }
