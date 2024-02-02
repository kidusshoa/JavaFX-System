CREATE DATABASE CarRental;

CREATE TABLE cars (
  ID integer PRIMARY KEY AUTO_INCREMENT,
  Maker varchar(255),
  Name varchar(255),
  Colour varchar(255),
  Type varchar(255),
  SeatingCapacity int,
  Model varchar(255),
  Car_Condition varchar(255),
  RegNo varchar(255),
  RentPerHour int,
  CarOwnerID int,
  IsRented tinyint(1) default 0
  );
  
CREATE TABLE customers (
  ID integer PRIMARY KEY AUTO_INCREMENT,
  CNIC varchar(20) UNIQUE,
  Name varchar(255),
  Contact_No varchar(20),
  Bill int
  );
  
CREATE TABLE bookings (
  ID integer PRIMARY KEY AUTO_INCREMENT,
  CustomerID int,
  CarID int,
  RentTime bigint,
  ReturnTime bigint
  );
  
CREATE TABLE carowners (
  ID integer PRIMARY KEY AUTO_INCREMENT,
  Balance int,
  CNIC varchar(20),
  Name varchar(255),
  Contact_No varchar(255)
  );
  
