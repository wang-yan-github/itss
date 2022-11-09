// pages/thing/working/zhipai/index.js
import Toast from '@vant/weapp/toast/toast';
var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
        columns: [],
        rColumns: [],
        columns1: [],
        rColumns1: [],
        show: false,
        show1: false,
        group_name: '请选择',
        group_index: '',
        deal_name: '请选择',
        deal_index: '',
        remark: '',
      event: {
        id: '',
        title: '',
        description: '',
        source_id: '',
        source_name: '',
        request_type: '',
        request_type_name: '',
        event_category_name: '',
        effect_degree: '',
        effect_degree_name: '',
        urgency_degree_id: '',
        urgency_degree_name: '',
        is_effect: '',
        priority_name: '',
      },
      id: '',
        // 状态栏高度
        statusBarHeight: wx.getStorageSync('statusBarHeight') + 'px',
        // 导航栏高度
        navigationBarHeight: wx.getStorageSync('navigationBarHeight') + 'px',
        // 胶囊按钮高度
        menuButtonHeight: wx.getStorageSync('menuButtonHeight') + 'px',
        // 导航栏和状态栏高度
        navigationBarAndStatusBarHeight: wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight') + 'px',
        title: '指派',
        topHeight: 0,
        activeNames: ['1','2'],
        fileList: [
            // {
            //   url: 'https://img.yzcdn.cn/vant/leaf.jpg',
            //   name: '图片1',
            // },
            // // Uploader 根据文件后缀来判断是否为图片文件
            // // 如果图片 URL 中不包含类型信息，可以添加 isImage 标记来声明
            // {
            //   url: 'http://iph.href.lu/60x60?text=default',
            //   name: '图片2',
            //   isImage: true,
            //   deletable: true,
            // },
          ],

    },
    onClosepopup() {
      wx.navigateTo({
        url: '../index'
    })
  },
    onOpenpopup() {
      var that = this;
      console.log(that.data.id);
      var service_groups_id = '';
      for(var i = 0 ; i< that.data.rColumns.length;i++){
        if(that.data.group_name == that.data.rColumns[i].name && that.data.group_index == i){
          service_groups_id = that.data.rColumns[i].id;
        }
      }
      console.log(service_groups_id);
      var handler = '';
      for(var i = 0 ; i< that.data.rColumns1.length;i++){
        if(that.data.deal_name == that.data.rColumns1[i].name && that.data.deal_index == i){
          handler = that.data.rColumns1[i].id;
        }
      }
      

      console.log(handler);
      var pic_ids = "";

      for(var i = 0 ; i < that.data.fileList.length; i++){
        var id = that.data.fileList[i].id;
        if(i==that.data.fileList.length-1){
            pic_ids+=id;
        }else{
            pic_ids+=id+",";
        }
      }
      console.log(pic_ids);

      wx.request({
        url: app.globalData.address + 'event/designate',
        method: 'POST',
        header: {
          "Authorization":wx.getStorageSync('tokenValue'),
          "Content-Type": "application/json"
        },
        data: {
          "id": that.data.id,
          "service_groups_id": service_groups_id,
          "handler": handler,
          "appointRemark": that.data.remark,
          "fileIds": pic_ids,
          "userId": wx.getStorageSync('userInfo').id,
        },
        success: function (res) {
          if(res.data.code == 0){
            wx.showToast({
                title: '指派成功',
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
                id: options.id,
            });
        })

        wx.request({
          url: app.globalData.address + 'event/getById',
          method: 'POST',
          header: {
            "Authorization":wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            "id": options.id
          },
          success: function (res) {
            console.log(res.data.data.data);
            that.setData({
              event: res.data.data.data,
            })
          }
        })
        that.getAllManageInfo();
        that.getAllUser();   
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
    getAllManageInfo: function(e){
        var that = this;
        wx.request({
          url: app.globalData.address + 'event/getAllManageInfo',
          method: 'POST',
          header: {
            "Authorization":wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            
          },
          success: function (res) {
            let x = [];
            for(var i = 0 ; i<res.data.data.length; i++ ){
              x.push(res.data.data[i].name)
            }
            console.log(x);
            that.setData({
              columns: x,
              rColumns: res.data.data,
            })
          }
        })
        console.log(that.data.columns);
        console.log(that.data.rColumns);
       },
       getAllUser: function(e){
        var that = this;
        wx.request({
          url: app.globalData.address + 'event/getAllUser',
          method: 'POST',
          header: {
            "Authorization":wx.getStorageSync('tokenValue'),
            "Content-Type": "application/x-www-form-urlencoded"
          },
          data: {
            
          },
          success: function (res) {
            console.log(res.data.data)
            let x = [];
            for(var i = 0 ; i<res.data.data.length; i++ ){
              x.push(res.data.data[i].name)
            }
            console.log(x);
            that.setData({
              columns1: x,
              rColumns1: res.data.data,
            })
          }
        })
        console.log(that.data.columns1);
        console.log(that.data.rColumns1);
       },
       showjiejue(event) {
        this.setData({
          show: true,
        });
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
        that.setData({
          show: false,
          group_name: value,
          group_index: index,
        });
      },
      onCancel() {
        this.setData({
          show: false,
        });
        Toast('取消');
      },
      showchuli(event) {
        this.setData({
          show1: true,
        });
      },
      onChangechuli(event) {
        const {
          picker,
          value,
          index
        } = event.detail;
        // Toast(`当前值：${value}, 当前索引：${index}`);
      },
      onConfirm1(event) {
        var that = this;
        const {
          picker,
          value,
          index
        } = event.detail;
        
        console.log(value)
        that.setData({
          show1: false,
          deal_name: value,
          deal_index: index,
        });
      },
      onCancel1() {
        this.setData({
          show1: false,
        });
        Toast('取消');
      },
      remark: function(e){
        var that = this;
        that.setData({
          remark: e.detail,
        })
      },
      afterRead(event) {
        var that = this;
  
        const { file } = event.detail;
      // 当设置 mutiple 为 true 时, file 为数组格式，否则为对象格式
      wx.uploadFile({
        url: app.globalData.address + 'event/uploadFile.do',
        filePath: file.url,
        name: 'file',
        formData: { user: 'test' },
        header: {
          "Authorization":wx.getStorageSync('tokenValue'),
          "Content-Type": "application/x-www-form-urlencoded"
        },
        success(res) {
            console.log("-------------------")
            let row = JSON.parse(res.data);
            let name = row.data.file_name;
            let id = row.data.id;
            var list = that.data.fileList;
            var x = {};
          //   x.url = 'data:image/png;base64,' + base64
            x.url = row.data.file_path;
            console.log(x.url)
            x.name = name;
            x.id = id;
          //   x.deletable = true
            x.isImage= true;
            list.push(x);
            that.setData({
              fileList: list,
            })
        },
      });
    },
    picDel:function (e){
        console.log(e.detail.file.id);
        var that = this ; 
        var list = that.data.fileList;
        var num = -1;
        for(var i = 0 ; i < list.length ; i++){
            if(list[i].id == e.detail.file.id){
                num = i;
            }
        }
        if(num != -1){
          list.splice(num,1);
        }
        that.setData({
          fileList: list,
        })
  
    },
    picView:function(e){
        console.log(111111111111111);
        console.log(e);
    }
      
})