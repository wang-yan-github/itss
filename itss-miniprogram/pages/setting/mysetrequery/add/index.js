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
    title: '配置请求添加',
    qqlx: [], //请求类型 提示信息
    qqbm: [], //部门 提示信息
    qqlxValue: '请选择', //请求类型 显示的数据
    qqbmValue: '请选择', //部门 显示的数据
    qqlxShow: false, //请求类型 弹出层
    qqbmShow: false, //部门 弹出层
    qqlxColumns: [], //请求类型 对象数组配置每一列显示的数据
    qqbmColumns: [], //部门 对象数组配置每一列显示的数据
    checked: false,
    topHeight: 0,
    activeNames: ['1', '2', '3', '4'],
    fileList: [],
    changeList: [],
    changeList2: [],
    changeList3: [],
    formData: {
      "type": -1,
      "dw_id": -1,
      "content": '',
      "department_name": '',
      "result": '',
      "relevance_NO": '',
      "relevance_name": '',
      "status": '1',
      "assetsConfigReqProperties": [],
      "assetsConfigReqOrders": [],
    },
  },
  qqlxBindtap() { //请求类型 点击选择器触发
    this.setData({
      qqlxShow: true,
    });
  },
  qqbmBindtap() { //部门 点击选择器触发
    this.setData({
      qqbmShow: true,
    });
  },
  eventBindtap(){//关联工单 点击触发
    wx.navigateTo({
      url: 'seleserve/index',
    })
  },
  eventBindtap2(){//关联变更 点击触发
    wx.navigateTo({
      url: 'seleserve2/index',
    })
  },
  eventBindtap3(){//关联配置 点击触发
    wx.navigateTo({
      url: 'seleserve3/index',
    })
  },
  qqlxOnConfirm(event) { //请求类型 点击完成按钮时触发
    const {
      value,
      index
    } = event.detail;
    this.setData({
      qqlxShow: false,
      qqbmShow: false,
      qqlxValue: value,
      ['formData.type']: this.data.qqlx[index].id,
    });
  },
  qqbmOnConfirm(event) { //部门 点击完成按钮时触发
    const {
      value,
      index
    } = event.detail;
    this.setData({
      qqlxShow: false,
      qqbmShow: false,
      qqbmValue: value,
      ['formData.dw_id']: this.data.qqbm[index].id,
    });
  },
  //选择器取消按钮触发
  onCancel() {
    this.setData({
      qqlxShow: false,
      qqbmShow: false,
    });
    Toast('取消');
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    //请求类型
    this.assetsSourceList();
    //部门
    this.slaassetsFirstList();
    //关联
    this.setData({
        changeList: wx.getStorageSync('changeList'),
        changeList2: wx.getStorageSync('changeList2'),
        changeList3: wx.getStorageSync('changeList3'),
    })
  },

  /**
   * 请求类型
   */
  assetsSourceList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'assetsManage/getAll.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log("请求类型", res)
        console.log("请求类型", res.data.data)
        that.setData({
          qqlx: res.data.data.list,
          qqlxColumns: res.data.data.nameList
        })
      }
    })
  },
  /**
   * 部门
   */
  slaassetsFirstList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'assetsManage/deptList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
        console.log("部门", res)
        console.log("部门", res.data.data)
        that.setData({
          qqbm: res.data.data.list,
          qqbmColumns: res.data.data.nameList
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

    var assetsData = that.data.formData;
    //用户ID
    assetsData.WX_userId = wx.getStorageSync('userInfo').id;
    //关联工单
    assetsData.events = that.data.changeList
    //关联变更
    if(that.data.changeList2[0]){
      assetsData.relevance_NO = that.data.changeList2[0].id
    }
    //关联配置
    assetsData.assetsManages = that.data.changeList3
    console.log(assetsData)
    if (assetsData.type == -1) {
      wx.showToast({
        title: '请选择请求类型',
        icon: 'none',
        duration: 2000
      })
      return false;
    }
    if (assetsData.dw_id == -1) {
      wx.showToast({
        title: '请选择部门',
        icon: 'none',
        duration: 2000
      })
    }
    if (!assetsData.content) {
      wx.showToast({
        title: '请输入需变更内容',
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
      assetsData.fileIds = pic_ids;
    }

    var param = JSON.stringify(assetsData)
    console.log(param)
    wx.request({
      url: app.globalData.address + 'assetsManage/toAdd.do',
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
      changeList2: [],
      changeList3: [],
    })
    wx.setStorageSync('changeList', this.data.changeList);
    wx.setStorageSync('changeList2', this.data.changeList2);
    wx.setStorageSync('changeList3', this.data.changeList3);
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