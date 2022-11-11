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
import Toast from '@vant/weapp/toast/toast';
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
        title: '我的配置请求',
        selected: true,
        selected1: false,
        selected2: false,
        selected3: false,
        itemselected: false,
        topHeight: 0,
        searchHeight: 0,
        startTime: '请选择', //开始时间
        endTime: '请选择', //结束时间
        selDate: new Date().getTime(),
        minDate: new Date(2019, 10, 1).getTime(),
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
        customerList: [{
                firstLetter: "A",
                list: [],
            },
            {
                firstLetter: "B",
                list: [],
            },
            {
                firstLetter: "C",
                list: [],
            },
            {
                firstLetter: "D",
                list: [],
            },
        ],
        zhuangtai: '去选择',
        username: '去选择',
        actions: [           
            {
                name: '历史记录',
            },             
            {
                name: '删除',
            },
        ],
        actions1: [{
            name: '待处理',
        },
        {
            name: '已完成',
        },
        {
            name: '已撤销',
        },
        {
            name: '拒绝',
        },
        ],
        chulichecked: false,
        list: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: '',
        pages: '',
        queryForm: {
            title: '',
        },
        statusName: '请选择',
        requestList: [],
        requestNameList: [],
        request_name: '请选择',
        request_index: '',
        showMZView: false,
        count: 0,
        authority: {},
        starTag: true,
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
    //状态栏切换
    changtab1(event) {
        this.setData({
            selected: true,
            selected1: false,
            selected2: false,
        });
    },
    changtab2(event) {
        this.setData({
            selected: false,
            selected1: true,
            selected2: false,
        });
    },
    changtab3(event) {
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
    getUserInfo(event) {
        console.log(event.detail);
        this.setData({
            shuoming: '',
        });
    },
    onClosedia() {
        this.setData({
            showdia: false,
            shuoming: '',
        });
    },
    getUserInfo1(event) {
        console.log(event.detail);
        this.setData({
            shuoming: '',
        });
    },
    onClosedia1() {
        this.setData({
            showdia1: false,
            shuoming: '',
        });
    },
    getUserInfo2(event) {
        console.log(event.detail);
        this.setData({
            shuoming: '',
        });
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
        console.log(event.detail);
        this.setData({
            shuoming: '',
        });
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
            showselect: false,
            statusName: '请选择',
            startTime: '请选择',
            endTime: '请选择',
            request_name: '请选择',
            request_index: '',
        });
        this.page();
    },
    onOpenpopup() {
        this.setData({
            showselect: false
        });
        this.page();
    },
    onClosepopup1() {
        this.setData({
            showselect1: false
        });
    },
    showaction: function (options) {
        this.setData({
            show: true
        });
    },
    onClose() {
        this.setData({
            show: false
        });
    },
    onSelect(event) {
        //console.log(event.detail);
        if (event.detail.name == "历史记录") {
            this.setData({
                showdia: true
            });

        }  else if (event.detail.name == "删除") {
            // this.setData({
            //     showdia3: true
            // });
            Dialog.confirm({
                    title: '提示',
                    message: '确定要删除吗？',
                })
                .then(() => {
                    // on confirm
                })
                .catch(() => {
                    // on cancel
                });

        }
    },
    confirmpd: function (options) {
        Dialog.confirm({
            title: '提示',
            message: '确定要撤消吗？',
        })
        .then(() => {
            var id = options.currentTarget.dataset.id;
            console.log(id);


            var that = this;
            wx.request({
                url: app.globalData.address + 'assetsManage/toCancel.do',
                method: 'POST',
                header: {
                    "Authorization": wx.getStorageSync('tokenValue'),
                    "Content-Type": "application/json"
                },
                data: {
                    "id": id,
                    "userId": wx.getStorageSync('userInfo').id,
                },
                success: function (res) {
                    console.log(res);
                    if(res.data.code == 0){
                        wx.showToast({
                            title: '撤销成功',
                            icon: 'success',
                            duration: 2000
                        })
                        that.reflushPage();
                      
                    }else {
                        wx.showToast({
                            title: res.data.msg,
                            icon: 'error',
                            duration: 2000
                        })
                    }
                }
            })


            // on confirm
        })
        .catch(() => {
            // on cancel
            wx.showToast({
                title: "撤销失败",
                icon: 'error',
                duration: 2000
            })
        });
    },
    showaction1: function (options) {
        this.setData({
            show1: true
        });
    },
    showaction2: function (options) {
        this.setData({
            show2: true
        });
    },
    beizhu: function (options) {
        this.setData({
            showdia1: true
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
            statusName: '请选择',
        });
    },
    onSelect1(event) {
        console.log(event.detail);
        this.setData({
            statusName: event.detail.name
        });
    },
    bindDateChange: function (e) { //开始时间
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            startTime: e.detail.value,
            count: 1,
        })
    },
    bindDateChange2: function (e) { //结束时间
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            endTime: e.detail.value,
            count: 1,
        })
    },
    chuli: function (e) {
        wx.navigateTo({
            url: 'detail/index',
        })
    },
    addgd: function (e) {
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
        that.getAllRequest();
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

      const permission = wx.getStorageSync("authority");
      this.setData({
        authority: permission
      }) 
        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
        })
        var that = this;
        // var beanParam = {};
        // beanParam.beanParam = "1";
        wx.request({
            url: app.globalData.address + 'assetsManage/toMyReqList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": that.data.pageIndex,
                "pageSize": that.data.pageSize,
                "beanParam": null,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res.data.data.list)
                that.setData({
                    list: res.data.data.list,
                    pageIndex: res.data.data.pageNum,
                    pageSize: res.data.data.pageSize,
                    totalPage: res.data.data.total,
                    pages: res.data.data.pages,
                    // showMZView: false
                })
                if (res.data.data.list.length == 0) {
                    that.setData({
                        showMZView: true
                    })
                } else {
                    that.setData({
                        showMZView: false
                    })
                }
            }
        })
         //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
         wx.pageScrollTo({
            scrollTop: 0
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

        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
         })

        var that = this;
        var status = null ;
       if(null != that.data.statusName){
           if(that.data.statusName == '待处理'){
              status = 1;
           }else if(that.data.statusName == '已完成'){
              status = 2;
           }else if(that.data.statusName == '已撤销'){
              status = 3;
           }else if(that.data.statusName == '拒绝'){
              status = 4;
           }
        }
       console.log(that.data.requestList);
       var type = null;
       for(var i = 0 ; i< that.data.requestList.length ; i++){
           if(that.data.request_index == i && that.data.requestList[i].request_name == that.data.request_name){
            type = that.data.requestList[i].id;
           }
       }
       console.log(type);
       let create_time_start = '';
       let create_time_end = '';
       if(that.data.startTime == '请选择' || that.data.startTime==undefined || that.data.startTime==''){
           create_time_start = '';
           // create_time_end= '';
       }else{
           create_time_start = that.data.startTime==undefined?'':that.data.startTime;
           // create_time_end = that.data.endTime==undefined?'':that.data.endTime;
       }
       if(that.data.endTime == '请选择' || that.data.endTime==undefined || that.data.endTime==''){
           create_time_end= '';
       }else{
           create_time_end = that.data.endTime==undefined?'':that.data.endTime;
       }
        wx.request({
            url: app.globalData.address + 'assetsManage/toMyReqList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": 1,
                "pageSize": that.data.pageSize,
                "keyword": that.data.queryForm.title,
                "status": status==null?"":status,
                "start_time": create_time_start,
                "end_time": create_time_end,
                "type": type == null ? "":type,
                "userId": wx.getStorageSync('userInfo').id,
                
            },
            success: function (res) {
                console.log(res.data.data.list)
                that.setData({
                    list: [],
                })
                var raalList = [];
                raalList = that.data.list;
                for(var i = 0 ; i< res.data.data.list.length ; i ++ ){
                    raalList.push(res.data.data.list[i]);
                } 
                that.setData({
                    list: raalList,
                    pageIndex: res.data.data.pageNum,
                    pageSize: res.data.data.pageSize,
                    totalPage: res.data.data.total,
                    pages: res.data.data.pages,
                    // showMZView: false
                })
                if (res.data.data.list.length == 0) {
                    that.setData({
                        showMZView: true
                    })
                } else {
                    that.setData({
                        showMZView: false
                    })
                }
            }
        })
        setTimeout(function () {
            wx.hideNavigationBarLoading() //完成停止加载
            wx.stopPullDownRefresh() //停止下拉刷新
        }, 1500);


    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
         })

        var that = this;
        var status = null ;
       if(null != that.data.statusName){
           if(that.data.statusName == '待处理'){
              status = 1;
           }else if(that.data.statusName == '已完成'){
              status = 2;
           }else if(that.data.statusName == '已撤销'){
              status = 3;
           }else if(that.data.statusName == '拒绝'){
              status = 4;
           }
        }
       console.log(that.data.requestList);
       var type = null;
       for(var i = 0 ; i< that.data.requestList.length ; i++){
           if(that.data.request_index == i && that.data.requestList[i].request_name == that.data.request_name){
            type = that.data.requestList[i].id;
           }
       }
       console.log(type);
       let create_time_start = '';
       let create_time_end = '';
       if(that.data.startTime == '请选择' || that.data.startTime==undefined || that.data.startTime==''){
           create_time_start = '';
           // create_time_end= '';
       }else{
           create_time_start = that.data.startTime==undefined?'':that.data.startTime;
           // create_time_end = that.data.endTime==undefined?'':that.data.endTime;
       }
       if(that.data.endTime == '请选择' || that.data.endTime==undefined || that.data.endTime==''){
           create_time_end= '';
       }else{
           create_time_end = that.data.endTime==undefined?'':that.data.endTime;
       }
        wx.request({
            url: app.globalData.address + 'assetsManage/toMyReqList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": that.data.pageIndex + 1,
                "pageSize": that.data.pageSize,
                "keyword": that.data.queryForm.title,
                "status": status==null?"":status,
                "start_time": create_time_start,
                "end_time": create_time_end,
                "type": type == null ? "":type,
                "userId": wx.getStorageSync('userInfo').id,
                
            },
            success: function (res) {
                console.log(res.data.data.list)
                var raalList = [];
                raalList = that.data.list;
                for(var i = 0 ; i< res.data.data.list.length ; i ++ ){
                    raalList.push(res.data.data.list[i]);
                } 
                that.setData({
                    list: raalList,
                    pageIndex: res.data.data.pageNum,
                    pageSize: res.data.data.pageSize,
                    totalPage: res.data.data.total,
                    pages: res.data.data.pages,
                    // showMZView: false
                })
                if (res.data.data.list.length == 0) {
                    that.setData({
                        showMZView: true
                    })
                } else {
                    that.setData({
                        showMZView: false
                    })
                }
            }
        })
         //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
         wx.pageScrollTo({
            scrollTop: 0
        })


    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    },
    reflushPage: function (){
        var that = this;
        // var beanParam = {};
        // beanParam.beanParam = "1";
        wx.request({
            url: app.globalData.address + 'assetsManage/toMyReqList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": that.data.pageIndex,
                "pageSize": that.data.pageSize,
                "beanParam": null,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res.data.data.list)
                that.setData({
                    list: res.data.data.list,
                    pageIndex: res.data.data.pageNum,
                    pageSize: res.data.data.pageSize,
                    totalPage: res.data.data.total,
                    pages: res.data.data.pages,
                    // showMZView: false
                })
                // if (res.data.data.list.length == 0) {
                //     that.setData({
                //         showMZView: true
                //     })
                // } else {
                //     that.setData({
                //         showMZView: false
                //     })
                // }
            }
        })
         //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
         wx.pageScrollTo({
            scrollTop: 0
        })
    },
    getAllRequest:function (){
        var that = this;
        wx.request({
            url: app.globalData.address + 'assetsManage/getAllRequest.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
            },
            success: function (res) {
               
                var list = res.data.data;
                var listName = [];
                for(var i = 0 ; i< list.length ; i++){
                    let name = list[i].request_name;
                    listName.push(name);
                } 
                console.log(listName);
                that.setData({
                    requestList: list,
                    requestNameList: listName,
                })
            }
        })
       },
       //默认展示关键字搜索
     onkeywordOne: function (e) {
        console.log(e.detail.value);
       this.data.queryForm.title= e.detail.value;
   },
    // 默认-关键字搜索
    publishSearch: function (e) {
        this.setData({
           showselect: false
         });
       this.page();
      },
      page: function (val){
        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
         })
        var that = this;


        var status = null ;
       if(null != that.data.statusName){
           if(that.data.statusName == '待处理'){
              status = 1;
           }else if(that.data.statusName == '已完成'){
              status = 2;
           }else if(that.data.statusName == '已撤销'){
              status = 3;
           }else if(that.data.statusName == '拒绝'){
              status = 4;
        }
       }


       console.log(that.data.requestList);
       var type = null;
       for(var i = 0 ; i< that.data.requestList.length ; i++){
           if(that.data.request_index == i && that.data.requestList[i].request_name == that.data.request_name){
            type = that.data.requestList[i].id;
           }
       }
       console.log(type);


       let create_time_start = '';
       let create_time_end = '';
       if(that.data.startTime == '请选择' || that.data.startTime==undefined || that.data.startTime==''){
           create_time_start = '';
           // create_time_end= '';
       }else{
           create_time_start = that.data.startTime==undefined?'':that.data.startTime;
           // create_time_end = that.data.endTime==undefined?'':that.data.endTime;
       }
       if(that.data.endTime == '请选择' || that.data.endTime==undefined || that.data.endTime==''){
           create_time_end= '';
       }else{
           create_time_end = that.data.endTime==undefined?'':that.data.endTime;
       }
       console.log(create_time_start)
       console.log(create_time_end)




        wx.request({
            url: app.globalData.address + 'assetsManage/toMyReqList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": that.data.pageIndex,
                "pageSize": that.data.pageSize,
                "keyword": that.data.queryForm.title,
                "status": status==null?"":status,
                "start_time": create_time_start,
                "end_time": create_time_end,
                "type": type == null ? "":type,
                "userId": wx.getStorageSync('userInfo').id,
                
            },
            success: function (res) {
                console.log(res.data.data.list)
                that.setData({
                    list: res.data.data.list,
                    pageIndex: res.data.data.pageNum,
                    pageSize: res.data.data.pageSize,
                    totalPage: res.data.data.total,
                    pages: res.data.data.pages,
                    // showMZView: false
                })
                if (res.data.data.list.length == 0) {
                    that.setData({
                        showMZView: true
                    })
                } else {
                    that.setData({
                        showMZView: false
                    })
                }
            }
        })
         //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
         wx.pageScrollTo({
            scrollTop: 0
        })



      },
      onChangejieju(event) {
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
          show2: false,
          request_name: value,
          request_index: index,
        });
      },
      onCancel() {
        this.setData({
          show2: false,
          request_name: '请选择',
          request_index: '',
        });
        // Toast('取消');
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
          show3: false
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
      onClose3() {  
        if (this.data.starTag) {
          this.setData({
            show3: false,
            startTime: '请选择',
          });
        } else {
          this.setData({
            show3: false,
            endTime: '请选择',
          });
        }
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
})