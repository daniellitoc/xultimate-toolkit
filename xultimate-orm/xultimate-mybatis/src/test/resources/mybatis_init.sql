CREATE TABLE IF NOT EXISTS `ULTIMATE_AREA` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `po_version` bigint DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `boost` float DEFAULT NULL,
  `display_name` longtext NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_parentId` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `TEST_PO` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NOT NULL,
  `other_id` int NOT NULL,
  `login_count` int DEFAULT NULL,
  `boost` float DEFAULT NULL,
  `money` bigint DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `sex` tinyint DEFAULT NULL,
  `introduction` text NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_lock` char(0) DEFAULT NULL,
  `has_login` bool DEFAULT NULL,
  `state_set` tinyint DEFAULT NULL,
  `login_ip` int unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;