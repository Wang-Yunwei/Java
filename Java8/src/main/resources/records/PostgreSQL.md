# PostgreSQL

---

## 1、CentOS7 连接 postgres 数据库

> psql -U [userName] -d [databaseName] -h [serverHost]

- [注] 如果无法执行上面的命令请使用一下解决办法:

```text
1.查看/app/postgresql/lib这个目录有没加到环境变量 LD_LIBRARY_PATH 中,如果没有继续向下
[root@wyw ~]# echo $LD_LIBRARY_PATH

2.根据服务器上数据库的实际目录
[root@wyw ~]# LD_LIBRARY_PATH=/app/postgresql/lib
[root@wyw ~]# export LD_LIBRARY_PATH

3.再次查看
[root@wyw ~]# echo $LD_LIBRARY_PATH
```

### 1.1、列出所有数据库

> \l

### 1.2、切入指定数据库

> \c [databaseName]

### 1.3、列出当前数据库中所有的表

> \d

### 1.4、退出 SQL 命令行

> \q

### 1.5、两种导出数据库方式

> ./pg_dump -U [userName] -f /app/dump.sql [databaseName]
> ./pg_dump -U [userName] [databaseName] > /app/dump.sql

### 1.6、导入数据库

> psql -d [databaseName] -f /app/dump.sql [databaseName]

### 1.7、导出数据库表,进入 /app/postgresql/bin 目录下执行

> ./pg_dump -U [userName] -t [tableName] -f /app/dump.sql [databaseName]

### 1.8、导入数据库表

> psql -d [databaseName] -f /app/dump.sql postgres

### 1.9、将 SELECT 查询出来的数据导出为文件

> COPY (SELECT * FROM [tableName]) TO '/app/2019-8-7.csv' delimiter ',' csv header encoding 'UTF-8';

## 2、序列

> Sequence

### 2.1、添加序列

```text
CREATE SEQUENCE "public"."operator_log_id_seq"
INCREMENT 1 //步长
MINVALUE 1 //最小值
MAXVALUE 9223372036854775807 //最大值
START 1 //起始值
CACHE 1 //缓存值
CYCLE; //循环,表示到最大值后从头开始,一般不需要
```

### 2.2、删除序列

> DROP SEQUENCE IF EXISTS  "public"."operator_log_id_seq";

### 2.3、修改序列 (将序列起始值调整为 1)

> ALTER SEQUENCE operator_log_id_seq RESTART WITH 1;

### 2.4、查询序列 (同时在原有基础上加1)

> SELECT nextval ('operator_log_id_seq');

## 3、创建权限表

```text
CREATE TABLE "public"."authority" (
  id                     SERIAL                         NOT NULL,
  name                   varchar(20)                    NOT NULL,
  description            varchar(20)                    NOT NULL,
  level                  int4                           NOT NULL,
  parent_name            varchar(100)                   NOT NULL,
  create_time            timestamp(6)                   NOT NULL,
  update_time            timestamp(6)                   NOT NULL,
  del_flag               int4             DEFAULT 0     NOT NULL,
  CONSTRAINT PK_AUTHORITY PRIMARY KEY (id)
);

COMMENT ON TABLE "public"."authority" IS '权限表';

COMMENT ON COLUMN "public"."authority"."id" IS '主键';

COMMENT ON COLUMN "public"."authority"."name" IS '权限名';

COMMENT ON COLUMN "public"."authority"."description" IS '描述';

COMMENT ON COLUMN "public"."authority"."level" IS '自增主键';

COMMENT ON COLUMN "public"."authority"."parent_name" IS '上级名';

COMMENT ON COLUMN "public"."authority"."create_time" IS '创建时间';

COMMENT ON COLUMN "public"."authority"."update_time" IS '修改时间';

COMMENT ON COLUMN "public"."authority"."del_flag" IS '删除表示';
```

### 3.1、删除 "authority" 表

> DROP TABLE IF EXISTS "public"."authority";

### 3.2、创建索引

> CREATE UNIQUE INDEX authority_PK ON authority (id);

### 3.3、删除 "authority_PK" 索引

> DROP INDEX IF EXISTS authority_PK;

## 4、数据库基本操作

> INSERT INTO [tableName] () VALUES (value1,value2,...,valueN);
> DELETE FROM [tableName] WHERE ID = 1;
> UPDATE [tableName] SET field1=new-value1, field2=new-value2 WHERE ID=1;
> SELECT * FROM [tableName]

### 4.1、SELECT 将两个字段的值合并到一起作为一个值返回

> SELECT concat(column_name,column_name2) FROM [tableName];

- [例]

```text
SELECT concat(t1.value_name,',',t1.value_name) FROM test1 t1,test2 t2;
```

### 4.2、SELECT (注: order by 必须在分页之前,别名用双引号包裹可以使用特殊字符)

> SELECT id AS "%#$&*" FROM [tableName] ORDER BY create_time DESC OFFSET 1 LIMIT 2;

### 4.3、SELECT 查询的时候给返回的数据字段追加一个字段值,在返回的字段中追加 name 并赋值

> SELECT id,'str' AS name FROM [tableName];

### 4.4、SELECT DISTINCT 语句用于返回唯一不同的值

> SELECT DISTINCT column_name,column_name FROM [tableName];

### 4.5、如果 DISTINCT 想和 ORDER BY 共存,那么 ORDER BY 子句中的字段就必须出现在选择列表中

> SELECT DISTINCT id,create_time FROM [tableName] ORDER BY create_time DESC;

### 4.6、将两张不关联的表,相同的字段一起查询出来使用 union all

> SELECT column_name1,column_name2 FROM [tableName1] union all SELECT column_name1,column_name2 FROM [tableName2];

- [例]

```text
SELECT partner_id,partner_name FROM company_info WHERE del_flag = 0 union all SELECT partner_id,partner_name FROM personal_info WHERE del_flag = 0;
```

### 4.7、将两张不关联的表并列展示数据 (注:此处不是上一条语句合并到一列中,而是展示两列不同的数据)

- [例]

```text
SELECT t1.partner_id,t1.partner_name,t2.partner_id,t2.partner_name
FROM (SELECT ROW_NUMBER() OVER ( ORDER BY partner_id ) id,partner_id,partner_name FROM company_info) t1
FULL JOIN
(SELECT ROW_NUMBER() OVER ( ORDER BY partner_id ) id,partner_id,partner_name FROM personal_info) t2 ON t1.id = t2.id;
```

### 4.8、添加库表字段并添加默认值,[DEFAULT 0]可加可不加

> ALTER TABLE [tableName] ADD [fieldName] [fieldType] DEFAULT 0 [约束条件];

- [例]

```text
ALTER TABLE ability_info ADD is_insulate int4 DEFAULT 0 NOT NULL;
```

### 4.9、删除库表字段

> ALTER TABLE [tableName] DROP [fieldName];

- [例]

```text
ALTER TABLE knowledge_base DROP serv_man_id;
```

### 4.10、修改库表字段

> ALTER TABLE [tableName] ALTER COLUMN [fieldName] TYPE [fieldType](100);

- [例]

```text
ALTER TABLE "public"."alarm_notify_person" ALTER COLUMN "email" TYPE varchar(50);
```

### 4.11、BETWEEN 操作符选取介于两个值之间的数据范围内的值,这些值可以是数值、文本或者日期.

> SELECT [fieldName](s) FROM [tableName] WHERE [fieldName] BETWEEN value1 AND value2;

- [例]

```text
DELETE FROM sql_service_info WHERE sql_service_id BETWEEN 3 AND 133;
```