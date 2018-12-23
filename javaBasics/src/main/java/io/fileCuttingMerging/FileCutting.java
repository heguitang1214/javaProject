package io.fileCuttingMerging;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 文件切割
 *
 * @author heguiatng
 */
public class FileCutting {
    private static int SIZE = 1024 * 1024;

    /**
     * 文件的切割
     *
     * @param file 文件
     * @throws IOException IO异常
     */
    private static void splitFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[SIZE];
        FileOutputStream fos;
        int len;
        int count = 1;

        Properties prop = new Properties();
        File dir = new File("D:\\partfiles");
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new RuntimeException("创建临时文件失败！无法保存切割后的文件信息。");
            }
        }
        while ((len = fis.read(buf)) != -1) {
            fos = new FileOutputStream(new File(dir, (count++) + ".part"));
            fos.write(buf, 0, len);
            fos.close();
        }
        prop.setProperty("partcount", count + "");
        prop.setProperty("filename", file.getName());

        fos = new FileOutputStream(new File(dir, count + ".properties"));
        prop.store(fos, "save_file_info");
        fos.close();
        fis.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("每次切割文件的大小为：" + FileCutting.SIZE);
        long start = System.currentTimeMillis();
        splitFile(new File("D:\\test.txt"));
        long end = System.currentTimeMillis();
        System.out.println("文件切割成功,耗时" + (end - start) + "s");
    }

}
