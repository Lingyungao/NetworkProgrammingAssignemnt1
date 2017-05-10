package lab6;
import java.util.Scanner;

class Task1b implements Runnable {
	private static String input;
	private static Scanner keyboard = new Scanner(System.in);
	public String i;
		String callback;
		@Override
		public void run() {
			callback = input;
			//get data
			try {
				System.out.println("Thread get: " + input);
				if (callback.equals("x")) {
					System.out.println("System end");
					//end system
				} else {
					System.out.println("System print out : " + input);
                    new Task1b();
					Task1b.main(null);;
					// not "X" restart main thread
				}
			} catch (Exception e) {
				System.out.println("sth wrong");
			}
		}

	



	public static void main(String args[]) {
		// start main function
		try {
			System.out.println("please enter sth");
			input = keyboard.nextLine();
			System.out.println("user say: " + input);
			Task1b sender = new Task1b();
			Thread sender1 = new Thread(sender);
			sender1.start();
			// start call back thread
		} catch (Exception e) {
			System.out.println("sth wrong");
		}
	}
	

	}

