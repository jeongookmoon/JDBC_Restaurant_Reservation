import java.util.Scanner;

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

	public static void menuError() {
		System.out.println("Please Choose Valid Option");
	}
}
