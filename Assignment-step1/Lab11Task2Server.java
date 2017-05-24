import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Lab11Task2Server {
  private static final int PORT_NUMBER = 8888;
  private static final int BUFFER_SIZE = 256;

  public static void main(String[] args) throws IOException {
    ServerSocketChannel server = ServerSocketChannel.open();
    server.socket().bind(new InetSocketAddress(PORT_NUMBER));
    server.socket().setReuseAddress(true);
    server.configureBlocking(false);

    Selector selector = Selector.open();
    server.register(selector, SelectionKey.OP_ACCEPT);

    ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
    String upper;
    while (true) {
      int channelCount = selector.select();
      if (channelCount > 0) {
        Set<SelectionKey> keys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = keys.iterator();
        while (iterator.hasNext()) {
          SelectionKey key = iterator.next();
          iterator.remove();

          if (key.isAcceptable()) {
            SocketChannel client = server.accept();
            System.out.println(client.getLocalAddress());
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ, client.socket().getPort());
          } else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            p("port: " + key.attachment());
            if (client.read(buffer) < 0) {
              key.cancel();
              client.close();
            } else {
              buffer.flip(); // read from the buffer
              upper = buffer.toString().toUpperCase();
              System.out.println(upper);
              ByteBuffer outbuffer = ByteBuffer.wrap(upper.getBytes());
              outbuffer.flip();
              client.write(outbuffer);
              buffer.clear(); // write into the buffer            
              }
          }
        }
      }
    }
  }

  private static void p(String s) {
    System.out.println(s);
  }
}
