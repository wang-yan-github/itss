// pages/thing/working/chuli/index.js
import Toast from '@vant/weapp/toast/toast';
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
    title: '添加',
    topHeight: 0,
    activeNames: ['1'],
    show: false,
    show1: false,
    selectKey:{id:'111',name:'请选择'},
    treeData: {
      id: "111",
      name: "全部服务类型",
      childMenus: [{
          id: "222",
          name: "总路长"
        },
        { id: "333",
          name: "一级总路长1",
          childMenus: [{
            id: "444",
            name: "二级总路长",
            childMenus: [{
                id: "666",
                name: "三级总路长"
              },
              {
                id: "8888",
                name: "三级总路长"
              }
            ]
          }]
        },
        { id: "2",
          name: "一级总路长2",
          childMenus: [{
            id: "22",
            name: "二级总路长",
            childMenus: [{
                id: "223",
                name: "三级总路长"
              },
              {
                id: "224",
                name: "三级总路长"
              }
            ]
          }]
        },
        { id: "3",
          name: "一级总路长32",
          childMenus: [{
            id: "32",
            name: "二级总路长",
            childMenus: [{
                id: "233",
                name: "三级总路长"
              },
              {
                id: "234",
                name: "三级总路长"
              }
            ]
          }]
        },
      ]
    },
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
  choosefuwu() {
    this.setData({
      show1: true,
    });
  },
  onClose() {
    this.setData({
      show1: false
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
      //console.log(res[0].height);
      that.setData({
        topHeight: res[0].height + wx.getStorageSync('statusBarHeight') + wx.getStorageSync('navigationBarHeight'),
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