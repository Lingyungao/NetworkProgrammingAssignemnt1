import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.Socket;  
  
/** 
 * 创建socket对象,指定服务器的ip地址和服务器监听的端口号 
 * 客户端在new的时候,就发出了连接请求,服务器端就会进行处理 
 * 客户端在while循环中执行操作: 
 * 听，说，听，说... 
 * 
 */  
public class clients {  
    private final static String host = "localhost";  
    private final static int port = 8888;  
    public static void main(String[] args) {  
        try {  
            Socket s = new Socket(host,port);  
            System.out.println("客户端启动,发送请求...");  
            //打开并封装输出流  
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
            //打开并封装输入流  
            DataInputStream dis = new DataInputStream(s.getInputStream());  
            //读取并封装键盘输入流  
            BufferedReader br = new BufferedReader(  
                    new InputStreamReader(System.in));  
            String info;  
            while(true){  
                //客户端先读取键盘输入信息  
                info = br.readLine();
                if(info.equals(0) || info.equals(1)){
                	

                //写入到服务器  
                dos.writeUTF(info);  
                //如果客户端自己说bye,即结束对话  
                if(info.equals("bye")){  
                    break;  
                }  
                //接收服务器端信息  
                info = dis.readUTF();  
                System.out.println("服务器说: "+ info); } 
                if(info.equals("bye")){  
                    break;  
                }
                else
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