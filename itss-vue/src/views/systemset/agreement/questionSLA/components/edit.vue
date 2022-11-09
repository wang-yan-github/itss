<template>
  <el-dialog
    :title="title"
    top='4vh'
    :visible.sync="dialogFormVisible"
    width="700px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px" label-position='top'>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="名称" prop="sla_question_name">
            <el-input v-model.trim="form.sla_question_name" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="状态" prop="is_use">
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="启用" value="1"></el-option>
              <el-option label="禁用" value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="描述" prop="remark">
        <el-input
          v-model.trim="form.remark"
          autocomplete="off"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
      <el-form-item prop="is_first">
        <template>
          <el-checkbox v-model="is_first" @change="getList">考虑优先级</el-checkbox>
        </template>
      </el-form-item>

      <el-table ref="listTable" :max-height="tableH" stripe :data="list" :element-loading-text="elementLoadingText"
                highlight-current-row border>
        <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->

        <el-table-column show-overflow-tooltip prop="first_name" label="优先级" align="center" sortable>
        </el-table-column>

        <el-table-column show-overflow-tooltip prop="solve_time" label="解决时间(分钟数)" align="center">
          <template #default="scope">
            <el-input :ref="'inputid'+scope.row.id" v-model.trim="scope.row.solve_time" placeholder="解决时间(分钟数)"
                      autocomplete="off"></el-input>
          </template>

          <!--        <template slot-scope="scope">-->
          <!--          <el-input v-model.trim="form2.solve_time" placeholder="解决时间(分钟数)" autocomplete="off"></el-input>-->
          <!--        </template>-->
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
  import {slaQuestionGetLists, questionSlaToEdit, questionSlaToAdd, questionSlaGetSlaQuestion} from '@/api/affect'

  export default {
    name: 'slaQuestionedit',
    data() {
      return {
        isDisable: false,
        form: {
          sla_question_name: '',
          is_use: '',
          is_first: '0',
          remark: '',
          slaQuestionConfigVos: [],
        },
        form2: [{
          first_id: '',
          first_name: '',
          solve_time: '',
        }],
        rules: {
          sla_question_name: [
            {required: true, trigger: 'blur', message: '请输入名称'},
          ],
          is_use: [{required: true, trigger: 'blur', message: '请选择状态'}],
        },
        title: '',
        dialogFormVisible: false,
        idx: '',
        checked: false,
        is_first: false,
        // list: null,
        list: [{
          first_id: '',
          first_name: '-全部-',
          solve_time: ''
        }],
        olist: null,
        listLoading: true,
        elementLoadingText: '正在加载...',
        queryForm: {
          is_use: 1
        },
        tableH: 0
      }
    },
    created() {
      // this.fetchData();

      let h = document.documentElement.clientHeight || document.body.clientHeight;
      this.tableH = h - 400 + 'px';
    },
    methods: {
      showEdit(row) {
        this.isDisable = false;
        if (!row) {
          this.title = '添加';
          this.idx = '1';
          this.is_first = false;
          this.list = [{
            first_id: '',
            first_name: '-全部-',
            solve_time: ''
          }];
        } else {
          this.title = '编辑';
          this.idx = '2';
          this.getEditData(row);
          // this.form = Object.assign({}, row)
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields();
        this.form = this.$options.data().form;
        this.dialogFormVisible = false;
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.form.slaQuestionConfigVos = [];
            try{
                if (this.idx === '1') {
                  //新增
                  for (let i = 0; i < this.list.length; i++) {
                    let ss = {first_id: '', solve_time: ''};
                    ss.first_id = (this.list[i].id);
                    ss.solve_time = (this.$refs['inputid' + this.list[i].id].value);
                    this.form.slaQuestionConfigVos.push(ss);
                  }
                  // this.form.slaQuestionConfigVos = this.list;
                  this.form.is_first = this.is_first ? "1" : "0";
                  console.log(this.form);
                  const data = await questionSlaToAdd(this.form);
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
                  // if ((this.is_first)) {
                  //   this.form.slaQuestionConfigVos = this.list;
                  // } else {
                  // this.form.slaQuestionConfigVos = null;
                  for (let i = 0; i < this.list.length; i++) {
                    let ss = {first_id: '', solve_time: ''};
                    ss.first_id = (this.list[i].id);
                    ss.solve_time = (this.$refs['inputid' + this.list[i].id].value);
                    this.form.slaQuestionConfigVos.push(ss);
                  }
                  // }
                  // if (this.form.is_first) {
                  //   this.form.is_first = '0'
                  // } else {
                  //   this.form.is_first = '1'
                  // }
                  this.form.is_first = this.is_first ? "1" : "0";
                  // this.form.slaQuestionConfigVos = this.list;
                  const data = await questionSlaToEdit(this.form);
                  if (data.code == 0) {
                    this.$baseMessage(data.msg, 'success');
                    this.$emit('fetch-data');
                    this.close();
                  } else {
                    this.$baseMessage(data.msg, 'error');
                    this.isDisable = false;
                  }
                }
            }catch(e){
              this.isDisable = false;
            }
          } else {
            this.isDisable = false;
            return false;
          }
        })
      },
      //考虑优先级
      getList() {
        //新增
        this.form.is_first = this.is_first ? "1" : "0";
        if (this.is_first) {
          this.fetchData();
        } else {
          this.list = [{first_name: '-全部-'}]
        }
        // if (this.idx === '1') {
        //
        // } else {
        //   //修改
        //   this.list = this.olist
        //   if (this.is_first) {
        //     this.fetchData();
        //   } else {
        //     this.list = [{first_name: '-全部-'}]
        //   }
        // }

      },
      async getEditData(row) {
        // this.listLoading = true
        const {data} = await questionSlaGetSlaQuestion(row);
        this.form = data[0];
        console.log("编辑data：")
        console.log(this.form)
        this.list = data[0].slaQuestionConfigVos;
        console.log(this.list)
        this.first = data[0].is_first;
        this.is_first = this.form.is_first == "1" ? true : false;
        setTimeout(() => {
          this.listLoading = false;
        }, 300)
      },
      //查询问题优先级
      async fetchData() {
        this.listLoading = true;
        const {data} = await slaQuestionGetLists(this.queryForm);
        this.list = data.list;
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
