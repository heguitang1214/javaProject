package jvm.classLoader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 11256 on 2018/9/5.
 * 简易的自定义类加载器
 */
public class MyClassLoader1 {

    public static void main(String[] args) throws Exception {
        //自定义的类加载器
        ClassLoader loader = new ClassLoader() {
            @Override
            // 重载loadClass类，达到打断双亲委派的目的
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    // 获取class文件的名字
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    // class文件输入流
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        // 如果流为空，交给父类加载
                        return super.loadClass(name);
                    }
                    // 定义byte数组
                    byte[] b = new byte[is.available()];
                    // 把数据读到byte数组中
                    is.read(b);
                    // 将byte数组转换成一个对象Class<?>
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException();
                }
            }
        };

        String className = "jvm.classLoader.MyClassLoader1";
//        className = "jvm.classLoader.Dog";
        Object o1 = MyClassLoader1.class.getClassLoader().loadClass(className).newInstance();
        //单个类的重写类加载器(MyClassLoader1),对于其他的类,没有什么用处
        Object o2 = loader.loadClass(className).newInstance();

        System.out.println("=比较结果:" + (o1 == o2));
//        System.out.println("=比较结果:" + "123" == "123");????
        System.out.println("equals比较结果:" + o1.equals(o2));

        System.out.println("当前对象o1的类加载是否是自定义的类加载器:" + (o1 instanceof MyClassLoader1));
        System.out.println("当前对象o2的类加载是否是自定义的类加载器:" + (o2 instanceof MyClassLoader1));

    }


}
