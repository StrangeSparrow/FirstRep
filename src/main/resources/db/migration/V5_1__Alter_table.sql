ALTER TABLE `news`
ADD COLUMN `author` INT NULL AFTER `full`;

ALTER TABLE `users`
CHANGE COLUMN `group` `group` INT NULL DEFAULT NULL ;

ALTER TABLE `group_to_permission`
CHANGE COLUMN `group` `group` INT NOT NULL ,
CHANGE COLUMN `permission` `permission` INT NOT NULL ;