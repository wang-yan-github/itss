<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1000px"
    @close="close"
    top="80px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="名称" prop="name">
            <el-input
              v-model.trim="form.name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="状态" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="禁用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="排序" prop="sort">
            <el-input
              v-model.trim="form.sort"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="上级" prop="parent_name">
            <el-input
              placeholder="请输入内容"
              v-model="form.parent_name"
              @click.native="openwin"
            >
<!--              <el-button-->
<!--                slot="append"-->
<!--                icon="el-icon-search"-->
<!--                @click="openwin"-->
<!--              ></el-button>-->
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="评审人" prop="approveName">
            <el-input placeholder="请输入内容" v-model="form.approveName" @focus="openwin1" clearable>
              <!--<el-button slot="append" icon="el-icon-search" @click="openwin1"></el-button>-->
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="描述" prop="description">
            <el-input
              v-model.trim="form.description"
              autocomplete="off"
              type="textarea"
              :rows="5"
            ></el-input>
          </el-form-item>
        </el-col>

<!--        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--          <el-form-item label="审批人">-->
<!--            <el-table-->
<!--              v-loading="listLoading"-->
<!--              :data="form.approverUsers"-->
<!--              border-->
<!--              highlight-current-row-->
<!--              :element-loading-text="elementLoadingText"-->
<!--              @selection-change="setSelectRows"-->
<!--              height="300"-->
<!--            >-->
<!--              <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                type="selection"-->
<!--                align="center"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                prop="user_name"-->
<!--                label="用户名"-->
<!--                align="center"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                prop="name"-->
<!--                label="姓名"-->
<!--                align="center"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                prop="status"-->
<!--                label="状态"-->
<!--                sortable-->
<!--                align="center"-->
<!--                :formatter="formatterStatus"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                prop="telephone"-->
<!--                label="电话"-->
<!--                align="center"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                show-overflow-tooltip-->
<!--                prop="departmentName"-->
<!--                label="部门"-->
<!--                align="center"-->
<!--                sortable-->
<!--              ></el-table-column>-->
<!--            </el-table>-->
<!--            <div style="margin-top: 10px">-->
<!--              <el-button-->
<!--                type="danger"-->
<!--                icon="el-icon-delete"-->
<!--                @click="handleDelete"-->
<!--              >-->
<!--                移除选择对象-->
<!--              </el-button>-->
<!--              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">-->
<!--                添加联系人-->
<!--              </el-button>-->
<!--            </div>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <children
      ref="children"
      @categoryData="getCategoryData"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @listData="getUsers"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
    <children1 ref="children1" @userData="deptset" @fetch-data=" { bumen }"></children1>
  </el-dialog>
</template>

<script>
  import {categoryAdd, getCategoryDetail} from '@/api/knowledgeConfig'
  import Children from './knowCategory'
  // import Dialogone from './contactpeople'
  import Dialogone from '@/components/UsersDia'
  import Children1 from '@/components/UserSelectModel'

  export default {
    name: 'bumenEdit',
    components: {Children, Dialogone,Children1},
    data() {
      return {
        isDisable: false,
        input2: '',
        listLoading: true,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        bumen: '',
        form: {
          id: '',
          name: '',
          is_use: '',
          sort: '',
          parent_id: '',
          parent_name: '',
          description: '',
          approverUsers: [],
          approve: '',
          approveName: ''
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_use: [
            {required: true, trigger: 'blur', message: '请选择状态'},
          ],
          approveName: [{required: true, trigger: 'blur', message: '请选择评审人'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    mounted() {
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          // this.form = Object.assign({}, row)
          // this.form.parent_id = row.parent_id;
          // this.form.parent_name = row.parent_name;
          this.fetchData(row.id)
        }
        this.dialogFormVisible = true
      },
      fetchData(id) {
        getCategoryDetail(id).then((res) => {
          this.form = res.data.knowledgeCategory;
          // var x = [];
          // x = res.data.knowledgeApprovers
          // console.log(x)


        });
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      handleAdd() {
        this.$refs['dialogone'].showWin()
      },
      handleDelete() {
        if (this.selectRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRows.forEach((item, i) => {
              this.form.approverUsers.some((x, i) => {
                if (x.id == item.id) {
                  this.form.approverUsers.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
          })
          // const ids = this.selectRows.map((item) => item.id).join()
          // console.log(ids)
          // this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          //   //const { msg } = await doDelete({ ids })
          //   this.$baseMessage('模拟删除成功', 'success')
          //   //this.fetchData()
          // })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await categoryAdd(this.form)
              if (data.code == 0) {
                this.$baseMessage(data.msg, 'success');
                this.$emit('fetch-data');
                this.close();
              } else {
                this.$baseMessage("失败", 'error');
                this.isDisable = false;
              }
            }catch(e){
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false
          }
        })
      },
      getCategoryData(row) {
        this.form.parent_id = row.id;
        this.form.parent_name = row.name;
      },
      formatterStatus(row) {
        switch (row.status + "") {
          case "1":
            return "活动";
          case "2":
            return "暂停";
          case "3":
            return "已禁止";
          default:
            return "";
        }
      },
      getUsers(row) {
        row.forEach((item, i) => {
          let index = this.form.approverUsers.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.approverUsers.push(item)
          }
        })
      },
      openwin1() {
        this.$refs['children1'].showWin()
      },
      deptset(data) {
        this.form.approve = data.id;
        this.form.approveName = data.name;
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

  .custemitem {
    ::v-deep {
      label {
        line-height: 16px;
      }
    }
  }
</style>
