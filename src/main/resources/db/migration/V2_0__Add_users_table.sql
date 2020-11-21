CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(30) CHARACTER SET 'utf8' NOT NULL,
  `password` VARCHAR(20) CHARACTER SET 'utf8' NOT NULL,
  `group` VARCHAR(20) CHARACTER SET 'utf8' NULL,
  `auth_token` VARCHAR(20) CHARACTER SET 'utf8' NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `auth_token_UNIQUE` (`auth_token` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;