# CentOS 7

---

## Linux 目录详解

```text
/bin        #存放二进制可执行文件'ls','cat','mkdir'等,常用命令一般都在这里
/boot       #存放用于系统引导时使用的各种文件
/dev        #用于存放设备文件
/etc        #存放系统管理和配置文件
/home       #存放所有用户文件的根目录
/lib        #存放文件系统中程序运行所需要的共享库以及内核模块
/lib64      #
/lost+found #这个目录平时是空的,系统非正常关机而留下“无家可归”的文件就在这里
/media      #
/mnt        #系统管理员安装临时文件系统的安装点,系统提供这个目录是让用户临时挂载其他的文件系统
/opt        #额外安装的可选应用程序包所放置的位置,一般情况下,我们可以把jdk,mysql,tomcat等             都安装到这里
/proc       #虚拟文件系统目录,是系统内存的映射,可直接访问这个目录来获取系统信息
/root       #超级用户的主目录(系统管理员)
/run        #
/sbin       #存放二进制可执行文件,只有root才能访问
/srv        #
/sys        #
/tmp        #存放各种临时文件,是公用的临时文件存储点
/usr        #用于存放系统应用程序,比较重要的目录/usr/local本地系统管理员软件安装目录(安装系            统级的应用)
    /usr/x11r6 存放x window的目录
    /usr/bin 众多的应用程序
    /usr/sbin 超级用户的一些管理程序
    /usr/doc Linux文档
    /usr/include Linux下开发和编译应用程序所需要的头文件
    /usr/lib 常用的动态链接库和软件包的配置文件
    /usr/man 帮助文档
    /usr/src 源代码,linux内核的源代码就在/usr/src/linux里
    /usr/local/bin 本地增加的命令
    /usr/local/lib 本地增加的库
/var        #存放系统运行时需要改变数据的文件,也是某些大文件的溢出区
```

## 设置网卡IP

- 编辑有线网卡文件: vim /etc/sysconfig/network-scripts/ifcfg-eth0 ,修改并添加如下信息

```text
BOOTPROTO=dhcp        #将 dhcp 改为 none
ONBOOT=no             #将 no 改为 yes

IPADDR=192.168.2.5    #设置 IP 地址
PREFIX=24             #设置子网掩码
GATEWAY=192.168.2.254 #设置默认网关
DNS1=192.168.0.105    #设置DNS
```

- 重启网卡

> service network restart

## SSH设置

- 检查sshd服务是否已经启动

> ps -e|grep sshd

- 查看sshd服务是否开机自启动

> systemctl list-unit-files|grep sshd

- 如果ssh不可用修改配置文件: vi /etc/ssh/sshd_config ,修改如下信息

```text
Port 22
ListenAddress 0.0.0.0
ListenAddress ::
PermitRootLogin yes
将上面字段前面的#去掉
```

- 启动sshd服务

> service sshd start

- 将sshd服务添加到自启动列表中

> systemctl enable sshd.service

- SSH超时设置

```text
1.进入profile: vi/etc/profile
2.添加如下信息:
-----------------
export TMOUT=3600
-----------------
3.时配置生效: source /etc/profile
4.SSH保活配置: vi /etc/ssh/sshd_config
5.修改如下信息:
-----------------------------------------------------------
#Server每隔1800秒=30分钟,向client发送一次请求
ClientAliveInterval 1800
#Client超过3次无响应就断开连接
ClientAliveCountMax 3
-----------------------------------------------------------
```

## 图形界面

- 安装
  > yum -y groups install "GNOME Desktop"
- 进入
  > startx

## 设置开机后进入界面

- 命令界面
  > systemctl set-default multi-user.target

- 图形界面(图形界面会占用大量资源)
  > systemctl set-default graphical.target

- 停止图形界面

  > systemctl stop gdm.service

- 禁止图形界面开机启动
  > systemctl disable gdm.service

## 清理缓存

- 查看当前缓存剩余

> free -h

- 写缓存到文件系统

> sync

- 释放内存

> echo 1 > /proc/sys/vm/drop_caches

- 如果想让操作系统重新分配内存,那么设置drop_caches的值为0即可

> echo 0 > /proc/sys/vm/drop_caches

## 服务器关机&重启

- shutdown 命令

```text
1.立即关闭机器: shutdown -h now
2.立即停止机器: shutdown -p now
3.立即重启机器: shutdown -r now
4.设置计算机在10分钟后关机,并且将信息显示在屏幕中:
  > shutdown -h 10 ‘This service will shutdown after 10 mins’
5.指定计算机在今天20:25关机: shutdown -h 20:25
6.10分钟后重启计算机: shutdown -r 10
7.取消即将执行的关机: shutdown -c
```

- init 命令

```text
1.关闭计算机: init 0
2.重启计算机: init 6
```

- reboot 命令

```text
1.关闭计算机: reboot -p
2.停止计算机: reboot -halt
3.重启计算机: reboot
```

- poweroff 命令

```text
1.关闭计算机: poweroff
2.停止计算机: poweroff --halt
3.重启计算机: poweroff --reboot
```

- halt 命令

```text
1.关闭计算机: halt -p
2.停止计算机: halt
3.重启计算机: halt --reboot
```

## 查看系统信息

- 查看内核信息

> uname -a 或 uname -r 或 hostnamectl

- 查看正在运行的内核版本

> cat /proc/version 或 cat/etc/centos-release

- 查看系统位数

> getconf LONG_BIT

- 查询IP信息

> ifconfig 或 ip addr 或 ip a

- 查看公网IP

> curl cip.cc

- 查看运行内存

> free -h

- 查看存储内存

> df -h

- 查看资源使用率

> top

- 查看正在使用的端口

> netstat -tunlp

```text
[注解]
-t 显示 TCP 端口
-u 显示 UDP 端口
-n 显示数字地址而不是主机名
-l 仅显示侦听端口
-p 显示进程的 PID 和名称
```

- 查询指定端口通过grep过滤

> netstat -tunlp | grep :80

- 查看某进程正在占用的端口

> netstat -nap | grep 进程号

- 查看当前路径

> pwd

- 强制杀掉进程

> kill 9 进程号

- 等带进程中所有任务执行完毕后关闭进程

> kill -15 进程号

- 查看磁盘类型(返回0:SSD盘,固态硬盘;返回1:SATA盘,使用SATA口的机械硬盘)

> cat /sys/block/sda/queue/rotational

## 防火墙

- 临时关闭

> systemctl stop firewalld

- 禁止开机启动防火墙

> systemctl disable firewalld

- 开启防火墙

> systemctl start firewalld

- 删除防火墙

> systemctl mask firewalld.service

## 安装iptables防火墙

```text
1.安装: yum -y install iptables-services
2.启动防火墙: systemctl start iptables
3.编辑防火墙添加端口: vim /etc/sysconfig/iptables
4.在相关位置(-A INPUT -p tcp -m state --state NEW -m tcp --dport 22 -j ACCEPT)后面,添加如下信息:
-------------------------------------------------------------------
# 默认端口
-A INPUT -m state --state NEW -m tcp -p tcp --dport 80 -j ACCEPT
# HTTPS协议端口
-A INPUT -m state --state NEW -m tcp -p tcp --dport 443 -j ACCEPT
# MySQL数据库端口
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
# Redis数据库端口
-A INPUT -m state --state NEW -m tcp -p tcp --dport 6379 -j ACCEPT
-------------------------------------------------------------------
5.重启防火墙使配置生效: systemctl restart iptables.service
6.设置防火墙开机启动: systemctl enable iptables.service
```

## 磁盘挂载

```text
1.查看所有挂载点: fdisk -l
2.格式化需要挂载的磁盘: mkfs.ext3 /dev/vdb
3.为磁盘创建一个主分区: 
    > fdisk /dev/vdb
    > p
    > n
    > 1
    > 回车(输入指定大小的空间或者直接回车,直接回车就是整个磁盘都给了主分区)
    > w
--------------------------------
[注解]
p #表示主分区: 一个物理磁盘的主分区至少1个最多4个,主分区相当于windows系统的活动磁盘,系统内核和开机程序必须放在主分区
e #表示扩展分区: 扩展分区最少0个最多1个,扩展分区相当于windows系统的非活动分区,扩展分区通常用于存放文件和安装非系统依赖程序
n #创建新分区
w #保存分区
d #删除分区
--------------------------------
4.格式化分区: mkfs.ext3 /dev/vdb1
5.在根目录创建挂载点并挂载:
    > mkdir /app
    > mount /dev/vdb /app
6.修改fstab,以便系统启动时自动挂载磁盘,编辑fstab默认启动文件: vi /etc/fstab 添加如下信息
--------------------------------
/dev/vdb /app ext3 defaults 0 0
--------------------------------
[或]
    echo "/dev/vdb /app ext3 defaults 0 0" >> /etc/fstab
7.查看磁盘树形目录: lsblk
8.写入到/etc/fstab文件中的设备信息并不会立即生效，需要使用 mount -a 参数进行自动挂载
```

- Linux访问Windows共享文件(使用mount挂载)

> mount -t cifs -o username=xxx,password=xxx //10.1.31.18/服务软件 ./windows

```text
[注解]
命令格式: mount [-t 文件系统] [-L 卷标名] [-o 特殊选项] 设备文件名 挂载点
-l  查询系统中已经挂载的设备,-l 会显示卷标
-a  依据配置文件/etc/fstab的内容,自动挂载
-t  系统文件,加入系统类型指定挂载类型,可以Ext3、Ext4、XFS、SWAP、iso9660(此为光盘设备)等系统文件(建议用ext4)
-L  卷标名: 挂载指定分区,而不是设备文件名挂载
-o  特殊选择: 可以指定挂载额外选项
    atime/noatime #更新访问时间/不更新访问时间,访问分区时,是否更新文件的访问时间,默认更新
    async/sync    #异步/同步,默认异步
    auto/noauto   #自动/手动,mount -a命令执行时,是否会自动安装/etc/fstab 文件内容挂载,默              认自动
    defaults      #定义默认值,相当于rw、suid、dev、exec、auto、nouser、async这七个选择
    exec/noexec   #执行/不执行,设定师傅允许文件系统中执行可执行文件,默认是exec允许
    remount       #重新挂载已经挂载的文件系统,一般用于修改特殊权限
    rw/ro         #读写/只读,文件系统挂载时,是否有读写的权限,默认rw
    suid/nosuid   #具有/不具有SUID权限,设定文件系统是否具有SUID权限,默认具有
    user/nouser   #允许/不允许普通用户挂载,设定文件系统是否允许普通用户挂载,默认不允许,只有               root可以挂载分区
    usrquata      #写入代表文件系统支持用户磁盘配额,默认不支持
    grpquata      #写入代表文件系统支持组磁盘配额,默认不允许
eg： mount -o  remount,noexec /home   让 /home目录下不能执行可执行文件
```

## 重新分配挂载点

```text
1.备份home目录: tar -czvf /home/home.tgz -C /app
2.卸载home: umount /dev/mapper/centos-home
[问题]
    如果显示/home正忙,且不在/home目录下,说明/home下有进程正在使用
[解决]
    杀死/home下的进程,再解除挂载点: fuser -m -v -i -k /home
[注]
    fuser在centos7里面不是默认安装的: yum install psmisc
3.删除home所在的lv: lvremove /dev/mapper/centos-home
4.扩展root所在的lv增加200G: lvextend -L +200G /dev/mapper/centos-root
[注]
    如果想把剩余所有lv分配给root,把+200G换成 -l 100%free
5.扩展root文件系统: xfs_growfs /dev/mapper/centos-root
6.查看剩余未分配lv: vgdisplay
7.重新创建分区,分区的大小根据vgdisplay中的free PE的大小确定home lv: lvcreate -L 300G -n home centos
8.创建文件系统: mkfs.xfs /dev/centos/home
9.挂载home: mount /dev/centos/home /home
10.恢复备份: tar -xzvf /root/home.tgz -C /home
```

## 用户管理

- 查看所有用户组

> cat /etc/group

- 创建用户组

> groupadd wyw

- 创建指定gid用户组

> groupadd -g 1000 wyw

- 删除用户组

> groupdel wyw

- 查看所有用户

> cat /etc/passwd

- 查看可以登录系统的用户

> cat /etc/passwd | grep -v /sbin/nologin | cut -d : -f 1

- 创建用户

> adduser wyw

- 设置用户秘密

> passwd wyw

- 创建用户并添加root权限不需要配合sudo命令(注: 添加权限就是将用户添加到用户组)

```text
1.将wyw添加到root组: usermod -g root wyw
2.创建用户wyw(wyw已经有主要用户组),并添加到附加组root组: useradd -G root wyw
3.创建用户wyw并添加到多个附加组: useradd -G root,admin,docker wyw
4.创建uid为1000的用户wyw并加入wyw组: useradd -u 1000 wyw wyw
5.将wyw从组中删除(删除的组不可以是主要组): gpasswd -d wyw group
6.添加root权限(不需要使用sudo命令即可使用root权限): vim /etc/passwd
```

- 添加用户权限需配合sudo命令才可使用root权限

```text
1.切换到wyw用户: su wyw
2.查找sudoers文件: whereis sudoers
3.查看文件权限: ls -l /etc/sudoers
4.添加可写的权限: chmod -v u+w /etc/sudoers
5.删除可写的权限: chmod -v u-w /etc/sudoers
6.把wyw用户添加到sudoers中使wyw用户有root权限(需配合sudo命令才可使用root权限) ,添加如下信息
-----------------
wyw ALL=(ALL) ALL
-----------------
```

- 删除wyw用户但不删除/home/wyw目录

> userdel wyw

- 删除wyw用户同时删除/home/wyw目录

> userdel -r wyw

## 文件操作

- 建立软链接

> ln -s 源文件或目录 软链接名

- 删除软连接

> rm -rf ./软链接名

- 修改软链接

> ln -snf 新的源文件或目录 软链接名

- 查找文件

> find / -name start.log

- 压缩成 .tar 格式文件

> tar -cvf file.tar file/dir

- 解压 .tar 格式文件

> tar -xvf xxx.tar <file|dir>

- 压缩成 .tar.gz 格式的文件

> tar -zcvf xxx.tar.gz <file|dir>

- 解压.tar.gz 格式文件到指定目录

> tar -zxvf xxx.tar.gz -C /app

- 压缩为.zip格式的文件

> zip -r xxx.zip <file|dir>

- 解压 zip 格式文件到指定目录

> unzip xxx.zip -d /app

- 创建文件

> touch a.txt 或 > a.txt

- 创建两个文件夹

> mkdir dir1 dir2

- 创建多级目录

> mkdir -p /app/dir

- 删除文件

> rm -rf [file/dir]

- 复制文件

> cp file file1

- 复制到指定目录

> cp -a [file/dir] dir

- 移动文件到指定目录

> mv [file/dir] dir

- 本地复制文件到远程服务器

> scp ./file root@192.168.0.96:/app/

- 远程服务器复制文件到本地

> scp root@192.168.0.96:/app/file /app/

```text
[注解]
命令格式: scp [参数] [原路径] [目标路径]
-1  强制scp命令使用协议ssh1
-2  强制scp命令使用协议ssh1
-4  强制scp命令只是用IPv4寻址
-6  强制scp命令只是用IPv6寻址
-B  使用批处理模式(传输过程中不询问传输口令或短语)
-C  允许压缩
-p  保留源文件的修改时间、访问时间和访问权限
```

- 编辑文本

```text
:w file  将修改另外保存到file中,不退出vi
:w!       强制保存,不推出vi
:wq      保存文件并退出vi
:wq!      强制保存文件,并退出vi
q:        不保存文件,退出vi
:q!       不保存文件,强制退出vi
:e!       放弃所有修改,从上次保存文件开始再编辑
```

## 安装工具

- 安装 wget

> yum -y install wget

- 安装阿里yum

```text
1.wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
2.yum clean all
3.yum makecache
4.yum repolist
5.yum update
```

- 使用yum下载rpm包但不安装

> yum install --downloadonly --downloaddir=/tmp <package-name>

- 使用专门下载rpm包工具yumdownloader

```text
1.安装: yum install yum-utils
2.使用: yumdownloader <package-name>
[注]
    下载的包会被保存在当前目录中,你需要使用root权限,因为yumdownloader会在下载过程中更新包索引文件,与yum命令不同的是,任何依赖包不会被下载,可以通过添加参数--destdir来设置下载路径，使用--resolve参数将所依赖的也下载:
    yumdownloader lsof --resolve --destdir=/data/mydepot/
```

- 安装 rpm 包

> rpm -ivh <package-name>

## 救援模式

```text
1.进入boot启动选择界面,虚拟机在系统进度条加载的那一小段时间内按下ESC键
2.选择挂载的镜像设备: CD-ROM Drive
3.选择完成后进入安装界面,选择 Troubleshooting
4.进入后选择 Rescue a CentOS system (使用光盘制作一个小型的系统)
5.跳出 4 个选项,选择 1 继续
6.此时生成的系统是一个独立的系统,原系统挂载到 /mnt/sysimage 目录下
7.经过确认,在原系统中 libc.so.6 文件的确已经缺失,现在将光盘中的文件复制过去: cp /lib64/libc.so.6 /mnt/sysimage/lib64
8.退出救援模式,重启系统: exit
```

## 创建Swap分区空间

```text
1.使用dd命令创建名为swapfile 的swap交换文件(文件名和目录任意)
  > dd if=/dev/zero of=/home/swap bs=1M count=2048
[解释]
    1).if(即输入文件,input file),of(即输出文件,output file)
    2).dev/zero是Linux的一种特殊字符设备(输入设备),可以用来创建一个指定长度用于初始化的空文件,如临时交换文件,该设备无穷尽地提供0,可以提供任何你需要的数目, bs=1M代表增加的模块大小,count=2048代表是2048个模块,也就是2G空间大小
    3).count的计算公式: count = size * bs (size以MB为单位)
2.对交换文件格式化并转换为swap分区: mkswap /home/swap
3.挂载并激活分区: swapon /home/swap
[注]
    执行以上命令可能会出现: “不安全的权限 0644,建议使用 0600”类似提示,不要紧张,实际上已经激活了,可以忽略提示,也可以听从系统的建议修改下权限: chmod -R 0600 /home/swap
4.如果需要关闭swap分区则执行: swapoff /home/swap
5.修改fstab配置,设置开机自动挂载该分区: vim  /etc/fstab 添加如下配置
--------------------------------
/home/swap swap swap default 0 0
--------------------------------
[或]
    直接对fstab进行echo追加:
      > echo  "/home/swap   swap  swap  defaults  0  0" >>  /etc/fstab 
6. 


```