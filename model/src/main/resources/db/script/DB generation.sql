DROP SCHEMA IF EXISTS `my_db`;
CREATE SCHEMA `my_db`;
USE `my_db`;

-- -----------------------------------------------------
-- Table `my_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`user`;

CREATE TABLE IF NOT EXISTS `my_db`.`user`
(
    `id`       BIGINT                             NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(45)                        NULL DEFAULT NULL,
    `password` VARCHAR(45)                        NULL DEFAULT NULL,
    `role`     ENUM ('USER', 'REDACTOR', 'ADMIN') NULL DEFAULT NULL,
    `version`  BIGINT                             NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
);



-- -----------------------------------------------------
-- Table `my_db`.`organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`organisation`;

CREATE TABLE IF NOT EXISTS `my_db`.`organisation`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(45) NULL DEFAULT NULL,
    `description`  VARCHAR(45) NULL DEFAULT NULL,
    `is_active`    TINYINT     NULL DEFAULT NULL,
    `date_created` TIMESTAMP   NULL DEFAULT NULL,
    `address`      VARCHAR(45) NULL DEFAULT NULL,
    `version`      BIGINT      NULL DEFAULT NULL,
    `user_id`      BIGINT      NOT NULL UNIQUE ,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_organisation_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_organisation_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `my_db`.`user` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`profile`;

CREATE TABLE IF NOT EXISTS `my_db`.`profile`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name`   VARCHAR(45) NULL DEFAULT NULL,
    `last_name`    VARCHAR(45) NULL DEFAULT NULL,
    `phone_number` VARCHAR(45) NULL DEFAULT NULL,
    `email`        VARCHAR(45) NULL DEFAULT NULL,
    `date_created` TIMESTAMP   NULL DEFAULT NULL,
    `version`      BIGINT      NULL DEFAULT NULL,
    `user_id`      BIGINT      NOT NULL UNIQUE,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_profile_user1_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `fk_profile_user1`
        FOREIGN KEY (`user_id`)
            REFERENCES `my_db`.`user` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db`.`utility`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`utility`;

CREATE TABLE IF NOT EXISTS `my_db`.`utility`
(
    `id`              BIGINT         NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45)    NULL DEFAULT NULL,
    `price`           DECIMAL(10, 0) NULL DEFAULT NULL,
    `description`     TEXT           NULL DEFAULT NULL,
    `organisation_id` BIGINT         NOT NULL,
    `version`         BIGINT         NULL DEFAULT NULL,
    PRIMARY KEY (`id`, `organisation_id`),
    INDEX `fk_service_organisation1_idx` (`organisation_id` ASC) VISIBLE,
    CONSTRAINT `fk_service_organisation1`
        FOREIGN KEY (`organisation_id`)
            REFERENCES `my_db`.`organisation` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db`.`worker`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`worker`;

CREATE TABLE IF NOT EXISTS `my_db`.`worker`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name`      VARCHAR(45) NULL DEFAULT NULL,
    `last_name`       VARCHAR(45) NULL DEFAULT NULL,
    `description`     VARCHAR(45) NULL DEFAULT NULL,
    `organisation_id` BIGINT      NOT NULL,
    `version`         BIGINT      NULL DEFAULT NULL,
    PRIMARY KEY (`id`, `organisation_id`),
    INDEX `fk_worker_organisation1_idx` (`organisation_id` ASC) VISIBLE,
    CONSTRAINT `fk_worker_organisation1`
        FOREIGN KEY (`organisation_id`)
            REFERENCES `my_db`.`organisation` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db`.`schedule_record`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`schedule_record`;

CREATE TABLE IF NOT EXISTS `my_db`.`schedule_record`
(
    `id`         BIGINT                                               NOT NULL AUTO_INCREMENT,
    `time`       TIME                                                 NULL DEFAULT NULL,
    `status`     ENUM ('BOOKED', 'AVAILABLE', 'FINISHED', 'CANCELED') NULL DEFAULT NULL,
    `profile_id` BIGINT                                               NOT NULL,
    `version`    BIGINT                                               NULL DEFAULT NULL,
    `worker_id`  BIGINT                                               NOT NULL,
    `utility_id` BIGINT                                               NOT NULL,
    PRIMARY KEY (`id`, `profile_id`, `worker_id`, `utility_id`),
    INDEX `fk_schedule_record_profile1_idx` (`profile_id` ASC) VISIBLE,
    INDEX `fk_schedule_record_worker1_idx` (`worker_id` ASC) VISIBLE,
    INDEX `fk_schedule_record_service1_idx` (`utility_id` ASC) VISIBLE,
    CONSTRAINT `fk_schedule_record_profile1`
        FOREIGN KEY (`profile_id`)
            REFERENCES `my_db`.`profile` (`id`),
    CONSTRAINT `fk_schedule_record_service1`
        FOREIGN KEY (`utility_id`)
            REFERENCES `my_db`.`utility` (`id`),
    CONSTRAINT `fk_schedule_record_worker1`
        FOREIGN KEY (`worker_id`)
            REFERENCES `my_db`.`worker` (`id`)
);


-- -----------------------------------------------------
-- Table `my_db`.`worker_utility`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `my_db`.`worker_utility`;

CREATE TABLE IF NOT EXISTS `my_db`.`worker_utility`
(
    `worker_id`  BIGINT NOT NULL,
    `utility_id` BIGINT NOT NULL,
    PRIMARY KEY (`worker_id`, `utility_id`),
    INDEX `fk_worker_has_utility_utility1_idx` (`utility_id` ASC) VISIBLE,
    INDEX `fk_worker_has_utility_worker1_idx` (`worker_id` ASC) VISIBLE,
    CONSTRAINT `fk_worker_has_utility_utility1`
        FOREIGN KEY (`utility_id`)
            REFERENCES `my_db`.`utility` (`id`),
    CONSTRAINT `fk_worker_has_utility_worker1`
        FOREIGN KEY (`worker_id`)
            REFERENCES `my_db`.`worker` (`id`)
);
