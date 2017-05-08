     
    import java.io.BufferedReader;  
    import java.io.DataInputStream;  
    import java.io.DataOutputStream;  
    import java.io.IOException;  
    import java.io.InputStreamReader;  
    import java.net.ServerSocket;  
    import java.net.Socket;  
      
    /**  
     * �������Ĺ���������ָ���Ķ˿��ϼ��� 
     * �������� 
     * ������� 
     * ��װ����� 
     * ��ͻ��˷������� 
     * �رմ򿪵������ 
     * �رմ򿪵�Socket���� 
     * �������˵ĳ���,��whileѭ������ִ�еĶ��� 
     * ����˵������˵... 
     * @author Administrator 
     * 
     */  
    public class server {  
        private static final int port = 8888;  
        public static void main(String[] args) {  
            try {  
                ServerSocket ss = new ServerSocket(port);  
                //socket�������accept����,�ȴ����������  
                System.out.println("������������,�ȴ�����...");  
                Socket s = ss.accept();  
                //�򿪲���װ�����  
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
                //�򿪲���װ������  
                DataInputStream dis = new DataInputStream(s.getInputStream());  
                //��װ����ȡ�����ϵ�������  
                BufferedReader br = new BufferedReader  
                        (new InputStreamReader(System.in));  
                String info;  
                while(true){  
                    //���տͻ��˷���������Ϣ  
                    info = dis.readUTF();  
                    System.out.println("server:"+info);  
                    //������ֶԷ�˵bye,������Ự  
                    if(info.equals("bye")){  
                        break;  
                    }  
                    //��ȡ�����ϵ�������,д��ͻ���  
                    info = br.readLine()+"+1s";
                    dos.writeUTF(info);  
                    //����������Լ�˵bye,Ҳ�����Ự  
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
                System.out.println("���������쳣,�˳�����...");  
            }  
        }  
    }  