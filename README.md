# 博客项目

##  1、博客介绍

​	基于SpringBoot + Vue开发的前后端分离博客

##  2、在线地址

- 项目链接：即将更新
- 后台链接：即将更新
- 测试账号：即将将更新
- Github地址：https://github.com/Czh2000/Blog
- 在线接口文档地址：即将更新

##  3、目录结构

- 前端项目位于blog-vue下，blog为前台，admin为后台
- 后端项目位于blog-springboot下
- SQL文件位于根目录下的blog-mysql8.sql，需要MySQL8以上的版本
- PS（注意）：先运行后端项目，再启动前端项目，前端项目的配置由后端动态加载

- 后端结构

```Java
blog-springboot
  - annotation    -- 自定义注解
  - aspect				-- aop模块
  - config				-- 配置模块
  - constant			-- 常量模块
  - consumer			-- MQ消费者模块
  - controller		-- 控制器模块
  - dao						-- 框架核心模块
  - dto						-- dto模块
  - enums					-- 枚举模块
  - exception			-- 自定义异常模块
  - handler				-- 处理器模块（扩展Security过滤器，自定义Security提示信息等）
  - service				-- 服务模块
  - strategy			-- 策略模块（用于扩展第三方登录，搜索模式，上传文件模式等策略）
  - util					-- 工具类模块
  - vo						-- vo模块
```

##  4、项目特点

- 前台参考“HEXO”的Butterfly设计，美观简洁，响应式体验好
- 后台参考“element-admin”设计，侧边栏，历史标签，面包屑自动生成
- 采用Markdown编辑器，写法简单
- 评论支持表情输入恢复等，样式参考Valine
- 添加音乐播放器，支持在线搜索歌曲
- 前后端分离部署，适应当前潮流
- 接入第三方登录，减少注册成本
- 支持发布说说，随时分享趣事
- 留言采用碳幕墙，更加炫酷
- 支持代码高亮和复制，图片预览，深色模式等功能，提升用户体验
- 搜索文章支持高亮分词，响应速度快
- 新增文章目录，推荐文章等功能，优化用户体验
- 新增在线聊天室、支持撤回、语音输入、统计未读数量等功能
- 新增aop注解实现日志管理
- 支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新
- 后台管理支持修改背景图片，博客配置等信息，操作简单，支持上传相册
- 代码支持多种搜索模式（Elasticsearch 或 MySQL），支持多种上传模式（COS 或 本地），可支持配置
- 代码遵循阿里巴巴开发规范，利于开发者学习

##  5、技术介绍

1、前端：vue + vuex + vue+router + axios + vuetify + element + echarts

2、后端：SpringBoot + nginx + docker + SpringSecurity + Swagger2 + MybatisPlus + Mysql + Redis + elasticsearch + RabbitMQ + MaxWell + Websocket

3、其他环境：接入QQ

##  6、运行环境

1、服务器：腾讯云2核4G Centos8

2、CDN：阿里云全站加速

3、对象存储：腾讯云COS

- 这套配置响应速度非常快，可以做到响应100ms以下

4、最低配置：1核2G服务器（关闭ElasticSearch）

##  7、开发环境

|           开发工具            |       说明        |
| :---------------------------: | :---------------: |
|             IDEA              |  Java开发工具IDE  |
|            VSCode             |  Vue开发工具IDE   |
|            navicat            | MySQL远程连接工具 |
| Another Reids Desktop Manager | Redis远程连接工具 |
|            X-Shell            | Linux远程连接工具 |
|             Xftp              | Linux文件上传工具 |



|   开发环境    |  版本  |
| :-----------: | :----: |
|      JDK      |  1.8   |
|     MySQL     | 8.0.27 |
|     Redis     | 6.0.5  |
| Elasticsearch | 7.9.2  |
|   RabbitMQ    | 3.8.5  |

##  8、项目截图

<img src="/Users/chenzhihui/Library/Application Support/typora-user-images/image-20221015181440716.png" alt="image-20221015181440716" style="zoom: 50%;" />

##  9、快速开始

1、项目环境安装

2、项目配置

3、Docker项目部署

##  10、项目总结

  在慢慢总结完善，敬请期待。
