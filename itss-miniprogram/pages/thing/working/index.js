// pages/thing/working/index.js
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
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    titleOne: '',
    id: '',
    showView: false, //通过true 和 false 来控制显示隐藏
    showViewDb: false, //通过true 和 false 来控制显示隐藏
    list: [],
    pageIndex: 1,
    pageSize: 10,
    totalPage: '',
    pages: '',
    // 状态栏高度
    statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
    // 导航栏高度
    navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
    // 胶囊按钮高度
    menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
    // 导航栏和状态栏高度
    navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
    title: '工作台',
    selected: true,
    selected1: false,
    selected2: false,
    selected3: false,
    topHeight: 0,
    searchHeight: 0,
    startTime: '请选择', //开始时间
    endTime: '请选择', //结束时间
    selDate: new Date().getTime(),
    starTag: true,
    status: 0,
    show: false,
    show1: false,
    show2: false,
    show3: false,
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
    /** mock客户数据*/
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
    zhuangtai: '请选择',
    username: '请选择',
    actions: [{
        name: '转单',
      },
      {
        name: '暂停',
      },
      {
        name: '恢复',
      },
      {
        name: '响应',
      },
      {
        name: '指派',
      },
      {
        name: '备注',
      },
      {
        name: '撤消',
      },
      {
        name: '详情',
      },
    ],
    actions1: [{
        name: '固定资产项目',
      },
      {
        name: '智慧后勤项目',
      },
      {
        name: '科教平台项目',
      },
    ],
    authority: '',
    chulichecked: false,
    rowId: '',
    showCYDView: false, //通过true 和 false 来控制显示隐藏
    showZNView: false, //通过true 和 false 来控制显示隐藏
    showMZView: false,
    keyword: '',
    keyword3: '',
    queryForm: {
      id: '',
      title: '',
      source_id: '',
    },
    handlerWithMeFlag: '',
    minDate: new Date(2019, 10, 1).getTime(),
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
  showstartTime(event) {
    this.setData({
      show2: true,
      starTag: true,
      // selDate: this.strToData(this.data.startTime)
    }); 
  },
  showendTime(event) {
    this.setData({
      show2: true,
      starTag: false,
      // selDate: this.strToData(this.data.endTime)
    });
  },
  confirmData(event) {
    // let selData = new Date(event.detail).toLocaleDateString()
    // console.log(event.detail,selData)
    if (this.data.starTag) {
      this.setData({
        startTime: this.formatDate(event.detail),
      });
    } else {
      this.setData({
        endTime: this.formatDate(event.detail),
      });
    }
    // this.onClose2()
    this.setData({
      show2: false
    });
  },
  formatDate(selData) {
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
  strToData(strData) {
    //转换数组再转字符窜
    let formatData = strData.split("-").join(',')
    return new Date(formatData).getTime()
  },
  // 查询条件: 编号
  onbianhao: function (e) {
    console.log(e.detail);
    this.data.queryForm.id = e.detail;
  },
  // 查询条件: 标题
  onbiaoti: function (e) {
    this.data.queryForm.title = e.detail;
  },
  // 查询确定按钮
  onQueryConfirm(event) {
    console.log(event)
    let that = this;
    this.setData({
      showselect: false
    });
    console.log(that.data.selected);
    if (that.data.selected) {
      this.page(1)
      this.setData({
        queryForm: {},
        zhuangtai: '去选择',
        startTime: ymd, //开始时间
        endTime: ymd, //结束时间
      });
      return;
    }
    if (that.data.selected1) {
      this.page2(2)
      this.setData({
        queryForm: {},
        zhuangtai: '去选择',
      });
      return;
    }
    if (that.data.selected2) {
      this.page3(1)
      this.setData({
        queryForm: {},
        zhuangtai: '去选择',
      });
      return;
    }

  },
  //状态栏切换
  changtab1(event) {
    var that = this;
    that.setData({
      selected: true,
      selected1: false,
      selected2: false,
    });
    that.data.pageSize = 10;
    that.page();
  },
  // 参与的-关键字搜索
  onkeyword: function (e) {
    this.data.keyword = e.detail.value;
  },
  // 参与的-关键字搜索
  eventSearch: function (e) {
    this.setData({
      showselect: false
    });
    this.page2(1)
  },
  changtab2(event) {
    var that = this;
    this.setData({
      selected: false,
      selected1: true,
      selected2: false,
    });
    that.data.pageSize = 10;
    this.page2();
    //toMyParticipationList
  },
  // 组内-关键字搜索
  onkeyword3: function (e) {
    console.log("组内关键字:", e.detail)
    this.data.keyword3 = e.detail.value;
  },
  // 组内-关键字搜索
  eventSearch3: function (e) {
    this.setData({
      showselect: false
    });
    this.page3(2)
  },
  changtab3(event) {
    var that = this;
    this.setData({
      selected: false,
      selected1: false,
      selected2: true,
    });
    that.data.pageSize = 10;
    this.page3();
  },
  //索引栏发生变化事件	
  onPageScroll(event) {
    this.setData({
      scrollTop: event.scrollTop,
    });
  },
  //待我处理方法	
  onChangechuli(event) {
    let that = this;
    that.setData({
      chulichecked: event.detail,
    });
    console.log("待我处理: " ,event.detail )
    if (event.detail) {
      that.data.handlerWithMeFlag = '1';
      that.page();
    } else {
      that.data.handlerWithMeFlag = '';
      that.page();
    }
  },
  //弹窗方法	
  getUserInfo(event) {
    var that = this;
    console.log(that.data.id);
    console.log(that.data.shuoming);

    wx.request({
      url: app.globalData.address + 'event/pause.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": that.data.id,
        "remark": that.data.shuoming,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '暂停成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab1();
          that.page();
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'error',
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
  getUserInfo1(event) {
    var that = this;
    console.log("备注提交:", that.data.id)
    let id = event.currentTarget.dataset.id;
    // var that = this;
    wx.request({
      url: app.globalData.address + 'event/updateRemark',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": that.data.id,
        "remark": that.data.shuoming,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '添加成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab1();
          that.page();
          that.setData({
            rowId: '',
          });
        } else {
          wx.showToast({
            title: '添加失败',
            icon: 'error',
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
  getUserInfo2(event) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/revoke',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": that.data.id,
        "remark": that.data.shuoming,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '撤单成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab1();
          that.page();
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'error',
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
  getUserInfo3(event) {
    console.log(event.detail);
    this.setData({
      shuoming: '',
    });
  },
  onClosedia3() {
    this.setData({
      showdia3: false,
      shuoming: '',
    });
  },
  getUserInfo4(event) {
    var that = this;
    console.log(that.data.id);
    console.log(that.data.shuoming);

    wx.request({
      url: app.globalData.address + 'event/restore.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": that.data.id,
        "remark": that.data.shuoming,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '恢复成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab1();
          that.page();
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'error',
            duration: 2000
          })
        }
      }
    })

  },
  onClosedia4() {
    this.setData({
      showdia4: false,
      shuoming: '',
    });
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
    var that = this;
    that.data.id = id;
    console.log("-----------------更多操作------------------")
    const permission = wx.getStorageSync("authority");
    console.log("权限标识:",permission)
    let arr = [];
    if (status != "1" && permission.event_workbench_zhuandan) {
      arr.push({
        name: '转单'
      });
    }
    if (status == "2" && permission.event_workbench_zanting) {
      arr.push({
        name: '暂停'
      });
    }
    if (status == "3" && permission.event_workbench_huifu) {
      arr.push({
        name: '恢复'
      });
    }
    if (status == "1") {
      if (permission.event_workbench_xiangying) {
        arr.push({
          name: '响应'
        });
      }
      if (permission.event_workbench_zhipai) {
        arr.push({
          name: '指派'
        });
      }
    }
    if (permission.event_workbench_beizhu) {
      arr.push({
        name: '备注'
      });
    }
    if (permission.event_workbench_chexiao) {
      arr.push({
        name: '撤消'
      });
    }
    arr.push({
      name: '详情'
    });

    this.setData({
      show: true,
      actions: arr,
    });
  },
  onClose() {
    this.setData({
      show: false
    });
  },
  onClose2() {  
    if (this.data.starTag) {
      this.setData({
        show2: false,
        startTime: '请选择',
      });
    } else {
      this.setData({
        show2: false,
        endTime: '请选择',
      });
    }
  },
  onClose3() {
    this.setData({
      show3: false
    });
  },
  onSelect(event) {
    //console.log(event.detail);
    if (event.detail.name == "转单") {
      var that = this;
      var id = that.data.id;
      wx.navigateTo({
        url: 'zhuandan/index?id=' + id,
      })
    } else if (event.detail.name == "暂停") {
      this.setData({
        showdia: true
      });

    } else if (event.detail.name == "备注") {
      this.setData({
        showdia1: true
      });

    } else if (event.detail.name == "撤消") {
      this.setData({
        showdia2: true
      });
    } else if (event.detail.name == "响应") {

      Dialog.confirm({
          title: '提示',
          message: '是否响应事件？',
        })
        .then(() => {
          // on confirm
          console.log(1212121212121)
          this.responseOne();
        })
        .catch(() => {
          // on cancel

        });

    } else if (event.detail.name == "恢复") {
      this.setData({
        showdia4: true
      });
    } else if (event.detail.name == "指派") {
      var that = this;
      var id = that.data.id;
      wx.navigateTo({
        url: 'zhipai/index?id=' + id,
      })
    } else if (event.detail.name == "详情") {
      var that = this;
      var id = that.data.id;
      wx.navigateTo({
        url: '../allthing/detail/index?id=' + id,
      })
    }
  },
  showaction1: function (options) {
    let that = this;
    wx.request({
      url: app.globalData.address + 'event/getAllSource.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        if (res.data.code == 0) {
          let arr = [];
          //循环添加数据
          for (let i = 0; i < res.data.data.length; i++) {
            let rowData = res.data.data[i];
            arr.push({
              id: rowData.id,
              name: rowData.resource_name
            });
          }
          that.setData({
            show1: true,
            actions1: arr,
          });
        } else {
          wx.showToast({
            title: '来源查询失败',
            icon: 'none',
            duration: 2000
          })
        }
      }
    })
  },
  //备注
  beizhu: function (options) {
    var that = this;
    that.data.id = options.currentTarget.dataset.id;
    console.log("备注:",options.currentTarget.dataset)
    this.setData({
      showdia1: true,
      rowId: options.currentTarget.dataset.id,
    });
  },
  onClose1() {
    this.setData({
      show1: false,
      // zhuangtai:'请选择',
    });
  },
  onCancle1() {
    this.setData({
      show1: false,
      zhuangtai:'请选择',
    });
  },
  onSelect1(event) {
    //console.log(event.detail);
    this.setData({
      zhuangtai: event.detail.name,      
    });
    this.data.queryForm.source_id = event.detail.id;
  },
  bindDateChange: function (e) { //开始时间
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      startTime: e.detail.value
    })
  },
  bindDateChange2: function (e) { //结束时间
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      endTime: e.detail.value
    })
  },
  chuli: function (e) {
    var id = e.currentTarget.dataset.id;
    console.log(id);
    wx.navigateTo({
      url: 'chuli/index?id=' + id,
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
      icon: 'loading',
      duration: 1500
    })

    setTimeout(function () {
      // complete
      wx.hideNavigationBarLoading() //完成停止加载
      wx.stopPullDownRefresh() //停止下拉刷新
    }, 1500);
    var that = this;
    that.data.pageIndex = 1;
    that.data.pageSize = 10;
    
    if(that.data.selected){
      this.page();
    }else if(that.data.selected1){
      this.page2();
    }else if(that.data.selected2){
      this.page3();
    }else{
      this.page();
    }
    //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
    wx.pageScrollTo({
      scrollTop: 0
    })
    
     //结束刷新
    wx.stopPullDownRefresh();
    // wx.request({
    //   url: app.globalData.address + 'event/toWorkBaseList.do',
    //   method: 'POST',
    //   header: {
    //     "Authorization": wx.getStorageSync('tokenValue'),
    //     "Content-Type": "application/x-www-form-urlencoded"
    //   },
    //   data: {
    //     "statusList": ["1","2"],
    //     "pageIndex": 1,
    //     "pageSize": 10,
    //     "userId": wx.getStorageSync('userInfo').id,
    //   },
    //   success: function (res) {
    //     that.setData({
    //       list: res.data.data.list,
    //       pageIndex: res.data.data.pageNum,
    //       pageSize: res.data.data.pageSize,
    //       totalPage: res.data.data.total,
    //       pages: res.data.data.pages,
    //       showViewDb: false
    //     })
    //     if (res.data.data.list.length == 0) {
    //       that.setData({
    //         showView: true
    //       })
    //     } else {
    //       that.setData({
    //         showView: false
    //       })
    //     }
    //     //结束刷新
    //     wx.stopPullDownRefresh();
    //   }
    // })


  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    //上拉加载下一页
    var that = this;
    console.log("页面上拉触底事件的处理函数:",that);
    if (that.data.pageIndex < that.data.pages) {
      wx.showLoading({
        title: '数据加载中...',
        icon: 'loading',
        duration: 1500
      })
    }
      that.data.pageIndex = that.data.pageIndex + 1;
      if(that.data.selected){
        this.page();
      }else if(that.data.selected1){
        this.page2();
      }else if(that.data.selected2){
        this.page3();
      }else{
        this.page();
      }
       //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
       wx.pageScrollTo({
        scrollTop: 0
      })
      // wx.request({
      //   url: app.globalData.address + 'event/toWorkBaseList.do',
      //   method: 'POST',
      //   header: {
      //     "Authorization": wx.getStorageSync('tokenValue'),
      //     "Content-Type": "application/x-www-form-urlencoded"
      //   },
      //   data: {
      //       "statusList": ["1","2"],
      //     "pageIndex": that.data.pageIndex + 1,
      //     "pageSize": that.data.pageSize,
      //     "userId": wx.getStorageSync('userInfo').id,
      //   },
      //   success: function (res) {
      //     console.log(res.data);
      //     that.setData({
      //       list: that.data.list.concat(res.data.data.list),
      //       pageIndex: that.data.pageIndex + 1,
      //       pageSize: that.data.pageSize,
      //       showMZView: false
      //     })
      //     if (res.data.data.list.length == 0) {
      //       that.setData({
      //         showMZView: true
      //       })
      //     }
      //   }
      // })
    // } else {
    //   that.setData({
    //     showMZView: false
    //   })
    // }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },

  /**
   * 工作台列表
   */
  onShow: function () {
    wx.showLoading({
      title: '数据加载中...',
      icon: 'loading',
      duration: 1500
    })
    var that = this;
    console.log(wx.getStorageSync("token"))
    this.setData({
      user: wx.getStorageSync("userInfo"),
    });
    const permission = wx.getStorageSync("authority");
    this.setData({
      authority: permission
    })

    this.page();
    // wx.request({
    //   url: app.globalData.address + 'event/toWorkBaseList.do',
    //   method: 'POST',
    //   header: {
    //     "Authorization": wx.getStorageSync('tokenValue'),
    //     "Content-Type": "application/x-www-form-urlencoded"
    //   },
    //   data: {
    //      "statusList": ["1","2"],
    //     "pageIndex": that.data.pageIndex,
    //     "pageSize": that.data.pageSize,
    //     "userId": wx.getStorageSync('userInfo').id,
    //   },
    //   success: function (res) {
    //     console.log(res)
    //     that.setData({
    //       list: res.data.data.list,
    //       pageIndex: res.data.data.pageNum,
    //       pageSize: res.data.data.pageSize,
    //       totalPage: res.data.data.total,
    //       pages: res.data.data.pages,
    //       showMZView: false
    //     })
    //     if (res.data.data.list.length == 0) {
    //       that.setData({
    //         showMZView: true
    //       })
    //     } else {
    //       that.setData({
    //         showMZView: false
    //       })
    //     }
    //   }
    // })

    //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
    wx.pageScrollTo({
      scrollTop: 0
    })
  },
  reason: function (e) { //理由
    var that = this;
    this.setData({
      shuoming: e.detail
    });
  },
  responseOne: function (e) {
    var that = this;
    console.log(that.data.id);

    wx.request({
      url: app.globalData.address + 'event/response',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": that.data.id,
        "userId": wx.getStorageSync('userInfo').id,
      },
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '响应成功',
            icon: 'success',
            duration: 2000
          })
          that.changtab1();
          that.page();
        } else {
          wx.showToast({
            title: res.data.msg,
            icon: 'error',
            duration: 2000
          })
        }
      }
    })
  },
  page: function (val) {
    var that = this;
    // console.log("________________________________")
    // console.log(that.data.queryForm.title);
    // console.log(that.data.queryForm.id);
    // console.log(that.data.startTime);
    // console.log(that.data.endTime);
    // console.log(that);
    // console.log("________________________________")
    let billStartTime = '';
    let billEndTime = '';
    if (that.data.startTime == that.data.endTime && that.data.endTime == ymd && that.data.startTime == ymd) {
      billStartTime = '';
      billEndTime = '';
    } else {
      billStartTime = that.data.startTime == undefined ? '' : that.data.startTime;
      billEndTime = that.data.endTime == undefined ? '' : that.data.endTime;
    }
    let status = "";
    // if (that.data.handlerWithMeFlag == "1") {
    //   status = "2";
    // }
    let params = {
      "title": that.data.queryForm.title == undefined ? '' : that.data.queryForm.title,
      "id": that.data.queryForm.id == undefined ? '' : that.data.queryForm.id,
      // "billStartTime": billStartTime,
      // "billEndTime": billEndTime,
      "source_id": that.data.queryForm.source_id == undefined ? '' : that.data.queryForm.source_id,
      "status": status,
      "handler": that.data.handlerWithMeFlag == "1"?wx.getStorageSync('userInfo').id:'',
      // "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
      "pageIndex": 1,
      "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize*that.data.pageIndex,
      "userId": wx.getStorageSync('userInfo').id,
      "statusList": ["1","2","3"],
      // "statusList": ["2"],
    };
    console.log("that.data.pageIndex: " , that.data.pageIndex)
    console.log("工作台查询入参: ", params)
    wx.request({
      url: app.globalData.address + 'event/toWorkBaseList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: params,
      success: function (res) {
        console.log("工作台列表:", res)
        that.setData({
          list: res.data.data.list,
          pageIndex: res.data.data.pageNum,
          pageSize: res.data.data.pageSize,
          totalPage: res.data.data.total,
          pages: res.data.data.pages,
          showMZView: false,
          queryForm: {}
        })
        if (res.data.data.total < res.data.data.pageSize) {
          that.setData({
            showMZView: true
          })
        } else {
          that.setData({
            showMZView: false
          })
        }
        that.data.queryForm = {};
        that.data.keyword = '';
      }
    })
  },
  page2: function (val) {
    var that = this;
    console.log('----------------参与的列表查询---------------------')
    if (val == 2) {
      that.data.keyword = that.data.queryForm.title == undefined ? '' : that.data.queryForm.title;
    }
    let params = {
      "id": that.data.queryForm.id == undefined ? '' : that.data.queryForm.id,
      "title": that.data.keyword == undefined ? '' : that.data.keyword,
      "source_id": that.data.queryForm.source_id == undefined ? '' : that.data.queryForm.source_id,
      "pageIndex": 1,
      "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize*that.data.pageIndex,
      // "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
      // "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize,
      "current_user": wx.getStorageSync('userInfo').id,
    };
    console.log("参与的列表查询入参:", params)
    wx.request({
      url: app.globalData.address + 'event/toMyParticipationList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: params,
      success: function (res) {
        console.log("参与的列表查询结果:",res)
        if (res.data.code == 0) {
          that.setData({
            list: res.data.data.list,
            pageIndex: res.data.data.pageNum,
            pageSize: res.data.data.pageSize,
            totalPage: res.data.data.total,
            pages: res.data.data.pages,
            showCYDView: false,
            keyword: ''
          })
          if (res.data.data.total < res.data.data.pageSize) {
            that.setData({
              showCYDView: true
            })
          }
          that.data.queryForm = {};
        } else {
          that.setData({
            list: [],
            showCYDView: true
          })
          that.data.queryForm = {};
        }
        that.data.keyword = '';
      }
    })
  },
  page3: function (val) {
    var that = this;
    console.log('----------------组内的列表查询---------------------', val)
    
    let titleValue = "";
    if (val == 1) {
      titleValue = that.data.queryForm.title == undefined ? '' : that.data.queryForm.title;
    }else{
      titleValue = that.data.keyword3 == undefined?"":that.data.keyword3;
    }
    console.log("关键字-keyword3:", that.data.keyword3)
    console.log("关键字-titleValue:", titleValue)
    let params = {
      "title": titleValue,
      "id": that.data.queryForm.id == undefined ? '' : that.data.queryForm.id,
      "source_id": that.data.queryForm.source_id == undefined ? '' : that.data.queryForm.source_id,
      // "pageIndex": that.data.pageIndex == undefined ? 1 : that.data.pageIndex,
      // "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize,
      "pageIndex": 1,
      "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize*that.data.pageIndex,
      "userId": wx.getStorageSync('userInfo').id,
    };
    console.log("组内的列表查询入参:",params)
    wx.request({
      url: app.globalData.address + 'event/toMyGroupList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: params,
      success: function (res) {
        console.log("组内的列表查询结果:",res)
        if (res.data.code == 0) {
          that.setData({
            list: res.data.data.list,
            pageIndex: res.data.data.pageNum,
            pageSize: res.data.data.pageSize,
            totalPage: res.data.data.total,
            pages: res.data.data.pages,
            showZNView: false,
            keyword: '',
          })
          if (res.data.data.total < res.data.data.pageSize) {
            that.setData({
              showZNView: true
            })
          }
          that.data.queryForm = {};
        } else {
          that.setData({
            list: [],
            showZNView: true
          })
          that.data.queryForm = {};
        }
        that.data.keyword3 = '';
      }
    })
  },
  //默认展示关键字搜索
  onkeywordOne: function (e) {
    var that = this;
    that.data.queryForm.title = e.detail.value;
  },
  // 默认-关键字搜索
  eventSearchOne: function (e) {
    var that = this;
    that.setData({
      showselect: false
    });
    console.log("点击回车搜索:", that.data.queryForm.title)
    that.page();
  },
})