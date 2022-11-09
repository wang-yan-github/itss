<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="10" hidden>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="id" prop="id">
            <el-input v-model.trim="form.id" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="名称" prop="os_licence_name">
            <el-input v-model.trim="form.os_licence_name" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="Key" prop="key_license">
            <el-input v-model.trim="form.key_license" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="部门" prop="deptName">
            <el-input placeholder="请输入内容" v-model="form.deptName" @click.native="openwin">
              <!--<el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>-->
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="描述" prop="description">
            <el-input v-model.trim="form.description" type="textarea" :rows="3"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="使用限制" prop="use_limit">
            <el-input v-model.trim="form.use_limit" type="textarea" :rows="3"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="开始时间" prop="starTime">
            <el-date-picker v-model="form.starTime" type="date" placeholder="选择日期" class="w"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker v-model="form.endTime" type="date" placeholder="选择日期" class="w"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="是否无限期" prop="isac" class="is_limit" style="font-size: 13px;">
            <el-select v-model.trim="form.is_limit" class="w">
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
    <children ref="children" @deptData="deptset" @fetch-data=" { input2 }"></children>
  </el-dialog>
</template>

<script>
  import {toSave} from '@/api/assetsOsLicence'
  import Children from '@/components/bumenTree'

  export default {
    name: 'companyEdit',
    components: {Children},
    data() {
      return {
        loading: false,
        loadingText: '确 定',
        input2: '',
        form: {
          id: '',
          os_licence_name: '',
          deptId: '',
          deptName: '',
          description: '',
          starTime: '',
          endTime: '',
          key_license: '',
          is_limit: '',
          use_limit: '',
        },
        rules: {
          os_licence_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          deptName: [{required: true, trigger: 'submit', message: '请选择'}],
          // deptId: [{ required: true, trigger: 'blur', message: '请选择部门' }],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {

      showEdit(row) {
        this.loading = false
        this.loadingText = '确 定'
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
        }
        this.dialogFormVisible = true
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      deptset(row) {
        this.form.deptId = row.id;
        this.form.deptName = row.name;
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.loading = true
        this.loadingText = '提交中...'
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const data = await toSave(this.form)
            if (data.code == 0) {
              this.$baseMessage(data.msg, 'success');
              this.$emit('fetch-data');
              this.close();
            } else {
              this.$baseMessage("失败", 'error');
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

