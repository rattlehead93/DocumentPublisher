CREATE DATABASE IF NOT EXISTS `document_database`;
USE `document_database`;
CREATE TABLE IF NOT EXISTS `document` (
    `id` BIGINT(20) NOT NULL,
    `file_name` VARCHAR(256) NOT NULL,
    `file_content` TEXT NOT NULL,
    `extension` VARCHAR(4),
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;
