> ## [快速启动步骤](https://helio.uncarbon.cc/#/i18n/zh-CN/helio-cloud/quick-start)

1. 本目录存放的是脚手架附件，包括DB初始化脚本、Nacos初始化配置等
2. `db`目录存放脚手架的DB初始化脚本，请根据DB架构自行选择
    - `helio_sys.sql`: 提供后台管理能力，使用脚手架则必须导入
    - `helio_oss.sql`: 提供记录上传文件信息的能力，建议导入
    - `upgrade`目录存放脚手架版本升级时的DB变更脚本
3. `nacos`目录存放脚手架的Nacos初始化配置
   - `upgrade`目录存放脚手架版本升级时的Nacos配置变更点
