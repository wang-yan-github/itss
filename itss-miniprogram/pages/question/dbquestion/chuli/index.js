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
    title: '处理',
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
    columns: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
    id: '',
    solve_content: '', //描述
    form: '',
    steps: '',
    stepsactive: '',
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
    // Toast(`当前值：${value}, 当前索引：${index}`);
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
    // Toast(`当前值：${value}, 当前索引：${index}`);
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
    });
    //获取当前
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
        if(null!=obj.solve_content && undefined!=obj.solve_content && ''!=obj.solve_content){
          that.data.solve_content = obj.solve_content;
        }
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
            text: item.review_user_name +"审核",
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
      return time;
  },
  solveContent: function (e) { //描述
    var that = this;
    that.setData({
      solve_content: e.detail,
    })
  },
  saveClick: function (e) { //保存||解决方案
    var that = this;
    var auditStatus = e.currentTarget.dataset.status;
    console.log(that.data.solve_content )
    if (!that.data.solve_content) {
      wx.showToast({
        title: '请输入解决方案',
        icon: 'error',
        duration: 2000
      })
      return;
    } else {
      wx.request({
        url: app.globalData.address + 'question/onDbHandleUpd',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId":wx.getStorageSync('userInfo').id,
          "id": this.data.id,
          "current_user":wx.getStorageSync('userInfo').id,
          "auditStatus": auditStatus,
          "solve_content": this.data.solve_content,
          "create_time": this.data.form.create_time
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