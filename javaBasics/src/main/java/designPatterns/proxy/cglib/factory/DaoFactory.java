package designPatterns.proxy.cglib.factory;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/30]
 * Dao接口的工厂
 */
class DaoFactory {

    static Dao create() {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(DaoImpl.class);

        enhancer.setCallback(new Proxy());

        Dao dao = (Dao) enhancer.create();

        System.out.println("类型:" + dao.getClass());

        return dao;
    }

}
