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
    title: '我要发布',
    topHeight: 0,
    activeNames: ['1', '2'],
    show: false,
    show1: false,
    show2: false,
    actions1: [{
        name: '一般发布',
    },
    {
        name: '紧急发布',
    },
    ],

    categoryList: [],
    categoryNameList: [],
    categoryName: '请选择',
    categoryIndex: '',
    approver: '',
    publisherName: '',
    publisher: '',
    type: '',
    type_name: '请选择',
    mytitle: '',
    reason: '',
    content: '',
    publish_plan: '',
    test_plan: '',
    tester_id: '',
    tester_name: '请选择',
    testList: [],
    testNameList: [],
    testIndex: '',
    changeList: [],
  },
 
  onsetinput: function (e) { //bindinput 公共方法 输入框
    var that = this;
    // this.data.keyword = e.detail.value;

    that.setData({
      ['formData.' + e.currentTarget.dataset.params]: e.detail
    });
  

  },

  onchangedata: function (e) { //bind:change 公共方法
    var that = this;
    // this.data.keyword = e.detail.value;

    that.setData({
      ['formData.' + e.currentTarget.dataset.params]: e.detail
    });


  },

  onClose() { //关闭服务类别弹窗
    this.setData({
      show: false
    });
    wx.removeStorageSync('treeData'); // 移除指定的值
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



  choosed1(event) { //关联的配置项
    wx.navigateTo({
      url: 'seleserve/index',
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
    // Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onConfirm(event) { //选择关联的配置项
    var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        var list = this.data.categoryList;
        console.log(list);
        let temp1= '';
        let temp2 = '';
        let temp3 = '';
        console.log(index);
        console.log(list);
        for(var i = 0 ; i < list.length ; i++){
            if(i == index){
                temp1 = list[i].approver;
                temp2 = list[i].publisherName;
                temp3 = list[i].publisher;
            }
        }
        that.setData({
          show: false,
          categoryName: value,
          categoryIndex: index,
          approver : temp1,
          publisherName : temp2,
          publisher: temp3,
        });
  },
  onConfirm2(event) { //选择测试人
     var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
       
        that.setData({
          show2: false,
          tester_name: value,
          testIndex: index,
        
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

    //联动优先级
    this.getFirst();
  },
  onCancel() {
    this.setData({
      categoryName: '请选择',
      categoryIndex: '',
      approver: '',
      publisherName: '',
      publisher: '',
      show: false,
    });
 
  },
  onCancel2() {
    this.setData({
      tester_name: '请选择',
      testIndex: '',
      show2: false,
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
    //发布类别
    this.getCategoryList();
    //测试人
    this.getTesterList();
    var that = this;
    that.setData({
        changeList: wx.getStorageSync('changeList'),
    })
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
   * 发布类别
   */
  getCategoryList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'publishInfo/getAllCategory.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
      
        that.setData({
            categoryList: res.data.data,
         })
        var nameList = [];
        var list = that.data.categoryList;
        for(var i = 0 ; i < list.length ; i++){
            nameList.push(list[i].name);
        }
        that.setData({
            categoryNameList: nameList
         })
        
      }
    })
  },

 

  /**
   * 请求类型
   */
  getTesterList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'publishInfo/getAllUser.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {},
      success: function (res) {
          console.log(res);
          that.setData({
              testList : res.data.data,
          })
          var list = res.data.data;
          var nameList =  [];
          for(var i = 0 ; i< list.length ; i++){
            nameList.push(list[i].name);
          }
          that.setData({
            testNameList : nameList,
        })
        console.log(that.data.testNameList);
      }
    })
  },

 

  

  /**
   * 优先级联动
   */
  getFirst: function (val) {
    var that = this;

    //影响度、紧急度都不能为空
    if (that.data.formData.effect_degree > -1 && that.data.formData.urgency_degree_id > -1) {
      //紧急度
      var urgency_degree_id = that.data.urgencyList[that.data.formData.urgency_degree_id].id;;
    
      //影响度
      var effect_degree = that.data.affectList[that.data.formData.effect_degree].id;

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
        
          if (res.data.code == 0) {
           
            if (res.data.data) {
            
              that.setData({
                ['formData.priority_id']: res.data.data.first_id,
                ['formData.priority_name']: res.data.data.first_name,
              })
              that.getSlaData();
            } else {
           
              that.setData({
                ['formData.priority_id']: '',
                ['formData.priority_name']: '',
              })
            }
          }
  
        }
      })
    } else {
      
    }

  },

  /**
   * 相应SLA/解决SLA
   */
  getSlaData: function () {
    var that = this;
 
    //优先级id、工单类别id、请求类型id都不能为空
    if (that.data.formData.priority_id && that.data.formData.event_category_id && that.data.formData.request_type > -1) {
      //请求类型id
      var request_type = that.data.requestTypeList[that.data.formData.request_type].id;;
   
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
   
    }

  },

  moveDisabled: function () {
    var that = this;
    that.setData({
      disabledButton: false
    });
   
  },
  //开单
  onEventSave() {
  
    var that = this;
    that.setData({
      disabledButton: true //禁用button
    })
    //2秒后可点击,防重复提交
    setTimeout(function () {
      that.moveDisabled();
    }, 3000);
    //获取发布类别
    if(that.data.categoryIndex == null || that.data.categoryIndex == '' || that.data.categoryIndex == undefined){
        wx.showToast({
            title: '请选择发布类别',
            icon: 'error',
            duration: 2000
        })
        return;
    }
    var category = that.data.categoryList[that.data.categoryIndex].id;

    //获取发布类型
    if(that.data.type_name=='请选择'){
        wx.showToast({
            title: '请选择发布类型',
            icon: 'error',
            duration: 2000
        })
        return;
    }
    var type = '';
    if(that.data.type_name=='一般发布'){
        type = '1';
    }else{
        type = '2';
    }

    //标题
    if(that.data.mytitle == '' || that.data.mytitle == null || that.data.mytitle == undefined){
        wx.showToast({
            title: '请输入标题',
            icon: 'error',
            duration: 2000
        })
        return;
    }
     //申请发布理由
     if(that.data.reason == '' || that.data.reason == null || that.data.reason == undefined){
        wx.showToast({
            title: '请输入申请发布理由',
            icon: 'error',
            duration: 2000
        })
        return;
    }
    //发布内容
    if(that.data.content == '' || that.data.content == null || that.data.content == undefined){
        wx.showToast({
            title: '请输入发布内容',
            icon: 'error',
            duration: 2000
        })
        return;
    }
    //发布方案
    if(that.data.publish_plan == '' || that.data.publish_plan == null || that.data.publish_plan == undefined){
        wx.showToast({
            title: '请输入发布方案',
            icon: 'error',
            duration: 2000
        })
        return;
    }

    //测试方案
    if(that.data.test_plan == '' || that.data.test_plan == null || that.data.test_plan == undefined){
        wx.showToast({
            title: '请输入测试方案',
            icon: 'error',
            duration: 2000
        })
        return;
    }
    //测试人
    if(that.data.testIndex == null || that.data.testIndex == undefined || that.data.testIndex == ''){
        wx.showToast({
            title: '请选择测试人',
            icon: 'error',
            duration: 2000
        })
        return;
    }
    var tester_id = that.data.testList[that.data.testIndex].id;
    console.log(that.data.publisher)
   
    wx.request({
      url: app.globalData.address + 'publishInfo/toAdd.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: {
          "category": category,
          "type": type,
          "title": that.data.mytitle,
          "reason": that.data.reason,
          "content": that.data.content,
          "publish_plan": that.data.publish_plan,
          "test_plan": that.data.test_plan,
          "tester_id": tester_id,
          "changeVoList": that.data.changeList,
          "userId": wx.getStorageSync('userInfo').id,
          "approver": that.data.approver,
          "publisher": that.data.publisher,
          
      },
      success: function (res) {
        if (res.data.code == 0) {
          wx.showToast({
            title: '成功',
            icon: 'success',
            duration: 2000
          })
          that.backTo();
        } else {
          wx.showToast({
            title: '失败',
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
     
        let row = JSON.parse(res.data);
        let name = row.data.file_name;
        let id = row.data.id;
        var list = that.data.fileList;
        var x = {};
        //   x.url = 'data:image/png;base64,' + base64
        x.url = row.data.file_path;
   
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
   
  },
  chooseCategory(){
    this.setData({
        show: true
    });
  },
  chooseType(){
    this.setData({
        show1: true
    });
  },
  onClose1() {
    this.setData({
        show1: false
    });
   },
   onCancel1() {
    this.setData({
        show1: false,
        type_name: '请选择',
    });
   },
   onSelect1(event) {
    console.log(event.detail);
    this.setData({
        type_name: event.detail.name
    });
   },
   onchangeTitle: function (e) { 
    var that = this;
    console.log(e.detail)
    that.setData({
      mytitle: e.detail
    });
  },
  onchangereason: function (e) { 
    var that = this;
    console.log(e.detail)
    that.setData({
      reason: e.detail
    });
  },
  onchangecontent: function (e) { 
    var that = this;
    console.log(e.detail)
    that.setData({
      content: e.detail
    });
  },
  onchangepublishplan: function (e) { 
    var that = this;
    console.log(e.detail)
    that.setData({
      publish_plan: e.detail
    });
  },
  onchangetestplan: function(e){
    var that = this;
    console.log(e.detail)
    that.setData({
      test_plan: e.detail
    });
  },
  chooseTester(){
    this.setData({
        show2: true
    });
  },



})