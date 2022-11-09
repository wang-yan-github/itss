<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="800px" @close="close">
    <el-descriptions class="margin-top" title="" :column="1" border>
      <el-descriptions-item>
        <template slot="label">名称</template>
        {{ form.name }}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">状态</template>
        <el-tag size="small" type="success" v-if="form.is_use == '1'">启用</el-tag>
        <el-tag size="small" type="danger" v-else>禁用</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">发布人</template>
        {{ form.publisherName }}
      </el-descriptions-item>
    </el-descriptions>
    <el-table
      style="margin-top: 20px;"
      v-loading="listLoading"
      :data="this.userData"
      border
      :element-loading-text="elementLoadingText"
      row-key="id"
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column show-overflow-tooltip type="index" label="序号" width="60px;" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="user_name" align="center" label="名称"></el-table-column>
      <el-table-column show-overflow-tooltip label="状态" align="center" width="70px;">
        <template slot-scope="scope">
          <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
          <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
          <el-tag type="danger" v-else>已禁止</el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="telephone" align="center" label="电话"></el-table-column>
<!--      <el-table-column show-overflow-tooltip prop="user_type" align="center" label="类型"-->
<!--                       width="100px;"></el-table-column>-->
      <el-table-column show-overflow-tooltip  prop="sort" align="center" label="排序" width="50px;"></el-table-column>
    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getPublishCategory} from "@/api/publish";

  export default {
    name: 'companyviews',
    data() {
      return {
        title: '',
        dialogFormVisible: false,
        username: 'http://ent1.topvee.cn/admin/ticket/userAdd.do?wechatFlag=yes&scanQRCodeAdd=yes&',
        form: {
          type_name: '',
          is_use: '',
          sort: '',
          parent_name: '',
          changeCommonlyApproveList1: [],
        },
        userData: [],
        listLoading: true,
        elementLoadingText: '正在加载...',
      }
    },
    created() {

    },
    methods: {
      showView(row) {
        this.title = '查看详情'
        this.dialogFormVisible = true
        this.getPublishCategory(row)
        this.form = Object.assign({}, row)
      },
      close() {
        this.dialogFormVisible = false
      },
      save() {
        this.$baseMessage('模拟保存成功', 'success')
        this.$emit('fetch-data')
        this.close()
      },
      getPublishCategory(row) {
        getPublishCategory(row).then(res => {
          this.listLoading = false
          this.userData = res.data.publishApproveInfos;
          console.log(this.userData)
        })
      },
    },
  }
</script>
