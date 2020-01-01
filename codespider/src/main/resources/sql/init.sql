-- 小组
CREATE TABLE `douban_group` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL DEFAULT '' COMMENT '小组code',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '小组名称',
  `logo_url` varchar(100) NOT NULL DEFAULT '' COMMENT '小组logo地址',
  `attention_user` int(11) NOT NULL DEFAULT '0' COMMENT '关注人数',
  `group_create_date` datetime NOT NULL COMMENT '小组创建日期',
  `owner_id` varchar(20) NOT NULL DEFAULT '' COMMENT '组长id',
  `owner_name` varchar(20) NOT NULL DEFAULT '' COMMENT '组长昵称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `notes` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='豆瓣小组：https://www.douban.com/group/XXXXX';

-- 小组话题
CREATE TABLE `topic` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_code` varchar(20) NOT NULL COMMENT '小组code',
  `topic_id` int(11) NOT NULL DEFAULT '0' COMMENT '话题id',
  `topic_name` varchar(100) NOT NULL DEFAULT '' COMMENT '话题名称',
  `author_id` varchar(50) NOT NULL DEFAULT '' COMMENT '作者id',
  `author_name` varchar(50) NOT NULL DEFAULT '' COMMENT '作者昵称',
  `reply_count` int(11) NOT NULL DEFAULT '0' COMMENT '回应数量',
  `last_reply_time` datetime NOT NULL DEFAULT '1900-01-01 00:00:00' COMMENT '最后回应时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `notes` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小组话题：https://www.douban.com/group/XXXX/discussion?start=0';

-- 小组成员
CREATE TABLE `group_member` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_code` varchar(20) NOT NULL DEFAULT '' COMMENT '小组code',
  `member_id` varchar(20) NOT NULL DEFAULT '' COMMENT '组员id',
  `member_name` varchar(20) NOT NULL DEFAULT '' COMMENT '组员昵称',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '组员类型：0-普通组员，1-管理员',
  `location` varchar(30) NOT NULL DEFAULT '' COMMENT '常居地',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `notes` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COMMENT='小组成员：https://www.douban.com/people/XXXXXX';




———————————————————————————————分隔线———————————————————————————————————————
hospital库:

CREATE TABLE `hospital` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '医院名称',
  `level` varchar(32) NOT NULL DEFAULT '' COMMENT '医院等级',
  `hospital_id` int(11) NOT NULL DEFAULT '0' COMMENT '医院在平台上的id',
  `start_time` time NOT NULL DEFAULT '08:00:00' COMMENT '开始预约时间',
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '电话',
  `address` varchar(128) NOT NULL DEFAULT '' COMMENT '地址',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `notes` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='114平台上的医院';


———————————————————————————————分隔线———————————————————————————————————————

uft8mb4 编码的问题，需要修改 mysql 的配置文件

lynk库：

CREATE TABLE `book_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `book_id` int(10) unsigned NOT NULL COMMENT '预约 id',
  `book_name` varchar(32) NOT NULL DEFAULT '' COMMENT '景区名称',
  `book_date` date NOT NULL COMMENT '预约日期',
  `book_status` varchar(32) NOT NULL DEFAULT '' COMMENT '预约状态',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '预约人姓名',
  `card_no` varchar(32) NOT NULL DEFAULT '' COMMENT '预约人卡号',
  `card_type` varchar(16) NOT NULL DEFAULT '' COMMENT '卡类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='京津冀旅游一卡通已预约的景区人员信息';

ALTER TABLE `book_info` ADD UNIQUE INDEX `uniq_bookid_cardno` (`book_id`, `card_no`);

