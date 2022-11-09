<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="40%"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="属性" name="first">
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="名称" prop="sql_name">
                <el-input
                  v-model.trim="form.sql_name"
                  autocomplete="off"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <el-form-item label="SQL" prop="sql_content">
                <el-input
                  v-model="form.sql_content"
                  autocomplete="off"
                  :rows="5"
                  type="textarea"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="状态" prop="is_use">
                <el-select v-model.trim="form.is_use" class="w">
                  <el-option
                    v-for="item in sqlIsUse"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="显示方式" prop="display_mode">
                <el-select v-model.trim="form.display_mode" class="w">
                  <el-option
                    v-for="item in sqlDisplayMode"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item
                label="默认时间类型"
                class="custemitem"
                prop="time_choose"
              >
                <el-select v-model.trim="form.time_choose" class="w">
                  <el-option
                    v-for="item in sqlTimechoose"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="可见范围" prop="scope">
                <el-select v-model.trim="form.scope" class="w">
                  <el-option
                    v-for="item in sqlScope"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="所属模块" prop="module">
                <el-select v-model.trim="form.module" class="w">
                  <el-option
                    v-for="item in sqlModule"
                    :key="item.id"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
              <el-form-item label="分组统计" prop="is_group">
                <el-select v-model.trim="form.is_group" class="w">
                  <el-option label="是" value="0"></el-option>
                  <el-option label="否" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-alert :closable="false" type="success" title="支持的变量">
              <p>(timeStart)：开始时间</p>
              <p>(timeEnd)：结束时间</p>
              <p>示例：</p>
              <p>select t.id,t.title,t.caller</p>
              <p>from tic_ticket t</p>
              <p>
                where t.creation_time &ge; 'timeStart' and t.creation_time &le; 'timeEnd'
              </p>
            </el-alert>
          </el-col>
        </el-tab-pane>
        <el-tab-pane label="可见人员" name="second">
          <el-table
            v-loading="listLoading"
            :data="tableUserData"
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
              prop="user_name"
              label="用户名"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="name"
              label="姓名"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="departmentName"
              label="部门名称"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="telephone"
              label="手机号"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="mailbox"
              label="邮箱"
              align="center"
            ></el-table-column>
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
              添加可见人员
            </el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane label="可见角色" name="third">
          <el-table
            v-loading="listLoading"
            :data="tableRoleData"
            border
            highlight-current-row
            :element-loading-text="elementLoadingText"
            @selection-change="setSelectRowspz"
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
              prop="name"
              label="名称"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="description"
              label="描述"
              sortable
              align="center"
            ></el-table-column>
          </el-table>
          <div style="margin-top: 10px">
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click="handleDeletepz"
            >
              移除选择对象
            </el-button>
            <el-button type="primary" icon="el-icon-plus" @click="handleAddpz">
              添加可见角色...
            </el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <dialogone ref="dialogone" @listUserData="listUserData"></dialogone>
    <dialogtwo ref="dialogtwo" @listRowData="listRowData"></dialogtwo>
  </el-dialog>
</template>

<script>
  import {tosqlAdd, getsqlByIdData, tosqlEdit} from '@/api/userManagement'
  import vabQuill from '@/plugins/vabQuill'
  import Dialogone from './contactpeople'
  import Dialogtwo from './relaseset'

  export default {
    name: 'companyEdit',
    components: {vabQuill, Dialogone, Dialogtwo},
    data() {
      return {
        isDisable: false,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        loading: false,
        elementLoadingText: '正在加载...',
        tableUserData: [],
        tableRoleData: [],
        activeName: 'first',
        selectRows: '',
        selectRowspz: '',
        checked: false,
        isShow: true,
        form: {
          sql_name: '',
          sql_content: '',
          is_use: '',
          display_mode: '',
          time_choose: '',
          scope: '',
          module: '',
          is_group: '',
          users: '',
          roles: '',
          id: '',
        },
        rules: {
          sql_name: [{required: true, trigger: 'blur', message: '必填'}],
          sql_content: [{required: true, trigger: 'blur', message: '必填'}],
          is_use: [{required: true, trigger: 'blur', message: '请选择'}],
          display_mode: [{required: true, trigger: 'blur', message: '请选择'}],
          time_choose: [{required: true, trigger: 'blur', message: '请选择'}],
          scope: [{required: true, trigger: 'blur', message: '请选择'}],
          module: [{required: true, trigger: 'blur', message: '请选择'}],
          is_group: [{required: true, trigger: 'blur', message: '请选择'}],
        },
        title: '',
        titleType: '',
        dialogFormVisible: false,
        sqlModule: '', //所属模块
        sqlIsUse: '', //报表状态
        sqlTimechoose: '', //时间类型
        sqlScope: '', //可见范围
        sqlDisplayMode: '', //显示方式
      }
    },
    created() {
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    methods: {
      //新增添加判断
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
          this.titleType = '1'
          this.tableUserData = []
          this.tableRoleData = []
          this.editByIdData('0')
        } else {
          this.title = '编辑'
          this.titleType = '2'
          this.editByIdData(row.id)
        }
        this.dialogFormVisible = true
      },
      //编辑获取相关数据信息
      async editByIdData(id) {
        const {data} = await getsqlByIdData(id)
        if (this.titleType == '2') {
          var datas = data.sysSql
          this.tableUserData = datas.sysUserList
          this.tableRoleData = datas.sysRoleList
          this.form = datas
        }
        this.sqlModule = data.sqlModule
        this.sqlIsUse = data.sqlIsUse
        this.sqlTimechoose = data.sqlTimechoose
        this.sqlScope = data.sqlScope
        this.sqlDisplayMode = data.sqlDisplayMode
      },
      //打开角色列表
      handleAddpz() {
        this.$refs['dialogtwo'].showWin(this.tableRoleData, this.titleType)
      },
      //角色选中 参数回显
      listRowData(row) {
        console.log(this.tableRoleData)
        console.log(row)
        this.tableRoleData = row
      },
      //角色勾选列表数据
      setSelectRowspz(val) {
        this.selectRowspz = val
      },
      //移除角色信息
      handleDeletepz() {
        if (this.selectRowspz.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowspz.forEach((item, i) => {
              this.tableRoleData.some((x, i) => {
                if (x.id == item.id) {
                  this.tableRoleData.splice(i, 1)
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //打开用户列表
      handleAdd() {
        this.$refs['dialogone'].showWin(this.tableUserData, this.titleType)
      },
      //用户选中 参数回显
      listUserData(row) {
        this.tableUserData = row
      },
      //用户勾选列表数据
      setSelectRows(val) {
        this.selectRows = val
      },
      //移除用户信息
      handleDelete() {
        if (this.selectRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRows.forEach((item, i) => {
              this.tableUserData.some((x, i) => {
                if (x.id == item.id) {
                  this.tableUserData.splice(i, 1)
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      //关闭页面
      close() {
        this.$refs['form'].resetFields()
        this.isShow = true
        this.checked = false
        this.dialogFormVisible = false
      },
      //保存后台数据
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            // base64加密
            // var enc = window.btoa(this.sqlstr)
            // this.form.sql_content = enc
            // console.log(this.form.sql_content)
            //保存用户id
            let users = this.tableUserData.map((item) => {
              return item.id
            })
            let roles = this.tableRoleData.map((item) => {
              return item.id
            })
            this.form.users = users
            this.form.roles = roles
            //新增
            if (this.titleType == '1') {
              try{
                const data = await tosqlAdd(this.form)
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
              //修改
              try{
                const data = await tosqlEdit(this.form)
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
  ::v-deep {
    .el-date-editor.el-input {
      width: 100% !important;
    }
  }
</style>

