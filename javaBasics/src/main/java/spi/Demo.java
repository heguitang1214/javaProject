package spi;

import java.util.ServiceLoader;

/**
 * serviceLoader 加载接口的实现类
 *
 * JDK SPI机制，回家再所有的实现类
 *
 */
public class Demo {
    public static void main(String[] args) {
        ServiceLoader<Filter> serviceLoader = ServiceLoader.load(Filter.class);

        for (Filter filter : serviceLoader){
            filter.invoke();
        }

    }

}
