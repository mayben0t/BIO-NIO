package os;

import sun.nio.ch.FileChannelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipDemo {

    public static void main(String[] args)throws Exception {
//        new ZipInputStream(new FileInputStream("Z"));
        ZipFile("/Users/wangxiao/Desktop/s1.xlsx","/Users/wangxiao/Desktop/s11.zip");
    }


    public static void ZipFile(String filepath ,String zippath) {
        try {
            File file = new File(filepath);
            File zipFile = new File(zippath);
            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
            input.close();
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ZipFileNew(String filepath ,String zippath) throws Exception{
        try {
            File file = new File(filepath);
            File zipFile = new File(zippath);

            FileChannel fileChannel = new FileInputStream(file).getChannel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            fileChannel.read(allocate);



            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
            input.close();
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
