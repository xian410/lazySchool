/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : lazy_school

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 18/04/2023 18:16:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户主键',
  `money` float NOT NULL DEFAULT 0 COMMENT '账户余额',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `login_id`(`login_id`) USING BTREE,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '账户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (1, '15510267085', 0, 0, '', '2023-04-18 10:45:23', '2023-04-18 10:45:23');
INSERT INTO `account` VALUES (2, '13146897978', 40, 0, '', '2023-04-18 11:17:02', '2023-04-18 11:17:02');

-- ----------------------------
-- Table structure for dialogue
-- ----------------------------
DROP TABLE IF EXISTS `dialogue`;
CREATE TABLE `dialogue`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `dialogue_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息编号',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户主键',
  `form_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '来源用户Id',
  `details` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '对话内容',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dialogue_id`(`dialogue_id`) USING BTREE,
  INDEX `login_id`(`login_id`) USING BTREE,
  CONSTRAINT `dialogue_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会话表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for information
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户主键',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '名称',
  `nickname` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `student_number` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '学号',
  `gender` int(0) NOT NULL DEFAULT -1 COMMENT '性别',
  `telephone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '电话',
  `address` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '常用住址',
  `head_pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '头像地址',
  `college` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '学院',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '专业',
  `classes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '班级',
  `stars` double NOT NULL DEFAULT 0 COMMENT '评分',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `login_id`(`login_id`) USING BTREE,
  CONSTRAINT `information_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES (1, '15510267085', 'ljx', 'ljx', '195852', 1, '15510267085', '111\n\n\n111', '/file/a80f65225e7e4b06b754e596736cae68.jpg', '7', '', '', 0, 0, '', '2023-04-18 10:45:22', '2023-04-18 10:46:38');
INSERT INTO `information` VALUES (2, '13146897978', 'wf', 'wf', '195855', 0, '13146897978', '888', '/file/c8a39b4cae0a4a88a1503aefa9008fc9.jpg', '13', '', '', 0, 0, '', '2023-04-18 11:17:02', '2023-04-18 11:17:56');

-- ----------------------------
-- Table structure for login_user
-- ----------------------------
DROP TABLE IF EXISTS `login_user`;
CREATE TABLE `login_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录主键',
  `user_type` int(0) NOT NULL DEFAULT 0 COMMENT '用户类型',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '令牌',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `login_id`(`login_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户登录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_user
-- ----------------------------
INSERT INTO `login_user` VALUES (1, '15510267085', 0, '111111', '', 0, '', '2023-04-18 10:45:22', '2023-04-18 18:05:29');
INSERT INTO `login_user` VALUES (2, '13146897978', 0, '111111', 'token-82c45d30-10dc-4d67-a3c1-e7c457cdce73', 0, '', '2023-04-18 11:17:02', '2023-04-18 15:29:01');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `message_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息编号',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户主键',
  `good_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '货物主键',
  `from_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '来源用户Id',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '消息类型 0未知 1.有司机提出报价，2.货物已到达 3.报价被货主接受 4.被货主拒绝 5.已到账',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `message_id`(`message_id`) USING BTREE,
  INDEX `login_id`(`login_id`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统消息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 'message-680f0efe-367a-4fb1-a581-defcd5936c79', '15510267085', 'require-772ecb81-3a1e-4b4b-b7f9-e2c0b0d40109', '13146897978', 1, 0, '', '2023-04-18 15:26:57', '2023-04-18 15:26:57');
INSERT INTO `message` VALUES (2, 'message-3e89f93c-5b6e-4c76-a414-6755fea5289c', '15510267085', 'order-3a245967-dcb8-44fb-b0ac-a2ff959f9162', '13146897978', 2, 0, '', '2023-04-18 15:28:49', '2023-04-18 15:28:49');
INSERT INTO `message` VALUES (3, 'message-7bb05a8b-23ab-4a15-ad54-a3b101b80284', '13146897978', 'order-3a245967-dcb8-44fb-b0ac-a2ff959f9162', '15510267085', 3, 0, '', '2023-04-18 15:29:16', '2023-04-18 15:29:16');
INSERT INTO `message` VALUES (4, 'message-777eb6af-ccb0-4725-9159-33471db3c450', '15510267085', 'require-4c4c2d06-38bf-4cd4-8dcb-26038c0f74e5', '13146897978', 1, 0, '', '2023-04-18 18:05:45', '2023-04-18 18:05:45');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `require_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '需求编号',
  `customer_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户id',
  `driver_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配送人员id',
  `fees` int(0) NOT NULL DEFAULT 0 COMMENT '成交价格',
  `state` int(0) NOT NULL DEFAULT 0 COMMENT '状态，0配送中1送到',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE,
  INDEX `require_id`(`require_id`) USING BTREE,
  INDEX `driver_id`(`driver_id`) USING BTREE,
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`require_id`) REFERENCES `requires` (`require_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `orders_ibfk_3` FOREIGN KEY (`driver_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (4, 'order-8bf32e0c-966e-4a6f-b1a1-d97aec8d2a0b', 'require-4c4c2d06-38bf-4cd4-8dcb-26038c0f74e5', '15510267085', '13146897978', 12, 0, 0, '', '2023-04-18 18:05:45', '2023-04-18 18:05:45');

-- ----------------------------
-- Table structure for require_type
-- ----------------------------
DROP TABLE IF EXISTS `require_type`;
CREATE TABLE `require_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `type_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型编号',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '类型名称',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type_id`(`type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '需求类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of require_type
-- ----------------------------
INSERT INTO `require_type` VALUES (1, '1', '快递', 0, '', '2023-04-18 11:01:08', '2023-04-18 11:02:24');
INSERT INTO `require_type` VALUES (2, '2', '带饭', 0, '', '2023-04-18 11:01:11', '2023-04-18 11:02:26');
INSERT INTO `require_type` VALUES (3, '3', '打印', 0, '', '2023-04-18 11:01:13', '2023-04-18 11:02:46');
INSERT INTO `require_type` VALUES (4, '4', '搬运', 0, '', '2023-04-18 11:01:15', '2023-04-18 11:02:48');
INSERT INTO `require_type` VALUES (5, '5', '代取', 0, '', '2023-04-18 11:01:16', '2023-04-18 11:02:50');
INSERT INTO `require_type` VALUES (6, '6', '代购', 0, '', '2023-04-18 11:01:17', '2023-04-18 18:03:44');
INSERT INTO `require_type` VALUES (7, '7', '代办', 0, '', '2023-04-18 11:01:18', '2023-04-18 18:03:50');
INSERT INTO `require_type` VALUES (8, '8', '其它', 0, '', '2023-04-18 11:01:19', '2023-04-18 11:02:58');

-- ----------------------------
-- Table structure for requires
-- ----------------------------
DROP TABLE IF EXISTS `requires`;
CREATE TABLE `requires`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `login_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户id',
  `require_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '需求编号',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '需求名称',
  `type_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '需求类型编号',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '需求详细内容',
  `gender_limited` int(0) NOT NULL DEFAULT 0 COMMENT '是否限定男女，0不限定1男2女',
  `start` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '起点',
  `destination` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '目的地点',
  `fees` int(0) NOT NULL DEFAULT 0 COMMENT '运费要求',
  `deadline` datetime(0) NOT NULL COMMENT '最晚时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '备注',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '状态',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `require_id`(`require_id`) USING BTREE,
  INDEX `login_id`(`login_id`) USING BTREE,
  INDEX `type_id`(`type_id`) USING BTREE,
  CONSTRAINT `requires_ibfk_1` FOREIGN KEY (`login_id`) REFERENCES `login_user` (`login_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `requires_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `require_type` (`type_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '需求信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of requires
-- ----------------------------
INSERT INTO `requires` VALUES (5, '15510267085', 'require-4c4c2d06-38bf-4cd4-8dcb-26038c0f74e5', '代办', '7', '111', 0, '222', '333', 12, '2023-04-18 10:03:00', '不限', 1, 0, '', '2023-04-18 18:04:11', '2023-04-18 18:05:44');

-- ----------------------------
-- Table structure for trading_record
-- ----------------------------
DROP TABLE IF EXISTS `trading_record`;
CREATE TABLE `trading_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `trade_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '交易编号',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '笔记',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `trade_id`(`trade_id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE,
  CONSTRAINT `trading_record_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '交易记录表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
