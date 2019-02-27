create database ds0;
create database ds1;


CREATE TABLE `t_order` (
	`id` INT(64) NOT NULL,
	`user_id` INT(64) NULL,
	`order_id` INT(64) NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;

CREATE TABLE `t_order_item` (
	`order_id` INT(64) NOT NULL,
	`user_id` INT(64) NOT NULL,
	`brand_name` VARCHAR(50) NOT NULL,
	`product_name` VARCHAR(50) NOT NULL,
	`order_date` VARCHAR(50) NOT NULL,
	`pay_date` VARCHAR(50) NOT NULL,
	`total_price` FLOAT NOT NULL,
	`discount` INT(64) NOT NULL,
	`pay_price` FLOAT NOT NULL,
	PRIMARY KEY (`order_id`)
)
COLLATE='utf8mb4_unicode_ci'
ENGINE=InnoDB
;












