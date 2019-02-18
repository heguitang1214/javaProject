package codeOptimization;

import dataStructure.myHashMap.HashMap;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @Author heguitang
 * @Date 2019/2/18 15:50
 * @Version 1.0
 * @Desc
 */
public class StrategyFactory {

    private static StrategyFactory strategyFactory = new StrategyFactory();

    private static HashMap<Integer, String> source_map = new HashMap<>();


    private StrategyFactory() {
    }

    static {
//        source_map.put(1, "pay.impl.ICBCBankImpl");
//        source_map.put(1, "pay.impl.ICBCBankImpl");
//        source_map.put(1, "pay.impl.ICBCBankImpl");
//        source_map.put(1, "pay.impl.ICBCBankImpl");

        //注解维护map，通过注解维护
        //使用反射机制，去读取自定义的注解
        Reflections reflections = new Reflections("codeOptimization.impl");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Pay.class);

        for (Class clazz : classSet) {

            Pay pay = (Pay) clazz.getAnnotation(Pay.class);
            //包名 + 类名
            source_map.put(pay.channlId(), clazz.getCanonicalName());
        }
    }

    //生产对象的方法，生产的具体实现
    public Strategy create(int channelId) throws Exception {
        //获取实现类类名
        String clazz = source_map.get(channelId);
        //反射
        Class clazz_ = Class.forName(clazz);

        return (Strategy) clazz_.newInstance();
    }


    public static StrategyFactory getInstance() {
        return strategyFactory;
    }


}
