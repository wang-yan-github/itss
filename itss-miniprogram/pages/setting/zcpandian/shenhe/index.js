// pages/thing/working/chuli/index.js
import Toast from '@vant/weapp/toast/toast';
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
    title: '确认',
    topHeight: 0,
    activeNames: ['1'],
    show: false,
    fileList: [{
        url: 'https://img.yzcdn.cn/vant/leaf.jpg',
        name: '图片1',
      },
      // Uploader 根据文件后缀来判断是否为图片文件
      // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
      {
        url: 'http://iph.href.lu/60x60?text=default',
        name: '图片2',
        isImage: true,
        deletable: true,
      },
    ],
    columns: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
    assets: {},
    id: '',
    remark: ''
  },
  backTo() {
    wx.navigateBack({
      delta: 0,
    })
  },
  onChange(event) {
    this.setData({
      activeNames: event.detail,
    });
  },
  onChangejieju(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    Toast(`当前值：${value}, 当前索引：${index}`);
  },
  showjiejue(event) {
    this.setData({
      show: true,
    });
  },
  onConfirm(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    Toast(`当前值：${value}, 当前索引：${index}`);
    this.setData({
      show: false,
    });
  },

  onCancel() {
    this.setData({
      show: false,
    });
    Toast('取消');
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    let query = wx.createSelectorQuery();
    let that = this;
    query.select("#footer").boundingClientRect()
    query.exec(function (res) {
      //取高度
      console.log(res[0].height);
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
        id: options.id,
      });
    })
    console.log(options.id);
    wx.request({
        url: app.globalData.address + 'assetsManage/getInventoryId.do',
        method: 'POST',
        header: {
          "Authorization":wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          "id": options.id
        },
        success: function (res) {
            let obj = res.data.data
            console.log(res);
          that.setData({
            assets: res.data.data,
          })
        
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

  },
  remark: function(e){
    var that = this;
    that.setData({
      remark: e.detail,
    })
  },
  onClosepopup() {
    wx.navigateTo({
      url: '../index'
    })
 },
 onOpenpopup() {
    var that = this;
    console.log(that.data.remark);
    console.log(that.data.id);
    // if(null == that.data.remark || "" == that.data.remark){
    //     wx.showToast({
    //         title: "请填写备注",
    //         icon: 'error',
    //         duration: 2000
    //     })
    //     return ;
    // }
    wx.request({
        url: app.globalData.address + 'assetsManage/toInventoryConfirm.do',
        method: 'POST',
        header: {
          "Authorization":wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "id": that.data.id,
          "confirm_remark": that.data.remark,
          "userId": wx.getStorageSync('userInfo').id,
        },
        success: function (res) {
            if(res.data.code == 0){
                wx.showToast({
                    title: '确认成功',
                    icon: 'success',
                    duration: 2000
                })
                that.backTo();
                // that.onClosepopup();
              
            }else {
                wx.showToast({
                    title: res.data.msg,
                    icon: 'error',
                    duration: 2000
                })
            }
        
        }
      })



 }

})