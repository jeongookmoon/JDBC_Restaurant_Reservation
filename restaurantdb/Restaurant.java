import java.sql.PreparedStatement;
import java.util.Scanner;
public class Restaurant{
  public static void main(String[]args){
	  	System.out.println("Welcome to the DB Restaurant Reservation System");
		Scanner scan = new Scanner(System.in);
		int terminate = 0;
		while(terminate == 0) {
			System.out.println("Please select one option: ");
			System.out.println("[A] DB employee?");
			System.out.println("[B] DB customer?");
			System.out.println("[Q] Quit");
			String input = scan.nextLine().toLowerCase();
			Restaurantdb res = new Restaurantdb();
			try {
				if (input.equals("a")) {
					res.accessEmployee();
				}
				else if(input.equals("b")){
					res.customerPrompt();
				}
				else if(input.equals("q")){
					terminate = 1;
				}
				else {
					System.out.println("Please Choose either A or B");
				}
			}catch(Exception error) {
				System.out.println(error);	
			}	
		}
		System.out.println("Good Bye");
  }
}
