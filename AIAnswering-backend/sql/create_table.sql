create database if not exists AIAnswering;

use AIAnswering;

-- User Table
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment 'account',
    userPassword varchar(512)                           not null comment 'password',
    unionId      varchar(256)                           null comment 'WeChat Open Platform id',
    mpOpenId     varchar(256)                           null comment 'public website openId',
    userName     varchar(256)                           null comment 'username',
    userAvatar   varchar(1024)                          null comment 'avatar',
    userProfile  varchar(512)                           null comment 'user profile',
    userRole     varchar(256) default 'user'            not null comment 'role：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete     tinyint      default 0                 not null comment 'delete or not',
    index idx_unionId (unionId)
) comment 'User' collate = utf8mb4_unicode_ci;

-- Post Table
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment 'title',
    content    text                               null comment 'contents',
    tags       varchar(1024)                      null comment 'tags（json array）',
    thumbNum   int      default 0                 not null comment 'thumbs up number',
    favourNum  int      default 0                 not null comment 'favour number',
    userId     bigint                             not null comment 'user id',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete   tinyint  default 0                 not null comment 'delete or not',
    index idx_userId (userId)
) comment 'Post' collate = utf8mb4_unicode_ci;

-- Post Likes Table
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment 'post id',
    userId     bigint                             not null comment 'user id',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    index idx_postId (postId),
    index idx_userId (userId)
) comment 'thumbs up posts';

-- Post Favorites Table
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment 'post id',
    userId     bigint                             not null comment 'user id',
    createTime datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    index idx_postId (postId),
    index idx_userId (userId)
) comment 'favour posts';
