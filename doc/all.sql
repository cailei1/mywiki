drop table if exists `test`;
create table `test`
(
    `id`       bigint not null comment 'id',
    `name`     varchar(50) comment '名称',
    `password` varchar(250) comment '密码',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;

drop table if exists `demo`;
create table `demo`
(
    `id`   bigint not null comment 'id',
    `name` varchar(50) comment '名称',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4;