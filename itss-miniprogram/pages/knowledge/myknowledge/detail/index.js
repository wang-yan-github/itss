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
        title: '知识详情',
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
          knowledge: {},

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
        let query = wx.createSelectorQuery();
        let that = this;
        query.select("#footer").boundingClientRect()
        query.exec(function (res) {
            //取高度
            console.log(res[0].height);
            that.setData({
                topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
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

    }
})