import request from '@/utils/request'

//分页展示公告管理信息
export function toList(data) {
  return request({
    url: '/sysAnnouncement/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toNoticeList(data) {
  return request({
    url: '/sysAnnouncement/toNoticeList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//新增公告管理信息
export function toAdd(data) {
  return request({
    url: '/sysAnnouncement/toAdd.do',
    method: 'post',
    data,
  })
}

//删除公告管理信息
export function toDelete(data) {
  return request({
    url: '/sysAnnouncement/toDelete.do',
    method: 'post',
    params: data,
  })
}
//ID查询公告管理信息
export function getById(data) {
  return request({
    url: '/sysAnnouncement/getById.do',
    method: 'post',
    params: data,
  })
}
