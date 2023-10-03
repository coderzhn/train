drop table if exists `sk_token`;
create table `sk_token` (
                            `id` bigint not null comment 'id',
                            `date` date not null comment '日期',
                            `train_code` varchar(20) not null comment '车次编号',
                            `count` int not null comment '令牌余量',
                            `create_time` datetime(3) comment '新增时间',
                            `update_time` datetime(3) comment '修改时间',
                            primary key (`id`),
                            unique key `date_train_code_unique` (`date`, `train_code`)
) engine=innodb default charset=utf8mb4 comment='秒杀令牌';
