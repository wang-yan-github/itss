<template>
  <div class="index-container pb80">
    <el-form ref="queryForm" :rules="rules" :model="queryForm" label-width="140px">
      <template>
        <el-tabs v-model="activeName">
          <el-tab-pane label="属性" name="first">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
                    <span>
                      <vab-icon :icon="['fas', 'paperclip']"></vab-icon>
                      基础信息
                    </span>
                    <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="配置子类" prop="child_asset">
                      <el-select v-model.trim="queryForm.child_asset" class="w">
                        <el-option
                          v-for="item in assetsChildren"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="资产名称" prop="assets_name">
                      <el-input
                        v-model.trim="queryForm.assets_name"
                        placeholder="请输入"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="部门" prop="dept_name">
                      <el-input
                        v-model.trim="queryForm.dept_name"
                        placeholder="部门"
                        clearable
                        @click.native="handlebumen"
                      >
                        <el-button
                          slot="append"
                          icon="el-icon-search"
                          @click="handlebumen"
                        ></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="负责人">
                      <el-input
                        v-model.trim="queryForm.person_liable_name"
                        placeholder="负责人"
                        clearable
                        @click.native="handleSolveUser('liable')"
                      >
                        <el-button
                          slot="append"
                          icon="el-icon-search"
                          @click="handleSolveUser('liable')"
                        ></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="领用人">
                      <el-input
                        v-model.trim="queryForm.receiver_name"
                        placeholder="领用人"
                        clearable
                        @click.native="handleSolveUser('receiver')"
                      >
                        <el-button
                          slot="append"
                          icon="el-icon-search"
                          @click="handleSolveUser('receiver')"
                        ></el-button>
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="资产状态" prop="assets_status">
                      <el-select
                        v-model.trim="queryForm.assets_status"
                        class="w"
                      >
                        <el-option
                          v-for="item in statusManage"
                          :key="item.id"
                          :label="item.status_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="保密" prop="is_secrecy">
                      <el-select v-model.trim="queryForm.is_secrecy" class="w">
                        <el-option label="是" value="1"></el-option>
                        <el-option label="否" value="0"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="购置(创建)日期" prop="purchase_date">
                      <el-date-picker
                        v-model="queryForm.purchase_date"
                        type="date"
                        placeholder="选择日期"
                        style="width: 100%"
                        value-format="yyyy-MM-dd HH:mm:ss"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="财务编号">
                      <el-input
                        v-model.trim="queryForm.finance_number"
                        placeholder="请输入"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="保密编号">
                      <el-input
                        v-model.trim="queryForm.secrecy_number"
                        placeholder="请输入"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="购置公司">
                      <el-select
                        v-model.trim="queryForm.purchase_company"
                        class="w"
                      >
                        <el-option
                          v-for="item in companies"
                          :key="item.id"
                          :label="item.company_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="使用范围">
                      <el-select
                        v-model.trim="queryForm.use_scope_id"
                        class="w"
                      >
                        <el-option
                          v-for="item in useScope"
                          :key="item.id"
                          :label="item.scope_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="IT状态" prop="it_status">
                      <el-select v-model.trim="queryForm.it_status" class="w">
                        <el-option
                          v-for="item in itStatus"
                          :key="item.id"
                          :label="item.status_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
                    <span>
                      <vab-icon :icon="['fas', 'paperclip']"></vab-icon>
                      属性信息
                    </span>
                    <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="软件许可证">
                      <el-select v-model.trim="queryForm.assetsSoftwareLicence_id" class="w">
                        <el-option
                          v-for="item in softWares"
                          :key="item.id"
                          :label="item.os_licence_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="路径">
                      <el-input
                        v-model.trim="queryForm.path"
                        placeholder="请输入路径"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="版本">
                      <el-input
                        v-model.trim="queryForm.edition"
                        placeholder="版本"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>
              <!-- todo -->
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
                    <span>
                      <vab-icon :icon="['fas', 'paperclip']"></vab-icon>
                      拓展信息
                    </span>
                    <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">

                    <el-form-item label="配置项类型" prop="status">
                      <el-select
                        v-model="queryForm.assets_type_id"
                        placeholder="配置项类型"
                        @change="assetsTypeChange"
                      >
                        <el-option
                          :key="queryForm.assets_type_id"
                          :label="queryForm.assets_type_name"
                          :value="queryForm.assets_type_id"
                        ></el-option>
                        <el-option
                          v-for="item in assetsTypes"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>

                    <!--<el-form-item label="测试文本1001" prop="status">-->
                      <!--<el-input-->
                        <!--v-model.trim="queryForm.username"-->
                        <!--placeholder="请输入"-->
                        <!--autocomplete="off"-->
                      <!--&gt;</el-input>-->
                    <!--</el-form-item>-->
                  </el-col>
                </el-card>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="hover">
                  <div slot="header">
                    <span>
                      <vab-icon :icon="['fas', 'paperclip']"></vab-icon>
                      其他信息
                    </span>
                    <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
                  </div>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="照片">
                      <!--<el-button-->
                        <!--type="primary"-->
                        <!--size="small"-->
                        <!--icon="el-icon-camera"-->
                      <!--&gt;-->
                        <!--截屏-->
                      <!--</el-button>-->
                      <el-upload
                        ref="uploadPicture"
                        class="upload-demo"
                        action=""
                        accept=".jpg, .png, .jpeg"
                        :auto-upload="false"
                        :on-change="onChangePicture"
                         :on-remove="handleRemovePicture"
                        :multiple="true"
                        :limit="3"
                        :file-list="pictureList"
                        :data="queryForm"
                      >
                        <el-button
                          size="small"
                          icon="el-icon-upload2"
                          type="primary"
                        >
                          文件上传
                        </el-button>
                        <div slot="tip" class="el-upload__tip">
                          附件大小上限：5.0MB
                        </div>
                      </el-upload>
                    </el-form-item>

                    <el-form-item label="附件">
                      <!--<el-button-->
                        <!--type="primary"-->
                        <!--size="small"-->
                        <!--icon="el-icon-camera"-->
                      <!--&gt;-->
                        <!--截屏-->
                      <!--</el-button>-->
                      <el-upload
                        ref="uploadFile"
                        class="upload-demo"
                        action=""
                        :auto-upload="false"
                        :on-change="onChange"
                        :on-remove="handleRemove"
                        :multiple="true"
                        :limit="3"
                        :file-list="fileList"
                        :data="queryForm"
                      >
                        <el-button
                          size="small"
                          icon="el-icon-upload2"
                          type="primary"
                        >
                          文件上传
                        </el-button>
                        <div slot="tip" class="el-upload__tip">
                          附件大小上限：5.0MB
                        </div>
                      </el-upload>
                    </el-form-item>
                    <!-- <el-form-item label="附件">
                      <el-button
                        type="primary"
                        size="small"
                        icon="el-icon-camera"
                      >
                        截屏
                      </el-button>
                      <el-upload
                        class="upload-demo"
                        action="https://jsonplaceholder.typicode.com/posts/"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        multiple
                        :limit="3"
                        :on-exceed="handleExceed"
                        :file-list="fileList"
                      >
                        <el-button
                          size="small"
                          icon="el-icon-upload2"
                          type="primary"
                        >
                          文件上传
                        </el-button>
                        <div
                          slot="tip"
                          class="el-upload__tip"
                        >
                          附件大小上限：5.0MB
                        </div>
                      </el-upload>
                    </el-form-item> -->
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="用途">
                      <el-input
                        v-model.trim="queryForm.purpose"
                        autocomplete="off"
                        placeholder="请输入"
                        type="textarea"
                        :rows="3"
                      ></el-input>
                    </el-form-item>
                    <el-form-item label="位置">
                      <el-input
                        v-model.trim="queryForm.position"
                        autocomplete="off"
                        placeholder="请输入"
                        type="textarea"
                        :rows="3"
                      ></el-input>
                    </el-form-item>
                    <el-form-item label="备注">
                      <el-input
                        v-model.trim="queryForm.remark"
                        autocomplete="off"
                        placeholder="请输入"
                        type="textarea"
                        :rows="3"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="联系人" name="second">
            <el-table
              ref="listTable"
              border
              stripe
              :data="contractContactList"
              @selection-change="setSelectContactRows"
              highlight-current-row
            >
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
                label="手机"
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
            <div class="btn-box" style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteContract"
              >
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd2">
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="供应商合同" name="three">
            <el-table
              ref="listTable"
              border
              stripe
              :data="supplierContractList"
              @selection-change="setSelectSupplierRows"
              highlight-current-row
            >
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
                prop="start_time"
                align="center"
                label="开始时间"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="end_time"
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
            <div class="btn-box" style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteSupplier"
              >
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd3">
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="关联的配置项" name="four">
            <el-table
              ref="listTable"
              border
              stripe
              :data="assetsTypeList"
              @selection-change="setSelectAssetsTypeRows"
              highlight-current-row
            >
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
                    :type="success"
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
                prop="itStatusName"
                label="IT状态"
                sortable
                align="center"
              ></el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteAssetsType"
              >
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd4">
                添加
              </el-button>
            </div>
          </el-tab-pane>
        </el-tabs>
      </template>
    </el-form>
    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <bumen ref="bumen" @deptData="getDept" @fetch-data="fetchData"></bumen>
    <dialogthree
      ref="dialogthree"
      @listData="listAssetsContract"
      @fetch-data="
        {
          value2
        }
      "
    ></dialogthree>
    <dialogfour
      ref="dialogfour"
      @listData="listAssetsSupplier"
      @fetch-data="
        {
          value2
        }
      "
    ></dialogfour>
    <dialogfive
      ref="dialogfive"
      @listData="listAssetsType"
      @fetch-data="
        {
          value2
        }
      "
    ></dialogfive>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok" :disabled="this.isDisable">
        保存并继续
      </el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>
  </div>
</template>

<script>
import { getACByAssetsTypeId } from '@/api/assetsChildren'
import { toAssetsManageAdd } from '@/api/assetsManage'
import { getItStatus } from '@/api/assetsItStatus'
import { getAllStatus } from '@/api/assetsStatusManage'
import { getAllAssetsType } from '@/api/assetsType'
import { getAllCompanies } from '@/api/assetsCompany'
import { getAllUseScope } from '@/api/assetsUseScope'
import { getAllSoftWare } from '@/api/softwaremanage'
import { getFieldsByFormId } from '@/api/assetsExpandFormField'
import Users from '@/components/UserSelectModel'
import Bumen from '@/components/bumenTree'
import dialogthree from '@/components/contractContactDia'
import dialogfour from '@/components/supplierContractDia'
import dialogfive from '@/components/assetsTypeDia'
export default {
  name: 'softwareAdd',
  components: { Users, Bumen, dialogthree, dialogfour, dialogfive },
  data() {
    return {
      isDisable: false,
      activeName: 'first',
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
      softWares: [],
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
      queryForm: {
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
        edition:'',
        path:'',
        assetsSoftwareLicence_id:'',
        assets_type_name: '',

      },
      rules: {
         child_asset: [{required: true, trigger: 'blur', message: '请选择子类',},],
         assets_name: [{required: true, trigger: 'blur', message: '请输入资产名称',},],
         assets_status: [{required: true, trigger: 'blur', message: '请选择状态',},],
         purchase_date: [{required: true, trigger: 'blur', message: '请选择购置日期',},],
         dept_name : [{required: true, trigger: 'submit', message: '请选择部门',},],
         is_secrecy: [{required: true, trigger: 'blur', message: '请选择保密状态',}],
         it_status: [{required: true, trigger: 'blur', message: '请选择IT状态',}]
      },
      title: '',
      value2: '',
      value3: '',
      value4: '',
    }
  },
  mounted() {
    this.queryForm.assets_type_id = this.$route.query.id
    this.getAssetsChildren()
    this.getAllItStatus()
    this.getItStatus()
    this.getusesCppe()
    this.getStatus()
    this.getSoftwareLicence()
    //获取配置项类型
    this.getAssetsTypes();
    //this.getFields()
  },
  methods: {
    //---------------获取下拉框选项begin---------------------------
    /*--获取配置项类型--*/
    getAssetsTypes() {
      getAllAssetsType().then((res) => {
        if (res.code == 0) {
          this.assetsTypes = res.data
          var id = this.$route.query.id;
          for (var i= 0 ; i<this.assetsTypes.length;i++){
            if (this.assetsTypes[i].id == id ){
              this.queryForm.assets_type_name = this.assetsTypes[i].name;
            }
          }
        }
      })
    },

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

    /*--获取软件许可证--*/
    getSoftwareLicence() {
      getAllSoftWare().then((res) => {
        if (res.code == 0) {
          this.softWares = res.data
        }
      })
    },
    //---------------获取下拉框选项end-----------------------------
    getFields() {
      getFieldsByFormId({ assetsTypeId: this.queryForm.assets_type_id }).then(
        (res) => {
          console.log(res)
        }
      )
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

          if (item.is_enable == 0){
            item.is_enable_name = '禁用';
          } else {
            item.is_enable_name = '启用';
          }

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

          if (item.currency ==1 ){
            item.currency_name = '人民币'
          } else {
            item.currency_name = '美元'
          }

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
    onChangePicture(file, fileList) {
      this.pictureList = fileList
    },
    handleRemove(file, fileList) {
      this.fileList = fileList
    },
    handleRemovePicture(file, fileList) {
      this.pictureList = fileList
    },
    saveok() {
      this.$refs['queryForm'].validate(async (valid) => {
        if (valid) {
          this.isDisable = true;
          const params = new FormData()
          for (let i = 0; i < this.fileList.length; i++) {
            const f = this.fileList[i]
            params.append('file', f.raw)
          }
          for (let i = 0; i < this.pictureList.length; i++) {
            const f = this.pictureList[i]
            params.append('picture', f.raw)
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
              // this.$router.push({
              //         path: '/pzgl/assetsConfigManage',
              //       })
              this.closeall();
            }
          })
        } else {
          return false
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
