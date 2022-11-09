<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="800px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-row :gutter="10">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="模板名称" prop="print_name">
            <el-input
              v-model.trim="form.print_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="状态" prop="is_use" >
            <el-select v-model.trim="form.is_use" class="w">
              <el-option label="停用" value="0"></el-option>
              <el-option label="使用" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="模块"  >
            <el-input
              v-model.trim="form.module"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="排序" >
            <el-input v-model.trim="form.order_num"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="模板内容" prop="content">
            <vab-quill
              v-model="form.content"
              :min-height="200"
              :options="options"
            ></vab-quill>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-alert type="success" :closable="false" title="配置参数">
            编号: (ID) 标题: (Title) 描述: (Description) 用户: (User) 用户ID:
            (UserName) 邮箱 (Email) 电话 (Phone) 座机 (Landline) 部门
            (Organization) 是否是VIP (VIP) 职位 (Position) 位置 (Address)
            开单人: (Creator) 开单时间: (CreationTime) 工单类别: (Type) 服务群组
            (ServiceGroup) 处理人: (Agent) 指派人: (AssginPerson) 指派时间:
            (AssginTime) 响应时间 (ResponseTime) 解决工程师: (SolveEngineer)
            解决时间: (ResolvedTime) 解决方式: (ResloveType) 解决方案:
            (Solution) 方案分类: (CloseCode) 关单时间: (ClosedTime) 预计解决时间
            (ExpectDealTime) 状态: (Status) 备注 (TicketNote) 备注人
            (TicketNoteUser) 查看URL: (As)(/As) 重开URL (reopenAs)(/reopenAs)
            关联的配置项ID (CI_ID) 费用 (Expense) 工单信息附件
            (InfoAttDownloadURL) 解决方案附件 (DealAttDownloadURL)
          </el-alert>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisable">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {toAdd} from '@/api/sysPrint'
  import vabQuill from '@/plugins/vabQuill'

  export default {
    name: 'companyEdit',
    components: {vabQuill},
    data() {
      return {
        isDisable: false,
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
        value1: '',
        value2: '',
        form: {
          id: '',
          print_name: '',
          is_use: '',
          module: '',
          order_num: '',
          content: '',
        },
        rules: {
          print_name: [{ required: true, trigger: 'blur', message: '请输入名称' }],
          is_use: [
            { required: true, trigger: 'blur', message: '请选择状态' },
          ],
          content: [{ required: true, trigger: 'blur', message: '请输入内容' }],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
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
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.isDisable = true;
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            // this.form.id = this.$route.query.id
            try{
              const data = await toAdd(this.form)
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

