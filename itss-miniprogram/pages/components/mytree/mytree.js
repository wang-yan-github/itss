// pages/components/mytree/mytree.js
import Toast from '@vant/weapp/toast/toast';
Component({
    properties: {
      model: Object,
      selectKey: {
        // 选中的节点id
        type: String,
        value: "",
        worker_order_id: '',//工单类别ID
        engineer_id: '',//自动分派到工程师ID
      }
    },
    data: {
      open: true,
      isBranch: true,      
    },
  
    methods: {
      toggle: function(e) {
        if (this.data.isBranch) {
          this.setData({
            open: !this.data.open
          });
        }
      },
      tapItem: function(e) {
        var item = e.currentTarget.dataset.item;
        if (this.data.isBranch) {
          this.setData({
            open: !this.data.open, 
          });
        } 
        // Toast("组件里选中项: "+JSON.stringify(item));
        console.log(JSON.stringify(item));
        console.log(item);
        console.log(item.name);
        console.log(item.id);
        //console.log("组件里点击的id: "+item);
        //选中值放入缓存中
        wx.setStorageSync('treeData', item);
        this.triggerEvent("treeTap", { item: item }, { bubbles: true, composed: true });
      },
      treenodetap: function(e) {
        // 递归的最终子节点
        var item = e.detail.item;
        this.triggerEvent("treeTap", {item});
      }
    },
    ready(e) {
        var that=this;
        that.setData({
        isBranch: Boolean(
            that.data.model.childMenus && that.data.model.childMenus.length
        )
      });
      //console.log(that.data);
    }
  });
  
  
  