import request from '@/utils/request'

//分页查询
export function getAssetsChildrenList(data) {
  return request({
    url: '/assetsChildren/toList.do',
    method: 'post',
    data,
  })
}

//添加 修改
export function toAdd(data) {
  return request({
    url: '/assetsChildren/toAdd.do',
    method: 'post',
    data,
  })
}


//删除
export function delAssetsChildren(data) {
  return request({
    url: '/assetsChildren/delAssetsChildren.do',
    method: 'post',
    params: data,
  })
}

export function toEdit(data) {
  return request({
    url: '/assetsChildren/toEdit.do',
    method: 'post',
    data,
  })
}

//根据配置项类型查询子类
export function getACByAssetsTypeId(data) {
  return request({
    url: '/assetsChildren/getACByAssetsTypeId.do',
    method: 'post',
    data,
  })
}