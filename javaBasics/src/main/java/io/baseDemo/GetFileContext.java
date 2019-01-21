package io.baseDemo;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author heguitang
 * @Date 2019/1/21 11:24
 * @Version 1.0
 * @Desc
 */
public class GetFileContext {

    public static void main(String[] args) throws Exception {
        getFile();
//        listAll(new File("D:\\test"), 0);
    }


    private static void getFile() throws Exception {
        Map<String, String> map = new HashMap<>();
        File file = new File("D:\\test\\sources");
        String[] names = file.list();
        if (names == null){
            return;
        }
        for (String name : names){
            List<String> context =  ReadFile.readLineData("D:\\test\\sources\\" + name);
            System.out.println(name);
            for (String text : context){
                WriterFile.fileWriter(true, "D:\\test\\target\\text.txt", text);
            }
            WriterFile.fileWriter(true, "D:\\test\\target\\name.txt", name);
            map.put(name.split("\\.")[0].split("_")[1], context.get(0));
        }
        for (String key : map.keySet()){
            System.out.println(key + ">>>>>>>>>>" + map.get(key));
        }
    }

    /**
     * 获取文件目录
     */
    private static void listAll(File file, int level){
        System.out.println(getSpace(level) + file.getName());
        level ++;
        //获取指定目录下当前的所有文件夹或者文件对象
        File[] files = file.listFiles();
        if (files == null){
            return;
        }
        for (int i = 0; i < files.length; i++){
            if (files[i].isDirectory()){
                listAll(files[i], level);
            }else {
                System.out.println(getSpace(level) + files[i].getName());
            }
        }

    }


    private static String getSpace(int level){
        StringBuilder sb = new StringBuilder();
        sb.append("|--");
        for (int i = 0; i < level; i++){
            sb.insert(0,"|  ");
        }
        return sb.toString();
    }

}
