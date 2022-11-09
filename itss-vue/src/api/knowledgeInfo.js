import request from '@/utils/request'

//我的知識分頁查詢
export function getInfoByPage(data){
  return request({
    url: '/knowledgeinfo/getPage.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//我的知識添加
export function addKnowledgeInfo(data){
  return request({
    url: '/knowledgeinfo/addKnowledgeInfo.do',
    method: 'post',
    data,
  })
}

//根据主键查询我的知識
export function getKnowledgeInfoById(data){
  return request({
    url: '/knowledgeinfo/getKnowledgeInfoById.do',
    method: 'post',
    params: data,
  })
}

//我的知識修改
export function updateKnowledgeInfo(data){
  return request({
    url: '/knowledgeinfo/updateKnowledgeInfo.do',
    method: 'post',
    data,
  })
}

//分页查询待审核知识
export function getStatusByPage(data){
  return request({
    url: '/knowledgeinfo/getStatusByPage.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//知識審核通過
export function toExamineKnowledge(data){
  return request({
    url: '/knowledgeinfo/toExamineKnowledge.do',
    method: 'post',
    data,
  })
}
//知識審核拒絕
export function toRefuseExamineKnowledge(data){
  return request({
    url: '/knowledgeinfo/toRefuseExamineKnowledge.do',
    method: 'post',
    data,
  })
}

//知識使用
export function useKnowledge(data){
  return request({
    url: '/knowledgeinfo/useKnowledge.do',
    method: 'post',
    params: data,
  })
}
//知識禁用
export function unUseKnowledge(data){
  return request({
    url: '/knowledgeinfo/unUseKnowledge.do',
    method: 'post',
    params: data,
  })
}
//刪除知識
export function deleteKnowledge(data){
  return request({
    url: '/knowledgeinfo/deleteKnowledge.do',
    method: 'post',
    params: data,
  })
}

//导入模板下载
export function downloadKnowledge() {
  return request({
    url: '/knowledgeinfo/downloadKnowledge',
    method: 'post',
    // headers:{
    //  'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    // },
    responseType: 'blob'
  })
}


//分页查询内部知识
export function getInsideKnowledge(data){
  return request({
    url: '/knowledgeinfo/getInsideKnowledge.do',
    method: 'post',
    headers:{
     'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//导出内部知识
export function exporInsideKnowledgeExcel(data) {
  return request({
    url: '/knowledgeinfo/exporInsideKnowledgeExcel.do',
    method: 'post',
    responseType: 'blob',
    data,
  })
}


//分页查询知识管理
export function manageKnowledge(data){
  return request({
    url: '/knowledgeinfo/manageKnowledge.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//打分
export function getPoint(data){
  return request({
    url: '/knowledgeinfo/getPoint.do',
    method: 'post',
    data,
  })
}
//获取ES检索结果
export function getEsKnow(data){
  return request({
    url: '/knowledge/queryDoc',
    method: 'post',
    data,
  })
}
//查询热点和推荐知识
export function getHotOrRecommendKnow(data){
  return request({
    url: '/knowledgeinfo/getHotOrRecommendKnow',
    method: 'post',
    params:data,
  })
}

//知识管理导出
export function exporManageKnowledgeExcel(data) {
  return request({
    url: '/knowledgeinfo/exporManageKnowledgeExcel.do',
    method: 'post',
    responseType: 'blob',
    data,
  })
}


//树状查询知识分类
export function getCategoryWithTree(data) {
  return request({
    url: '/knowledgeinfo/getCategoryWithTree.do',
    method: 'post',
    data,
  })
}

//树状查询知识分类
export function getCategoryWithTree1(data) {
  return request({
    url: '/knowledgeinfo/getCategoryWithTree1.do',
    method: 'post',
    data,
  })
}

//查询所有可见范围
export function getAllRange() {
  return request({
    url: '/knowledgeinfo/getAllRange.do',
    method: 'post',
  })
}

//主页搜索知识
export function getSearchFourKnowledge(data) {
  return request({
    url: '/knowledgeinfo/getSearchFourKnowledge.do',
    method: 'post',
    params:data,
  })
}



export function importKnowledgeList(data) {
  return request({
    url: '/knowledgeinfo/importKnowledgeList',
    method: 'post',
    data,
  })
}



