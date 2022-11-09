<template>
  <el-dialog
    :title="title"
    top='4vh'
    :visible.sync="dialogFormVisible"
    width="700px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-position='top'>
      <el-form-item label="名称" prop="sla_event_name">
        <el-input v-model.trim="form.sla_event_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="remark">
        <el-input
          v-model.trim="form.remark"
          autocomplete="off"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
      <el-form-item prop="isac">
        <template>
          <el-checkbox v-model="param.is_first" @change="checkYxj">考虑优先级</el-checkbox>
          <el-checkbox v-model="param.is_request" @change="checkQqlx">考虑请求类型</el-checkbox>
        </template>
      </el-form-item>
      <el-table v-loading="listLoading" ref="listTable" :max-height="tableH" stripe :data="list"
                :element-loading-text="elementLoadingText"
                highlight-current-row border>
        <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
        <el-table-column show-overflow-tooltip prop="first_name" label="优先级" align="center" width="100px;" sortable>
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="request_type_name" width="200px;" sortable label="请求类型"
                         align="center">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="response_time" label="响应时间(分钟数)" align="center">
          <template slot-scope="scope">
            <el-input v-model.trim="scope.row.response_time" placeholder="响应时间(分钟数)" autocomplete="off"></el-input>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="solve_time" label="解决时间(分钟数)" align="center">
          <template slot-scope="scope">
            <el-input v-model.trim="scope.row.solve_time" placeholder="解决时间(分钟数)" autocomplete="off"></el-input>
          </template>
        </el-table-column>
      </el-table>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getFirstAndRequest, slaEventToEdit, slaEventToAdd, getSlaEvent, questionSlaGetSlaQuestion} from '@/api/affect'

  export default {
    name: 'slaEventedit',
    data() {
      return {
        isDisable: false,
        param: {
          is_first: false,
          is_request: false
        },
        form: {
          sla_event_name: '',
          is_first: '0',
          is_request: '0',
          first_name: '',
          request_type_name: '',
          remark: '',
          slaEventFirstAndRequestVos: []
        },
        rules: {
          sla_event_name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
        },
        title: '',
        dialogFormVisible: false,
        idx: '',
        checked: false,
        checked2: false,
        list: [{
          first_id: '',
          request_type_id: '',
          first_name: '',
          request_type_name: '',
          response_time: '',
          solve_time: '',
        }],
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableH: 0
      }
    },
    created() {
      let h = document.documentElement.clientHeight || document.body.clientHeight;
      this.tableH = h - 400 + 'px';
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加';
          this.idx = '1';
          this.param.is_first = false;
          this.param.is_request = false;

          this.fetchData();
          console.log("添加form");
          console.log(this.list)
          console.log(this.form);
        } else {
          this.title = '编辑';
          this.idx = '2';
          // this.form = Object.assign({}, row)
          this.getEditData(row)
        }
        this.dialogFormVisible = true
      },
      //考虑优先级
      checkYxj() {
        console.log("考虑优先级")
        this.form.is_first = this.param.is_first ? "1" : "0";
        this.fetchData();
      },
      //考虑请求类型
      checkQqlx() {
        console.log("考虑请求类型")
        this.form.is_request = this.param.is_request ? "1" : "0";
        this.fetchData();
      },
      //编辑传参查询data 事件sla data
      async getEditData(row) {
        const {data} = await getSlaEvent(row);
        console.log("编辑传参查询data");
        console.log(data);
        this.form = data[0];
        this.list = data[0].slaEventFirstAndRequestVos;
        this.form.is_first = data[0].is_first;
        this.form.is_request = data[0].is_request;
        this.param.is_first = this.form.is_first == "1" ? true : false;
        this.param.is_request = this.form.is_request == "1" ? true : false;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      //加载页面数据 优先级、请求类型列表
      async fetchData() {
        this.listLoading = true;
        const {data} = await getFirstAndRequest(this.form);
        console.log("加载页面数据");
        console.log(data);
        this.list = data;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      close() {
        this.$refs['form'].resetFields();
        this.form = this.$options.data().form;
        this.dialogFormVisible = false
      },
      //保存
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            try {
              if (this.idx === '1') {
                //新增
                console.log("新增");
                console.log(this.param.is_first);
                console.log(this.param.is_request);

                let flag = "0";
                this.list.forEach((row) => {
                  if (!row.response_time) {
                    flag = "1";
                    return false;
                  }
                  if (!row.solve_time) {
                    flag = "2";
                    return false;
                  }
                });
                if (flag == "1") {
                  this.$baseMessage("响应时间(分钟数)", 'error');
                  this.isDisable = false;
                  return false;
                } else if (flag == "2") {
                  this.$baseMessage("解决时间(分钟数)", 'error');
                  this.isDisable = false;
                  return false;
                }

                this.form.is_first = this.param.is_first ? "1" : "0";
                this.form.is_request = this.param.is_request ? "1" : "0";
                this.form.slaEventFirstAndRequestVos = this.list;
                const data = await slaEventToAdd(this.form);
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage(data.msg, 'error');
                  this.isDisable = false;
                }
              } else {
                //编辑
                console.log("编辑");
                console.log(this.form.is_first);
                console.log(this.form.is_request);

                let flag = "0";
                this.list.forEach((row) => {
                  if (!row.response_time) {
                    flag = "1";
                    return false;
                  }
                  if (!row.solve_time) {
                    flag = "2";
                    return false;
                  }
                });
                if (flag == "1") {
                  this.$baseMessage("响应时间(分钟数)", 'error');
                  this.isDisable = false;
                  return false;
                } else if (flag == "2") {
                  this.$baseMessage("解决时间(分钟数)", 'error');
                  this.isDisable = false;
                  return false;
                }

                this.form.is_first = this.param.is_first ? "1" : "0";
                this.form.is_request = this.param.is_request ? "1" : "0";
                this.form.slaEventFirstAndRequestVos = this.list;
                const data = await slaEventToEdit(this.form);
                if (data.code == 0) {
                  this.$baseMessage(data.msg, 'success');
                  this.$emit('fetch-data');
                  this.close();
                } else {
                  this.$baseMessage(data.msg, 'error');
                  this.isDisable = false;
                }
              }
            } catch (e) {
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false;
          }
        })
      },
    },
  }
</script>
