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
        // 状态栏高度
        statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
        // 导航栏高度
        navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
        // 胶囊按钮高度
        menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
        // 导航栏和状态栏高度
        navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
        title: '发布管理',
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
        zhuangtai: '去选择',
        leixing: '去选择',
        username: '去选择',
        actions: [           
            {
                name: '备注',
            },
            {
                name: '删除',
            },            
            {
                name: '关单',
            },
            {
                name: '详情',
            },
        ],
        actions1: [{
            name: '一般发布',
        },
        {
            name: '紧急发布',
        },
    ],
    actions2: [{
        name: '已撤销',
    },
    {
        name: '待评审',
    },
    {
        name: '评审拒绝',
    },
    {
        name: '待测试',
    },
    {
        name: '待发布',
    },
    {
        name: '发布成功',
    },
    {
        name: '发布失败',
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
            leixing: '请选择',
            zhuangtai: '请选择',
            startTime: '请选择',
            endTime: '请选择',
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

        } else if (event.detail.name == "删除") {
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
        } else if (event.detail.name == "关单") {
            
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

        }else if (event.detail.name == "详情") {
            wx.navigateTo({
              url: 'detail/index',
            })

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
            leixing: '请选择'
        });
    },
    onClose2() {
        this.setData({
            show2: false
        });
    },
    onCancel2() {
        this.setData({
            show2: false,
            zhuangtai: '请选择'
        });
    },
    onSelect1(event) {
        console.log(event.detail);
        this.setData({
            leixing: event.detail.name
        });
    },
    onSelect2(event) {
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
    seeView: function (e) {
        var id = e.currentTarget.dataset.id;
        console.log(id);
        wx.navigateTo({
            url: '../myfabu/detail/index?id=' + id,
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
            url: app.globalData.address + 'publishInfo/toManageList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": that.data.pageIndex,
                "pageSize": that.data.pageSize,
                "beanParam": null,
                // "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res)
                console.log(res);
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
        var that = this;
        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
        })

        var type = null;
        if(null != that.data.leixing && "" != that.data.leixing && undefined != that.data.leixing){
            if(that.data.leixing == '一般发布'){
                type = 1;
            }else if(that.data.leixing == '紧急发布'){
                type = 2;
            }
        }
        var status = null ;
    if(null != that.data.zhuangtai && "" != that.data.zhuangtai && undefined != that.data.zhuangtai){
        if(that.data.zhuangtai == '已撤销'){
            status = 0;
        }else if (that.data.zhuangtai == '待评审'){
            status = 1;
        }else if (that.data.zhuangtai == '评审拒绝'){
            status = 2;
        }else if (that.data.zhuangtai == '待测试'){
            status = 3;
        }else if (that.data.zhuangtai == '待发布'){
            status = 4;
        }else if (that.data.zhuangtai == '发布成功'){
            status = 5;
        }else if (that.data.zhuangtai == '发布失败'){
            status = 6;
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
            url: app.globalData.address + 'publishInfo/toManageList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": 1,
                "pageSize": that.data.pageSize,
                "keyword": that.data.queryForm.title,
                "type": type == null?"":type,
                "status": status==null?"":status,
                "create_time_start": create_time_start,
                "create_time_end": create_time_end,
   
            },
            success: function (res) {
                that.setData({
                    list: [],
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
        var that = this;
        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
        })

        var type = null;
        if(null != that.data.leixing && "" != that.data.leixing && undefined != that.data.leixing){
            if(that.data.leixing == '一般发布'){
                type = 1;
            }else if(that.data.leixing == '紧急发布'){
                type = 2;
            }
        }
        var status = null ;
    if(null != that.data.zhuangtai && "" != that.data.zhuangtai && undefined != that.data.zhuangtai){
        if(that.data.zhuangtai == '已撤销'){
            status = 0;
        }else if (that.data.zhuangtai == '待评审'){
            status = 1;
        }else if (that.data.zhuangtai == '评审拒绝'){
            status = 2;
        }else if (that.data.zhuangtai == '待测试'){
            status = 3;
        }else if (that.data.zhuangtai == '待发布'){
            status = 4;
        }else if (that.data.zhuangtai == '发布成功'){
            status = 5;
        }else if (that.data.zhuangtai == '发布失败'){
            status = 6;
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
            url: app.globalData.address + 'publishInfo/toManageList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": that.data.pageIndex + 1,
                "pageSize": that.data.pageSize,
                "keyword": that.data.queryForm.title,
                "type": type == null?"":type,
                "status": status==null?"":status,
                "create_time_start": create_time_start,
                "create_time_end": create_time_end,
   
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
    deleteOne: function(e){
        var id = e.currentTarget.dataset.id;
        var that = this;


        wx.request({
            url: app.globalData.address + 'publishInfo/toDel.do',
            method: 'POST',
            header: {
              "Authorization":wx.getStorageSync('tokenValue'),
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
                    title: '删除成功',
                    icon: 'success',
                    duration: 2000
                })
                that.reloadPage();
              
            }else {
                wx.showToast({
                    title: '删除失败',
                    icon: 'error',
                    duration: 2000
                })
            }
            }
          })
    
    


    },
    reloadPage: function(e){
      
        var that = this;
        wx.request({
            url: app.globalData.address + 'publishInfo/toManageList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": that.data.pageIndex,
                "pageSize": that.data.pageSize,
                "beanParam": null,
                // "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                console.log(res)
                console.log(res);
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

            var type = null;
            if(null != that.data.leixing && "" != that.data.leixing && undefined != that.data.leixing){
                if(that.data.leixing == '一般发布'){
                    type = 1;
                }else if(that.data.leixing == '紧急发布'){
                    type = 2;
                }
            }

            var status = null ;
        if(null != that.data.zhuangtai && "" != that.data.zhuangtai && undefined != that.data.zhuangtai){
            if(that.data.zhuangtai == '已撤销'){
                status = 0;
            }else if (that.data.zhuangtai == '待评审'){
                status = 1;
            }else if (that.data.zhuangtai == '评审拒绝'){
                status = 2;
            }else if (that.data.zhuangtai == '待测试'){
                status = 3;
            }else if (that.data.zhuangtai == '待发布'){
                status = 4;
            }else if (that.data.zhuangtai == '发布成功'){
                status = 5;
            }else if (that.data.zhuangtai == '发布失败'){
                status = 6;
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
                url: app.globalData.address + 'publishInfo/toManageList.do',
                method: 'POST',
                header: {
                    "Authorization": wx.getStorageSync('tokenValue'),
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                data: {
                    "pageIndex": 1,
                    "pageSize": 10,
                    "keyword": that.data.queryForm.title,
                    "type": type == null?"":type,
                    "status": status==null?"":status,
                    "create_time_start": create_time_start,
                    "create_time_end": create_time_end,
       
                },
                success: function (res) {
                    console.log(res)
                    console.log(res);
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
          strToData(strData) {
            //转换数组再转字符窜
            let formatData = strData.split("-").join(',')
            return new Date(formatData).getTime()
          },
})