package spring.aop.userAudit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * springAOP实现用户审计
 * 定义自定义注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserAudited {
    /**
     * 操作类型
     */
    OpTypeEnum opType();

    /**
     * 操作名称，优先保证跟权限信息一致，如不一致请在remark说明
     */
    String action();

    /**
     * 备注，操作名称与权限信息不一致添加说明
     */
    String remark() default "";
}
