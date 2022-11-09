<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="600px" @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="所属盘点期">

        {{form.inventory_Name}}
        <el-input v-model.trim="form.inventory_id" autocomplete="off" style="display: none"></el-input>
        <el-input v-model.trim="form.inventory_Name" autocomplete="off" style="display: none"></el-input>
      </el-form-item>

      <el-form-item label="配置项类型" prop="assets_type_id">

        <el-select v-model="form.assets_type_id" placeholder="配置项类型" @change="changeAssetsType"  clearable>
           <el-option>请选择</el-option>
          <el-option
            v-for="item in this.assetsTypeList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
			<el-form-item label="配置项子类" prop="assets_children_id">
        <el-select v-model="form.assets_children_id" placeholder="配置子类" clearable>
           <el-option>请选择</el-option>
          <el-option
            v-for="item in this.assetsChildrenList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
			</el-form-item>
			<el-form-item label="部门"  prop="dept_id">
        <el-input readonly="readonly" v-model="form.deptName"> </el-input>
				<div class="treeborder">
          <el-tree
				  class="filter-tree"
				  :data="data"
				  :props="defaultProps"
          @node-click="handleNodeClick"
				  default-expand-all
				  :filter-node-method="filterNode"
				  ref="tree">
				</el-tree></div>
			</el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

  </el-dialog>
</template>

<script>
  import {
    save, info,edit,getList
  } from '@/api/origin'

  import {getAssetsType} from '@/api/configtypemanagement'
  import { getDepartmentWithTree } from '@/api/userManagement'
 // import { saveAssetsInventoryConfig } from "../../../../api/periodManage";
  import { saveAssetsInventoryConfig } from '@/api/periodManage'
  export default {
    name: 'generate',
		watch: {
		  filterText(val) {
		    this.$refs.tree.filter(val);
		  }
		},
    data() {
      return {
        assetsTypeList:'',
        assetsChildrenList:[],
        form: {
          inventory_id: '',
          assets_type_id: '',
          assets_children_id: '',
          dept_id: '',
          deptName: '',
          showDeptName: '',
          remakes: '',
          resource_name: '',
          sort: '',
          is_use: '',
          description: '',
          child_asset:'',
          currentSelectAssestsType:'',
        },
        rules: {
          assets_type_id: [
            {
              required: true,
              trigger: 'blur',
              message: '请选择配置项类型',
            },
          ],
          assets_children_id: [{
            required: true,
            trigger: 'blur',
            message: '请输入配置子类'
          },],
          showDeptName: [{
            required: true,
            trigger: 'blur',
            message: '请选择部门'
          },],
          dept_id: [{
            required: true,
            trigger: 'blur',
            message: '请重新选择部门名称'
          },],
          inventory_id: [{
            required: false,
            trigger: 'blur',
            message: '请输入名称'
          },],

        },
        title: '',
        dialogFormVisible: false,
        filterText: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    created() {
      this.fetchAssetsTypeData()
      this.showWithTree()

    },
    props: ['sendData'],
    methods: {
      changeAssetsType(data){
        for (let i = 0; i < this.assetsTypeList.length; i++) {
              var json = this.assetsTypeList[i];
              if(json.id == data){
                this.assetsChildrenList =  this.assetsTypeList[i].assetsChildrens;
                break;
              }
        }
        console.log(this.assetsChildrenList)
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.form.dept_id = data.id;
        this.form.deptName = data.name;
        this.form.showDeptName =data.name;
      },
      showEdit(row) {

        this.title = '生成盘点资产'
        row.status = row.status+"";//status是integer 要用到的时候string
        row.inventory_id = row.id
        row.inventory_Name = row.name
        this.form = Object.assign({},this.form, row)
        this.dialogFormVisible = true
      },
	  filterNode(value, data) {
	    if (!value) return true;
	    return data.label.indexOf(value) !== -1;
	  },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        // debugger
        // var that = this;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.form.id="";
            const { msg } = await saveAssetsInventoryConfig(this.form)
            this.$baseMessage(msg, 'success')
            this.$emit('fetch-data')
            this.close()
          } else {
            return false
          }
        })


      },
      fetchData() {
        var that  =this ;
        getList(that.queryForm).then((res) => {
          that.list = res.data.list ;
          that.total = res.data.total
        });
      },
      async fetchAssetsTypeData() {
        this.listLoading = true
        const {data} = await getAssetsType({})
        this.assetsTypeList = data

        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async showWithTree() {
        const { data } = await getDepartmentWithTree(this.queryForm)
        this.data = data
      },
    },
  }
</script>
