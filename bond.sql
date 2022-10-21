/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80025
Source Host           : localhost:3306
Source Database       : bond

Target Server Type    : MYSQL
Target Server Version : 80025
File Encoding         : 65001

Date: 2022-10-21 11:02:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `internal_account_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `internal_account_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `external_account_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `external_account_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '默认用户', 'lcd_排券_银行间', 'lcd_pq_yhj', 'lcd_中债登_排券', 'lcd_wzq_pq1');

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account` int DEFAULT NULL,
  `opponent` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `temporary_opponent` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trader` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `financing_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `financing_rate` double DEFAULT NULL,
  `clearing_speed` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `initial_settlement_method` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expiry_settlement_method` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `value` double DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `recording_day` date DEFAULT NULL,
  `trading_day` date DEFAULT NULL,
  `maturity_day` date DEFAULT NULL,
  `aim` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mark` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `emergency` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of apply
-- ----------------------------

-- ----------------------------
-- Table structure for bond_info
-- ----------------------------
DROP TABLE IF EXISTS `bond_info`;
CREATE TABLE `bond_info` (
  `bond_code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `bond_trading_market` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `short_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `maturity` datetime DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bond_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `institution_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city_debt` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bond_credit` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `institution_credit` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remaining_period` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `valuation_rate` double DEFAULT NULL,
  `callable_bond_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bond_market_type` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `issue_scale` decimal(10,2) DEFAULT NULL,
  `bond_tenor` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `repayment_principal_mode` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `major_underwriter` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `guarantee_mode` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `guarantee_person` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `interest_mode` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `face_rate` double DEFAULT NULL,
  `underlying_index` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `underlying_rate` double DEFAULT NULL,
  `issue_yield` double DEFAULT NULL,
  `issue_price` double DEFAULT NULL,
  `pay_interest_frequency` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `accrual_interest_frequency` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rating_agency` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `have_credit` tinyint(1) DEFAULT NULL,
  `same_bonds_in_diff_market` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`bond_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of bond_info
-- ----------------------------
INSERT INTO `bond_info` VALUES ('132100052', '', '21重庆轨交GN003(碳中和债)', '2026-05-11 00:00:00', '重庆市轨道交通(集团)有限公司2021年度第三期绿色中期票据(碳中和债)', '企业债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102101455', '', '21南京交建MTN003', '2026-08-04 00:00:00', '南京市交通建设投资控股(集团)有限责任公司2021年度第三期中期票据', '企业债', null, null, 'AA', 'AA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102100237', '', '21川铁投MTN003', null, null, '企业债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('2205028', '', '22北京债06', null, null, '地方政府债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('2105244', '', '21江苏债09', null, null, '地方政府债', null, null, 'AA', 'AA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('2205029', '', '22北京债07', null, null, '地方政府债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('2280250', '', '22金桥债01', null, null, '企业债', null, null, 'AA', 'AA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('2180370', '', '21山能债02', null, null, '企业债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102281087', '', '22大唐新能MTN001', null, null, '企业债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102280888', '', '22皖出版MTN002', null, null, '企业债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102282010', '', '22中电国际MTN004', null, null, '企业债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('032100339', '', '21江阴城投PPN003', null, null, '企业债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('032100189', '', '21富阳城投PPN001', null, null, '企业债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('031900114', '', '19南昌水投PPN001', null, null, '企业债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('220215', '', '22国开15', null, null, '国债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102282143', '', '22浦东开发MTN001', null, null, '企业债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('220016', '', '22附息国债16', null, null, '国债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('2271504', '', '22广东债44', null, null, '地方政府债', null, null, 'AAA', 'AAA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102001276', '', '20大唐发电MTN002', null, null, '企业债', null, null, 'AA', 'AA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102001222', '', '20华能MTN002', null, null, '企业债', null, null, 'AA', 'AA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102000871', '', '20招商局MTN001A', null, null, '企业债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('102000761', '', '20保利发展MTN003', null, null, '企业债', null, null, 'AA+', 'AA+', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `bond_info` VALUES ('082280730', '', '22蓉交投ABN002优先', null, null, '企业债', null, null, 'AA', 'AA', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apply` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  `value` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of orders
-- ----------------------------


-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account` int DEFAULT NULL,
  `value` double DEFAULT NULL,
  `opponent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `market` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `initial_settlement_day` date DEFAULT NULL,
  `expiry_settlement_day` date DEFAULT NULL,
  `interest_rate` double DEFAULT NULL,
  `pledge_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of position
-- ----------------------------
