package baseDemo;

import multiThreading.demo.syncTool.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *	同步集合
 *
 *
 */
public class CollectionModifyExceptionTest {

	public static void main(String[] args) {
		Collection<User> users = new ArrayList<>();
//		Collection<User> users = new CopyOnWriteArrayList<>();
		/*
		 * ArrayList可能会造成无限循环
		 */
		users.add(new User("张三1",28));
		users.add(new User("李四1",25));
		users.add(new User("王五1",31));
		Iterator itrUsers = users.iterator();
		while(itrUsers.hasNext()){
//			System.out.println("aaaaa");
			User user = (User)itrUsers.next();
			if("李四1".equals(user.getName())){
				users.remove(user);
				//itrUsers.remove();
			} else {
				System.out.println(user);
			}
		}
	}
}	 
