<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1200px"
    @close="close"
    top="50px"
  >
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="属性" name="first">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-row :gutter="10">
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="供应商名" prop="supplier_name">
                <el-input
                  v-model.trim="form.supplier_name"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="合同名称" prop="contract_name">
                <el-input
                  v-model.trim="form.contract_name"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="部门" prop="deptName">
                <el-input
                  v-model.trim="form.deptName"
                  placeholder="部门"
                  clearable
                  @click.native="openbumen"
                >
                  <!--<el-button-->
                  <!--slot="append"-->
                  <!--icon="el-icon-search"-->
                  <!--@click="openbumen"-->
                  <!--&gt;</el-button>-->
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="状态" prop="status">
                <el-select v-model.trim="form.status" class="w">
                  <el-option label="执行中" value="1"></el-option>
                  <el-option label="已结束" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="备注" prop="remark">
                <el-input
                  v-model.trim="form.remark"
                  autocomplete="off"
                  type="textarea"
                  :rows="3"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="开始时间" prop="start_time">
                <el-date-picker
                  v-model="form.start_time"
                  type="date"
                  placeholder="选择日期"
                  class="w"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="结束时间" prop="end_time">
                <el-date-picker
                  v-model="form.end_time"
                  type="date"
                  placeholder="选择日期"
                  class="w"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="金额" prop="amount">
                <el-input v-model.trim="form.amount"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="币种" prop="status">
                <el-select v-model.trim="form.currency" class="w">
                  <el-option label="人民币" value="1"></el-option>
                  <el-option label="美元" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item
                label="续约提醒时间"
                prop="renew_time"
                class="custemitem"
              >
                <el-date-picker
                  v-model="form.renew_time"
                  type="date"
                  placeholder="选择日期"
                  class="w"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="文档" prop="username">
                <!--<el-upload-->
                <!--class="upload-demo"-->
                <!--action="https://jsonplaceholder.typicode.com/posts/"-->
                <!--:on-change="handleChange"-->
                <!--:file-list="fileList"-->
                <!--&gt;-->
                <!--<el-button size="small" type="primary">点击上传</el-button>-->
                <!--<div slot="tip" class="el-upload__tip">-->
                <!--只能上传jpg/png文件，且不超过500kb-->
                <!--</div>-->
                <!--</el-upload>-->
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
                  <el-button size="small" icon="el-icon-upload2" type="primary">
                    文件上传
                  </el-button>
                  <div slot="tip" class="el-upload__tip">
                    附件大小上限：5.0MB
                  </div>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="联系人" name="second">
        <el-table
          v-loading="listLoading"
          :data="tablePeopleData"
          border
          highlight-current-row
          :element-loading-text="elementLoadingText"
          @selection-change="setSelectRows"
          height="400"
        >
          <el-table-column
            show-overflow-tooltip
            type="selection"
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            type="index"
            label="序号"
            width="60"
            sortable
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="contact_name"
            label="姓名"
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="is_enable"
            label="状态"
            sortable
            align="center"
          >
            <template #default="scope">
              <el-tag
                size="mini"
                icon="el-icon-edit"
                v-if="scope.row.is_enable == '1'"
                type="primary"
              >
                使用
              </el-tag>
              <el-tag
                size="mini"
                icon="el-icon-delete"
                class="red"
                v-if="scope.row.is_enable == '0'"
                type="success"
              >
                禁用
              </el-tag>
            </template>
          </el-table-column>

          <el-table-column
            show-overflow-tooltip
            prop="company_name"
            label="联系人公司"
            align="center"
          ></el-table-column>

          <!--<el-table-column-->
          <!--show-overflow-tooltip-->
          <!--prop="gongchengshi"-->
          <!--label="工程师"-->
          <!--align="center"-->
          <!--&gt;</el-table-column>-->
          <el-table-column
            prop="deptName"
            show-overflow-tooltip
            label="我司对接部门"
            width="200"
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="email"
            label="EMAIL"
            align="center"
            sortable
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="phone"
            label="PHONE"
            align="center"
            sortable
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="remark"
            label="备注"
            align="center"
            sortable
          ></el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-button type="danger" icon="el-icon-delete" @click="handleDelete">
            移除选择对象
          </el-button>
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
            添加联系人
          </el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane label="关联的配置项" name="third">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          border
          highlight-current-row
          :element-loading-text="elementLoadingText"
          @selection-change="setSelectRowspz"
          height="400"
        >
          <el-table-column
            show-overflow-tooltip
            type="selection"
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            type="index"
            label="序号"
            width="60"
            sortable
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="assets_number"
            label="资产编号"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="assetsTypeName"
            label="配置项类型"
            sortable
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
            prop="assets_name"
            label="资产名称"
            sortable
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="deptName"
            label="部门"
            align="center"
            sortable
          ></el-table-column>
          <!--<el-table-column-->
          <!--show-overflow-tooltip-->
          <!--prop="receiver"-->
          <!--label="领用人"-->
          <!--align="center"-->
          <!--sortable-->
          <!--&gt;</el-table-column>-->
          <el-table-column
            show-overflow-tooltip
            prop="purchaseCompanyName"
            label="购置公司"
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="is_secrecy"
            label="保密"
            align="center"
            sortable
          >
            <template #default="scope">
              <el-tag
                size="mini"
                icon="el-icon-edit"
                v-if="scope.row.is_secrecy == '0'"
                type="primary"
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
            align="center"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="itStatusName"
            label="IT状态"
            align="center"
          ></el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-button
            type="danger"
            icon="el-icon-delete"
            @click="handleDeletepz"
          >
            移除选择对象
          </el-button>
          <el-button type="primary" icon="el-icon-plus" @click="handleAddpz">
            添加关联的配置项...
          </el-button>
        </div>
      </el-tab-pane>
    </el-tabs>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">
        {{ loadingText }}
      </el-button>
    </div>
    <children
      ref="children"
      @deptData="deptset"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @listData="listContactPeople"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
    <dialogtwo
      ref="dialogtwo"
      @listData="listAssetsManages"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogtwo>
  </el-dialog>
</template>

<script>
import { toSave } from '@/api/contractshopmanagement'
import Children from '@/components/bumenTree'
import Dialogone from './contactpeople'
import Dialogtwo from './relaseset'
import { download } from '@/api/file'

export default {
  name: 'companyEdit',
  components: { Children, Dialogone, Dialogtwo },
  data() {
    return {
      fileList: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      loading: false,
      loadingText: '保 存',
      elementLoadingText: '正在加载...',
      tableData: [],
      tablePeopleData: [],
      activeName: 'first',
      selectRowspz: '',
      bumen: '',
      value1: '',
      value2: '',
      form: {
        id: '',
        supplier_name: '',
        contractContacts: [],
        assetsManages: [],
        contract_name: '',
        dept_id: '',
        deptName: '',
        remark: '',
        start_time: '',
        end_time: '',
        amount: '',
        currency: '',
        status: '',
        renew_time: '',
        fileTemp: [],
        uploadedFiles: '',
      },
      rules: {
        supplier_name: [
          { required: true, trigger: 'blur', message: '请输入名称' },
        ],
        contract_name: [
          { required: true, trigger: 'blur', message: '请输入名称' },
        ],
        deptName: [
          { required: true, trigger: 'submit', message: '请选择部门' },
        ],
        status: [{ required: true, trigger: 'blur', message: '请选择状态' }],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  methods: {
    showEdit(row) {
      this.loading = false
      this.loadingText = '保 存'
      if (!row) {
        this.title = '添加'
        this.tablePeopleData = []
        this.tableData = []
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        this.form.status = row.status + ''
        this.form.currency = row.currency + ''
        this.tablePeopleData = row.contractContacts
        this.tableData = row.assetsManages
        if (row.fileManageList) {
          row.fileManageList.forEach((x) => {
            this.fileList.push({ name: x.file_name, url: x.id })
          })
        }
      }
      this.dialogFormVisible = true
    },
    handleAdd() {
      this.$refs['dialogone'].showWin(this.form.dept_id)
    },
    listAssetsManages(row) {
      row.forEach((item, i) => {
        let index = this.tableData.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.tableData.push(item)
        }
      })
    },
    handleDelete() {
      if (this.selectRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectRows.forEach((item, i) => {
            this.tablePeopleData.some((x, i) => {
              if (x.id == item.id) {
                this.tablePeopleData.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
        })
        // const ids = this.selectRows.map((item) => item.id).join()
        // console.log(ids);
        // this.$baseConfirm('你确定要删除选中项吗', null, async () => {
        //   //const { msg } = await doDelete({ ids })
        //   this.$baseMessage('模拟删除成功', 'success')
        //   //this.fetchData()
        // })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleAddpz() {
      this.$refs['dialogtwo'].showWin()
    },
    handleDeletepz() {
      if (this.selectRowspz.id) {
        const ids = this.selectRowspz.id
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          const { msg } = await doDelete({ ids })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleCurrentChangepz(val) {
      this.selectRowspz = val
    },
    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3)
    },
    openbumen() {
      this.$refs['children'].showWin()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    setSelectRowspz(val) {
      this.selectRowspz = val
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    listContactPeople(row) {
      row.forEach((item, i) => {
        let index = this.tablePeopleData.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.tablePeopleData.push(item)
        }
      })
      // this.tablePeopleData.push(row)
    },
    deptset(data) {
      this.form.dept_id = data.id
      this.form.deptName = data.name
    },
    save() {
      this.loading = true
      this.loadingText = '保存中'
      this.$refs['form'].validate(async (valid) => {
        const params = new FormData()
        const uploadedFiles = []
        for (let i = 0; i < this.fileList.length; i++) {
          const f = this.fileList[i]
          if (f.status == 'ready') {
            params.append('file', f.raw)
            console.log(f)

            const isLt2M = f.size / 1024 / 1024 < 5
            if (!isLt2M) {
              this.$message.error('上传文件大小不能超过 5MB!')
              this.loading = false
              this.loadingText = '保 存'
              return false
            }
          } else if (f.status == 'success') {
            uploadedFiles.push(f.url)
          }
        }
        console.log(this.fileList)
        this.form.uploadedFiles = uploadedFiles.join(',')
        console.log(this.form.uploadedFiles)
        this.form.contractContacts = this.tablePeopleData
        this.form.assetsManages = this.tableData
        params.append('body', JSON.stringify(this.form))
        if (valid) {
          const data = await toSave(params)
          if (data.code == 0) {
            this.$baseMessage(data.msg, 'success')
            this.$emit('fetch-data')
            this.fileList = []
            window.parent.location.reload()
            this.close()
          } else {
            this.$baseMessage('失败', 'error')
            this.loading = false
            this.loadingText = '保 存'
          }
        } else {
          this.loading = false
          this.loadingText = '保 存'
          return false
        }
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
    handleRemove(file, fileList) {
      this.fileList = fileList
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    onChange(file, fileList) {
      this.fileList = fileList
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
  },
}
</script>
<style scoped>
::v-deep .el-upload {
  text-align: left;
}
</style>

