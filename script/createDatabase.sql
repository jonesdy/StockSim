CREATE DATABASE stocksimdb;
USE stocksimdb;

CREATE TABLE users (
   username VARCHAR(60) NOT NULL,
   password VARCHAR(60) NOT NULL,
   enabled TINYINT NOT NULL DEFAULT 1,
   PRIMARY KEY (username));
   
CREATE TABLE user_roles (
   user_role_id INT(11) NOT NULL AUTO_INCREMENT,
   username VARCHAR(60) NOT NULL,
   role VARCHAR(60) NOT NULL,
   PRIMARY KEY (user_role_id),
   UNIQUE KEY uni_username_role (role, username),
   KEY fk_username_idx (username),
   CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));