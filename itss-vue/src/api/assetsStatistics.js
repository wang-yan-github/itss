import request from '@/utils/request'

//配置项类型统计
export function assetsTypeStatistics(data) {
    return request({
        url: '/assets/statistics/assetsTypeStatistics.do',
        method: 'get',
        params: data,
    })
}

//配置项CL分组统计
export function clGroupStatistics(data) {
    return request({
        url: '/assets/statistics/clGroupStatistics.do',
        method: 'get',
        params: data,
    })
}

//部门统计
export function assetsDeptStatistics(data) {
    return request({
        url: '/assets/statistics/assetsDeptStatistics.do',
        method: 'get',
        params: data,
    })
}