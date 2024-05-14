# Zookeeper

---

## 安装server

```text
1.通过wget命令下载: wget http://mirrors.hust.edu.cn/apache/zookeeper/zookeeper-3.4.13/zookeeper-3.4.13.tar.gz
[或]
    网页下载: http://mirror.bit.edu.cn/apache/zookeeper/
2.解压: tar -zxvf zookeeper-3.4.13.tar.gz
3.在解压包中创建文件夹: mkdir data
4.复制一份zoo_sample.cfg文件并重命名: cp zoo_sample.cfg zoo_2019_7_22.cfg
5.编辑zoo_sample.cfg文件,修改如下信息:
----------------------------------
dataDir=/app/zookeeper-3.4.13/data
----------------------------------
6.进入/app/zookeeper-3.4.13/bin目录启动zookeeper服务: ./zkServer.sh start
7.查看zookeeper服务状态: ./zkServer.sh status
```

## 安装zookeeper-ui

```text
1.通过git clone拉取源码包: git clone https://github.com/DeemOpen/zkui.git
[或]
    网页下载: https://github.com/DeemOpen/zkui
2.解压: unzip zkui-master.zip
3.安装maven: yum install -y maven
4.进入zkui解压包执行: mvn clean install -D maven.test.skip=true
5.修改config.cfg配置文件,添加如下信息:
--------------------------
serverPort=7070   #指定端口
zkServer=localhost:2181,10.154.9.104:2181
userSet={"users":[{"username":"admin","password":"admin","role":"ADMIN"},{...}]}
--------------------------
6.启动: nohup java -jar zkui-2.0-SNAPSHOT-jar-with-dependencies.jar &
```
