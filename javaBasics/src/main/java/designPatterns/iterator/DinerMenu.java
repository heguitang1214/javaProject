package designPatterns.iterator;

/**
 * 餐馆菜单
 */
public class DinerMenu {
    private final static int Max_Items = 5;
    private int numberOfItems = 0;
    private MenuItem[] menuItems;

    public DinerMenu() {
        menuItems = new MenuItem[Max_Items];
        addItem("餐馆：vegetable Blt", "bacon&lettuce&tomato&cabbage", true, 3.58f);
        addItem("餐馆：Blt", "bacon&lettuce&tomato", false, 3.00f);
        addItem("餐馆：bean soup", "bean&potato salad", true, 3.28f);
        addItem("餐馆：hotdog", "onions&cheese&bread", false, 3.05f);

    }

    private void addItem(String name, String description, boolean vegetable,
                         float price) {
        MenuItem menuItem = new MenuItem(name, description, vegetable, price);
        if (numberOfItems >= Max_Items) {
            System.err.println("sorry,menu is full!can not add another item");
        } else {
            menuItems[numberOfItems] = menuItem;
            numberOfItems++;
        }

    }

    public Iterator getIterator() {
        return new DinerIterator();
    }

    /**
     * 实现接口进行迭代
     */
    class DinerIterator implements Iterator {
        private int position;

        public DinerIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            if (position < numberOfItems) {
                return true;
            }

            return false;
        }

        @Override
        public Object next() {
            MenuItem menuItem = menuItems[position];
            position++;
            return menuItem;
        }
    }

    ;
}
