package springTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import springDemo.baseDemo.BeanDemo;

/**
 * Bean生命周期测试
 */
public class BeanDemoTest {

    public static void main(String[] args) {
        //只启动BeanLifecycle这一个Bean，观察效果
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:springConf/spring-chapter2-beanDemo.xml");
        BeanDemo beanLifecycle = context.getBean("beanLifecycle", BeanDemo.class);
//        System.out.println(beanLifecycle.getApplicationContext().getBean("beanLifecycle"));
        beanLifecycle.sayHello();
        context.close();
    }

}
