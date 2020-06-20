package nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Fille {

    public static void main(String[] args) throws Exception{
        String str = "hello,王骁";

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\aaaaa\\file01.txt");

        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        byteBuffer.put(str.getBytes());
        byteBuffer.flip();

        channel.write(byteBuffer);

//        byteBuffer
        fileOutputStream.close();


    }
}
