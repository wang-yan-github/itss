// pages/thing/working/index.js
import Dialog from '@vant/weapp/dialog/dialog';
//app引用
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
    title: '变更管理',
    selected: true, //列表样式
    searchHeight: 0, //设置高度
    startTime: '请选择', //开始时间
    endTime: '请选择', //结束时间
    show: false,
    showdiack: false,
    statusShow: false, //状态
    typeShow: false, //变更类别
    sortShow: false, //变更类型
    statustap: '请选择', //状态
    typetap: '请选择',
    sorttap: '请选择',
    actionsList: [], //状态
    typeList: [], //变更类别
    sortList: [], //变更类型
    showselect: false,
    showselect1: false,
    indexList: ["A", "B", "C", "D"],
    showdia: false,
    showdiack: false,
    shuoming: '',
    shuomingck: '',
    zhuangtai: '去选择',
    username: '去选择',
    actions: [],
    list: [],
    detailId: '',
    queryForm: {
      change_classify: '', //变更类别
      change_type: '', //变更类型
      keyword: '', //关键字
      status: '', //状态
      create_start_time: '', //开单时间
      create_end_time: '' //开单结束时间
    },
    startShow: false,
    startTag: true,
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
        dict_type: "change_status"
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
  sortClick: function () { //变更类别点击按钮
    //初始化数据
    var that = this;
    //每次进来先清空数据
    that.setData({
      sortShow: true,
      sortList: []
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
        dict_type: "change_type"
      },
      success: function (res) {
        if (res.data.code == 0) {
          var list = res.data.data;
          if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
              that.data.sortList.push({
                text: list[i].label,
                keys: list[i].value
              });
            }
            that.setData({
              sortShow: true,
              sortList: that.data.sortList
            });
          }
        }
      }
    })
  },
  onSelectSort(event) { //变更类别下拉选择赋值
    var that = this;
    //给下拉框赋值
    that.data.queryForm.change_type = event.detail.value.keys; //取选择的值
    that.setData({
      sortShow: false,
      sorttap: event.detail.value.text,
      queryForm: that.data.queryForm
    });
  },
  onCloseSort() { //关闭类别
    var that = this;
    that.data.queryForm.change_type = ''
    that.setData({
      sortShow: false,
      sorttap: '请选择',
      queryForm: that.data.queryForm
    });
  },
  typeClick: function () { //变更类别点击按钮
    //初始化数据
    var that = this;
    //每次进来先清空数据
    that.setData({
      typeShow: true,
      typeList: []
    });
    //获取筛选状态
    wx.request({
      url: app.globalData.address + 'change/getAll.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        dict_type: "change_type"
      },
      success: function (res) {
        if (res.data.code == 0) {
          var list = res.data.data;
          if (list.length > 0) {
            for (var i = 0; i < list.length; i++) {
              that.data.typeList.push({
                text: list[i].type_name,
                keys: list[i].id
              });
            }
            that.setData({
              typeShow: true,
              typeList: that.data.typeList
            });
          }
        }
      }
    })
  },
  onSelectType(event) { //类别下拉选择赋值
    var that = this;
    that.data.queryForm.change_classify = event.detail.value.keys; //取选择的值
    that.setData({
      typeShow: false,
      typetap: event.detail.value.text,
      queryForm: that.data.queryForm
    });
  },
  onCloseType() { //关闭类别
    var that = this;
    that.data.queryForm.change_classify = '';
    that.setData({
      typeShow: false,
      typetap: '请选择',
      queryForm: that.data.queryForm
    });
  },
  onClosepopupSX() { //点击筛选条件中的取消
    var that = this;
    that.data.queryForm.change_classify = '';
    that.data.queryForm.change_type = '';
    that.data.queryForm.status = '';
    that.data.queryForm.create_start_time = '';
    that.data.queryForm.create_end_time = '';
    this.setData({
      showselect: false,
      queryForm: that.data.queryForm,
      statustap: '请选择',
      typetap: '请选择',
      sorttap: '请选择',
      startTime: '请选择',
      endTime: '请选择'
    });
    this.page();
  },
  queryDataClick() { //点击筛选条件中的确定查询按钮
    var that = this;
    if (that.data.count == 1) { //判断如果未点击开始结束时间，不进行传值
      that.data.queryForm.create_start_time = that.data.startTime; //开始时间
      that.data.queryForm.create_end_time = that.data.endTime; //结束时间
    }
    that.setData({
      showselect: false,
      queryForm: that.data.queryForm
    });
    that.page(); //调用page
  },
  //关键字搜索
  onkeyword: function (e) {
    this.data.queryForm.keyword = e.detail.value;
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
  zhuanbanClick: function (event) {
    wx.navigateTo({
      url: 'zhuandan/index?id=' + event.currentTarget.dataset.id,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let query = wx.createSelectorQuery();
    let that = this;
    // query.select("#tabs").boundingClientRect()
    query.select("#searchs").boundingClientRect()
    query.exec(function (res) {
      //取高度
      //console.log(res[0].height,res[1].height);
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
        searchHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight')
      });
    })
    that.page(); //调用page分页
  },
  //列表分页功能
  page: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'change/getPage.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        "userId": wx.getStorageSync('userInfo').id,
        "change_classify": that.data.queryForm.change_classify,
        "change_type": that.data.queryForm.change_type,
        "keyword": that.data.queryForm.keyword,
        "status": that.data.queryForm.status,
        "create_start_time": that.data.startTime == '请选择' ? '' : that.data.startTime,
        "create_end_time": that.data.endTime == '请选择' ? '' : that.data.endTime,
        "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
        "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize,
        "data_flag": '2'
      },
      success: function (res) {
        if (res.data.code == 0) {
          if (res.data.data.list.length == 0) {
            that.setData({
              list: [],
              totalPage: 0,
              showView: true
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
  //弹窗方法	-备注
  remarksAddClick(event) {
    var that = this;
    if (null == that.detailId || that.detailId == '') {
      wx.showToast({
        title: '请选择一条数据',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    if (null == that.data.shuoming || that.data.shuoming == '' || undefined == that.data.shuoming) {
      wx.showToast({
        title: '请输入备注内容',
        icon: 'none',
        duration: 2000
      });
      return;
    } else {
      wx.request({
        url: app.globalData.address + 'change/toRemark.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId": wx.getStorageSync('userInfo').id,
          "id": that.data.detailId,
          "remark": that.data.shuoming, //备注
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
  onCloseRemarks() { //备注
    this.setData({
      showdia: false,
      shuoming: '',
    });
  },
  //重开弹框
  reopenAddClick(event) {
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
        "id": id,
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
  onCloseReopen() {
    this.setData({
      showdiack: false,
      shuomingck: '',
    });
  },
  /**选择客户发生事件 */
  getCustomer: function (event) {
    this.setData({
      showselect1: false,
      username: event.currentTarget.dataset.name,
    });
  },
  backTo() {
    wx.navigateBack({
      delta: 0,
    })
  },
  showPopup() {
    this.setData({
      showselect: true
    });
  },
  onClosepopup() {
    this.setData({
      showselect: false
    });
  },
  onClosepopup1() {
    this.setData({
      showselect1: false
    });
  },
  showaction: function (event) {
    this.detailId = event.currentTarget.dataset.id
    var status = event.currentTarget.dataset.status
    this.data.actions = [];
    var authority = wx.getStorageSync('authority');
    if (authority.change_manage_beizhu) {
      this.data.actions.push({
        name: '备注'
      });
    }
    if (authority.change_manage_delete) {
      this.data.actions.push({
        name: '删除'
      });
    }
    this.data.actions.push({
      name: '详情'
    });
    if (authority.change_manage_chexiao && status != 12) {
      this.data.actions.push({
        name: '撤销'
      });
    }
    this.setData({
      show: true,
      actions: this.data.actions
    });
  },
  onClose() {
    this.setData({
      show: false
    });
  },
  onSelect(event) {
    var that = this;
    if (event.detail.name == "备注") {
      this.setData({
        showdia: true
      });
    } else if (event.detail.name == "详情") {
      wx.navigateTo({
        url: 'detail/index?id=' + that.detailId
      })
    } else if (event.detail.name == "撤销") {
      Dialog.confirm({
          title: '提示',
          message: '确定要撤销吗？',
        }).then(() => {
          wx.request({
            url: app.globalData.address + 'change/toCancel.do',
            method: 'POST',
            header: {
              "Authorization": wx.getStorageSync('tokenValue'),
              "Content-Type": "application/json"
            },
            data: {
              "userId": wx.getStorageSync('userInfo').id,
              "id": that.detailId,
              "status": '12'
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

        });
    } else if (event.detail.name == "删除") {
      console.log(that.detailId)
      Dialog.confirm({
          title: '提示',
          message: '确定要删除吗？',
        }).then(() => {
          wx.request({
            url: app.globalData.address + 'change/deleteChange.do',
            method: 'POST',
            header: {
              "Authorization": wx.getStorageSync('tokenValue'),
              "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
              "id": that.detailId
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

        });
    }
  },
  showaction1: function (options) {
    this.setData({
      show1: true
    });
  },
  onClose1() {
    this.setData({
      show1: false
    });
  },
  onSelect1(event) {
    this.setData({
      zhuangtai: event.detail.name
    });
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
        url: app.globalData.address + 'change/getPage.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "userId": wx.getStorageSync('userInfo').id,
          "change_classify": that.data.queryForm.change_classify,
          "change_type": that.data.queryForm.change_type,
          "keyword": that.data.queryForm.keyword,
          "status": that.data.queryForm.status,
          "create_start_time": that.data.startTime == '请选择' ? '' : that.data.startTime,
          "create_end_time": that.data.endTime == '请选择' ? '' : that.data.endTime,
          "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
          "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize,
          "data_flag": '2'
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

  }
})