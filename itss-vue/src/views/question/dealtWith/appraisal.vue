<template>
  <div class="main-container pb80">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-card shadow="never">
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="属性" name="first">
            <el-card shadow="never" style="border-top: 0">
              <div class="box_card">
                <div class="box_header">基本信息</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="编号:">{{ form.id }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="问题单号:">{{ form.code }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="当前状态:">{{ form.status_name }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="问题类别:">{{ form.category_name }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="SLA:">{{ form.sla_time }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="问题来源:" prop="source_id">
                        <el-select v-model="form.source_id" class="w">
                          <el-option v-for="item in this.sourceList" :key="item.id" :label="item.name" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="优先级:" prop="priority_id">
                        <el-select v-model="form.priority_id" class="w">
                          <el-option v-for="item in this.firstList" :key="item.id" :label="item.first_name" :value="item.id"></el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="标题:" prop="title">
                        <el-input v-model="form.title"></el-input>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="描述:">
                        <vab-quill v-model="form.description" :min-height="200" :options="options"></vab-quill>
                        <p class="ts">请详细描述问题或现象，可ctrl+v粘贴图片或上传附件</p>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="期望解决时间:">
                        <el-date-picker v-model="form.hope_solve_time" type="date"></el-date-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="创建人:">{{ form.create_user_name }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="创建时间:">{{ form.create_time }}</el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="附件:" style="width: 400px;">
                        <el-upload class="upload-button" action="" :auto-upload="false" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed" :file-list="fileManageInfoList" :on-change="onChange">
                          <el-button size="small" type="primary">点击上传</el-button>
                        </el-upload>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>

              <div class="box_card">
                <div class="box_header">鉴定信息</div>
                <div class="box_content">
                  <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                      <el-form-item label="处理人:" prop="handle_user">
                        <el-select v-model="form.handle_user" filterable remote reserve-keyword placeholder="请输入姓名" :loading="loading" style="width: 80%">
                          <template #prefix>
											      <span style="padding-left: 2px"><i class="el-icon-search"></i></span>
                          </template>
                          <el-option v-for="item in auto_engineer_data" :key="item.id" :label="item.name" :value="item.id"></el-option>
                        </el-select>
                        <el-button icon="el-icon-search" style="border-left: 0; background-color: #f5f7fa; height: 32px; width: 20%" @click="openwinTable"></el-button>
                        <!---->
                        <!--<el-input-->
                        <!--v-model.trim="form.handle_user"-->
                        <!--placeholder=""-->
                        <!--clearable-->
                        <!--@click.native="handlepeople"-->
                        <!--&gt;-->
                        <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                        <!--@click="handlepeople"-->
                        <!--&gt;</el-button>-->
                        <!--</el-input>-->
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="鉴定意见:">
                        <vab-quill v-model="form.appraisal_opinion" :min-height="200" :options="options"></vab-quill>
                        <p class="ts">请详细描述问题或现象，可ctrl+v粘贴图片或上传附件</p>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </div>
              </div>
            </el-card>
          </el-tab-pane>
          <el-tab-pane :label="eventsName" name="five">
            <el-table v-loading="listLoading" :data="form.events" border highlight-current-row :element-loading-text="elementLoadingText" @selection-change="setSelectEventRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center" header-align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center" sortable></el-table-column>
              <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="user_id_name" width="100px;" label="用户" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center" width="160px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="handler_name" label="处理人" align="center" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间" ></el-table-column>
              <el-table-column show-overflow-tooltip prop="statusName" align="center" label="状态" width="100px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" sortable>
                <template #default="{ row }">
                  <el-rate v-model="row.event_satisfaction" disabled>
                  </el-rate>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteEvents">移除选择对象</el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleEventsAdd">添加关联的工单</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane :label="changesName" name="seven">
            <el-table v-loading="listLoading" :data="form.changeInfos" border highlight-current-row :element-loading-text="elementLoadingText" @selection-change="setSelecChangeRows">
              <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
              <el-table-column show-overflow-tooltip prop="change_no" width="140px;" label="变更单号" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="title" sortable label="标题" header-align="center" align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="start_time" width="160px;" label="计划开始时间"  align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="classify_name" align="center"  label="变更类别"></el-table-column>
              <el-table-column show-overflow-tooltip prop="type_name" align="center"  label="变更类型"></el-table-column>
              <el-table-column show-overflow-tooltip prop="approve_name" align="center" width="100px;" label="审批人"></el-table-column>
              <el-table-column show-overflow-tooltip prop="approve_time" align="center"  label="审批时间"></el-table-column>
              <el-table-column show-overflow-tooltip prop="create_time" align="center" width="160px;" label="创建时间"></el-table-column>
              <el-table-column show-overflow-tooltip prop="status_name" align="center" width="100px;" label="状态"></el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button type="danger" icon="el-icon-delete" @click="handleDeleteChange">移除选择对象</el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleChangeAdd">添加关联变更</el-button>
            </div>
          </el-tab-pane>
          <el-tab-pane :label="logsName" name="fourth">
            <div class="box_card">
              <div class="box_content">
                <el-timeline>
                  <el-timeline-item v-for="(log, index) in form.logs" :key="index" :timestamp="log.operate_time" placement="top" size="large" color="#0bbd87">
                    <el-card>
                      <h4>{{log.title}}</h4>
                      <p>{{log.user_name}}</p>
                      <div class="ql-editor content-detail" v-html="log.content"/>
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </div>
          </el-tab-pane>

        </el-tabs>
      </el-card>
      <div class="bottom">
        <el-button type="primary" size="medium" :loading="loadingbut" :disabled="disabledData" @click="handleAdd(1)">通过</el-button>
        <el-button type="warning" size="medium" :loading="loadingbhbut" :disabled="disabledData" @click="handleAdd(2)">拒绝</el-button>
      </div>
    </el-form>

    <!--事件-->
    <eventDialog ref="eventDialog" @listData="listEventLists"></eventDialog>
    <!--变更-->
    <changeDialog ref="changeDialog" @listData="listChanges"></changeDialog>
    <!--用户-->
    <users ref="users" @userData="getUser"></users>
  </div>
</template>

<script>
  import {getQuestionById, getQuestionFirstList, getSourceList, onDbAppraisalUpd, upUserData} from '@/api/question'
  import {download} from '@/api/file'
  // 引用文本编辑器页面
  import vabQuill from '@/plugins/vabQuill'
  // 引用事件弹窗页面
  import eventDialog from '@/components/eventDialog'
  // 引用变更弹窗页面
  import changeDialog from '@/components/changeDialog'
  //引用处理人弹窗页面
  import Users from '@/components/UserSelectModel'

  export default {
    name: 'dealAppraisal',
    components: {vabQuill, eventDialog, changeDialog, Users},
    data() {
      return {
        events_type:'1',
        firstList: [],
        sourceList: [],
        loadingbut: false,//默认提交按钮可点击
        loadingbhbut: false,//默认提交按钮可点击
        disabledData:false,
        elementLoadingText: '正在加载...',
        selectEventRows: '',
        selectChangeRows: '',
        tableData: [],
        useroptions: [],
        list: [],
        loading: false,
        listLoading: true,
        auto_engineer_data: [],
        eventsName: '',
        changesName: '',
        logsName: '',
        fileManageInfoList: [],
        fileManageHandleList: [],
        fileList: [{
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }, {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
        }],

        options: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{header: [1, 2, 3, 4, 5, 6, false]}],
              [{size: ['small', false, 'large', 'huge']}],
              [{color: []}, {background: []}],
              ['blockquote', 'code-block'],
              [{list: 'ordered'}, {list: 'bullet'}],
              [{script: 'sub'}, {script: 'super'}],
              [{indent: '-1'}, {indent: '+1'}],
              [{align: []}],
              [{direction: 'rtl'}],
              [{font: []}],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
        form: {
          id: '',
          //来源id 来源通过设置-问题配置中的问题来源管理中获取
          source_id: '',
          //3	优先级	设置-服务级别协议中的问题优先级管理中获取
          priority_id: '',
          //4	问题类别id	问题类别通过设置-问题配置中的问题类别管理中获取
          category_id: '',
          //5	标题
          title: '',
          //6	描述
          description: '',
          //7	期望解决时间 yyyy-MM-dd
          hope_solve_time: '',
          //8	问题鉴定通知 0否 1是
          is_notice: '',
          //9	状态 【字典】1待鉴定 2待审核 3待解决 4审核通过 5关闭 6已撤销 7鉴定拒绝 8审核拒绝 9挂起
          status: '',
          //10 备注
          remark: '',
          //11 问题单号
          code: '',
          //14 鉴定人 用户表id
          appraisal_user: '',
          //15 鉴定时间
          appraisal_time: '',
          //16 鉴定意见
          appraisal_opinion: '',
          //17 处理人
          handle_user: '',
          //18 解决人 用户表id
          solve_user: '',
          //19 解决时间
          solve_time: '',
          //20 解决方案
          solve_content: '',
          //21 审核人 用户表id
          audit_user: '',
          //22 审核时间
          audit_time: '',
          //23 审核内容
          audit_content: '',
          //24 关单人 用户表id
          clearance_user: '',
          //25 关单时间
          clearance_time: '',
          //26 sla:(小时)
          sla_time: '',
          //27 关单时限
          time_limit: '',
          //28 解决分钟数
          resolution_minutes: '',
          //29 当前操作人 用户表id
          now_operator_user: '',
          //挂起原因
          suspend_reason: '',
          //审核人名称
          audit_user_name: '',
          //来源名称
          source_name: '',
          //关单人名称
          clearance_user_name: '',
          //解决人名称
          solve_user_name: '',
          //鉴定人名称
          appraisal_user_name: '',
          //处理人名称
          handle_user_name: '',
          //状态名称
          status_name: '',
          //事件工单列表
          events: [],
          //变更列表
          changeInfos: [],
          //问题日志
          questionLogs: [],
          uploadedFiles: '',
          uploadedHandleFiles: '',
        },
        rules: {
          handle_user: [
            {required: true, message: '请选择处理人', trigger: 'submit'}
          ],
          source_id: [
            {required: true, message: '请选择问题来源', trigger: 'change'}
          ],
          priority_id: [
            {required: true, message: '请选择优先级', trigger: 'change'}
          ],
          title: [
            {required: true, message: '请输入标题', trigger: 'change'}
          ],
          // username: [
          //   {required: true, trigger: 'blur', message: '请输入用户名'},
          // ],
          // content: [
          //   {
          //     required: true,
          //     message: '请输入内容',
          //     trigger: 'blur',
          //   },
          // ],
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
        input2: '',
        dialogFormVisible: false,
        username:
          'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
      }
    },
    created() {
      this.getData();
      this.upUserData();
      this.getFirstList();
      this.getSourceList();
    },
    methods: {
      showView() {
        this.title = '查看详情';
        this.dialogFormVisible = true
      },
      async getData() {
        // 取到路由带过来的参数
        const query = this.$route.query;
        const {data} = await getQuestionById(query.id);
        this.form = data;
        this.eventsName = "关联工单(" + data.events.length + ")";
        this.changesName = "关联变更(" + data.changeInfos.length + ")";
        this.logsName = "日志(" + data.logs.length + ")";
        console.log( data)
        //属性信息 附件
        if (data.fileManageInfoList) {
          data.fileManageInfoList.forEach((x) => {
            this.fileManageInfoList.push({name: x.file_name, url: x.id})
          })
        }

        // console.log(data)
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async getSourceList() {//问题来源列表
        const {data} = await getSourceList();
        this.sourceList = data;
      },
      async getFirstList() {//问题优先级列表
        const {data} = await getQuestionFirstList();
        this.firstList = data;
      },
      handleEventsAdd() {
        this.$refs['eventDialog'].showWin(this.event_type,this.form.events)
      },
      handleChangeAdd() {
        this.$refs['changeDialog'].showWin()
      },
      openwinTable() {
        this.$refs['users'].showWin();
      },
      //处理人选择，数据渲染
      getUser(row) {
        this.form.handle_user = row.id;
        this.form.handle_user_name = row.name;
      },
      remoteMethod(query) {
        if (query !== '') {
          this.loading = true;
          setTimeout(() => {
            this.loading = false;
            this.useroptions = this.list.filter((item) => {
              return item.label.toLowerCase().indexOf(query.toLowerCase()) > -1
            })
          }, 200)
        } else {
          this.useroptions = []
        }
      },
      async upUserData() {
        var that = this;
        const resp = await upUserData();
        that.auto_engineer_data = resp.data;
      },
      setSelectEventRows(val) {
        this.selectEventRows = val
      },
      setSelecChangeRows(val) {
        this.selectChangeRows = val
      },
      listEventLists(rows) {
        this.form.events = [];
        rows.forEach((item, i) => {
          let index = this.form.events.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.events.push(item)
          }
        })
      },
      listChanges(rows) {
        rows.forEach((item, i) => {
          let index = this.form.changeInfos.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.changeInfos.push(item)
          }
        })
      },
      handleDeleteEvents() {
        if (this.selectEventRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectEventRows.forEach((item, i) => {
              this.form.events.some((x, i) => {
                if (x.id == item.id) {
                  this.form.events.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success');
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleDeleteChange() {
        if (this.selectChangeRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectChangeRows.forEach((item, i) => {
              this.form.changeInfos.some((x, i) => {
                if (x.id == item.id) {
                  this.form.changeInfos.splice(i, 1);
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            });
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error');
          return false
        }
      },
      handleAdd(val) {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            if(val == 1){
              this.loadingbut = true;
            }else{
              this.loadingbhbut = true;
            }
            this.disabledData=true;
            const params = new FormData();
            const uploadedFiles = [];
            for (let i = 0; i < this.fileManageInfoList.length; i++) {
              const f = this.fileManageInfoList[i];
              if (f.status == 'ready') {
                params.append('file', f.raw);
              } else if (f.status == 'success') {
                uploadedFiles.push(f.url);
              }
            }
            this.form.uploadedFiles = uploadedFiles.join(',');

            this.form.auditStatus = val;
            params.append('body', JSON.stringify(this.form));
            onDbAppraisalUpd(params).then((res) => {
              if (res.code == 0) {
                this.$baseMessage('成功', 'success');
                this.closeall();
                this.disabledData = false;
              } else {
                this.$baseMessage('失败', 'error');
                this.loadingbut = false;
                this.loadingbhbut = false;
                this.disabledData = false;
              }
            });
          }else {
            this.$baseMessage('请检查表单', 'error')
          }
        })


        // this.$refs['form'].validate(async (valid) => {
        //   console.log(this.form);
        //   if (valid) {
        //     const {msg} = await onDbAppraisalUpd(this.form);
        //     this.$baseMessage(msg, 'success');
        //     this.$emit('fetch-data');
        //
        //     this.closeall();
        //   } else {
        //     return false
        //   }
        // })
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route);
        // 返回上一步路由
        this.$router.go(-1)
      },
      handleRemove(file, fileList) {
        this.fileManageInfoList = fileList;
        console.log(file, fileList);
      },
      // handlePreview(file) {
      //   download(file.url).then((res) => {
      //     const url = window.URL.createObjectURL(new Blob([res]));
      //     const link = document.createElement('a');
      //     link.href = url;
      //     link.setAttribute('download', file.name);
      //     document.body.appendChild(link);
      //     link.click();
      //   })
      // },
      handleExceed(files, fileList) {
        this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      //附件上传 选择文件
      onChange(file, fileList) {
        this.fileManageInfoList = fileList;
      },
      // close() {
      //   // this.$refs['form'].resetFields()
      //   // this.form = this.$options.data().form
      //   this.dialogFormVisible = false
      // },
      // save() {
      //   this.$baseMessage('模拟保存成功', 'success')
      //   this.$emit('fetch-data')
      //   this.close()
      // },
    },
  }
</script>
<style lang="scss" scoped>
  .main-container {
    ::v-deep {
      .el-divider--horizontal {
        margin-bottom: 0 !important;
      }

      .upload-button {
        display: inline-block;
        margin-right: 10px;
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

      .el-upload {
        text-align: left;
      }
    }
  }
</style>
