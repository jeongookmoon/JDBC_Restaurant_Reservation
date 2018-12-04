/*This is a list of SQL statements needed to statisfy the functional req*/

/*Group by and having: query the list of customers who made more than one reservations*/
select name
from Reservations, Customer
where Customer.cID = Reservations.cID
group by customer.cID
having count(*)>1;

/*list of employees that are not assigned to a table*/
select name
from Employee e1
where not exists (
  select sid
  from Restaurant
  where sid = e1.sid
)

/*The average number of tables requested for customers (drop-ins and reservation)*/
select avg(tables)
where (
  select numofTable from Reservations
  union
  select numOfTable from CurrentDropIns
) tables;

/*Show all customers names and their reservations and current drop-ins
(if a customer does not have a reservation or drop in, it should show NULL)*/
select *
from Customer FULL OUTER JOIN Reservations on Customer.cID = Reservations.cID
UNION
select *
from Customer FULL OUTER JOIN CurrentDropIns on Customer.cID = CurrentDropIns.cid;

/*Customers that are in CurrentDropIns but not in Reservations (vice versa)*/
select name from Customer, CurrentDropIns where Customer.cid = CurrentDropIns.cid
except
select name from Customer, Reservations where Customer.cid = Reservations.cid;

/*Customer makes a new customer profile */
INSERT INTO Customer(name, phoneNum) VALUES (?,?);

/*Customer updates their phone number in profile */
Update Customer
set phoneNum = ?
where name = ? ;

/*Customer updates their name in profile */
Update Customer
set phoneNum = ?
where name = ? ;

/*Delete Customer profile*/
Delete from Customer
where phoneNum = ? and name =?;

/*Employee makes a new employee profile*/
INSERT INTO Employee(name, phoneNum) VALUES (?,?);

/*Employee change to day off */
Update Employee
set isOff = 1
where name = ?;

/* Employee returns back to work */
Update Employee
set isOff = 0
where name = ?;

/*Delete Employee profile */
Delete from Employee
where name = ? and phoneNum =? ;

/*Customer makes a new reservation*/
INSERT INTO Reservations(numOfTable, timeReserved, cID)
select ?, ?, cid
from Customer
where phoneNum = ? and name = ?;

/*Customer change a current reservation to a different time*/
Update Reservation
set timeReserved = ?
where cID in cid in (select cid from Customer where name = ? and phoneNum= ?)
      and timeReserved = ?;

/*Customer deletes a reservation*/
DELETE FROM Reservations
WHERE cid in (select cid from Customer where name = ? and phoneNum= ?)
      and timeReserved = ?;

/*Customer makes a new drop in*/
INSERT INTO CurrentDropIns(numOfTable, timeDropIn, cID, queueID)
select ?, ?, cid, ?
from Customer
where phoneNum = ? and name = ? ;

/*Customer change number of tables needed in exisiting drop in  */
UPDATE CurrentDropIns
set numOfTable = ?
where cid in (select cid from Customer where name =? and phoneNum =?)
      and timeDropIn = ? ;

/*Customer deletes a drop-in*/
DELETE FROM CurrentDropIns
WHERE cid in (select cid from Customer where name = ? and phoneNum= ?)
      and timeDropIn = ? ;

/*When a server is off, their assigned tables needs to be assigned to another server*/
CREATE TRIGGER ServerOff
AFTER Update ON Employee
FOR EACH ROW
WHEN NEW.isOff = 1
BEGIN
    UPDATE Restaurant
      SET subServerID = OLD.sID and sID = ( select min(E1.sID)
 					      from Employee E1
      where E1.isOff = 0)
    WHERE sID = NEW.sID;
END;

/*When a server is back to work (when isOff is updated to 0),
tables need to be reassigned to them*/
CREATE TRIGGER ServerOn
AFTER Update ON Employee
FOR EACH ROW
WHEN NEW.isOff = 0
BEGIN
    UPDATE Restaurant
    SET subServerID = NULL and sID = NEW.sID
    WHERE subServerID = NEW.sID;
END;



/*Find all reservations and current drop in of a particular  customer*/
DELIMITER //
CREATE PROCEDURE allDropInAndReservation (IN name VARCHAR, IN phoneNum VARCHAR)
BEGIN
  select numOfTable, timeDropIn
  from CurrentDropIns
  where cid in (select cid
                from Customer
                where Customer.name = name and Customer.phoneNum = phoneNum);

  select numOfTable, timeReserved
  from reservations
  cid in (select cid
                from Customer
                where Customer.name = name and Customer.phoneNum = phoneNum);
END //
DELIMITER ;


/*Archiving Reservations*/

DELIMITER //
CREATE PROCEDURE archiveReservations (IN cutOff VARCHAR)
BEGIN
    INSERT INTO ReservationsArchive(numOfTable, timeReserved, cID, updatedAt)
    SELECT *
    FROM Reservations
    WHERE Reservations.updatedAt < cutOff;

    DELETE FROM Reservations
    WHERE Reservations.updatedAt < cutOff;
END//
DELIMITER ;

/*Archiving CurrentDropIns*/
DELIMITER //
CREATE PROCEDURE archiveCurrentDropIns (IN cutOff VARCHAR)
BEGIN
    INSERT INTO CurrentDropInsArchive(numOfTable, timeReserved, cID, updatedAt)
    SELECT *
    FROM CurrentDropIns
    WHERE CurrentDropIns.updatedAt < cutOff;

    DELETE FROM CurrentDropIns
    WHERE CurrentDropIns.updatedAt < cutOff;
END//
DELIMITER ;
