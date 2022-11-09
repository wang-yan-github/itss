import request from '@/utils/request'

//分页展示首页背景图管理信息
export function toList(data) {
  return request({
    url: '/sysBackgroundPic/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//添加 修改背景图管理信息
export function toAdd(data) {
  return request({
    url: '/sysBackgroundPic/toAdd.do',
    method: 'post',
    data,
  })
}

//删除背景图管理信息
export function toDelete(data) {
  return request({
    url: '/sysBackgroundPic/toDelete.do',
    method: 'post',
    params: data,
  })
}

//删除背景图管理信息
export function getById(data) {
  return request({
    url: '/sysBackgroundPic/getById.do',
    method: 'post',
    params: data,
  })
}

//展示配置信息
export function showSetting() {
  return request({
    url: '/sysBackgroundPic/showSetting.do',
    method: 'post',
  })
}

//修改配置信息
export function updateSetting(data) {
  return request({
    url: '/sysBackgroundPic/updateSetting.do',
    method: 'post',
    data,
  })
}
