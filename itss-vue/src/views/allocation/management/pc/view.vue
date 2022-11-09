<template>
  <div class="main-container pb80">
    <el-form ref="form" :rules="rules" label-width="90px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="18" :lg="18" :xl="18">
          <el-card shadow="never">
            <el-tabs v-model="activeName" type="card">
              <el-tab-pane label="属性" name="first">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">属性信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="品牌:" class="tsitem">
                            {{ queryForm.brandName }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="型号:" class="tsitem">
                            {{ queryForm.model }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="序列号(SN号):" class="tsitem">
                            {{ queryForm.sn }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="维保开始日期:" class="tsitem">
                            {{ queryForm.start_time }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="操作系统:" class="tsitem">
                            {{ queryForm.os_id_name }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="系统安装时间:" class="tsitem">
                            {{ queryForm.os_install_time }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="硬盘序列号:" class="tsitem">
                            {{ queryForm.harddisk_serial }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="有线IP地址:" class="tsitem">
                            {{ queryForm.ip }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="有线MAC地址:" class="tsitem">
                            {{ queryForm.mac }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="无线IP地址:" class="tsitem">
                            {{ queryForm.wireless_ip }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="无线MAC地址:" class="tsitem">
                            {{ queryForm.wireless_mac }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="CPU:" class="tsitem">
                            {{ queryForm.cpu }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="内存:" class="tsitem">
                            {{ queryForm.memory }}
                          </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="硬盘:" class="tsitem">
                            {{ queryForm.hard_disk }}
                          </el-form-item>
                        </el-col>
                      </div>
                    </div>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <div class="box_card">
                      <div class="box_header">拓展信息</div>
                      <div class="box_content">
                        <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                          <el-form-item label="配置项类型:" class="tsitem">
                            {{ queryForm.assetsTypeName }}
                          </el-form-item>
                        </el-col>
                        <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                          <!--<el-form-item label="测试文本1001:" class="tsitem">-->
                            <!--3245-->
                          <!--</el-form-item>-->
                        <!--</el-col>-->
                        <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                          <!--<el-form-item label="添加的:" class="tsitem">-->
                            <!--3245-->
                          <!--</el-form-item>-->
                        <!--</el-col>-->
                      </div>
                    </div>
                  </el-col>
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
                                :preview-src-list="queryForm.srcList"
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
                                :data="queryForm"
                              ></el-upload>
                            </div>
                          </el-form-item>
                        </el-col>

                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                          <el-form-item label="用途:" class="tsitem">
                            {{ queryForm.purpose }}
                          </el-form-item>
                          <el-form-item label="位置:" class="tsitem">
                            {{ queryForm.position }}
                          </el-form-item>
                          <el-form-item label="备注:" class="tsitem">
                            {{ queryForm.remark }}
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
                        type="success"
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
                <el-form-item label="配置子类:" class="tsitem">
                  {{ queryForm.childAssetName }}
                </el-form-item>
                <el-form-item label="资产名称:" class="tsitem">
                  {{ queryForm.assets_name }}
                </el-form-item>
                <el-form-item label="部门:" class="tsitem">
                  {{ queryForm.dept_name }}
                </el-form-item>
                <el-form-item label="负责人:" class="tsitem">
                  {{ queryForm.person_liable_name }}
                </el-form-item>
                <el-form-item label="领用人:" class="tsitem">
                  {{ queryForm.receiver_name }}
                </el-form-item>
                <el-form-item label="资产状态:" class="tsitem">
                  {{ queryForm.assetsStatusName }}
                </el-form-item>
                <el-form-item
                  label="保密:"
                  class="tsitem"
                  v-if="queryForm.is_secrecy == '0'"
                >
                  否
                </el-form-item>
                <el-form-item label="保密:" class="tsitem" v-else>
                  是
                </el-form-item>
                <el-form-item label="购置(创建)日期:" class="tsitem">
                  {{ queryForm.purchase_date }}
                </el-form-item>
                <el-form-item label="财务编号:" class="tsitem">
                  {{ queryForm.finance_number }}
                </el-form-item>
                <el-form-item label="保密编号:" class="tsitem">
                  {{ queryForm.secrecy_number }}
                </el-form-item>
                <el-form-item label="购置公司:" class="tsitem">
                  {{ queryForm.purchaseCompanyName }}
                </el-form-item>
                <el-form-item label="使用范围:" class="tsitem">
                  {{ queryForm.useScopeName }}
                </el-form-item>
                <el-form-item label="IT状态:" class="tsitem">
                  {{ queryForm.itStatusName }}
                </el-form-item>
              </div>
            </div>
            <div class="box_card">
              <div class="box_header">二维码</div>
              <div class="box_content">
                <el-form-item label="" class="mb0">
                  <el-form-item label="" class="mb0">
                    <el-image
                      :src="queryForm.qrCode"
                      style="width: 140px; height: 140px"
                    ></el-image>
                  </el-form-item>
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
import { getACByAssetsTypeId } from '@/api/assetsChildren'
import { toAssetsManageAdd, getAssetsManageById } from '@/api/assetsManage'
import { getItStatus } from '@/api/assetsItStatus'
import { getAllStatus } from '@/api/assetsStatusManage'
import { getAllAssetsType } from '@/api/assetsType'
import { getAllCompanies } from '@/api/assetsCompany'
import { getAllUseScope } from '@/api/assetsUseScope'
import { getAllBrand } from '@/api/assetsBrand'
import { download } from '@/api/file'
import { getFieldsByFormId } from '@/api/assetsExpandFormField'
import Users from '@/components/UserSelectModel'
import Bumen from '@/components/bumenTree'
import dialogthree from '@/components/contractContactDia'
import dialogfour from '@/components/supplierContractDia'
import dialogfive from '@/components/assetsTypeDia'
export default {
  name: 'edit',
  components: { Users, Bumen, dialogthree, dialogfour, dialogfive },
  data() {
    return {
      activeName: 'first',
      manage_id: '',
      checked: false,
      checked1: false,
      checked2: false,
      checked3: false,
      assetsTypes: [],
      assetsChildren: [],
      itStatus: [],
      companies: [],
      useScope: [],
      statusManage: [],
      brands: [],
      options: [],
      fileList: [],
      pictureList: [],
      contracts: [],
      supplierContractList: [],
      assetsTypeList: [],
      multipleSelection: [],
      selectSupplierRows: [],
      selectAssetsTypeRows: [],
      contractContactList: [],
      tableData: [],
      queryForm: {
        qrCode: '',
        child_asset: '',
        assets_name: '',
        person_liable: '',
        person_liable_name: '',
        receiver: '',
        receiver_name: '',
        assets_status: '',
        is_secrecy: '',
        finance_number: '',
        secrecy_number: '',
        purchase_date_start: '',
        purchase_date_end: '',
        it_status: '',
        use_scope_id: '',
        dept_id: '',
        dept_name: '',
        brand_id: '',
        model: '',
        start_time: '',
        sn: '',
        phone: '',
        ip: '',
        mac: '',
        imei: '',
        pin: '',
        purpose: '',
        position: '',
        remark: '',
        contractIds: '',
        supplierIds: '',
        assetsIds: '',
        assets_type_id: '',
        srcList: [],
      },
      rules: {
        username: [
          {
            required: true,
            trigger: 'blur',
            message: '请输入名称',
          },
        ],
        status: [
          {
            required: true,
            trigger: 'blur',
            message: '请选择状态',
          },
        ],
        data1: [
          {
            required: true,
            trigger: 'blur',
            message: '请选择开始日期',
          },
        ],
      },
      title: '',
      value2: '',
      value3: '',
      value4: '',
    }
  },
  mounted() {
    this.manage_id = this.$route.query.id
    this.getAssetsChildren()
    this.getAllItStatus()
    this.getItStatus()
    this.getusesCppe()
    this.getStatus()
    this.getBrand()
    this.getData()
    //this.getFields()
  },
  methods: {
    //---------------获取下拉框选项begin---------------------------

    /*--获取配置项子类--*/
    getAssetsChildren() {
      getACByAssetsTypeId({
        assets_type_id: this.queryForm.assets_type_id,
      }).then((res) => {
        if (res.code == 0) {
          this.assetsChildren = res.data
        }
      })
    },

    /*--获取it状态--*/
    getAllItStatus() {
      getItStatus().then((res) => {
        if (res.code == 0) {
          this.itStatus = res.data
        }
      })
    },

    /*--获取购置公司--*/
    getItStatus() {
      getAllCompanies().then((res) => {
        if (res.code == 0) {
          this.companies = res.data
        }
      })
    },

    /*--获取购置公司--*/
    getusesCppe() {
      getAllUseScope().then((res) => {
        if (res.code == 0) {
          this.useScope = res.data
        }
      })
    },

    /*--获取配置状态--*/
    getStatus() {
      getAllStatus().then((res) => {
        if (res.code == 0) {
          this.statusManage = res.data
        }
      })
    },

    /*--获取品牌信息--*/
    getBrand() {
      getAllBrand().then((res) => {
        if (res.code == 0) {
          this.brands = res.data
        }
      })
    },
    //---------------获取下拉框选项end-----------------------------
    getData() {
      getAssetsManageById(this.manage_id).then((res) => {
        if (res.code == 0) {
          this.queryForm = res.data

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
          this.queryForm.qrCode = 'data:image/png;base64,' + res.data.qrCode
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
    handleSolveUser(type) {
      switch (type) {
        case 'receiver':
          this.selectType = 'receiver'
          this.$refs['users'].showWin()
          break
        case 'liable':
          this.selectType = 'liable'
          this.$refs['users'].showWin()
          break
        case 'contract':
          this.selectType = 'contract'
          this.$refs['users'].showWin()
      }
    },
    //供应商合同 弹窗页面
    handleAdd3() {
      this.$refs['dialogfour'].showWin()
    },
    //配置项类型 弹窗页面
    handleAdd4() {
      this.$refs['dialogfive'].showWin()
    },
    getUser(row) {
      switch (this.selectType) {
        case 'receiver':
          this.queryForm.receiver = row.id
          this.queryForm.receiver_name = row.name
          break
        case 'liable':
          this.queryForm.person_liable = row.id
          this.queryForm.person_liable_name = row.name
          break
        case 'contract':
          if (!this.contracts.some((item) => item.id === row.id)) {
            this.contracts.push(row)
          }
          break
      }
    },
    handlebumen() {
      this.$refs['bumen'].showWin()
    },
    getDept(row) {
      this.queryForm.dept_id = row.id
      this.queryForm.dept_name = row.name
    },
    //合同联系人 选中数据回显
    listAssetsContract(rows) {
      console.log('合同联系人 选中数据回显')
      console.log(rows)
      rows.forEach((item, i) => {
        let index = this.contractContactList.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.contractContactList.push(item)
        }
      })
    },
    //关联的配置项 选中数据回显
    listAssetsType(rows) {
      console.log('关联的配置项 选中数据回显')
      console.log(rows)
      rows.forEach((item, i) => {
        let index = this.assetsTypeList.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.assetsTypeList.push(item)
        }
      })
    },
    //供应商合同 选中数据回显
    listAssetsSupplier(rows) {
      console.log('供应商合同 选中数据回显')
      console.log(rows)
      rows.forEach((item, i) => {
        let index = this.supplierContractList.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.supplierContractList.push(item)
        }
      })
    },
    //合同联系人 选中事件
    setSelectSupplierRows(val) {
      this.selectSupplierRows = val
    },
    //合同联系人 选中事件
    setSelectAssetsTypeRows(val) {
      this.selectAssetsTypeRows = val
    },
    setSelectContactRows(val) {
      this.selectContactRows = val
    },
    //移除 合同联系人
    handleDeleteContract() {
      if (this.selectContactRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectContactRows.forEach((item, i) => {
            this.contractContactList.some((x, i) => {
              if (x.id == item.id) {
                this.contractContactList.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    //移除 供应商合同
    handleDeleteSupplier() {
      if (this.selectSupplierRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectSupplierRows.forEach((item, i) => {
            this.supplierContractList.some((x, i) => {
              if (x.id == item.id) {
                this.supplierContractList.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    //移除 关联的配置项
    handleDeleteAssetsType() {
      if (this.selectAssetsTypeRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectAssetsTypeRows.forEach((item, i) => {
            this.assetsTypeList.some((x, i) => {
              if (x.id == item.id) {
                this.assetsTypeList.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    handleDelete() {
      if (this.multipleSelection.length == 0) {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
      let newArr = this.contracts.filter((item) => {
        return !this.multipleSelection.some((ele) => ele.id === item.id)
      })
      this.contracts = newArr
    },
    handleAdd2() {
      this.$refs['dialogthree'].showWin()
    },
    handleAdd3() {
      this.$refs['dialogfour'].showWin()
    },
    handleAdd4() {
      this.$refs['dialogfive'].showWin()
    },

    remoteMethod(query) {
      if (query !== '') {
        this.loading = true
        setTimeout(() => {
          this.loading = false
          this.options = this.list.filter((item) => {
            return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
          })
        }, 200)
      } else {
        this.options = []
      }
    },
    closeall() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    onChange(file, fileList) {
      this.fileList = fileList
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
    saveok() {
      const params = new FormData()
      for (let i = 0; i < this.fileList.length; i++) {
        const f = this.fileList[i]
        params.append('file', f.raw)
      }
      let newarr = this.contractContactList.map((item) => {
        return item.id
      })
      let newarr1 = this.supplierContractList.map((item) => {
        return item.id
      })
      let newarr2 = this.assetsTypeList.map((item) => {
        return item.id
      })
      this.queryForm.assetsIds = newarr2.join(',')
      this.queryForm.supplierIds = newarr1.join(',')
      this.queryForm.contractIds = newarr.join(',')
      params.append('body', JSON.stringify(this.queryForm))
      toAssetsManageAdd(params).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('添加成功', 'success')
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
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
}
</style>
