# MAVEN

---

## 常用命令

> **1.显示版本信息:**
> mvn –version

> **2.清理项目生产的临时文件,一般是模块下的target目录:**
> mvn clean

> **3.编译源代码,一般编译模块下的src/main/java目录:**
> mvn compile

> **4.项目打包工具,会在模块下的target目录生成jar或war等文件:**
> mvn package

> **5.测试命令,或执行src/test/java/下junit的测试用例:**
> mvn test

> **6.将打包的jar/war文件复制到你的本地仓库中,供其他模块使用:**
> mvn install

> **7.将打包的文件发布到远程参考,提供其他人员进行下载依赖:**
> mvn deploy

> **8.生成项目相关信息的网站:**
> mvn site

> **9.将项目转化为Eclipse项目:**
> mvn eclipse:eclipse

> **10.打印出项目的整个依赖树:**
> mvn dependency:tree

> **11.创建Maven的普通java项目:**
> mvn archetype:generate

> **12.在tomcat容器中运行web应用:**
> mvn tomcat:run

> **13.调用 Jetty 插件的 Run 目标在 Jetty Servlet 容器中启动 web 应用:**
> mvn jetty:run

## 命令参数

> **1.-D 传入属性参数:**
> mvn package -D maven.test.skip=true

> **2.-P 使用指定的Profile配置:**
> mvn package -P dev

> **6.-U 强制去远程更新snapshot的插件或依赖,默认每天只更新一次**

> **5.-X 显示maven允许的debug信息**

> **3.-e 显示maven运行出错的信息**

> **4.-o 离线执行命令,即不去远程仓库更新包**