     
    import java.io.BufferedReader;  
    import java.io.DataInputStream;  
    import java.io.DataOutputStream;  
    import java.io.IOException;  
    import java.io.InputStreamReader;  
    import java.net.ServerSocket;  
    import java.net.Socket;  
      
    /**  
     * 服务器的工作就是在指定的端口上监听 
     * 建立连接 
     * 打开输出流 
     * 封装输出流 
     * 向客户端发送数据 
     * 关闭打开的输出流 
     * 关闭打开的Socket对象 
     * 服务器端的程序,在while循环中所执行的动作 
     * 听，说，听，说... 
     * @author Administrator 
     * 
     */  
    public class server {  
        private static final int port = 8888;  
        public static void main(String[] args) {  
            try {  
                ServerSocket ss = new ServerSocket(port);  
                //socket对象调用accept方法,等待请求的连接  
                System.out.println("服务器已启动,等待请求...");  
                Socket s = ss.accept();  
                //打开并封装输出流  
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
                //打开并封装输入流  
                DataInputStream dis = new DataInputStream(s.getInputStream());  
                //封装并读取键盘上的输入流  
                BufferedReader br = new BufferedReader  
                        (new InputStreamReader(System.in));  
                String info;  
                while(true){  
                    //接收客户端发过来的信息  
                    info = dis.readUTF();  
                    System.out.println("server:"+info);  
                    //如果发现对方说bye,则结束会话  
                    if(info.equals("bye")){  
                        break;  
                    }  
                    //读取键盘上的输入流,写入客户端  
                    info = br.readLine()+"+1s";
                    dos.writeUTF(info);  
                    //如果服务器自己说bye,也结束会话  
                    if(info.equals("bye")){  
                        break;  
                    }  
                }  
                dis.close();  
                dos.close();  
                s.close();  
                ss.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
                System.out.println("网络连接异常,退出程序...");  
            }  
        }  
    }  