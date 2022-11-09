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
        title: '发布详情',
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
              text: '待评审',
              desc: '07-15 08:00',              
              activeIcon: 'checked',
            },
            {
              text: '待测试',
              desc: '',          
              
            },
            {
              text: '待发布',
              desc: '',              
              
            },
            {
              text: '发布成功',
              desc: '',              
              
            },
          ],
          publish: {},

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
        });
        var beanParam = {};
        console.log(options.id);
        beanParam.id = options.id;
        console.log(beanParam)
        wx.request({
            url: app.globalData.address + 'publishInfo/getPublishBasic.do',
            method: 'POST',
            header: {
              "Authorization":wx.getStorageSync('tokenValue'),
              "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
              "id": options.id,
              "userId": wx.getStorageSync('userInfo').id,
            },
            success: function (res) {
                let obj = res.data.data
              that.setData({
                publish: res.data.data,
              })
              console.log(obj);
              that.data.steps = [];
              that.data.steps.push({text: "待评审", desc:  "" });
              that.data.steps.push({text: "待测试", desc: obj.test_time == null ? "" : obj.test_time});
              that.data.steps.push({text: "待发布", desc: obj.publish_time == null ? "" : obj.publish_time});
              that.data.steps.push({text: "发布成功", desc: obj.publish_time == null ? "" : obj.publish_time});
              if (obj.status == '1') {
                that.data.stepsactive = 1;
            } else if (obj.status == '3') {
                that.data.stepsactive = 2;
            } else if (obj.status == '4') {
                that.data.stepsactive = 3;
            } else if (obj.status == '5') {
                that.data.stepsactive = 4;
            } 
            that.setData({
                steps: that.data.steps,
                stepsactive: that.data.stepsactive - 1,
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