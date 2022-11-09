import request from '@/utils/request'

//我的问题 分页查询
export function getMyQuestionPageList(data) {
  return request({
    url: '/questionManage/toList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题管理分页查询
export function getQuestionPageList(data) {
  return request({
    url: '/questionManage/getManageByPage.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//新增 问题
export function toQuestionAdd(data) {
  return request({
    url: '/questionManage/myQuestionToAdd.do',
    method: 'post',
    data,
  })
}

//修改 问题
export function toQuestionEdit(data) {
  return request({
    url: '/questionManage/myQuestionToEdit.do',
    method: 'post',
    data,
  })
}

//根据ID 查询绑定的问题信息,(用于修改页面,初始化数据) 问题
export function getQuestionById(data) {
  return request({
    url: '/questionManage/getById.do',
    method: 'post',
    data,
  })
}

//撤销操作 问题
export function toQuestionRevoke(data) {
  return request({
    url: '/questionManage/toRevoke.do',
    method: 'post',
    data,
  })
}

//导出我的问题
export function toExportMyQuestions(data) {
  return request({
    url: '/questionManage/toExportMyQuestions.do',
    method: 'post',
    responseType: 'blob'
  })
}

//问题管理删除问题
export function deleteQuestion(data) {
  return request({
    url: '/questionManage/deleteQuestion.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题管理修改关联
export function questionModifyRelevance(data) {
  return request({
    url: '/questionManage/modifyRelevance.do',
    method: 'post',
    data,
  })
}

//生成知识
export function generateKnowledge(data) {
  return request({
    url: '/questionManage/generateKnowledge.do',
    method: 'post',
    data,
  })
}

//生成变更
export function generateChangeInfo(data) {
  return request({
    url: '/questionManage/generateChangeInfo.do',
    method: 'post',
    data,
  })
}

//Excel问题导入模板下载
export function downloadQuestion(data) {
  return request({
    url: '/questionManage/downloadQuestion',
    method: 'post',
    responseType: 'blob'
  })
}

//Excel导入问题
export function importQuestionList(data) {
  return request({
    url: '/questionManage/importQuestionList',
    method: 'post',
    data,
  })
}

//问题待办 审核列表
export function getDbPageList(data) {
  return request({
    url: '/questionManage/getDbPageList',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题待办 参与的列表
export function getParticipatingPageList(data) {
  return request({
    url: '/questionManage/getParticipatingPageList',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题待办 鉴定审核 同意/拒绝
export function onDbAppraisalUpd(data) {
  return request({
    url: '/questionManage/onDbAppraisalUpd',
    method: 'post',
    data,
  })
}

//问题待办 处理 保存/解决完成
export function onDbHandleUpd(data) {
  return request({
    url: '/questionManage/onDbHandleUpd',
    method: 'post',
    data,
  })
}

//问题待办 审核 同意/拒绝
export function onDbAuditUpd(data) {
  return request({
    url: '/questionManage/onDbAuditUpd',
    method: 'post',
    data,
  })
}

//问题待办 关单
export function onDbCloseUpd(data) {
  return request({
    url: '/questionManage/onDbCloseUpd',
    method: 'post',
    data,
  })
}

//问题待办 转单
export function onDbTransferOrderUpd(data) {
  return request({
    url: '/questionManage/onDbTransferOrderUpd',
    method: 'post',
    data,
  })
}

//问题待办 转办
export function onDbTransferUpd(data) {
  return request({
    url: '/questionManage/onDbTransferUpd',
    method: 'post',
    data,
  })
}

//问题待办 挂起
export function onDbHangUpd(data) {
  return request({
    url: '/questionManage/onDbHangUpd',
    method: 'post',
    data,
  })
}

//问题待办 恢复
export function onDbRecoveryUpd(data) {
  return request({
    url: '/questionManage/onDbRecoveryUpd',
    method: 'post',
    data,
  })
}

//问题优先级列表
export function getQuestionFirstList(data) {
  return request({
    url: '/slaQuestionFirst/getList.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题状态列表
export function getQuestionStatusList(data) {
  return request({
    url: '/sysDict/getDicts.do',
    method: 'post',
    data,
  })
}

//树状展示问题类别
export function getQuestionCategoryList(data) {
  return request({
    url: '/questionCategory/getParentList.do',
    method: 'post',
    data,
  })
}

//问题来源列表
export function getSourceList(data) {
  return request({
    url: '/questionSource/getList.do',
    method: 'post',
    data,
  })
}

//下拉选择人员列表
export function upUserData(data) {
  return request({
    url: '/sysuser/upUserData',
    method: 'post',
    data,
  })
}

//添加备注
export function addRemark(data) {
  return request({
    url: '/questionManage/addRemark',
    method: 'post',
    data,
  })
}


export function exportQuestionManExcel() {
  return request({
    url: '/questionManage/exportQuestionManExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

