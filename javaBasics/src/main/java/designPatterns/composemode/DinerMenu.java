package designPatterns.composemode;

import java.util.Iterator;

public class DinerMenu extends MenuComponent {
	private final static int Max_Items = 5;
	private int numberOfItems = 0;
	private MenuComponent[] menuItems;

	public DinerMenu() {
		menuItems = new MenuComponent[Max_Items];
		addItem("餐馆：vegetable Blt", "bacon&lettuce&tomato&cabbage", true, 3.58f);
		addItem("餐馆：Blt", "bacon&lettuce&tomato", false, 3.00f);
		addItem("餐馆：bean soup", "bean&potato salad", true, 3.28f);
		addItem("餐馆：hotdog", "onions&cheese&bread", false, 3.05f);
		addSubMenu(new SubMenu());
		
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
	private void addSubMenu(MenuComponent mMenuComponent) {
		if (numberOfItems >= Max_Items) {
			System.err.println("sorry,menu is full!can not add another item");
		} else {
			menuItems[numberOfItems] = mMenuComponent;
			numberOfItems++;
		}

	}
	public Iterator getIterator() {
		return new ComposeIterator(new DinerIterator());
	}

	class DinerIterator implements Iterator {
		private int position;

		public DinerIterator() {
			position = 0;
		}

		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if (position < numberOfItems) {
				return true;
			}

			return false;
		}

		@Override
		public Object next() {
			// TODO Auto-generated method stub
			MenuComponent menuItem = menuItems[position];
			position++;
			return menuItem;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		System.out.println("****This is DinerMenu****");
	};
}
