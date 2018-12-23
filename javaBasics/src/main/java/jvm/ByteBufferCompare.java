package jvm;


import java.nio.ByteBuffer;

/**
 * @author he_guitang
 * @version [1.0 , 2018/7/9]
 * 直接内存
 */
public class ByteBufferCompare {

    public static void main(String[] args) {
        allocateCompare();   //分配比较
        operateCompare();    //读写比较
    }

    /**
     * 直接内存 和 堆内存的 分配空间比较
     *
     * 结论： 在数据量提升时，直接内存相比非直接内的申请，有很严重的性能问题
     */
    private static void allocateCompare(){
        int count = 10000000;    //操作次数

        long st = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            //ByteBuffer.allocate(int capacity)   分配一个新的字节缓冲区。
            ByteBuffer buffer = ByteBuffer.allocate(2);      //非直接内存分配申请
        }
        long et = System.currentTimeMillis();
        System.out.println("在进行"+count+"次分配操作时，堆内存 分配耗时:" + (et-st) +"ms" );//72ms


        long st_heap = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            //ByteBuffer.allocateDirect(int capacity) 分配新的直接字节缓冲区。
            ByteBuffer buffer = ByteBuffer.allocateDirect(2); //直接内存分配申请
        }
        long et_direct = System.currentTimeMillis();
        System.out.println("在进行"+count+"次分配操作时，直接内存 分配耗时:" + (et_direct-st_heap) +"ms" );//6014ms
    }


    /**
     * 直接内存 和 堆内存的 读写性能比较
     *
     * 结论：直接内存在直接的IO操作上，在频繁的读写时 会有显著的性能提升
     */
    private static void operateCompare(){
        int count = 1000000000;

        ByteBuffer buffer = ByteBuffer.allocate(2 * count);
        long st = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            //  putChar(char value) 用来写入 char 值的相对 put 方法
            buffer.putChar('a');
        }
        buffer.flip();
        for (int i = 0; i < count; i++) {
            buffer.getChar();
        }
        long et = System.currentTimeMillis();
        System.out.println("在进行"+count+"次读写操作时，非直接内存 读写耗时：" + (et-st) +"ms");//1075ms


        ByteBuffer buffer_d = ByteBuffer.allocateDirect(2 * count);
        long st_direct = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            //  putChar(char value) 用来写入 char 值的相对 put 方法
            buffer_d.putChar('a');
        }
        buffer_d.flip();
        for (int i = 0; i < count; i++) {
            buffer_d.getChar();
        }
        long et_direct = System.currentTimeMillis();
        System.out.println("在进行"+count+"次读写操作时，直接内存 读写耗时:" + (et_direct - st_direct) +"ms");//1075ms
    }




}
