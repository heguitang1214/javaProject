CREATE TABLE `payment` (
  `order_id` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `order_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `pay_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `total_price` decimal(10,2) DEFAULT NULL,
  `discount` int(255) DEFAULT NULL,
  `pay_price` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `test`.`payment` (`order_id`, `brand_id`, `product_id`, `order_date`, `pay_date`, `total_price`, `discount`, `pay_price`) VALUES ('1000000', '1000001', '1000002', '2019-06-25 12:51:45', '2019-06-25 12:51:47', '100.00', '200', '100.00');

