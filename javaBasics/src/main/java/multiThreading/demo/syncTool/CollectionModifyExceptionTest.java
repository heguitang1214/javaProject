package multiThreading.demo.syncTool;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *	同步集合
 */
public class CollectionModifyExceptionTest {

	public static void main(String[] args) {
//		Collection<User> users = new ArrayList<>();
		Collection<User> users = new CopyOnWriteArrayList<>();
		/*
		 * ArrayList可能会造成无限循环
		 */
		users.add(new User("张三",28));
		users.add(new User("李四",25));			
		users.add(new User("王五",31));	
		Iterator itrUsers = users.iterator();
		while(itrUsers.hasNext()){
//			System.out.println("aaaa");
			User user = (User)itrUsers.next();
			if("张三".equals(user.getName())){
				users.remove(user);
				//itrUsers.remove();
			} else {
				System.out.println(user);
			}
		}
	}
}	 
