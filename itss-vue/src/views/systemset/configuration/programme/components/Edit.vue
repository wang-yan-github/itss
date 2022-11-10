<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="90px">
      <el-form-item label="名称" prop="name">
        <el-input v-model.trim="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="状态" prop="is_use">
        <el-select v-model.trim="form.is_use" class="w">
          <el-option label="启用" value="0"></el-option>
          <el-option label="禁用" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="上级" prop="parent_id" v-model.trim="form.parent_id">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText"
          clearable
        ></el-input>
        <div class="treeborder">
          <el-tree
            class="filter-tree"
            :data="data"
            :props="defaultProps"
            default-expand-all
            :filter-node-method="filterNode"
            ref="tree"
            @node-click="handleNodeClick"
          ></el-tree>
        </div>
      </el-form-item>
      <el-form-item label="排序">
        <el-input v-model.trim="form.sort" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {tree, save, edit, view} from '@/api/schemeClassIfication'

  export default {
    name: 'schemeClassIficationEdit',
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val)
      },
    },
    data() {
      return {
        isDisable: false,
        filterText: '',
        data: [],
        defaultProps: {
          children: 'children',
          label: 'name',
        },
        form: {
          parent_id: "",
          parent_name: "",
          name: "",
          is_use: "",
          sort: ""
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'},],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
      this.select();
    },
    mounted() {
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        this.select();
        this.filterText = "";
        this.form.parent_id = "";
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.fetchData(row)
        }
        this.dialogFormVisible = true
      },
      fetchData(id) {
        view(id).then((res) => {
          this.form = res.data;
          this.filterText = res.data.parent_name;
        });
      },
      filterNode(value, data) {
        if (!value) return true
        return data.name.indexOf(value) !== -1
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        var that = this;
        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
                if (that.form.id == null || that.form.id == undefined || that.form.id == "") {
                  save(that.form).then((res) => {
                    if (res.code == 0) {
                      that.$baseMessage("操作成功", 'success');
                      that.$emit('fetch-data');
                      that.close();
                    } else {
                      that.$baseMessage(res.msg, 'error');
                      that.isDisable = false;
                    }
                  });
                } else {
                  edit(that.form).then((res) => {
                    if (res.code == 0) {
                      that.$baseMessage("操作成功", 'success');
                      that.$emit('fetch-data');
                      that.close();
                    } else {
                      that.$baseMessage(res.msg, 'error');
                      that.isDisable = false;
                    }
                  });
                }
            }catch(e){
                that.isDisable = false;
            }
          } else {
            that.isDisable = false;
            return false
          }
        })
      },
      select() {//触发子组件城市选择-选择城市的事件\
        var that = this;
        tree().then((res) => {
          that.data = res.data;
        });
      },
      handleNodeClick(data) {
        console.log(data);
        var that = this;
        that.filterText = data.name;
        that.form.parent_id = data.id;
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

  .custemitem {
    ::v-deep {
      label {
        line-height: 16px;
      }
    }
  }
</style>
