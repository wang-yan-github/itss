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
import Dialog1 from '@vant/weapp/dialog/dialog';
// import Dialog1 from '@vant/weapp/dist/dialog/dialog';

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
        title: '全部事件管理',
        selected: true,
        selected1: false,
        selected2: false,
        selected3: false,
        itemselected: false,
        topHeight: 0,
        searchHeight: 0,
        startTime: ymd, //开始时间
        endTime: ymd, //结束时间
        status: 0,
        show: false,
        show1: false,
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
            {name: '重开',}, {name: '撤销',}, {name: '备注',}, {name: '删除',},
        ],
        actions1: [],
        chulichecked: false,
        showView: false, //通过true 和 false 来控制显示隐藏
        showCloseView: false, //通过true 和 false 来控制显示隐藏
        list: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: '',
        pages: '',
        first_ids: [],
        rowId: '',
        form: {
            id: '',
            title: '',
            billStartTime: '',
            billEndTime: '',
            source_id: '',
        },
        authority:'',
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
            first_ids: [],
        });
        this.page();
    },
    changtab3(event) {
        this.setData({
            selected: false,
            selected1: false,
            selected2: true,
        });
    },
    //关键字搜索
    onkeyword: function (e) {
        this.data.form.title = e.detail.value;
    },
    //关键字搜索
    eventSearch: function (e) {
        this.setData({
            showselect: false
        });
        this.page(1)
    },
    // 查询条件: 编号
    onbianhao: function (e) {
        this.data.form.id = e.detail;
    },
    // 查询条件: 标题
    onbiaoti: function (e) {
        this.data.form.title = e.detail;
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
        let list = that.data.list;
        let ids = [];
        for (let i = 0; i < list.length; i++) {
            ids.push(list[i].id);
        }
        if (event.detail) {
            this.setData({
                chulichecked: event.detail,
                first_ids: ids,
            });
        } else {
            this.setData({
                chulichecked: event.detail,
                first_ids: [],
            });
        }

    },
    itemonchange(event) {
        var that = this;
        let row = event.currentTarget.dataset.ischecked;
        let first_ids = that.data.first_ids;
        //判断checked是否为undefined，如果为undefined，则默认为false，否则为true
        if (first_ids.findIndex(o => o == row.id) != -1) {
            first_ids.splice(first_ids.findIndex(o => o == row.id), 1);
            that.setData({
                first_ids: first_ids,
            })
        } else {
            first_ids.push(row.id)
            that.setData({
                first_ids: first_ids,
            })
        }
    },
    //弹窗方法
    //重开
    getUserInfo(event) {
        var that = this;
        let id = event.currentTarget.dataset.id;
        if (null == id || id == '') {
            wx.showToast({
                title: '请选择一条数据',
                icon: 'none',
                duration: 2000
            });
            return;
        }
        wx.request({
            url: app.globalData.address + 'event/toReopen.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "id": id,
                "current_user": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                if (res.data.code == 0) {
                    that.setData({
                        shuoming: '',
                        list: that.data.list,
                    });
                    wx.showToast({
                        title: '重开成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.changtab1();
                    that.page();
                } else {
                    wx.showToast({
                        title: res.data.msg,
                        icon: 'none',
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
    //备注
    getUserInfo1(event) {
        var that = this;
        let id = event.currentTarget.dataset.id;
        if (null == id || id == '') {
            wx.showToast({
                title: '请选择一条数据',
                icon: 'none',
                duration: 2000
            });
            return;
        }
        wx.request({
            url: app.globalData.address + 'event/updateRemark',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "id": id,
                "remark": that.data.shuoming,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                if (res.data.code == 0) {
                    that.setData({
                        shuoming: '',
                        list: that.data.list,
                    });
                    wx.showToast({
                        title: '备注成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.changtab1();
                    that.page();
                } else {
                    wx.showToast({
                        title: res.data.msg,
                        icon: 'none',
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
    // 撤销
    getUserInfo2(event) {
        var that = this;
        let id = event.currentTarget.dataset.id;
        if (null == id || id == "" || id == undefined) {
            wx.showToast({
                title: '请选择要变更的事件',
                icon: 'none',
                duration: 2000
            })
            return;
        }
        wx.request({
            url: app.globalData.address + 'event/revoke',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "id": id,
                "remark": that.data.shuoming,
            },
            success: function (res) {
                if (res.data.code == 0) {
                    that.setData({
                        shuoming: '',
                        list: that.data.list,
                    });
                    wx.showToast({
                        title: '撤销成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.changtab1();
                    that.page();
                } else {
                    wx.showToast({
                        title: '撤销失败',
                        icon: 'none',
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
            showselect: true,
            form: {},
        });
    },
    showPopup1() {
        this.setData({
            showselect1: true
        });
    },
    // 关闭查询弹窗
    onQueryCloseChange(event) {
        this.setData({
            showselect: false,
            showselect1: false,
        });
        this.data.form = {};
    },
    // 查询确定按钮
    onQueryConfirm(event) {
        let that = this;
        this.setData({
            showselect: false
        });
        this.page(1)
        this.data.form = {};
    },
    //批量关单
    onClosepopup() {
        let that = this;
        let ids = that.data.first_ids;
        if (ids.length == 0) {
            wx.showToast({
                title: '请选择关单事件',
                icon: 'error',
                duration: 2000
            })
            return;
        }
        wx.showLoading({
            title: '关单中...',
            icon: 'loading',
            duration: 2000
        })
        wx.request({
            url: app.globalData.address + 'event/closingBatch.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/json"
            },
            data: {
                "ids": ids,
                "current_user": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                if (res.data.code == 0) {
                    wx.showToast({
                        title: '关单成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.changtab2();
                    that.page();
                } else {
                    wx.showToast({
                        title: '关单失败',
                        icon: 'error',
                        duration: 2000
                    })
                }
            }
        })
    },
    onClosepopup1() {
        this.setData({
            showselect1: false
        });
    },
    showaction: function (options) {
      var id = options.currentTarget.dataset.id;
      var status = options.currentTarget.dataset.state;
      console.log("-----------------更多操作------------------")
      const permission = wx.getStorageSync("authority");
      console.log(permission)
      let arr = [];
      if(permission.event_whole_chongkai){
        if(status == 4 || status == 5 || status == 6){
          arr.push({name: '重开'});
        }
      }
      if(permission.event_whole_chexiao){
        if(status == 1 || status == 2){
          arr.push({name: '撤销'});
        }
      }
      if(permission.event_whole_beizhu){
        arr.push({name: '备注'});
      }
      if(permission.event_whole_delete){
        arr.push({name: '删除'});
      }
      
      this.setData({
          show: true,
          actions: arr,
          rowId: id,
      });
    },
    onClose() {
        this.setData({
            show: false
        });
    },
    onSelect(event) {
        let that = this;
        //console.log(event.detail);
        if (event.detail.name == "重开") {
            this.setData({
                showdia: true
            });

        } else if (event.detail.name == "备注") {
            this.setData({
                showdia1: true
            });

        } else if (event.detail.name == "撤销") {
            this.setData({
                showdia2: true
            });
        } else if (event.detail.name == "删除") {
            let id = event.currentTarget.dataset.id;
            if(id == ''){
                wx.showToast({
                    title: '请选择要删除的事件',
                    icon: 'none',
                    duration: 2000
                })
                return;
            }
            Dialog1.confirm({
                title: '提示',
                message: '确定要删除选中记录吗？',
            })
                .then(() => {
                    wx.request({
                        url: app.globalData.address + 'event/toDelete.do',
                        method: 'POST',
                        header: {
                            "Authorization": wx.getStorageSync('tokenValue'),
                            "Content-Type": "application/x-www-form-urlencoded"
                        },
                        data: {
                            "id": id,
                            "userId": wx.getStorageSync('userInfo').id,
                        },
                        success: function (res) {
                          console.log("删除结果:", res)
                            if (res.data.code == 0) {
                                wx.showToast({
                                    title: '删除成功',
                                    icon: 'success',
                                    duration: 2000
                                })
                                that.changtab1();
                                that.page();
                            } else {
                                wx.showToast({
                                    title: res.data.msg,
                                    icon: 'none',
                                    duration: 2000
                                })
                            }
                        }
                    })
                })
                .catch(() => {
                    // on cancel
                });

        }
    },
    // 查询来源
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
                        arr.push({id: rowData.id, name: rowData.resource_name});
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
    beizhu: function (options) {
        this.setData({
            showdia1: true
        });
    },
    onClose1(event) {
        let that = this;
        console.log("来源source:", that)
        that.setData({
            show1: false,
            shuoming: '',
        });
    },
    onCancel1(event) {
        let that = this;
        that.setData({
            show1: false,
            zhuangtai: '请选择',
        });
    },
    onSelect1(event) {
        let that = this;
        that.data.form.source_id = event.detail.id
        this.setData({
            zhuangtai: event.detail.name,
            // form: that.data.form,
        });
    },
    bindDateChange: function (e) { //开始时间
        this.data.form.billStartTime = e.detail.value;
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            startTime: e.detail.value
        })
    },
    bindDateChange2: function (e) { //结束时间
        this.data.form.billEndTime = e.detail.value;
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            endTime: e.detail.value
        })
    },
    //查看详情跳转
    chuli: function (e) {
        wx.navigateTo({
            url: 'detail/index?id=' + e.currentTarget.dataset.id,
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
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        wx.showLoading({
            title: '数据加载中...',
            icon: 'none',
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
        this.page(1);
        
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
            icon: 'none',
            duration: 1500
        })

        setTimeout(function () {
            // complete
            wx.hideNavigationBarLoading() //完成停止加载
            wx.stopPullDownRefresh() //停止下拉刷新
        }, 1500);
        that.data.pageIndex = 1;
        this.page(2)
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        console.log('上拉加载')
        //上拉加载下一页
        var that = this;
        that.data.pageIndex = that.data.pageIndex + 1;
        that.page();
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
    /**
     * 初始化分页查询
     */
    page: function (val) {
        var that = this;
        
        let params = {
          // "user_id": wx.getStorageSync('userInfo').id,
          // "user_type": 1,
          "id": that.data.form.id==undefined?'':that.data.form.id,
          "title": that.data.form.title==undefined?'':that.data.form.title,
          "billStartTime": that.data.form.billStartTime==undefined?'':that.data.form.billStartTime,
          "billEndTime": that.data.form.billEndTime==undefined?'':that.data.form.billEndTime,
          // "request_type": that.data.requestTypeId,
          "source_id": that.data.form.source_id==undefined?'':that.data.form.source_id,
          "pageIndex": 1,
          "pageSize": that.data.pageSize*that.data.pageIndex
        };
        console.log("初始化分页查询:", params)
        wx.request({
            url: app.globalData.address + 'event/toAllEvent.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: params,
            success: function (res) {
                console.log("初始化分页查询:",res)
                that.setData({
                    list: res.data.data.list,
                    pageIndex: res.data.data.pageNum,
                    pageSize: res.data.data.pageSize,
                    totalPage: res.data.data.total,
                    pages: res.data.data.pages,
                    showView: false,
                    form: {}
                })
                if (res.data.data.total < res.data.data.pageSize) {
                // if (res.data.data.list.length == 0) {
                    that.setData({
                        showView: true
                    })
                } else {
                    that.setData({
                        showView: false
                    })
                }
                //结束刷新
                if (val == 2) {
                    wx.stopPullDownRefresh();
                }
            }
        })
        //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
          wx.pageScrollTo({
            scrollTop: 0
        })
    },
    
})