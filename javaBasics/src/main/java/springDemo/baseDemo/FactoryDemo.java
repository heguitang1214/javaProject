package springDemo.baseDemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 工厂级生命周期
 */
public class FactoryDemo implements BeanFactoryPostProcessor {

    /**
     * 构造器
     */
    public FactoryDemo() {
        System.out.println("一  Factory()");
    }

    /**
     * Bean实例化之前
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("二  postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)");
    }
}
