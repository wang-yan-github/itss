<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="名称" prop="request_name">
        <el-input v-model.trim="form.request_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="状态"
        prop="is_enable"
      >
        <el-select v-model.trim="form.is_enable" class="w">
          <el-option label="禁用" value="0"></el-option>
          <el-option label="使用" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        label="处理人"
        prop="handler"
      >
        <el-select v-model="form.handler" class="w">
          <el-option v-for="(item,index) in users" :key="index" :label="item.name" :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item
        label="描述"
        prop="description"

      >
        <el-input v-model.trim="form.description" type="textarea" :rows="10"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loadingText }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toSave} from '@/api/assetsRequestConfig'
  import {getList} from '@/api/userManagement'

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
        users: [],
        form: {
          id: '',
          request_name: '',
          is_enable: '',
          handler: '',
          description: '',
        },
        rules: {
          request_name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_enable: [
            {required: true, trigger: 'blur', message: '请选择状态'},
          ],
          handler: [
            {required: true, trigger: 'blur', message: '请选择状态'},
          ],
          description: [{required: true, trigger: 'blur', message: '请输入描述'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
      this.getUsers();
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
          this.form.is_enable = row.is_enable + "";
        }
        this.dialogFormVisible = true
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
      async getUsers() {
        var param = {};
        param.pageNo = 1;
        param.pageSize = 10000;
        const {data} = await getList(param)
        this.users = data.list
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>

