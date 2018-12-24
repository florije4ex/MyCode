-- 小组
CREATE TABLE `douban_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '小组code',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '小组名称',
  `logo_url` varchar(100) NOT NULL DEFAULT '' COMMENT '小组logo地址',
  `attention_user` int(11) NOT NULL DEFAULT '0' COMMENT '关注人数',
  `group_create_date` datetime NOT NULL COMMENT '小组创建日期',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `notes` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='豆瓣小组：https://www.douban.com/group/XXXXX';