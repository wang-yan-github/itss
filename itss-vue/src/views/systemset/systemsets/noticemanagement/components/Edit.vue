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
          <el-form-item label="标题" prop="title">
            <el-input
              v-model.trim="form.title"
              autocomplete="off"
            ></el-input>
          </el-form-item>

          <el-form-item label="内容" prop="content">
            <vab-quill
              v-model="form.content"
              :min-height="200"
              :options="options"
            ></vab-quill>
          </el-form-item>

          <el-form-item label="状态" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="停用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="可见范围" prop="scope">
            <el-select v-model.trim="form.scope" class="w">
              <el-option label="公开" value="0"></el-option>
              <el-option label="指定人员 角色 部门" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="有效期至" prop="effective_date">
            <el-date-picker
              v-model="form.effective_date"
              type="date"
              placeholder="选择日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="置顶" prop="is_top">
            <el-select v-model.trim="form.is_top" class="w">
              <el-option label="是" value="0"></el-option>
              <el-option label="否" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-tab-pane>
        <el-tab-pane label="可见人员" name="second">
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
              prop="status"
              label="状态"
              sortable
              align="center"
            >
              <template #default="scope">
                <el-tag
                  size="mini"
                  icon="el-icon-edit"
                  v-if="scope.row.status == '1'"
                  type="primary"
                >
                  活动
                </el-tag>
                <el-tag
                  size="mini"
                  icon="el-icon-delete"
                  class="red"
                  v-if="scope.row.status == '2'"
                  type="success"
                >
                  暂停
                </el-tag>
                <el-tag
                  size="mini"
                  icon="el-icon-delete"
                  class="red"
                  v-if="scope.row.status == '3'"
                  type="success"
                >
                  禁止
                </el-tag>
              </template>


            </el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="telephone"
              label="电话"
              align="center"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="mailbox"
              label="Email"
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
            :data="tableData1"
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
              align="center"
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
              添加可见角色
            </el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane label="可见部门" name="four">
          <table class="desctable" style="width: 100%" ref="addTable">
            <colgroup>
              <col/>
              <col style="width:100px;"/>
            </colgroup>
            <thead>
            <th>部门</th>
            <th>
              <el-button
                round
                size="mini"
                type="primary"
                icon="el-icon-plus"
                @click="addItem"
              ></el-button>
            </th>
            </thead>
            <tbody>
            <tr v-for="(item, index) in formArr" :key="index">
              <td>
                <el-input
                  placeholder="请输入内容"
                  v-model="tableData3[index]"
                  @click.native="openwin"
                >
                  <!--<el-button-->
                    <!--slot="append"-->
                    <!--icon="el-icon-search"-->
                    <!--@click="openwin"-->
                  <!--&gt;</el-button>-->
                </el-input>
              </td>

              <td align="center">
                <el-button
                  round
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="delItem(index,tableData3[index])"
                ></el-button>
              </td>
            </tr>
            </tbody>
          </table>
        </el-tab-pane>
      </el-tabs>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <children
      ref="children"
      @deptData="deptset2"
      @fetch-data="
        {
          benmenTree
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @deptData="deptset"
      @fetch-data="
        {
          contactpeople
        }
      "
    ></dialogone>
    <dialogtwo
      ref="dialogtwo"
      @deptData="deptset1"
      @fetch-data="
        {
          relaseset
        }
      "
    ></dialogtwo>
  </el-dialog>
</template>

<script>
  import {toAdd,deleteByRoleId,deleteByUserId} from '@/api/sysAnnouncement'
  import vabQuill from '@/plugins/vabQuill'
  import Children from '@/components/bumenTree'
  import Dialogone from './contactpeople'
  import Dialogtwo from './relaseset'

  export default {
    name: 'companyEdit',
    components: {vabQuill, Dialogone, Dialogtwo, Children},
    data() {
      return {
        isDisable: false,
        input2: '',
        formArr: [{value1: ''}],
        fileList: [],
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        loading: false,
        elementLoadingText: '正在加载...',
        tableData: [],
        tableData1: [],
        tableData2: [],
        tableData3: [],
        activeName: 'first',
        selectRows: '',
        selectRowspz: '',
        bumen: '',
        value1: '',
        value2: '',
        options: {
          theme: 'snow',
          bounds: document.body,
          debug: 'warn',
          modules: {
            toolbar: [
              ['bold', 'italic', 'underline', 'strike'],
              [{header: [1, 2, 3, 4, 5, 6, false]}],
              [{size: ['small', false, 'large', 'huge']}],
              [{color: []}, {background: []}],
              ['blockquote', 'code-block'],
              [{list: 'ordered'}, {list: 'bullet'}],
              [{script: 'sub'}, {script: 'super'}],
              [{indent: '-1'}, {indent: '+1'}],
              [{align: []}],
              [{direction: 'rtl'}],
              [{font: []}],
              ['clean'],
              ['link', 'image'],
            ],
          },
          placeholder: '内容...',
          readOnly: false,
        },
        borderColor: '#dcdfe6',
        checked: false,
        isShow: true,
        form: {
          id: '',
          title: '',
          content: '',
          is_use: '',
          scope: '',
          effective_date: '',
          is_top: '',
          announcementSees: []
        },
        userId:'',
        roleId:'',
        rules: {
          title: [{required: true, trigger: 'blur', message: '请输入标题'}],
          content: [{required: true, trigger: 'blur', message: '请输入内容',min:15,message:'不能低于15个字符'}],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'}],
          scope: [{required: true, trigger: 'blur', message: '请选择可见范围'}],
          effective_date: [{required: true, trigger: 'blur', message: '请选择有效期至'}],
          is_top: [{required: true, trigger: 'blur', message: '请选择置顶'}],
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
      deptset(data) {
        this.tableData = data;
      },
      deptset1(data) {
        this.tableData1 = data;
      },
      deptset2(data) {
        console.log(data);
        var x = {};
        x.name = data.name;
        x.id = data.id;
        var count = 0;
        for(var i = 0 ; i < this.tableData2.length ; i++ ){
          if (this.tableData2[i].id == x.id){
            count++;
          }
        }
        if (count == 0){
          this.tableData2.push(x);
          this.tableData3.push(data.name);
        }else {
          this.$baseMessage('部门重复', 'warning',)
        }


      },
      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index, val) {
        var that = this
        console.log(val);
        that.formArr.splice(index, 1)
        var num = -1;
        for (var i = 0; i < this.tableData2.length; i++) {
          if (this.tableData2[i].name == val) {
            num = i;
          }
        }
        if (num != -1) {
          this.tableData2.splice(num, 1);
        }
        var count = -1;
        for (var i = 0; i < this.tableData3.length; i++) {
          if (this.tableData3[i] == val) {
            count = i;
          }
        }
        if (count != -1) {
          this.tableData3.splice(count, 1);
        }
        console.log(this.tableData2)

        console.log(this.tableData3)
      },
      showEdit(row) {
        this.isDisable = false;
        this.tableData = [];
        this.tableData1 = [];
        this.tableData2 = [];
        this.tableData3 = [];
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          // this.changeClick()
          this.tableData = row.sysUserList;
          this.tableData1 = row.sysRoleList;
          this.tableData2 = row.sysDepartmentList;
          for (var i = 0; i < row.sysDepartmentList.length; i++) {
            this.tableData3.push(row.sysDepartmentList[i].name)
          }
          this.tableData3.forEach((item, i) => {
            var that = this
            that.formArr.push({
              value1: '',
            })
          })

        }
        this.dialogFormVisible = true
      },

      handleAdd() {
        this.$refs['dialogone'].showWin()
      },

      handleDelete() {
        if (this.selectRows.length > 0) {

          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRows.forEach((item, i) => {
              this.tableData.some((x, i) => {
                if (x.id == item.id) {
                  this.tableData.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
              this.oneUser = "协查人(" + this.tableData.length + ")";
            })
            this.$baseMessage('删除成功', 'success')
          })

        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      handleAddpz() {
        this.$refs['dialogtwo'].showWin()
      },
      handleDeletepz() {
        if (this.selectRowspz.length >0) {

          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowspz.forEach((item, i) => {
              this.tableData1.some((x, i) => {
                if (x.id == item.id) {
                  this.tableData1.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
              this.oneUser = "协查人(" + this.tableData1.length + ")";

            })
            this.$baseMessage('删除成功', 'success')
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
      setSelectRows(val) {
        this.selectRows = val
      },
      setSelectRowspz(val) {
        this.selectRowspz = val
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      close() {
        this.$refs['form'].resetFields()
        this.isShow = true
        this.checked = false
        this.tableData = [];
        this.formArr = [];
        this.formArr.push({
          value1: '',
        })
        this.form = this.$options.data().form
        this.dialogFormVisible = false
        this.$emit('fetch-data');
        // this.$router.push({
        //   path: '/set/noticemanagement',
        // })
        // window.parent.location.reload();
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            // this.form.id =  this.$route.query.id

            for (let i = 0; i < this.tableData.length; i++) {
              let ss = {visual_id: '', visual_type: ''}
              ss.visual_id = (this.tableData[i].id)
              ss.visual_type = ('0')
              this.form.announcementSees.push(ss);
            }
            for (let i = 0; i < this.tableData1.length; i++) {
              let ss = {visual_id: '', visual_type: ''}
              ss.visual_id = (this.tableData1[i].id)
              ss.visual_type = ('1')
              this.form.announcementSees.push(ss);
            }
            for (let i = 0; i < this.tableData2.length; i++) {
              let ss = {visual_id: '', visual_type: ''}
              ss.visual_id = (this.tableData2[i].id)
              ss.visual_type = ('2')
              this.form.announcementSees.push(ss);
            }
            const data = await toAdd(this.form)
            if (data.code == 0) {
              this.$baseMessage(data.msg, 'success');
              // this.$emit('fetch-data');
              this.close();
            } else {
              this.$baseMessage("失败", 'error');
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
  ::v-deep {
    .el-date-editor.el-input {
      width: 100% !important;
    }
  }
</style>

