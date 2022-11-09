// 查询我的事件列表
import request from "@/utils/request";

export function getMyList(data) {
  return request({
    url: '/eventSelfService/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function editEvent(data) {
  return request({
    url: '/eventSelfService/toEdit.do',
    method: 'post',
    data,
  })
}
//评价
export function evaluateAdd(data) {
  return request({
    url: '/event/evaluateUser.do',
    method: 'post',
    data,
  })
}

//查询盘点期
export function toInventoryList(data) {
  return request({
    url: '/eventSelfService/toInventoryList.do',
    method: 'post',
    data,
  })
}

export function toInventoryById(data) {
  return request({
    url: '/eventSelfService/toInventoryById.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//批量更新
export function editBatchDetails(data) {
  return request({
    url: '/eventSelfService/editBatchDetails.do',
    method: 'post',
    // headers:{
    //   'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    // },
    data,
  })
}

export function closingBatch(data) {
  return request({
    url: '/eventSelfService/editBatchDetails.do',
    method: 'post',
    data,
  })
}

