------------------------------
# 进入项目目录
cd ITSS
# 安装依赖
npm install -g cnpm --registry=https://registry.npm.taobao.org
cnpm i
# 本地开发 启动项目
npm run serve
cnpm run serve --fix
------------------------------------

cnpm install sortablejs --save-dev     //可拖拽表格组件
cnpm install vue-clipboard2 --save   //复制到剪切板组件

npm install --save html2canvas  //将页面html转换成图片

npm install jspdf --save //将图片生成pdf

npm install vue-quill-editor --save  //富文本编辑

npm install vue-print-nb --save  //打印

项目默认使用lf换行符而非crlf换行符，新建文件时请注意选择文件换行符
项目默认使用的最严格的eslint校验规范（plugin:vue/recommended），使用之前建议配置开发工具实现自动修复（建议使用vscode开发）
