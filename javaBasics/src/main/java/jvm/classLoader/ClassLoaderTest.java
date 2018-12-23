package jvm.classLoader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by 11256 on 2018/9/5.
 * 测试  类的加载器
 */
public class ClassLoaderTest {

    public static void main(String[] args) {
        ClassLoader c = ClassLoaderTest.class.getClassLoader();
        while (c != null){
            System.out.println(c);
            c = c.getParent();
        }
        //启动加载器
        System.out.println("===========================启动加载器=================================");
        System.out.println(System.getProperty("sun.boot.class.path"));
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(URL url : urls)
            System.out.println(url);
        System.out.println();
        System.out.println("===========================扩展加载器=================================");
        //扩展加载器
        System.out.println(System.getProperty("java.ext.dirs"));
        URLClassLoader extClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader().getParent();
        urls = extClassLoader.getURLs();
        for(URL url : urls)
            System.out.println(url);
        System.out.println();
        System.out.println("===========================系统加载器=================================");
        //系统加载器
        System.out.println(System.getProperty("java.class.path"));
        URLClassLoader appClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        urls = appClassLoader.getURLs();
        for(URL url : urls)
            System.out.println(url);
    }

}
