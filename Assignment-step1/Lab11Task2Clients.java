import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Lab11Task2Clients {
      private final static String host = "localhost";
      private final static int port = 8888;
      private static final int BUFFER_SIZE = 256;


      public static void main(String[] args) {
           try {
   			  InetSocketAddress server = new InetSocketAddress(host,port);
        	   SocketChannel socketChannel = SocketChannel.open();
        	   socketChannel.connect(server);

        	   
                 System.out.println("clients start.");
                 System.out.println("client/"+socketChannel.socket().getLocalAddress()+"/"+socketChannel.socket().getLocalPort());
                 ByteBuffer read;
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
                     ByteBuffer outbuffer = ByteBuffer.wrap(input.getBytes());
                     outbuffer.flip();
                     socketChannel.write(outbuffer);
                      //socketOut.writeUTF(input);
                     read = ByteBuffer.allocate(BUFFER_SIZE);
                     
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