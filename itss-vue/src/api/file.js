import request from '@/utils/request'

export function download(data) {
    return request({
        url: '/file/downloadFile.do',
        method: 'post',
        data,
        responseType: 'blob'
    })
}


//分页展示首页背景图管理信息
export function toList(data) {
  return request({
    url: '/file/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}
