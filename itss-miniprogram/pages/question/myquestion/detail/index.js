var app = getApp();
import Dialog from '@vant/weapp/dialog/dialog';
// pages/thing/working/zhipai/index.js
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
    title: '详情',
    topHeight: 0,
    activeNames: ['1'],
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
    stepsactive: 0,
    activetabs: 0,
    steps: [], //时间显示
    id: '',
    row: {},
    showdia: false,
    shuoming: '',
    showdiacx: false,
    shuomingcx: '',
    authorityShow: false,
    reviewerSize: 0
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
  onChangetabs(event) {
    // event.detail 的值为当前选中项的索引
    this.setData({
      activetabs: event.detail
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (null == options || undefined == options || options.id == undefined) {
      return;
    }
    let id = options.id;
    wx.request({
      url: app.globalData.address + 'question/selectQuestionById.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": id,
        "user_id": wx.getStorageSync('userInfo').id
      },
      success: function (res) {
        let obj = res.data.data;
        //时间信息
        //1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
        that.data.steps = [];
        // 时间步骤条设置
        that.data.steps.push({
          text: "开单",
          desc: that.parseDateTimeFull(obj.create_time)
        });
        that.data.steps.push({
          text: "鉴定",
          desc: that.parseDateTimeFull(obj.appraisal_time)
        });
        that.data.steps.push({
          text: "解决",
          desc: that.parseDateTimeFull(obj.solve_time)
        });
        obj.questionReviewers.forEach((item, i) => {
          that.data.steps.push({
            text: item.review_user_name + "审核",
            desc: that.parseDateTimeFull(item.update_time)
          });
          if (undefined != item.update_time && null != item.update_time && '' != item.update_time) {
            that.data.reviewerSize = that.data.reviewerSize + 1;
          }
        });
        that.data.steps.push({
          text: "关单",
          desc: that.parseDateTimeFull(obj.clearance_time)
        });
        that.data.steps.forEach(function (item) {
          if ('' != item.desc) {
            that.data.stepsactive = that.data.stepsactive + 1;
          }
        });
        console.log(that.data.reviewerSize)
        that.setData({
          form: obj,
          steps: that.data.steps,
          stepsactive: that.data.stepsactive - 1,
          reviewerSize: that.data.reviewerSize
        })
      }
    })
  },
  parseDateTimeFull(time) {
    // 判断是否为空
    if (time == null || time == '') {
      return ''
    }
    return time;
  },
  gdBindtap(event) { //关单
    let id = event.currentTarget.dataset.id;
    var that = this;
    if (id == '') {
      wx.showToast({
        title: '请选择要关闭的问题',
        icon: 'none',
        duration: 2000
      })
      return;
    }
    Dialog.confirm({
      title: '提示',
      message: '确定要关闭吗？',
    }).then(() => {
      wx.request({
        url: app.globalData.address + 'question/onDbCloseUpd',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId": wx.getStorageSync('userInfo').id,
          "id": id,
          "current_user": wx.getStorageSync('userInfo').id
        },
        success: function (res) {
          if (res.data.code == 0) {
            wx.showToast({
              title: '关闭成功',
              icon: 'success',
              duration: 2000
            })
            that.backTo();
          } else {
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
        }
      })
    }).catch(() => {
      // on cancel
    });
  },
  backTo() {
    wx.navigateBack({
      delta: 0,
    })
  },
  //备注执行按钮操作
  remarksClick() {
    this.setData({
      showdia: true,
      shuoming: '',
    });
  },
  onRemarksCloseClick() {
    this.setData({
      showdia: false,
      shuoming: '',
    });
  },
  //弹窗方法
  onshuoming(event) {
    this.data.shuoming = event.detail;
  },
  remarksQuestion(event) { //备注方法
    var that = this;
    if (that.data.shuoming != null && that.data.shuoming != '' && that.data.shuoming != undefined) {
      let id = event.currentTarget.dataset.id;
      wx.request({
        url: app.globalData.address + 'question/addRemark',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId": wx.getStorageSync('userInfo').id,
          "id": id,
          "remark": that.data.shuoming,
          "create_user": wx.getStorageSync('userInfo').id
        },
        success: function (res) {
          if (res.data.code == 0) {
            wx.showToast({
              title: '操作成功',
              icon: 'success',
              duration: 2000
            })
            that.backTo();
          } else {
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
        }
      })
    } else {
      wx.showToast({
        title: '请输入备注内容',
        icon: 'none',
        duration: 2000
      });
    }
  },
  //撤销执行按钮操作
  revokeClick() {
    this.setData({
      showdiacx: true,
      shuomingcx: '',
    });
  },
  onRevokeClosedia() {
    this.setData({
      showdiacx: false,
      shuomingcx: '',
    });
  },

  onshuomingcx(event) {
    this.data.shuomingcx = event.detail;
  },
  revokeQuestion(event) { //撤销方法
    var that = this;
    let id = event.currentTarget.dataset.id;
    if (null == id || id == '') {
      wx.showToast({
        title: '请选择一条数据',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    wx.request({
      url: app.globalData.address + 'question/toRevoke.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        "userId": wx.getStorageSync('userInfo').id,
        "id": id,
        "remark": that.data.shuomingcx,
        "current_user": wx.getStorageSync('userInfo').id
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            shuoming: '',
            list: that.data.list,
          });
          wx.showToast({
            title: '撤销成功',
            icon: 'success',
            duration: 2000
          })
          that.backTo();
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 2000
          })
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