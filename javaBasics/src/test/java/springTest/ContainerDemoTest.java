package springTest;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import springDemo.baseDemo.BeanDemo;

/**
 * Bean级生命周期+容器级生命周期测试
 */
public class ContainerDemoTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:springConf/spring-chapter2-beanDemo.xml",
                "classpath:springConf/spring-chapter2-container.xml");
        BeanDemo beanLifecycle = context.getBean("beanLifecycle", BeanDemo.class);
        beanLifecycle.sayHello();
        context.close();
    }
}
