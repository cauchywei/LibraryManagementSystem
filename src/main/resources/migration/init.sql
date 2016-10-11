CREATE DATABASE IF NOT EXISTS `librarian` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `librarian`;

CREATE TABLE `User` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` char(32) DEFAULT NULL,
  `role` varchar(50) NOT NULL DEFAULT 'reader',
  `status` varchar(50) NOT NULL DEFAULT 'normal',
  `name` varchar(50) DEFAULT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `age` tinyint(3) unsigned DEFAULT NULL,
  `major` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `remarks` text,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `Book` (
  `ISBN` varchar(128) NOT NULL DEFAULT '',
  `status` varchar(50) NOT NULL DEFAULT 'NORMAL',
  `name` varchar(256) DEFAULT NULL,
  `total` int(10) unsigned NOT NULL DEFAULT '0',
  `margin` int(10) unsigned NOT NULL DEFAULT '0',
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ISBN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `BorrowRecord` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userId` int(10) unsigned NOT NULL,
  `ISBN` varchar(128) NOT NULL DEFAULT '',
  `status` varchar(50) DEFAULT NULL,
  `borrowTime` datetime DEFAULT NULL,
  `returnTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
