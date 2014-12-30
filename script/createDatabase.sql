CREATE DATABASE stocksimdb;
USE stocksimdb;

CREATE TABLE users (
   username VARCHAR(60) NOT NULL,
   password VARCHAR(60) NOT NULL,
   email VARCHAR(60) NOT NULL,
   confirmCode VARCHAR(32) NOT NULL,
   enabled BOOLEAN NOT NULL DEFAULT TRUE,
   confirmed BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (username));
   
CREATE TABLE user_roles (
   user_role_id INT(11) NOT NULL AUTO_INCREMENT,
   username VARCHAR(60) NOT NULL,
   role VARCHAR(60) NOT NULL,
   PRIMARY KEY (user_role_id),
   UNIQUE KEY uni_username_role (role, username),
   KEY fk_username_idx (username),
   CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
   
CREATE TABLE games (
   gid INT(11) NOT NULL AUTO_INCREMENT,
   title VARCHAR(60) NOT NULL,
   starting_money INT(11) NOT NULL,
   private BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (gid));
   
CREATE TABLE players (
   pid INT(11) NOT NULL AUTO_INCREMENT,
   username VARCHAR(60) NOT NULL,
   gid INT(11) NOT NULL,
   balance INT(11) NOT NULL,
   is_admin BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (pid),
   CONSTRAINT fk_username_players FOREIGN KEY (username) REFERENCES users (username),
   CONSTRAINT fk_gid FOREIGN KEY (gid) REFERENCES games (gid));
   
CREATE TABLE stocks (
   sid INT(11) NOT NULL AUTO_INCREMENT,
   ticker_symbol VARCHAR(10) NOT NULL,
   pid INT(11) NOT NULL,
   count INT(11) NOT NULL,
   PRIMARY KEY (sid),
   CONSTRAINT fk_pid FOREIGN KEY (pid) REFERENCES players (pid));
   
CREATE TABLE transactions (
   tid INT(11) NOT NULL AUTO_INCREMENT,
   sid INT(11) NOT NULL,
   count INT(11) NOT NULL,
   price INT(11) NOT NULL,       /* In pennies */
   buy BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (tid),
   CONSTRAINT fk_sid FOREIGN KEY (sid) REFERENCES stocks (sid));