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
  primary key(tID)
);
ALTER TABLE Restaurant AUTO_INCREMENT = 1;

CREATE TABLE Reservations(
  numOfTable INT,
  timeReserved VARCHAR(50),
  cID INT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key (cID, timeReserved),
  FOREIGN KEY (cID) REFERENCES Customer(cID) on update cascade
);

CREATE TABLE ReservationsArchive(
  numOfTable INT,
  timeReserved VARCHAR(50),
  cID INT,
  updatedAt DATETIME
);

CREATE TABLE CurrentDropIns(
  numOfTable INT,
  timeDropIn VARCHAR(50),
  cID INT,
  updatedAt DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key (cID, timeDropIn),
  FOREIGN KEY (cID) REFERENCES Customer(cID) on update cascade
);

CREATE TABLE CurrentDropInsArchive(
  numOfTable INT,
  timeDropIn VARCHAR(50),
  cID INT,
  updatedAt DATETIME
);

/* Inputting mock data*/

INSERT INTO CurrentDropInsArchive(numOfTable,timeDropIn,cID, updatedAt)
Values(2,"00:12:00",1, "2018-12-03 14:26:35"),
      (2,"00:14:00",2, "2018-12-03 14:26:35"),
      (1,"00:14:20",3, "2018-12-03 14:26:35");

INSERT INTO ReservationsArchive(numOfTable,timeReserved,cID, updatedAt)
      Values(1,"00:08:00",1, "2018-12-03 13:26:35"),
            (1,"00:22:00",2, "2018-12-03 16:26:35"),
            (1,"00:21:20",3, "2018-12-03 18:26:35");

/*Archiving Reservations*/
DELIMITER //
CREATE PROCEDURE archiveReservations (IN cutOff VARCHAR(50))
BEGIN
    INSERT INTO ReservationsArchive
    SELECT *
    FROM Reservations
    WHERE Reservations.updatedAt < cutOff;

    DELETE FROM Reservations
    WHERE Reservations.updatedAt < cutOff;
END//
DELIMITER ;

/*Archiving CurrentDropIns*/
DELIMITER //
CREATE PROCEDURE archiveCurrentDropIns (IN cutOff VARCHAR(50))
BEGIN
    INSERT INTO CurrentDropInsArchive
    SELECT *
    FROM CurrentDropIns
    WHERE CurrentDropIns.updatedAt < cutOff;

    DELETE FROM CurrentDropIns
    WHERE CurrentDropIns.updatedAt < cutOff;
END//
DELIMITER ;

/*When a server is off, their assigned tables needs to be assigned to another server*/
DELIMITER //
CREATE TRIGGER ServerOff
    AFTER Update ON Employee
    FOR EACH ROW
BEGIN
	IF NEW.isOff = 1 THEN
    UPDATE Restaurant
    SET Restaurant.subServerID = NEW.sID and Restaurant.sID =
        (select min(employee.sID)
         from employee
         where employee.isOff = 0);
    END IF;
END//
DELIMITER ;

/*When a server is back to work (when isOff is updated to 0),
tables need to be reassigned to them*/
DELIMITER //
CREATE TRIGGER ServerOn
    AFTER Update ON Employee
    FOR EACH ROW
BEGIN
    IF NEW.isOff = 0 THEN
    UPDATE Restaurant
    SET subServerID = 0 and sID = NEW.sID
    WHERE subServerID = NEW.sID;
    END IF;
END//
DELIMITER ;

# if the below data path doesn't work, use the full path from your machine
LOAD DATA LOCAL INFILE 'C:/Users/L/Desktop/RestaurantReservation/data/employee.txt' INTO TABLE Employee;
LOAD DATA LOCAL INFILE 'C:/Users/L/Desktop/RestaurantReservation/data/customer.txt' INTO TABLE Customer;
LOAD DATA LOCAL INFILE 'C:/Users/L/Desktop/RestaurantReservation/data/restaurant.txt' INTO TABLE Restaurant;
