package spring.aop.loggerManage;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * AOP日志切面
 * within(): 限制连接点指定匹配的类型
 **/
@Aspect
@Component
public class LoggerAdvice {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * controller..*.*(..))")
//    @Pointcut("execution(public * spring.aopAnnotation.service..*.add(..))")//没有实现接口,需要使用CGLib
    public void myMethod() {
    }

    /**
     * 前置增强
     */
//    @Before("@annotation(loggerManage)")
//    @Before("myMethod() && @annotation(loggerManage)")
    @Before("within(controller..*) && @annotation(loggerManage)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        LocalDateTime now = LocalDateTime.now();
        logger.info(now.toString()+"执行[" + loggerManage.logDescription() + "]开始");
        logger.info(joinPoint.getSignature().toString());
        logger.info(parseParames(joinPoint.getArgs()));

    }

    /**
     * 后置增强
     */
//    @After("myMethod() && @annotation(loggerManage)")
    @After("within(controller..*) && @annotation(loggerManage)")
    public void addAfterLogger(JoinPoint joinPoint, LoggerManage loggerManage) {
        LocalDateTime now = LocalDateTime.now();
        logger.info(now.toString()+"执行 [" + loggerManage.logDescription() + "] 结束");
    }

    /**
     * 出现异常
     */
    @AfterThrowing(pointcut = "within(controller..*) && @annotation(loggerManage)", throwing = "ex")
//    @AfterThrowing(pointcut = "myMethod() && @annotation(loggerManage)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, LoggerManage loggerManage, Exception ex) {
        LocalDateTime now = LocalDateTime.now();
        logger.error(now.toString()+"执行 [" + loggerManage.logDescription() + "] 异常", ex);
    }

    private String parseParames(Object[] parames) {
        if (null == parames || parames.length <= 0) {
            return "";
        }
        StringBuilder param = new StringBuilder("传入参数 # 个:[ ");
        int i = 0;
        for (Object obj : parames) {
            i++;
            if (i == 1) {
                param.append(obj.toString());
                continue;
            }
            param.append(" ,").append(obj.toString());
        }
        return param.append(" ]").toString().replace("#", String.valueOf(i));
    }

}
