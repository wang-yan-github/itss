// index.js
// 获取应用实例
const app = getApp()

Page({
  data: {
    user: wx.getStorageSync("userInfo"),
    // 状态栏高度
    statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
    // 导航栏高度
    navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
    // 胶囊按钮高度
    menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
    // 导航栏和状态栏高度
    navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
    // topbar高度
    topbarHeight: 120 - (wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight')) + 'px',
    title: '首页',
    authority: {},


  },
  goGzt: function (e) {
    wx.navigateTo({
      url: '../thing/working/index',
    })
  },
  goallthing: function (e) {
    wx.navigateTo({
      url: '../thing/allthing/index',
    })
  },
  goauto: function (e) {
    wx.navigateTo({
      url: '../thing/autoserver/index',
    })
  },
  goWdwt: function (e) { //我的问题
    wx.navigateTo({
      url: '../question/myquestion/index',
    })
  },
  goWdwtdb: function (e) { //问题待办
    wx.navigateTo({
      url: '../question/dbquestion/index',
    })
  },
  goWdwtgl: function (e) { //问题管理
    wx.navigateTo({
      url: '../question/glquestion/index',
    })
  },
  goMyPublish: function (options) {
    wx.navigateTo({
      url: '../fabu/myfabu/index',
    })
  },

  goDbPublish: function (options) {
    wx.navigateTo({
      url: '../fabu/dbfabu/index',
    })
  },
  goGlPublish: function (options) {
    wx.navigateTo({
      url: '../fabu/glfabu/index',
    })
  },
  overview: function (options) {
    wx.navigateTo({
      url: '../setting/index',
    })
  },
  setManagement: function (options) {
    wx.navigateTo({
      url: '../setting/setmanagement/index',
    })
  },
  zcpandian: function (options) {
    wx.navigateTo({
      url: '../setting/zcpandian/index',
    })
  },
  setrequerymana: function (options) {
    wx.navigateTo({
      url: '../setting/setrequerymana/index',
    })
  },
  mysetrequery: function (options) {
    wx.navigateTo({
      url: '../setting/mysetrequery/index',
    })
  },

  onLoad: function (options) {
    this.setData({
      user: wx.getStorageSync("userInfo")
    });
  },
  agencyrequery: function (options) {
    wx.navigateTo({
      url: '../setting/agencyrequery/index',
    })
  },

  myknowledge: function (options) {
    wx.navigateTo({
      url: '../knowledge/myknowledge/index',
    })
  },
  examineknowledge: function (options) {
    wx.navigateTo({
      url: '../knowledge/examineknowledge/index',
    })
  },
  insideknowledge: function (options) {
    wx.navigateTo({
      url: '../knowledge/insideknowledge/index',
    })
  },
  manageknowledge: function (options) {
    wx.navigateTo({
      url: '../knowledge/manageknowledge/index',
    })
  },
  searchknowledge: function (options) {
    wx.navigateTo({
      url: '../knowledge/searchknowledge/index',
    })
  },
  // 变更待办
  dbchanged: function (options) {
    wx.navigateTo({
      url: '../changed/dbchanged/index',
    })
  },
  // 变更管理
  glchanged: function (options) {
    wx.navigateTo({
      url: '../changed/glchanged/index',
    })
  },
  //我的变更
  mychanged: function (options) {
    wx.navigateTo({
      url: '../changed/mychanged/index',
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
    console.log(wx.getStorageSync("token"))
    this.setData({
      user: wx.getStorageSync("userInfo"),
    });
    const permission = wx.getStorageSync("authority");
    this.setData({
      authority: permission
    }) 

  },
//   tologin: function(res){
//     wx.navigateTo({
//         url: '../login/index',
//       })
//   }

})