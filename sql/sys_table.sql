
create table sys_user
(
 id int(11)  auto_increment PRIMARY KEY,
 username varchar(100) not null,
 is_admin int(2) not null default 0,
 disable int(2) not null default 0,
 remark varchar(200) null
);


create table sys_role
(
 id int(11) auto_increment PRIMARY KEY,
 role varchar(200) not null,
 remark varchar(200) null
);

create table sys_user_role
(
 user_id int(11) not null,
 role_id int(11) not null
);

drop table sys_menu;
create table sys_menu
(
 id int(11) auto_increment PRIMARY KEY,
 menu_name varchar(100) not null,
 url varchar(500) not null,
 ioc varchar(100) null,
 disable int(2) not null default 0,
 remark varchar(500) null,
 parent_id int(11) not null default -1,
 level int(11) not null DEFAULT  1,
 sequence int(11) not null DEFAULT 1
);

create table sys_role_menu
(
 role_id int(11) not null,
 menu_id int(11) not null
);

create table login_log
(
 id int(11) AUTO_INCREMENT PRIMARY KEY,
 user_name varchar(200) not null,
 login_time VARCHAR(20) NOT NULL,
 IP VARCHAR(18) NULL,
 remark VARCHAR(500) NULL
);


drop table sys_schedule;
create table sys_schedule(
 job_id int(11) AUTO_INCREMENT not null PRIMARY KEY,
 job_name varchar(200) not null,
 job_group varchar(200) default 'job_group',
 job_status int(2) not null default 1 comment '启动状态 1、启动，0、不启动',
 cron_expression varchar(100) not null comment 'cron表达示，quartz',
 bean_class varchar(200) not null comment '包名加类名',
 method_name varchar(200) not null comment '要调用的方法名',
 is_concurrent int(2) not null default 0 comment '同一方法是否可以同时执行，1、可以，0、不可以',
 description varchar(1000) null comment '描述',
 is_param int(2) not null DEFAULT 0 comment '任务启动是否需要参数，默认0不需要，1 需要',
 json_param varchar(1000) DEFAULT NULL comment 'json格式的参数，一般方法中需要一个string类型的json格式的参数时使用',
 start_time int(11) DEFAULT  null comment '任务的开始时间，为空时表示马上启动',
 end_time int(11) DEFAULT  null comment '任务的结束时间，为空时，表示任务一直运行下去',
 create_date int(11) not null comment '创建时间',
 update_date int(11) not null comment '修改时间'
);

drop table work_flow_sql;
CREATE TABLE `work_flow_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `data` text,
  `remark` text,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `trade_simple` (
  `date` int(11) NOT NULL,
  `trade_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` tinyint(2) DEFAULT NULL COMMENT '0表示非首次交易，1表示首次交易',
  KEY `trade_simple_idx_date` (`date`),
  KEY `trade_simple_idx_user_id` (`user_id`),
  KEY `trade_simple_idx_trade_id` (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;