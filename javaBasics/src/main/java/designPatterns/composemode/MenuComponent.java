package designPatterns.composemode;

import java.util.Iterator;

/**
 * 菜单项、子菜单、菜单的统一继承的父类，用来解决存储一致性问题
 */
public abstract class MenuComponent {

    public String getName() {
        return "";
    }

    public String getDescription() {
        return "";
    }

    public float getPrice() {
        return 0;
    }

    public boolean isVegetable() {
        return false;
    }

    public abstract void print();

    public Iterator getIterator() {
        return new NullIterator();
    }
}
