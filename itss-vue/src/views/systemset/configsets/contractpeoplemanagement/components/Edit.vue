<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1000px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="姓名" prop="contact_name">
            <el-input
              v-model.trim="form.contact_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="联系人公司" prop="company_id" class="custemitem">
            <el-select v-model.trim="form.company_id" class="w">
              <el-option
                v-for="item in this.company"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="我司对接部门" prop="deptName" class="custemitem">
            <el-input
              placeholder="请输入内容"
              v-model="form.deptName"
              @click.native="openwin"
            >
              <!--<el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>-->
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="状态" prop="is_enable">
            <el-select v-model.trim="form.is_enable" class="w">
              <el-option label="禁用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="Email" prop="email">
            <el-input v-model.trim="form.email"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="手机" prop="phone">
            <el-input v-model.trim="form.phone"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="座机" prop="telephone">
            <el-input v-model.trim="form.telephone"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item
            label="是否接受通知"
            prop="is_receive_notice"
            class="custemitem"
          >
            <el-select v-model.trim="form.is_receive_notice" class="w">
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="联系地址" prop="contact_address">
            <el-input
              v-model.trim="form.contact_address"
              :rows="3"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model.trim="form.remark"
              :rows="3"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">
        {{ loadingText }}
      </el-button>
    </div>
    <children ref="children" @deptData="deptset"></children>
  </el-dialog>
</template>

<script>
import { toSave } from '@/api/assetsContractContact'
import Children from '@/components/bumenTree'
import { sysCompanyGetList } from '@/api/userManagement'

export default {
  name: 'companyEdit',
  components: { Children },
  data() {
    return {
      loading: false,
      loadingText: '确 定',
      value1: '',
      value2: '',
      selectType: '1',
      company: [],
      form: {
        id: '',
        contact_name: '',
        company_name: '',
        dept_id: '',
        deptName: '',
        is_enable: '',
        email: '',
        phone: '',
        telephone: '',
        is_receive_notice: '',
        contact_address: '',
        remark: '',
      },
      rules: {
        contact_name: [
          { required: true, trigger: 'blur', message: '请输入名称' },
        ],
        company_id: [
          { required: true, trigger: 'blur', message: '请输入联系人公司' },
        ],
        deptName: [
          { required: true, trigger: 'submit', message: '请选择部门' },
        ],
        is_enable: [{ required: true, trigger: 'blur', message: '请选择状态' }],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
    this.sysCompanyGetList()
  },
  methods: {
    // 获取公司列表
    async sysCompanyGetList() {
      const { data } = await sysCompanyGetList({})
      this.company = data.list
    },
    showEdit(row) {
      this.loading = false
      this.loadingText = '确 定'
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        this.form.is_enable = row.is_enable + ''
        if (row.is_receive_notice == null) {
          this.form.is_receive_notice = ''
        } else {
          this.form.is_receive_notice = row.is_receive_notice + ''
        }
      }
      this.dialogFormVisible = true
    },
    openwin() {
      this.selectType = '1'
      this.$refs['children'].showWin()
    },
    openwinCompany() {
      this.selectType = '2'
      this.$refs['children'].showWin()
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    deptset(row) {
      if (this.selectType == '1') {
        this.form.dept_id = row.id
        this.form.deptName = row.name
      } else if (this.selectType == '2') {
      }
    },
    save() {
      this.loading = true
      this.loadingText = '提交中...'
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          const data = await toSave(this.form)
          if (data.code == 0) {
            this.$baseMessage(data.msg, 'success')
            this.$emit('fetch-data')
            this.close()
          } else {
            this.$baseMessage('失败', 'error')
            this.loading = false
            this.loadingText = '确 定'
          }
        } else {
          this.loading = false
          this.loadingText = '确 定'
          return false
        }
      })
    },
  },
}
</script>

