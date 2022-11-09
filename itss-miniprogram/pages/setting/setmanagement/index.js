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
        title: '配置项管理',
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
                firstLetter: "",
                list: [],
            },
            {
                firstLetter: "",
                list: [],
            },
            {
                firstLetter: "",
                list: [],
            },
            {
                firstLetter: "",
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
        actions1: [],
        chulichecked: false,
        list: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: '',
        pages: '',
        queryForm: {
            title: '',
        },
        typeList: [],
        typeNameList: [],
        childList: [],
        childNameList: [],
        scopeList: [],
        scopeNameList: [],
        type_name: '请选择',
        type_index: '',
        child_name: '请选择',
        child_index: '',
        scope_name: '请选择',
        scope_index: '',
        showMZView: false,
        authority: {},
    },
    inputedit(e){
      this.setData({
        'queryForm.title': e.detail.value
      })
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
            type_name: '请选择',
            type_index: '',
            child_name: '请选择',
            child_index: '',
            scope_name: '请选择',
            scope_index: '',
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
        if (event.detail.name == "历史记录") {
          wx.navigateTo({
            url: 'history/index',
        })

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
    showaction3: function (options) {
        this.setData({
            show3: true
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
        this.setData({
            zhuangtai: event.detail.name
        });
    },
    bindDateChange: function (e) { //开始时间
        this.setData({
            startTime: e.detail.value
        })
    },
    bindDateChange2: function (e) { //结束时间
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
            url: '../add/index',
        })
    },
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
            that.setData({
                topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
                searchHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight')
            });
        })
        this.getType();
        this.getChild();
        this.getScope();
    },

    /**
     * 获取所有配置项类型
     */
    getType: function(){
        var that = this;
        wx.request({
            url: app.globalData.address + 'assetsManage/getAllType.do',
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
                that.setData({
                    typeList: list,
                    typeNameList: listName,
                })
            }
        })
    },


    /**
     * 获取所有配置项子类
     */
    getChild: function(){
        var that = this;
        wx.request({
            url: app.globalData.address + 'assetsManage/getAllChild.do',
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
                that.setData({
                    childList: list,
                    childNameList: listName,
                })
            }
        })
    },

    /**
     * 获取所有配置项使用范围
     */
    getScope: function(){
        var that = this;
        wx.request({
            url: app.globalData.address + 'assetsManage/getAllScope.do',
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
                    let name = list[i].scope_name;
                    listName.push(name);
                } 
                that.setData({
                    scopeList: list,
                    scopeNameList: listName,
                })
            }
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
            url: app.globalData.address + 'assetsManage/toList.do',
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

        var assets_type_id = null;

        for(var i = 0 ; i< that.data.typeList.length ; i++){
            if(that.data.type_index == i && that.data.typeList[i].name == that.data.type_name){
                assets_type_id = that.data.typeList[i].id;
            }
        }
       
        var child_asset = null;
        for(var i = 0 ; i< that.data.childList.length ; i++){
            if(that.data.child_index == i && that.data.childList[i].name == that.data.child_name){
                child_asset = that.data.childList[i].id;
            }
        }
        

        var use_scope_id = null ; 
        for(var i = 0 ; i< that.data.scopeList.length ; i++){
            if(that.data.scope_index == i && that.data.scopeList[i].scope_name == that.data.scope_name){
                use_scope_id = that.data.scopeList[i].id;
            }
        }

     

        wx.request({
            url: app.globalData.address + 'assetsManage/toList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": 1,
                "pageSize": that.data.pageSize,
                "assets_name": that.data.queryForm.title,
                "assets_type_id": assets_type_id==null ?"":assets_type_id,
                "child_asset": child_asset==null?"":child_asset,
                "use_scope_id": use_scope_id==null?"":use_scope_id,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                that.setData({
                    list: [],
                });
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

        var assets_type_id = null;

        for(var i = 0 ; i< that.data.typeList.length ; i++){
            if(that.data.type_index == i && that.data.typeList[i].name == that.data.type_name){
                assets_type_id = that.data.typeList[i].id;
            }
        }
       
        var child_asset = null;
        for(var i = 0 ; i< that.data.childList.length ; i++){
            if(that.data.child_index == i && that.data.childList[i].name == that.data.child_name){
                child_asset = that.data.childList[i].id;
            }
        }
        

        var use_scope_id = null ; 
        for(var i = 0 ; i< that.data.scopeList.length ; i++){
            if(that.data.scope_index == i && that.data.scopeList[i].scope_name == that.data.scope_name){
                use_scope_id = that.data.scopeList[i].id;
            }
        }

     

        wx.request({
            url: app.globalData.address + 'assetsManage/toList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageNo": that.data.pageIndex + 1,
                "pageSize": that.data.pageSize,
                "assets_name": that.data.queryForm.title,
                "assets_type_id": assets_type_id==null ?"":assets_type_id,
                "child_asset": child_asset==null?"":child_asset,
                "use_scope_id": use_scope_id==null?"":use_scope_id,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
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
        wx.navigateTo({
            url: 'detail/index?id=' + id,
        })
    },
    deleteOne: function(e) {
        var id = e.currentTarget.dataset.id;
        var that = this;

        wx.request({
            url: app.globalData.address + 'assetsManage/toDel.do',
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
    history: function(e){
        var id = e.currentTarget.dataset.id;
        wx.navigateTo({
            url: 'history/index?id=' + id,
        })

    },
    reloadPage: function(e){
        var that = this;
        wx.request({
            url: app.globalData.address + 'assetsManage/toList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": 1,
                "pageSize": 10,
                "beanParam": null,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
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
       this.data.queryForm.title= e.detail.value;
   },
   // 默认-关键字搜索
   publishSearch: function (e) {
      this.setData({
         showselect: false
       });
     this.page();
    },
    onOpenpopup(){
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

        var assets_type_id = null;

        for(var i = 0 ; i< that.data.typeList.length ; i++){
            if(that.data.type_index == i && that.data.typeList[i].name == that.data.type_name){
                assets_type_id = that.data.typeList[i].id;
            }
        }
       
        var child_asset = null;
        for(var i = 0 ; i< that.data.childList.length ; i++){
            if(that.data.child_index == i && that.data.childList[i].name == that.data.child_name){
                child_asset = that.data.childList[i].id;
            }
        }

        var use_scope_id = null ; 
        for(var i = 0 ; i< that.data.scopeList.length ; i++){
            if(that.data.scope_index == i && that.data.scopeList[i].scope_name == that.data.scope_name){
                use_scope_id = that.data.scopeList[i].id;
            }
        }
        wx.request({
            url: app.globalData.address + 'assetsManage/toList.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "pageIndex": 1,
                "pageSize": 10,
                "assets_name": that.data.queryForm.title,
                "assets_type_id": assets_type_id==null ?"":assets_type_id,
                "child_asset": child_asset==null?"":child_asset,
                "use_scope_id": use_scope_id==null?"":use_scope_id,
                "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
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
      onChangejieju2(event) {
        const {
          picker,
          value,
          index
        } = event.detail;
        // Toast(`当前值：${value}, 当前索引：${index}`);
      },
      onChangejieju3(event) {
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
        
        that.setData({
          show1: false,
          type_name: value,
          type_index: index,
        });
      },
      onConfirm2(event) {
        var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        
        that.setData({
          show2: false,
          child_name: value,
          child_index: index,
        });
      },
      onConfirm3(event) {
        var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        
        that.setData({
          show3: false,
          scope_name: value,
          scope_index: index,
        });
      },
      onCancel() {
        this.setData({
          show1: false,
          type_name: '请选择',
          type_index: '',
        });
        // Toast('取消');
      },
      onCancel2() {
        this.setData({
          show2: false,
          child_name: '请选择',
          child_index: '',
        });
        // Toast('取消');
      },
      onCancel3() {
        this.setData({
          show3: false,
          scope_name: '请选择',
          scope_index: '',
        });
        // Toast('取消');
      },

})