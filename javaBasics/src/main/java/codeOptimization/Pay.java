package codeOptimization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//注解
@Target(ElementType.TYPE)//作用的目标范围
@Retention(RetentionPolicy.RUNTIME)//生命周期
public @interface Pay {

    int channlId();

}
