create database if not exists `hzx` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `hzx`;

-- /客服信息表
drop table if exists `tbl_user_info`;
create table `tbl_user_info` (
	`id` int(11) unsigned not null auto_increment comment '主键id',
	`username` varchar(64) not null comment '登录账号',
  `password` varchar(64) not null comment '密码',
	`company` varchar(64) not null comment '所属公司',
	`nickname` varchar(64) not null comment '昵称',
	`create_time` datetime not null comment '创建时间',
	`update_time` datetime not null comment '更新时间',
	`deleted` int(1) not null comment '逻辑删除标记',
	primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- /客服信息扩展表
drop table if exists `tbl_user_info_expand`;
create table `tbl_user_info_expand` (
	`id` int(11) unsigned not null auto_increment comment '主键id',
	`info_id` int(11) unsigned not null comment '客服信息表id', 
	`external_nickname` varchar(64) not null comment '对外显示昵称',
  `email` varchar(64) not null comment '邮箱',
	`phone` varchar(64) not null comment '电话',
	`country` varchar(64) not null comment '国家',
	`default_language` varchar(64) not null comment '默认语言',
	primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;