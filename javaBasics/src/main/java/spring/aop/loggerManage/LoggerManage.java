package spring.aop.loggerManage;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
//@Documented
public @interface LoggerManage {

    String logDescription();
}
