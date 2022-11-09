<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="900px" @close="close">
    <el-descriptions class="margin-top" title="" :column="2" border>
      <el-descriptions-item>
        <template slot="label">名称</template>
        {{ form.type_name}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">状态</template>
        <el-tag size="small" type="success" v-if="form.is_use == '1'">启用</el-tag>
        <el-tag size="small" type="danger" v-else>禁用</el-tag>
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">排序</template>
        {{ form.sort}}
      </el-descriptions-item>
      <el-descriptions-item>
        <template slot="label">上级</template>
        {{ form.parent_name}}
      </el-descriptions-item>
    </el-descriptions>

    <el-tabs v-model="activeName" style="margin-top: 20px" type="card" @tab-click="handleClick">
      <el-tab-pane label="一般变更" name="first">
        <el-table
          v-loading="listLoading"
          :data="this.form.changeCommonlyApproveList1"
          border
          :element-loading-text="elementLoadingText"
          row-key="id"
          default-expand-all
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column show-overflow-tooltip type="index" label="序号" width="60px;" align="center"></el-table-column>
          <el-table-column show-overflow-tooltip prop="user_name" align="center" label="名称"
                           width="80px;"></el-table-column>
          <el-table-column show-overflow-tooltip label="状态" align="center" width="70px;">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
              <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
              <el-tag type="danger" v-else>已禁止</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="nonFirstLineGroup" align="center" label="非一线组" width="80px;">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.nonFirstLineGroup == 1">是</el-tag>
              <el-tag type="warning" v-else-if="scope.row.nonFirstLineGroup == 2">否</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="principalName" align="center" label="负责人"></el-table-column>
          <el-table-column show-overflow-tooltip prop="engineerName" align="center" label="工程师"></el-table-column>
          <!--      <el-table-column show-overflow-tooltip prop="auto_engineer" align="center" label="处理人为空自动指定工程师"></el-table-column>-->
          <el-table-column show-overflow-tooltip type="index" align="center" label="排序" width="50px;"></el-table-column>
        </el-table>
        <el-descriptions class="margin-top" title="" :column="2" style="margin-top: 10px" border>
          <el-descriptions-item>
            <template slot="label">一般变更方案审核人</template>
            {{ form.commonly_audit_user_name}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">一般变更实施后评审人</template>
            {{ form.commonly_implement_user_name}}
          </el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
      <el-tab-pane label="紧急变更" name="second">
        <el-table
          v-loading="listLoading"
          :data="this.form.changeUrgentApproveList1"
          border
          :element-loading-text="elementLoadingText"
          row-key="id"
          default-expand-all
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column show-overflow-tooltip type="index" label="序号" width="60px;" align="center"></el-table-column>
          <el-table-column show-overflow-tooltip prop="user_name" align="center" label="名称"
                           width="80px;"></el-table-column>
          <el-table-column show-overflow-tooltip label="状态" align="center" width="70px;">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
              <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
              <el-tag type="danger" v-else>已禁止</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="nonFirstLineGroup" align="center" label="非一线组" width="80px;">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.nonFirstLineGroup == 1">是</el-tag>
              <el-tag type="warning" v-else-if="scope.row.nonFirstLineGroup == 2">否</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="principalName" align="center" label="负责人"></el-table-column>
          <el-table-column show-overflow-tooltip prop="engineerName" align="center" label="工程师"></el-table-column>
          <!--      <el-table-column show-overflow-tooltip prop="auto_engineer" align="center" label="处理人为空自动指定工程师"></el-table-column>-->
          <el-table-column show-overflow-tooltip type="index" align="center" label="排序" width="50px;"></el-table-column>
        </el-table>
        <el-descriptions class="margin-top" title="" :column="1" style="margin-top: 10px" border>
          <el-descriptions-item>
            <template slot="label">紧急变更实施后评审人</template>
            {{ form.urgent_implement_user_name}}
          </el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
      <el-tab-pane label="重大变更" name="third">
        <el-table
          v-loading="listLoading"
          :data="this.form.changeMajorApproveList1"
          border
          :element-loading-text="elementLoadingText"
          row-key="id"
          default-expand-all
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column show-overflow-tooltip type="index" label="序号" width="60px;" align="center"></el-table-column>
          <el-table-column show-overflow-tooltip prop="user_name" align="center" label="名称"
                           width="80px;"></el-table-column>
          <el-table-column show-overflow-tooltip label="状态" align="center" width="70px;">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.status == 1">活动</el-tag>
              <el-tag type="warning" v-else-if="scope.row.status == 2">暂停</el-tag>
              <el-tag type="danger" v-else>已禁止</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="nonFirstLineGroup" align="center" label="非一线组" width="80px;">
            <template slot-scope="scope">
              <el-tag type="success" v-if="scope.row.nonFirstLineGroup == 1">是</el-tag>
              <el-tag type="warning" v-else-if="scope.row.nonFirstLineGroup == 2">否</el-tag>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="principalName" align="center" label="负责人"></el-table-column>
          <el-table-column show-overflow-tooltip prop="engineerName" align="center" label="工程师"></el-table-column>
          <!--      <el-table-column show-overflow-tooltip prop="auto_engineer" align="center" label="处理人为空自动指定工程师"></el-table-column>-->
          <el-table-column show-overflow-tooltip type="index" align="center" label="排序" width="50px;"></el-table-column>
        </el-table>
        <el-descriptions class="margin-top" title="" :column="2" style="margin-top: 10px" border>
          <el-descriptions-item>
            <template slot="label">重大变更方案审核人</template>
            {{ form.major_audit_user_name}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">重大变更实施后评审人</template>
            {{ form.major_implement_user_name}}
          </el-descriptions-item>
        </el-descriptions>
      </el-tab-pane>
    </el-tabs>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {getById} from "@/api/sourceSet";

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
        listLoading: true,
        elementLoadingText: '正在加载...',
        tableData: [],
        activeName: 'first'
      }
    },
    created() {

    },
    methods: {
      handleClick(tab, event) {
      },
      showView(id) {
        this.title = '查看详情'
        this.dialogFormVisible = true
        this.getById(id);
      },
      close() {
        this.dialogFormVisible = false
      },
      save() {
        this.$baseMessage('模拟保存成功', 'success')
        this.$emit('fetch-data')
        this.close()
      },
      // 初始化表单数据
      getById(id) {
        this.listLoading = false
        const ids = id
        //判断ids非空
        if (ids != '' && ids != undefined) {
          getById({ids}).then(res => {
            this.form = res.data
            this.activeName = 'first';
          })
        }
      },
    },
  }
</script>
<style scoped>
  ::v-deep .el-descriptions-item__label{
    width: 142px;
  }
</style>
