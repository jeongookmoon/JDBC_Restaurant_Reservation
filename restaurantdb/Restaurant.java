import java.util.Scanner;

public class Restaurant{
  public static void main(String[]args){
	  	System.out.println("Welcome to the DB Restaurant Reservation System");
		Scanner scan = new Scanner(System.in);
		boolean endFlag = false;
		while(endFlag != true) {
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
					endFlag = true;
				}
				else {
					menuError();
				}
			}catch(Exception error) {
				System.out.println(error);	
			}	
		}
		
		scan.close();
		System.out.println("Good Bye");
  }
  
  public static void menuError() {
	  System.out.println("Please Choose Valid Option");
  }
}
