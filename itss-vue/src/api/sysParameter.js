import request from '@/utils/request'

//展示系统参数设置数据
export function getOneInfo() {
  return request({
    url: '/sysParameter/getOneInfo.do',
    method: 'post',
  })
}

//修改系统参数设置数据
export function updateOne(data) {
  return request({
    url: '/sysParameter/updateOne.do',
    method: 'post',
    data,
  })
}
