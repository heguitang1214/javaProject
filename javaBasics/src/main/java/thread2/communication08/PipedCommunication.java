package thread2.communication08;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @Author: 无双老师【云析学院】
 * @Date: 2019-08-06 19:50
 * @Description: 线程之间的通信——管道输入/输出流
 * <p>
 * 和普通的文件输入/输出流或者网络输入/输出流不同之处在于
 * <p>
 * 它主要用于线程之间的数据传输，而传输的媒介为内存。
 * <p>
 * 管道输入/输出流主要包括了如下4种具体实现：
 * <p>
 * PipedOutputStream、PipedInputStream
 * <p>
 * PipedReader和PipedWriter
 * <p>
 * 前两种面向字节，而后两种面向字符。
 */
public class PipedCommunication {
    /**
     * 写入数据的线程
     */
    private static class Writer implements Runnable {
        /**
         * 管道字符输出流
         */
        private PipedWriter pipedWriter;

        /**
         * 构造器
         *
         * @param writer 管道字符输出流
         */
        private Writer(PipedWriter writer) {
            pipedWriter = writer;
        }

        /**
         * 重写run()方法
         */
        @Override
        public void run() {
            // 输入的字符
            int receive;
            try {
                // 读取控制台写入的字符
                while ((receive = System.in.read()) != -1) {
                    System.out.println(Thread.currentThread().getName() + "写入字符:" + (char) receive);
//                    System.out.println(Thread.currentThread().getName() + "写入字符:" + receive);
                    // 写入字符到到管道字符输出流中
                    pipedWriter.write(receive);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 关闭流
                    pipedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印数据的线程
     */
    private static class Printer implements Runnable {
        /**
         * 管道字符输入流
         */
        private PipedReader pipedReader;

        /**
         * 构造器
         *
         * @param in 管道字符输入流
         */
        private Printer(PipedReader in) {
            this.pipedReader = in;
        }

        @Override
        public void run() {
            // 输入的字符
            int receive;
            try {
                // 管道字符输入流读取数据
                while ((receive = pipedReader.read()) != -1) {
                    System.out.println(Thread.currentThread().getName() + "打印字符:" + (char) receive);
//                    System.out.println(Thread.currentThread().getName() + "写入字符:" + receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 输出流与输入流相连接
        out.connect(in);
        // 打印线程
        Thread printThread = new Thread(new Printer(in), "打印线程");
        printThread.start();
        // 写入线程
        Thread writeThread = new Thread(new Writer(out), "写入线程");
        writeThread.start();
    }
}
