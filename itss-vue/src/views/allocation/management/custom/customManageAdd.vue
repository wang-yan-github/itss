<template>
	<div class="index-container pb80">
		<el-form ref="form" :model="form" :rules="rules" label-width="140px">
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
                      <el-select v-model="form.child_asset" class="w" clearable>
                         <el-option>请选择</el-option>
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
											<el-input v-model.trim="form.assets_name" placeholder='请输入' autocomplete="off"></el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="部门" prop="dept_name" clearable>
											<el-input autocomplete="off" placeholder="请选择"  v-model.trim="form.dept_name"
												@click.native="openwinOne"></el-input>
<!--											<el-button icon="el-icon-search"-->
<!--												style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%"-->
<!--												@click="openwinOne"></el-button>-->
										</el-form-item>
									</el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="负责人">
                      <el-input v-model.trim="form.person_liable_name" placeholder="负责人"  clearable @focus="handleSolveUser('fzr')">
<!--                        <el-button slot="append" icon="el-icon-search" @click="handleSolveUser('fzr')"-->
<!--                        ></el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="领用人">
                      <el-input autocomplete="off" placeholder="领用人"   v-model.trim="form.receiver_name" @focus="handleSolveUser('lyr')"></el-input>
<!--                      <el-button icon="el-icon-search" @click="handleSolveUser('lyr')"></el-button>-->
                    </el-form-item>
                  </el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="资产状态" prop="assets_status" >

                      <el-select v-model.trim="form.assets_status" class="w" clearable>
                         <el-option>请选择</el-option>
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
                      <el-select v-model.trim="form.is_secrecy" class="w" clearable>
                         <el-option>请选择</el-option>
                        <el-option label="是" value="1"></el-option>
                        <el-option label="否" value="0"></el-option>
                      </el-select>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="购置(创建)日期" prop="purchase_date" >
                      <el-date-picker v-model="form.purchase_date" type="date" placeholder="选择日期"  style="width: 100%"
                        value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="财务编号">
											<el-input v-model.trim="form.finance_number" placeholder='财务编号' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="保密编号">
											<el-input v-model.trim="form.secrecy_number" placeholder='保密编号' autocomplete="off">
											</el-input>
										</el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="购置公司">
                      <el-select v-model.trim="form.purchase_company" class="w" clearable>
                         <el-option>请选择</el-option>
                        <el-option
                          v-for="item in companies"
                          :key="item.id"
                          :label="item.company_name"
                          :value="item.id"
                        ></el-option>                      </el-select>
                    </el-form-item>
									</el-col>
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="使用范围">
											<el-select v-model.trim="form.use_scope_id" class="w" clearable>
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
									<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
										<el-form-item label="IT状态" prop="it_status">
											<el-select v-model.trim="form.it_status" class="w" clearable>
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
								</el-card>
							</el-col>
							<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-show="customFields.length > 0">
								<el-card class="cardbox" shadow="hover">
									<div slot="header">
										<span>
											<vab-icon :icon="['fas', 'paperclip']"></vab-icon>
											拓展信息
										</span>
										<!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
									</div>

                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-for="(item,index) in this.customFields" :key="index">

                      <el-form-item v-if="item.type == 0">
                        <label slot="label">{{item.name}}</label>
                        <el-input v-model="dyFieldItems[item.id]"></el-input>
                      </el-form-item>

                      <!--长文本-->
                      <el-form-item v-if="item.type == 1">
                        <label slot="label">{{item.name}}</label>
                        <el-input type="textarea" v-model="dyFieldItems[item.id]"></el-input>
                      </el-form-item>

                      <!--整数-->
                      <el-form-item v-if="item.type == 2">
                        <label slot="label">{{item.name}}</label>
                        <el-input v-model="dyFieldItems[item.id]" type="number" placeholder="请输入内容"  onKeypress="return (/[\d]/.test(String.fromCharCode(event.keyCode)))"></el-input>
                      </el-form-item>

                      <!--小数-->
                      <el-form-item v-if="item.type == 3">
                        <label slot="label">{{item.name}}</label>
                        <el-input v-model="dyFieldItems[item.id]"  type="number" ></el-input>
                      </el-form-item>

                      <!--日期-->
                      <el-form-item v-if="item.type == 4">
                        <label slot="label">{{item.name}}</label>
                        <el-date-picker v-model="dyFieldItems[item.id]"  type="datetime" placeholder="选择日期时间">
                        </el-date-picker>
                      </el-form-item>

                      <!--单选-->
                      <el-form-item v-if="item.type == 5">
                        <label slot="label">{{item.name}}</label>
                        <el-radio-group v-model="dyFieldItems[item.id]">
                          <el-radio v-for="(fieldItem,index) in item.fieldVal" :key="fieldItem.fId" :label="fieldItem.fId">{{fieldItem.fVal}}</el-radio>
                        </el-radio-group>
                      </el-form-item>

                    <!--复选框-->
                    <el-form-item v-if="item.type == 6">

                      <label slot="label">{{item.name}} = ={{index}}</label>
                      <el-checkbox-group v-model="dyFieldItemsCheck[index-1]">
                        <el-checkbox v-for="data in item.fieldVal" :label="data.fId" :key="data.fVal">
                          {{data.fVal}}
                        </el-checkbox>
                      </el-checkbox-group>
                    </el-form-item>
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
                        :on-remove="handleRemovePicture"
                        :before-remove="beforeRemove"
                        :on-change="onChangePicture"
                        :multiple="true"
                        :limit="3"
                        :on-exceed="handleExceed"
                        :file-list="pictureList"
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
                        ref="uploadPicture"
                        class="upload-demo"
                        action=""
                        :auto-upload="false"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :before-remove="beforeRemove"
                        :on-change="onChange"
                        :multiple="true"
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
                        <div slot="tip" class="el-upload__tip">
                          附件大小上限：5.0MB
                        </div>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="用途">
                      <el-input v-model.trim="form.purpose" autocomplete="off" placeholder='请输入'
                                type="textarea" :rows="3"></el-input>
                    </el-form-item>
                    <el-form-item label="位置">
                      <el-input v-model.trim="form.position" autocomplete="off" placeholder='请输入'
                                type="textarea" :rows="3"></el-input>
                    </el-form-item>
                    <el-form-item label="备注">
                      <el-input v-model.trim="form.remark" autocomplete="off" placeholder='请输入'
                                type="textarea" :rows="3"></el-input>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>

						</el-row>
					</el-tab-pane>
          <el-tab-pane
            label="联系人"
            name="second"
          >
            <el-table
              ref="listTable"
              border
              stripe
              :data="contractContactList"
              @selection-change="setSelectContactRows"
              :element-loading-text="elementLoadingText"
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
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="contact_name"
                align="center"
                sortable
                label="姓名"
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="is_enable_name"
                align="center"
                sortable
                label="状态"
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="company_name"
                align="center"
                label="联系人公司"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="deptName"
                align="center"
                label="我司对接部门"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="email"
                align="center"
                label="Email"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="phone"
                align="center"
                label="手机"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="remark"
                align="center"
                label="备注"
                sortable
              >
              </el-table-column>
            </el-table>
            <div
              class="btn-box"
              style="margin-top: 10px;"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteContract"
              >
                移除选择对象
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAdd2"
              >
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane
            label="供应商合同"
            name="three"
          >
            <el-table
              ref="listTable"
              border
              stripe
              :data="supplierContractList"
              @selection-change="setSelectSupplierRows"
              :element-loading-text="elementLoadingText"
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
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="supplier_name"
                align="center"
                sortable
                label="供应商"
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="contract_name"
                align="center"
                sortable
                label="合同名称"
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="deptName"
                align="center"
                label="部门"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="renew_time"
                align="center"
                label="续约提醒时间"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="start_time"
                align="center"
                label="开始时间"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="end_time"
                align="center"
                label="结束时间"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="amount"
                align="center"
                label="金额"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="currency_name"
                align="center"
                label="币种"
                sortable
              >
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="status_name"
                align="center"
                label="状态"
                sortable
              >
              </el-table-column>
            </el-table>
            <div
              class="btn-box"
              style="margin-top: 10px;"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteSupplier"
              >
                移除选择对象
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAdd3"
              >
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane
            label="关联的配置项"
            name="four"
          >
            <el-table
              ref="listTable"
              border
              stripe
              :data="assetsTypeList"
              @selection-change="setSelectAssetsTypeRows"
              :element-loading-text="elementLoadingText"
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
            <div
              class="btn-box"
              style="margin-top: 10px;"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDeleteAssetsType"
              >
                移除选择对象
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-plus"
                @click="handleAdd4"
              >
                添加
              </el-button>
            </div>
          </el-tab-pane>
				</el-tabs>
			</template>
		</el-form>
		<div class="bottom">
			<el-button type="primary" size="medium" :loading="loading"  @click="saveok" :disabled="this.isDisable">{{ loadingbuttext }}</el-button>
			<el-button size="medium" @click="closeall">取消</el-button>
		</div>
		<dialogone ref="dialogone"  @deptData="getDept" @fetch-data="
			    {
			      input2
			    }
			  "></dialogone>
		<dialogtwo ref="dialogtwo" @userData="getUser" @fetch-data="
			      {
			        input2
			      }
			    "></dialogtwo>
		<dialogthree ref="dialogthree"  @listData="listAssetsContract" @fetch-data="
				    {
				      input2
				    }
				  "></dialogthree>
		<dialogfour ref="dialogfour" @listData="listAssetsSupplier" @fetch-data="
				  		    {
				  		      input2
				  		    }
				  		  "></dialogfour>
		<dialogfive ref="dialogfive"  @listData="listAssetsType" @fetch-data="
						  		    {
						  		      input2
						  		    }
						  		  "></dialogfive>
	</div>

</template>

<script>
	import { doEdit } from '@/api/userManagement'

  import { toAssetsManageAdd } from '@/api/assetsManage'
  import { getACByAssetsTypeId } from '@/api/assetsChildren'
	import dialogone from '../components/UserDiaOne'
	import dialogtwo from '../components/UserDiaTwo'
	//合同联系人
    import dialogthree from '@/components/contractContactDia'
    //合同供应商
    import dialogfour from '@/components/supplierContractDia'
    //关联的配置项
    import dialogfive from '@/components/assetsTypeDia'


  import {getAssetsType} from '@/api/configtypemanagement'
  import { getItStatus } from '@/api/assetsItStatus'
  import { getAllStatus } from '@/api/assetsStatusManage'
  import { getAllUseScope } from '@/api/assetsUseScope'
  import { getAllBrand } from '@/api/assetsBrand'
  import { getAllCompanies } from '@/api/assetsCompany'
  import { getFieldsByFormId } from '@/api/assetsExpandFormField'
  export default {
		name: 'customManageAdd',
		components: {
			dialogone,
			dialogtwo,
			dialogthree,
			dialogfour,
			dialogfive,
		},
		data() {
			return {
        isDisable: false,
        loadingbuttext: '保存并继续',
        loading: false,
				activeName: 'first',
				checked: false,
				checked1: false,
				checked2: false,
				checked3: false,
        customFields: [],
        assetsChildrenList: [],
        assetsTypes: [],
        assetsChildren: [],
        itStatus: [],
        companies: [],
        useScope: [],
        statusManage: [],
        brands: [],
        options: [],
        fileList: [],
        contracts: [],
        supplierContractList: [],
        assetsTypeList: [],
        multipleSelection: [],
        selectSupplierRows: [],
        selectAssetsTypeRows: [],
        contractContactList: [],
        typeForm:{
				  id:"",
        },
        dyFieldItems:{},
        dyFieldItemsCheck: [],
        pictureList: [],
				form: {
					username: '',
					status: '',
					num: '',
					remakes: '',
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
          extend: '',
				},
				rules: {
          child_asset: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择子类',
            },
          ],
          assets_name: [
            {
              required: true,
              trigger: 'blur',
              message: '请输入资产名称',
            },
          ],
          assets_status: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择状态',
            },
          ],
          purchase_date: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择购置日期',
            },
          ],
          dept_name : [
            {
              required: true,
              trigger: 'submit',
              message: '请选择部门',
            }
          ],
          is_secrecy: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择保密状态',
            }
          ],
          it_status: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择IT状态',
            }
          ]
				},
				title: '',
				value2: '',
				value3: '',
				value4: '',
			}
		},
		created() {
      this.form.assets_type_id  = this.$route.query.id
      this.typeForm.id  = this.$route.query.id
      this.fetchAssetsTypeData()
      this.getAssetsChildren()
      this.getAllItStatus()
      this.getItStatus()
      this.getusesCppe()
      this.getStatus()
      this.getBrand()
      this.getAllCompanies()
      // this.customFields();
    },
		methods: {

      /*--获取配置项子类--*/
      getAssetsChildren() {
        getACByAssetsTypeId({
          assets_type_id: this.form.assets_type_id,
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
			openwinOne() {
				this.$refs['dialogone'].showWin()
			},
      getDept(row) {
        this.form.dept_id = row.id
        this.form.dept_name = row.name
      },
			openwinTwo() {
				this.$refs['dialogtwo'].showWin()
			},

      handleSolveUser(type) {
        switch (type) {
          case 'lyr':
            this.selectType = 'lyr'
            this.$refs['dialogtwo'].showWin()
            break
          case 'qrr':
            this.selectType = 'qrr'
            this.$refs['dialogtwo'].showWin()
            break
          case 'clr':
            this.selectType = 'clr'
            this.$refs['dialogtwo'].showWin()
            break
          case 'fzr':
            this.selectType = 'fzr'
            this.$refs['dialogtwo'].showWin()
            break
        }
      },
      getUser(row) {
        switch (this.selectType) {
          case 'lyr':
            //领用人
            this.form.receiver = row.id
            this.form.receiver_name = row.name
            console.log("领用人" + form.su_name)
            break
          case 'fzr':
            //负责人
            this.form.person_liable = row.id
            this.form.person_liable_name = row.name
            console.log("确认人" + form.suc_name)
            break
          case 'qrr':
            //确认人
            this.form.suc_id = row.id
            this.form.suc_name = row.name
            console.log("确认人" + form.suc_name)
            break
          case 'clr':
            //处理人
            this.form.suh_id = row.id
            this.form.suh_name = row.name
            console.log("处理人" + form.suh_name)
            break
        }

      },

      openwinThree() {
				this.$refs['dialogthree'].showWin()
			},
			handleDelete() {
				if (this.selectRows != '') {
					const ids = this.selectRows.id
					this.$baseConfirm('你确定要删除选中项吗', null, async () => {
						const {
							msg
						} = await doDelete({
							ids
						})
						this.$baseMessage(msg, 'success')
						this.fetchData()
					})
				} else {
					this.$baseMessage('未选中任何行', 'error')
					return false
				}
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
     saveok() {
       this.$refs['form'].validate((v) => {
           if (v) {
             this.isDisable = true;
           this.loading = true;
           this.loadingbuttext = '保存中...';

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
        if(newarr2.length > 0){
          this.form.assetsIds = newarr2.join(',')
        }
        if (newarr1.length > 0) {
          this.form.supplierIds = newarr1.join(',')
        }
        if (newarr.length > 0) {
          this.form.contractIds = newarr.join(',')
        }
       if(this.dyFieldItemsCheck.length > 0){//单独处理checkbox
         for (var i = 0; i < this.dyFieldItemsCheck.length; i++) {
           this.dyFieldItems["cBox__"+i] = this.dyFieldItemsCheck[i];
         }
       }
        console.log(JSON.stringify(this.dyFieldItems))
        this.form.extend = JSON.stringify(this.dyFieldItems);
        params.append('body', JSON.stringify(this.form))
        console.log(params)
        toAssetsManageAdd(params).then((res) => {
          if (res.code == 0) {
            this.$baseMessage('添加成功', 'success')
            // this.$router.push({
            //   path: '/pzgl/assetsConfigManage',
            // })
            this.closeall();
          }else{
            this.$baseMessage('失败', 'error');
            this.loading = false;
            this.loadingbuttext =  '保存并继续'
          }
        })
         }
       })
     },
      //获取自定义字段
      async fetchAssetsTypeData() {
        this.listLoading = true
        console.log("==this.typeForm" + this.typeForm.id);
        const {data} = await getAssetsType(this.typeForm)
        if(data.length >0){
          this.assetsChildrenList = data[0].assetsChildrens;
          if(data[0].sysExtendFormSees != null){
            for(var i in data[0].sysExtendFormSees){
              var temp = data[0].sysExtendFormSees[i];
              if(temp.type == 5 || temp.type == 6){
                var fArray = temp.value.split("@,@")
                var arry= new Array();
                for(var j in fArray){
                  var rowJson = JSON.parse("{}");
                  rowJson.fId = j
                  rowJson.fVal = fArray[j]
                  arry.push(rowJson);
                }
                temp.fieldVal = arry
                if(temp.type == 6){
                  this.dyFieldItemsCheck.push(new Array());
                }
              }
            }
            this.customFields = data[0].sysExtendFormSees;
            console.log("=d=" + this.customFields);
          }
        }

        setTimeout(() => {
          this.listLoading = false
        }, 300)
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
      customFields(){

      },


      onChange(file, fileList) {
        // const isLt2M = file.size / 1024 / 1024 < 5
        //
        // console.log(file.size / 1024 / 1024)
        // console.log(isLt2M);
        //
        // if (!isLt2M) {
        //   this.$message({
        //     message: '上传文件大小不能超过 5MB!',
        //     type: 'warning'
        //   })
        //   return false;
        // }
        this.fileList = fileList

      },
      onChangePicture(file, fileList) {
        // var testmsg = file.name.substring(file.name.lastIndexOf('.') + 1)
        // const isLt2M = file.size / 1024 / 1024 < 5
        //
        // if (!isLt2M) {
        //   this.$refs.upload.clearFiles()
        //   this.$message({
        //     message: '上传图片大小不能超过 5MB!',
        //     type: 'warning'
        //   })
        //   return ;
        // }
        this.pictureList = fileList
      },
      handleRemove(file, fileList) {
        this.fileList = fileList
      },
      handleRemovePicture(file, fileList) {
        this.pictureList = fileList
      },
      handlePreview(file) {
        console.log(file);
      },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
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
