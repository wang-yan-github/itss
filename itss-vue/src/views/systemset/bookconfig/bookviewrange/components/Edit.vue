<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1000px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-tabs v-model="activeName" type="card">
        <el-tab-pane label="属性" name="first">
          <el-form-item label="名称" prop="name">
            <el-input
              v-model.trim="form.name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
          <el-form-item label="状态" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="禁用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input v-model.trim="form.sort"></el-input>
          </el-form-item>
          <el-form-item label="描述" prop="description">
            <el-input
              v-model.trim="form.description"
              :rows="8"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-tab-pane>
        <el-tab-pane label="可查看角色范围" name="second">
          <el-table
            v-loading="listLoading"
            :data="form.roles"
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
              width="80px"
              sortable
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="name"
              width="150px"
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
              @click="handleDelete"
            >
              移除选择对象
            </el-button>
            <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
              添加可查看范围
            </el-button>
          </div>
        </el-tab-pane>
        <el-tab-pane label="可查看部门范围" name="third">
          <table class="desctable" style="width: 100%" ref="addTable">
            <colgroup>
              <col/>
              <col class="w100"/>
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
                  v-model="form.deptNames[index]"
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
                  @click="delItem(index)"
                ></el-button>
              </td>
            </tr>
            </tbody>
          </table>
        </el-tab-pane>
        <el-tab-pane label="可查看服务群组" name="fourth">
          <el-table
            v-loading="listLoading"
            :data="form.groups"
            border
            highlight-current-row
            :element-loading-text="elementLoadingText"
            @selection-change="setSelectRowsfw"
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
              width="80px"
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
              prop="is_use"
              label="状态"
              sortable
              align="center"
              :formatter="formatterIsUse"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="principalName"
              label="负责人"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="engineerName"
              label="工程师"
              align="center"
              sortable
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="auto_engineer"
              label="处理人为空自动指定工程师"
              align="center"
              sortable
            >
              <template slot-scope="scope">
                <el-tag type="success" v-if="scope.row.auto_engineer == 1">是</el-tag>
                <el-tag type="danger" v-else>否</el-tag>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="sort"
              label="排序"
              align="center"
              sortable
            ></el-table-column>
          </el-table>
          <div style="margin-top: 10px">
            <el-button
              type="danger"
              icon="el-icon-delete"
              @click="handleDeletefw"
            >
              移除选择对象
            </el-button>
            <el-button type="primary" icon="el-icon-plus" @click="handleAddfw">
              添加可查看服务群组
            </el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
    <children
      ref="children"
      @deptData="getDeptData"
      @fetch-data="
        {
          bumen
        }
      "
    ></children>
    <dialogone
      ref="dialogone"
      @listData="getRolesData"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogone>
    <dialogtwo
      ref="dialogtwo"
      @listData="getGroupData"
      @fetch-data="
        {
          input2
        }
      "
    ></dialogtwo>
  </el-dialog>
</template>

<script>
  import {rangeAdd} from '@/api/knowledgeConfig'
  import Children from '@/components/bumenTree'
  import Dialogone from './contactpeople'
  import Dialogtwo from './fuwuqunzu'
  // import Dialogtwo from '@/components/serviceGroupDX'
  export default {
    name: 'companyEdit',
    components: {Children, Dialogone, Dialogtwo},
    data() {
      return {
        isDisable: false,
        input2: '',
        listLoading: true,
        selectRows: '',
        selectRowsfw: '',
        loading: false,
        elementLoadingText: '正在加载...',
        formArr: [{value1: ''}],
        tableData: [],
        value1: '',
        value2: '',
        activeName: 'first',
        form: {
          id: '',
          name: '',
          is_use: '',
          sort: '',
          description: '',
          roles: [],
          depts: [],
          deptIds: [],
          deptNames: [],
          groups: [],
        },
        rules: {
          name: [{required: true, trigger: 'blur', message: '请输入名称'}],
          is_use: [
            {required: true, trigger: 'blur', message: '请选择状态'},
          ],
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
      addItem() {
        var that = this
        that.formArr.push({
          value1: '',
        })
      },
      delItem(index) {
        var that = this
        if (that.formArr.length == 1) {
          this.$baseMessage('至少保留一条', 'error')
        } else {
          this.form.deptIds.splice(index, 1)
          this.form.deptNames.splice(index, 1)
          that.formArr.splice(index, 1);
        }
      },
      openwin() {
        this.$refs['children'].showWin()
      },
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加'
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.deptNames.forEach((item, i) => {
            var that = this
            that.formArr.push({
              value1: '',
            })
          })
        }
        this.dialogFormVisible = true
      },
      handleAdd() {
        this.$refs['dialogone'].showWin(this.form.roles)
      },
      handleAddfw() {
        this.$refs['dialogtwo'].showWin(this.form.groups)
      },
      handleDelete() {
        if (this.selectRows.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRows.forEach((item, i) => {
              this.form.roles.some((x, i) => {
                if (x.id == item.id) {
                  this.form.roles.splice(i, 1)
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
      handleDeletefw(){
        if (this.selectRowsfw.length > 0) {
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            this.selectRowsfw.forEach((item, i) => {
              this.form.groups.some((x, i) => {
                if (x.id == item.id) {
                  this.form.groups.splice(i, 1)
                  //在数组的some方法中，如果return true，就会立即终止这个数组的后续循环
                  return true
                }
              })
            })
            this.$baseMessage('删除成功', 'success')
          })
        }else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      setSelectRowsfw(val){
        this.selectRowsfw = val
      },
      close() {
        // this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        // this.$emit('fetch-data')
        // window.parent.location.reload();
        // this.formArr= [];
        //
        this.$emit('fetch-data');
        this.formArr = [{value1: ''}];
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try{
              const data = await rangeAdd(this.form)
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
      formatterIsUse(row) {
        if (null == row) {
          return "";
        }
        return row.is_use == 0 ? "禁用" : "使用";
      },
      getRolesData(rows) {
        rows.forEach((item, i) => {
          let index = this.form.roles.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.roles.push(item)
          }
        })
      },
      getGroupData(rows) {
        rows.forEach((item, i) => {
          let index = this.form.groups.findIndex((ele) => {
            return ele.id === item.id;
          });
          if (index === -1) {
            this.form.groups.push(item)
          }
        })
      },
      getDeptData(row) {
        var count = 0;
        for(var i = 0 ; i < this.form.deptIds.length ; i++) {
          if (this.form.deptIds[i] == row.id){
              count++;
          }
        }
        if (count == 0){
          this.form.deptNames.push(row.name)
          this.form.deptIds.push(row.id)
        }else {
          this.$baseMessage('部门重复', 'warning');
        }

        // this.form.depts.forEach((item,i) => {
        //   let index = this.form.depts.findIndex((ele) => {
        //     return ele.id === item.id;
        //   });
        //   if (index === -1) {
        //     this.form.roles.push(item)
        //   }
        // })
      },
    },
  }
</script>

