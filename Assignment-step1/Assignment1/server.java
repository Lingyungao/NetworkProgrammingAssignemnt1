package Assignment1;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class server {
	private static final int port = 8888;

	public static void main(String[] args) throws Exception {
		try {
			ServerSocket serverSocket  = new ServerSocket(port);
			// connect
			System.out.println("server start, waiting");
			Socket socket = serverSocket.accept();
			String NowTime = refFormatNowDate();
			String userIP;
			String clientInfo;
			String info;
			int max = 2;
			int min = 0;
			int resultNumber;
			int sumNumber;
			int socketPort = socket.getPort();
			String gameInfo;

			// creat Connect log
			File acceptLog = new File("acceptLog.txt");
			createFile(acceptLog);

			// creat game history log
			File gamelog = new File("gamelog.txt");
			createFile(gamelog);

			// output steam
			DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
			// input steam
			DataInputStream socketIn = new DataInputStream(socket.getInputStream());
			// read from keyboard
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			Random random = new Random();
			int randomNumber = random.nextInt(max) % (max - min + 1) + min;

			while (true) {
				userIP = socketIn.readUTF();
				clientInfo = userIP + " using " + socketPort + " at " + NowTime + " accept " ;
				System.out.println(clientInfo);
				writeToFile(acceptLog, clientInfo);

				// get user input
				resultNumber = socketIn.readInt();

				// write to gamelog
				gameInfo = userIP + " send out " + resultNumber;
				writeToFile(gamelog, gameInfo);

				// read from keyboard and send to user
				sumNumber = resultNumber + randomNumber;
				
				// write to gamelog
				gameInfo = userIP + " get sumNumber " + sumNumber;
				writeToFile(gamelog, gameInfo);

				System.out.println("server number is:" + resultNumber);
				System.out.println("random Number is:" + randomNumber);
				System.out.println("sum number is:" + sumNumber);
				break;
			}
			info = "server:sum number ready";
			socketOut.writeUTF(info);
			while (true) {
				// get user input
				resultNumber = socketIn.readInt();
				System.out.println("server guess:" + resultNumber);
				// upadte game info
				gameInfo = userIP + " guess sumNumber " + resultNumber;
				writeToFile(gamelog, gameInfo);

				// game compare
				if ((sumNumber - resultNumber) * (sumNumber - resultNumber) <= 1) {
					info = "win";
					gameInfo = userIP + info;
					writeToFile(gamelog, gameInfo);

				} else {
					info = "fail";
					gameInfo = userIP + " " + info;
					writeToFile(gamelog, gameInfo);
				}
				socketOut.writeUTF(info);
				break;
			}
			// all steam close
			socketIn.close();
			socketOut.close();
			socket.close();
			serverSocket.close();
			// update accept log
			NowTime = refFormatNowDate();
			clientInfo = userIP + " at " + NowTime + " leave ";
			System.out.println(clientInfo);
			writeToFile(acceptLog, clientInfo);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("sth error");
		}
	}

	/*
	 * set time format and get system
	 */
	public static String refFormatNowDate() {
		Date nowTime = new Date(System.currentTimeMillis());
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
		String retStrFormatNowDate = sdFormatter.format(nowTime);

		return retStrFormatNowDate;
	}

	/*
	 * file check and create
	 */
	public static boolean createFile(File fileName) throws Exception {
		boolean flag = false;
		try {
			if (!fileName.exists()) {
				fileName.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void writeToFile(File filename, String log) throws IOException {
		FileWriter fw = new FileWriter(filename.getName(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(log);
		bw.write("\r\n");
		bw.close();
	}

	// info = socketIn.readUTF();
	// System.out.println("server:" + resultNumber);
}
