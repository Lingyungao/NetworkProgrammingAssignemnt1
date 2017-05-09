import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.Socket;  
  
/** 
 * ����socket����,ָ����������ip��ַ�ͷ����������Ķ˿ں� 
 * �ͻ�����new��ʱ��,�ͷ�������������,�������˾ͻ���д��� 
 * �ͻ�����whileѭ����ִ�в���: 
 * ����˵������˵... 
 * 
 */  
public class clients {  
    private final static String host = "localhost";  
    private final static int port = 8888;  
    public static void main(String[] args) {  
        try {  
            Socket s = new Socket(host,port);  
            System.out.println("�ͻ�������,��������...");  
            //�򿪲���װ�����  
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());  
            //�򿪲���װ������  
            DataInputStream dis = new DataInputStream(s.getInputStream());  
            //��ȡ����װ����������  
            BufferedReader br = new BufferedReader(  
                    new InputStreamReader(System.in));  
            String info;  
            while(true){  
                //�ͻ����ȶ�ȡ����������Ϣ  
                info = br.readLine();
                if(info.equals(0) || info.equals(1)){
                	

                //д�뵽������  
                dos.writeUTF(info);  
                //����ͻ����Լ�˵bye,�������Ի�  
                if(info.equals("bye")){  
                    break;  
                }  
                //���շ���������Ϣ  
                info = dis.readUTF();  
                System.out.println("������˵: "+ info); } 
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