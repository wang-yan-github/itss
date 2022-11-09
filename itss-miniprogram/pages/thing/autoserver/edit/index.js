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
    title: '修改',
    pzx: '请选择', //关联的配置项
    qqlx: '请选择', //请求类型
    jjd: '请选择', //紧急度
    yyd: '请选择', //影响度
    topHeight: 0,
    selectKey: {
      id: '',
      name: '请选择',
      worker_order_id: '', //工单类别ID
      engineer_id: '', //自动分派到工程师ID
    },
    activeNames: ['1', '2'],
    fileList: [
      //     {
      //     url: 'https://img.yzcdn.cn/vant/leaf.jpg',
      //     name: '图片1',
      //   },
      //   // Uploader 根据文件后缀来判断是否为图片文件
      //   // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
      //   {
      //     url: 'http://iph.href.lu/60x60?text=default',
      //     name: '图片2',
      //     isImage: true,
      //     deletable: true,
      //   },
    ],
    show: false,
    show1: false,
    show2: false,
    show3: false,
    show4: false,
    //选择服务类别
    treeData: {
      // id: "111",
      // name: "全部服务类型",
      // childMenus: [{
      //     id: "222",
      //     name: "总路长"
      //   },
      //   {
      //     id: "333",
      //     name: "一级总路长1",
      //     childMenus: [{
      //       id: "444",
      //       name: "二级总路长",
      //       childMenus: [{
      //           id: "666",
      //           name: "三级总路长"
      //         },
      //         {
      //           id: "8888",
      //           name: "三级总路长"
      //         }
      //       ]
      //     }]
      //   },
      //   {
      //     id: "2",
      //     name: "一级总路长2",
      //     childMenus: [{
      //       id: "22",
      //       name: "二级总路长",
      //       childMenus: [{
      //           id: "223",
      //           name: "三级总路长"
      //         },
      //         {
      //           id: "224",
      //           name: "三级总路长"
      //         }
      //       ]
      //     }]
      //   },
      //   {
      //     id: "3",
      //     name: "一级总路长32",
      //     childMenus: [{
      //       id: "32",
      //       name: "二级总路长",
      //       childMenus: [{
      //           id: "233",
      //           name: "三级总路长"
      //         },
      //         {
      //           id: "234",
      //           name: "三级总路长"
      //         }
      //       ]
      //     }]
      //   },
      // ]
    },
    //选择关联的配置项
    columns1: [],
    //选择请求类型
    columns2: [],
    //选择紧急度
    columns3: [],
    //选择影响度
    columns4: [],
    formData: {
      id: "",
      // "service_category_name": '', //服务类别
      "service_category": '', //服务类别id
      "title": '', //标题
      "description": '', //描述
      // "eventAssetName": '', //关联的配置项名称
      "eventAssetId": -1, //关联的配置项id
      "eventAssets1": [], //关联的配置项
      "request_type": -1, //请求类型id
      // "request_type_name": '', //请求类型名称
      "urgency_degree_id": -1, //紧急度id
      // "urgency_degree_name": '', //紧急度名称
      "effect_degree": -1, //影响度id
      // "effect_degree_name": '', //影响度名称
      "priority_id": '', //事件优先级id
      "priority_name": '', //事件优先级名称
      "responseSLA": '', //响应SLA/ 解决SLA
      "handler": '', //处理人id
      "event_category_id": '', //工单类别id
    },
    formData2: {
      id: "",
      // "service_category_name": '', //服务类别
      "service_category": '', //服务类别id
      "title": '', //标题
      "description": '', //描述
      // "eventAssetName": '', //关联的配置项名称
      "eventAssetId": -1, //关联的配置项id
      "eventAssets1": [], //关联的配置项
      "request_type": -1, //请求类型id
      // "request_type_name": '', //请求类型名称
      "urgency_degree_id": -1, //紧急度id
      // "urgency_degree_name": '', //紧急度名称
      "effect_degree": -1, //影响度id
      // "effect_degree_name": '', //影响度名称
      "priority_id": '', //事件优先级id
      "priority_name": '', //事件优先级名称
      "responseSLA": '', //响应SLA/ 解决SLA
      "handler": '', //处理人id
      "event_category_id": '', //工单类别id
    },
    assetsTypes: [], //配置项类型
    //请求类型
    requestTypeList: [],
    //选择紧急度
    urgencyList: [],
    //选择影响度
    affectList: [],
    disabledButton: false,
    pzxData: {}

  },
  /**   * 初始化富文本框   */
  onEditorReady: async function () {
    const that = this;
    setTimeout(() => {
        wx.createSelectorQuery().select('#editor').context(function (res) {
            that.editorCtx = res.context
            that.editorCtx.setContents({
                html: that.data.formData.description
            })
        }).exec()
        // wx.createSelectorQuery().select('#editor2').context(function (res) {
        //     that.editorCtx = res.context
        //     that.editorCtx.setContents({
        //         html: that.data.fallback_plan
        //     })
        // }).exec()
        // wx.createSelectorQuery().select('#editor3').context(function (res) {
        //     that.editorCtx = res.context
        //     that.editorCtx.setContents({
        //         html: that.data.risk_assessment
        //     })
        // }).exec()
    }, 500)
  },
  changeDescription: function (e) { //变更方案
    var that = this;
    that.data.formData.description = e.detail.html
    that.setData({
        description: e.detail.html,
    })
},
  onsetinput: function (e) { //bindinput 公共方法 输入框
    var that = this;
    // this.data.keyword = e.detail.value;
    console.log("bindinput");
    console.log(e.detail);
    // console.log(e.detail.value);
    console.log(e.currentTarget.dataset.params);
    that.setData({
      ['formData.' + e.currentTarget.dataset.params]: e.detail
    });
    // console.log(`formData：${this.data.formData}`)
    console.log(this.data.formData.title)
    console.log(this.data.formData.description)
    console.log(JSON.stringify(this.data.formData))

  },

  onchangedata: function (e) { //bind:change 公共方法
    var that = this;
    // this.data.keyword = e.detail.value;
    console.log("bind:change");
    console.log(e.detail);
    console.log(e.currentTarget.dataset.params);
    that.setData({
      ['formData.' + e.currentTarget.dataset.params]: e.detail
    });
    // console.log(`formData：${this.data.formData}`)
    console.log(JSON.stringify(this.data.formData))

  },
  choosefuwu() { //请选择服务类别
    this.setData({
      show: true,
    });
  },
  onClose() { //关闭服务类别弹窗
    this.setData({
      show: false
    });
  },

  onOkTree() { //确认服务类别弹窗
    var that = this;
    that.setData({
      show: false,
      selectKey: wx.getStorageSync('treeData'),
      ['formData.event_category_id']: wx.getStorageSync('treeData').worker_order_id,
      ['formData.handler']: wx.getStorageSync('treeData').engineer_id,
    });
    wx.removeStorageSync('treeData'); // 移除指定的值
    //请求类型id和优先级id都不能为空
    console.log(that.data.formData)
    if (that.data.formData.request_type > -1 && that.data.formData.priority_id) {
      //相应SLA/解决SLA
      that.getSlaData();
    }
  },
  backTo() {
    wx.navigateBack({
      delta: 0,
    })
  },
  onChangecol(event) {
    this.setData({
      activeNames: event.detail,
    });
  },
  tapItems(e) {
    // console.log(e.detail.item);
    console.log(e.detail.item.id);
    console.log(e.detail.item.name);
    this.selectKey = e.detail.item;
  },

  selectKey(e) {
    console.log(e.detail.item);
    console.log(e.detail.item.id);
    console.log(e.detail.item.name);
    this.selectKey = e.detail.item;
  },
  choosed1(event) { //关联的配置项
    wx.navigateTo({
      url: '../add/seleserve/index',
    })
    // this.setData({
    //   show1: true,
    // });
  },
  choosed2(event) {
    this.setData({
      show2: true,
    });
  },
  choosed3(event) {
    this.setData({
      show3: true,
    });
  },
  choosed4(event) {
    this.setData({
      show4: true,
    });
  },
  onChange(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onConfirm(event) { //选择关联的配置项
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
    this.setData({
      show1: false,
      show2: false,
      show3: false,
      show4: false,
      pzx: value,
      ['formData.eventAssetId']: index,
    });
  },
  onConfirm2(event) { //选择请求类型
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
    this.setData({
      show1: false,
      show2: false,
      show3: false,
      show4: false,
      qqlx: value,
      ['formData.request_type']: index,
    });
  },
  onConfirm3(event) { //选择紧急度
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
    this.setData({
      show1: false,
      show2: false,
      show3: false,
      show4: false,
      jjd: value,
      ['formData.urgency_degree_id']: index,
    });
    //联动优先级
    this.getFirst();
  },
  onConfirm4(event) { //选择影响度
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
    this.setData({
      show1: false,
      show2: false,
      show3: false,
      show4: false,
      yyd: value,
      ['formData.effect_degree']: index,
    });
    // console.log(this.data.formData.effect_degree)
    // console.log(JSON.stringify(this.data.formData))
    //联动优先级
    this.getFirst();
  },
  onCancel() {
    this.setData({
      show1: false,
      show2: false,
      show3: false,
      show4: false,
    });
    Toast('取消');
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.id)
    let query = wx.createSelectorQuery();
    let that = this;
    query.select("#footer").boundingClientRect()
    query.exec(function (res) {
      //取高度
      //console.log(res[0].height);
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
      });
    })
    that.setData({
      ['formData.id']: options.id,
    });
    this.getDatas(options.id);
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

    //服务类别
    that.getCategoryTree();
    //请求类型 
    that.getRequestTypeList();
    //配置项列表
    that.getAllAssetsType();
    //事件紧急度列表查询
    that.urgencyGetAllList();
    //事件影响度查询
    that.affectGetAllList();

    that.getDatas(that.data.formData.id);

     

    setTimeout(function () {
      that.setData({
        // ['formData.eventAssetId']: that.data.assetsTypes.findIndex(o => o.id == that.data.formData2.eventAssetId),
        ['formData.request_type']: that.data.requestTypeList.findIndex(o => o.id == that.data.formData2.request_type),
        ['formData.urgency_degree_id']: that.data.urgencyList.findIndex(o => o.id == that.data.formData2.urgency_degree_id),
        ['formData.effect_degree']: that.data.affectList.findIndex(o => o.id == that.data.formData2.effect_degree),
      });
      //得到优先级和响应SLA时间
      that.getFirst();
      //得到配置项内容
      var pages = getCurrentPages();
      // --上一个编辑页面
      var currPage = pages[pages.length - 1];
      let pzxData1 = currPage.data.pzxData;
      if(pzxData1.id != undefined){
        that.setData({
          ['formData.eventAssetId']:pzxData1.id,
          pzx: pzxData1.name
        })
      }
    }, 500);
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

  },

  /**
   * 服务类别
   */
  getCategoryTree: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/tree',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log("服务类别")
        console.log(res)
        // console.log(JSON.stringify(res.data.data))
        that.setData({
          treeData: res.data.data
        })
      }
    })
  },

  /**
   * 关联的配置项列表
   */
  getAllAssetsType: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'assetsManage/getList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          assetsTypes: res.data.data.list,
          columns1: res.data.data.nameList
        })
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
          requestTypeList: res.data.data.list,
          columns2: res.data.data.nameList
        })
      }
    })
  },

  /**
   * 事件紧急度列表查询
   */
  urgencyGetAllList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/urgencyGetAllList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          urgencyList: res.data.data.list,
          columns3: res.data.data.nameList
        })
      }
    })
  },

  /**
   * 事件影响度查询
   */
  affectGetAllList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/affectGetAllList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log(res)
        that.setData({
          affectList: res.data.data.list,
          columns4: res.data.data.nameList
        })
      }
    })
  },

  /**
   * 优先级联动
   */
  getFirst: function (val) {
    var that = this;
    console.log("getFirst")
    console.log("that.data.formData.effect_degree:", that.data.formData.effect_degree)
    console.log("that.data.formData.urgency_degree_id:", that.data.formData.urgency_degree_id)
    //影响度、紧急度都不能为空
    if (that.data.formData.effect_degree > -1 && that.data.formData.urgency_degree_id > -1) {
      //紧急度
      console.log("that.data.urgencyList:", that.data.urgencyList)
      var urgency_degree_id = that.data.urgencyList[that.data.formData.urgency_degree_id].id;;
      console.log(urgency_degree_id)
      //影响度
      var effect_degree = that.data.affectList[that.data.formData.effect_degree].id;
      console.log(effect_degree)
      wx.request({
        url: app.globalData.address + 'event/getFirst.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          "impact_id": effect_degree,
          "urgent_id": urgency_degree_id
        },
        success: function (res) {
          console.log(res)
          if (res.data.code == 0) {
            // console.log(333)
            if (res.data.data) {
              // console.log(444)
              that.setData({
                ['formData.priority_id']: res.data.data.first_id,
                ['formData.priority_name']: res.data.data.first_name,
              })
              that.getSlaData();
            } else {
              // console.log(55)
              that.setData({
                ['formData.priority_id']: '',
                ['formData.priority_name']: '',
              })
            }
          }
          // console.log(that.data.formData.priority_id)
          // console.log(that.data.formData.priority_name)
        }
      })
    } else {
      console.log("紧急度和影响度都不能为空不满足条件")
    }

  },

  /**
   * 相应SLA/解决SLA
   */
  getSlaData: function () {
    var that = this;
    console.log("getSlaData")
    console.log(that.data.formData)
    //优先级id、工单类别id、请求类型id都不能为空
    if (that.data.formData.priority_id && that.data.formData.event_category_id && that.data.formData.request_type > -1) {
      //请求类型id
      var request_type = that.data.requestTypeList[that.data.formData.request_type].id;;
      console.log(request_type)
      wx.request({
        url: app.globalData.address + 'event/categoryList.do',
        method: 'POST',
        header: {
          "Authorization": wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          "first_id": that.data.formData.priority_id,
          "eventCategoryId": that.data.formData.event_category_id,
          "request_type_id": request_type
        },
        success: function (res) {
          console.log(res)
          if (res.data.code == 0) {
            if (res.data.data == 1) {
              that.setData({
                ['formData.responseSLA']: '暂无'
              })
            } else {
              that.setData({
                ['formData.responseSLA']: res.data.data.response_time + "分钟 / " + res.data.data.solve_time + "分钟"
              })
            }
          }
        }
      })
    } else {
      console.log("优先级id、工单类别id、请求类型id都不能为空不滿足条件")
    }

  },

  moveDisabled: function () {
    var that = this;
    that.setData({
      disabledButton: false
    });
    console.log('禁用解除');
  },

  //开单
  onEventSave() {
    console.log('禁用开始');
    var that = this;
    that.setData({
      disabledButton: true //禁用button
    })
    //2秒后可点击,防重复提交
    setTimeout(function () {
      that.moveDisabled();
    }, 3000);

    var eventData = that.data.formData;
    var eventData2 = that.data.formData2;
    console.log("修改保存:",eventData)
    console.log(eventData2)
    if (!that.data.selectKey.id) {
      wx.showToast({
        title: '请选择服务类别',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (!eventData.title) {
      wx.showToast({
        title: '请输入标题',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (!eventData.description) {
      wx.showToast({
        title: '请输入描述',
        icon: 'none',
        duration: 2000
      })
      return false;
    }

    // if (eventData.eventAssetId == -1) {
    //   wx.showToast({
    //     title: '请选择关联的配置项',
    //     icon: 'none',
    //     duration: 2000
    //   })
    //   return false;
    // }

    // if (eventData.request_type == -1) {
    //   wx.showToast({
    //     title: '请选择请求类型',
    //     icon: 'none',
    //     duration: 2000
    //   })
    //   return false;
    // }

    // if (eventData.effect_degree == -1) {
    //   wx.showToast({
    //     title: '请选择影响度',
    //     icon: 'none',
    //     duration: 2000
    //   })
    //   return false;
    // }
    // if (eventData.urgency_degree_id == -1) {
    //   wx.showToast({
    //     title: '请选择紧急度',
    //     icon: 'none',
    //     duration: 2000
    //   })
    //   return false;
    // }

    //关联的配置项
    var eventAssetId = eventData2.eventAssetId
    var eventAssets1 = [];
    if (eventData.eventAssetId > -1) {
      eventAssetId = that.data.pzxData.id;
      eventAssets1.push({
        "id": eventAssetId
      })
      console.log(eventAssetId)
      console.log(eventAssets1)
    }

    var request_type = eventData2.request_type
    if (eventData.request_type > -1) {
      //请求类型id
      request_type = that.data.requestTypeList[eventData.request_type].id;;
      console.log(request_type)
    }

    var urgency_degree_id = eventData2.urgency_degree_id
    if (eventData.urgency_degree_id > -1) {
      //紧急度
      urgency_degree_id = that.data.urgencyList[eventData.urgency_degree_id].id;
      console.log(urgency_degree_id)
    }

    var effect_degree = eventData2.effect_degree
    if (eventData.effect_degree > -1) {
      //影响度
      effect_degree = that.data.affectList[eventData.effect_degree].id;
      console.log(effect_degree)
    }

    eventData2.title = eventData.title;
    eventData2.description = eventData.description;
    eventData2.service_category = that.data.selectKey.id;
    eventData2.event_category_id = eventData.event_category_id;
    eventData2.handler = eventData.handler;
    eventData2.eventAssetId = eventAssetId;
    eventData2.eventAssets1 = eventAssets1;
    eventData2.request_type = request_type;
    eventData2.urgency_degree_id = urgency_degree_id;
    eventData2.effect_degree = effect_degree;
    eventData2.userId = wx.getStorageSync('userInfo').id;
    //获得图片ID
    var pic_ids = "";
    for (var i = 0; i < that.data.fileList.length; i++) {
      var id = that.data.fileList[i].id;
      if (i == that.data.fileList.length - 1) {
        pic_ids += id;
      } else {
        pic_ids += id + ",";
      }
    }
    console.log(pic_ids);
    if ("" != pic_ids) {
      eventData2.fileIds = pic_ids;
    }

    var param = JSON.stringify(eventData2)
    console.log("自助开单修改保存方法-入参: ",eventData2);
    wx.request({
      url: app.globalData.address + 'event/saveWantToBill',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: param,
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '保存成功',
            icon: 'success',
            duration: 2000
          })
          that.backTo();
        } else {
          wx.showToast({
            title: '保存失败',
            icon: 'error',
            duration: 2000
          })
        }
      }
    })


  },
  afterRead(event) {
    var that = this;

    const {
      file
    } = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    wx.uploadFile({
      url: app.globalData.address + 'event/uploadFile.do',
      filePath: file.url,
      name: 'file',
      formData: {
        user: 'test'
      },
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success(res) {
        console.log("-------------------")
        let row = JSON.parse(res.data);
        let name = row.data.file_name;
        let id = row.data.id;
        var list = that.data.fileList;
        var x = {};
        //   x.url = 'data:image/png;base64,' + base64
        x.url = row.data.file_path;
        console.log(x.url)
        x.name = name;
        x.id = id;
        //   x.deletable = true
        x.isImage = true;
        list.push(x);
        that.setData({
          fileList: list,
        })
      },
    });
  },
  picDel: function (e) {
    console.log(e.detail.file.id);
    var that = this;
    var list = that.data.fileList;
    var num = -1;
    for (var i = 0; i < list.length; i++) {
      if (list[i].id == e.detail.file.id) {
        num = i;
      }
    }
    if (num != -1) {
      list.splice(num, 1);
    }
    that.setData({
      fileList: list,
    })

  },
  picView: function (e) {
    console.log(111111111111111);
    console.log(e);
  },

  /**
   * 数据回显
   */
  getDatas: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/updateWantToBill',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        "id": val
      },
      success: function (res) {
        console.log("数据回显data:", res)
        var selectKeys = {
          id: res.data.data.service_category,
          name: res.data.data.parent_type_name,
          worker_order_id: res.data.data.event_category_id, //工单类别ID
          engineer_id: res.data.data.handler, //自动分派到工程师ID
        }
        // console.log(JSON.stringify(res.data.data))
        // that.data.formData
        that.setData({
          "pzx": res.data.data.eventAssetName,
          "qqlx": res.data.data.request_type_name,
          "jjd": res.data.data.urgency_degree_name,
          "yyd": res.data.data.effect_degree_name,
          "fileList": res.data.data.files,
          "selectKey": selectKeys,
          ['formData.title']: res.data.data.title,
          ['formData.description']: res.data.data.description,
          ['formData.id']: res.data.data.id,
          ['formData.priority_name']: res.data.data.priority_name,
          ['formData.priority_id']: res.data.data.priority_id,
          ['formData.responseSLA']: res.data.data.responseSLA,
          ['formData2.id']: res.data.data.id,
          ['formData2.service_category']: res.data.data.service_category,
          ['formData2.eventAssetId']: res.data.data.eventAssetId,
          ['formData2.request_type']: res.data.data.request_type,
          ['formData2.urgency_degree_id']: res.data.data.urgency_degree_id,
          ['formData2.effect_degree']: res.data.data.effect_degree,
          ['formData.event_category_id']: res.data.data.event_category_id,
          ['formData2.event_category_id']: res.data.data.event_category_id,
          ['formData.handler']: res.data.data.handler,
          ['formData2.handler']: res.data.data.handler,
        });
        
      }
    })
  },

})