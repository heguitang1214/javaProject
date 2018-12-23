package springDemo.ioc.springTest;
import springDemo.ioc.model.User;
import springDemo.ioc.service.UserService;
import springDemo.mySpring.BeanFactory;
import springDemo.mySpring.ClassPathXmlApplicationContext;


public class UserServiceTest {

	public static void main(String[] args) throws Exception {
		testAdd();
	}

	private static void testAdd() throws Exception {
		BeanFactory applicationContext = new ClassPathXmlApplicationContext();

		UserService service = (UserService)applicationContext.getBean("userService");

		User u = new User();
		u.setUsername("zhangsan");
		u.setPassword("zhangsan");
		service.add(u);
	}

}
