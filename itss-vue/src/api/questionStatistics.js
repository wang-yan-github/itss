import request from '@/utils/request'

export function getQuestionStatistics(data) {
  return request({
    url: '/questionManage/questionStatistics',
    method: 'post',
    data,
  })
}

export function exporQuestionExcel(data) {
  return request({
    url: '/questionManage/exporQuestionExcel.do',
    method: 'post',
    data,
    responseType: 'blob'
  })
}
//
export function exporCydQuestionExcel(data) {
  return request({
    url: '/questionManage/exportQuestion',
    method: 'post',
    data,
    responseType: 'blob'
  })
}
export function exporDBQuestionExcel(data) {
  return request({
    url: '/questionManage/exportDBQuestion',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
    responseType: 'blob'
  })
}

