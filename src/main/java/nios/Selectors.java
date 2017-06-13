package nios;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by linux on -5-8.
 */
public class Selectors {
    private static  ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

    public static void main(String args[]) {

        try {
            //Selector的创建
            Selector selector = Selector.open();
            //向Selector注册通道
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            ServerSocket ss = channel.socket();
            ss.bind(new InetSocketAddress(8000));


            SelectionKey keys = channel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                int readyChannels = selector.select();
                Set selectedKeys = selector.selectedKeys();
                Iterator keyIterator = selectedKeys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = (SelectionKey) keyIterator.next();
                    if (key.isAcceptable()) {
                        ServerSocketChannel ssc = (ServerSocketChannel) keys.channel();
                        SocketChannel sc = ssc.accept();
                        sc.configureBlocking(false);
                        SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ);
                        System.out.println("Got connection from " + sc);
                        // a connection was accepted by a ServerSocketChannel.
                    } else if (key.isConnectable()) {
                        // a connection was established with a remote server.
                    } else if (key.isReadable()) {
                        SocketChannel sc = (SocketChannel) key.channel();
                        int bytesEchoed = 0;
                        while (true) {
                            echoBuffer.clear();
                            int r = sc.read(echoBuffer);
                            if (r <= 0) {
                                break;
                            }
                            echoBuffer.flip();
                            sc.write(echoBuffer);
                            bytesEchoed += r;
                        }
                        System.out.println("Echoed " + bytesEchoed + " from " + sc);
                        // a channel is ready for reading
                    } else if (key.isWritable()) {
                        // a channel is ready for writing
                    }
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


    }
}
