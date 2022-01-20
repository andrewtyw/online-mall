# online-mall
Elective courses project

### 姓名
王天寅

### 学号
2019301424**

### 网址:
http://120.24.230.237/home

### 测试账号:
1. 用户(你也可以自己注册一个账号)
* 账户名: tywang
* 密码: 123456

2. 商家(你也可以自己注册一个账号)
* 账户名: Apple旗舰店
* 密码: 123456

## 后端代码结构(serverV2)
src.main.java.com.example.serverv2 中的内容为主要的后端代码
```
|-- serverV2
    |-- .gitignore
    |-- HELP.md
    |-- mvnw
    |-- mvnw.cmd
    |-- pom.xml
    |-- serverV2.iml
    |-- .mvn
    |   |-- wrapper
    |       |-- maven-wrapper.jar
    |       |-- maven-wrapper.properties
    |       |-- MavenWrapperDownloader.java
    |-- src
    |   |-- main
    |   |   |-- java
    |   |   |   |-- com
    |   |   |       |-- example
    |   |   |           |-- serverv2
    |   |   |               |-- ServerV2Application.java
    |   |   |               |-- CoEntity
    |   |   |               |   |-- CartGoodsCo.java
    |   |   |               |-- common
    |   |   |               |   |-- MybatisConfig.java
    |   |   |               |   |-- Result.java
    |   |   |               |-- controller
    |   |   |               |   |-- CartController.java
    |   |   |               |   |-- ClicksController.java
    |   |   |               |   |-- FileController.java
    |   |   |               |   |-- GoodsController.java
    |   |   |               |   |-- OrdersController.java
    |   |   |               |   |-- SellerController.java
    |   |   |               |   |-- UserController.java
    |   |   |               |-- mapper
    |   |   |               |   |-- CartMapper.java
    |   |   |               |   |-- ClicksMapper.java
    |   |   |               |   |-- GoodsMapper.java
    |   |   |               |   |-- OGMapper.java
    |   |   |               |   |-- OrdersMapper.java
    |   |   |               |   |-- SellerMapper.java
    |   |   |               |   |-- UserMapper.java
    |   |   |               |-- networks
    |   |   |               |   |-- Mail.java
    |   |   |               |   |-- serverNetwork.java
    |   |   |               |-- pojo
    |   |   |               |   |-- Cart.java
    |   |   |               |   |-- Clicks.java
    |   |   |               |   |-- Goods.java
    |   |   |               |   |-- OG.java
    |   |   |               |   |-- Orders.java
    |   |   |               |   |-- Seller.java
    |   |   |               |   |-- User.java
    |   |   |               |-- service
    |   |   |                   |-- CartService.java
    |   |   |                   |-- ClicksService.java
    |   |   |                   |-- GoodsService.java
    |   |   |                   |-- OGService.java
    |   |   |                   |-- OrdersService.java
    |   |   |                   |-- SellerService.java
    |   |   |                   |-- UserService.java
    |   |   |                   |-- impl
    |   |   |                       |-- CartServiceImpl.java
    |   |   |                       |-- ClicksServiceImpl.java
    |   |   |                       |-- GoodsServiceImpl.java
    |   |   |                       |-- OGServiceImpl.java
    |   |   |                       |-- OrdersServiceImpl.java
    |   |   |                       |-- SellerServiceImpl.java
    |   |   |                       |-- UserServiceImpl.java
    |   |   |-- resources
    |   |       |-- application-prod.properties
    |   |       |-- application.properties
    |   |       |-- files
    |   |       |-- static
    |   |       |-- templates
    |   |-- test
    |       |-- java
    |           |-- com
    |               |-- example
    |                   |-- serverv2
    |                       |-- ServerV2ApplicationTests.java
    |-- target
        |-- serverV2-0.0.1-SNAPSHOT.jar
        |-- serverV2-0.0.1-SNAPSHOT.jar.original
        |-- classes
        |   |-- application-prod.properties
        |   |-- application.properties
        |   |-- com
        |   |   |-- example
        |   |       |-- serverv2
        |   |           |-- ServerV2Application.class
        |   |           |-- CoEntity
        |   |           |   |-- CartGoodsCo.class
        |   |           |-- common
        |   |           |   |-- MybatisConfig.class
        |   |           |   |-- Result.class
        |   |           |-- controller
        |   |           |   |-- CartController.class
        |   |           |   |-- ClicksController.class
        |   |           |   |-- FileController.class
        |   |           |   |-- GoodsController.class
        |   |           |   |-- OrdersController.class
        |   |           |   |-- SellerController.class
        |   |           |   |-- UserController.class
        |   |           |-- mapper
        |   |           |   |-- CartMapper.class
        |   |           |   |-- ClicksMapper.class
        |   |           |   |-- GoodsMapper.class
        |   |           |   |-- OGMapper.class
        |   |           |   |-- OrdersMapper.class
        |   |           |   |-- SellerMapper.class
        |   |           |   |-- UserMapper.class
        |   |           |-- networks
        |   |           |   |-- Mail.class
        |   |           |   |-- serverNetwork.class
        |   |           |-- pojo
        |   |           |   |-- Cart.class
        |   |           |   |-- Clicks.class
        |   |           |   |-- Goods.class
        |   |           |   |-- OG.class
        |   |           |   |-- Orders.class
        |   |           |   |-- Seller.class
        |   |           |   |-- User.class
        |   |           |-- service
        |   |               |-- CartService.class
        |   |               |-- ClicksService.class
        |   |               |-- GoodsService.class
        |   |               |-- OGService.class
        |   |               |-- OrdersService.class
        |   |               |-- SellerService.class
        |   |               |-- UserService.class
        |   |               |-- impl
        |   |                   |-- CartServiceImpl.class
        |   |                   |-- ClicksServiceImpl.class
        |   |                   |-- GoodsServiceImpl.class
        |   |                   |-- OGServiceImpl.class
        |   |                   |-- OrdersServiceImpl.class
        |   |                   |-- SellerServiceImpl.class
        |   |                   |-- UserServiceImpl.class
        |   |-- files
        |          
        |-- generated-sources
        |   |-- annotations
        |-- generated-test-sources
        |   |-- test-annotations
        |-- maven-archiver
        |   |-- pom.properties
        |-- maven-status
        |   |-- maven-compiler-plugin
        |       |-- compile
        |       |   |-- default-compile
        |       |       |-- createdFiles.lst
        |       |       |-- inputFiles.lst
        |       |-- testCompile
        |           |-- default-testCompile
        |               |-- createdFiles.lst
        |               |-- inputFiles.lst
        |-- surefire-reports
        |   |-- com.example.serverv2.ServerV2ApplicationTests.txt
        |   |-- TEST-com.example.serverv2.ServerV2ApplicationTests.xml
        |-- test-classes
            |-- com
                |-- example
                    |-- serverv2
                        |-- ServerV2ApplicationTests.class


```

## 前端代码结构(serverV2)
shopvue.src  下的内容为主要的前端代码
```
|-- shopvue
    |-- .gitignore
    |-- babel.config.js
    |-- package-lock.json
    |-- package.json
    |-- README.md
    |-- shopvue.iml
    |-- vue.config.js
    |-- .idea
    |   |-- modules.xml
    |   |-- runConfigurations.xml
    |   |-- shopvue.iml
    |   |-- vcs.xml
    |   |-- workspace.xml
    |-- dist
    |   |-- favicon.ico
    |   |-- index.html
    |   |-- css
    |   |   |-- app.dd952cfa.css
    |   |   |-- chunk-vendors.0b92cd75.css
    |   |-- fonts
    |   |   |-- element-icons.abe71f7d.ttf
    |   |   |-- element-icons.d9491be2.woff
    |   |-- js
    |       |-- about.b8bd926f.js
    |       |-- about.b8bd926f.js.map
    |       |-- app.2573626e.js
    |       |-- app.2573626e.js.map
    |       |-- chunk-vendors.43b798eb.js
    |       |-- chunk-vendors.43b798eb.js.map
    |-- public
    |   |-- favicon.ico
    |   |-- index.html
    |-- src
        |-- App.vue
        |-- main.js
        |-- api
        |   |-- elementApi.js
        |   |-- storage.js
        |-- assets
        |   |-- logo.png
        |   |-- imgs
        |       |-- logo.png
        |       |-- phone.png
        |-- components
        |   |-- Header.vue
        |   |-- HelloWorld.vue
        |-- router
        |   |-- index.js
        |-- store
        |   |-- index.js
        |-- utils
        |   |-- request.js
        |-- views
            |-- About.vue
            |-- AllGoods.vue
            |-- Home.vue
            |-- SellerManage.vue
            |-- UserCart.vue
            |-- UserOrders.vue

```
