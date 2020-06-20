package bio;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class BIOClient {


    public static void main(String[] args) throws Exception{
        Socket socket = new Socket();
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",6666);
        socket.connect(socketAddress);

        socket.getOutputStream().write("那真的牛批".getBytes());
        socket.getOutputStream().flush();
    }
}
