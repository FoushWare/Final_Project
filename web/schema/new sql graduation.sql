CREATE TABLE `orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  PRIMARY KEY (`id`))
ENGINE = MyISAM;

ALTER TABLE `orders` 
ADD COLUMN `checked` TINYINT NULL AFTER `user_id`;



CREATE TABLE `products` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` TEXT NULL,
  `price` FLOAT NULL,
  `quantity` INT NULL,
  `created_at` TIMESTAMP NULL,
  `updated_at` TIMESTAMP NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `product_id` INT NOT NULL,
  `price` FLOAT NULL,
  PRIMARY KEY (`id`));

ALTER TABLE `items`
CHANGE COLUMN `price` `quantity` INT NULL DEFAULT NULL ,
ADD COLUMN `created_at` TIMESTAMP NULL AFTER `quantity`,
ADD COLUMN `update_at` TIMESTAMP NULL AFTER `created_at`;

ALTER TABLE `items` 
CHANGE COLUMN `update_at` `updated_at` TIMESTAMP NULL DEFAULT NULL ;

ALTER TABLE `items` 
ADD COLUMN `price` FLOAT NULL AFTER `updated_at`;

SELECT SUM(quantity*price) as total FROM otg.items WHERE order_id = 1;
