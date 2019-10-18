
CREATE DATABASE `xa_account` /*!40100 DEFAULT CHARACTER SET utf8 */;
use xa_account;


CREATE TABLE `capital_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户ID',
	`balance_amount` decimal(10,0) DEFAULT '0' COMMENT '账户余额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='账户信息表';

INSERT INTO `capital_account` (`id`,`user_id`,`balance_amount`) VALUES (1,1,2000);






CREATE DATABASE `xa_red_account` /*!40100 DEFAULT CHARACTER SET utf8 */;
use xa_red_account;

CREATE TABLE `red_packet_account` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) NOT NULL COMMENT '用户ID',
	`balance_amount` decimal(10,0) DEFAULT '0' COMMENT '账户余额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='红包账户信息表';

INSERT INTO `red_packet_account` (`id`,`user_id`,`balance_amount`) VALUES (1,2,1000);