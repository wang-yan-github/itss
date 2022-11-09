<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    append-to-body
  >
    <el-input
      placeholder="输入关键字进行过滤"
      v-model="filterText">
    </el-input>
    <div class="treeborder"><el-tree
      class="filter-tree"
      :data="data"
      :props="defaultProps"
      default-expand-all
      :filter-node-method="filterNode"
      @node-click="handleNodeClick"
      ref="tree">
    </el-tree></div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { getCategoryWithTree } from '@/api/knowledgeInfo'
  export default {
    name: 'diaOne',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    data() {
      return {
        row:{},
        title: '',
        dialogFormVisible: false,
        filterText: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    created() {
      setTimeout(() => {
        this.showWithTree()
      }, 300)
    },
    methods: {
      // 节点单击事件
      handleNodeClick(data) {
        this.row.id = data.id;
        this.row.name = data.name;
        // this.$parent.
        // this.$parent.form.deptName = this.row.name;
        // this.queryParams.deptId = data.id;
        // this.handleQuery();
      },
      showWin() {
        this.title = '选择知识类别'
        this.dialogFormVisible = true
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this ;
        that.$emit('deptData',that.row)
        this.close();
      },
      async showWithTree() {
        const { data } = await getCategoryWithTree(this.queryForm)
        this.data = data

      },
    },
  }
</script>
<style lang="scss" scoped>
  .filter-tree{margin-top:0px;}
  .treeborder {
    padding: 20px;
    height: 200px;
    margin-top:20px;
    border: 1px solid #dcdfe6;
    border-radius: 2.5px;
    overflow-y: auto;
  }
</style>
