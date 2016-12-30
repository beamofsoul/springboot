/*
Navicat MySQL Data Transfer

Source Server         : LocalMySQL
Source Server Version : 50716
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50716
File Encoding         : 65001

Date: 2016-12-30 09:16:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_detail_control
-- ----------------------------
DROP TABLE IF EXISTS `t_detail_control`;
CREATE TABLE `t_detail_control` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `entity_class` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `unavailable_columns` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `filter_rules` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `priority` smallint(6) DEFAULT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_detail_control
-- ----------------------------
INSERT INTO `t_detail_control` VALUES ('1', '1', 'com.beamofsoul.springboot.entity.User', 'password,sex,status', null, '1', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57');

-- ----------------------------
-- Table structure for t_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `available` bit(1) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `resource_type` enum('menu','button') COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `action` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '用户管理', '', '1', 'menu', 'user/list', 'user:list', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_permission` VALUES ('2', '用户添加', '', '1', 'button', 'user/add', 'user:add', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_permission` VALUES ('3', '用户删除', '', '1', 'button', 'user/delete', 'user:delete', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_permission` VALUES ('4', '用户修改', '', '1', 'button', 'user/update', 'user:update', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_permission` VALUES ('5', '系统管理', '', '5', 'menu', 'sys/list', 'sys:list', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_permission` VALUES ('6', '系统添加', '', '5', 'button', 'sys/add', 'sys:add', '2016-12-08 14:47:57', '2016-12-08 14:47:57');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', 'admin', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null);
INSERT INTO `t_role` VALUES ('2', 'manager', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null);
INSERT INTO `t_role` VALUES ('3', 'normal', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null);

-- ----------------------------
-- Table structure for t_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `permission` tinyblob,
  `role` tinyblob,
  PRIMARY KEY (`id`),
  KEY `FKjobmrl6dorhlfite4u34hciik` (`permission_id`),
  KEY `FK90j038mnbnthgkc17mqnoilu9` (`role_id`),
  CONSTRAINT `FK90j038mnbnthgkc17mqnoilu9` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKjobmrl6dorhlfite4u34hciik` FOREIGN KEY (`permission_id`) REFERENCES `t_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------
INSERT INTO `t_role_permission` VALUES ('1', '1', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_role_permission` VALUES ('2', '2', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_role_permission` VALUES ('3', '3', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_role_permission` VALUES ('4', '4', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_role_permission` VALUES ('5', '5', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_role_permission` VALUES ('6', '6', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_role_permission` VALUES ('7', '1', '3', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'haven', '2016-11-03 14:19:56', '123456', '', 'tom', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_user` VALUES ('2', 'hell', '2016-11-03 14:20:32', '123456', '', 'jack', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57');
INSERT INTO `t_user` VALUES ('3', 'hell', '2016-11-03 14:20:50', '123456', '\0', 'rose', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `role` tinyblob,
  `user` tinyblob,
  PRIMARY KEY (`id`),
  KEY `FKa9c8iiy6ut0gnx491fqx4pxam` (`role_id`),
  KEY `FKq5un6x7ecoef5w1n39cop66kl` (`user_id`),
  CONSTRAINT `FKa9c8iiy6ut0gnx491fqx4pxam` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`),
  CONSTRAINT `FKq5un6x7ecoef5w1n39cop66kl` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_user_role` VALUES ('2', '3', '2', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
INSERT INTO `t_user_role` VALUES ('3', '2', '3', '2016-12-08 14:47:57', '2016-12-08 14:47:57', null, null);
