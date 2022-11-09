// 弹框提示引用
import Dialog1 from '@vant/weapp/dialog/dialog';
//app引用
var app = getApp();
Page({
  data: {
    statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
    navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px', // 导航栏高度
    menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px', // 胶囊按钮高度
    navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px', // 导航栏和状态栏高度
    title: '问题待办',
    selected: true,
    searchHeight: 0,
    startTime: '请选择', //开始时间
    endTime: '请选择', //结束时间
    status: 0, //状态
    show: false,
    showsxqx: false,
    statusShow: false,
    showselect: false,
    showdia: false,
    shuoming: '',
    statustap: '请选择',
    actions: [],
    actionsList: [],
    showView: false,
    keyword: '',
    rowId: '',
    queryForm: {
      title: '', //关键字
      status: '', //状态
      create_start_time: '', //开单时间
      create_end_time: '' //开单结束时间
    },
    pages: '',
    totalPage: 0,
    startShow: false,
    startTag: true,
    minDate: new Date(2019, 10, 1).getTime(),
    selDate: new Date().getTime(),
    formatter(type, value) {
      if (type === 'year') {
        return `${value}年`;
      }
      if (type === 'month') {
        return `${value}月`;
      }
      return value;
    },
  },


  //筛选条件
  statusClick: function () { //状态点击按钮
    //初始化数据
    var that = this;
    //每次进来先清空数据
    that.setData({
      statusShow: true,
      actionsList: []
    });
    //获取筛选状态
    wx.request({
      url: app.globalData.address + 'question/selectByDictTypeList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        dict_type: "question_status"
      },
      success: function (res) {
        if (res.data.code == 0) {
          var list = res.data.data;
          if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
              that.data.actionsList.push({
                text: list[i].label,
                keys: list[i].value
              });
            }
            that.setData({
              statusShow: true,
              actionsList: that.data.actionsList
            });
          }
        }
      }
    })
  },
  onSelectStatus(event) { //状态下拉选择赋值
    var that = this;
    //给下拉框赋值
    that.data.queryForm.status = event.detail.value.keys; //取选择的值
    that.setData({
      statusShow: false,
      statustap: event.detail.value.text,
      queryForm: that.data.queryForm
    });
  },
  onCloseStatus() { //关闭状态
    var that = this;
    that.data.queryForm.status = '';
    that.setData({
      statusShow: false,
      statustap: '请选择',
      queryForm: that.data.queryForm
    });
  },
  onChangejieju(event) { //筛选状态
    const {
      picker,
      value,
      index
    } = event.detail;
  },


  onLoad: function (options) {
    //  高度引用
    let query = wx.createSelectorQuery();
    let that = this;
    query.select("#searchs").boundingClientRect()
    query.exec(function (res) {
      //取高度
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
        searchHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight')
      });
    })
    
  },
  onshuoming(event) {
    this.data.shuoming = event.detail;
  },
  revokeClick() { //挂起点击事件
    this.setData({
      showdia: false,
      shuoming: '',
    });
  },
  revokeQuestion(event) { //挂起方法
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
      url: app.globalData.address + 'question/onDbHangUpd',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        "userId":wx.getStorageSync('userInfo').id,
        "id": id,
        "current_user": wx.getStorageSync('userInfo').id,
        "suspend_reason": that.data.shuoming,
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            shuoming: '',
            list: that.data.list,
          });
          wx.showToast({
            title: '操作成功',
            icon: 'success',
            duration: 2000
          })
          that.page();
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
  backTo() {
    wx.navigateBack({
      delta: 0,
    })
  },
  showPopup() { //筛选状态
    this.setData({
      showselect: true
    });
  },
  showaction: function (options) {
    var that = this;
    var status = options.currentTarget.dataset.status;
    var authority = wx.getStorageSync('authority');
    that.data.actions = [];
    that.data.actions.push({
      name: '详情'
    });
    if (authority.question_dealtWith_zhuandan && (status == 3 || status == 8)) {
      that.data.actions.push({
        name: '转单'
      });
    } else if (authority.question_dealtWith_huifu && status == 9) {
      that.data.actions.push({
        name: '恢复'
      });
    } else if (authority.question_dealtWith_guaqi && (status == 3 || status == 8)) {
      that.data.actions.push({
        name: '挂起'
      });
    }
    var index = options.currentTarget.dataset.id;
    that.data.rowId = index;
    this.setData({
      show: true,
      rowId: index,
      actions: that.data.actions
    });
  },
  onClose() {
    this.setData({
      show: false
    });
  },
  onSelect(event) {
    let that = this;
    if (event.detail.name == "转单") {
      wx.navigateTo({
        url: 'zhuandan/index?id=' + event.currentTarget.dataset.id,
      })
    } else if (event.detail.name == "详情") {
      wx.navigateTo({
        url: 'detail/index?id=' + event.currentTarget.dataset.id,
      })
    } else if (event.detail.name == "挂起") {
      this.setData({
        showdia: true
      });
    } else if (event.detail.name == "恢复") {
      Dialog1.confirm({
        title: '提示',
        message: '确定要恢复吗？',
      }).then(() => {
        wx.request({
          url: app.globalData.address + 'question/onDbRecoveryUpd',
          method: 'POST',
          header: {
            "Authorization": wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            "userId":wx.getStorageSync('userInfo').id,
            "id": event.currentTarget.dataset.id,
            "current_user": wx.getStorageSync('userInfo').id
          },
          success: function (res) {
            console.log(res)
            if (res.data.code == 0) {
              wx.showToast({
                title: '操作成功',
                icon: 'success',
                duration: 2000
              })
              that.page();
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
    }
  },
  showaction1: function (options) {
    this.setData({
      showsxqx: true
    });
  },
  //关键字搜索
  onkeyword: function (e) {
    this.data.queryForm.title = e.detail.value;
  },
  onClosepopupSX() { //筛选取消按钮
    var that = this;
    that.data.queryForm.title = '';
    that.data.queryForm.status = '';
    that.data.queryForm.create_start_time = '';
    that.data.queryForm.create_end_time = '';
    this.setData({
      showselect: false,
      queryForm: that.data.queryForm,
      zhuangtai: '请选择',
      statustap: '请选择',
      startTime: '请选择',
      endTime: '请选择'
    });
    this.page();
  },
  queryDataClick() { //查询按钮
    var that = this;
    if (that.data.count == 1) { //判断如果未点击开始结束时间，不进行传值
      that.data.queryForm.create_start_time = that.data.startTime; //开始时间
      that.data.queryForm.create_end_time = that.data.endTime; //结束时间
    }
    that.setData({
      showselect: false,
      queryForm: that.data.queryForm
    });
    that.page(1); //调用page
  },
  confirmData(event) {
    let selData = new Date(event.detail).toLocaleDateString()
    if (this.data.startTag) {
      this.setData({
        startTime: this.dataToStr(selData),
      });
    } else {
      this.setData({
        endTime: this.dataToStr(selData),
      });
    }
    this.setData({
      startShow: false
    });
  },
  dataToStr(selData) {
    var date = new Date(selData);
    var YY = date.getFullYear() + '-';
    var MM = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var DD = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate());
    var hh = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var mm = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var ss = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
    // return YY + MM + DD + " " + hh + mm + ss;
    return YY + MM + DD ;
  },
  onCloseDatatime() {
    if (this.data.startTag) {
      this.setData({
        startShow: false,
        startTime: '请选择',
      });
    } else {
      this.setData({
        startShow: false,
        endTime: '请选择',
      });
    }
  },
  bindStartDateChange: function (e) { //开始时间
    this.setData({
      startShow: true,
      startTag: true,
    });
  },
  bindEndDateChange: function (e) { //结束时间
    this.setData({
      startShow: true,
      startTag: false,
    });
  },
  jiandingClick: function (e) {
    console.log(e.currentTarget.dataset.strs)
    if (e.currentTarget.dataset.strs == 1) { //鉴定跳转
      wx.navigateTo({
        url: 'jianding/index?id=' + e.currentTarget.dataset.id
      })
    } else if (e.currentTarget.dataset.strs == 2) { //审核跳转
      wx.navigateTo({
        url: 'shenhe/index?id=' + e.currentTarget.dataset.id,
      })
    } else if (e.currentTarget.dataset.strs == 3) {
      wx.navigateTo({
        url: 'chuli/index?id=' + e.currentTarget.dataset.id,
      })
    }
  },
  page: function (val) { //列表分页功能
    var that = this;
    wx.request({
      url: app.globalData.address + 'question/getDbPageList',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "title": that.data.queryForm.title,
        "status": that.data.queryForm.status,
        "create_time_start": that.data.startTime == '请选择' ? '' : that.data.startTime,
        "create_time_end": that.data.endTime == '请选择' ? '' : that.data.endTime,
        "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
        "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize,
        "now_operator_user": wx.getStorageSync('userInfo').id
      },
      success: function (res) {
        if (res.data.code == 0) {
          if (res.data.data.list.length == 0) {
            that.setData({
              list: [],
              showView: false,
              totalPage: 0
            })
          } else {
            that.setData({
              list: res.data.data.list,
              pageIndex: res.data.data.pageNum,
              pageSize: res.data.data.pageSize,
              totalPage: res.data.data.total,
              pages: res.data.data.pages,
              showView: false
            })
          }
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'none',
            duration: 2000
          })
        }
        //结束刷新
        if (val == 2) {
          wx.stopPullDownRefresh();
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
    var that = this;
    that.page(); //调用page分页
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
    //下拉刷新
    wx.showLoading({
      title: '正在刷新...',
      icon: 'none',
      duration: 1500
    })
    var that = this;
    that.setData({
      pageIndex: 1
    })
    that.page()
    setTimeout(function () {
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    //上拉加载下一页
    var that = this;
    if (that.data.pageIndex < that.data.pages) {
      wx.showLoading({
        title: '数据加载中...',
        icon: 'loading',
        duration: 1500
      })
      wx.request({
        url: app.globalData.address + 'question/getDbPageList',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          "title": that.data.queryForm.title,
          "status": that.data.queryForm.status,
          "create_time_start": that.data.startTime == '请选择' ? '' : that.data.startTime,
          "create_time_end": that.data.endTime == '请选择' ? '' : that.data.endTime,
          "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
          "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize
        },
        success: function (res) {
          that.setData({
            list: res.data.data.list,
            pageIndex: that.data.pageIndex + 1,
            pageSize: that.data.pageSize
          })
          if (res.data.data.list.length == 0) {
            that.setData({
              showView: true
            })
          }
        }
      })
    } else {
      that.setData({
        showView: true
      })
    }
    wx.pageScrollTo({
      scrollTop: 0
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
});