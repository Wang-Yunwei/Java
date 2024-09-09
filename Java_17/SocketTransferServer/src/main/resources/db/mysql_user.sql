DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
    `ID`                bigint(20)            NOT  NULL   COMMENT 'ID',
    `USER_NAME`         varchar(20)           NOT  NULL   COMMENT '用户名',
    `PASSWORD`          varchar(20)           NOT  NULL   COMMENT '密码',
    `EMAIL`             varchar(20)           NOT  NULL   COMMENT '邮箱',
    `FIXED_PHONE`       varchar(20)       DEFAULT  NULL   COMMENT '固定电话',
    `MOBILE_PHONE`      varchar(11)           NOT  NULL   COMMENT '移动电话',
    `REAL_NAME`         varchar(20)           NOT  NULL   COMMENT '真实姓名',
    `ID_NUMBER`         varchar(20)           NOT  NULL   COMMENT '身份证号',
    `ADDRESS`           varchar(50)           NOT  NULL   COMMENT '住址',
    `CREATED_DATE`      datetime          DEFAULT  NOW()  COMMENT '创建时间',
    `CREATED_BY`        bigint(20)        DEFAULT  NULL   COMMENT '创建者',
    `CREATED_NAME`      varchar(20)       DEFAULT  NULL   COMMENT '创建者用户名',
    `UPDATE_DATE`       datetime          DEFAULT  NOW()  COMMENT '更新时间',
    `UPDATE_BY`         bigint(20)        DEFAULT  NULL   COMMENT '更新人',
    `UPDATE_NAME`       varchar(20)       DEFAULT  NULL   COMMENT '更新人名称',
    `COMPANY_CODE`      varchar(50)       DEFAULT  NULL   COMMENT '单位编码',
    `COMPANY_NAME`      varchar(100)      DEFAULT  NULL   COMMENT '单位简称',
    `SECOND_ORG_CODE`   varchar(50)       DEFAULT  NULL   COMMENT '二级组织编码',
    `SECOND_ORG_NAME`   varchar(100)      DEFAULT  NULL   COMMENT '二级组织简称',
    `ORG_CODE`          varchar(50)       DEFAULT  NULL   COMMENT '组织编码',
    `ORG_NAME`          varchar(100)      DEFAULT  NULL   COMMENT '组织简称',
    `DELETE_FLAG`       tinyint(4)        DEFAULT  0      COMMENT '删除标识(0:未删除,1:已删除)',
    PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';


DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_role` (
    `ID`                bigint(20)            NOT  NULL   COMMENT 'ID',
    `USER_ID`           bigint(20)            NOT  NULL   COMMENT '用户ID',
    `ROLE_ID`           bigint(20)            NOT  NULL   COMMENT '角色ID',
    `CREATED_DATE`      datetime          DEFAULT  NOW()  COMMENT '创建时间',
    `CREATED_BY`        bigint(20)        DEFAULT  NULL   COMMENT '创建者',
    `CREATED_NAME`      varchar(20)       DEFAULT  NULL   COMMENT '创建者用户名',
    `UPDATE_DATE`       datetime          DEFAULT  NOW()  COMMENT '更新时间',
    `UPDATE_BY`         bigint(20)        DEFAULT  NULL   COMMENT '更新人',
    `UPDATE_NAME`       varchar(20)       DEFAULT  NULL   COMMENT '更新人名称'
    `DELETE_FLAG`       tinyint(4)        DEFAULT  0      COMMENT '删除标识(0:未删除,1:已删除)',
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与角色中间表';


DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
    `ID`                bigint(20)            NOT  NULL   COMMENT 'ID',
    `NAME`              bigint(20)            NOT  NULL   COMMENT '名称',
    `CODE`              bigint(20)            NOT  NULL   COMMENT '编码',
    `CREATED_DATE`      datetime          DEFAULT  NOW()  COMMENT '创建时间',
    `CREATED_BY`        bigint(20)        DEFAULT  NULL   COMMENT '创建者',
    `CREATED_NAME`      varchar(20)       DEFAULT  NULL   COMMENT '创建者用户名',
    `UPDATE_DATE`       datetime          DEFAULT  NOW()  COMMENT '更新时间',
    `UPDATE_BY`         bigint(20)        DEFAULT  NULL   COMMENT '更新人',
    `UPDATE_NAME`       varchar(20)       DEFAULT  NULL   COMMENT '更新人名称',
    `COMPANY_CODE`      varchar(50)       DEFAULT  NULL   COMMENT '单位编码',
    `COMPANY_NAME`      varchar(100)      DEFAULT  NULL   COMMENT '单位简称',
    `SECOND_ORG_CODE`   varchar(50)       DEFAULT  NULL   COMMENT '二级组织编码',
    `SECOND_ORG_NAME`   varchar(100)      DEFAULT  NULL   COMMENT '二级组织简称',
    `ORG_CODE`          varchar(50)       DEFAULT  NULL   COMMENT '组织编码',
    `ORG_NAME`          varchar(100)      DEFAULT  NULL   COMMENT '组织简称',
    `DELETE_FLAG`       tinyint(4)        DEFAULT  0      COMMENT '删除标识(0:未删除,1:已删除)',
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

DROP TABLE IF EXISTS `auth_role_resource`;
CREATE TABLE `auth_resource` (
    `ID`                bigint(20)            NOT  NULL   COMMENT 'ID',
    `ROLE_ID`           bigint(20)            NOT  NULL   COMMENT '角色ID',
    `RESOURCE_ID`       bigint(20)            NOT  NULL   COMMENT '资源ID',
    `CREATED_DATE`      datetime          DEFAULT  NOW()  COMMENT '创建时间',
    `CREATED_BY`        bigint(20)        DEFAULT  NULL   COMMENT '创建者',
    `CREATED_NAME`      varchar(20)       DEFAULT  NULL   COMMENT '创建者用户名',
    `UPDATE_DATE`       datetime          DEFAULT  NOW()  COMMENT '更新时间',
    `UPDATE_BY`         bigint(20)        DEFAULT  NULL   COMMENT '更新人',
    `UPDATE_NAME`       varchar(20)       DEFAULT  NULL   COMMENT '更新人名称'
    `DELETE_FLAG`       tinyint(4)        DEFAULT  0      COMMENT '删除标识(0:未删除,1:已删除)',
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与资源中间表';

DROP TABLE IF EXISTS `auth_resource`;
CREATE TABLE `auth_resource` (
    `ID`                bigint(20)            NOT  NULL   COMMENT 'ID',
    `MODULE_NAME`       varchar(20)           NOT  NULL   COMMENT '模块名',
    `MODULE_CODE`       varchar(20)           NOT  NULL   COMMENT '模块编码',
    `OPERATE_NAME`      varchar(20)           NOT  NULL   COMMENT '操作名 (增, 删, 改, 查)',
    `OPERATE_CODE`      varchar(20)           NOT  NULL   COMMENT '操作编码 (I, D, U, S)',
    `CREATED_DATE`      datetime          DEFAULT  NOW()  COMMENT '创建时间',
    `CREATED_BY`        bigint(20)        DEFAULT  NULL   COMMENT '创建者',
    `CREATED_NAME`      varchar(20)       DEFAULT  NULL   COMMENT '创建者用户名',
    `UPDATE_DATE`       datetime          DEFAULT  NOW()  COMMENT '更新时间',
    `UPDATE_BY`         bigint(20)        DEFAULT  NULL   COMMENT '更新人',
    `UPDATE_NAME`       varchar(20)       DEFAULT  NULL   COMMENT '更新人名称',
    `COMPANY_CODE`      varchar(50)       DEFAULT  NULL   COMMENT '单位编码',
    `COMPANY_NAME`      varchar(100)      DEFAULT  NULL   COMMENT '单位简称',
    `SECOND_ORG_CODE`   varchar(50)       DEFAULT  NULL   COMMENT '二级组织编码',
    `SECOND_ORG_NAME`   varchar(100)      DEFAULT  NULL   COMMENT '二级组织简称',
    `ORG_CODE`          varchar(50)       DEFAULT  NULL   COMMENT '组织编码',
    `ORG_NAME`          varchar(100)      DEFAULT  NULL   COMMENT '组织简称',
    `DELETE_FLAG`       tinyint(4)        DEFAULT  0      COMMENT '删除标识(0:未删除,1:已删除)',
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';
