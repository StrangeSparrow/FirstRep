CREATE TABLE IF NOT EXISTS `group_to_permission` (
  `group` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  `permission` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  PRIMARY KEY (`group`, `permission`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;