CREATE DATABASE RestaurantReservation;
USE RestaurantReservation;

CREATE TABLE Customer(
  cID INT AUTO_INCREMENT,
  name VARCHAR(50),
  phoneNum VARCHAR(10),
  primary key(cID)
);
ALTER TABLE Customer AUTO_INCREMENT = 1;

CREATE TABLE Employee(
  sID INT AUTO_INCREMENT,
  name VARCHAR(50),
  isOff BOOLEAN,
  phoneNum VARCHAR(10),
  primary key(sID)
);

ALTER TABLE Employee AUTO_INCREMENT = 1;

CREATE TABLE Restaurant(
  tID INT AUTO_INCREMENT,
  occupied BOOLEAN DEFAULT FALSE,
  numOfSeats INT,
  sID INT,
  subServerID INT,
  primary key(tID),
  FOREIGN KEY(sID) REFERENCES Employee(sID) on update cascade,
  FOREIGN KEY(subServerID) REFERENCES Employee(sID) on update cascade
);
ALTER TABLE Restaurant AUTO_INCREMENT = 1;

CREATE TABLE Reservations(
  numOfTable INT,
  timeReserved VARCHAR(50),
  cID INT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key (cID, numOfTable),
  FOREIGN KEY (cID) REFERENCES Customer(cID) on update cascade
);

CREATE TABLE ReservationsArchive(
  numOfTable INT,
  timeReserved VARCHAR(50),
  cID INT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE CurrentDropIns(
  numOfTable INT,
  timeDropIn VARCHAR(50),
  cID INT,
  queueID INT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key (cID, numOfTable),
  FOREIGN KEY (cID) REFERENCES Customer(cID) on update cascade
);

CREATE TABLE CurrentDropInsArchive(
  numOfTable INT,
  timeDropIn VARCHAR(50),
  cID INT,
  queueID INT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

# if the below data path doesn't work, use the full path from your machine
LOAD DATA LOCAL INFILE '../data/employee.txt' INTO TABLE Employee;
#LOAD DATA LOCAL INFILE '../data/customer.txt' INTO TABLE Customer;
#LOAD DATA LOCAL INFILE '../data/restaurant.txt' INTO TABLE Restaurant;
