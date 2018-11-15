CREATE DATABASE RestaurantReservation;
USE RestaurantReservation;

CREATE TABLE Customer(
  cID INT AUTO_INCREMENT,
  name VARCHAR(50),
  phoneNum INT,
  primary key(cID)
);

CREATE TABLE Employee(
  sID INT AUTO_INCREMENT,
  name VARCHAR(50),
  isOff BOOLEAN,
  primary key(sID)
);

CREATE TABLE Restaurant(
  tID INT AUTO_INCREMENT,
  occupied BOOLEAN DEFAULT FALSE,
  numOfSeats INT,
  sID INT,
  subServerID INT,
  primary key(tID),
  FOREIGN KEY(sID) REFERENCES Employee(sID) on update cascade,
  FOREIGN KEY(subServerID) REFERENCES Employee(sID) on update cascade,
);

CREATE TABLE Reservations(
  numOfTable INT,
  timeReserved VARCHAR(50),
  cID INT,
  primary key (cID, numOfTable),
  FOREIGN KEY (cID) REFERENCES Customer(cID) on update cascade
);

CREATE TABLE CurrentDropIns(
  numOfTable INT,
  timeDropIn VARCHAR(50),
  cID INT,
  queueID INT,
  primary key (cID, numOfTable),
  FOREIGN KEY (cID) REFERENCES Customer(cID) on update cascade
); 
