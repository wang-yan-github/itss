<!--打印  暂未开发/用到-->
<template>
  <div class="index-container">
    <el-card shadow="never" ref="print_remark">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="20" :lg="20" :xl="20">
          <h4 style="margin: 5px">如何进行打印设置?</h4>
          <ul style="margin: 2px 0px">
            <li>
              <b>Chrome(谷歌)</b>
              ：直接在打印页面设置页眉页脚和背景图形
            </li>

            <li>
              <b>IE和FireFox(火狐)</b>
              ：点击菜单栏：文件-&gt;页面设置，进行相关设置
            </li>

            <li>
              <b>360、搜狗及其他</b>
              ：先在IE浏览器中设置好打印选项，然后将这些浏览器设置为兼容模式即可
            </li>
          </ul>
        </el-col>
        <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
          <el-button type="primary" onclick="window.print();" ref="printBtn">
            打印（Ctrl+P）
          </el-button>
        </el-col>
      </el-row>
    </el-card>
    <el-card ref="print_content" shadow="never">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <p>编号：13491</p>
          <table
            align="left"
            border="1"
            cellspacing="0"
            class="Table"
            style="
              border-collapse: collapse;
              border-image: none;
              border: 1.5pt solid windowtext;
              margin-left: 0;
              margin-right: 0;
              width: 800px;
              margin-top: 30px;
              margin-bottom: 20px;
            "
          >
            <tbody>
              <tr>
                <td
                  colspan="4"
                  style="background-color: #d9d9d9; height: 2cm; width: 515.4pt"
                >
                  <h1
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: center;
                    "
                  >
                    <strong>维修单</strong>
                  </h1>
                </td>
              </tr>
              <tr>
                <td style="height: 2cm; vertical-align: bottom; width: 69.2pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: left;
                    "
                  >
                    <span style="color: black">用户：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 167.3pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: left;
                    "
                  >
                    迟鹏（admin）
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 59.5pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: left;
                    "
                  >
                    <span style="color: black">邮件：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 219.4pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: left;
                    "
                  >
                    chipeng@topvee.com
                  </p>
                </td>
              </tr>
              <tr>
                <td style="height: 2cm; vertical-align: bottom; width: 69.2pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">电话：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 167.3pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    18101053540
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 59.5pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">部门：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 219.4pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    拓唯科技
                  </p>
                </td>
              </tr>
              <tr>
                <td
                  colspan="4"
                  style="background-color: #d9d9d9; height: 2cm; width: 515.4pt"
                >
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: center;
                    "
                  >
                    <strong>
                      <span style="color: black">设备基本信息</span>
                    </strong>
                  </p>
                </td>
              </tr>
              <tr>
                <td style="height: 2cm; vertical-align: bottom; width: 69.2pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">设备编号：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 167.3pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  ></p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 59.5pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">SN</span>
                    <span style="color: black">：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 219.4pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    &nbsp;
                  </p>
                </td>
              </tr>
              <tr>
                <td
                  colspan="4"
                  style="background-color: #d9d9d9; height: 2cm; width: 515.4pt"
                >
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: center;
                    "
                  >
                    <strong><span style="color: black">故障信息</span></strong>
                  </p>
                </td>
              </tr>
              <tr>
                <td style="height: 2cm; vertical-align: bottom; width: 69.2pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">开单人：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 167.3pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    迟鹏
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 59.5pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">处理人：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 219.4pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    DingChi
                  </p>
                </td>
              </tr>
              <tr>
                <td style="height: 2cm; vertical-align: bottom; width: 69.2pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">响应时间：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 167.3pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    2022-03-14 15:21
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 59.5pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">解决时间：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 219.4pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  ></p>
                </td>
              </tr>
              <tr>
                <td style="height: 2cm; vertical-align: bottom; width: 69.2pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">工单类别：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 167.3pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  ></p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 59.5pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">备注：</span>
                  </p>
                </td>
                <td style="height: 2cm; vertical-align: bottom; width: 219.4pt">
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    &nbsp;
                  </p>
                </td>
              </tr>
              <tr>
                <td
                  colspan="4"
                  style="height: 2cm; vertical-align: bottom; width: 515.4pt"
                >
                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    标题：fdsafdsafdsafd
                  </p>

                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <span style="color: black">描述：&nbsp;&nbsp;&nbsp;</span>
                  </p>

                  <p
                    style="
                      margin-left: 0cm;
                      margin-right: 0cm;
                      text-align: justify;
                    "
                  >
                    <a
                      href="/admin/filee/viewPic.do?manageKey=8e8aef0ede99120170043792200d14e7"
                      target="_blank"
                    >
                      big_buck_bunny.mp4
                    </a>
                  </p>
                </td>
              </tr>
            </tbody>
          </table>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <strong>※此单据做为资产维修凭证，取件时需出示，请妥善保管。</strong>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'printpaper',
  data() {
    return {
      value1: '',
      title: '',
      dialogFormVisible: false,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      loading: false,
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
    }
  },
  created() {
    setTimeout(() => {
      this.listLoading = false
    }, 300)
  },
  methods: {
    close() {
      // this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.close()
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .left-panel {
    .el-input {
      width: 200px !important;
    }
  }
}
</style>
