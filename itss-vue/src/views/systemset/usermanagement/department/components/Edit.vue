<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="600px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="编码" prop="code">
        <el-input v-model.trim="form.code" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="上级" prop="parent_name">
        <el-input
          placeholder="请输入内容"
          clearable
          v-model="form.parent_name"
          @click.native="openwin"
        >
          <!--<el-button-->
            <!--slot="append"-->
            <!--icon="el-icon-search"-->
            <!--@click="openwin"-->
          <!--&gt;</el-button>-->
        </el-input>
      </el-form-item>
      <el-form-item label="快速报障支持群组" prop="group_id" class="custemitem">
        <el-select v-model.trim="form.group_id" class="w">
          <el-option
            v-for="item in this.eventServiceManage"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="公司" prop="company_id">
        <el-select v-model.trim="form.company_id" class="w">
          <el-option
            v-for="item in this.company"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number
          v-model.trim="form.sort"
          autocomplete="off"
          :min="1"
        ></el-input-number>
      </el-form-item>
      <el-form-item label="唯一ID" prop="unique_id">
        <el-input v-model.trim="form.unique_id" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
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
  </el-dialog>
</template>

<script>
  import {
    sysDepartmentToEdit,
    sysDepartmentToAdd,
    sysCompanyGetList,
    getSysDepartmentInfo,
  } from '@/api/userManagement'
  import {getAllList} from '@/api/eventServiceManage'
  import Children from '@/components/bumenTree'

  export default {
    name: 'bumenEdit',
    components: {Children},
    data() {
      return {
        isDisable: false,
        bumen: '',
        form: {
          name: '',
          code: '',
          is_use: '',
          parent_id: '',
          group_id: '',
          company_id: '',
          sort: '',
          unique_id: '',
          is_del: '0',
          parent_name: '',
        },
        idx: '',
        company: [],
        eventServiceManage: [],
        tableData: [],
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'}],
          code: [{required: true, trigger: 'blur', message: '请输入编码'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
      this.sysCompanyGetList()
      this.getEventServiceManageList()
    },
    mounted() {
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
          this.getSysDepartmentInfo(row.id)
          // this.form = row
          // this.form.is_use = row.is_use == '1' ? '1' : '0'
          // this.form = Object.assign({}, this.form)
        }
        this.dialogFormVisible = true
      },
      openwin() {
        this.$refs['children'].showWin()
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
                const data = await sysDepartmentToAdd(this.form)
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage("失败", 'error');
                  this.isDisable = false;
                }
              } else {
                const data = await sysDepartmentToEdit(this.form)
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data')
                  this.close()
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
      deptset(data) {
        this.form.parent_id = data.id;
        this.form.parent_name = data.name;
      },
      // 获取公司列表
      async sysCompanyGetList() {
        const {data} = await sysCompanyGetList({});
        this.company = data.list;
      },
      // 服务群组列表
      async getEventServiceManageList() {
        const {data} = await getAllList({});
        this.eventServiceManage = data.data;
      },
      async getSysDepartmentInfo(id) {
        const {data} = await getSysDepartmentInfo({id: id});
        this.form = data.data;
        this.form.is_use = data.data.is_use == '1' ? '1' : '0';
      },
    },
  }
</script>

<style lang="scss" scoped>
  .filter-tree {
    margin-top: 0px;
  }

  .treeborder {
    padding: 20px;
    height: 200px;
    margin-top: 20px;
    border: 1px solid #dcdfe6;
    border-radius: 2.5px;
    overflow-y: auto;
  }

  .custemitem {
    ::v-deep {
      label {
        line-height: 16px;
      }
    }
  }
</style>
