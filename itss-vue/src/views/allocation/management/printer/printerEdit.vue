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
                    <el-form-item label="ID">
                      {{ form.id }}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="资产编号">
                      {{ form.assets_number }}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="配置项类型">
                      {{ form.assetsTypeName }}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="配置子类">
                      {{ form.childAssetName }}
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="资产名称" prop="assets_name">
                      <el-input v-model.trim="form.assets_name" placeholder='请输入' autocomplete="off">
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="部门" prop="dept_id">
                      <el-input
                        v-model.trim="form.dept_name"
                        placeholder="部门"
                        clearable
                        @click.native="handlebumen"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="handlebumen"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="负责人">
                      <el-input
                        v-model.trim="form.person_liable_name"
                        placeholder="负责人"
                        clearable
                        @click.native="handleSolveUser('liable')"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="handleSolveUser('liable')"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="领用人">
                      <el-input
                        v-model.trim="form.receiver_name"
                        placeholder="领用人"
                        clearable
                        @click.native="handleSolveUser('receiver')"
                      >
                        <!--<el-button-->
                          <!--slot="append"-->
                          <!--icon="el-icon-search"-->
                          <!--@click="handleSolveUser('receiver')"-->
                        <!--&gt;</el-button>-->
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="资产状态" prop="status">
                      <el-select v-model.trim="form.assets_status" class="w">
                        <el-option
                          v-for="item in assetsStatusList"
                          :key="item.id"
                          :label="item.status_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="保密">
                      <el-select v-model.trim="form.is_secrecy" class="w">
                        <el-option label="是" value="1"></el-option>
                        <el-option label="否" value="0"></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="购置(创建)日期" prop="data1">
                      <el-date-picker v-model="form.purchase_date" type="date" placeholder="选择日期"
                                      style="width: 100%;">
                      </el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="财务编号">
                      <el-input v-model.trim="form.finance_number" placeholder='请输入' autocomplete="off">
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="保密编号">
                      <el-input v-model.trim="form.secrecy_number" placeholder='请输入' autocomplete="off">
                      </el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="购置公司">
                      <el-select v-model.trim="form.purchase_company" class="w">
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
                      <el-select v-model.trim="form.use_scope_id" class="w">
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
                      <el-select v-model.trim="form.it_status" class="w">
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
                    <el-form-item label="品牌">
                      <el-select
                        v-model.trim="form.brand_id"
                        class="w"
                      >
                        <el-option
                          v-for="item in assetsBrandList"
                          :key="item.id"
                          :label="item.brand_name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="型号">
                      <el-input
                        v-model.trim="form.model"
                        placeholder="请输入型号"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="序列号（SN号）">
                      <el-input
                        v-model.trim="form.sn"
                        placeholder="请输入序列号（SN号）"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item
                      label="维保开始日期"
                      prop="data1"
                    >
                      <el-date-picker
                        v-model="form.start_time"
                        type="date"
                        placeholder="选择日期"
                        style="width: 100%"
                        value-format="yyyy-MM-dd HH:mm:ss"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item
                      label="维保结束日期"
                      prop="data1"
                    >
                      <el-date-picker
                        v-model="form.end_time"
                        type="date"
                        placeholder="选择日期"
                        style="width: 100%"
                        value-format="yyyy-MM-dd HH:mm:ss"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="硒鼓型号">
                      <el-input
                        v-model.trim="form.selenium_drum_model"
                        placeholder="请输入硒鼓型号"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="MAC地址">
                      <el-input
                        v-model.trim="form.mac"
                        placeholder="请输入MAC地址"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="IP地址">
                      <el-input
                        v-model.trim="form.ip"
                        placeholder="请输入IP地址"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="无线MAC地址">
                      <el-input
                        v-model.trim="form.wireless_mac"
                        placeholder="请输入无线MAC地址"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                    <el-form-item label="无线IP地址">
                      <el-input
                        v-model.trim="form.wireless_ip"
                        placeholder="请输入无线IP地址"
                        autocomplete="off"
                      ></el-input>
                    </el-form-item>
                  </el-col>
                </el-card>
              </el-col>
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
                        v-model="form.assets_type_id"
                        placeholder="配置项类型"
                        @change="assetsTypeChange"
                      >
                        <el-option
                          v-for="item in assetsTypes"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                  <!--<el-form-item label="字段一">-->
                  <!--<el-select v-model.trim="form.status" class="w">-->
                  <!--<el-option label="是" value="0"></el-option>-->
                  <!--<el-option label="否" value="1"></el-option>-->
                  <!--</el-select>-->
                  <!--</el-form-item>-->
                  <!--</el-col>-->
                  <!--<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">-->
                  <!--<el-form-item label="字段二">-->
                  <!--<el-select v-model.trim="form.status" class="w">-->
                  <!--<el-option label="是" value="0"></el-option>-->
                  <!--<el-option label="否" value="1"></el-option>-->
                  <!--</el-select>-->
                  <!--</el-form-item>-->
                  <!--</el-col>-->

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
                        :on-preview="handlePreview"
                        :on-remove="handleRemovePicture"
                        :on-change="onChangePicture"
                        :multiple="true"
                        :limit="3"
                        :file-list="pictureList"
                        :data="form"
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
                        :data="form"
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
          <el-tab-pane label="联系人" name="second">
            <el-table ref="listTable" border stripe :data="contractContactList" @selection-change="setSelectContactRows"
                      :element-loading-text="elementLoadingText" highlight-current-row>
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="contact_name" align="center" sortable label="姓名">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="is_enable_name" align="center" sortable label="状态">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="company_name" align="center" label="联系人公司" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="deptName" align="center" label="我司对接部门" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="email" align="center" label="Email" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="phone" align="center" label="手机" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="remark" align="center" label="备注" sortable>
              </el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteContract">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd2">
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="供应商合同" name="three">
            <el-table ref="listTable" border stripe :data="supplierContractList"
                      @selection-change="setSelectSupplierRows"
                      :element-loading-text="elementLoadingText" highlight-current-row>
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" sortable align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="supplier_name" align="center" sortable label="供应商">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="contract_name" align="center" sortable label="合同名称">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="deptName" align="center" label="部门" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="renew_time" align="center" label="续约提醒时间" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="start_time" align="center" label="开始时间" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="end_time" align="center" label="结束时间" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="amount" align="center" label="金额" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="currency_name" align="center" label="币种" sortable>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="status_name" align="center" label="状态" sortable>
              </el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteSupplier">
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd3">
                添加
              </el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane label="关联的配置项" name="four">
            <el-table ref="listTable" border stripe :data="assetsTypeList" @selection-change="setSelectAssetsTypeRows"
                      :element-loading-text="elementLoadingText" highlight-current-row>
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="assets_number" label="资产编号" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="assetsTypeName" label="配置项类型"></el-table-column>
              <el-table-column show-overflow-tooltip prop="childAssetName" label="配置子类" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="assets_name" label="资产名称" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="deptName" label="部门" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="receiverName" label="领用人" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="purchaseCompanyName" label="购置公司" sortable
                               align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="is_secrecy" label="保密" sortable
                               align="center">
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

              <el-table-column show-overflow-tooltip prop="assetsStatusName" label="资产状态" sortable
                               align="center">
                <template #default="scope">
                  <el-tag size="mini" icon="el-icon-delete" class="red" type="success">
                    {{ scope.row.assetsStatusName }}
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column show-overflow-tooltip prop="itStatusName" label="IT状态" sortable
                               align="center"></el-table-column>
            </el-table>
            <div class="btn-box" style="margin-top: 10px;">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteAssetsType">
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
    <div class="bottom">
      <el-button type="primary" size="medium" @click="saveok">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>
    <!--<dialogone ref="dialogone" @fetch-data="-->
    <!--{-->
    <!--input2-->
    <!--}-->
    <!--"></dialogone>-->
    <!--<dialogtwo ref="dialogtwo" @fetch-data="-->
    <!--{-->
    <!--input2-->
    <!--}-->
    <!--"></dialogtwo>-->
    <dialogthree ref="dialogthree" @listData="listAssetsContract" @fetch-data="
    {
    value2
    }
    "></dialogthree>
    <dialogfour ref="dialogfour" @listData="listAssetsSupplier" @fetch-data="
    {
    value2
    }
    "></dialogfour>
    <dialogfive ref="dialogfive" @listData="listAssetsType" @fetch-data="
    {
    value2
    }
    "></dialogfive>

    <users ref="users" @userData="getUser" @fetch-data="{value2}"></users>
    <bumen ref="bumen" @deptData="getDept" @fetch-data="{value2}"></bumen>
  </div>

</template>

<script>
  //配置管理新增保存接口
  import {toAssetsManageEdit, getAssetsManageById} from '@/api/assetsManage'
  //获取配置项子类
  import {getACByAssetsTypeId} from '@/api/assetsChildren'
  //获取it状态
  import {getItStatus} from '@/api/assetsItStatus'
  //获取配置项
  import {getAllAssetsType} from '@/api/assetsType'
  //获取购置公司
  import {getAllCompanies} from '@/api/assetsCompany'
  //获取使用范围
  import {getAllUseScope} from '@/api/assetsUseScope'
  //获取品牌
  import {getAssetsBrandList} from '@/api/assetsBrand'
  //获取资产状态
  import {getAllStatus} from '@/api/assetsStatusManage'

  //引用领用人、负责人弹窗页面
  import Users from '@/components/UserSelectModel'
  //引用部门弹窗页面
  import Bumen from '@/components/bumenTree'

  // import dialogone from './components/UserDiaOne'
  // import dialogtwo from './components/UserDiaTwo'
  import dialogthree from '@/components/contractContactDia'
  import dialogfour from '@/components/supplierContractDia'
  import dialogfive from '@/components/assetsTypeDia'
  import {download} from "@/api/file";

  export default {
    name: 'printerEdit',
    components: {
      Users,
      Bumen,
      // dialogone,
      // dialogtwo,
      dialogthree,
      dialogfour,
      dialogfive,
    },
    data() {
      return {
        activeName: 'first',
        checked: false,
        checked1: false,
        checked2: false,
        checked3: false,
        options: [],
        assetsTypes: [],//配置项
        assetsChildren: [],//配置项子类
        itStatus: [], //it状态
        companies: [], //购置公司
        useScope: [], //使用范围
        assetsBrandList: [],//获取品牌
        assetsStatusList: [],//获取资产状态
        selectType: '',//选择类型 领用人、负责人
        contractContactList: [],//合同联系人
        supplierContractList: [],//供应商合同
        assetsTypeList: [],//关联的配置项
        selectContactRows: '',//合同联系人 选中行
        selectSupplierRows: '',//供应商合同 选中行
        selectAssetsTypeRows: '',//关联的配置项 选中行
        fileList: [],
        pictureList: [],
        form: {
          manage_id:'',
          dept_name: '', //部门名称
          receiver_name: '', //领用人名称
          person_liable_name: '', //负责人名称
          id: '',
          assets_number: '',                         //     资产编号
          assets_type_id: '',                         //     配置项类型id
          child_asset: '',                            //     配置子类
          assets_name: '',                             //     资产名称
          dept_id: '',                                //     部门
          person_liable: '',                           //     负责人
          receiver: '',                                //     领用人
          assets_status: '',                          //     资产状态
          is_secrecy: '',                             //     保密
          purchase_date: '',                             //     购置（创建）日期
          finance_number: '',                          //     财务编号
          secrecy_number: '',                          //     保密编号
          purchase_company: '',                       //     购置公司
          use_scope_id: '',                           //     使用范围
          it_status: '',                              //     IT状态
          purpose: '',                                //     用途
          position: '',                                //     位置
          remark: '',                                  //     备注
          brand_id: '',                               //     品牌
          model: '',                                   //     型号
          sn: '',                                      //     序列号（SN）
          start_time: '',                                //     维保开始日期
          end_time: '',                                  //     维保结束日期
          cabinet_id: '',                             //     机柜
          start_seat: '',                              //     起始机位
          end_seat: '',                                //     终止机位
          selenium_drum_model: '',                     //     硒鼓型号
          mac: '',                                     //     MAC地址
          ip: '',                                      //     IP地址
          wireless_mac: '',                            //     无线MAC地址
          wireless_ip: '',                             //     无线IP地址
          nbu: '',                                     //     NBU
          mac2: '',                                    //     MAC地址2
          mac3: '',                                    //     MAC地址3
          ip2: '',                                     //     IP地址2
          cpu: '',                                     //     CPU
          memory: '',                                  //     内存
          hard_disk: '',                               //     硬盘
          server_config: '',                           //     服务器配置
          os_id: '',                                  //     操作系统
          is_invirtual: '',                           //     是否纳入虚拟化资源池
          server_code: '',                             //     服务编号/快速服务代码
          os_licence_id: '',                          //     OS许可证
          allocated_capacity: '',                      //     分配容量
          raid: '',                                    //     Raid信息
          capacity: '',                                //     总容量
          os_edition: '',                              //     系统版本
          server_id: '',                              //     服务器
          farm_id: '',                                //     farm
          phone: '',                                   //     电话号码
          imei: '',                                    //     IMEI
          pin: '',                                     //     硬件PIN
          os_install_time: '',                           //     系统安装时间
          harddisk_serial: '',                         //     硬盘序列号
          software_licence_id: '',                    //     软件许可证
          path: '',                                    //     路径
          edition: '',                                 //     版本
          system_name: '',                             //     系统名称
          url: '',                                     //     URL
          databaseName: '',                               //     数据库
          extranet_ip: '',                             //     外网IP
          intranet_ip: '',                             //     内网IP
          mid_name_edition: '',                        //     中间件名称及版本
          software_manufacturer: '',                   //     软件厂商名称
          implementation_manufacturer: '',             //     实施厂商名称
          js_liable: '',                               //     应用系统技术负责人
          js_phone: '',                                //     技术负责人联系电话
          yw_liable: '',                               //     系统业务负责人
          yw_phone: '',                                //     业务负责人联系电话
          is_enable: '',                              //     启用类型
          backup_id: '',                              //     备份方式
          online_time: '',                               //     系统上线时间
          extend: '',                                  //     扩展字段
          is_inventory: '',                           // 是否已生成资产盘点 0 生成 1.未生成
          deptName: '',//部门名称
          assetsTypeName: '',                         //     配置项类型id 对应的名称
          childAssetName: '',                            //     配置子类 对应的名称
          receiverName: '',                            //     配置子类 对应的名称
          purchaseCompanyName: '',                            //     公司
          purchase_date_start: '',                      //购置日期开始
          purchase_date_end: '',                        //购置日期结束
          assetsStatusName: '',
        },
        rules: {
          assets_name: [{
            required: true,
            trigger: 'blur',
            message: '资产名称'
          },],
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
        url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
        srcList: [
          'https://fuss10.elemecdn.com/8/27/f01c15bb73e1ef3793e64e6b7bbccjpeg.jpeg',
          'https://fuss10.elemecdn.com/1/8e/aeffeb4de74e2fde4bd74fc7b4486jpeg.jpeg',
        ],
      }
    },
    created() {
      //页面传参配置项类型id
      this.form.manage_id = this.$route.query.id
      const query = this.$route.query;
      this.getData();
      //获取配置项类型
      this.getAssetsTypes();
      //获取配置项子类
      this.assetsTypeChange(query.id);
      //获取it状态
      this.getAllItStatus();
      //获取购置公司
      this.getItStatus();
      //获取使用范围
      this.getusesCppe();
      //获取品牌
      this.getBrandList();
      //获取资产状态
      this.getStatusList();
    },
    methods: {
      //初始化数据
      getData() {
        getAssetsManageById(this.form.manage_id).then((res) => {
          if (res.code == 0) {
            this.form = res.data
            this.form.is_secrecy =res.data.is_secrecy + "";

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
            if (res.data.fileManageList) {
              res.data.fileManageList.forEach((x) => {
                this.fileList.push({ name: x.file_name, url: x.id })
              })
            }
            if (res.data.pictureList) {
              res.data.pictureList.forEach((x) => {
                this.pictureList.push({ name: x.file_name, url: x.id })
              })
            }
            this.getAssetsChildren()
            //this.fileList = res.data.fileManageList
          }
        })
      },
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
        getACByAssetsTypeId({assets_type_id: data}).then((res) => {
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

      /*--获取使用范围--*/
      getusesCppe() {
        getAllUseScope().then((res) => {
          if (res.code == 0) {
            this.useScope = res.data
          }
        })
      },
      /*--获取品牌--*/
      getBrandList() {
        getAssetsBrandList().then((res) => {
          if (res.code == 0) {
            this.assetsBrandList = res.data
          }
        })
      },
      /*--获取资产状态--*/
      getStatusList() {
        getAllStatus().then((res) => {
          if (res.code == 0) {
            this.assetsStatusList = res.data
          }
        })
      },

      //---------------获取下拉框选项end-----------------------------
      // openwinOne() {
      //   this.$refs['dialogone'].showWin()
      // },
      // openwinTwo() {
      //   this.$refs['dialogtwo'].showWin()
      // },
      // openwinThree() {
      //   this.$refs['dialogthree'].showWin()
      // },
      //领用人、负责人点击弹窗事件
      handleSolveUser(type) {
        switch (type) {
          case 'receiver':
            this.selectType = 'receiver';
            this.$refs['users'].showWin();
            break;
          case 'liable':
            this.selectType = 'liable';
            this.$refs['users'].showWin();
        }
      },
      //领用人、负责人选择，数据渲染
      getUser(row) {
        switch (this.selectType) {
          case 'receiver'://领用人
            this.form.receiver = row.id;
            this.form.receiver_name = row.name;
            break;
          case 'liable'://负责人
            this.form.person_liable = row.id;
            this.form.person_liable_name = row.name;
            break
        }
      },
      //部门弹窗
      handlebumen() {
        this.$refs['bumen'].showWin();
      },
      //部门选中 参数回显
      getDept(row) {
        this.form.dept_id = row.id;
        this.form.dept_name = row.name;
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
      //合同联系人 弹窗页面
      handleAdd2() {
        this.$refs['dialogthree'].showWin()
      },
      //供应商合同 弹窗页面
      handleAdd3() {
        this.$refs['dialogfour'].showWin()
      },
      //配置项类型 弹窗页面
      handleAdd4() {
        this.$refs['dialogfive'].showWin()
      },
      //合同联系人 选中事件
      setSelectContactRows(val) {
        this.selectContactRows = val
      },
      //合同联系人 选中事件
      setSelectSupplierRows(val) {
        this.selectSupplierRows = val
      },
      //合同联系人 选中事件
      setSelectAssetsTypeRows(val) {
        this.selectAssetsTypeRows = val
      },
      //合同联系人 选中数据回显
      listAssetsContract(rows) {
        console.log("合同联系人 选中数据回显")
        console.log(rows)
        rows.forEach((item, i) => {
          let index = this.contractContactList.findIndex((ele) => {
            return ele.id === item.id;
          });
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
      //供应商合同 选中数据回显
      listAssetsSupplier(rows) {
        console.log("供应商合同 选中数据回显")
        console.log(rows)
        rows.forEach((item, i) => {
          let index = this.supplierContractList.findIndex((ele) => {
            return ele.id === item.id;
          });
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
      //关联的配置项 选中数据回显
      listAssetsType(rows) {
        console.log("关联的配置项 选中数据回显")
        console.log(rows)
        rows.forEach((item, i) => {
          let index = this.assetsTypeList.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.assetsTypeList.push(item)
          }
        })
      },
      //移除 合同联系人
      handleDeleteContract() {
        if (this.selectContactRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectContactRows.forEach((item, i) => {
              this.contractContactList.some((x, i) => {
                if (x.id == item.id) {
                  this.contractContactList.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false;
        }
      },
      //移除 供应商合同
      handleDeleteSupplier() {
        if (this.selectSupplierRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectSupplierRows.forEach((item, i) => {
              this.supplierContractList.some((x, i) => {
                if (x.id == item.id) {
                  this.supplierContractList.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false;
        }
      },
      //移除 关联的配置项
      handleDeleteAssetsType() {
        if (this.selectAssetsTypeRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectAssetsTypeRows.forEach((item, i) => {
              this.assetsTypeList.some((x, i) => {
                if (x.id == item.id) {
                  this.assetsTypeList.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false;
        }
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
      saveok() {
        this.$refs['form'].validate((v) => {
          if (v) {
            const params = new FormData()
            const uploadedFiles = []
            const uploadedpictures = []
            for (let i = 0; i < this.fileList.length; i++) {
              const f = this.fileList[i]
              if (f.status == 'ready') {
                params.append('file', f.raw)
              } else if (f.status == 'success') {
                uploadedFiles.push(f.url)
              }
            }
            for (let i = 0; i < this.pictureList.length; i++) {
              const f = this.pictureList[i]
              if (f.status == 'ready') {
                params.append('picture', f.raw)
              } else if (f.status == 'success') {
                uploadedpictures.push(f.url)
              }
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
            this.form.assetsIds = newarr2.join(',')
            this.form.supplierIds = newarr1.join(',')
            this.form.contractIds = newarr.join(',')
            this.form.uploadedFiles = uploadedFiles.join(',')
            this.form.uploadedpictures = uploadedpictures.join(',')
            params.append('body', JSON.stringify(this.form))
            toAssetsManageEdit(params).then((res) => {
              if (res.code == 0) {
                this.$baseMessage('修改成功', 'success')
                // this.$router.push({
                //   path: '/pzgl/assetsConfigManage',
                // })
                this.closeall();
              }
            })
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

    .upload-button {
      .el-upload {
        display: inline-block;
        margin-right: 10px;
      }
    }
    .upload-demo{
      .el-upload{
        text-align: left;
      }
    }
  }

  .bghui {
    margin-bottom: 20px;
    background: #f5f5f5;
    padding: 15px;
    display: flex;
    flex-direction: row;
    border: 0px solid #ddd;
    width: 300px;
    align-items: center;
  }

  .bghui .r {
    display: inline-block;
    margin-left: 10px;
    line-height: 25px;
  }
</style>
