import java.util.Scanner;
public class Restaurant{
  public static void main(String[]args){
    System.out.println("Welcome to the DB Restaurant Reservation System");
		System.out.println("Please select one option: ");
		System.out.println("[A] DB employee?");
		System.out.println("[B] DB customer?");
		Scanner scan = new Scanner(System.in);
		String input = scan.next();
		Restaurantdb res = new Restaurantdb();
		if (input.equals("A")) {
			res.accessEmployee();
		}
		else {
			res.customerPrompt();
		}

  }
}
