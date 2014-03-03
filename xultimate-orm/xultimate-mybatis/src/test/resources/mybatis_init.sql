CREATE TABLE IF NOT EXISTS `ULTIMATE_AREA` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `po_version` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `boost` float DEFAULT NULL,
  `display_name` longtext NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_parentId` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `TEST_PO` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `other_id` int(11) NOT NULL,
  `login_count` int(11) DEFAULT NULL,
  `boost` float DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `message` varchar(255) NOT NULL,
  `sex` tinyint(6) DEFAULT NULL,
  `introduction` text NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `is_lock` char(0) DEFAULT NULL,
  `has_login` bool DEFAULT NULL,
  `state_set` tinyint(6) DEFAULT NULL,
  `login_ip` int(20) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;