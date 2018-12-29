package com.zero.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// 李四帮小王支付租金，实现java动态代理的接口
public class DymanicProxy implements InvocationHandler {
    private Object target;

    public static void main(String[] args) {
        Payment payment = new PaymentImpl();
        DymanicProxy proxy = new DymanicProxy();
        Payment p = (Payment) proxy.bind(payment);
        p.rent(3000);
    }

//    动态代理进行增强
    private Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("======= 支付租金前 =======");
        method.invoke(target, args);
        System.out.println("======= 支付租金后 =======");
        return null;
    }
}
