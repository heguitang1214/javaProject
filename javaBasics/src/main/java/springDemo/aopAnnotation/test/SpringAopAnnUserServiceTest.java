package springDemo.aopAnnotation.test;
import entry.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import springDemo.aopAnnotation.service.UserService;


//Dependency Injection
//Inverse of Control
public class SpringAopAnnUserServiceTest {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("springConf/spring_beans.xml");

		UserService service = (UserService)ctx.getBean("userService");
		System.out.println("动态代理对象:" + service.getClass());
		service.add(new User());

		ctx.destroy();
	}


}
