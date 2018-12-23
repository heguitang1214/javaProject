package designPatterns.proxy.cglib.factory;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/30]
 */
public class DaoImpl implements Dao {
    @Override
    public void add(Object o) {
        System.out.println("add(Object o)");
    }

    @Override
    public void add(int i) {
        System.out.println("add(int i)");
    }

    @Override
    public void add(String s) {
        System.out.println("add(String s)");
    }
}
