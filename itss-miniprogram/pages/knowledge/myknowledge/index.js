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
        title: '我的知识',
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
        zhuangtai: '去选择',
        username: '去选择',
        actions: [           
            {
                name: '备注',
            },
            {
                name: '撤销',
            },            
            {
                name: '关单',
            },
        ],
        actions1: [{
                name: '待审核',
            },
            {
                name: '发布',
            },
            {
                name: '审核拒绝',
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
            startTime: '请选择',
            endTime: '请选择',
            zhuangtai: '请选择',
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
        if (event.detail.name == "备注") {
            this.setData({
                showdia: true
            });

        } else if (event.detail.name == "撤销") {
            this.setData({
                showdia2: true
            });
        } else if (event.detail.name == "关单") {
            // this.setData({
            //     showdia3: true
            // });
            Dialog.confirm({
                    title: '提示',
                    message: '确定要关闭吗？',
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
            message: '确定要确认吗？',
        })
        .then(() => {
            // on confirm
        })
        .catch(() => {
            // on cancel
        });
    },
    showaction1: function (options) {
        this.setData({
            show1: true
        });
    },
    beizhu: function (options) {
        this.setData({
            showdia1: true
        });
    },
    onClose1() {
        this.setData({
            show1: false,
        });
    },
    onCancel1() {
        this.setData({
            show1: false,
            zhuangtai: '请选择'
        });
    },
    onSelect1(event) {
        console.log(event.detail);
        this.setData({
            zhuangtai: event.detail.name
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

        wx.request({
            url: app.globalData.address + 'knowledge/getMyKnowledgeByPage.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "knowledgeInfo": null,
                "pageIndex": that.data.pageIndex,
                "pageSize": that.data.pageSize,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res)
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

        var status = null;
        if(that.data.zhuangtai != null){
            if(that.data.zhuangtai == '待审核'){
                status = 1
            }else if(that.data.zhuangtai == '发布'){
                status = 2
            }else if(that.data.zhuangtai == '审核拒绝'){
                status = 3
            }
        }

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
            url: app.globalData.address + 'knowledge/getMyKnowledgeByPage.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "title": that.data.queryForm.title,
                "startTime": create_time_start,
                "endTime": create_time_end,
                "status": status==null?"":status,
                "pageNo": 1,
                "pageSize": that.data.pageSize,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                that.setData({
                    list: []
                })
                console.log(res)
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

        var status = null;
        if(that.data.zhuangtai != null){
            if(that.data.zhuangtai == '待审核'){
                status = 1
            }else if(that.data.zhuangtai == '发布'){
                status = 2
            }else if(that.data.zhuangtai == '审核拒绝'){
                status = 3
            }
        }

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
            url: app.globalData.address + 'knowledge/getMyKnowledgeByPage.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "title": that.data.queryForm.title,
                "startTime": create_time_start,
                "endTime": create_time_end,
                "status": status==null?"":status,
                "pageNo": that.data.pageIndex + 1,
                "pageSize": that.data.pageSize,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res)
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
    seeView: function(e){
        var id = e.currentTarget.dataset.id;
        console.log(id);
        wx.navigateTo({
            url: 'detail/index?id=' + id,
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

        var status = null;
        if(that.data.zhuangtai != null){
            if(that.data.zhuangtai == '待审核'){
                status = 1
            }else if(that.data.zhuangtai == '发布'){
                status = 2
            }else if(that.data.zhuangtai == '审核拒绝'){
                status = 3
            }
        }

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
        console.log(create_time_start);
        console.log(create_time_end);

        wx.request({
            url: app.globalData.address + 'knowledge/getMyKnowledgeByPage.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "title": that.data.queryForm.title,
                "startTime": create_time_start,
                "endTime": create_time_end,
                "status": status==null?"":status,
                "pageIndex": 1,
                "pageSize": 10,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res)
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