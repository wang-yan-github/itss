import request from '@/utils/request'

//发布类别管理
export function publishCategoryList(data) {
  return request({
    url: '/publishCategory/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}


export function getAllCategoryList(data) {
  return request({
    url: '/publishCategory/getAllList.do',
    method: 'post',
    data,
  })
}
export function publishCategoryToAdd(data) {
  return request({
    url: '/publishCategory/toAdd.do',
    method: 'post',
    data,
  })
}

export function publishCategoryToEdit(data) {
  return request({
    url: '/publishCategory/toEdit.do',
    method: 'post',
    data,
  })
}

export function getPublishCategory(data) {
  return request({
    url: '/publishCategory/getPublishCategory.do',
    method: 'post',
    data,
  })
}

export function getPublishBasicInfo(data) {
  return request({
    url: '/publishBasicInfo/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}
export function getPublishListByParticipate(data) {
  return request({
    url: '/publishBasicInfo/getPublishListByParticipate.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function toManageList(data) {
  return request({
    url: '/publishBasicInfo/toManageList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function getDbPublishBasicInfo(data) {
  return request({
    url: '/publishBasicInfo/toDbList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function publishBasicInfoToAdd(data) {
  return request({
    url: '/publishBasicInfo/toAdd.do',
    method: 'post',
    data,
  })
}

export function publishBasicInfoToEdit(data) {
  return request({
    url: '/publishBasicInfo/toEdit.do',
    method: 'post',
    data,
  })
}

export function publishBasicInfoToDel(data) {
  return request({
    url: '/publishBasicInfo/toDel.do',
    method: 'post',
    data,
  })
}

export function getPublishBasic(data) {
  return request({
    url: '/publishBasicInfo/getPublishBasic.do',
    method: 'post',
    params: data,
  })
}

export function getApprovalByPublishId(data) {
  return request({
    url: '/publishBasicInfo/getApprovalByPublishId.do',
    method: 'post',
    data,
  })
}

export function revokePublish(data) {
  return request({
    url: '/publishBasicInfo/revokePublish.do',
    method: 'post',
    data,
  })
}

export function approvePublish(data) {
  return request({
    url: '/publishBasicInfo/approvePublish.do',
    method: 'post',
    data,
  })
}

export function testPublish(data) {
  return request({
    url: '/publishBasicInfo/testPublish.do',
    method: 'post',
    data,
  })
}

export function doPublish(data) {
  return request({
    url: '/publishBasicInfo/doPublish.do',
    method: 'post',
    data,
  })
}


export function exportMyPublishExcel() {
  return request({
    url: '/publishBasicInfo/exportMyPublishExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

export function exportPublishExcel() {
  return request({
    url: '/publishBasicInfo/exportPublishExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

export function exportPublishExcelTemplate() {
  return request({
    url: '/publishBasicInfo/exportPublishExcelTemplate.do',
    method: 'post',
    responseType: 'blob'
  })
}


export function exportDbPublishExcel() {
  return request({
    url: '/publishBasicInfo/exportDbPublishExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}
//exportPublishParticipateExcel
export function exportPublishParticipateExcel() {
  return request({
    url: '/publishBasicInfo/exportPublishParticipateExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

export function getLogInfo(data) {
  return request({
    url: '/publishBasicInfo/getLogInfo.do',
    method: 'post',
    data,
  })
}


export function importPublishList(data) {
  return request({
    url: '/publishBasicInfo/importPublishList',
    method: 'post',
    data,
  })
}


export function publishedSources() {
  return request({
    url: '/publishBasicInfo/publishedSources',
    method: 'post'
  })
}

export function releaseTheCategory() {
  return request({
    url: '/publishBasicInfo/releaseTheCategory',
    method: 'post'
  })
}


export function publishType() {
  return request({
    url: '/publishBasicInfo/publishType',
    method: 'post'
  })
}

export function contributionScoreStatistics() {
  return request({
    url: '/knowledgeinfo/contributionScoreStatistics',
    method: 'post'
  })
}

export function departmentOfKnowledge() {
  return request({
    url: '/knowledgeinfo/departmentOfKnowledge',
    method: 'post'
  })
}






