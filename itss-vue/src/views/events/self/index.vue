<template>
	<div class="main-container">
		<el-row :gutter="20">      
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" style="height: 437px;padding-left:0;padding-right:0;">
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
            <el-card shadow="never" class="quick-card">
                 <i class="el-icon-s-order" @click="selfAdd"></i>
                 <p><el-link @click="selfAdd">我要开单</el-link></p>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
            <el-card shadow="never" class="quick-card">
                 <i class="el-icon-s-check" @click="capitaShow"></i>
                 <p><el-link @click="capitaShow">资产盘点</el-link></p>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
            <el-card shadow="never" class="quick-card">
                 <i class="el-icon-s-opportunity" @click="searchShow"></i>
                 <p><el-link @click="searchShow">知识搜索</el-link></p>
            </el-card>
          </el-col>
          <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
            <el-card shadow="never" class="quick-card quick-card-no">
                 <i class="el-icon-more"></i>
                 <p>敬请期待</p>
            </el-card>
          </el-col>

        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" style="height: 437px;">
          <el-card shadow="never" class="box-card">
            <div slot="header" class="clearfix">
              <span>我的工单</span>
               <el-button style="padding: 3px 20px" type="text" @click='selfBill'>查看更多<i class="el-icon-caret-right el-icon--right"></i></el-button>
              <el-button icon="el-icon-refresh" style="float: right; padding: 3px 0" type="text"></el-button>
            </div>
            <div class="text item">
              <el-table v-loading="listLoading" ref="listTable" stripe :data="list" border :element-loading-text="elementLoadingText"
                        highlight-current-row @current-change="handleCurrentChange" height="324px">
                <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
                <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="70px;"></el-table-column>
                <el-table-column show-overflow-tooltip prop="title" sortable label="标题" align="center">
                  <template #default="{ row }">
                    <el-link @click="handlesee(row)">{{row.title}}</el-link>
                  </template>
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="user_id_name" label="用户" align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="category_name" label="工单类别" align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="create_user_name" label="开单人" align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="create_time" label="开单时间" align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="service_name" label="服务群组" align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="handler_name" label="当前操作人" align="center"></el-table-column>
                <el-table-column show-overflow-tooltip prop="ex_solve_time" align="center" label="解决时间"></el-table-column>
                <el-table-column show-overflow-tooltip prop="status" align="center" label="状态" sortable :formatter="formatterStatus"></el-table-column>

                <el-table-column show-overflow-tooltip prop="event_satisfaction" align="center" label="用户满意度" min-width="130px;">
                  <template #default="{ row }">
                    <el-rate v-model="row.event_satisfaction" disabled>
                    </el-rate>
                  </template>
                </el-table-column>
                <el-table-column show-overflow-tooltip label="操作" width="160px;" align="center" fixed="right">
                  <template #default="{ row }">
                    <el-button v-permission="event_self_chongkai" plain @click="handleReopen(row)" type="primary" size="mini"
                               v-if="row.status == '4' || row.status == '5' || row.status == '7'">重开</el-button>
                    <el-dropdown trigger="click" @command="handleCommandmore" style="margin-left: 10px">
                      <el-button plain size="mini">更多<i class="el-icon-arrow-down el-icon--right"></i></el-button>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item v-permission="event_self_beizhu" icon="el-icon-reading" :command="commandmoreValue('a', row)">备注</el-dropdown-item>
                        <el-dropdown-item v-permission="event_self_chexiao" icon="el-icon-refresh-right" :command="commandmoreValue('b', row)">撤销</el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </el-card>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-card shadow="never" class="box-card">
            <div slot="header" class="clearfix">
              <span>公告</span>
              <el-button @click='toNoticePage()' style="padding: 3px 20px" type="text" >查看更多<i class="el-icon-caret-right el-icon--right"></i></el-button>
              <el-button icon="el-icon-refresh" style="float: right; padding: 3px 0" type="text"></el-button>
            </div>
            <div class="list-box">
              <div v-for="(item) in noticeList" class="text item">
                <el-link @click='toNotice(item)' class="classify-item" icon="el-icon-chat-line-square" type="primary" v-text="item.title"></el-link>
              </div>
            </div>
          </el-card>
        </el-col>
				<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-card shadow="never" class="box-card">
            <el-form :model="queryForm" class="search-box">
            	<el-row :gutter="20">
            		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            			<el-form-item>
            				<el-input autocomplete="off" placeholder="请输入文章标题" style="width: 80%;" v-model="queryForm.keyword" @keyup.enter.native="getEsKnowledge"></el-input>
            				<el-button type='primary' icon="el-icon-search" style="height: 32px; width: 20%" @click='getEsKnowledge'>搜索</el-button>
            			</el-form-item>
            		</el-col>
            	</el-row>
            </el-form>
            <div class="classify">
              <div v-for="item in recommendKnows"  class="text item">
                <el-link @click='toArticle(item.id)' class="classify-item" icon="el-icon-document" type="primary" v-text="item.title"></el-link>
              </div>
            </div>
          </el-card>

				</el-col>    
		</el-row>
    <roles ref="roles" @fetch-data="fetchData"></roles>
    <reopen ref="reopen" @fetch-data="fetchData"></reopen>
    <revoke ref="revoke" @fetch-data="fetchData"></revoke>
    <views ref="views" @fetch-data="fetchData"></views>
    <selfView ref="selfView" @fetch-data="fetchData"></selfView>
	</div>
</template>

<script>
import { getMyList } from '@/api/eventSelfService'
// 查看公告
import Views from '@/views/systemset/systemsets/noticemanagement/components/View'
// 备注
import Roles from '@/components/eventRemake'
// 重开
import Reopen from '../whole/components/reopen'
// 撤销
import Revoke from '../whole/components/revoke'
// 公告
import selfView from './components/views'
import permission from "@/directive/permission";
import {getSearchFourKnowledge, getHotOrRecommendKnow} from '@/api/knowledgeInfo'
import {toNoticeList} from "@/api/sysAnnouncement";
export default {
  name: 'webSocket1',
  components: { Roles,  Reopen, Revoke,Views, selfView },
  directives: {permission},
  data() {
    return {
      event_self_chongkai: 'event_self_chongkai',
      event_self_beizhu: 'event_self_beizhu',
      event_self_chexiao: 'event_self_chexiao',
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        username: '',
        user_type:1,
        keyword: '',
        is_full_search: ""
      },
      recommendKnows: [],
      // 公告
      noticeList: [],
    }
  },
  created() {
    this.fetchData();
    this.getRecommendKnowledge();
    this.getNoticeData();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    formatterStatus(row){
      switch (row.status) {
        case '1':
          return '待响应';
        case '2':
          return '处理中';
        case '3':
          return '暂停';
        case '4':
          return '处理完成';
        case '5':
          return '待评价';
        case '6':
          return '已关闭';
        case '7':
          return '已撤销';
        default:
          return '';
      }
    },
    //重开页面
    handleReopen(row){
      this.$refs['reopen'].showEdit(row)
    },
    handlesee(row) {
      if (row.id != '' && row.id != null) {
        this.$router.push({
          path: '/thing/wholeview',
          query: { id: row.id },
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    commandmoreValue(command, row){
      return {
        "row": row,
        "command": command
      }
    },
    handleCommandmore(val) {
      var command = val.command;
      switch (command) {
        case 'a':
          this.$refs['roles'].showEdit()
          break
        case 'b':
          // 撤销
          this.$refs['revoke'].showEdit(val.row)
          break

        default:
          //这里是没有找到对应的值处理
          break
      }
    },
    selfBill(row){
      this.$router.push({
        path: '/thing/selfBill',
      })
    },
    selfAdd(){
      this.$router.push({
        path: '/thing/wykdadd',
      })
    },
    capitaShow(){
      this.$router.push({
        path: '/thing/thingcapitalIndex',
      })
    },
    searchShow(){
      this.$router.push({
        path: '/book/knowledgeSearch',
      })
    },
    toArticle(val){
      this.$router.push({
        path: '/book/myKnowledgeView',
        query: { id: val },
      })
    },
    toNotice(val){
      this.$refs['views'].showView(val)
    },
    toNoticePage(){
      this.$refs['selfView'].showWin()
    },
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getMyList(this.queryForm)
      this.list = data.list
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    getRecommendKnowledge() {
      var that = this;
      getHotOrRecommendKnow({"type": "2"}).then((res) => {
        if (res.code == 0) {
          that.recommendKnows = res.data.splice(0,4);
          console.info(that.recommendKnows);
        }
      })
    },
    async getEsKnowledge() {
      var that = this;
      if (this.queryForm.keyword == null || this.queryForm.keyword == "") {
        this.getRecommendKnowledge();
      } else {
        const { data } = await getSearchFourKnowledge(this.queryForm)
        console.log(data)
        that.recommendKnows = data;
      }
    },
    getNoticeData() {
      var that = this;
      toNoticeList().then((res) => {
        console.log("公告", res);
        if (res.code == 0) {
          that.noticeList = res.data.list;
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>
  .box-card-none-mb{
    margin-bottom: 0;
  }
  .quick-card{
    padding: 25px 0;
    text-align: center;
  }
  .quick-card p{
    margin-bottom: 0;
    height: 26px;
  }
  .quick-card i{
    margin-top: 10px;
    padding: 10px;
    border: 1px solid #efefef;
    border-radius: 50%;
    display: inline-block;
    font-size: 40px;
    color: #409eff;
  }
  .quick-card-no i{
    border: 1px solid #F2F6FC;
    color: #F2F6FC;
  }
  .quick-card-no p{
    color: #C0C4CC;
  }
  .search-box{
    margin-bottom: 8px;
  }
  .list-box{
    overflow-y: auto;
    min-height: 200px;
    height: calc(100vh - 740px);
  }
  .classify{
    overflow-y: auto;
    min-height: 200px;
    height: calc(100vh - 740px);
  }

	.classify h2 span{
		font-size: 1rem;
		font-weight: normal;
		color: #999;
	}
	.classify-item{
		line-height: 2;
	}
   .clearfix:before,
    .clearfix:after {
      display: table;
      content: "";
    }
    .clearfix:after {
      clear: both
    }


</style>
