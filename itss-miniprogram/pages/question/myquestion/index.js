// pages/thing/working/index.js
import Dialog1 from '@vant/weapp/dialog/dialog';
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
    title: '我的问题',
    selected: true,
    showViewDb: false, //通过true 和 false 来控制显示隐藏
    searchHeight: 0,
    startTime: '请选择', //开始时间
    endTime: '请选择', //结束时间
    status: 0,
    show: false,
    statusShow: false,
    show1: false,
    showselect: false,
    showdia: false,
    showdia2: false,
    showdia4: false,
    shuoming: '',
    actions: [], //更多操作按钮
    actionsList: [], //筛选下来状态
    showView: false,
    statustap: '请选择',
    rowId: '',
    count: 0, //时间点击判断
    startShow: false,
    startTag: true,
    queryForm: {
      title: '', //关键字
      status: '', //状态
      create_start_time: '', //开单时间
      create_end_time: '' //开单结束时间
    },
    pages: '',
    totalPage: 0,
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
        dict_type: "question_status",
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
  detailClick: function (e) { //进入详情页面
    wx.navigateTo({
      url: 'detail/index?id=' + e.currentTarget.dataset.id,
    })
  },
  onLoad: function (options) { //页面加载数据
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
    // that.page(); //分页
  },
  page: function (val) { //分页
    var that = this;
    wx.request({
      url: app.globalData.address + 'question/my/list.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "title": that.data.queryForm.title,
        "status": that.data.queryForm.status,
        "create_start_time": that.data.startTime == '请选择' ? '' : that.data.startTime,
        "create_end_time": that.data.endTime == '请选择' ? '' : that.data.endTime,
        "pageNo": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
        "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize,
        "current_user": wx.getStorageSync('userInfo').id
      },
      success: function (res) {
        if (res.data.code == 0) {
          if (res.data.data.list.length == 0) {
            that.setData({
              list: [], //将数据清空
              totalPage: 0,
              showView: false
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
  //弹窗方法
  onshuoming(event) {
    this.data.shuoming = event.detail;
  },
  //备注
  remarksClick(event) { //备注方法
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
    if (that.data.shuoming != null && that.data.shuoming != '' && that.data.shuoming != undefined) {
      wx.request({
        url: app.globalData.address + 'question/addRemark',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId":wx.getStorageSync('userInfo').id,
          "id": id,
          "remark": that.data.shuoming,
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
    } else {
      wx.showToast({
        title: '请输入备注内容',
        icon: 'none',
        duration: 2000
      });
      return;
    }
  },
  onCloseRemarksClick() { //备注关闭
    this.setData({
      showdia: false,
      shuoming: '',
    });
  },
  getUserInfo1(event) {
    this.setData({
      shuoming: '',
    });
  },
  onClosedia1() {
    this.setData({
      showdia1: false,
      shuoming: '',
    });
  },
  // 撤销
  revokeClick(event) {
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
    if (that.data.shuoming != null && that.data.shuoming != '' && that.data.shuoming != undefined) {
      wx.request({
        url: app.globalData.address + 'question/toRevoke.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "id": id,
          "userId": wx.getStorageSync('userInfo').id,
          "remark": that.data.shuoming,
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
    } else {
      wx.showToast({
        title: '请输入撤销说明',
        icon: 'none',
        duration: 2000
      });
      return;
    }
  },
  onCloseRevokeClick() {
    this.setData({
      showdia2: false,
      shuoming: '',
    });
  },
  recoveryClick(event) { //恢复方法
    console.log(event.detail);
    this.setData({
      shuoming: '',
    });
  },
  onCloseRecovery() { //关闭恢复弹框
    this.setData({
      showdia4: false,
      shuoming: '',
    });
  },
  backTo() { //返回
    wx.navigateBack({
      delta: 0,
    })
  },
  showPopup() { //点击筛选带出下拉数据
    this.setData({
      showselect: true
    });
  },
  showaction: function (options) { //更多操作
    var that = this;
    var status = options.currentTarget.dataset.status;
    var authority = wx.getStorageSync('authority');
    that.data.actions = [];
    if (status == 4 && authority.question_myquestion_delete) {
      that.data.actions.push({
        name: '关单'
      });
    } 
    if (authority.question_myquestion_guandan && (status == 1 || status == 7)) {
      that.data.actions.push({
        name: '撤销'
      });
    } 
    if (authority.question_myquestion_beizhu) {
      that.data.actions.push({
        name: '备注'
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
  onClose() { //更多按钮取消
    this.setData({
      show: false
    });
  },
  onSelect(event) { //获取更多按钮下拉
    let that = this;
    if (event.detail.name == "备注") {
      this.setData({
        showdia: true,
        shuoming: ''
      });
    } else if (event.detail.name == "撤销") {
      this.setData({
        showdia2: true,
        shuoming: ''
      });
    } else if (event.detail.name == "关单") {
      let id = event.currentTarget.dataset.id;
      if (id == '') {
        wx.showToast({
          title: '请选择要关闭的问题',
          icon: 'none',
          duration: 2000
        })
        return;
      }
      Dialog1.confirm({
          title: '提示',
          message: '确定要关闭吗？',
        })
        .then(() => {
          // on confirm
          wx.request({
            url: app.globalData.address + 'question/onDbCloseUpd',
            method: 'POST',
            header: {
              "Authorization": wx.getStorageSync('tokenValue'),
              "Content-Type": "application/json"
            },
            data: {
              "userId":wx.getStorageSync('userInfo').id,
              "id": id,
            },
            success: function (res) {
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
        })
        .catch(() => {
          // on cancel
        });

    }
  },
  showStatus: function (options) { //状态
    this.setData({
      show1: true
    });
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
      statustap: '请选择',
      startTime: '请选择',
      endTime: '请选择'
    });
    this.page();
  },
  //关键字搜索
  onkeyword: function (e) {
    this.data.queryForm.title = e.detail.value;
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
    var that = this;
    //下拉刷新
    wx.showLoading({
      title: '正在刷新...',
      icon: 'none',
      duration: 1500
    })
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
        url: app.globalData.address + 'question/my/list.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          "title": that.data.queryForm.title,
          "status": that.data.queryForm.status,
          "create_start_time": that.data.startTime == '请选择' ? '' : that.data.startTime,
          "create_end_time": that.data.endTime == '请选择' ? '' : that.data.endTime,
          "pageNo": that.data.pageIndex == undefined ? 1 : that.data.pageIndex + 1,
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
  addgd: function (e) { //开单
    wx.navigateTo({
      url: 'add/index',
    })
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  /**
   * 初始化分页查询
   */

})