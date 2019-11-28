DROP TABLE IF EXISTS station;
DROP TABLE IF EXISTS stand;
DROP TABLE IF EXISTS bike;
DROP TABLE IF EXISTS rent;

CREATE TABLE station (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT,
  publicId VARCHAR(36),
  name VARCHAR(256)
);

CREATE TABLE bike (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT,
  publicId VARCHAR(36),
  station BIGINT
);

CREATE TABLE stand (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT,
  publicId VARCHAR(36),
  available BOOLEAN,
  station BIGINT
);

CREATE TABLE rent (
  id IDENTITY PRIMARY KEY AUTO_INCREMENT,
  publicId VARCHAR(36),
  bike BIGINT
);