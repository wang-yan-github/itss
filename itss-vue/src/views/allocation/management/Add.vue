<template>
  <div class="main-container pb80">
    <el-card shadow="never" class="allcon">
      <div slot="header" class="clearfix">
        <i class="el-icon-check"></i>
        &nbsp;
        <span>请选择配置项类型</span>
      </div>
      <div class="box_card" v-for="item in CIList" :key="item.id">
        <div class="box_header">{{ item.group_name }}</div>
        <div class="box_content">
          <template v-for="assetsType in assetsTypeList">
            <div
              class="allocation"
              :key="assetsType.id"
              v-if="assetsType.group_id == item.id"
            >
              <el-card shadow="hover">
                <el-image
                  style="width: 100px; height: 100px"
                  :src= "assetsType.pic_path"
                  fit="contain"
                ></el-image>
                <h5>{{ assetsType.name }}</h5>
                <div
                  @click="
                    openWinSel(
                      assetsType.code,
                      assetsType.id,
                      assetsType.add_address
                    )
                  "
                >
                  <el-link>添加</el-link>
                </div>
              </el-card>
            </div>
          </template>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getList } from '@/api/assetsClGroup'
import { getAssetsTypeList } from '@/api/configtypemanagement'

export default {
  name: 'manageSelAdd',
  components: {},
  data() {
    return {
      CIList: {},
      assetsTypeList: {},
      url: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
      // pic_path: require('E:\\upload\\背景.jpeg')
    }
  },
  created() {
    this.fetchCIData();
    this.fetchAssetsTypeData()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    isEmpty(obj) {
      if (typeof obj == 'undefined' || obj == null || obj == '') {
        return true
      } else {
        return false
      }
    },
    openWinSel(code, id, add_address) {
      if (this.isEmpty(add_address)) {
        this.$message.error('请先添加地址')
        return
      }
      this.$router.push({
        path: add_address,
        query: { id: id },
      })
    },
    async fetchCIData() {
      this.listLoading = true
      const { data } = await getList({})
      this.CIList = data.list
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async fetchAssetsTypeData() {
      this.listLoading = true
      const { data } = await getAssetsTypeList({})
      console.log(data);
      this.assetsTypeList = data
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  ::v-deep {
    .allcon .el-card__body {
      padding-bottom: 0;
      .allocation .el-card__body {
        padding-bottom: 20px;
      }
    }
  }

  .box_content {
    display: -webkit-flex;
    /* Safari */
    display: flex;
    flex-wrap: wrap;
  }

  .allocation {
    margin: 0 20px 30px 0;
    width: 160px;
    height: 160px;
    text-align: center;
  }

  .allocation h5 {
    margin: 0 0 6px;
  }
}
</style>
