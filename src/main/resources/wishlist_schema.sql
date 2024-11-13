DROP DATABASE IF EXISTS wishlistdb;
CREATE DATABASE IF NOT EXISTS wishlistdb;
USE wishlistdb;

DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS reservation;

CREATE TABLE wishes(
wishId INT NOT NULL AUTO_INCREMENT,
wishName VARCHAR(100) NOT NULL,
price DOUBLE NOT NULL,
wishDescription VARCHAR(300),
link VARCHAR(300),
PRIMARY KEY(wishId),
FOREIGN KEY(wishlistId) REFERENCES wishlist(wishlistId),
FOREIGN KEY(userId) REFERENCES users(userId)
);

CREATE TABLE users(
userId INT NOT NULL AUTO_INCREMENT,
username VARCHAR(100) NOT NULL,
userPassword VARCHAR(100) NOT NULL,
PRIMARY KEY(userId)
);

CREATE TABLE wishlist(
wishlistId INT NOT NULL AUTO_INCREMENT,
wishlistName VARCHAR(100) NOT NULL,
PRIMARY KEY(wishlistId),
FOREIGN KEY(userId) REFERENCES users(userId)
);

CREATE TABLE reservation(
wish VARCHAR(100) REFERENCES wishes(wishName),
FOREIGN KEY(userId) REFERENCES users(userId),
FOREIGN KEY(wishlistId) REFERENCES wishlist(wishlistId)
);

