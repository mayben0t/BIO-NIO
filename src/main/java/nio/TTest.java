package nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TTest {

    public static void main(String[] args) throws Exception{
        File file = new File("d:\\aaaaa\\file01.txt");
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        //先从文件channel读到 ByteBuffer
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel inputChannel = fileInputStream.getChannel();
        inputChannel.read(byteBuffer);


        //再从ByteBuffer通过channel最终写到文件
        //翻转一次
        byteBuffer.flip();

    }
}
