ALTER TABLE `news_db`.`group`
RENAME TO  `news_db`.`user_group`;

ALTER TABLE `news_db`.`users`
DROP FOREIGN KEY `grooup_user`;
ALTER TABLE `news_db`.`users`
CHANGE COLUMN `group` `user_group` INT NULL DEFAULT NULL ;
ALTER TABLE `news_db`.`users`
ADD CONSTRAINT `grooup_user`
  FOREIGN KEY (`user_group`)
  REFERENCES `news_db`.`user_group` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;