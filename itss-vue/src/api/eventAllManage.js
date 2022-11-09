import request from '@/utils/request'

// 查询全部事件列表
export function getList(data) {
  return request({
    url: '/eventAll/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

// 协查事件列表
export function toXieChaList(data) {
  return request({
    url: '/eventAll/toXieChaList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

// 查询一个事件
export function getById(data) {
  return request({
    url: '/eventAll/' + data.id + '/getById',
    method: 'post',
    data,
  })
}

//eventSource 来源字典
export function getSourceMap(data) {
  return request({
    url: '/eventSource/getList.do',
    method: 'post',
    data,
  })
}

//eventRequestType 请求类型字典
export function getRequestTypeMap(data) {
  return request({
    url: '/eventRequestType/getList.do',
    method: 'post',
    data,
  })
}

//getList 影响度字典
export function getEventImpactMap(data) {
  return request({
    url: '/slaEventImpact/getList.do',
    method: 'post',
    data,
  })
}

//得到问题列表
export function getQuestionList(data) {
  return request({
    url: '/questionManage/getManageByPage.do',
    method: 'post',
    data,
  })
}

//得到变更列表
export function getChangeList(data) {
  return request({
    url: '/changeInfo/toList.do',
    method: 'post',
    data,
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

//修改的保存方法
export function doEdit(data) {
  return request({
    url: '/eventAll/toEdit.do',
    method: 'post',
    data,
  })
}

//导出Excel
export function exportEventExcel() {
  return request({
    url: '/eventAll/exportEventExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

//重开事件
export function reopenEvent(data) {
  return request({
    url: '/eventAll/toReopen.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

// 删除事件
export function doDelete(data) {
  return request({
    url: '/eventAll/toDelete.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

// 生成知识
export function generateKnowledge(data) {
  return request({
    url: '/eventAll/generateKnowledge.do',
    method: 'post',
    data,
  })
}

//撤销事件
export function revokeEvent(data) {
  return request({
    url: '/eventAll/toRevoke.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//批量关单
export function closingBatch(data) {
  return request({
    url: '/eventAll/closingBatch.do',
    method: 'post',
    data,
  })
}

//下载模板
export function download() {
  return request({
    url: '/eventAll/exporTemplatesEventExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

//导入
export function importExcel(data) {
  return request({
    url: '/eventAll/importExcel.do',
    method: 'post',
    data,
  })
}

// 得到服务群组
export function getServiceGroup(data) {
  return request({
    url: '/eventServiceManage/toList.do',
    method: 'post',
    data,
  })
}

// 转单
export function toTransfer(data) {
  return request({
    url: '/event/transferOrder',
    method: 'post',
    data,
  })
}

// 转单
export function getAllManageInfo(data) {
  return request({
    url: '/eventServiceManage/getAllManageInfo',
    method: 'post',
    params:data
  })
}

//我参与的
export function getMyParticipationList(data) {
  return request({
    url: '/event/toMyParticipationList.do',
    method: 'post',
    data,
  })
}

// 用户历史
export function userHistory(data) {
  return request({
    url: '/event/userHistory',
    method: 'post',
    data
  })
}

//点击模板，填写内容
export function mouldList(data) {
  return request({
    url: '/event/mouldList?id=' + data,
    method: 'post'
  })
}

//根据工单类别id查询\优先级、请求类型查询SLA的结果
export function categoryList(data) {
  return request({
    url: '/event/categoryList.do',
    method: 'post',
    data
  })
}

//导出功能
export function eventAllManageExcel(data) {
  return request({
    url: '/eventAll/eventAllManageExcel.do',
    method: 'post',
    responseType: 'blob',
    data
  })
}

//我要开单保存功能
export function saveWantToBill(data) {
  return request({
    url: '/event/saveWantToBill',
    method: 'post',
    data
  })
}


//我要开单修改功能
export function updateWantToBill(data) {
  return request({
    url: '/event/updateWantToBill?id=' + data,
    method: 'post'
  })
}

//我参与的
export function toMyParticipationList(data) {
  return request({
    url: '/event/toMyParticipationList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data
  })
}

//组内事件的
export function toMyGroupList(data) {
  return request({
    url: '/event/toMyGroupList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data
  })
}
