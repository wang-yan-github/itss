<template>
  <div class="index-container">
    <el-table
      v-loading="listLoading"
      ref="listTable"
      :max-height="tableH"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
    >
      <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
      <el-table-column
        show-overflow-tooltip
        prop="impact_name"
        label="影响度"
        align="center"
        width="200px;"
        sortable
      ></el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="urgent_name"
        width="200px;"
        sortable
        label="紧急度"
        align="center"
      ></el-table-column>
      <el-table-column show-overflow-tooltip label="优先级" sortable align="center">
        <!--				<template slot-scope="scope">
					<el-select v-model="queryForm.username" placeholder="优先级">
						<el-option label="高" value="shanghai"></el-option>
						<el-option label="低" value="beijing"></el-option>
					</el-select>
				</template>-->
        <template slot-scope="scope">
          <el-select
            v-model="scope.row.first_id"
            clearable
            placeholder="-请选择-"
          >
            <el-option
              v-for="(item, index) in firstlist"
              :key="index"
              :label="item.first_name"
              :value="item.id"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>
    <div class="bottom">
      <el-button type="primary" size="medium" @click="save">保存</el-button>
      <el-button size="medium" @click="closeall">取消</el-button>
    </div>
  </div>
</template>

<script>
  import {
    priorityGetList,
    slaEventImpactUrgentGetList,
    slaEventImpactUrgentToEdit,
    urgencyToAdd,
    urgencyToEdit,
  } from '@/api/affect'

  export default {
    name: 'relation',
    data() {
      return {
        checked: false,
        isShow: false,
        list: null,
        firstlist: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        tableH: 0,
        rules: {
          'list.first_id': [
            {required: true, trigger: 'blur', message: '请选择优先级'},
          ],
        },
      }
    },
    created() {
      this.fetchData()
      this.fetchFirstData()
      let h = document.documentElement.clientHeight || document.body.clientHeight
      this.tableH = h - 240 + 'px'
    },
    methods: {
      async fetchFirstData() {
        this.listLoading = true
        const {data} = await priorityGetList(this.queryForm)
        this.firstlist = data.list
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async fetchData() {
        this.listLoading = true
        const {data} = await slaEventImpactUrgentGetList(this.queryForm)
        this.list = data
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async save() {
        // let param = JSON.stringify(this.list)
        // this.$refs['form'].validate(async (valid) => {
        //   if (valid) {
        const {msg} = await slaEventImpactUrgentToEdit({
          list: JSON.stringify(this.list),
        })
        this.$baseMessage(msg, 'success')
        // this.$emit('fetch-data')
        this.closeall()
        // this.close()
        // } else {
        //   return false
        // }
        // })
      },
      closeall() {
        // 调用全局挂载的方法
        this.$store.dispatch('tabsBar/delRoute', this.$route)
        // 返回上一步路由
        this.$router.go(-1)
      },
    },
  }
</script>
<style lang="scss" scoped>
  .index-container {
    min-height: calc(100vh - 300px);
    height: calc(100vh - 290px);
    .hiddenmore {
      display: inline;

      ::v-deep {
        .el-input {
          width: 130px;
        }
      }
    }
  }

  .save-box {
    padding: 20px 0;
    background-color: #fff;
    text-align: center;
  }
</style>
