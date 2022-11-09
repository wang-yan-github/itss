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
      v-model="queryForm.type_name"
      @keyup.enter.native="queryData"
    ></el-input>
    <div class="treeborder">
      <el-tree
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        default-expand-all
        @node-click="handleNodeClick"
        :filter-node-method="filterNode"
        ref="tree"
      ></el-tree>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">选 择</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { getParentList } from '@/api/sourceSet'

export default {
  name: 'diaOne',
  watch: {
    queryForm(val) {
      this.$refs.tree.filter(val)
    },
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
        label: 'type_name',
      },
      queryForm: {
        type_name: '',
      },
    }
  },
  created() {},
  methods: {
    showWin(id) {
      this.title = '请选择'
      this.dialogFormVisible = true
      this.queryForm.type_name = ''
      setTimeout(() => {
        this.showWithTree(id)
      }, 300)
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    close() {
      // this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    queryData() {
      this.showWithTree()
    },
    save() {
      var that = this
      console.log(this.row)
      that.$emit('deptData', this.row)
      this.close()
    },

    async showWithTree(id) {
      this.queryForm.id = id
      const { data } = await getParentList(this.queryForm)
      this.data = data
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.row.id = data.id
      this.row.name = data.type_name
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
