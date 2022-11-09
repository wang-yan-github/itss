<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="名称" prop="name">
            <el-input
              v-model.trim="form.name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="状态" prop="is_enable">
            <el-select v-model.trim="form.is_enable" class="w">
              <el-option label="启用" value="0"></el-option>
              <el-option label="禁用" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="分组" prop="group_id">
            <el-select v-model.trim="form.group_id" class="w" placeholder="请选择" label="分组" prop="form.group_id"
                       @change="changeCIGroup($event)">
              <el-option
                v-for="item in ciGroups"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>

        </el-col>
        <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
        <!--<el-form-item label="代码" prop="code">-->
        <!--<el-input-->
        <!--v-model.trim="form.code"-->
        <!--autocomplete="off"-->
        <!--&gt;</el-input>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="排序" prop="sort">
            <el-input
              v-model.trim="form.sort"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <!--
        全部都是自定义类的不要这三个值
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="添加地址" prop="add_address">
            <el-input
              v-model.trim="form.add_address"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="列表地址" prop="list_address">
            <el-input
              v-model.trim="form.list_address"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="查看地址" prop="check_address">
            <el-input
              v-model.trim="form.check_address"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        -->
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <!--<el-form-item label="快速报障支持群组" prop="support_group_id" class="custemitem">-->
          <!--<el-select v-model.trim="form.support_group_id" class="w">-->
          <!--<el-option label="是" value="0"></el-option>-->
          <!--<el-option label="否" value="1"></el-option>-->
          <!--</el-select>-->
          <!--</el-form-item>-->


          <el-form-item label="快速报障支持群组" prop="support_group_id" class="custemitem">
            <el-select v-model.trim="form.support_group_id" class="w">
              <el-option
                v-for="item in this.eventServiceManage"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="表单" prop="form_id">
            <el-input
              v-model.trim="form.formName"
              autocomplete="off"
              @click.native="openbumen"
            >
              <!--<el-button slot="append" icon="el-icon-search" @click="openbumen"></el-button>-->
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="图标" prop="file_id">
            <el-upload
              class="upload-demo"
              drag
              action="https://jsonplaceholder.typicode.com/posts/"
              multiple
              :auto-upload="false"
              :on-change="onChange"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :before-remove="beforeRemove"
              :file-list="fileList"
              :limit="1"
              :on-exceed="handleExceed"
              style="width: 100%"
            >
              <!--class="upload-demo"-->
              <!--drag-->
              <!--action="https://jsonplaceholder.typicode.com/posts/"-->
              <!--multiple-->
              <!---->
              <!--&gt;-->
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">
                将文件拖到此处，或
                <em>点击上传</em>
              </div>
              <div class="el-upload__tip" slot="tip">
                建议48x48以下,只能上传jpg/png文件，且不超过500kb
              </div>
            </el-upload>
          </el-form-item>

        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <dialogone
      ref="dialogone"
      @formIdData="formIdset"
      @fetch-data="
        {
          biaodan
        }
      "
    ></dialogone>
  </el-dialog>
</template>

<script>
  import {addAssetsType} from '@/api/configtypemanagement'
  import {getAllList} from '@/api/eventServiceManage'
  import {getCIList} from '@/api/configtypemanagement'
  import Dialogone from './biaodan'
  import {download} from '@/api/file'

  export default {
    name: 'companyEdit',
    components: {Dialogone},
    data() {
      //这里是自定义的规则
      var fileCount = (rule, value, callback) => {
        if (this.fileList.length == 0) {
          return callback(new Error('请上传图片'));
        } else if (this.fileList.length > 1) {
          return callback(new Error('只能上传一个图片'));
        } else {
          callback()
        }
      };
      return {
        isDisable: false,
        fileList: [],
        input2: '',
        groupName: '',
        eventServiceManage: [],
        form: {
          id: '',
          name: '',
          is_enable: '',
          group_id: '',
          group_name: '',
          code: '',
          sort: '',
          add_address: '',
          list_address: '',
          check_address: '',
          support_group_id: '',
          form_id: '',
          formName: '',
          file_id: '',
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          username: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_enable: [{required: true, trigger: 'blur', message: '请输入状态'}],
          group_id: [{required: true, trigger: 'blur', message: '请输入分组'}],
          code: [{required: true, trigger: 'blur', message: '请输入代码'}],
          file_id: [{required: true, trigger: 'blur', validator: fileCount}],
        },
        title: '',
        dialogFormVisible: false,
        ciGroups: [],
      }
    },
    created() {
      this.form.code = "CUSTOM";
      this.getCIGroups();
      this.getEventServiceManageList()
    },
    methods: {
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`);
      },
      handleRemove(file, fileList) {
        this.fileList = fileList
      },
      handleExceed() {
        return this.$confirm(`只能上传一个图片！`);
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
      //属性 附件上传 选择文件
      onChange(file, fileList) {
        this.fileList = fileList;

      },
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.group_id = this.form.group_id + "";
          this.form.form_id = row.form_id + "";
          this.form.formName = row.formName;
          console.log(row);
          if (row.fileManageList) {
            row.fileManageList.forEach((x) => {
              this.fileList.push({name: x.file_name, url: x.id})
            })
          }

        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()

        this.form = this.$options.data().form
        // window.parent.location.reload();
        this.$emit('fetch-data')
        this.dialogFormVisible = false
      },
      openbumen() {
        this.$refs['dialogone'].showWin()
      },
      formIdset(val) {
        for (var i = 0; i < val.length; i++) {
          this.form.form_id = val[i].id
          this.form.formName = val[i].form_name
        }

      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            if (this.fileList.length > 1) {
              this.$baseMessage('只能上传一个图片', 'error')
              this.isDisable = false;
              return false;
            }
            if (this.fileList.length == 0) {
              this.$baseMessage('请上传图片', 'error')
              this.isDisable = false;
              return false;
            }

            // if (this.title != '添加') {
            //   this.form.id = this.$route.query.id
            // }

            const params = new FormData();
            const uploadedFiles = [];

            const f = this.fileList[0]
            if (f.status == 'ready') {
              params.append('file', f.raw);
            }

            params.append('body', JSON.stringify(this.form))
            try {
              const data = await addAssetsType(params)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.fileList = [];
                // window.parent.location.reload();
                this.close()
              } else {
                this.$baseMessage("失败", 'error');
                this.isDisable = false;
              }
            } catch (e) {
              this.isDisable = false;
            }

          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      async getEventServiceManageList() {
        const {data} = await getAllList({})
        this.eventServiceManage = data.data
      },
      async getCIGroups() {
        this.listLoading = true
        const data = await getCIList()
        this.ciGroups = data.data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      changeCIGroup(event) {
        this.groupName = event;
      }
    },
  }
</script>

