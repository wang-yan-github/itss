import request from '@/utils/request'

//首页统计
export function statistics(data) {
  return request({
    url: '/index/statistics.do',
    method: 'post',
    params: data,
  })
}

export function eventStatistics(data) {
  return request({
    url: '/index/eventStatistics.do',
    method: 'post',
    params: data,
  })
}

export function getLogs(data) {
  return request({
    url: '/index/getLogs.do',
    method: 'post',
    params: data,
  })
}


export function getNotices(data) {
  return request({
    url: '/index/getNotices.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}
