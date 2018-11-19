
import java.util.Scanner;
import java.sql.*;


/**
 * @author Hung Tang, Faith Chau, Jeong Moon
 * @description the application is about restaurant reservation system
 *
 */
public class Restaurantdb {

	Connection conn ;

	public Restaurantdb(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//DriverManager.useSSL=false;
			conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/RestaurantReservation","root","faith114"
			);

		}catch(Exception e){
			System.out.println(e);
		}
	}

	public void accessEmployee() {
		//System.out.println("Please enter your server ID");
		System.out.println("Insert Employee Name");
		Scanner scanner = new Scanner(System.in);
		String eName = scanner.nextLine();

		try{
			// PreparedStatement stmt = conn.prepareStatement("INSERT INTO Employee(name,isOff) VALUES (?,?)");
			// stmt.setString(1, eName);
			// stmt.setInt(2, 0);
			// stmt.executeUpdate();

			System.out.println("Change to time off?");
			System.out.println("[A] Yes");
			System.out.println("[B] No");
			String ans = scanner.next();
			if (ans.equals("A"))
			{
				PreparedStatement stmt2 = conn.prepareStatement("Update Employee set isOff = 1 where name = ?");
				stmt2.setString(1, eName);
				stmt2.executeUpdate();
			}
		}catch(Exception e){
			System.out.println(e);
		}

	}

	public void createCustomer() {
		System.out.println("Can we have your first name and last name, please?");
		Scanner firstN = new Scanner(System.in);
		System.out.print("Please enter your First Name: ");
		String firstName = firstN.nextLine();
		Scanner lastN = new Scanner(System.in);
		System.out.print("Please enter your Last Name: ");
		String lastName = lastN.nextLine();
		System.out.println("is your name "+ firstName+ " "+ lastName+" correct?");
		System.out.println("[A] Yes");
		System.out.println("[B] No");
		Scanner optionScan = new Scanner(System.in);
		String cusInput = optionScan.nextLine();
		if (cusInput.equals("A".toLowerCase())) {
			System.out.println("Please enter your phone number: ");
			Scanner phoneN = new Scanner(System.in);
			String phoneNumber = phoneN.next(); //Will match with database later

			try{
				PreparedStatement stmt = conn.prepareStatement("INSERT INTO Customer(name, phoneNum) VALUES (?,?)");
				stmt.setString(1, firstName+" "+lastName);
				stmt.setString(2, phoneNumber);
				stmt.executeUpdate();
			}catch(Exception e){
				System.out.println(e);
			}


		}



	}
	public void returnCustomer() {

		System.out.println("Can we have your phone number, please? ");
		Scanner phoneN = new Scanner(System.in);
		String phoneNumber = phoneN.next(); //Will match with database later
		reservationOption();



	}
	public void customerPrompt() {
		System.out.println("Are you new customer?");
		System.out.println("Please select one option: ");
		System.out.println("[A] Yes");
		System.out.println("[B] No");
		Scanner customerScan = new Scanner(System.in);
		String cusInput = customerScan.nextLine();
		if (cusInput.equals("A".toLowerCase())) {
			System.out.println("Thank you for choosing DB Restaurant");
			createCustomer();
		}
		else if (cusInput.equals("B".toLowerCase())){
			System.out.println("Welcome back, thank you for choosing DB restaurant");
			returnCustomer();
		}
		else {
			System.out.println("That's not a valid answer");
		}

	}
	public void reservationOption() {
		System.out.println("Please select one of the options below: ");
		System.out.println("[A] Make a new reservation");
		System.out.println("[B] Current drop in ");
		System.out.println("[C] Update personal information ");
		Scanner selectOption = new Scanner(System.in);
		String optionS= selectOption.nextLine();

	}

	/**
	 * main method prompting the user
	 * @param args
	 */
	// public static void main(String[] args) {
	//
	// }

}
