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

-- App table
create table if not exists app
(
    id              bigint auto_increment comment 'id' primary key,
    appName         varchar(128)                       not null comment 'application name',
    appDesc         varchar(2048)                      null comment 'application description',
    appIcon         varchar(1024)                      null comment 'application icon',
    appType         tinyint  default 0                 not null comment 'Type of application (0-scoring category, 1-assessment category)',
    scoringStrategy tinyint  default 0                 not null comment 'Scoring strategy (0-custom, 1-AI)',
    reviewStatus    int      default 0                 not null comment 'Audit Status: 0-Pending, 1-Passed, 2-Rejected',
    reviewMessage   varchar(512)                       null comment 'review message',
    reviewerId      bigint                             null comment 'reviewer id',
    reviewTime      datetime                           null comment 'review time',
    userId          bigint                             not null comment 'user id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete        tinyint  default 0                 not null comment 'delete or not',
    index idx_appName (appName)
) comment 'App' collate = utf8mb4_unicode_ci;

-- question table
create table if not exists question
(
    id              bigint auto_increment comment 'id' primary key,
    questionContent text                               null comment 'question content（json）',
    appId           bigint                             not null comment 'app id',
    userId          bigint                             not null comment 'user id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete        tinyint  default 0                 not null comment 'delete or not',
    index idx_appId (appId)
) comment 'question' collate = utf8mb4_unicode_ci;

-- scoring result table
create table if not exists scoring_result
(
    id               bigint auto_increment comment 'id' primary key,
    resultName       varchar(128)                       not null comment 'Result name, e.g. logistician',
    resultDesc       text                               null comment 'Result description',
    resultPicture    varchar(1024)                      null comment 'result picture',
    resultProp       varchar(128)                       null comment 'Collection of Resulting Properties, JSON，e.g. [I,S,T,J]',
    resultScoreRange int                                null comment 'Range of result scores, e.g. 80, means scores of 80 and above hit this result',
    appId            bigint                             not null comment 'app id',
    userId           bigint                             not null comment 'user id',
    createTime       datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime       datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete         tinyint  default 0                 not null comment 'delete or not',
    index idx_appId (appId)
) comment 'scoring result' collate = utf8mb4_unicode_ci;

-- user answer table
create table if not exists user_answer
(
    id              bigint auto_increment primary key,
    appId           bigint                             not null comment 'app id',
    appType         tinyint  default 0                 not null comment 'Type of application (0-scoring category, 1-assessment category)',
    scoringStrategy tinyint  default 0                 not null comment 'Scoring strategy (0-custom, 1-AI)',
    choices         text                               null comment 'user choices (JSON array)',
    resultId        bigint                             null comment 'result id',
    resultName      varchar(128)                       null comment 'Result name, e.g. logistician',
    resultDesc      text                               null comment 'Result description',
    resultPicture   varchar(1024)                      null comment 'result picture',
    resultScore     int                                null comment 'score',
    userId          bigint                             not null comment 'user id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete        tinyint  default 0                 not null comment 'delete or not',
    index idx_appId (appId),
    index idx_userId (userId)
) comment 'user answer' collate = utf8mb4_unicode_ci;

-- user answer table 0
create table if not exists user_answer_0
(
    id              bigint auto_increment primary key,
    appId           bigint                             not null comment 'app id',
    appType         tinyint  default 0                 not null comment 'Type of application (0-scoring category, 1-assessment category)',
    scoringStrategy tinyint  default 0                 not null comment 'Scoring strategy (0-custom, 1-AI)',
    choices         text                               null comment 'user choices (JSON array)',
    resultId        bigint                             null comment 'result id',
    resultName      varchar(128)                       null comment 'Result name, e.g. logistician',
    resultDesc      text                               null comment 'Result description',
    resultPicture   varchar(1024)                      null comment 'result picture',
    resultScore     int                                null comment 'score',
    userId          bigint                             not null comment 'user id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete        tinyint  default 0                 not null comment 'delete or not',
    index idx_appId (appId),
    index idx_userId (userId)
) comment 'user answer 0' collate = utf8mb4_unicode_ci;

-- user answer table 1
create table if not exists user_answer_1
(
    id              bigint auto_increment primary key,
    appId           bigint                             not null comment 'app id',
    appType         tinyint  default 0                 not null comment 'Type of application (0-scoring category, 1-assessment category)',
    scoringStrategy tinyint  default 0                 not null comment 'Scoring strategy (0-custom, 1-AI)',
    choices         text                               null comment 'user choices (JSON array)',
    resultId        bigint                             null comment 'result id',
    resultName      varchar(128)                       null comment 'Result name, e.g. logistician',
    resultDesc      text                               null comment 'Result description',
    resultPicture   varchar(1024)                      null comment 'result picture',
    resultScore     int                                null comment 'score',
    userId          bigint                             not null comment 'user id',
    createTime      datetime default CURRENT_TIMESTAMP not null comment 'create time',
    updateTime      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'update time',
    isDelete        tinyint  default 0                 not null comment 'delete or not',
    index idx_appId (appId),
    index idx_userId (userId)
) comment 'user answer 1' collate = utf8mb4_unicode_ci;