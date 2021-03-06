ALTER TABLE `users`
ADD INDEX `grooup_user_idx` (`group` ASC) VISIBLE;
;
ALTER TABLE `users`
ADD CONSTRAINT `grooup_user`
  FOREIGN KEY (`group`)
  REFERENCES `group` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;
;
ALTER TABLE `group_to_permission`
ADD INDEX `permission_idx` (`permission` ASC) VISIBLE;
;
ALTER TABLE `group_to_permission`
ADD CONSTRAINT `permission`
  FOREIGN KEY (`permission`)
  REFERENCES `permission` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION,
ADD CONSTRAINT `group`
  FOREIGN KEY (`group`)
  REFERENCES `group` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;