// pages/thing/working/chuli/index.js
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
        title: '制定方案',
        topHeight: 0,
        activeNames: ['5'],
        show: false,
        fileList: [{
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
        columns: ['杭州', '宁波', '温州', '嘉兴', '湖州'],
        change_programme: '', //变更方案
        fallback_plan: '', //回退计划
        risk_assessment: '', //风险评估
        form: '', //对象
        stepData: '',
        stepsactive: 0,
        id: ''
    },
    /**   * 初始化富文本框   */
    onEditorReady: async function () {
        const that = this;
        setTimeout(() => {
            wx.createSelectorQuery().select('#editor').context(function (res) {
                that.editorCtx = res.context
                that.editorCtx.setContents({
                    html: that.data.change_programme
                })
            }).exec()
            wx.createSelectorQuery().select('#editor2').context(function (res) {
                that.editorCtx = res.context
                that.editorCtx.setContents({
                    html: that.data.fallback_plan
                })
            }).exec()
            wx.createSelectorQuery().select('#editor3').context(function (res) {
                that.editorCtx = res.context
                that.editorCtx.setContents({
                    html: that.data.risk_assessment
                })
            }).exec()
        }, 500)
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
    onChangejieju(event) {
        const {
            picker,
            value,
            index
        } = event.detail;
        Toast(`当前值：${value}, 当前索引：${index}`);
    },
    showjiejue(event) {
        this.setData({
            show: true,
        });
    },
    onConfirm(event) {
        const {
            picker,
            value,
            index
        } = event.detail;
        Toast(`当前值：${value}, 当前索引：${index}`);
        this.setData({
            show: false,
        });
    },

    onCancel() {
        this.setData({
            show: false,
        });
        Toast('取消');
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

        //获取当前详情数据
        if (null == options || undefined == options || options.id == undefined) {
            return;
        }
        let id = options.id;
        that.data.id = id;
        wx.request({
            url: app.globalData.address + 'change/getChange.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/json"
            },
            data: {
                "id": id
            },
            success: function (res) {
                console.log(res.data.data.changeProgrammeDraft)
                let obj = res.data.data;
                that.data.stepData = [];
                if (obj.change_type == "1") {
                    obj.changeNodes.forEach((item, i) => {
                        //紧急 1.开单 2.审批 3.实施 4.实施后评审 5.关单
                        if (item.time_nodes == "1") {
                            that.data.stepData.push({
                                text: "开单",
                                desc: item.create_time == null ? "" : item.create_time
                            });
                        } else if (item.time_nodes == "2") {
                            that.data.stepData.push({
                                text: "审批",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "5") {
                            that.data.stepData.push({
                                text: "实施",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "6") {
                            that.data.stepData.push({
                                text: "实施后评审",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "7") {
                            that.data.stepData.push({
                                text: "关单",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        }
                    });
                } else {
                    //一般、重大时间轴节点 1.开单 2.审批 3.制定方案 4.方案审核 5.实施 6.实施后评审 7.关单 出现多节点的情况下用sort排序
                    obj.changeNodes.forEach((item, i) => {
                        if (item.time_nodes == "1") {
                            that.data.stepData.push({
                                text: "开单",
                                desc: item.create_time == null ? "" : item.create_time
                            });
                        } else if (item.time_nodes == "2") {
                            that.data.stepData.push({
                                text: "审批",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "3") {
                            that.data.stepData.push({
                                text: "制定方案",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "4") {
                            that.data.stepData.push({
                                text: "方案审核",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "5") {
                            that.data.stepData.push({
                                text: "实施",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "6") {
                            that.data.stepData.push({
                                text: "实施后评审",
                                desc: item.update_time == null ? "" : item.update_time
                            });
                        } else if (item.time_nodes == "7") {
                            that.data.stepData.push({
                                text: "关单",
                                desc: item.update_time == null ? "" : item.update_timee
                            });
                        }
                    });
                }
                that.data.stepData.forEach(function (item) {
                    if ('' != item.desc) {
                        that.data.stepsactive = that.data.stepsactive + 1;
                    }
                });
                that.setData({
                    form: obj,
                    stepData: that.data.stepData,
                    stepsactive: that.data.stepsactive - 1,
                    change_programme: obj.changeProgrammeDraft.change_programme,
                    fallback_plan: obj.changeProgrammeDraft.fallback_plan,
                    risk_assessment: obj.changeProgrammeDraft.risk_assessment,
                })
            }
        })
    },
    approveRemark: function (e) {
        var that = this;
        that.setData({
            approve_remark: e.detail,
        })
    },
    saveClick: function (event) {
        var that = this;
        var status = event.currentTarget.dataset.id;
        that.data.form.changeNode.approve_status = status;
        that.data.form.changeProgrammeDraft.change_programme = that.data.change_programme;
        that.data.form.changeProgrammeDraft.fallback_plan = that.data.fallback_plan;
        that.data.form.changeProgrammeDraft.risk_assessment = that.data.risk_assessment;
        if (!that.data.change_programme) {
            wx.showToast({
                title: '请输入变更方案',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        if (!that.data.fallback_plan) {
            wx.showToast({
                title: '请输入回退计划',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        if (!that.data.risk_assessment) {
            wx.showToast({
                title: '请输入风险评估',
                icon: 'none',
                duration: 2000
            })
            return false;
        }
        wx.request({
            url: app.globalData.address + 'change/toProgramme.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/json"
            },
            data: {
                "id": that.data.id,
                "userId": wx.getStorageSync('userInfo').id,
                "changeNode": that.data.form.changeNode,
                "changeInfo": that.data.form.changeInfo,
                "changeProgrammeDraft": that.data.form.changeProgrammeDraft,
                "changeImplement": that.data.form.changeImplement,
                "changeProgrammeApprove": that.data.form.changeProgrammeApprove,
            },
            success: function (res) {
                if (res.data.code == 0) {
                    wx.showToast({
                        title: '操作成功',
                        icon: 'success',
                        duration: 2000
                    })
                    that.backTo();
                    // that.onClosepopup();
                } else {
                    wx.showToast({
                        title: res.data.msg,
                        icon: 'error',
                        duration: 2000
                    })
                }
            }
        })
    },
    changeProgramme: function (e) { //变更方案
        var that = this;
        that.setData({
            change_programme: e.detail.html,
        })
    },
    fallbackPlan: function (e) { //回退计划
        var that = this;
        that.setData({
            fallback_plan: e.detail.html,
        })
    },
    riskAssessment: function (e) { //风险评估
        var that = this;
        that.setData({
            risk_assessment: e.detail.html,
        })
    },
    onClosepopup() {
        wx.navigateTo({
            url: '../index'
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