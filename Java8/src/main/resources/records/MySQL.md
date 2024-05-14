# MySQL

---

## 安装

```text
在线安装MySQL
    1.使用wget命令下载: wget https://dev.mysql.com/get/mysql57-community-release-el7-11.noarch.rpm
    2.wget没安装的话需要先安装wget
[注]
    (1)yum list|grep wget;
    (2)yun -y install wget;

	3.md5校验: md5sum mysql57-community-release-el7-11.noarch.rpm
	4.安装MySQL软件包的源: rpm -ivh mysql57-community-release-el7-11.noarch.rpm
    5.开始安装MySQL: yum -y install mysql-community-server
    6.启动MySQL服务: systemctl start mysqld
    7.查看MySQL服务状态: systemctl status mysqld
    8.安装成功后查看MySQL临时密码: grep 'temporary password' /var/log/mysqld.log 或 cat /var/log/mysqld.log | grep password
    9.进入数据库命令界面: mysql -u root -p
    10.修改密码: ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root'
    11.执行行远程访问的授权
        > create user 'root'@'%' identified with mysql_native_password by 'Wa_1322@%';
        > grant all privileges on *.* to 'root'@'%' with grant option;
        > flush privileges;
    12.修改加密规则(MySql8.0 版本 和 5.0 的加密规则不一样,而现在的可视化工具只支持旧的加密方式)
        > ALTER USER 'root'@'localhost' IDENTIFIED BY 'root' PASSWORD EXPIRE NEVER;
```

## 使用SQL记录

```text
<sql id="Base_Column_List">
  id, file_name, file_path, create_time, update_time, del_flag
</sql>

<!-- 生成uuid并赋值给id -->
<selectKey resultType="java.lang.String" order="BEFORE" keyProperty="id">
    select REPLACE(uuid(), '-', '') as id from dual
</selectKey>

<!-- 文件信息插入数据库 -->
<insert id="insert" parameterType="com.chinamobile.cmss.services.discipline.core.entity.datasyn.DataSynFileInfo"
useGeneratedKeys="true" keyProperty="id">
  INSERT INTO data_syn_file_info (file_name,file_path,create_time,update_time) VALUES (#{fileName,jdbcType=VARCHAR},
  #{filePath,jdbcType=VARCHAR},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})
</insert>

<!-- 这两种方式都可以获得库表自增的ID -->
 <selectKey  会将 SELECT LAST_INSERT_ID()的结果放入到传入的model的主键里面,
     keyProperty 对应的model中的主键的属性名，这里是 user 中的id，因为它跟数据库的主键对应  
     order AFTER 表示 SELECT LAST_INSERT_ID() 在insert执行之后执行,多用与自增主键,
           BEFORE 表示 SELECT LAST_INSERT_ID() 在insert执行之前执行，这样的话就拿不到主键了,  
                 这种适合那种主键不是自增的类型
     resultType 主键类型 -->
 <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Long">
   SELECT LAST_INSERT_ID()
 </selectKey>


<!-- 遍历新增 -->
<foreach collection="list" item="item" index="index" separator=",">
    (#{item.infoId,jdbcType=BIGINT},#{item.emergencyStartId,jdbcType=BIGINT},
     #{item.projectCode,jdbcType=VARCHAR},#{item.projectName,jdbcType=VARCHAR},
     #{item.createdBy,jdbcType=BIGINT},#{item.createdName,jdbcType=VARCHAR},
     #{item.creationDate,jdbcType=TIMESTAMP},#{item.updatedBy,jdbcType=BIGINT},
     #{item.updatedName,jdbcType=VARCHAR},#{item.updateDate,jdbcType=TIMESTAMP},
     #{item.companyCode,jdbcType=VARCHAR},#{item.companyName,jdbcType=VARCHAR},
     #{item.secondOrgCode,jdbcType=VARCHAR},#{item.secondOrgName,jdbcType=VARCHAR},
     #{item.orgCode,jdbcType=VARCHAR},#{item.orgName,jdbcType=VARCHAR},#{item.deleteFlag,jdbcType=INTEGER})
</foreach>

<!-- 手动发起电子会审 - 批量更新内部会审结果 -->
<update id="updateInAuditResult">
    UPDATE mdsg_task_status
    <trim prefix="SET" suffixOverrides=",">
        AUDIT_ID = #{auditId,jdbcType=BIGINT},
        <trim prefix="IN_AUDIT_RESULT = CASE TASK_ID" suffix="end,">
            <foreach collection="list" item="ent" index="index">
                WHEN #{ent.taskId,jdbcType=BIGINT} THEN #{ent.inAuditResult,jdbcType=VARCHAR}
            </foreach>
        </trim>
    </trim>
    WHERE TASK_ID IN
    <foreach collection="list" item="ent" index="index" open="(" separator="," close=")">
        #{ent.taskId,jdbcType=BIGINT}
    </foreach>
</update>
```

## 常用函数

- 多行查询结果合并成一行: GROUP_CONCAT
- 行转列
  [注] - 可以使用SUM()或MAX()

```sql
--使用SUM()函数实现
SELECT T1.ID,
       T1.USERNAME,
       SUM(
               IF
                   (T2.INTEGRAL_TYPE = 0, T2.INTEGRAL_VALUE, 0)) AS 单次积分,
       SUM(
               IF
                   (T2.INTEGRAL_TYPE = 1, T2.INTEGRAL_VALUE, 0)) AS 可用积分
FROM user_info T1
         LEFT JOIN integral_info T2 ON T1.ID = T2.USER_ID
GROUP BY T1.ID

--使用MAX()函数实现
SELECT T1.ID,
       T1.USERNAME,
       MAX(CASE T2.INTEGRAL_TYPE WHEN 0 THEN T2.INTEGRAL_VALUE ELSE 0 END) AS 单次积分,
       MAX(CASE T2.INTEGRAL_TYPE WHEN 1 THEN T2.INTEGRAL_VALUE ELSE 0 END) AS 可用积分
FROM user_info T1
         LEFT JOIN integral_info T2 ON T1.ID = T2.USER_ID
GROUP BY T1.ID;
```

## 创建数据库

- 创建数据库
  > CREATE DATABASE [DBname] CHARACTER SET utf8 COLLATE utf8_bin;

- 创建用户
  > CREATE USER '[Uname]'@'%' IDENTIFIED BY '[password]';

- 赋予权限
  > GRANT ALL PRIVILEGES ON [DBname].* TO '[Uname]'@'%';

- 创建用户并赋予权限
  > GRANT ALL NO *.* TO '[Uname]'@'%' IDENTIFIED BY '[password]';

## 隔离级别

- 查看全局隔离级别
  > SELECT @@GLOBAL.transaction_isolation;

- 设置全局隔离级别

```text
set global transaction isolation level REPEATABLE READ;
set global transaction isolation level READ COMMOTTED;
set global transaction isolation level READ UNCOMMOTTED;
set global transaction isolation level SERIALIZABLE;
```

- 查看会话隔离级别
  > SELECT @@SESSION.transaction_isolation;

- 设置会话隔离级别

```text
set session transaction isolation level REPEATABLE READ;
set session transaction isolation level READ COMMOTTED;
set session transaction isolation level READ UNCOMMOTTED;
set session transaction isolation level SERIALIZABLE;
```

## 数据事务

- 查看数据库事务超时时间(毫秒)
  > show variables like 'innodb_lock_wait_timeout'

- 修改数据库事务超时时间(毫秒)
  [注]:spring的事务超时时间和mysql的事务超时时间是相互影响的 @Transactional(timeout=5)
  > set innodb_lock_wait_timeout = 10;

## 表结构

- 查看表结构
  > describe [table_name];
  > show columns from [table_name];

```sql
/*==============================================================*/
/* Table structure for "public"."data_syn_file_info"-2019.8.16  */
/*==============================================================*/
CREATE TABLE data_syn_file_info
(
    ID          bigint(20) AUTO_INCREMENT COMMENT '主键ID',
    FILE_NAME   varchar(256)   NOT NUll COMMENT '文件名',
    FILE_PATH   varchar(256)   NOT null COMMENT '文件路径',
    DESC        text COMMENT '描述',
    CREATE_TIME datetime       NOT NULL COMMENT '创建时间',
    UPDATE_TIME datetime       NOT NULL COMMENT '更新时间',
    DEL_FLAG    int4 DEFAULT 0 NOT NULL COMMENT '删除标记',
    PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据同步文件信息表';
```

## 表操作

- 清空数据,相当于创建新表,不记录日志,删除数据无法恢复
  > TRUNCATE TABLE [table_name];

- 使用索引
    - 添加 INDEX (普通索引)
      > ALTER TABLE `table_name` ADD INDEX index_name ( `column` );
    - 添加多 列索引 (普通索引)
      > ALTER TABLE `table_name` ADD INDEX index_name ( `column1`, `column2`, `column3` );
    - 添加 UNIQUE (唯一索引)
      > ALTER TABLE `table_name` ADD UNIQUE ( `column` );
    - 添加 PRIMARY KEY (主键索引)
      > ALTER TABLE `table_name` ADD PRIMARY KEY ( `column` );
    - 添加 FULLTEXT (全文索引)
      > ALTER TABLE `table_name` ADD FULLTEXT ( `column` );

- 截取某字段固定字符串长度更新 (截取字段field中从1开始长度为11的字符)
  > UPDATE table_name SET field = SUBSTRING(field,1,11)

- mysql查询结果导出写入文件 关键字 【into outfile】
  > SELECT * FROM tableName into outfile 'path'

  [例]
  > SELECT * FROM sys_user into outfile '/app/test.xlsx';

  [注]
  > show variables like '%secure%'; #查看 secure-file-priv 当前的值是什么,
  > 如果 secure-file-priv 的值为 NULL 则表示禁止写入,需要修改 my.cnf 配置文件,直接在里面添加
  secure-file-priv="" 并重启 mysql 即可

- SELECT 将两个字段的值合并到一起作为一个值返回
  > SELECT CONCAT(column_name,column_name2) FROM table_name;

- 修改字段类型
  > ALTER TABLE `discipline`.`clue_info_temporary` MODIFY COLUMN `unit_type` tinyint(0) NULL DEFAULT NULL COMMENT '单位类别'
  AFTER `repeat_number`;
- 添加表字段
  > ALTER TABLE `数据库名`.`表名` ADD COLUMN `添加的字段名` 字段类型 非空约束 COMMENT '字段注释' AFTER `字段名`;
- 删除表字段
  > ALTER TABLE `数据库`.`表名` DROP COLUMN `字段名`;
- 修改表字段
  > ALTER TABLE `数据库名`.`表名` CHANGE COLUMN `原字段名` `变更字段名` 字段类型 CHARACTER SET utf8 COLLATE
  utf8_general_ci NULL DEFAULT NULL COMMENT '线索编码' AFTER `字段名`;

## 存储引擎选择

| 功能     | MYISAM | Memory | InnoDB | Archive |
|--------|--------|--------|--------|---------|
| 存储限制   | 256TB  | RAM    | 64TB   | None    |
| 支持事务   | No     | No     | Yes    | No      |
| 支持全文索引 | Yes    | No     | No     | No      |
| 支持数索引  | Yes    | Yes    | Yes    | No      |
| 支持哈希索引 | No     | Yes    | No     | No      |
| 支持数据缓存 | No     | N/A    | Yes    | No      |
| 支持外键   | No     | No     | Yes    | No      |

## 字段类型

| 类型(数值类型)    | 大小  | 范围(有符号)                                                                                                 | 范围(无符号)                                               | 用途       |
|-------------|-----|---------------------------------------------------------------------------------------------------------|-------------------------------------------------------|----------|
| TINYINT     | 1字节 | (-128,127)                                                                                              | (0,255)                                               | 小整数值     |
| SMALLINT    | 2字节 | (-32768,32767)                                                                                          | (0,65535)                                             | 大整数值     |
| MEDIUMINT   | 3字节 | (-8388608,8388607)                                                                                      | (0,16777215)                                          | 大整数值     |
| INT或INTEGER | 4字节 | (-2147483648,2147483647)                                                                                | (0,4294967295)                                        | 大整数值     |
| BIGINT      | 8字节 | (-9233372036854775808,9223372036854775807)                                                              | (0,18446744073709551615)                              | 大整数值     |
| FLOAT       | 4字节 | (-3.402823466E+38,-1.175494351E-38),0,(1.175494351E-38,3.402823466351E+38)                              | (0,(1.175 494 351 E-38,3.402 823 466 E+38))           | 单精度 浮点数值 |
| DOUBLE      | 8字节 | (-1.7976931348623157E+308,-2.2250738585072014E-308),0,(2.2250738585072014E-308,1.7976931348623157E+308) | (0,(2.2250738585072014E-308,1.7976931348623157E+308)) | 双精度 浮点数值 |

| 类型(字符串)    | 大小              |                  |
|------------|-----------------|------------------|
| CHAR       | 0-255        字节 | 定长字符串            |
| VARCHAR    | 0-65535      字节 | 变长字符串            |
| TINYBLOB   | 0-255        字节 | 不超过255个字符的二进制字符串 |
| TINYTEXT   | 0-255        字节 | 短文本字符串           |
| BLOB       | 0-65535      字节 | 二进制形式的长文本数据      |
| TEXT       | 0-65535      字节 | 长文本数据            |
| MEDIUMBLOB | 0-16777215   字节 | 二进制形式的中等长度文本数据   |
| MEDIUMTEXT | 0-16777215   字节 | 中等长度文本数据         |
| LONGBLOB   | 0-4294967295 字节 | 二进制形式的极大文本数据     |
| LONGTEXT   | 0-4294967295 字节 | 极大文本数据           |

| 类型(日期类型)  | 大小  | 格式                     |                 |
|-----------|-----|------------------------|-----------------|
| YEAR      | 1字节 | YYYY                   | 年份值             |
| TIME      | 3字节 | HH:MM:SS               | 时间值             |
| DATE      | 3字节 | YYYY-MM-DD             | 日期值             |
| DATETIME  | 8字节 | YYYY-MM-DD HH:MM:SS    | 日期值 + 时间值       |
| TIMESTAMP | 4字节 | YYYY-MM-DD HH:MM:SS MS | 日期值 + 时间值 + 毫秒值 |