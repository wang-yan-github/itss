// pages/thing/working/chuli/index.js
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
import Toast from '@vant/weapp/toast/toast';
var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        is_del: true,
        id: '',
        startTime: ymd, //开始时间
        sole_id: '',
        sole_name: '请选择',
        sole_index: '',
        category_id: '',
        category_name: '请选择',
        category_index: '',
        cost: '',
        plan: '',
        event: {
          id: '',
          title: '',
          description: '',
          create_user_name: '',
          create_time: '',
          response_user_name: '',
          responseTime: '',
        },
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
        activeNames: ['1'],
        show: false,
        show1: false,
        fileList: [
            // {
            //   url: 'E:/upload/idea.jpeg',
            //   name: '图片1',
            // },
            // // Uploader 根据文件后缀来判断是否为图片文件
            // // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
            // {
            //   url: 'http://iph.href.lu/60x60?text=default',
            //   name: '图片2',
            //   isImage: true,
            //   deletable: true,
            // },
          ],
          columns: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
          rColumns: [],
          columns1: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
          rColumns1: [],
    },
    onClosepopup() {
      wx.navigateTo({
        url: '../index'
    })
  },
  onOpenpopup() {
    var that = this;
    console.log(that.data.startTime);
    var solve_type = '';
    for(var i = 0 ; i<that.data.rColumns.length;i++ ){
      if(that.data.sole_name == that.data.rColumns[i].solution_name && that.data.sole_index == i){
          solve_type = that.data.rColumns[i].id;
      }
    }
    console.log(solve_type)
    var programme_type = '';
    for(var i = 0 ; i<that.data.rColumns1.length;i++ ){
      if(that.data.category_name == that.data.rColumns1[i].name && that.data.category_index == i){
        programme_type = that.data.rColumns1[i].id;
      }
    }
    console.log(programme_type);
    var pic_ids = "";

    // for(var i = 0 ; i < that.data.fileList.length; i++){
    //     var id = that.data.fileList[i].id;
    //     if(i==that.data.fileList.length-1){
    //         pic_ids+=id;
    //     }else{
    //         pic_ids+=id+",";
    //     }
    // }
    // console.log(pic_ids);

    wx.request({
      url: app.globalData.address + 'event/processClose',
      method: 'POST',
      header: {
        "Authorization":wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
        "id": that.data.id,
        "ex_time" : that.data.startTime,
        "solve_type": solve_type,
        "programme_type": programme_type,
        "expenses": that.data.cost,
        "solution": that.data.plan,
        "fileIds": pic_ids,
      },
      success: function (res) {
        if(res.data.code == 0){
          wx.showToast({
              title: '处理成功',
              icon: 'success',
              duration: 2000
          })
          that.backTo();
          // that.onClosepopup();
        
      }else {
          wx.showToast({
              title: '处理失败',
              icon: 'error',
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
    onChange(event) {
        this.setData({
          activeNames: event.detail,
        });
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
            that.setData({
                topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),                
                id: options.id,
            });
        })
        var x = null;
        wx.request({
          url: app.globalData.address + 'event/getById',
          method: 'POST',
          header: {
            "Authorization":wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            "id": options.id
          },
          success: function (res) {
            console.log(res.data.data.data.title);
            that.setData({
              event: res.data.data.data,
            })
          }
        })
        that.getAllTypeInfo();
        that.getAllSchemeClassification();
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

    },
    bindDateChange: function (e) { //开始时间
      console.log('picker发送选择改变，携带值为', e.detail.value)
      this.setData({
          startTime: e.detail.value
      })
  },
  showjiejue(event) {
    this.setData({
      show: true,
    });
  },
  showfenlei(event) {
    this.setData({
      show1: true,
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
  onChangefenlei(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onConfirm(event) {
    var that = this;
    const {
      picker,
      value,
      index
    } = event.detail;
    
    console.log(value)
    console.log(index)
    that.setData({
      show: false,
      sole_name: value,
      sole_index: index,
    });
  },
  onConfirm1(event) {
    var that = this;
    const {
      picker,
      value,
      index
    } = event.detail;
    
    console.log(value)
    that.setData({
      show1: false,
      category_name: value,
      category_index: index,
    });
  },
  onCancel() {
    this.setData({
      show: false,
    });
    Toast('取消');
  },
  onCancel1() {
    this.setData({
      show1: false,
    });
    Toast('取消');
  },

  getAllTypeInfo: function(e){
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/getAllTypeInfo',
      method: 'POST',
      header: {
        "Authorization":wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        
      },
      success: function (res) {
        let x = [];
        for(var i = 0 ; i<res.data.data.length; i++ ){
          x.push(res.data.data[i].solution_name)
        }
        console.log(x);
        that.setData({
          columns: x,
          rColumns: res.data.data,
        })
      }
    })
  },
  getAllSchemeClassification: function(e){
    var that = this;
    wx.request({
      url: app.globalData.address + 'event/getAllSchemeClassification',
      method: 'POST',
      header: {
        "Authorization":wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        
      },
      success: function (res) {
        console.log(res.data.data);
        let x = [];
        for(var i = 0 ; i<res.data.data.length; i++ ){
          x.push(res.data.data[i].name)
        }
        console.log(x);
        that.setData({
          columns1: x,
          rColumns1: res.data.data,
        })
      }
    })
  },
  cost: function(e){
    var that = this;
    that.setData({
      cost: e.detail,
    })
  },
  plan: function(e){
    var that = this;
    that.setData({
      plan: e.detail,
    })
  },
  afterRead(event) {
      var that = this;

      const { file } = event.detail;
    // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
    wx.uploadFile({
      url: app.globalData.address + 'event/uploadFile.do',
      filePath: file.url,
      name: 'file',
      formData: { user: 'test' },
      header: {
        "Authorization":wx.getStorageSync('tokenValue'),
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
          x.isImage= true;
          list.push(x);
          that.setData({
            fileList: list,
          })
      },
    });
  },
  picDel:function (e){
      console.log(e.detail.file.id);
      var that = this ; 
      var list = that.data.fileList;
      var num = -1;
      for(var i = 0 ; i < list.length ; i++){
          if(list[i].id == e.detail.file.id){
              num = i;
          }
      }
      if(num != -1){
        list.splice(num,1);
      }
      that.setData({
        fileList: list,
      })

  },
  picView:function(e){
      console.log(111111111111111);
      console.log(e);
  }


})