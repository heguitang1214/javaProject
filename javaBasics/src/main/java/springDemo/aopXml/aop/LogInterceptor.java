package springDemo.aopXml.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class LogInterceptor {

    public void before() {
        System.out.println("method before......");
    }

    public void aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("method around start......");
        pjp.proceed();
        System.out.println("method around end......");
    }

}
