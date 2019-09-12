
create table sys_user
(
 id int(11)  auto_increment PRIMARY KEY,
 username varchar(100) not null,
 is_admin int(2) not null default 0,
 disable int(2) not null default 0,
 remark varchar(200) null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table sys_role
(
 id int(11) auto_increment PRIMARY KEY,
 role varchar(200) not null,
 remark varchar(200) null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table sys_user_role
(
 user_id int(11) not null,
 role_id int(11) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table sys_role_menu
(
 role_id int(11) not null,
 menu_id int(11) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table login_log
(
 id int(11) AUTO_INCREMENT PRIMARY KEY,
 user_name varchar(200) not null,
 login_time VARCHAR(20) NOT NULL,
 IP VARCHAR(18) NULL,
 remark VARCHAR(500) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table work_flow_sql;
CREATE TABLE `work_flow_sql` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text,
  `data` text,
  `remark` text,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


create table `build_report` (
  id int(11) not null primary key AUTO_INCREMENT,
  params text ,
  remark text,
  menu_name text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (1, '用户列表', '/SystemController/userIndex', null, 0, null, -1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (2, '用户编辑', '/SystemController/editUser', null, 0, null, 1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (3, '删除用户', '/SystemController/deleteUser', null, 0, null, 1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (4, '新增用户', '/SystemController/insertUser', null, 0, null, 1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (5, '菜单列表', '/SystemController/menuIndex', null, 0, null, -1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (6, '报表', '/SystemController/buildReportIndex', null, 0, null, -1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (7, '用户修改', '/SystemController/updateUser', null, 0, null, 1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (8, '角色列表', '/SystemController/roleIndex', null, 0, null, -1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (9, '角色编辑', '/SystemController/editRole', null, 0, null, 8, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (10, '角色删除', '/SystemController/deleteRole', null, 0, null, 8, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (11, '角色新增', '/SystemController/insertRole', null, 0, null, 8, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (12, '角色修改', '/SystemController/updateRole', null, 0, null, 8, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (13, '编辑菜单', '/SystemController/editMenu', null, 0, null, 5, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (14, '删除菜单', '/SystemController/deleteMenu', null, 0, null, 5, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (15, '新增菜单', '/SystemController/insertMenu', null, 0, null, 5, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (16, '修改菜单', '/SystemController/updateMenu', null, 0, null, 5, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (17, '编辑报表', '/SystemController/editBuildReport', null, 0, null, 6, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (18, '查询报表', '/SystemController/queryBuildReport', null, 0, null, 6, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (19, '删除报表', '/SystemController/deleteBuildReport', null, 0, null, 6, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (20, '新增报表', '/SystemController/insertBuildReport', null, 0, null, 6, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (21, '流程式SQL', '/SystemController/sqlWorkFlowIndex', null, 0, null, -1, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (22, '编辑流程式SQL', '/SystemController/sqlWorkFlowEdit', null, 0, null, 21, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (23, '单个流程式SQL', '/SystemController/sqlWorkFlowById', null, 0, null, 21, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (24, '新增流程式SQL', '/SystemController/insertWorkSqlFlow', null, 0, null, 21, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (25, '修改流程式SQL', '/SystemController/updateWorkSqlFlow', null, 0, null, 21, 1, 1);
INSERT INTO sys_menu (id, menu_name, url, ioc, disable, remark, parent_id, level, sequence) VALUES (26, '删除流程式SQL', '/SystemController/deleteWorkSqlFlow', null, 0, null, 21, 1, 1);


INSERT INTO sys_role (id, role, remark) VALUES ('admin', null);


INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 1);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 2);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 3);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 4);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 5);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 6);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 7);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 8);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 9);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 10);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 11);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 12);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 13);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 14);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 15);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 16);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 17);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 18);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 19);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 20);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 21);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 22);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 23);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 24);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 25);
INSERT INTO sys_role_menu (role_id, menu_id) VALUES (1, 26);


INSERT INTO sys_user (id, username, is_admin, disable, remark) VALUES ('admin', 1, 0, null);