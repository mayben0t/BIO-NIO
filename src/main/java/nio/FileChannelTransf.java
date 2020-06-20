package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileChannelTransf {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("d:\\aaaaa\\file04.mp4");

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\aaaaa\\file06.mp4");

        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();

        fileOutputStreamChannel.transferFrom(fileInputStreamChannel,0,fileInputStreamChannel.size());

        fileInputStream.close();
        fileOutputStream.close();
    }
}
