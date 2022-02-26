/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : building_control

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 26/02/2022 18:34:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bc_role
-- ----------------------------
DROP TABLE IF EXISTS `bc_role`;
CREATE TABLE `bc_role`  (
  `id` int NOT NULL,
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `name_zh` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名中文',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bc_role
-- ----------------------------
INSERT INTO `bc_role` VALUES (1, 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO `bc_role` VALUES (2, 'ROLE_ADMIN', '管理员');
INSERT INTO `bc_role` VALUES (3, 'ROLE_USER', '用户');

SET FOREIGN_KEY_CHECKS = 1;
