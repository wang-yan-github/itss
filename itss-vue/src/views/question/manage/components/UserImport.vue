<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-tabs v-model="activeName" type="card" @tab-click="">
      <el-tab-pane label="数据导入" name="first">
        <div class="uploadbox">
          <div class="moban">
            <el-button size="medium" icon="el-icon-download" type="danger" @click="downloadTemple">
              导入模版下载
            </el-button>
            <p>
              提示:多级部门、多个服务群组和多个角色时，用逗号(,)分隔；类型和状态取值请与添加页面一致。
            </p>
          </div>

          <el-upload
            class="upload-demo"
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple
            :auto-upload="false"
            :on-change="onChange"
            style="width: 100%"
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">
              将文件拖到此处，或
              <em>点击上传</em>
            </div>
            <div class="el-upload__tip" slot="tip">
              只能上传（excel）文件，且不超过500kb
            </div>
          </el-upload>
        </div>
      </el-tab-pane>
      <el-tab-pane label="查看导入结果" name="second">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-card shadow="never">
              <div slot="header">
                <span>
                  <vab-icon :icon="['fas', 'user']"></vab-icon>
                  导入数据（总数：{{totalNum}}）
                </span>
                <!--<el-button style="float: right; padding: 3px 0" type="text">-->
                  <!--发现的0条部门信息-->
                <!--</el-button>-->
                <!-- <div class="rbox"><vab-icon :icon="['fas', 'times']"></vab-icon><vab-icon :icon="['fas', 'sync-alt']"></vab-icon></div> -->
              </div>

              <vab-query-form>
                <vab-query-form-left-panel :span="24">
                  <!--<el-form :inline="true" label-position="left" label-width="">-->
                    <!--<el-form-item label="仅显示错误数据">-->
                      <!--<el-select-->
                        <!--v-model="queryForm.username"-->
                        <!--placeholder="请选择"-->
                      <!--&gt;-->
                        <!--<el-option label="是" value="shanghai"></el-option>-->
                        <!--<el-option label="否" value="beijing"></el-option>-->
                      <!--</el-select>-->
                    <!--</el-form-item>-->

                    <!--<el-form-item>-->
                      <!--<el-button-->
                        <!--icon="el-icon-search"-->
                        <!--type="primary"-->
                        <!--@click="queryData"-->
                      <!--&gt;-->
                        <!--检索-->
                      <!--</el-button>-->
                    <!--</el-form-item>-->
                  <!--</el-form>-->
                </vab-query-form-left-panel>
              </vab-query-form>
              <div style="color: red; font-size: 16px; font-weight: bold">
                总数:{{totalNum}}， 验证成功:{{successNum}}， 数据错误:{{errorNum}}
              </div>
              <el-table
                v-loading="listLoading"
                :data="successList"
                border
                :element-loading-text="elementLoadingText"
                height="240"
                style="margin-top: 20px"
              >
                <el-table-column
                  show-overflow-tooltip
                  prop="source_name"
                  label="问题来源"
                  sortable
                  fixed="left"
                  width="100px"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="priority_name"
                  sortable
                  width="100px"
                  label="优先级"
                  fixed="left"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="category_name"
                  sortable
                  width="100px"
                  label="问题类别"
                  fixed="left"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="title"
                  width="100px"
                  label="标题"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="description"
                  width="100px"
                  label="描述"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="hope_solve_time"
                  width="100px"
                  label="期望解决时间"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="create_user_name"
                  width="100px"
                  label="创建人"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="is_success"
                  sortable
                  width="120px"
                  label="是否成功"
                  fixed="left"
                  align="center"
                ></el-table-column>

              </el-table>
              <el-pagination
                background
                :current-page="queryForm.pageNo"
                :page-size="queryForm.pageSize"
                :layout="layout"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              ></el-pagination>
            </el-card>
          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">导 入</el-button>
    </div>
  </el-dialog>
</template>

<script>

  import {downloadUser} from '@/api/userManagement'
  import {downloadQuestion,importQuestionList} from '@/api/question'

  export default {
    name: 'userroles',
    data() {
      return {
        fileManageInfoList: {},
        activeName: 'first',
        totalNum: 0,
        successNum: 0 ,
        errorNum: 0,
        successList: [],
        errorList: [],
        form: {
          username: '',
          password: '',
          email: '',
          permissions: [],
        },
        rules: {
          username: [
            {required: true, trigger: 'blur', message: '请输入用户名'},
          ],
          password: [{required: true, trigger: 'blur', message: '请输入密码'}],
          email: [{required: true, trigger: 'blur', message: '请输入邮箱'}],
          permissions: [
            {required: true, trigger: 'blur', message: '请选择权限'},
          ],
        },
        title: '',
        dialogFormVisible: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableData: [
          {
            id: '10001',
            username: '系统管理员',
            state: '全部权限',
          },
          {
            id: '10001',
            username: '用户',
            state: '自助开单、查询本人工单、满意度调查',
          },
          {
            id: '10001',
            username: '工程师',
            state: '开单、响应、处理、转单、关单权限',
          },
          {
            id: '10001',
            username: '服务台',
            state: '开单、响应、指派、处理、转单、关单',
          },
          {
            id: '10001',
            username: '事件管理员',
            state: '事件管理、事件配置、服务级别管理全部权限',
          },
          {
            id: '10001',
            username: '知识工程师',
            state: '可创建、查看和搜索知识',
          },
          {
            id: '10001',
            username: '知识管理员',
            state: '知识管理全部权限',
          },
          {
            id: '10001',
            username: '问题工程师',
            state: '可创建、处理、解决问题',
          },
        ],
      }
    },
    created() {
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      //属性 附件上传 选择文件
      onChange(file, fileList) {
        this.fileManageInfoList = file;
        console.log(this.fileManageInfoList);
      },
      showWin() {
        this.title = '问题信息导入'
        this.dialogFormVisible = true
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        // this.tableData
      },
      handleCurrentChange(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        location. reload()
        this.dialogFormVisible = false
      },
      async save() {
        const params = new FormData();
        const uploadedFiles = [];

        const f = this.fileManageInfoList;
        if (f.status == 'ready') {
          params.append('file', f.raw);
        } else if (f.status == 'success') {
          uploadedFiles.push(f.url);
        }

        debugger
        const { data } = await importQuestionList(params)
        console.log(data)
        this.totalNum = data.all.length;
        this.successNum = data.success.length;
        this.errorNum = data.error.length;
        this.successList = data.all;
        this.errorList = data.error;
        this.$baseMessage('导入成功，请查看结果', 'success')
        this.$emit('fetch-data')
        // this.close()
      },
      async downloadTemple() {
        //const { data} = await downloadUser()
        downloadQuestion().then((res) => {
          console.log(res);
          // 获取文件名
          let objectUrl = URL.createObjectURL(new Blob([res]));
          // 文件地址
          const link = document.createElement('a');
          link.download = "问题导入模板.xlsx";
          link.href = objectUrl;
          link.click();

          // debugger
          //    const url = window.URL.createObjectURL(res);
          //    const link = document.createElement('a');
          //    link.href = url;
          //    link.setAttribute('downloadTemple', '用户信息模板.xlsx');
          //    document.body.appendChild(link);
          //    link.click();
        });
      },
    },
  }
</script>
<style lang="scss" scoped>
  .uploadbox {
    ::v-deep {
      .el-upload-dragger {
        width: 760px !important;
      }
    }
  }

  .el-card {
    ::v-deep {
      .el-input {
        width: 100px;
      }

      .el-card__header {
        font-size: 16px;
      }
    }
  }
</style>
