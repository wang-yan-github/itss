<script src="../../../api/knowledgeInfo.js"></script>
<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
        <el-card class="box-card" shadow="never">
        <!-- <div class="treeborder"> -->
          <el-tree class="filter-tree" :data="data" :props="defaultProps" default-expand-all ref="tree">
          </el-tree>
        <!-- </div> -->
      </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="14" :lg="14" :xl="14">
        <el-card class="box-card" shadow="never">
          <el-form :model="queryForm">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item>
                  <el-input autocomplete="off" v-model="queryForm.keyWord" placeholder="请输入关键字"
                            style="width: calc(100% - 100px);" @keyup.enter.native="getEsKnowledge"></el-input>
                  <el-button type='primary' icon="el-icon-search" style="height: 36px; width: 100px"
                             @click='getEsKnowledge'>搜索
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>

          <div class="tips-text" v-if="recommend">
            <div><i class="el-icon-s-opportunity"></i></div>
            <p> 请输入相关词语进行查询！</p>
          </div>

          <div v-else class="classify search-result">
            <h2>搜索结果<span>（共{{ totalCount }}条）</span></h2>
            <div v-for="item in hits" class="text item">
              <el-link @click='toArticle(item.id)' class="classify-item" type="primary" v-html="item.title"></el-link>
              <p v-html="item.content"></p>
              <el-divider></el-divider>
            </div>
          </div>
        </el-card>

      </el-col>

      <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
        <el-card class="card" shadow="never">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-document"></i> 推荐知识</span>
          </div>
          <div class="classify">
            <div v-for="item in recommendKnows"  class="text item">
              <el-link @click='toArticle(item.id)' class="classify-item" icon="el-icon-caret-right" type="primary" v-text="item.title">
              </el-link>
            </div>
          </div>
        </el-card>
        <el-card class="card" shadow="never">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-document"></i> 热点知识</span>
          </div>
          <div class="classify">
            <div v-for="item in hotKnows"  class="text item">
              <el-link @click='toArticle(item.id)' class="classify-item" icon="el-icon-caret-right" type="primary" v-text="item.title">
              </el-link>
            </div>
          </div>
        </el-card>
      </el-col>


    </el-row>
  </div>
</template>

<script>
  import {getDepartmentWithTree} from '@/api/userManagement'
  import {getEsKnow, getHotOrRecommendKnow} from '@/api/knowledgeInfo'

  export default {
    name: 'myknowledgeadd',
    data() {
      return {
        queryForm: {
          pageIndex: 0,
          pageSize: 10,
          keyWord: '',
          is_full_search: ""
        },
        hits: [],
        hotKnows: [],
        recommendKnows: [],
        recommend: true,
        totalCount: 0,
        data: [],
        defaultProps: {
          children: 'children',
          label: 'label'
        }
      }
    },
    created() {
      setTimeout(() => {
        this.showWithTree()
        this.getHotKnowledge()
        this.getRecommendKnowledge()
      }, 300)
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    methods: {
      articleSearch() {
        this.recommend = false
      },
      toArticle(value) {
        this.$router.push({
          path: '/book/myKnowledgeView',
          query: { id: value },
        })
      },
      async showWithTree() {
        const {data} = await getDepartmentWithTree()
        this.data = data
      },
      getEsKnowledge() {
        var that = this;
        if (this.queryForm.keyWord == null || this.queryForm.keyWord == "") {
          this.$baseMessage('请输入关键字！', 'error')
        } else {
          this.recommend = false;
          getEsKnow(this.queryForm).then((res) => {
            if (res.code == 0) {
              that.hits = res.data.highLights
              that.totalCount = res.data.totalCount
              console.info(that.hits);
            }
          })
        }
      },
      getHotKnowledge() {
        var that = this;
        getHotOrRecommendKnow({"type": "1"}).then((res) => {
          if (res.code == 0) {
            that.hotKnows = res.data;
            console.info(that.hotKnows);
          }
        })
      },
      getRecommendKnowledge() {
        var that = this;
        getHotOrRecommendKnow({"type": "2"}).then((res) => {
          if (res.code == 0) {
            that.recommendKnows = res.data;
            console.info(that.recommendKnows);
          }
        })
      }
    },
  }
</script>
<style lang="scss" scoped>
  .index-container{
    padding-bottom: 0 !important;
  }
  .tips-text {
    width: 100%;
    height: calc(100vh - 342px);
    text-align: center;
    color: #a9b1b9;

    div {
      width: 6.5rem;
      height: 6.5rem;
      background-color: #F5FAFF;
      text-align: center;
      border-radius: 50%;
      border: 1px solid #d6ebff;
      line-height: 6.5rem;
      margin: 40px auto;

      i {
        font-size: 60px;
        display: inline-block;
        vertical-align: middle;
        color: #d6ebff;
      }
    }
  }

  .search-result {
    height: calc(100vh - 318px);

    p {
      margin: 4px 0 10px;
      line-height: 1.6;
      color: #909399;
    }

    ::v-deep {
      .el-link {
        font-size: 1rem;
        line-height: 1.6;
      }
    }
  }

  ::v-deep {
    html body .el-divider--horizontal {
      margin: 6px 0 20px 0;
    }
    .box-card{
    .el-card__body{
      height: calc(100vh - 215px);
    }
  }
  .card{
    .el-card__body{
      height: calc(50vh - 190px);
    }
  }
    .el-form-item--small .el-form-item__content {
      margin-bottom: 16px;

      .el-input--small .el-input__inner {
        line-height: 36px;
        height: 36px;
      }

      .el-button--small {
        font-size: 0.875rem;
      }
    }
  }
  

  .treeborder {
    padding: 20px;
    height: calc(100vh - 230px);
    margin-top: 0px;
    border: 1px solid #dcdfe6;
    border-radius: 2.5px;
    overflow-y: auto;
  }

  .classify h2 span {
    font-size: 1rem;
    font-weight: normal;
    color: #999;
  }

  .classify-item {
    line-height: 2;
  }
</style>
