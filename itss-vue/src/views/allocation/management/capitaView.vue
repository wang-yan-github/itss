<template>
  <div class="index-container pb80">
    <el-descriptions title="盘点记录" :column='5' class='des-new' size='medium'>
      <el-descriptions-item label="ID"> {{assetsInventoryDetailsVo.id}}</el-descriptions-item>
      <el-descriptions-item label="资产编号"> {{assetsInventoryDetailsVo.am_assets_number}}</el-descriptions-item>
      <el-descriptions-item label="配置项类型"> {{assetsInventoryDetailsVo.aty_name}}</el-descriptions-item>
      <el-descriptions-item label="配置子类">  {{assetsInventoryDetailsVo.ac_name}}</el-descriptions-item>
      <el-descriptions-item label="资产名称">  {{assetsInventoryDetailsVo.am_assets_name}}</el-descriptions-item>
      <el-descriptions-item label="部门">  {{assetsInventoryDetailsVo.sd_name}}</el-descriptions-item>
      <el-descriptions-item label="负责人">无</el-descriptions-item>
      <el-descriptions-item label="领用人"> {{assetsInventoryDetailsVo.su_name}}</el-descriptions-item>
      <el-descriptions-item label="领用人编码">{{assetsInventoryDetailsVo.su_id}}</el-descriptions-item>
      <el-descriptions-item label="购置(创建)日期">2021-11-27 09:54:54</el-descriptions-item>
      <el-descriptions-item label="财务编号"> {{assetsInventoryDetailsVo.finance_number}}</el-descriptions-item>
      <el-descriptions-item label="购置公司">{{assetsInventoryDetailsVo.purchase_company}}</el-descriptions-item>
      <el-descriptions-item label="资产状态"> {{assetsInventoryDetailsVo.ast_status_name}}</el-descriptions-item>
      <el-descriptions-item label="IT状态">
        <el-tag type="success" v-if="assetsInventoryDetailsVo.am_it_status == 1">使用</el-tag>
        <el-tag type="warning" v-else>禁用</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="CPU"> {{assetsInventoryDetailsVo.cpu}}</el-descriptions-item>
      <el-descriptions-item label="内存"> {{assetsInventoryDetailsVo.memory}}</el-descriptions-item>
      <el-descriptions-item label="硬盘" :span='8'> {{assetsInventoryDetailsVo.hard_disk}}</el-descriptions-item>
      <el-descriptions-item label="用途" :span='10'> {{assetsInventoryDetailsVo.purpose}}</el-descriptions-item>
      <el-descriptions-item label="序列号(SN号)" :span='10'>{{assetsInventoryDetailsVo.sn}}</el-descriptions-item>
      <el-descriptions-item label="附件" :span='10'>
        <el-upload ref="uploadPicture" class="upload-demo" action="" :auto-upload="false" :on-preview="handlePreview" :multiple="true" disabled :file-list="fileList"></el-upload>
      </el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="盘点记录确认" :column='5' class='des-new' size='medium'>
      <el-descriptions-item label="所属盘点期"> {{assetsInventoryDetailsVo.ai_name}}</el-descriptions-item>
      <el-descriptions-item label="确认人">  {{assetsInventoryDetailsVo.suc_name}}</el-descriptions-item>
      <el-descriptions-item label="确认时间" :span='6'> {{assetsInventoryDetailsVo.confirm_time}}</el-descriptions-item>
      <el-descriptions-item label="确认备注" :span='10'> {{assetsInventoryDetailsVo.confirm_remark}}</el-descriptions-item>
      <!-- <el-descriptions-item label="通知发送时间">2022-3-28 18:25:31</el-descriptions-item> -->
      <el-descriptions-item label="盘点状态" v-if="assetsInventoryDetailsVo.status == 1">确认无误</el-descriptions-item>
      <el-descriptions-item label="盘点状态" v-if="assetsInventoryDetailsVo.status == 2">确认无误</el-descriptions-item>
    </el-descriptions>
    <el-descriptions title="盘点记录处理" :column='5' class='des-new' size='medium'>
      <el-descriptions-item label="处理人">{{assetsInventoryDetailsVo.suh_name}}</el-descriptions-item>
      <el-descriptions-item label="处理时间" :span='8'>{{assetsInventoryDetailsVo.suh_name?assetsInventoryDetailsVo.update_time:""}}</el-descriptions-item>
      <el-descriptions-item label="处理备注" :span='10'>{{assetsInventoryDetailsVo.handle_result}}</el-descriptions-item>
    </el-descriptions>

    <div class="bottom">
      <el-button size="medium" @click="closeall">关闭</el-button>
    </div>
  </div>
</template>

<script>

import {getInventoryDetails,doDetailsEdit } from '@/api/configtypemanagement'
import {download} from '@/api/file'
export default {
  name: 'thingcapitaView1',
  data() {
    return {
      fileList: [],
      pictureList: [],
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      srcList: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
      ],
      assetsInventoryDetailsVo:{},
      form:{
        srcList:[],
      },
      queryForm:{},
    }
  },
  created() {
    this.queryForm.id = this.$route.query.id;
    this.fetchData()
  },
  methods: {
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    handlePreview(file) {
      download(file.url).then((res) => {
        const url = window.URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', file.name)
        document.body.appendChild(link)
        link.click()
      })
    },
    downloadPic(id, name) {
      download(id).then((res) => {
        const url = window.URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.setAttribute('download', name)
        document.body.appendChild(link)
        link.click()
      })
    },
    async fetchData() {
      this.listLoading = true
      const {	data,totalCount} = await getInventoryDetails(this.queryForm)
      this.assetsInventoryDetailsVo = data
      if (data.assetsEnclosures) {
        data.assetsEnclosures.forEach((x) => {
          this.fileList.push({name: x.file_name, url: x.id})
        })
      }
      if (data.assetsPhotos) {
        this.pictureList = data.assetsPhotos
      }
      this.pictureList.forEach((x) => {
        x.base64 = 'data:image/png;base64,' + x.base64
      })
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  .hiddenmore {
    display: inline;

    ::v-deep {
      .el-input {
        width: 130px;
      }
    }
  }
}
.photo-box{
  display: -webkit-flex;
  /* Safari */
  display: flex;
  flex-wrap: wrap;
}
::v-deep {
  .des-new {
    .el-descriptions__title{
      font-size: 14px;
      font-weight: bold;
      border-left: 4px solid $base-color-default;
      padding-left: 10px;
      color: $base-color-default;
    }
    .el-descriptions-item__container{
      margin-bottom: 18px;
    }

  }
  .el-image{
    margin-right: 10px;
  }
}
</style>
