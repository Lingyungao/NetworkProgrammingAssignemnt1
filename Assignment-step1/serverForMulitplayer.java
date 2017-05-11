import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.*;

public class serverForMulitplayer {
    private int port=8888;
    private ServerSocket serverSocket;
    private ExecutorService executorService;//thread pool
    private final int POOL_SIZE=10;//thread pool size

    public serverForMulitplayer() throws IOException{
        serverSocket=new ServerSocket(port);
        executorService=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*POOL_SIZE);
        System.out.println("server start, waiting");
    }

    public void service(){
        int[] countArray = new int[5];
        int player = 0;
        while(true){
            Socket socket=null;
            try {
                //get user accept to create thread
            	player ++;
            	//pick less than 4 players
            	if(player>4){
            		player = 0;
            		countArray = new int[5];
            	}
                socket=serverSocket.accept();
				executorService.execute(new game(socket,countArray,player));
				System.out.println("countArray is " + countArray);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new serverForMulitplayer().service();

    }

}


class pickPlayer implements Runnable{
    private Socket socket;
	private int[] countArray;
	public pickPlayer(Socket socket, int[] countArray){
        this.socket=socket;
        this.countArray = countArray;

    }
	@Override
	public void run() {
		int player=0;
		// TODO Auto-generated method stub
		for(int i =0;i<3;i++){
			new game(socket,countArray,player);
		}
		
	}
	
}


class game implements Runnable{
    private Socket socket;
	private int[] countArray;
	private int player;
    public game(Socket socket, int[] countArray,int player){
        this.socket=socket;
        this.countArray = countArray;
        this.player = player;

    }

    public int a(int a){
        return a;
    }
    private PrintWriter getWriter(Socket socket) throws IOException{
        OutputStream socketOut=socket.getOutputStream();
        return new PrintWriter(socketOut,true);
    }
    private BufferedReader getReader(Socket socket) throws IOException{
        InputStream socketIn=socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn));
    }

    public String echo(String msg){
        return "echo:"+msg;
    }
    @Override
    public void run(){
		try {
			System.out.println("server start, waiting");
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
				
				//check if guess number is exist
				while(searchReasult(countArray,resultNumber) == true){
					System.out.println("guess exist" );
					socketOut.writeUTF("False,guess" + resultNumber + " exist,please try other number");
					resultNumber = socketIn.readInt();
				}
				//guess number not used,new guess number add to array
				System.out.println("server guess:" + resultNumber);
				socketOut.writeUTF("PASS");
				countArray[player]=resultNumber;
				
				
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
			// update accept log
			NowTime = refFormatNowDate();
			clientInfo = userIP + " at " + NowTime + " leave ";
			System.out.println(clientInfo);
			writeToFile(acceptLog, clientInfo);

		} catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
            try {
                if(socket!=null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    //below are tool functions
    
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
	
    /*
     * write to file or update file
     */
	public static void writeToFile(File filename, String log) throws IOException {
		FileWriter fw = new FileWriter(filename.getName(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(log);
		bw.write("\r\n");
		bw.close();
	}
	
	public static boolean searchReasult(int[] countArray,int resultNumber){
		for(int i=0;i<5;i++){
			if(countArray[i] == resultNumber){
				return true;
			}
		}
		return false;
	}
	
}