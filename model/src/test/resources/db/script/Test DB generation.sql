DROP SCHEMA IF EXISTS `my_db_test`;
CREATE SCHEMA `my_db_test`;
USE `my_db_test`;

-- -----------------------------------------------------
-- Table `my_db_test`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`user`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`user`
(
    `id`       BIGINT                             NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(45)                        NULL DEFAULT NULL,
    `password` VARCHAR(45)                        NULL DEFAULT NULL,
    `role`     ENUM ('USER', 'REDACTOR', 'ADMIN') NULL DEFAULT NULL,
    `version`  BIGINT                             DEFAULT 0,
    PRIMARY KEY (`id`)
);



-- -----------------------------------------------------
-- Table `my_db_test`.`organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`organisation`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`organisation`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(45) NULL DEFAULT NULL,
    `description`  VARCHAR(45) NULL DEFAULT NULL,
    `is_active`    TINYINT     NULL DEFAULT NULL,
    `date_created` TIMESTAMP   NULL DEFAULT NULL,
    `address`      VARCHAR(45) NULL DEFAULT NULL,
    `version`      BIGINT      DEFAULT 0,
    `user_id`      BIGINT      NOT NULL UNIQUE ,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_organisation_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_organisation_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `my_db_test`.`user` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db_test`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`profile`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`profile`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name`   VARCHAR(45) NULL DEFAULT NULL,
    `last_name`    VARCHAR(45) NULL DEFAULT NULL,
    `phone_number` VARCHAR(45) NULL DEFAULT NULL,
    `email`        VARCHAR(45) NULL DEFAULT NULL,
    `date_created` TIMESTAMP   NULL DEFAULT NULL,
    `version`      BIGINT      DEFAULT 0,
    `user_id`      BIGINT      NOT NULL UNIQUE,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_profile_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_profile_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `my_db_test`.`user` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db_test`.`utility`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`utility`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`utility`
(
    `id`              BIGINT         NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45)    NULL DEFAULT NULL,
    `price`           DECIMAL(10, 2) NULL DEFAULT NULL,
    `description`     TEXT           NULL DEFAULT NULL,
    `organisation_id` BIGINT         NOT NULL,
    `version`         BIGINT         DEFAULT 0,
    PRIMARY KEY (`id`, `organisation_id`),
    INDEX `fk_service_organisation1_idx` (`organisation_id` ASC) VISIBLE,
    CONSTRAINT `fk_service_organisation1`
        FOREIGN KEY (`organisation_id`)
            REFERENCES `my_db_test`.`organisation` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db_test`.`worker`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`worker`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`worker`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name`      VARCHAR(45) NULL DEFAULT NULL,
    `last_name`       VARCHAR(45) NULL DEFAULT NULL,
    `description`     VARCHAR(45) NULL DEFAULT NULL,
    `organisation_id` BIGINT      NOT NULL,
    `version`         BIGINT      DEFAULT 0,
    PRIMARY KEY (`id`, `organisation_id`),
    INDEX `fk_worker_organisation1_idx` (`organisation_id` ASC) VISIBLE,
    CONSTRAINT `fk_worker_organisation1`
        FOREIGN KEY (`organisation_id`)
            REFERENCES `my_db_test`.`organisation` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db_test`.`schedule_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`schedule_record`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`schedule_record`
(
    `id`         BIGINT                                               NOT NULL AUTO_INCREMENT,
    `time`       TIME                                                 NULL DEFAULT NULL,
    `status`     ENUM ('BOOKED', 'AVAILABLE', 'DONE', 'CANCELED') NULL DEFAULT NULL,
    `profile_id` BIGINT                                               NOT NULL,
    `version`    BIGINT                                               DEFAULT 0,
    `worker_id`  BIGINT                                               NOT NULL,
    `utility_id` BIGINT                                               NOT NULL,
    PRIMARY KEY (`id`, `profile_id`, `worker_id`, `utility_id`),
    INDEX `fk_schedule_record_profile1_idx` (`profile_id` ASC) VISIBLE,
    INDEX `fk_schedule_record_worker1_idx` (`worker_id` ASC) VISIBLE,
    INDEX `fk_schedule_record_service1_idx` (`utility_id` ASC) VISIBLE,
    CONSTRAINT `fk_schedule_record_profile1`
        FOREIGN KEY (`profile_id`)
            REFERENCES `my_db_test`.`profile` (`id`),
    CONSTRAINT `fk_schedule_record_service1`
        FOREIGN KEY (`utility_id`)
            REFERENCES `my_db_test`.`utility` (`id`),
    CONSTRAINT `fk_schedule_record_worker1`
        FOREIGN KEY (`worker_id`)
            REFERENCES `my_db_test`.`worker` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db_test`.`worker_utility`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db_test`.`worker_utility`;

CREATE TABLE IF NOT EXISTS `my_db_test`.`worker_utility`
(
    `worker_id`  BIGINT NOT NULL,
    `utility_id` BIGINT NOT NULL,
    PRIMARY KEY (`worker_id`, `utility_id`),
    INDEX `fk_worker_has_utility_utility1_idx` (`utility_id` ASC) VISIBLE,
    INDEX `fk_worker_has_utility_worker1_idx` (`worker_id` ASC) VISIBLE,
    CONSTRAINT `fk_worker_has_utility_utility1`
        FOREIGN KEY (`utility_id`)
            REFERENCES `my_db_test`.`utility` (`id`),
    CONSTRAINT `fk_worker_has_utility_worker1`
        FOREIGN KEY (`worker_id`)
            REFERENCES `my_db_test`.`worker` (`id`)
);
