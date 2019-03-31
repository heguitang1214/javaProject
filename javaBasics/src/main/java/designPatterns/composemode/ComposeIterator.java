package designPatterns.composemode;

import java.util.Iterator;
import java.util.Stack;

public class ComposeIterator implements Iterator {
	private Stack<Iterator> stack = new Stack<Iterator>();

	public ComposeIterator(Iterator iterator) {
		stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		if (stack.empty()) {
			return false;
		}
		Iterator iterator = stack.peek();
		if (!iterator.hasNext()) {
			stack.pop();
			return hasNext();
		} else {
			return true;
		}
	}

	@Override
	public Object next() {
		if (hasNext()) {
			Iterator iterator = stack.peek();
			MenuComponent mMenuComponent = (MenuComponent) iterator.next();
			stack.push(mMenuComponent.getIterator());
			return mMenuComponent;
		}
		return null;
	}

	@Override
	public void remove() {

	}

}
