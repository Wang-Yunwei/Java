# Redis

---

## 安装(由于redis是用C语言开发的,安装之前需确认是否安装了gcc-c++环境)

- 在线安装gcc-c++

> yum install -y gcc

- 离线安装gcc-c++

```text
1.离线下载gcc-c++: https://mirrors.aliyun.com/centos/7/os/x86_64/Packages/
2.安装: rpm -Uvh *.rpm --nodeps --force
3.测试: gcc -v 或 g++ -v
```

- 安装redis

```text
1.下载redis安装包: wget http://download.redis.io/releases/redis-5.0.3.tar.gz
2.解压: tar -zxvf redis-5.0.3.tar.gz
3.进入redis解压目录下安装并指定安装目录: make install PREFIX=/usr/local/redis
4.从redis的解压目录中复制 redis.conf 到 redis 的安装目录:
  cp /usr/local/redis-5.0.3/redis.conf /usr/local/redis/bin/
5.如果需要远程连接需要修改 redis.conf 文件,修改如下信息
----------------------------------------------------------------------
# 127.0.0.1代表本地地址,访问redis服务只能通过本机的客户端连接,而无法通过远程连接
# 0.0.0.0接受所有来自于可用网络接口的连接
bind 127.0.0.1 改为 bind 0.0.0.0
# yes:保护模式,只允许本地链接 no:保护模式关闭
protected-mode yes 改为 protected-mode no
#daemonize这个是是否在后台启动不占用一个主程窗口
daemonize no 改为 yesdaemonize yes 
# yes代表开启守护进程模式,此时是单进程多线程的模式,redis将在后台运行
# 并将pid写入redis.conf–pidfile文件中,此时redis将一直运行除非手动kill
# no当前界面将进入redis的命令行界面,exit强制退出或者关闭,连接工具(xshell等)都会导致redis进程退出
#添加密码
# requirepass foobared 改为 # requirepass mdsd
----------------------------------------------------------------------
6.启动服务: ./redis-server redis.conf
7.进入redis命令界面: ./redis-cli
[错误]
    (error) NOAUTH Authentication required说明需要验证
[解决]
    127.0.0.1:6379> auth "wyw_redis@1322"
[注]
    127.0.0.1:6379> select [db_num]        #切换到指定 db
    127.0.0.1:6379> dbsize                 #查看当前 db 大小
    127.0.0.1:6379> set [key_name] [value] #向redis中插入数据
    127.0.0.1:6379> keys *                 #列出所有的 key
    127.0.0.1:6379> get [key_name]         #获取指定的key值
    127.0.0.1:6379> del key [key_name]     #删除 key
    127.0.0.1:6379> flushdb                #只清除当前 db 数据
    127.0.0.1:6379> flushall               #所有db数据全部清除
8.创建redis.service文件: touch /lib/systemd/system/redis.service
9.编辑redis.service文件: vim /lib/systemd/system/redis.service ,添加如下信息
--------------------------
[Unit]
Description=nginx service
After=network.target
[Service]
Type=forking
ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf
PrivateTmp=true
[Install]
WantedBy=multi-user.targe
--------------------------
10.重载配置文件: systemctl daemon-reload
11.启动redis: systemctl start redis.service
12.设置开机启动: systemctl enable redis.service
```