CREATE TABLE feed
(
    id        bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    name      varchar(100)        NOT NULL,
    url       varchar(500)        NOT NULL,
    owner_id  bigint(20) unsigned NOT NULL,
    create_at DATETIME(6),
    modify_at DATETIME(6),
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8
  COLLATE utf8_general_ci;
