// pages/thing/working/chuli/index.js
import Toast from '@vant/weapp/toast/toast';
//app引用
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
        title: '转办',
        topHeight: 0,
        activeNames: ['4'],
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
        columns: [],
        detailsId: '', //获取当前id
        form: '', //对象
        selectName: '请选择', //转办人
        remarks: '', //备注
        trans_form_user: '', //用户id
        stepsactive:0,
        stepData: []
    },
    remarks: function (e) { //备注
        var that = this;
        that.setData({
            remarks: e.detail,
        })
    },
    convertHtmlToText: function (inputText) {
      var returnText = "" + inputText;
      returnText = returnText.replace(/<\/div>/ig, '\r\n');
      returnText = returnText.replace(/<\/li>/ig, '\r\n');
      returnText = returnText.replace(/<li>/ig, ' * ');
      returnText = returnText.replace(/<\/ul>/ig, '\r\n');
      //-- remove BR tags and replace them with line break
      returnText = returnText.replace(/<br\s*[\/]?>/gi, "\r\n");
    
      //-- remove P and A tags but preserve what's inside of them
      returnText=returnText.replace(/<p.*?>/gi, "\r\n");
      returnText=returnText.replace(/<a.*href="(.*?)".*>(.*?)<\/a>/gi, " $2 ($1)");
    
      //-- remove all inside SCRIPT and STYLE tags
      returnText=returnText.replace(/<script.*>[\w\W]{1,}(.*?)[\w\W]{1,}<\/script>/gi, "");
      returnText=returnText.replace(/<style.*>[\w\W]{1,}(.*?)[\w\W]{1,}<\/style>/gi, "");
      //-- remove all else
      returnText=returnText.replace(/<(?:.|\s)*?>/g, "");
    
      //-- get rid of more than 2 multiple line breaks:
      returnText=returnText.replace(/(?:(?:\r\n|\r|\n)\s*){2,}/gim, "\r\n\r\n");
    
      //-- get rid of more than 2 spaces:
      returnText = returnText.replace(/ +(?= )/g,'');
    
      //-- get rid of html-encoded characters:
      returnText=returnText.replace(/&nbsp;/gi," ");
      returnText=returnText.replace(/&amp;/gi,"&");
      returnText=returnText.replace(/&quot;/gi,'"');
      returnText=returnText.replace(/&lt;/gi,'<');
      returnText=returnText.replace(/&gt;/gi,'>');
    
      return returnText;
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
    },
    showjiejue(event) {
        this.setData({
            show: true,
        });
    },
    onConfirm(event) {
        var that = this;
        const {
            picker,
            value,
            index
        } = event.detail;
        //给下拉框赋值
        this.setData({
            selectName: event.detail.value.text
        });
        this.data.trans_form_user = event.detail.value.keys; //取选择的值
        this.setData({
            show: false,
        });
    },

    onCancel() {
        this.setData({
            show: false,
            selectName: '请选择'
        });
    },
    quxiao() { //取消
        this.setData({
            show: false,
            selectName: '请选择',
            remarks: '',
            trans_form_user: ''
        });
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


        //获取当前指定用户列表信息
        wx.request({
            url: app.globalData.address + 'question/upUserData.do',
            method: 'POST',
            header: {
                "Authorization": wx.getStorageSync('tokenValue'),
                "Content-Type": "application/json"
            },
            success: function (res) {
                var list = res.data.data;
                for (var i = 0; i < list.length; i++) {
                    that.data.columns.push({
                        text: list[i].name,
                        keys: list[i].id
                    })
                }
                that.setData({
                    columns: that.data.columns,
                })
            }
        })
        var id = options.id;
        that.data.detailsId = id;
        //获取转办信息
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
                                text: item.approve_user_name + "审批",
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
                                text: item.approve_user_name + "审批",
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
                })
                // that.convertHtmlToText(that.form.changeInfo.change_reason)
                // if (res.data.code == 0) {
                //     that.data.form = res.data.data;
                //     console.log(that.data.form)
                // }
                // that.setData({
                //     form: that.data.form,
                // })
            }
        })
    },
    //保存功能
    saveClick: function () {
        var that = this;
        if (null == this.data.trans_form_user || undefined == this.data.trans_form_user || '' == this.data.trans_form_user) {
            wx.showToast({
                title: '请选择转办人',
                icon: 'error',
                duration: 2000
            })
            return;
        }
        // else if (null == this.data.remarks || undefined == this.data.remarks || '' == this.data.remarks) {
        //   wx.showToast({
        //     title: '请输入备注',
        //     icon: 'error',
        //     duration: 2000
        //   })
        //   return;
        // } 
        else {
            this.data.form.changeInfo.remarks = this.data.remarks;
            wx.request({
                url: app.globalData.address + 'change/toTransForm.do',
                method: 'POST',
                header: {
                    "Authorization": wx.getStorageSync('tokenValue'),
                    "Content-Type": "application/json"
                },
                data: {
                    "id": this.data.detailsId,
                    "userId": wx.getStorageSync('userInfo').id,
                    "change_type": this.data.form.change_type,
                    "changeNode": this.data.form.changeNode,
                    "changeInfo": this.data.form.changeInfo,
                    "trans_form_user": this.data.trans_form_user,
                },
                success: function (res) {
                    if (res.data.code == 0) {
                        wx.showToast({
                            title: '转单成功',
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
        }

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