package Assignment1;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class clients {
      private final static String host = "localhost";
      private final static int port = 8888;

      public static void main(String[] args) {
           try {
                 Socket socket = new Socket(host, port);
                 System.out.println("clients start.");
                 String info;

                 // output steam
                 DataOutputStream socketOut = new DataOutputStream(socket.getOutputStream());
                 // input steam
                 DataInputStream socketIn = new DataInputStream(socket.getInputStream());
                 // read from keyboard
                 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                 
                 //register to server
     			 InetAddress userIP = InetAddress.getLocalHost();
     			 info = userIP.toString();
     			 System.out.println("User :" + userIP + "ready");
     			 socketOut.writeUTF(info);
     			 
                 int resultNumber;
                 while (true) {
                     System.out.println("Please enter a number between 0-2");                      
                     resultNumber = Integer.parseInt(br.readLine());
                     while (2<resultNumber||resultNumber<0){
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
                     System.out.println("Please guess sum,sum should between 0-4");                      
                     resultNumber = Integer.parseInt(br.readLine());
                     System.out.println("guess number is:"+resultNumber);
                     while (4<resultNumber||resultNumber<0){
                            // read from keyboard
                            System.out.println("Wrong input,Please enter a number between 0-4");                      
                            resultNumber = Integer.parseInt(br.readLine());
                      }
                      // send to server second time
                      socketOut.writeInt(resultNumber);
                      info = socketIn.readUTF();
                      System.out.println(info);
                      break;
                      }
                 //steam close, accept close
                 socketIn.close();
                 socketOut.close();
                 socket.close();

           } catch (Exception e) {
                 e.printStackTrace();
           }
      }
      
}