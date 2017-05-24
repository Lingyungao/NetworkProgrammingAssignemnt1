import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
 
/**
 * @author Crunchify.com
 *
 */
 
public class Lab11Task2ClientsTest {
 
	public static void main(String[] args) throws IOException, InterruptedException {
 
		InetSocketAddress crunchifyAddr = new InetSocketAddress("localhost", 8888);
		SocketChannel crunchifyClient = SocketChannel.open(crunchifyAddr);
 
		log("Connecting to Server on port 8888...");
 
		ArrayList<String> companyDetails = new ArrayList<String>();
		String input;

		while (true) {
			System.out.println("client/"
					+ crunchifyClient.socket().getLocalAddress());
			// output steam
			DataOutputStream socketOut = new DataOutputStream(crunchifyClient
					.socket().getOutputStream());
			// input steam
			DataInputStream socketIn = new DataInputStream(crunchifyClient
					.socket().getInputStream());
			// read from keyboard
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			do {
				input = socketIn.readUTF();
				companyDetails.add(input);




		
		for (String companyName : companyDetails) {
 
			byte[] message = new String(companyName).getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(message);
			crunchifyClient.write(buffer);
 
			log("sending: " + companyName);
			buffer.clear();
 
			// wait for 2 seconds before sending next message
			Thread.sleep(2000);
		}}
		while (!input.equals("X"));
			
		crunchifyClient.close();
		} 

	}
 
	private static void log(String str) {
		System.out.println(str);
	}
}