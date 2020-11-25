ALTER TABLE `news`
ADD INDEX `author_news_idx` (`author` ASC) VISIBLE;
;
ALTER TABLE `news`
ADD CONSTRAINT `author_news`
  FOREIGN KEY (`author`)
  REFERENCES `users` (`id`)
  ON DELETE SET NULL
  ON UPDATE SET NULL;