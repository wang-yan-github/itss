<!--公共 问题类别 弹窗-->
<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close" append-to-body>
    <el-input placeholder="输入关键字进行过滤" v-model="queryForm.name" @keyup.enter.native="queryData"></el-input>
    <div class="treeborder">
      <el-tree class="filter-tree" :data="data" :props="defaultProps" default-expand-all
               :filter-node-method="filterNode" @node-click="handleNodeClick" ref="tree">
      </el-tree>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getQuestionCategoryList} from '@/api/question'

  export default {
    name: 'diaQuestionCategory',
    watch: {
      queryForm(val) {
        this.$refs.tree.filter(val);
      }
    },
    data() {
      return {
        row: {},
        title: '',
        dialogFormVisible: false,
        filterText: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        queryForm: {
          name: '',
        },
      }
    },
    created() {
      
    },
    methods: {
      showWin() {
        this.title = '选择问题类别';
        this.dialogFormVisible = true;
        this.queryForm.name='';
        this.row = [];//清空
        setTimeout(() => {
        this.showWithTree()
      }, 300)
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.row.id = data.id;
        this.row.name = data.name;
        console.log(this.row.name);
        // this.$parent.
        // this.$parent.form.deptName = this.row.name;
        // this.queryParams.deptId = data.id;
        // this.handleQuery();
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false;
      },
      queryData(){
        this.showWithTree()
      },
      save() {
        var that = this;
        if(undefined == that.row.id){
          that.$baseMessage('请选择', 'error')
        }else{
          that.$emit('questionCategoryData', that.row);
          this.close();
        }
      },
      async showWithTree() {
        const {data} = await getQuestionCategoryList(this.queryForm);
        this.data = data;
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
</style>
