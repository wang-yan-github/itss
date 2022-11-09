// app.js
App({
  onLaunch() {


    wx.cloud.init({

        traceUser: true,

    })

    var that = this
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // navigationBarHeight高度
    const {
      statusBarHeight,
      platform
    } = wx.getSystemInfoSync()
    const {
      top,
      height
    } = wx.getMenuButtonBoundingClientRect()

    // 状态栏高度
    wx.setStorageSync('statusBarHeight', statusBarHeight)
    // 胶囊按钮高度 一般是32 如果获取不到就使用32
    wx.setStorageSync('menuButtonHeight', height ? height : 32)

    // 判断胶囊按钮信息是否成功获取
    if (top && top !== 0 && height && height !== 0) {
      const navigationBarHeight = (top - statusBarHeight) * 2 + height
      // 导航栏高度
      wx.setStorageSync('navigationBarHeight', navigationBarHeight)
    } else {
      wx.setStorageSync(
        'navigationBarHeight',
        platform === 'android' ? 48 : 40
      )
    }

    // 登录
    // wx.login({
    //   success: res => {
    //     console.log(wx.getStorageSync("userInfo"));
    //     if (!wx.getStorageSync("userInfo")) {
    //       console.log(1111111);
    //       wx.navigateTo({
    //         url: '../login/index',
    //       });
    //     } else {
    //       console.log(2222);
    //       wx.redirectTo({
    //         url: '../index/index',
    //       });
    //     }
    //   }
    // })
    wx.login({
      success (res) {
        if (res.code) {
          console.log()
          wx.request({
            url: that.globalData.address+'wxLogin.do',
            data: {
              code: res.code
            },
            success: function(res){
              wx.setStorageSync("openid", res.data);
              wx.request({
                url: that.globalData.address+'checkOpenid.do',
                data: {
                  openId: res.data
                },
                success: function(r){
                  if(r.data.code == 0){
                    wx.setStorageSync("userInfo", r.data.data.userInfo);
                    const permission = require('./utils/permission')
                    var json = permission.permission;
                    for(var i = 0; i < r.data.data.authority.length; i++){
                      var key = r.data.data.authority[i]
                      json[key] = true;
                    }
                    wx.setStorageSync("authority", json);
                  }else{
                    wx.navigateTo({
                      url: '../login/index',
                    });
                  }
                }

              })
            }
          })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      }
    })
    // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo
    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     }
    //   }
    // })

  },
  globalData: {
    userInfo: null,
    address: "http://192.168.0.96:8081/itss-app/app/",
    uploadUrl: "http://192.168.0.96:8081/itss-app/app/fileManage/imgFileSave.json"
  }
})