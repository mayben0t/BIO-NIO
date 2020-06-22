package nio;


import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * 1.MappedByteBuffer  可让文件直接在内存（堆外内存）中修改，操作系统不需要拷贝一次
 *
 */

public class MappedByteBufferDemo {

    public static void main(String[] args) throws Exception{

        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\aaaaa\\file04.mp4", "rw");
        //获取对应通道
        FileChannel channel = randomAccessFile.getChannel();

        channel.map(FileChannel.MapMode.READ_WRITE,0,5);
    }
}
