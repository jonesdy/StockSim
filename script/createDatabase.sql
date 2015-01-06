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
   user_role_id INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(60) NOT NULL,
   role VARCHAR(60) NOT NULL,
   PRIMARY KEY (user_role_id),
   UNIQUE KEY uni_username_role (role, username),
   KEY fk_username_idx (username),
   CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));
   
CREATE TABLE games (
   gid INT NOT NULL AUTO_INCREMENT,
   title VARCHAR(60) NOT NULL,
   startingMoney INT NOT NULL,
   private BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (gid));
   
CREATE TABLE players (
   pid INT NOT NULL AUTO_INCREMENT,
   username VARCHAR(60) NOT NULL,
   gid INT NOT NULL,
   balance INT NOT NULL,
   isAdmin BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (pid),
   CONSTRAINT fk_username_players FOREIGN KEY (username) REFERENCES users (username),
   CONSTRAINT fk_gid FOREIGN KEY (gid) REFERENCES games (gid));
   
CREATE TABLE stocks (
   sid INT NOT NULL AUTO_INCREMENT,
   tickerSymbol VARCHAR(10) NOT NULL,
   pid INT NOT NULL,
   count INT NOT NULL,
   PRIMARY KEY (sid),
   CONSTRAINT fk_pid FOREIGN KEY (pid) REFERENCES players (pid));
   
CREATE TABLE transactions (
   tid INT NOT NULL AUTO_INCREMENT,
   sid INT NOT NULL,
   count INT NOT NULL,
   price INT NOT NULL,       /* In pennies */
   timestamp BIGINT NOT NULL, /* Milliseconds since the epoch */
   buy BOOLEAN NOT NULL DEFAULT FALSE,
   PRIMARY KEY (tid),
   CONSTRAINT fk_sid FOREIGN KEY (sid) REFERENCES stocks (sid));
