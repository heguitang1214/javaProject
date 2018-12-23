package springDemo.baseDemo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Bean生命周期
 *  单个Bean的生命周期涉及到的方法
 */
public class BeanDemo implements BeanNameAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private ApplicationContext applicationContext;

    /**
     * 获取SpringBean的上下文
     */
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /**
     * 1. 构造器
     */
    public BeanDemo() {
        System.out.println("1 Person()");
    }

    /**
     * 2. BeanNameAware接口方法实现
     */
    @Override
    public void setBeanName(String name) {
        System.out.println("2 setBeanName(String name)");
    }

    /**
     * 3. ApplicationContextAware接口方法实现
     *  用来获取SpringBean的上下文
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("3 setApplicationContext(ApplicationContext applicationContext)");
        this.applicationContext = applicationContext;
    }

    /**
     * 4. InitializingBean接口方法实现
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("4 afterPropertiesSet()");
    }

    /**
     * 5. init-method属性指定的方法
     */
    public void lifecycleInit() {
        System.out.println("5 lifecycleInit()");
    }

    /**
     * 6. Bean中的业务方法
     */
    public void sayHello() {
        System.out.println("6 sayHello()");
    }

    /**
     * 7. DisposableBean接口方法实现
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("7 destroy()");
    }

    /**
     * 8. destroy-method属性指定的方法
     */
    public void lifecycleInitDestroy() {
        System.out.println("8 lifecycleInitDestroy()");
    }
}
