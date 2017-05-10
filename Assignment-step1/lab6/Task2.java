package lab6;
import java.util.Scanner;

class Task2 implements Runnable {
	private String input;
	private static Scanner keyboard = new Scanner(System.in);
    //main thread
    public void run() {	
		System.out.println("please enter sth");
		input = keyboard.nextLine();		
		System.out.println("user say: " + input);
		
        CallBack callback = new CallBack();
        Thread callback1 = new Thread(callback);
        callback1.start();
        //start call back thread
    }
    //call back thread
    class CallBack implements Runnable {
       String callback;
 	   @Override
		public void run() {
			callback = input;
			try {
				System.out.println("Call back get: " + input);
				if (callback.equals("X")) {
					System.out.println("System end");
				} else {
					System.out.println("System print out : " + input);
					try {		
						System.out.println("sleeping");
						Thread.sleep(500);
						//sleep
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Task2 sender = new Task2();
					Thread sender1 = new Thread(sender);
					sender1.start();
					// not "x" restart main thread,check input again
				}
			} catch (Exception e) {
				System.out.println("sth wrong");
			}
		}

	}
 
    //start main thread
	public static void main(String args[]) {
           Task2 sender = new Task2();           
           Thread sender1 = new Thread(sender);
           sender1.start();
	}

}
	
	
	
