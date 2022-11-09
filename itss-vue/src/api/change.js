import request from '@/utils/request'

//我的变更列表 分页查询
export function toList(data) {
  return request({
    url: '/changeInfo/toList.do',
    method: 'post',
    data,
  })
}

//变更待办 分页查询
export function toNeedList(data) {
  return request({
    url: '/changeInfo/toNeedList.do',
    method: 'post',
    data,
  })
}

//变更管理 分页查询
export function getPage(data) {
  return request({
    url: '/changeInfo/getPage.do',
    method: 'post',
    data,
  })
}

//我参与的 分页查询
export function getParticipatingPageList(data) {
  return request({
    url: '/changeInfo/getParticipatingPageList.do',
    method: 'post',
    data,
  })
}

// 我的变更添加
export function toAdd(data) {
  return request({
    url: '/changeInfo/toAdd.do',
    method: 'post',
    data,
  })
}

// 我的变更审批
export function toApprove(data) {
  return request({
    url: '/changeInfo/toApprove.do',
    method: 'post',
    data,
  })
}

// 我的变更实施
export function toImplement(data) {
  return request({
    url: '/changeInfo/toImplement.do',
    method: 'post',
    data,
  })
}

// 我的变更实施评审
export function toImplementApprove(data) {
  return request({
    url: '/changeInfo/toImplementApprove.do',
    method: 'post',
    data,
  })
}

// 制定方案
export function toProgramme(data) {
  return request({
    url: '/changeInfo/toProgramme.do',
    method: 'post',
    data,
  })
}

// 方案审核
export function toProgrammeApprove(data) {
  return request({
    url: '/changeInfo/toProgrammeApprove.do',
    method: 'post',
    data,
  })
}


// 我的变更编辑
export function toEdit(data) {
  return request({
    url: '/changeInfo/toEdit.do',
    method: 'post',
    data,
  })
}

// 我的变更添加备注
export function toRemark(data) {
  return request({
    url: '/changeInfo/toRemark.do',
    method: 'post',
    data,
  })
}

// 我的变更修改关联
export function toEditRelevance(data) {
  return request({
    url: '/changeInfo/toEditRelevance.do',
    method: 'post',
    data,
  })
}

// 我的变更撤销
export function toCancel(data) {
  return request({
    url: '/changeInfo/toCancel.do',
    method: 'post',
    data,
  })
}

// 我的变更转办
export function toTransForm(data) {
  return request({
    url: '/changeInfo/toTransForm.do',
    method: 'post',
    data,
  })
}

// 我的变更转单
export function toTransOrder(data) {
  return request({
    url: '/changeInfo/toTransOrder.do',
    method: 'post',
    data,
  })
}

// 我的变更挂起
export function toSuspend(data) {
  return request({
    url: '/changeInfo/toSuspend.do',
    method: 'post',
    data,
  })
}


//我的变更导出
export function exportExcel(data) {
  return request({
    url: '/changeInfo/exportExcel.do',
    method: 'post',
    data,
    dataType: 'json',
    contentType: 'application/json',
    responseType: 'blob'
  })
}

//变更待办导出
export function exportNeedExcel(data) {
  return request({
    url: '/changeInfo/exportNeedExcel.do',
    method: 'post',
    data,
    dataType: 'json',
    contentType: 'application/json',
    responseType: 'blob'
  })
}

//变更管理导出
export function exportManagerExcel(data) {
  return request({
    url: '/changeInfo/exportManagerExcel.do',
    method: 'post',
    data,
    dataType: 'json',
    contentType: 'application/json',
    responseType: 'blob'
  })
}

// 变更类别全部数据
export function getAllChangeType(data) {
  return request({
    url: '/changeType/getAll.do',
    method: 'post',
    data,
  })
}

// 变更类型全部数据
export function getDict(data) {
  return request({
    url: '/sysDict/getDicts.do',
    method: 'post',
    data,
  })
}

// 变更来源全部数据
export function getChangeSource(data) {
  return request({
    url: '/changeSource/getList.do',
    method: 'post',
    data,
  })
}

// 获取用户
export function getUserList() {
  return request({
    url: '/sysuser/getUserList.do',
    method: 'get',
  })
}

// 获取事件
export function getEventList(data) {
  return request({
    url: '/event/toList.do',
    method: 'post',
    data,
  })
}

// 根据变更类别和变更类型获取审批人等信息
export function getChangeApproveUser(data) {
  return request({
    url: '/changeType/getApproveName.do',
    method: 'post',
    params: data,
  })
}

// 根据id获取变更信息
export function getChange(data) {
  return request({
    url: '/changeInfo/getChange.do',
    method: 'post',
    data,
  })
}

// 删除变更
export function deleteChange(data) {
  return request({
    url: '/changeInfo/deleteChange.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}



export function sourcesOfStatistics(data) {
  return request({
    url: '/changeInfo/sourcesOfStatistics.do',
    method: 'post',
    data,
  })
}

export function categoriesOfStatistical(data) {
  return request({
    url: '/changeInfo/categoriesOfStatistical.do',
    method: 'post',
    data,
  })
}

export function typesOfStatistical(data) {
  return request({
    url: '/changeInfo/typesOfStatistical.do',
    method: 'post',
    data,
  })
}
