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
        title: '资产盘点管理',
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
        zhuangtai: '去选择',
        username: '去选择',
        actions: [           
            {
                name: '确认',
            },
            {
                name: '处理',
            },            
            {
                name: '删除',
            },           
        ],
        actions1: [{
                name: '固定资产项目',
            },
            {
                name: '智慧后勤项目',
            },
            {
                name: '科教平台项目',
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
        inventoryList: [],
        inventoryNameList: [],
        inventory_name: '请选择',
        inventory_index: '',
        showMZView: false,
        authority: {},
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
            inventory_name: '请选择',
            inventory_index: '',
        });
        this.page();
    },
    onOpenpopup(){
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
        if (event.detail.name == "确认") {
            wx.navigateTo({
              url: 'shenhe/index',
            })

        }else if (event.detail.name == "处理") {
            wx.navigateTo({
                url: 'chuli/index',
              })

        }else if (event.detail.name == "删除") {
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
            show1: false
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
            startTime: e.detail.value
        })
    },
    bindDateChange2: function (e) { //结束时间
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.setData({
            endTime: e.detail.value
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
        that.getAllInventory();
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
            url: app.globalData.address + 'assetsManage/toInventoryList.do',
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
        var that = this;
        wx.showLoading({
            title: '数据加载中...',
            icon: 'loading',
            duration: 1500
        })

        var inventory_id = null;
        for(var i = 0 ; i< that.data.inventoryList.length ; i++){
            if(that.data.inventory_index == i && that.data.inventoryList[i].name == that.data.inventory_name){
                inventory_id = that.data.inventoryList[i].id;
            }
        }



        wx.request({
            url: app.globalData.address + 'assetsManage/toInventoryList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": 1,
                "pageSize": that.data.pageSize,
                "am_assets_name": that.data.queryForm.title,
                "inventory_id": inventory_id==null?"":inventory_id,
                // "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                that.setData({
                    list: [],
                })
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

        var inventory_id = null;
        for(var i = 0 ; i< that.data.inventoryList.length ; i++){
            if(that.data.inventory_index == i && that.data.inventoryList[i].name == that.data.inventory_name){
                inventory_id = that.data.inventoryList[i].id;
            }
        }



        wx.request({
            url: app.globalData.address + 'assetsManage/toInventoryList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": that.data.pageIndex + 1,
                "pageSize": that.data.pageSize,
                "am_assets_name": that.data.queryForm.title,
                "inventory_id": inventory_id==null?"":inventory_id,
                // "userId": wx.getStorageSync('userInfo').id,
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
    seeView: function (e) {
        var id = e.currentTarget.dataset.id;
        console.log(id);
        wx.navigateTo({
            url: 'detail/index?id=' + id,
        })
    },
    makeSure: function (e){
        var id = e.currentTarget.dataset.id;
        console.log(id);
        wx.navigateTo({
            url: 'shenhe/index?id=' + id,
        })
    },
    makeDeal: function (e){
        var id = e.currentTarget.dataset.id;
        console.log(id);
        wx.navigateTo({
            url: 'chuli/index?id=' + id,
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

        var inventory_id = null;
        for(var i = 0 ; i< that.data.inventoryList.length ; i++){
            if(that.data.inventory_index == i && that.data.inventoryList[i].name == that.data.inventory_name){
                inventory_id = that.data.inventoryList[i].id;
            }
        }



        wx.request({
            url: app.globalData.address + 'assetsManage/toInventoryList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": 1,
                "pageSize": 10,
                "am_assets_name": that.data.queryForm.title,
                "inventory_id": inventory_id==null?"":inventory_id,
                // "userId": wx.getStorageSync('userInfo').id,
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
      getAllInventory:function (){
        var that = this;
        wx.request({
            url: app.globalData.address + 'assetsManage/getAllInventory.do',
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
                    let name = list[i].name;
                    listName.push(name);
                } 
                console.log(listName);
                that.setData({
                    inventoryList: list,
                    inventoryNameList: listName,
                })
            }
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
          show1: false,
          inventory_name: value,
          inventory_index: index,
        });
      },
      onCancel() {
        this.setData({
          show1: false,
          inventory_name: '请选择',
          inventory_index: '',
        });
      },
})