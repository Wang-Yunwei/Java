# Elasticsearch

---

## 安装 Elasticsearch

```text
1.解压: tar -zxvf elasticsearch-6.5.1.tar.gz
2.启动: ./bin/elasticsearch
[报错]
    -max file descriptors [51200] for elasticsearch process is too low, increase to at least [65536]
[解决]
    maxfile descriptors为最大文件描述符,设置其大于65536即可: vi /etc/security/limits.conf ,添加如下信息:
------------------------------------
*          soft     nofile     65536
*          hard     nofile     65536
------------------------------------
[报错]
    -max virtual memory areas vm.max_map_count [65530] is too low, increase to at least [262144]
[解决]
    elasticsearch用户拥有的内存不足至少需要262144: vi /etc/sysctl.conf ,添加如下信息:
-----------------------
vm.max_map_count=262144
-----------------------
3.使以上配置生效: sysctl -p
4.测试 elasticsearch 是否安装成功: curl -X GET localhost:9200
```

## 安装 SearchGuard

```text
1.在线安装: ./bin/elasticsearch-plugin install -b com.floragunn:search-guard-6-6.5.1-24.3
[或]
    本地安装: ./bin/elasticsearch-plugin install -b file:///app/ELK/search-guard-6-6.5.1-24.3.zip
2.下载证书生成工具: https://search.maven.org/search?q=g:com.floragunn%20AND%20a:search-guard-tlstool&core=gav
3.解压: tar -zxvf search-guard-tlstool-1.5.tar.gz
4. 证书目录结构:
---------------------------------------------------
config   #配置文件目录,工具可以根据配置文件模板为你生成证书
dep      #工具所依赖的jar包
tools    #生成证书的脚本
---------------------------------------------------
5.配置数据模板: tlsconfig.yml
6.执行命令生成证书: ./sgtlstool.sh -c ../config/tlsconfig.yml  -ca -crt
[注]
    证书在目录 /tools/out 下
---------------------------------------------------
root-ca.pem    #根证书
root-ca.key    #根CA的私钥
root.ca.readme #根CA和中间CA的密码

[nodename].pem      #节点证书
[nodename].key      #节点证书的私钥
[nodename]_http.pem #REST证书,仅在reuseTransportCertificatesForHttp为false时生成
[nodename]_http.key #REST证书的私钥,仅在reuseTransportCertificatesForHttp为false时生成

[name].pem #客户端证书
[name].key #客户端证书的私钥

[nodename]_elasticsearch_config_snippet.yml 搜索此节点的Guard配置代码段,将其添加到elasticsearch.yml
client-certificates.readme 包含证书的自动生成密码
---------------------------------------------------
7.复制证书
[注]
    (1).复制root-ca.pem到elasticsearch的config目录下
    (2).复制[nodename].pem到elasticsearch的config目录下
    (3).复制[nodename].key到elasticsearch的config目录下
    (4).复制[nodename]_http.pem到elasticsearch的config目录下
    (5).复制[nodename]_http.key到elasticsearch的config目录下
    (6).复制root-ca.pem到elasticsearch的plugins/search-guard-[version]/tools目录下
    (7).复制 [name].key到elasticsearch的plugins/search-guard-[version]/tools目录下
    (8).复制 [name].key到elasticsearch的plugins/search-guard-[version]/tools目录下
8.配置 Elasticsearch.yml 编辑: vi elasticsearch.yml ,修改如下信息:
---------------------------------------------------
cluster.name: ESHUB-ESCluster
network.host: 0.0.0.0
node.name: es-node02
#node.master: false
#node.data: false
#node.ingest: false

http.port: 9200
http.cors.enabled: true
http.cors.allow-origin: "*"
http.cors.allow-headers: Authorization,X-Requested-With,Content-Length,Content-Type
http.cors.allow-credentials: true

searchguard.ssl.transport.pemcert_filepath: node1.pem
searchguard.ssl.transport.pemkey_filepath: node1.key
searchguard.ssl.transport.pemkey_password: O2a6pLDTrTYZ
searchguard.ssl.transport.pemtrustedcas_filepath: root-ca.pem
searchguard.ssl.transport.enforce_hostname_verification: false
searchguard.ssl.transport.resolve_hostname: false
searchguard.ssl.http.enabled: true
searchguard.ssl.http.pemcert_filepath: node1_http.pem
searchguard.ssl.http.pemkey_filepath: node1_http.key
searchguard.ssl.http.pemkey_password: DXaIz44qpUD2
searchguard.ssl.http.pemtrustedcas_filepath: root-ca.pem

searchguard.nodes_dn:
- CN=node1.cmss.com,OU=IT,O=CMSS Com\, Inc.,DC=example,DC=com
searchguard.authcz.admin_dn:
- CN=itsa.cmss.com,OU=Ops,O=CMSS Com\, Inc.,DC=example,DC=com

searchguard.allow_unsafe_democertificates: true
xpack.security.enabled: false
searchguard.enterprise_modules_enabled: false

discovery.zen.minimum_master_nodes: 2
#discovery.zen.ping.multicast.enabled: false
discovery.zen.ping.unicast.hosts: ["10.154.0.52:9300","10.154.11.10:9300"]
---------------------------------------------------
9.激活 SearchGuard 插件: ./sgadmin.sh -cacert root-ca.pem -cert admin.pem -key admin.key -keypass EubaNtazzHPN -nhnv -icl -cd ../sgconfig
[错误]
    如果提示没有权限则增加 sgadmin.sh 的权限重新执行即可
[解决]
    chmod -R 777 sgadmin.sh
    #-R 是指级联应用到目录里的所有子目录和文件
    #777 是所有用户都拥有最高权限
```

## 安装 Elasticsearch-head(注:elasticsearch2.3.x版本不需要单独装head插件)

```text
1.安装 grunt-cli
[注]
    (1). 切换到: /app/soft/node/node-v10.15.3-linux-x64 的 bin 目录下
    (2). npm init -f
    (3). npm install grunt-cli -g   #-g 为全局安装
    (4).执行验证: grunt

2.网页下载压缩包: https://github.com/mobz/elasticsearch-head
3.解压: unzip elasticsearch-head-master.zip
4.编辑Gruntfile.js ,并添加如下内容:
---------------------------------------------------
connect: {
      server: {
            options: {
                        port: 9100,
                        base: '.',
                        keepalive: true,
                        hostname: '0.0.0.0'
             }
       }
}
---------------------------------------------------
5.编辑app.js : vi /elasticsearch-head-master/_site/app.js ,修改如下内容
---------------------------------------------------
查找: this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://localhost:9200";
将其中的 localhost 替换成 elasticsearch 服务所在的 IP
---------------------------------------------------
6.切换到elasticsearch-head-master目录下安装grunt: npm install grunt
7.启动 Head 插件: nohup grunt server &
[错误]
    Local Npm module "grunt-contrib-clean" not found. Is it installed?
    Local Npm module "grunt-contrib-concat" not found. Is it installed?
    Local Npm module "grunt-contrib-watch" not found. Is it installed?
    Local Npm module "grunt-contrib-connect" not found. Is it installed?
    Local Npm module "grunt-contrib-copy" not found. Is it installed?
    Local Npm module "grunt-contrib-jasmine" not found. Is it installed?
    Warning: Task "connect:server" not found. Use --force to continue.
[解决]
    npm install grunt@latest
    npm install grunt-cli@latest
    npm install grunt-contrib-copy@latest
    npm install grunt-contrib-concat@latest
    npm install grunt-contrib-uglify@latest
    npm install grunt-contrib-clean@latest
    npm install grunt-contrib-watch@latest
    npm install grunt-contrib-connect@latest
    npm install grunt-contrib-jasmine --ignore-scripts
```

## 安装 Kibana 插件

```text
1.解压: tar -zxvf kibana-6.5.1-linux-x86_64.tar.gz
2.编辑kibana.yml ,添加如下内容:
---------------------------------------------------
server.port: 5601
server.host: "127.0.0.1"
elasticsearch.url: "https://localhost:9200"
elasticsearch.username: "kibana
elasticsearch.password: "kibana"
elasticsearch.ssl.verificationMode: none
elasticsearch.requestHeadersWhitelist: [ "Authorization", "sgtenant" ]
xpack.monitoring.enabled: false
xpack.graph.enabled: false
xpack.ml.enabled: false
xpack.watcher.enabled: false
xpack.security.enabled: false
---------------------------------------------------
3.浏览器访问:
[注]
    http://10.154.0.52:9100/?auth_user=admin&auth_password=admin
[或]
    http://10.154.0.52:9100/?base_uri=https://10.154.0.52:9200&auth_user=admin&auth_password=admin
```

## Cerebro

```text
1.解压: unzip cerebro-0.8.3.zip
2.编辑application.conf文件: vi /cerebro-0.8.3/conf/application.conf ,修改如下信息
---------------------------------------------------
hosts = [
  {
    host = "https://10.154.11.10:9200"
    name = "ESHUB-ESCluster"
    auth = {
      username = "admin"
      password = "admin"
    }
  }
]
---------------------------------------------------
3. 如果 Elasticsearch 安装了 SearchGuard 需添加如下配置
---------------------------------------------------
#配置根CA
play.ws.ssl {
  trustManager = {
    stores = [
      {type="PEM",path="/app/ELK/cerebro-0.8.3/conf/root-ca.pem"}
    ]
  }
}
---------------------------------------------------
4.启动: nohup ./bin/cerebro -D http.port=1234 -D http.address=10.154.11.10 &
[注]
    port=1234   #可以自己指定,如果不指定默认端口为 9000
5.启动 Filebeat: ./filebeat -e -c filebeat.yml -d "publish"
6.启动 Logstash: ./bin/logstash -f ./config/first-pipeline.conf --config.reload.automatic
7.测试配置文件语法: bin/logstash -f first-pipeline.conf --config.test_and_exit
8.编辑logstash-sample.conf文件: vi logstash-6.5.1/config/logstash-sample.conf ,添加如下信息:
---------------------------------------------------
input {
  beats {
    port => 5044
  }
}
output {
  elasticsearch {
    action => "index"
    hosts => ["es-node02.cmss.com:9200","es-node03.cmss.com:9200","es-node04.cmss.com:9200"]
    index => "logstash-abilitycalllog"
    ssl => true
    ssl_certificate_verification => true
    cacert => "/app/ELK/elasticsearch-6.5.1/config/root-ca.pem"
    user => "admin"
    password => "admin"
  }
}
---------------------------------------------------
```