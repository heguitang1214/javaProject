package jvm.classLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;

/**
 * @author he_guitang
 * @version [1.0 , 2018/7/24]
 *          自定义的类加载器
 */
public class MyClassLoader extends ClassLoader {

    private String name;// 类加载器的名字
    private String path = "d:\\";// 加载类的路径
    private final String fileType = ".class";// class文件的扩展名

    public MyClassLoader(String name) {
        //调用this(checkCreateClassLoader(), getSystemClassLoader());
        // 让系统类加载器成为该类加载器的父加载器
        super();
        this.name = name;
    }

    public MyClassLoader(ClassLoader parent, String name) {
        super(parent);// 显示指定该类加载器的父加载器
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    /**
     * 当loadClass()方法中父加载器加载失败后，则会调用自己的findClass()方法来完成类加载，
     * 这样就可以保证自定义的类加载器也符合双亲委托模式。
     * 需要注意的是ClassLoader类中并没有实现findClass()方法的具体代码逻辑，
     * 取而代之的是抛出ClassNotFoundException异常(即:双亲委派后,找不到加载该类的加载器)
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        /*
            defineClass将字节数组转换成Class对象
            defineClass()方法是用来将byte字节流解析成JVM能够识别的Class对象(ClassLoader中已实现该方法逻辑)，
            通过这个方法不仅能够通过class文件实例化class对象，也可以通过其他方式实例化class对象:
            如通过网络接收一个类的字节码，然后转换为byte字节流创建对应的Class对象，
            defineClass()方法通常与findClass()方法一起使用，
            一般情况下，在自定义类加载器时，会直接覆盖ClassLoader的findClass()方法并编写加载规则，
            获取到要加载类的字节码后将其转换成流，然后调用defineClass()方法生成类的Class对象
         */
        return this.defineClass(name, data, 0, data.length);
    }

    //将字节码文件转换成byte[]数组
    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        try {
            // 将.装换成\
            this.name = this.name.replace(".", "\\");
            String pathLoader = path + name + fileType;
            System.out.println("字节码文件的路径是:" + pathLoader);
            is = new FileInputStream(new File(pathLoader));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (baos != null) baos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return data;
    }


//    /**
//     * java自己实现的是双亲委托机制
//     * 重写loadClass,修改其中的逻辑,就会破坏java的双亲委派机制
//     */
//    @Override
//    public Class<?> loadClass(String name) throws ClassNotFoundException {
//        Class<?> clazz = null;
//        ClassLoader system = getSystemClassLoader();
//        try {
//            clazz = system.loadClass(name);
//        } catch (Exception e) {
//            // ignore
//        }
//        if (clazz != null)
//            return clazz;
//        clazz = findClass(name);
//        return clazz;
//    }


    /**
     * 没有包名的情况
     *      如果指定的路径是系统路径找不到的,那么系统加载器就没什么用了
     */
    private static void noPackageName(String path) throws Exception {
        //默认的父加载器是系统加载器
//        MyClassLoader loader1 = new MyClassLoader("loader1");
        //显示调用系统加载器,指定父加载器是系统加载器
//        MyClassLoader loader1 = new MyClassLoader(ClassLoader.getSystemClassLoader(), "loader1");
        MyClassLoader loader1 = new MyClassLoader(null, "loader1");
        loader1.setPath(path);
        MyClassLoader loader2 = new MyClassLoader(loader1, "loader2");
        loader2.setPath(path);

        Class clazz1 = loader1.loadClass("Dog");
        Class clazz2 = loader2.loadClass("Simple");
        clazz1.newInstance();
        clazz2.newInstance();
        System.out.println("hashCode=" + clazz1.hashCode() + ",当前加载器为:" + clazz1.getClassLoader() +
                ",父类加载器为:" + clazz1.getClassLoader().getParent());
        System.out.println("hashCode=" + clazz2.hashCode() + ",当前加载器为:" + clazz2.getClassLoader() +
                ",父类加载器为:" + clazz1.getClassLoader().getParent());
        //重新加载,对象地址改变
        loader1 = new MyClassLoader("loader1");
        loader1.setPath(path);
        clazz1 = loader1.loadClass("Dog");
        System.out.println("hashCode=" + clazz1.hashCode() + ",当前加载器为:" + clazz1.getClassLoader() +
                ",父类加载器为:" + clazz1.getClassLoader().getParent());
    }


    /**
     * 有包名的情况
     */
    private static void existPackageName() throws Exception {
        URL url = MyClassLoader.class.getResource("/");
        //默认的父加载器是系统加载器
        MyClassLoader loader1 = new MyClassLoader("loader1");
        //这种情况下需要重写loadClass,是因为路径问题.这个时候使用的是自己的加载器,需要写明加载格则(双亲委派还是其他),
        // 有运行时包,自定义的类加载器,没有这个规范,所以你的路径会不对,即重写它的加载规则
        //完整路径也会报错,是因为里面有包的定义
//        MyClassLoader loader1 = new MyClassLoader(null,"loader1");
        loader1.setPath(url.getPath());
        Class clazz1 = loader1.loadClass("jvm.classLoader.Simple");


        System.out.println("hashCode=" + clazz1.hashCode() + ",当前加载器为:" + clazz1.getClassLoader() +
                ",父类加载器为:" + clazz1.getClassLoader().getParent());
        //反射Simple类中的属性
        Object object = clazz1.newInstance();//创建一个Simple类的对象
        Field field = clazz1.getField("number");
        int number = field.getInt(object);
        System.out.println("反射获取属性number的值= " + number);
        //强转:当两个不同命名空间的内的类互相不可见时,可采用java反射机制来访问对方实例的属性和方法.强转是不能实现的.
        Simple simple = (Simple) object;
        System.out.println("强转获取属性number的值= " + simple.number);
    }


    /**
     * 该方法在这抛出了异常,如果Simple在loader1加载器的路径下,而Dog在loader2的加载路径下
     * 会出现java.io.FileNotFoundException的异常,找不到Dog.class
     */
    public static void main(String[] args) throws Exception {
        System.out.println("文件类型是:" + new MyClassLoader("").fileType);
        noPackageName("d:\\test\\");
        System.out.println("============================分界线==================================");
        existPackageName();
    }


}
