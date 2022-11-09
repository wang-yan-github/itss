<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-input v-model.trim="form.id" autocomplete="off" style="display: none"></el-input>
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>盘点记录</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="ID:">{{ assetsInventoryDetailsVo.id }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="资产编号:">{{ assetsInventoryDetailsVo.am_assets_number }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="配置项类型:">{{ assetsInventoryDetailsVo.aty_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="配置子类:">{{ assetsInventoryDetailsVo.ac_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="部门:">{{ assetsInventoryDetailsVo.sd_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="负责人:">{{ assetsInventoryDetailsVo.person_liable }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="领用人:">{{ assetsInventoryDetailsVo.su_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="领用人编码:">{{ assetsInventoryDetailsVo.su_id }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="购置(创建)日期:">{{ assetsInventoryDetailsVo.purchase_date }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="财务编号:">{{ assetsInventoryDetailsVo.finance_number }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="购置公司:">{{ assetsInventoryDetailsVo.purchase_company }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="资产状态:">{{ assetsInventoryDetailsVo.ast_status_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="IT状态:">
                <el-tag type="success" v-if="assetsInventoryDetailsVo.am_it_status == 1">使用</el-tag>
                <el-tag type="warning" v-else>禁用</el-tag>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="CPU:">{{ assetsInventoryDetailsVo.cpu }}</el-form-item>
            </el-col>

            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="内存:">{{ assetsInventoryDetailsVo.memory }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="硬盘:">{{ assetsInventoryDetailsVo.hard_disk }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="用途:">{{ assetsInventoryDetailsVo.purpose }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="序列号(SN号):">{{ assetsInventoryDetailsVo.sn }}</el-form-item>
            </el-col>
            <!--              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
            <!--                <el-form-item label="照片:">-->
            <!--                <div class="photo-box">-->
            <!--                  <el-image-->
            <!--                    v-for="item in pictureList"-->
            <!--                    :key="item.id"-->
            <!--                    @click.native="downloadPic(item.id, item.file_name)"-->
            <!--                    style="width: 50px; height: 50px"-->
            <!--                    :src="item.base64"-->
            <!--                    :preview-src-list="form.srcList"-->
            <!--                  ></el-image>-->
            <!--                </div>-->
            <!--                </el-form-item>-->
            <!--              </el-col>-->
            <el-col :xs="12" :sm="12" :md="6" :lg="6" :xl="6">
              <el-form-item label="附件:">
                <el-upload ref="uploadPicture" class="upload-demo" action="" :auto-upload="false"
                           :on-preview="handlePreview" :multiple="true" disabled :file-list="fileList"></el-upload>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header">
              <span>盘点记录确认</span>
              <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
            </div>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="所属盘点期:">{{ assetsInventoryDetailsVo.ai_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="确认人:">{{ assetsInventoryDetailsVo.suc_name }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="确认时间:">{{ assetsInventoryDetailsVo.confirm_time }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="确认备注:">{{ assetsInventoryDetailsVo.confirm_remark }}</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="通知发送时间:">2022-3-28 18:02:21</el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
              <el-form-item label="盘点状态:">
                <el-link type="primary" :underline="false" v-if="assetsInventoryDetailsVo.status == 1">确认无误</el-link>
                <el-link type="danger" :underline="false" v-if="assetsInventoryDetailsVo.status == 2">确认有误</el-link>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="cardbox" shadow="hover">
            <div slot="header"><span>盘点记录处理</span></div>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="处理备注:" prop="handle_result">
                <el-input v-model.trim="form.handle_result" placeholder="请输入" autocomplete="off" type="textarea"
                          :rows="5"></el-input>
              </el-form-item>
            </el-col>
          </el-card>
        </el-col>
      </el-row>
    </el-form>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>

  </div>
</template>

<script>
import {
  getList,
  doDelete,
  doEdit
} from '@/api/userManagement'
import {getInventoryDetails, doDetailsEdit, toDeal} from '@/api/configtypemanagement'
import {download} from '@/api/file'

export default {
  name: 'capitaDeal',
  components: {},
  data() {
    return {
      fileList: [],
      pictureList: [],
      activeName: 'first',
      input2: '',
      formArr: [{
        value1: ''
      }],
      formArr1: [{
        value1: ''
      }],
      tablelist: null,
      listLoading: true,
      loading: false,
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      srcList: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg'
      ],
      form: {
        id: '',
        handle_result: '',
        content: '',
        srcList: [],
      },
      rules: {
        handle_result: [
          {required: true, message: '请输入处理备注', trigger: 'blur'},
        ],
      },
      assetsInventoryDetailsVo: {},
      queryForm: {},
    }
  },
  created() {
    this.queryForm.id = this.$route.query.id;
    this.form.id = this.$route.query.id;
    this.fetchData()
  },
  beforeDestroy() {
  },
  mounted() {

  },
  methods: {

    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    saveok() {
      this.$refs['form'].validate(async (valid) => {

        if (valid) {
          const {msg} = await toDeal(this.form);
          this.$baseMessage(msg, 'success')
          // this.$emit('fetch-data')

          this.closeall()
        } else {
          return false
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const {data, totalCount} = await getInventoryDetails(this.queryForm)
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

  },
}
</script>
<style lang="scss" scoped>
.photo-box {
  display: -webkit-flex;
  /* Safari */
  display: flex;
  flex-wrap: wrap;
}

.tips {
  color: #E6A23C;
}

.boxtable .btn-box {
  margin-top: 10px;
}

.filltext {
  margin: 0;
}

::v-deep {
  .el-upload-list__item:first-child{
    margin-top: 5px;
  }
  .tsitemd {
    label {
      width: 260 !important;
      text-align: left;
    }

    .el-form-item__content {
      margin-left: 0 !important;
    }
  }

  .el-upload {
    text-align: left;
    /*margin-top: 20px;*/
  }

  .el-image {
    margin-right: 10px;
  }
}
</style>
