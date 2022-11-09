<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
<!--    <el-input placeholder="输入关键字回车进行过滤" v-model="queryForm.name" @change="selectOne"></el-input>-->
    <div class="treeborder">
      <el-tree
        class="filter-tree"
        show-checkbox
        :check-strictly="checkstrictly"
        node-key="id"
        :data="data"
        :props="defaultProps"
        default-expand-all
        :filter-node-method="filterNode"
        ref="tree"
      ></el-tree>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {
    getSysAuthorityByTree,
    powerSysRole,
    getAllPermission,
  } from '@/api/userManagement'

  export default {
    name: 'diaOne',
    watch: {
      queryForm(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        isDisable: false,
        title: '',
        dialogFormVisible: false,
        queryForm: {
          name: '',
        },
        data: [],
        checkstrictly: true,
        defaultProps: {
          children: 'children',
          label: 'name',
        },
        form: {
          id: '',
          authorityIdList: [],
        },
        test: {},
        selectRows: [],
      }
    },
    created() {
      this.checkstrictly = true
      setTimeout(() => {
        this.fetchData()
      }, 300)
    },
    methods: {
      // handleCheckChange (data, checked, indeterminate) {
      //   console.log(data)
      //
      // },
      showWin(row) {
        this.isDisable = false;
        this.checkstrictly = true
        console.log("row", row)
        this.title = '设置权限'
        this.dialogFormVisible = true
        this.fetchData()
        this.getAll(row)
        this.form = row
        this.$nextTick(() => {
        })
      },
      getAll(row) {
        getAllPermission(row.id).then((res) => {
          console.log("getAll-res", res)
          this.form.authorityIdList = res.data
          console.log(this.form.authorityIdList)
          this.$refs.tree.setCheckedKeys(this.form.authorityIdList)

          setTimeout(() => {
            this.checkstrictly = false
          }, 300)
        })
      },
      filterNode(value, data) {
        if (!value) return true
        return data.label.indexOf(value) !== -1
      },
      close() {
        // this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        // let nodes = this.$refs.tree
        //   .getCheckedKeys()
        //   .concat(this.$refs.tree.getHalfCheckedKeys())
        // console.log(nodes)
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.form.authorityIdList = this.$refs.tree
          .getCheckedKeys()
          .concat(this.$refs.tree.getHalfCheckedKeys())
        console.log(this.form)
        powerSysRole(this.form).then((res) => {
          if (res.code == 0) {
            this.$baseMessage('设置成功', 'success');
            // this.$message.success('设置成功')
            this.fetchData()
            this.close()
          } else {
            this.$baseMessage("失败", 'error');
            this.isDisable = false;
          }
        })
      },
      fetchData() {
        getSysAuthorityByTree(this.queryForm).then((res) => {
          this.data = res.data
        })
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      selectOne(val){
        this.queryForm.name = val;
        this.fetchData()
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
    height: 300px;
    margin-top: 20px;
    border: 1px solid #dcdfe6;
    border-radius: 2.5px;
    overflow-y: auto;
  }
</style>
