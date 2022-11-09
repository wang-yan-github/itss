<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1200px"
    @close="close"
    top="80px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item label="名称" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item label="状态" prop="isac">
            <el-select v-model.trim="form.isac" class="w">
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item label="发布人" prop="bumen">
            <el-input
              placeholder="请输入内容"
              v-model="input2"
              @click.native="openwin"
            >
              <!--<el-button-->
                <!--slot="append"-->
                <!--icon="el-icon-search"-->
                <!--@click="openwin"-->
              <!--&gt;</el-button>-->
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="">
            <p class="ts">评审人: *排序号小的先审批</p>
            <el-table
              v-loading="listLoading"
              :data="tableData"
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelectRows"
              height="300"
            >
              <el-table-column
                show-overflow-tooltip
                type="selection"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                type="index" label="序号"
                width="60"
                sortable
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="username"
                label="姓名"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="state"
                label="状态"
                sortable
                align="center"
              ></el-table-column>

              <el-table-column
                show-overflow-tooltip
                prop="gongchengshi"
                label="电话"
                align="center"
              ></el-table-column>

              <el-table-column
                show-overflow-tooltip
                prop="paixu"
                label="类型"
                align="center"
                sortable
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="排序"
                align="center"
              >
                <template #default={row}>
                  <el-input v-model="row.paixun"></el-input>
                </template>
              </el-table-column>
            </el-table>
            <div style="margin-top: 10px">
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete"
              >
                移除选择对象
              </el-button>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
                添加审批人
              </el-button>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <children
      ref="children"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
  </el-dialog>
</template>

<script>
  import {doEdit} from '@/api/userManagement'
  import Children from './takeman'
  import Dialogone from './contactpeople'

  export default {
    name: 'bumenEdit',
    components: {Children, Dialogone},
    data() {
      return {
        isDisable: false,
        input2: '',
        listLoading: true,
        selectRows: '',
        loading: false,
        elementLoadingText: '正在加载...',
        tableData: [],
        bumen: '',
        form: {
          username: '',
          isac: '',
          remakes: '',
        },
        rules: {
          username: [{required: true, trigger: 'blur', message: '请输入名称'}],
          isac: [
            {required: true, trigger: 'blur', message: '请选择是否事件工程师'},
          ],
          remakes: [{required: true, trigger: 'blur', message: '请输入描述'}],
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
          this.form = Object.assign({}, row)
        }
        this.dialogFormVisible = true
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      handleAdd() {
        this.$refs['dialogone'].showWin()
      },
      handleDelete() {
        if (this.selectRows.length > 0) {
          const ids = this.selectRows.map((item) => item.id).join()
          console.log(ids)
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            //const { msg } = await doDelete({ ids })
            this.$baseMessage('模拟删除成功', 'success')
            //this.fetchData()
          })
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
              const data = await doEdit(this.form)
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
