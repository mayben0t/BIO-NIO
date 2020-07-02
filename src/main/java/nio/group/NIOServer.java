package nio.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    //定义变量
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private final static String HOST = "127.0.0.1";
    private final static Integer PORT = 7777;


    public NIOServer() throws Exception{
        this.serverSocketChannel = ServerSocketChannel.open();
        this.selector = Selector.open();
        serverSocketChannel.bind(new InetSocketAddress(HOST,PORT));
        System.out.println("服务器已启动,localAddress:"+serverSocketChannel.getLocalAddress());
        serverSocketChannel.configureBlocking(false);
        //注册到selector上 监听连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void doHandler(){
        while (true){
            int events = 0;
            try {
                events = selector.select(2000);
            } catch (IOException e) {
                System.out.println("select获取事件出事了...");
//                e.printStackTrace();
                continue;
            }
            if (events>0){
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                //先处理连接事件 处理完 把对应的客户端socketChannel注册读(OP_READ)事件
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()){
                    SelectionKey currentSelectionKey = selectionKeyIterator.next();


                    if(currentSelectionKey.isAcceptable()){
                        try {
                            SocketChannel accept = serverSocketChannel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector,SelectionKey.OP_READ);
                            String message = accept.getRemoteAddress() + " 上线了...";
                            System.out.println(message);
                            sendInfoToOtherClients(message,accept);
                        }catch (Exception e){

                        }

                    }
                    if(currentSelectionKey.isReadable()){
                        SocketChannel channel = (SocketChannel) currentSelectionKey.channel();
                        ByteBuffer allocate = ByteBuffer.allocate(1024);
//                        ByteBuffer allocate = (ByteBuffer) currentSelectionKey.attachment();

                        boolean isClose = false;
                        try {

                            channel.read(allocate);
                        } catch (IOException e) {
                            try {
                                String msg = channel.getRemoteAddress()+" 下线了...";
                                System.out.println("服务端打算发送离线消息: "+msg);
                                sendInfoToOtherClients(msg,channel);
                                isClose = true;
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(!isClose) {
                            String msg = new String(allocate.array());
                            try {
                                msg = channel.getRemoteAddress() + " 说:" + msg;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            sendInfoToOtherClients(msg, channel);
                        }
                    }
                    selectionKeyIterator.remove();
                }
            }
        }
    }

    public void sendInfoToOtherClients(String msg,SocketChannel sc){
        Set<SelectionKey> keys = selector.keys();
//        Iterator<SelectionKey> iterator = keys.iterator();
        for (SelectionKey next:keys){
//            SelectionKey next = iterator.next();
            SelectableChannel channel = next.channel();
            if(channel!=null && channel instanceof SocketChannel){
                SocketChannel channel1 = (SocketChannel) channel;
                if(channel1!=sc){
//                    try {
                    msg = msg.trim();
                        System.out.println("啊啊啊啊啊啊啊啊啊啊啊啊啊:"+msg);
                    try {
                        channel1.write(ByteBuffer.wrap(msg.getBytes()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    } catch (IOException e) {
//                        //异常说明有人中断了客户端
//                        try {
//                            String message =sc.getRemoteAddress().toString()+" 下线了...";
//                            sendInfoToOtherClients(message,channel1);
//                        } catch (IOException ex) {
//                            ex.printStackTrace();
//                        }
//                    }

                }
            }
        }

    }


    public static void main(String[] args) throws Exception{
        NIOServer nioServer = new NIOServer();
//        System.out.println();
        nioServer.doHandler();
    }

}
