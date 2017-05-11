package Assignment1;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class clientsForMulitplayer {

	public static void main(String[] args) throws IOException {
		// int numTasks = 5;

		ExecutorService exec = Executors.newCachedThreadPool();

		exec.execute(createTask(0));

	}

	// Task(game
	private static Runnable createTask(final int taskID) throws IOException {
		final Timer timer = new Timer(); 
		TimerTask task; 
		return new Runnable() {
			private Socket socket = null;
			private int port = 8888;
			public void run() {
				System.out.println("Task " + taskID + ":start");
				try {
					System.out.println("clients start.");
					String info;
					socket = new Socket("localhost", port);
					// output steam
					DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
					// input steam
					DataInputStream socketIn = new DataInputStream(socket.getInputStream());
					// read from keyboard
					BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

					// register to server
					InetAddress userIP = InetAddress.getLocalHost();
					info = userIP.toString();
					System.out.println("User :" + userIP + "ready");
					socketOut.writeUTF(info);

					int resultNumber;
					while (true) {
						System.out.println("Please enter a number between 0-2");
						resultNumber = Integer.parseInt(br.readLine());
						while (2 < resultNumber || resultNumber < 0) {
							// read from keyboard
							System.out.println("Wrong input,Please enter a number between 0-2");
							resultNumber = Integer.parseInt(br.readLine());
						}
						// send to server first time
						socketOut.writeInt(resultNumber);
						break;
					}
					socketIn.readUTF();
					System.out.println(info);
					while (true) {
						do {
							System.out.println("Please guess sum,sum should between 0-4");
							resultNumber = Integer.parseInt(br.readLine());
							System.out.println("guess number is:" + resultNumber);
							while (4 < resultNumber || resultNumber < 0) {
								// read from keyboard
								System.out.println("Wrong input,Please enter a number between 0-4");
								resultNumber = Integer.parseInt(br.readLine());
							}
							// send to server second time
							socketOut.writeInt(resultNumber);
							info = socketIn.readUTF();
							System.out.println(info);
						} while (!info.equals("PASS"));
						break;

					}
					// steam close, accept close
					socketIn.close();
					socketOut.close();
					socket.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
	}



}