// pages/thing/working/index.js
var app = getApp();
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
var ymd2 = Y + "-" + M + "-" + D + " " + h + ":" + m;
import Dialog from '@vant/weapp/dialog/dialog';
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
    title: '自助服务',
    selected: true,
    selected1: false,
    selected2: false,
    selected3: false,
    itemselected: false,
    topHeight: 0,
    searchHeight: 0,
    startTime: '请选择', //开始时间
    endTime: '请选择', //结束时间
    startTime2: ymd, //开始时间
    endTime2: ymd, //结束时间
    // status: 0,
    show: false,
    showRequestType: false, //请求类型请选择
    showState: false, // 状态请选择
    isShowPdq: false, // 盘点期请选择
    isShowAssetsType: false, // 配置项类型请选择
    isShowChildAsset: false, // 配置子类请选择
    isShowPdStatus: false, // 盘点状态请选择
    isShowHandleStatus: false, // 处理状态请选择
    showselect: false,
    showselect1: false,
    indexList: ["A", "B", "C", "D"],
    scrollTop: 0,
    showdia: false,
    showdia1: false,
    showdia2: false,
    showdia3: false,
    showdia4: false,
    shuoming: '',
    customerList: [{
        firstLetter: "A",
        list: [{
            code: "AB01",
            name: "A1客户"
          },
          {
            code: "AB02",
            name: "A2客户"
          },
          {
            code: "AB03",
            name: "A3客户"
          },
        ],
      },
      {
        firstLetter: "B",
        list: [{
            code: "BB01",
            name: "B1客户"
          },
          {
            code: "BB02",
            name: "B2客户"
          },
          {
            code: "BB03",
            name: "B3客户"
          },
          {
            code: "BB04",
            name: "B4客户"
          },
        ],
      },
      {
        firstLetter: "C",
        list: [{
            code: "CB01",
            name: "C1客户"
          },
          {
            code: "CB02",
            name: "C2客户"
          },
          {
            code: "CB03",
            name: "C3客户"
          },
          {
            code: "CB04",
            name: "C4客户"
          },
          {
            code: "CB05",
            name: "C5客户"
          },
        ],
      },
      {
        firstLetter: "D",
        list: [{
            code: "DB01",
            name: "D1客户"
          },
          {
            code: "DB02",
            name: "D2客户"
          },
          {
            code: "DB03",
            name: "D3客户"
          },
          {
            code: "DB04",
            name: "D4客户"
          },
          {
            code: "DB05",
            name: "D5客户"
          },
          {
            code: "DB06",
            name: "D6客户"
          },
        ],
      },
    ],
    requestType: '去选择',
    status: '去选择',
    requestTypeId: '',
    statusId: '',
    username: '去选择',
    actions: [{
        name: '修改',
      },
      {
        name: '重开',
      },
      {
        name: '撤销',
      },
      {
        name: '确认评价',
      },
    ],
    //请求类型
    requestTypeList: [],
    //状态
    statusList: [],
    chulichecked: false,
    showView: false, //通过true 和 false 来控制显示隐藏
    showViewDb: false, //通过true 和 false 来控制显示隐藏
    list: [],
    pageIndex: 1,
    pageSize: 10,
    totalPage: '',
    pages: '',
    keyword: '',
    searchType: '1',
    listType: '1', //1我的工单 2资产盘点
    keyword2: '',
    searchType2: '1',
    panDianQiArr: [], //所属盘点期
    assetsTypes: [], //配置项类型
    assetsChildren: [], //配置子类
    //盘点状态
    pdStatusList: [{
        id: 0,
        name: '未确认',
      },
      {
        id: 1,
        name: '确认无误',
      },
      {
        id: 2,
        name: '确认有误',
      },
    ],
    //处理状态
    handleStatusList: [{
        id: 0,
        name: '未处理',
      },
      {
        id: 1,
        name: '已处理',
      },
    ],
    pdqName: '去选择',
    pdqId: '',
    assetsTypeName: '去选择',
    assetsTypeId: '',
    childTypeName: '去选择',
    childTypeId: '',
    pdStatusName: '去选择',
    pdStatusId: '',
    handleStatusName: '去选择',
    handleStatusId: '',
    rowId: '',
    rowId2: '',
    qruser: '',
    authority: '',
  },
  //状态栏切换
  changtab1(event) {
    this.setData({
      selected: true,
      selected1: false,
      selected2: false,
      listType: '1',
    });
    //我的工单
    this.page(1)
  },
  changtab2(event) {
    //资产盘点
    this.setData({
      selected: false,
      selected1: true,
      selected2: false,
      listType: '2',
    });
    this.assetsPage(1)
  },
  changtab3(event) { //暂时未用到
    this.setData({
      selected: false,
      selected1: false,
      selected2: true,
    });
  },
  //索引栏发生变化事件	
  onPageScroll(event) {
    this.setData({
      scrollTop: event.scrollTop,
    });
  },
  //选中状态切换	
  onChangechuli(event) {
    var that = this;
    this.setData({
      chulichecked: event.detail,
      itemselected: !that.data.itemselected,
    });
  },
  itemonchange(event) {
    var that = this;
    this.setData({
      itemselected: !that.data.itemselected,
    });
  },
  //弹窗方法	
  getUserInfo(event) { //重开
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
      url: app.globalData.address + 'event/toReopen.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": id,
        "remark": that.data.shuoming,
        "current_user": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            shuoming: '',
            list: that.data.list,
          });
          wx.showToast({
            title: '重开成功',
            icon: 'success',
            duration: 2000
          })
          //状态栏切换
          that.changtab1();
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
  onClosedia() {
    this.setData({
      showdia: false,
      shuoming: '',
    });
  },
  getUserInfo1(event) { //备注
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
      url: app.globalData.address + 'event/updateRemark',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
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
            title: '备注成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab1();
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
  onClosedia1() {
    this.setData({
      showdia1: false,
      shuoming: '',
    });
  },
  getUserInfo2(event) { //撤销
    var that = this;
    let id = event.currentTarget.dataset.id;
    if (null == id || id == "" || id == undefined) {
      wx.showToast({
        title: '请选择要撤销的事件',
        icon: 'none',
        duration: 2000
      })
      return;
    }
    wx.request({
      url: app.globalData.address + 'event/revoke',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": id,
        "remark": that.data.shuoming,
        "userId": wx.getStorageSync('userInfo').id,
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
          that.changtab1();
        } else {
          wx.showToast({
            title: '撤销失败',
            icon: 'none',
            duration: 2000
          })
        }
      }
    })
  },
  onClosedia2() {
    this.setData({
      showdia2: false,
      shuoming: '',
    });
  },
  // onChangePj(event) {
  //   console.log(event.detail)
  //   this.setData({
  //     shuoming: event.detail,
  //   });
  // },
  getUserInfo3(event) { //确认评价
    var that = this;
    console.log(that.data.shuoming)
    let id = event.currentTarget.dataset.id;
    if (null == id || id == '') {
      wx.showToast({
        title: '请选择一条数据',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    if (!that.data.shuoming) {
      wx.showToast({
        title: '请评价打星',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    wx.request({
      url: app.globalData.address + 'event/evaluateUser.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": id,
        "event_satisfaction": that.data.shuoming,
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            shuoming: '',
            list: that.data.list,
          });
          wx.showToast({
            title: '评价成功',
            icon: 'success',
            duration: 2000
          })
          //状态栏切换
          that.changtab1();
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
  onClosedia3() {
    this.setData({
      showdia3: false,
      shuoming: '',
    });
  },
  getUserInfo4(event) { //资产盘点 确认无误
    var that = this;
    console.log("确认无误")
    console.log(that.data.shuoming)
    let qruser = that.data.qruser; //确认人
    console.log(qruser)
    if (qruser) {
      wx.showToast({
        title: '工单状态不匹配',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    that.toConfirm(1)
  },
  onPdQryw(event) { //资产盘点 确认有误
    var that = this;
    console.log("确认有误")
    console.log(that.data.shuoming)
    let qruser = that.data.qruser; //确认人
    console.log(qruser)
    if (qruser) {
      wx.showToast({
        title: '工单状态不匹配',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    that.toConfirm(2)
  },
  onClosedia4(event) { //资产盘点 确认弹窗关闭
    this.setData({
      showdia4: false,
      shuoming: '',
    });
  },
  toConfirm(status) { //资产盘点 确认
    var that = this;
    console.log("资产盘点 确认:", that.data.shuoming)
    if (!that.data.rowId2) {
      wx.showToast({
        title: '请选择一条数据',
        icon: 'none',
        duration: 2000
      });
      return;
    }
    wx.request({
      url: app.globalData.address + 'event/toConfirm.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": that.data.rowId2,
        "confirm_remark": that.data.shuoming,
        "status": status,
        "userId": wx.getStorageSync('userInfo').id
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            shuoming: '',
            rowId2: '',
            qruser: '',
            list: that.data.list,
          });
          wx.showToast({
            title: '确认成功',
            icon: 'success',
            duration: 2000
          })
          //状态栏切换
          that.changtab2();
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

  /**选择客户发生事件 */
  getCustomer: function (event) {
    console.log(event.currentTarget.dataset.id);
    console.log(event.currentTarget.dataset.name);
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
  showPopup1() {
    this.setData({
      showselect1: true
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
  showaction: function (options) {
    var id = options.currentTarget.dataset.id;
    var status = options.currentTarget.dataset.state;
    console.log("-----------------更多操作------------------")
    const permission = wx.getStorageSync("authority");
    console.log(permission)
    let arr = [];
    if(permission.event_self_xiugai){
      if(status == "1"){
        arr.push({name: '修改'});
      }
    }
    if(permission.event_self_chongkai){
      if(status == "4" || status == "5" || status == "7"){
        arr.push({name: '重开'});
      }
    }
    if(permission.event_self_chexiao){
      arr.push({name: '撤销'});
    }
    if(permission.event_self_querenpingjia && status == "5"){
      arr.push({name: '确认评价'});
    }
    
    this.setData({
        show: true,
        actions: arr,
        rowId: id,
    });
  },
  onClose() {
    this.setData({
      show: false
    });
  },
  onSelect(event) {
    //console.log(event.detail);
    if (event.detail.name == "重开") {
      this.setData({
        showdia: true
      });

    } else if (event.detail.name == "修改") {
      wx.navigateTo({
        url: 'edit/index?id=' + this.data.rowId,
      })

    } else if (event.detail.name == "撤销") {
      this.setData({
        showdia2: true
      });
    } else if (event.detail.name == "确认评价") {
      this.setData({
        showdia3: true
      });
      // Dialog.confirm({
      //     title: '提示',
      //     message: '确定要确认评价吗？',
      //   })
      //   .then(() => {
      //     // on confirm
      //   })
      //   .catch(() => {
      //     // on cancel
      //   });

    }
  },
  confirmpd: function (options) { //资产盘点 确认
    this.setData({
      showdia4: true,
      rowId2: options.currentTarget.dataset.id,
      qruser: options.currentTarget.dataset.qruser,
    });

    // Dialog.confirm({
    //     title: '提示',
    //     message: '确定要确认吗？',
    //   })
    //   .then(() => {
    //     // on confirm
    //   })
    //   .catch(() => {
    //     // on cancel
    //   });
  },
  confirmpd1: function(options){//资产盘点 处理
    this.setData({
      showdia5: true,
      rowId: options.currentTarget.dataset.id,
    });
  },
  onClosedia5() {//资产盘点 处理 取消操作
    this.setData({
      showdia5: false,
      shuoming: '',
    });
  },
  getUserInfo5(event) { //资产盘点 处理 保存操作
    var that = this;
    let id = event.currentTarget.dataset.id;
    if (null == id || id == "" || id == undefined) {
      wx.showToast({
        title: '请选择要处理的资产',
        icon: 'none',
        duration: 2000
      })
      return;
    }
    wx.request({
      url: app.globalData.address + 'event/toConfirmHandle.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": id,
        "handle_result": that.data.shuoming,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          that.setData({
            shuoming: '',
            list: that.data.list,
          });
          wx.showToast({
            title: '处理成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab2();
        } else {
          wx.showToast({
            title: '处理失败',
            icon: 'none',
            duration: 2000
          })
        }
      }
    })
  },
  showRequestTypes: function (options) { //请求类型请选择
    this.setData({
      showRequestType: true
    });
  },
  onCloseRequestType() {
    this.setData({
      showRequestType: false
    });
  },
  onSelectRequestType(event) {
    console.log(event.detail);
    this.setData({
      requestTypeId: event.detail.id,
      requestType: event.detail.name
    });
  },

  onCancelRequestType(){
    this.setData({
      requestTypeId: '',
      requestType: '请选择',
      showRequestType: false
    });
  },

  showStatus: function (options) { //状态请选择
    this.setData({
      showState: true
    });
  },
  onCloseStatus() {
    this.setData({
      showState: false
    });
  },
  onSelectStatus(event) {
    console.log(event.detail);
    this.setData({
      statusId: event.detail.code,
      status: event.detail.name
    });
  },
  onCancelStatus(){
    this.setData({
      statusId: '',
      status: '请选择',
      showState: false
    });
  },

  showPdq: function (options) { //盘点期请选择
    this.setData({
      isShowPdq: true
    });
  },
  onClosePdq() {
    this.setData({
      isShowPdq: false
    });
  },
  onCancelPdq() {
    this.setData({
      isShowPdq: false,
      pdqName: '请选择'
    });
  },
  onSelectPdq(event) {
    console.log(event.detail);
    this.setData({
      pdqId: event.detail.id,
      pdqName: event.detail.name
    });
  },

  showAssetsType: function (options) { //配置项类型请选择
    this.setData({
      isShowAssetsType: true
    });
  },
  onCloseAssetsType() {
    this.setData({
      isShowAssetsType: false
    });
  },
  onCancelAssetsType() {
    this.setData({
      isShowAssetsType: false,
      assetsTypeName: '请选择'
    });
  },
  onSelectAssetsType(event) {
    console.log(event.detail);
    this.setData({
      assetsTypeId: event.detail.id,
      assetsTypeName: event.detail.name
    });

    //联动配置子类项列表
    this.getACByAssetsTypeId(event.detail.id)
    this.setData({
      childTypeId: '',
      childTypeName: '去选择'
    });
  },

  showChildAsset: function (options) { //配置子类请选择
    this.setData({
      isShowChildAsset: true
    });
  },
  onCloseChildAsset() {
    this.setData({
      isShowChildAsset: false
    });
  },
  onCancelChildAsset() {
    this.setData({
      isShowChildAsset: false,
      childTypeName: '请选择'
    });
  },
  onSelectChildAsset(event) {
    console.log(event.detail);
    this.setData({
      childTypeId: event.detail.id,
      childTypeName: event.detail.name
    });
  },

  showPdStatus: function (options) { //盘点状态请选择
    this.setData({
      isShowPdStatus: true
    });
  },
  onClosePdStatus() {
    this.setData({
      isShowPdStatus: false
    });
  },
  onCancelPdStatus() {
    this.setData({
      isShowPdStatus: false,
      pdStatusName: '请选择'
    });
  },
  
//   onClosePdStatus() {
//     this.setData({
//       isShowPdStatus: false,
//       pdStatusName: '请选择'
//     });
//   },
  onSelectPdStatus(event) {
    console.log(event.detail);
    this.setData({
      pdStatusId: event.detail.id,
      pdStatusName: event.detail.name
    });
  },

  showHandleStatus: function (options) { //处理状态请选择
    this.setData({
      isShowHandleStatus: true
    });
  },
  onCloseHandleStatus() {
    this.setData({
      isShowHandleStatus: false
    });
  },
  onCancelHandleStatus() {
    this.setData({
      isShowHandleStatus: false,
      handleStatusName: '请选择'
    });
  },
  onSelectHandleStatus(event) {
    console.log(event.detail);
    this.setData({
      handleStatusId: event.detail.id,
      handleStatusName: event.detail.name
    });
  },

  beizhu: function (options) {
    this.setData({
      showdia1: true
    });
  },
  bindDateChange: function (e) { //开始时间
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      startTime: e.detail.value
    })
  },
  bindDateCancel: function(){
    this.setData({
      startTime: '请选择'
    })
  },
  bindDateChange2: function (e) { //结束时间
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      endTime: e.detail.value
    })
  },
  bindDateCancel2: function(){
    this.setData({
      endTime: '请选择'
    })
  },
  eventView: function (e) { //我的工单 详情 跳转页面
    wx.navigateTo({
      url: '../allthing/detail/index?id=' + e.currentTarget.dataset.id,
    })
  },
  chuli: function (e) { //处理
    console.log("---------查看详情---------")
    console.log(e)
    wx.navigateTo({
      url: 'detail/index?id=' + e.currentTarget.dataset.id,
    })
  },
  addgd: function (e) { //开单
    wx.navigateTo({
      url: 'add/index',
    })
  },
  // getHeight:function(){
  //     //创建节点选择器
  //     let query=wx.createSelectorQuery();
  //     let that=this;
  //     query.select("#tabs").boundingClientRect()
  //     query.select("#searchs").boundingClientRect()
  //     query.exec(function(res){
  //         //取高度
  //         console.log(res[0].height,res[1].height);
  //         that.setData({
  //             topHeight:res[0].height,
  //             searchHeight:res[0].height + res[1].height
  //         });
  //     })
  // },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let query = wx.createSelectorQuery();
    let that = this;
    query.select("#tabs").boundingClientRect()
    query.select("#searchs").boundingClientRect()
    query.exec(function (res) {
      //取高度
      //console.log(res[0].height,res[1].height);
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
        searchHeight: res[0].height + res[1].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight')
      });
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
    wx.showLoading({
      title: '数据加载中...',
      icon: 'none',
      duration: 1500
    })
    console.log("onShow")
    console.log(that.data.selected)
    console.log(that.data.selected1)
    that.setData({
      selected: true,
      selected1: false,
      selected2: false,
      listType: '1',
    });

    var that = this;
    console.log(wx.getStorageSync("token"))
    this.setData({
      user: wx.getStorageSync("userInfo"),
    });
    const permission = wx.getStorageSync("authority");
    this.setData({
      authority: permission
    }) 
    
    //我的工单分页查询
    that.page(1);
    //请求类型 
    that.getRequestTypeList();
    //状态列表
    that.getStatusList();
    
    //盘点期
    that.getAllPanDianQi();
    //配置项列表
    that.getAllAssetsType();
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
    console.log("下拉刷新")
    //下拉刷新
    wx.showLoading({
      title: '正在刷新...',
      icon: 'none',
      duration: 1500
    })

    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    if (this.data.listType == "1") {
      //我的工单
      this.page(2)
    } else {
      //资产盘点
      this.assetsPage(2)
    }

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    console.log(1111)
    var that = this;
    that.data.pageIndex = that.data.pageIndex+1;
    console.log("上拉加载下一页 我的工单")
    //上拉加载下一页 我的工单
    if (this.data.listType == "1") {
      //我的工单
      // this.pageNext()
      this.page()
    } else {
      //资产盘点
      // this.assetsPageNext()
      this.assetsPage()
    }

  },

  /**
   * 上拉加载下一页
   * 我的工单
   * 分页查询
   * type 1关键字查询 2筛选条件查询
   */
  pageNext: function () {
    var that = this;
    var param = {
      "user_id": wx.getStorageSync('userInfo').id,
      "user_type": 1,
      "pageIndex": that.data.pageIndex + 1,
      "pageSize": that.data.pageSize
    }

    if (that.data.searchType) {
      if (that.data.searchType == "1") {
        param = {
          "user_id": wx.getStorageSync('userInfo').id,
          "user_type": 1,
          "title": that.data.keyword,
          "pageIndex": that.data.pageIndex + 1,
          "pageSize": that.data.pageSize
        }
      } else if (that.data.searchType == "2") {
        if(that.data.startTime == '请选择'){
          that.data.startTime = '';
        }
        if(that.data.endTime == '请选择'){
          that.data.endTime = '';
        }
        param = {
          "user_id": wx.getStorageSync('userInfo').id,
          "user_type": 1,
          "billStartTime": that.data.startTime,
          "billEndTime": that.data.endTime,
          "request_type": that.data.requestTypeId,
          "status": that.data.statusId,
          "pageIndex": that.data.pageIndex + 1,
          "pageSize": that.data.pageSize
        }
      }
    }

    if (that.data.pageIndex < that.data.pages) {
      wx.showLoading({
        title: '数据加载中...',
        icon: 'loading',
        duration: 1500
      })
      wx.request({
        url: app.globalData.address + 'eventSelfService/toList.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: param,
        success: function (res) {
          that.setData({
            list: that.data.list.concat(res.data.data.list),
            pageIndex: that.data.pageIndex + 1,
            pageSize: that.data.pageSize
          })
          if (res.data.data.list.length == 0) {
            that.setData({
              showViewDb: true
            })
          }
        }
      })
    } else {
      that.setData({
        showViewDb: true
      })
    }
  },

  /**
   * 上拉加载下一页
   * 资产盘点
   * 分页查询
   * type 1关键字查询 2筛选条件查询
   */
  assetsPageNext: function () {
    var that = this;
    var param = {
      "pageIndex": that.data.pageIndex + 1,
      "pageSize": that.data.pageSize
    }

    if (that.data.searchType) {
      if (that.data.searchType == "1") {
        param = {
          "keywords": that.data.keyword2,
          "pageIndex": that.data.pageIndex + 1,
          "pageSize": that.data.pageSize
        }
      } else if (that.data.searchType == "2") {
        param = {
          "inventory_id": that.data.pdqId,
          "aty_id": that.data.assetsTypeId,
          "ac_id": that.data.childTypeId,
          "status": that.data.pdStatusId,
          "handle_status": that.data.handleStatusId,
          "pageIndex": that.data.pageIndex + 1,
          "pageSize": that.data.pageSize
        }
      }
    }

    if (that.data.pageIndex < that.data.pages) {
      wx.showLoading({
        title: '数据加载中...',
        icon: 'loading',
        duration: 1500
      })
      wx.request({
        url: app.globalData.address + 'event/assetsInventoryList.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: param,
        success: function (res) {
          that.setData({
            list: that.data.list.concat(res.data.data.list),
            pageIndex: that.data.pageIndex + 1,
            pageSize: that.data.pageSize
          })
          if (res.data.data.list.length == 0) {
            that.setData({
              showViewDb: true
            })
          }
        }
      })
    } else {
      that.setData({
        showViewDb: true
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  onkeyword2: function (e) { //关键字搜索 公共方法 输入框
    var that = this;
    // this.data.keyword = e.detail.value;
    console.log("我的工单关键字搜索");
    console.log(e.detail);
    that.setData({
      [`${e.currentTarget.dataset.params}`]: e.detail.value
    });
    console.log(`我的工单关键字：${this.data.keyword},资产盘点关键字：${this.data.keyword2}`)

  },

  onkeyword: function (e) { //关键字搜索 公共方法
    var that = this;
    // this.data.keyword = e.detail.value;
    console.log("说明");
    console.log(e.detail);
    that.setData({
      [`${e.currentTarget.dataset.params}`]: e.detail
    });
    console.log(`我的工单关键字：${this.data.keyword},资产盘点关键字：${this.data.keyword2}`)
    console.log(`说明：${this.data.shuoming}`)

  },

  eventSearch: function (e) { //搜索查询
    if (this.data.listType == "1") {
      console.log("我的工单搜索查询");
      //我的工单
      this.setData({
        searchType: e.currentTarget.dataset.type,
        showselect: false
      });
      this.page(1)
    } else {
      console.log("资产盘点搜索查询");
      //资产盘点
      this.setData({
        searchType2: e.currentTarget.dataset.type,
        showselect: false
      });
      this.assetsPage(1)
    }
  },

  /**
   * 我的工单
   * 分页查询
   * type 1关键字查询 2筛选条件查询
   */
  page: function (val) {
    var that = this;
    // console.log(that.data.keyword)
    // console.log(that.data.searchType)
    var param = {
      "user_id": wx.getStorageSync('userInfo').id,
      "user_type": 1,
      "pageIndex": 1,
      "pageSize": 10
    }

    if (that.data.searchType) {
      if (that.data.searchType == "1") {
        param = {
          "user_id": wx.getStorageSync('userInfo').id,
          "user_type": 1,
          "title": that.data.keyword,
          "pageIndex": 1,
          "pageSize": that.data.pageSize*that.data.pageIndex
        }
      } else if (that.data.searchType == "2") {
        if(that.data.startTime == '请选择'){
          that.data.startTime = '';
        }
        if(that.data.endTime == '请选择'){
          that.data.endTime = '';
        }
        param = {
          "user_id": wx.getStorageSync('userInfo').id,
          "user_type": 1,
          "billStartTime": that.data.startTime,
          "billEndTime": that.data.endTime,
          "request_type": that.data.requestTypeId,
          "status": that.data.statusId,
          "pageIndex": 1,
          "pageSize": that.data.pageSize*that.data.pageIndex
        }
      }
    }

    wx.request({
      url: app.globalData.address + 'eventSelfService/toList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: param,
      success: function (res) {
        console.log(res)
        that.setData({
          list: res.data.data.list,
          pageIndex: res.data.data.pageNum,
          pageSize: res.data.data.pageSize,
          totalPage: res.data.data.total,
          pages: res.data.data.pages,
          showViewDb: false
        })
        if (res.data.data.total < res.data.data.pageSize) {
        // if (res.data.data.list.length == 0) {
          that.setData({
            showViewDb: true
          })
        } else {
          that.setData({
            showViewDb: false
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
   * 资产盘点
   * 分页查询
   * type 1关键字查询 2筛选条件查询
   */
  assetsPage: function (val) {
    var that = this;
    // console.log(that.data.keyword2)
    // console.log(that.data.searchType2)
    var param = {
      "pageIndex": 1,
      "pageSize": 10
    }

    if (that.data.searchType2) {
      if (that.data.searchType2 == "1") {
        param = {
          "keywords": that.data.keyword2,
          "pageIndex": 1,
          "pageSize": that.data.pageSize*that.data.pageIndex
        }
      } else if (that.data.searchType2 == "2") {
        param = {
          "inventory_id": that.data.pdqId,
          "aty_id": that.data.assetsTypeId,
          "ac_id": that.data.childTypeId,
          "status": that.data.pdStatusId,
          "handle_status": that.data.handleStatusId,
          "pageIndex": 1,
          "pageSize": that.data.pageSize*that.data.pageIndex
        }
      }
    }

    wx.request({
      url: app.globalData.address + 'event/assetsInventoryList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: param,
      success: function (res) {
        console.log(res)
        that.setData({
          list: res.data.data.list,
          pageIndex: res.data.data.pageNum,
          pageSize: res.data.data.pageSize,
          totalPage: res.data.data.total,
          pages: res.data.data.pages,
          showView: false
        })
        if (res.data.data.total < res.data.data.pageSize) {
        // if (res.data.data.list.length == 0) {
          that.setData({
            showViewDb: true
          })
        } else {
          that.setData({
            showViewDb: false
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
   * 请求类型
   */
  getRequestTypeList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'eventSelfService/getRequestTypeList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          requestTypeList: res.data.data.list
        })
      }
    })
  },

  /**
   * 状态
   */
  getStatusList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'eventSelfService/getEventStatusList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          statusList: res.data.data
        })
      }
    })
  },

  /**
   * 所属盘点期
   */
  getAllPanDianQi: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/getAllPanDianQi.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          panDianQiArr: res.data.data
        })
      }
    })
  },

  /**
   * 配置项列表
   */
  getAllAssetsType: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/getAllAssetsType.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          assetsTypes: res.data.data.list
        })
      }
    })
  },

  /**
   * 配置项子类列表
   */
  getACByAssetsTypeId: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/getACByAssetsTypeId.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "assets_type_id": val
      },
      success: function (res) {
        console.log(res)
        that.setData({
          assetsChildren: res.data.data
        })
      }
    })
  },

  // // e.currentTarget.dataset.type事件对象拿到组件绑定的值
  // inputEvent(e) { // 输入框双向绑定 资产编号
  //   this.setData({
  //     "am_assets_number": e.detail
  //   })
  //   // this.setData({
  //   // 		['applicationForm.' + e.currentTarget.dataset.type]: e.detail
  //   // })
  // },



})