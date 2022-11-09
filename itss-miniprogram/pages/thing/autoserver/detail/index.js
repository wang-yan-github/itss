// pages/thing/working/zhipai/index.js
var app = getApp();
import Dialog1 from '@vant/weapp/dialog/dialog';
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
        title: '资产详情',
        topHeight: 0,
        activeNames: ['1', '4'],
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
        stepsactive: 0,
        activetabs: 0,
        steps: [],
        row: {},
        showdia1: false,
        shuoming: '',
        id: '',
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
        this.setData({activetabs: event.detail});
        if(event.detail == 1){
            this.setData({
                showdia1: false,
                shuoming: '',
            });
        }
        console.log(event)
    },
    onClosedia1() {
        this.setData({
            showdia1: false,
            shuoming: '',
        });
    },
    //备注
    getUserInfo1(event) {
        var that = this;
        let id = that.data.id;
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
            },
            success: function (res) {
                if (res.data.code == 0) {
                    that.setData({
                        shuoming: '',
                    });
                    wx.showToast({
                        title: '备注成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.onLoad();
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
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log("--------------初始化资产盘点数据--options---------------");
        console.log(options);
        // let query = wx.createSelectorQuery();
        let that = this;
        // query.select("#footer").boundingClientRect()
        // query.exec(function (res) {
        //     //取高度
        //     // console.log(res[0].height);
        //     that.setData({
        //         topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
        //     });
        // })

        if(null == options || undefined == options || options.id == undefined){
            return;
        }
        let id = options.id;
        this.data.id = id;

        wx.request({
            url: app.globalData.address + 'event/getAssetsConfig.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "id": id
            },
            success: function (res) {
                console.log("--------------初始化资产盘点数据--res---------------");
                console.log(res);
                let obj = res.data.data;
                console.log(obj)
                that.setData({
                    row: obj,
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