<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="140px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        label="自助服务时只显示本公司服务类型"
        prop="is_show"
        class="custemitem"
      >
        <el-select v-model.trim="form.is_show" :value="form.is_show" class="w">
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="默认服务类别" prop="username" class="custemitem">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText"
          clearable
        ></el-input>
        <div class="treeborder">
          <el-tree
            class="filter-tree"
            :data="data"
            :props="defaultProps"
            default-expand-all
            :filter-node-method="filterNode"
            ref="tree"
          ></el-tree>
        </div>
      </el-form-item>
     <el-form-item
        label="自助分派到服务组"
        prop="isac"
      >
        <el-select v-model.trim="form.isac" class="w">
          <el-option label="是" value="0"></el-option>
          <el-option label="否" value="1"></el-option>
        </el-select>
      </el-form-item>-->
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" :value="form.is_use" class="w">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序号" prop="sort">
        <el-input-number
          v-model.trim="form.sort"
          autocomplete="off"
          :min="1"
          :max="10"
        ></el-input-number>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {sysCompanyToAdd, sysCompanyToEdit} from '@/api/userManagement'

  export default {
    name: 'companyEdit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        isDisable: false,
        filterText: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'label',
        },
        form: {
          name: '',
          is_show: '0',
          is_use: '1',
          sort: '',
        },
        idx: '',
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          isac: [
            {required: true, trigger: 'blur', message: '请选择是否事件工程师'},
          ],
          remakes: [{required: true, trigger: 'blur', message: '请输入描述'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
          this.idx = '1'
        } else {
          this.title = '编辑'
          this.idx = '2'
          console.log(row)
          this.form = Object.assign({}, row)
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
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
              try{
                  if (this.idx === '1') {
                    const data = await sysCompanyToAdd(this.form)
                    if (data.code == 0) {
                      this.$baseMessage(data.msg, 'success');
                      this.$emit('fetch-data');
                      this.close();
                    } else {
                      this.$baseMessage("失败", 'error');
                      this.isDisable = false;
                    }
                  } else {
                    const data = await sysCompanyToEdit(this.form)
                    if (data.code == 0) {
                      this.$baseMessage(data.msg, 'success');
                      this.$emit('fetch-data');
                      this.close();
                    } else {
                      this.$baseMessage("失败", 'error');
                      this.isDisable = false;
                    }
                  }
              }catch(e){
                 this.isDisable = false;
              }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
    },
  }
</script>

