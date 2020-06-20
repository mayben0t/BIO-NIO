package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReWr {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\wangxiao\\Desktop\\bb.mp4");
        FileChannel inputStreamChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\aaaaa\\file04.mp4");
        FileChannel outputStreamChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true){
            byteBuffer.clear();

            int read = inputStreamChannel.read(byteBuffer);
            System.out.println(read);
            if(read == -1 ){
                break;
            }


            byteBuffer.flip();
            outputStreamChannel.write(byteBuffer);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
