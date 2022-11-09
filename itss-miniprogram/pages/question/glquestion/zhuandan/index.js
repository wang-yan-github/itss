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
    title: '转办',
    topHeight: 0,
    activeNames: ['3'],
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
    columns: [], //获取指定人列表
    handle_user: '', //获取转办指定用户
    remarks: '', //备注
    form: '',
    steps: '',
    stepsactive: '',
    selectName: '请选择',
    id: ''
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
  },
  showjiejue(event) {
    this.setData({
      show: true
    });
  },
  onConfirm(event) {
    var that = this;
    const {
      picker,
      value,
      index
    } = event.detail;
    //给下拉框赋值
    this.setData({
      selectName: event.detail.value.text
    });
    this.data.handle_user = event.detail.value.keys; //取选择的值
    this.setData({
      show: false,
    });
  },

  onCancel() {
    this.setData({
      show: false,
      selectName: '请选择'
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
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
      });
    })

    //获取当前指定用户列表信息
    wx.request({
      url: app.globalData.address + 'question/upUserData.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      success: function (res) {
        var list = res.data.data;
        for (var i = 0; i < list.length; i++) {
          that.data.columns.push({
            text: list[i].name,
            keys: list[i].id
          })
        }
        that.setData({
          columns: that.data.columns,
        })
      }
    })
    //获取当前专办信息
    let id = options.id;
    this.data.id = id;
    wx.request({
      url: app.globalData.address + 'question/selectQuestionById.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": id,
        "user_id":wx.getStorageSync('userInfo').id
      },
      success: function (res) {
        let obj = res.data.data;
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
            text:  item.review_user_name +"审核",
            desc: that.parseDateTimeFull(item.update_time)
          });
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
        that.setData({
          form: obj,
          steps: that.data.steps,
          stepsactive: that.data.stepsactive - 1,
        })
      }
    })
  },
  parseDateTimeFull(time) {
    // 判断是否为空
    if (time == null || time == '') {
      return ''
    }
    var x = new Date(time);
    var z = {
      y: x.getFullYear(),
      M: x.getMonth() + 1,
      d: x.getDate(),
      h: x.getHours(),
      m: x.getMinutes(),
      s: x.getSeconds(),
    };
    if (z.M < 10) {
      z.M = "0" + z.M;
    }
    if (z.d < 10) {
      z.d = "0" + z.d;
    }
    if (z.h < 10) {
      z.h = "0" + z.h;
    }
    if (z.m < 10) {
      z.m = "0" + z.m;
    }
    return z.M + "-" + z.d + " " + z.h + ":" + z.m;
  },
  remarks: function (e) {
    var that = this;
    that.setData({
      remarks: e.detail,
    })
  },
  saveClick() { //保存
    var that = this;
    if (that.data.handle_user == null || that.data.handle_user == undefined || that.data.handle_user == '') {
      wx.showToast({
        title: '请选择指定人',
        icon: 'error',
        duration: 2000
      })
      return;
    } else if (that.data.remarks == null || that.data.remarks == undefined || that.data.remarks == '') {
      wx.showToast({
        title: '请填写备注',
        icon: 'error',
        duration: 2000
      })
      return;
    } else {
      wx.request({
        url: app.globalData.address + 'question/onDbTransferUpd',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          "userId":wx.getStorageSync('userInfo').id,
          "id": this.data.id,
          "status": this.data.form.status,
          "transfer_remark": this.data.remarks,
          "handle_user": this.data.handle_user,
          "current_user": wx.getStorageSync('userInfo').id
        },
        success: function (res) {
          if (res.data.code == 0) {
            wx.showToast({
              title: '转单成功',
              icon: 'success',
              duration: 2000
            })
            that.backTo();
            // that.onClosepopup();
          } else {
            wx.showToast({
              title: res.data.msg,
              icon: 'error',
              duration: 2000
            })
          }
        }
      })
    }
  },
  onClosepopup() {
     wx.navigateBack({
      delta: 0,
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