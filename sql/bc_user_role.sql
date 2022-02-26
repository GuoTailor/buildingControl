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

 Date: 26/02/2022 18:34:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bc_user_role
-- ----------------------------
DROP TABLE IF EXISTS `bc_user_role`;
CREATE TABLE `bc_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fc_user_role_role_id_fkey`(`role_id`) USING BTREE,
  INDEX `fc_user_role_user_id_fkey`(`user_id`) USING BTREE,
  CONSTRAINT `fc_user_role_role_id_fkey` FOREIGN KEY (`role_id`) REFERENCES `bc_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fc_user_role_user_id_fkey` FOREIGN KEY (`user_id`) REFERENCES `bc_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bc_user_role
-- ----------------------------
INSERT INTO `bc_user_role` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
