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
              <el-form-item label="客户名称" prop="customer_name">
                <el-input
                  v-model.trim="form.customer_name"
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
                  <el-option label="执行中" value="0"></el-option>
                  <el-option label="已结束" value="1"></el-option>
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
              <el-form-item label="成本阈值" prop="costing_threshold">
                <el-input @input="limitInput($event, 'costing_threshold')" v-model.trim="form.costing_threshold"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="币种" prop="currency">
                <el-select v-model.trim="form.currency" class="w">
                  <el-option>请选择</el-option>
                  <el-option label="人民币" value="0"></el-option>
                  <el-option label="美元" value="1"></el-option>
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
                  <el-button size="small" type="primary">点击上传</el-button>
                  <div slot="tip" class="el-upload__tip">
                    只能上传jpg/png文件，且不超过500kb
                  </div>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="事件SLA">
                <el-input
                  v-model.trim="form.sla_event_name"
                  clearable
                  @click.native="openSlaEvent"
                >
                  <!--<el-button-->
                  <!--slot="append"-->
                  <!--icon="el-icon-search"-->
                  <!--@click="openSlaEvent"-->
                  <!--&gt;</el-button>-->
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="问题SLA">
                <el-input
                  v-model.trim="form.sla_question_name"
                  clearable
                  @click.native="openSlaQuestion"
                >
                  <!--<el-button-->
                  <!--slot="append"-->
                  <!--icon="el-icon-search"-->
                  <!--@click="openSlaQuestion"-->
                  <!--&gt;</el-button>-->
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="联系人" name="second">
        <el-table
          v-loading="listLoading"
          :data="form.contractContacts"
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
            align="center"
            sortable
            label="姓名"
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
            label="部门"
            sortable
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="email"
            align="center"
            label="Email"
            sortable
          ></el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-button type="danger" icon="el-icon-delete" @click="handleDelete">
            移除选择对象
          </el-button>
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
            添加合同联系人
          </el-button>
        </div>
      </el-tab-pane>

      <el-tab-pane label="关联的配置项" name="third">
        <el-table
          v-loading="listLoading"
          :data="form.assetsManages"
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

    <children ref="children" @deptData="deptset" @fetch-data=""></children>

    <dialogone
      ref="dialogone"
      @listData="listContactPeople"
      @fetch-data=""
    ></dialogone>

    <dialogtwo
      ref="dialogtwo"
      @listData="listAssetsManages"
      @fetch-data=""
    ></dialogtwo>

    <slaEventDia
      ref="slaEventDia"
      @listData="slaEventDataSet"
      @fetch-data=""
    ></slaEventDia>
    <slaQuestionDia
      ref="slaQuestionDia"
      @listData="slaQuestionDataSet"
      @fetch-data=""
    ></slaQuestionDia>
  </el-dialog>
</template>

<script>
import { getList, toSave } from '@/api/assetsCustomerContract'
//部门
import Children from '@/components/bumenTree'
//合同联系人
import Dialogone from './contractContactDia'
//关联的配置弹窗引用
import Dialogtwo from '@/components/assetsTypeDia'
//事件SLA弹窗引用
import slaEventDia from '@/components/slaEventDia'
import slaQuestionDia from '@/components/slaQuestionDia'
import { download } from '@/api/file'

export default {
  name: 'companyEdit',
  components: { Children, Dialogone, Dialogtwo, slaEventDia, slaQuestionDia },
  data() {
    var check_costing_threshold = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请填写成本阈值'));
      }
      setTimeout(() => {
        debugger
        if (parseFloat(this.form.costing_threshold) > parseFloat(this.form.amount)) {
          callback(new Error('成本阈值必须小于金额'));
        } else {
          callback();
        }
      }, 1000);
    };
    return {
      fileList: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      loading: false,
      loadingText: '保存',
      elementLoadingText: '正在加载...',
      tableData: [],
      activeName: 'first',
      selectRows: '',
      selectRowspz: '',
      bumen: '',
      value1: '',
      value2: '',
      tablePeopleData: [],
      form: {
        id: '',
        customer_name: '',
        contract_name: '',
        dept_id: '',
        deptName: '',
        remark: '',
        start_time: '',
        end_time: '',
        amount: '',
        costing_threshold: '',
        currency: '',
        status: '',
        renew_time: '',
        contractContacts: [],
        assetsManages: [],
        sla_id: '',
        sla_event_name: '',
        sla_question_id: '',
        sla_question_name: '',
        uploadedFiles: [],
      },
      rules: {
        customer_name: [
          { required: true, trigger: 'blur', message: '请输入名称' },
        ],
        contract_name: [
          { required: true, trigger: 'blur', message: '请输入名称' },
        ],
        deptName: [
          { required: true, trigger: 'submit', message: '请选择部门' },
        ],
        status: [{ required: true, trigger: 'blur', message: '请选择状态' }],
        amount: [{ required: true, trigger: 'blur', message: '请填写金额' }],
        currency: [{ required: true, trigger: 'blur', message: '请选择币种' }],
        costing_threshold: [{ required: true, validator:check_costing_threshold, rigger: 'blur' }],
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
    //限制只能输入两位小数
    limitInput(value, name) {
      this.form[name] = ('' + value) // 第一步：转成字符串
        .replace(/[^\d^\.]+/g, '') // 第二步：把不是数字，不是小数点的过滤掉
        .replace(/^0+(\d)/, '$1') // 第三步：第一位0开头，0后面为数字，则过滤掉，取后面的数字
        .replace(/^\./, '0.') // 第四步：如果输入的第一位为小数点，则替换成 0. 实现自动补全
        .match(/^\d*(\.?\d{0,2})/g)[0] || '' // 第五步：最终匹配得到结果 以数字开头，只有一个小数点，	而且小数点后面只能有0到2位小数
    },
    showEdit(row) {
      this.loading = false
      this.loadingText = '保存'
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        this.form.status = row.status + ''
        this.form.currency = row.currency + ''
        this.form.contractContacts = row.contractContacts
        this.form.assetsManages = row.assetsManages
        if (row.fileManageList) {
          row.fileManageList.forEach((x) => {
            this.fileList.push({ name: x.file_name, url: x.id })
          })
        }
      }
      this.dialogFormVisible = true
    },
    handleAdd() {
      debugger
      if ('' == this.form.dept_id) {
        this.$baseMessage('请先选择部门', 'error')
        return false
      }
      console.log(this.form.contractContacts)
      this.$refs['dialogone'].showWin(
        this.form.contractContacts,
        this.form.dept_id
      )
    },
    handleDelete() {
      if (this.selectRows.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectRows.forEach((item, i) => {
            this.form.contractContacts.some((x, i) => {
              if (x.id == item.id) {
                this.form.contractContacts.splice(i, 1)
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
      console.log(this.form.assetsManages)
      this.$refs['dialogtwo'].showWin(this.form.assetsManages)
    },
    handleDeletepz() {
      if (this.selectRowspz.length > 0) {
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectRowspz.forEach((item, i) => {
            this.form.assetsManages.some((x, i) => {
              if (x.id == item.id) {
                this.form.assetsManages.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
        })
        // const ids = this.selectRowspz.id
        // this.$baseConfirm('你确定要删除选中项吗', null, async () => {
        //   const { msg } = await doDelete({ ids })
        //   this.$baseMessage(msg, 'success')
        //   this.fetchData()
        // })
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
      this.fileList = []
      this.form = this.$options.data().form
      this.$emit('fetch-data')
      this.dialogFormVisible = false
    },
    listContactPeople(row) {
      row.forEach((item, i) => {
        let index = this.form.contractContacts.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.contractContacts.push(item)
        }
      })
      // this.tablePeopleData.push(row)
    },
    listAssetsManages(row) {
      row.forEach((item, i) => {
        let index = this.form.assetsManages.findIndex((ele) => {
          return ele.id === item.id
        })
        if (index === -1) {
          this.form.assetsManages.push(item)
        }
      })
    },
    deptset(data) {
      this.form.dept_id = data.id
      this.form.deptName = data.name
    },
    //事件SLA弹窗
    openSlaEvent() {
      this.$refs['slaEventDia'].showWin()
    },
    //问题SLA弹窗
    openSlaQuestion() {
      this.$refs['slaQuestionDia'].showWin()
    },
    //事件SLA选中数据渲染
    slaEventDataSet(row) {
      console.log('slaEventDataSet')
      console.log(row)
      this.form.sla_id = row.id
      this.form.sla_event_name = row.sla_event_name
    },
    slaQuestionDataSet(row) {
      this.form.sla_question_id = row.id
      this.form.sla_question_name = row.sla_question_name
    },
    formatStatus(row) {
      if (row.status == null) {
        return ''
      }
      return row.status == 0 ? '执行中' : '已结束'
    },
    save() {
      this.loading = true
      this.loadingText = '保存中'
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          const params = new FormData()
          const uploadedFiles = []
          for (let i = 0; i < this.fileList.length; i++) {
            const f = this.fileList[i]
            if (f.status == 'ready') {
              params.append('file', f.raw)
            } else if (f.status == 'success') {
              uploadedFiles.push(f.url)
            }
          }
          console.log(this.fileList)
          this.form.uploadedFiles = uploadedFiles.join(',')

          params.append('body', JSON.stringify(this.form))

          const data = await toSave(params)
          if (data.code == 0) {
            this.$baseMessage(data.msg, 'success')

            this.close()
          } else {
            this.$baseMessage('失败', 'error')
            this.loading = false
            this.loadingText = '保存'
          }
        } else {
          this.loading = false
          this.loadingText = '保存'
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

