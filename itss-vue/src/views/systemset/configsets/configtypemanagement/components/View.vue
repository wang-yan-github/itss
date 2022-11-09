<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
    @close="close"
  >
    <el-descriptions
      class="margin-top"
      title=""
      :column="2"
      border
    >

      <el-descriptions-item>
        <template slot="label">

          名称
        </template>
        {{ form.name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          状态
        </template>
        {{form.is_enable== 0 ? '启用' : '禁用'}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">

          排序
        </template>
        {{ form.sort}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          代码
        </template>
        {{form.code }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          添加地址
        </template>
        {{form.add_address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          列表地址
        </template>
        {{form.list_address }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">

          查看地址
        </template>
        {{form.check_address }}
      </el-descriptions-item>


    </el-descriptions>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>

  </el-dialog>
</template>

<script>
  import {getAssetsById} from '@/api/configtypemanagement'

  export default {
    name: 'companyviews',
    data() {
      return {
        list: {},
        title: '',
        dialogFormVisible: false,
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
        form: {}
      }
    },
    created() {
      this.getById()
    },
    methods: {
      showView(row) {
        this.title = '查看详情'
        this.dialogFormVisible = true
        this.form = Object.assign({}, row)
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        this.$baseMessage('模拟保存成功', 'success')
        this.$emit('fetch-data')
        this.close()
      },
      async getById() {
        const id = this.$route.query.id
        const {data} = await getAssetsById({id})
        this.list = data
      },
    },
  }
</script>
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 100px;
  }
</style>
