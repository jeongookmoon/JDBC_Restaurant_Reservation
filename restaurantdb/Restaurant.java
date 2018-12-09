import java.util.Scanner;
/*
 * Author: Jeong Moon, Hung Tang, Faith Chau
 * Description: This is the restaurant reservation system allowing restaurant can perform multiple task. It also
 * allow its customer and employee utilize it. This is the semester project done by Team Pineapple.
 * Date: 12/8/2018
 */
/*
 * main method of the app displaying different categories. Each user will select the category that they belongs to.
 */
public class Restaurant {
	public static void main(String[] args) {
		System.out.println("Welcome to the DB Restaurant Reservation System");
		Restaurantdb res = new Restaurantdb();
		Scanner scan = new Scanner(System.in);
		boolean endFlag = false;
		while (endFlag != true) {
			System.out.println("Please select one option: ");
			System.out.println("[A] DB employee?");
			System.out.println("[B] DB customer?");
			System.out.println("[C] DB restaurant?");
			System.out.println("[D] DB currentDropIns?");
			System.out.println("[E] DB reservations?");
			System.out.println("[Q] Quit");

			try {
				String input = scan.nextLine().toLowerCase();
				switch (input) {
				case "a":
					res.employeeMenu();
					break;
				case "b":
					res.customerMenu();
					break;
				case "c":
					res.restaurantMenu();
					break;
				case "d":
					res.currentDropInsMenu();
					break;
				case "e":
					res.reservationMenu();
					break;
				case "q":
					endFlag = true;
					break;
				default:
					menuError();
				}
			} catch (Exception error) {
				System.out.println(error);
			}
		}

		scan.close();
		res.closeConnection();
		System.out.println("Good Bye");
	}
	/*
	 * menuError will user choose invalid option
	 */
	public static void menuError() {
		System.out.println("Please Choose Valid Option");
	}
}
