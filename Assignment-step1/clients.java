import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class clients {
      private final static String host = "localhost";
      private final static int port = 8888;

      public static void main(String[] args) {
           try {
                 Socket s = new Socket(host, port);
                 System.out.println("clients start.");
                 // output steam
                 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                 // input steam
                 DataInputStream dis = new DataInputStream(s.getInputStream());
                 // read from keyboard
                 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                 String info;
                 int resultNumber;
                 while (true) {
                     System.out.println("Please enter a number between 0-2");                      
                     resultNumber = Integer.parseInt(br.readLine());
                     //System.out.println("server number is:"+resultNumber);
                     while (2<resultNumber||resultNumber<0){
                            // read from keyboard
                            System.out.println("Wrong input,Please enter a number between 0-2");                      
                            //info = br.readLine();
                            resultNumber = Integer.parseInt(br.readLine());
                          //  System.out.println("server number is:"+resultNumber);
                            // if user say bye,end
                            // get server information
                      }
                      // send to server first time
                      dos.writeInt(resultNumber);                      
                     // System.out.println("send" + info + "out,plase say the guess number");
                 break;
                 }
                 while (true) {
                     System.out.println("Please guess sum,sum should between 0-4");                      
                     resultNumber = Integer.parseInt(br.readLine());
                     System.out.println("guess number is:"+resultNumber);
                     while (4<resultNumber||resultNumber<0){
                            // read from keyboard
                            System.out.println("Wrong input,Please enter a number between 0-4");                      
                            //info = br.readLine();
                            resultNumber = Integer.parseInt(br.readLine());
                         //   System.out.println("server number is:"+resultNumber);
                            // if user say bye,end
                            // get server information
                      }
                      // send to server first time
                      dos.writeInt(resultNumber);
                      info = dis.readUTF();
                      System.out.println(info);
                      break;
                      }
                 dis.close();
                 dos.close();
                 s.close();

           } catch (Exception e) {
                 e.printStackTrace();
           }
      }
}