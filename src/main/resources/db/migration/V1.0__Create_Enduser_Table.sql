CREATE TABLE user
(
  id        bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  user_name varchar(50)         NOT NULL,
  email     varchar(255),
  PRIMARY KEY (id)
);