// pages/thing/working/index.js

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
        title: '添加关联的事件',
        selected: true,
        selected1: false,
        selected2: false,
        selected3: false,
        itemselected: false,
        topHeight: 0,
        searchHeight: 0,
        scrollTop: 0,
        chulichecked: false,
        list: [],
        first_ids: [],
        pageIndex:1,
        pageSize: 10,
        first_id: [],
        first_value: [],
        queryForm: {
          title:'',
        },
        oneData:{},
        totalPage: 0,
        pages: '',
    },
    
    //选中状态切换	
    onChangechuli(event) {
        var that = this;
        console.log("选中状态切换-onChangechuli:",event)
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
        // this.setData({
        //     chulichecked: event.detail,
        //     itemselected: !that.data.itemselected,
        // });
    },
    itemonchange(event) {
        var that = this;
        let row = event.currentTarget.dataset.ischecked;
        console.log(row);
        let first_ids = that.data.first_ids;
        let first_value = that.data.first_value;
        //判断checked是否为undefined，如果为undefined，则默认为false，否则为true

        //判断first_ids是否包含Id
        var count = 0 ;
        var index = -1;
        for(var i=0 ; i < first_ids.length ; i++){
            if(first_ids[i] == row.id){
                count++;
                index = i;
            }
        }
        if(count > 0){
            first_ids.splice(index,1);
            first_value.splice(index,1);

        }else{
            first_ids.push(row.id);
            first_value.push(row);
        }
        that.setData({
            first_ids: first_ids,
            first_value: first_value,
        })
        console.log(this.data.first_ids);
        console.log(this.data.first_value);




        // if (first_ids.findIndex(o => o == row.id) != -1) {
        //     first_ids.splice(first_ids.findIndex(o => o == row.id), 1);
        //     that.setData({
        //         first_ids: first_ids,
        //     })
        // } else {
        //     first_ids.push(row.id)
        //     that.setData({
        //         first_ids: first_ids,
        //     })
        // }
       
        
    },
    
    // 返回上层
    backTo() {
        wx.navigateBack({
            delta: 0,
        })
    },
    
    showaction: function (options) {
        this.setData({
            show: true
        });
    },
    
    chuli: function (e) {
        wx.navigateTo({
            url: 'detail/index',
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
        query.select("#searchs").boundingClientRect()
        query.exec(function (res) {
            //取高度
            console.log(res[0].height);
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
      wx.showLoading({
        title: '数据加载中...',
        icon: 'loading',
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
      this.page();
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
      //下拉刷新
      wx.showLoading({
        title: '正在刷新...',
        icon: 'loading',
        duration: 1500
      })

      setTimeout(function () {
        // complete
        wx.hideNavigationBarLoading() //完成停止加载
        wx.stopPullDownRefresh() //停止下拉刷新
      }, 1500);
      var that = this;
      that.data.pageIndex = 1;
      that.data.pageSize = 10;

      this.page();
      //2. 切换页面时 滚动条滚回到顶部 切换页面时调用API
      wx.pageScrollTo({
        scrollTop: 0
      })

      //结束刷新
      wx.stopPullDownRefresh();
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
      //上拉加载下一页
      var that = this;
      console.log("页面上拉触底事件的处理函数:",that);
      if (that.data.pageIndex < that.data.pages) {
        wx.showLoading({
          title: '数据加载中...',
          icon: 'loading',
          duration: 1500
        })
      }
      that.data.pageIndex = that.data.pageIndex + 1;
      this.page();
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
    inputedit(e){
      this.data.queryForm.title = e.detail.value;
    //   this.setData({
    //     'queryForm.title': e.detail.value
    //   })
    },
    // 默认-关键字搜索
    publishSearch: function (e) {
      this.page();
    },

    // 配置项list查询
    page: function (val) {
      var that = this;
      let params = {
        "pageIndex": 1,
        "pageSize": that.data.pageSize == undefined ? 10 : that.data.pageSize*that.data.pageIndex,
        "status": '4,5,6',
        "title" : that.data.queryForm.title,
      };
      console.log("that.data.pageIndex: " , that.data.pageIndex)
      console.log("配置项list查询: ", params)
      wx.request({
        url: app.globalData.address + 'event/toAllEvent.do',
        method: 'POST',
        header: {
            "Authorization": wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
        },
        data: params,
        success: function (res) {
          console.log("事件list查询:", res)

          if (res.data.code == 0) {
            if (res.data.data.list.length == 0) {
              that.setData({
                list: [],
                totalPage: 0,
              })
            } else {
              that.setData({
                list: res.data.data.list,
                pageIndex: res.data.data.pageNum,
                pageSize: res.data.data.pageSize,
                totalPage: res.data.data.total,
                pages: res.data.data.pages,
              
              })
            }
          } else {
            wx.showToast({
              title: res.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
          //结束刷新
          if (val == 2) {
            wx.stopPullDownRefresh();
          }
        }
      })
    },
    onClosepopup: function (val) {
      console.log("确定操作:", val)
      wx.setStorageSync('eventList', this.data.first_value);
      console.log(wx.getStorageSync('eventList'))
    //   var pages = getCurrentPages();
    //   // 上一个编辑页面
    //   var prevPage = pages[pages.length - 2];
    //   // 直接低啊用上一个页面的setData(),把数据存到上个页面既编辑款项页面中去
    //   prevPage.setData({
    //     pzx: val.currentTarget.dataset.name,
    //     pzxData: val.currentTarget.dataset
    //   })
      this.backTo();
    }
})