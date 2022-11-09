<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    append-to-body
  >
    <el-input placeholder="输入关键字进行过滤" v-model="queryForm"></el-input>
    <div class="treeborder">
      <el-tree
        class="filter-tree"
        :data="data"
        :props="defaultProps"
        default-expand-all
        :filter-node-method="filterNode"
        @node-click="handleNodeClick"
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
  import {getList} from '@/api/assetsExpandForm'

  export default {
    name: 'diaTwo',
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
          label: 'name',
        },
        queryForm: '',
      }
    },
    created() {
      setTimeout(() => {
        this.fetchData()
        this.listLoading = false
      }, 300)
    },
    methods: {
      showWin() {
        this.title = '父级表单选择'
        this.fetchData()
        this.dialogFormVisible = true
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.row.id = data.id
        this.row.name = data.name
        console.log(this.row);
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this
        that.$emit('deptData', that.row)
        this.close();
      },
      async fetchData() {
        this.listLoading = true
        const {data} = await getList(this.queryForm)
        this.data = data
      },
    },
  }
</script>
