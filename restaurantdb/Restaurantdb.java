
import java.util.Scanner;
import java.sql.*;

/**
 * @author Hung Tang, Faith Chau, Jeong Moon
 * @description the application is about restaurant reservation system
 *
 */
public class Restaurantdb {

	Connection conn;

	public Restaurantdb() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			// DriverManager.useSSL=false;
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/RestaurantReservation?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false",
					"root", "Xayeuxa1110!");

			// triggers not working yet
			// serverOffTrigger();
			// serverOnTrigger();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void closeConnection() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Query Error:" + e.getStackTrace());
		}
	}

	/*
	 * Displaying table
	 */
	public void displayTable(String tableName) {
		try {
			String query = "SELECT * FROM " + tableName;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNumber = rsmd.getColumnCount();

			System.out.println("<< " + tableName + " DB >>");
			while (rs.next()) {
				for (int i = 1; i <= colNumber; i++) {
					if (i > 1)
						System.out.print("\t");
					String colVal = rs.getString(i);
					System.out.print(rsmd.getColumnName(i) + ": " + colVal);
				}
				System.out.println();
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	/*
	 * Restaurant options
	 */
	public void restaurantMenu() {
		displayTable("restaurant");
		Scanner scanner = new Scanner(System.in);
		boolean endFlag = false;
		while (endFlag != true) {
			System.out.println("<<Restaurant Table Menu>>");
			System.out.println("[A] Insert Restaurant Table into DB");
			System.out.println("[B] Delete Restaurant Table from DB");
			System.out.println("[M] MainMenu");
			String input = scanner.nextLine().toLowerCase();
			switch (input) {
			case "a":
				insertRestaurantMenu();
				break;
			case "b":
				deleteRestaurantMenu();
				break;
			case "m":
				endFlag = true;
				break;
			default:
				menuError();
			}
		}
	}

	public int insertRestaurant(boolean occupied, int numOfSeats, int sID) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO Restaurant (occupied, numOfSeats, sID) VALUES (?,?,?)");
			pstmt.setBoolean(1, occupied);
			pstmt.setInt(2, numOfSeats);
			pstmt.setInt(3, sID);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void insertRestaurantMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Insert Restaurant Table>>\n");
		System.out.println("Occupied?");
		System.out.println("[A] Yes");
		System.out.println("[B] No");
		String input = scanner.nextLine().toLowerCase();
		boolean occupied = input.equals("a") ? true : false;

		System.out.println("How many seats?");
		int numOfSeats = scanner.nextInt();
		String dummy = scanner.nextLine(); // Consumes "\n"

		System.out.println("Server ID?");
		int sID = scanner.nextInt();
		dummy = scanner.nextLine(); // Consumes "\n"

		if (insertRestaurant(occupied, numOfSeats, sID) != 0)
			success();
		else
			fail();
	}

	public int deleteRestaurant(int tID) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Restaurant WHERE tID=?");
			pstmt.setInt(1, tID);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void deleteRestaurantMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Delete Restaurant Table>>\n");

		System.out.println("Table ID?");
		int tID = scanner.nextInt();
		String dummy = scanner.nextLine(); // Consumes "\n"

		if (deleteRestaurant(tID) != 0)
			success();
		else
			fail();

	}

	public void employeeMenu() {
		displayTable("employee");
		Scanner scanner = new Scanner(System.in);
		boolean endFlag = false;
		while (endFlag != true) {
			System.out.println("<<Employee Menu>>");
			System.out.println("[A] Insert Employee into DB");
			System.out.println("[B] Delete Employee from DB");
			System.out.println("[C] Update Employee IsOff");
			System.out.println("[D] Find Employee(s) W/O Assigned Table");
			System.out.println("[E] Check Current Drops In Customer");
			System.out.println("[J] Check Average Table Requests");
			System.out.println("[P] Complete list of all customers paired with their reservations");
			System.out.println("[H] Check Customer with more than one Reservations");
			System.out.println("[M] MainMenu");
			String input = scanner.nextLine().toLowerCase();
			switch (input) {
			case "a":
				insertEmployeeMenu();
				break;
			case "b":
				deleteEmployeeMenu();
				break;
			case "c":
				updateEmployeeIsOffMenu();
				break;
			case "d":
				findEmployeeWOTable();
				break;
			case "e":
				checkCurrentDropInNoReservation();
				break;
			case "j":
				checkAverageTableRequest();
				break;
			case "p":
				checkListReservationsNdropins();
				break;
			case "h":
				checkCustomerManyReservation();
				break;
			case "m":
				endFlag = true;
				break;
			default:
				menuError();
			}
		}
	}

	public void checkCustomerManyReservation() {
		try {
			Statement stmt = conn.createStatement();
			String query = "select name from Reservations, Customer where Customer.cID = Reservations.cID group by customer.cID having count(*)>1";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out.println("<< List of Customers with more than one reservation >>");
				System.out.println("name: " + rs.getString("name"));
			}

			while (rs.next()) {
				System.out.println("name: " + rs.getString("name"));
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	public void checkListReservationsNdropins() {
		try {
			Statement stmt = conn.createStatement();
			String query = "select * from Customer left join Reservations on Customer.cID = Reservations.cID UNION select * from Customer right join Reservations on Customer.cID = Reservations.cID";

			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out.println("<< List of Reservations with Customer Names >>");
				System.out.println(
						"Customer Name: "+ rs.getString("name")+" Reservation time: "+ rs.getString("timeReserved"));
			}

			while (rs.next()) {
				System.out.println(
						"Customer Name: "+ rs.getString("name")+" Reservation time: "+ rs.getString("timeReserved"));
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	public void checkAverageTableRequest() {
		try {
			Statement stmt = conn.createStatement();
			String query = "select avg(tables.numOfTable) label from (select numofTable from Reservations UNION ALL select numOfTable from CurrentDropIns) tables";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out.println("<< Average Number of Table >>");
				System.out.println("avg(numOfTable): " + rs.getDouble("label"));
			}

			while (rs.next()) {
				System.out.println("avg(numOfTable): " + rs.getDouble("label"));
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	// Task 5 Current Drop ins not reservation
	public void checkCurrentDropInNoReservation() {
		try {
			Statement stmt = conn.createStatement();
			String query = "select cID, name from customer WHERE customer.cID IN (select cID from currentdropins where cID IN (select cID from Reservations))";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out.println("<< Current Drop In Customer >>");
				System.out.println("cID: " + rs.getInt("cID") + "\t" + "name: " + rs.getString("name"));
			}

			while (rs.next()) {
				System.out.println("cID: " + rs.getInt("cID") + "\t" + "name: " + rs.getString("name"));
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	public void archiveReservations() {
		Scanner in = new Scanner(System.in);
		System.out.println("<<Archive Reservations>>\n");

		System.out.println("Enter cutoff date");
		String cutOff = in.nextLine();

		try {
			String archiveReservations = "{call archiveReservations(?)}";
			CallableStatement cstmst = conn.prepareCall(archiveReservations);
			cstmst.setString(1, cutOff);
			cstmst.execute();
			success();
		} catch (SQLException e) {
			fail();
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	public void archiveCurrentDropIns() {
		Scanner in = new Scanner(System.in);
		System.out.println("<<Archive CurrentDropIns>>\n");

		System.out.println("Enter cutoff date");
		String cutOff = in.nextLine();

		try {
			String archiveCurrentDropIns = "{call archiveCurrentDropIns(?)}";
			CallableStatement cstmst = conn.prepareCall(archiveCurrentDropIns);
			cstmst.setString(1, cutOff);
			cstmst.execute();
			success();
		} catch (SQLException e) {
			fail();
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	public void findEmployeeWOTable() {
		try {
			Statement stmt = conn.createStatement();
			String query = "select sID, name from employee e1 WHERE not exists (select sid from Restaurant where sid = e1.sid)";
			ResultSet rs = stmt.executeQuery(query);

			if (rs.next()) {
				System.out.println("<< Employee(s) without Assigned Table >>");
				System.out.println("sID: " + rs.getInt("sID") + "\t" + "name: " + rs.getString("name"));
			}

			while (rs.next()) {
				System.out.println("sID: " + rs.getInt("sID") + "\t" + "name: " + rs.getString("name"));
			}
			System.out.println();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
	}

	public int insertEmployee(String name, boolean isOFF, String phoneNum) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO Employee (name, isOff, phoneNum) VALUES (?,?,?)");
			pstmt.setString(1, name);
			pstmt.setBoolean(2, isOFF);
			pstmt.setString(3, phoneNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void insertEmployeeMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Insert Employee>>\n");

		System.out.println("Employee Name?");
		String name = scanner.nextLine();

		System.out.println("Is the employee off today?");
		System.out.println("[A] Yes");
		System.out.println("[B] No");

		String ans = scanner.nextLine().toLowerCase();
		boolean isOff = ans.equals("a") ? true : false;

		System.out.println("Employee Phone Number?");
		String phoneNum = scanner.nextLine();

		if (insertEmployee(name, isOff, phoneNum) != 0)
			success();
		else
			fail();
	}

	public int deleteEmployee(String name, String phoneNum) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Employee WHERE name=? and phoneNum=?");
			pstmt.setString(1, name);
			pstmt.setString(2, phoneNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void deleteEmployeeMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Delete Employee>>\n");

		System.out.println("Employee Name?");
		String name = scanner.nextLine();

		System.out.println("Employee Phone Number?");
		String phoneNum = scanner.nextLine();

		if (deleteEmployee(name, phoneNum) != 0)
			success();
		else
			fail();

	}

	public int updateEmployee(String name, boolean isOFF, String phoneNum) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("Update Employee set isOff=? where name=? and phoneNum=?");
			pstmt.setBoolean(1, isOFF);
			pstmt.setString(2, name);
			pstmt.setString(3, phoneNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void updateEmployeeIsOffMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Update Employee IsOff>>\n");

		System.out.println("Employee Name?");
		String name = scanner.nextLine();

		System.out.println("Employee Phone Number?");
		String phoneNum = scanner.nextLine();

		System.out.println("[A] Switched to Off, IsOff = 1");
		System.out.println("[B] Switched to Back to Work, IsOff = 0");

		String ans = scanner.nextLine().toLowerCase();
		boolean isOff = ans.equals("a") ? true : false;

		if (updateEmployee(name, isOff, phoneNum) != 0)
			success();
		else
			fail();
	}

	public static void menuError() {
		System.out.println("Please Choose Valid Option");
	}

	public static void success() {
		System.out.println("Successfully Done");
	}

	public static void fail() {
		System.out.println("Failed");
	}

	public void customerMenu() {
		displayTable("customer");
		Scanner scanner = new Scanner(System.in);
		boolean endFlag = false;
		while (endFlag != true) {
			System.out.println("<<Customer Menu>>");
			System.out.println("[A] Insert Customer into DB");
			// System.out.println("[B] Delete Customer from DB");
			System.out.println("[C] Update Customer Info");
			System.out.println("[M] MainMenu");
			String input = scanner.nextLine().toLowerCase();
			switch (input) {
			case "a":
				insertCustomerMenu();
				break;
			// case "b":
			// deleteCustomerMenu();
			// break;
			case "c":
				updateCustomerInfoMenu();
				break;
			case "m":
				endFlag = true;
				break;
			default:
				menuError();
			}
		}
	}

	public int insertCustomer(String name, String phoneNum) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Customer (name, phoneNum) VALUES (?,?)");
			pstmt.setString(1, name);
			pstmt.setString(2, phoneNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void insertCustomerMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Insert Customer>>\n");

		System.out.println("Customer Name?");
		String name = scanner.nextLine();

		System.out.println("Customer Phone Number?");
		String phoneNum = scanner.nextLine();

		if (insertCustomer(name, phoneNum) != 0)
			success();
		else
			fail();
	}

	public int updateCustomer(String name, String phoneNum, String newPhoneNum) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn
					.prepareStatement("Update Customer set phoneNum=? where name=? and phoneNum=?");
			pstmt.setString(1, newPhoneNum);
			pstmt.setString(2, name);
			pstmt.setString(3, phoneNum);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void updateCustomerInfoMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Update Customer IsOff>>\n");

		System.out.println("Customer Name?");
		String name = scanner.nextLine();

		System.out.println("Customer Phone Number?");
		String phoneNum = scanner.nextLine();

		System.out.println("Customer New Phone Number?");
		String newPhoneNum = scanner.nextLine();

		if (updateCustomer(name, phoneNum, newPhoneNum) != 0)
			success();
		else
			fail();
	}
	public void currentDropInsMenu() {
		displayTable("currentdropins");
		Scanner scanner = new Scanner(System.in);
		boolean endFlag = false;
		while (endFlag != true) {
			System.out.println("<<Current Drop Ins Menu>>");
			System.out.println("[A] Create new Drop Ins requests");
			System.out.println("[B] Delete existing Drop Ins from DB");
			System.out.println("[C] Update number of table needed for current Drop Ins");
			System.out.println("[M] MainMenu");
			String input = scanner.nextLine().toLowerCase();
			switch (input) {
			case "a":
				makeReservationMenu();
				break;
			case "b":
				deleteReservationMenu();
				break;
			case "c":
				updateReservationInfoMenu();
				break;
			case "m":
				endFlag = true;
				break;
			default:
				menuError();
			}
		}
	}

	public void reservationMenu() {
		displayTable("reservations");
		Scanner scanner = new Scanner(System.in);
		boolean endFlag = false;
		while (endFlag != true) {
			System.out.println("<<Reservation Menu>>");
			System.out.println("[A] Make Reservation into DB");
			System.out.println("[B] Delete Reservation from DB");
			System.out.println("[C] Update Reservation Info");
			System.out.println("[M] MainMenu");
			String input = scanner.nextLine().toLowerCase();
			switch (input) {
			case "a":
				makeReservationMenu();
				break;
			case "b":
				deleteReservationMenu();
				break;
			case "c":
				updateReservationInfoMenu();
				break;
			case "m":
				endFlag = true;
				break;
			default:
				menuError();
			}
		}
	}

	public int makeReservationReturnCustomer(String name, String phoneNum, int numTable, String dateTime) {
		int result = 0;
		int result2 = 0;
		int cID = 0;
		try {
			Statement stmt = conn.createStatement();
			String query = "select cID from customer WHERE name = \"" + name + "\" and phoneNum = " + phoneNum;
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				result = 1;
				cID = rs.getInt("cID");
			}
			PreparedStatement pstmt = conn.prepareStatement(
					"Insert INTO reservations (numOfTable, timeReserved, cID, updatedAt) VALUES (?,?,?,now())");
			pstmt.setInt(1, numTable);
			pstmt.setString(2, dateTime);
			pstmt.setInt(3, cID);
			result2 = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result + result2;
	}

	public void makeReservationNewCustomer(String name, String phoneNum, int numTable, String dateTime) {
		if (insertCustomer(name, phoneNum) != 0)
			success();
		else
			fail();
		makeReservationReturnCustomer(name, phoneNum, numTable, dateTime);
	}

	public void makeReservationReturnCustomerMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome back, thank you for choosing DB restaurant");
		System.out.println("Name?");
		String name = scanner.nextLine();

		System.out.println("Can we have your phone number, please? ");
		String phoneNum = scanner.nextLine();

		System.out.println("Number of table, please? (ex: 1, 2, 3 ..)");
		int numTable = scanner.nextInt();
		String dummy = scanner.nextLine(); // Consumes "\n"

		System.out.println("Reservation date and time, please? (ex: 2018-12-31 17:30:00)");
		String dateTime = scanner.nextLine();

		if (makeReservationReturnCustomer(name, phoneNum, numTable, dateTime) != 0)
			success();
		else
			fail();
	}

	public void makeReservationNewCustomerMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Thank you for choosing DB Restaurant");
		System.out.println("Name?");
		String name = scanner.nextLine();

		System.out.println("Can we have your phone number, please? ");
		String phoneNum = scanner.nextLine();

		System.out.println("Number of table, please? (ex: 1, 2, 3 ..)");
		int numTable = scanner.nextInt();
		String dummy = scanner.nextLine(); // Consumes "\n"

		System.out.println("Reservation date and time, please? (ex: 2018-12-31 17:30:00)");
		String dateTime = scanner.nextLine();
		makeReservationNewCustomer(name, phoneNum, numTable, dateTime);
	}

	public void makeReservationMenu() {
		Scanner customerScan = new Scanner(System.in);
		System.out.println("<<Make Reservation>>\n");

		System.out.println("Are you new customer?");
		System.out.println("Please select one option: ");
		System.out.println("[A] Yes");
		System.out.println("[B] No");
		String cusInput = customerScan.nextLine().toLowerCase();

		if (cusInput.equals("a")) {
			makeReservationNewCustomerMenu();
		} else if (cusInput.equals("b")) {
			makeReservationReturnCustomerMenu();
		} else {
			System.out.println("That's not a valid answer");
		}
	}

	public int deleteReservation(String name, String phoneNum) {
		int result = 0;
		int cID = 0;
		try {
			Statement stmt = conn.createStatement();
			String query = "select cID from customer WHERE name = \"" + name + "\" and phoneNum = " + phoneNum;
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
				result = 1;
				cID = rs.getInt("cID");
			}

			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM reservations WHERE cID=?");
			pstmt.setInt(1, cID);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void deleteReservationMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Delete Reservation>>\n");

		System.out.println("Name?");
		String name = scanner.nextLine();

		System.out.println("Phone Number?");
		String phoneNum = scanner.nextLine();

		if (deleteReservation(name, phoneNum) != 0)
			success();
		else
			fail();

	}

	public int updateReservation(String name, String phoneNum, String reservedDateTime, String newDateTime) {
		int result = 0;
		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"Update reservations set timeReserved = ? where cID in (select cid from Customer where name = ? and phoneNum= ?) and timeReserved = ?");
			pstmt.setString(1, newDateTime);
			pstmt.setString(2, name);
			pstmt.setString(3, phoneNum);
			pstmt.setString(4, reservedDateTime);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Query Error: " + e.getStackTrace());
		}
		return result;
	}

	public void updateReservationInfoMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("<<Update Reservation IsOff>>\n");

		System.out.println("Name?");
		String name = scanner.nextLine();

		System.out.println("Phone Number?");
		String phoneNum = scanner.nextLine();

		System.out.println("Reserved Date and time?");
		String reservedDateTime = scanner.nextLine();

		System.out.println("New Date and time?");
		String newDateTime = scanner.nextLine();

		if (updateReservation(name, phoneNum, reservedDateTime, newDateTime) != 0)
			success();
		else
			fail();
	}
}
