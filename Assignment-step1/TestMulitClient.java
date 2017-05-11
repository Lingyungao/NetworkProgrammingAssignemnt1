import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestMulitClient {

    public static void main(String[] args) {
        int numTasks = 5;

        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < numTasks; i++) {
            exec.execute(createTask(i));
        }

    }

    // ����һ���򵥵�����
    private static Runnable createTask(final int taskID) {
        return new Runnable() {
            private Socket socket = null;
            private int port=8888;

            public void run() {
                System.out.println("Task " + taskID + ":start");
                try {                    
                    socket = new Socket("localhost", port);
                    // ���͹ر�����
                    OutputStream socketOut = socket.getOutputStream();
                    socketOut.write("shutdown\r\n".getBytes());

                    // ���շ������ķ���
                    BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
                    String msg = null;
                    while ((msg = br.readLine()) != null)
                    System.out.println(msg);
                } catch (IOException e) {                    
                    e.printStackTrace();
                }
            }

        };
    }
}