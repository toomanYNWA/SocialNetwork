-- !Ups

CREATE TABLE IF NOT EXISTS `users` (
`userId` BIGINT(20)  NOT NULL AUTO_INCREMENT,
`name` varchar(255) NOT NULL,
`username` varchar(255) NOT NULL,
`password` varchar(255) NOT NULL,
PRIMARY KEY(`userId`)
);

CREATE TABLE IF NOT EXISTS `posts` (
`postId` BIGINT(20)  NOT NULL AUTO_INCREMENT,
`userId` varchar(255) NOT NULL,
`postedAt` timestamp NOT NULL,
`text` varchar(1000) NOT NULL,
PRIMARY KEY(`postId`)
);

CREATE TABLE IF NOT EXISTS `friendRequests` (
`friendRequestId` BIGINT(20)  NOT NULL AUTO_INCREMENT,
`senderId` varchar(255) NOT NULL,
`recipientId` varchar(255) NOT NULL,
`accepted` bit,
PRIMARY KEY(`friendRequestId`)
);

CREATE TABLE IF NOT EXISTS `likes`(
`likeId` BIGINT(20)  NOT NULL AUTO_INCREMENT,
`userId` varchar(255) NOT NULL,
`postId` varchar(255) NOT NULL,
PRIMARY KEY(`likeId`)
);

INSERT INTO users (name, username, password) VALUES (1, 'Tom', 'asdf', '123');
INSERT INTO users (name, username, password) VALUES (2, 'Jovan', 'pTc44', '321');

-- !Downs
DROP TABLE users;
DROP TABLE posts;
DROP TABLE friendRequests;
DROP TABLE likes;