package lab6;
import java.util.Scanner;

class Task1a implements Runnable {
	private static String input;
	private static Scanner keyboard = new Scanner(System.in);

	
	
	public void run() {
		// thread
		try {
			System.out.println("thread get: " + input);
			System.out.println("System end");

		} catch (Exception e) {
			System.out.println("sth wrong");
		}
	}



	public static void main(String args[]) {
		//main function
		System.out.println("please enter sth");
		input = keyboard.nextLine();
		System.out.println("user say: " + input);
		Task1a sender = new Task1a();
		Thread sender1 = new Thread(sender);
		sender1.start();
	}

}
