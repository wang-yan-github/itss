<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
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
  ref="tree">
</el-tree></div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>

  export default {
    name: 'diaOne',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        filterText: '',
        data: [{
          id: 1,
          label: '拓维科技',
          children: [{
            id: 4,
            label: '信息技术部',
            children: [{
              id: 9,
              label: '纬创软件'
            }, {
              id: 10,
              label: '人力资源部'
            }, {
              id: 11,
              label: '信息管理部'
            }, {
              id: 12,
              label: '技术支持部'
            }]
          }]
        }, {
          id: 2,
          label: '护航科技',
          children: [{
            id: 5,
            label: '华南区公司'
          }, {
            id: 6,
            label: '上海分部'
          }]
        }, {
          id: 3,
          label: '总公司',
          children: [{
            id: 7,
            label: '宿迁分公司'
          }, {
            id: 8,
            label: '南京分公司'
          }]
        }],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    created() {},
    methods: {
      showWin() {
        this.title = '查询变更类别'
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
        this.close();
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
