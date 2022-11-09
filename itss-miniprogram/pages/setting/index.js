// pages/setting/index.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // 状态栏高度
    statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
    // 导航栏高度
    navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
    // 胶囊按钮高度
    menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
    // 导航栏和状态栏高度
    navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
    title: '概览',
    list: [],
  },
  backTo() {
    wx.navigateBack({
      delta: 0,
    })
  },
  addset: function (e) {
    wx.navigateTo({
      url: 'add/index',
    })
  },
  setmana: function (e) {
    var id = e.currentTarget.dataset.id;
    console.log(id);
    wx.navigateTo({
      url: 'gailandetail/index?id='+id,
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    wx.showLoading({
        title: '数据加载中...',
        icon: 'loading',
        duration: 1500
    })
    var that = this;
    var user = wx.getStorageSync("userInfo");
    wx.request({
        url: app.globalData.address + 'assetsManage/overviewList.do',
        method: 'POST',
        header: {
            "Authorization": wx.getStorageSync('tokenValue'),
            "Content-Type": "application/json"
        },
        data: {
            "beanParam": null,
            "userId": wx.getStorageSync('userInfo').id,
        },
        success: function (res) {
            console.log(res.data.data)
            that.setData({
                list: res.data.data,
                // pageIndex: res.data.data.pageNum,
                // pageSize: res.data.data.pageSize,
                // totalPage: res.data.data.total,
                // pages: res.data.data.pages,
                // showMZView: false
            })
            // if (res.data.data.list.length == 0) {
            //     that.setData({
            //         showMZView: true
            //     })
            // } else {
            //     that.setData({
            //         showMZView: false
            //     })
            // }
        }
    })








  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})