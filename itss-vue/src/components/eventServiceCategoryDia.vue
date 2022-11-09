<!--自助服务类别、自助报障类别-->
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
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {
  getList, treeByUser
} from '@/api/selfService'
import {getDepartmentWithTree} from "@/api/userManagement";

  export default {
    name: 'eventServiceCategoryDia',
    watch: {
      filterText(val) {
        // console.log('filterText:', val)
        this.$refs.tree.filter(val);
      }
    },
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        filterText: '',
        sendData: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        queryForm: {
          id: '',
          name: '',
          useUser: '',
        }
      }
    },
    created() {
      setTimeout(() => {
        this.select()
      }, 300)
    },
    methods: {
      showWin(id,isUseUser) {
        console.log("showWin:", id)
        this.title = '服务类别';
        this.dialogFormVisible = true;
        if(id != undefined && id != null) {
          this.queryForm.id = id;
        }
        if(isUseUser == 1) {

          this.queryForm.useUser = 1;
        }
        // this.queryForm.id = id;
        this.select(id);
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.name.indexOf(value) !== -1;
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this;
        that.$emit('fetch-data', that.sendData)
        this.close();
      },
      select(id) {//触发子组件城市选择-选择城市的事件\
        var that = this;
        console.log("111111111111")
        treeByUser(this.queryForm).then((res) => {
          that.data = res.data;
        });
      },
      handleNodeClick(data) {
        var that = this;
        // that.filterText = data.name;
        that.sendData = data;
      }
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
