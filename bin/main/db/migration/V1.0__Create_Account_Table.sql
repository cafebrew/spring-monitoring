CREATE TABLE account
(
    id        bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    username  varchar(20)         NOT NULL UNIQUE,
    name      varchar(20)         NOT NULL,
    password  varchar(20)         NOT NULL,
    email     varchar(50),
    roles     varchar(100),
    create_at DATETIME(6),
    modify_at DATETIME(6),
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8
  COLLATE utf8_general_ci;
