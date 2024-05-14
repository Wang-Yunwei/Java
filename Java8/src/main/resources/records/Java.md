# Java

---

## JavaRun参数注解

```text
(注: 通常 -Xms 与 -Xmx两个参数的配置相同的值,其目的是为了能够在java垃圾回收机制清理完堆区后不需要重新分隔计算堆区的大小而浪费资源)

* -Xms 表示JVM启动初始堆大小
* -Xmx 表示最大堆大小
* -XX:newSize 表示新生代初始内存的大小，小于 -Xms的值
* -XX:MaxnewSize 表示新生代可被分配的内存的最大上限,小于 -Xmx的值
* -Xmn 表示-XX:newSize、-XX:MaxnewSize两个参数的同时配置，如果通过-Xmn来配置新生代的内存大小，那么-XX:newSize = -XX:MaxnewSize = -Xmn，虽然会很方便，但需要注意的是这个参数是在JDK1.4版本以后才使用的。
* -XX:NativeMemoryTracking=summary 表示开启内存追踪
* --Xdebug 表示开启调试
* -Xrunjdwp 表示加载JVM的JPDA参考实现库
* server=y/n 表示VM 是否需要作为调试服务器执行
* transport 用于在调试程序和VM使用的进程之间通讯
* dt_socket 表示套接字传输
* suspend=y/n 是否在调试客户端建立连接之后启动VM

* Java 7 及以前版本
* -XX:PermSize 表示非堆区初始内存分配大小，其缩写为permanent size(持久化内存)
* -XX:MaxPermSize 表示对非堆区分配的内存的最大上限

* Java 8 版本
* -XX:MetaspaceSize 元空间大小
* -XX:MaxMetaspaceSize 最大元空间
```

- 指定配置文件运行

```text
# java -jar xxx.jar --spring.profiles.active=fileName
```

- 配置运行内存运行

```text
# java -jar xxx.jar -Xms512m -Xmx1024m -XX:PermSize=256M -XX:MaxPermSize=512M
```

- 断点运行

```text
# java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=8123,suspend=n -jar xxx.jar
```

## 安装JDK

- 卸载CentOS原本自带的openjdk (注: -e表示卸载,--nodeps表示强制卸载)

```text
1.列出系统自带的openjdk: rpm -qa|grep jdk
2.删除openjdk: rpm -e --nodeps 或 yum remove *openjdk*
[例]
    rpm -e --nodeps java-1.8.0-openjdk-1.8.0.102-4.b14.el7.x86_64
    rpm -e --nodeps java-1.8.0-openjdk-headless-1.8.0.102-4.b14.el7.x86_64
    rpm -e --nodeps java-1.7.0-openjdk-1.7.0.111-2.6.7.8.el7.x86_64
    rpm -e --nodeps java-1.7.0-openjdk-headless-1.7.0.111-2.6.7.8.el7.x86_64
```

- 安装 oracle-jdk

```text
1.创建文件夹: mkdir /opt/java
2.解压: tar -zxvf jdk-8u211-linux-x64.tar.gz -C /opt/java
3.配置环境变量 (需root用户权限)修改文件: vi /etc/profile ,添加如下信息
-----------------------------------------------------------------
export JAVA_HOME=/opt/java/jdk1.8.0_211
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
-----------------------------------------------------------------
4.使配置生效: source /etc/profile
5.测试是否配置成功: java -version
```

- yum方式安装openjdk (注: 此方式不需要配置环境变量)

```text
1.列出可以安装的openjdk: yum list|grep jdk
2.选择版本安装: yum -y install java-1.8.0-openjdk.x86_64
```

- 查看GC信息

> jstat -gcutil 进程号 ls