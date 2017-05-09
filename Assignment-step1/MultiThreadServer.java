import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class MultiThreadServer {
private static final int port = 8888;

public static void main(String[] args) {
try {
ServerSocket ss = new ServerSocket(port);
// connect
System.out.println("server start, waiting");
Socket s = ss.accept();
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
} catch (IOException e) {
e.printStackTrace();
System.out.println("sth error");
		}
	}
}