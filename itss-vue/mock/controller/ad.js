const data = [
  {
    title: '欢迎使用ITSS',
    url: 'http://localhost:3005',
  },
  {
    title: 'ITSS 2.0版本已发布，点我提前体验',
    url: 'http://localhost:3005',
  },
]
module.exports = [
  {
    url: '/ad/getList',
    type: 'get',
    response() {
      return {
        code: 200,
        msg: 'success',
        data,
      }
    },
  },
]
