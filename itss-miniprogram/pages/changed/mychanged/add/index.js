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
    title: '我要变更',
    topHeight: 0,
    activeNames: ['1', '2'],
    show: false,
    show1: false,
    show2: false,
    show3: false,
    show4: false,
    show5: false,
    starTag: true,
    selDate: new Date().getTime(),
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
    mytitle: '',
    start_time: '请选择',
    end_time: '请选择',
    categoryList: [],
    categoryNameList: [],
    categoryName: '请选择',
    categoryIndex: '',
    typeList: [],
    typeNameList: [],
    typeName: '请选择',
    typeIndex: '',
    sourceList: [],
    sourceNameList: [],
    sourceName: '请选择',
    sourceIndex: '',
    changeApproveUserData: [],
    tableDataApproveUser: [],
    programmeApproveUser: '',
    implementApproveUser: '',
    approve_user: '',//方案审核人ID
    after_implement_user: '',//实施后评审人
    approveNames: '',//评审人名字集合
    change_reason: '',//变更原因
    current_environment: '',//当前环境
    peopleList: [],
    peopleNameList: [],
    programme_user_name: '请选择',//方案拟定人名字
    programme_user_index: '', //方案拟定人下标
    implement_user_name: '请选择', //实施人名字
    implement_user_index: '',//实施人下标
    change_programme: '',//变更方案
    fallback_plan: '',//回退计划
    risk_assessment: '',//风险评估
    eventList: [], //关联的事件
    questionList: [], //关联的问题
    assetsList: [],  //关联的配置
    real_start_time: '',
    real_end_time: '',
  },
 




 
  backTo() {
      this.setData({
        eventList: [],
        questionList: [],
        assetsList: [],
      })
      wx.setStorageSync('eventList', this.data.eventList);
      wx.setStorageSync('questionList', this.data.questionList);
      wx.setStorageSync('assetsList', this.data.assetsList);
    wx.navigateBack({
      delta: 0,
    })
  },


  choosed1(event) { //关联的事件
    wx.navigateTo({
      url: 'seleserve/index',
    })
  },
  
  choosed2(event) { //关联的问题
    wx.navigateTo({
      url: 'selquestion/index',
    })
  },
  choosed3(event) { //关联的配置
    wx.navigateTo({
      url: 'selassets/index',
    })
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
    //变更类别
    this.getCategoryList();
    // 变更类型
    this.getDicts();
    //变更来源
    this.getSource();
    //获取人员信息
    this.getPeopleList();

    var that = this;
    //关联的事件
    that.setData({
        eventList: wx.getStorageSync('eventList'),
        questionList:  wx.getStorageSync('questionList'),
        assetsList: wx.getStorageSync('assetsList'),
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

    //关联的事件
    var changeEvents = [];
  
    var list = that.data.eventList;
    for (let index = 0; index < list.length; index++) {
        var x = {};
        x.event_id = list[index].id;
        x.is_del = '0';
        changeEvents.push(x);  
    }


    //关联的问题
    var changeQuestions = [];
    var qlist = that.data.questionList;
    for (let index = 0; index < qlist.length; index++) {
        var x = {};
        x.question_id = qlist[index].id;
        x.is_del = '0';
        changeQuestions.push(x);  
    }
  
    //关联的配置
    var changeAssets = [];
    var alist = that.data.assetsList;
    for (let index = 0; index < alist.length; index++) {
        var x = {};
        x.assets_id = alist[index].id;
        x.is_del = '0';
        changeAssets.push(x);  
    }
   

    var approve_user = ''; //当前审批人
    var handler_user = ''; //下位处理人

     // 节点
    var changeNodes = [];
    var sorts = 1;
    var n = 7;
    if (that.data.typeName == '紧急变更') {
        n = 5;
    }
    // 构建节点
    for (var i = 1; i <= n; i++) {
    
        if (i == 1) {
            changeNodes.push({time_nodes: i, sort: sorts, time_status: '0', is_del: '0'});
        } else if (i == 2) {
            var approveList = that.data.tableDataApproveUser;
            for (var j = 0; j < approveList.length; j++) {
                if (j == 0) {// 当前审批人赋值
                    approve_user = approveList[j].approve_user;
                        // 下一位处理人
                    handler_user = approveList[j].approve_user;
                }
    
                changeNodes.push({
                    time_nodes: i,
                    sort: sorts,
                    time_status: "1",
                    approve_user: approveList[j].approve_user,
                    is_del: "0"
                });
                sorts++;
            }
        } else {
            if(that.data.typeName == '紧急变更'){
                changeNodes.push({time_nodes: i+2, sort: sorts, time_status: "1", is_del: "0"});
            }else{
                changeNodes.push({time_nodes: i, sort: sorts, time_status: "1", is_del: "0"});
            }
        }
        sorts++;
    }
 


    //处理数据
    var form = {};
    form.changeNodes = changeNodes;
    var changeInfo = {}; //基本数据
    changeInfo.title = that.data.mytitle;
    changeInfo.start_time = that.data.real_start_time;
    changeInfo.end_time = that.data.real_end_time;
    changeInfo.change_classify = that.data.categoryList[that.data.categoryIndex].id;
    changeInfo.approve_user = approve_user;
    if(that.data.typeName == '紧急变更' ){
        changeInfo.change_type = '1';
    }else if(that.data.typeName == '一般变更'){
        changeInfo.change_type = '2';
    }else{
        changeInfo.change_type = '3';
    }
    changeInfo.change_source = that.data.sourceList[that.data.sourceIndex].id;
    changeInfo.change_reason = that.data.change_reason;
    changeInfo.status =  '1';
    changeInfo.handler_user = handler_user;
    changeInfo.current_environment = that.data.current_environment;
    changeInfo.is_del = '0';
    form.changeInfo = changeInfo;
    

    var changeProgrammeDraft = {}; //方案拟定
    changeProgrammeDraft.programme_user = that.data.peopleList[that.data.programme_user_index].id;
    changeProgrammeDraft.change_programme = that.data.change_programme;
    changeProgrammeDraft.fallback_plan = that.data.fallback_plan;
    changeProgrammeDraft.risk_assessment = that.data.risk_assessment;
    changeProgrammeDraft.implement_user = that.data.peopleList[that.data.implement_user_index].id;
    changeProgrammeDraft.is_del = '0';
    form.changeProgrammeDraft = changeProgrammeDraft;


    //方案评审
    var changeProgrammeApprove = {}
    changeProgrammeApprove.approve_user = that.data.approve_user ;
    changeProgrammeApprove.is_del = '0';
    form.changeProgrammeApprove = changeProgrammeApprove;
   

    // 方案实施
    var changeImplement = {};
    changeImplement.implement_user = that.data.peopleList[that.data.implement_user_index].id;
    changeImplement.is_del = '0';
    form.changeImplement = changeImplement;

    //实施后评审人
    console.log("实施后评审人",that.data.after_implement_user);
    var changeImplementApprove = {};
    changeImplementApprove.after_implement_user = that.data.after_implement_user;
    changeImplementApprove.is_del = '0';
    form.changeImplementApprove = changeImplementApprove;

    form.changeEvents = changeEvents;
    form.changeQuestions = changeQuestions;
    form.changeAssets = changeAssets;

    form.userId =  wx.getStorageSync('userInfo').id;

    console.log(form);


    var param = JSON.stringify(form)
    


    wx.request({
      url: app.globalData.address + 'change/toAdd.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/json"
      },
      data: param,
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
  
  

 
  onClose1() {
    this.setData({
        show1: false
    });
   },

   onSelect1(event) {
    console.log(event.detail);
    this.setData({
        type_name: event.detail.name
    });
   },


  //标题
  onchangeTitle: function (e) { 
    var that = this;
    console.log(e.detail)
    that.setData({
      mytitle: e.detail
    });
  },

  showstartTime(event) {
    this.setData({
      show3: true,
      starTag: true,
      // selDate: this.strToData(this.data.startTime)
    });
   },
   showendTime(event) {
    this.setData({
      show3: true,
      starTag: false,
      // selDate: this.strToData(this.data.endTime)
    });
   },
   confirmData(event) {
    // let selData = new Date(event.detail).toLocaleDateString()
    if (this.data.starTag) {
      this.setData({
        start_time: this.formatDate(event.detail),
        real_start_time: event.detail,
      });
    } else {
      this.setData({
        end_time: this.formatDate(event.detail),
        real_end_time: event.detail,
      });
    }
    // this.onClose2()
    this.setData({
      show3: false
    });
  },
  onClose3() {  
    if (this.data.starTag) {
      this.setData({
        show3: false,
        start_time: '请选择',
        real_start_time: '',
      });
    } else {
      this.setData({
        show3: false,
        end_time: '请选择',
        real_end_time: '',
      });
    }
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
  /**
   * 变更类别
   */
  getCategoryList: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'change/getAll.do',
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
            nameList.push(list[i].type_name);
        }
        that.setData({
            categoryNameList: nameList
         })
         console.log(that.data.categoryNameList);
        
      }
    })
  },
  chooseCategory(){
    this.setData({
        show: true
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
        that.setData({
          show: false,
          categoryName: value,
          categoryIndex: index,
        });
   
        if(that.data.categoryName != '请选择' && that.data.typeName != '请选择'){
            var id = that.data.categoryList[that.data.categoryIndex].id;
            this.getChangeApproveUser(id);
        }
    
  },
  onCancel() {
    this.setData({
      categoryName: '请选择',
      categoryIndex: '',
      show: false,
      changeApproveUserData: [],
      tableDataApproveUser: [],
      programmeApproveUser: '',
      implementApproveUser: '',
      approve_user: '',//方案审核人ID
      after_implement_user: '',//实施后评审人
      approveNames: '',

    });
 
  },
  /**
   * 变更类型
   */
  getDicts: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'change/getDicts.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
    
      success: function (res) {
          console.log(res);
          that.setData({
              typeList : res.data.data,
          })
          var list = res.data.data;
          var nameList =  [];
          for(var i = 0 ; i< list.length ; i++){
            nameList.push(list[i].label);
          }
          that.setData({
            typeNameList : nameList,
        })
        console.log(that.data.typeNameList);
      }
    })
  },
  /**
   * 变更来源
   */
  getSource: function (val) {
    var that = this;
    wx.request({
      url: app.globalData.address + 'change/getSourceList.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
    
      success: function (res) {
          console.log(res);
          that.setData({
              sourceList : res.data.data,
          })
          var list = res.data.data;
          var nameList =  [];
          for(var i = 0 ; i< list.length ; i++){
            nameList.push(list[i].source_name);
          }
          that.setData({
            sourceNameList : nameList,
        })
        console.log(that.data.sourceNameList);
      }
    })
  },
 
  onChange1(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onChange2(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onConfirm1(event) { //选择关联的配置项
    var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        that.setData({
          show1: false,
          typeName: value,
          typeIndex: index,
        });
        if(that.data.categoryName != '请选择' && that.data.typeName != '请选择'){
            var id = that.data.categoryList[that.data.categoryIndex].id;
            this.getChangeApproveUser(id);
        }
  },
  onCancel1() {
    this.setData({
        typeName: '请选择',
        typeIndex: '',
        show1: false,
        changeApproveUserData: [],
        tableDataApproveUser: [],
        programmeApproveUser: '',
        implementApproveUser: '',
        approve_user: '',//方案审核人ID
        after_implement_user: '',//实施后评审人
        approveNames: '',
    });
 
  },
  chooseType(){
    this.setData({
        show1: true
    });
  },

  chooseSource(){
    this.setData({
        show2: true
    });
  },
  onConfirm2(event) { //选择关联的配置项
    var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        that.setData({
          show2: false,
          sourceName: value,
          sourceIndex: index,
        });
  },
  onCancel2() {
    this.setData({
        sourceName: '请选择',
        sourceIndex: '',
        show2: false,
    });
 
  },
  //根据变更类别和变更类型获取审批人 方案审核人 实施后评审人
  getChangeApproveUser: function(val){
    var that = this;
    wx.request({
      url: app.globalData.address + 'change/getApproveName.do',
      method: 'POST',
      header: {
        "Authorization": wx.getStorageSync('tokenValue'),
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
          "id": val,
      },
      success: function (res) {
          
     
          that.setData({
            changeApproveUserData: res.data.data,
         });

         console.log(that.data.typeList[that.data.typeIndex]);

         if (that.data.typeList[that.data.typeIndex].value == 1) {//紧急

            that.setData({
                tableDataApproveUser: res.data.data.changeUrgentApproveList,
                programmeApproveUser: '',
                implementApproveUser: res.data.data.changeType.urgent_implement_user_name,
                approve_user: '',
                after_implement_user: res.data.data.changeType.urgent_implement_user,
            })
          } else if (that.data.typeList[that.data.typeIndex].value == 2) {// 一般
            that.setData({
                tableDataApproveUser: res.data.data.changeCommonlyApproveList,
                programmeApproveUser: res.data.data.changeType.commonly_audit_user_name,
                implementApproveUser: res.data.data.changeType.commonly_implement_user_name,
                approve_user: res.data.data.changeType.commonly_audit_user,
                after_implement_user: res.data.data.changeType.commonly_implement_user,
            })
        
          } else if (that.data.typeList[that.data.typeIndex].value == 3) {// 重大
            that.setData({
                tableDataApproveUser: res.data.data.changeMajorApproveList,
                programmeApproveUser: res.data.data.changeType.major_audit_user_name,
                implementApproveUser: res.data.data.changeType.major_implement_user_name,
                approve_user: res.data.data.changeType.urgent_implement_user,
                after_implement_user: res.data.data.changeType.major_implement_user,
            })
        }
        console.log("审核人",that.data.tableDataApproveUser);
        var list = that.data.tableDataApproveUser;
        var approveNames = '';
        for (let i = 0; i < list.length; i++) {
            if(i == list.length-1){
                approveNames+= list[i].approve_name;
            }else{
                approveNames+= list[i].approve_name;
                approveNames += ","
            }
        }
        that.setData({
            approveNames: approveNames
        })
      }
    })

  },
  onchangereason: function(e){
      var that = this
      that.setData({
          change_reason: e.detail,
      })
      console.log(that.data.change_reason);

  },
  onchangeenvironment: function(e){
      var that = this
      that.setData({
          current_environment: e.detail
      })
      console.log(that.data.current_environment);
  },
  //获取人员信息
  getPeopleList: function (val) {
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
            peopleList : res.data.data,
          })
          var list = res.data.data;
          var nameList =  [];
          for(var i = 0 ; i< list.length ; i++){
            nameList.push(list[i].name);
          }
          that.setData({
            peopleNameList : nameList,
        })
        console.log(that.data.peopleNameList);
      }
    })
  },
  chooseProgramme(){
    this.setData({
        show4: true
    });
  },
  onChange4(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onChange5(event) {
    const {
      picker,
      value,
      index
    } = event.detail;
    // Toast(`当前值：${value}, 当前索引：${index}`);
  },
  onConfirm4(event) { 
    var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        that.setData({
          show4: false,
          programme_user_name: value,
          programme_user_index: index,
        });
  },
  onConfirm5(event) { 
    var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        that.setData({
          show5: false,
          implement_user_name: value,
          implement_user_index: index,
        });
  },
  onCancel4() {
    this.setData({
        programme_user_name: '请选择',
        programme_user_index: '',
        show4: false,
    });
 
  },
  onCancel5() {
    this.setData({
        implement_user_name: '请选择',
        implement_user_index: '',
        show5: false,
    });
  },
  chooseImplement(){
    this.setData({
        show5: true
    });
  },
  onchangeprogramme: function(e){
    var that = this
    that.setData({
        change_programme: e.detail
    })
    console.log(that.data.change_programme);
  },
  onfallbackplan: function(e){
    var that = this
    that.setData({
        fallback_plan: e.detail
    })
    console.log(that.data.fallback_plan);
  },
  onriskassessment: function(e){
    var that = this
    that.setData({
        risk_assessment: e.detail
    })
    console.log(that.data.risk_assessment);
  },

  onChangecol(event) {
    this.setData({
      activeNames: event.detail,
    });
  },


 

})