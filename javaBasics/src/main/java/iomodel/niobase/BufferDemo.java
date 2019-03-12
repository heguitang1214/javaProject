package iomodel.niobase;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("D:\\nio.txt", "r");
            FileChannel channel = file.getChannel();
            //先初始化一个ByteBuffer
            ByteBuffer buf = ByteBuffer.allocate(48);
            getBufferStatus("初始化Buffer", buf);
            //从channel中将数据读入到Buffer(Buffer是一个写模式)
            channel.read(buf);
            getBufferStatus("数据读入Buffer", buf);
            //将buffer的写模式切换为读模式
            buf.flip();
            getBufferStatus("切换为读模式", buf);
            while (buf.hasRemaining()) {
                // channel的写数据方法，就是将buf中的数据写到channel中，
                // 读buffer中的数据到channel
//                channel.write(buf);
                byte b = buf.get();
                System.out.print((char) b);
            }
            System.out.println();
            getBufferStatus("数据读取完毕", buf);
            //重读数据
            buf.rewind();
            getBufferStatus("rewind()", buf);

            //清理数据：只清理读过的数据
            buf.compact();
            getBufferStatus("compact()", buf);

            //清空数据
            buf.clear();
            getBufferStatus("clear()", buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getBufferStatus(String str, ByteBuffer buf) {
        System.out.println(str + "======》 capacity:" + buf.capacity() +
                "; position:" + buf.position() + "; limit:" + buf.limit());
    }
}