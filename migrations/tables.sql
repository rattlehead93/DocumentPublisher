CREATE DATABASE IF NOT EXISTS `document_database`;
USE `document_database`;

CREATE TABLE IF NOT EXISTS `document` (
    `id` BIGINT(20) NOT NULL,
    `file_name` VARCHAR(256) NOT NULL,
    `file_content` TEXT NOT NULL,
    `extension` VARCHAR(4),
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;


CREATE TABLE processed_document
(
    `id`            INT AUTO_INCREMENT PRIMARY KEY,
    `document_id`   INT,
    `unique_number` INT,
    INDEX         fk_document_id_idx (document_id),
    FOREIGN KEY (document_id) REFERENCES document (id)
);
