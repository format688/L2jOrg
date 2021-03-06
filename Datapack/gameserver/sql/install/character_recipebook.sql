DROP TABLE IF EXISTS `character_recipebook`;
CREATE TABLE IF NOT EXISTS `character_recipebook` (
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `id` decimal(11) NOT NULL DEFAULT 0,
  `classIndex` TINYINT NOT NULL DEFAULT 0,
  `type` INT NOT NULL DEFAULT 0,

  PRIMARY KEY (`id`,`charId`,`classIndex`),
  FOREIGN KEY FK_FRIENDS_FRIEND (`charId`) REFERENCES characters (`charId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;