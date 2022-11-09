// pages/thing/working/zhipai/index.js
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
        title: '打分',
        topHeight: 0,
        activeNames: ['1','4'],
        fileList: [
            {
              url: 'https://img.yzcdn.cn/vant/leaf.jpg',
              name: '图片1',
            },
            // Uploader 根据文件后缀来判断是否为图片文件
            // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
            {
              url: 'http://iph.href.lu/60x60?text=default',
              name: '图片2',
              isImage: true,
              deletable: true,
            },
          ],
          stepsactive:0,
          activetabs:0,
          steps: [
            {
              text: '待响应',
              desc: '07-15 08:00',              
              activeIcon: 'checked',
            },
            {
              text: '处理中',
              desc: '',          
              
            },
            {
              text: '暂停',
              desc: '',              
              
            },
            {
              text: '已处理',
              desc: '',              
              
            },
            {
                text: '待评价',
                desc: '',              
                
              },
          ],
          id: '',
          point: '',
          remark: '',
          knowledge: {},
          type: '',

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
    onChangetabs(event) {
        // event.detail 的值为当前选中项的索引
        this.setData({ activetabs: event.detail });
      },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
       console.log(options.type)
        let query = wx.createSelectorQuery();
        let that = this;
        query.select("#footer").boundingClientRect()
        query.exec(function (res) {
            //取高度
            console.log(res[0].height);
            that.setData({
                topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
                id: options.id,
                type: options.type,
            });
        })
        console.log(options.id);
        wx.request({
            url: app.globalData.address + 'knowledge/getKnowledgeInfoById.do',
            method: 'POST',
            header: {
              "Authorization":wx.getStorageSync('tokenValue'),
              "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
              "id": options.id
            },
            success: function (res) {
                let obj = res.data.data
                console.log(res.data.data);
              that.setData({
                knowledge: res.data.data,
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
    remark: function(e){
        var that = this;
        that.setData({
          remark: e.detail,
        })
      },
      getPoint: function(e){
          if(!(/(^[0-9]*$)/.test(e.detail))){
            wx.showToast({
                title: "请填写正整数",
                icon: 'error',
                duration: 2000
            })
            return ;

          }
        //   console.log(e);
        //   console.log(e.detail)
        var that = this;
        that.setData({
          point: e.detail,
        })
      },
      onClosepopup() {
        var that = this;
        if(that.data.type == null || that.data.type == undefined || that.data.type == ''){
            wx.navigateTo({
                url: '../index'
              })
        }else {
            wx.navigateTo({
                url: '../../manageknowledge/index'
              })
        }
       
     },
     onOpenpopup(){
        var that = this;
        console.log(that.data.remark);
        console.log(that.data.id);
        console.log(that.data.point);
        if(that.data.remark == null || that.data.remark == ''){
            wx.showToast({
                title: "请填写评语",
                icon: 'error',
                duration: 2000
            })
            return ;
        }
        if(that.data.point == null || that.data.point == ''){
            wx.showToast({
                title: "请填写贡献分",
                icon: 'error',
                duration: 2000
            })
            return ;
        }
        if(that.data.point < 1 || that.data.point > 10){
            wx.showToast({
                title: "分值在1-10之间",
                icon: 'error',
                duration: 2000
            })
            return ;
        }

        wx.request({
            url: app.globalData.address + 'knowledge/getPoint.do',
            method: 'POST',
            header: {
              "Authorization":wx.getStorageSync('tokenValue'),
              "Content-Type": "application/json"
            },
            data: {
              "knowledge_info_id": that.data.id,
              "comment": that.data.remark,
              "score": that.data.point,
              "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                if(res.data.code == 0){
                    wx.showToast({
                        title: '评分成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.backTo();
                    // that.onClosepopup();
                  
                }else {
                    wx.showToast({
                        title: res.data.msg,
                        icon: 'error',
                        duration: 2000
                    })
                }
            
            }
          })
    






        
     }
})