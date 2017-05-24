import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Lab11Task1Server {
	private static final int port = 8888;
	private final static String host = "localhost";

	public static void main(String[] args) throws Exception {

		try {

			InetSocketAddress server = new InetSocketAddress(host, port);
			//Selector selector = Selector.open();

			ServerSocketChannel serverSocketChannel = ServerSocketChannel
					.open();
			//start non-block mode
			serverSocketChannel.socket().bind(server);
			// SelectionKey key = serverSocketChannel.register(selector,
			// SelectionKey.OP_READ);

			System.out.println("server/" + server.getAddress());

			// connect
			System.out.println("server start, waiting");

			String ClientInput;
			String ServerOutput;

			while (true) {
				SocketChannel socketChannel = serverSocketChannel.accept();
				System.out.println("client/"
						+ socketChannel.socket().getLocalAddress());
				// output steam
				DataOutputStream socketOut = new DataOutputStream(socketChannel
						.socket().getOutputStream());
				// input steam
				DataInputStream socketIn = new DataInputStream(socketChannel
						.socket().getInputStream());
				// read from keyboard
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				do {
					ClientInput = socketIn.readUTF();
					System.out.println(ClientInput);
					ServerOutput = ClientInput.toUpperCase();
					socketOut.writeUTF(ServerOutput);
				} while (!ServerOutput.equals("X"));

				// close all steam and channel
				socketIn.close();
				socketOut.close();
				socketChannel.close();
				serverSocketChannel.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("sth error");
		}
	}

}



