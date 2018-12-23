package io.baseDemo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 读取文件
 */
public class ReadFile {

    private static final int BUFFER_SIZE = 1024;

    /**
     * 按行读取数据
     */
    public static List<String> readLineData(String path) throws Exception {
        List<String> list = new ArrayList<>();
        FileReader reader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(reader);
        //按行读取
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            list.add(line);
//            System.out.println(line);
        }
        bufferedReader.close();
        return list;
    }


    /**
     * 单个的读取数据
     *
     * @throws Exception 异常
     */
    private static void readData(String path) throws Exception {
        FileReader reader = new FileReader(path);
        char[] chars = new char[BUFFER_SIZE];
        int len;
        while ((len = reader.read(chars)) != -1) {
            System.out.print(new String(chars, 0, len));
        }
        reader.close();
    }

    public static void main(String[] args) throws Exception {
//        readLineData("src/main/resources/test1.txt");
        readData("src/main/resources/ioFile/test3.txt");
    }

}



