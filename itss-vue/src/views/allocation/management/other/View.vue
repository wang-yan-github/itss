<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <el-row :gutter="20">
                  <!-- <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">拓展信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="测试文本1001:" class="tsitem">
                            {{ form.purpose }}
                          </el-form-item>
                        </el-col>
                      </div>
                    </div>
                  </el-col> -->
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">其他信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="照片:" class="tsitem">
                            <div class="bghui">
                              <el-image
                                v-for="item in pictureList"
                                :key="item.id"
                                @click.native="
                                  downloadPic(item.id, item.file_name)
                                "
                                style="width: 50px; height: 50px"
                                :src="item.base64"
                                :preview-src-list="form.srcList"
                              ></el-image>
                            </div>
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="附件:" class="tsitem">
                            <div class="bghui">
                              <el-upload
                                ref="uploadPicture"
                                class="upload-demo"
                                action=""
                                :auto-upload="false"
                                :on-preview="handlePreview"
                                :multiple="true"
                                disabled
                                :file-list="fileList"
                                :data="form"
                              ></el-upload>
                            </div>
                          </el-form-item>
                        </el-col>

                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="用途:" class="tsitem">
                            {{ form.purpose }}
                          </el-form-item>
                          <el-form-item label="位置:" class="tsitem">
                            {{ form.position }}
                          </el-form-item>
                          <el-form-item label="备注:" class="tsitem">
                            {{ form.remark }}
                          </el-form-item>
                        </el-col>
                      </div>
                    </div>
                  </el-col>
                </el-row>
              </el-tab-pane>

              <el-tab-pane label="联系人" name="second">
                <el-table :data="contractContactList" stripe border>
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index" label="序号"
                    width="60"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="contact_name"
                    align="center"
                    sortable
                    label="姓名"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="is_enable_name"
                    align="center"
                    sortable
                    label="状态"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="company_name"
                    align="center"
                    label="联系人公司"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="deptName"
                    align="center"
                    label="我司对接部门"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="email"
                    align="center"
                    label="Email"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="phone"
                    align="center"
                    label="Phone"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="remark"
                    align="center"
                    label="备注"
                    sortable
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="供应商合同" name="third">
                <el-table :data="supplierContractList" stripe border>
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    type="index" label="序号"
                    width="60"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="supplier_name"
                    align="center"
                    sortable
                    label="供应商"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="contract_name"
                    align="center"
                    sortable
                    label="合同名称"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="deptName"
                    align="center"
                    label="部门"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="renew_time"
                    align="center"
                    label="续约提醒时间"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="renew_time"
                    align="center"
                    label="开始时间"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="renew_time"
                    align="center"
                    label="结束时间"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="amount"
                    align="center"
                    label="金额"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="currency_name"
                    align="center"
                    label="币种"
                    sortable
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="status_name"
                    align="center"
                    label="状态"
                    sortable
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联的配置项" name="fourth">
                <el-table :data="assetsTypeList" stripe border>
                  <el-table-column
                    show-overflow-tooltip
                    type="selection"
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_number"
                    label="资产编号"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assetsTypeName"
                    label="配置项类型"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="childAssetName"
                    label="配置子类"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="assets_name"
                    label="资产名称"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="deptName"
                    label="部门"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="receiverName"
                    label="领用人"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="purchaseCompanyName"
                    label="购置公司"
                    sortable
                    align="center"
                  ></el-table-column>
                  <el-table-column
                    show-overflow-tooltip
                    prop="is_secrecy"
                    label="保密"
                    sortable
                    align="center"
                  >
                    <template #default="scope">
                      <el-tag
                        size="mini"
                        icon="el-icon-edit"
                        v-if="scope.row.is_secrecy == '0'"
                        :type="primary"
                      >
                        否
                      </el-tag>
                      <el-tag
                        size="mini"
                        icon="el-icon-delete"
                        class="red"
                        v-if="scope.row.is_secrecy == '1'"
                      >
                        是
                      </el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="assetsStatusName"
                    label="资产状态"
                    sortable
                    align="center"
                  >
                    <template #default="scope">
                      <el-tag
                        size="mini"
                        icon="el-icon-delete"
                        class="red"
                        type="success"
                      >
                        {{ scope.row.assetsStatusName }}
                      </el-tag>
                    </template>
                  </el-table-column>

                  <el-table-column
                    show-overflow-tooltip
                    prop="it_status"
                    label="IT状态"
                    sortable
                    align="center"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="工单" name="five">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="关联的变更" name="six">
                <el-table :data="tableData" stripe border>
                  <el-table-column
                    prop="date"
                    label="日期"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="name"
                    label="姓名"
                    width="180"
                  ></el-table-column>
                  <el-table-column
                    prop="address"
                    label="地址"
                  ></el-table-column>
                </el-table>
              </el-tab-pane>
            </el-tabs>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
          <el-card shadow="never" class="hasiscoll">
            <div class="box_card">
              <div class="box_header">基础信息</div>
              <div class="box_content">
                <el-form-item label="资产编号:" class="mb0">
                  {{ form.assets_number }}

                </el-form-item>
                <el-form-item label="ID:" class="mb0" hidden>
                  {{ form.id }}
                </el-form-item>
                <el-form-item label="配置项类型:" class="mb0">
                  {{ form.assetsTypeName }}
                </el-form-item>
                <el-form-item label="配置子类:" class="mb0">
                  {{ form.childAssetName }}
                </el-form-item>
                <el-form-item label="资产名称:" class="mb0">
                  {{ form.assets_name }}
                </el-form-item>
                <el-form-item label="部门:" class="mb0">
                  {{ form.dept_name }}
                </el-form-item>
                <el-form-item label="负责人:" class="mb0">
                  {{ form.person_liable_name }}
                </el-form-item>
                <el-form-item label="领用人:" class="mb0">
                  {{ form.receiver_name }}
                </el-form-item>
                <el-form-item label="资产状态:" class="mb0">
                  {{ form.assetsStatusName }}
                </el-form-item>
                <el-form-item label="保密:" class="mb0">
                  {{ form.is_secrecy == 0 ? '否' : '是' }}
                </el-form-item>
                <el-form-item label="购置(创建)日期:" class="custemitem mb0">
                  {{ form.purchase_date }}
                </el-form-item>
                <el-form-item label="财务编号:" class="mb0">
                  {{ form.finance_number }}
                </el-form-item>
                <el-form-item label="保密编号:" class="mb0">
                  {{ form.secrecy_number }}
                </el-form-item>
                <el-form-item label="购置公司:" class="mb0">
                  {{ form.purchaseCompanyName }}
                </el-form-item>
                <el-form-item label="使用范围:" class="mb0">
                  {{ form.useScopeName }}
                </el-form-item>
                <el-form-item label="IT状态:" class="mb0">
                  {{ form.itStatusName }}
                </el-form-item>
              </div>
            </div>
             <div class="box_card">
                <div class="box_header">二维码</div>
                <div class="box_content">
                  <el-form-item label="" class="mb0">
                    <el-image :src="form.qrCode" style="width: 140px; height: 140px"></el-image>
                  </el-form-item>
                </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div class="bottom">
        <el-button size="medium" @click="closeall">关闭</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
//配置管理新增保存接口
import { getAssetsManageById } from '@/api/assetsManage'

import vabQuill from '@/plugins/vabQuill'

export default {
  name: 'companyviews',
  components: { vabQuill },
  data() {
    return {
      pictureList: [],
      fileList: [],
      manage_id: '',
      imagesrc:'',
      tableData: [],
      contractContactList: [], //合同联系人
      supplierContractList: [], //供应商合同
      assetsTypeList: [], //关联的配置项
      options: {
        theme: 'snow',
        bounds: document.body,
        debug: 'warn',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ color: [] }, { background: [] }],
            ['blockquote', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ align: [] }],
            [{ direction: 'rtl' }],
            [{ font: [] }],
            ['clean'],
            ['link', 'image'],
          ],
        },
        placeholder: '内容...',
        readOnly: false,
      },
      form: {
        dept_name: '', //部门名称
        receiver_name: '', //领用人名称
        person_liable_name: '', //负责人名称
        id: '',
        assets_number: '', //     资产编号
        assets_type_id: '', //     配置项类型id
        child_asset: '', //     配置子类
        assets_name: '', //     资产名称
        dept_id: '', //     部门
        person_liable: '', //     负责人
        receiver: '', //     领用人
        assets_status: '', //     资产状态
        is_secrecy: '', //     保密
        purchase_date: '', //     购置（创建）日期
        finance_number: '', //     财务编号
        secrecy_number: '', //     保密编号
        purchase_company: '', //     购置公司
        use_scope_id: '', //     使用范围
        it_status: '', //     IT状态
        purpose: '', //     用途
        position: '', //     位置
        remark: '', //     备注
        brand_id: '', //     品牌
        model: '', //     型号
        sn: '', //     序列号（SN）
        start_time: '', //     维保开始日期
        end_time: '', //     维保结束日期
        cabinet_id: '', //     机柜
        start_seat: '', //     起始机位
        end_seat: '', //     终止机位
        selenium_drum_model: '', //     硒鼓型号
        mac: '', //     MAC地址
        ip: '', //     IP地址
        wireless_mac: '', //     无线MAC地址
        wireless_ip: '', //     无线IP地址
        nbu: '', //     NBU
        mac2: '', //     MAC地址2
        mac3: '', //     MAC地址3
        ip2: '', //     IP地址2
        cpu: '', //     CPU
        memory: '', //     内存
        hard_disk: '', //     硬盘
        server_config: '', //     服务器配置
        os_id: '', //     操作系统
        is_invirtual: '', //     是否纳入虚拟化资源池
        server_code: '', //     服务编号/快速服务代码
        os_licence_id: '', //     OS许可证
        allocated_capacity: '', //     分配容量
        raid: '', //     Raid信息
        capacity: '', //     总容量
        os_edition: '', //     系统版本
        server_id: '', //     服务器
        farm_id: '', //     farm
        phone: '', //     电话号码
        imei: '', //     IMEI
        pin: '', //     硬件PIN
        os_install_time: '', //     系统安装时间
        harddisk_serial: '', //     硬盘序列号
        software_licence_id: '', //     软件许可证
        path: '', //     路径
        edition: '', //     版本
        system_name: '', //     系统名称
        url: '', //     URL
        databaseName: '', //     数据库
        extranet_ip: '', //     外网IP
        intranet_ip: '', //     内网IP
        mid_name_edition: '', //     中间件名称及版本
        software_manufacturer: '', //     软件厂商名称
        implementation_manufacturer: '', //     实施厂商名称
        js_liable: '', //     应用系统技术负责人
        js_phone: '', //     技术负责人联系电话
        yw_liable: '', //     系统业务负责人
        yw_phone: '', //     业务负责人联系电话
        is_enable: '', //     启用类型
        backup_id: '', //     备份方式
        online_time: '', //     系统上线时间
        extend: '', //     扩展字段
        is_inventory: '', // 是否已生成资产盘点 0 生成 1.未生成
        deptName: '', //部门名称
        assetsTypeName: '', //     配置项类型id 对应的名称
        childAssetName: '', //     配置子类 对应的名称
        receiverName: '', //     配置子类 对应的名称
        purchaseCompanyName: '', //     公司
        purchase_date_start: '', //购置日期开始
        purchase_date_end: '', //购置日期结束
        assetsStatusName: '',
        qrCode: '',
      },
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
        ],
        content: [
          {
            required: true,
            message: '请输入内容',
            trigger: 'blur',
          },
        ],
      },
      borderColor: '#dcdfe6',
      checked: false,
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      srcList: [
        'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
        'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg',
      ],
      activeName: 'first',
      title: '',
      value: 3.7,
      dialogFormVisible: false,
      username:
        'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
    }
  },
  created() {
    this.manage_id = this.$route.query.id
    this.getData()
  },
  methods: {
    getData() {
        getAssetsManageById(this.manage_id).then((res) => {
          if (res.code == 0) {
            this.form = res.data

            for (var i = 0 ; i< res.data.contractContactList.length ; i++){
              if (res.data.contractContactList[i].is_enable == 0){
                res.data.contractContactList[i].is_enable_name = '禁用';
              } else {
                res.data.contractContactList[i].is_enable_name = '启用';
              }
            }

            this.contractContactList = res.data.contractContactList
            this.supplierContractList = res.data.supplierContractList
            this.assetsTypeList = res.data.assetsTypeList
            this.form.qrCode='data:image/png;base64,' + res.data.qrCode ;
            if (res.data.fileManageList) {
              res.data.fileManageList.forEach((x) => {
                this.fileList.push({ name: x.file_name, url: x.id })
              })
            }
            if (res.data.pictureList) {
              this.pictureList = res.data.pictureList
            }
            this.pictureList.forEach((x) => {
              x.base64 = 'data:image/png;base64,' + x.base64
            })
          }
        })
    },
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
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
  },
}
</script>
<style lang="scss" scoped>
.main-container {
  ::v-deep {
    .el-divider--horizontal {
      margin-bottom: 0 !important;
    }

    .el-tabs--border-card {
      box-shadow: 0 0px 0px 0 rgba(0, 0, 0, 0), 0 0 6px 0 rgba(0, 0, 0, 0);
    }

    .el-card__body {
      height: calc(100vh - 290px) !important;

      .el-tabs__content {
        height: calc(100vh - 350px) !important;
        overflow-y: auto;
        overflow-x: hidden;
      }
    }

    .hasiscoll {
      .el-form-item__label {
        line-height: 22px;
      }

      .el-form-item__content {
        line-height: 22px;
      }

      .el-card__body {
        overflow-y: auto !important;
        overflow-x: hidden;
      }
    }

    .el-timeline {
      .el-card__body {
        height: auto !important;
      }
    }
  }
}
</style>
