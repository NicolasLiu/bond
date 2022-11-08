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
-- Table structure for opponent
-- ----------------------------
DROP TABLE IF EXISTS `opponent`;
CREATE TABLE `opponent` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `bond_credit_limit` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `institution_credit_limit` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `issuer_prefer` longtext COLLATE utf8mb4_unicode_ci,
  `issuer_exclude` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of opponent
-- ----------------------------
INSERT INTO `opponent` VALUES ('1', '默认对手', '1', '0,C,CC,CCC,B-,B,B+,BB-,BB,BB+,BBB-,BBB,BBB+,A-1,A-,A,A+,AA-,AA,AA+,AAA', '0,C,CC,CCC,CCC+,B-,B,B+,BB-,BB,BB+,BBB-,BBB,BBB+,A-1,A-,A,A+,AA-,AA,AA+,AAA,A1,AApi,AA+pi,A3,AAApi,Ba1,Ba2,Baa1,Baa2,Baa3,Caa1',NULL,NULL);

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
INSERT INTO `account` VALUES ('1', '默认用户', '', '', '', '');

-- ----------------------------
-- Table structure for apply
-- ----------------------------
DROP TABLE IF EXISTS `apply`;
CREATE TABLE `apply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `account` int DEFAULT NULL,
  `opponent` int DEFAULT NULL,
  `temporary_opponent` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `trader` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `discount_rate` double DEFAULT NULL,
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
  `issuer_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`bond_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of bond_info
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apply` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  `value` double DEFAULT NULL,
  `discount_rate` double DEFAULT NULL,
  `discount_value` double DEFAULT NULL,
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

-- ----------------------------
-- Table structure for issuer
-- ----------------------------
DROP TABLE IF EXISTS `issuer`;
CREATE TABLE `issuer` (
  `issuer_name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  UNIQUE KEY `uniq` (`issuer_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;