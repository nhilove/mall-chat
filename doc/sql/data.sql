CREATE TABLE `user` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户id',
                        `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
                        `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
                        `sex` int(11) DEFAULT NULL COMMENT '性别 1为男性，2为女性',
                        `open_id` char(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '微信openid用户标识',
                        `active_status` int(11) DEFAULT '2' COMMENT '在线状态 1在线 2离线',
                        `last_opt_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '最后上下线时间',
                        `ip_info` json DEFAULT NULL COMMENT 'ip信息',
                        `item_id` bigint(20) DEFAULT NULL COMMENT '佩戴的徽章id',
                        `status` int(11) DEFAULT '0' COMMENT '使用状态 0.正常 1拉黑',
                        `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                        `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE KEY `uniq_open_id` (`open_id`) USING BTREE,
                        UNIQUE KEY `uniq_name` (`name`) USING BTREE,
                        KEY `idx_create_time` (`create_time`) USING BTREE,
                        KEY `idx_update_time` (`update_time`) USING BTREE,
                        KEY `idx_active_status_last_opt_time` (`active_status`,`last_opt_time`)
) ENGINE=InnoDB AUTO_INCREMENT=11000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='用户表';

CREATE TABLE `item_config`  (
                                `id` bigint(20) UNSIGNED NOT NULL COMMENT 'id',
                                `type` int(11) NOT NULL COMMENT '物品类型 1改名卡 2徽章',
                                `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物品图片',
                                `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '物品功能描述',
                                `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `idx_create_time`(`create_time`) USING BTREE,
                                INDEX `idx_update_time`(`update_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '功能物品配置表' ROW_FORMAT = Dynamic;
CREATE TABLE `user_backpack`  (
                                  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `uid` bigint(20) NOT NULL COMMENT 'uid',
                                  `item_id` bigint(20) NOT NULL COMMENT '物品id',
                                  `status` int(11) NOT NULL COMMENT '使用状态 0.待使用 1已使用',
                                  `idempotent` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '幂等号',
                                  `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                  `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  UNIQUE INDEX `uniq_idempotent`(`idempotent`) USING BTREE,
                                  INDEX `idx_uid`(`uid`) USING BTREE,
                                  INDEX `idx_create_time`(`create_time`) USING BTREE,
                                  INDEX `idx_update_time`(`update_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户背包表' ROW_FORMAT = Dynamic;
INSERT INTO `item_config` VALUES (1, 1, NULL, '用户可以使用改名卡，更改自己的名字。mallchat名称全局唯一，快抢订你的专属昵称吧', '2023-03-25 22:27:30.511', '2023-03-25 22:27:30.511');
INSERT INTO `item_config` VALUES (2, 2, 'https://cdn-icons-png.flaticon.com/128/1533/1533913.png', '爆赞徽章，单条消息被点赞超过10次，即可获得', '2023-05-07 17:50:31.090', '2023-05-07 18:12:05.824');
INSERT INTO `item_config` VALUES (3, 2, 'https://cdn-icons-png.flaticon.com/512/6198/6198527.png ', '抹茶聊天前10名注册的用户才能获得的专属徽章', '2023-05-07 17:50:31.100', '2023-05-07 18:12:01.448');
INSERT INTO `item_config` VALUES (4, 2, 'https://cdn-icons-png.flaticon.com/512/10232/10232583.png', '抹茶聊天前100名注册的用户才能获得的专属徽章', '2023-05-07 17:50:31.109', '2023-05-07 17:56:36.059');
INSERT INTO `item_config` VALUES (5, 2, 'https://cdn-icons-png.flaticon.com/128/2909/2909937.png', '抹茶知识星球成员的专属徽章', '2023-05-07 17:50:31.109', '2023-05-07 17:56:36.059');
INSERT INTO `item_config` VALUES (6, 2, 'https://minio.mallchat.cn/mallchat/%E8%B4%A1%E7%8C%AE%E8%80%85.png', '抹茶项目contributor专属徽章', '2023-05-07 17:50:31.109', '2023-05-07 17:56:36.059');

CREATE TABLE `black`  (
                          `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
                          `type` int(11) NOT NULL COMMENT '拉黑目标类型 1.ip 2uid',
                          `target` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '拉黑目标',
                          `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                          `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                          PRIMARY KEY (`id`) USING BTREE,
                          UNIQUE INDEX `idx_type_target`(`type`, `target`) USING BTREE
) COMMENT = '黑名单' ROW_FORMAT = Dynamic;
CREATE TABLE `role` (
                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                        `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
                        `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                        `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                        PRIMARY KEY (`id`) USING BTREE,
                        KEY `idx_create_time` (`create_time`) USING BTREE,
                        KEY `idx_update_time` (`update_time`) USING BTREE
) COMMENT='角色表';
CREATE TABLE `user_role` (
                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `uid` bigint(20) NOT NULL COMMENT 'uid',
                             `role_id` bigint(20) NOT NULL COMMENT '角色id',
                             `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                             `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                             PRIMARY KEY (`id`) USING BTREE,
                             KEY `idx_uid` (`uid`) USING BTREE,
                             KEY `idx_role_id` (`role_id`) USING BTREE,
                             KEY `idx_create_time` (`create_time`) USING BTREE,
                             KEY `idx_update_time` (`update_time`) USING BTREE
) COMMENT='用户角色关系表';
insert into role(id,`name`) values(1,'超级管理员');
insert into role(id,`name`) values(2,'抹茶群聊管理员');

CREATE TABLE `user_apply` (
                              `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                              `uid` bigint(20) NOT NULL COMMENT '申请人uid',
                              `type` int(11) NOT NULL COMMENT '申请类型 1加好友',
                              `target_id` bigint(20) NOT NULL COMMENT '接收人uid',
                              `msg` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '申请信息',
                              `status` int(11) NOT NULL COMMENT '申请状态 1待审批 2同意',
                              `read_status` int(11) NOT NULL COMMENT '阅读状态 1未读 2已读',
                              `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                              `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                              PRIMARY KEY (`id`) USING BTREE,
                              KEY `idx_uid_target_id` (`uid`,`target_id`) USING BTREE,
                              KEY `idx_target_id_read_status` (`target_id`,`read_status`) USING BTREE,
                              KEY `idx_target_id` (`target_id`) USING BTREE,
                              KEY `idx_create_time` (`create_time`) USING BTREE,
                              KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户申请表';
DROP TABLE IF EXISTS `user_friend`;
CREATE TABLE `user_friend` (
                               `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                               `uid` bigint(20) NOT NULL COMMENT 'uid',
                               `friend_uid` bigint(20) NOT NULL COMMENT '好友uid',
                               `delete_status` int(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0-正常,1-删除)',
                               `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                               `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `idx_uid_friend_uid` (`uid`,`friend_uid`) USING BTREE,
                               KEY `idx_create_time` (`create_time`) USING BTREE,
                               KEY `idx_update_time` (`update_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户联系人表';

DROP TABLE IF EXISTS `secure_invoke_record`;
CREATE TABLE `secure_invoke_record` (
                                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `secure_invoke_json` json NOT NULL COMMENT '请求快照参数json',
                                        `status` tinyint(8) NOT NULL COMMENT '状态 1待执行 2已失败',
                                        `next_retry_time` datetime(3) NOT NULL COMMENT '下一次重试的时间',
                                        `retry_times` int(11) NOT NULL COMMENT '已经重试的次数',
                                        `max_retry_times` int(11) NOT NULL COMMENT '最大重试次数',
                                        `fail_reason` text COMMENT '执行失败的堆栈',
                                        `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
                                        `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改时间',
                                        PRIMARY KEY (`id`) USING BTREE,
                                        KEY `idx_next_retry_time` (`next_retry_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='本地消息表';