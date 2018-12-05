INSERT INTO Reservations(numOfTable, timeReserved, cID)
Values(1, "00:13:00", 2),
      (1, "00:09:30", 3),
      (1, "00:10:00", 4),
      (2,"00:20:00", 5),
      (1,"00:19:30", 6),
      (1, "00:22:00", 4);

INSERT INTO Reservations(numOfTable, timeReserved, cID)
      Values(2, "00:09:00", 2),
            (3, "00:010:30", 3),
            (2, "00:12:00", 4),
            (1,"00:11:00", 5),
            (7,"00:17:30", 6),
            (2, "00:21:30", 4);

INSERT INTO CurrentDropIns(numOfTable, timeDropIn, cID)
Values(1, "00:13:10", 3),
      (1, "00:10:30", 5),
      (1, "00:10:00", 7),
      (2,"00:17:00", 1),
      (1,"00:15:30", 3),
      (1, "00:22:00", 6);

INSERT INTO CurrentDropInsArchive(numOfTable,timeDropIn,cID, updatedAt)
Values(2,"00:12:00",1, "2018-12-03 14:26:35"),
      (2,"00:14:00",2, "2018-12-03 14:26:35"),
      (1,"00:14:20",3, "2018-12-03 14:26:35");

INSERT INTO ReservationsArchive(numOfTable,timeReserved,cID, updatedAt)
      Values(1,"00:08:00",1, "2018-12-03 13:26:35"),
            (1,"00:22:00",2, "2018-12-03 16:26:35"),
            (1,"00:21:20",3, "2018-12-03 18:26:35");
