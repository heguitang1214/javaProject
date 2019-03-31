package designPatterns.composemode;

import java.util.ArrayList;
import java.util.Iterator;

public class SubMenu extends MenuComponent {
	private ArrayList<MenuComponent> menuItems;

	public SubMenu() {
		menuItems = new ArrayList<MenuComponent>();

		addItem("子菜单：Apple Cookie", "Apple&candy&Cookie", true, 1.99f);
		addItem("子菜单：Banana Cookie", "Banana&candy&Cookie", false, 1.59f);
		addItem("子菜单：Orange Cookie", "Orange&Cookie", true, 1.29f);
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
		System.out.println("****This is SubMenu****");
	};
	// 其他功能代码

}
