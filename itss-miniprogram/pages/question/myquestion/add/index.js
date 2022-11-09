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
    title: '问题添加',
    wtly: [], //问题来源 提示信息
    yxj: [], //优先级 提示信息
    wtlb: [], //问题类别 提示信息
    wtlyValue: '请选择', //问题来源 显示的数据
    yxjValue: '请选择', //优先级 显示的数据
    wtlbValue: '请选择', //问题类别 显示的数据
    jjsjValue: '请选择', //解决时间 显示的数据
    wtlyShow: false, //问题来源 弹出层
    yxjShow: false, //优先级 弹出层
    wtlbShow: false, //问题类别 弹出层
    jjsjShow: false, //解决时间 弹出层
    wtlyColumns: [], //问题来源 对象数组配置每一列显示的数据
    yxjColumns: [], //优先级 对象数组配置每一列显示的数据
    wtlbColumns: [], //问题类别 对象数组配置每一列显示的数据
    checked: false,
    topHeight: 0,
    activeNames: ['1', '2'],
    fileList: [],
    changeList: [],
    formData: {
      "source_id": -1, //来源id
      "priority_id": -1, //优先级id
      "category_id": -1, //问题类别id
      "service_category": '', //服务类别id
      "title": '', //标题
      "description": '', //描述,
      "hope_solve_time": '', //解决时间,
      "is_notice": "0", //是否通知
    },
  },
  wtlyBindtap() { //问题来源 点击选择器触发
    this.setData({
      wtlyShow: true,
    });
  },
  yxjBindtap() { //优先级 点击选择器触发
    this.setData({
      yxjShow: true,
    });
  },
  wtlbBindtap() { //问题类别 点击选择器触发
    this.setData({
      wtlbShow: true,
    });
  },
  jjsjBindtap() { //解决时间 点击时间选择触发
    this.setData({
      jjsjShow: true,
    });
  },
  eventBindtap(){//关联工单 点击触发
    wx.navigateTo({
      url: 'seleserve/index',
    })
  },
  wtlyOnConfirm(event) { //问题来源 点击完成按钮时触发
    const {
      value,
      index
    } = event.detail;
    this.setData({
      wtlyShow: false,
      yxjShow: false,
      wtlbShow: false,
      jjsjShow: false,
      wtlyValue: value,
      ['formData.source_id']: this.data.wtly[index].id,
    });
  },
  yxjOnConfirm(event) { //优先级 点击完成按钮时触发
    const {
      value,
      index
    } = event.detail;
    this.setData({
      wtlyShow: false,
      yxjShow: false,
      wtlbShow: false,
      jjsjShow: false,
      yxjValue: value,
      ['formData.priority_id']: this.data.yxj[index].id,
    });
  },
  wtlbOnConfirm(event) { //问题类别 点击完成按钮时触发
    const {
      value,
      index
    } = event.detail;
    this.setData({
      wtlyShow: false,
      yxjShow: false,
      wtlbShow: false,
      jjsjShow: false,
      wtlbValue: value,
      ['formData.category_id']: this.data.wtlb[index].id,
    });
  },
  jjsjOnConfirm(event) { //解决时间 点击完成按钮时触发
    let selData = this.dataToStr(event.detail)
    this.setData({
      wtlyShow: false,
      yxjShow: false,
      wtlbShow: false,
      jjsjShow: false,
      jjsjValue: selData,
      ['formData.hope_solve_time']: selData,
    });
  },
  //选择器取消按钮触发
  onCancel() {
    this.setData({
      wtlyShow: false,
      yxjShow: false,
      wtlbShow: false,
      jjsjShow: false,
    });
    Toast('取消');
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //问题来源
    this.questionSourceList();
    //优先级
    this.slaQuestionFirstList();
    //问题类别
    this.getParentList();
    //关联工单
    this.setData({
        changeList: wx.getStorageSync('changeList'),
    })
  },

  /**
   * 问题来源
   */
  questionSourceList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'question/questionSourceList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log("问题来源", res)
        console.log("问题来源", res.data.data)
        that.setData({
          wtly: res.data.data.list,
          wtlyColumns: res.data.data.nameList
        })
      }
    })
  },
  /**
   * 优先级
   */
  slaQuestionFirstList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'question/slaQuestionFirstList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log("优先级", res)
        console.log("优先级", res.data.data)
        that.setData({
          yxj: res.data.data.list,
          yxjColumns: res.data.data.nameList
        })
      }
    })
  },
  /**
   * 问题类别
   */
  getParentList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'question/getParentList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log("问题类别", res)
        console.log("问题类别", res.data.data)
        that.setData({
          wtlb: res.data.data.list,
          wtlbColumns: res.data.data.nameList
        })
      }
    })
  },

  //2秒后可点击,防重复提交
  moveDisabled: function () {
    var that = this;
    that.setData({
      disabledButton: false
    });
    console.log('禁用解除');
  },
  //添加
  onSave() {
    console.log('禁用开始');
    var that = this;
    that.setData({
      disabledButton: true //禁用button
    })
    //2秒后可点击,防重复提交
    setTimeout(function () {
      that.moveDisabled();
    }, 3000);

    var questionData = that.data.formData;
    questionData.userId = wx.getStorageSync('userInfo').id;
    console.log(questionData)
    if (questionData.source_id == -1) {
      wx.showToast({
        title: '请选择问题来源',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (questionData.priority_id == -1) {
      wx.showToast({
        title: '请选择优先级',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (questionData.category_id == -1) {
      wx.showToast({
        title: '请选择问题类别',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (!questionData.title) {
      wx.showToast({
        title: '请输入标题',
        icon: 'none',
        duration: 2000
      })
      return false;
    }

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
      questionData.fileIds = pic_ids;
    }

    //是否通知
    that.is_notice = this.checked ? 1 : 0
    //关联工单
    that.events = that.data.changeList

    var param = JSON.stringify(questionData)
    console.log(param)
    wx.request({
      url: app.globalData.address + 'question/myQuestionToAdd.do',
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
  backTo() {
    this.setData({
      changeList: [],
    })
    wx.setStorageSync('changeList', this.data.changeList);
    wx.navigateBack({
      delta: 0,
    })
  },
  onChangecol(event) {
    this.setData({
      activeNames: event.detail,
    });
  },
  onChangeChecked(event) {
    this.setData({
      checked: event.detail,
    });
  },
  //日期格式转字符串
  dataToStr(selData) {
    let selDataArr = new Date(selData).toLocaleDateString().split("/")
    let strArr = selDataArr.map(function (value) {
      if (value.length == 1) {
        value = 0 + value
      }
      return value
    });
    return strArr.join("-")
  },
  onchangedata: function (e) { //bind:change 公共方法
    var that = this;
    console.log(e.detail);
    console.log(e.currentTarget.dataset.params);
    that.setData({
      ['formData.' + e.currentTarget.dataset.params]: e.detail
    });
    console.log(JSON.stringify(this.data.formData))
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let query = wx.createSelectorQuery();
    let that = this;
    query.select("#footer").boundingClientRect()
    query.exec(function (res) {
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
      });
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {},
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {},
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {},
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {},
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},
})