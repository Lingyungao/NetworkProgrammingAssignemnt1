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

public class serverForMulitplayer {
	private static final int port = 8888;

	public static void main(String[] args) throws Exception {
		try {
			ServerSocket ss = new ServerSocket(port);
			// connect
			System.out.println("server start, waiting");
			Socket s = ss.accept();
			String NowTime = refFormatNowDate();
			String userIP = ss.getInetAddress().getHostAddress();
			String userName = ss.getInetAddress().getHostName();
			String clientInfo = "user " + userName + " from ip: " + userIP
					+ " at " + NowTime;
			String info;
			int max = 2;
			int min = 0;
			int resultNumber;
			int sumNumber;
			String gameInfo;

			// Connect log
			File acceptLog = new File("acceptLog.txt");
			createFile(acceptLog);
			writeToFile(acceptLog, clientInfo);

			// game history log
			File gamelog = new File("gamelog.txt");
			createFile(gamelog);

			// output steam
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			// input steam
			DataInputStream dis = new DataInputStream(s.getInputStream());
			// read from keyboard
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			Random random = new Random();
			int randomNumber = random.nextInt(max) % (max - min + 1) + min;

			while (true) {
				// get user input
				resultNumber = dis.readInt();

				// write to gamelog
				gameInfo = userName + " send out " + resultNumber;
				writeToFile(gamelog, gameInfo);

				// read from keyboard and send to user
				sumNumber = resultNumber + randomNumber;
				// write to gamelog
				gameInfo = userName + " get sumNumber " + sumNumber;
				writeToFile(gamelog, gameInfo);

				System.out.println("server number is:" + resultNumber);
				System.out.println("random Number is:" + randomNumber);
				System.out.println("sum number is:" + sumNumber);
				info = "server:please enter guess number";
				break;
			}
			while (true) {
				// get user input
				resultNumber = dis.readInt();
				// info = dis.readUTF();
				System.out.println("server guess:" + resultNumber);
				gameInfo = userName + " guess sumNumber " + resultNumber;
				writeToFile(gamelog, gameInfo);
                
				//game compare
				if ((sumNumber - resultNumber) * (sumNumber - resultNumber) <= 1) {
					info = "win";
					gameInfo = userName + info;
					writeToFile(gamelog, gameInfo);

				} else {
					info = "fail";
					gameInfo = userName + " " + info;
					writeToFile(gamelog, gameInfo);
				}
				dos.writeUTF(info);
				break;
			}
			dis.close();
			dos.close();
			s.close();
			ss.close();
			System.out.println(clientInfo);

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

	public static void writeToFile(File filename, String log)
			throws IOException {
		FileWriter fw = new FileWriter(filename.getName(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(log);
		bw.write("\r\n");
		bw.close();
	}

	// info = dis.readUTF();
	// System.out.println("server:" + resultNumber);
}
