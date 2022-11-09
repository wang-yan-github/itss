// pages/thing/working/chuli/index.js
import Toast from '@vant/weapp/toast/toast';
//今天的时间
var timestamp = Date.parse(new Date());
timestamp = timestamp / 1000;
//获取当前时间
var n = timestamp * 1000;
var date = new Date(n);
//年
var Y = date.getFullYear();
//月
var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1);
//日
var D = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
//时
var h = date.getHours() < 10 ? '0' + date.getHours() : date.getHours();
//分
var m = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes();
//秒
var s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

var ymd = Y + "-" + M + "-" + D;
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
    title: '鉴定',
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
    columnswtlys: [], //问题来源
    wtlyselectName: '请选择',
    wtlyshow: false,
    yxjshow: false,
    columnsyxj: [], //优先级
    yxjselectName: '请选择',
    columnclr: [],
    clrselectName: '请选择',
    clrshow: false,
    // 保存方式涉及字段
    handle_user: '', //处理人
    appraisal_opinion: '', //鉴定意见
    source_id: '', //问题来源
    priority_id: '', //优先级
    titles: '', //标题
    description: '', //描述
    hope_solve_time: '', //期望时间
    form: '', //赋值当前id的数据
    id: '', //赋值id值
    startTime: ymd, //开始时间
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
  onChangejiejuwtly(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
  },
  bindDateChange: function (e) { //开始时间
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      startTime: e.detail.value
    })
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
    var objDetails = ''
    //读取鉴定数据内容
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
        that.data.titles = obj.title;
        that.data.description = obj.description;
        that.setData({
          form: obj,
          titles: that.data.titles,
          description: that.data.description
        })
        that.questionSourceList(obj);
        that.slaQuestionFirstList(obj)
      }
    })

    //获取处理人列表
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
          that.data.columnclr.push({
            text: list[i].name,
            keys: list[i].id
          })
        }
        that.setData({
          columnclr: that.data.columnclr,
        })
      }
    })
  },
  questionSourceList: function (obj) {
    var that = this;
    //问题来源
    wx.request({
      url: app.globalData.address + 'question/questionSourceList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        let list = res.data.data;
        var wtlyselectName = '';
        if (list.length > 0) {
          for (var i = 0; i < list.length; i++) {
            if (obj.source_id == list[i].id) { //默认带出值
              wtlyselectName = list[i].name;
              that.data.source_id = list[i].id;
            }
            that.data.columnswtlys.push({
              text: list[i].name,
              keys: list[i].id
            })
          }
          that.setData({
            columnswtlys: that.data.columnswtlys,
            wtlyselectName: wtlyselectName,
            source_id: that.data.source_id
          })
        }
      }
    })
  },
  slaQuestionFirstList: function (obj) {
    var that = this;
    //优先级
    wx.request({
      url: app.globalData.address + 'question/slaQuestionFirstList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        let list = res.data.data;
        var yxjselectName = '';
        if (list.length > 0) {
          for (var i = 0; i < list.length; i++) {
            if (obj.priority_id == list[i].id) { //默认带出值
              yxjselectName = list[i].first_name;
              that.data.priority_id = list[i].id;
            }
            that.data.columnsyxj.push({
              text: list[i].first_name,
              keys: list[i].id
            })
          }
          that.setData({
            columnsyxj: that.data.columnsyxj,
            yxjselectName: yxjselectName,
            priority_id: that.data.priority_id
          })
        }
      }
    })

  },
  onClosepopup() {
    wx.navigateTo({
      url: '../index'
    })
  },
  //问题来源
  wtlySelect() {
    this.setData({
      wtlyshow: true
    });
  },
  onChangejiejuwtly(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
  },
  onConfirmwtly(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    //给下拉框赋值
    this.setData({
      wtlyselectName: event.detail.value.text
    });
    this.data.source_id = event.detail.value.keys; //取选择的值
    this.setData({
      wtlyshow: false,
    });
  },
  onCancelwtyl() {
    this.setData({
      wtlyshow: false,
      wtlyselectName: '请选择'
    });
  },
  //优先级选择
  yxjSelect() {
    this.setData({
      yxjshow: true
    });
  },
  onChangejiejuyxj(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
  },
  onConfirmyxj(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    //给下拉框赋值
    this.setData({
      yxjselectName: event.detail.value.text
    });
    this.data.priority_id = event.detail.value.keys; //取选择的值
    this.setData({
      yxjshow: false,
    });
  },
  onCancelyxj() {
    this.setData({
      yxjshow: false,
    });
  },
  //处理人
  clrSelect() {
    this.setData({
      clrshow: true
    });
  },
  onChangejiejuclr(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
  },
  onConfirmclr(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    //给下拉框赋值
    this.setData({
      clrselectName: event.detail.value.text
    });
    this.data.handle_user = event.detail.value.keys; //取选择的值
    this.setData({
      clrshow: false,
    });
  },
  onCancelclr() {
    this.setData({
      clrshow: false,
    });
  },
  opinionsClick(e) {//鉴定意见
    var that = this;
    that.setData({
      appraisal_opinion: e.detail,
    })
  },
  
  saveClick(event) { //保存
    var that = this;
    console.log(that.data.appraisal_opinion)
    if (that.data.source_id == null || that.data.source_id == undefined || that.data.source_id == '') {
      wx.showToast({
        title: '请选择问题来源',
        icon: 'error',
        duration: 2000
      })
      return;
    } else if (that.data.priority_id == null || that.data.priority_id == undefined || that.data.priority_id == '') {
      wx.showToast({
        title: '请选择优先级',
        icon: 'error',
        duration: 2000
      })
      return;
    } else if (that.data.titles == null || that.data.titles == undefined || that.data.titles == '') {
      wx.showToast({
        title: '请输入标题',
        icon: 'error',
        duration: 2000
      })
      return;
    } else 
    // if (that.data.description == null || that.data.description == undefined || that.data.description == '') {
    //   wx.showToast({
    //     title: '请输入描述内容',
    //     icon: 'error',
    //     duration: 2000
    //   })
    //   return;
    // } else
     if (that.data.handle_user == null || that.data.handle_user == undefined || that.data.handle_user == '') {
      wx.showToast({
        title: '请选择处理人',
        icon: 'error',
        duration: 2000
      })
      return;
    } else if (that.data.appraisal_opinion == null || that.data.appraisal_opinion == undefined || that.data.appraisal_opinion == '') {
      wx.showToast({
        title: '请输入鉴定意见',
        icon: 'error',
        duration: 2000
      })
      return;
    } else {
      wx.request({
        url: app.globalData.address + 'question/onDbAppraisalUpd',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          id: that.data.id,
          title: that.data.titles,
          source_id: that.data.source_id,
          priority_id: that.data.priority_id,
          description: that.data.description,
          handle_user: that.data.handle_user,
          appraisal_opinion: that.data.appraisal_opinion,
          hope_solve_time: that.data.hope_solve_time,
          auditStatus: event.currentTarget.dataset.status,
          current_user:wx.getStorageSync('userInfo').id,
          userId:wx.getStorageSync('userInfo').id
        },
        success: function (res) {
          if (res.data.code == 0) {
            wx.showToast({
              title: '操作成功',
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