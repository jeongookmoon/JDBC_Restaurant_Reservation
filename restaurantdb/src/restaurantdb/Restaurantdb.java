/**
 * 
 */
package restaurantdb;

import java.util.Scanner;

/**
 * @author Hung Tang, Faith Chau, Jeong Moon
 * @description the application is about restaurant reservation system
 *
 */
public class Restaurantdb {
	
	public void accessEmployee() {
		System.out.println("Please enter your server ID");
		
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
			int phoneNumber = phoneN.nextInt(); //Will match with database later
			reservationOption();
		}
		
		
		
	}
	public void returnCustomer() {
		
		System.out.println("Can we have your phone number, please? ");
		Scanner phoneN = new Scanner(System.in);
		int phoneNumber = phoneN.nextInt(); //Will match with database later
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
	public static void main(String[] args) {
		System.out.println("Welcome to the DB Restaurant Reservation System");
		System.out.println("Please select one option: ");
		System.out.println("[A] DB employee?");
		System.out.println("[B] DB customer?");
		Scanner scan = new Scanner(System.in);		
		String input = scan.nextLine();
		Restaurantdb res = new Restaurantdb();
		if (input.equals("A".toLowerCase())) {
			res.accessEmployee();
		}
		else {
			res.customerPrompt();
		}
	}

}
