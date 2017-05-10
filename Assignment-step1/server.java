import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class server {
private static final int port = 8888;

public static void main(String[] args) {
try {
ServerSocket ss = new ServerSocket(port);
// connect
System.out.println("server start, waiting");
Socket s = ss.accept();
String NowTime = refFormatNowDate();
String userIP = ss.getInetAddress().getHostAddress();
String userName = ss.getInetAddress().getHostName();


// output steam
DataOutputStream dos = new DataOutputStream(s.getOutputStream());
// input steam
DataInputStream dis = new DataInputStream(s.getInputStream());
// read from keyboard
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String info;
int max=2;
int min=0;
Random random = new Random();
int randomNumber = random.nextInt(max)%(max-min+1) + min;
int resultNumber;
int sumNumber;
while (true) {
//get user input
resultNumber = dis.readInt();
//info = dis.readUTF();
System.out.println("server:" + resultNumber);
// if user say "bye",stop connect
// read from keyboard and send to user
sumNumber = resultNumber + randomNumber;
System.out.println("server number is:"+resultNumber);
System.out.println("random Number is:"+randomNumber);
System.out.println("sum number is:"+sumNumber);
info = "server:please enter guess number";
break;
}
while (true) {
	//get user input
	resultNumber = dis.readInt();
	//info = dis.readUTF();
	System.out.println("server guess:" + resultNumber);
	if((sumNumber-resultNumber)>0){
		info = "you win";
	}
	else
		info = "you fail";
	dos.writeUTF(info);
	break;
	}
dis.close();
dos.close();
s.close();
ss.close();
String clientLog = "user " + userName + " from ip: " + userIP + " at " + NowTime;
System.out.println(clientLog);

} catch (IOException e) {
e.printStackTrace();
System.out.println("sth error");
		}
	}

/* set time format
 * and get system
 */
public static String refFormatNowDate() {
	  Date nowTime = new Date(System.currentTimeMillis());
	  SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd");
	  String retStrFormatNowDate = sdFormatter.format(nowTime);

	  return retStrFormatNowDate;
	}

/*file check and create
 * 
 */
public static boolean createFile(File fileName)throws Exception{  
	  boolean flag=false;  
	  try{
	   if(!fileName.exists()){  
	    fileName.createNewFile();  
	    flag=true;  
	   }  
	  }catch(Exception e){  
	   e.printStackTrace();  
	  }  
	  return true;  
	 }   
}
