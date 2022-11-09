// pages/login/index.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  bindEye() {
    this.setData({
      currentTab: !this.data.currentTab,
      password: !this.data.password,
    })
  },
  data: {
    userName: null,
    currentTab: true,
    password: true,
    closed: false,
    closed1: false,
    butStatus: false,
    disabled: true,
    loading: false,
    // 状态栏高度
    statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
    // 导航栏高度
    navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
    // 胶囊按钮高度
    menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
    // 导航栏和状态栏高度
    navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
    title: '登录'
  },
  userName: function (e) { //用户名
    this.data.userName = e.detail.value;
    //var that = this
    if (this.data.userName == null || this.data.userName == '') {
      this.setData({
        closed: false
      })
    } else {
      this.setData({
        closed: true
      })
    }
  },
  clearData: function (e) {
    this.setData({
      closed: false,
    })
  },
  clearData1: function (e) {
    this.setData({
      closed1: false,
      butStatus: false,
      disabled: true,
    })
  },
  password: function (e) { //密码
    this.data.password = e.detail.value;
    if (this.data.password == null || this.data.password == '') {
      this.setData({
        closed1: false,
        butStatus: false,
        disabled: true
      })
    } else {
      this.setData({
        closed1: true,
        butStatus: true,
        disabled: false
      })
    }
  },

  //登录
  loginBtnClick: function (e) {
    var that = this
    if (!that.data.userName) {
      //跳转登录页
      wx.showToast({
        title: '用户名为空',
        icon: 'none',
        duration: 2000
      })
    } else if (!that.data.password) {
      //跳转登录页
      wx.showToast({
        title: '密码为空',
        icon: 'none',
        duration: 2000
      })
    }

    console.log("用户名：" + that.data.userName + " 密码：" + that.data.password);
    var openid = wx.getStorageSync("openid");
    wx.request({
      url: app.globalData.address + 'login',
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        userName: that.data.userName,
        passWord: that.data.password,
        openid: openid,
      },
      success: function (res) {
          
        console.log(res)
        if (res.data.success) {
          wx.setStorageSync("userInfo", res.data.user);
          const permission = require('../../utils/permission')
          var json = permission.permission;
          for(var i = 0; i < res.data.authority.length; i++){
            var key = res.data.authority[i]
            json[key] = true;
          }
          wx.setStorageSync("authority", json);
          //跳转到商家首页
          setTimeout(function () {
            wx.redirectTo({
              url: '../index/index'
            })
          }, 1000);

        } else {
          //登陆失败
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 2000
          })
          that.setData({
            closed1: true,
            butStatus: true,
            disabled: false
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.login({
      success (res) {
        if (res.code) {
          //发起网络请求
          wx.request({
            url: app.globalData.address + 'wxLogin.do',
            data: {
              code: res.code
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})