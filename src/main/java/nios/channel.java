package nios;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by linux on -5-8.
 */
public class channel {
    public static void main(String args[]) {
        test2();
    }

    public static void test1() {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("/home/linux/Desktop/netty/data/nio-data.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(48);
            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {
                System.out.println("Read----" + bytesRead);
                buf.flip();
                int i = 0;
                while (buf.hasRemaining()) {
                    i++;
                    System.out.print((char) buf.get());
                    if (i == 5) {
                        buf.mark();
                    }
                    if (i == 10) {
                        buf.reset();
                    }
                }
                buf.clear();
                bytesRead = inChannel.read(buf);
            }
            aFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void test2() {
        RandomAccessFile aFile = null;
        try {
            aFile = new RandomAccessFile("/home/linux/Desktop/netty/data/nio-data.txt", "rw");
            FileChannel inChannel = aFile.getChannel();
            String newdata = "new string write to file" + System.currentTimeMillis();
            ByteBuffer buf = ByteBuffer.allocate(1000);
            buf.clear();
            buf.put(newdata.getBytes());
            buf.flip();
            inChannel.position(inChannel.size());

            while (buf.hasRemaining()) {
                inChannel.write(buf);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
