package thread.concurrentCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 同步集合：多个线程操作同一个数据，保证数据安全
 */
public class CollectionModifyExceptionTest {

    public static void main(String[] args) {
        copyOnWriteArrayListTest();
//        arrayListTest();
    }

    /**
     * 操作CopyOnWriteArrayList
     */
    private static void copyOnWriteArrayListTest(){
        Collection<User> users = new CopyOnWriteArrayList<>();
        /*
		 * ArrayList可能会造成无限循环
		 */
        users.add(new User("张三", 28));
        users.add(new User("李四", 25));
        users.add(new User("王五", 31));
        Iterator itrUsers = users.iterator();
        while (itrUsers.hasNext()) {
            User user = (User) itrUsers.next();
            if ("张三".equals(user.getName())) {
                users.remove(user);
                //itrUsers.remove();
            } else {
                System.out.println(user);
            }
        }
    }

    /**
     *  操作ArrayList，迭代的时候删除数据会出现java.util.ConcurrentModificationException
     */
    private static void arrayListTest() {
        Collection<User> users = new ArrayList<>();
		/*
		 * ArrayList可能会造成无限循环
		 */
        users.add(new User("张三", 28));
        users.add(new User("李四", 25));
        users.add(new User("王五", 31));
        Iterator itrUsers = users.iterator();
        while (itrUsers.hasNext()) {
			System.out.println("执行...");
            User user = (User) itrUsers.next();
            if ("李四".equals(user.getName())) {
                users.remove(user);
            } else {
                System.out.println(user);
            }
        }
        System.out.println(users);
    }

}	 
