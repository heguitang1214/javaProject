create database ds0;
create database ds1;

use ds0;
CREATE TABLE `t_province` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(64) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=201
;

CREATE TABLE `t_order0` (
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`user_id` INT(64) NULL DEFAULT NULL,
	`order_id` INT(64) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order1` (
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`user_id` INT(64) NULL DEFAULT NULL,
	`order_id` INT(64) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order_item0` (
	`order_id` INT(64) NOT NULL,
	`user_id` INT(64) NOT NULL,
	`brand_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`product_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`order_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`pay_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`total_price` FLOAT NOT NULL,
	`discount` INT(64) NOT NULL,
	`pay_price` FLOAT NOT NULL,
	PRIMARY KEY (`order_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order_item1` (
	`order_id` INT(64) NOT NULL,
	`user_id` INT(64) NOT NULL,
	`brand_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`product_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`order_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`pay_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`total_price` FLOAT NOT NULL,
	`discount` INT(64) NOT NULL,
	`pay_price` FLOAT NOT NULL,
	PRIMARY KEY (`order_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;






use ds1;
CREATE TABLE `t_province` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(64) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=201
;


CREATE TABLE `t_order0` (
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`user_id` INT(64) NULL DEFAULT NULL,
	`order_id` INT(64) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order1` (
	`id` INT(64) NOT NULL AUTO_INCREMENT,
	`user_id` INT(64) NULL DEFAULT NULL,
	`order_id` INT(64) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order_item0` (
	`order_id` INT(64) NOT NULL,
	`user_id` INT(64) NOT NULL,
	`brand_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`product_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`order_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`pay_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`total_price` FLOAT NOT NULL,
	`discount` INT(64) NOT NULL,
	`pay_price` FLOAT NOT NULL,
	PRIMARY KEY (`order_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order_item1` (
	`order_id` INT(64) NOT NULL,
	`user_id` INT(64) NOT NULL,
	`brand_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`product_name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`order_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`pay_date` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_unicode_ci',
	`total_price` FLOAT NOT NULL,
	`discount` INT(64) NOT NULL,
	`pay_price` FLOAT NOT NULL,
	PRIMARY KEY (`order_id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

