CREATE SCHEMA IF NOT EXISTS `my_db`;
USE `my_db`;


CREATE TABLE IF NOT EXISTS `my_db`.`user`
(
    `id`       BIGINT                             NOT NULL AUTO_INCREMENT,
    `login`    VARCHAR(30)                        ,
    `password` VARCHAR(30)                        ,
    `role`     ENUM ('USER', 'REDACTOR', 'ADMIN') ,
    `version`  BIGINT                             DEFAULT 0,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `my_db`.`organisation`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(30) ,
    `description`  VARCHAR(120) ,
    `is_active`    TINYINT     ,
    `date_created` TIMESTAMP   ,
    `address`      VARCHAR(45) ,
    `version`      BIGINT      DEFAULT 0,
    `user_id`      BIGINT      NOT NULL UNIQUE ,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_organisation_user_idx` (`user_id` ASC),
    CONSTRAINT `fk_organisation_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `my_db`.`user` (`id`)
);


CREATE TABLE IF NOT EXISTS `my_db`.`profile`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name`   VARCHAR(45) ,
    `last_name`    VARCHAR(45) ,
    `phone_number` VARCHAR(45) ,
    `email`        VARCHAR(45) ,
    `date_created` TIMESTAMP   ,
    `version`      BIGINT      DEFAULT 0,
    `user_id`      BIGINT      NOT NULL UNIQUE,
    PRIMARY KEY (`id`, `user_id`),
    INDEX `fk_profile_user_idx` (`user_id` ASC),
    CONSTRAINT `fk_profile_user`
        FOREIGN KEY (`user_id`)
            REFERENCES `my_db`.`user` (`id`)
);


CREATE TABLE IF NOT EXISTS `my_db`.`utility`
(
    `id`              BIGINT         NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45)    ,
    `price`           DECIMAL(10, 2) ,
    `description`     TEXT           ,
    `organisation_id` BIGINT         NOT NULL,
    `version`         BIGINT         DEFAULT 0,
    PRIMARY KEY (`id`, `organisation_id`),
    INDEX `fk_service_organisation_idx` (`organisation_id` ASC),
    CONSTRAINT `fk_service_organisation`
        FOREIGN KEY (`organisation_id`)
            REFERENCES `my_db`.`organisation` (`id`)
);


CREATE TABLE IF NOT EXISTS `my_db`.`worker`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `first_name`      VARCHAR(45) ,
    `last_name`       VARCHAR(45) ,
    `description`     VARCHAR(45) ,
    `organisation_id` BIGINT      NOT NULL,
    `version`         BIGINT      DEFAULT 0,
    PRIMARY KEY (`id`, `organisation_id`),
    INDEX `fk_worker_organisation_idx` (`organisation_id` ASC),
    CONSTRAINT `fk_worker_organisation`
        FOREIGN KEY (`organisation_id`)
            REFERENCES `my_db`.`organisation` (`id`)
);

CREATE TABLE IF NOT EXISTS `my_db`.`schedule_record`
(
    `id`         BIGINT                                               NOT NULL AUTO_INCREMENT,
    `time`       TIME                                                 ,
    `status`     ENUM ('BOOKED', 'AVAILABLE', 'DONE', 'CANCELED')     ,
    `profile_id` BIGINT                                               ,
    `version`    BIGINT                                               DEFAULT 0,
    `worker_id`  BIGINT                                               NOT NULL,
    `utility_id` BIGINT                                               ,
    PRIMARY KEY (`id`, `worker_id`),
    INDEX `fk_schedule_record_profile_idx` (`profile_id` ASC),
    INDEX `fk_schedule_record_worker_idx` (`worker_id` ASC),
    INDEX `fk_schedule_record_service_idx` (`utility_id` ASC),
    CONSTRAINT `fk_schedule_record_profile`
        FOREIGN KEY (`profile_id`)
            REFERENCES `my_db`.`profile` (`id`),
    CONSTRAINT `fk_schedule_record_service`
        FOREIGN KEY (`utility_id`)
            REFERENCES `my_db`.`utility` (`id`),
    CONSTRAINT `fk_schedule_record_worker`
        FOREIGN KEY (`worker_id`)
            REFERENCES `my_db`.`worker` (`id`)
);

CREATE TABLE IF NOT EXISTS `my_db`.`worker_utility`
(
    `worker_id`  BIGINT NOT NULL,
    `utility_id` BIGINT NOT NULL,
    PRIMARY KEY (`worker_id`, `utility_id`),
    INDEX `fk_worker_has_utility_utility_idx` (`utility_id` ASC),
    INDEX `fk_worker_has_utility_worker_idx` (`worker_id` ASC),
    CONSTRAINT `fk_worker_has_utility_utility`
        FOREIGN KEY (`utility_id`)
            REFERENCES `my_db`.`utility` (`id`),
    CONSTRAINT `fk_worker_has_utility_worker`
        FOREIGN KEY (`worker_id`)
            REFERENCES `my_db`.`worker` (`id`)
);
