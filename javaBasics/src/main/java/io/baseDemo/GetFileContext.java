package io.baseDemo;

import java.io.File;
import java.util.List;

/**
 * @Author heguitang
 * @Date 2019/1/21 11:24
 * @Version 1.0
 * @Desc
 */
public class GetFileContext {

    public static void main(String[] args) throws Exception {
        getFile();
    }


    private static void getFile() throws Exception {

        File file = new File("D:\\test\\sources");
        String[] names = file.list();
        for (String name : names){
            List<String> context =  ReadFile.readLineData("D:\\test\\sources\\" + name);
            System.out.println(name);
            for (String text : context){
                WriterFile.fileWriter(true, "D:\\test\\target\\text.txt", text);
            }


            WriterFile.fileWriter(true, "D:\\test\\target\\name.txt", name);
        }


    }




}
