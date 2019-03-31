package designPatterns.composemode;

import java.util.ArrayList;
import java.util.Iterator;

public class CakeHouseMenu extends MenuComponent {
    private ArrayList<MenuComponent> menuItems;

    public CakeHouseMenu() {
        menuItems = new ArrayList<>();

        addItem("蛋糕：KFC Cake Breakfast", "boiled eggs&toast&cabbage", true, 3.99f);
        addItem("蛋糕：MDL Cake Breakfast", "fried eggs&toast", false, 3.59f);
        addItem("蛋糕：Stawberry Cake", "fresh stawberry", true, 3.29f);
        addItem("蛋糕：Regular Cake Breakfast", "toast&sausage", true, 2.59f);
    }

    private void addItem(String name, String description, boolean vegetable,
                         float price) {
        MenuItem menuItem = new MenuItem(name, description, vegetable, price);
        menuItems.add(menuItem);
    }

    public Iterator getIterator() {
        return new ComposeIterator(menuItems.iterator());
    }

    @Override
    public void print() {
        System.out.println("****This is CakeHouseMenu****");
    }

    // 其他功能代码

}
