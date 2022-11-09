<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" hidden>
          <el-form-item label="id" prop="id">
            <el-input v-model.trim="form.id" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
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
          <el-form-item label="部门" prop="deptId" hidden>
            <el-input
              v-model="form.deptId"
              clearable
            ></el-input>
          </el-form-item>
          <el-form-item label="部门" prop="deptName">
            <el-input
              placeholder="输入关键字进行过滤"
              v-model="form.deptName"
              clearable
            ></el-input>
            <div class="treeborder">
              <el-tree
                class="filter-tree"
                :data="data"
                node-key="id"
                @node-click="handleNodeClick"
                :props="defaultProps"
                default-expand-all
                :filter-node-method="filterNode"
                ref="tree"
              ></el-tree>
            </div>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="描述" prop="remakes">
            <el-input
              v-model.trim="form.description"
              type="textarea"
              :rows="3"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="使用限制" prop="remakes">
            <el-input
              v-model.trim="form.use_limit"
              type="textarea"
              :rows="3"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="开始时间" prop="starTime">
            <el-date-picker
              v-model="form.starTime"
              type="date"
              placeholder="选择日期" class="w">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              v-model="form.endTime"
              type="date"
              placeholder="选择日期" class="w">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="是否无限期" prop="is_limit" class="custemitem">
            <el-select v-model.trim="form.is_limit" class="w">
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="软件" prop="software" class="custemitem">
            <el-select v-model.trim="form.software" class="w">
              <el-option>请选择</el-option>
              <el-option v-for="item in softList" :key="item.id" :label="item.assets_name"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toSave,getAssetsManageList} from '@/api/softwaremanage'
  import {getDepartmentWithTree} from "@/api/userManagement";

  export default {
    name: 'companyEdit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        loading: false,
        loadingText: '确 定',
        value1: '',
        value2: '',
        filterText: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name',
        },
        form: {
          id: '',
          os_licence_name: '',
          deptId: '',
          deptName: '',
          description: '',
          use_limit: '',
          key_license: '',
          is_limit: '',
          software: '',
          starTime: '',
          endTime: '',
        },
        rules: {
          os_licence_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_limit: [{required: true, trigger: 'blur', message: '请选择是否无限期'}],
        },
        title: '',
        dialogFormVisible: false,
        softList: [],
      }
    },
    created() {
      setTimeout(() => {
        this.showWithTree();
      }, 300)
    },
    methods: {
      showEdit(row) {
        this.loading = false
        this.loadingText = '确 定'
        this.getAssetsManageList()
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.is_limit = this.form.is_limit + "";
          console.log(row);
          this.form.starTime = row.start_time;
          this.form.endTime = row.end_time;
          this.form.software = row.software?parseInt(row.software):'';
        }
        this.dialogFormVisible = true
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.form.deptId = data.id;
        this.form.deptName = data.label;
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
      async showWithTree() {
        const {data} = await getDepartmentWithTree(this.queryForm)
        this.data = data
      },
      async getAssetsManageList() {
        const {data} = await getAssetsManageList()
        this.softList = data;
      },
    },
  }
</script>

