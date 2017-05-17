import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Lab11Task1Clients {
      private final static String host = "localhost";
      private final static int port = 8888;

      public static void main(String[] args) {
           try {
   			  InetSocketAddress server = new InetSocketAddress(host,port);
        	   SocketChannel socketChannel = SocketChannel.open();
        	   socketChannel.connect(server);

                 System.out.println("clients start.");
                 System.out.println("client/"+socketChannel.socket().getLocalAddress()+"/"+socketChannel.socket().getLocalPort());
                 String read;
                 String input;

                 // output steam
                 DataOutputStream socketOut = new DataOutputStream(socketChannel.socket().getOutputStream());
                 // input steam
                 DataInputStream socketIn = new DataInputStream(socketChannel.socket().getInputStream());
                 // read from keyboard
                 BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                 do{
                     System.out.println("Start input");                      
                     input = br.readLine();
                      // send to server second time
                      socketOut.writeUTF(input);
                      read = socketIn.readUTF();
                      System.out.println(read);
                      }while(!read.equals("X"));
                 //steam close, accept close
                 socketIn.close();
                 socketOut.close();
                 socketChannel.close();

           } catch (Exception e) {
                 e.printStackTrace();
           }
      }
      
}