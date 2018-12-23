package io.baseDemo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyFile {


    private static final int BUFFER_SIZE = 1024;

    /**
     * 文件的复制
     */
    private static void copyFile(String srcPath, String targetPath) {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader(srcPath);
            fw = new FileWriter(targetPath);
            char[] buf = new char[BUFFER_SIZE];
            int len;
            while ((len = fr.read(buf)) != -1) {
                fw.write(buf, 0, len);
            }

        } catch (Exception e) {
            throw new RuntimeException("文件读写失败");
        } finally {
            //如果创建路径错误，直接关闭不仅会出现java.io.FileNotFoundException，
            // 还会出现空指针异常java.lang.NullPointerException
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        copyFile("src/main/resources/ioFile/test3.txt", "src/main/resources/ioFile/test4.txt");
    }
}
