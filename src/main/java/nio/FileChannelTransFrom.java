package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FileChannelTransFrom {

    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("/Users/wangxiao/Desktop/meinv.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/wangxiao/Desktop/mei2.jpg");

        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();


        fileOutputStreamChannel.transferFrom(fileInputStreamChannel,0,fileInputStreamChannel.size());

        fileInputStreamChannel.close();
        fileOutputStreamChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
        System.out.println("---end---");

    }
}
