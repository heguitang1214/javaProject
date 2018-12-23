package designPatterns.proxy.javaProxy.core;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 动态生成代理对象,主要依靠JavaCompiler,和URLClassLoader
 *
 * @author he_guitang
 * @version [1.0 , 2018/5/22]
 */
public class Proxy {//本身不需要实现接口

    //JDK6 Complier API, CGLib, ASM
    public static Object newProxyInstance(Class infce, InvocationHandler h) throws Exception {
        String methodStr = "";
        String rt = "\r\n";

        Method[] methods = infce.getMethods();
        for (Method m : methods) {
            methodStr += "@Override" + rt +
                    "public void " + m.getName() + "() {" + rt +
                    "    try {" + rt +
                    "    Method md = " + infce.getName() + ".class.getMethod(\"" + m.getName() + "\");" + rt +
                    "    System.out.println(\"invokeMethod before.....\");" + rt +
                    "    h.invokeMethod(this, md);" + rt +
                    "    System.out.println(\"invokeMethod after.....\");" + rt +
                    "    }catch(Exception e) {e.printStackTrace();}" + rt +

                    "}";
        }

        String src =
                "package designPatterns.proxy.javaProxy.classpath;" + rt +
                        "import designPatterns.proxy.javaProxy.core.InvocationHandler;" + rt +
                        "import java.lang.reflect.Method;" + rt +
                        "public class $Proxy1 implements " + infce.getName() + "{" + rt +
                        "    public $Proxy1(InvocationHandler h) {" + rt +
                        "        this.h = h;" + rt +
                        "    }" + rt +

//                        "    proxy.InvocationHandler h;" + rt +//指定特定的类proxy包下的InvocationHandler
                        "    InvocationHandler h;" + rt +
                        methodStr +
                        "}";
        String fileName = System.getProperty("user.dir")
                + "/src/main/java/designPatterns/proxy/javaProxy/classpath/$Proxy1.java";
        File f = new File(fileName);
        FileWriter fw = new FileWriter(f);
        fw.write(src);
        fw.flush();
        fw.close();

        //compile
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
        Iterable units = fileMgr.getJavaFileObjects(fileName);
        JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
        t.call();
        fileMgr.close();

        //load into memory and create an instance
        URL[] urls = new URL[]{new URL("file:/" + System.getProperty("user.dir") + "/src")};
        URLClassLoader ul = new URLClassLoader(urls);
        Class c = ul.loadClass("designPatterns.proxy.javaProxy.classpath.$Proxy1");
        System.out.println("代理类对象[" + c + "]");

        Constructor ctr = c.getConstructor(InvocationHandler.class);
        Object m = ctr.newInstance(h);
//        m.move();
        return m;
    }

}
