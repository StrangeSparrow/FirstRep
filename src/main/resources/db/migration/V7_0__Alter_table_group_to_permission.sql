ALTER TABLE `news_db`.`group_to_permission`
DROP FOREIGN KEY `group`;
ALTER TABLE `news_db`.`group_to_permission`
CHANGE COLUMN `group` `group_id` INT NOT NULL ;
ALTER TABLE `news_db`.`group_to_permission`
ADD CONSTRAINT `group`
  FOREIGN KEY (`group_id`)
  REFERENCES `news_db`.`group` (`id`);