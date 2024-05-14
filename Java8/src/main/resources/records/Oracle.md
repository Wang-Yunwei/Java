# Oracle

---

## 安装

```text
1.修改系统标识(由于Oracle默认不支持CentOS,需要修改为redhat): vim /etc/redhat-release
-------------------------------
#删除CentOS Linux release 7.6.2009 (Core) (快捷键dd)
#改成redhat-7
-------------------------------
2.磁盘空间要求
  1)./tmp目录中至少有1 GB的磁盘空间: df -h /tmp
3.检查需要安装的依赖: rpm -q binutils compat-libcap1 compat-libstdc++-33 gcc gcc-c++ glibc glibc-devel ksh libaio libaio-devel libgcc libstdc++ libstdc++-devel libXi libXtst make sysstat unixODBC unixODBC-devel | grep "not installed"
4.在线安装所需依赖: yum -y install binutils compat-libcap1 compat-libstdc++-33 gcc gcc-c++ glibc glibc-devel ksh libaio libaio-devel libgcc libstdc++ libstdc++-devel libXi libXtst make sysstat unixODBC unixODBC-devel
```

## 创建普通用户并赋予权限

```text
1.进入Oracle数据库(注:需先切换到oracle用户 su - oracle): sqlplus / as sysdba;
2.创建普通用户
    > create user [user_name] identified by pwd_oracle;
3.删除用户
    > drop user [user_name];
4.授予用户登录数据库的权限
    > grant create session to [user_name];
5.授予用户操作表空间的权限
    > grant unlimited tablespace to [user_name];
    > grant create tablespace to [user_name];
    > grant alter tablespace to [user_name];
    > grant drop tablespace to [user_name];
    > grant manage tablespace to [user_name];
6.授予用户操作表的权限
    > grant create table to [user_name];
7.授予用户操作视图的权限
    > grant create view to [user_name];
8.授予用户操作触发器的权限
    > grant create trigger to [user_name]
9.授予用户操作存储过程的权限
    > grant create procedure to [user_name]
10.授予用户操作序列的权限
    > grant create sequence to [user_name]
11.授予用户回退段权限
    > grant create rollback segment to [user_name];
    > grant alter rollback segment to [user_name];
    > grant drop rollback segment to [user_name];
12.授予用户同义词权限
    > grant create synonym to [user_name]; 
    > grant create public synonym to [user_name];
    > grant drop public synonym to [user_name];
13.授予用户关于用户的权限
    >grant create user to [user_name];
    >grant alter user to [user_name];
    >grant become user to [user_name];
    >grant drop user to [user_name];
14.授予用户关于角色的权限
    > grant create role to [user_name];
15.授予用户操作概要文件的权限
    > grant create profile to [user_name];
    > grant alter profile to [user_name];
    > grant drop profile to [user_name];
16.允许从sys用户所拥有的数据字典表中进行选择
    > grant select any dictionary to [user_name];
```

## 创建数据库(ALTER SESSION SET CURRENT_SCHEMA = sys;)

```text
1.创建用户
    > CREATE USER "用户名" IDENTIFIED BY "密码" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP";
2.添加成员
    > GRANT "CONNECT", "RESOURCE" TO "用户名" WITH ADMIN OPTION;
3.修改用户角色
    > ALTER USER "用户名" DEFAULT ROLE "CONNECT", "RESOURCE";
4.切换到SIN用户(注:此步骤非常重要,不执行将无法执行CRUD语句)
    > ALTER SESSION SET CURRENT_SCHEMA = "用户名";
5.查看字符集
    > SELECT userenv（'language'） FROM dual;
6.查看密码有效天数
    > SELECT * FROM dba_profiles WHERE profile='DEFAULT' AND resource_name='PASSWORD_LIFE_TIME';
7.使其永久有效
    > ALTER profile DEFAULT limit password_life_time unlimited;
```

## Oracle_11g使用序列

```text
1.创建序列
    > create sequence mdm_login_log_sequence
                             start with 1
                             increment by 1
                             nomaxvalue
                             nocache;
[注]
    create sequence name_sequence -- name_sequence序列名
    minvalue 1 -- 最小值
    maxvalue 99999999999999999999999999 -- 最大值(可以不指定)
    start with 1 -- 从1开始
    increment by 1 -- 每次加1
    nocache; -- 不建缓冲区

2.创建触发器
    > CREATEOR REPLACE TRIGGER BLOG_ID_AUTOINCREMENT before INSERT ON MDM_LOGIN_LOG FOR each ROW BEGIN SELECT mdm_login_log_sequence.Nextval INTO : new.ID FROM dual; END;
[注]
    CREATE
      OR REPLACE TRIGGER name_trigger -- name_trigger触发器名
      before INSERT ON tableName -- tableName表名
      FOR each ROW
    DECLARE
    BEGIN
      SELECT
        name_sequence.nextval -- name_sequence序列名
        INTO : new.column_id -- column_id列名
      FROM
      sys.dual;
    END;
```

## Linux上执行SQL

- 切换oracle用户
> su - oracle

- 进入数据库
> sqlplus / as sysdba;

- 查看已存在的用户

> SELECT USERNAME, CASE WHEN (USERNAME = USER) THEN 1 ELSE 0 END ISCURRENTUSER FROM SYS.ALL_USERS;

- 查询表

> SELECT table_name FROM all_tables WHERE table_name LIKE 'MED_%';

- 查看BASE用户下的表

> SELECT OWNER, TABLE_NAME FROM ALL_TABLES WHERE OWNER = 'BASE' ORDER BY OWNER;

- 删除表

> DROP TABLE "BASE"."TEST_12点07分";

## 查看密码期限并修改期限

- 查看期限
> select * from dba_profiles where profile='DEFAULT' and resource_name='PASSWORD_LIFE_TIME';

- 修改期限为永久
> alter profile default limit password_life_time unlimited;

- 修改期限后再次修改密码

## 创建同义词

```text
1.CREATE PUBLIC SYNONYM MED_TEMPLATE FOR BASE.MED_TEMPLATE;
2.赋权
    > GRANT SELECT ANY TABLE TO "PHEP" WITH ADMIN OPTION;
3.赋予 PHEP 用户 BASE.MED_DICTIONARY 表的 CURD 权限
    > GRANT INSERT,DELETE,UPDATE,SELECT ON BASE.MED_DICTIONARY TO PHEP
```

## 误操作Update、Delete数据恢复

```text
--------------------------------------------
Flashback query
SQL> show parameter undo;

Undo_retention = n(秒),设置决定undo最多的保存时间,其值越大,就需要越多的undo表空间的支持
SQL> alter system set undo_retention = 604800;
--------------------------------------------
1.查询指定时间内数据是否正常
    > SELECT * FROM MED_PHEP_MOBILE_CONSULTATION AS OF TIMESTAMP TO_TIMESTAMP('2022-03-25 15:58:36', 'YYYY-MM-DD HH24:MI:SS');
2.执行数据恢复
    > FLASHBACK TABLE MED_PHEP_MOBILE_CONSULTATION TO TIMESTAMP TO_TIMESTAMP('2022-03-25 15:58:36', 'YYYY-MM-DD HH24:MI:SS');
[错误]
    没有开启行移动
    ORA-08189: cannot flashback the table because row movement is not enabled
[解决]
    1.开启该表的行移动
        > ALTER TABLE MED_PHEP_MOBILE_CONSULTATION ENABLE ROW MOVEMENT;
    2.再次执行数据恢复
        > FLASHBACK TABLE MED_PHEP_MOBILE_CONSULTATION TO TIMESTAMP TO_TIMESTAMP('2022-03-25 15:58:36', 'YYYY-MM-DD HH24:MI:SS');
```

## Error处理

- ORA-12516 解决方法

```text
1.取得数据库目前的进程数
    > SELECT count(*) FROM v$process;
2.取得进程数的上限
    > SELECT value FROM v$parameter WHERE name='processes';
3.查看当前会话数 processes和sessions值,发现session数和2个参数的值非常接近
    > SELECT count(*) FROM v$session;
    > show parameter processes;
    > show parameter sessions;
4.只要会话连接数超过上面的process数或session数,就会产生12516错误,因此修改如下:
    1).修改processes和sessions值 (注: sessions=1.1*processes+5)
        > alter system set processes=3000 scope=spfile;
        > alter system set sessions=3305 scope=spfile;
    2).重启数据库
        > shutdown immediate
        > startup
```
