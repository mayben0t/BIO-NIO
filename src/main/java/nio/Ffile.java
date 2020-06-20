package nio;

import sun.nio.ch.FileChannelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Ffile {

    public static void main(String[] args) throws Exception{
//        String str = "hello 王骁";

        File file = new File("d:\\aaaaa\\file01.txt");
        FileInputStream fileOutputStream = new FileInputStream(file);

        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((Integer.valueOf((int) file.length())));

//        byteBuffer.put(str.getBytes());

//        byteBuffer.flip();

//        channel.write(byteBuffer);
        channel.read(byteBuffer);
        byteBuffer.flip();
        if (byteBuffer.hasRemaining()){
            byte[] array = byteBuffer.array();
            System.out.println(new String(array));
        }

        fileOutputStream.close();


    }
}
