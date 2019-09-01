# DEMO概述

使用MyBatis XML+MVC+Servlet+JSP思想，实现一个简单的用户信息管理系统DEMO。拥有对用户的CRUD、修改用户状态的功能。

# 所用到的技术

- MyBatis基本操作

  - 建立<font color="#FF0000">**mybatis.xml**</font>配置文件→读取配置文件获得<font color="#FF0000">**SqlSessionFactory**</font>对象→建立<font color="#FF0000">**SqlSession**</font>→建立<font color="#FF0000">**mapper.xml**</font>文件→使用SqlSession执行mapper中的SQL语句。

- 动态SQL语句

- Servlet使用

- 工具类的抽取

  - 抽取SqlSessionFactory和SqlSession。

- 监听器的使用

  一旦Web应用启动，就自动建立SqlSessionFactory对象。Web应用关闭，则释放。

------

# 案例结构

![项目结构](https://raw.githubusercontent.com/MaJesTySA/JavaWeb/master/img/User_Data_Management/struct.png)

# 功能演示

## 首页

用户状态为“正常”时，点击“锁定”，用户状态变成“锁定”。用户状态为“锁定”时，点击“解锁”，用户状态变成“正常”。

![效果](https://raw.githubusercontent.com/MaJesTySA/JavaWeb/master/img/User_Data_Management/UserData.png)

## 添加

![添加](<https://raw.githubusercontent.com/MaJesTySA/JavaWeb/master/img/User_Data_Management/add.png>)

## 详情+编辑

![详情](<https://raw.githubusercontent.com/MaJesTySA/JavaWeb/master/img/User_Data_Management/detail.png>)

