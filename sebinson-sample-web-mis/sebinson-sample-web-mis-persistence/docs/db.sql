/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.5.40-0ubuntu1 : Database - gooagoo_sample
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gooagoo_sample` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `gooagoo_sample`;

/*Table structure for table `sample_menu` */

DROP TABLE IF EXISTS `sample_menu`;

CREATE TABLE `sample_menu` (
  `menu_id` varchar(32) NOT NULL COMMENT '菜单唯一标识，UUID',
  `menu_code` varchar(32) NOT NULL COMMENT '菜单编码',
  `menu_type` char(2) NOT NULL COMMENT '菜单类型：0 根菜单，1 级菜单，2 菜单项',
  `menu_name` varchar(32) NOT NULL COMMENT '菜单名称',
  `menu_status` char(1) NOT NULL COMMENT '菜单状态：0 可用，1 不可用',
  `menu_parent_id` varchar(32) DEFAULT NULL COMMENT '上级菜单标识',
  `menu_order` int(11) DEFAULT NULL COMMENT '同级菜单序号',
  `menu_uri` varchar(128) DEFAULT NULL COMMENT '菜单URI',
  `menu_actions` varchar(1024) DEFAULT NULL COMMENT '操作项的相对URI，权限随当前菜单，不可定制授权，多操作以''|''分割 eg：add.do|remove.do|update.do',
  `menu_desc` varchar(128) DEFAULT NULL COMMENT '菜单描述',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `INDEX_MENU_CODE` (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sample_menu` */

insert  into `sample_menu`(`menu_id`,`menu_code`,`menu_type`,`menu_name`,`menu_status`,`menu_parent_id`,`menu_order`,`menu_uri`,`menu_actions`,`menu_desc`) values ('1000','1000','0','系统管理','0','0000',NULL,NULL,NULL,NULL),('10001000','10001000','0','权限管理','0','1000',NULL,'/menu/index','add|delete|update|list.do',NULL),('10001001','10001001','0','角色管理','0','1000',NULL,'/role/index',NULL,NULL),('10001002','10001002','0','用户管理','0','1000',NULL,'/user/index',NULL,NULL);

/*Table structure for table `sample_menu_operation` */

DROP TABLE IF EXISTS `sample_menu_operation`;

CREATE TABLE `sample_menu_operation` (
  `oper_id` varchar(32) NOT NULL COMMENT '操作标识 UUID',
  `menu_id` varchar(32) NOT NULL COMMENT '所属菜单标标识',
  `oper_code` varchar(32) DEFAULT NULL COMMENT '操作码',
  `oper_name` varchar(32) DEFAULT NULL COMMENT '操作名称',
  `oper_type` varchar(16) DEFAULT NULL COMMENT '操作类型',
  `oper_actions` varchar(1024) DEFAULT NULL COMMENT '操作URI',
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sample_menu_operation` */

insert  into `sample_menu_operation`(`oper_id`,`menu_id`,`oper_code`,`oper_name`,`oper_type`,`oper_actions`) values ('100010001001','10001000','100010001001','新增权限','add','add'),('100010001002','10001000','100010001002','删除权限','remove','delete'),('100010001003','10001000','100010001003','更新权限','edit','update');

/*Table structure for table `sample_role` */

DROP TABLE IF EXISTS `sample_role`;

CREATE TABLE `sample_role` (
  `role_id` varchar(32) NOT NULL COMMENT 'UUID角色标识',
  `role_code` varchar(32) NOT NULL COMMENT '角色编号，规律性编码，要求唯一性',
  `role_name` varchar(32) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(128) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sample_role` */

insert  into `sample_role`(`role_id`,`role_code`,`role_name`,`role_desc`) values ('1000','1000','系统管理',NULL);

/*Table structure for table `sample_role_menu` */

DROP TABLE IF EXISTS `sample_role_menu`;

CREATE TABLE `sample_role_menu` (
  `role_id` varchar(32) NOT NULL COMMENT '角色标识',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单标识',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sample_role_menu` */

insert  into `sample_role_menu`(`role_id`,`menu_id`) values ('1000','1000'),('1000','10001000');

/*Table structure for table `sample_user` */

DROP TABLE IF EXISTS `sample_user`;

CREATE TABLE `sample_user` (
  `user_id` varchar(32) NOT NULL COMMENT 'UUID 唯一标识',
  `user_name` varchar(32) NOT NULL COMMENT '用户名，账户名，登入名',
  `password` varchar(32) NOT NULL COMMENT '登入密码',
  `cdt` datetime DEFAULT NULL COMMENT '创建日期',
  `udt` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sample_user` */

insert  into `sample_user`(`user_id`,`user_name`,`password`,`cdt`,`udt`) values ('10000000000000000000000000000000','gooagoo','202CB962AC59075B964B07152D234B70','2014-12-17 12:39:17','2014-12-17 12:39:22'),('10000000000000000000000000000001','admin','202CB962AC59075B964B07152D234B70','2014-12-17 17:13:39','2014-12-17 17:13:43');

/*Table structure for table `sample_user_role` */

DROP TABLE IF EXISTS `sample_user_role`;

CREATE TABLE `sample_user_role` (
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `remarks` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sample_user_role` */

insert  into `sample_user_role`(`user_id`,`role_id`,`remarks`) values ('10000000000000000000000000000000','1000',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
