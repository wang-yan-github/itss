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
    title: '问题管理',
    selected: true,
    searchHeight: 0,
    startTime: '请选择', //开始时间
    endTime: '请选择', //结束时间
    show: false,
    showsxqx: false,
    statusShow: false,
    statustap: '请选择',
    showselect: false,
    showdia: false,
    showdia2: false,
    showdia4: false,
    shuoming: '',
    actions: [],
    //】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
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
    authorityData:wx.getStorageSync('authority'),
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



  //弹窗方法
  onshuoming(event) {
    this.data.shuoming = event.detail;
  },
  remarksClick() { //备注点击关闭方法
    this.setData({
      showdia: false,
      shuoming: '',
    });
  },
  //备注方法
  remarksQuestion(event) {
    var that = this;
    let id = event.currentTarget.dataset.id;
    if (null == id || id == '') {
      wx.showToast({
        title: '请选择一条数据',
        icon: 'none',
        duration: 2000
      });
      return;
    } else if (null == that.data.shuoming || that.data.shuoming == '' || undefined == that.data.shuoming) {
      wx.showToast({
        title: '请输入备注',
        icon: 'none',
        duration: 2000
      });
      return;
    } else {
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
          "create_user": wx.getStorageSync('userInfo').id,
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
    }

  },
  deleteClick() { //删除点击事件
    this.setData({
      showdelete: false,
      shuoming: '',
    });
  },
  revokeClick() { //撤销点击事件
    this.setData({
      showdia2: false,
      shuoming: '',
    });
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
  },
  recoveryClick(event) { //恢复点击方法
    this.setData({
      shuoming: '',
    });
  },
  onCloseRecover() { //恢复关闭
    this.setData({
      showdia4: false,
      shuoming: '',
    });
  },
  backTo() { //返回上一层
    wx.navigateBack({
      delta: 0,
    })
  },
  showPopup() { //点击筛选按钮
    this.setData({
      showselect: true
    });
  },
  onClosepopup() {
    this.setData({
      showselect: false
    });
  },
  showaction: function (options) { //筛选状态下拉
    var that = this;
    var authority = wx.getStorageSync('authority');
    that.data.actions = [];
    that.data.actions.push({
      name: '详情'
    });
    if (authority.question_manage_beizhu) {
      that.data.actions.push({
        name: '备注'
      });
    } else if (status == 4 && authority.question_myquestion_delete) {
      that.data.actions.push({
        name: '关单'
      });
    } else if (authority.question_manage_chexiao) {
      that.data.actions.push({
        name: '撤销'
      });
    } else if (authority.question_manage_shanchu) {
      that.data.actions.push({
        name: '删除'
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
  onClose() { //取消方法
    this.setData({
      show: false
    });
  },
  onSelect(event) { //更多按钮下拉
    let that = this;
    var authority = wx.getStorageSync('authority');
    console.log(authority.question_manage_beizhu)
    if (authority.question_manage_beizhu && event.detail.name == "备注") {
      this.setData({
        showdia: true
      });
    } else if (event.detail.name == "详情") {
      wx.navigateTo({
        url: 'detail/index?id=' + event.currentTarget.dataset.id,
      })
    } else if (authority.question_manage_chexiao && event.detail.name == "撤销") {
      this.setData({
        showdia2: true
      });
    } else if (authority.question_manage_shanchu && event.detail.name == "删除") {
      Dialog1.confirm({
        title: '提示',
        message: '确定要删除吗？',
      }).then(() => {
        wx.request({
          url: app.globalData.address + 'question/deleteQuestion.do',
          method: 'POST',
          header: {
            "Authorization": wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            "id": event.currentTarget.dataset.id,
            "user_id": wx.getStorageSync('userInfo').id
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
    let selDataArr = selData.split("/")
    let strArr = selDataArr.map(function (value) {
      if (value.length == 1) {
        value = 0 + value
      }
      return value
    });
    return strArr.join("-")
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
  zhuandanClick: function (e) { //转办跳转方法
    wx.navigateTo({
      url: 'zhuandan/index?id=' + e.currentTarget.dataset.id,
    })
  },
  onDbCloseUpdClick: function (event) { //关单操作
    var that = this;
    Dialog1.confirm({
      title: '提示',
      message: '确定要关单吗？',
    }).then(() => {
      wx.request({
        url: app.globalData.address + 'question/onDbCloseUpd',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId":wx.getStorageSync('userInfo').id,
          "id": event.currentTarget.dataset.id
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
    }).catch(() => {
      // on cancel
    });
  },
  onLoad: function (options) {
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
    // that.page();
  },
  page: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'question/getManageByPage.do',
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
        "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
        "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize
      },
      success: function (res) {
        if (res.data.code == 0) {
          if (res.data.data.list.length == 0) {
            that.setData({
              list: [],
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
        url: app.globalData.address + 'question/getManageByPage.do',
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
})