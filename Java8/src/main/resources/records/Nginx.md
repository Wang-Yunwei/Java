# Nginx

---

## 安装(Linux环境)

- 在线安装

```text
1.安装nginx所需环境依赖: yum install gcc-c++ pcre pcre-devel zlib zlib-devel openssl openssl-devel
2.下载源码包: wget https://nginx.org/download/nginx-1.22.0.tar.gz
3.解压: tar -zxvf nginx-1.22.0.tar.gz
4.编译前指定安装目录: ./configure --prefix=/opt/nginx
5.编译并安装: make && make install
```

- 离线安装依赖包

```text
1.一键下载依赖包: yum -y install gcc zlib zlib-devel pcre-devel openssl openssl-devel
3.通过rpm命令一键安装所有依赖包: rpm -ivh --nodeps *.rpm
```

## 操作命令

- 检查配置文件是否正常

> ./nginx -t -c conf/nginx.conf

- 启动

> ./nginx

- 修改配置后平滑重启

> ./nginx -s reload -c conf/nginx.conf

- 停止

> ./nginx -s stop

- 优雅停止(会在执行完当前的任务后再退出)

> ./nginx -s quit

## 配置系统命令启停Nginx

```text
1.创建nginx.service文件: touch nginx.service
2.添加如下信息:
---------------------------------
[Unit]
Description=nginx
After=network.target

[Service]
# 后台运行的形式
Type=forking
ExecStart=/usr/local/nginx/sbin/nginx
ExecReload=/usr/local/nginx/sbin/nginx -s reload
ExecStop=/usr/local/nginx/sbin/nginx -s stop
ExecQuit=/usr/local/nginx/sbin/nginx -s quit
#Stop quit是一个优雅的关闭方式,Nginx在退出前完成已经接受的连接请求
PrivateTmp=true

[Install]
WantedBy=multi-user.target
---------------------------------
3.停止nginx服务: ./nginx -s stop
4.重载配置文件: systemctl daemon-reload
5.启动: systemctl start nginx
6.设置开机启动: systemctl enable nginx.service
```

## Nginx 配置

- 跨域配置

```text
location / {
    # 允许跨域的请求,可以自定义变量$http_origin, *表示所有
    add_header 'Access-Control-Allow-Origin' *;
    # 允许携带cookie请求
    add_header 'Access-Control-Allow-Credentials' 'true';
    # 允许跨域请求的方法: GET,POST,OPTIONS,PUT
    add_header 'Access-Control-Allow-Methods' 'GET,POST,OPTIONS,PUT';
    # 允许请求时携带的头部信息, *表示所有
    add_header 'Access-Control-Allow-Headers' *;
    # 允许发送按段获取资源的请求
    add_header 'Access-Control-Expose-Headers' 'Content-Length,Content-Range';
    # 在发送Post跨域请求前,会以Options方式发送预检请求,服务器接受时才会正式请求(一定要有否则post请求无法进行跨域)
    if ($request_method = 'OPTIONS') {
        add_header 'Access-Control-Max-Age' 1728000;
        add_header 'Content-Type' 'text/plain; charset=utf-8';
        add_header 'Content-Length' 0;
        # 对于Options方式的请求返回204,表示接受跨域请求
        return 204;
    }
}

    产生跨域问题的主要原因就在于同源策略,为了保证用户信息安全,防止恶意网站窃取数据,同源策略是必须的
否则cookie可以共享,由于http无状态协议通常会借助cookie来实现有状态的信息记录,例如用户的身份/密码等
因此一旦cookie被共享，那么会导致用户的身份信息被盗取
    同源策略主要是指三点相同,协议+域名+端口 相同的两个请求,则可以被看做是同源的,但如果其中任意一点
存在不同,则代表是两个不同源的请求,同源策略会限制了不同源之间的资源交互
```

- 大文件传输配置

```text
    在某些业务场景中需要传输一些大文件,但大文件传输时往往都会会出现一些Bug,比如文件超出限制、文件传输过程中请求
超时等,那么此时就可以在nginx稍微做一些配置,关于大文件传输时可能会用的配置项:
```

| 配置项                   | 释义                        |
|-----------------------|---------------------------|
| client_max_body_size  | 设置请求体允许的最大体积              |
| client_header_timeout | 等待客户端发送一个请求头的超时时间         |
| client_body_timeout   | 设置读取请求体的超时时间              |
| proxy_read_timeout    | 设置请求被后端服务器读取时nginx等待的最长时间 |
| proxy_send_timeout    | 设置后端向nginx返回响应时的超时时间      |

```text
http{
    #nginx优化----------------------
    #隐藏版本号
    server_tokens on;
    
    #开启高效文件传输模式
    sendfile on;
    
    #减少网络报文段数量
    #tcp_nopush on;
    
    #提高I/O性能
    tcp_nodelay on;
    
    #连接超时 时间定义 默认秒 默认65秒
    keepalive_timeout 60;
    
    #读取客户端请求头数据的超时时间 默认秒 默认60秒
    client_header_timeout 15;
    
    #读取客户端请求主体的超时时间 默认秒 默认60秒
    client_body_timeout 15;
    
    #响应客户端的超时时间 默认秒 默认60秒
    send_timeout 25;
 
    #上传文件的大小限制  默认1m
    client_max_body_size 10m;
}

注:
    上述配置仅是作为代理层需要配置的,因为最终客户端传输文件还是直接与后端进行交互,这里只是把作为网关层的Nginx配置
调高一点,调到能够“容纳大文件”传输的程度,当然,nginx中也可以作为文件服务器使用,但需要用到一个专门的第三方模块
nginx-upload-module,如果项目中文件上传的作用处不多,那么建议可以通过nginx搭建,毕竟可以节省一台文件服务器资源,但
如若文件上传/下载较为频繁,那么还是建议额外搭建文件服务器,并将上传/下载功能交由后端处理;
```

- 反向代理

```text
[例如]
#院前急救-APP
server {
    listen       20220;
    server_name  localhost;

    location / {
        root   html/phfa_app/dist;
        index  index.html index.htm;
    }
    location ~ /phfa/ {
        if ($request_method = 'OPTIONS') {
            add_header 'Access-Control-Max-Age' 1800;
            add_header 'Content-Type' 'text/plain; charset=utf-8';
            add_header 'Content-Length' 0;
            return 204;
        }
        proxy_pass http://192.168.0.96:20220;
    }
}
-----------------------------------
[解释]
    Accept
      |- Accept: text/html  浏览器可以接受服务器回发的类型为 text/html
      |- Accept: */*   代表浏览器可以处理所有类型,(一般浏览器发给服务器都是发这个)
    Accept-Encoding
      |- Accept-Encoding: gzip, deflate 浏览器申明自己接收的编码方法，通常指定压缩方法，是否支持压缩，支持什么压缩方法（gzip，deflate）
    Accept-Language
      |- Accept-Language: zh-CN,zh;q=0.9 浏览器申明自己接收的语言    
    Connection
      |- Connection: keep-alive  当一个网页打开完成后，客户端和服务器之间用于传输HTTP数据的TCP连接不会关闭，如果客户端再次访问这个服务器上的网页，会继续使用这一条已经建立的连接
      |- Connection: close 代表一个Request完成后，客户端和服务器之间用于传输HTTP数据的TCP连接会关闭， 当客户端再次发送Request，需要重新建立TCP连接
    Host（发送请求时，该报头域是必需的）
      |- Host:www.baidu.com 请求报头域主要用于指定被请求资源的Internet主机和端口号，它通常从HTTP URL中提取出来的
    Referer
      |- Referer:https://www.baidu.com/?tn=62095104_8_oem_dg 当浏览器向web服务器发送请求的时候，一般会带上Referer，告诉服务器我是从哪个页面链接过来的，服务器籍此可以获得一些信息用于处理
    User-Agent
      |- User-Agent:Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36告诉HTTP服务器， 客户端使用的操作系统和浏览器的名称和版本
    Cache-Control
      |- Cache-Control:private 默认为private响应只能够作为私有的缓存,不能再用户间共享
      |- Cache-Control:public 响应会被缓存,并且在多用户间共享,正常情况如果要求HTTP认证,响应会自动设置为 private
      |- Cache-Control:must-revalidate  响应在特定条件下会被重用,以满足接下来的请求,但是它必须到服务器端去验证它是不是仍然是最新的
      |- Cache-Control:no-cache  响应不会被缓存,而是实时向服务器端请求资源
      |- Cache-Control:max-age=10 设置缓存最大的有效时间，但是这个参数定义的是时间大小（比如：60）而不是确定的时间点。单位是[秒 seconds]
      |- Cache-Control:no-store 在任何条件下，响应都不会被缓存，并且不会被写入到客户端的磁盘里，这也是基于安全考虑的某些敏感的响应才会使用这个
    Cookie
      |- Cookie是用来存储一些用户信息以便让服务器辨别用户身份的 (大多数需要登录的网站上面会比较常见) 比如cookie会存储一些用户的用户名和密码,当用户登录后就会在客户端产生一个cookie来存储相关信息,这样浏览器通过读取cookie的信息去服务器上验证并通过后会判定你是合法用户,从而允许查看相应网页
    Range(用于断点续传)
      |- Range:bytes=0-5 指定第一个字节的位置和最后一个字节的位置,用于告诉服务器自己想取对象的哪部分
[响应]
    Content-Type
      |- Content-Type: text/html;charset=UTF-8 告诉客户端，资源文件的类型，还有字符编码，客户端通过utf-8对资源进行解码，然后对资源进行html解析。通常我们会看到有些网站是乱码的，往往就是服务器端没有返回正确的编码
    Content-Type
      |- Content-Type: text/html;charset=UTF-8 告诉客户端，资源文件的类型，还有字符编码，客户端通过utf-8对资源进行解码，然后对资源进行html解析。通常我们会看到有些网站是乱码的，往往就是服务器端没有返回正确的编码
    Transfer-Encoding
      |- Transfer-Encoding: chunked这个响应头告诉客户端，服务器发送的资源的方式是分块发送的。一般分块发送的资源都是服务器动态生成的，在发送时还不知道发送资源的大小，所以采用分块发送，每一块都是独立的，独立的块都能标示自己的长度，最后一块是0长度的，当客户端读到这个0长度的块时，就可以确定资源已经传输完了
    Access-Control-Max-Age
      |- Access-Control-Max-Age: 1800 用来指定本次预检请求的有效期,单位为秒 
    Access-Control-Allow-Origin
      |- Access-Control-Allow-Origin: *   *号代表所有网站可以跨域资源共享，如果当前字段为*那么Access-Control-Allow-Credentials就不能为true
      |- Access-Control-Allow-Origin: www.baidu.com 指定哪些网站可以跨域资源共享
    Access-Control-Allow-Credentials
      |- Access-Control-Allow-Credentials: true 是否允许发送cookie。默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。如果access-control-allow-origin为*，当前字段就不能为true
    Access-Control-Allow-Methods
      |- Access-Control-Allow-Methods：GET,POST,PUT,DELETE  允许哪些方法来访问
```

- 负载均衡

```text
upstream nginx_boot{
    # 30s内检查心跳发送两次包,未回复就代表该机器宕机, (weight)请求分发权重比为1:2
    server 192.168.0.000:8080 weight=1 max_fails=2 fail_timeout=30s; 
    server 192.168.0.000:8090 weight=2 max_fails=2 fail_timeout=30s;
}

server {
    location / {
        root   html;
        # 配置一下index的地址，最后加上index.ftl。
        index  index.html index.htm index.jsp index.ftl;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        # 请求交给名为nginx_boot的upstream上
        proxy_pass http://nginx_boot;
    }
}

```

- 轮询策略(默认)

```text
upstream test {
    server 0.0.0.0:9000;
    server 0.0.0.0:9001; 
    server 0.0.0.0:9002;
}

server {
    listen       8081;
    server_name  localhost;
    location / {
        proxy_pass http://test;
    }
}
```

- 加权轮询策略

```text
#9000 端口处理50%的请求,9001 端口处理25%的请求,9002 端口处理25%的请求
upstream test {
    server 0.0.0.0:9000 weight=2;
	server 0.0.0.0:9001 weight=1; 
	server 0.0.0.0:9002 weight=1;
}

server {
    listen       8081;
    server_name  localhost;
    location / {
        proxy_pass http://test;
    }
}
```

- ip_hash 策略

```text
#根据 ip 来分配请求,固定的客户端发出的请求会被固定分配到一台服务器
upstream test {
    ip_hash;
    server 0.0.0.0:9000;
    server 0.0.0.0:9001; 
    server 0.0.0.0:9002;
}

server {
    listen       8081;
    server_name  localhost;
    location / {
        proxy_pass http://test;
    }
}
```

- ip_hash + 加权轮询策略 + 心跳检测

```text
upstream test1 {
    ip_hash;
    server 0.0.0.0:9000 weight=2 max_fails=3 fail_timeout=30s;
    server 0.0.0.0:9001 weight=1 max_fails=3 fail_timeout=30s; 
    server 0.0.0.0:9002 weight=2 max_fails=3 fail_timeout=30s;
}
    
upstream test2 {
    ip_hash;
    server 0.0.0.0:9010 weight=1 max_fails=3 fail_timeout=30s;
    server 0.0.0.0:9011 weight=2 max_fails=3 fail_timeout=30s; 
    server 0.0.0.0:9012 weight=1 max_fails=3 fail_timeout=30s;
}
    
server {
    listen       8081;
    server_name  localhost;
    location / {
        proxy_pass http://test;
    }
}
server {
    listen       8082;
    server_name  localhost;
    location / {
        proxy_pass http://test2;
    }
```

- 配置SLL证书

```text
SSL证书配置过程:
1. 先去CA机构或从云控制台中申请对应的SSL证书,审核通过后下载Nginx版本的证书;
2. 下载数字证书后,完整的文件总共有三个: .crt、.key、.pem：
    - .crt: 数字证书文件, .crt是.pem的拓展文件,因此有些人下载后可能没有; 
    - .key: 服务器的私钥文件;及非对称加密的私钥;用于解密公钥传输的数据;
    - .pem: Base64-encoded编码格式的源证书文本文件;可自行根需求修改拓展名;

3. 在nginx目录下新建certificate目录,并将下载好的证书/私钥等文件上传至该目录;
4. 修改nginx.conf文件:
# ----------HTTPS配置-----------
server {
    # 监听HTTPS默认的443端口
    listen 443;
    # 配置自己项目的域名
    server_name www.xxx.com;
    # 打开SSL加密传输
    ssl on;
    # 输入域名后，首页文件所在的目录
    root html;
    # 配置首页的文件名
    index index.html index.htm index.jsp index.ftl;
    # 配置自己下载的数字证书
    ssl_certificate  certificate/xxx.pem;
    # 配置自己下载的服务器私钥
    ssl_certificate_key certificate/xxx.key;
    # 停止通信时，加密会话的有效期，在该时间段内不需要重新交换密钥
    ssl_session_timeout 5m;
    # TLS握手时，服务器采用的密码套件
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    # 服务器支持的TLS版本
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    # 开启由服务器决定采用的密码套件
    ssl_prefer_server_ciphers on;

    location / {
        root html;
        index  index.html index.htm;
    }
}

# ---------HTTP请求转HTTPS-------------
server {
    # 监听HTTP默认的80端口
    listen 80;
    # 如果80端口出现访问该域名的请求
    server_name www.xxx.com;
    # 将请求改写为HTTPS（这里写你配置了HTTPS的域名）
    rewrite ^(.*)$ https://www.xxx.com;
}
```

- IP黑白名单

```text
nginx做黑白名单机制,主要是通过allow、deny配置项来实现:
    allow xxx.xxx.xxx.xxx; # 允许指定的IP访问,可以用于实现白名单;
    deny xxx.xxx.xxx.xxx; # 禁止指定的IP访问,可以用于实现黑名单;

# --------黑名单：BlocksIP.conf---------
deny 192.177.12.222; # 屏蔽192.177.12.222访问
deny 192.177.44.201; # 屏蔽192.177.44.201访问
deny 127.0.0.0/8; # 屏蔽127.0.0.1到127.255.255.254网段中的所有IP访问

# --------白名单：WhiteIP.conf---------
allow 192.177.12.222; # 允许192.177.12.222访问
allow 192.177.44.201; # 允许192.177.44.201访问
allow 127.45.0.0/16; # 允许127.45.0.1到127.45.255.254网段中的所有IP访问
deny all; # 除开上述IP外，其他IP全部禁止访问

http{
    # 屏蔽该文件中的所有IP
    include /soft/nginx/IP/BlocksIP.conf; 
    server{
        location xxx {
            # 某一系列接口只开放给白名单中的IP
            include /soft/nginx/IP/blockip.conf; 
        }
    }
}

注:
    对于文件具体在哪儿导入,这个也并非随意的,如果要整站屏蔽/开放就在http中导入,如果只需要一个域名下屏蔽/开放
就在sever中导入,如果只需要针对于某一系列接口屏蔽/开放IP,那么就在location中导入;
    以上只是最简单的IP黑/白名单实现方式,同时也可以通过ngx_http_geo_module、ngx_http_geo_module第三方库
去实现(这种方式可以按地区、国家进行屏蔽,并且提供了IP库);
```

- 动静分离

```text
location ~ .*\.(html|htm|gif|jpg|jpeg|bmp|png|ico|txt|js|css){
    root   /soft/nginx/static_resources;
    expires 7d;
}

#location规则：
location ~ .*\.(html|htm|gif|jpg|jpeg|bmp|png|ico|txt|js|css)
- ~代表匹配时区分大小写
- .*代表任意字符都可以出现零次或多次,即资源名不限制
- \.代表匹配后缀分隔符.
- (html|...|css)代表匹配括号里所有静态资源类型
综上所述,简单一句话概述: 该配置表示匹配以.html~.css为后缀的所有资源请求
```

- ₪资源压缩

```text
http{
    # 开启压缩机制
    gzip on;
    # 指定会被压缩的文件类型(也可自己配置其他类型)
    gzip_types text/plain application/javascript text/css application/xml text/javascript image/jpeg image/gif image/png;
    # 设置压缩级别，越高资源消耗越大，但压缩效果越好
    gzip_comp_level 5;
    # 在头部中添加Vary: Accept-Encoding（建议开启）
    gzip_vary on;
    # 处理压缩请求的缓冲区数量和大小
    gzip_buffers 16 8k;
    # 对于不支持压缩功能的客户端请求不开启压缩机制
    gzip_disable "MSIE [1-6]\."; # 低版本的IE浏览器不支持压缩
    # 设置压缩响应所支持的HTTP最低版本
    gzip_http_version 1.1;
    # 设置触发压缩的最小阈值
    gzip_min_length 2k;
    # 关闭对后端服务器的响应结果进行压缩
    gzip_proxied off;
}
    建立在动静分离的基础之上,如果一个静态资源的Size越小,那么自然传输速度会更快,同时也会更节省带宽
因此我们在部署项时,也可以通过Nginx对于静态资源实现压缩传输,一方面可以节省带宽资源,第二方面也可以加
快响应速度并提升系统整体吞吐.
    在nginx也提供了三个支持资源压缩的模块ngx_http_gzip_module、ngx_http_gzip_static_module、
ngx_http_gunzip_module,其中ngx_http_gzip_module属于内置模块,代表着可以直接使用该模块下的一些
压缩指令，后续的资源压缩操作都基于该模块.

注: 对于图片、视频类型的数据,会默认开启压缩机制,因此一般无需再次开启压缩
    对于.js文件而言,需要指定压缩类型为application/javascript,而并非
    text/javascript、application/x-javascript

压缩配置的一些参数/指令：
```

| 参数项               | 释义                                | 参数值                     |
|-------------------|-----------------------------------|-------------------------|
| gzip              | 开启或关闭压缩机制                         | on/off                  |
| gzip_types        | 根据文件类型选择性开启压缩机制                   | image/png、text/css...   |
| gzip_comp_level   | 用于设置压缩级别，级别越高越耗时                  | 1~9(越高压缩效果越好)           |
| gzip_vary         | 设置是否携带Vary:Accept-Encoding头域的响应头部 | on/off                  |
| gzip_buffers      | 设置处理压缩请求的缓冲区数量和大小                 | 数量 大小，如16 8k            |
| gzip_disable      | 针对不同客户端的请求来设置是否开启压缩               | 如 .*Chrome.*            |
| gzip_http_version | 指定压缩响应所需要的最低HTTP请求版本              | 如1.1                    |
| gzip_min_length   | 设置触发压缩的文件最低大小                     | 如512k                   |
| gzip_proxied      | 对于后端服务器的响应结果是否开启压缩                | off、expired、no-cache... |

```text
gzip_proxied选项,可以根据系统的实际情况决定,总共存在多种选项:

- off: 关闭Nginx对后台服务器的响应结果进行压缩;
- expired: 如果响应头中包含Expires信息,则开启压缩;
- no-cache: 如果响应头中包含Cache-Control:no-cache信息,则开启压缩;
- no-store: 如果响应头中包含Cache-Control:no-store信息,则开启压缩;
- private: 如果响应头中包含Cache-Control:private信息,则开启压缩;
- no_last_modified: 如果响应头中不包含Last-Modified信息,则开启压缩;
- no_etag: 如果响应头中不包含ETag信息,则开启压缩;
- auth: 如果响应头中包含Authorization信息,则开启压缩;
- any: 无条件对后端的响应结果开启压缩机制;
```

- 缓冲区

```text
    接入Nginx的项目一般请求流程为: “客户端→nginx→服务端”,在这个过程中存在两个连接: “客户端→nginx、nginx→服务端”
那么两个不同的连接速度不一致,就会影响用户的体验(比如浏览器的加载速度跟不上服务端的响应速度),其实也就类似电脑的内存跟不
上CPU速度,所以对于用户造成的体验感极差,因此在CPU设计时都会加入三级高速缓冲区,用于缓解CPU和内存速率不一致的矛盾,在
nginx也同样存在缓冲区的机制,主要目的就在于,用来解决两个连接之间速度不匹配造成的问题,有了缓冲后,nginx代理可暂存后端的
响应,然后按需供给数据给客户端,关于缓冲区的配置项:

- proxy_buffering: 是否启用缓冲机制,默认为off关闭状态;
- client_body_buffer_size: 设置缓冲客户端请求数据的内存大小;
- proxy_buffers: 为每个请求/连接设置缓冲区的数量和大小,默认4 4k/8k;
- proxy_buffer_size: 设置用于存储响应头的缓冲区大小;
- proxy_busy_buffers_size: 在后端数据没有完全接收完成时,Nginx可以将busy状态的缓冲返回给客户端,该参数用来设置busy
状态的buffer具体有多大,默认为proxy_buffer_size*2;
- proxy_temp_path: 当内存缓冲区存满时,可以将数据临时存放到磁盘,该参数是设置存储缓冲数据的目录语法proxy_temp_path path, path是临时目录的路径;
- proxy_temp_file_write_size: 设置每次写数据到临时文件的大小限制;
- proxy_max_temp_file_size: 设置临时的缓冲目录中允许存储的最大容量;

非缓冲参数项:
- proxy_connect_timeout: 设置与后端服务器建立连接时的超时时间;
- proxy_read_timeout: 设置从后端服务器读取响应数据的超时时间;
- proxy_send_timeout: 设置向后端服务器传输请求数据的超时时间;

http{
    proxy_buffering on;
    client_body_buffer_size 512k;
    proxy_buffers 4 64k;
    proxy_buffer_size 16k;
    proxy_busy_buffers_size 128k;
    proxy_temp_path /soft/nginx/temp_buffer;
    proxy_temp_file_write_size 128k;
    proxy_connect_timeout 10;
    proxy_read_timeout 120;
    proxy_send_timeout 10;
}

注:
    缓冲区参数,是基于每个请求分配的空间,而并不是所有请求的共享空间,当然具体的参数值还需要根据业务去决定,要综合考虑机
器的内存以及每个请求的平均数据大小,使用缓冲也可以减少即时传输带来的带宽消耗;
```

- 缓存机制

```text
    对于性能优化而言,缓存是一种能够大幅度提升性能的方案,因此几乎可以在各处都能看见缓存,如客户端缓存、代理缓存、服务器
缓存等,Nginx的缓存则属于代理缓存的一种,对于整个系统而言,加入缓存带来的优势额外明显：
- 减少了再次向后端或文件服务器请求资源的带宽消耗;
- 降低了下游服务器的访问压力,提升系统整体吞吐;
- 缩短了响应时间,提升了加载速度,打开页面的速度更快;

http{
    # 设置缓存的目录，并且内存中缓存区名为hot_cache，大小为128m，
    # 三天未被访问过的缓存自动清楚，磁盘中缓存的最大容量为2GB。
    proxy_cache_path /soft/nginx/cache levels=1:2 keys_zone=hot_cache:128m inactive=3d max_size=2g;
    
    server{
        location / {
            # 使用名为nginx_cache的缓存空间
            proxy_cache hot_cache;
            # 对于200、206、304、301、302状态码的数据缓存1天
            proxy_cache_valid 200 206 304 301 302 1d;
            # 对于其他状态的数据缓存30分钟
            proxy_cache_valid any 30m;
            # 定义生成缓存键的规则（请求的url+参数作为key）
            proxy_cache_key $host$uri$is_args$args;
            # 资源至少被重复访问三次后再加入缓存
            proxy_cache_min_uses 3;
            # 出现重复请求时，只让一个去后端读数据，其他的从缓存中读取
            proxy_cache_lock on;
            # 上面的锁超时时间为3s，超过3s未获取数据，其他请求直接去后端
            proxy_cache_lock_timeout 3s;
            # 对于请求参数或cookie中声明了不缓存的数据，不再加入缓存
            proxy_no_cache $cookie_nocache $arg_nocache $arg_comment;
            # 在响应头中添加一个缓存是否命中的状态（便于调试）
            add_header Cache-status $upstream_cache_status;
        }
    }
}

缓存相关的配置项:
- proxy_cache_path: 代理缓存的路径
语法:proxy_cache_path path [levels=levels] [use_temp_path=on|off] keys_zone=name:size [inactive=time] [max_size=size] [manager_files=number] [manager_sleep=time] [manager_threshold=time] [loader_files=number] [loader_sleep=time] [loader_threshold=time] [purger=on|off] [purger_files=number] [purger_sleep=time] [purger_threshold=time];
每个参数项的含义:
|- path: 缓存的路径地址
|- levels: 缓存存储的层次结构,最多允许三层目录
|- use_temp_path: 是否使用临时目录
|- keys_zone: 指定一个共享内存空间来存储热点Key(1M可存储8000个Key)
|- inactive: 设置缓存多长时间未被访问后删除(默认是十分钟)
|- max_size: 允许缓存的最大存储空间,超出后会基于LRU算法移除缓存,Nginx会创建一个Cache manager的进程移除数据,也可
             以通过purge方式
|- manager_files: manager进程每次移除缓存文件数量的上限
|- manager_sleep: manager进程每次移除缓存文件的时间上限
|- manager_threshold: manager进程每次移除缓存后的间隔时间
|- loader_files: 重启Nginx载入缓存时,每次加载的个数,默认100
|- loader_sleep: 每次载入时,允许的最大时间上限,默认200ms
|- loader_threshold: 一次载入后,停顿的时间间隔,默认50ms
|- purger: 是否开启purge方式移除数据
|- purger_files: 每次移除缓存文件时的数量
|- purger_sleep: 每次移除时,允许消耗的最大时间
|- purger_threshold: 每次移除完成后,停顿的间隔时间

- proxy_cache: 开启或关闭代理缓存,开启时需要指定一个共享内存区域
语法: proxy_cache zone | off; zone为内存区域的名称,即上面中keys_zone设置的名称;

- proxy_cache_key: 定义如何生成缓存的键
语法: proxy_cache_key string; string为生成Key的规则,如$scheme$proxy_host$request_uri;

- proxy_cache_valid: 缓存生效的状态码与过期时间
语法: proxy_cache_valid [code ...] time; code为状态码,time为有效时间,可以根据状态码设置不同的缓存时间
例如: proxy_cache_valid 200 302 30m;

- proxy_cache_min_uses: 设置资源被请求多少次后被缓存
语法: proxy_cache_min_uses number; number为次数,默认为1;

- proxy_cache_use_stale: 当后端出现异常时,是否允许Nginx返回缓存作为响应
语法: proxy_cache_use_stale error;
error为错误类型,可配置timeout|invalid_header|updating|http_500...;

- proxy_cache_lock: 对于相同的请求,是否开启锁机制,只允许一个请求发往后端
语法: proxy_cache_lock on | off;

- proxy_cache_lock_timeout: 配置锁超时机制，超出规定时间后会释放请求
语法: proxy_cache_lock_timeout time;

- proxy_cache_methods: 设置对于那些HTTP方法开启缓存
语法: proxy_cache_methods method; method为请求方法类型,如GET、HEAD等;

- proxy_no_cache: 定义不存储缓存的条件，符合时不会保存
语法: proxy_no_cache string...; string为条件,例如$cookie_nocache $arg_nocache $arg_comment;

- proxy_cache_bypass: 定义不读取缓存的条件，符合时不会从缓存中读取
语法: proxy_cache_bypass string...; 和上面proxy_no_cache的配置方法类似;

- add_header: 往响应头中添加字段信息
语法: add_header fieldName fieldValue;
$upstream_cache_status,记录了缓存是否命中的信息,存在多种情况:
|- MISS: 请求未命中缓存;
|- HIT: 请求命中缓存;
|- EXPIRED: 请求命中缓存但缓存已过期;
|- STALE: 请求命中了陈旧缓存;
|- REVALIDDATED: Nginx验证陈旧缓存依然有效;
|- UPDATING: 命中的缓存内容陈旧,但正在更新缓存;
|- BYPASS: 响应结果是从原始服务器获取的;
```

- 缓存清理

```text
    当缓存过多时,如果不及时清理会导致磁盘空间被“吃光”,因此我们需要一套完善的缓存清理机制去删除缓存在之前
的proxy_cache_path参数中有purger相关的选项,开启后可以帮我们自动清理缓存,但遗憾的是: purger系列参数只
有商业版的 NginxPlus 才能使用,因此需要付费才可使用;

通过第三方模块ngx_cache_purge清理缓存:

1. 到nginx安装目录下创建两个文件夹: mkdir cache_purge && cd cache_purge

2. 通过wget指令从github上拉取安装包的压缩文件并解压: 
    wget https://github.com/FRiCKLE/ngx_cache_purge/archive/2.3.tar.gz
    tar -xvzf 2.3.tar.gz

3. 进入nginx解压目录: cd /app/nginx-1.9.9

4. 重新构建一次nginx,通过--add-module的指令添加刚刚的第三方模块:
    ./configure --prefix=/soft/nginx/ --add-module=/soft/nginx/cache_purge/ngx_cache_purge-2.3/

5. 重新根据刚刚构建的nginx,再次编译一下,但切记不要make install: make

6. 删除之前nginx的启动文件,不放心的也可以移动到其他位置: rm -rf /opt/nginx/sbin/nginx

7. 从生成的objs目录中,重新复制一个nginx的启动文件到原来的位置: cp objs/nginx /opt/nginx/sbin/nginx

8. 然后再重启nginx,接下来即可通过 http://xxx/purge/xx 的方式清除缓存

location ~ /purge(/.*) {
  # 配置可以执行清除操作的IP（线上可以配置成内网机器）
  # allow 127.0.0.1; # 代表本机
  allow all; # 代表允许任意IP清除缓存
  proxy_cache_purge $host$1$is_args$args;
}
```

- 防盗链设计

```text
    nginx的防盗链机制实现,跟上篇文章《HTTP/HTTPS》中分析到的一个头部字段: Referer有关,该字段主要描述了当前请求
是从哪儿发出的,那么在nginx中就可获取该值,然后判断是否为本站的资源引用请求,如果不是则不允许访问,nginx中存在一个配置
项为valid_referers,正好可以满足前面的需求，语法如下:

valid_referers none | blocked | server_names | string ...;
- none: 表示接受没有Referer字段的HTTP请求访问;
- blocked: 表示允许http://或https//以外的请求访问;
- server_names: 资源的白名单,这里可以指定允许访问的域名;
- string: 可自定义字符串,支配通配符、正则表达式写法;

# 在动静分离的location中开启防盗链机制
location ~ .*\.(html|htm|gif|jpg|jpeg|bmp|png|ico|txt|js|css){
    # 最后面的值在上线前可配置为允许的域名地址
    valid_referers blocked 192.168.12.129;
    if ($invalid_referer) {
        # 可以配置成返回一张禁止盗取的图片
        # rewrite   ^/ http://xx.xx.com/NO.jpg;
        # 也可直接返回403
        return   403;
    }   
    root   /soft/nginx/static_resources;
    expires 7d;
}

注:
    也有专门的第三方模块ngx_http_accesskey_module实现了更为完善的设计,防盗链机制也无法解决爬虫
伪造referers信息的这种方式抓取数据;
```

- 长连接配置

```text
通常 nginx 作为代理服务,负责分发客户端的请求,那么建议开启 HTTP 长连接,用户减少握手的次数,降低服务器损耗;

upstream xxx {
    # 长连接数
    keepalive 32;
    # 每个长连接提供的最大请求数
    keepalived_requests 100;
    # 每个长连接没有新的请求时，保持的最长时间
    keepalive_timeout 60s;
}
```

- 开启零拷贝技术

```text
零拷贝这个概念,在大多数性能较为不错的中间件中都有出现,例如 Kafka、Netty 等,而 nginx 中也可以配置数据零拷贝技术

sendfile on; # 开启零拷贝机制

零拷贝读取机制与传统资源读取机制的区别:
    传统方式: 硬件-->内核-->用户空间-->程序空间-->程序内核空间-->网络套接字
    零拷贝方式: 硬件-->内核-->程序内核空间-->网络套接字
```

- 开启无延迟或多包共发机制(tcp_nodelay on,tcp_nopush on)

```text
    TCP/IP协议中默认是采用了Nagle算法的,即在网络数据传输过程中,每个数据报文并不会立马发送出去,而是会等待一段时间
将后面的几个数据包一起组合成一个数据报文发送,但这个算法虽然提高了网络吞吐量,但是实时性却降低了,因此你的项目属于交互
性很强的应用,那么可以手动开启 tcp_nodelay 配置,让应用程序向内核递交的每个数据包都会立即发送出去,但这样会产生大量
的TCP报文头,增加很大的网络开销;
    相反,有些项目的业务对数据的实时性要求并不高,追求的则是更高的吞吐,那么则可以开启 tcp_nopush 配置项,这个配置就
类似于“塞子”的意思,首先将连接塞住,使得数据先不发出去,等到拔去塞子后再发出去,设置该选项后,内核会尽量把小数据包拼接成
一个大的数据包 (一个MTU) 再发送出去;
    当然若一定时间后 (一般为200ms)，内核仍然没有积累到一个MTU的量时,也必须发送现有的数据,否则会一直阻塞;

tcp_nodelay、tcp_nopush两个参数是“互斥”的:
    如果追求响应速度的应用推荐开启 tcp_nodelay 参数如IM、金融等类型的项目;
    如果追求吞吐量的应用则建议开启 tcp_nopush 参数，如调度系统、报表系统等;

注: 
    - tcp_nodelay 一般要建立在开启了长连接模式的情况下使用;
    - tcp_nopush 参数是必须要开启sendfile参数才可使用的;
```

- 开启CPU亲和机制

```text
worker_cpu_affinity auto;

    进程/线程数往往都会远超出系统CPU的核心数,因为操作系统执行的原理本质上是采用时间片切换机制,也就是一个CPU
核心会在多个进程之间不断频繁切换,造成很大的性能损耗,而CPU亲和机制则是指将每个nginx的工作进程,绑定在固定的
CPU核心上，从而减小CPU切换带来的时间开销和资源损耗;
```

- 调整Worker工作进程

```text
    nginx启动后默认只会开启一个Worker工作进程处理客户端请求,而我们可以根据机器的CPU核数开启对应数量的工作进程
以此来提升整体的并发量支持;

# 自动根据CPU核心数调整Worker进程数量
worker_processes auto;

注: 工作进程的数量最高开到8个就OK了,8个之后就不会有再大的性能提升

也可以稍微调整一下每个工作进程能够打开的文件句柄数:

# 每个Worker能打开的文件描述符，最少调整至1W以上，负荷较高建议2-3W
worker_rlimit_nofile 20000;

注:
    操作系统内核 kernel 都是利用文件描述符来访问文件,无论是打开、新建、读取、写入文件时,都需要使用文件描述符来
指定待操作的文件,因此该值越大,代表一个进程能够操作的文件越多,但不能超出内核限制,最多建议3.8W左右为上限;
```

- 开启epoll模型及调整并发连接数

```text
events {
    # 使用epoll网络模型
    use epoll;
    # 调整每个Worker能够处理的连接数上限
    worker_connections  10240;
}

注:
    nginx、Redis都是基于多路复用模型去实现的程序,但最初版的多路复用模型select/poll最大只能监听1024个连接
而epoll则属于select/poll接口的增强版,因此采用该模型能够大程度上提升单个Worker的性能;
```

### 高可用

```text
    keepalived在之前单体架构开发时,是一个用的较为频繁的高可用技术,比如MySQL、Redis、MQ、Proxy、Tomcat等
各处都会通过keepalived提供的VIP机制(VIP: 是指Virtual IP,即虚拟IP),实现单节点应用的高可用,keepalived + 
重启脚本 + 双机热备搭建

1. 下载: wget https://www.keepalived.org/software/keepalived-2.2.4.tar.gz
2. 解压: tar -zxvf keepalived-2.2.4.tar.gz
3. 进入: cd keepalived-2.2.4
4. 构建: ./configure --prefix=/app/keepalived/
5. 编译并安装: make && make install
6. 编辑主机的keepalived.conf核心配置文件: vim keepalived.conf
global_defs {
    # 自带的邮件提醒服务，建议用独立的监控或第三方SMTP，也可选择配置邮件发送。
    notification_email {
        root@localhost
    }
    notification_email_from root@localhost
    smtp_server localhost
    smtp_connect_timeout 30
    # 高可用集群主机身份标识(集群中主机身份标识名称不能重复，建议配置成本机IP)
	router_id 192.168.12.129 
}

# 定时运行的脚本文件配置
vrrp_script check_nginx_pid_restart {
    # 之前编写的nginx重启脚本的所在位置
	script "/soft/scripts/keepalived/check_nginx_pid_restart.sh" 
    # 每间隔3秒执行一次
	interval 3
    # 如果脚本中的条件成立，重启一次则权重-20
	weight -20
}

# 定义虚拟路由，VI_1为虚拟路由的标示符（可自定义名称）
vrrp_instance VI_1 {
    # 当前节点的身份标识：用来决定主从（MASTER为主机，BACKUP为从机）
	state MASTER
    # 绑定虚拟IP的网络接口，根据自己的机器的网卡配置
	interface ens33 
    # 虚拟路由的ID号，主从两个节点设置必须一样
	virtual_router_id 121
    # 填写本机IP
	mcast_src_ip 192.168.12.129
    # 节点权重优先级，主节点要比从节点优先级高
	priority 100
    # 优先级高的设置nopreempt，解决异常恢复后再次抢占造成的脑裂问题
	nopreempt
    # 组播信息发送间隔，两个节点设置必须一样，默认1s（类似于心跳检测）
	advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    # 将track_script块加入instance配置块
    track_script {
        # 执行Nginx监控的脚本
		check_nginx_pid_restart
    }

    virtual_ipaddress {
        # 虚拟IP(VIP)，也可扩展，可配置多个。
		192.168.12.111
    }
}

7. 编辑从机的keepalived.conf文件:
global_defs {
    # 自带的邮件提醒服务，建议用独立的监控或第三方SMTP，也可选择配置邮件发送。
    notification_email {
        root@localhost
    }
    notification_email_from root@localhost
    smtp_server localhost
    smtp_connect_timeout 30
    # 高可用集群主机身份标识(集群中主机身份标识名称不能重复，建议配置成本机IP)
	router_id 192.168.12.130 
}

# 定时运行的脚本文件配置
vrrp_script check_nginx_pid_restart {
    # 之前编写的nginx重启脚本的所在位置
	script "/soft/scripts/keepalived/check_nginx_pid_restart.sh" 
    # 每间隔3秒执行一次
	interval 3
    # 如果脚本中的条件成立，重启一次则权重-20
	weight -20
}

# 定义虚拟路由，VI_1为虚拟路由的标示符（可自定义名称）
vrrp_instance VI_1 {
    # 当前节点的身份标识：用来决定主从（MASTER为主机，BACKUP为从机）
	state BACKUP
    # 绑定虚拟IP的网络接口，根据自己的机器的网卡配置
	interface ens33 
    # 虚拟路由的ID号，主从两个节点设置必须一样
	virtual_router_id 121
    # 填写本机IP
	mcast_src_ip 192.168.12.130
    # 节点权重优先级，主节点要比从节点优先级高
	priority 90
    # 优先级高的设置nopreempt，解决异常恢复后再次抢占造成的脑裂问题
	nopreempt
    # 组播信息发送间隔，两个节点设置必须一样，默认1s（类似于心跳检测）
	advert_int 1
    authentication {
        auth_type PASS
        auth_pass 1111
    }
    # 将track_script块加入instance配置块
    track_script {
        # 执行Nginx监控的脚本
		check_nginx_pid_restart
    }

    virtual_ipaddress {
        # 虚拟IP(VIP)，也可扩展，可配置多个。
		192.168.12.111
    }
}

8. 新建scripts目录并编写nginx的重启脚本,check_nginx_pid_restart.sh:
    mkdir /app/scripts /app/scripts/keepalived
    touch /app/scripts/keepalived/check_nginx_pid_restart.sh
    vi /app/scripts/keepalived/check_nginx_pid_restart.sh

#!/bin/sh
# 通过ps指令查询后台的nginx进程数，并将其保存在变量nginx_number中
nginx_number=`ps -C nginx --no-header | wc -l`
# 判断后台是否还有Nginx进程在运行
if [ $nginx_number -eq 0 ];then
    # 如果后台查询不到`Nginx`进程存在，则执行重启指令
    /opt/nginx/sbin/nginx -c /opt/nginx/conf/nginx.conf
    # 重启后等待1s后，再次查询后台进程数
    sleep 1
    # 如果重启后依旧无法查询到nginx进程
    if [ `ps -C nginx --no-header | wc -l` -eq 0 ];then
        # 将keepalived主机下线，将虚拟IP漂移给从机，从机上线接管Nginx服务
        systemctl stop keepalived.service
    fi
fi

9. 编写的脚本文件需要更改编码格式，并赋予执行权限，否则可能执行失败
    vi /soft/scripts/keepalived/check_nginx_pid_restart.sh
    :set fileformat=unix # 在vi命令里面执行，修改编码格式
    :set ff # 查看修改后的编码格式
    
    chmod +x /soft/scripts/keepalived/check_nginx_pid_restart.sh

10. 由于安装keepalived时，是自定义的安装位置，因此需要拷贝一些文件到系统目录中
    mkdir /etc/keepalived/
    cp /soft/keepalived/etc/keepalived/keepalived.conf /etc/keepalived/
    cp /soft/keepalived/keepalived-2.2.4/keepalived/etc/init.d/keepalived /etc/init.d/
    cp /soft/keepalived/etc/sysconfig/keepalived /etc/sysconfig/
    
11. 将keepalived加入系统服务并设置开启自启动，然后测试启动是否正常
    chkconfig keepalived on
    systemctl daemon-reload
    systemctl enable keepalived.service
    systemctl start keepalived.service
其他命令：
systemctl disable keepalived.service # 禁止开机自动启动
systemctl restart keepalived.service # 重启keepalived
systemctl stop keepalived.service # 停止keepalived
tail -f /var/log/messages # 查看keepalived运行时日志

12. 最后测试一下VIP是否生效,通过查看本机是否成功挂载虚拟IP: ip addr
13. 由于前面没有域名的原因,因此最初server_name配置的是当前机器的IP,所以需稍微更改一下nginx.conf的配置
sever{
    listen    80;
    # 这里从机器的本地IP改为虚拟IP
	server_name 192.168.12.111;
	# 如果这里配置的是域名，那么则将域名的映射配置改为虚拟IP
}
```