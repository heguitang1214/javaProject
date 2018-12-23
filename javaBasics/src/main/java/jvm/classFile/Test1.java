package jvm.classFile;

import java.io.Serializable;

/**
 * Created by 11256 on 2018/9/4.
 * 简单的字节码分析
 */
public class Test1 implements Serializable {
    private String name = "heguiatng";
    private static String age = "18";

    static void methodTest() {
        try {
            int i = 0;
            for (int j = 0; j < 10; j++) {
                i = 2 * j;
                System.out.printf("序号【" + i + "】方法methodTest......");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
//        System.out.printf("方法methodTest......");

    }
}


