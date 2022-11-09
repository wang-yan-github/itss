// pages/thing/working/chuli/index.js
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
    title: '实施',
    topHeight: 0,
    activeNames: ['6'],
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
    startTime: ymd, //开始时间
    endTime: ymd, //结束时间
    columns: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
    implement_remark: '', //变更方案
    form: '', //对象
    stepData: '',
    stepsactive: 0,
    id: ''
  },
  bindDateStartTime: function (e) { //开始时间
    this.setData({
      startTime: e.detail.value
    })
  },
  bindDateEndTime: function (e) { //结束时间
    this.setData({
      endTime: e.detail.value
    })
  },
  approveRemark: function (e) { //描述
    this.setData({
      implement_remark: e.detail
    })
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
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
      });
    })

    //获取当前详情数据
    if (null == options || undefined == options || options.id == undefined) {
      return;
    }
    let id = options.id;
    that.data.id = id;
    wx.request({
      url: app.globalData.address + 'change/getChange.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        "id": id
      },
      success: function (res) {
        let obj = res.data.data;
        that.data.stepData = [];
        console.log(obj)
        if (obj.change_type == "1") {
          obj.changeNodes.forEach((item, i) => {
            //紧急 1.开单 2.审批 3.实施 4.实施后评审 5.关单
            if (item.time_nodes == "1") {
              that.data.stepData.push({
                text: "开单",
                desc: item.create_time == null ? "" : item.create_time
              });
            } else if (item.time_nodes == "2") {
              that.data.stepData.push({
                text: "审批",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "5") {
              that.data.stepData.push({
                text: "实施",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "6") {
              that.data.stepData.push({
                text: "实施后评审",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "7") {
              that.data.stepData.push({
                text: "关单",
                desc: item.update_time == null ? "" : item.update_time
              });
            }
          });
        } else {
          //一般、重大时间轴节点 1.开单 2.审批 3.制定方案 4.方案审核 5.实施 6.实施后评审 7.关单 出现多节点的情况下用sort排序
          obj.changeNodes.forEach((item, i) => {
            if (item.time_nodes == "1") {
              that.data.stepData.push({
                text: "开单",
                desc: item.create_time == null ? "" : item.create_time
              });
            } else if (item.time_nodes == "2") {
              that.data.stepData.push({
                text: "审批",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "3") {
              that.data.stepData.push({
                text: "制定方案",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "4") {
              that.data.stepData.push({
                text: "方案审核",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "5") {
              that.data.stepData.push({
                text: "实施",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "6") {
              that.data.stepData.push({
                text: "实施后评审",
                desc: item.update_time == null ? "" : item.update_time
              });
            } else if (item.time_nodes == "7") {
              that.data.stepData.push({
                text: "关单",
                desc: item.update_time == null ? "" : item.update_timee
              });
            }
          });
        }
        that.data.stepData.forEach(function (item) {
          if ('' != item.desc) {
            that.data.stepsactive = that.data.stepsactive + 1;
          }
        });
        that.setData({
          form: obj,
          stepData: that.data.stepData,
          stepsactive: that.data.stepsactive - 1,
        })
      }
    })
  },
  saveClick: function (event) {
    var that = this;
    if (null == that.data.startTime || undefined == that.data.startTime || '' == that.data.startTime) {
      wx.showToast({
        title: '请选择实施开始时间',
        icon: 'error',
        duration: 2000
      })
      return;
    } else if (null == that.data.endTime || undefined == that.data.endTime || '' == that.data.endTime) {
      wx.showToast({
        title: '请选择实施结束时间',
        icon: 'error',
        duration: 2000
      })
      return;
    }else if(null==that.data.implement_remark||undefined==that.data.implement_remark||''==that.data.implement_remark){
      wx.showToast({
        title: '请输入审批意见',
        icon: 'error',
        duration: 2000
      })
      return;
    } else {
      var status = event.currentTarget.dataset.id;
      that.data.form.changeNode.approve_status = status;
      that.data.form.changeImplement.status = status;
      that.data.form.changeImplement.implement_remark = that.data.implement_remark;  
      wx.request({
        url: app.globalData.address + 'change/toImplement.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "id": that.data.id,
          "userId": wx.getStorageSync('userInfo').id,
          "changeNode": that.data.form.changeNode,
          "changeImplement": that.data.form.changeImplement,
          "changeProgrammeApprove":that.data.form.changeProgrammeApprove,
          "changeImplementApprove":that.data.form.changeImplementApprove,
          "changeInfo": that.data.form.changeInfo,
          "start_time": that.data.startTime,
          "end_time": that.data.endTime
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
  onClosepopup() {
    wx.navigateTo({
      url: '../index'
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