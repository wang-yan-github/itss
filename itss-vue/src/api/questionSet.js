import request from '@/utils/request'

//问题来源分页
export function getList(data) {
  return request({
    url: '/questionSource/getPageList.do',
    method: 'post',
    data,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
  })
}


//新增问题来源
export function doEdit(data) {
  return request({
    url: '/questionSource/saveOrUpd.do',
    method: 'post',
    data,
  })
}

//删除
export function doDelete(data) {
  return request({
    url: '/questionSource/deleteSource.do',
    method: 'post',
    params: data,
  })
}

//树状展示问题类别
export function getParentList(data) {
  return request({
    url: '/questionCategory/getParentList.do',
    method: 'post',
    data,

  })
}


//新增问题类别
export function saveOrUpd(data) {
  return request({
    url: '/questionCategory/saveOrUpd.do',
    method: 'post',
    data,
  })
}

//删除问题类别
export function deleteCategory(data) {
  return request({
    url: '/questionCategory/deleteCategory.do',
    method: 'post',
    params: data,
  })
}

//问题通知配置查看
export function getOneInfo(data) {
  return request({
    url: '/questionNoticeConfig/getOneInfo.do',
    method: 'post',
    data,
  })
}


//问题通知配置修改
export function saveOrUpdConfig(data) {
  return request({
    url: '/questionNoticeConfig/saveOrUpd.do',
    method: 'post',
    data,
  })
}

//问题类别查询
export function getById(data) {
  return request({
    url: '/questionCategory/getById.do',
    method: 'post',
    params: data,
  })
}
