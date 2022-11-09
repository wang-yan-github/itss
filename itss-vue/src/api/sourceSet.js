import request from '@/utils/request'

//变更来源分页查询
export function getList(data) {
  return request({
    url: '/changeSource/getPageList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//删除来源
export function doDelete(data) {
    return request({
      url: '/changeSource/deleteSource.do',
      method: 'post',
      params: data,
    })
  }

//变更来源修改 新增
export function saveOrUpd(data) {
    return request({
      url: '/changeSource/saveOrUpd.do',
      method: 'post',
      data,
    })
}


//变更类别树状查询
export function getParentList(data) {
  return request({
    url: '/changeType/getParentList.do',
    method: 'post',
    data,
  })
}

//变更类别添加
export function doEdit(data) {
  return request({
    url: '/changeType/saveOrUpd.do',
    method: 'post',
    data,
  })
}

//变更类别删除
export function deleteChangeType(data) {
  return request({
    url: '/changeType/deleteChangeType.do',
    method: 'post',
    params: data,
  })
}

//变更类别查询
export function getById(data) {
  return request({
    url: '/changeType/getById.do',
    method: 'post',
    params: data,
  })
}

//变更通知查询
export function getOneByType(data) {
  return request({
    url: '/changeNoticeConfig/getOneByType.do',
    method: 'post',
    data,
  })
}

//变更通知修改
export function updateConfig(data) {
  return request({
    url: '/changeNoticeConfig/saveOrUpd.do',
    method: 'post',
    data,
  })
}

//根据用户得到服务群组的相关信息
export function getServerGroupByUser(data) {
  return request({
    url: '/eventServiceManage/getEventServiceManageByUserIds',
    method: 'post',
    data,
  })
}
