package nio.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelectionKey;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class NIOClient {


    //定义变量
    private SocketChannel socketChannel;
    private Selector selector;
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 7777;

    public NIOClient() throws IOException {
        this.socketChannel = SocketChannel.open();
        this.selector = Selector.open();
        //连接
        socketChannel.configureBlocking(false);
        boolean connect = socketChannel.connect(new InetSocketAddress(HOST, PORT));
        if(!connect){
            boolean finishConnect = socketChannel.finishConnect();
            while (!finishConnect){
                //未连接成功的情况下 可以非阻塞的执行其他任务
                doOtherThings();
            }
        }
        System.out.println("客户端"+socketChannel.getLocalAddress()+"连接成功,服务器地址"+socketChannel.getRemoteAddress());
        //连接成功 监听read事件
        socketChannel.register(selector,SelectionKey.OP_READ);
    }

    private Void doOtherThings() {
        System.out.println("这里可以用于扩展");
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (Exception e){

        }

        return null;
    }

    public void readInfo(){
        int select = 0;
        try {
            select  = selector.select(1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(select<=0){
            return;
        }
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey next = iterator.next();
            if(next.isReadable()){
                SelectableChannel channel = next.channel();
                SocketChannel channel1 = (SocketChannel) channel;
//                ByteBuffer allocate = (ByteBuffer) next.attachment();
                ByteBuffer allocate = ByteBuffer.allocate(1024);
                int read = 0;
                try {
                    read = channel1.read(allocate);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(read>0){
                    String msg = new String(allocate.array());
                    System.out.println(msg.trim());
                }
                iterator.remove();
            }
        }
    }


    public void sendInfo()throws Exception{
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
//        System.out.println("打算发消息:"+s);
//        for (SelectionKey selectedKey : selector.selectedKeys()) {
//            selectedKey.attach()
//        }
        socketChannel.write(ByteBuffer.wrap(s.getBytes()));
    }


    public static void main(String[] args)throws Exception {
        NIOClient nioClient = new NIOClient();
        new Thread(()->{
            while (true) {
                nioClient.readInfo();
            }
        }).start();

        while (true){
            nioClient.sendInfo();
        }
    }
}
