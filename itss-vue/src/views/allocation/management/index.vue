<template>
  <div class="index-container">
    <el-form :inline="true" :model="queryForm" @submit.native.prevent>
      <div class="hiddenmoretop" v-if="isShow">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select
                clearable="true"
                v-model="queryForm.assets_type_id"
                placeholder="配置项类型"
                @change="assetsTypeChange"
              >
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in assetsTypes"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!--        <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">-->
          <!--          <el-form-item>-->
          <!--            <el-select-->
          <!--              clearable="true"-->
          <!--              v-model="queryForm.child_asset"-->
          <!--              placeholder="配置子类"-->
          <!--            >-->
          <!--              <el-option-->
          <!--                v-for="item in assetsChildren"-->
          <!--                :key="item.id"-->
          <!--                :label="item.name"-->
          <!--                :value="item.id"-->
          <!--              ></el-option>-->
          <!--            </el-select>-->
          <!--          </el-form-item>-->
          <!--        </el-col>-->
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item>
              <el-select
                clearable="true"
                v-model="queryForm.it_status"
                placeholder="IT状态"
              >
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in itStatus"
                  :key="item.id"
                  :label="item.status_name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input
                autocomplete="off"
                placeholder="资产名称"
                clearable
                v-model="queryForm.assets_name"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input
                v-model.trim="queryForm.receiver_name"
                placeholder="领用人"
                clearable
                @clear="clearQuery('lyr')"
                @focus="handleSolveUser('receiver')"
              >
                <!-- <el-button
                slot="append"
                icon="el-icon-search"
                @click="handleSolveUser('receiver')"
              ></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox">
              <el-input
                v-model.trim="queryForm.person_liable_name"
                placeholder="负责人"
                clearable
                @clear="clearQuery('fzr')"
                @focus="handleSolveUser('liable')"
              >
                <!-- <el-button
                slot="append"
                icon="el-icon-search"
                @click="handleSolveUser('liable')"
              ></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="selbox" prop="isvip">
              <el-input
                v-model.trim="queryForm.dept_name"
                placeholder="部门"
                clearable
                @clear="clearQuery('bm')"
                @focus="handlebumen"
              >
                <!-- <el-button
                slot="append"
                icon="el-icon-search"
                @click="handlebumen"
              ></el-button> -->
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-select
                clearable="true"
                v-model="queryForm.purchase_company"
                placeholder="购置公司"
              >
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in companies"
                  :key="item.id"
                  :label="item.company_name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-select
                clearable="true"
                v-model="queryForm.use_scope_id"
                placeholder="使用范围"
              >
                <el-option>请选择</el-option>
                <el-option
                  v-for="item in useScope"
                  :key="item.id"
                  :label="item.scope_name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker
                v-model="queryForm.purchase_date_start"
                type="date"
                placeholder="(Start)购置创建日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-date-picker
                v-model="queryForm.purchase_date_end"
                type="date"
                placeholder="(End)购置创建日期"
                value-format="yyyy-MM-dd"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="3" :lg="3" :xl="3">
            <el-form-item class="textbox">
              <el-select v-model="queryForm.is_print" placeholder="已打印标签">
                <el-option>请选择</el-option>
                <el-option label="已打印" value="0"></el-option>
                <el-option label="未打印" value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <vab-query-form>
        <vab-query-form-left-panel :span="8">
          <el-button
            v-permission="pzgl_manage_add"
            icon="el-icon-plus"
            type="primary"
            @click="handleAdd"
          >
            添加
          </el-button>
          <el-button
            v-permission="pzgl_manage_inventory_select"
            icon="el-icon-s-cooperation"
            type="primary"
            @click="handleCooperation"
          >
            资产盘点管理
          </el-button>
          <!--<el-button icon="el-icon-download" type="primary" @click="handleBatch">-->
          <!--批量操作-->
          <!--</el-button>-->
          <el-button
            v-permission="pzgl_manage_print"
            icon="el-icon-printer"
            type="primary"
            @click="handleTag"
          >
            打印标签
          </el-button>
          <!--<el-dropdown trigger="hover" @command="handleTag" class="mr5">-->
          <!--<el-button>-->
          <!--打印标签-->
          <!--<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
          <!--</el-button>-->
          <!--<el-dropdown-menu slot="dropdown">-->
          <!--<el-dropdown-item icon="el-icon-edit" command="a">-->
          <!--打印标签-->
          <!--</el-dropdown-item>-->
          <!--<el-dropdown-item icon="el-icon-check" command="b">-->
          <!--批量打印-->
          <!--</el-dropdown-item>-->
          <!--</el-dropdown-menu>-->
          <!--</el-dropdown>-->

          <el-dropdown trigger="hover" @command="handleCommand">
            <el-button>
              更多
              <i class="el-icon-arrow-down el-icon--right"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                v-permission="pzgl_manage_edit"
                icon="el-icon-edit"
                command="a"
              >
                修改
              </el-dropdown-item>

              <el-dropdown-item
                v-permission="pzgl_manage_history"
                icon="el-icon-time"
                command="b"
              >
                历史记录
              </el-dropdown-item>

              <el-dropdown-item
                v-permission="pzgl_manage_deleted"
                icon="el-icon-delete"
                command="c"
              >
                删除
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="pzgl_manage_out"
                icon="el-icon-upload2"
                command="d"
              >
                导出
              </el-dropdown-item>
              <el-dropdown-item
                v-permission="pzgl_manage_import"
                icon="el-icon-download"
                command="e"
              >
                导入
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="16">
          <el-form-item>
            <el-input
              v-model.trim="queryForm.keyword"
              placeholder="资产编号"
              clearable
              @keyup.enter.native="queryData"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              v-permission="pzgl_manage_select"
              icon="el-icon-search"
              type="primary"
              @click="queryData"
            >
              查询
            </el-button>
            <el-button
              v-permission="pzgl_manage_select"
              icon="el-icon-d-arrow-left"
              @click="moreQuery"
              v-if="isShow"
            >
              高级
            </el-button>
            <el-button
              v-permission="pzgl_manage_select"
              icon="el-icon-d-arrow-right"
              @click="moreQuery"
              v-else
            >
              高级
            </el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </vab-query-form>
    </el-form>

    <el-table
      v-loading="listLoading"
      :data="list"
      border
      highlight-current-row
      :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows"
    >
      <el-table-column
        show-overflow-tooltip
        type="selection"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        label="序号"
        sortable
        align="center"
        width="80px;"
      >
        <template slot-scope="scope">
          {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assets_number"
        label="资产编号"
        sortable
        align="center"
      >
        <template #default="{ row }">
          <el-link @click="handlesee(row)">{{ row.assets_number }}</el-link>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assets_name"
        label="资产名称"
        sortable
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assetsTypeName"
        label="配置项类型"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="childAssetName"
        label="配置子类"
        align="center"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="deptName"
        label="部门"
        align="center"
        width="110px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="receiverName"
        label="领用人"
        align="center"
        width="100px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="purchaseCompanyName"
        label="购置公司"
        align="center"
        width="150px;"
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="is_secrecy"
        label="保密"
        align="center"
        width="100px;"
      >
        <template #default="scope">
          <el-tag
            size="mini"
            icon="el-icon-edit"
            v-if="scope.row.is_secrecy == '0'"
            type="primary"
          >
            非保密
          </el-tag>
          <el-tag
            size="mini"
            icon="el-icon-delete"
            class="red"
            v-if="scope.row.is_secrecy == '1'"
            type="success"
          >
            保密
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="assetsStatusName"
        label="资产状态"
        align="center"
        width="100px;"
      >
        <template #default="scope">
          <el-tag size="mini" icon="el-icon-delete" class="red" type="success">
            {{ scope.row.assetsStatusName }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      background
      :current-page="queryForm.pageNo"
      :page-size="queryForm.pageSize"
      :layout="layout"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"
    ></el-pagination>
    <users ref="users" @userData="getUser" @fetch-data="fetchData"></users>
    <bumen ref="bumen" @deptData="getDept" @fetch-data="fetchData"></bumen>
    <userimport ref="userimport"></userimport>
    <historyImport ref="historyImport"></historyImport>
    <el-dialog
      title="打印提示"
      :visible.sync="dialogVisible"
      width="500px"
      center
    >
      <span class="print-tips">确定打印此标签吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" v-print="printObj" @click="printShow">
          确 定
        </el-button>
      </span>
    </el-dialog>
    <div v-show="false">
      <div id="printTest" class="print-table">
        <table>
          <tbody>
            <tr>
              <td colspan="4" class="title-tb">基础信息</td>
            </tr>
            <tr>
              <td class="sub-tb">资产编号</td>
              <td>{{ form.assets_number }}</td>
              <td class="sub-tb">ID</td>
              <td>{{ form.id }}</td>
            </tr>
            <tr>
              <td class="sub-tb">配置项类型</td>
              <td>{{ form.assetsTypeName }}</td>
              <td class="sub-tb">配置子类</td>
              <td>{{ form.childAssetName }}</td>
            </tr>
            <tr>
              <td class="sub-tb">资产名称</td>
              <td>{{ form.assets_name }}</td>
              <td class="sub-tb">部门</td>
              <td>{{ form.dept_name }}</td>
            </tr>
            <tr>
              <td class="sub-tb">负责人</td>
              <td>{{ form.person_liable_name }}</td>
              <td class="sub-tb">领用人</td>
              <td>{{ form.receiver_name }}</td>
            </tr>
            <tr>
              <td class="sub-tb">资产状态</td>
              <td>{{ form.assetsStatusName }}</td>
              <td class="sub-tb">保密</td>
              <td>{{ form.is_secrecy == 0 ? '否' : '是' }}</td>
            </tr>
            <tr>
              <td class="sub-tb">购置(创建)日期</td>
              <td>{{ form.purchase_date }}</td>
              <td class="sub-tb">财务编号</td>
              <td>{{ form.finance_number }}</td>
            </tr>
            <tr>
              <td class="sub-tb">保密编号</td>
              <td>{{ form.secrecy_number }}</td>
              <td class="sub-tb">购置公司</td>
              <td>{{ form.purchaseCompanyName }}</td>
            </tr>
            <tr>
              <td class="sub-tb">使用范围</td>
              <td>{{ form.useScopeName }}</td>
              <td class="sub-tb">IT状态</td>
              <td>{{ form.itStatusName }}</td>
            </tr>
            <!--<tr>-->
            <!--<td class="sub-tb">二维码</td>-->
            <!--<td colspan="3">-->
            <!--<el-form-item label="" class="mb0">-->
            <!--<el-image :src="form.qrCode" style="width: 140px; height: 140px"></el-image>-->
            <!--</el-form-item>-->
            <!--</td>-->
            <!--</tr>-->
            <tr v-show="form.show">
              <td colspan="4" class="title-tb">属性信息</td>
            </tr>
            <tr v-if="form.show == true">
              <td class="sub-tb" v-if="null != form.brandName">品牌</td>
              <td v-if="null != form.brandName" colspan="3">
                {{ form.brandName }}
              </td>
            </tr>
            <tr v-if="form.show == true">
              <td class="sub-tb" v-if="'' != form.model">型号</td>
              <td v-if="'' != form.model" colspan="3">{{ form.model }}</td>
            </tr>
            <tr v-if="form.show == true">
              <td class="sub-tb" v-if="'' != form.sn">序列号(SN号)</td>
              <td v-if="'' != form.sn" colspan="3">{{ form.sn }}</td>
            </tr>
            <tr v-if="form.show == true">
              <td class="sub-tb" v-if="null != form.cabinet_name">机柜</td>
              <td v-if="null != form.cabinet_name" colspan="3">
                {{ form.cabinet_name }}
              </td>
            </tr>
            <tr v-if="form.show == true">
              <td class="sub-tb" v-if="null != form.start_time">
                维保开始日期
              </td>
              <td v-if="null != form.start_time" colspan="3">
                {{ form.start_time }}
              </td>
            </tr>
            <tr v-if="form.show == true">
              <td class="sub-tb" v-if="null != form.end_time">维保结束日期</td>
              <td v-if="null != form.end_time" colspan="3">
                {{ form.end_time }}
              </td>
            </tr>
          </tbody>
        </table>

        <div class="box_card">
          <!--<div style="margin: 0 auto; text-align: center" class="title-tb">二维码</div>-->
          <div class="box_content" style="margin: 0 auto; text-align: center">
            <el-image
              :src="form.qrCode"
              style="width: 140px; height: 140px"
            ></el-image>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getAssetsManageList,
  delAssets,
  getAssetsManageById,
  changePrint,
  exportTemplate
} from '@/api/assetsManage'
import { getACByAssetsTypeId } from '@/api/assetsChildren'
import { getItStatus } from '@/api/assetsItStatus'
import { getAllAssetsType } from '@/api/assetsType'
import { getAllCompanies } from '@/api/assetsCompany'
import { getAllUseScope } from '@/api/assetsUseScope'
import Users from '@/components/UserSelectModel'
import Bumen from '@/components/bumenTree'
import Userimport from './components/UserImport'
import historyImport from './components/history'
import { exportEventExcel } from '@/api/eventAllManage'
import permission from '@/directive/permission/index.js'
export default {
  name: 'myknowledgeadd',
  directives: { permission },
  components: {
    Userimport,
    Users,
    Bumen,
    historyImport,
  },
  data() {
    return {
      time_range: '',
      assetsTypes: [],
      assetsChildren: [],
      itStatus: [],
      companies: [],
      useScope: [],
      checked: false,
      isShow: false,
      list: [],
      listLoading: true,
      loading: false,
      options: [],
      selectedData: [],
      value: '',
      value1: '',
      value2: '',
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      selectType: '',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        assets_type_id: '', //配置类型
        child_asset: '', //配置子类
        it_status: '', //it状态
        assets_name: '', //资产名称
        receiver: '', //领用人
        person_liable: '', //负责人
        dept_id: '', //部门
        dept_name: '',
        purchase_company: '', //购置公司
        use_scope_id: '', //使用范围
        purchase_date_start: '', //购置开始日期
        purchase_date_end: '', //购置结束日期
        receiver_name: '',
        person_liable_name: '',
        keyword: '',
        is_print: '',
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
        show: true,
        qrCode: '',
      },
      printObj: {
        id: 'printTest',
        popTitle: '&nbsp;', //页眉标题 默认浏览器标题 空字符串时显示undefined 使用html语言
        extraHead: '<meta http-equiv="Content-Language"content="zh-cn"/>', //头部文字 默认空
      },
      test: '哈哈哈',
      dialogVisible: false,
      src: '',
    }
  },
  mounted() {
    this.queryForm.assets_type_id = this.$route.params.assets_type_id //undefined
    this.fetchData()
    this.getAssetsTypes()
    this.getAllItStatus()
    this.getCompanies()
    this.getusesCppe()
  },
  methods: {
    // timechange() {
    //   if (null == form.time || [] == form.time || '' == form.time) {
    //     this.queryForm.purchase_date_start = ''
    //     this.queryForm.purchase_date_end = ''
    //   }
    //   this.queryForm.purchase_date_start = this.time_range[0]
    //   this.queryForm.purchase_date_end = this.time_range[1]
    // },
    //---------------获取下拉框选项begin---------------------------

    /*--获取配置项类型--*/
    getAssetsTypes() {
      getAllAssetsType().then((res) => {
        if (res.code == 0) {
          this.assetsTypes = res.data
        }
      })
    },

    /*--获取配置项子类--*/
    assetsTypeChange(data) {
      console.log(data)
      getACByAssetsTypeId({ assets_type_id: data }).then((res) => {
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
    getCompanies() {
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
    //---------------获取下拉框选项end-----------------------------
    handleSolveReceicer() {
      this.selectType = 'receiver'
      this.$refs['users'].showWin()
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
      }
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
      }
    },
    handlebumen() {
      this.$refs['bumen'].showWin()
    },
    getDept(row) {
      this.queryForm.dept_id = row.id
      this.queryForm.dept_name = row.name
    },
    setSelectRows(val) {
      this.selectedData = val
    },
    handleCommand(command) {
      switch (command) {
        case 'a':
          this.handleEdit()
          break
        case 'b':
          this.handleAction()
          break
        case 'c':
          this.handleDelete()
          // this.handleDisable()
          break
        case 'd':
          this.$baseConfirm('你确定要导出这些信息吗', null, async () => {
            this.exportAssetsExcel()
          })
          break
        case 'e':
          this.$refs['userimport'].showWin()
          break
        default:
          //这里是没有找到对应的值处理
          break
      }
    },
    exportAssetsExcel() {
      exportTemplate(this.queryForm).then((res) => {
        const url = URL.createObjectURL(new Blob([res]))
        const link = document.createElement('a')
        link.href = url
        link.href = url
        link.setAttribute('download', '配置项导出.xls')
        document.body.appendChild(link)
        link.click()
      })
    },
    printShow() {
      this.dialogVisible = false
    },
    async handleTag() {
      console.log(this.selectedData)
      if (this.selectedData.length == 0) {
        this.$baseMessage('未选中任何行', 'error')
        return false
      } else if (this.selectedData.length > 1) {
        this.$baseMessage('请选择一条数据', 'error')
        return false
      } else {
        const val = this.selectedData[0].id
        this.dialogVisible = true
        this.test = '选中后的新值'
        const { data } = await getAssetsManageById(val)
        console.log(data)
        this.changeOnePrint(val)
        this.form = data
        this.form.qrCode = 'data:image/png;base64,' + data.qrCode
        if (
          (null == data.brandName || '' == data.brandName) &&
          (null == data.model || '' == data.model) &&
          (null == data.sn || '' == data.sn) &&
          (null == data.cabinet_name || '' == cabinet_name) &&
          (null == start_time || '' == start_time) &&
          (null == end_time || '' == end_time)
        ) {
          this.form.show = false
        } else {
          this.form.show = true
        }
        this.form.is_secrecy = data.is_secrecy + ''
      }
    },
    // openwinOne() {
    //   this.$refs['dialogone'].showWin()
    // },
    // openwinTwo() {
    //   this.$refs['dialogtwo'].showWin()
    // },
    // openwinThree() {
    //   this.$refs['dialogthree'].showWin()
    // },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleAdd() {
      this.$router.push({
        path: '/pzgl/manageSelAdd',
      })
    },
    handleCooperation() {
      this.$router.push({
        path: '/pzgl/capitalIndex',
      })
    },
    handleView() {
      this.$router.push({
        path: '/book/myKnowledgeView',
      })
    },
    handleBatch() {},
    // 判断是否为空
    isEmpty(obj) {
      if (typeof obj == 'undefined' || obj == null || obj == '') {
        return true
      } else {
        return false
      }
    },
    handleEdit() {
      console.log(this.selectedData)
      if (this.selectedData.length == 1) {
        const ids = this.selectedData[0].id
        const assets_type_id = this.selectedData[0].assets_type_id
        console.log('配置项编辑参数 ：' + ids + '===' + assets_type_id)
        if (this.isEmpty(this.selectedData[0].edit_address)) {
          this.$message.error('请先配置修改地址')
          return
        }
        this.$router.push({
          path: this.selectedData[0].edit_address,
          query: { id: ids, assets_type_id: assets_type_id },
        })
      } else {
        if (this.selectedData.length == 0) {
          this.$baseMessage('未选中任何行', 'error')
          return false
        } else {
          this.$baseMessage('请选择一条数据', 'error')
          return false
        }
      }
    },
    handlesee(row) {
      if (row.id != '' && row.id != null) {
        const assets_type_id = row.assets_type_id
        this.$router.push({
          path: row.check_address,
          query: { id: row.id, assets_type_id: assets_type_id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleCheck() {
      this.$router.push({
        path: '/book/examineCheck',
      })
    },
    handleDelete() {
      if (this.selectedData.length == 0) {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
      this.$baseConfirm('确定要删除该条记录？', null, async () => {
        let newarr = this.selectedData.map((item) => {
          return item.id
        })
        const ids = newarr.join(',')
        delAssets(ids).then((res) => {
          if (res.code == 0) {
            if (res.data) {
              this.$baseMessage('删除成功', 'success')

              // 为了在删除最后一页的最后一条数据时能成功跳转回最后一页的上一页
              const totalPage = Math.ceil((this.total - 1) / this.queryForm.pageSize) // 总页数
              console.log(totalPage)
              this.queryForm.pageNo = this.queryForm.pageNo > totalPage ? totalPage : this.queryForm.pageNo
              this.queryForm.pageNo = this.queryForm.pageNo < 1 ? 1 : this.queryForm.pageNo
              console.log(totalPage)

              this.fetchData()
            } else {
              this.$baseMessage('删除失败', 'error')
            }
          }
        })
      })
    },
    handleAction() {
      //历史记录
      if (this.selectedData.length == 0) {
        this.$baseMessage('未选中任何行', 'error')
        return false
      } else if (this.selectedData.length > 1) {
        this.$baseMessage('请选择一条数据', 'error')
        return false
      } else {
        const ids = this.selectedData[0].id
        this.$refs['historyImport'].showWin(ids)
      }
    },
    handleDisable() {
      if (this.selectRows != '' && this.selectRows != null) {
        const ids = this.selectRows.id
        this.$baseConfirm('确定要禁用该条记录？', null, async () => {
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
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
    clearQuery(type) {
      switch (type) {
        case 'fzr':
          this.queryForm.person_liable = ''
          break
        case 'lyr':
          this.queryForm.receiver = ''
          break
        case 'bm':
          this.queryForm.dept_id = ''
          break
      }
    },
    async fetchData() {
      this.listLoading = true
      console.log(this.queryForm)
      const res = await getAssetsManageList(this.queryForm)
      this.list = res.data.list
      this.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async changeOnePrint(val) {
      const { data } = await changePrint(val)
      console.log(data)
    },
  },
}
</script>
<style media="printTest">
@media print {
  /*最外层打印节点*/
  #printTest {
    display: block;
    height: auto;
    overflow: hidden;
  }
  .print-table table {
    width: 100%;
    border-collapse: collapse;
  }
  table,
  td {
    border: 1px solid black;
  }
  .print-table table td {
    padding: 10px 6px;
    line-height: 18px;
    font-size: 14px;
  }
  .title-tb {
    text-align: center;
    font-weight: bold;
    background-color: #efefef;
  }
  .sub-tb {
    width: 100px;
    text-align: right;
  }
}
</style>
<style lang="scss" scoped>
.index-container {
  .hiddenmoretop {
    ::v-deep {
      .el-form-item {
        margin-bottom: 15px;
      }
      .el-date-editor.el-input {
        width: 100%;
      }
      .el-date-editor.el-input--prefix .el-input__inner {
        padding-right: 15px;
      }
    }
  }

  .mr5 {
    margin-right: 5px;
  }
}
::v-deep {
  .el-dialog__body,
  .el-dialog__footer {
    border-top: none !important;
  }
  .print-tips {
    display: inline-block;
    width: 100%;
    text-align: center;
    font-size: 16px;
  }
}
</style>
