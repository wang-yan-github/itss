import request from '@/utils/request'

export function getRoleIdsByUser(data) {
    return request({
        url: '/userRole/getRoleIdsByUser.do',
        method: 'post',
        data,
    })
}