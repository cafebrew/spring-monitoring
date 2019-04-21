CREATE TABLE user
(
    id        bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    username  varchar(20)         NOT NULL,
    name      varchar(20)         NOT NULL,
    password  varchar(20)         NOT NULL,
    email     varchar(50),
    create_at DATETIME(6),
    modify_at DATETIME(6),
    PRIMARY KEY (id)
);
