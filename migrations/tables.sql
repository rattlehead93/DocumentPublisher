CREATE DATABASE IF NOT EXISTS document_database;
USE `document_database`;

CREATE TABLE IF NOT EXISTS document
(
    `id`           BIGINT(20) AUTO_INCREMENT NOT NULL,
    `file_name`    VARCHAR(256) NOT NULL,
    `file_content` TEXT NOT NULL,
    `extension`    VARCHAR(4),
	PRIMARY KEY    (`id`)
);


CREATE TABLE processed_document
(
    `id`            BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `document_id`   BIGINT(20),
    `unique_number` INT,
    INDEX         fk_document_id_idx (document_id),
    FOREIGN KEY (document_id) REFERENCES document (id)
);

CREATE TABLE failed_document
(
    `id`             BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
    `document_id`    BIGINT(20),
    `last_failure`   BIGINT(13),
    `failure_reason` TEXT
);
