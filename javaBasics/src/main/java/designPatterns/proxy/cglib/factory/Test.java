package designPatterns.proxy.cglib.factory;

/**
 * @author he_guitang
 * @version [1.0 , 2018/8/30]
 */
public class Test {

    public static void main(String[] args) {

        Dao dao = DaoFactory.create();

        dao.add(new Object());
        dao.add(1);
        dao.add("1");

    }

}
