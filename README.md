# helio-cloud

## 项目介绍

`helio-cloud`基于 Spring Boot 2.7.x + Spring Cloud Alibaba 2021 + Dubbo 3，是一款预置SaaS、RBAC能力的微服务项目脚手架，助力开发者快速上手企业级微服务开发

JDK compatibility: 1.8 - 17

【[官方文档](https://helio.uncarbon.cc/)】
【[主要技术栈依赖](https://helio.uncarbon.cc/#/i18n/zh-CN/helio-starters/dependencies)】
【[快速启动步骤](https://helio.uncarbon.cc/#/i18n/zh-CN/helio-cloud/quick-start)】
【[前端演示站](https://helio-demo.uncarbon.cc/)】
【[更新记录](https://helio.uncarbon.cc/#/i18n/zh-CN/appendix/change-log)】


需要先安装 `MySQL`、`Redis` 等必需中间件

基础支撑构件 [helio-starters](https://github.com/uncarbon97/helio-starters) 已推送至Maven中央仓库，加载时会自动拉取

## 配套后台管理前端模板 && 代码生成器
| 项目名                  | 简介                                                                          | Gitee                                                      | GitHub                                                       |
|----------------------|-----------------------------------------------------------------------------|------------------------------------------------------------|--------------------------------------------------------------|
| helio-generator      | 可一键生成单体or微服务版的前、后端代码，减少无谓的重复劳动                                              | [Gitee](https://gitee.com/uncarbon97/helio-generator)      | [GitHub](https://github.com/uncarbon97/helio-generator)      |
| helio-admin-vue-vben | 基于[Vue vben admin](https://github.com/anncwb/vue-vben-admin) 改造适配的前端框架，开箱即用 | [Gitee](https://gitee.com/uncarbon97/helio-admin-vue-vben) | [GitHub](https://github.com/uncarbon97/helio-admin-vue-vben) |

## 项目构成
| 子模块名                | 简介                                                                                                                               | HTTP 路由安全设计                               |
|---------------------|----------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------|
| api/app-api         | 【只是一个骨架，并没有业务实现】为C端预留的HTTP-API微服务模块 <br> 可直接将域名反代至该模块(如:`app-api.uncarbon.cc`)                                                   | 使用的是路由拦截鉴权，即除登录、注册外几乎所有接口都需要登录            |
| api/saas-api        | 为后台管理准备的HTTP-API微服务模块 <br> 可直接将域名反代至该模块(如:`saas-api.uncarbon.cc`)                                                                | 使用的是注解鉴权，即所有接口都得指定是否需要登录或权限标识字符串，以实现细粒度鉴权 |
| attachments         | 本脚手架附件                                                                                                                           |
| knife4j/aggregation | 【生产环境务必关闭】Knife4j聚合文档，本地运行后打开网址http://127.0.0.1:7000/doc.html ，默认账号密码helio helio <br> 可直接将域名反代至该模块(如:`dev.uncarbon.cc/doc.html`) |
| microservice/sys/** | 脚手架预置的后台管理微服务模块。 <br> `sys-facade`暴露功能接口、Bean，供其他微服务通过 Dubbo 调用 <br> `sys-service`实现 Facade 接口和业务逻辑                              |
| microservice/oss/** | 脚手架预置的对象存储微服务模块。 <br> `oss-facade`暴露功能接口、Bean，供其他微服务通过 Dubbo 调用 <br> `oss-service`实现 Facade 接口和业务逻辑                              |
