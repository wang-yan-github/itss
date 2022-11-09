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
          <el-form-item label="名称" prop="name">
            <el-input v-model.trim="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item label="状态" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item label="发布人" prop="publisherName">
            <el-input
              placeholder="请输入内容"
              v-model="form.publisherName"
              @focus="openwin"
              clearable
            >
              <!--<el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>-->
            </el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="">
            <p class="ts" style="color: red">
              评审人: *排序号小的先审批,且序号不能相同
            </p>
            <el-table
              v-loading="listLoading"
              :data="userData"
              border
              highlight-current-row
              :element-loading-text="elementLoadingText"
              @selection-change="setSelectRows"
            >
              <el-table-column
                show-overflow-tooltip
                type="selection"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="user_name"
                label="用户名"
                sortable
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="name"
                label="姓名"
                header-align="center"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="status"
                label="状态"
                align="center"
                width="120px;"
              >
                <template #default="scope">
                  <el-tag
                    size="small"
                    type="success"
                    v-if="scope.row.status == '1'"
                  >
                    活动
                  </el-tag>
                  <el-tag
                    size="small"
                    type="warning"
                    v-else-if="scope.row.status == '2'"
                  >
                    暂停
                  </el-tag>
                  <el-tag size="small" type="danger" v-else>已禁止</el-tag>
                </template>
              </el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="telephone"
                label="电话"
                align="center"
              ></el-table-column>
              <!--<el-table-column show-overflow-tooltip prop="user_type" label="类型" align="center" sortable></el-table-column>-->
              <el-table-column
                show-overflow-tooltip
                label="排序"
                align="center"
              >
                <template #default="scope">
                  <el-input v-model="scope.row.sort" type="number"></el-input>
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
                添加评审人
              </el-button>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="isDisable">
        确 定
      </el-button>
    </div>
    <children
      ref="children"
      @userData="deptset"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @listData="listUser"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
  </el-dialog>
</template>

<script>
import {
  publishCategoryToAdd,
  publishCategoryToEdit,
  getPublishCategory,
} from '@/api/publish'
import Children from '@/components/UserSelectModel'
import Dialogone from '@/components/UsersDiaApprove'

export default {
  name: 'bumenEdit',
  components: { Children, Dialogone },
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
        name: '',
        is_use: '',
        publisher: '',
        publisherName: '',
        publishApproveInfos: [],
      },
      userData: [],
      rules: {
        name: [{ required: true, trigger: 'blur', message: '请输入名称' }],
        is_use: [{ required: true, trigger: 'blur', message: '请选择状态' }],
        remakes: [{ required: true, trigger: 'blur', message: '请输入描述' }],
        publisherName: [
          { required: true, trigger: 'submit', message: '请选择发布人' },
        ],
      },
      title: '',
      idx: '',
      dialogFormVisible: false,
    }
  },
  created() {
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  mounted() {},
  methods: {
    showEdit(row) {
      this.isDisable = false
      if (!row) {
        this.title = '添加'
        this.idx = '1'
      } else {
        this.title = '编辑'
        this.idx = '2'
        this.userData = []
        this.getPublishCategory(row)
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    openwin() {
      this.$refs['children'].showWin()
    },
    handleAdd() {
      console.log(this.userData)
      this.$refs['dialogone'].showWin(this.userData)
    },
    handleDelete() {
      if (this.selectRows.length > 0) {
        const ids = this.selectRows.map((item) => item.id).join()
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          this.selectRows.forEach((item, i) => {
            this.userData.some((x, i) => {
              if (x.user_name == item.user_name) {
                this.userData.splice(i, 1)
                //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                return true
              }
            })
          })
          this.$baseMessage('删除成功', 'success')
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
      this.userData = []
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    deptset(data) {
      this.form.publisher = data.id
      this.form.publisherName = data.name
    },
    listUser(row) {
      for (var a in row) {
        if (!this.isContain(this.userData, row[a])) {
          this.userData.push(row[a])
        }
      }
    },
    isContain(arr, value) {
      for (var i = 0; i < arr.length; i++) {
        if (value.user_name === arr[i].user_name) {
          return true
        }
      }
      return false
    },
    async getPublishCategory(row) {
      const { data } = await getPublishCategory(row)
      this.userData = data.publishApproveInfos
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    save() {
      if (this.userData.length == 0) {
        this.$baseMessage('请选择审批人', 'error')
        return
      }

      var sortNum = []
      for (var i = 0; i < this.userData.length; i++) {
        if (
          this.userData[i].sort == null ||
          this.userData[i].sort == '' ||
          this.userData[i].sort == undefined
        ) {
          this.$baseMessage('请填写排序', 'error')
          return
        }
        if (this.userData[i].sort < 1) {
          this.$baseMessage('序号不能小于1', 'error')
          return
        }

        var user = this.userData[i].sort
        for (var j = 0; j < sortNum.length; j++) {
          if (user == sortNum[j]) {
            this.$baseMessage('排序不能相同', 'error')
            return
          }
        }
        sortNum.push(this.userData[i].sort)
      }

      this.isDisable = true
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          if (this.idx == '1') {
            this.form.publishApproveInfos = this.userData
            publishCategoryToAdd(this.form).then((res) => {
              if (res.code == 0) {
                this.$baseMessage(res.msg, 'success')
                this.$emit('fetch-data')
                this.close()
              } else {
                this.$baseMessage(res.msg, 'error')
                this.isDisable = false
              }
            })
          } else {
            this.form.publishApproveInfos = this.userData
            publishCategoryToEdit(this.form).then((res) => {
              if (res.code == 0) {
                this.$baseMessage(res.msg, 'success')
                this.$emit('fetch-data')
                this.close()
              } else {
                this.$baseMessage(res.msg, 'error')
                this.isDisable = false
              }
            })
          }
        } else {
          this.isDisable = false
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
