package bio;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class BIOServer {

    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(6666);

        while (true){
            Socket accept = serverSocket.accept();
            Executors.newCachedThreadPool().execute(()->{
                handler(accept);
            });
        }
    }

    public static void handler(Socket socket){
        try {
            byte[] pac = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true){
                int read = inputStream.read(pac);
                if(read != -1){
                    System.out.println(new String(pac,0,read));
                }
            }

        }catch (Exception e){

        }finally {

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
