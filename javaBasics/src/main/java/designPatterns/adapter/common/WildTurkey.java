package designPatterns.adapter.common;

/**
 * 火鸡的具体实现
 */
public class WildTurkey implements Turkey {

    @Override
    public void gobble() {
        System.out.println("火鸡的叫声：Go Go Go......");
    }

    @Override
    public void fly() {
        System.out.println("火鸡的飞行......");
    }

}
