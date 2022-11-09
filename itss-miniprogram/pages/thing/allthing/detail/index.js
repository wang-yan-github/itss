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
        title: '事件详情',
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
            url: app.globalData.address + 'event/getById',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/x-www-form-urlencoded"
            },
            data: {
                "id": id
            },
            success: function (res) {
                let obj = res.data.data.data;
                console.log(res.data.data.data);
                console.log(that);
                that.setData({
                    row: obj,
                })

                //时间信息
                //变更类型1.待响应2.处理中  3暂停 4.处理完成5.待评价6.已关闭7.已撤销
                that.data.steps = [];
                that.data.steps.push({text: "待响应", desc: obj.create_time == null ? "" : obj.create_time});
                that.data.steps.push({text: "处理中", desc: obj.processTime == null ? "" : obj.processTime});
                that.data.steps.push({text: "暂停", desc: obj.pauseTime == null ? "" : obj.pauseTime});
                that.data.steps.push({text: "已处理", desc: obj.processTime == null ? "" : obj.processTime});
                that.data.steps.push({text: "待评价", desc: obj.processTime == null ? "" : obj.processTime});
                that.data.steps.push({text: "已关闭", desc: obj.closeTime == null ? "" : obj.closeTime});
                that.data.steps.push({text: "已撤销", desc: obj.revokeTime == null ? "" : obj.revokeTime});
                if (obj.status == '1') {
                    that.data.stepsactive = 1;
                } else if (obj.status == '2') {
                    that.data.stepsactive = 2;
                } else if (obj.status == '3') {
                    that.data.stepsactive = 3;
                } else if (obj.status == '4') {
                    that.data.stepsactive = 4;
                } else if (obj.status == '5') {
                    that.data.stepsactive = 5;
                } else if (obj.status == '6') {
                    that.data.stepsactive = 6;
                } else if (obj.status == '7') {
                    that.data.stepsactive = 7;
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