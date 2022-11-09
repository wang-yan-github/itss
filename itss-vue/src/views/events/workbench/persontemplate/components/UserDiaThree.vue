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
      ref="tree" @node-click="handleNodeClick">
    </el-tree></div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { tree} from '@/api/schemeClassIfication.js'
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
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        sendData:""
      }
    },
    created() {
      this.select();
    },
    methods: {
      showWin() {
        this.title = '选择方案分类'
        this.dialogFormVisible = true
      },
      select(){//触发子组件城市选择-选择城市的事件\
        var that  =this ;
        tree().then((res) => {
          that.data = res.data ;
        });
      },
      filterNode(value, data) {
        if (!value) return true;

        return true;
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this ;
        that.$emit('fetch-data',that.sendData)
        this.close();
      },
      handleNodeClick(data) {
        this.filterText = data.name ;
        this.sendData = data ;
      }
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
