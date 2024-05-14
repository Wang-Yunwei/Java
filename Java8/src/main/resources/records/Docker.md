# Oeacle

```text
[注]
    Docker-CE指Docker社区版: 为免费版本,由社区维护和提供技术支持
    Docker-EE指Docker企业版: 为收费版本
Docker的发布版本分为Stable版、Edge版,区别在于前者是按季度发布的稳定版(发布慢),后者是按月发布的边缘版(发布快),还有Test版和Nightly版,所谓nightly版本,通常是开发者自己维护的一个版本
```

---

# 安装

- 在线安装

```text
1.卸载旧版本: yum remove docker \
                               docker-client \
                               docker-client-latest \
                               docker-common \
                               docker-latest \
                               docker-latest-logrotate \
                               docker-logrotate \
                               docker-engine
2.安装yum-util包(提供yum-config-manager实用程序): yum install -y yum-utils
3.设置稳定存储库: yum-config-manager \--add-repo \https://download.docker.com/linux/centos/docker-ce.repo
4.列出并排序存储库中可用的版本: yum list docker-ce --showduplicates | sort -r
5.安装: yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io
[例]
    yum install docker-ce-19.03.9 docker-ce-cli-19.03.9 containerd.io
6.启动Docker服务: systemctl start docker
7.查看docker服务状态: systemctl status docker
8.查看docker信息: docker info
9.查看docker版本信息: docker version
10.通过运行hello-world 映像验证 Docker Engine 是否已正确安装: docker run hello-world
11.设为开机启动: systemctl enable docker
```

- 离线安装

```text
1.下载安装包: https://download.docker.com/linux/centos/7/x86_64/stable/Packages/
2.安装: yum install /app/docker-ce-20.10.9-3.el7.x86_64.rpm /app/docker-ce-cli-20.10.9-3.el7.x86_64.rpm /app/containerd.io-1.5.10-3.1.el7.x86_64.rpm
3.启动: systemctl start docker
4.测试: docker run hello-world
5.创建docker.service文件: touch docker.service 或 > docker.service,并添加如下信息:
-----------------------------------------------
[Unit]
Description=Docker Application Container Engine
Documentation=https://docs.docker.com
After=network-online.target firewalld.service
Wants=network-online.target
[Service]
Type=notify
#此处的--insecure-registry=127.0.0.1(此处改成你私服ip)
#设置是针对有搭建了自己私服Harbor时允许docker进行不安全的访问,否则访问将会被拒绝
#ExecStart=/usr/bin/dockerd --selinux-enabled=false --insecure-registry=127.0.0.1
#此处的--graph=/app/docker-root 是修改默认的Docker Root Dir 路径
#ExecStart=/usr/bin/dockerd --graph=/app/docker-root
ExecStart=/usr/bin/dockerd --selinux-enabled=false
ExecReload=/bin/kill -s HUP $MAINPID
LimitNOFILE=infinity
LimitNPROC=infinity
LimitCORE=infinity
TimeoutStartSec=0
Delegate=yes
KillMode=process
Restart=on-failure
StartLimitBurst=3
StartLimitInterval=60s
[Install]
WantedBy=multi-user.target
-----------------------------------------------
6.给docker.service添加执行权限: chmod +x /etc/systemd/system/docker.service
7.重载配置文件: systemctl daemon-reload
8.启动: systemctl start docker
9.查看docker服务状态: systemctl status docker
10.查看docker版本信息: docker version
11.设置开机启动: systemctl enable docker.service
```

## 启动容器参数详解

```text
1.--name tomcat        #为此容器起一个别名叫 tomcat
1.--appendonly yes    #开启redis 持久化
2.--privileged=true   #使用该参数,容器内的root拥有真正的root权限,否则容器内的root只是外部的一个普通用户权限。
3.--restart=always    #能够使我们在重启docker时,自动启动相关容器,如果容器启动时未添加该参数可以通过命令来修
                                      改 docker container update --restart=always [CONTAINER ID]
[注]
    no             #默认策略，在容器退出时不重启容器
    on-failure     #在容器非正常退出时（退出状态非0），才会重启容器
    on-failure:3   #在容器非正常退出时重启容器，最多重启3次
    always         #在容器退出时总是重启容器，表示此容器开机启动,只要docker也设置了开机
                    自启,docker不死则运行的项目不死,就问你怕不怕(个人表示很害怕,弱小无
                    助的我,瑟瑟发抖)
    unless-stopped #在容器退出时总是重启容器,但是不考虑在Docker守护进程启动时就已经停止
                    了的容器
[错误]
    Docker容器启动报WARNING: IPv4 forwarding is disabled. Networking will not work
[解决 ]
    1).在宿主机上编辑: vim /etc/sysctl.conf 或 vi /usr/lib/sysctl.d/00-system.conf ,添加如下信息
---------------------
net.ipv4.ip_forward=1
---------------------
    2).重启network服务: systemctl restart network
    3).查看是否修改成功: /sbin/sysctl net.ipv4.ip_forward
```

## 镜像操作

```text
1.查询本地镜像: docker images 或 docker image ls
2.搜索Tomcat镜像: docker search tomcat
3.获取镜像: docker pull tomcat:7.0.75
4.修改镜像TAG: docker tag mytomcat:7.0.75 [IMAGE ID]
5.删除镜像: docker rmi [IMAGE ID]
6.查看镜像详细信息: docker inspect [IMAGE ID]
7.导出镜像: docker save -o xxx.tar [IMAGE ID] 或 docker save [IMAGE ID] > xxx.tar
8.导入镜像: docker load -i xxx.tar 或 docker load < xxx.tar
```

## 容器操作

```text
1.查看所有容器: docker ps -a
2.查看正在运行的容器: docker ps 或 不省略信息: docker ps -a --no-trunc
3.启动容器: docker start [CONTAINER ID]
4.停止容器: docker stop [CONTAINER ID]
5.重启容器: docker restart [CONTAINER ID]
6.删除容器: docker rm [CONTAINER ID]
7.删除运行中的容器: docker rm -f [CONTAINER ID]
8.导出容器: docker export [CONTAINER ID] > xxx.tar
9.导入容器: docker import [CONTAINER ID]
10.进入容器: docker exec -it [CONTAINER ID] /bin/bash
11.从服务器拷贝文件到容器中(注:要在容器外执行): docker cp /app/esi-service-8.0.1-SNAPSHOT.war [CONTAINER ID]:/app/eshub/3-service/tomcat/webapps
12.从容器拷贝文件到服务器上(注:要在容器外执行): docker cp [CONTAINER ID]:/app/eshub/3-service/tomcat/webapps/esi-service-8.0.1-SNAPSHOT.war /app
13.查看容器日志: docker logs -f --tail=100 [CONTAINER ID]
14.容器中无法执行 vi 命令解决办法:
    1).apt-get update
    2).apt-get install -y vim
15.修改 Docker Root Dir 路径: ExecStart=/usr/bin/dockerd  --graph=/docker-root
```
