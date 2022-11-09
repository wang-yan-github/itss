// pages/thing/working/zhuandan/index.js
import Toast from '@vant/weapp/toast/toast';
var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        id:'',
        remark: '',
        event: {
          id: '',
          title: '',
          description: '',
          create_user_name: '',
          create_time: '',
          response_user_name: '',
          responseTime: '',
          update_time: '',
          expenses: '',
        },
        // 状态栏高度
        statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
        // 导航栏高度
        navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
        // 胶囊按钮高度
        menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
        // 导航栏和状态栏高度
        navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
        title: '转单',
        topHeight: 0,
        activeNames: ['1'],
        columns: [],
        rColumns: [],
        columns1: [],
        rColumns1: [],
        show: false,
        group_name: '请选择',
        group_index: '',
        deal_name: '请选择',
        deal_index: '',
        show1: false,
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

        wx.request({
          url: app.globalData.address + 'event/getById',
          method: 'POST',
          header: {
            "Authorization":wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            "id": options.id
          },
          success: function (res) {
            console.log(res.data.data.data);
            that.setData({
              event: res.data.data.data,
            })
          }
        })
        that.getAllManageInfo();
        that.getAllUser();   
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
    onClosepopup() {
      wx.navigateTo({
        url: '../index'
    })
  },
  onOpenpopup() {
    var that = this;
    var service_groups_id = '';
    for(var i = 0 ; i< that.data.rColumns.length;i++){
      if(that.data.group_name == that.data.rColumns[i].name && that.data.group_index == i){
        service_groups_id = that.data.rColumns[i].id;
      }
    }
    console.log(service_groups_id)
    var handler = '';
    for(var i = 0 ; i< that.data.rColumns1.length;i++){
      if(that.data.deal_name == that.data.rColumns1[i].name && that.data.deal_index == i){
        handler = that.data.rColumns1[i].id;
      }
    }
    console.log(handler)
    wx.request({
      url: app.globalData.address + 'event/transferOrder',
      method: 'POST',
      header: {
        "Authorization":wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        "id": that.data.id,
        "service_groups_id": service_groups_id,
        "handler": handler,
        "transferRemark": that.data.remark,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if(res.data.code == 0){
          wx.showToast({
              title: '转单成功',
              icon: 'success',
              duration: 2000
          })
          that.backTo();
          // that.onClosepopup();
        
      }else {
          console.log(res);
          wx.showToast({
              title: res.data.msg,
              icon: 'error',
              duration: 2000
          })
      }
      }
    })
 },
 getAllManageInfo: function(e){
  var that = this;
  wx.request({
    url: app.globalData.address + 'event/getAllManageInfo',
    method: 'POST',
    header: {
      "Authorization":wx.getStorageSync('tokenValue'),
      "Content-Type": "application/x-www-form-urlencoded"
    },
    data: {
      
    },
    success: function (res) {
      let x = [];
      for(var i = 0 ; i<res.data.data.length; i++ ){
        x.push(res.data.data[i].name)
      }
      console.log(x);
      that.setData({
        columns: x,
        rColumns: res.data.data,
      })
    }
  })
  console.log(that.data.columns);
  console.log(that.data.rColumns);
 },
 getAllUser: function(e){
  var that = this;
  wx.request({
    url: app.globalData.address + 'event/getAllUser',
    method: 'POST',
    header: {
      "Authorization":wx.getStorageSync('tokenValue'),
      "Content-Type": "application/x-www-form-urlencoded"
    },
    data: {
      
    },
    success: function (res) {
      console.log(res.data.data)
      let x = [];
      for(var i = 0 ; i<res.data.data.length; i++ ){
        x.push(res.data.data[i].name)
      }
      console.log(x);
      that.setData({
        columns1: x,
        rColumns1: res.data.data,
      })
    }
  })
  console.log(that.data.columns1);
  console.log(that.data.rColumns1);
 },
 showjiejue(event) {
  this.setData({
    show: true,
  });
},
onChangejieju(event) {
  const {
    picker,
    value,
    index
  } = event.detail;
//   Toast(`当前值：${value}, 当前索引：${index}`);
},
onChangechuli(event) {
  const {
    picker,
    value,
    index
  } = event.detail;
//   Toast(`当前值：${value}, 当前索引：${index}`);
},
onConfirm(event) {
  var that = this;
  const {
    picker,
    value,
    index
  } = event.detail;
  
  console.log(value)
  that.setData({
    show: false,
    group_name: value,
    group_index: index,
  });
},

onConfirm1(event) {
  var that = this;
  const {
    picker,
    value,
    index
  } = event.detail;
  
  console.log(value)
  that.setData({
    show1: false,
    deal_name: value,
    deal_index: index,
  });
},

onCancel() {
  this.setData({
    show: false,
  });
  Toast('取消');
},
onCancel1() {
  this.setData({
    show1: false,
  });
  Toast('取消');
},
showchuli(event) {
  this.setData({
    show1: true,
  });
},
remark: function(e){
  var that = this;
  that.setData({
    remark: e.detail,
  })
},
})