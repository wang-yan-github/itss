import request from '@/utils/request'

//事件影响度管理
export function affectGetList(data) {
    return request({
      url: '/slaEventImpact/toList.do',
      method: 'post',
      headers:{
        'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
      },
      data,
    })
  }

export function affectGetAllList(data) {
  return request({
    url: '/slaEventImpact/getList.do',
    method: 'post',
    data,
  })
}

  export function affectToAdd(data) {
    return request({
      url: '/slaEventImpact/toAdd.do',
      method: 'post',
      data,
    })
  }

  export function affectToEdit(data) {
    return request({
      url: '/slaEventImpact/toEdit.do',
      method: 'post',
      data,
    })
  }

//事件紧急度管理
export function urgencyGetList(data) {
  return request({
    url: '/slaEventUrgent/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function urgencyGetAllList(data) {
  return request({
    url: '/slaEventUrgent/getList.do',
    method: 'post',
    data,
  })
}

export function urgencyToAdd(data) {
  return request({
    url: '/slaEventUrgent/toAdd.do',
    method: 'post',
    data,
  })
}

export function urgencyToEdit(data) {
  return request({
    url: '/slaEventUrgent/toEdit.do',
    method: 'post',
    data,
  })
}

//事件优先级管理
export function priorityGetList(data) {
  return request({
    url: '/slaEventFirst/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function priorityToAdd(data) {
  return request({
    url: '/slaEventFirst/toAdd.do',
    method: 'post',
    data,
  })
}

export function priorityToEdit(data) {
  return request({
    url: '/slaEventFirst/toEdit.do',
    method: 'post',
    data,
  })
}


//问题优先级管理
export function slaQuestionGetList(data) {
  return request({
    url: '/slaQuestionFirst/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题优先级管理
export function slaQuestionGetLists(data) {
  return request({
    url: '/slaQuestionFirst/toList.do',
    method: 'post',
    data,
  })
}

export function slaQuestionToAdd(data) {
  return request({
    url: '/slaQuestionFirst/toAdd.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function slaQuestionToEdit(data) {
  return request({
    url: '/slaQuestionFirst/toEdit.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//问题SLA管理
export function questionSlaGetList(data) {
  return request({
    url: '/slaQuestion/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function questionSlaToAdd(data) {
  return request({
    url: '/slaQuestion/toAdd.do',
    method: 'post',
    data,
  })
}

export function questionSlaToDel(data) {
  return request({
    url: '/slaQuestion/toDel.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function questionSlaToEdit(data) {
  return request({
    url: '/slaQuestion/toEdit.do',
    method: 'post',
    data,
  })
}

export function questionSlaGetSlaQuestion(data) {
  return request({
    url: '/slaQuestion/getSlaQuestion.do',
    method: 'post',
    data,
  })
}

//事件影响度紧急度关系管理
export function slaEventImpactUrgentGetList(data) {
  return request({
    url: '/slaEventImpactUrgent/toList.do',
    method: 'post',
    data,
  })
}

export function slaEventImpactUrgentGetFirst(data) {
  return request({
    url: '/slaEventImpactUrgent/getFirst.do',
    method: 'post',
    data,
  })
}

export function slaEventImpactUrgentToEdit(data) {
  return request({
    url: '/slaEventImpactUrgent/toEdit.do',
    method: 'post',
    data,
  })
}

//事件sla管理
export function slaEventGetList(data) {
  return request({
    url: '/slaEvent/toList.do',
    method: 'post',
    headers:{
      'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function slaEventToAdd(data) {
  return request({
    url: '/slaEvent/toAdd.do',
    method: 'post',
    data,
  })
}

export function slaEventToEdit(data) {
  return request({
    url: '/slaEvent/toEdit.do',
    method: 'post',
    data,
  })
}
export function getFirstAndRequest(data) {
  return request({
    url: '/slaEvent/getFirstAndRequest.do',
    method: 'post',
    data,
  })
}

export function getSlaEvent(data) {
  return request({
    url: '/slaEvent/getSlaEvent.do',
    method: 'post',
    data,
  })
}
