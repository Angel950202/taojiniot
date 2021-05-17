/*
 Navicat Premium Data Transfer

 Source Server         : 119.3.232.132
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : 119.3.232.132:3306
 Source Schema         : fojiya-iot

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 09/01/2020 10:14:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for iot_agreement_rc701
-- ----------------------------
DROP TABLE IF EXISTS `iot_agreement_rc701`;
CREATE TABLE `iot_agreement_rc701`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `command_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 140 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_agreement_rc701_value
-- ----------------------------
DROP TABLE IF EXISTS `iot_agreement_rc701_value`;
CREATE TABLE `iot_agreement_rc701_value`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `address_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `command_str` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `command_value` int(11) NULL DEFAULT NULL,
  `date_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `equip_type` int(11) NULL DEFAULT NULL,
  `history_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `iccid` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45123 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_charts_type
-- ----------------------------
DROP TABLE IF EXISTS `iot_charts_type`;
CREATE TABLE `iot_charts_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `echart_type` int(11) NOT NULL,
  `echart_type_params` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_company_profile
-- ----------------------------
DROP TABLE IF EXISTS `iot_company_profile`;
CREATE TABLE `iot_company_profile`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `count` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment`;
CREATE TABLE `iot_equipment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `alarm_count` int(11) NOT NULL,
  `device_position` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_position_lat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_position_lng` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `duration` int(11) NOT NULL,
  `equipment_ipaddress` tinyblob NULL,
  `equipment_protocol` int(11) NOT NULL,
  `id_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `line_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `off_time` int(11) NOT NULL,
  `serial_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `share` int(11) NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  `state_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_station_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_station_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `yield_target` int(11) NOT NULL,
  `charts_type_id` bigint(20) NULL DEFAULT NULL,
  `equipment_ico_id` bigint(20) NULL DEFAULT NULL,
  `classs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `material_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `production_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `standard_quantity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment_ipaddress_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_number`(`id_number`) USING BTREE,
  UNIQUE INDEX `serial_number`(`serial_number`) USING BTREE,
  INDEX `FK88CE3E9D18DD4DDB`(`equipment_ico_id`) USING BTREE,
  INDEX `FK88CE3E9D6B066C64`(`charts_type_id`) USING BTREE,
  INDEX `FK88CE3E9D7B1B9A1B`(`equipment_ipaddress_id`) USING BTREE,
  CONSTRAINT `FK88CE3E9D7B1B9A1B` FOREIGN KEY (`equipment_ipaddress_id`) REFERENCES `iot_equipment_ipaddress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `iot_equipment_ibfk_1` FOREIGN KEY (`equipment_ico_id`) REFERENCES `iot_equipment_ico` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `iot_equipment_ibfk_2` FOREIGN KEY (`charts_type_id`) REFERENCES `iot_charts_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 343 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_ico
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_ico`;
CREATE TABLE `iot_equipment_ico`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `ico_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ico_type` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `online_ico` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `issystem` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 224 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_ipaddress
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_ipaddress`;
CREATE TABLE `iot_equipment_ipaddress`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `ip_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ip_port` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ip_address`(`ip_address`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_maintenance
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_maintenance`;
CREATE TABLE `iot_equipment_maintenance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_report
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_report`;
CREATE TABLE `iot_equipment_report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `boot_up_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_trs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `failure_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `line_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `running_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_station_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_station_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50091 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_sensor
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_sensor`;
CREATE TABLE `iot_equipment_sensor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `channel` int(11) NULL DEFAULT NULL,
  `id_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `serial_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sign` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment_id` bigint(20) NULL DEFAULT NULL,
  `equipment_ico_id` bigint(20) NULL DEFAULT NULL,
  `equipment_sensor_params_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id_number`(`id_number`) USING BTREE,
  INDEX `FK3CD8D79C18DD4DDB`(`equipment_ico_id`) USING BTREE,
  INDEX `FK3CD8D79CD8C63A30`(`equipment_sensor_params_id`) USING BTREE,
  INDEX `FK3CD8D79CC0E2C6DC`(`equipment_id`) USING BTREE,
  CONSTRAINT `iot_equipment_sensor_ibfk_1` FOREIGN KEY (`equipment_ico_id`) REFERENCES `iot_equipment_ico` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `iot_equipment_sensor_ibfk_2` FOREIGN KEY (`equipment_id`) REFERENCES `iot_equipment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `iot_equipment_sensor_ibfk_3` FOREIGN KEY (`equipment_sensor_params_id`) REFERENCES `iot_equipment_sensor_params` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1911 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_sensor_params
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_sensor_params`;
CREATE TABLE `iot_equipment_sensor_params`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parameter` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_sensor_state
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_sensor_state`;
CREATE TABLE `iot_equipment_sensor_state`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `sensor_id_number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sensor_true_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_values` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sensor_id_number`(`sensor_id_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_trigger
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_trigger`;
CREATE TABLE `iot_equipment_trigger`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `equipment_alarm_type_enum` int(11) NOT NULL,
  `is_transfer` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `target` int(11) NOT NULL,
  `target_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `upper_boundc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment_id` bigint(20) NULL DEFAULT NULL,
  `equipment_sensor_id` bigint(20) NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `target_type` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKA90B4E161A9ACB99`(`equipment_sensor_id`) USING BTREE,
  INDEX `FKA90B4E16C0E2C6DC`(`equipment_id`) USING BTREE,
  CONSTRAINT `iot_equipment_trigger_ibfk_1` FOREIGN KEY (`equipment_sensor_id`) REFERENCES `iot_equipment_sensor` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `iot_equipment_trigger_ibfk_2` FOREIGN KEY (`equipment_id`) REFERENCES `iot_equipment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_trigger_log
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_trigger_log`;
CREATE TABLE `iot_equipment_trigger_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `faile_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `send_state` int(11) NOT NULL,
  `target` int(11) NULL DEFAULT NULL,
  `trigger_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `trigger_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment_trigger_id` bigint(20) NULL DEFAULT NULL,
  `production_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `production_model` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `work_station_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `alert_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classs` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `line` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `material_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK7817259B6EC4CE7B`(`equipment_trigger_id`) USING BTREE,
  CONSTRAINT `iot_equipment_trigger_log_ibfk_1` FOREIGN KEY (`equipment_trigger_id`) REFERENCES `iot_equipment_trigger` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4746 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_type
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_type`;
CREATE TABLE `iot_equipment_type`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `icopath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_equipment_update
-- ----------------------------
DROP TABLE IF EXISTS `iot_equipment_update`;
CREATE TABLE `iot_equipment_update`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `file_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_maintenance
-- ----------------------------
DROP TABLE IF EXISTS `iot_maintenance`;
CREATE TABLE `iot_maintenance`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_maintenance_detail1
-- ----------------------------
DROP TABLE IF EXISTS `iot_maintenance_detail1`;
CREATE TABLE `iot_maintenance_detail1`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `standard` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_maintenance_detail2
-- ----------------------------
DROP TABLE IF EXISTS `iot_maintenance_detail2`;
CREATE TABLE `iot_maintenance_detail2`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `advance` int(11) NOT NULL,
  `cycle` int(11) NOT NULL,
  `equipment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_maintenance_record
-- ----------------------------
DROP TABLE IF EXISTS `iot_maintenance_record`;
CREATE TABLE `iot_maintenance_record`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `clesss` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenance_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `maintenancer` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `production_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `record_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_maintenance_record_datail
-- ----------------------------
DROP TABLE IF EXISTS `iot_maintenance_record_datail`;
CREATE TABLE `iot_maintenance_record_datail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `record_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `result` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `standard` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_plugin_config
-- ----------------------------
DROP TABLE IF EXISTS `iot_plugin_config`;
CREATE TABLE `iot_plugin_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `orders` int(11) NULL DEFAULT NULL,
  `is_enabled` bit(1) NOT NULL,
  `plugin_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `plugin_id`(`plugin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_plugin_config_attribute
-- ----------------------------
DROP TABLE IF EXISTS `iot_plugin_config_attribute`;
CREATE TABLE `iot_plugin_config_attribute`  (
  `plugin_config` bigint(20) NOT NULL,
  `attributes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`plugin_config`, `name`) USING BTREE,
  INDEX `FKA3A6303A7FD80775`(`plugin_config`) USING BTREE,
  CONSTRAINT `iot_plugin_config_attribute_ibfk_1` FOREIGN KEY (`plugin_config`) REFERENCES `iot_plugin_config` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_production_line
-- ----------------------------
DROP TABLE IF EXISTS `iot_production_line`;
CREATE TABLE `iot_production_line`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creater_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ct` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `line_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `progress_target` int(11) NOT NULL,
  `trs_target` int(11) NOT NULL,
  `yield_target` int(11) NOT NULL,
  `dtu_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_quality_inspection
-- ----------------------------
DROP TABLE IF EXISTS `iot_quality_inspection`;
CREATE TABLE `iot_quality_inspection`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `receipt_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_quality_inspection_detail
-- ----------------------------
DROP TABLE IF EXISTS `iot_quality_inspection_detail`;
CREATE TABLE `iot_quality_inspection_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `bad_count` int(11) NULL DEFAULT NULL,
  `bad_describe` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bad_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspection_count` int(11) NULL DEFAULT NULL,
  `inspection_standard_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspection_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspector` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `material_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `material_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `qualified` int(11) NULL DEFAULT NULL,
  `receipt_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `specification` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `quality_inspection_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKDC22214BFB31D0AA`(`quality_inspection_id`) USING BTREE,
  CONSTRAINT `iot_quality_inspection_detail_ibfk_1` FOREIGN KEY (`quality_inspection_id`) REFERENCES `iot_quality_inspection` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_quality_standard
-- ----------------------------
DROP TABLE IF EXISTS `iot_quality_standard`;
CREATE TABLE `iot_quality_standard`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspection_standard_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspection_standard_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspection_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_quality_standard_detail
-- ----------------------------
DROP TABLE IF EXISTS `iot_quality_standard_detail`;
CREATE TABLE `iot_quality_standard_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `indicators` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `inspection_project` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `quality_standard_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKBF1563A2D1877C0A`(`quality_standard_id`) USING BTREE,
  CONSTRAINT `iot_quality_standard_detail_ibfk_1` FOREIGN KEY (`quality_standard_id`) REFERENCES `iot_quality_standard` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_report_dtu
-- ----------------------------
DROP TABLE IF EXISTS `iot_report_dtu`;
CREATE TABLE `iot_report_dtu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `equipmenrt_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reply` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `valuess` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipmenrt_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `rst_values` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_report_equipment_sensor
-- ----------------------------
DROP TABLE IF EXISTS `iot_report_equipment_sensor`;
CREATE TABLE `iot_report_equipment_sensor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `date_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_id` bigint(20) NULL DEFAULT NULL,
  `sensor_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_true_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_values` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_report_real_time_sensor
-- ----------------------------
DROP TABLE IF EXISTS `iot_report_real_time_sensor`;
CREATE TABLE `iot_report_real_time_sensor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `equipment_number` bigint(20) NULL DEFAULT NULL,
  `sensor_id` bigint(20) NULL DEFAULT NULL,
  `sensor_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sensor_values` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_senior_management
-- ----------------------------
DROP TABLE IF EXISTS `iot_senior_management`;
CREATE TABLE `iot_senior_management`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `belong` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `netflow` int(11) NOT NULL,
  `number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pay_net_flow` int(11) NOT NULL,
  `remote` bit(1) NOT NULL,
  `startdate` datetime NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `number`(`number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_traffic_card
-- ----------------------------
DROP TABLE IF EXISTS `iot_traffic_card`;
CREATE TABLE `iot_traffic_card`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `card_state` int(11) NOT NULL,
  `cardnumber` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `iccid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `surplus_traffic` decimal(21, 2) NOT NULL,
  `total_traffic` decimal(21, 2) NOT NULL,
  `used_traffic` decimal(21, 2) NOT NULL,
  `warning_line` decimal(21, 2) NOT NULL,
  `equipment_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cardnumber`(`cardnumber`) USING BTREE,
  UNIQUE INDEX `iccid`(`iccid`) USING BTREE,
  INDEX `FK8803A0A3C0E2C6DC`(`equipment_id`) USING BTREE,
  CONSTRAINT `iot_traffic_card_ibfk_1` FOREIGN KEY (`equipment_id`) REFERENCES `iot_equipment` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_traffic_card_order
-- ----------------------------
DROP TABLE IF EXISTS `iot_traffic_card_order`;
CREATE TABLE `iot_traffic_card_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `area_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `buycardnumber` int(11) NOT NULL,
  `company_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `consignee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `contact_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fee` decimal(21, 6) NOT NULL,
  `freight` decimal(21, 6) NOT NULL,
  `freight_type` int(11) NOT NULL,
  `order_status` int(11) NOT NULL,
  `payment_status` int(11) NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sn` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `years_number` int(11) NOT NULL,
  `zip_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sn`(`sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_transmit_session
-- ----------------------------
DROP TABLE IF EXISTS `iot_transmit_session`;
CREATE TABLE `iot_transmit_session`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `ccid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `heartbeat_time` int(11) NULL DEFAULT NULL,
  `last_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ccid`(`ccid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_trs
-- ----------------------------
DROP TABLE IF EXISTS `iot_trs`;
CREATE TABLE `iot_trs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `ct` int(11) NULL DEFAULT NULL,
  `finish_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `trs` double NULL DEFAULT NULL,
  `trs_target` int(11) NULL DEFAULT NULL,
  `shifts` int(11) NULL DEFAULT NULL,
  `noknum` double NULL DEFAULT NULL,
  `oknum` double NULL DEFAULT NULL,
  `total_num` double NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1136 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_user
-- ----------------------------
DROP TABLE IF EXISTS `iot_user`;
CREATE TABLE `iot_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `api_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `company_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `fee` decimal(21, 6) NOT NULL,
  `head_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_type` int(11) NULL DEFAULT NULL,
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `sms_count` int(11) NULL DEFAULT NULL,
  `department` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role_id` bigint(20) NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `line` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `skills` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `creator_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `api_key`(`api_key`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_user_binding
-- ----------------------------
DROP TABLE IF EXISTS `iot_user_binding`;
CREATE TABLE `iot_user_binding`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `head_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKAA921EA29A643BAF`(`user_id`) USING BTREE,
  CONSTRAINT `iot_user_binding_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `iot_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_user_contact
-- ----------------------------
DROP TABLE IF EXISTS `iot_user_contact`;
CREATE TABLE `iot_user_contact`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `weixin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_user_order
-- ----------------------------
DROP TABLE IF EXISTS `iot_user_order`;
CREATE TABLE `iot_user_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `money` decimal(21, 6) NOT NULL,
  `pay_num` int(11) NOT NULL,
  `pay_status` int(11) NOT NULL,
  `pay_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_type` int(11) NOT NULL,
  `sn` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sn`(`sn`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_user_role
-- ----------------------------
DROP TABLE IF EXISTS `iot_user_role`;
CREATE TABLE `iot_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `role_list` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipment_show` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_user_session
-- ----------------------------
DROP TABLE IF EXISTS `iot_user_session`;
CREATE TABLE `iot_user_session`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `bind_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `session_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `system_version` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `session_id`(`session_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_work_order
-- ----------------------------
DROP TABLE IF EXISTS `iot_work_order`;
CREATE TABLE `iot_work_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `count` int(11) NOT NULL,
  `creat_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `creator_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `end_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `finish_time` bigint(20) NOT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `production_model` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `start_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_work_report
-- ----------------------------
DROP TABLE IF EXISTS `iot_work_report`;
CREATE TABLE `iot_work_report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `actual_count` int(11) NOT NULL,
  `equipment_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `nok_cause` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `nok_count` int(11) NOT NULL,
  `nok_percent` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `ok_count` int(11) NOT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `plan_count` int(11) NOT NULL,
  `production_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `production_model` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `report_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `reporter` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `work_repor_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `yield_percent` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_work_report_detail
-- ----------------------------
DROP TABLE IF EXISTS `iot_work_report_detail`;
CREATE TABLE `iot_work_report_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `works` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1214 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for iot_work_trace_detail
-- ----------------------------
DROP TABLE IF EXISTS `iot_work_trace_detail`;
CREATE TABLE `iot_work_trace_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_date` datetime NOT NULL,
  `creator` bigint(20) NULL DEFAULT NULL,
  `equipment_type_id` bigint(20) NULL DEFAULT NULL,
  `is_del` bit(1) NOT NULL,
  `modify_date` datetime NOT NULL,
  `owner_id` bigint(20) NULL DEFAULT NULL,
  `creat_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `line_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `model` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `nok_num` int(11) NULL DEFAULT NULL,
  `ok_num` int(11) NULL DEFAULT NULL,
  `order_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `total_num` int(11) NULL DEFAULT NULL,
  `works` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `actual_time` bigint(20) NULL DEFAULT NULL,
  `fault_time` bigint(20) NULL DEFAULT NULL,
  `idle_time` bigint(20) NULL DEFAULT NULL,
  `runing_time` bigint(20) NULL DEFAULT NULL,
  `shut_down_time` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16485 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
