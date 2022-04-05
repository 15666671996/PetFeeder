/*
 Navicat Premium Data Transfer

 Source Server         : MySQL(Azure Linux Server PetFeeder)
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : 23.96.114.177:3306
 Source Schema         : pet_feeder

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/04/2022 23:25:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `userId` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`userId`, `time`) USING BTREE,
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('67069ad80b984e9aa9ab884b0f6262c2', '04:00');
INSERT INTO `task` VALUES ('67069ad80b984e9aa9ab884b0f6262c2', '12:20');
INSERT INTO `task` VALUES ('67069ad80b984e9aa9ab884b0f6262c2', '12:30');
INSERT INTO `task` VALUES ('67069ad80b984e9aa9ab884b0f6262c2', '18:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`userId`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('67069ad80b984e9aa9ab884b0f6262c2', 'tester', '123456');

SET FOREIGN_KEY_CHECKS = 1;