<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1200px"
    @close="close"
    top="50px"
  >
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="属性" name="first">
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-row :gutter="10">
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="类型" prop="username">
                 <el-select v-model.trim="form.isac" class="w">
                  <el-option label="是" value="0"></el-option>
                  <el-option label="否" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="执行时间" prop="username">
                <el-input
                  v-model.trim="form.username"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="开始日期" prop="username">
                 <el-date-picker
                  v-model="value1"
                  type="date"
                  placeholder="选择日期"
                  class="w"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="结束日期" prop="username">
                <el-date-picker
                  v-model="value1"
                  type="date"
                  placeholder="选择日期"
                  class="w"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="每 ( 工作日 ):" prop="username">
                <el-input v-model.trim="form.remakes"></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="备注" prop="username">
                <el-input
                  v-model.trim="form.username"
                  autocomplete="off"
                  type="textarea"
                  :rows="8"
                ></el-input>
              </el-form-item>
            </el-col>          
          </el-row>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="工单模板" name="second">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          border
          highlight-current-row
          :element-loading-text="elementLoadingText"
          @selection-change="setSelectRows"
          height="400"
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

      <el-table-column show-overflow-tooltip prop="fuzeren" label="联系人公司" align="center">
      </el-table-column>

      <el-table-column
        show-overflow-tooltip
        prop="gongchengshi"
        label="工程师"
        align="center"
      ></el-table-column>
      <el-table-column prop="isac" show-overflow-tooltip label="我司对接部门" width="200" align="center">
       
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="paixu"
        label="EMAIL"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="paixu"
        label="PHONE"
        align="center"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="paixu"
        label="备注"
        align="center"
        sortable
      ></el-table-column>
        </el-table>
        <div style="margin-top: 10px">
          <el-button type="danger" icon="el-icon-delete" @click="handleDelete">
            移除选择对象
          </el-button>
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
            添加联系人
          </el-button>
        </div>
      </el-tab-pane>
     
    </el-tabs>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保 存</el-button>
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
import { doEdit } from '@/api/userManagement'
import Children from '@/components/bumenTree'

export default {
  name: 'companyEdit',
  components: { Children },
  data() {
    return {
      fileList:[],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      loading: false,
      elementLoadingText: '正在加载...',
      tableData: [
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
        {
          id: '10001',
          username: '王小虎',
          state: '使用',
          fuzeren: '王大虎',
          gongchengshi: '工程师',
          isac: '是',
          paixu: '1',
        },
      ],
      activeName: 'first',
      selectRows: '',
      selectRowspz: '',
      bumen: '',
      value1: '',
      value2: '',
      form: {
        username: '',
        isac: '',
        remakes: '',
      },
      rules: {
        username: [{ required: true, trigger: 'blur', message: '请输入名称' }],
        isac: [
          { required: true, trigger: 'blur', message: '请选择是否事件工程师' },
        ],
        remakes: [{ required: true, trigger: 'blur', message: '请输入描述' }],
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
  methods: {
    showEdit(row) {
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    handleAdd() {
    },
    handleDelete() {
      if (this.selectRows.length > 0) {
            const ids = this.selectRows.map((item) => item.id).join()
            console.log(ids);
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
    handleDeletepz() {
      if (this.selectRowspz.id) {
        const ids = this.selectRowspz.id
        this.$baseConfirm('你确定要删除选中项吗', null, async () => {
          const { msg } = await doDelete({ ids })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleCurrentChangepz(val) {
      this.selectRowspz = val
    },
    handleChange(file, fileList) {
      this.fileList = fileList.slice(-3)
    },
    openbumen() {
      this.$refs['children'].showWin()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    setSelectRowspz(val) {
      this.selectRows = val
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          const { msg } = await doEdit(this.form)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>

