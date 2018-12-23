package io.baseDemo;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class WriterFile {

    /**
     *  文件写入
     * @param append 是否追加
     * @param path 文件写入路径
     * @param context 文件写入内容
     * @throws Exception 异常
     */
    private static void fileWriter(boolean append, String path, String context) throws Exception {
        FileWriter fw = new FileWriter(path, append);
        /*
            为了提高写入效率,使用了字符流的缓冲区，
            创建了一个字符写入流的缓冲区对象，并和指定要缓冲的流对象相关联
         */
        BufferedWriter bufferedWriter = new BufferedWriter(fw);
        //使用缓冲区的写入方法将数据先写入到缓冲区
        bufferedWriter.write(context);
        bufferedWriter.newLine();//写入换行符
        bufferedWriter.flush();//可以防止数据丢失
        /*
            关闭缓冲区,其实关闭的就是被缓冲的流对象底层关的就是fw,缓冲区仅仅只是提高效率
         */
        bufferedWriter.close();
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            fileWriter(false, "src/main/resources/ioFile/test3.txt", "234567" + "[" + i + "]");
        }
//        fileWriter(true, "src/main/resources/test4.txt", "234567");
    }

}
