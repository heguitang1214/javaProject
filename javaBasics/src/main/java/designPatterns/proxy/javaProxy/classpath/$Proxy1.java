package designPatterns.proxy.javaProxy.classpath;
import designPatterns.proxy.javaProxy.core.InvocationHandler;
import java.lang.reflect.Method;
public class $Proxy1 implements designPatterns.proxy.javaProxy.promotion.UserMgr{
    public $Proxy1(InvocationHandler h) {
        this.h = h;
    }
    InvocationHandler h;
@Override
public void addUser() {
    try {
    Method md = designPatterns.proxy.javaProxy.promotion.UserMgr.class.getMethod("addUser");
    System.out.println("invokeMethod before.....");
    h.invokeMethod(this, md);
    System.out.println("invokeMethod after.....");
    }catch(Exception e) {e.printStackTrace();}
}}