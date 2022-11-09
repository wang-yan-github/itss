const data = [
  {
    title: '用户管理',
    closable: true,
    type: 'primary',
    link: '/set/usermanagement'
  },
  {
    title: '角色管理',
    closable: true,
    type: 'primary',
    link: '/set/rolesmanagement'
  },
  {
    title:
      '部门管理',
    closable: true,
    type: 'primary',
    link: '/set/departmentmana'
  },
  {
    title:
      '公司管理',
    closable: true,
    type: 'primary',
    link: 'index'
  },
  {
    title:
      '人力地图',
    closable: true,
    type: 'primary',
    link: '/index'
  },
  {
    title:
      '域用户管理',
    closable: true,
    type: 'primary',
    link: 'index'
  },
  {
    title:
      '飞书同步管理',
    closable: true,
    type: 'primary',
    link: 'index'
  },
]
module.exports = [
  {
    url: '/notice/getList',
    type: 'post',
    response() {
      return {
        code: 200,
        msg: 'success',
        data,
      }
    },
  },
]
