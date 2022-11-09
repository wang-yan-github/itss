import request from '@/utils/request'

//查询所有it状态
export function getItStatus(data) {
    return request({
        url: '/itstatus/getAll.do',
        method: 'post',
        data,
    })
}